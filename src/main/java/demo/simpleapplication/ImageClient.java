package demo.simpleapplication;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.PFrame;

public class ImageClient extends PFrame implements Client, Animation.Observer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private World world;
    
    private Master master;
    
    public ImageClient() {
        this(null);
    }

    public ImageClient(final PCanvas aCanvas) {
        super("Image", false, aCanvas);
    }
    
    public void setMaster(final Master master) {
        this.master = master;
    }

    public void initialize() {
    	world = new ImageWorld();
    	getCanvas().getLayer().addChild(world.getLayer());
    	master.clientIsReady();
    }

	public void setWorld(World world) {
		throw new RuntimeException("Not implemented");
	}

	public void performAnimation(final Animation animation) {
		animation.perform(world, this);
	}

	public void setViewport(double xTopLeft, double yTopLeft) {
		PCamera camera = getCanvas().getLayer().getCamera(0);
		PBounds bounds = camera.getViewBounds();
		bounds.x = xTopLeft;
		bounds.y = yTopLeft;
		camera.setViewBounds(bounds); 
	}

	public void onAnimationFinished() {
		master.animationIsFinished();
	}
}
