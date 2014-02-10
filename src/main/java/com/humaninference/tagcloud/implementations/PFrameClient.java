package com.humaninference.tagcloud.implementations;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.PFrame;

import com.humaninference.tagcloud.implementations.TextAnimation;

;

/**
 * 
 * @author andersprivat
 * 
 *         A base class for rendering animations on a PFrame client.
 * 
 *         This class takes care of constructing the PFrame, connecting the
 *         canvas to the layer(s) in the World, and coordinating the program
 *         flow.
 * 
 *         Note the Observer: A PFrame takes a while to get ready, and can't
 *         handle any requests until initialize() is in progress, and the world
 *         has been hooked up to the canvas.
 * 
 */
public class PFrameClient extends PFrame implements Client, Animation.Observer {

    static Logger logger = Logger.getLogger(PFrameClient.class);

    private final Lock lock = new ReentrantLock();
    private final PText popUpText = new PText();

    /**
     * 
     * @author andersprivat
     * 
     *         Do NOT start using an instance of this class, until you have
     *         received the pframeClientIsReady() event!
     */
    public interface Observer {

        /**
         * Client can accept requests for animation once you have received this
         * event.
         */
        void pframeClientIsReady();
    }

    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {

                int keyCode = e.getKeyCode();
                switch (keyCode) {
                case KeyEvent.VK_UP:

                    bounds.y += 50;
                    setViewport(bounds.x, bounds.y);
                    logger.debug("You moved the viewport up");
                    changeTitle("UP");

                    break;
                case KeyEvent.VK_DOWN:
                    
                    logger.debug("You moved the viewport down");
                    bounds.y -= 50;
                    setViewport(bounds.x, bounds.y);
                    changeTitle("DOWN");
                    break;
                    
                case KeyEvent.VK_LEFT:
                    
                    logger.debug("You moved the viewport to the left");
                    bounds.x += 50;
                    setViewport(bounds.x, bounds.y);
                    changeTitle("to the LEFT");
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    logger.debug("You moved the viewport to the right ");
                    bounds.x -= 50;
                    setViewport(bounds.x, bounds.y);
                    changeTitle("to the RIGHT");
                    break;
                }
               
            }
            return false;
        }
    }

    private static final long serialVersionUID = 1L;

    protected World world;

    private Master master;

    private final Observer obs;
    private PBounds bounds;

    public PFrameClient(final String title, Observer obs, final World world, final Master master,
            final boolean runAsFullScreen) {
        this(title, null, obs, world, master, runAsFullScreen);

        this.setFocusable(true);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
        super.addEscapeFullScreenModeListener();

    }

    public PFrameClient(final String title, final PCanvas aCanvas, final Observer obs, final World world,
            final Master master, final boolean runAsFullScreen) {
        super(title, runAsFullScreen, aCanvas);
        this.world = world;
        this.master = master;
        this.obs = obs;

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
        super.addEscapeFullScreenModeListener();

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
        final Animation.Observer obs = this;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // logger.debug("1 - entering performAnimation");
                animation.perform(world, obs);
                // logger.debug("2 - performAnimation");
            }
        });
    }

    synchronized public void setViewport(final double xTopLeft, final double yTopLeft) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                PCamera camera = getCanvas().getLayer().getCamera(0);
                bounds = camera.getViewBounds();
                bounds.x = xTopLeft;
                bounds.y = yTopLeft;
                camera.setViewBounds(bounds);
                
            }
        });
    }

    /**
     * Override in subclasses to implement advanced world manipulation based on
     * animation life cycle.
     * 
     * @param tag
     *            Identifies an animation, so we can do world manipulations when
     *            it is done. Such as removing or adding items to the world.
     */
    protected void templateMethodOnAnimationFinished(final int tag) {
        // Template method - do nothing.
        // Note that World is protected, so you can override and manipulate it
        // here.
    }

    public void onAnimationFinished(final int tag) {
        final Timer t = new Timer();
        final TimerTask r = new TimerTask() {

            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        logger.debug("3 - entering onAnimationFinished");

                        templateMethodOnAnimationFinished(tag);
                        try {
                            master.animationIsFinished(tag);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        logger.debug("4 - leaving onAnimationFinished");
                        logger.debug("----");

                    }
                });

            }

        };

        t.schedule(r, 100);
    }
    private void changeTitle(String where){
        this.setTitle("You moved the viewport " + where);
    }
}
