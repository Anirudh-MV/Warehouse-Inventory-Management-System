import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.util.HashSet;
import java.util.Properties;


public class Iorders extends JFrame {

	private JPanel orderPanel;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Iorders frame = new Iorders();
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
	public Iorders() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 686);
		orderPanel = new JPanel();
		orderPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		orderPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(101, 147, 648, 519);
		orderPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Display Items to Order");
		btnNewButton.setBounds(153, 49, 182, 36);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
				//	String q= "select ItemID from inventory where Quantity < DefaultQuantity";
				// Stored Proc down .
					String q= "Call 'Login1'{}";
					PreparedStatement ps= con.prepareStatement(q);
					ResultSet rs= ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					HashSet<Integer> hashSetOfOrders = new HashSet<Integer>();
					while(rs.next()){
						hashSetOfOrders.add(rs.getInt(1));
					}
					q= "select isp.itemid, isp.sid, price, i.itemname, s.email, s.suppliername, defaultquantity, quantity from isp, supplier s, inventory i where price in (select MIN(price) from isp group by itemid) and isp.sid = s.sid and i.itemid = isp.itemid";
					ps= con.prepareStatement(q);
					rs= ps.executeQuery();
					
					while(rs.next()){
						if(hashSetOfOrders.contains(rs.getInt(1))){
					        int itemid = rs.getInt(1);
					        int price = rs.getInt(3);
					        String itemName = rs.getString(4);
					        String supplierName = rs.getString(6);
					        int minquantity = rs.getInt(7);
					        int newQuantity = 2*minquantity + 10;
					        int newerQuantity = newQuantity + rs.getInt(8); 

					        String body =  "Hello "+ supplierName + ",\nWe request you to ship " + newQuantity + " quantity of " + itemName + " at the specified price of " + price + ". Kindly ship the items ASAP. \n\n Regards,\nThe Warehouse";

					        try{
					                    //TODO Create a new email id pls
					            String user = "wims22svit@gmail.com"; 
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

//					                    java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

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
					            
					            System.out.println("Mail sent successfully! " + to);
					            //Query - update inventory set minquantity = newPrice where itemid = rs.getInt(1);
					            q = "update inventory set DefaultQuantity = " + newerQuantity + " where itemid = " + rs.getInt(1);
					            ps= con.prepareStatement(q);
								rs= ps.executeQuery();
					            hashSetOfOrders.remove(itemid);
					            
						        }
					        catch(Exception e5){
					            System.out.println("Email couldn't be sent, please contact Admin");
					            JOptionPane.showMessageDialog(null, "Email couldn't be sent, please contact Admin");
					        }	
					    }
					//email code
					}
				}
				catch(Exception e3)
				{
					System.out.println(e3.getMessage());
				}
				
				
			}
		});
		orderPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Inventory");
		btnNewButton_1.setBounds(466, 49, 173, 36);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inventory i=new Inventory();
				i.setVisible(true);
				dispose();
			}
		});
		orderPanel.add(btnNewButton_1);
	}
}
