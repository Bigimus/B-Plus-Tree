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

public class DeletePart extends JFrame {

	private JPanel contentPane;
	private JTextField partID;
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
		String ID=partID.getText();
		String Description1=this.tree.searchNode(ID);
		if(Description1==null)
		{
			JOptionPane.showMessageDialog(contentPane,"Part successfully deleted.","Alert",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else
		{
			return true;
		}
	}
	public void deleteNode()
	{
		String partId=partID.getText();
		System.out.println(partId);
		
		if(Search())
		{
			this.tree.delete(partId);
			JOptionPane.showMessageDialog(contentPane,"Part successfully deleted.","Alert",JOptionPane.WARNING_MESSAGE);
			
		}
	}
	public DeletePart(B_PLus_Tree<String, String> tree) {
		this.tree=tree;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Delete Part");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setBounds(146, 11, 133, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Part ID");
		lblNewLabel_1.setBounds(69, 69, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		partID = new JTextField();
		partID.setBounds(125, 66, 117, 20);
		contentPane.add(partID);
		partID.setColumns(10);
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNode();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setBounds(260, 139, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				close();
				gotoMain();
			}
		});
		btnNewButton_1.setBounds(93, 139, 89, 23);
		contentPane.add(btnNewButton_1);
	}

}
