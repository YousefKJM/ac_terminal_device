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
		bt = new BTChip(this);
		db = new Database();
		terminal = new TerminalMgr(); 
		terminal.showMainUI();
		Account ac = db.getAccount(758837);
		ac.setAdmin(true);
		ac.setApproved(true);
		ac.setPending(true);
		db.updateAccount(ac);
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
			bt.sendCommand("OKCMD002");
			break;
		case "ADMPN":
			terminal.showAdminView();
		}
	}
}