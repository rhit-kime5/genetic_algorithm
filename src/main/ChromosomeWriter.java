package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class: ChromosomeWriter
 * @author Group R-202
 * <br>Purpose: Used to write a chromosome's data to a file (overwrite an existing file or create a new file)
 * <br>For example:
 * <pre>
 * 		ChromosomeWriter writer = new ChromosomeWriter();
 * 		writer.writeFile("ChromosomeFiles/testChromosome2.txt", genes, false);
 * </pre>
 */
public class ChromosomeWriter {

	
	// writeFile method
	public void writeFile(String filename, String genes, boolean createNewFile) {

		PrintWriter writer;
		
		try {
			if (createNewFile) {
				
				File file = new File("ChromosomeFiles/" + filename + ".txt");
				
				try {
					file.createNewFile();
				} catch (IOException e) {
					System.err.println("File not created");
				}
				
				writer = new PrintWriter(file);	
				writer.print(genes);
				file.isDirectory();
			} else {
				writer = new PrintWriter(filename);
				writer.flush();
				writer.print(genes);
			}
			
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			return;
		}
		writer.close();
	} // end method
	
	
} // end class
