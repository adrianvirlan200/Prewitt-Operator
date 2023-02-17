package Package2;

public class Buffer {
	private double[][][] image;
	private boolean available = false;
	private int height;
	private int width;
	
	//constructor 
	Buffer(int x, int y) {
		image = new double[3][x][y];
		height = x;
		width = y;
	}

	private int p = 1;
	
	
	//metoda care da cate 1/4 din imagine 
	//functie sincrona se folosetse notify
	public synchronized double[] get(int i, int j) {
		//cat timp nu sunt pixeli de luat asteapta
		while (!available) {
			try {
				//System.out.print("c w\n");
				wait();
				// aici asteapta ca producatorul sa puna 1/4
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		double[] aux = new double[3];

		aux[0] = image[0][i][j];
		aux[1] = image[1][i][j];
		aux[2] = image[2][i][j];
		
		//se opreste cand a luat cate 1/4 din imagine
		if (i >= (height - 1) * (p / 4) && j >= width - 1) {
			System.out.print("Consumatorul a luat\t" + p++ + "/4 \tdin informatie\n");
			available = false;
			notifyAll();
		}

		return aux;
	}
	
	//metoda care pune in buffer cate 1/4 din imagine
	//functie sincrona se folosetse notify
	public synchronized void put(double r, double g, double b, int i, int j) {
		//cat timp bufferul e plin asteapta
		while (available) {
			try {
				//System.out.print("p w\n");
				wait();
				// aici consumatorul asteapta ca sa preia 1/4
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		image[0][i][j] = r;
		image[1][i][j] = g;
		image[2][i][j] = b;
		
		
		//se opreste cand a pus cate 1/4 din imagine
		if (i >= (height - 1) * (p / 4) && j >= width - 1) {
			System.out.print("Producatorul a pus\t" + p + "/4\tdin informatie\n");
			available = true;
			notifyAll();
		}
	}
}
