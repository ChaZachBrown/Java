/*Zach Brown
 * October 4 ,2016
 * 
 * 
 */




// This code gives the layout for the UI and
//   demonstrates two ways of updating the data
//   in a JTable.
// Another option to consider when using JTable is
//   creating your own data model by overriding
//   AbstractTableModel. You might use this option
//   if data for table was coming from say a DB.
//   One example: http://www.java2s.com/Code/Java/Swing-JFC/CreatingsimpleJTableusingAbstractTableModel.htm

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class gui extends JFrame {

	
	
	
	//global variables
	static String[] columnNames = {"Account ID",
            "Account Name",
            "Balance"};
	
	static Object[][] data = {
			{'1', "", new Integer(0)},
			{'2', "", new Integer(0)}};
	
	static JTextField fromField = new JTextField("",8);
	
	static JTextField toField = new JTextField("",8);
	
	static JTextField amountField = new JTextField("",8);
	
	public String getamountField(){
		return amountField.getText();
	}
	public void setamountField(String t){
		amountField.setText(t);
	}
	
	//creating TableModel and table to use it in
	static DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
	static JTable table1 = new JTable(dtm);

	//constructor *******************************************************************
	public gui() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		
		
		// The default size of a JTable is something like
		// 450 X 400.
		Dimension smallerSize = new Dimension(450, 50);
		table1.setPreferredScrollableViewportSize(smallerSize );
		table1.setEnabled(false);
		JScrollPane scrollPaneForTable = new JScrollPane(table1);
				
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.fill = GridBagConstraints.BOTH;

		contentPane.add(scrollPaneForTable,constraints);
		
		constraints.gridx = 0;
//		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(2, 4, 2, 4);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel toLabel = new JLabel("From:");
		contentPane.add(toLabel,constraints);
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		
		
		// Workaround, because of: http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4247013
		fromField.setMinimumSize(fromField.getPreferredSize());
		contentPane.add(fromField,constraints);
		
		
		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel fromLabel = new JLabel("To:");
		contentPane.add(fromLabel,constraints);
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		toField.setMinimumSize(toField.getPreferredSize());
		contentPane.add(toField,constraints);

		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel amountLabel = new JLabel("Amount:");
		contentPane.add(amountLabel,constraints);
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		amountField.setMinimumSize(amountField.getPreferredSize());
		contentPane.add(amountField,constraints);

		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JButton clearButton = new JButton("Clear");
		contentPane.add(clearButton,constraints);
		// ATTENTION!!! The action here is just another
		//   example of how to update JTable. It is
		//   certainly not the logic for clearing the
		//   values in the GUI.
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logic.clearMethod();
			}
		});
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fromField.getText() != " "){
				try {
					logic.transferMoney(Integer.valueOf(fromField.getText()), Integer.valueOf(toField.getText()), Integer.valueOf(amountField.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
			
		});
		contentPane.add(transferButton,constraints);
		
	}
	
	
	
	

	public static void main(String[] args) {
		JFrame frame = new gui();
		frame.pack();
		frame.setVisible(true);
		
		
		//connecting to database
		try {
          
            logic.connect();
            logic.queryTable();
           
            logic.updateFullTable();
      
        }
        catch (SQLException e) {
            System.err.println(e);        
        }
	
		
		
	}
}