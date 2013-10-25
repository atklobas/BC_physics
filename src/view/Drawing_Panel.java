package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class Drawing_Panel extends JPanel{
	private int width,height;
	
	private BufferedImage[] image;
	private int imageLength=2;
	private int currentImage=1;
	
	public Drawing_Panel(int x, int y){
		this.width=x;
		this.height=y;
		this.setPreferredSize(new Dimension(x,y));
		image=new BufferedImage[imageLength];
		for(int i=0;i<imageLength;i++){
			image[i]=new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
		}
		
	}
	
	public void drawComponents(List<Renderable> toRender){
		int num=currentImage+1;
		Graphics2D g=image[num%imageLength].createGraphics();
		for(Renderable r:toRender){
			g.drawImage(r.getImage(), r.getX(), r.getY(), null);
		}
		currentImage=num;
	}
	
	
	
	/*private double x,y;
	private double xv=100,yv=150;
	private int bs=21;
	private long lastProc=System.currentTimeMillis();
	*/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawImage(image[currentImage%imageLength], 0, 0, null);
        g.setColor(Color.CYAN);
        g.drawString("Rendered at:"+System.currentTimeMillis(),10,20);
        /*
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);
       
        //int x=50,y=30;
        if(x<0||x+bs>width){
        	x=0;
        	xv*=-1;
        }
        if(y<0||y+bs>height){
        	y=0;
        	yv*=-1;
        }
        long current=System.currentTimeMillis();
        double elapse=(current-lastProc)/1000.0;
        g.drawString("time elapsed:"+elapse,10,30);
        
        x+=xv*elapse;
        y+=yv*elapse;
        lastProc=current;
        g.setColor(Color.RED);
        g.fillRect(((int)x)+1,((int)y)+1,bs-1,bs-1);
        g.setColor(Color.BLACK);
        g.drawRect((int)x,(int)y,bs,bs);*/
    }

}
