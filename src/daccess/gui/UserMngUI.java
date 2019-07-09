package daccess.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

import daccess.Database;

@SuppressWarnings("serial")
public class UserMngUI extends JFrame {

	
    @SuppressWarnings("unused")
	private static final int BOOLEAN_COLUMN = 2;

	/**
	 * Create the frame.
	 */
	
	Object[][] data;
	Database db;
	public UserMngUI(Object[][] data, Database db) {
		this.data = data;
		this.db = db;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(0, -22, 800, 502);
//		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblUserMng = new JLabel("User Management");
		lblUserMng.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserMng.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblUserMng.setBounds(95, 7, 625, 54);
		getContentPane().add(lblUserMng);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 65, 779, 2);
		getContentPane().add(separator);
	
		JTable table = createTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 73, 779, 378);
		getContentPane().add(scrollPane);
		
		JButton btnBack = new JButton();
		final UserMngUI prnt = this;
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prnt.dispose();
			}
		});
		btnBack.setBounds(10, 15, 64, 40);
		btnBack.setContentAreaFilled(false);
		btnBack.setFocusPainted(false);
		btnBack.setIcon(new ImageIcon(UserMngUI.class.getResource("/daccess/gui/src/img/back-arrow_32.png")));
		getContentPane().add(btnBack);
		
		
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
        	 final Database dbs = db;
        	 public void setValueAt(Object value, int row, int col) {
        		    super.setValueAt(value, row, col);
        		    if (col == 3) {
        		        int badge =  ((int) this.getValueAt(row, 0));
        		        dbs.setAdmin(badge, (Boolean) this.getValueAt(row, col));
        		    }
        		    
        		    if (col == 4) {
        		        int badge =  ((int) this.getValueAt(row, 0));
        		        dbs.setApproved(badge, (Boolean) this.getValueAt(row, col));
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

