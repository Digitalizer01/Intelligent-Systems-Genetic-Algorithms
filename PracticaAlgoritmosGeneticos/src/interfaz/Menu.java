package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 439, 628);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Ayuda");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu_1.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox_Estrategia_Reemplazamiento = new JComboBox();
		comboBox_Estrategia_Reemplazamiento.setModel(new DefaultComboBoxModel(new String[] {"Estacionario", "Generacional"}));
		comboBox_Estrategia_Reemplazamiento.setBounds(241, 59, 155, 21);
		contentPane.add(comboBox_Estrategia_Reemplazamiento);
		
		JLabel lblNewLabel = new JLabel("Estrategia de reemplazamiento");
		lblNewLabel.setBounds(10, 59, 165, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblEstrategiaOperadorDe = new JLabel("Operador de cruce");
		lblEstrategiaOperadorDe.setBounds(10, 102, 165, 13);
		contentPane.add(lblEstrategiaOperadorDe);
		
		JComboBox comboBox_Operador_de_cruce = new JComboBox();
		comboBox_Operador_de_cruce.setModel(new DefaultComboBoxModel(new String[] {"Cruce en un punto", "Cruce multipunto", "Cruce uniforme"}));
		comboBox_Operador_de_cruce.setBounds(241, 102, 155, 21);
		contentPane.add(comboBox_Operador_de_cruce);
		
		JLabel lblOperadorDeMutacin = new JLabel("Operador de mutaci\u00F3n");
		lblOperadorDeMutacin.setBounds(10, 144, 165, 13);
		contentPane.add(lblOperadorDeMutacin);
		
		JComboBox comboBox_Operador_de_mutacion = new JComboBox();
		comboBox_Operador_de_mutacion.setModel(new DefaultComboBoxModel(new String[] {"Distribuci\u00F3n uniforme", "Distribuci\u00F3n normal", "Aleatorio"}));
		comboBox_Operador_de_mutacion.setBounds(241, 144, 155, 21);
		contentPane.add(comboBox_Operador_de_mutacion);
		
		JLabel lblAlgoritmoDeSeleccin = new JLabel("Algoritmo de selecci\u00F3n de individuos");
		lblAlgoritmoDeSeleccin.setBounds(10, 189, 192, 13);
		contentPane.add(lblAlgoritmoDeSeleccin);
		
		JComboBox comboBox_Algoritmo_seleccion_de_individuos = new JComboBox();
		comboBox_Algoritmo_seleccion_de_individuos.setModel(new DefaultComboBoxModel(new String[] {"M\u00E9todo de la ruleta", "M\u00E9todo rank", "Selecci\u00F3n competitiva", "Selecci\u00F3n truncada"}));
		comboBox_Algoritmo_seleccion_de_individuos.setBounds(241, 189, 155, 21);
		contentPane.add(comboBox_Algoritmo_seleccion_de_individuos);
		
		JLabel lblPoblacin = new JLabel("Poblaci\u00F3n");
		lblPoblacin.setBounds(10, 244, 52, 13);
		contentPane.add(lblPoblacin);
		
		JLabel lblIteraciones = new JLabel("Iteraciones");
		lblIteraciones.setBounds(10, 281, 52, 13);
		contentPane.add(lblIteraciones);
		
		JSpinner spinner_poblacion = new JSpinner();
		spinner_poblacion.setBounds(206, 244, 52, 20);
		contentPane.add(spinner_poblacion);
		
		JSpinner spinner_iteraciones = new JSpinner();
		spinner_iteraciones.setBounds(206, 281, 52, 20);
		contentPane.add(spinner_iteraciones);
		
		JButton btn_Generar_CSV = new JButton("Generar CSV");
		btn_Generar_CSV.setBounds(10, 497, 113, 21);
		contentPane.add(btn_Generar_CSV);
		
		JSpinner spinner_generaciones = new JSpinner();
		spinner_generaciones.setBounds(206, 322, 52, 20);
		contentPane.add(spinner_generaciones);
		
		JLabel lblCantidadGeneraciones = new JLabel("Cantidad generaciones");
		lblCantidadGeneraciones.setBounds(10, 322, 104, 13);
		contentPane.add(lblCantidadGeneraciones);
		
		JSpinner spinner_cruce = new JSpinner();
		spinner_cruce.setModel(new SpinnerNumberModel(0, null, 100, 1));
		spinner_cruce.setBounds(206, 439, 52, 20);
		contentPane.add(spinner_cruce);
		
		JLabel lblIteraciones_1_1 = new JLabel("Probabilidad de cruce (%)");
		lblIteraciones_1_1.setBounds(10, 439, 165, 13);
		contentPane.add(lblIteraciones_1_1);
		
		JSpinner spinner_mutacion = new JSpinner();
		spinner_mutacion.setModel(new SpinnerNumberModel(0, null, 100, 1));
		spinner_mutacion.setBounds(206, 398, 52, 20);
		contentPane.add(spinner_mutacion);
		
		JLabel lblProbabilidadDeMutacin = new JLabel("Probabilidad de mutaci\u00F3n (%)");
		lblProbabilidadDeMutacin.setBounds(10, 398, 155, 13);
		contentPane.add(lblProbabilidadDeMutacin);
		
		JLabel lblElitista = new JLabel("Elitista");
		lblElitista.setBounds(10, 361, 52, 13);
		contentPane.add(lblElitista);
		
		JSpinner spinner_elitista = new JSpinner();
		spinner_elitista.setBounds(206, 361, 52, 20);
		contentPane.add(spinner_elitista);
	}
}
