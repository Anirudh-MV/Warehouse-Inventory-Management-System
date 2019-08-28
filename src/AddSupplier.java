import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AddSupplier extends JFrame {

	private JPanel contentPane;
	private boolean changes = false;
	private JTextField tfgst;
	private JTextField tfsn;
	private JTextField tfmail;
	private JTextField tf_sa;
	private JTextField tfsid;
	Random random = new Random();
	private JButton btnDeleteSupplier;
	private JButton btnUpdateSupplier;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSupplier frame = new AddSupplier();
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
	public AddSupplier() {
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 663, 503);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfgst = new JTextField();
		tfgst.setHorizontalAlignment(SwingConstants.LEFT);
		tfgst.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfgst.setColumns(10);
		tfgst.setBounds(260, 265, 304, 41);
		contentPane.add(tfgst);
		
		JButton btnAddSupplier = new JButton("Add Supplier");
		btnAddSupplier.setBackground(Color.DARK_GRAY);
		btnAddSupplier.setForeground(Color.ORANGE);
		btnAddSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q1= "insert into supplier(sid,suppliername,supplieraddress,gstid,email) values(?,?,?,?,?)";
					PreparedStatement ps= con.prepareStatement(q1);
					
					int tfsid1=Integer.parseInt(tfsid.getText());
					ps.setInt(1,tfsid1);
					ps.setString(2,tfsn.getText());
					ps.setString(3,tf_sa.getText());
					ps.setString(4,tfgst.getText());
					ps.setString(5,tfmail.getText());
					
					ps.execute();
					
					
					JOptionPane.showMessageDialog(null, "Succesfully Added");
					changes = true;
					tfsid.setText("");
					tfsn.setText("");
					tf_sa.setText("");
					tfgst.setText("");
					tfmail.setText("");
					
					String q= "select itemid from inventory";
					PreparedStatement ps1= con.prepareStatement(q);
					ResultSet rs= ps1.executeQuery();
					while(rs.next()){
						q1= "insert into item_price_supplier(itemid, sid, price, last_updated) values(?,?,?, curdate())";
						ps= con.prepareStatement(q1);
						ps.setInt(1, rs.getInt(1));
						ps.setInt(2, tfsid1);
						ps.setInt(3,  random.nextInt(10000));
						ps.execute();
					}
				}
				
				catch(Exception e2)
				{
					JOptionPane.showMessageDialog(null, e2);
				}
				
			}
		});
		btnAddSupplier.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnAddSupplier.setBounds(53, 387, 177, 41);
		contentPane.add(btnAddSupplier);
		
		JLabel lblGstId_1 = new JLabel("GST ID");
		lblGstId_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblGstId_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblGstId_1.setBounds(66, 265, 130, 41);
		contentPane.add(lblGstId_1);
		
		tfsn = new JTextField();
		tfsn.setHorizontalAlignment(SwingConstants.LEFT);
		tfsn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfsn.setColumns(10);
		tfsn.setBounds(260, 114, 304, 41);
		contentPane.add(tfsn);
		
		JLabel lblGstId = new JLabel("Supplier Address");
		lblGstId.setHorizontalAlignment(SwingConstants.LEFT);
		lblGstId.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblGstId.setBounds(66, 166, 177, 41);
		contentPane.add(lblGstId);
		
		JLabel lblSupplierName = new JLabel("Supplier Name");
		lblSupplierName.setHorizontalAlignment(SwingConstants.LEFT);
		lblSupplierName.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblSupplierName.setBounds(66, 114, 152, 41);
		contentPane.add(lblSupplierName);
		
		JLabel lblEmailId = new JLabel("Email ID");
		lblEmailId.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmailId.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblEmailId.setBounds(66, 320, 130, 41);
		contentPane.add(lblEmailId);
		
		tfmail = new JTextField();
		tfmail.setHorizontalAlignment(SwingConstants.LEFT);
		tfmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfmail.setColumns(10);
		tfmail.setBounds(260, 320, 304, 41);
		contentPane.add(tfmail);
		
		tf_sa = new JTextField();
		tf_sa.setBounds(260, 178, 304, 76);
		contentPane.add(tf_sa);
		tf_sa.setColumns(10);
		
		JLabel lblSupplierId = new JLabel("Supplier ID");
		lblSupplierId.setHorizontalAlignment(SwingConstants.LEFT);
		lblSupplierId.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblSupplierId.setBounds(70, 62, 130, 41);
		contentPane.add(lblSupplierId);
		
		tfsid = new JTextField();
		tfsid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				char ch = arg0.getKeyChar();
				if(ch == KeyEvent.VK_ENTER){
					//TODO Fill in the blanks
					
					Connection con;
					try {

						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
						System.out.println("Hello1");
     					String q= "select suppliername, supplieraddress, gstid, email from supplier where sid = ?" ;  
						PreparedStatement ps= con.prepareStatement(q);	
						ps.setInt(1, Integer.parseInt(tfsid.getText()));
						ResultSet rs = ps.executeQuery();
						rs.next();
						tfsn.setText(rs.getString(1));
						tf_sa.setText(rs.getString(2));
						tfgst.setText(rs.getString(3));
						tfmail.setText(rs.getString(4));
						
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		tfsid.setHorizontalAlignment(SwingConstants.LEFT);
		tfsid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfsid.setColumns(10);
		tfsid.setBounds(260, 62, 304, 41);
		contentPane.add(tfsid);
		
		btnDeleteSupplier = new JButton("Delete Supplier");
		btnDeleteSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q1= "DELETE FROM supplier WHERE sid= '"+tfsid.getText()+"'";
					PreparedStatement ps= con.prepareStatement(q1);
					ps.execute();
					
					
					JOptionPane.showMessageDialog(null, "Succesfully Deleted");
					changes = true;
					tfsid.setText("");
					tfsn.setText("");
					tf_sa.setText("");
					tfgst.setText("");
					tfmail.setText("");
					
				}
				
				catch(Exception e2)
				{
					JOptionPane.showMessageDialog(null, e2);
				}
				
				
				
			}
		});
		btnDeleteSupplier.setForeground(Color.ORANGE);
		btnDeleteSupplier.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnDeleteSupplier.setBackground(Color.DARK_GRAY);
		btnDeleteSupplier.setBounds(253, 387, 177, 41);
		contentPane.add(btnDeleteSupplier);
		
		btnUpdateSupplier = new JButton("Update Supplier");
		btnUpdateSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{

				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
 					String q1= "update supplier set sid= '"+tfsid.getText()+"',suppliername='"+tfsn.getText()+"',supplieraddress='"+ tf_sa.getText()+"',gstid='"+tfgst.getText()+"',email='"+ tfmail.getText()+"' where sid ='"+tfsid.getText()+"' " ;                      
					PreparedStatement ps= con.prepareStatement(q1);	
					ps.execute();
					JOptionPane.showMessageDialog(null, "1 record Updated!");
				
					
				}
				
				catch(Exception e1)
				{
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error Updating!");
				}
				
				
				
				
				
				
				
				
				
			}
			
		});
		btnUpdateSupplier.setForeground(Color.ORANGE);
		btnUpdateSupplier.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnUpdateSupplier.setBackground(Color.DARK_GRAY);
		btnUpdateSupplier.setBounds(470, 387, 177, 41);
		contentPane.add(btnUpdateSupplier);
		
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				if(!changes){
					JOptionPane.showMessageDialog(null, "You haven't added any new supplier", "No Supplier Added", 1);
				}
				Inventory.isAddingData = false;
				changes = false;
			};
		});
	}
}
