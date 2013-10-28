package model;

import java.awt.Image;

import mathematics.Vector;
import view.Renderable;

public class Box extends Movable implements Renderable{
	private int width,height;
	public Box(double x, double y, Vector trajectory, double mass, int width,int height) {
		super(x, y, trajectory, mass);
		this.width=width;
		this.height=height;
	}

	@Override
	public void bounce(Movable m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBoundingHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBoundingWidth() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
