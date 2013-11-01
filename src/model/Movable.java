package model;

import mathematics.Vector;
import mathematics.Matrix;

public abstract class Movable extends Collidable{
	private double mass, elacticity, charge;
	private Vector pos,lastpos,trajectory;
	private Movable LastCollidedWith=null;
	private double COR=1;
	public boolean immovable=false;
	//private int lastCollided=2;
	public void setImmovable(boolean isImmovable){
		immovable=isImmovable;
		//mass=0xFFFFFFFF;
	}
	
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
	public double getVelX(){
		return trajectory.getElement(0);
	}
	public double getVelY(){
		return trajectory.getElement(1);
	}
	
	public void setTrajectory(Vector trajectory) {
		if(immovable)return;
		if(trajectory.getLength()>50*(1000/20)){
			this.trajectory=trajectory.scale(50*(1000/20)/trajectory.getLength());
		}else{
			this.trajectory = trajectory;//trajectory.scale(momentum/getMomentum().getLength());
		}/**/
		
	}
	public boolean isImovable(){
		return immovable;
		//return mass==0xFFFFFFFF;
	}

	public void reflect(Vector vector) {
		//if(immovable)return;
		LastCollidedWith=null;
		Matrix toBase= Matrix.createOrthonormal(vector);
		Matrix fromBase=toBase.invert();
		Vector temp =toBase.apply(trajectory);
		temp= new Vector(-Math.abs(temp.getElement(0))*COR,temp.getElement(1));
		this.setTrajectory(fromBase.apply(temp));
	}
	public void bounceX(Movable m){
		
		if(immovable || m.immovable){
			
			double x1=this.trajectory.getElement(0);
			double x2=m.trajectory.getElement(0);
			if(!immovable)x1=Math.abs(x1);
			if(!m.immovable)x2=-Math.abs(x2);
			this.setTrajectory(new Vector(x1,trajectory.getElement(1)));
			m.setTrajectory(new Vector(x2,m.trajectory.getElement(1)));
			
		}else{
		
		double x1=this.trajectory.getElement(0);
		double x2=m.trajectory.getElement(0);
		double cm=(x1*getMass() + x2*m.getMass())/(getMass()+m.getMass());
		x1-=cm;
		x2-=cm;
		x1=Math.abs(x1)*(this.COR+m.COR)/2;
		x2=-Math.abs(x2)*(this.COR+m.COR)/2;
		x1+=cm;
		x2+=cm;
		this.setTrajectory(new Vector(x1,trajectory.getElement(1)));
		m.setTrajectory(new Vector(x2,m.trajectory.getElement(1)));
		}
		
	}
	

	public abstract void bounce(Movable m);
	
	
	public Vector getMomentum(){
		return this.trajectory.scale(this.mass);
	}


	public void setCOR(double cor) {
		this.COR=cor;
		// TODO Auto-generated method stub
		
	}

	public void reflect(Vector vector, double time) {
		advance(-time);
		reflect(vector);
		advance(time);
		
	}
	
	
}
