package fr.etu.ea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individu {
	
	private List<Integer> villes;
	private double[][] distance;
 	private int age;
	
	/**
	 * Initialisation d'un individu de taille N à 0
	 * @param taille
	 */
	public Individu(double[][] distance) {
		int taille = distance.length ;
		this.age = 0;
		this.setDistance(distance);
		this.villes = new ArrayList<>();
		for(int i = 0; i < taille; i++) {
			this.villes.add(i);
		}
		Collections.shuffle(this.villes);
	}
	
	public Individu(Individu individu) {
		this.age = 0;
		this.setDistance(individu.distance);
		this.villes = new ArrayList<>();
		for(int i = 0; i < individu.getVilles().size(); i++) {
			this.villes.add(individu.getVilles().get(i));
		}
	}
	
	public Individu(List<Integer> villes, double[][] distance) {
		this.age = 0;
		this.setDistance(distance);
		this.villes = new ArrayList<>();
		this.villes = new ArrayList<>();
		for(int i = 0; i < villes.size(); i++) {
			this.villes.add(villes.get(i));
		}
		Collections.copy(this.villes, villes);
	}
	
	/**
	 * Retourne le tableau de villes de l'individu
	 * @return
	 */
	public List<Integer> getVilles() {
		return this.villes;
	}
	
	public void setVilles(List<Integer> newChild) {
		this.villes = newChild;
	}
	
	public void setVille(int i, int ville) {
		this.villes.set(i, ville);
	}
	
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the distance
	 */
	public double[][] getDistance() {
		return this.distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double[][] distance) {
		this.distance = distance;
	}

	/**
	 * Renvoie le calcul de fitness de cet individu
	 * @return le fitness de l'individu
	 */
	public int fitness() {
		int value = 0;
		int taille = this.villes.size();
		for(int i = 0; i < taille-1; ++i) {
			value += this.distance[this.villes.get(i)][this.villes.get(i+1)];
		}
		value += this.distance[this.villes.get(taille-1)][this.villes.get(0)];
		return value;
	}
	
	public String toString() {
		String individu = "";
		for(int j = 0; j < this.villes.size(); ++j) {
			individu += this.villes.get(j)+1 + " ";
		}
		return individu;
	}
}

