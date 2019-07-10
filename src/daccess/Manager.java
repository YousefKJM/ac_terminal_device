package daccess;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import daccess.gui.UserMngUI;

public class Manager implements ActionMessageInterface {

	BTChip bt;
	Database db;
	TerminalMgr terminal;

	int mode = 0;
	
	public static void main(String[] args) {
		new Manager();
	}
	
	public Manager()
	{
		terminal = new TerminalMgr(this); 
		terminal.showMainUI();
		db = new Database();
		bt = new BTChip(this);
		new NFC(this);
//		Account ac =db.getAccount(758837);
//		ac.setAdmin(true);
//		ac.setApproved(true);
//		ac.setPending(true);
//		db.updateAccount(ac);
	}
	
	public void showPengingMgmt()
	{
		terminal.showPengingMgmt(db.getPendingAccounts());
	}
	
	public void showUsersMgmt()
	{
		terminal.showUsersMgmt(db.getAllAccounts());
	}
	
	Account cardAccount = null;
	public void cardRead(String id)
	{
		switch (mode)
		{
		case 0:
			Account ac = db.getAccount(id);
			if (ac != null)
			{
				approveLogin(ac);
			}
			break;
		case 1:
			cardAccount.setNFC(id);
			db.updateAccount(cardAccount);
			terminal.setMainToSub("Registration Completed", "Your account is now pending approval");
			mode = 0;
			break;
		}
	}
	
	public void approveLogin(int badge)
	{
		Account ac = db.getAccount(badge);
		approveLogin(ac);
	}

	public void approveLogin(Account ac)
	{
		terminal.setMainToSub("Welcome!", ac.getFirstName() + " " + ac.getLastName());
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
				mode = 1;
				cardAccount = db.getAccount(badge);
				terminal.setMainToSub("Please swipe your card", null);
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
					bt.sendCommand(String.format("LOGOK%s^%s^%s^%s^", new String[] {Integer.toString(badgeNo), r.getString("FirstName") , r.getString("LastName") , (r.getBoolean("isAdmin") ? "1" : "0")}));
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
				{
					bt.sendCommand("OKCMD002");
					approveLogin(ac);
				}
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