package model;

import mathematics.Vector;
import mathematics.Matrix;

public abstract class Movable extends Collidable{
	private double mass, elacticity, charge;
	private Vector pos,lastpos,trajectory;
	private Movable LastCollidedWith=null;
	//private int lastCollided=2;
	
	public Movable(double x, double y, Vector trajectory,double mass){
		this.pos=new Vector(x,y);
		this.lastpos=pos;
		this.trajectory=trajectory;
		this.mass=mass;
		this.charge=0;
	}
	

	public void advance(double seconds){
		lastpos=pos;
		this.pos=this.pos.add(trajectory.scale(seconds));
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public double getElacticity() {
		return elacticity;
	}
	
	public void setElacticity(double elacticity) {
		this.elacticity = elacticity;
	}
	
	public double getCharge() {
		return charge;
	}
	
	public void setCharge(double charge) {
		this.charge = charge;
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public void setPos(Vector pos) {
		this.pos = pos;
	}
	
	public double getX() {
		return pos.getElement(0);
	}
	
	public void setX(double x) {
		pos=new Vector(x,pos.getElement(1));
	}
	
	public double getY() {
		return pos.getElement(1);
	}
	
	public void setY(double y) {
		pos=new Vector(pos.getElement(0),y);
	}
	
	public Vector getTrajectory() {
		return trajectory;
	}
	
	public void setTrajectory(Vector trajectory) {
		if(trajectory.getLength()>50*(1000/20)){
			this.trajectory=trajectory.scale(50*(1000/20)/trajectory.getLength());
		}else{
			this.trajectory = trajectory;//trajectory.scale(momentum/getMomentum().getLength());
		}/**/
		
	}

	public void reflect(Vector vector) {
		LastCollidedWith=null;
		Matrix toBase= Matrix.createOrthonormal(vector);
		Matrix fromBase=toBase.invert();
		Vector temp =toBase.apply(trajectory);
		temp= new Vector(-Math.abs(temp.getElement(0)),temp.getElement(1));
		this.setTrajectory(fromBase.apply(temp));
		//this.advance(.02);
	}
	public void bounceX(Movable m){
		
		double x1=this.trajectory.getElement(0);
		double x2=m.trajectory.getElement(0);
		double cm=(x1*getMass() + x2*m.getMass())/(getMass()+m.getMass());
		x1-=cm;
		x2-=cm;
		x1=Math.abs(x1);
		x2=-Math.abs(x2);
		x1+=cm;
		x2+=cm;
		this.setTrajectory(new Vector(x1,trajectory.getElement(1)));
		m.setTrajectory(new Vector(x2,m.trajectory.getElement(1)));
		
		
	}
	

	public abstract void bounce(Movable m);
	
	
	public Vector getMomentum(){
		return this.trajectory.scale(this.mass);
	}
	
}
