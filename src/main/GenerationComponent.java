package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Class: GenerationComponent
 * @author Group R-202
 * <br>Purpose: Used as a component to draw a given generation on the generation viewer
 * <br>For example:
 * <pre>
 * 		GenerationComponent comp = new GenerationComponent(secondGen);
 * </pre>
 */
public class GenerationComponent extends JComponent {
	
	
	// Fields
	public Generation generationToDraw;

	
	// Constructor
	public GenerationComponent(Generation generationToDraw) {
		this.generationToDraw = generationToDraw;
	} // end constructor
	
	
	// paintComponent method
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (this.generationToDraw == null) {
			return;
		}
		
		// determine the side length of the generation grid, as the number of chromosomes
		Integer genGridSideLength = (int) Math.ceil(Math.sqrt(this.generationToDraw.populationSize));
		
		// dimensions for drawing a chromosome
		int widthOfChromosome = 500 / genGridSideLength;
		int heightOfChromosome = widthOfChromosome;
		
		// for each chromosome in the generation,
		for (int i = 0; i < this.generationToDraw.populationSize; i++) {
			
			// its location is determined as,
			int upperleftXOfChromosome = 10 + ((10 + widthOfChromosome) * (i % genGridSideLength));
				// e.g. for i=0 (the first chromosome), shift none, for i=1 (the second), shift by one width
			
			int upperleftYOfChromosome = 10 + ((10 + heightOfChromosome) * (i / genGridSideLength));
				// e.g. for (0 <= i < genGridSideLength), shift none, for (genGridSideLength <= i < genGridSideLength), shift by one width
			
			// access the chromosome to draw
			Chromosome c = this.generationToDraw.population.get(i);			
			
			// if the chromosome reaches the right end of the screen,
			if (upperleftXOfChromosome > 500 + widthOfChromosome) { 
				
				// shift it back to the left end of the screen
				upperleftXOfChromosome = 10;			
			} 
			
			// determine the side length of the chromosome grid, as the number of genes
			Integer chromoGridSideLength = (int) Math.ceil(Math.sqrt(c.length));
			
			
			// dimensions for drawing a gene
			int widthOfGene = widthOfChromosome / chromoGridSideLength;
			int heightOfGene = widthOfGene;
			
			// for each gene in the chromosome,
			for (int j = 0; j < c.length; j++) {
				
				// its location is determined as,
				int upperleftXOfGene = upperleftXOfChromosome + (widthOfGene * (j % chromoGridSideLength));
				int upperleftYOfGene = upperleftYOfChromosome + (widthOfGene * (j / chromoGridSideLength));
				
				// access the gene to draw
				Integer gene = c.genes.get(j);
				
				// if the gene reaches the right end of the chromosome,
				if (upperleftXOfGene > upperleftXOfChromosome + widthOfChromosome - widthOfGene) { 
					
					// shift it back to the left end of the chromosome
					upperleftXOfGene = upperleftXOfChromosome;			
				} 
				
				if (gene == 0) {
					g2.setColor(Color.BLACK); 	// Black for gene 0
				} else {
					g2.setColor(Color.MAGENTA); // Purple for gene 1
				} // end if statement
				g2.fillRect(upperleftXOfGene, upperleftYOfGene, widthOfGene, heightOfGene);
				
			} // end for loop
				
		} // end for loop

	} // end method
	
	
} // end class
