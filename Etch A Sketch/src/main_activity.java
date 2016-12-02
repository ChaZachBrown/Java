/*Zach Brown October 2, 2016
 * 
 * This is a simple EtchASkethc program meant to introduce me to mouse events and 2D graphics
 * it takes mouse input and draws lines then uses PaintComponent to redraw the lines when
 * the size of the program is changed. It also has a clear button that clears the screen
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class main_activity extends JFrame implements ActionListener{


	public static void main(String [] args){
		JFrame frame = new main_activity("Etch A Sketch");
		frame.setSize(600,600);
		frame.setVisible(true);
	}
	
	//declaring GUI items 
	Container mainPane = getContentPane();
	JButton clearButton = new JButton("Clear");
	newJPanel drawingPanel = new newJPanel();
	
	
	//Constructor******************************************************************************************************************************************
	public main_activity(String title){
		super(title);//giving out app a title
		mainPane.add(clearButton, BorderLayout.SOUTH);
		clearButton.addActionListener(this);
		mainPane.add(drawingPanel, BorderLayout.CENTER);
		drawingPanel.addMouseListener(drawingPanel);
		drawingPanel.addMouseMotionListener(drawingPanel);
		drawingPanel.setForeground(Color.WHITE);
		
		//closing using the X button
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            System.exit(0);
            }
		});
	}
	//end Constructor****************************************************************************************************************************************
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == clearButton) {
			drawingPanel.removeAll();
			drawingPanel.repaint();
			drawingPanel.listOfPointLists.clear();
			drawingPanel.indexInPoints = 1;
		}
	}
}


//making a new inner class *******************************************************************************************************************************
class newJPanel extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	/* This class extends JPanel and MouseListener so that that 
	 * paintComponent method in JPanel can be overwritten to work the way it needs to be
	 */
	
	// declaring variable and such
	ArrayList<ArrayList<Point>> listOfPointLists = new ArrayList<ArrayList<Point>>();//creating an arraylist to put arraylists of object points in it 
	int indexInLists = 0;//index for listOfPointLists
	int indexInPoints = 1;//index for arrayslists inside the arraylist listOfPointLists
	ArrayList<Point> points = new ArrayList<Point>();//create a new list of point objects 


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.CYAN);
		if (listOfPointLists.size() != 0 ){
			System.out.println("if");
			for (int i = 1; i <= listOfPointLists.size() ; i++) {
				System.out.println("for");
				points = listOfPointLists.get(i);
			}
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//code to add new points to the new arraylist
		points.add(new Point(e.getX(), e.getY()));
		Graphics g = getGraphics();//drawing the lines that the mouse is creating
		g.setColor(Color.CYAN);
		g.drawLine((int) points.get(indexInPoints - 1).getX(), (int) points.get(indexInPoints - 1).getY(),
				(int) points.get(indexInPoints).getX(), (int) points.get(indexInPoints).getY());
		indexInPoints++;
		
		System.out.println(points.get(indexInPoints -1).getX() + "  "  + points.get(indexInPoints -1).getY());//debuggin
		
		
		
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		points.add(new Point(e.getX(), e.getY()));//add the  coordinate points to this new list of points 

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		listOfPointLists.add(indexInLists, points);//adding the points arraylist to the listOfPointsLists arraylist to store all the lines that have been drawn
		points.clear();
		indexInPoints = 1;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
//End newJPanel class **********************************************************************************************************************************************
