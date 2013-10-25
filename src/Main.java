
import java.util.HashMap;
import java.util.Random;

import controller.Collision_Controller;
import model.Collision_Simulator;
public class Main {
	public static void main(String args[]){
		/*System.out.println(evenDigits(8342116));
		System.out.println(evenDigits(-34512));
		System.out.println(evenDigits(-31515));
		System.out.println(evenDigits(212));
		System.out.println(evenDigits(2112));
		System.out.println(evenDigits(21212));//3
		System.out.println(evenDigits(1212));
		System.out.println(evenDigits(2121));
		System.out.println(evenDigits(12121));
		/*smartmap.
		for(int i=1;i<=10;i++){
			//System.out.println(smartString(i));
		}
		for(int i=1;i<=10;i++){
			//System.out.println(starString2(i));
		}
		Random rand =new Random();
		
		
		int length=9000;
		int iterations=10000;
		System.out.println("testing from 0 to "+length+" stars");
		for(int j=0;j<10&&false;j++){
		
		long time1=System.currentTimeMillis();
		for(int i=1;i<=iterations;i++){
			starString(rand.nextInt(length));
		}
		long time2=System.currentTimeMillis();
		for(int i=1;i<=iterations;i++){
			smartString(rand.nextInt(length));
		}
		long time3=System.currentTimeMillis();
		System.out.println();
		System.out.println("recursive logic:"+((time2-time1)));
		//System.out.println("iterative recursion:"+((time3-time2)));
		System.out.println("smart recursive logic:"+((time3-time2)));
		}
		
		*/
		//System.out.println("initializing collision simulator");
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
