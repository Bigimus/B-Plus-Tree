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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class UpdatePart extends JFrame {

	private JPanel contentPane;
	private JTextField PartID;
	private JTextField PartDescription;
	public  B_PLus_Tree<String, String> tree;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public void goTomain()
	{
		MainWindow frame=new MainWindow(this.tree);
		frame.setVisible(true);
	}
	public boolean Search()
	{
		String ID=PartID.getText();
		String Description1=this.tree.searchNode(ID);
		if(Description1==null)
		{
			JOptionPane.showMessageDialog(contentPane,"Part not found.","Alert",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else
		{
			PartDescription.setText(Description1);
			return true;
		}
	}
	
	public void close()
	{
		this.hide();
	}
	public void update()
	{
		if(Search()){
		String ID=PartID.getText();
		String Description1=this.tree.searchNode(ID);
		this.tree.delete(ID);
		this.tree.insert(ID, PartDescription.getText());
		
		JOptionPane.showMessageDialog(contentPane,"Successfully updated.","Alert",JOptionPane.WARNING_MESSAGE);
		String Description2=this.tree.searchNode(ID);
		System.out.println(Description2);
				}
	}
	public UpdatePart(B_PLus_Tree<String, String> tree) {
		this.tree=tree;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Update Part");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(160, 22, 133, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Part ID");
		lblNewLabel_1.setBounds(71, 78, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		PartID = new JTextField();
		PartID.setBounds(149, 75, 187, 20);
		contentPane.add(PartID);
		PartID.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search();
			}
		});
		btnNewButton.setBounds(284, 106, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Part Description");
		lblNewLabel_2.setBounds(71, 161, 89, 14);
		contentPane.add(lblNewLabel_2);
		
		PartDescription = new JTextField();
		PartDescription.setBounds(149, 158, 187, 20);
		contentPane.add(PartDescription);
		PartDescription.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnNewButton_1.setBackground(new Color(0, 153, 0));
		btnNewButton_1.setBounds(284, 204, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				goTomain();
			}
			
		});
		btnNewButton_2.setBounds(10, 204, 78, 23);
		contentPane.add(btnNewButton_2);
	}

}
