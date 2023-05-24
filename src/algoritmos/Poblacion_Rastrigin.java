package algoritmos;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * Clase encargada de gestionar la poblacion de los individuos de referidos al
 * algoritmo Rastrigin.
 *
 */
public class Poblacion_Rastrigin {

	private int ELITISM_K; /// aaa
	private int POP_SIZE; // population size
	private int MAX_ITER; // max number of iterations
	private double MUTATION_RATE; // probability of mutation
	private double CROSSOVER_RATE; // probability of crossover

	private static Random m_rand = new Random(); // random-number generator
	private Individuo_Rastrigin[] m_population;
	private double totalFitness;

	/**
	 * Funcion encargada de generar una poblacion aleatoria y asigna el fitness
	 * value adecuado a cada uno de los individuos.
	 * 
	 * @param eLITISM_K      Cantidad de elitistas.
	 * @param pOP_SIZE       Tamaño de la poblacion.
	 * @param mAX_ITER       Cantidad maxima de iteraciones.
	 * @param mUTATION_RATE  Probabilidad de mutacion.
	 * @param cROSSOVER_RATE Probabilidad de cruce.
	 */
	public Poblacion_Rastrigin(int eLITISM_K, int pOP_SIZE, int mAX_ITER, double mUTATION_RATE, double cROSSOVER_RATE) {
		ELITISM_K = eLITISM_K;
		POP_SIZE = pOP_SIZE;
		MAX_ITER = mAX_ITER;
		MUTATION_RATE = mUTATION_RATE;
		CROSSOVER_RATE = cROSSOVER_RATE;

		m_population = new Individuo_Rastrigin[POP_SIZE];

		// init population
		for (int i = 0; i < POP_SIZE; i++) {
			m_population[i] = new Individuo_Rastrigin();
			m_population[i].randGenes();
		}

		// evaluate current population
		this.evaluate();
	}

	/**
	 * Set de la poblacion.
	 * 
	 * @param newPop Poblacion de individuos.
	 */
	public void setPopulation(Individuo_Rastrigin[] newPop) {
		// this.m_population = newPop;
		System.arraycopy(newPop, 0, this.m_population, 0, POP_SIZE);
	}

	/**
	 * Get del fitness total de la poblacion.
	 * 
	 * @return Fitness total de la poblacion.
	 */
	public double gettotalFirness() {
		return this.totalFitness;
	}

	/**
	 * Get double aleatorio.
	 * 
	 * @return Double aleatorio.
	 */
	public double getm_rand() {
		return this.m_rand.nextDouble();
	}

	/**
	 * Get poblacion como array de individuos.
	 * 
	 * @return Poblacion en forma de array de individuos.
	 */
	public Individuo_Rastrigin[] getPopulation() {
		return this.m_population;
	}

	/**
	 * Asigna el fitness a la poblacion.
	 * 
	 * @return Valor fitness.
	 */
	public double evaluate() {
		this.totalFitness = 0.0;
		for (int i = 0; i < POP_SIZE; i++) {
			this.totalFitness += m_population[i].evaluate();
		}
		this.totalFitness = Math.round(this.totalFitness * 100.0) / 100.0;
		return this.totalFitness;
	}

	/**
	 * Funcion que devuelve el individuo con el mejor valor de fitness de la
	 * poblacion.
	 * 
	 * @return Individuo con el mejor valor de fitness de la poblacion
	 */
	public Individuo_Rastrigin findBestIndividuo_Rastrigin() {
		int idxMax = 0, idxMin = 0;
		double currentMax = 0.0;
		double currentMin = 1.0;
		double currentVal;

		for (int idx = 0; idx < POP_SIZE; ++idx) {
			currentVal = m_population[idx].getFitnessValue();
			if (currentMax < currentMin) {
				currentMax = currentMin = currentVal;
				idxMax = idxMin = idx;
			}
			if (currentVal > currentMax) {
				currentMax = currentVal;
				idxMax = idx;
			}
			if (currentVal < currentMin) {
				currentMin = currentVal;
				idxMin = idx;
			}
		}

		return m_population[idxMin]; // minimization
		// return m_population[idxMax]; // maximization
	}

