package daccess;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import daccess.gui.UserMngUI;

public class Manager implements ActionMessageInterface {

	BTChip bt;
	Database db;
	TerminalMgr terminal;
	GPIO gpio;
	
	int mode = 0;
	
	public static void main(String[] args) {
		new Manager();
	}

	
	public Manager()
	{
		gpio = new GPIO(this);
		terminal = new TerminalMgr(this);
		terminal.showMainUI();
		db = new Database();
		bt = new BTChip(this);
		new NFC(this);
		new MQTT(this);
		Account ac =db.getAccount(758837);
		ac.setAdmin(true);
		ac.setApproved(true);
		ac.setPending(true);
		db.updateAccount(ac);
	}
	
	public void confirmSeamless()
	{
		if (around.size() > 0)
		{
			approveLogin(around.get(around.size()-1));
		}
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
			} else
			{
				rejectLogin(ac);
			}
			break;
		case 1:
			cardAccount.setNFC(id);
			db.createAccount(cardAccount);
			terminal.setMainToSub("Registration Completed", "Your account is now pending approval");
			startTimer(10);
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
		terminal.setMainToSub("Welcome!", ac.getFirstName() + " " + ac.getLastName(), new Color(0,50,0));
		gpio.openDoor();
		startTimer(4);
	}
	
	public void rejectLogin(Account ac)
	{
		terminal.setMainToSub("Access Denied!", null, new Color(180,0,0));
		startTimer(4);
	}
	
	ArrayList<Account> around = new ArrayList<Account>();
	public void seamlessIn(int badge)
	{
		Account ac = db.getAccount(badge);
		if (ac != null)
		{
			around.add(ac);
			System.out.println("Access granted! Welcome " + ac.getFirstName() + " @ " + LocalDateTime.now() + "\nWe have " + around.size() + " users around");
			gpio.openDoor();
		}
	}
	
	public void seamlessOut(int badge)
	{
		Account ac = new Account(0, badge, "", "", false, false, "", false, "");
		if (around.contains(ac))
		{
			around.remove(ac);
			System.out.println("Good bye " + ac.getFirstName() + " @ " + LocalDateTime.now() + "\nWe have " + around.size() + " users around");
		}
		if (around.size() == 0)
			startTimer(1);
	}
	
	int seconds=-1;
	public void timeLoop()
	{
		while (seconds > 0)
		{
			seconds = seconds - 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		terminal.setMainToBasic();
		gpio.closeDoor();
		mode = 0;
		seconds = -1;
	}
	
	public void startTimer(int seconds)
	{
		if (this.seconds >= 0)
		{
			this.seconds = Math.max(seconds, this.seconds);
		} else {
			this.seconds = Math.max(seconds, this.seconds);
			Thread th = new Thread() {
			    public void run(){
			        timeLoop();
			      }
			};
			th.start();
		}
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
			//int status = db.createAccount(badge, prm[1], prm[2], prm[3], "");
			int status = db.accountExist(badge) ? 1 : 0;
			switch (status)
			{
			case 0:
				bt.sendCommand("OKCMD001");
				mode = 1;
				//cardAccount = db.getAccount(badge);
				cardAccount = new Account(0, badge, prm[1] , prm[2], false, false, prm[3] , false, "");
				terminal.setMainToSub("Please swipe your ID card", null);
				startTimer(30);
				break;
			case 1:
				bt.sendCommand("ERROR901");
				break;
			}
			break;
		case "LOGIN":
			int badgeNo = Integer.parseInt(prm[0]);
			switch (db.verifyPassword(badgeNo, prm[1]))
			{
			case 0:
				Account acL = db.getAccount(badgeNo);
				bt.sendCommand(String.format("LOGOK%s^%s^%s^%s^", prm[0] , acL.getFirstName() , acL.getLastName(), acL.isAdmin() ? "1" : "0"));
				break;
			case 1:
				bt.sendCommand("ERROR904");
				break;
			case 2:
				bt.sendCommand("ERROR905");
				break;
			case 3:
				bt.sendCommand("ERROR906");
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