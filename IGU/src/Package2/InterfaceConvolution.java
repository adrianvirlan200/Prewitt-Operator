package Package2;

//interfata care contine toate metodele ce sunt folosite
//pentru a implementa operatia de convolutie
//aceasta clasa nu poate fi instantiata, ci va fi
//implementata de o alta clasa copil
public interface InterfaceConvolution {
	//metodele din aceasta clasa sunt abstarcte
	//acestea vor fi implementate(suprascriese) de clasele care vor mosteni aceasta interfata
	public abstract double singlePixelConvolution(double[][] input,
								            int x, int y,
								            double[][] k,
								            int kernelWidth,
								            int kernelHeight);
	 public abstract double[][] convolution2D(double[][] input,
							             int width, int height,
							             double[][] kernel,
							             int kernelWidth,
							             int kernelHeight);
	 public abstract double[][] convolution2DPadded(double[][] input,
							              int width, int height,
							              double[][] kernel,
							              int kernelWidth,
							              int kernelHeight);
	 public abstract double[][] convolutionType2(double[][] input,
							              int width, int height,
							              double[][] kernel,
							              int kernelWidth, int kernelHeight,
							              int iterations);
}
