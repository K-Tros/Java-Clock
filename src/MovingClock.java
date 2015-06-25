/**
 * Class for creating a clock from the time on the machine
 * @author Kevin Trosinski
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class MovingClock {

	public static int hour;
	public static int minute;
	public static int second;
	public static void main(String[] args) {
	
		
		//Creates GregorianCalendar object
		
		Calendar calendar = new GregorianCalendar();
		
		//Gets time from Calendar object
		
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
		
		//Draws Frame
		
		DrawFrame frame = new DrawFrame(hour, minute, second);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		//Creates a timer
		ActionListener listener = new HandMover();
		
		Timer t = new Timer(1000, listener);
		t.start();
	}

}

/**
 * A frame that will contain the clock face
 */
@SuppressWarnings("serial")
class MovingDrawFrame extends JFrame{
	
	public MovingDrawFrame(int h, int m, int s){
		
		
		setTitle("Display Clock");
		setSize(DEFAULT_WIDTH+9,DEFAULT_HEIGHT+30);
		
		//Add panel to frame
		
	
	MovingDrawComponent component = new MovingDrawComponent();
		add(component);
		
	}
	
	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;
	
}

/**
 * A component that displays ellipses and lines
 */ 
@SuppressWarnings("serial")
class MovingDrawComponent extends JPanel{
	
	/**
	 * 
	 */
	int hour = MovingClock.hour;
	int minute = MovingClock.minute;
	int second = MovingClock.second;

	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		//Draws a circle
		
		Color c = new Color(123,200,123);
		Ellipse2D circle = new Ellipse2D.Double();
		circle.setFrame(MovingDrawFrame.DEFAULT_WIDTH*(1/2),MovingDrawFrame.DEFAULT_HEIGHT*(1/2),MovingDrawFrame.DEFAULT_WIDTH,MovingDrawFrame.DEFAULT_HEIGHT);
		g2.draw(circle);
		g2.setPaint(c);
		g2.fill(circle);
		
		//Draws second circle in the middle
		
		Ellipse2D circle2 = new Ellipse2D.Double();
		circle2.setFrame(circle.getCenterX()-MovingDrawFrame.DEFAULT_WIDTH/30 , circle.getCenterY()-MovingDrawFrame.DEFAULT_HEIGHT/30, MovingDrawFrame.DEFAULT_WIDTH/15, MovingDrawFrame.DEFAULT_HEIGHT/15);
		g2.setPaint(Color.BLACK);
		g2.fill(circle2);
		
		//Draws second hand
		
		g2.setPaint(Color.BLACK);
		double thetasec = (90 - (second*6.0))*(Math.PI/180);
		g2.draw(new Line2D.Double(circle.getCenterX(),circle.getCenterY(),200+(150*Math.cos(thetasec)),200-(150*Math.sin(thetasec))));
		
		//Draws minute hand
		
		g2.setStroke(new BasicStroke(5));
		double thetamin = (90 - (minute*6.0))*(Math.PI/180);
		g2.draw(new Line2D.Double(circle.getCenterX(),circle.getCenterY(),200+(150*Math.cos(thetamin)),200-(150*Math.sin(thetamin))));
		
		//Draws hour hand
		
		double thetahour = (90-(hour+(minute/60))*30)*(Math.PI/180);
		g2.draw(new Line2D.Double(circle.getCenterX(),circle.getCenterY(),200+(75*Math.cos(thetahour)),200-(75*Math.sin(thetahour))));

		//Draws the numbers on the clock face
		
		for(int i = 1 ; i <= 12 ; i++){
			Font f = new Font("Times New Roman", Font.BOLD, 30);
			g2.setFont(f);
			g2.drawString(Integer.toString(i), (int) (circle.getCenterX()-10+(180*Math.cos((90-i*30)*(Math.PI/180)))), (int) (circle.getCenterY()+5-(180*Math.sin((90-i*30)*(Math.PI/180)))));
		
		}
		
		//Adds tick marks on the hour
		
		g2.setStroke(new BasicStroke(2));
		for(int i = 1 ; i <= 12 ; i++){
			g2.draw(new Line2D.Double(circle.getCenterX()-190*Math.cos((90-i*30)*(Math.PI/180)), circle.getCenterY()+190*Math.sin((90-i*30)*(Math.PI/180)),circle.getCenterX()-200*Math.cos((90-i*30)*(Math.PI/180)),circle.getCenterY()+200*Math.sin((90-i*30)*(Math.PI/180))));
		}
		
		//Adds tick marks on the minute
		
		g2.setStroke(new BasicStroke(1));
		for(int i = 1 ; i <= 60 ; i++){
			g2.draw(new Line2D.Double(circle.getCenterX()-195*Math.cos((90-i*6)*(Math.PI/180)),circle.getCenterY()+195*Math.sin((90-i*6)*(Math.PI/180)),circle.getCenterX()-200*Math.cos((90-i*6)*(Math.PI/180)),circle.getCenterY()+200*Math.sin((90-i*6)*(Math.PI/180))));
		}
	}
}

class HandMover implements ActionListener{
	public void actionPerformed(ActionEvent event){
		MovingClock.second++;
	}
}