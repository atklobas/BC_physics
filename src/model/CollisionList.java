package model;

import java.util.ArrayList;

import mathematics.Vector;
/**
 * 
 * @author Anthony Klobas
 * 
 * note: if objects move, make a new list, this does not support shrinking (yet)
 *
 */
public class CollisionList {
	private static final int EAST=1,SOUTH=2;
	private static final int SOUTH_EAST=3;
	private static final int WEST=4;
	private static final int NORTH=5;
	private static final int NORTH_WEST=6;
	private static final int NORTH_EAST=7;
	private static final int SOUTH_WEST=8;
	private static final int CENTER=0;
	
	private CollisionList subLists[];
	private ArrayList<Collidable> list;
	
	
	private int minX,minY,maxX,maxY;
	
	
	
	private CollisionList levelUp;
	private int maxContained;
	private boolean isTop, isBottom;
	//This Constructor comes with a massive performance cost
	public CollisionList(){
		this(Integer.MAX_VALUE,Integer.MAX_VALUE);
	}
	public CollisionList(int n){
		this(Integer.MAX_VALUE,Integer.MAX_VALUE,n);
	}
	public CollisionList(int width,int height){
		this(width,height,10);//10 is default max per list
	}
	public CollisionList(int width,int height,int maxContained){
		this.minX=this.minY=0;
		this.maxX=width;
		this.maxY=height;
		this.maxContained=maxContained;
		isTop=true;
		isBottom=true;
		list=new ArrayList<Collidable>();
	}
	/**
	 * this method is only to be used internally for instanciation of subLists;
	 */
	private CollisionList(int minX,int maxX, int minY, int maxY,int maxContained, CollisionList levelUp){
		this.minX=minX;
		this.maxX=maxX;
		this.minY=minY;
		this.maxY=maxY;
		this.maxContained=maxContained;
		this.levelUp=levelUp;
		isTop=false;
		isBottom=true;
		list=new ArrayList<Collidable>();
	}
	
	private void toSublists(){
		int newWidth=(maxX-minX)/2;
		int newHeight=(maxY-minY)/2;
		if(newWidth<=800||true){
			return;//if everything is in the same spot, you'd get infinite recursion
		}
		
		this.subLists=new CollisionList[4];
		this.subLists[CENTER]=new CollisionList(minX,minX+newWidth,minY,minY+newHeight,maxContained, this);
		this.subLists[SOUTH]=new CollisionList(minX,minX+newWidth,minY+newHeight,maxY+newHeight,maxContained, this);
		this.subLists[EAST]=new CollisionList(minX+newWidth,maxX,minY,minY+newHeight,maxContained, this);
		this.subLists[SOUTH_EAST]=new CollisionList(minX+newWidth,maxX,minY+newHeight,maxY+newHeight,maxContained, this);
		isBottom=false;
		for(Collidable c: list){
			add(c);
		}
		list=null;
	}
	
	
	public void add(Collidable c){
		if(isBottom){
			list.add(c);
			if(list.size()>this.maxContained) toSublists();
		}else{
			
			if(c.getX()>this.minX){//add to east/southeast
				if(c.getY()>this.minY){
					subLists[CollisionList.SOUTH_EAST].add(c);
				}else{
					subLists[CollisionList.EAST].add(c);
					if(c.getY()+c.getBoundingWidth()>this.minY);{
						subLists[CollisionList.SOUTH_EAST].add(c);
					}
				}
			}else{
				if(c.getX()+c.getBoundingWidth()>this.minX){//overlaps east&west
					if(c.getY()>this.minY){
						subLists[CollisionList.SOUTH_EAST].add(c);
						subLists[CollisionList.SOUTH].add(c);
					}else{
						subLists[CollisionList.CENTER].add(c);
						subLists[CollisionList.EAST].add(c);
						if(c.getY()+c.getBoundingWidth()>this.minY);
							subLists[CollisionList.SOUTH].add(c);{
							subLists[CollisionList.SOUTH_EAST].add(c);
						}
					}
				}else{//only west
					if(c.getY()>this.minY){
						subLists[CollisionList.SOUTH].add(c);
					}else{
						subLists[CollisionList.CENTER].add(c);
						if(c.getY()+c.getBoundingWidth()>this.minY);{
							subLists[CollisionList.SOUTH].add(c);
						}
					}
				}
				
				
			}
			
			
			
			
			
			/*switch(pos){
			default:;
			case CollisionList.EAST:;
			case CollisionList.SOUTH:;
			case CollisionList.SOUTH_EAST:;
			
			}*/
			
			
			
			//subLists[pos].add(c);
		}
	}
	private boolean checkCollision(Collidable m1, Collidable m2){
		if(m1 instanceof Sphere){
			Sphere s1 =(Sphere)m1;
			if(m2 instanceof Sphere){
				Sphere s2 =(Sphere)m2;
				
				double s1rad=s1.getRadius();
				double s2rad=s2.getRadius();
				
				if (s1.getPos().add(new Vector(s1rad,s1rad)).distance(s2.getPos().add(new Vector(s2rad,s2rad)))<s1rad+s2rad){
					return true;
				}
			}
		}
		return false;
	}
	
	public void CheckCollisions(){
		if(isBottom){
			int length=list.size();
			for(int i=0;i<length;i++){
				for(int j=i+1;j<length;j++){
					if(checkCollision(list.get(i),list.get(j))){
						((Movable)(list.get(i))).bounce(((Movable)list.get(j)));
					}
				}
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	public String toString(){
		if(isBottom){
			String ret=list.toString();
			ret=ret.replace('<', '[');
			ret=ret.replace('>', ']');
			return list.toString();
		}else{
			String ret=
				"<list"+this.hashCode()+">";
			for(CollisionList c:subLists){
				ret+=c;
			}
			return ret+"</list"+this.hashCode()+">";
		}
	}
	
	
	
	

}
