package com.humaninference.tagcloud;

/**
 * 
 * @author andersprivat
 * 
 * An interface for a factory that can create a world.
 * 
 * Use case:
 * 
 * To avoid having to distribute an actual World (that contains references to 
 * graphics that only live on a local machine/canvas), we instead start all nodes
 * off with a world description in a serializable format, that can then be converted
 * to a world description by a common WorldFactory implementation.
 * 
 * For example:
 * 
 * WorldFactory gets this input "Text 'Hello world' font Helvetica-Bold at 
 * x=100 y=200 z=10"
 * 
 * WorldFactory can then create a world that contains the actual graphics for this
 * on a local node.
 * 
 */
public interface WorldFactory {
	
	World makeWorld();

}