	/**
	 * Funcion que devuelve el individuo con el mejor fitness de la lista dada por
	 * parametro.
	 * 
	 * @param lista_mejores_individuos Lista de individuos.
	 * @return Individuo con el mejor fitness de la lista dada por parametros.
	 */
	public Individuo_Rastrigin findBestIndividuo_Rastrigin_varios(
			LinkedList<Individuo_Rastrigin> lista_mejores_individuos) {
		int idxMax = 0, idxMin = 0;
		double currentMax = 0.0;
		double currentMin = 1.0;
		double currentVal;

		for (int idx = 0; idx < POP_SIZE; ++idx) {
			currentVal = m_population[idx].getFitnessValue();
			if (currentMax < currentMin) {
				currentMax = currentMin = currentVal;
				idxMax = idxMin = idx;
			}
			if (currentVal > currentMax && !lista_mejores_individuos.contains(m_population[idx])) {
				currentMax = currentVal;
				idxMax = idx;
			}
			if (currentVal < currentMin && !lista_mejores_individuos.contains(m_population[idx])) {
				currentMin = currentVal;
				idxMin = idx;
			}
		}

		return m_population[idxMin]; // minimization
		// return m_population[idxMax]; // maximization
	}

	// ---------------- ALGORITMOS DE SELECCIoN ----------------

	/**
	 * Funcion que realiza el algoritmo seleccion llamado "metodo de la ruleta". La
	 * probabilidad de que un individuo sea seleccionado es proporcional a su
	 * fitness relativo, es decir, a su fitness dividido por la suma del fitness de
	 * todos los individuos de la poblacion. Si el fitness de un individuo es el
	 * doble que el de otro, tambien lo sera la probabilidad de que sea
	 * seleccionado. Este metodo presenta problemas si el fitness de unos pocos
	 * individuos es muy superior (varios ordenes de magnitud) al resto, ya que
	 * estos seran seleccionados de forma repetida y casi todos los individuos de la
	 * siguiente generacion seran "hijos" de los mismos "padres" (poca variacion).
	 * 
	 * @return Individuo.
	 */
	public Individuo_Rastrigin metodo_ruleta() {

		// Ordenamos los individuos en base a su fitness value.
		// De mejor a peor (mejor: mas cercano a 0)

		double numero;

		Individuo_Rastrigin[] individuos_ordenados = new Individuo_Rastrigin[this.POP_SIZE];

		for (int i = 0; i < individuos_ordenados.length; i++) {
			individuos_ordenados[i] = new Individuo_Rastrigin(this.m_population[i]);
		}

//		// Vector original sin ordenar
//		System.out.println("Vector original sin ordenar:");
//		for (int i = 0; i < individuos_ordenados.length; i++) {
//			System.out.println(individuos_ordenados[i].getFitnessValue());
//		}

		// Ordenamos
		for (int i = 0; i < individuos_ordenados.length - 1; i++) {
			for (int j = 0; j < individuos_ordenados.length - i - 1; j++) {
				if (Math.abs(individuos_ordenados[j + 1].getFitnessValue()) < Math
						.abs(individuos_ordenados[j].getFitnessValue())) {
					Individuo_Rastrigin x = individuos_ordenados[j + 1];
					individuos_ordenados[j + 1] = individuos_ordenados[j];
					individuos_ordenados[j] = x;
				}
			}
		}

//		// Vector original ordenado
//		System.out.println("Vector original ordenado:");
//		for (int i = 0; i < individuos_ordenados.length; i++) {
//			System.out.println(individuos_ordenados[i].getFitnessValue());
//		}

		// Hacemos una copia del array anterior para poder transformar los fitness
		// values
		// de cada individuo.
		Individuo_Rastrigin[] individuos_ordenados_transformacion = new Individuo_Rastrigin[this.POP_SIZE];

		for (int i = 0; i < individuos_ordenados.length; i++) {
			individuos_ordenados_transformacion[i] = new Individuo_Rastrigin(individuos_ordenados[i]);
		}
//
//		// Vector transformado sin ordenar
//		System.out.println("Vector transformado sin ordenar:");
//		for (int i = 0; i < individuos_ordenados_transformacion.length; i++) {
//			System.out.println(individuos_ordenados_transformacion[i].getFitnessValue());
//		}

		double fitness_value_trasnformado = 0;
		for (int i = 0; i < individuos_ordenados_transformacion.length; i++) {

			numero = Math.round((this.totalFitness / individuos_ordenados_transformacion[i].getFitnessValue()) * 100.0)
					/ 100.0;

			individuos_ordenados_transformacion[i].setFitnessValue(numero);
			// Calculamos el nuevo fitness transformado
			fitness_value_trasnformado = fitness_value_trasnformado
					+ individuos_ordenados_transformacion[i].getFitnessValue();
		}

//		// Vector transformado ordenado
//		System.out.println("Vector transformado ordenado:");
//		for (int i = 0; i < individuos_ordenados_transformacion.length; i++) {
//			System.out.println(individuos_ordenados_transformacion[i].getFitnessValue());
//		}

		fitness_value_trasnformado = Math.round(fitness_value_trasnformado * 100.0) / 100.0;

		// Elegimos individuo segun la probabilidad de este.
		double randNum = m_rand.nextDouble() * fitness_value_trasnformado;
		randNum = Math.round((randNum * 100.0) / 100.0);
		int idx;
		for (idx = 0; idx < POP_SIZE && randNum > 0; ++idx) {
			randNum -= individuos_ordenados_transformacion[idx].getFitnessValue();
		}

//		System.out.println("Fitness value total transformado: " + fitness_value_trasnformado);
//		System.out.println("idx: " + idx);
		if (idx < 1) {
			idx = 1;
		}
		return individuos_ordenados[idx - 1];
	}

