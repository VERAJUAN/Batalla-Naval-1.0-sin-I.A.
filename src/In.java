
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

/**
 * La clase <b><code>In</code></b> permite que una aplicaci&oacute;n pueda leer 
 * datos de pantalla f&aacute;cilmente. Ha sido desarrollada para tener un 
 * comportamiento "similar" al <code>cin</code> de C++.
 * <p>
 * A diferencia de C++, en Java no existe la sobrecarga de operadores. Por ello,
 * la &uacute;nica forma posible de leer distintos tipos de datos es ofreciendo 
 * un conjunto de m&eacute;todos de entrada. Todos los m&eacute;todos de entrada 
 * definidos en esta clase (con s&oacute;lo una excepci&oacute;n) poseen el mismo 
 * formato:
 * <ul>
 *     <li><code>&lt;<i>tipo</i>&gt; read&lt;<i>Tipo</i>&gt;()</code><br>
 *     donde <code>&lt;<i>tipo</i>&gt;</code> representa el tipo de dato 
 *     retornado, pudiendo &eacute;ste ser <code>char</code>, <code>int</code>, 
 *     <code>long</code>, <code>float</code>, <code>double</code> o 
 *     <code>String</code>, y<br>
 *     <code>&lt;<i>Tipo</i>&gt;</code> es el mismo que fue indicado como tipo 
 *     de retorno, pero comenzando en may&uacute;sculas, o <code>Line</code>.
 * </ul>
 * 
 * Todos los m&eacute;todos son est&aacute;ticos (<code>static</code>) por lo 
 * que no es necesario crear una instancia de esta clase para realizar la 
 * lectura de datos. Es m&aacute;s, para evitar la innecesaria instanciaci&oacute;n
 * de <code><b>In</b></code> su constructor se ha definido privado. Esto impide
 * tanto la creaci&oacute;n de objetos de esta clase como su derivaci&oacute;n
 * o extensi&oacute;n.
 * <p>
 * El siguiente es un ejemplo de uso de los m&eacute;todos de entrada de esta
 * clase. Si ejecutamos el <code>main()</code> que sigue:
 * <blockquote><pre>
 * import utn.frc.io.In;
 * 
 * public static void main(String[] args) {
 *    int i;
 *    long lng;
 *    String str;
 *    String line;
 *    
 *    System.out.print("Introduzca un entero: ");
 *    i = In.readInt();
 *    System.out.print("Introduzca una cadena (sin espacios): ");
 *    str = In.readString();
 *    System.out.print("Introduzca un entero largo: ");
 *    lng = In.readLong();
 *    System.out.print("Introduzca una l&iacute;nea: ");
 *    line = In.readLine();
 *    
 *    System.out.println();
 *    System.out.println("Entero le&iacute;do: " + i);
 *    System.out.println("Entero largo le&iacute;do: " + lng);
 *    System.out.println("Cadena le&iacute;da: " + str);
 *    System.out.println("L&iacute;nea le&iacute;da: " + line);
 * }</pre></blockquote>
 * Podemos tener la siguiente ejecuci&oacute;n por pantalla:
 * <blockquote><pre>
 * Introduzca un entero: 123
 * Introduzca una cadena (sin espacios): hola!
 * Introduzca un entero largo: 45678
 * Introduzca una l&iacute;nea: Hola Mundo!
 * 
 * Entero le&iacute;do: 123
 * Entero largo le&iacute;do: 45678
 * Cadena le&iacute;da: hola!
 * L&iacute;nea le&iacute;da: Hola Mundo!</pre></blockquote>
 * 
 * Sin embargo, si realizamos una ejecuci&oacute;n diferente, podremos verificar
 * que los valores introducidos que no son utilizados por alguno de los 
 * m&eacute;todos <code>read&lt;<i>Tipo</i>&gt;()</code> quedan disponibles para
 * el siguiente <code>read&lt;<i>Tipo</i>&gt;()</code> que los pueda utilizar.
 * Por ejemplo:
 * <blockquote><pre>
 * Introduzca un entero: 123 hola!
 * Introduzca una cadena (sin espacios): Introduzca un entero largo: 45678 Hola Mundo!
 * Introduzca una l&iacute;nea: 
 * Entero le&iacute;do: 123
 * Entero largo le&iacute;do: 45678
 * Cadena le&iacute;da: hola!
 * L&iacute;nea le&iacute;da: Hola Mundo!</pre></blockquote>
 * 
 * @author Gustavo Garcia
 */
public class In {
    /**
     * Objeto utilizado para parsear la l&iacute;nea introducida por el usuario
     * mediante el teclado.
     * @see #getNextToken(String)
     */
    private static StringTokenizer st;
    /**
     * Objeto de I/O (entrada/salida) utilizado para leer los datos desde el 
     * teclado.
     * @see #getNextToken(String)
     */
    private static BufferedReader source;
    
    /**
     * Constructor privado implementado para evitar instanciaci&oacute;n y
     * derivaci&oacute;n.
     */
    private In() {}
    
    /**
     * M&eacute;todo utilizado para leer cadenas de caracteres que no contengan
     * espacios. Los siguientes caracteres son considerados espacios:
     * <ul>
     *     <li><code>' '</code>: espacio en blanco.
     *     <li><code>'\t'</code>: tabulaci&oacute;n (tab).
     *     <li><code>'\n'</code>: nueva l&iacute;nea, equivalente al [Enter] (new-line).
     *     <li><code>'\r'</code>: retorno de carro (carriage-return).
     *     <li><code>'\f'</code>: avance de p&aacute;gina (form-feed).
     * </ul>
     * @return la primer palabra completa que se haya introducido.
     * @see #readLine()
     */
    public static String readString() {
        return getNextToken();
    }
    
