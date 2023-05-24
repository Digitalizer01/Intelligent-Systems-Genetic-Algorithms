package ejecucion;

import java.util.LinkedList;
import java.util.Random;

import algoritmos.Individuo_Rastrigin;
import algoritmos.Poblacion_Rastrigin;
import interfaz.Menu;
import main.SwingWorkerRealTime;

/**
 * Clase que ejecuta la gestion del algoritmo genetico de la funcion Rastrigin.
 * 
 *
 */
public class EjecucionRastrigin {

	/**
	 * args[0] = algoritmo args[1] = estrategia de reemplazamiento args[2] =
	 * operador de cruce args[3] = operador de mutacion args[4] = seleccion de
	 * individuos. args[5] = tamaño poblacion. args[6] = numero de iteraciones.
	 * args[7] = cantidad de elitistas. args[8] = probabilidad de mutacion. args[9]
	 * = probabilidad de cruce. args[10] = numero de torneos. args[11] = numero de
	 * individuos que sacar.
	 * 
	 * @param args Array de argumentos.
	 * @throws Exception Excepction del main.
	 */
	@SuppressWarnings({ "static-access", "unused" })
	public static void main(String[] args) throws Exception {
		SwingWorkerRealTime swingWorkerRealTime_mejor_fitness = new SwingWorkerRealTime();
		swingWorkerRealTime_mejor_fitness.go("Mejor fitness", "Rastring", "Iteración", "Valor fitness");

		SwingWorkerRealTime swingWorkerRealTime_fitness_total = new SwingWorkerRealTime();
		swingWorkerRealTime_fitness_total.go("Fitness total", "Rastring", "Iteración", "Valor fitness");
//
		SwingWorkerRealTime swingWorkerRealTime_mejores_genes_x1 = new SwingWorkerRealTime();
		swingWorkerRealTime_mejores_genes_x1.go("Mejores genes - X1", "Rastring", "Iteración", "Valor X1");

		SwingWorkerRealTime swingWorkerRealTime_mejores_genes_x2 = new SwingWorkerRealTime();
		swingWorkerRealTime_mejores_genes_x2.go("Mejores genes - X2", "Rastring", "Iteración", "Valor X2");
//
		SwingWorkerRealTime swingWorkerRealTime_mejores_genes_x3 = new SwingWorkerRealTime();
		swingWorkerRealTime_mejores_genes_x3.go("Mejores genes - X3", "Rastring", "Iteración", "Valor X3");

		System.out.println("Recibido de argumento 1: " + args[0]);
		System.out.println("Recibido de argumento 2: " + args[1]);
		System.out.println("Recibido de argumento 3: " + args[2]);
		System.out.println("Recibido de argumento 4: " + args[3]);
		System.out.println("Recibido de argumento 5: " + args[4]);
		System.out.println("Recibido de argumento 6: " + args[5]);
		System.out.println("Recibido de argumento 7: " + args[6]);
		System.out.println("Recibido de argumento 8: " + args[7]);
		System.out.println("Recibido de argumento 9: " + Double.parseDouble(args[8]) / 100);
		System.out.println("Recibido de argumento 10: " + Double.parseDouble(args[9]) / 100);

		String estrategia_reemplazamiento = args[1];
		String tipo_cruce = args[2];
		String tipo_mutacion = args[3];
		String metodo_seleccion = args[4];

		int numeroTorneo = Integer.parseInt(args[10]);
		int individuosSacar = Integer.parseInt(args[11]);

		int ELITISM_K = Integer.parseInt(args[7]);
		int POP_SIZE = Integer.parseInt(args[5]) + ELITISM_K; // population size
		int MAX_ITER = Integer.parseInt(args[6]); // max number of iterations
		double MUTATION_RATE = Double.parseDouble(args[8]) / 100; // probability of mutation
		double CROSSOVER_RATE = Double.parseDouble(args[9]) / 100; // probability of crossover

		Poblacion_Rastrigin pop = new Poblacion_Rastrigin(ELITISM_K, POP_SIZE, MAX_ITER, MUTATION_RATE, CROSSOVER_RATE);
		Poblacion_Rastrigin pop_aux = new Poblacion_Rastrigin(ELITISM_K, POP_SIZE, MAX_ITER, MUTATION_RATE,
				CROSSOVER_RATE);
		Individuo_Rastrigin[] newPop = new Individuo_Rastrigin[POP_SIZE];
		// ****************
		newPop = pop.getPopulation();
		// ***************
		Individuo_Rastrigin[] indiv = new Individuo_Rastrigin[2];

		// current population
		System.out.println("Current population:");
		System.out.print("Total Fitness = " + pop.gettotalFirness());
		System.out.println(" ; Best Fitness = " + pop.findBestIndividuo_Rastrigin().getFitnessValue());
		System.out.print("Best Individual: ");
		pop.findBestIndividuo_Rastrigin().show_Individual();

		// main loop
		int acabar = 0;
		int count;
		double fitness_total_aux = 0;
		for (int iter = 0; iter < MAX_ITER && acabar == 0; iter++) {

			count = 0;

			LinkedList<Individuo_Rastrigin> lista_mejores_individuos = new LinkedList<>(); // Metemos aquí la población

			Individuo_Rastrigin[] individuos_hijos = new Individuo_Rastrigin[2];

			// Elitism
			for (int i = 0; i < ELITISM_K; ++i) {
				newPop[count] = pop.findBestIndividuo_Rastrigin_varios(lista_mejores_individuos);
				lista_mejores_individuos.add(newPop[count]);
				count++;
			}

			// build new Population
			while (count + 1 < POP_SIZE) {
				// Selection

				switch (metodo_seleccion) {
				case "Método de la ruleta":

					indiv[0] = pop.metodo_ruleta();
					indiv[1] = pop.metodo_ruleta();
					while (indiv[1] == (indiv[0])) {
						indiv[1] = pop.metodo_ruleta();
					}

					indiv[0].evaluate();
					indiv[1].evaluate();

					break;

				case "Método rank":

					indiv[0] = pop.metodo_rank();
					indiv[1] = pop.metodo_rank();
					while (indiv[0] == indiv[1]) {
						indiv[1] = pop.metodo_rank();
					}

					indiv[0].evaluate();
					indiv[1].evaluate();

					break;

				case "Selección competitiva":

					indiv = pop.seleccion_competitiva(numeroTorneo);
					indiv[0].evaluate();
					indiv[1].evaluate();

					break;

				case "Selección truncada":

					indiv[0] = pop.seleccion_truncada(numeroTorneo, individuosSacar);
					indiv[1] = pop.seleccion_truncada(numeroTorneo, individuosSacar);
					while (indiv[0] == indiv[1]) {
						indiv[1] = pop.seleccion_truncada(numeroTorneo, individuosSacar);
					}
					indiv[0].evaluate();
					indiv[1].evaluate();

					break;
				}

				// Crossover
				if (pop.getm_rand() < CROSSOVER_RATE) {

					switch (tipo_cruce) {
					case "Cruce en un punto":
						individuos_hijos = pop.cruce_en_un_punto(indiv[0], indiv[1]);
						break;

					case "Cruce multipunto":
						individuos_hijos = pop.cruce_multipunto(indiv[0], indiv[1]);
						break;

					case "Cruce uniforme":
						individuos_hijos = pop.cruce_uniforme(indiv[0], indiv[1]);
						break;
					}

					boolean hijo_1_valido = false;
					boolean hijo_2_valido = false;

					for (int i = 0; i < individuos_hijos.length; i++) {
						Individuo_Rastrigin individuo_Rastrigin = individuos_hijos[i];
						if (i == 0) {
							// Hijo 1
							hijo_1_valido = individuo_Rastrigin.individuo_valido();
						} else {
							// Hijo 2
							hijo_2_valido = individuo_Rastrigin.individuo_valido();
						}
					}

					if (hijo_1_valido == true || hijo_2_valido == true) {
						// Mutation
						if (hijo_1_valido == true) {
							// Hijo 1
							if (pop.getm_rand() < MUTATION_RATE) {

								switch (tipo_mutacion) {
								case "Distribución normal":
									individuos_hijos[0].mutate_distribucion_normal();
									break;

								case "Distribución uniforme":
									individuos_hijos[0].mutate_distribucion_uniforme();
									break;

								case "Aleatorio":
									individuos_hijos[0].mutate_aleatorio();
									break;
								}

							}
							individuos_hijos[0].evaluate();
						}

						if (hijo_2_valido == true) {
							// Hijo 2
							if (pop.getm_rand() < MUTATION_RATE) {
								switch (tipo_mutacion) {
								case "Distribución normal":
									individuos_hijos[1].mutate_distribucion_normal();
									break;

								case "Distribución uniforme":
									individuos_hijos[1].mutate_distribucion_uniforme();
									break;

								case "Aleatorio":
									individuos_hijos[1].mutate_aleatorio();
									break;
								}
							}
							individuos_hijos[1].evaluate();
						}

						for (int i = 0; i < individuos_hijos.length; i++) {
							Individuo_Rastrigin individuo_Rastrigin = individuos_hijos[i];
							if (i == 0) {
								// Hijo 1
								hijo_1_valido = individuo_Rastrigin.individuo_valido();
							} else {
								// Hijo 2
								hijo_2_valido = individuo_Rastrigin.individuo_valido();
							}
						}

						if (hijo_1_valido == true || hijo_2_valido == true) {
							switch (estrategia_reemplazamiento) {
							case "Estacionario":
								// add to new population
								// Sacamos los dos mejores entre los dos padres y los dos hijos.

								Individuo_Rastrigin[] individuos_orden;
								if (hijo_1_valido == true && hijo_2_valido == true) {
									individuos_orden = new Individuo_Rastrigin[4];
									individuos_orden[0] = indiv[0];
									individuos_orden[1] = indiv[1];
									individuos_orden[2] = individuos_hijos[0];
									individuos_orden[3] = individuos_hijos[1];
								} else {
									if (hijo_1_valido == false && hijo_2_valido == true) {
										individuos_orden = new Individuo_Rastrigin[3];
										individuos_orden[0] = indiv[0];
										individuos_orden[1] = indiv[1];
										individuos_orden[2] = individuos_hijos[1];
									} else {
										individuos_orden = new Individuo_Rastrigin[3];
										individuos_orden[0] = indiv[0];
										individuos_orden[1] = indiv[1];
										individuos_orden[2] = individuos_hijos[0];
									}
								}

								/*
								 * individuos_orden[0].show_Individual(); individuos_orden[1].show_Individual();
								 * individuos_orden[2].show_Individual(); individuos_orden[3].show_Individual();
								 */

								Individuo_Rastrigin individuo_aux;
								for (int i = 0; i < individuos_orden.length - 1; i++) {
									for (int j = 0; j < individuos_orden.length - i - 1; j++) {
										if (Math.abs(individuos_orden[j].getFitnessValue()) > Math
												.abs(individuos_orden[j + 1].getFitnessValue())) {
											individuo_aux = individuos_orden[j + 1];
											individuos_orden[j + 1] = individuos_orden[j];
											individuos_orden[j] = individuo_aux;
										}
									}
								}

//								individuos_orden[0].show_Individual();
//								individuos_orden[1].show_Individual();
//								individuos_orden[2].show_Individual();
//								individuos_orden[3].show_Individual();
//								System.out.println("Generacion: " + iter);
//								System.out.println("Count:" + count);

								newPop[count] = individuos_orden[0];
								newPop[count + 1] = individuos_orden[1];
								count += 2;
								pop_aux.setPopulation(newPop);
								pop_aux.evaluate();
//								if (iter > 0) {
//									if (pop_aux.gettotalFirness() > pop.gettotalFirness()) {
//										if(hijo_1_valido == true && hijo_2_valido == true) {
//											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
//											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 2");
//										}
//										
//										if(hijo_1_valido == false && hijo_2_valido == true) {
//											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
//											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 2");
//										}
//										
//										if(hijo_1_valido == true && hijo_2_valido == true) {
//											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
//											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 2");
//										}
//									}
//								}
								break;

							case "Generacional":
								// add to new population

								if (hijo_1_valido == true && hijo_2_valido == true) {
									newPop[count] = individuos_hijos[0];
									newPop[count + 1] = individuos_hijos[1];
									count += 2;
									System.out.println("Generacion: " + iter);
									System.out.println("Count:" + count);
									pop_aux.setPopulation(newPop);
									pop_aux.evaluate();
									if (iter > 0) {
										if (pop_aux.gettotalFirness() > pop.gettotalFirness()) {
											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
											System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 2");
										}
									}
								} else {
									if (hijo_1_valido == true && hijo_2_valido == false) {
										newPop[count] = individuos_hijos[0];
										count += 1;
										System.out.println("Generacion: " + iter);
										System.out.println("Count:" + count);
										pop_aux.setPopulation(newPop);
										pop_aux.evaluate();
//										if (iter > 0) {
//											if (pop_aux.gettotalFirness() > pop.gettotalFirness()) {
//												System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
//												System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 2");
//											}
//										}
									} else {
										newPop[count] = individuos_hijos[1];
										count += 1;
										System.out.println("Generacion: " + iter);
										System.out.println("Count:" + count);
										pop_aux.setPopulation(newPop);
										pop_aux.evaluate();

									}
								}

								break;

							} // switch
						} // Primer if

					} // if bajo el for

				} // Crossover

			}

			pop_aux.setPopulation(newPop);
			pop_aux.evaluate();
			fitness_total_aux = pop_aux.gettotalFirness();

			pop.setPopulation(newPop);

			// reevaluate current population
			pop.evaluate();

			if (iter > 0) {
				if (pop.gettotalFirness() > fitness_total_aux) {
					System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
					System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 2");
				}
			}

			fitness_total_aux = pop.gettotalFirness();

			System.out.println("Iteration: " + iter);
			System.out.print("Total Fitness = " + pop.gettotalFirness());
			System.out.println(" ; Best Fitness = " + pop.findBestIndividuo_Rastrigin().getFitnessValue());
			System.out.print("Best Individual: ");
			swingWorkerRealTime_mejores_genes_x1.getMySwingWorker()
					.insertar_valor(pop.findBestIndividuo_Rastrigin().getGene(0));
			swingWorkerRealTime_mejores_genes_x2.getMySwingWorker()
					.insertar_valor(pop.findBestIndividuo_Rastrigin().getGene(1));
			swingWorkerRealTime_mejores_genes_x3.getMySwingWorker()
					.insertar_valor(pop.findBestIndividuo_Rastrigin().getGene(2));
			pop.findBestIndividuo_Rastrigin().show_Individual();

			swingWorkerRealTime_mejor_fitness.getMySwingWorker()
					.insertar_valor(pop.findBestIndividuo_Rastrigin().getFitnessValue());
			swingWorkerRealTime_fitness_total.getMySwingWorker().insertar_valor(pop.gettotalFirness());

			if (pop.findBestIndividuo_Rastrigin().getFitnessValue() == 0.0) {
				System.out.println("¡¡¡Ha salido perfecto!!!");
				acabar = 1;
			}

		}
		swingWorkerRealTime_mejor_fitness.getMySwingWorker().finalizar_hilo_swingworker();
		swingWorkerRealTime_mejor_fitness.finalizar_hilo_go();

		swingWorkerRealTime_fitness_total.getMySwingWorker().finalizar_hilo_swingworker();
		swingWorkerRealTime_fitness_total.finalizar_hilo_go();

		swingWorkerRealTime_mejores_genes_x1.getMySwingWorker().finalizar_hilo_swingworker();
		swingWorkerRealTime_mejores_genes_x1.finalizar_hilo_go();

		swingWorkerRealTime_mejores_genes_x2.getMySwingWorker().finalizar_hilo_swingworker();
		swingWorkerRealTime_mejores_genes_x2.finalizar_hilo_go();

		swingWorkerRealTime_mejores_genes_x3.getMySwingWorker().finalizar_hilo_swingworker();
		swingWorkerRealTime_mejores_genes_x3.finalizar_hilo_go();

		// best indiv
		Individuo_Rastrigin bestIndiv = pop.findBestIndividuo_Rastrigin();

		System.out.print("Best Final Individual: ");
		pop.findBestIndividuo_Rastrigin().show_Individual();
	}

}