	/**
	 * Funcion que realiza el algoritmo seleccion llamado "metodo rank". La
	 * probabilidad de seleccion de un individuo es inversamente proporcional a la
	 * posicion que ocupa tras ordenar todos los individuos de mayor a menor
	 * fitness. Este metodo es menos agresivo que el metodo ruleta cuando la
	 * diferencia entre los mayores fitness es varios ordenes de magnitud superior
	 * al resto.
	 * 
	 * @return Individuo.
	 */
	public Individuo_Rastrigin metodo_rank() {
		// Ordenamos los individuos en base a su fitness value.
		// De mejor a peor (mejor: mas cercano a 0)

		Individuo_Rastrigin[] individuos_ordenados = new Individuo_Rastrigin[this.POP_SIZE];

		for (int i = 0; i < individuos_ordenados.length; i++) {
			individuos_ordenados[i] = new Individuo_Rastrigin(this.m_population[i]);
		}

//		// Vector original sin ordenar
//		System.out.println("Vector original sin ordenar:");
//		for (int i = 0; i < individuos_ordenados.length; i++) {
//			System.out.println(individuos_ordenados[i].getFitnessValue());
//		}

		// Ordenamos
		for (int i = 0; i < individuos_ordenados.length - 1; i++) {
			for (int j = 0; j < individuos_ordenados.length - i - 1; j++) {
				if (Math.abs(individuos_ordenados[j + 1].getFitnessValue()) > Math
						.abs(individuos_ordenados[j].getFitnessValue())) {
					Individuo_Rastrigin x = individuos_ordenados[j + 1];
					individuos_ordenados[j + 1] = individuos_ordenados[j];
					individuos_ordenados[j] = x;
				}
			}
		}

//		// Vector original ordenado
//		System.out.println("Vector original ordenado:");
//		for (int i = 0; i < individuos_ordenados.length; i++) {
//			System.out.println(individuos_ordenados[i].getFitnessValue());
//		}

		// Hacemos una copia del array anterior para poder transformar los fitness
		// values
		// de cada individuo.
		Individuo_Rastrigin[] individuos_ordenados_transformacion = new Individuo_Rastrigin[this.POP_SIZE];

		for (int i = 0; i < individuos_ordenados.length; i++) {
			individuos_ordenados_transformacion[i] = new Individuo_Rastrigin(individuos_ordenados[i]);
		}

//		// Vector transformado sin ordenar
//		System.out.println("Vector transformado sin ordenar:");
//		for (int i = 0; i < individuos_ordenados_transformacion.length; i++) {
//			System.out.println(individuos_ordenados_transformacion[i].getFitnessValue());
//		}

		double fitness_value_trasnformado = 0.0D;
		for (int i = 0; i < individuos_ordenados_transformacion.length; i++) {

			double sumaParcial = this.totalFitness / individuos_ordenados_transformacion[i].getFitnessValue();
			sumaParcial = Math.round((sumaParcial) * 100.0) / 100.0;

			individuos_ordenados_transformacion[i].setFitnessValue(sumaParcial);

			// Calculamos el nuevo fitness transformado
			fitness_value_trasnformado = fitness_value_trasnformado + sumaParcial;
		}

//		// Vector transformado ordenado
//		System.out.println("Vector transformado ordenado:");
//		for (int i = 0; i < individuos_ordenados_transformacion.length; i++) {
//			System.out.println(individuos_ordenados_transformacion[i].getFitnessValue());
//		}

		fitness_value_trasnformado = Math.round(fitness_value_trasnformado * 100.0) / 100.0;

		// Elegimos individuo segun la probabilidad de este.
		double randNum = m_rand.nextDouble() * fitness_value_trasnformado;
		randNum = Math.round((randNum * 100.0) / 100.0);
		int idx;
		for (idx = 0; idx < POP_SIZE && randNum > 0; ++idx) {
			randNum -= individuos_ordenados_transformacion[idx].getFitnessValue();
		}

//		System.out.println("Fitness value total transformado: " + fitness_value_trasnformado);
//		System.out.println("idx: " + idx);
		if (idx < 1) {
			idx = 1;
		}
		return individuos_ordenados[idx - 1];
	}

