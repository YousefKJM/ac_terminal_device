package daccess.gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class MainUI extends JFrame {

	public MainUI() {
		initialize();
	}
	
	JLabel lMain = new JLabel(new ImageIcon(MainUI.class.getResource("/daccess/gui/src/img/Main.jpg")));
	JLabel lSub = new JLabel(new ImageIcon(MainUI.class.getResource("/daccess/gui/src/img/msg.jpg")));
	JLabel lHeader = new JLabel("", SwingConstants.CENTER);
	JLabel lHeader2 = new JLabel("", SwingConstants.CENTER);
	
	public void showMainGUI()
	{
		lMain.setVisible(true);
		lSub.setVisible(false);
	}

	public void showSubGUI()
	{
		lMain.setVisible(false);
		lSub.setVisible(true);
	}
	
	public void showMessage(String header, String sub)
	{
		lHeader.setText(header);
		if (sub == null)
		{
			lHeader.setBounds(0, 200, 800, 35);
			lHeader2.setText("");
			lHeader.setFont(new Font("Liberation Serif" , Font.PLAIN , 30));
		} else
		{
			lHeader.setBounds(0, 100, 800, 40);
			lHeader2.setBounds(0, 200, 800, 35);
			lHeader2.setText(sub);
			lHeader.setFont(new Font("Liberation Serif" , Font.PLAIN , 40));
			lHeader2.setFont(new Font("Liberation Serif" , Font.PLAIN , 30));
		}
			showSubGUI();
	}
	
	private void initialize() {
		Container p = getContentPane();
		p.setLayout(null);
		p.add(lHeader);
		p.add(lHeader2);
		p.add(lMain);
		p.add(lSub);
		lMain.setBounds(0, 0, 800, 480);
		lSub.setBounds(0, 0, 800, 480);
		setBounds(0, 0, 800, 480);
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
