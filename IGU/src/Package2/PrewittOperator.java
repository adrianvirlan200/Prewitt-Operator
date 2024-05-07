package Package2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

//aceasta clasa mosteneste clasa Convolution
public abstract class PrewittOperator extends Convolution {
	
	private static final String HORIZONTAL_FILTER = "Horizontal Filter";
	public HashMap<String, double[][]> getFilterMap() {
		return filterMap;
	}

	public static double[][] getX() {
		return x;
	}

	public static double[][] getY() {
		return y;
	}

	private static final String VERTICAL_FILTER = "Vertical Filter";
	private static double[][] FILTER_VERTICAL;
	private static double[][] FILTER_HORIZONTAL;
	private final HashMap<String, double[][]> filterMap;
	
	//constructor default al clasei
	public PrewittOperator() {
		filterMap = buildFilterMap();
	}

	private final static double[][] x = { { 1, 0, -1 }, { 1, 0, -1 }, { 1, 0, -1 } };
	private final static double[][] y = { { 1, 1, 1 }, { 0, 0, 0 }, { -1, -1, -1 } };
	
	//bloc static de initalizare
	static {
		if (true) {
			FILTER_VERTICAL = x;
			FILTER_HORIZONTAL = y;
		}
	}

	public File detectEdges(double[][][] image,int width, int height, String selectedFilter, String out) throws IOException {
		long start = System.currentTimeMillis();
		
		double[][] filter = filterMap.get(selectedFilter);
		double[][] convolvedPixels = applyConvolution(width, height, image, filter);
		
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		System.out.println("Prelucrarea imaginii a durat " + sec +" secunde");
		
		return createImageFromConvolutionMatrix(width, height, convolvedPixels, out);
	}

	public static double[][] getFILTER_VERTICAL() {
		return FILTER_VERTICAL;
	}

	public static void setFILTER_VERTICAL(double[][] fILTER_VERTICAL) {
		FILTER_VERTICAL = fILTER_VERTICAL;
	}

	public static double[][] getFILTER_HORIZONTAL() {
		return FILTER_HORIZONTAL;
	}

	public static void setFILTER_HORIZONTAL(double[][] fILTER_HORIZONTAL) {
		FILTER_HORIZONTAL = fILTER_HORIZONTAL;
	}

	public static String getHorizontalFilter() {
		return HORIZONTAL_FILTER;
	}

	public static String getVerticalFilter() {
		return VERTICAL_FILTER;
	}

	private double[][] applyConvolution(int width, int height, double[][][] image, double[][] filter) {
		//se instantiaza o clasa de tipul Convolution
		Convolution convolution = new Convolution();
		double[][] redConv = convolution.convolutionType2(image[0], height, width, filter, 3, 3, 1);
		double[][] greenConv = convolution.convolutionType2(image[1], height, width, filter, 3, 3, 1);
		double[][] blueConv = convolution.convolutionType2(image[2], height, width, filter, 3, 3, 1);
		double[][] finalConv = new double[redConv.length][redConv[0].length];
		for (int i = 0; i < redConv.length; i++) {
			for (int j = 0; j < redConv[i].length; j++) {
				finalConv[i][j] = redConv[i][j] + greenConv[i][j] + blueConv[i][j];
			}
		}
		return finalConv;
	}

	private File createImageFromConvolutionMatrix(int width, int height, double[][] imageRGB, String out)
			throws IOException {
		long start = System.currentTimeMillis();
		
		BufferedImage writeBackImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < imageRGB.length; i++) {
			for (int j = 0; j < imageRGB[i].length; j++) {
				Color color = new Color(fixOutOfRangeRGBValues(imageRGB[i][j]), fixOutOfRangeRGBValues(imageRGB[i][j]),
						fixOutOfRangeRGBValues(imageRGB[i][j]));
				writeBackImage.setRGB(j, i, color.getRGB());
			}
		}
		File outputFile = new File(".\\src\\" + out);
		ImageIO.write(writeBackImage, "png", outputFile);
		
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		System.out.println("Salvarea imaginii a durat " + sec +" secunde");
		
		return outputFile;
	}

	private int fixOutOfRangeRGBValues(double value) {
		if (value < 0.0) {
			value = -value;
		}
		if (value > 255) {
			return 255;
		} else {
			return (int) value;
		}
	}

	private HashMap<String, double[][]> buildFilterMap() {
		HashMap<String, double[][]> filterMap;
		filterMap = new HashMap<>();
		filterMap.put(VERTICAL_FILTER, FILTER_VERTICAL);
		filterMap.put(HORIZONTAL_FILTER, FILTER_HORIZONTAL);

		return filterMap;
	}

}
