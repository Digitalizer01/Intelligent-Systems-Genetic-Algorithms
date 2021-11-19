package algoritmos;

import java.util.Random;

public class Individuo {

	public static final int SIZE = 500;
	private double[] genes = new double[SIZE];
	private int fitnessValue;

	public Individuo(double[] genes, int fitnessValue) {
		this.genes = genes;
		this.fitnessValue = fitnessValue;
	}

	public Individuo() {

	}

	public int getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(int fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public double getGene(int index) {
		return genes[index];
	}

	public void setGene(int index, double gene) {
		this.genes[index] = gene;
	}

	public void randGenes() {
		Random rand = new Random();
		for(int i = 0; i<SIZE; i++) {
			this.setGene(i, rand.nextDouble(??));
		}
	}

	public void mutate() {
		Random rand = new Random();
		int index = rand.nextInt(SIZE);
		this.setGene(index, 1 - this.getGene(index)); // flip
	}

	public int evaluate() {
		int fitness = 0;
		for (int i = 0; i < SIZE; i++) {
			fitness += this.getGene(i);
		}
		this.setFitnessValue(fitness);

		return fitness;
	}

	public void show_Individual() {
		for (int i = 0; i < SIZE; i++) {
			System.out.println(genes[i]);
		}
		System.out.println("");
	}
}
