import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;


public class Ilocation extends JFrame {

	private JPanel locationPane;
	private JTable table;
	private JTextField lid;
	private JTextField lcondition;
	private JTextField llocation;
	private JTextField ldlocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ilocation frame = new Ilocation();
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
	public Ilocation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 797);
		locationPane = new JPanel();
		locationPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(locationPane);
		locationPane.setLayout(null);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(34, 238, 664, 475);
		locationPane.add(scrollPane2);
		
		table = new JTable();
		scrollPane2.setViewportView(table);
		
		JButton btnConnect = new JButton("Item Location");
		btnConnect.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
				String q2 = "select * from ilocation";
				PreparedStatement ps= con.prepareStatement(q2);
				ResultSet rs= ps.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				
				}
				catch(Exception e3)
				{
					System.out.println(e3.getMessage());
				}
			}
		});
		btnConnect.setBounds(143, 29, 160, 28);
		locationPane.add(btnConnect);
		
		JButton btnInventroy = new JButton("Inventory");
		btnInventroy.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnInventroy.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				Inventory i= new Inventory();
				i.setVisible(true);
				dispose();
			}
		});
		btnInventroy.setBounds(430, 29, 160, 28);
		locationPane.add(btnInventroy);
		
		JLabel lblItemId = new JLabel("Item Id");
		lblItemId.setFont(new Font("Arial", Font.BOLD, 15));
		lblItemId.setBounds(50, 98, 75, 14);
		locationPane.add(lblItemId);
		
		JLabel lblItemCondition = new JLabel("Item Condition ");
		lblItemCondition.setFont(new Font("Arial", Font.BOLD, 15));
		lblItemCondition.setBounds(186, 98, 117, 14);
		locationPane.add(lblItemCondition);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Arial", Font.BOLD, 15));
		lblLocation.setBounds(385, 98, 75, 14);
		locationPane.add(lblLocation);
		
		JLabel lblDefaultLocation = new JLabel("Default Location ");
		lblDefaultLocation.setFont(new Font("Arial", Font.BOLD, 15));
		lblDefaultLocation.setBounds(520, 98, 139, 14);
		locationPane.add(lblDefaultLocation);
		
		lid = new JTextField();
		lid.setBounds(36, 140, 86, 20);
		locationPane.add(lid);
		lid.setColumns(10);
		
		lcondition = new JTextField();
		lcondition.setBounds(206, 140, 86, 20);
		locationPane.add(lcondition);
		lcondition.setColumns(10);
		
		llocation = new JTextField();
		llocation.setBounds(374, 140, 86, 20);
		locationPane.add(llocation);
		llocation.setColumns(10);
		
		ldlocation = new JTextField();
		ldlocation.setBounds(532, 140, 86, 20);
		locationPane.add(ldlocation);
		ldlocation.setColumns(10);
		
		JButton btnUpdateEntry = new JButton("Update Entry");
		btnUpdateEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q= "insert into ilocation(ItemID,icondition,location,lastseen) values(?,?,?,?)";
					PreparedStatement ps= con.prepareStatement(q);	
					
					int tid1 = Integer.parseInt(lid.getText());
					ps.setInt(1,tid1);
					
					ps.setString(2,lcondition.getText());
					
					ps.setString(3,llocation.getText());
					ps.setString(4,ldlocation.getText());
					
					ps.execute();
					lid.setText("");
					lcondition.setText("");
					llocation.setText("");
					ldlocation.setText("");
					JOptionPane.showMessageDialog(null, "1 record Updated!");
					
					String q2 = "select * from ilocation";
					PreparedStatement ps1= con.prepareStatement(q2);
					ResultSet rs= ps1.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				
				catch(Exception e1)
				{
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error Updating!");
				}
				finally{
					table.repaint();
				}
				
				
			}
		});
		btnUpdateEntry.setFont(new Font("Constantia", Font.BOLD, 15));
		btnUpdateEntry.setBounds(284, 185, 145, 23);
		locationPane.add(btnUpdateEntry);
		
		
	}
}
