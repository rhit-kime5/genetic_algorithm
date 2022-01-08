package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class: ChromosomeViewer
 * @author Group R-202
 * <br>Purpose: Used to view a given chromosome and allow the user to edit, mutate, save, and load the chromosome
 * <br>For example:
 * <pre>
 * 		ChromosomeViewer viewer = new ChromosomeViewer(lactobacillus, lactoScore);
 * </pre>
 */
public class ChromosomeViewer {
	
	
	// Constants
	public static final Dimension CHROMOSOME_VIEWER_SIZE = new Dimension(540, 630);
	public static final Dimension TEXT_FIELD_SIZE = new Dimension(30, 27);
	
	
	// Fields
	public Integer mutationRate;
	public ChromosomeComponent comp;
	public Chromosome chromosomeToView;
	public JFrame frame;
	public Integer score;
	

	// Constructor
	public ChromosomeViewer(Chromosome chromosomeInput, Integer score) {
		this.chromosomeToView = chromosomeInput;
		this.score = score;
		
		// Construct frame, set size and title based on the chromosome's name
		frame = new JFrame();
		frame.setSize(CHROMOSOME_VIEWER_SIZE);
		
		if (this.chromosomeToView.fromFile == null) {
			frame.setTitle("Fittest chromosome in population, score: " + this.score);
		} else {
			frame.setTitle(this.chromosomeToView.fromFile.substring(16, 31));
		}

		// Construct panel for buttons and add to the bottom of screen
		JPanel buttonsPanel = new JPanel();
		frame.add(buttonsPanel);
		buttonsPanel.setBounds(0, 510, 540, 35);
		
		JPanel buttonsPanel2 = new JPanel();
		frame.add(buttonsPanel2);
		buttonsPanel2.setBounds(0, 545, 540, 35);
		
		// Construct new component for the given chromosome and add to frame
		comp = new ChromosomeComponent(this.chromosomeToView);
		frame.add(comp);
		
		// Construct button for mutation and add to panel
		JButton mutateButton = new JButton("Mutate");
		buttonsPanel.add(mutateButton);

		// Construct label for the left side of the displayed text and add to panel
		JLabel leftLabel = new JLabel();
		buttonsPanel.add(leftLabel);
		leftLabel.setText("          Mutation Rate = (");
		
		// Set default mutation rate to 1.0 gene mutated per click
		this.mutationRate = 1;

		// Construct text field for user to specify mutation rate, set size and add to panel 
		JTextField mutationRateInput = new JTextField(this.mutationRate.toString());
		mutationRateInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(mutationRateInput);

		// Construct label for the right side of the displayed text and add to panel
		JLabel rightLabel = new JLabel();
		buttonsPanel.add(rightLabel);
		rightLabel.setText(" / Chromosome Length)");
		
		JButton loadButton = new JButton("Load");
		buttonsPanel2.add(loadButton);
		
		JButton saveButton = new JButton("Save");
		buttonsPanel2.add(saveButton);
		
		class LoadListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String filename = "ChromosomeFiles/" + JOptionPane.showInputDialog("Type filename from the ChromosomeFiles folder:");
				chromosomeToView = new ChromosomeReader().getChromosomeFrom(filename);				
				comp.chromosomeToDraw = chromosomeToView;
				comp.repaint();
			} // end method
		} // end inner class
		loadButton.addActionListener(new LoadListener());
		
		
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				//
				Double sqrt = Math.sqrt(chromosomeToView.length);
				Integer sideLength = (int) Math.ceil(sqrt);
				
				//
				int width = 500 / sideLength;
				int height = 500 / sideLength;
				
				Integer x = e.getX() - 20;
				Integer y = e.getY();
				Integer geneIndex = (sideLength * (y / width) + (x / width) - sideLength);
				
				Integer geneToChange = chromosomeToView.genes.get(geneIndex);
				if (geneToChange == 0) {
					chromosomeToView.genes.add(geneIndex, 1);
					chromosomeToView.genes.remove(geneIndex + 1);
				} else {
					chromosomeToView.genes.add(geneIndex, 0);
					chromosomeToView.genes.remove(geneIndex + 1);
				}
				comp.repaint();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
	
		// Inner class for receiving user input on mutation rate
		// 		*comes before MutateListener (below) as it needs to receive input on the 
		//		 mutation rate, before the mutate button is clicked and mutation is executed
		class MutationRateListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				mutationRate = Integer.parseInt(mutationRateInput.getText());
			} // end method
		} // end inner class
		mutationRateInput.addActionListener(new MutationRateListener());

		// Inner class for receiving click on mutation button
		class MutateListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {				
				chromosomeToView.mutate(mutationRate);	// change the chromosome's data,
				comp.repaint();							// and repaint the component
			} // end method
		} // end inner class
		mutateButton.addActionListener(new MutateListener());
		
		class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String genes = "";
				
				for (Integer gene : chromosomeToView.genes) {
					genes += gene;
				}
				
				String command = JOptionPane.showInputDialog("Type 'overwrite' or 'new file': ");
				boolean createNewFile = (command.equals("new file"));
				
				if (createNewFile) {
					String newFileName = JOptionPane.showInputDialog("Type name of new file:	(exclude .txt)");
					new ChromosomeWriter().writeFile(newFileName, genes, createNewFile);
				} else {
					new ChromosomeWriter().writeFile(chromosomeToView.fromFile, genes, createNewFile);
				}
			} // end method
		} // end inner class
		saveButton.addActionListener(new SaveListener());

		// Set close operation for frame and set visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // end constructor


} // end class