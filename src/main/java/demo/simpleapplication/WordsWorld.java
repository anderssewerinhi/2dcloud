package demo.simpleapplication;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import com.humaninference.tagcloud.World;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

public class WordsWorld implements World{

	private final PLayer layer = new PLayer();

	private final List<PText> textLabelList = new LinkedList<PText>();
	
	private final List<PImage> imgList = new LinkedList<PImage>();
	

	public PText getTextLabel(int idx) {
		if (idx < 0 || idx > textLabelList.size() || textLabelList.isEmpty()  ) {
			throw new RuntimeException(
					String.format(
							"No label nr. %d in this world - we have %d text labels", 
							idx, textLabelList.size()));
		}
		return textLabelList.get(idx);
	}

	public PPath getEdge(int idx) {
		throw new RuntimeException("No edges in this world");
	}

	public PImage getImage(int idx) {
		//throw new RuntimeException("No img in this world");
		if (idx < 0 || idx > imgList.size() || imgList.isEmpty()  ) {
			throw new RuntimeException(
					String.format(
							"No label nr. %d in this world - we have %d text labels", 
							idx, imgList.size()));
		}
		return imgList.get(idx);
	}

	public PLayer getLayer() {
		return layer;
	}

	public void addLogo(double x, double y, double scale) {
		addImage("hilogo.gif", x, y, scale);
	}

	public void addImage(final String resourceName, double x, double y, double scale) {
		final InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        try {
			final PImage img = new PImage(ImageIO.read(imgStream));
			img.setOffset(x, y);
			img.setScale(scale);
	        layer.addChild(img);
	        imgList.add(img);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        
	}

	public void addLabel(String text, double x, double y, double scale, Color color){
		final PText textLabel; 
		textLabel = new PText(text);
		textLabel.setVisible(true);
		Font bigger = textLabel.getFont().deriveFont((float) 24.0);
		textLabel.setFont(bigger);
		textLabel.setOffset(x, y);
		textLabel.setScale(scale);
		textLabel.setTextPaint(color);
		layer.addChild(textLabel); 
		textLabelList.add(textLabel);


	}


}
