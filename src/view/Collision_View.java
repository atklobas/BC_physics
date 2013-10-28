package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.Collision_Controller;


public class Collision_View {
	JFrame f;
	Drawing_Panel panel;
	int width=800,height=600;
	Collision_Controller controller=null;
	public Collision_View(){
		this(800,600,null);
	}


    public Collision_View(int width, int height, Collision_Controller c) {
		this.width=width;
		this.height=height;
		controller=c;
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
	}

    

	private void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        f = new JFrame("Collision Simulaton");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        panel=new Drawing_Panel(width,height);
        f.add(panel);
        f.pack();
        f.setVisible(true);
        if(this.controller!=null){
        	controller.startTimer();
        }
    } 

	
	public void updateScreen(List<Renderable> toRender){
		if(panel==null)return;
		panel.drawComponents(toRender);
		f.repaint();
	}

	public void addMouseListener(Collision_Controller controller) {
		// TODO Auto-generated method stub
		
	}


	public void addMouseMotionListener(Collision_Controller controller) {
		// TODO Auto-generated method stub
		
	}


	public void move() {
		if(f!=null)
		f.repaint();
		// TODO Auto-generated method stub
		
	}

}