	/**
	 * Funcion auxiliar que devuelve si un valor entero esta en un array.
	 * 
	 * @param array Array de muestra.
	 * @param valor Valor a comprobar.
	 * @return True si el valor esta en el array. False si no lo esta.
	 */
	private boolean array_contiene_valor(int[] array, int valor) {
		boolean enc = false;

		for (int i = 0; i < array.length && !enc; i++) {
			if (array[i] == valor) {
				enc = true;
			}
		}

		return enc;
	}

	/**
	 * Funcion que imprime los valores fitness de los individuos de la poblacion.
	 */
	private void imprimir_individuos_fv() {
		for (int i = 0; i < m_population.length; i++) {
			System.out.println("Posicion: " + i + " | FV: " + m_population[i].getFitnessValue());
		}
	}

	/**
	 * Funcion que imprime los valores fitness de los individuos de la poblacion
	 * dada por parametro.
	 * 
	 * @param poblacion Poblacion que usamos para imprimir.
	 */
	private void imprimir_individuos_fv(Individuo_Rastrigin[] poblacion) {
		for (int i = 0; i < poblacion.length; i++) {
			System.out.println("Posicion: " + i + " | FV: " + poblacion[i].getFitnessValue());
		}
	}

	/**
	 * Funcion que realiza el algoritmo seleccion llamado "seleccion competitiva".
	 * Se seleccionan aleatoriamente dos parejas de individuos de la poblacion
	 * (todos con la misma probabilidad). De cada pareja se selecciona el que tenga
	 * mayor fitness. Finalmente, se comparan los dos finalistas y se selecciona el
	 * de mayor fitness. Este metodo tiende a generar una distribucion de la
	 * probabilidad de seleccion mas equilibrada que las dos anteriores.
	 * 
	 * @param n Numero de torneos.
	 * @return Individuo.
	 */
	public Individuo_Rastrigin[] seleccion_competitiva(int n) {

		// Tenemos por ejemplo un numero de 50 individuos de la poblacion
		// Decimos, vamos a hacer un torneo de 5
		// Cogemos 5 numeros aleatorios, y nos quedamos con el de mejor fitness y ese lo
		// guardamos
		// Volvemos a coger 5 numeros aletorios, sin contar el que ha salido antes y lo
		// guardamos, y devolvemos los dos
//		System.out.println("Original");
//		imprimir_individuos_fv();

		Individuo_Rastrigin[] poblacion_individuos = new Individuo_Rastrigin[this.POP_SIZE];

		for (int i = 0; i < poblacion_individuos.length; i++) {
			poblacion_individuos[i] = new Individuo_Rastrigin(this.m_population[i]);
		}

		int[] array_aleatorios;
		array_aleatorios = new int[n];

		for (int i = 0; i < array_aleatorios.length; i++) {
			array_aleatorios[i] = -1;
		}

		for (int i = 0; i < n; i++) {
			Random rand = new Random();
			int numero_aleatorio = rand.nextInt(POP_SIZE);

			while (array_contiene_valor(array_aleatorios, numero_aleatorio)) {
				rand = new Random();
				numero_aleatorio = rand.nextInt(POP_SIZE);
			}

			array_aleatorios[i] = numero_aleatorio;
		}

//		System.out.println("Valor primer aleatorio:");
//		for (int i = 0; i < array_aleatorios.length; i++) {
//			System.out.println(array_aleatorios[i]);
//		}

		Individuo_Rastrigin individuo_mejor_fitness = new Individuo_Rastrigin();
		individuo_mejor_fitness = poblacion_individuos[array_aleatorios[0]];
		int index_individuo_mejor_fitness = -1;

		for (int i = 0; i < n; i++) {

			// Selecciono el individuo que indique el array array_aleatorios

			if (poblacion_individuos[array_aleatorios[i]].getFitnessValue() < individuo_mejor_fitness
					.getFitnessValue()) {
				individuo_mejor_fitness = poblacion_individuos[array_aleatorios[i]];
				index_individuo_mejor_fitness = array_aleatorios[i];
			}

		}

//		System.out.println("");
//		System.out.println("Primer mejor individuo. Posicion: " + index_individuo_mejor_fitness + " | FV: "
//				+ individuo_mejor_fitness.getFitnessValue());
//
//		System.out.println("");
//		System.out.println("Tras tener el primer mejor individuo");
//		imprimir_individuos_fv();

		// ----------

		array_aleatorios = new int[n];

		for (int i = 0; i < array_aleatorios.length; i++) {
			array_aleatorios[i] = -1;
		}

		for (int i = 0; i < n; i++) {
			Random rand = new Random();
			int numero_aleatorio = rand.nextInt(POP_SIZE);

			while (array_contiene_valor(array_aleatorios, numero_aleatorio)
					|| index_individuo_mejor_fitness == numero_aleatorio) {
				rand = new Random();
				numero_aleatorio = rand.nextInt(POP_SIZE);
			}

			array_aleatorios[i] = numero_aleatorio;
		}

//		System.out.println("Valor segundo aleatorio:");
//		for (int i = 0; i < array_aleatorios.length; i++) {
//			System.out.println(array_aleatorios[i]);
//		}

		Individuo_Rastrigin individuo_mejor_fitness_2 = new Individuo_Rastrigin();
		individuo_mejor_fitness_2 = poblacion_individuos[array_aleatorios[0]];
		int index_individuo_mejor_fitness_2 = -1;

		for (int i = 0; i < n; i++) {

			// Selecciono el individuo que indique el array array_aleatorios

			if (poblacion_individuos[array_aleatorios[i]].getFitnessValue() < individuo_mejor_fitness_2
					.getFitnessValue()) {
				individuo_mejor_fitness_2 = poblacion_individuos[array_aleatorios[i]];
				index_individuo_mejor_fitness_2 = array_aleatorios[i];
			}

		}

//		System.out.println("");
//		System.out.println("Segundo mejor individuo. Posicion: " + index_individuo_mejor_fitness_2 + " | FV: "
//				+ individuo_mejor_fitness_2.getFitnessValue());
//
//		System.out.println("");
//		System.out.println("Tras tener el segundo mejor individuo");
//		imprimir_individuos_fv();

		Individuo_Rastrigin[] array_individuos = new Individuo_Rastrigin[2];

		array_individuos[0] = individuo_mejor_fitness;
		array_individuos[1] = individuo_mejor_fitness_2;

		return array_individuos;
	}

