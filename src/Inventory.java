import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;

























import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JMenuBar;
import javax.swing.JButton;



















import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.Canvas;


public class Inventory extends JFrame {
	public Connection con= null;
	
	static boolean isAddingData = false;

	private JPanel contentPane;
	private JTextField tfiid;
	private JTextField tfiname;
	private JTextField tfquantity;
	private JTextField tfdefaultq;
	private boolean changes = false;
	JTable priceTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Inventory frame = new Inventory();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	

	/**
	 * Create the frame.
	 */
	public Inventory() {
		Random random = new Random();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1021, 601);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setToolTipText("Exit application");
        exitItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        
        JMenu moreItem = new JMenu("More");
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setToolTipText("About");
        aboutItem.addActionListener((ActionEvent event)->{
        	JOptionPane.showMessageDialog(this, "This application is developed by Anirudh M V and Keval S Thanki as a mini project for the 5th semester of BE as per VTU norms");
        });
        
        file.add(exitItem);
        moreItem.add(aboutItem);
        menuBar.add(file);
        menuBar.add(moreItem);

        setJMenuBar(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		
		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(inventoryPanel, "inventory");
		
		cardLayout.show(contentPane, "inventory");
		String data[][] = new String[100][4];
				
		
	//	assignDefaultInventory(data);
		
		//TODO Call API for MySql here
		String head[] = {"Item ID", "Item Name", "Current Quantity", "Minimum Quantity", "Actions"};
		inventoryPanel.setLayout(null);
		String[] options = {"Update", "Delete"};
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, head);
		JTable jt = new JTable(dm){
		      public boolean isCellEditable(int row,int column){
		          if(column == 4) return true;
		          return false;
		        }
		      };
		jt.setFont(new Font("Tahoma", Font.PLAIN, 17));
		jt.setRowMargin(5);
		      
		
		JComboBox jcb = new JComboBox(options);
		
