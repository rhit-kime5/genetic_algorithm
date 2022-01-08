package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Class: ChromosomeComponent
 * @author Group R-202
 * <br>Purpose: Used as a component to draw the chromosome onto the chromosome viewer
 * <br>For example:
 * <pre>
 * 		ChromosomeComponent comp = new ChromosomeComponent(lactobacillus);
 * </pre>
 */
public class ChromosomeComponent extends JComponent {
	
	
	// Fields
	public Chromosome chromosomeToDraw;
	
	
	// Constructor
	public ChromosomeComponent(Chromosome chromosomeToDraw) {
		this.chromosomeToDraw = chromosomeToDraw;
	} // end constructor
	
	
	// paintComponent method
	@Override
	protected void paintComponent(Graphics g) {
		
		// Set Graphics2D background
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (this.chromosomeToDraw == null) {
			return;
		}
		
		// Location of the first gene to draw
		int upperleftX = 10;
		int upperleftY = 10;
		
		//
		Double sqrt = Math.sqrt(this.chromosomeToDraw.length);
		Integer sideLength = (int) Math.ceil(sqrt);
		
		// Dimensions for drawing a gene
		int width = 500 / sideLength;
		int height = 500 / sideLength;
		
		// Draw all genes
		for (int i = 0; i < this.chromosomeToDraw.length; i++) {
			
			// Access the gene to draw
			Integer gene = this.chromosomeToDraw.genes.get(i);
			
			if (upperleftX >= 500 - width) { 	// If the genes drawn reach the right end of the screen,
				upperleftX = 10;				// go back to the left end of the screen
			} else {
				if (gene == 0) {
					g2.setColor(Color.BLACK); 	// Black for gene 0
				} else {
					g2.setColor(Color.MAGENTA); // Purple for gene 1
				} // end if statement
				g2.fillRect(upperleftX + (width * (i % sideLength)), upperleftY + (width * (i / sideLength)), width, height);
			} // end if statement
		} // end for loop
	} // end method
	
	
} // end class