	/**
	 * Funcion que realiza el algoritmo seleccion llamado "seleccion truncada". Se
	 * realizan selecciones aleatorias de individuos, habiendo descartado primero
	 * los n individuos con menor fitness de la poblacion.
	 * 
	 * @param n Numero de torneos.
	 * @param cantidad_eliminar Cantidad de individuos que sacar.
	 * @return Individuo.
	 */
	public Individuo_Rastrigin seleccion_truncada(int n, int cantidad_eliminar) {
		// Tenemos por ejemplo un numero de 50 individuos de la poblacion
		// Decimos, vamos a hacer un torneo de 5 con descarte de los 2 peores
		// Cogemos 5 numeros aleatorios, y nos quedamos con los tres con mejor fitness,
		// los dos con peores lo descartamos
		// De los tres mejores nos quedamos con el de mejor fitness y lo devolvemos

//		System.out.println("Original");
//		imprimir_individuos_fv();

		Individuo_Rastrigin[] poblacion_individuos = new Individuo_Rastrigin[this.POP_SIZE];

		for (int i = 0; i < poblacion_individuos.length; i++) {
			poblacion_individuos[i] = new Individuo_Rastrigin(this.m_population[i]);
		}

		int[] array_aleatorios;
		array_aleatorios = new int[n];

		for (int i = 0; i < array_aleatorios.length; i++) {
			array_aleatorios[i] = -1;
		}

		for (int i = 0; i < n; i++) {
			Random rand = new Random();
			int numero_aleatorio = rand.nextInt(POP_SIZE);

			while (array_contiene_valor(array_aleatorios, numero_aleatorio)) {
				rand = new Random();
				numero_aleatorio = rand.nextInt(POP_SIZE);
			}

			array_aleatorios[i] = numero_aleatorio;
		}

//		System.out.println("Valor primer aleatorio:");
//		for (int i = 0; i < array_aleatorios.length; i++) {
//			System.out.println(array_aleatorios[i]);
//		}

		Individuo_Rastrigin[] individuos_aleatorios = new Individuo_Rastrigin[array_aleatorios.length];

		for (int i = 0; i < array_aleatorios.length; i++) {
			individuos_aleatorios[i] = poblacion_individuos[array_aleatorios[i]];
		}

		Individuo_Rastrigin temp = new Individuo_Rastrigin();

		// Sort the array in ascending order
		for (int i = 0; i < individuos_aleatorios.length; i++) {
			for (int j = i + 1; j < individuos_aleatorios.length; j++) {
				if (individuos_aleatorios[i].getFitnessValue() > individuos_aleatorios[j].getFitnessValue()) {
					temp = individuos_aleatorios[i];
					individuos_aleatorios[i] = individuos_aleatorios[j];
					individuos_aleatorios[j] = temp;
				}
			}
		}

//		System.out.println("Tras ordenar");
//		imprimir_individuos_fv(individuos_aleatorios);

		Individuo_Rastrigin[] aux = new Individuo_Rastrigin[individuos_aleatorios.length - cantidad_eliminar];

		for (int i = 0; i < aux.length; i++) {
			aux[i] = individuos_aleatorios[i];
		}

//		System.out.println("Final");
//		imprimir_individuos_fv(aux);

		return aux[0];

	}

