package daccess;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import daccess.gui.UserMngUI;

public class Manager implements ActionMessageInterface {

	BTChip bt;
	Database db;
	TerminalMgr terminal;
	
	public static void main(String[] args) {
		new Manager();
	}
	
	public Manager()
	{
		terminal = new TerminalMgr(this); 
		terminal.showMainUI();
		bt = new BTChip(this);
		db = new Database();
	}
	
	public void showPengingMgmt()
	{
		terminal.showPengingMgmt(db.getPendingAccounts());
	}
	
	public void showUsersMgmt()
	{
		terminal.showUsersMgmt(db.getAllAccounts());
	}

	@Override
	public void processAction(String action) {
		System.out.println("New message: " + action);
		String cmd = action.substring(0,5);
		String prm[] = null;
		if (action.length()>5)
			prm = action.substring(5).split("\\^");
		switch (cmd)
		{
		case "CREAT":
			int badge = Integer.parseInt(prm[0]);
			int status = db.createAccount(badge, prm[1], prm[2], prm[3]);
			switch (status)
			{
			case 0:
				bt.sendCommand("OKCMD001");
				break;
			case 1:
				bt.sendCommand("ERROR901");
				break;
			}
			break;
		case "LOGIN":
			int badgeNo = Integer.parseInt(prm[0]);
			ResultSet r = db.executeQ("Select * From Accounts where badge = " + badgeNo + " Limit 1;");
			switch (db.verifyPassword(badgeNo, prm[1]))
			{
			case 0:
				try {
					bt.sendCommand(String.format("LOGOK%s^%s^%s^%s^", new String[] {Integer.toString(badgeNo) , r.getString("FirstName") , r.getString("LastName") , (r.getBoolean("isAdmin") ? "1" : "0")}));
				} catch (SQLException e) {
				}
				break;
			case 1:
				bt.sendCommand("ERROR904");
				break;
			case 2:
				bt.sendCommand("ERROR905");
				break;
			}
			break;
		case "OPEND":
			Account ac = db.getAccount(Integer.parseInt(prm[0]));
			if (!ac.isPending())
			{
				bt.sendCommand("ERROR902");	
			} else if (!ac.isApproved()) {
				bt.sendCommand("ERROR903");
			} else
				bt.sendCommand("OKCMD002");
			break;
		case "ADMPN":
			Account ac2 = db.getAccount(Integer.parseInt(prm[0]));
			if (ac2.isAdmin() && ac2.isPending())
			{
				terminal.showAdminView();
				bt.sendCommand("OKADM");
			}
			else
				{
				bt.sendCommand("FLGOT");
				}
			break;		
		}
	}
}