    /**
     * M&eacute;todo utilizado para leer cadenas de caracteres que contengan
     * espacios en blanco y/o tabulaciones.
     * @return la l&iacute;nea introducida hasta que se encuentre un [Enter].
     * @see #readString()
     */
    public static String readLine() {
        return getNextToken("\r\n\f");
    }
    
    /**
     * M&eacute;todo utilizado para leer n&uacute;meros enteros de 32 bits.
     * @return el n&uacute;mero introducido o 0 (cero) si el valor introducido
     * no puede ser interpretado como n&uacute;mero entero. Esto ocurre cuando 
     * se introducen letras o signos de puntuaci&oacute;n mezclados con el 
     * n&uacute;mero.
     * @see #readLong()
     */
    public static int readInt() {
        return (int)readLong();
    }
    
    /**
     * M&eacute;todo utilizado para leer n&uacute;meros enteros de 64 bits.
     * @return el n&uacute;mero introducido o 0L (cero) si el valor introducido
     * no puede ser interpretado como n&uacute;mero entero. Esto ocurre cuando 
     * se introducen letras o signos de puntuaci&oacute;n mezclados con el 
     * n&uacute;mero.
     * @see #readInt()
     */
    public static long readLong() {
        long retVal = 0;
        try {
            retVal = Long.parseLong(getNextToken());
        } catch (NumberFormatException e) {}
        return retVal;
    }
    
    /**
     * M&eacute;todo utilizado para leer n&uacute;meros reales de 32 bits de 
     * precisi&oacute;n.
     * @return el n&uacute;mero introducido o 0.0F (cero) si el valor introducido
     * no puede ser interpretado como n&uacute;mero entero. Esto ocurre cuando 
     * se introducen letras o signos de puntuaci&oacute;n mezclados con el 
     * n&uacute;mero. Los &uacute;nicos signos permitidos son el - (menos) al
     * comienzo del n&uacute;mero y una &uacute;nica aparici&oacute;n del . 
     * (punto decimal).
     * @see #readDouble()
     */
    public static float readFloat() {
        return (float)readDouble();
    }
    
    /**
     * M&eacute;todo utilizado para leer n&uacute;meros reales de 64 bits de 
     * precisi&oacute;n.
     * @return el n&uacute;mero introducido o 0.0 (cero) si el valor introducido
     * no puede ser interpretado como n&uacute;mero entero. Esto ocurre cuando 
     * se introducen letras o signos de puntuaci&oacute;n mezclados con el 
     * n&uacute;mero. Los &uacute;nicos signos permitidos son el - (menos) al
     * comienzo del n&uacute;mero y una &uacute;nica aparici&oacute;n del . 
     * (punto decimal).
     * @see #readFloat()
     */
    public static double readDouble() {
        double retVal = 0.0;
        try {
            retVal = Double.valueOf(getNextToken()).doubleValue();
        } catch (NumberFormatException e) {}
        return retVal;
    }
    
    /**
     * M&eacute;todo utilizado para leer un caracter.
     * @return el primer caracter de la cadena introducida o '\0' (caracter nulo) 
     * si se introdujo una cadena vac&iacute;a.
     * @see #readString()
     */
    public static char readChar() {
        char car = '\0';
        String str;
        
        str = getNextToken("\0");
        if (str.length() > 0) {
            car = str.charAt(0);
            st = new StringTokenizer(str.substring(1));
        }
        return car;
    }
    
    /**
     * M&eacute;todo utilizado para obtener la siguiente palabra (o n&uacute;mero)
     * del objeto parseador de la entrada.
     * @return siguiente elemento del parseador de cadenas, considerando como 
     * separadores a los caracteres de espacio (ver documentaci&oacute;n de 
     * {@link #readString() readString()}).
     * @see #getNextToken(String)
     * @see #st
     * @see "Documentaci&oacute;n de la clase <code>StringTokenizer</code> en el
     * sitio oficial de Java: <a href="http://java.sun.com">http://java.sun.com</a>."
     */
    private static String getNextToken() {
        return getNextToken(null);
    }
    
    /**
     * M&eacute;todo utilizado para obtener el siguiente elemento del objeto 
     * parseador de la entrada. Los elementos estar&aacute;n definidos por el 
     * delimitador que se recibe como par&aacute;metro.
     * @param delim delimitador a utilizar durante el parseo de la entrada. Si
     * el par&aacute;metro es <code>null</code> se tomarán los delimitadores
     * indicados en {@link #readString() readString()}.
     * @return siguiente elemento del parseador de cadenas, considerando como 
     * separadores al par&aacute;metro recibido.
     * @see #getNextToken()
     * @see #st
     * @see "Documentaci&oacute;n de la clase <code>StringTokenizer</code> en el
     * sitio oficial de Java: <a href="http://java.sun.com">http://java.sun.com</a>."
     */
    private static String getNextToken(String delim) {
        String input;
        String retVal = "";
        
        try {
            if ((st == null) || !st.hasMoreElements()) {
                if (source == null) {
                    source = new BufferedReader(new InputStreamReader(System.in));
                }
                input = source.readLine();
                st = new StringTokenizer(input);
            }
            if (delim == null) {
                delim = " \t\n\r\f";
            }
            retVal = st.nextToken(delim);
        } catch (NoSuchElementException e1) {
            // si ocurre una excepción, no hacer nada
        } catch (IOException e2) {
            // si ocurre una excepción, no hacer nada
        }
        
        return retVal;
    }
}
