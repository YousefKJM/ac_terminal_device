package daccess;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import daccess.gui.*;

public class TerminalMgr {
	Manager t;
	public TerminalMgr(Manager t)
	{
		this.t = t;
	}
	
	public void showUsersMgmt(ArrayList<Account> accounts)
	{
		final Object[][] acts = new Object[accounts.size()][5];
		for (int i = 0 ; i < accounts.size() ; i++)
		{
			Object[] act = new Object[5];
			act[0] = accounts.get(i).getBadge();
			act[1] = accounts.get(i).getFirstName();
			act[2] = accounts.get(i).getLastName();
			act[3] = accounts.get(i).isAdmin();
			act[4] = accounts.get(i).isApproved();
			acts[i] = act;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
					javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
//					javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel");
					UserMngUI frame = new UserMngUI(acts, t.db);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showPengingMgmt(ArrayList<Account> accounts)
	{
		final Object[][] acts = new Object[accounts.size()][5];
		ImageIcon approveIcon = new ImageIcon(ApprovalUI.class.getResource("/daccess/gui/src/img/approve_32px.png"));
		ImageIcon rejectIcon = new ImageIcon(ApprovalUI.class.getResource("/daccess/gui/src/img/reject_32px.png")); 
		for (int i = 0 ; i < accounts.size() ; i++)
		{
			Object[] act = new Object[5];
			act[0] = accounts.get(i).getBadge();
			act[1] = accounts.get(i).getFirstName();
			act[2] = accounts.get(i).getLastName();
			act[3] = approveIcon;
			act[4] = rejectIcon;
			acts[i] = act;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
					ApprovalUI frame = new ApprovalUI(acts, t.db);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	MainUI mainWindow;
	public void showMainUI()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow = new MainUI();
					mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setMainToBasic()
	{
		mainWindow.showMainGUI();
	}

	public void setMainToSub(String msg, String sub, Color cl)
	{
		mainWindow.showMessage(msg, sub, cl);
		mainWindow.showSubGUI();
	}

	public void setMainToSub(String msg, String sub)
	{
		mainWindow.showMessage(msg, sub);
		mainWindow.showSubGUI();
	}
	
	public void showAdminView()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
					MenuUI frame = new MenuUI(t);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}