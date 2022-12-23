import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchPart extends JFrame {

	private JPanel contentPane;
	private JTextField partID;
	private JTextField Description;
	public static B_PLus_Tree<String, String> tree;

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
	public void close()
	{
		this.hide();
	}
	public void Search()
	{
		String ID=partID.getText();
		String Description1=this.tree.searchNode(ID);
		if(Description1==null)
		{
			JOptionPane.showMessageDialog(contentPane,"Part not found.","Alert",JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			Description.setText(Description1);
		}
	}
	public SearchPart(B_PLus_Tree<String, String> tree) {
		this.tree=tree;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Search Part");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setBounds(141, 28, 121, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Part ID");
		lblNewLabel_1.setBounds(89, 74, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		partID = new JTextField();
		partID.setBounds(141, 71, 139, 20);
		contentPane.add(partID);
		partID.setColumns(10);
		
		Description = new JTextField();
		Description.setEditable(false);
		Description.setBounds(24, 179, 388, 248);
		contentPane.add(Description);
		Description.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Search Part Along With Next 10 Part");
		lblNewLabel_2.setBounds(24, 161, 207, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				goTomain();
			}
		});
		btnNewButton.setBounds(10, 438, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search();
			}
		});
		search.setBounds(271, 120, 89, 23);
		contentPane.add(search);
	}
}
