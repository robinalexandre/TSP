package fr.etu.ea.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.etu.ea.model.Individu;

public class Utilities {
	
	public static Random random = new Random();
	
	/**
	 *  Return true or false en fonction d'une probabilité "percent" du type 80 pour 80%
	 * @param percent
	 * @return un booleen
	 */
	public static boolean probabilite(Integer percent) {
		if(percent != null) {
			int i = random.nextInt(100);
	        if (i < percent) {
	            return true;
	        } else {
	            return false;
	        }
		} else
			return true;
	}
	
	public static boolean checkTour(Individu individu) {
		List<Integer> cities = new ArrayList<>();
		for(int city : individu.getVilles())
			if(!cities.contains(city))
				cities.add(city);
			else
				return false;
		return true;
	}
}
