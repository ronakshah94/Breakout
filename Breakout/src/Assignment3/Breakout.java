package Assignment3;

/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram implements MouseListener, MouseMotionListener {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 6;

/** Width of a brick */
	private static final int BRICK_WIDTH =100;

/** Height of a brick */
	private static final int BRICK_HEIGHT =15;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/** Array of Colors */
 private static Color colors[]={Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN, Color.CYAN};
/** Runs the Breakout program. */
	
	public void run() {
	setup();
	while (Nturns>0 && Nbricks>0) { 
		 moveBall(); 
		 checkForCollision(); 
		 pause(15); 
	}
	}
	/*sets up the various components of the game */
	public void setup(){
	setBricks();
	createPaddle();
	this.addMouseListeners();
	createBall();
	
	}

	public void createPaddle(){
		paddle=new GRect(10,600,BRICK_WIDTH+20,BRICK_HEIGHT);
		this.add(paddle);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		paddle.setVisible(true);
	}
	/* Sets up the bricks of the game piece by piece */
	public void setBricks(){
		int x=150,y=10,p=0;
		for(int i=1;i<=NBRICK_ROWS;i++)
		{
			for(int j=1;j<=NBRICKS_PER_ROW;j++)
			{
				rect=new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT);
				rect.setFilled(true);
				rect.setColor(colors[p]);
				rect.setVisible(true);
				this.add(rect);
				x+=BRICK_WIDTH+BRICK_SEP;
			}
			x=150;
			y+=BRICK_HEIGHT+BRICK_SEP;
			if(i%2==0)
				p++;
		}
	}
	/* moving the paddle with the mouse */
	public void mouseMoved(MouseEvent e)
	{
		paddle.setLocation(e.getX()-(BRICK_WIDTH/2), 638);//moving the center of the paddle with the mouse
		if(e.getX()+(BRICK_WIDTH/2)>getWidth())//ensuring the paddle does not go off the screen
			paddle.setLocation(this.getWidth()-BRICK_WIDTH,638);
		if(e.getX()-(BRICK_WIDTH/2)<0)
			paddle.setLocation(0,638);
	}
	public void createBall()
	{
		
		turns=new GLabel("Turns left: "+Nturns,20,40);
		turns.setFont(new Font("Serif", Font.BOLD, 18));
		score.setFont(new Font("Serif", Font.BOLD, 18));
		this.add(score);
		this.add(turns);
		ball=new GOval(664,245,15,15);
		this.add(ball);
		ball.setFilled(true);
		ball.setColor(Color.BLUE);
		ball.setVisible(true);
		vx = rgen.nextDouble(1.0, 3.0); 
		if (rgen.nextBoolean(0.5)) vx = -vx; 
	}
	private void moveBall() {
		
		if(ball.getX()<0)
			vx=-vx;
		if(ball.getX()>getWidth()-ball.getWidth())
			vx=-vx;
		if(ball.getY()>650)
		{
			Nturns--;
			remove(ball);
			remove(score);
			remove(turns);
			pause(2000);
			createBall();
		}
		if(ball.getY()<0)
			vy=-vy;
		
		 ball.move(vx,vy); 
		 } 
	
		 private void checkForCollision() { 
			 double positions[][]={{ball.getX(),ball.getY()},{ball.getX()+15,ball.getY()},
					 {ball.getX(),ball.getY()+15},{ball.getX()+15,ball.getY()+15}};
			 
		 for(int i=0;i<4;i++){
			 GObject specimen=getCollidingObject(positions[i][0],positions[i][1]);
			if(specimen!=null && specimen.equals(paddle))
			{
				vy=-vy;
				return;
			}
			else
			if(specimen!=null && !specimen.equals(score) && !specimen.equals(turns))
			{
				remove(specimen);
				vy=-vy;
				Nbricks--;
				sc+=100;
				remove(score);
				score=new GLabel("Score: "+sc,20,20);
				score.setFont(new Font("Serif", Font.BOLD, 18));
				this.add(score);
				return;
			}
		 }
		 
		 }

		
		 
		 
		 public GObject getCollidingObject(double x, double y)
		 {
			 return this.getElementAt(x,y);
		 }
	private GRect rect;
	private GRect paddle;
	private double vx,vy=3.0;
	private RandomGenerator rgen=RandomGenerator.getInstance();
	private GOval ball;
	private GLabel score=new GLabel("Score: ",20,20),turns;
	private int Nturns=NTURNS, Nbricks= NBRICK_ROWS *NBRICKS_PER_ROW,sc=0;
}
