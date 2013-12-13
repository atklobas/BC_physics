package view;

import java.awt.Color;
import java.awt.Image;

public interface Renderable {
	int getImageX();
	int getImageY();
	Image getImage();
	
	//used for vector drawings objects with a defined geometric perimeter  
	//public Color getColor();
	//Shape getShape();
}
