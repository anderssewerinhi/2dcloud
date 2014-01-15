package com.humaninference.tagcloud.implementations;

import java.rmi.RemoteException;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.PFrame;

public class PFrameClient extends PFrame implements Client, Animation.Observer {
	
	public interface Observer {
		void pframeClientIsReady();
	}

    private static final long serialVersionUID = 1L;

    protected World world;
    
    private Master master;
    
    private final Observer obs;
    
    public PFrameClient(final String title, Observer obs) {
        this(title, null, obs);
    }

    public PFrameClient(final String title, final PCanvas aCanvas, final Observer obs) {
        super(title, false, aCanvas);
        this.obs = obs;
    }
    
    
    public void setMaster(final Master master) {
        this.master = master;
    }
    
    public void setWorld(final World world) {
    	this.world = world;
    }

    public void initialize() {
    	getCanvas().getLayer().addChild(world.getLayer());
		obs.pframeClientIsReady();
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

	/**
	 * Override in subclasses to implement advanced world manipulation based on 
	 * animation life cycle.
	 * 
	 * @param tag Identifies an animation, so we can do world manipulations when it
	 * is done. Such as removing or adding items to the world.
	 */
	protected void templateMethodOnAnimationFinished(@SuppressWarnings("unused") final int tag) {
		// Template method - do nothing.
		// Note that World is protected, so you can override and manipulate it here.
	}
	
	public void onAnimationFinished(final int tag) {
		templateMethodOnAnimationFinished(tag);
		try {
			master.animationIsFinished(tag);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		
		
	}
}
