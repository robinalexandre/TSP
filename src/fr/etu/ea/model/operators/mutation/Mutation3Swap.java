package fr.etu.ea.model.operators.mutation;

import java.util.ArrayList;
import java.util.List;

import fr.etu.ea.model.Individu;
import fr.etu.ea.util.Utilities;

public class Mutation3Swap implements IMutation {

	/** Permet la mutation (swap) de kSwap gènes dans cet individu
	 **/
	@Override
	public void mutate(Individu individu) {
		List<Integer> previousPosition = new ArrayList<>();
		List<Integer> previousPosition2 = new ArrayList<>();
		int size = individu.getVilles().size();
		for(int i = 0; i < 3; ++i) {
			int position = Utilities.random.nextInt(size);
			int position2 = Utilities.random.nextInt(size);
			while(previousPosition.contains(position) && previousPosition2.contains(position))
				position = Utilities.random.nextInt(size);
			while(previousPosition.contains(position2) && previousPosition2.contains(position2))
				position2 = Utilities.random.nextInt(size);
			previousPosition2.add(position2);
			int ville1 = individu.getVilles().get(position);
			individu.setVille(position, individu.getVilles().get(position2));
			individu.setVille(position2, ville1);
		}
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
