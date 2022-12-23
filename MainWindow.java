import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	public static  B_PLus_Tree<String, String> tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		File file = new File("partfile.txt");
		try {
			tree = new B_PLus_Tree<String, String>();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Scanner input = null;
		try {
	    input = new Scanner(file); 
		}
		catch(Exception e)
		{
			System.out.println("file not found");
		}
	    int count = 0;
	    while (input.hasNext()) {
	      String word  = input.nextLine();
	     count=count+1;
	     String ID= word.substring(0, 7);
	     String Description= word.substring(15, word.length());
	     tree.insert(ID, Description);

	      

	    }
	  
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(tree);
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
	public void close()
	{
		this.hide();
	}
	public void search()
	{
		SearchPart obj = new SearchPart(this.tree);
		obj.setVisible(true);
		
	}
	public void add()
	{
		AddPart frame=new AddPart(this.tree);
		frame.setVisible(true);
		
	}
	public void delete()
	{
		DeletePart obj=new DeletePart(this.tree);
		obj.setVisible(true);
	}
	public void update()
	{
		UpdatePart obj=new UpdatePart(this.tree);
		obj.setVisible(true);
	}
	public MainWindow(B_PLus_Tree<String, String> tree) {
		this.tree=tree;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Part Managment System");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(117, 11, 213, 42);
		contentPane.add(lblNewLabel);
		
		
		JButton btnNewButton = new JButton("Search Part");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				search();
				
				
			}
		});
		btnNewButton.setBounds(115, 64, 113, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Part");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				add();
				
			}
		});
		btnNewButton_1.setBounds(117, 110, 111, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Update Part");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				update();
			}
		});
		btnNewButton_2.setBounds(117, 151, 111, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete  Part");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				delete();
				
			}
			
			
		});
		btnNewButton_3.setForeground(Color.RED);
		btnNewButton_3.setBounds(117, 196, 111, 23);
		contentPane.add(btnNewButton_3);
		
		
		
		JButton btnNewButton_4 = new JButton("Exit");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				close();
			}
		});
		btnNewButton_4.setBounds(116, 227, 113, 23);
		contentPane.add(btnNewButton_4);
	}
}
