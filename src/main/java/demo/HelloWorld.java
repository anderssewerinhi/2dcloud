package demo;

import java.awt.Font;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.PFrame;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

public class HelloWorld extends PFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HelloWorld() {
        this(null);
    }

    public HelloWorld(final PCanvas aCanvas) {
        super("HelloWorld", false, aCanvas);
    }

    public void initialize() {

        final PLayer l = new PLayer();

    	PText someText = new PText("Hello, world");
    	Font bigger = someText.getFont().deriveFont((float) 48.0);
    	someText.setFont(bigger);
    	
    	someText.setBounds(20, 20, 400, 50);
        l.addChild(someText);
        
        
        final PCamera c = new PCamera();
        c.setBounds(30, 30, 100, 80);
        c.scaleView(1.2);
        c.addLayer(l);
        PBoundsHandle.addBoundsHandlesTo(c);
        final PLayer topLayer = getCanvas().getLayer();
		topLayer.addChild(c);

        final PCamera c2 = new PCamera();
        c2.setBounds(80, 230, 200, 200);
        c2.scaleView(1.2);
        c2.addLayer(l);
        
        
//        final PBounds bounds = l.getBounds();
/*
         final PBounds bounds = new PBounds(0, 0, 0, 0);

        double x = bounds.x;
		c2.animateViewToCenterBounds(bounds, true, 1000);
        */
        PBoundsHandle.addBoundsHandlesTo(c2);
        topLayer.addChild(c2);
        c2.setViewOffset(0, 230);
//        topLayer.setOffset(-50, 0);
//      getCanvas().getLayer().animateToPositionScaleRotation(-50, 0, 1, 0, 1000);
        /*
        final PBounds layerbounds =  topLayer.getBounds();
        layerbounds.x -= 50;
        topLayer.setBounds(layerbounds.getBounds2D());
		*/
        someText.setOffset(-50, 0);

    }

    public static void main(final String[] args) {
        new HelloWorld();
    }
}