	// ---------------------------------------------------------

	// ------------------- OPERADOR DE CRUCE -------------------
	
	/**
	 * Funcion que realiza el cruce entre dos individuos.
	 * @param indiv1 Individuo 1.
	 * @param indiv2 Individuo 2.
	 * @return Individuo.
	 */
	public static Individuo_Rastrigin[] crossover(Individuo_Rastrigin indiv1, Individuo_Rastrigin indiv2) {
		Individuo_Rastrigin[] newIndiv = new Individuo_Rastrigin[2];
		newIndiv[0] = new Individuo_Rastrigin();
		newIndiv[1] = new Individuo_Rastrigin();

		int randPoint = m_rand.nextInt(Individuo_Rastrigin.SIZE);
		int i;
		for (i = 0; i < randPoint; ++i) {
			newIndiv[0].setGene(i, indiv1.getGene(i));
			newIndiv[1].setGene(i, indiv2.getGene(i));
		}
		for (; i < Individuo_Rastrigin.SIZE; ++i) {
			newIndiv[0].setGene(i, indiv2.getGene(i));
			newIndiv[1].setGene(i, indiv1.getGene(i));
		}

		return newIndiv;
	}

	/**
	 * Cruce en un punto de dos individuos dados por parametros.
	 * @param indiv1 Individuo 1.
	 * @param indiv2 Individuo 2.
	 * @return Individuo.
	 */
	public static Individuo_Rastrigin[] cruce_en_un_punto(Individuo_Rastrigin indiv1, Individuo_Rastrigin indiv2) {
		int tamanio = indiv1.SIZE;

		Random rand = new Random();
		int index = rand.nextInt(tamanio - 1);

//		System.out.println("Genes del primer individuo:");
//		indiv1.show_Individual();
//
//		System.out.println("");
//
//		System.out.println("Genes del segundo individuo:");
//		indiv2.show_Individual();

		Individuo_Rastrigin indiv_hijo1;
		Individuo_Rastrigin indiv_hijo2;

		Individuo_Rastrigin[] hijos = new Individuo_Rastrigin[2];

		double[] genes_hijo1 = new double[3];
		double[] genes_hijo2 = new double[3];

		if (index == 0) {
			// Dividimos desde la primera seccion.

//			System.out.println("Dividimos desde la primera seccion.");

			genes_hijo1[0] = indiv1.getGene(0);
			genes_hijo1[1] = indiv2.getGene(1);
			genes_hijo1[2] = indiv2.getGene(2);

			genes_hijo2[0] = indiv2.getGene(0);
			genes_hijo2[1] = indiv1.getGene(1);
			genes_hijo2[2] = indiv1.getGene(2);

			indiv_hijo1 = new Individuo_Rastrigin(genes_hijo1, -1);
			indiv_hijo1.evaluate();

			indiv_hijo2 = new Individuo_Rastrigin(genes_hijo2, -1);
			indiv_hijo2.evaluate();

			hijos[0] = indiv_hijo1;
			hijos[1] = indiv_hijo2;

		} else {
			// Dividimos desde la segunda seccion.

//			System.out.println("Dividimos desde la segunda seccion.");

			genes_hijo1[0] = indiv2.getGene(0);
			genes_hijo1[1] = indiv2.getGene(1);
			genes_hijo1[2] = indiv1.getGene(2);

			genes_hijo2[0] = indiv1.getGene(0);
			genes_hijo2[1] = indiv1.getGene(1);
			genes_hijo2[2] = indiv2.getGene(2);

			indiv_hijo1 = new Individuo_Rastrigin(genes_hijo1, -1);
			indiv_hijo1.evaluate();

			indiv_hijo2 = new Individuo_Rastrigin(genes_hijo2, -1);
			indiv_hijo2.evaluate();

			hijos[0] = indiv_hijo1;
			hijos[1] = indiv_hijo2;
		}

//		System.out.println("Hijo 1:");
//		hijos[0].show_Individual();
//
//		System.out.println("Hijo 2:");
//		hijos[1].show_Individual();

		return hijos;
	}

