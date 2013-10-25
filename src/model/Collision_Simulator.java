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
	Collision_View view;
	int width,height;
	ArrayList<view.Renderable> rendered = new ArrayList<view.Renderable>();
	ArrayList<Movable> movable = new ArrayList<Movable>();
	
	
	public Collision_Simulator(int width, int height, Collision_View view) {
		this.width=width;
		this.height=height;
		this.view=view;
		rendered.add(new view.Renderable(){
			Image im;
			public Image getImage() {
				if(im==null){
					im=new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB);
					Graphics g=im.getGraphics();
					g.setColor(Color.darkGray);
					g.fillRect(0, 0, 800, 600);
				}
				return im;
			}
			public int getX() {return 0;}
			public int getY() {return 0;}
		});
		Random rand=new Random();
		for(int i=0;i<10;i++){
			Sphere s=new Sphere(rand.nextInt(600)+50,rand.nextInt(400)+50,new Vector(rand.nextInt(400)-200,rand.nextInt(400)-200),rand.nextInt(50)+50);
			rendered.add(s);
			movable.add(s);
		}/**/
		
		Sphere s=new Sphere(100,100,new Vector(100,0),100);
		rendered.add(s);
		movable.add(s);
		s=new Sphere(600,100,new Vector(00,0),100);
		rendered.add(s);
		movable.add(s);/**/
		
		
	}
	public void advance(){
		advance(20.0/1000);
		
	}
	
	
	public void wallCollision(double seconds){
		for(Movable m : movable){
			m.advance(seconds);
			int x=m.getX(),y=m.getY(),width=m.getBoundingWidth(),height=m.getBoundingHeight();;
			if(x<0){
				m.advance(-.5*seconds);
				m.reflect(new Vector(-1,0));
				
			}else if(x+width>this.width){
				m.advance(-.5*seconds);
				m.reflect(new Vector(-1,0));
			}
			if(y<0){
				m.advance(-.5*seconds);
				m.reflect(new Vector(0,-1));
			}else if(y+height>this.height){
				m.advance(-.5*seconds);
				m.reflect(new Vector(0,-1));
			}
		}
	}
	public boolean checkCollision(Movable m1, Movable m2){
		if(m1 instanceof Sphere){
			Sphere s1 =(Sphere)m1;
			if(m2 instanceof Sphere){
				Sphere s2 =(Sphere)m2;
				if (s1.getPos().distance(s2.getPos())<s1.getRadius()+s2.getRadius()){
					//return s1.getPos().subtract(s2.getPos());
					return true;
				}
			}
		}
		return false;
	}
	
	public void advance(double seconds){
		wallCollision(seconds);
		int length=movable.size();
		for(int i=0;i<length;i++){
			for(int j=i+1;j<length;j++){
				//Vector check=checkCollision(movable.get(i),movable.get(j));
				
				if(checkCollision(movable.get(i),movable.get(j))){
					movable.get(i).bounce(movable.get(j));
				}
				
			}
		}
		//System.out.println(new Vector(20,20).getUnitVector());
		//System.exit(0);
		view.updateScreen(rendered);
	}
	
	
	
}
