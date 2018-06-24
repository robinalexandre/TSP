package fr.etu.ea.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class Reader {
	
	private static double[][] distance;
	
	private Reader(String path) {
		try(FileReader reader = new FileReader(new File(path));
			BufferedReader buffer = new BufferedReader(reader)) {
			String line;
			String[] values;
			boolean begin = false;
			List<Float[]> coord = new ArrayList<>();
			while((line = buffer.readLine()) != null) {
				if(!line.isEmpty() && (line.charAt(0) == '1' || begin) && !line.startsWith("EOF")) {
					values = line.split(" ");
					begin = true;
					Float[] tab = new Float[2];
					tab[0] = Float.valueOf(values[1]);
					tab[1] = Float.valueOf(values[2]);
					coord.add(tab);
				}				
			}
			Reader.distance = new double[coord.size()][coord.size()];
			for(int i = 0; i < coord.size(); ++i) {
				for(int j = 0; j < coord.size(); ++j) {
					Reader.distance[i][j] = Math.sqrt(Math.pow((coord.get(i)[0] - coord.get(j)[0]), 2) + Math.pow((coord.get(i)[1] - coord.get(j)[1]), 2));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static double[][] askATSP() {
		JFileChooser file = new JFileChooser();
//		file.setCurrentDirectory(new File("/Users/Alex/Documents/Cours/M2/EA/Cours"));
		file.showOpenDialog(null);
		new Reader(file.getSelectedFile().getPath());
		return Reader.distance;
	}
	
	/**
	 * Affichage en mode texte du graphe
	 */
	public static void afficheModeTexte() {
		System.out.println ("- Matrice de distance :");

		// affichage d'indices au dessus de la matrice afin d'y voir plus clair
		System.out.printf("%-2s", "");   
		for (int i=0;i<Reader.distance.length; i++)
			System.out.printf("%-5s", i);
		System.out.println();

		// affichage d'un trait au dessus de la matrice pour que ca soit plus joli
		System.out.printf("%-2s", "");   
		for (int i=0;i<Reader.distance.length; i++)
			System.out.printf("%-5s", "--");
		System.out.println();


		// affichage du contenu de la matrice, ligne par ligne
		for (int i=0;i<Reader.distance.length; i++) {
			System.out.print("| ");
			for (int j=0;j<Reader.distance.length; j++) {
				System.out.printf("%-5s", Reader.distance[i][j]);
			}
			System.out.println("| " + i);
		}

		// affichage d'un trait en dessous de la matrice pour que ca soit plus joli
		System.out.printf("%-2s", "");   
		for (int i=0;i<Reader.distance.length; i++)
			System.out.printf("%-5s", "--");
		System.out.println();
	}
	
}
