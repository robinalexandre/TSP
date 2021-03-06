package fr.etu.ea.model.operators.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.etu.ea.model.Individu;
import fr.etu.ea.util.Utilities;

public class Mutation3Scramble implements IMutation {

	@Override
	public void mutate(Individu individu) {
		int position1 = Utilities.random.nextInt(individu.getVilles().size()-3);
		int position2 = position1+3;
		List<Integer> sublist = individu.getVilles().subList(position1, position2);
		Collections.shuffle(sublist);
		List<Integer> newList = new ArrayList<>(individu.getVilles().subList(0, position1));
		newList.addAll(sublist);
		newList.addAll(individu.getVilles().subList(position2, individu.getVilles().size()));
		individu.setVilles(new ArrayList<>(newList));
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
