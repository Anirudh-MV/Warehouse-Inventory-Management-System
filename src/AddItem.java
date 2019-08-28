import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.awt.Color;

public class AddItem extends JFrame {

	String quantity = "";
	
	private JPanel contentPane;
	private JTextField tname;
	private JTextField tq;
	private JTextField tmq;
	private boolean changes = false;
	private JTextField tid;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddItem frame = new AddItem();
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
	public AddItem() {
		Random random = new Random();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 479, 405);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblItemName = new JLabel("Item Name");
		lblItemName.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblItemName.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemName.setBounds(34, 109, 130, 41);
		contentPane.add(lblItemName);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setBounds(34, 161, 130, 41);
		contentPane.add(lblQuantity);
		
		JLabel lblMinimum = new JLabel("Minimum quantity");
		lblMinimum.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblMinimum.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinimum.setBounds(22, 213, 167, 41);
		contentPane.add(lblMinimum);
		
		tname = new JTextField();
		tname.setHorizontalAlignment(SwingConstants.CENTER);
		tname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tname.setBounds(199, 110, 230, 41);
		contentPane.add(tname);
		tname.setColumns(10);
		
		tq = new JTextField();
		tq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if(!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE))){
					getToolkit().beep();
					e.consume();
				};
			}
		});
		tq.setHorizontalAlignment(SwingConstants.CENTER);
		tq.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tq.setColumns(10);
		tq.setBounds(199, 162, 230, 41);
		contentPane.add(tq);
		
		tmq = new JTextField();
		tmq.setHorizontalAlignment(SwingConstants.CENTER);
		tmq.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tmq.setColumns(10);
		tmq.setBounds(199, 214, 230, 41);
		contentPane.add(tmq);
		
		JButton btnAddItem = new JButton("ADD ITEM");
		btnAddItem.setForeground(Color.ORANGE);
		btnAddItem.setBackground(Color.DARK_GRAY);
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q= "insert into inventory(ItemID,ItemName,Quantity,DefaultQuantity) values(?,?,?,?)";
					PreparedStatement ps= con.prepareStatement(q);	
					
					int tid1 = Integer.parseInt(tid.getText());
					ps.setInt(1,tid1);
					
					ps.setString(2,tname.getText());
					
					int tq1 = Integer.parseInt(tq.getText());
					ps.setInt(3,tq1);
					
					int tmq1 = Integer.parseInt(tmq.getText());
					ps.setInt(4, tmq1);
					
					ps.execute();
					
					JOptionPane.showMessageDialog(null, "Succesfully Added");
					changes = true;
					
					tid.setText("");
					tname.setText("");
					tq.setText("");
					tmq.setText("");
					String q1= "select sid from supplier";
					PreparedStatement ps1= con.prepareStatement(q1);
					ResultSet rs= ps1.executeQuery();
					while(rs.next()){
						q1= "insert into item_price_supplier(itemid, sid, price, last_updated) values(?,?,?, curdate())";
						ps= con.prepareStatement(q1);
						ps.setInt(1, tid1);
						ps.setInt(2, rs.getInt(1));
						ps.setInt(3,  random.nextInt(10000));
						ps.execute();
					}
				}
				
				catch(Exception e1)
				{
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error inserting. Check if the an item with same ID already exists!");
				}
				finally{
					tid.requestFocus();
				}
				
				
			}
		});
		btnAddItem.setFont(new Font("Fira Sans Condensed Medium", Font.PLAIN, 26));
		btnAddItem.setBounds(123, 293, 230, 41);
		contentPane.add(btnAddItem);
		
		JLabel lblItemId = new JLabel("Item ID");
		lblItemId.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemId.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblItemId.setBounds(34, 57, 130, 41);
		contentPane.add(lblItemId);
		
		tid = new JTextField();
		tid.setHorizontalAlignment(SwingConstants.CENTER);
		tid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tid.setColumns(10);
		tid.setBounds(199, 58, 230, 41);
		contentPane.add(tid);
		
		
		
		
		tmq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if(!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE))){
					getToolkit().beep(); 
					e.consume();
				};
			}
		});
		
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				if(!changes){
					JOptionPane.showMessageDialog(null, "You haven't added any items", "No Items Added", 1);
				}
				Inventory.isAddingData = false;
				changes = false;
			};
		});
	}
}
