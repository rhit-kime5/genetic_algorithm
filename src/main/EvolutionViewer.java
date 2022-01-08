package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Class: EvolutionViewer
 * @author Group R-202
 * <br>Purpose: Used to view evolution progress, and for the user to control all options and features of the program
 * <br>For example:
 * <pre>
 * 		new EvolutionViewer(targetChromosome);
 * </pre>
 */
public class EvolutionViewer {
	
	
	// Constants
	public static final Dimension EVOLUTION_VIEWER_SIZE = new Dimension(1230, 665);
	public static final Dimension TEXT_FIELD_SIZE = new Dimension(35, 27);
	
	
	// Fields
	public Integer terminationCount = 99; // will create 99 new "next generations", ending at Generation 100
	public Integer populationSize = 100;
	public Integer chromosomeLength = 100;
	public Integer mutationRate = 1;
	public GeneticAlgorithm GA = new GeneticAlgorithm();
	public EvolutionComponent comp;
	public Integer viewingGeneration = 1;
	public Integer startClickCount = 0;
	public Integer evolutionDelay = 20;
	public ChromosomeViewer viewer;
	public GenerationViewer genViewer;
	public Integer resetClickCount = 0;
	public Integer terminationScore = 100;
	
	
	// Constructor
	public EvolutionViewer(Chromosome target) {
		this.GA.target = target;
		
		JFrame frame = new JFrame();
		frame.setSize(EVOLUTION_VIEWER_SIZE);
		frame.setTitle("Evolution Viewer");
		
		JPanel optionsPanel = new JPanel();
		frame.add(optionsPanel);
		optionsPanel.setBounds(0, 490, 1210, 35);
		
		JPanel buttonsPanel = new JPanel();
		frame.add(buttonsPanel);
		buttonsPanel.setBounds(0, 525, 1210, 35);
		
		JPanel buttonsPanel2 = new JPanel();
		frame.add(buttonsPanel2);
		buttonsPanel2.setBounds(0, 560, 1210, 35);
		
		comp = new EvolutionComponent(this.GA);
		frame.add(comp);
		
		JLabel displayLabel = new JLabel();
		optionsPanel.add(displayLabel);
		displayLabel.setText("Display: ");
		
		JCheckBox diversityInput = new JCheckBox("Diversity", true);
		optionsPanel.add(diversityInput);
		
		class DiversityListener implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				GA.displayDiversity = (e.getStateChange() == 1);				
			} // end method
		} // end inner class
		diversityInput.addItemListener(new DiversityListener());
		
		JCheckBox chromosomeViewerInput = new JCheckBox("Best Individual", true);
		optionsPanel.add(chromosomeViewerInput);
		
		class ChromosomeViewerListener implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				if (viewer == null) {
					return;
				} else {
					viewer.frame.setVisible(e.getStateChange() == 1);
				}
			} // end method
		} // end inner class
		chromosomeViewerInput.addItemListener(new ChromosomeViewerListener());
		
		JCheckBox generationViewerInput = new JCheckBox("Population", true);
		optionsPanel.add(generationViewerInput);
		
		class GenerationViewerListener implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				if (genViewer == null) {
					return;
				}
				
				genViewer.frame.setVisible(e.getStateChange() == 1);
			} // end method
		} // end inner class
		generationViewerInput.addItemListener(new GenerationViewerListener());
				
		JLabel generationsLabel = new JLabel();
		buttonsPanel.add(generationsLabel);
		generationsLabel.setText("Generations ");
		
		Integer generationCount = this.terminationCount + 1;
		JTextField generationsInput = new JTextField(generationCount.toString());
		generationsInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(generationsInput);
		
		class GenerationsListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				terminationCount = Integer.parseInt(generationsInput.getText()) - 1;
			} // end method
		} // end inner class
		generationsInput.addActionListener(new GenerationsListener());
		
		JLabel populationLabel = new JLabel();
		buttonsPanel.add(populationLabel);
		populationLabel.setText("Population Size ");
		
		JTextField populationInput = new JTextField(this.populationSize.toString());
		populationInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(populationInput);
		
		class PopulationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				populationSize = Integer.parseInt(populationInput.getText());
			} // end method
		} // end inner class
		populationInput.addActionListener(new PopulationListener());
	
		JLabel lengthLabel = new JLabel();
		buttonsPanel.add(lengthLabel);
		lengthLabel.setText("Genome Length ");
		
		JTextField lengthInput = new JTextField(this.chromosomeLength.toString());
		lengthInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(lengthInput);
		
		class LengthListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				chromosomeLength = Integer.parseInt(lengthInput.getText());
			} // end method
		} // end inner class
		lengthInput.addActionListener(new LengthListener());
		
		JLabel rateLabel = new JLabel();
		buttonsPanel.add(rateLabel);
		rateLabel.setText("Mutation Rate");
		
		JTextField rateInput = new JTextField(this.mutationRate.toString());
		rateInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(rateInput);
		
		class RateListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				mutationRate = Integer.parseInt(rateInput.getText());
			} // end method
		} // end inner class
		rateInput.addActionListener(new RateListener());
		
		JLabel delayLabel = new JLabel();
		buttonsPanel.add(delayLabel);
		delayLabel.setText("Evolution Delay (ms) ");
		
		JTextField delayInput = new JTextField(this.evolutionDelay.toString());
		delayInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(delayInput);
		
		JLabel terminationLabel = new JLabel();
		buttonsPanel.add(terminationLabel);
		terminationLabel.setText("Termination Score ");
		
		JTextField terminationInput = new JTextField(this.terminationScore.toString());
		terminationInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel.add(terminationInput);
		
		class TerminationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				terminationScore = Integer.parseInt(terminationInput.getText());
			} // end method
		} // end inner class
		terminationInput.addActionListener(new TerminationListener());
		
		JLabel fitnessLabel = new JLabel();
		buttonsPanel2.add(fitnessLabel);
		fitnessLabel.setText("Fitness Type ");
		
		String[] fitnessTypes = {"matchTarget", "countNumOf1s", "countNumOf0s", "maxConsec1s", "maxConsec0s"};
		JComboBox fitnessInput = new JComboBox(fitnessTypes);
		buttonsPanel2.add(fitnessInput);
		
		class FitnessListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GA.fitnessType = (String) ((JComboBox) e.getSource()).getSelectedItem();
			} // end method
		} // end inner class
		fitnessInput.addActionListener(new FitnessListener());
		
		JLabel selectionLabel = new JLabel();
		buttonsPanel2.add(selectionLabel);
		selectionLabel.setText("Selection Type ");
		
		String[] selectionTypes = {"Truncation", "Roulette Wheel"};
		JComboBox selectionInput = new JComboBox(selectionTypes);
		buttonsPanel2.add(selectionInput);
		
		class SelectionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GA.selectionType = (String) ((JComboBox) e.getSource()).getSelectedItem();
			} // end method
		} // end inner class
		selectionInput.addActionListener(new SelectionListener());
		
		JLabel elitismLabel = new JLabel();
		buttonsPanel2.add(elitismLabel);
		elitismLabel.setText("Elitism (%) ");
		
		JTextField elitismInput = new JTextField(this.GA.elitismPercent.toString());
		elitismInput.setPreferredSize(TEXT_FIELD_SIZE);
		buttonsPanel2.add(elitismInput);
		
		class ElitismListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				GA.elitismPercent = Integer.parseInt(elitismInput.getText());
			} // end method
		} // end inner class
		elitismInput.addActionListener(new ElitismListener());
		
		JLabel crossoverLabel = new JLabel();
		buttonsPanel2.add(crossoverLabel);
		crossoverLabel.setText("Crossover");
		
		JCheckBox crossoverInput = new JCheckBox("", false);
		buttonsPanel2.add(crossoverInput);
		
		class CrossoverListener implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				GA.crossover = (e.getStateChange() == 1);				
			} // end method
		} // end inner class
		crossoverInput.addItemListener(new CrossoverListener());
		
		JButton startButton = new JButton("Start/Pause Evolution");
		buttonsPanel2.add(startButton);
		
		class ContinueEvolutionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (GA.allGenerations.get(viewingGeneration - 1).getHighestFitness() >= terminationScore) {
					return;
				}
				
				if (GA.allGenerations.size() < terminationCount + 1) {
					viewingGeneration++;
					GA.nextGeneration(viewingGeneration, mutationRate);
					comp.repaint();
					
					genViewer.generationToView = GA.allGenerations.get(viewingGeneration - 1);
					genViewer.comp.generationToDraw = genViewer.generationToView;
					genViewer.comp.repaint();
					
					Generation gen = GA.allGenerations.get(viewingGeneration - 1);
					for (Chromosome c : gen.population) {
						Integer highestScore = gen.getHighestFitness();
						
						if (c.evaluateFitness(GA.fitnessType, GA.target) == highestScore) {
							viewer.chromosomeToView = c;
							viewer.comp.chromosomeToDraw = viewer.chromosomeToView;
							viewer.score = c.evaluateFitness(GA.fitnessType, GA.target);
							viewer.frame.setTitle("Fittest chromosome in population, score: " + viewer.score);
							viewer.comp.repaint();
						} // end if statement
					} // end for loop
				} // end if statement
			} // end method
		} // end inner class
		
		Timer timer = new Timer(this.evolutionDelay, new ContinueEvolutionListener());
		if (GA.allGenerations.size() == terminationCount + 1) {
			timer.stop();
		}		
		
		class DelayListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				evolutionDelay = Integer.parseInt(delayInput.getText());
				timer.setDelay(evolutionDelay);
			} // end method
		} // end inner class
		delayInput.addActionListener(new DelayListener());
		
		class StartListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				startClickCount++;
				if (startClickCount == 1) { 			// very first click (i.e. "start")
					GA.allGenerations.clear();
					GA.createGeneration(1, populationSize, chromosomeLength);
					viewingGeneration = 1;
					timer.start();
					
					if (resetClickCount == 0) {
						genViewer = new GenerationViewer(GA.allGenerations.get(viewingGeneration - 1));
					}
					
					Generation gen1 = GA.allGenerations.get(0);
					for (Chromosome c : gen1.population) {
						Integer highestScore = gen1.getHighestFitness();
						
						if (c.evaluateFitness(GA.fitnessType, GA.target) == highestScore && resetClickCount == 0) {
							viewer = new ChromosomeViewer(c, c.evaluateFitness(GA.fitnessType, GA.target));
						} // end if statement
					} // end for loop
					
				} else if (startClickCount % 2 == 0) {	// "pause"
					timer.stop();
				} else {								// "continue"
					timer.start();
				}	
			} // end method
		} // end inner class
		startButton.addActionListener(new StartListener());
		
		JButton resetButton = new JButton("Reset");
		buttonsPanel2.add(resetButton);
		
		class ResetListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				resetClickCount++;
				timer.stop();
				viewingGeneration = 0;
				startClickCount = 0;
				GA.allGenerations.clear();
				comp.repaint();
				
				viewer.score = 0;
				viewer.chromosomeToView = null;
				viewer.comp.chromosomeToDraw = viewer.chromosomeToView;
				viewer.comp.repaint();
				
				genViewer.generationToView = null;
				genViewer.comp.generationToDraw = genViewer.generationToView;
				genViewer.comp.repaint();
				
				System.out.println("________________________________________evolution_reset");
				for (int i = 0; i < 100; ++i) System.out.println();
				System.out.println("________________________________________evolution_reset");
				System.out.println();
			}
		}
		resetButton.addActionListener(new ResetListener());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // end constructor
	
	
} // end method
