package demo.simpleapplication;

import java.rmi.RemoteException;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.PFrame;

public class ImageClient extends PFrame implements Client, Animation.Observer {
	
	public interface Observer {
		void imageClientIsReady();
	}

    private static final long serialVersionUID = 1L;

    private World world;
    
    private Master master;
    
    private final Observer obs;
    
    public ImageClient(Observer obs) {
        this(null, obs);
    }

    public ImageClient(final PCanvas aCanvas, final Observer obs) {
        super("Image", false, aCanvas);
        this.obs = obs;
    }
    
    public void setMaster(final Master master) {
        this.master = master;
    }

    public void initialize() {
    	world = new ImageWorld();
    	getCanvas().getLayer().addChild(world.getLayer());
		obs.imageClientIsReady();
    }

	public void performAnimation(final Animation animation) throws RemoteException {
		animation.perform(world, this);
	}

	public void setViewport(double xTopLeft, double yTopLeft) {
		PCamera camera = getCanvas().getLayer().getCamera(0);
		PBounds bounds = camera.getViewBounds();
		bounds.x = xTopLeft;
		bounds.y = yTopLeft;
		camera.setViewBounds(bounds); 
	}

	public void onAnimationFinished(final int tag) {
		try {
			master.animationIsFinished(tag);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		
	}
}
