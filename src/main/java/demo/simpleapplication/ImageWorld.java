package demo.simpleapplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class ImageWorld implements World {
	
	private final PImage img;
	
	private final PLayer layer = new PLayer();
	
	public ImageWorld() {
		final InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream("hilogo.gif");
        final URL url = getClass().getClassLoader().getResource("hilogo.gif");
        System.out.println("Loading image from URL " + url);
        try {
			img = new PImage(ImageIO.read(imgStream));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        
        layer.addChild(img);

	}

	public PText getTextLabel(int idx) {
		throw new RuntimeException("No labels in this world");
	}


	public PImage getImage(int idx) {
		if (idx != 0) {
			throw new RuntimeException("Only one image (idx 0) in this world");
		}
		return img;
	}

	public PLayer getLayer() {
		return layer;
	}

}
