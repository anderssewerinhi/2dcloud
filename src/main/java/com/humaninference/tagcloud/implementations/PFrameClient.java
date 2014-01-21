package com.humaninference.tagcloud.implementations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.rmi.RemoteException;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.PFrame;

/**
 * 
 * @author andersprivat
 * 
 * A base class for rendering animations on a PFrame client.
 * 
 * This class takes care of constructing the PFrame, connecting the canvas
 * to the layer(s) in the World, and coordinating the program flow.
 * 
 * Note the Observer: A PFrame takes a while to get ready, and can't handle
 * any requests until initialize() is in progress, and the world has been 
 * hooked up to the canvas. 
 *
 */
public class PFrameClient extends PFrame implements Client, Animation.Observer {
	
	/**
	 * 
	 * @author andersprivat
	 * 
	 * Do NOT start using an instance of this class, until you have received the
	 * pframeClientIsReady() event! 
	 */
	public interface Observer {
		
		/**
		 * Client can accept requests for animation once you have received this event.
		 */
		void pframeClientIsReady();
	}

    private static final long serialVersionUID = 1L;

    protected World world;
    
    private Master master;
    
    private final Observer obs;
    
    public PFrameClient(final String title, Observer obs, final World world, final Master master) {
        this(title, null, obs, world, master);
    }

    public PFrameClient(final String title, final PCanvas aCanvas, final Observer obs, final World world, final Master master) {
        super(title, true, aCanvas);
        this.world = world;
        this.master = master;
        this.obs = obs;
    }
    
    public void initialize() {
    	
        /* 
         * Set the background black 
         */
        getCanvas().setBackground(Color.black);
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
