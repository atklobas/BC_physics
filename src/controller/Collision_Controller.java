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
	public Collision_Controller(){
		width=800;
		height=600;
		Timer t=new Timer();
		view = new Collision_View(800,600);
		model = new Collision_Simulator(800,600,view);
		
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		
		
		t.scheduleAtFixedRate(new TimerTask(){
			public void run() {procTimer();}
		}, 20, 10);
	}
	
	
	public void procTimer(){
		if(lastTime==0){
			lastTime=System.currentTimeMillis();
			//model.advance(20/1000.0);
		}else{
			long thisTime=System.currentTimeMillis();
			model.advance((thisTime-lastTime)/1000.0*3);
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

}
