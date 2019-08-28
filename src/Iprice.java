import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Font;


public class Iprice extends JFrame {

	private JPanel pricePanel;
	private JTable priceTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Iprice frame = new Iprice();
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
	public Iprice() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 602);
		pricePanel = new JPanel();
		pricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pricePanel);
		pricePanel.setLayout(null);
		
		
		JButton btnShowPrice = new JButton("Item Price");
		btnShowPrice.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnShowPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
				String q2 = "select * from item_price_supplier";
				PreparedStatement ps= con.prepareStatement(q2);
				ResultSet rs= ps.executeQuery();
				priceTable.setModel(DbUtils.resultSetToTableModel(rs));
				
				}
				catch(Exception e3)
				{
					System.out.println(e3.getMessage());
				}
			}
		});
		btnShowPrice.setBounds(60, 34, 180, 35);
		pricePanel.add(btnShowPrice);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 80, 659, 446);
		pricePanel.add(scrollPane);
		
		priceTable = new JTable();
		scrollPane.setViewportView(priceTable);
		
		JButton btnInventory = new JButton("Inventory");
		btnInventory.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inventory i= new Inventory();
				i.setVisible(true);
				dispose();
			}
		});
		btnInventory.setBounds(280, 34, 180, 35);
		pricePanel.add(btnInventory);
	}

}