	/**
	 * Cruce multipunto de dos individuos dados por parametros.
	 * @param indiv1 Individuo 1.
	 * @param indiv2 Individuo 2.
	 * @return Individuo.
	 */
	public static Individuo_Rastrigin[] cruce_multipunto(Individuo_Rastrigin indiv1, Individuo_Rastrigin indiv2) {
		int tamanio = indiv1.SIZE;

		Random rand = new Random();
		int index = rand.nextInt(tamanio - 1);

//		System.out.println("Genes del primer individuo:");
//		indiv1.show_Individual();
//
//		System.out.println("");
//
//		System.out.println("Genes del segundo individuo:");
//		indiv2.show_Individual();

		Individuo_Rastrigin indiv_hijo1;
		Individuo_Rastrigin indiv_hijo2;

		Individuo_Rastrigin[] hijos = new Individuo_Rastrigin[2];

		double[] genes_hijo1 = new double[3];
		double[] genes_hijo2 = new double[3];

		genes_hijo1[0] = indiv1.getGene(0);
		genes_hijo1[1] = indiv2.getGene(1);
		genes_hijo1[2] = indiv1.getGene(2);

		genes_hijo2[0] = indiv2.getGene(0);
		genes_hijo2[1] = indiv1.getGene(1);
		genes_hijo2[2] = indiv2.getGene(2);

		indiv_hijo1 = new Individuo_Rastrigin(genes_hijo1, -1);
		indiv_hijo1.evaluate();

		indiv_hijo2 = new Individuo_Rastrigin(genes_hijo2, -1);
		indiv_hijo2.evaluate();

		hijos[0] = indiv_hijo1;
		hijos[1] = indiv_hijo2;

//		System.out.println("Hijo 1:");
//		hijos[0].show_Individual();
//
//		System.out.println("Hijo 2:");
//		hijos[1].show_Individual();

		return hijos;
	}

