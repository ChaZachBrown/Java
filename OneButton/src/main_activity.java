/*Zach Brown October 2, 2016
 * 
 * This program is a demonstration of how to activate a button when it is set
 * to disable whenever the cursor is hovering over it. I have set the requirements to 
 * disable the button when the cursor is hovering over it and then enable it when the 
 * cursor moves off of it. The only way to activate the button is to add an actionListener
 * to it and then make the button as the default listener button by having it be the only button
 * in the program. I can then press the space key and it will activate the button printing out 
 * "One Button was pressed".
 * 
 */



import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class main_activity extends JFrame implements MouseListener, ActionListener{

	
	public static void main(String[] args){
		main_activity frame = new main_activity();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	//adding UI components
	Container mainPane = getContentPane();
	JButton oneButton = new JButton("One Button");
	JPanel panel = new JPanel();
	
	//Constructor******************************************************************
	public main_activity(){
		mainPane.add(panel, BorderLayout.CENTER);
		panel.add(oneButton, BorderLayout.CENTER);
		oneButton.addMouseListener(this);
		oneButton.addActionListener(this);

		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            System.exit(0);
            }
		});
	}
	//End Constructor******************************************************************

	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == oneButton){
			
			oneButton.setEnabled(false);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == oneButton){
			oneButton.setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == oneButton){//can only be activated by the space key when the cursor is not hovering over the button
			System.out.println("One Button was pressed");
		}
	}

	
	
}
