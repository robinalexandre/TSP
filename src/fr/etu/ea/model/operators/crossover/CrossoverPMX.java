package fr.etu.ea.model.operators.crossover;

import java.util.ArrayList;
import java.util.List;

import fr.etu.ea.model.Individu;
import fr.etu.ea.util.Utilities;

public class CrossoverPMX implements ICrossover {
	
	@Override
	public Individu[] crossover(Individu parent1, Individu parent2) {
		List<Integer> tour1 = new ArrayList<>(parent1.getVilles());
		List<Integer> tour2 = new ArrayList<>(parent2.getVilles());
		
	    final int size = tour1.size();

	    int number1 = Utilities.random.nextInt(size - 1);
	    int number2 = Utilities.random.nextInt(size);
	    
	    while(number1 == number2)
	    		number2 = Utilities.random.nextInt(size);

	    final int start = Math.min(number1, number2);
	    final int end = Math.max(number1, number2);

	    ICrossover.swap(tour1, tour2, start, end);

	    // get a view of the crossover over sections in each tour
	    final List<Integer> swappedSectionInTour1 = tour1.subList(start, end);
	    final List<Integer> swappedSectionInTour2 = tour2.subList(start, end);

	    Integer currentCity = new Integer(0);
	    int replacementCityIndex = 0;
	    Integer replacementCity = new Integer(0);

	    for (int i = end % size; i >= end || i < start; i = (i + 1) % size) {
	    		currentCity = tour1.get(i);
	    		if (swappedSectionInTour1.contains(currentCity)) {
	    			replacementCityIndex = swappedSectionInTour1.indexOf(currentCity);
	    			replacementCity = swappedSectionInTour2.get(replacementCityIndex);
			    while (swappedSectionInTour1.contains(replacementCity)) {
	    				replacementCityIndex = swappedSectionInTour1.indexOf(replacementCity);
			        replacementCity = swappedSectionInTour2.get(replacementCityIndex);
			    }
			    tour1.set(i, replacementCity);
		  }

	      currentCity = tour2.get(i);
	      if (swappedSectionInTour2.contains(currentCity)) {
	    	  	  replacementCityIndex = swappedSectionInTour2.indexOf(currentCity);
	    	  	  replacementCity = swappedSectionInTour1.get(replacementCityIndex);
	    	  	  while (swappedSectionInTour2.contains(replacementCity)) {
	    	  		  replacementCityIndex = swappedSectionInTour2.indexOf(replacementCity);
	    	  		  replacementCity = swappedSectionInTour1.get(replacementCityIndex);
	    	  	  }
	    	  	  tour2.set(i, replacementCity);
	      	}
	    }
	    Individu child1 = new Individu(tour1, parent1.getDistance());
	    Individu child2 = new Individu(tour2, parent2.getDistance());
	    Individu[] children = {child1, child2};
	    return children;
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
