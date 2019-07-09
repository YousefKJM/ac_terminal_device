package daccess.gui;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import daccess.Manager;
import daccess.TerminalMgr;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


@SuppressWarnings("serial")
public class MenuUI extends JFrame {

	public static final int windowWidth = 800;
	public static final int windowHight = 480;
	ImageIcon approval = new ImageIcon(ApprovalUI.class.getResource("/daccess/gui/src/img/approved.png"));
	ImageIcon management = new ImageIcon(ApprovalUI.class.getResource("/daccess/gui/src/img/management.png")); 

	/**
	 * Create the frame.
	 */
	Manager t;
	public MenuUI(Manager t) {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 800, 480);
		setSize(windowWidth, windowHight);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnPendingApprovals = new JButton("Pending Approvals");
		btnPendingApprovals.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPendingApprovals.setIcon(approval);
		btnPendingApprovals.setHorizontalTextPosition(SwingConstants.CENTER);
		
		
		btnPendingApprovals.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPendingApprovals.setContentAreaFilled(false);
		btnPendingApprovals.setFocusPainted(false);
		btnPendingApprovals.setBorder(new LineBorder(Color.BLACK));
		final Manager tm = t;
		btnPendingApprovals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tm.showPengingMgmt();
			}
		});
		btnPendingApprovals.setBounds(105, 87, 273, 301);
		btnPendingApprovals.setIconTextGap(22);
		getContentPane().add(btnPendingApprovals);
		
		
		
		
		JButton btnUserManagement = new JButton("User Management");
		btnUserManagement.setIcon(management);
		btnUserManagement.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnUserManagement.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUserManagement.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnUserManagement.setContentAreaFilled(false);
		btnUserManagement.setFocusPainted(false);
		btnUserManagement.setBorder(new LineBorder(Color.BLACK));
		btnUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tm.showUsersMgmt();
			}
		});
		btnUserManagement.setBounds(428, 87, 273, 301);
		btnUserManagement.setIconTextGap(10);
		getContentPane().add(btnUserManagement);
		
		

	}

}
