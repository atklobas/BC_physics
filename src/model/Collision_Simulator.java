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
	int pixelsPerMeter=00;
	private Vector gravity=new Vector(0,pixelsPerMeter*9.8);
	Collision_View view;
	int width,height;
	ArrayList<view.Renderable> rendered = new ArrayList<view.Renderable>();
	ArrayList<Movable> movable = new ArrayList<Movable>();
	Random rand=new Random();
	
	public Collision_Simulator(int width, int height, Collision_View view) {
		this.width=width;
		this.height=height;
		this.view=view;
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
		
		Sphere s=new Sphere(100,0,new Vector(0,0),900);
		rendered.add(s);
		movable.add(s);
		for(int i=0;i<2500;i++){
			s=new Sphere(rand.nextInt(100),rand.nextInt(height),new Vector(rand.nextInt(510),rand.nextInt(110)-55),2 );
			rendered.add(s);
			movable.add(s);
		}
		/**/
		/*/
		for(int i=0;i<100;i++){
			Sphere s=new Sphere(rand.nextInt(width-100)+100,rand.nextInt(height),new Vector(rand.nextInt(40)-20,rand.nextInt(40)-20),rand.nextInt(100)+50 );
			rendered.add(s);
			movable.add(s);
		}
		
		
		/**/
		
		
		/*s=new Sphere(150,150,new Vector(-55,-50),100);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(0,0,new Vector(55,50),100);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(0,150,new Vector(55,-50),100);
		rendered.add(s);
		movable.add(s);
		
		
		
		/*Sphere s=new Sphere(50,100,new Vector(300,5),900);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(200,100,new Vector(0,0),900);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(270,100,new Vector(0,0),400);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(340,100,new Vector(0,0),900);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(410,100,new Vector(0,0),900);
		rendered.add(s);
		movable.add(s);
		/*for(int testRange=0;testRange<200;testRange++){
			System.out.println();
			for(int i=0;i<3;i++){
				ArrayList<Sphere> list=new ArrayList<Sphere>();
				
				for(int j=0;j<200*testRange;j++){
					Sphere s=new Sphere(rand.nextInt(),rand.nextInt(),null,1);
					list.add(s);
				}
				
				
				
			long time1=System.currentTimeMillis();
			
			CollisionList c=new CollisionList(10);
			for(Collidable cc:movable){
				c.add(cc);
			}
			long time2=System.currentTimeMillis();
			System.out.print((time2-time1)+",");
			}
		}*/
		/*
		CollisionList c2=new CollisionList(10);
		for(Collidable cc:movable){
			c2.add(cc);
		}
		
		long time3=System.currentTimeMillis();
		CollisionList c3=new CollisionList(10);
		for(Collidable cc:movable){
			c3.add(cc);
		}
		
		long time4=System.currentTimeMillis();
		CollisionList c4=new CollisionList(10);
		for(Collidable cc:movable){
			c2.add(cc);
		}
		
		long time5=System.currentTimeMillis();
		
		System.out.println("time elapsed"+(time2-time1));
		System.out.println("time elapsed"+(time3-time2));
		System.out.println("time elapsed"+(time4-time3));
		System.out.println("time elapsed"+(time5-time4));
		//System.out.println(c);/
		/**/
		//System.exit(0);
		/*s=new Sphere(400,200,new Vector(00,00),10000);
		rendered.add(s);
		movable.add(s);/**/
		for(Movable m:movable){
			m.setCOR(1);
		}
		
		
	}
	public void addShpere(){
		//Sphere s=new Sphere(rand.nextInt(1)+20,rand.nextInt(1)+220,new Vector(rand.nextInt(1)+800,rand.nextInt(1)-200/**/),rand.nextInt(1)+150);
		Sphere s=new Sphere(rand.nextInt(1)+20,rand.nextInt(1)+220,new Vector(rand.nextInt(400)-800,rand.nextInt(300)-600/**/),rand.nextInt(1)+150);
		
		rendered.add(s);
		movable.add(s);
		
	}
	public void advance(){
		advance(20.0/1000);
		
	}
	
	public void wallCollision(double seconds){
		for(Movable m : movable){
			double x=m.getX(),y=m.getY(),width=m.getBoundingWidth(),height=m.getBoundingHeight();;
			if(x<0){
				m.reflect(new Vector(-1,0));
				
			}else if(x+width>this.width){
				m.reflect(new Vector(1,0));
			}
			if(y<0){
				m.reflect(new Vector(0,-1));
			}else if(y+height>this.height){
				m.reflect(new Vector(0,1));
			}
		}

	}

	private boolean checkCollision(Collidable m1, Collidable m2){
		if(m1 instanceof Sphere){
			Sphere s1 =(Sphere)m1;
			if(m2 instanceof Sphere){
				Sphere s2 =(Sphere)m2;
				
				double s1rad=s1.getRadius();
				double s2rad=s2.getRadius();
				
				if (s1.getPos().add(new Vector(s1rad,s1rad)).distance(s2.getPos().add(new Vector(s2rad,s2rad)))<s1rad+s2rad){
					return true;
				}
			}
		}
		return false;
	}
	
	public void advance(double seconds){
		wallCollision(seconds);
		
		
		/**/
		CollisionList c=new CollisionList(width,height,10);
		for(Movable m:movable){
			
			m.advance(seconds);
			m.setTrajectory(m.getTrajectory().add(gravity.scale(seconds)));
			c.add(m);
		}
		c.checkCollisions();
		
		/*///
		int length=movable.size();
		for(int i=0;i<length;i++){
			movable.get(i).advance(seconds);
			for(int j=i+1;j<length;j++){
				
				if(checkCollision(movable.get(i),movable.get(j))){
					movable.get(i).bounce(movable.get(j));
				}
			}
		}/**/
		if(view!=null)
		view.updateScreen(rendered);
	}
	
	
	
}
