package daccess.gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class UserMngUI extends JFrame {

	public static final int windowWidth = 800;
	public static final int windowHight = 480;
	
    @SuppressWarnings("unused")
	private static final int BOOLEAN_COLUMN = 2;

	/**
	 * Create the frame.
	 */
	
	Object[][] data;
	
	public UserMngUI(Object[][] data) {
		this.data = data;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 800, 480);
		setSize(windowWidth, windowHight);
//		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblUserMng = new JLabel("User Management");
		lblUserMng.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserMng.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblUserMng.setBounds(95, 7, 625, 54);
		getContentPane().add(lblUserMng);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 65, 779, 2);
		getContentPane().add(separator);
	
		JTable table = createTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 73, 779, 378);
		getContentPane().add(scrollPane);
		
		
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}
	
    private JTable createTable() {
		String[] columnNames = {"Badge Number", "First Name", "Last Name", "Admin", "Access"};

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
	                        return Boolean.class;
	                    default:
	                        return Boolean.class;
	                }
	            }
        	 public void setValueAt(Object value, int row, int col) {
        		    super.setValueAt(value, row, col);
        		    if (col == 3) {
        		        if ((Boolean) this.getValueAt(row, col) == true) {
        		            //code goes here
    	                    System.out.println("col 3, row " + row +": " + true);
                            
        		        }
        		        else if ((Boolean) this.getValueAt(row, col) == false) {
        		            //code goes here
    	                    System.out.println("col 3, row " + row +": " + false);
        		        }
        		    }
        		    
        		    if (col == 4) {
        		        if ((Boolean) this.getValueAt(row, col) == true) {
        		            //code goes here
    	                    System.out.println("col 4, row " + row +": " + true);
        		        }
        		        else if ((Boolean) this.getValueAt(row, col) == false) {
        		            //code goes here
    	                    System.out.println("col 4, row " + row +": " + false);
        		        }
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
//        table.setBackground(Color.WHITE);
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

