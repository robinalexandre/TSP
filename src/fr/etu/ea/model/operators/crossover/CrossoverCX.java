package fr.etu.ea.model.operators.crossover;

import java.util.ArrayList;
import java.util.List;

import fr.etu.ea.model.Individu;
import fr.etu.ea.util.Utilities;

public class CrossoverCX implements ICrossover {

	@Override
	public Individu[] crossover(Individu parent1, Individu parent2) {
		Individu child1 = new Individu(parent1);
		Individu child2 = new Individu(parent1);
		List<Integer> tour1 = child1.getVilles();
		List<Integer> tour2 = child2.getVilles();
		
		List<Integer> cycleIndices = new ArrayList<Integer>();
	    int tour1index = Utilities.random.nextInt(tour1.size() - 1);
	    cycleIndices.add(tour1index);
	    
	    Integer tour2city = tour2.get(tour1index);
	    tour1index = tour1.indexOf(tour2city);
	    
	    while (tour1index != cycleIndices.get(0)) {
	      cycleIndices.add(tour1index);
	      tour2city = tour2.get(tour1index);
	      tour1index = tour1.indexOf(tour2city);
	    }

	    for (final int index : cycleIndices) {
	      ICrossover.swap(tour1, tour2, index, index+1);
	    }
	    Individu[] children = {child1, child2};
	    return children;
	   
	}

}
