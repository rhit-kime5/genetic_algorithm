package main;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Class: GenerationViewer
 * @author Group R-202
 * <br>Purpose: Used to view all chromosomes in a given generation
 * <br>For example:
 * <pre>
 * 		GenerationViewer genViewer = new GenerationViewer(secondGen);
 * </pre>
 */
public class GenerationViewer {
	
	
	// Constants
	public static final Dimension GENERATION_VIEWER_SIZE = new Dimension(630, 670);
	
	
	// Fields
	public JFrame frame;
	public Generation generationToView;
	public GenerationComponent comp;
	
	
	// Constructor
	public GenerationViewer(Generation generationToView) {
		this.generationToView = generationToView;
		
		frame = new JFrame();
		frame.setTitle("Generation Viewer");
		frame.setSize(GENERATION_VIEWER_SIZE);
		
		comp = new GenerationComponent(this.generationToView);
		frame.add(comp);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // end constructor
	
	
} // end class
