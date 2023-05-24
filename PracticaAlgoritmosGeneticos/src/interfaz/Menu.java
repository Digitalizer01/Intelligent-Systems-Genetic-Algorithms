package interfaz;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import ejecucion.EjecucionAckley;
import ejecucion.EjecucionBeale;
import ejecucion.EjecucionBukin;
import ejecucion.EjecucionRastrigin;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

/**
 * Clase que crea el menu grafico de la aplicacion.
 * 
 *
 */
public class Menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Main del menú.
	 * 
	 * @param args Array de argumentos.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/imagenes/icon.png")));
		setTitle("Algoritmos gen\u00E9ticos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1096, 688);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu_1 = new JMenu("Ayuda");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmInformacion = new JMenuItem("Informaci\u00F3n");
		mntmInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Práctica de algoritmos genéticos de Sistemas Inteligentes (2021 - 2022).",
						"Información", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnNewMenu_1.add(mntmInformacion);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUNEXlogo = new JLabel("");
		lblUNEXlogo.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/little_logo.png")));
		lblUNEXlogo.setBounds(996, 501, 86, 127);
		contentPane.add(lblUNEXlogo);

		JComboBox comboBox_Estrategia_Reemplazamiento = new JComboBox();
		comboBox_Estrategia_Reemplazamiento
				.setModel(new DefaultComboBoxModel(new String[] { "Estacionario", "Generacional" }));
		comboBox_Estrategia_Reemplazamiento.setBounds(241, 59, 155, 21);
		contentPane.add(comboBox_Estrategia_Reemplazamiento);

		JLabel lblNewLabel = new JLabel("Estrategia de reemplazamiento");
		lblNewLabel.setBounds(10, 59, 221, 13);
		contentPane.add(lblNewLabel);

		JLabel lblEstrategiaOperadorDe = new JLabel("Operador de cruce");
		lblEstrategiaOperadorDe.setBounds(10, 102, 221, 13);
		contentPane.add(lblEstrategiaOperadorDe);

		JComboBox comboBox_Operador_de_cruce = new JComboBox();
		comboBox_Operador_de_cruce.setModel(
				new DefaultComboBoxModel(new String[] { "Cruce en un punto", "Cruce multipunto", "Cruce uniforme" }));
		comboBox_Operador_de_cruce.setBounds(241, 102, 155, 21);
		contentPane.add(comboBox_Operador_de_cruce);

		JLabel lblOperadorDeMutacin = new JLabel("Operador de mutaci\u00F3n");
		lblOperadorDeMutacin.setBounds(10, 144, 221, 13);
		contentPane.add(lblOperadorDeMutacin);

		JComboBox comboBox_Operador_de_mutacion = new JComboBox();
		comboBox_Operador_de_mutacion.setModel(new DefaultComboBoxModel(
				new String[] { "Distribuci\u00F3n uniforme", "Distribuci\u00F3n normal", "Aleatorio" }));
		comboBox_Operador_de_mutacion.setBounds(241, 144, 155, 21);
		contentPane.add(comboBox_Operador_de_mutacion);

		JLabel lblAlgoritmoDeSeleccin = new JLabel("Algoritmo de selecci\u00F3n de individuos");
		lblAlgoritmoDeSeleccin.setBounds(10, 189, 221, 13);
		contentPane.add(lblAlgoritmoDeSeleccin);

		JComboBox comboBox_Algoritmo_seleccion_de_individuos = new JComboBox();
		comboBox_Algoritmo_seleccion_de_individuos
				.setModel(new DefaultComboBoxModel(new String[] { "M\u00E9todo de la ruleta", "M\u00E9todo rank",
						"Selecci\u00F3n competitiva", "Selecci\u00F3n truncada" }));
		comboBox_Algoritmo_seleccion_de_individuos.setBounds(241, 189, 155, 21);
		contentPane.add(comboBox_Algoritmo_seleccion_de_individuos);

		JLabel lblPoblacin = new JLabel("Poblaci\u00F3n");
		lblPoblacin.setBounds(10, 244, 165, 13);
		contentPane.add(lblPoblacin);

		JLabel lblIteraciones = new JLabel("Iteraciones");
		lblIteraciones.setBounds(10, 281, 165, 13);
		contentPane.add(lblIteraciones);

		JSpinner spinner_poblacion = new JSpinner();
		spinner_poblacion.setModel(new SpinnerNumberModel(new Integer(50), new Integer(2), null, new Integer(1)));
		spinner_poblacion.setBounds(206, 244, 86, 20);
		contentPane.add(spinner_poblacion);

		JSpinner spinner_iteraciones = new JSpinner();
		spinner_iteraciones.setModel(new SpinnerNumberModel(new Integer(150), new Integer(5), null, new Integer(1)));
		spinner_iteraciones.setBounds(206, 281, 86, 20);
		contentPane.add(spinner_iteraciones);

		JSpinner spinner_cruce = new JSpinner();
		spinner_cruce.setModel(new SpinnerNumberModel(80, 0, 100, 1));
		spinner_cruce.setBounds(206, 398, 86, 20);
		contentPane.add(spinner_cruce);

		JLabel lblIteraciones_1_1 = new JLabel("Probabilidad de cruce (%)");
		lblIteraciones_1_1.setBounds(10, 398, 165, 13);
		contentPane.add(lblIteraciones_1_1);

		JSpinner spinner_mutacion = new JSpinner();
		spinner_mutacion.setModel(new SpinnerNumberModel(10, 0, 100, 1));
		spinner_mutacion.setBounds(206, 357, 86, 20);
		contentPane.add(spinner_mutacion);

		JLabel lblProbabilidadDeMutacin = new JLabel("Probabilidad de mutaci\u00F3n (%)");
		lblProbabilidadDeMutacin.setBounds(10, 357, 165, 13);
		contentPane.add(lblProbabilidadDeMutacin);

		JLabel lblElitista = new JLabel("Elitista");
		lblElitista.setBounds(10, 320, 165, 13);
		contentPane.add(lblElitista);

		JSpinner spinner_elitista = new JSpinner();
		spinner_elitista.setModel(new SpinnerNumberModel(new Integer(7), new Integer(0), null, new Integer(1)));
		spinner_elitista.setBounds(206, 320, 86, 20);
		contentPane.add(spinner_elitista);

		JButton btn_Mostrar_graficas = new JButton("Mostrar gr\u00E1ficas");
		btn_Mostrar_graficas.setBounds(124, 566, 165, 21);
		contentPane.add(btn_Mostrar_graficas);

		JLabel lblAlgoritmo = new JLabel("Algoritmo");
		lblAlgoritmo.setBounds(10, 20, 221, 13);
		contentPane.add(lblAlgoritmo);

		JComboBox comboBox_Algoritmo = new JComboBox();
		comboBox_Algoritmo.setModel(new DefaultComboBoxModel(new String[] { "Rastrigin", "Bukin", "Beale", "Ackley" }));
		comboBox_Algoritmo.setBounds(241, 20, 155, 21);
		contentPane.add(comboBox_Algoritmo);

		JSpinner spinner_numero_torneos = new JSpinner();
		spinner_numero_torneos.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_numero_torneos.setBounds(206, 456, 86, 20);
		contentPane.add(spinner_numero_torneos);

		JLabel lblNmeroDeTorneos = new JLabel("N\u00FAmero de torneos");
		lblNmeroDeTorneos.setBounds(10, 456, 165, 13);
		contentPane.add(lblNmeroDeTorneos);

		JSpinner spinner_individuos_fuera = new JSpinner();
		spinner_individuos_fuera.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_individuos_fuera.setBounds(206, 489, 86, 20);
		contentPane.add(spinner_individuos_fuera);

		JLabel lblFuncinAlgoritmo = new JLabel("Funci\u00F3n algoritmo");
		lblFuncinAlgoritmo.setBounds(461, 24, 221, 13);
		contentPane.add(lblFuncinAlgoritmo);

		JLabel lblConsola = new JLabel("Consola");
		lblConsola.setBounds(461, 339, 221, 13);
		contentPane.add(lblConsola);

		JLabel lbl_imagen_funcion = new JLabel("");
		lbl_imagen_funcion.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/Rastrigin.png")));
		lbl_imagen_funcion.setBounds(461, 63, 565, 252);
		contentPane.add(lbl_imagen_funcion);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(464, 376, 437, 164);
		contentPane.add(scrollPane);

		JTextArea textArea_Console = new JTextArea();
		scrollPane.setViewportView(textArea_Console);
		textArea_Console.setWrapStyleWord(true);
		textArea_Console.setLineWrap(true);
		textArea_Console.setEditable(false);

		JLabel lblIndividuos_sacar = new JLabel("Individuos a sacar");
		lblIndividuos_sacar.setBounds(10, 492, 165, 13);
		contentPane.add(lblIndividuos_sacar);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/white_background.png")));
		lblBackground.setBounds(0, 0, 1096, 688);
		contentPane.add(lblBackground);

		// -------------------- FUNCIONES --------------------

		comboBox_Algoritmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBox_Algoritmo.getSelectedIndex() != 0) {
					// Si no estamos con Rastrigin, el cruce multipunto no puede darse.
					comboBox_Operador_de_cruce.removeAllItems();
					comboBox_Operador_de_cruce.addItem("Cruce en un punto");
					comboBox_Operador_de_cruce.addItem("Cruce uniforme");

				} else {
					comboBox_Operador_de_cruce.removeAllItems();
					comboBox_Operador_de_cruce.addItem("Cruce en un punto");
					comboBox_Operador_de_cruce.addItem("Cruce multipunto");
					comboBox_Operador_de_cruce.addItem("Cruce uniforme");
				}

				switch (comboBox_Algoritmo.getSelectedItem().toString()) {

				case "Rastrigin":
					lbl_imagen_funcion.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/Rastrigin.png")));
					break;

				case "Bukin":
					lbl_imagen_funcion.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/Bukin.png")));
					break;

				case "Beale":
					lbl_imagen_funcion.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/Beale.png")));
					break;

				case "Ackley":
					lbl_imagen_funcion.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/Ackley.png")));
					break;

				}
			}
		});
		lblIndividuos_sacar.setVisible(false);
		spinner_individuos_fuera.setVisible(false);

		lblNmeroDeTorneos.setVisible(false);
		spinner_numero_torneos.setVisible(false);

		comboBox_Algoritmo_seleccion_de_individuos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (comboBox_Algoritmo_seleccion_de_individuos.getSelectedItem().toString()) {
				case "Selección competitiva":
					lblIndividuos_sacar.setVisible(false);
					spinner_individuos_fuera.setVisible(false);

					lblNmeroDeTorneos.setVisible(true);
					spinner_numero_torneos.setVisible(true);
					break;

				case "Selección truncada":
					lblIndividuos_sacar.setVisible(true);
					spinner_individuos_fuera.setVisible(true);

					lblNmeroDeTorneos.setVisible(true);
					spinner_numero_torneos.setVisible(true);
					break;
				default:
					lblIndividuos_sacar.setVisible(false);
					spinner_individuos_fuera.setVisible(false);

					lblNmeroDeTorneos.setVisible(false);
					spinner_numero_torneos.setVisible(false);
					break;
				}
			}
		});

		btn_Mostrar_graficas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int n_torneos = (int) spinner_numero_torneos.getValue();
				int n_individuos_sacar = (int) spinner_individuos_fuera.getValue();
				int poblacion = (int) spinner_poblacion.getValue() + (int) spinner_elitista.getValue();

				String[] argumentos = new String[12];
				argumentos[0] = comboBox_Algoritmo.getSelectedItem().toString();
				argumentos[1] = comboBox_Estrategia_Reemplazamiento.getSelectedItem().toString();
				argumentos[2] = comboBox_Operador_de_cruce.getSelectedItem().toString();
				argumentos[3] = comboBox_Operador_de_mutacion.getSelectedItem().toString();
				argumentos[4] = comboBox_Algoritmo_seleccion_de_individuos.getSelectedItem().toString();
				argumentos[5] = spinner_poblacion.getValue().toString();
				argumentos[6] = spinner_iteraciones.getValue().toString();
				argumentos[7] = spinner_elitista.getValue().toString();
				argumentos[8] = spinner_mutacion.getValue().toString();
				argumentos[9] = spinner_cruce.getValue().toString();
				argumentos[10] = spinner_numero_torneos.getValue().toString();
				argumentos[11] = spinner_individuos_fuera.getValue().toString();

				// Algoritmo Rastrigin.
				Thread t_rastrigin = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new EjecucionRastrigin().main(argumentos);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

				// Algoritmo Bukin.
				Thread t_bukin = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new EjecucionBukin().main(argumentos);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

				// Algoritmo Beale.
				Thread t_beale = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new EjecucionBeale().main(argumentos);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

				// Algoritmo Ackley.
				Thread t_ackley = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new EjecucionAckley().main(argumentos);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

				boolean fallo = false;

				// Hacemos la comprobación de los campos del algoritmo de selección.
				// Si hemos elegido:
				// - Selección competitiva: el número de torneos no puede ser mayor que la mitad
				// de población.
				// - Selección truncada: el número de torneos no puede ser mayor que la mitad de
				// población, tampoco el número de invididuos a sacar.
				// Si se detecta algún problema, fallo=true y no podrá ejecutarse el algoritmo.
				switch (comboBox_Algoritmo_seleccion_de_individuos.getSelectedItem().toString()) {
				case "Selección competitiva":
					if (n_torneos > (poblacion / 2)) {
						JOptionPane.showMessageDialog(null,
								"El número de torneos es mayor que la mitad de la población. Por favor, introduzca un número menor."
										+ comboBox_Algoritmo_seleccion_de_individuos.getSelectedItem().toString(),
								"Error", JOptionPane.ERROR_MESSAGE);
						fallo = true;
					}
					break;

				case "Selección truncada":
					if (n_torneos > (poblacion / 2)) {
						JOptionPane.showMessageDialog(null,
								"El número de torneos es mayor que la mitad de la población. Por favor, introduzca un número menor."
										+ comboBox_Algoritmo_seleccion_de_individuos.getSelectedItem().toString(),
								"Alert", JOptionPane.ERROR_MESSAGE);
						fallo = true;
					}
					if (n_individuos_sacar > (poblacion / 2)) {
						JOptionPane.showMessageDialog(null,
								"El número de individuos que sacar es mayor que la mitad de la población. Por favor, introduzca un número menor."
										+ comboBox_Algoritmo_seleccion_de_individuos.getSelectedItem().toString(),
								"Error", JOptionPane.ERROR_MESSAGE);
						fallo = true;
					}

					break;

				default:

					break;
				}

				// Si no hemos detectado ningún fallo.
				if (fallo == false) {
					// Selección del algoritmo.
					switch (comboBox_Algoritmo.getSelectedItem().toString()) {

					case "Rastrigin":
						t_rastrigin.start();
						break;

					case "Bukin":
						t_bukin.start();
						break;

					case "Beale":
						t_beale.start();
						break;

					case "Ackley":
						t_ackley.start();
						break;

					}

					textArea_Console.setText("");

					System.setOut(new PrintStream(new OutputStream() {
						@Override
						public void write(int b) throws IOException {
							textArea_Console.append(String.valueOf((char) b));
						}
					}));

				}

			}
		});

	}
}
