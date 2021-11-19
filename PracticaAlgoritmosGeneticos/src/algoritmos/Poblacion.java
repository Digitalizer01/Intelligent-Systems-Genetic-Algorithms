package algoritmos;

import java.util.Random;

public class Poblacion {
	int ELITISM_K;
	int POP_SIZE; // population size
	int MAX_ITER; // max number of iterations
	double MUTATION_RATE; // probability of mutation
	double CROSSOVER_RATE; // probability of crossover
	
	private static Random m_rand = new Random(); // random-number generator
	private Individuo[] m_population;
	private double totalFitness;
}
