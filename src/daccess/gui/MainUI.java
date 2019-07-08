package daccess.gui;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class MainUI extends JFrame {

	public static final int windowWidth = 800;
	public static final int windowHight = 480;


	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		
		
		
		
		JLabel lblImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainUI.class.getResource("/daccess/gui/src/img/logo.png"))));
		lblImage.setBounds(76, 89, 187, 211);
		getContentPane().add(lblImage);
		
		
		
		JButton btnExit = new JButton("Exit");
//		btnExit.setBounds(94, 351, 500, 100);
		btnExit.setContentAreaFilled(false);
		btnExit.setFocusPainted(false);
		btnExit.setBorder(new LineBorder(Color.BLACK));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(99, 343, 141, 35);
		getContentPane().add(btnExit);
		
		JLabel lblScanLabel = new JLabel("Scan to download the app");
		lblScanLabel.setFont(new Font("Cooper Black", Font.PLAIN, 26));
		lblScanLabel.setBounds(361, 53, 350, 101);
		getContentPane().add(lblScanLabel);
		
		JLabel lblQR = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainUI.class.getResource("/daccess/gui/src/img/QR_d.png"))));
		lblQR.setBounds(444, 147, 187, 211);
		getContentPane().add(lblQR);
	}
}
