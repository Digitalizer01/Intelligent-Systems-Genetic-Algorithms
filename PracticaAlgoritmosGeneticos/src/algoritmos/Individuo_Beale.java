package algoritmos;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * Clase encargada de gestionar un individuo referido al algoritmo Beale.
 * 
 *
 */
public class Individuo_Beale {
	public static final int SIZE = 2;
	public static final double MAX_VALUE = 4.5;
	public static final double MIN_VALUE = -4.5;
	private double[] genes = new double[SIZE];
	private double fitnessValue;

	/**
	 * Funcion que crea un individuo con los genes y valor de fitness pasados por
	 * parametros.
	 * 
	 * @param genes        Genes del individuo.
	 * @param fitnessValue Valor fitness.
	 */
	public Individuo_Beale(double[] genes, double fitnessValue) {
		this.genes = genes;
		this.fitnessValue = fitnessValue;
	}

	/**
	 * Funcion que crea un individuo.
	 * 
	 * @param individuo Individuo.
	 */
	public Individuo_Beale(Individuo_Beale individuo) {
		for (int i = 0; i < this.genes.length; i++) {
			this.genes[i] = individuo.getGene(i);
		}
		fitnessValue = individuo.fitnessValue;
	}

	/**
	 * Funcion que crea un individuo con valor fitness -1.
	 */
	public Individuo_Beale() {
		fitnessValue = -1;
	}

	/**
	 * Get valor fitness.
	 * 
	 * @return Valor fitness.
	 */
	public double getFitnessValue() {
		return fitnessValue;
	}

	/**
	 * Set valor fitness
	 * 
	 * @param fitnessValue Valor fitness.
	 */
	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	/**
	 * Get de los genes del individuo.
	 * 
	 * @return Array de los genes.
	 */
	public double[] getGene2() {
		return genes;
	}

	/**
	 * Get de un gen concreto del individuo.
	 * 
	 * @param index indice del array de genes.
	 * @return Gen.
	 */
	public double getGene(int index) {
		return genes[index];
	}

	/**
	 * Set gen en posicion concreta del array de genes.
	 * 
	 * @param index indice del array de genes.
	 * @param gene  Gen.
	 */
	public void setGene(int index, double gene) {
		this.genes[index] = gene;
	}

	/**
	 * Asigna genes aleatorios al individuo.
	 */
	public void randGenes() {
		double numero;
		Random rand = new Random();
		for (int i = 0; i < SIZE; i++) {
			numero = Math.random() * (MAX_VALUE - MIN_VALUE) + MIN_VALUE;
			numero = Math.round(numero * 100.0) / 100.0;
			this.setGene(i, numero);
		}
	}

	/**
	 * La mutacion de la posicion i se consigue sumandole al valor de i un valor
	 * extraido de una distribucion uniforme, por ejemplo una entre [-1,+1].
	 */
	public void mutate_distribucion_normal() {
		Random rand = new Random();
		Random rand_posicion = new Random();
		int index = rand_posicion.nextInt(SIZE);

		double valor_aleatorio = rand.nextGaussian();
		valor_aleatorio = this.getGene(index) + valor_aleatorio;
		valor_aleatorio = Math.round(valor_aleatorio * 100.0) / 100.0;

		this.setGene(index, valor_aleatorio); // flip
	}

	/**
	 * La mutacion de la posicion i se consigue sumandole al valor de i un valor
	 * extraido de una distribucion normal, comunmente centrada en 0 y con una
	 * determinada desviacion estandar. Cuanto mayor la desviacion estandar, con
	 * mayor probabilidad la mutacion introducira cambios grandes.
	 */
	public void mutate_distribucion_uniforme() {
		Random rand_posicion = new Random();
		int index = rand_posicion.nextInt(SIZE);

		double valor_aleatorio = (double) (Math.random() * (1 - (-1)) + (-1));
		valor_aleatorio = this.getGene(index) + valor_aleatorio;
		valor_aleatorio = Math.round(valor_aleatorio * 100.0) / 100.0;

		this.setGene(index, valor_aleatorio); // flip
	}

	/**
	 * La mutacion de la posicion i se consigue reemplazando el valor de i por nuevo
	 * valor aleatorio dentro del rango permitido para esa variable. Esta estrategia
	 * suele conllevar mayores variaciones que las dos anteriores.
	 */
	public void mutate_aleatorio() {
		Random rand_posicion = new Random();
		int index = rand_posicion.nextInt(SIZE);

		double valor_aleatorio = (double) (Math.random() * (MAX_VALUE - MIN_VALUE)) + MIN_VALUE;
		valor_aleatorio = Math.round(valor_aleatorio * 100.0) / 100.0;

		this.setGene(index, valor_aleatorio); // flip
	}

	/**
	 * Funcion que asigna un valor fitness al individuo en base a la funcion del
	 * algoritmo y los genes.
	 * 
	 * @return Valor fitness.
	 */
	public double evaluate() {
		double x = 0.0;
		double y = 0.0;
		double value = 0.0;

		double primera_suma = 0.0;
		double segunda_suma = 0.0;
		double tercera_suma = 0.0;

		primera_suma = (1.5 - this.genes[0] + (this.genes[0] * this.genes[1]));
		segunda_suma = (2.25 - this.genes[0] + (this.genes[0] * Math.pow(this.genes[1], 2)));
		tercera_suma = (2.625 - this.genes[0] + (this.genes[0] * Math.pow(this.genes[1], 3)));

		value = (Math.pow(primera_suma, 2) + Math.pow(segunda_suma, 2) + Math.pow(tercera_suma, 2));

		value = Math.round(value * 10000.0) / 10000.0;
		this.setFitnessValue(value);
		return (value);
	}

	/**
	 * Devuelve los valores del individuo.
	 */
	public void show_Individual() {
		for (int i = 0; i < SIZE; i++) {
			System.out.print(genes[i]);
			System.out.print(" | ");
		}

		System.out.println("");
	}
	
	/**
	 * Metodo que devuelve true si el hijo es válido y false si no lo es.
	 * 
	 * @return True si el hijo es válido y false si no lo es.
	 */
	public boolean individuo_valido() {
		boolean valido = true;

		for (int j = 0; j < genes.length && valido == true; j++) {
			double d = genes[j];
			if (genes[j] > MAX_VALUE || genes[j] < MIN_VALUE) {
				valido = false;
			}
		}

		return valido;
	}

	/**
	 * Devuelve el codigo hash del individuo.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitnessValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(genes);
		return result;
	}

	/**
	 * Devuelve si el individuo es igual a otro individuo.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individuo_Beale other = (Individuo_Beale) obj;
		if (Double.doubleToLongBits(fitnessValue) != Double.doubleToLongBits(other.fitnessValue))
			return false;
		if (!Arrays.equals(genes, other.genes))
			return false;
		return true;
	}
}