	/**
	 * Cruce uniforme de dos individuos dados por parametros.
	 * @param indiv1 Individuo 1.
	 * @param indiv2 Individuo 2.
	 * @return Individuo.
	 */
	public static Individuo_Rastrigin[] cruce_uniforme(Individuo_Rastrigin indiv1, Individuo_Rastrigin indiv2) {
		int tamanio = indiv1.SIZE;

//		System.out.println("Genes del primer individuo:");
//		indiv1.show_Individual();
//
//		System.out.println("");
//
//		System.out.println("Genes del segundo individuo:");
//		indiv2.show_Individual();

		Individuo_Rastrigin indiv_hijo1;
		Individuo_Rastrigin indiv_hijo2;

		double[] genes_hijo1 = new double[3];
		double[] genes_hijo2 = new double[3];

		for (int i = 0; i < tamanio; i++) {

			Random rand = new Random();
			int index = rand.nextInt(2);

			if (index == 0) {
				// Escogemos el gen i del padre 1
//				System.out.println("El valor " + i + " es del padre 1.");
				genes_hijo1[i] = indiv1.getGene(i);
				genes_hijo2[i] = indiv2.getGene(i);
			} else {
				// Escogemos el gen i del padre 2
//				System.out.println("El valor " + i + " es del padre 2.");
				genes_hijo1[i] = indiv2.getGene(i);
				genes_hijo2[i] = indiv1.getGene(i);
			}

		}

		indiv_hijo1 = new Individuo_Rastrigin(genes_hijo1, -1);
		indiv_hijo1.evaluate();

		indiv_hijo2 = new Individuo_Rastrigin(genes_hijo2, -1);
		indiv_hijo2.evaluate();

		Individuo_Rastrigin[] hijos = new Individuo_Rastrigin[2];

		hijos[0] = indiv_hijo1;
		hijos[1] = indiv_hijo2;

//		System.out.println("Hijo 1:");
//		indiv_hijo1.show_Individual();
//		
//		System.out.println("Hijo 2:");
//		indiv_hijo2.show_Individual();

		return hijos;
	}

	// ---------------------------------------------------------

	public static void main(String[] args) {
		Poblacion_Rastrigin poblacion = new Poblacion_Rastrigin(0, 10, 1, 1, 1);
		// poblacion.seleccion_truncada(5, 3);
		double[] genes1 = { 111.111, 222.222, 333.333 };
		Individuo_Rastrigin indiv1 = new Individuo_Rastrigin(genes1, -1);
		indiv1.evaluate();

		double[] genes2 = { 444.444, 555.555, 666.666 };
		Individuo_Rastrigin indiv2 = new Individuo_Rastrigin(genes2, -2);
		indiv2.evaluate();

		poblacion.cruce_uniforme(indiv1, indiv2);

//		System.out.println("Padres:");
//		indiv1.show_Individual();
//		indiv2.show_Individual();
	}
}
