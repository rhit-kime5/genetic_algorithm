package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Class: EvolutionComponent
 * @author Group R-202
 * <br>Purpose: Used as a component to draw evolution progress on the evolution viewer
 * <br>For example:
 * <pre>
 * 		EvolutionComponent comp = new EvolutionComponent(GA);
 * </pre>
 */
public class EvolutionComponent extends JComponent {
	
	
	// Fields
	public GeneticAlgorithm GA;
	
	
	// Constructor
	public EvolutionComponent(GeneticAlgorithm GA) {
		this.GA = GA;
	}
	
	
	// paintComponent method
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.BLACK);
		g2.drawRect(50, 50, 990, 400);
		
		// Draw the x axis lines
		int xAxis = 50;
		for (int i = 0; i < 10; i++) {
			g2.drawLine(xAxis, 440, xAxis, 460);
			xAxis = xAxis + 100;
		}
		g2.drawLine(1040, 440, 1040, 460);
		
		// Draw the y axis lines
		int yAxis = 50;
		for (int i = 0; i < 11; i++) {
			g2.drawLine(40, yAxis, 60, yAxis);
			yAxis = yAxis + 40;
		}
		
		Integer generationCount = GA.allGenerations.size();
		
		for (int i = 0; i < generationCount - 1; i++) {		
			Generation thisGen = GA.allGenerations.get(i);
			Generation nextGen = GA.allGenerations.get(i + 1);
			
			Integer ceilingScore = thisGen.population.get(0).length;
			Integer x1 = 50 + (1000 / generationCount) * i;
			Integer x2 = 50 + (1000 / generationCount) * (i + 1);
			Integer y1;
			Integer y2;
			g2.setStroke(new BasicStroke(3));
			Double scaleFactor = 400 / Double.parseDouble(ceilingScore.toString());
			
			
			y1 = (int) (450 - thisGen.getHighestFitness() * scaleFactor);
			y2 = (int) (450 - nextGen.getHighestFitness() * scaleFactor);
			g2.setColor(Color.BLUE);
			g2.drawLine(x1, y1, x2, y2);
			
			y1 = (int) (450 - thisGen.getLowestFitness() * scaleFactor);
			y2 = (int) (450 - nextGen.getLowestFitness() * scaleFactor);
			g2.setColor(Color.RED);
			g2.drawLine(x1, y1, x2, y2);
			
			y1 = (int) (450 - thisGen.getAverageFitness() * scaleFactor);
			y2 = (int) (450 - nextGen.getAverageFitness() * scaleFactor);
			g2.setColor(Color.GREEN);
			g2.drawLine(x1, y1, x2, y2);
			
			if (GA.displayDiversity) {
				y1 = (int) (450 - thisGen.getAverageHammingDistance() * scaleFactor);
				y2 = (int) (450 - nextGen.getAverageHammingDistance() * scaleFactor);
				g2.setColor(Color.ORANGE);
				g2.drawLine(x1, y1, x2, y2);
			}
			
			
			// Draw the x axis numbers
			g2.setFont(new Font("Times New Romans", Font.PLAIN, 11));
			int fontXLocation = 144;
			g2.setColor(Color.BLACK);
			for (int j = 0; j < 9; j++) {
				int value = (int) (generationCount * (0.1 + (0.1 * j)));
				g2.drawString("" + value, fontXLocation, 480);
				fontXLocation = fontXLocation + 100;
			}
			int value1 = (int) (generationCount);
			g2.drawString("" + value1, 1030, 480);
			
			// Draw the y axis numbers
			int fontYLocation = 415;
			g2.setColor(Color.BLACK);
			for (int k = 0; k < 10; k++) {
				int valueY = (int) (ceilingScore * (0.1 + (0.1 * k)));
				g2.drawString("" + valueY, 15, fontYLocation);
				fontYLocation = fontYLocation - 40;
			}
		}
		
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.BLUE);
		g2.fillRect(1070, 230, 15, 15);
		g2.setColor(Color.BLACK);
		g2.drawRect(1070, 230, 15, 15);
		g2.drawString("Highest fitness", 1090, 241);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(1070, 250, 15, 15);
		g2.setColor(Color.BLACK);
		g2.drawRect(1070, 250, 15, 15);
		g2.drawString("Average fitness", 1090, 262);
		
		g2.setColor(Color.RED);
		g2.fillRect(1070, 270, 15, 15);
		g2.setColor(Color.BLACK);
		g2.drawRect(1070, 270, 15, 15);
		g2.drawString("Lowest fitness", 1090, 283);
		
		g2.setColor(Color.ORANGE);
		g2.fillRect(1070, 290, 15, 15);
		g2.setColor(Color.BLACK);
		g2.drawRect(1070, 290, 15, 15);
		g2.drawString("Hamming Distance", 1090, 304);
	} // end method
	
	
} // end class
