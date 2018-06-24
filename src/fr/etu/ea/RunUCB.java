package fr.etu.ea;

import java.util.Calendar;

import fr.etu.chart.Chart;
import fr.etu.ea.apprentissage.SelectionOperators;
import fr.etu.ea.apprentissage.SelectionUCB;
import fr.etu.ea.model.Population;
import fr.etu.ea.model.operators.ComputeAllFitness;
import fr.etu.ea.model.operators.IOperators;
import fr.etu.ea.model.operators.crossover.ICrossover;
import fr.etu.ea.model.operators.insertion.IInsertion;
import fr.etu.ea.model.operators.mutation.IMutation;
import fr.etu.ea.model.operators.selection.ISelection;
import fr.etu.ea.util.Utilities;

public class RunUCB implements Runnable {

	private Integer populationSize;
	private Integer probabilityMutation;
	private Integer probabilityCroisement;
	private int nbSelection;
	private float pmin;
	private float alpha;
	private int windowSize;
	private float scalingFactor;
	private float tolerance;
	private float threshold;
	ISelection[] selection;
	ICrossover[] crossover;
	IMutation[] mutation;
	IInsertion[] insertion;
	private int mutationSelected;
	private int crossoverSelected;
	private double[][] distance;
	private long duration;

	public RunUCB(Integer populationSize, Integer probabilityMutation, Integer probabilityCroisement, int nbSelection, float pmin, float alpha, int windowSize, float scalingFactor, float tolerance, float threshold, ISelection[] selection, ICrossover[] crossover, IMutation[] mutation, IInsertion[] insertion, double[][] distance, long duration) {
		super();
		this.populationSize = populationSize;
		this.probabilityMutation = probabilityMutation;
		this.probabilityCroisement = probabilityCroisement;
		this.nbSelection = nbSelection;
		this.pmin = pmin;
		this.alpha = alpha;
		this.windowSize = windowSize;
		this.scalingFactor = scalingFactor;
		this.tolerance = tolerance;
		this.threshold = threshold;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.insertion = insertion;
		this.distance = distance;
		this.duration = duration;
	}
	
	@Override
	public void run() {
		//Création du graphe
		Chart graph = new Chart("UCB");
//		Chart graphChoosen = new Chart();

		Population population = new Population(populationSize, this.distance);
		int iteration = 0;
		ComputeAllFitness fitness = new ComputeAllFitness(population, null, null);
		
		SelectionOperators selectionsOperatorsCrossover = new SelectionUCB((IOperators[])this.crossover, this.alpha, this.pmin, this.windowSize, this.scalingFactor, this.tolerance, this.threshold);
		SelectionOperators selectionsOperatorsMutation = new SelectionUCB((IOperators[])this.mutation, this.alpha, this.pmin, this.windowSize, this.scalingFactor, this.tolerance, this.threshold);
		
//		graph.addBestXY(iteration, fitness.getBestFitness());
//		graph.addMoyenneXY(iteration, fitness.getAverageFitness());
//		graph.addMinXY(iteration, fitness.getWorstFitness());
		
		long time = 0;
		long start = Calendar.getInstance().getTimeInMillis();
		while(time < start + this.duration) {
			time = Calendar.getInstance().getTimeInMillis();
			//System.out.println("------------ Iteration" + iteration + "--------------");
			//((SelectionUCB) selectionsOperatorsCrossover).checkDynamic();
			//((SelectionUCB) selectionsOperatorsMutation).checkDynamic();
			
			crossoverSelected = selectionsOperatorsCrossover.getChoosenOperator(population);
			mutationSelected = selectionsOperatorsMutation.getChoosenOperator(population);
			
			Population parents = this.selection[0].selection(population, nbSelection);
			fitness = new ComputeAllFitness(parents, null, null);
			selectionsOperatorsCrossover.setBestFitness(fitness.getBestFitness());
			
			Population enfants = this.crossover[crossoverSelected].crossoverAll(parents, probabilityCroisement);
			
			fitness = new ComputeAllFitness(enfants, null, null);
			selectionsOperatorsCrossover.addLastPerformance(fitness.getBestFitness(), crossoverSelected);
			selectionsOperatorsMutation.setBestFitness(fitness.getBestFitness());
			
			this.mutation[mutationSelected].mutationAll(enfants, probabilityMutation);
			
			fitness = new ComputeAllFitness(enfants, null, null);
			selectionsOperatorsMutation.addLastPerformance(fitness.getBestFitness(), mutationSelected);
			
			this.insertion[0].insert(population, enfants);
						
			fitness = new ComputeAllFitness(population, null, null);
			
			graph.addBestXY(iteration, fitness.getBestFitness());
			graph.addMinXY(iteration, fitness.getWorstFitness());
			graph.addMoyenneXY(iteration, fitness.getAverageFitness());
			

//			graphChoosen.addSeriesScramble5(iteration, selectionsOperatorsMutation.getChoosen()[0]);
//			graphChoosen.addSeriesSwap5(iteration, selectionsOperatorsMutation.getChoosen()[1]);
//			graphChoosen.addSeriesInversion5(iteration, selectionsOperatorsMutation.getChoosen()[2]);
//			graphChoosen.addSeriesMoveAfter5(iteration, selectionsOperatorsMutation.getChoosen()[3]);
//
//			graphChoosen.addSeriesScramble3(iteration, selectionsOperatorsMutation.getChoosen()[4]);
//			graphChoosen.addSeriesSwap3(iteration, selectionsOperatorsMutation.getChoosen()[5]);
//			graphChoosen.addSeriesInversion3(iteration, selectionsOperatorsMutation.getChoosen()[6]);
//			graphChoosen.addSeriesMoveAfter3(iteration, selectionsOperatorsMutation.getChoosen()[7]);
//
//			graphChoosen.addSeriesScramble1(iteration, selectionsOperatorsMutation.getChoosen()[8]);
//			graphChoosen.addSeriesSwap1(iteration, selectionsOperatorsMutation.getChoosen()[9]);
//			graphChoosen.addSeriesInversion1(iteration, selectionsOperatorsMutation.getChoosen()[10]);
//			graphChoosen.addSeriesMoveAfter1(iteration, selectionsOperatorsMutation.getChoosen()[11]);
			
			iteration++;
		}
		System.out.println("\n\nUCB------------Iteration "+ (iteration-1) +"------------");
		System.out.println(Utilities.checkTour(population.getIndividus()[fitness.getBestIndex()]));
		System.out.print("------------Best individu: "+ fitness.getBestIndex() +", Fitness: "+ fitness.getBestFitness() +"------------\nVilles: " + population.getIndividus()[fitness.getBestIndex()].toString());			
	}

}
