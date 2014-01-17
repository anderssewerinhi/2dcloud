package demo.simpleapplication;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.Position;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

public class WordsWorld implements World{
	
	private final PLayer layer = new PLayer();

	private final PImage img; 
	
	//private final PText textLabel = new PText();
	
	private List<PText> textLabelList;
	
	
	
	public WordsWorld() {
		
		
		final InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream("hilogo.gif");
        final URL url = getClass().getClassLoader().getResource("hilogo.gif");
        System.out.println("Loading image from URL " + url);
        try {
			img = new PImage(ImageIO.read(imgStream));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        //setLocationRelativeTo(null);
        
         layer.addChild(img);
      
         
         textLabelList = new ArrayList<PText>();
		
	}

public PText getTextLabel(int idx) {
	//throw new RuntimeException("No labels in this world");
	if (idx < 0 || idx > textLabelList.size() || textLabelList.isEmpty()  ) {
		throw new RuntimeException("No labels in this world");
	}
	return textLabelList.get(idx);
}

public PPath getEdge(int idx) {
	throw new RuntimeException("No edges in this world");
}

public PImage getImage(int idx) {
	//throw new RuntimeException("No img in this world");
	if (idx != 0) {
		throw new RuntimeException("Only one image (idx 0) in this world");
	}
	return img;
}

public PLayer getLayer() {
	return layer;
}

public void addLabel(String text, double x, double y, double scale){

    PText textLabel; 
    
    textLabel = new PText(text);
    
    textLabel.setVisible(true);
	Font bigger = textLabel.getFont().deriveFont((float) 24.0);
	textLabel.setFont(bigger);

	textLabel.setOffset(x, y);
	textLabel.setScale(scale);
	
    //textLabel.setBounds(x, y, 10,10);
  
   
	layer.addChild(textLabel); 
	
	textLabelList.add(textLabel);

	
}


}
