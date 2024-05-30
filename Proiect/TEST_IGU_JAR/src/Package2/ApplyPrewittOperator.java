package Package2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class ApplyPrewittOperator extends PrewittOperator {
	int filter;
	
	public float percentage;

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
	}
	
	public void prewittStart(String in, String out, int filter, ProgressListener listener, Boolean save, Boolean vertical, Boolean horizontal) throws InterruptedException {
		BufferedImage bufferedImage = null;
		
		this.save = save;
		
		try {
			bufferedImage = ImageIO.read(new File(".\\src\\" + in));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		double[][][] image = new double[3][height][width];
		
		Buffer b = new Buffer(height,width);
		b.addProgressListener(listener);
		Producer producer = new Producer(bufferedImage, b);
		Consumer consumer = new Consumer(bufferedImage, b);
		
		long start = System.currentTimeMillis();
		producer.start();
		consumer.start();
		
		producer.join();
		consumer.join();
		
		image = consumer.getImage();
		
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		System.out.println("Citirea imaginii a durat " + sec +" secunde");
		
		
		if (horizontal) {
			try {
				this.detectEdges(image, width, height ,"Horizontal Filter", "horizontal_" + out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		if (vertical) {
			try {
				this.detectEdges(image, width,height , "Vertical Filter", "vertical_" + out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
