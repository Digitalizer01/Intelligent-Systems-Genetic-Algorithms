package main;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Creates a real-time chart using SwingWorker
 */
public class SwingWorkerRealTime {

	MySwingWorker mySwingWorker;
	SwingWrapper<XYChart> sw;
	XYChart chart;

	public MySwingWorker getMySwingWorker() {
		return mySwingWorker;
	}

	public void setMySwingWorker(MySwingWorker mySwingWorker) {
		this.mySwingWorker = mySwingWorker;
	}

	public void go(String titleWindow, String graph_name, String x_name, String y_name) {

		// Create Chart
		chart = QuickChart.getChart(graph_name, x_name, y_name, "randomWalk", new double[] { 0 }, new double[] { 0 });
		chart.getStyler().setLegendVisible(false);
		chart.getStyler().setXAxisTicksVisible(true);
		
		// Show it
		sw = new SwingWrapper<XYChart>(chart);
		sw.setTitle(titleWindow);
		sw.displayChart();

		mySwingWorker = new MySwingWorker();
		mySwingWorker.execute();
	}

	public void finalizar_hilo_go() {
		mySwingWorker.cancel(true);
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public class MySwingWorker extends SwingWorker<Boolean, double[]> {

		LinkedList<Double> fifo = new LinkedList<Double>();

		public MySwingWorker() {

		}

		public Boolean insertar_valor(double dato) throws Exception {

			fifo.add(dato);

			// Eje y
			double[] array = new double[fifo.size()];
			for (int i = 0; i < fifo.size(); i++) {
				array[i] = fifo.get(i);

			}
			publish(array);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// eat it. caught when interrupt is called
				System.out.println("MySwingWorker shut down.");
			}

			return true;
		}

		@Override
		protected void process(List<double[]> chunks) {

			System.out.println("number of chunks: " + chunks.size());

			double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

			chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
			sw.repaintChart();

			long start = System.currentTimeMillis();
			long duration = System.currentTimeMillis() - start;
			try {
				Thread.sleep(40 - duration); // 40 ms ==> 25fps
				// Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
			} catch (InterruptedException e) {
			}

		}

		@Override
		protected Boolean doInBackground() throws Exception {

			return null;
		}

		public void finalizar_hilo_swingworker() {
			this.cancel(true);
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}
}