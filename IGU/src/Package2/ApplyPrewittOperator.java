package Package2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

//aceasta clasa mosteneste clasa PrewittOperator
//are rolul de a implementa restul motodelor necesare
//implamentarii algoritmului
public class ApplyPrewittOperator extends PrewittOperator {
	int filter;

	public int getFilter() {
		return filter;
	}

	public void setFilter(int filter) {
		this.filter = filter;
	}

	{
		System.out.print("Alege tipul de filtru: 0 orizontal, 1 vertical\n");
	}
	
	
	//constructor
	public ApplyPrewittOperator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//citeste ce tip de filtru se doreste
//	public void read() {
//		@SuppressWarnings("resource")
//		Scanner sc = new Scanner(System.in);
//		this.setFilter(sc.nextInt());
//	}
	
	//metoda ce porneste algoritmul
	public void prewittStart(String in, String out, int filter) throws InterruptedException {
		BufferedImage bufferedImage = null;
		
		//se citeste imaginea
		try {
			bufferedImage = ImageIO.read(new File(".\\src\\" + in));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		double[][][] image = new double[3][height][width];
		
		//se instantiaza clasele Buffer, Producer, Consumer
		Buffer b = new Buffer(height,width);
		Producer producer = new Producer(bufferedImage, b);
		Consumer consumer = new Consumer(bufferedImage, b);
		
		long start = System.currentTimeMillis();
		//se pornesc cele 2 threaduri sincrone
		producer.start();
		consumer.start();
		
		//se asteapta pe threadul principal ca ambele threaduri secundare sa 
		//finalizeze de citit imaginea
		producer.join();
		consumer.join();
		
		//se salveaza imaginea(matricea de pixeli)
		image = consumer.getImage();
		
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		System.out.println("Citirea imaginii a durat " + sec +" secunde");
		
		
		///////////
		
		//se aplica algoritul pe matricea citita
		//in funtie de tipul de filtru ales de utilizator
		if (filter == 0) {

			try {
				this.detectEdges(image, width, height ,"Horizontal Filter", out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				this.detectEdges(image, width,height , "Vertical Filter", out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
