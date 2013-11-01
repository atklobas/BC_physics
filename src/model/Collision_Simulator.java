package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mathematics.Vector;

import view.Collision_View;
import controller.Collision_Controller;

public class Collision_Simulator {
	int pixelsPerMeter=10;
	private Vector gravity=new Vector(0,0);
	Collision_View view;
	int width,height;
	ArrayList<view.Renderable> rendered;
	ArrayList<Movable> movable;
	Random rand=new Random();
	double COR=1;
	
	public Collision_Simulator(int width, int height, Collision_View view) {
		this.width=width;
		this.height=height;
		this.view=view;
		this.reset();
	}
	public void setGravity(double n){
		gravity=new Vector(0,pixelsPerMeter*n);
	}
	public void placeSphere(int x, int y,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(0,0),100);
		s.setImmovable(isImmovable);
		s.setCOR(COR);
		rendered.add(s);
		movable.add(s);
	}
	
	
	public void addShpere(){
		this.addRandomCircles(1);
	}
	
	public void reset(){
		rendered = new ArrayList<view.Renderable>();
		movable = new ArrayList<Movable>();
		setCOR(1);
		setGravity(0);
		rendered.add(new view.Renderable(){
			Image im;
			public Image getImage() {
				if(im==null){
					im=new BufferedImage(1200,900,BufferedImage.TYPE_INT_ARGB);
					Graphics g=im.getGraphics();
					g.setColor(Color.darkGray);
					g.fillRect(0, 0, 1200, 900);
				}
				return im;
			}
			public int getImageX() {return 0;}
			public int getImageY() {return 0;}
		});
	}
	public void addNewtonsCradle(){
		Sphere s=new Sphere(50,100,new Vector(300,0),900);
		s.setCOR(COR);
		rendered.add(s);
		movable.add(s);
		
		
		for(int i=0;i<4;i++){
			s=new Sphere(200+120*i,100,new Vector(0,0),900);
			s.setCOR(COR);
			rendered.add(s);
			movable.add(s);
		}
	}
	
	public void addRandomCircles(int n){
		for(int i=0;i<n;i++){
			Sphere s=new Sphere(0,0,new Vector(rand.nextInt(200)-100,rand.nextInt(200)-100),rand.nextInt(200)+50 );
			int width=s.getBoundingWidth();
			int height=s.getBoundingHeight();
			s.setPos(new Vector(rand.nextInt(this.width-width),rand.nextInt(this.height-height)));
			s.setCOR(COR);
			rendered.add(s);
			movable.add(s);
		}
	}
	
	private void wallCollision(Movable m){
		double x=m.getX(),y=m.getY(),width=m.getBoundingWidth(),height=m.getBoundingHeight();;
		double time;
		if(x<0){
			time=x/m.getVelX(); 
			m.reflect(new Vector(-1,0),time);
		}else if(x+width>this.width){
			time=(x+width-this.width)/m.getVelX();
			m.reflect(new Vector(1,0),time);
		}
		if(y<0){
			time= y/m.getVelY();
			m.reflect(new Vector(0,-1),time);
			
		}else if(y+height>this.height){
			time=(y+height-this.height)/m.getVelY();
			m.reflect(new Vector(0,1),time);
		}
	}
	
	public void wallCollision(){
		for(Movable m : movable){
			wallCollision(m);
		}

	}

	
	
	public void advance(){
		advance(20.0/1000);
		
	}
	public void advance(double seconds){
		/**/
		CollisionList c=new CollisionList(width,height,10);
		
		for(Movable m:movable){
			
			m.advance(seconds);
			m.setTrajectory(m.getTrajectory().add(gravity.scale(seconds)));
			c.add(m);
		}
		c.checkCollisions();
		wallCollision();
		
		/*///depricated
		int length=movable.size();
		for(int i=0;i<length;i++){
			movable.get(i).advance(seconds);
			for(int j=i+1;j<length;j++){
				if(checkCollision(movable.get(i),movable.get(j))){
					movable.get(i).bounce(movable.get(j));
				}
			}
		}/**/
		
		
	}
	public void updateScreen() {
		if(view!=null)
			view.updateScreen(rendered);
		
	}
	public void setCOR(double parseDouble) {
		this.COR=parseDouble;
		for(Movable m: movable){
			m.setCOR(parseDouble);
		}
		
	}
	
	
	
}
