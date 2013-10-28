package model;

import mathematics.Vector;


public abstract class Collidable {
	
	public abstract int getBoundingWidth(); 
	public abstract int getBoundingHeight();
	public abstract double getX();
	public abstract double getY();
	public String toString(){
		return "("+((int)getX())+","+((int)getY())+")";
	}

}
