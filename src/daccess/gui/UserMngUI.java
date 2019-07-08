package daccess.gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class UserMngUI extends JFrame {

	public static final int windowWidth = 800;
	public static final int windowHight = 480;
	
    private static final int BOOLEAN_COLUMN = 2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public UserMngUI(Object[][] data) {
		this.data = data;
		getContentPane().setLayout(null);
		setBounds(100, 100, 800, 480);
		setSize(windowWidth, windowHight);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblUserMng = new JLabel("User Management");
		lblUserMng.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserMng.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
		lblUserMng.setBounds(274, 21, 253, 54);
		getContentPane().add(lblUserMng);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 80, 779, 2);
		getContentPane().add(separator);
		
	
		JTable table = createTable();
		

		
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 93, 779, 376);

		getContentPane().add(scrollPane);
		
		
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	Object[][] data;
	
    private JTable createTable() {
		String[] columnNames = {"Badge Number", "First Name", "Last Name", "Admin", "Access"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
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
        return table;
    }

}
