package fr.etu.ea.model.operators.mutation;

import fr.etu.ea.model.Individu;
import fr.etu.ea.util.Utilities;

public class Mutation3MoveAfter implements IMutation {

	@Override
	public void mutate(Individu individu) {
		int taille = individu.getVilles().size();
		int position = Utilities.random.nextInt(taille);
		int position2 = Utilities.random.nextInt(taille);
		while(position2 == position)
			position2 = Utilities.random.nextInt(taille);
		if(position < position2) {
			for(int i = 0; i < 3; ++i) {
				if(position2+i >= taille) {
					break;
				}
				int ville = individu.getVilles().get(position2+i);
				individu.getVilles().remove(position2+i);
				individu.getVilles().add(position+i+1, ville);
			}
		} else {
			for(int i = 0; i < 3; ++i) {
				if(position+i >= taille) {
					break;
				}
				int ville = individu.getVilles().get(position+i);
				individu.getVilles().remove(position+i);
				individu.getVilles().add(position2+i+1, ville);
			}
		}
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
