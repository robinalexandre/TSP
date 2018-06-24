package fr.etu.ea.model;

import java.util.List;

public class Population {
	
	Individu[] individus;
	private double[][] distance;
	
	/**
	 * Initialise la population avec une taille populationSize
	 * @param populationSize
	 */
	public Population(Integer populationSize, double[][] distance) {
		this.distance = distance;
		this.individus = new Individu[populationSize];
		for(int i = 0; i < populationSize; ++i) {
			this.individus[i] = new Individu(distance);
		}
	}
	
	public Population(Population population) {
		this.distance = population.distance;
		this.individus = new Individu[population.getTaille()];
		for(int i = 0; i < population.getTaille(); ++i) {
			this.individus[i] = new Individu(population.getIndividus()[i]);
		}
	}

	public Individu[] getIndividus() {
		return this.individus;
	}
	
	public void setIndividus(Individu[] population) {
		this.individus = population;
	}
	
	public int getTaille() {
		return this.individus.length;
	}
	
	public String toString() {
		String pop = "";
		for(int i = 0; i < this.getTaille(); i++) {
			pop += "Individu: " + i + "\n" + individus[i].toString() + "\n";
		}
		return pop;
	}
	
	/**
	 * Renvoie l'index de l'individu (sauf ceux dans "index") ayant le plus vieil age dans la population
	 * @param index à ne pas prendre en compte
	 * @return index du plus vieil individu
	 */
	public int getOlderIndex(List<Integer> index) {
		int indexOlder = -1;
		int valueOlder = -1;
		for(int i = 0; i < this.getIndividus().length; i++) {
			int age = this.getIndividus()[i].getAge();
			if(valueOlder < age && !index.contains(i)) {
				indexOlder = i;
				valueOlder = age;
			}
		}
		return indexOlder;
	}
	
	/**
	 * Permet de faire vieillir la population en ajoutant 1 à l'age de chaque individu
	 */
	public void getOld() {
		for(int i = 0; i < this.getIndividus().length; i++) {
			this.getIndividus()[i].setAge(this.getIndividus()[i].getAge()+1);
		}
	}
}
