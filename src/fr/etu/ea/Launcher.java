package fr.etu.ea;

import fr.etu.ea.model.operators.crossover.CrossoverCX;
import fr.etu.ea.model.operators.crossover.CrossoverERX;
import fr.etu.ea.model.operators.crossover.CrossoverOX;
import fr.etu.ea.model.operators.crossover.CrossoverPMX;
import fr.etu.ea.model.operators.crossover.ICrossover;
import fr.etu.ea.model.operators.insertion.IInsertion;
import fr.etu.ea.model.operators.insertion.InsertionFitness;
import fr.etu.ea.model.operators.mutation.IMutation;
import fr.etu.ea.model.operators.mutation.Mutation1Inversion;
import fr.etu.ea.model.operators.mutation.Mutation1MoveAfter;
import fr.etu.ea.model.operators.mutation.Mutation1Scramble;
import fr.etu.ea.model.operators.mutation.Mutation1Swap;
import fr.etu.ea.model.operators.mutation.Mutation3Inversion;
import fr.etu.ea.model.operators.mutation.Mutation3MoveAfter;
import fr.etu.ea.model.operators.mutation.Mutation3Scramble;
import fr.etu.ea.model.operators.mutation.Mutation3Swap;
import fr.etu.ea.model.operators.mutation.Mutation5Inversion;
import fr.etu.ea.model.operators.mutation.Mutation5MoveAfter;
import fr.etu.ea.model.operators.mutation.Mutation5Scramble;
import fr.etu.ea.model.operators.mutation.Mutation5Swap;
import fr.etu.ea.model.operators.selection.ISelection;
import fr.etu.ea.model.operators.selection.SelectionTournoi;
import fr.etu.ea.util.Reader;

public class Launcher {

	public static void main(String[] args) {
		///Initialisation des valeurs
		Integer populationSize = 50;
				
		int probabilityMutation = 100; //Probability in %
		int probabilityCroisement = 100;
		
		int nbSelection = 2; //Nombre pair d'individu selectionnés
		
		int windowSize = 100;
		float pmin = (float) 0.1;
		float alpha = (float) 0.3;
		float beta = (float) 0.3;
		float scalingFactor = (float) 0.6;
		float tolerance = (float) 0.15;
		float threshold = (float) 5;
		
		long duration = 120 * 1000;
		
		double distance[][] = Reader.askATSP();
		
		ISelection[] selection = {new SelectionTournoi()};
		ICrossover[] crossover = {new CrossoverOX(), new CrossoverERX(), new CrossoverPMX(), new CrossoverCX()};
		IMutation[] mutation = {new Mutation5Scramble(), new Mutation5Swap(), new Mutation5Inversion(), new Mutation5MoveAfter(), new Mutation3Scramble(), new Mutation3Swap(), new Mutation3Inversion(), new Mutation3MoveAfter(), new Mutation1Scramble(), new Mutation1Swap(), new Mutation1Inversion(), new Mutation1MoveAfter()};
		IInsertion[] insertion = {new InsertionFitness()};
				
		Thread thread = new Thread(new RunGeneticAlgorithm(populationSize, probabilityMutation, nbSelection, probabilityCroisement, (ISelection)new SelectionTournoi(), (IMutation)new Mutation5Scramble(), (ICrossover)new CrossoverOX(), (IInsertion) new InsertionFitness(), distance, duration));
		thread.start();
		
		Thread thread2 = new Thread(new RunAdaptiveRouletteWheel(populationSize, probabilityMutation, probabilityCroisement, nbSelection, pmin, alpha, windowSize, selection, crossover, mutation, insertion, distance, duration));
		thread2.start();
		
		Thread thread3 = new Thread(new RunAdaptivePursuit(populationSize, probabilityMutation, probabilityCroisement, nbSelection, pmin, beta, alpha, windowSize, selection, crossover, mutation, insertion, distance, duration));
		thread3.start();
		
		Thread thread4 = new Thread(new RunUCB(populationSize, probabilityMutation, probabilityCroisement, nbSelection, pmin, alpha, windowSize, scalingFactor, tolerance, threshold, selection, crossover, mutation, insertion, distance, duration));
		thread4.start();
		
	}
}
