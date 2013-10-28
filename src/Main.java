
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import controller.Collision_Controller;
import model.Collidable;
import model.CollisionList;
import model.Collision_Simulator;
import model.Sphere;
public class Main {
	public static void main(String args[]){
		

		
		
		
		
		
		
		
		/*Random rand =new Random();
		
		//int[]testCases={100,200,400,800,1600,3200,6400,6400+3200,12800,12800+6400,25600,25600+12800,51200,51200+25600,102400};

		int[]testCases=new int[1500];
		for(int i=0;i<testCases.length;i++){
			testCases[i]=rand.nextInt(300);
			//System.out.println(testCases[i]);
		}
		System.out.println("------------------------------");
		int number=2000;
		int derp=0;
		for(int testRange=0;testRange<number;testRange++){
			
			
			int total=0;
			for(int i=0;i<1;i++){
				ArrayList<Sphere> list=new ArrayList<Sphere>();
				
				for(int j=0;j<300;j++){
					Sphere s=new Sphere(rand.nextInt(800),rand.nextInt(600),null,1);
					list.add(s);
				}
				
				
				
			long time1=System.currentTimeMillis();
			
			CollisionList c=new CollisionList(800,600,300);
			
			for(Collidable cc:list){
				c.add(cc);
			}
			long time2=System.currentTimeMillis();
			total+=time2-time1;
			}
			derp+=total;
			if(testRange%1000==0){
				System.out.println(testRange);
			}
			//System.out.println(total);
		}
	System.out.println(derp/(double)number);	
		/**/
		Collision_Controller Application=new Collision_Controller();
	}
	
	
	public static int evenDigits(int n){
		if(n<10){//base case
			if(n<0){//if negative, make not negative :P
				return evenDigits(-n);
			}
			return n%2==0?n:0;
		}
		int ret=evenDigits(n/10);
		if(n%2==0){//if even add a 0 and addcurrent
			ret=10*ret+n%10;
		}
		return ret;
	}
	/*
	public static int evenDigits(int n) {
	    if (n < 10) {
	        if ((n & 1) == 0) {
	            return n;
	        }
	        return 0;
	    }
	    if ((n & 1) == 0) {
	        return 10*evenDigits(n / 10) + n % 10;
	    }
	    return evenDigits(n / 10);
	}
	*/
	
	public static String toConcat="*";
	
	
	private static HashMap<Integer,String> smartmap=new HashMap<Integer,String>();
	
	public static String smartString(int n){
		String temp=smartmap.get(n);
		if(n<1){
			return "";
		}else if(n==1){
			return toConcat;
		}else if(temp!=null){
			return temp;
		}else{
			 temp=starString(n>>1);
			 temp+=temp;
			if((n&1)==1){
				temp+=toConcat;
			}
			smartmap.put(n, temp);
			return (temp);
		}
	}
	
	
	
	
	
	public static String starString(int n){
		if(n<1){
			return "";
		}else if(n==1){
			return toConcat;
		}else{
			String temp=starString(n>>1);
			if((n&1)==1){
				return toConcat+temp+temp;
			}
			return (temp+temp);
		}
	}
	
	public static String starString2(int n){
		if(n<1){
			return "";
		}else if(n==1){
			return toConcat;
		}else{
			return starString2(n-1)+toConcat;
		}
	}
	
}
