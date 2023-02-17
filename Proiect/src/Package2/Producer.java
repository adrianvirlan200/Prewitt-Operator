package Package2;

import java.awt.Color;
import java.awt.image.BufferedImage;

//clasa producator
//mosteneste thread
public class Producer extends Thread {
	private Buffer b;
	private BufferedImage bufferedImage;
	private int p = 1;
	
	public Buffer getB() {
		return b;
	}

	public void setB(Buffer b) {
		this.b = b;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	//constructor seteaza bufferul si imaginea
	public Producer(BufferedImage bufferedImage, Buffer b) {
		this.b = b;
		this.bufferedImage = bufferedImage;
	}
	
	//suprascriere
	public void run() {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color color = new Color(bufferedImage.getRGB(j, i));
				
				double[] ans = new double[3];
				ans[0] = color.getRed();
				ans[1] = color.getGreen();
				ans[2] = color.getBlue();
				
				//se pun in buffer valori
				b.put(ans[0], ans[1], ans[2], i, j);
				
				//se asteapata la fiecare 1/4 din informatie
				if (i >= (height - 1) * (p / 4) && j >= width - 1) {
					try {
						p++;
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

}
