package demo;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolox.PFrame;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

public class GraphTest extends  PFrame {

	public GraphTest() {
		this(null);
	}

	public GraphTest(final PCanvas aCanvas) {
		super("GraphEditor", false, aCanvas);
	}


	
	public void initialize() {
		int numNodes = 50;
		int numEdges = 50;
		
                // Initialize, and create a layer for the edges
                // (always underneath the nodes)
        final PLayer l = new PLayer();

		
        final PCamera c = new PCamera();
        c.setBounds(30, 30, 100, 80);
        c.scaleView(1.2);
        c.addLayer(l);
        PBoundsHandle.addBoundsHandlesTo(c);
        final PLayer topLayer = getCanvas().getLayer();
		topLayer.addChild(c);

        final PCamera c2 = new PCamera();
        c2.setBounds(30, 230, 200, 200);
        c2.scaleView(1.2);
        c2.addLayer(l);
        PBoundsHandle.addBoundsHandlesTo(c2);
        topLayer.addChild(c2);
        c2.setViewOffset(0, 230);
 
		addNodesToLayer(200, 200, numNodes, numEdges, l);

	}
	private void addNodesToLayer(int width, int height, int numNodes,
			int numEdges, final PLayer nodeLayer) {
		PLayer edgeLayer = new PLayer();
		nodeLayer.addChild(edgeLayer);
//		getCamera().addLayer(0, edgeLayer);
		Random random = new Random();

                // Create some random nodes
		// Each node's attribute set has an ArrayList to store associated edges
		for (int i = 0; i < numNodes; i++) {
			float x = random.nextInt(width);
			float y = random.nextInt(height);
			PPath node = PPath.createEllipse(x, y, 20, 20);
			node.addAttribute("edges", new ArrayList());
			nodeLayer.addChild(node);
		}
		
                // Create some random edges
		// Each edge's attribute set has an ArrayList to store associated nodes
		for (int i = 0; i < numEdges; i++) {
			int n1 = random.nextInt(numNodes);
			int n2 = random.nextInt(numNodes);
			while (n1 == n2) {
				n2 = random.nextInt(numNodes);  // Make sure we have two distinct nodes.
			}
			
			PNode node1 = nodeLayer.getChild(n1);
			PNode node2 = nodeLayer.getChild(n2);
			PPath edge = new PPath();
			((ArrayList)node1.getAttribute("edges")).add(edge);
			((ArrayList)node2.getAttribute("edges")).add(edge);
			edge.addAttribute("nodes", new ArrayList());
			((ArrayList)edge.getAttribute("nodes")).add(node1);
			((ArrayList)edge.getAttribute("nodes")).add(node2);
			edgeLayer.addChild(edge);
			updateEdge(edge);
		}
		
                // Create event handler to move nodes and update edges
		nodeLayer.addInputEventListener(new PDragEventHandler() {
			{
				PInputEventFilter filter = new PInputEventFilter();
				filter.setOrMask(InputEvent.BUTTON1_MASK | InputEvent.BUTTON3_MASK);
				setEventFilter(filter);
			}
			public void mouseEntered(PInputEvent e) {
				super.mouseEntered(e);
				if (e.getButton() == MouseEvent.NOBUTTON) {
					e.getPickedNode().setPaint(Color.RED);
				}
			}
			
			public void mouseExited(PInputEvent e) {
				super.mouseExited(e);
				if (e.getButton() == MouseEvent.NOBUTTON) {
					e.getPickedNode().setPaint(Color.WHITE);
				}
			}
			
			protected void startDrag(PInputEvent e) {
				super.startDrag(e);
				e.setHandled(true);
				e.getPickedNode().moveToFront();
			}
			
			protected void drag(PInputEvent e) {
				super.drag(e);
				
				ArrayList edges = (ArrayList) e.getPickedNode().getAttribute("edges");
				for (int i = 0; i < edges.size(); i++) {
					GraphTest.this.updateEdge((PPath) edges.get(i));
				}
			}
		});
	}
	
	public void updateEdge(PPath edge) {
		// Note that the node's "FullBounds" must be used (instead of just the "Bound") 
		// because the nodes have non-identity transforms which must be included when
		// determining their position.

		PNode node1 = (PNode) ((ArrayList)edge.getAttribute("nodes")).get(0);
		PNode node2 = (PNode) ((ArrayList)edge.getAttribute("nodes")).get(1);
		Point2D start = node1.getFullBoundsReference().getCenter2D();
		Point2D end = node2.getFullBoundsReference().getCenter2D();
		edge.reset();
		edge.moveTo((float)start.getX(), (float)start.getY());
		edge.lineTo((float)end.getX(), (float)end.getY());
	}
	
    public static void main(final String[] args) {
        new GraphTest();
    }

}