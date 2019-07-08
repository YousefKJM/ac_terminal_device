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
	 * Launch the application.
	 */
	Object[][] data;
	
	public ApprovalUI(Object[][] data) {
		this.data = data;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 800, 480);
		setSize(windowWidth, windowHight);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblPendingApprovals = new JLabel("Pending Approvals");
		lblPendingApprovals.setHorizontalAlignment(SwingConstants.CENTER);
		lblPendingApprovals.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
		lblPendingApprovals.setBounds(274, 21, 253, 54);
		getContentPane().add(lblPendingApprovals);
		
/*		
		 DefaultListModel model = new DefaultListModel();
         model.addElement(new Item("Yousef", "798688"));
         model.addElement(new Item("Yousef", "798688"));
         model.addElement(new Item("Yousef", "798688"));
         JList list = new JList(model);
         list.setCellRenderer(new ItemCellRenderer());
         list.setBounds(10, 93, 769, 376); 	
         
         
		JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 93, 769, 376);
		getContentPane().add(scrollPane); */
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 80, 779, 2);
		getContentPane().add(separator);
		
		String[] columnNames = {"Badge Number", "First Name", "Last Name", "", ""};
		
		 
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       //Only the third column
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
		JTable table = new JTable();
		table.setModel(model);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 93, 779, 376);
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
	


	
/*	private JPanel supplyPanel(String name, String badge) { // Just supply something that extends JPanel. You can put as much data in as you want. E.g. "boolean isMouseHovering" etc.

		 	JLabel lblbadge = new JLabel(badge);
		 	lblbadge.setHorizontalAlignment(SwingConstants.CENTER);
		 	lblbadge.setVerticalAlignment(SwingConstants.CENTER);
		 	
		 	JLabel lblname = new JLabel(name);
		 	lblname.setHorizontalAlignment(SwingConstants.CENTER);
		 	lblname.setVerticalAlignment(SwingConstants.CENTER);

	        final JButton btnApprove = new JButton("Aprrove");
	        final JButton btnReject = new JButton("Reject");


	        final JPanel panel = new JPanel(new SpringLayout());
	        panel.setBorder(BorderFactory.createEmptyBorder(10,
	                                                        10,
	                                                        10,
	                                                        10));
//	        panel.setBackground(new Color((float) Math.random(),
//	                                      (float) Math.random(),
//	                                      (float) Math.random()));
	        panel.add(lblbadge);
	        panel.add(lblname);
	        panel.add(btnApprove);
	        panel.add(btnReject);
	        
	        SpringUtilities.makeCompactGrid(panel, 1,
	        		panel.getComponentCount(),
                 0, 6, 140, 2);


	        return panel;
	    } */
}

//class Item extends JPanel {
//    JLabel name = new JLabel(" ");
//    JLabel badge = new JLabel(" ");
//    JButton btnApprove = new JButton("Approve");    
//    JButton btnReject = new JButton("Reject");    
//
//
//    Item(){
//        setLayout(new SpringLayout());
//        add(name);
//        add(badge);
//        add(btnApprove);
//        add(btnReject);
//        SpringUtilities.makeCompactGrid(this, 1,
//        		this.getComponentCount(),
//             0, 6, 140, 2);
//    }
//}

 class Item {
    private String name;
    private String badge;

    public Item(String name, String badge) {
        this.name = name;
        this.badge = badge;
    }

    public String getBadge() {
        return badge;
    }

    public String getName() {
        return name;
    }

}
 /*
 class ItemCellRenderer extends JPanel implements ListCellRenderer<Item>{

     private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
 private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
 protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

       JLabel name = new JLabel(" ");
	   JLabel badge = new JLabel(" ");
	   JButton btnApprove = new JButton("Aprrove");    
	   JButton btnReject = new JButton("Reject"); 

     public ItemCellRenderer() {
    	 
       setLayout(new SpringLayout());
       add(name);
       add(badge);
       add(btnApprove);
       add(btnReject);
       SpringUtilities.makeCompactGrid(this, 1,
       		this.getComponentCount(),
            0, 6, 130, 2);
//         setLayout(new BorderLayout());
//         add(name, BorderLayout.WEST);
//         add(badge, BorderLayout.CENTER);
//         add(btnApprove, BorderLayout.EAST);
     }






 protected Border getNoFocusBorder() {
     Border border = UIManager.getBorder("List.cellNoFocusBorder");
     if (System.getSecurityManager() != null) {
         if (border != null) return border;
         return SAFE_NO_FOCUS_BORDER;
     } else {
         if (border != null &&
                 (noFocusBorder == null ||
                 noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) {
             return border;
         }
         return noFocusBorder;
     }
 }

     @Override
     public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
         setComponentOrientation(list.getComponentOrientation());

         Color bg = null;
         Color fg = null;

         JList.DropLocation dropLocation = list.getDropLocation();
         if (dropLocation != null
                         && !dropLocation.isInsert()
                         && dropLocation.getIndex() == index) {

             bg = UIManager.getColor("List.dropCellBackground");
             fg = UIManager.getColor("List.dropCellForeground");

             isSelected = true;
         }

         if (isSelected) {
             setBackground(bg == null ? list.getSelectionBackground() : bg);
             setForeground(fg == null ? list.getSelectionForeground() : fg);
         } else {
             setBackground(list.getBackground());
             setForeground(list.getForeground());
         }

         name.setText(value.getName());
         badge.setText(value.getBadge());
//         btnApprove.setText("Approve");
//         btnReject.setText("Reject");

         name.setForeground(getForeground());
         badge.setForeground(getForeground());
//         btnApprove.setForeground(getForeground());
//         btnReject.setForeground(getForeground());

         setEnabled(list.isEnabled());

         name.setFont(list.getFont());
         badge.setFont(list.getFont());
//         btnApprove.setFont(list.getFont());
//         btnReject.setFont(list.getFont());


         Border border = null;
         if (cellHasFocus) {
             if (isSelected) {
                 border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
             }
             if (border == null) {
                 border = UIManager.getBorder("List.focusCellHighlightBorder");
             }
         } else {
             border = getNoFocusBorder();
         }
         setBorder(border);

         return this;
     }
 }
*/