		TableColumn col = jt.getColumnModel().getColumn(4);
	    col.setCellEditor(new DefaultCellEditor(jcb));
	    jcb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Action Performed");
				JComboBox jcb = (JComboBox) e.getSource();
				int selectedRow;
				System.out.println(selectedRow = jt.getSelectedRow());
				if(selectedRow >= 0 && jcb.getSelectedIndex() >= 0){
					System.out.println("Item ID = " + jt.getValueAt(selectedRow, 0));
					System.out.println("Action = " + options[jcb.getSelectedIndex()]);
				}
				jcb.setSelectedIndex(-1);
			}
		});
	    
		JScrollPane jsp = new JScrollPane(jt);
		jsp.setFont(new Font("Arial", Font.PLAIN, 15));
		jsp.setForeground(Color.BLACK);
		jsp.setBackground(Color.LIGHT_GRAY);
		jsp.setBounds(249,61,712,469);
		inventoryPanel.add(jsp);
		JButton manageSuppliers = new JButton("Suppliers");
		manageSuppliers.setForeground(Color.ORANGE);
		manageSuppliers.setBackground(Color.DARK_GRAY);
		manageSuppliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(contentPane, "suppliers");
			}
		});
		manageSuppliers.setFont(new Font("Constantia", Font.PLAIN, 20));
		manageSuppliers.setBounds(199, 11, 196, 39);
		inventoryPanel.add(manageSuppliers);
		
		
		/////////////////////////////////////////////////////
		JPanel suppliersPanel = new JPanel();
		suppliersPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(suppliersPanel, "suppliers");
		//////////////////////////////////////////////////////
		
		this.addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent e){
	        }
	    });
		
		
		String[][] suppliersData = new String[100][5];
		String[] suppliersHead = {"Supplier ID", "Supplier Name", "Supplier Address", "GSTID", "Email"};
		/*for(int ii = 0; ii < 100; ii++){
		for(int ij = 0; ij < 5; ij++){
				//suppliersData[ii][ij] = Inventory.getSaltString();
			}
		}*/
		suppliersPanel.setLayout(null);
		JTable supplierTable = new JTable(suppliersData, suppliersHead);
		//Supplier Table down.
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
			String q2 = "select sid as Supplier_ID ,suppliername as Supplier_Name, supplieraddress as Supplier_Address,gstid as GST_ID,email as Email  from supplier";
			PreparedStatement ps= con.prepareStatement(q2);
			ResultSet rs= ps.executeQuery();
			supplierTable.setModel(DbUtils.resultSetToTableModel(rs));
		
		}
		catch(Exception e3)
		{
			System.out.println(e3.getMessage());
		}
		
		jt.setRowHeight(30);
		
		JButton btnAddItem =  	new JButton("Add Item");
		btnAddItem.setForeground(Color.ORANGE);
		btnAddItem.setBackground(Color.DARK_GRAY);
		btnAddItem.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isAddingData){
					AddItem addItem = new AddItem();
					addItem.setVisible(true);
					setVisible(false);
					isAddingData = true;
					addItem.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent arg0) {
							// TODO Auto-generated method stub
							try{
								Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");

								String q= "select * from inventory";
							
								PreparedStatement ps1= con.prepareStatement(q);
								
								ResultSet rs= ps1.executeQuery();
								jt.setModel(DbUtils.resultSetToTableModel(rs));
								
							}
							catch(Exception e1)
							{
								System.out.println("fail");
							}
							jt.repaint();
						}
						
						@Override
						public void windowClosed(WindowEvent arg0) {
							// TODO Auto-generated method stub
							setVisible(true);
						}
						
						@Override
						public void windowActivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
				
				}
			}
		});
		btnAddItem.setBounds(10, 11, 179, 39);
		inventoryPanel.add(btnAddItem);
		supplierTable.setRowHeight(40);
		supplierTable.setEnabled(false);
		JScrollPane supplierSP = new JScrollPane(supplierTable);
		supplierSP.setBounds(126, 114, 793, 416);
		suppliersPanel.add(supplierSP);
		JButton goInventory = new JButton("Inventory");
		goInventory.setBackground(Color.DARK_GRAY);
		goInventory.setForeground(Color.ORANGE);
		goInventory.setFont(new Font("Constantia", Font.PLAIN, 20));
		goInventory.setBounds(643, 39, 274, 39);
		suppliersPanel.add(goInventory);
		
		JButton btnAddSupplier = new JButton("Manage Supplier");
		btnAddSupplier.setBackground(Color.DARK_GRAY);
		btnAddSupplier.setForeground(Color.ORANGE);
		btnAddSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isAddingData){
					AddSupplier addSupplier = new AddSupplier();
					addSupplier.setVisible(true);
					isAddingData = true;
					setVisible(false);
					addSupplier.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent arg0) {
							// TODO Auto-generated method stub
							try{
								Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");

								String q= "select * from supplier";
							
								PreparedStatement ps1= con.prepareStatement(q);
								
								ResultSet rs= ps1.executeQuery();
								supplierTable.setModel(DbUtils.resultSetToTableModel(rs));
								
							}
							catch(Exception e1)
							{
								System.out.println("fail");
							}
							jt.repaint();
						}
						
						@Override
						public void windowClosed(WindowEvent arg0) {
							// TODO Auto-generated method stub
							setVisible(true);
						}
						
						@Override
						public void windowActivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}
		});
		btnAddSupplier.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnAddSupplier.setBounds(126, 39, 274, 39);
		suppliersPanel.add(btnAddSupplier);
		goInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(contentPane, "inventory");
			}
		});
		try{
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");

			String q= "select * from inventory";
		
			PreparedStatement ps1= con.prepareStatement(q);
			
			ResultSet rs= ps1.executeQuery();
			jt.setModel(DbUtils.resultSetToTableModel(rs));
			
			JButton ilb = new JButton("Item Location");
			ilb.setForeground(Color.ORANGE);
			ilb.setBackground(Color.DARK_GRAY);
			ilb.setFont(new Font("Constantia", Font.PLAIN, 20));
			ilb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					cardLayout.show(contentPane, "location");
				}
			});
			
			ilb.setBounds(405, 11, 189, 39);
			inventoryPanel.add(ilb);
			
			JButton btnNewButton_1 = new JButton("Item Price");
			btnNewButton_1.setForeground(Color.ORANGE);
			btnNewButton_1.setBackground(Color.DARK_GRAY);
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(contentPane, "price");
					try{
						Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
						String q2 = "select itemid as Item_ID, sid as Supplier_ID, price as Price from item_price_supplier order by item_id";
						PreparedStatement ps= con.prepareStatement(q2);
						ResultSet rs= ps.executeQuery();
						priceTable.setModel(DbUtils.resultSetToTableModel(rs));
					}
					catch(Exception e2){
						System.out.println(e2);
					}
					
				}
			});
			btnNewButton_1.setFont(new Font("Constantia", Font.PLAIN, 20));
			btnNewButton_1.setBounds(604, 11, 189, 39);
			inventoryPanel.add(btnNewButton_1);
			
			JButton btnUpdate = new JButton("Update");
			btnUpdate.setForeground(Color.ORANGE);
			btnUpdate.setBackground(Color.DARK_GRAY);
			btnUpdate.setFont(new Font("Arial Black", Font.PLAIN, 13));
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
     					String q= "update inventory set ItemID= '"+tfiid.getText()+"',ItemName='"+tfiname.getText()+"',Quantity='"+ tfquantity.getText()+"',DefaultQuantity='"+tfdefaultq.getText()+"' where ItemID ='"+tfiid.getText()+"' " ;                      
						PreparedStatement ps= con.prepareStatement(q);	
						ps.execute();
						JOptionPane.showMessageDialog(null, "1 record Updated!");
						
						Connection con1= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");

						String q1= "select * from inventory";
					
						PreparedStatement ps1= con1.prepareStatement(q1);
						
						ResultSet rs= ps1.executeQuery();
						jt.setModel(DbUtils.resultSetToTableModel(rs));
					}
					
					catch(Exception e1)
					{
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error Updating!");
					}
					finally{
						jt.repaint();
					}
				
				}
			});
			btnUpdate.setBounds(10, 311, 101, 38);
			inventoryPanel.add(btnUpdate);
			
			JButton btnDelete = new JButton("Delete");
			btnDelete.setForeground(Color.ORANGE);
			btnDelete.setBackground(Color.DARK_GRAY);
			btnDelete.setFont(new Font("Arial Black", Font.PLAIN, 13));
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{

					try{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
						String q= "DELETE FROM inventory WHERE ItemID= '"+tfiid.getText()+"'";
						PreparedStatement ps= con.prepareStatement(q);	
						ps.execute();
						JOptionPane.showMessageDialog(null, "1 Record Deleted!");
						Connection con1= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");

						String q1= "select * from inventory";
					
						PreparedStatement ps1= con1.prepareStatement(q1);
						
						ResultSet rs= ps1.executeQuery();
						jt.setModel(DbUtils.resultSetToTableModel(rs));
						
					}
					
					catch(Exception e1)
					{
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error Deleting!");
					}
					finally{
						jt.repaint();
					}
					
				}
			});
			btnDelete.setBounds(138, 311, 101, 39);
			inventoryPanel.add(btnDelete);
			
			JLabel lblNewLabel = new JLabel("Item ID");
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
			lblNewLabel.setBounds(10, 116, 67, 14);
			inventoryPanel.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Item Name");
			lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
			lblNewLabel_1.setBounds(10, 160, 89, 14);
			inventoryPanel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("Quantity");
			lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
			lblNewLabel_2.setBounds(10, 204, 67, 14);
			inventoryPanel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("Default Quantity");
			lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 15));
			lblNewLabel_3.setBounds(10, 244, 116, 14);
			inventoryPanel.add(lblNewLabel_3);
			
			tfiid = new JTextField();
			tfiid.setToolTipText("press enter");
			tfiid.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					char ch = arg0.getKeyChar();
					if(ch == KeyEvent.VK_ENTER){
						//TODO Fill in the blanks
						Connection con;
						try {
							con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
						
	     					String q= "select itemname, quantity, defaultquantity from inventory where itemid = ?" ;                      
							PreparedStatement ps= con.prepareStatement(q);	
							ps.setInt(1, Integer.parseInt(tfiid.getText()));
							ResultSet rs = ps.executeQuery();
							rs.next();
							tfiname.setText(rs.getString(1));
							tfquantity.setText(String.valueOf(rs.getInt(2)));
							tfdefaultq.setText(String.valueOf(rs.getInt(3)));
							
						}
						catch (SQLException e) {
						}
					}
				}
			});
			tfiid.setBounds(138, 114, 101, 20);
			inventoryPanel.add(tfiid);
			tfiid.setColumns(10);
			
			tfiname = new JTextField();
			tfiname.setBounds(138, 158, 101, 20);
			inventoryPanel.add(tfiname);
			tfiname.setColumns(10);
			
			tfquantity = new JTextField();
			tfquantity.setBounds(138, 202, 101, 20);
			inventoryPanel.add(tfquantity);
			tfquantity.setColumns(10);
			
			tfdefaultq = new JTextField();
			tfdefaultq.setBounds(141, 242, 98, 20);
			inventoryPanel.add(tfdefaultq);
			tfdefaultq.setColumns(10);
			JButton orderi = new JButton("Order Items");
			orderi.setForeground(Color.ORANGE);
			orderi.setBackground(Color.DARK_GRAY);
			orderi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					cardLayout.show(contentPane, "orderPane");
				}
			});
			orderi.setFont(new Font("Constantia", Font.PLAIN, 20));
			orderi.setBounds(803, 11, 158, 39);
			inventoryPanel.add(orderi);
			
			
			
			
			
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
		JPanel locationPanel = new JPanel();
		locationPanel.setBackground(Color.LIGHT_GRAY);
		locationPanel.setForeground(Color.ORANGE);
		contentPane.add(locationPanel, "location");
		
		JPanel pricePanel = new JPanel();
		pricePanel.setBackground(Color.LIGHT_GRAY);
		pricePanel.setFont(new Font("Segoe Script", Font.PLAIN, 16));
		contentPane.add(pricePanel, "price");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
			String q2 = "select * from ilocation";
			PreparedStatement ps= con.prepareStatement(q2);
			ResultSet rs= ps.executeQuery();
		}
		catch(Exception e3)
		{
			System.out.println(e3.getMessage());
		}
		
		jt.repaint();
		pricePanel.setLayout(null);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(248, 128, 452, 402);
		pricePanel.add(scrollPane1);
		
		priceTable = new JTable();
		priceTable.setRowHeight(25);
		priceTable.setRowMargin(5);
		priceTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane1.setViewportView(priceTable);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
			String q2 = "select itemid as Item_ID, sid as Supplier_ID, price as Price from item_price_supplier";
			PreparedStatement ps= con.prepareStatement(q2);
			ResultSet rs= ps.executeQuery();
			priceTable.setModel(DbUtils.resultSetToTableModel(rs));
		
		}
		catch(Exception e3)
		{
			System.out.println(e3.getMessage());
		}
		JButton btnInventory = new JButton("Inventory");
		btnInventory.setForeground(Color.ORANGE);
		btnInventory.setBackground(Color.DARK_GRAY);
		btnInventory.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "inventory");
			}
		});
		btnInventory.setBounds(376, 28, 193, 35);
		pricePanel.add(btnInventory);
		
		JPanel orderPanel = new JPanel();
		orderPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(orderPanel, "orderPane");
		
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                int confirmExit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Pres yes to exit", JOptionPane.YES_NO_OPTION);

                if(confirmExit == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }else{
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
		orderPanel.setLayout(null);
		
		JScrollPane scrollPane11 = new JScrollPane();
		scrollPane11.setBackground(Color.LIGHT_GRAY);
		scrollPane11.setBounds(211, 84, 596, 446);
		orderPanel.add(scrollPane11);
		
		JTable orderTable = new JTable();
		orderTable.setEnabled(false);
		scrollPane11.setViewportView(orderTable);
		
		JButton btnNewButton = new JButton("Items To Order");
		btnNewButton.setForeground(Color.ORANGE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q= "select ItemID, itemname, quantity, defaultquantity from inventory where Quantity < DefaultQuantity";
					PreparedStatement ps= con.prepareStatement(q);
					ResultSet rs= ps.executeQuery();
					orderTable.setModel(DbUtils.resultSetToTableModel(rs));
					orderTable.repaint();
					
					HashSet<Integer> hashSetOfOrders = new HashSet<Integer>();
					rs= ps.executeQuery();
					while(rs.next()){
						hashSetOfOrders.add(rs.getInt(1));
						System.out.println(hashSetOfOrders);
					}
					
//					System.out.println("test1");
					while(!hashSetOfOrders.isEmpty()){
						q= "select isp.itemid, isp.sid, price, i.itemname, s.email, s.suppliername, defaultquantity, quantity from item_price_supplier isp, supplier s, inventory i where price in (select MIN(price) from item_price_supplier isp group by itemid) and isp.sid = s.sid and i.itemid = isp.itemid";
						ps= con.prepareStatement(q);
						rs= ps.executeQuery();
						while(rs.next()){
//							System.out.println("testwhile");
							System.out.println(rs.getInt(1));
	
							System.out.println(hashSetOfOrders.contains(rs.getInt(1)));
							if(hashSetOfOrders.contains(rs.getInt(1))){
//								System.out.println("testif");
						        int itemid = rs.getInt(1);
						        
						        int price = rs.getInt(3);
						        String itemName = rs.getString(4);
						        String supplierName = rs.getString(6);
						        int minquantity = rs.getInt(7);
						        int newQuantity = 2*minquantity + 10;
						        int newerQuantity = newQuantity + rs.getInt(8); 
	
						        String body =  "Hello "+ supplierName + ",\nWe request you to ship " + newQuantity + " quantity of " + itemName + " at the specified price of " + price + ". Kindly ship the items ASAP. \n\n Regards,\nThe Warehouse";
//						        System.out.println("testHello");
						        try{
						        	sendMail(rs, body, itemid, price, itemName, supplierName, minquantity, newQuantity, newerQuantity);
						        }
						        catch(Exception e5){
						            System.out.println("Email couldn't be sent, please contact Admin");
						            JOptionPane.showMessageDialog(null, "Email couldn't be sent, please contact Admin");
						        }	
						        
						        try{
					            	q = "update inventory set Quantity = ? where itemid = ?";	//query to update quantity
						            ps= con.prepareStatement(q);
						            ps.setInt(1, newerQuantity);
						            ps.setInt(2, rs.getInt(1));
									ps.execute();
//									System.out.println("\nItemID remove = " + itemid);
						            hashSetOfOrders.remove(itemid);
						            
//									System.out.println("Hashset of order = " + hashSetOfOrders);
									Class.forName("com.mysql.jdbc.Driver");
									Connection con1= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
									String q1= "select * from inventory";
									PreparedStatement ps1= con1.prepareStatement(q1);
									rs = ps1.executeQuery();
									jt.setModel(DbUtils.resultSetToTableModel(rs));
//									System.out.println("not break");
								}
								
								catch(Exception e1)
								{
									System.out.println(e1.getMessage());
									JOptionPane.showMessageDialog(null, "Error Updating!");
								}
								finally{
									jt.repaint();
								}
						    }
						//email code
						}
					}
//					System.out.println("*****WHILE LOOP ENDED SUCCESSFULLY");
				}
				catch(Exception e3)
				{
					System.out.println("Error error mail error " + e3.getMessage());
				}
				
				
			}
		});
		btnNewButton.setBounds(211, 26, 251, 43);
		orderPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Inventory");
		btnNewButton_1.setForeground(Color.ORANGE);
		btnNewButton_1.setBackground(Color.DARK_GRAY);
		btnNewButton_1.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "inventory");
			}
		});
		btnNewButton_1.setBounds(648, 26, 159, 43);
		orderPanel.add(btnNewButton_1);
		locationPanel.setLayout(null);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBackground(Color.LIGHT_GRAY);
		scrollPane2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane2.setBounds(63, 164, 878, 366);
		locationPanel.add(scrollPane2);
		
		JTable table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setRowMargin(5);
		table.setRowHeight(30);
		scrollPane2.setViewportView(table);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
			String q2 = "select Itemid as Item_ID, icondition as Item_Condition, location as Location,  last_updated as Last_Updated from ilocation";
			PreparedStatement ps= con.prepareStatement(q2);
			ResultSet rs= ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		
		}
		catch(Exception e3)
		{
			System.out.println(e3.getMessage());
		}
		
		JButton btnInventroy1 = new JButton("Inventory");
		btnInventroy1.setBackground(Color.DARK_GRAY);
		btnInventroy1.setForeground(Color.ORANGE);
		btnInventroy1.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnInventroy1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				cardLayout.show(contentPane, "inventory");
			}
		});
		btnInventroy1.setBounds(363, 29, 257, 35);
		locationPanel.add(btnInventroy1);
		
		JLabel lblItemId = new JLabel("Item Id");
		lblItemId.setFont(new Font("Arial", Font.BOLD, 15));
		lblItemId.setBounds(135, 94, 47, 18);
		locationPanel.add(lblItemId);
		
		JLabel lblItemCondition = new JLabel("Item Condition ");
		lblItemCondition.setFont(new Font("Arial", Font.BOLD, 15));
		lblItemCondition.setBounds(260, 94, 107, 18);
		locationPanel.add(lblItemCondition);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Arial", Font.BOLD, 15));
		lblLocation.setBounds(618, 94, 61, 18);
		locationPanel.add(lblLocation);
		
		JTextField lid = new JTextField();
		lid.setBounds(135, 123, 86, 20);
		locationPanel.add(lid);
		lid.setColumns(10);
		
		JTextField lcondition = new JTextField();
		lcondition.setBounds(260, 123, 86, 20);
		locationPanel.add(lcondition);
		lcondition.setColumns(10);
		
		JTextField llocation = new JTextField();
		llocation.setBounds(618, 123, 86, 20);
		locationPanel.add(llocation);
		llocation.setColumns(10);
		
		JButton btnUpdateEntry = new JButton("Update Entry");
		btnUpdateEntry.setBackground(Color.DARK_GRAY);
		btnUpdateEntry.setForeground(Color.ORANGE);
		btnUpdateEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q= "insert into ilocation(ItemID,icondition,location,last_updated) values(?,?,?,curdate())";
					PreparedStatement ps= con.prepareStatement(q);	
					
					int tid1 = Integer.parseInt(lid.getText());
					ps.setInt(1,tid1);
					
					ps.setString(2,lcondition.getText());
					
					ps.setString(3,llocation.getText());
					
					ps.execute();
					lid.setText("");
					lcondition.setText("");
					llocation.setText("");
					JOptionPane.showMessageDialog(null, "1 record Updated!");
					
					String q2 = "select Itemid as Item_ID, icondition as Item_Condition, location as Location,  last_updated as Last_Updated from ilocation";
					PreparedStatement ps1= con.prepareStatement(q2);
					ResultSet rs= ps1.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				
				catch(Exception e1)
				{
					try{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
						System.out.println("abc");
						String q= "update ilocation set icondition = ?,location = ?, last_updated = curdate() where  ItemId = ?";
						PreparedStatement ps= con.prepareStatement(q);	
						
						int tid1 = Integer.parseInt(lid.getText());
						ps.setInt(3,tid1);
						
						ps.setString(1,lcondition.getText());
						
						ps.setString(2,llocation.getText());
						
						ps.execute();
						lid.setText("");
						lcondition.setText("");
						llocation.setText("");
						JOptionPane.showMessageDialog(null, "1 record Updated!");
						
						String q2 = "select Itemid as Item_ID, icondition as Item_Condition, location as Location,  last_updated as Last_Updated from ilocation";
						PreparedStatement ps1= con.prepareStatement(q2);
						ResultSet rs= ps1.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}
					catch(Exception e2){
						System.out.println(e2.getMessage());
						JOptionPane.showMessageDialog(null, "Error Updating!");
					}
				}
				finally{
					table.repaint();
				}
				
				
			}
		});
		btnUpdateEntry.setFont(new Font("Constantia", Font.BOLD, 15));
		btnUpdateEntry.setBounds(374, 126, 220, 27);
		locationPanel.add(btnUpdateEntry);
		
	}
	public static boolean sendMail(ResultSet rs, String body, int itemid, int price, String itemName, String supplierName, int minquantity, int newQuantity, int newerQuantity) throws Exception{
		
		String user = "wims21svit@gmail.com"; 
        String pass = "abcd$#@!";
        String to = rs.getString(5);
        String subject = "Order Request from Warehouse";
        String msg = body;
        boolean sessionDebug = false;
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.required", "true");

//                java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        Session session = Session.getDefaultInstance(properties,  
            new javax.mail.Authenticator() {  
                protected PasswordAuthentication getPasswordAuthentication() {  
                    return new PasswordAuthentication(user,pass);  
                }  
            });

        session.setDebug(sessionDebug); 

        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject(subject);  
        message.setText(msg);  
        Transport.send(message);  
        System.out.println("Mail sent successfully!");
		return true;
	}
}