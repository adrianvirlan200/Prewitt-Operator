package Package2;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
	private double[][][] image;
	private boolean available = false;
	private int height;
	private int width;
	
	private volatile float percentage = 0.0f;
	private List<ProgressListener> listeners = new ArrayList<>();
	public void addProgressListener(ProgressListener listener) {
	        listeners.add(listener);
	    }
	private void notifyProgressUpdate(float percentage) {
        for (ProgressListener listener : listeners) {
            listener.onProgressUpdate(percentage);
        }
    }
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage += percentage;
		notifyProgressUpdate(percentage);
	}

	//constructor 
	Buffer(int x, int y) {
		image = new double[3][x][y];
		height = x;
		width = y;
	}

	private int p = 1;
	
	public synchronized double[] get(int i, int j) {
		while (!available) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		double[] aux = new double[3];

		aux[0] = image[0][i][j];
		aux[1] = image[1][i][j];
		aux[2] = image[2][i][j];
		
		if (i >= (height - 1) * (p / 4) && j >= width - 1) {
			System.out.print("Consumatorul a luat\t" + p++ + "/4 \tdin informatie\n");
			available = false;
			
			setPercentage(0.25f);
			notifyProgressUpdate(percentage);
			
			notifyAll();
		}

		return aux;
	}
	
	public synchronized void put(double r, double g, double b, int i, int j) {
		while (available) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		image[0][i][j] = r;
		image[1][i][j] = g;
		image[2][i][j] = b;
		
		int totalPixels = height * width;
	    int processedPixels = i * width + j + 1;
		
		if (i >= (height - 1) * (p / 4) && j >= width - 1) {
			
			System.out.print("Producatorul a pus\t" + p + "/4\tdin informatie\n");
			available = true;
			notifyAll();
		}
	}
}
