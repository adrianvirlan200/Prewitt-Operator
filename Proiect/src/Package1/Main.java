package Package1;

import Package2.ApplyPrewittOperator;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//se instantiaza un obiect din clasa ApplyPrewitt
		ApplyPrewittOperator obj = new ApplyPrewittOperator();
		
		//argumentele sunt cele 2 fisiere(din care se citeste imaginea si unde se scrie)
		if (args.length != 2) {
			System.out.print("Eroare, numar invalid de argumente");
		} else {
			//se incepe algoritmul
			obj.read();
			obj.prewittStart(args[0],args[1]);
		}
	}

}
