package controller;

import model.*;
import java.util.Random;

import mathematics.Vector;
import model.Collision_Simulator;
import model.Sphere;
/**
 * 
 * @author Anthony Klobas
 * 
 * this class is currently used to load test cases into the the game
 * 
 */
public class Tester {
	
	public void testCase(){
		lineCollisionTest();
	}
	
	public void lineCollisionTest(){
		//sim.addGameObject(new Sphere(20,300, new Vector(200,0),100));
		//sim.addGameObject(new Sphere(780,300, new Vector(-200,0),100));
		//this.addRandomCircles(200);
		//sim.addGameObject(new Line(200,100,600,500));
		//sim.addGameObject(new Line(200,500,600,100));
		
		sim.addGameObject(new Box(100,100,100,100));
	}
	
	
	
	
	
	
	
	
	Collision_Simulator sim;
	Random rand=new Random();
	
	
	
	public Tester(Collision_Simulator sim){
		this.sim=sim;
	}
	public void placeSphere(int x, int y,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(0,0),rand.nextInt(50)+50);
		s.setImmovable(isImmovable);
		sim.addGameObject(s);
	}
	public void placeSphere(int x, int y, int xvel, int yvel,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(xvel,yvel),rand.nextInt(50)+50);
		s.setImmovable(isImmovable);
		sim.addGameObject(s);
	}
	
	
	public void addShpere(){
		this.addRandomCircles(1);
	}
	public void addNewtonsCradle(){
		Sphere s=new Sphere(50,100,new Vector(500,0),900);
		sim.addGameObject(s);
		
		
		for(int i=0;i<4;i++){
			s=new Sphere(200+120*i,100,new Vector(0,0),900);
			sim.addGameObject(s);
		}
	}
	
	public void addRandomCircles(int n){
		for(int i=0;i<n;i++){
			Sphere s=new Sphere(0,0,new Vector(rand.nextInt(200)-100,rand.nextInt(200)-100),rand.nextInt(200)+50 );
			int width=s.getBoundingWidth();
			int height=s.getBoundingHeight();
			s.setPos(new Vector(rand.nextInt(sim.getWidth()-width),rand.nextInt(sim.getHeight()-height)));
			sim.addGameObject(s);
		}
	}

}
