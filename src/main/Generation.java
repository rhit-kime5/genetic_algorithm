package main;

import java.util.ArrayList;

/**
 * Class: Generation
 * @author Group R-202
 * <br>Purpose: Used to represent a specific generation of the population during evolution
 * <br>For example:
 * <pre>
 * 		Generation secondGen = new Generation(2, 100, 100, "matchTarget", targetChromosome);
 * </pre>
 */
public class Generation {
	
	
	// Fields
	public ArrayList<Chromosome> population = new ArrayList<Chromosome>();
	public Integer genNumber;
	public Integer populationSize;
	public Integer chromosomeLength;
	public String fitnessType;
	public Chromosome target;
	
	
	// Constructor
	public Generation(Integer genNumber, Integer populationSize, Integer chromosomeLength, String fitnessType, Chromosome target) {
		this.genNumber = genNumber;
		this.populationSize = populationSize;
		this.chromosomeLength = chromosomeLength;
		this.fitnessType = fitnessType;
		this.target = target;
		
		for (int i = 0; i < populationSize; i++) {
			this.population.add(new Chromosome(chromosomeLength));
		} // end for loop
	} // end constructor
	
	
	// getAverageHammingDistance method
	public Integer getAverageHammingDistance() {
		
		Integer hammingDistanceSum = 0;
		Integer comparisonCount = 0;
		
		for (int i = 0; i < this.populationSize; i++) {				// get chromosome
			Chromosome c = this.population.get(i);
			
			for (int j = i + 1; j < this.populationSize; j++) {		// get another chromosome to compare it to
				Chromosome comparison = this.population.get(j);
				
				comparisonCount++;
				
				for (int k = 0; k < c.length; k++) {				// go through all genes
					Integer geneOfC = c.genes.get(k);
					Integer geneOfComparison = comparison.genes.get(k);
					
					if (geneOfC != geneOfComparison) {
						hammingDistanceSum++;
					} // end if statement
				} // end for loop
			} // end for loop
		} // end for loop
		return hammingDistanceSum / comparisonCount;		
	} // end method
	
	
	// getHighestFitness method
	public Integer getHighestFitness() {
		Integer highestScore = 0;
		
		for (Chromosome c : this.population) {
			if (c.evaluateFitness(fitnessType, target) > highestScore) {
				highestScore = c.evaluateFitness(fitnessType, target);
			} // end if statement
		} // end for loop
		return highestScore;
	} // end method
	
	
	// getLowestFitness method
	public Integer getLowestFitness() {
		Integer lowestScore = 100;
		
		for (Chromosome c : this.population) {
			if (c.evaluateFitness(fitnessType, target) < lowestScore) {
				lowestScore = c.evaluateFitness(fitnessType, target);
			} // end if statement
		} // end for loop
		return lowestScore;
	} // end method
	
	
	// getAverageFitness method	
	public Integer getAverageFitness() {
		Integer scoreSum = 0;
		
		for (Chromosome c : this.population) {
			scoreSum += c.evaluateFitness(fitnessType, target);
		}
		return scoreSum / this.populationSize;
	} // end method
	
	
} // end class
