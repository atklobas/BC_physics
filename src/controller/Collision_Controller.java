package controller;

import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;

import model.Collision_Simulator;

import view.Collision_View;

public class Collision_Controller implements ActionListener, MouseListener, MouseMotionListener{
	private Timer timer;
	private Collision_View view;
	private Collision_Simulator model;
	private int width, height;
	private long lastTime=0;
	private TimerTask gameloop;
	private boolean isPaused=true;
	private Queue<AWTEvent> queue=new LinkedList<AWTEvent>();
	
	public Collision_Controller(int width, int height){
		this.width=width;
		this.height=height;
		timer=new Timer();
		
		view = new Collision_View(width,height, this);
		model = new Collision_Simulator(width,height,view);
		
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addActionListener(this);
		//model.addRandomCircles(50);
		model.addNewtonsCradle();
		gameloop=getLoopTask();
		startTimer();
		
	}
	private TimerTask getLoopTask(){
		return new TimerTask(){
			public void run() {procTimer();}
		};
	}
	
	public void gameLoop(){
		if(lastTime==0){
			lastTime=System.currentTimeMillis();
		}else{
			AWTEvent event=null;
			
			
			while((event=queue.poll())!=null){
				
				if(event instanceof MouseEvent){
					MouseEvent mouse=(MouseEvent)event;
					model.placeSphere(mouse.getX(), mouse.getY(),mouse.isShiftDown());
					System.out.println(new mathematics.Vector(mouse.getX(),mouse.getY()));
				}else if(event instanceof ActionEvent){
					ActionEvent action=(ActionEvent)event;
					if(action.getActionCommand()=="Reset"){
						model.reset();
					}else if(action.getActionCommand()=="Add Cradle"){
						model.addNewtonsCradle();
					}else if(action.getActionCommand()=="Add Random"){
						model.addRandomCircles(10);
					}else if(action.getActionCommand()=="Set COR"){
						gameloop.cancel();
						gameloop=getLoopTask();
						String s = (String)JOptionPane.showInputDialog("give coefficiant of restitution!");
						try{
							model.setCOR(Double.parseDouble(s));
						} catch(Exception e){
							System.err.println("invalid input");
						}
						timer.schedule(gameloop, 20, 20);
					}else if(action.getActionCommand()=="New"){
						model.reset();
						model.addNewtonsCradle();
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
						timer.schedule(gameloop, 20, 20);
						
					}else if(action.getActionCommand()=="Add Large Random"){
						model.addRandomCircles(200);
					}
					System.out.println(action.getActionCommand());
				}
				
				
			}
			
			
			
			long thisTime=System.currentTimeMillis();
			if(!isPaused){
				model.advance((/**/20/*/Math.min(thisTime-lastTime,100)/**/)/1000.0);
				
			}
			model.updateScreen();
			lastTime=thisTime;
		}
		
	}
	public void startTimer() {
		synchronized(this){
			model.advance(0.0);
			timer.scheduleAtFixedRate(gameloop, 500, 20);
			isPaused=false;
		}
		
	}
	
	
	public void procTimer(){
		gameLoop();
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

	@Override
	public void mousePressed(MouseEvent event) {
			queue.add(event);
			

		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		//System.out.println("mouse released");
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		System.out.println("mouse dragged ("+event.getX()+","+event.getY()+")");
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		//System.out.println("mouse moved");
		
	}


	
	

}
