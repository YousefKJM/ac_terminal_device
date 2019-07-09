package daccess.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;



@SuppressWarnings("serial")
public class ApprovalUI extends JFrame {

	public static final int windowWidth = 800;
	public static final int windowHight = 480;



	/**
	 * Create the frame.
	 */
	
	Object[][] data;
	public ApprovalUI(Object[][] d) {
		data = d;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(0, -22, 800, 480);
		setSize(windowWidth, windowHight);
//		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblPendingApprovals = new JLabel("Pending Approvals");
		lblPendingApprovals.setHorizontalAlignment(SwingConstants.CENTER);
		lblPendingApprovals.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblPendingApprovals.setBounds(95, 7, 625, 54);
		getContentPane().add(lblPendingApprovals);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 65, 779, 2);
		getContentPane().add(separator);
		

		 
		JTable table = createTable();
		
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 73, 779, 378);
		getContentPane().add(scrollPane);
		

		
		Action approve = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
//		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
//		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		        System.out.println("Approve --> Row: " + modelRow + " clicked!");
		    }
		};

		ButtonColumn buttonColumnA = new ButtonColumn(table, approve, 3);
		
		buttonColumnA.setMnemonic(KeyEvent.VK_D);
		
//-------------------------------------------------------------------------------------
		
		Action reject = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
//		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
//		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		        System.out.println("Reject --> Row:" + modelRow + " clicked!");

		    }
		};
		ButtonColumn buttonColumnB = new ButtonColumn(table, reject, 4);
		buttonColumnB.setMnemonic(KeyEvent.VK_D);
		
		

		

	
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	        table.setFont(new Font("Arial", Font.PLAIN, 20));
	        table.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 22));
	        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	        table.getTableHeader().setBackground(Color.WHITE);
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