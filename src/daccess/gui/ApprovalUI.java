package daccess.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import daccess.Database;



@SuppressWarnings("serial")
public class ApprovalUI extends JFrame {

	
	Object[][] data;
	Database db;
	public ApprovalUI(Object[][] d, Database db) {
		data = d;
		this.db = db;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(0, -22, 800, 502);
		//setSize(windowWidth, windowHight);
//		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblPendingApprovals = new JLabel("Pending Approvals");
		lblPendingApprovals.setHorizontalAlignment(SwingConstants.CENTER);
		lblPendingApprovals.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblPendingApprovals.setBounds(95, 7, 625, 54);
		getContentPane().add(lblPendingApprovals);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 65, 779, 2);
		getContentPane().add(separator);
		

		 
		JTable table = createTable();
		
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 73, 779, 378);
		getContentPane().add(scrollPane);
		
//-------------------------------------------------------------------------------------

		final Database dbs = db;
		Action approve = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        int badge = (int) ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 0);
		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		        dbs.approveAccount(badge, true);
		    }
		};

		ButtonColumn buttonColumnA = new ButtonColumn(table, approve, 3);
		buttonColumnA.setMnemonic(KeyEvent.VK_D);
		
		
		Action reject = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        int badge = (int) ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 0);
		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		        dbs.approveAccount(badge, false);

		    }
		};
		ButtonColumn buttonColumnB = new ButtonColumn(table, reject, 4);
		buttonColumnB.setMnemonic(KeyEvent.VK_D);
//-------------------------------------------------------------------------------------
	
		final ApprovalUI prnt = this;
		JButton btnBack = new JButton();
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prnt.dispose();
			}
		});
		btnBack.setBounds(10, 15, 64, 40);
		btnBack.setContentAreaFilled(false);
		btnBack.setFocusPainted(false);
		btnBack.setIcon(new ImageIcon(ApprovalUI.class.getResource("/daccess/gui/src/img/back-arrow_32.png")));
		getContentPane().add(btnBack);
		

	
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	 private JTable createTable() {
			String[] columnNames = {"Badge Number", "First Name", "Last Name", "", ""};

			DefaultTableModel model = new DefaultTableModel(data, columnNames) {
				@Override
				   public boolean isCellEditable(int row, int column) {
				       //Only the third and fourth column
				       return column == 3 || column == 4;
				   }
				 @Override
		            public Class<?> getColumnClass(int column) {
		                switch (column) {
		                    case 0:
		                        return String.class;
		                    case 1:
		                        return String.class;
		                    case 2:
		                        return String.class;
		                    case 3:
		                        return JButton.class;
		                    default:
		                        return JButton.class;
		                }
		            }
			};
			
			JTable table = new JTable(model);
			table.setBorder(null);
			table.setShowGrid(false);
			table.setRowSelectionAllowed(false);
	        table.setShowVerticalLines(false);
	        table.setShowHorizontalLines(false);
	        table.setRowHeight(50);
	        table.setFont(new Font("Arial", Font.PLAIN, 16));
	        table.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 16));
	        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
//	        table.getTableHeader().setBackground(Color.WHITE);
//	        table.setBackground(Color.WHITE);
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	        table.setDefaultRenderer(String.class, centerRenderer);
	        table.getTableHeader().setReorderingAllowed(false);
	        table.getTableHeader().setResizingAllowed(false);
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        table.getColumnModel().getColumn(0).setPreferredWidth(175);
	        table.getColumnModel().getColumn(1).setPreferredWidth(170);
	        table.getColumnModel().getColumn(2).setPreferredWidth(170);
	        table.getColumnModel().getColumn(3).setPreferredWidth(123);
	        table.getColumnModel().getColumn(4).setPreferredWidth(123);
	        table.setFocusable(false);

	        

	        return table;
	        
	 }

}