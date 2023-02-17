package Package2;

import java.awt.image.BufferedImage;

//mosteneste thread
public class Consumer extends Thread {

	private Buffer b;
	private BufferedImage bufferedImage;
	int width;
	int height;
	double[][][] image;
	
	//getter
	public double[][][] getImage() {
		return this.image;
	}
	
	
	//constructor seteaza bufferul, imagine si dim ei
	public Consumer(BufferedImage bufferedImage, Buffer b) {
		this.b = b;
		this.bufferedImage = bufferedImage;
		width = bufferedImage.getWidth();
		height = bufferedImage.getHeight();
		this.image = new double[3][height][width];
	}
	
	//suprascriere
	public void run() {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		double[] aux = new double[3];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				//preia pixelii din buffer
				aux = b.get(i, j);
				image[0][i][j] = aux[0];
				image[1][i][j] = aux[1];
				image[2][i][j] = aux[2];
			}
		}
	}
}
