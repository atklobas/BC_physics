package controller;

import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import model.Collision_Simulator;

import view.Collision_View;

public class Collision_Controller implements ActionListener, MouseListener, MouseMotionListener{
	Timer t;
	Collision_View view;
	Collision_Simulator model;
	int width, height;
	long lastTime=0;
	public TimerTask gameloop;
	private long timerCounter=0;
	public Collision_Controller(){
		width=1000;
		height=700;
		t=new Timer();
		view = new Collision_View(width,height, this);
		model = new Collision_Simulator(width,height,view);
		
		
		
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		gameloop=new TimerTask(){
			public void run() {procTimer();}
		};
		
		
		
	}
	
	
	public void procTimer(){
		timerCounter++;
		if(timerCounter%(2*3)==0){
			for(int i=0;i<1;i++){
			//model.addShpere();
			}
			
			//System.out.println(timerCounter*10+"balls");
		}
		
		if(lastTime==0){
			lastTime=System.currentTimeMillis();
			//model.advance(20/1000.0);
		}else{
			long thisTime=System.currentTimeMillis();
			model.advance((/**/20/*/Math.min(thisTime-lastTime,100)/**/)/1000.0);
			lastTime=thisTime;
		}
		//model.advance();
		
	}
	

	@Override
	public void actionPerformed(ActionEvent action) {
		System.out.println("action performed");
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		System.out.println("mouse clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		System.out.println("mouse entered");
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		System.out.println("mouse exited");
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		System.out.println("mouse pressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		System.out.println("mouse released");
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		System.out.println("mouse dragged ("+event.getX()+","+event.getY()+")");
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		//System.out.println("mouse moved");
		
	}


	public void startTimer() {
		model.advance(0.0);
		t.scheduleAtFixedRate(gameloop, 500, 20);
		
	}
	

}
