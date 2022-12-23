import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddPart extends JFrame {

	private JPanel contentPane;
	private JTextField PartID;
	private JTextField Description;
	public B_PLus_Tree<String, String> tree;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	 
		public void gotoMain(){
			MainWindow frame=new MainWindow(this.tree);
			frame.setVisible(true);
		}
		public void close()
		{
			this.hide();
		}
		public boolean Search()
		{
			String ID=PartID.getText();
			String Description1=this.tree.searchNode(ID);
			if(Description1==null)
			{
				
				return false;
			}
			else
			{
				return true;
			}
		}
		public void Add()
		{
			if(!Search())
			{
			String ID=PartID.getText();
			String description=Description.getText();
			this.tree.insert(ID, description);
			JOptionPane.showMessageDialog(contentPane,"Successfully Added.","Alert",JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(contentPane,"Already Exist","Alert",JOptionPane.WARNING_MESSAGE);
				
			}
		}
	public AddPart(B_PLus_Tree<String, String> tree) {
		this.tree=tree;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add Part");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setBounds(161, 11, 118, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Part ID");
		lblNewLabel_1.setBounds(62, 84, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Description");
		lblNewLabel_2.setBounds(62, 134, 69, 14);
		contentPane.add(lblNewLabel_2);
		
		PartID = new JTextField();
		PartID.setBounds(141, 81, 138, 20);
		contentPane.add(PartID);
		PartID.setColumns(10);
		
		Description = new JTextField();
		Description.setBounds(141, 131, 138, 20);
		contentPane.add(Description);
		Description.setColumns(10);
		
		JButton btnNewButton = new JButton("Add ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add();
				
			}
		});
		btnNewButton.setBackground(new Color(0, 102, 51));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(218, 175, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				gotoMain();
			}
		});
		btnNewButton_1.setBounds(10, 175, 89, 23);
		contentPane.add(btnNewButton_1);
	}

}
