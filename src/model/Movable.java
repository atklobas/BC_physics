package model;

import mathematics.Vector;
import mathematics.Matrix;

public abstract class Movable extends Collidable{
	private double mass, elacticity, charge;
	private Vector pos,lastpos,trajectory;
	
	public Movable(double x, double y, Vector trajectory,double mass){
		this.pos=new Vector(x,y);
		this.lastpos=pos;
		this.trajectory=trajectory;
		this.mass=mass;
		this.charge=0;
	}
	
	public abstract int getBoundingWidth(); 
	public abstract int getBoundingHeight();
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
	
	public int getX() {
		return (int)pos.getElement(0);
	}
	
	public void setX(double x) {
		pos=new Vector(x,pos.getElement(1));
	}
	
	public int getY() {
		return (int)pos.getElement(1);
	}
	
	public void setY(double y) {
		pos=new Vector(pos.getElement(0),y);
	}
	
	public Vector getTrajectory() {
		return trajectory;
	}
	
	public void setTrajectory(Vector trajectory) {
		this.trajectory = trajectory;
	}

	public void reflect(Vector vector) {
		
		Matrix toBase= Matrix.createOrthonormal(vector);
		
		Matrix fromBase=toBase.invert();
		
		Vector temp =toBase.apply(trajectory);
		temp= new Vector(-temp.getElement(0),temp.getElement(1));
		
		
		
		temp=fromBase.apply(temp);
		
		if(temp.getLength()>this.trajectory.getLength()){
			temp=temp.scale(trajectory.getLength()/temp.getLength());
		}else if(temp.getLength()<this.trajectory.getLength()){
			temp=temp.scale(temp.getLength()/trajectory.getLength());
		}
		
		if(Math.abs(temp.getLength()-this.trajectory.getLength())>.00000001){
			System.out.println(temp.getLength()+":"+(temp.getLength()-trajectory.getLength()));
		}
		trajectory=temp;
		//System.out.println("vector="+this.trajectory);
		//System.exit(0);
		
	}

	public void bounce(Movable m) {
		
		Vector cmVel=trajectory.scale(mass).add(m.trajectory.scale(m.mass)).scale(1/(this.mass+m.mass));
		//System.out.println(cmVel);
		if(this instanceof Sphere){
			Sphere s1=(Sphere)this;
			if(m instanceof Sphere){
				Sphere s2=(Sphere)m;
				Vector normal =s1.getPos().subtract(s2.getPos());
				this.trajectory=this.trajectory.subtract(cmVel);
				m.trajectory=m.trajectory.subtract(cmVel);
				
				this.reflect(normal);
				m.reflect(normal);
				this.trajectory=this.trajectory.add(cmVel);
				m.trajectory=m.trajectory.add(cmVel);
				
				
			}
			
		}
		Vector cmVel2=trajectory.scale(mass).add(m.trajectory.scale(m.mass)).scale(1/(this.mass+m.mass));
		if(Math.abs(cmVel.getLength()-cmVel2.getLength())>.01){
			System.out.println(cmVel.getLength()-cmVel2.getLength());
		}/**/
		// TODO Auto-generated method stub
		
	}
	
}
