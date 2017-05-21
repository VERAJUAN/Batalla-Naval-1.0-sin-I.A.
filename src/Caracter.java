public class Caracter{
	
	public static boolean esLetra(char car){ 		// Retorna true si verdadero, false caso contrario
		boolean letra = false;
		if(esLetMay(car)) letra=true;
		if(esLetMin(car)) letra=true;
		return letra;      //  Retornemos lo que haya sido
	} //  esLetra()

	public static boolean esLetMay(char car){		//  Es letra may�scula ?
		boolean mayus = false;
		String mayu = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i < mayu.length();i++)
			if(mayu.charAt(i)==car) mayus = true;  //  Es una letra may�scula
		return mayus;
	}

	public static boolean esLetMin(char car){		//  Es letra minuscula ?
		boolean minus = false;
		String minu = "abcdefghijklmnopqrstuvwxyz";
		for(int i=0;i < minu.length();i++)
			if(minu.charAt(i)==car) minus = true;  //  Es una letra min�scula
		return minus;
	}
        public static boolean letrasTablero(char car){	
		boolean minus = false;
		String minu = "abcdefghijABCDEFGHIJ";
		for(int i=0;i < minu.length();i++)
		if(minu.charAt(i)==car) minus = true;
		return minus;
	}
        public static boolean letrasPosicion(char car){	
		boolean minus = false;
		String minu = "VHvh";
		for(int i=0;i < minu.length();i++)
		if(minu.charAt(i)==car) minus = true;
		return minus;
	}

	
     
	
        
}
