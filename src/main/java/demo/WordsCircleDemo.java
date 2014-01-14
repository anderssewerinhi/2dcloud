package demo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingUtilities;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.PFrame;

public class WordsCircleDemo extends PFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String names[] = {"Ankit","Bohra","Xyz","Claudia", "Anders","Kasper"};
	
	public WordsCircleDemo() {
	 super("Circle Text Demo",false,null);
	 setSize(480,320);
	 setLocationRelativeTo(null);
	}
	
	
	public void createWordObjects(int i,int x,int y, int w, int z){
		
		PNode aNode = new PNode();
		PText aText = new PText(names[i]);
		Font bigger = aText.getFont().deriveFont((float) 16.0);
		aText.setFont(bigger);
		aText.setPaint(Color.GREEN);
		aText.setBounds(x,y,w,z);
		aNode.addChild(aText);
		getCanvas().getLayer().addChild(aNode); 
		
	}
	public void initialize(){
		
		
		int x = 10, y= 10, w = 30, z = 30; 
		
		PNode centralNode = new PNode();
		PText centralTextNode =	new PText("Hello Me");
		centralTextNode.setBounds(70, 70, 400, 500);
		Font bigger = centralTextNode.getFont().deriveFont((float) 48.0);
		centralTextNode.setFont(bigger);
		centralTextNode.setPaint(Color.GREEN);
		centralTextNode.setWidth(13);
		centralNode.addChild(centralTextNode);
		
		getCanvas().getLayer().addChild(centralNode);
		
		for (int i=0;i < 3; i++){
			
			createWordObjects(i,x+=100,y+=100,w,z);
		}
		
		
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				new WordsCircleDemo().setVisible(true);
			}
			
		});
		

	}

}
