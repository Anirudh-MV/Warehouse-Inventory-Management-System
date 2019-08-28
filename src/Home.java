import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.sql.*;

import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField tu;
	private JPasswordField tp;
	
	
	Home(){
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 572);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(147, 112, 219));
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
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWarehouseInventoryManagement = new JLabel("WAREHOUSE INVENTORY MANAGEMENT SYSTEM");
		lblWarehouseInventoryManagement.setForeground(Color.BLACK);
		lblWarehouseInventoryManagement.setBackground(Color.GRAY);
		lblWarehouseInventoryManagement.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblWarehouseInventoryManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarehouseInventoryManagement.setBounds(22, 11, 749, 50);
		
		contentPane.add(lblWarehouseInventoryManagement);
		
		JLabel lblManagerId = new JLabel("Manager ID");
		lblManagerId.setForeground(Color.BLACK);
		lblManagerId.setFont(new Font("Arial Black", Font.BOLD, 21));
		lblManagerId.setBounds(353, 198, 153, 44);
		contentPane.add(lblManagerId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font("Arial Black", Font.BOLD, 21));
		lblPassword.setBounds(353, 295, 123, 50);
		contentPane.add(lblPassword);
		
		tu = new JTextField();
		tu.setToolTipText("Enter Email ");
		tu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tu.setBounds(290, 253, 262, 44);
		contentPane.add(tu);
		tu.setColumns(10);
		
		tp = new JPasswordField();
		tp.setToolTipText("Enter Password");
		tp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tp.setBounds(290, 346, 262, 48);
		contentPane.add(tp);
		
		JButton btnSubmit = new JButton("LOGIN");
		btnSubmit.setFont(new Font("Arial Black", Font.BOLD, 16));
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(Color.DARK_GRAY);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				String uname= tu.getText();
				String pswd =String.valueOf(tp.getPassword());
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse","root","");
					String q= "select * from Login where Email=? and Password= ?";
					PreparedStatement ps1= con.prepareStatement(q);
					ps1.setString(1, uname);
					ps1.setString(2, pswd);
					ResultSet rs= ps1.executeQuery();
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null, "Login Success");
						dispose();
						Inventory inventory = new Inventory();
						dispose();
						inventory.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "ERROR: Invalid LOGIN CREDENTIALS");
					}
				}
				catch(Exception e1)
				{
					System.out.println(e1.getMessage());
				}
				
				
				
				
			}
		});
		
		btnSubmit.setBounds(225, 405, 400, 44);
		contentPane.add(btnSubmit);
		
		JButton btnForgotPassword = new JButton("FORGOT PASSWORD");
		btnForgotPassword.setFont(new Font("Arial Black", Font.BOLD, 16));
		btnForgotPassword.setForeground(Color.WHITE);
		btnForgotPassword.setBackground(Color.DARK_GRAY);
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Contact Admin");
				System.exit(0);
			}
		});
		btnForgotPassword.setBounds(225, 460, 400, 44);
		contentPane.add(btnForgotPassword);
		
		JLabel lblNewLabel = new JLabel("");
		Image img=new ImageIcon(this.getClass().getResource("/inv.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		
		lblNewLabel.setBounds(360, 48, 128, 154);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		Image img1=new ImageIcon(this.getClass().getResource("/grad.jpg")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(img1));
		
		lblNewLabel_1.setBounds(0, -14, 800, 550);
		contentPane.add(lblNewLabel_1);
		
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new Home();
			}
		});
	}

}
