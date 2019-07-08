package daccess.gui;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


@SuppressWarnings("serial")
public class MenuUI extends JFrame {

	public static final int windowWidth = 800;
	public static final int windowHight = 480;

	public MenuUI() {
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
		btnPendingApprovals.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
		btnPendingApprovals.setContentAreaFilled(false);
		btnPendingApprovals.setFocusPainted(false);
		btnPendingApprovals.setBorder(new LineBorder(Color.BLACK));
		btnPendingApprovals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnPendingApprovals.setBounds(194, 87, 404, 126);
		getContentPane().add(btnPendingApprovals);
		
		
		
		
		JButton btnUserManagement = new JButton("User Management");
		btnUserManagement.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
//		btnUserManagement.setContentAreaFilled(false);
//		btnUserManagement.setFocusPainted(false);
		btnUserManagement.setBorder(new LineBorder(Color.BLACK));
		btnUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUserManagement.setBounds(194, 262, 404, 126);
		getContentPane().add(btnUserManagement);
		
		

	}

}
