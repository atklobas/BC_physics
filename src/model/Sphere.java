package model;

import java.awt.*;
import java.awt.image.BufferedImage;

import view.Renderable;
import mathematics.Matrix;
import mathematics.Vector;

public class Sphere extends Movable implements Renderable{
	private double radius;
	private BufferedImage image;
	private int width,height;
	public static int number=0;
	private int thisnum;
	int dimention;
	
	public Sphere(double x, double y, Vector trajectory,double mass){
		
		super(x,y,trajectory,mass);
		thisnum=number++;
		this.radius=Math.sqrt(mass);
		dimention=(int)(radius*2);
		width=dimention;
		height=dimention;
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		
	}
	public Image getImage() {
		Graphics2D g= image.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		g.setColor(new Color(255,255,255,0));
		g.fillRect(0, 0, width, height);
		if(thisnum==99999){
		g.setColor(Color.RED);
		}else{
			g.setColor(Color.WHITE);
		}
		
		g.fillOval(0, 0, dimention, dimention);
		g.setColor(Color.CYAN);
		g.drawString(""+((int)(this.getTrajectory().getLength())), 00,(int)radius+5);
		
		return image;
	}
	@Override
	public int getBoundingHeight() {
		return dimention;
	}
	@Override
	public int getBoundingWidth() {
		// TODO Auto-generated method stub
		return dimention;
	}
	public double getRadius(){
		return radius;
	}
	public void setMass(double m){
		if(m>10000)return;
		super.setMass(m);
		radius=Math.sqrt(m);
		dimention=(int)radius*2;
		
		if(image.getWidth()<dimention){
			width=dimention*2;
			height=dimention*2;
			image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		}
		
	}
	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return (int)this.getX();
	}
	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return (int)this.getY();
	}
	@Override
	public void bounce(Movable m) {
		if(m instanceof Sphere){
			
			
			
			Sphere sphere=(Sphere)m;
			//Vector normal =this.getPos().add(new Vector(radius,radius)).subtract(sphere.getPos().add(new Vector(sphere.radius,sphere.radius)));
			Vector normal =this.getPos().add(new Vector(radius,radius)).subtract(sphere.getPos().add(new Vector(sphere.radius,sphere.radius)));
			
			double depth=normal.getLength();
			double neededLength=this.radius+sphere.radius;
			
			//a b c make a triangle, when c==neededLength, the balls have just collided
			double c=sphere.getTrajectory().distance(this.getTrajectory());
			double a=sphere.getTrajectory().getLength();
			double b=this.getTrajectory().getLength();
			//
			double time=(neededLength-depth)/c;
			
			sphere.advance(-time);
			this.advance(-time);
			normal=this.getPos().add(new Vector(radius,radius)).subtract(sphere.getPos().add(new Vector(sphere.radius,sphere.radius)));
			
			
			//double time=neededLength/(depth*v1);
			//System.out.println(neededLength/(depth)+","+(neededLength/(depth)));
			
			/*System.out.println("needed "+neededLength+", depth "+depth);
			System.out.println();
			System.out.println(sphere.getTrajectory().scale(neededLength/depth));
			System.out.println(sphere.getPos());
			System.out.println(sphere.getPos().subtract(sphere.getTrajectory().getUnitVector().scale(neededLength/depth)));
			System.out.println();
			System.out.println(this.getTrajectory().scale(neededLength/depth));
			System.out.println(this.getPos());
			System.out.println(this.getPos().subtract(this.getTrajectory().getUnitVector().scale(neededLength/depth)));
			
			/**/
			
			/*if(v1!=0){
				sphere.setPos(sphere.getPos().add(sphere.getTrajectory().scale(neededLength/(depth*v1))));
			}
				
			if(v2!=0){
				this.setPos(this.getPos().add(this.getTrajectory().scale(neededLength/depth*v2)));
			}*/

			/**/
			
			if(normal.getLength()==0){
				return;
			}
			Matrix toBase= Matrix.createOrthonormal(normal);
			Matrix fromBase=toBase.invert();
			setTrajectory(toBase.apply(getTrajectory()));
			m.setTrajectory(toBase.apply(m.getTrajectory()));
			this.bounceX(m);
			this.setTrajectory(fromBase.apply(getTrajectory()));
			m.setTrajectory(fromBase.apply(m.getTrajectory()));
			
			sphere.advance(time);
			this.advance(time);
			
			
		}else if(m instanceof Box){
			Box box= (Box)m;
			double cx=this.getX()+radius;
			double cy=this.getY()+radius;
			double bx=box.getX();
			double by=box.getY();
			double width=0;
			double height=0;
			
			if(cx<bx){
				if(cy<by){//top left
					
				}else if(cy>by+height){//bottom left
					
				}else{///left mid;
					
				}
			}else if(cx>bx+width){
				if(cy<by){//top right
					
				}else if(cy>by+height){//bottom right
					
				}else{//right mid
					
				}
			}else{
				if(cy<by){//top
					
				}else if(cy>by+height){//bottom
					
				}else{//contained
					
				}
			}
			
			
			
		}

		
	}
	

}
