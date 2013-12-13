package model;

import mathematics.Vector;


public abstract class Collidable {
	
	public abstract int getBoundingWidth(); 
	public abstract int getBoundingHeight();
	public abstract double getX();
	public abstract double getY();
	public abstract boolean canCollideWith(Collidable C);
	public abstract boolean collide(Collidable C);
	public abstract boolean collide(Collidable C, boolean ignorePosition);
	public abstract int getCollisionPrecedence();
	public abstract boolean stillExists();
	public abstract void advance(double seconds);
	
	public String toString(){
		return "("+((int)getX())+","+((int)getY())+")";
	}

}
