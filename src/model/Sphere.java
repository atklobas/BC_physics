package model;

import java.awt.*;
import java.awt.image.BufferedImage;

import view.Renderable;
import mathematics.Vector;

public class Sphere extends Movable implements Renderable{
	private double radius;
	private Image image;
	int dimention;
	public Sphere(double x, double y, Vector trajectory,double mass){
		super(x,y,trajectory,mass);
		this.radius=Math.sqrt(mass);
		dimention=(int)(radius*2);
		image = new BufferedImage(dimention,dimention,BufferedImage.TYPE_INT_ARGB);
		Graphics g= image.getGraphics();
		g.setColor(Color.RED);
		g.fillOval(0, 0, dimention, dimention);
	}
	public Image getImage() {
		return image;
	}
	@Override
	public int getBoundingHeight() {
		// TODO Auto-generated method stub
		return dimention;
	}
	@Override
	public int getBoundingWidth() {
		// TODO Auto-generated method stub
		return dimention;
	}
	public double getRadius(){
		return this.radius;
	}
	

}
