package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: ChromosomeReader
 * @author Group R-202
 * <br>Purpose: Used to read a gene sequence from file and construct a chromosome
 * <br>For example:
 * <pre>
 * 		ChromosomeReader reader = new ChromosomeReader();
 *		reader.readFile("ChromosomeFiles/testChromosome2.txt");
 * </pre>
 */
public class ChromosomeReader {
	
	
	// Fields
	public ArrayList<Chromosome> chromosomesRead = new ArrayList<Chromosome>();
	
	
	// readFile method
	public void readFile(String filename) {
		
		// Declare scanner
		Scanner scanner;
		
		try {
			scanner = new Scanner(new File(filename)); 	// Scan file, and if file not found,
		} catch (FileNotFoundException e) {
			System.err.println("File not found");		// display error message on console
			return;
		}
		
		// Construct ArrayList to hold genes data
		ArrayList<Integer> genes = new ArrayList<>();
		
		// Get the entire gene sequence as a string
		String genesString = scanner.next();
		
		// Iterate through each character in the string to read each gene
		for (int i = 0; i < genesString.length(); i++) {
			Character geneChar = genesString.charAt(i);
			genes.add(Character.getNumericValue(geneChar));
		} // end for loop
		
		// Construct a new chromosome with the completed gene sequence
		Chromosome thisChromosome = new Chromosome(genes, filename);
		
		// Add the new chromosome to the list of chromosomes that has been read
		chromosomesRead.add(thisChromosome);
		
		// Notify the file has been read
		System.out.println(filename.substring(16, 35) + " has been read: ");
		System.out.println(filename.substring(16, 31) + " has been born");
		System.out.println();
		
		// Close scanner
		scanner.close();
	} // end method
	
	
	// getChromosomeFrom method
	public Chromosome getChromosomeFrom(String filename) {
		readFile(filename);
		return chromosomesRead.get(chromosomesRead.size() - 1);
	} // end method
	
	
} // end class
