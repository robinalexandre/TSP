package fr.etu.ea.model.operators.crossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.etu.ea.model.Individu;
import fr.etu.ea.util.Utilities;

public class CrossoverOX implements ICrossover {

	@Override
	public Individu[] crossover(Individu parent1, Individu parent2) {
		List<Integer> tour1 = parent1.getVilles();
		List<Integer> tour2 = parent2.getVilles();

		// get the size of the tours
	    final int size = tour1.size();

	    // choose two random numbers for the start and end indices of the slice
	    // (one can be at index "size")
	    final int number1 = Utilities.random.nextInt(size - 1);
	    final int number2 = Utilities.random.nextInt(size);

	    // make the smaller the start and the larger the end
	    final int start = Math.min(number1, number2);
	    final int end = Math.max(number1, number2);

	    // instantiate two child tours
	    final List<Integer> child1 = new ArrayList<>();
	    final List<Integer> child2 = new ArrayList<>();

	    // add the sublist in between the start and end points to the children
	    child1.addAll(tour1.subList(start, end));
	    child2.addAll(tour2.subList(start, end));

	    // iterate over each city in the parent tours
	    int currentCityIndex = 0;
	    Integer currentCityInTour1 = null;
	    Integer currentCityInTour2 = null;
	    for (int i = 0; i < size; ++i) {

	      // get the index of the current city
	      currentCityIndex = (end + i) % size;

	      // get the city at the current index in each of the two parent tours
	      currentCityInTour1 = tour1.get(currentCityIndex);
	      currentCityInTour2 = tour2.get(currentCityIndex);

	      // if child 1 does not already contain the current city in tour 2, add it
	      // Note: Integers override equals() so .contains() works
	      if (!child1.contains(currentCityInTour2)) {
	        child1.add(currentCityInTour2);
	      }

	      // if child 2 does not already contain the current city in tour 1, add it
	      // Note: Integers override equals() so .contains() works
	      if (!child2.contains(currentCityInTour1)) {
	        child2.add(currentCityInTour1);
	      }
	    }

	    // rotate the lists so the original slice is in the same place as in the
	    // parent tours
	    Collections.rotate(child1, start);
	    Collections.rotate(child2, start);

	    Individu c1 = new Individu(child1, parent1.getDistance());
		Individu c2 = new Individu(child2, parent1.getDistance());
		Individu[] children = {c1, c2};
		return children;
	}

}
