package controller;

import java.awt.AWTEvent;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import model.Collision_Simulator;
import view.Collision_View;

public class Collision_Controller implements ActionListener, MouseListener, MouseMotionListener{
	
	private Collision_View view;
	private Collision_Simulator model;
	private Tester tester;
	
	//A time set to proc every 20 millis
	private Timer timer;
	private int millisPerProc=20;
	private double timeDialation=1;
	//timers can be inconsistent, so keep track of time
	private long lastTime=0;
	private TimerTask gameloop;//points the timer at the gameloop
	private boolean isPaused=true;
	
	
	@SuppressWarnings("unused")
	private int width, height;
	
	private Queue<AWTEvent> queue=new LinkedList<AWTEvent>();
	
	/**
	 * creates a Controller which then creates a model and view
	 * 
	 * @param width width of screen
	 * @param height height of screen
	 */
	public Collision_Controller(int width, int height){
		this.width=width;
		this.height=height;
		
		//create a view which can update the controller(this)
		view = new Collision_View(width,height, this);
		//create a model which can update to the view
		model = new Collision_Simulator(width,height,view);
		
		tester=new Tester(model);
		
		//make events in the view update this controller
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addActionListener(this);
		
		
		timer=new Timer();
		tester.testCase();
		gameloop=getLoopTask();
		startTimer();
		
	}
	
	/**
	 * gets a time task pointing at the start of the game logic
	 * @return 
	 */
	private TimerTask getLoopTask(){
		return new TimerTask(){
			public void run() {procTimer();}
		};
	}
	/**
	 * entry point for the timer, should filter out the stack of repeated procs
	 * when there is a frame of lag.
	 */
	public void procTimer(){
		//TODO filter out procs that happen in significantly less than 20 millis
		gameLogic();
	}
	/**
	 * contains all the logic that periodically updates the model
	 */
	public void gameLogic(){
		if(lastTime==0){
			//use first one to get a baring for time, 
			lastTime=System.currentTimeMillis();
			//cant to logic because delta time is undefined(0)
		}else{
			//model modification methods are not thread safe, so do them in the gameloop
			interpretCommands();
			
			long thisTime=System.currentTimeMillis();
			if(!isPaused){
				model.advance(timeDialation*(/**/20/*/Math.min(thisTime-lastTime,100)/**/)/1000.0);
			}
			
			model.updateView();
			lastTime=thisTime;
		}
	}
	/**
	 * iterates through the list of commands and performs them. should be called
	 * in gameloop thread as this is not thread safe
	 */
	public void interpretCommands(){
		AWTEvent event=null;
		while((event=queue.poll())!=null){
			//TODO log time and operation
			if(event instanceof MouseEvent){
				MouseEvent mouse=(MouseEvent)event;
				tester.placeSphere(startx, starty,endx-startx,endy-starty,mouse.isShiftDown());
				System.out.println(new mathematics.Vector(mouse.getX(),mouse.getY()));
			}else if(event instanceof ActionEvent){
				ActionEvent action=(ActionEvent)event;
				if(action.getActionCommand()=="Reset"){
					model.reset();
				}else if(action.getActionCommand()=="Add Cradle"){
					tester.addNewtonsCradle();
				}else if(action.getActionCommand()=="Add Random"){
					tester.addRandomCircles(10);
				}else if(action.getActionCommand()=="Add CurrentTest"){
					tester.testCase();
				}else if(action.getActionCommand()=="Set MillisPerFrame"){
					gameloop.cancel();
					gameloop=getLoopTask();
					String s = (String)JOptionPane.showInputDialog("Give milliseconds per rendering cycle!");
					try{
						millisPerProc=(Integer.parseInt(s));
					} catch(Exception e){
						System.err.println("invalid input");
					}
					timer.schedule(gameloop, millisPerProc, millisPerProc);
				}else if(action.getActionCommand()=="Set COR"){
					gameloop.cancel();
					gameloop=getLoopTask();
					String s = (String)JOptionPane.showInputDialog("give coefficiant of restitution!");
					try{
						model.setCOR(Double.parseDouble(s));
					} catch(Exception e){
						System.err.println("invalid input");
					}
					timer.schedule(gameloop, millisPerProc, millisPerProc);
				}else if(action.getActionCommand()=="New"){
					model.reset();
					tester.addNewtonsCradle();
				}else if(action.getActionCommand()=="Quit"){
					System.exit(0);
				}else if(action.getActionCommand()=="Set Gravity"){
					gameloop.cancel();
					gameloop=getLoopTask();
					String s = (String)JOptionPane.showInputDialog("give gravity!!");
					try{
						model.setGravity(Double.parseDouble(s));
					} catch(Exception e){
						System.err.println("invalid input");
					}
					timer.schedule(gameloop, millisPerProc, millisPerProc);
				}else if(action.getActionCommand()=="Add Large Random"){
					tester.addRandomCircles(200);
				}
				System.out.println(action.getActionCommand());
			}
		}
	}
	/**
	 * starts the timer
	 */
	public void startTimer() {
		synchronized(this){
			//model.advance(0.0);
			timer.scheduleAtFixedRate(gameloop, 500, millisPerProc);
			isPaused=false;
		}
		
	}
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent action) {
		if(action.getActionCommand()=="Pause"){
			if(isPaused){
				isPaused=false;
			}else{
				isPaused=true;
			}
		}else{
			queue.add(action);
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		//System.out.println("mouse clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		//System.out.println("mouse entered");
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		//System.out.println("mouse exited");
		
	}

	private int startx,starty,endx, endy;
	@Override
	public void mousePressed(MouseEvent event) {
		startx=event.getX();starty=event.getY();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		endx=event.getX();endy=event.getY();
		queue.add(event);
		//System.out.println("mouse released");
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		//System.out.println("mouse dragged ("+event.getX()+","+event.getY()+")");
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		//System.out.println("mouse moved");
		
	}


	
	

}
