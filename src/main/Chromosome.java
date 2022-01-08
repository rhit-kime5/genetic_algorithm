package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class: Chromosome
 * @author Group R-202
 * <br>Purpose: Used to represent a single-chromosome organism, with a sequence of genes
 * <br>For example:
 * <pre>
 * 		Chromosome lactobacillus = new Chromosome(1800000);
 * 		Chromosome escherichiaColi = new Chromosome(E.ColiGenes);
 * 		Chromosome testChromosome2 = new Chromosome(genes, "ChromosomeFiles/testChromosome2.txt");
 * </pre>
 */
public class Chromosome {

	
	// Fields
	public ArrayList<Integer> genes = new ArrayList<Integer>();
	public String fromFile;
	public Integer length;	
	
	
	// Constructor (gene sequence passed)
	public Chromosome(ArrayList<Integer> genes) {
		
		for (int i = 0; i < genes.size(); i++) {
			this.genes.add(genes.get(i));
		}
		
		this.length = this.genes.size();
	} // end constructor
	
	
	// Constructor (random gene generation)
	public Chromosome(Integer length) {
		this.length = length;

		Random randomizer = new Random();
		for (int i = 0; i < length; i++) {
			this.genes.add(randomizer.nextInt(2));
		}
	} // end constructor
	

	// Constructor (read from file)
	public Chromosome(ArrayList<Integer> genes, String fromFile) {
		this.genes = genes;
		this.fromFile = fromFile;
		this.length = genes.size();
	} // end constructor

	
	// mutate method
	public void mutate(Integer mutationRate) {
		
		// Construct Random object to use
		Random randomizer = new Random();

		// Randomly change genes
		for (int i = 0; i < this.length; i++) {
			
			if (mutationRate == 0) {
				continue;
				
			} else if (randomizer.nextDouble() <= 1.0 * (new Double(mutationRate) / this.length)) { 
				
				if (this.genes.get(i) == 0) { 	// if gene is 0,
					this.genes.remove(i); 		// remove the gene at index i,
					this.genes.add(i, 1); 		// and add new gene at same index, as 1
					
				} else {
					this.genes.remove(i);
					this.genes.add(i, 0);
				} // end if statement
				
			} // end if statement
		} // end for loop
	} // end method
	
	
	// evaluateFitness method
	public Integer evaluateFitness(String type, Chromosome target) {
		
		if (type.equals("countNumOf1s")) {
			return this.countGene(1);
			
		} else if (type.equals("countNumOf0s")) {
			return this.countGene(0);
			
		} else if (type.equals("matchTarget")) {
			return this.matchTarget(target);
			
		} else if (type.equals("maxConsec1s")) {
			return this.countMaxConsecGene(1);
			
		} else if (type.equals("maxConsec0s")) {
			return this.countMaxConsecGene(0);
			
		} else {
			return 0;
		}
		
	} // end method
	
	
	// countGene method
	public Integer countGene(Integer gene) {
		Integer fitnessScore = 0;
		
		for (int i = 0; i < this.length; i++) {
			if (this.genes.get(i) == gene) {
				fitnessScore++;
			}
		}
		return fitnessScore;
	} // end method
	
	
	// matchTarget method
	public Integer matchTarget(Chromosome target) {
		Integer fitnessScore = 0;
		
		for (int i = 0; i < this.genes.size(); i++) {
			if (this.genes.get(i) == target.genes.get(i)) {
				fitnessScore++;
			}
		}
		return fitnessScore;
	} // end method
	
	
	// countMaxConsecGene method
	public Integer countMaxConsecGene(Integer gene) {
		Integer maxConsecGene = 0;
		Integer currConsecGene = 0;
		Integer count = 0;
		
		for (int i = 0; i < this.genes.size() - 1; i++) {	
			if (this.genes.get(i) != gene) {
				count = 0;
				currConsecGene = 0;
			} // end if statement
			
			if (this.genes.get(i) == gene && this.genes.get(i + 1) == gene) {
				count++;
				
				if (count == 1) {
					currConsecGene += 2;
				} else {
					currConsecGene++;
				} // end if statement
				
				if (currConsecGene > maxConsecGene) {
					maxConsecGene = currConsecGene;
				} // end if statement
				
			} // end if statement
			
		} // end for loop
		return maxConsecGene;
	} // end method
	

} // end class