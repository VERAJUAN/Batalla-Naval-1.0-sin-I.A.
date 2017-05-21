
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.print.*;

/**
 * La clase GraphicsConsole representa una ventana de trabajo sencilla para que
 * un programa basado en consola est�ndar pueda visualizar objetos gr�ficos
 * elementales como cuadrados, elipses, c�rculos, l�neas, etc. Tambi�n puede
 * desplegar cadenas de caracteres en fuentes diversas, y gestionar el color de
 * los objetos mostrados - [Versi�n 1.0].
 *
 * A partir de esta versi�n 1.1, se agrega tambi�n la funcionalidad de poder
 * desplegar una imagen que exista previamente en un archivo, y una opci�n de
 * men� que permite imprimir lo que se haya dibujado, en una sola hoja. La
 * impresi�n se recomienda con orientaci�n apaisada. Para imprimir en una sola
 * p�gina, la clase toma la gr�fica que se haya generado, y crea una imagen para
 * imprimir a una escala tal que pueda entrar en el �rea imprimible de la
 * p�gina, por lo cual es posible que la impresi�n produzca alg�n efecto de
 * estiramiento de la imagen con respecto a la que se ve en pantalla - [Versi�n
 * 1.1].
 *
 * Se agrega un simple m�todo clear() que permite limpiar el contenido completo
 * del �rea de dibujo - [Versi�n 1.1.1].
 *
 * @author Ing. Valerio Frittelli.
 * @version Junio de 2011 - Versi�n 1.1.1.
 */
public class GraphicsConsole extends JFrame {
    // atributo static: gestionado via Singleton Pattern.

    private static GraphicsConsole gc = new GraphicsConsole();
    // el objeto donde se har�n los dibujos...
    private Lienzo lienzo;
    // un objeto imagen para usar como doble buffer...
    private Image db;
    // el contenedor gr�fico del doble buffer...
    private Graphics cg;
    // las dimensiones de la pantalla al correr el programa...
    private Dimension dim;

    //********************************************************
    //************************************ M�todos privados...
    //********************************************************
    // constructor privado... Singleton Pattern.
    private GraphicsConsole() {
        super("Batalla Naval 1.0");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setMenu();
        this.setInitialLook();
        this.initGraphicArea();
        this.initButton();
    }

    // crea, activa y controla el men� superior de opciones.
    private void setMenu() {
        // activamos la barra del men� superior...
        JMenuBar barra = new JMenuBar();
        this.setJMenuBar(barra);

        // agregamos opciones horizontales en la barra del men�...
        JMenu file = new JMenu("Archivo");
        JMenu help = new JMenu("Ayuda");
        barra.add(file);
        barra.add(help);

        // agregamos los items de opciones en cada opci�n horizontal...
        JMenuItem exit = new JMenuItem("Ocultar");
        JMenuItem about = new JMenuItem("Sobre");
        JMenuItem print = new JMenuItem("Imprimir...");
        file.add(print);
        file.add(exit);
        help.add(about);

        // notificaciones de captura de eventos...
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });

        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doHelp();
            }
        });

        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doPrint();
            }
        });

    }

    // configura el tama�o y la posici�n inicial de la ventana...
    private void setInitialLook() {
        // tomamos la configuraci�n actual de la pantalla...
        Toolkit kit = Toolkit.getDefaultToolkit();
        dim = kit.getScreenSize();
        int alto = dim.height;
        int ancho = dim.width;

        // fijamos el ancho, el alto y las coordenadas de arranque... 
        this.setSize(ancho / 2, alto / 2);
        this.setLocation(ancho / 4, alto / 4);
    }

    // inicializa el �rea de dibujo de la ventana, agregando un tapiz blanco...
    private void initGraphicArea() {
        int alto = dim.height;
        int ancho = dim.width;

        lienzo = new Lienzo();
        db = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        cg = db.getGraphics();
        cg.setColor(Color.white);
        cg.fillRect(0, 0, ancho, alto);
        Container c = getContentPane();
        c.add(lienzo);
    }

    // agrega y configura el bot�n de salida de la ventana
    private void initButton() {
        JButton out = new JButton("Hide");
        out.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });

        JPanel jp = new JPanel();
        jp.add(out);

        Container c = this.getContentPane();
        c.add(BorderLayout.SOUTH, jp);
    }

    // cierra la ventana...
    private void doExit() {
        this.setVisible(false);
    }

    // muestra una ventanita con datos generales...
    private void doHelp() {
        String cad = "GraphicsConsole - Versi�n 1.1.1 - Junio de 2011";
        cad += "\nPowered by Ing. Valerio Frittelli";
        JOptionPane.showMessageDialog(this, cad);
    }

    // activa un proceso de impresi�n...
    private void doPrint() {
        // una clase local al m�todo... para controlar la impresi�n...
        class PrintHandler implements Printable {

            public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
                if (pi >= 1) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                Image img = db.getScaledInstance((int) pf.getImageableWidth(), (int) pf.getImageableHeight(), Image.SCALE_AREA_AVERAGING);
                g2.drawImage(img, 0, 0, GraphicsConsole.this);
                return Printable.PAGE_EXISTS;
            }
        }

        // ajustar configuraci�n de la p�gina... se recomienda impresi�n apaisada...
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.LANDSCAPE);

        PrintHandler ph = new PrintHandler();
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(ph, pf);

        // mostrar cuadro de impresi�n, y si todo est� bien, imprimir...
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //********************************************************
    //************************ M�todos p�blicos y est�ticos...
    //********************************************************
    /**
     * Obtiene el �nico objeto existente de la clase GraphicsConsole. Se aplica
     * el patr�n Singleton.
     *
     * @return el �nico objeto GraphicsConsole que la clase permite crear.
     */
    public static GraphicsConsole getInstance() {
        return gc;
    }

    /**
     * Emite un sonido de atenci�n.
     */
    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * Provoca una pausa de "milis" milisegundos en la ejecuci�n del programa.
     *
     * @param milis la cantidad de milisegundos que durar� la pausa en la
     * ejecuci�n.
     */
    public static void delay(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
        }
    }

    //********************************************************
    //************************************ M�todos p�blicos...
    //********************************************************
    /**
     * Borra el contenido completo del �rea de dibujo. El �rea ser� rellenada
     * con el color blanco.
     */
    public void clear() {
        int anc = ((BufferedImage) db).getWidth();
        int alt = ((BufferedImage) db).getHeight();
        cg.setColor(Color.white);
        this.clearRect(0, 0, anc, alt);
    }

    /**
     * Borra el contenido del �rea rectangular especificada por los par�metros.
     * Los l�mites izquierdo y derecho del �rea rectangular a limpiar son (x) y
     * (x + width) respectivamente. Los l�mites superior e inferior del
     * rect�ngulo son (y) y (y + height) respectivamente. Note que el �rea ser�
     * rellenada con el color usado hasta ese momento como color de dibujo para
     * la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     */
    public void clearRect(int x, int y, int width, int height) {
        cg.fillRect(x, y, width, height);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Copia el contenido del �rea en el rect�ngulo indicado, hacia otra �rea
     * desplazada en dx y dy pixels. El desplazamiento se har� de arriba hacia
     * abajo y hacia la derecha. Si se desea un desplazamiento hacia arriba o
     * hacia la izquierda, especifique valores negativos para dx y dy. Los
     * l�mites izquierdo y derecho del �rea rectangular a copiar son (x) y (x +
     * width) respectivamente. Los l�mites superior e inferior del rect�ngulo
     * son (y) y (y + height) respectivamente.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param dx desplazamiento horizontal para copiar los pixels.
     * @param dy desplazamiento vertical para copiar los pixels.
     */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        cg.copyArea(x, y, width, height, dx, dy);
        lienzo.repaint();
    }

    /**
     * Dibuja el contorno de un rect�ngulo con bordes trabajados para simular un
     * efecto de sobre relieve (raised = true) o bajo relieve (raised = false).
     * Los l�mites izquierdo y derecho del rect�ngulo son (x) y (x + width)
     * respectivamente, aunque gr�ficamente el rect�ngulo acupar� un �rea que
     * llegar� hasta (width + 1) y (height + 1). Los l�mites superior e inferior
     * del rect�ngulo son (y) y (y + height) respectivamente. El color usado
     * ser� el que en ese momento est� fijado como color de dibujo para la
     * ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param raised un boolean para indicar si el rect�ngulo debe aparecer
     * elevado o hundido en la ventana.
     */
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
        cg.draw3DRect(x, y, width, height, raised);
        lienzo.repaint(x, y, x + width + 1, y + height + 1);
    }

    /**
     * Dibuja el contorno de un arco circular o el�ptico, dentro de los l�mites
     * del rect�ngulo indicado. Los l�mites izquierdo y derecho del rect�ngulo
     * son (x) y (x + width) respectivamente. Los l�mites superior e inferior
     * del rect�ngulo son (y) y (y + height) respectivamente. El arco comienza a
     * dibujarse desde el �ngulo indicado por (startAngle) y proseguir� hasta
     * barrer un �ngulo equivalente a (arcAngle). El color usado ser� el que en
     * ese momento est� fijado como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param startAngle el �ngulo de inicio del arco a dibujar.
     * @param arcAngle el �ngulo que ser� barrido por el arco.
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        cg.drawArc(x, y, width, height, startAngle, arcAngle);
        lienzo.repaint(x, y, x + width + 1, y + height);
    }

    /**
     * Despliega tanto como sea posible una imagen, comenzando desde el punto
     * superior izquierdo cuyas coordenadas son (x, y). Si el m�todo logr�
     * cargar la imagen completamente, retorna true. Si no logr� cargar por
     * completo la imagen, retorna false. El par�metro img indica el nombre del
     * archivo que contiene a la imagen (que puede ser de tipo img, jpeg o png).
     * Si no se indica la ruta de directorios de ese archivo, se supondr� que el
     * mismo est� en la carpeta del proyecto.
     *
     * @param img el nombre del archivo que contiene a la imagen.
     * @param x coordenada de columna del punto superior izquierdo de la imagen.
     * @param y coordenada de fila del punto superior izquierdo.
     */
    public boolean drawImage(String img, int x, int y) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage(img);
        this.setIconImage(image);
        boolean ok = cg.drawImage(image, x, y, null);
        lienzo.repaint(x, y, image.getWidth(this), image.getHeight(this));
        this.setIconImage(null);
        return ok;
    }

    /**
     * Despliega tanto como sea posible una imagen, comenzando desde el punto
     * superior izquierdo cuyas coordenadas son (x, y). Si el m�todo logr�
     * cargar la imagen completamente, retorna true. Si no logr� cargar por
     * completo la imagen, retorna false. El par�metro img indica el nombre del
     * archivo que contiene a la imagen que puede ser de tipo img, jpeg o png).
     * Si no se indica la ruta de directorios de ese archivo, se supondr� que el
     * mismo est� en la carpeta del proyecto.
     *
     * @param img el nombre del archivo que contiene a la imagen.
     * @param x coordenada de columna del punto superior izquierdo de la imagen.
     * @param y coordenada de fila del punto superior izquierdo.
     * @param width el ancho del rect�ngulo que contendr� a la imagen.
     * @param height el alto del rect�ngulo.
     */
    public boolean drawImage(String img, int x, int y, int width, int height) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage(img);
        this.setIconImage(image);
        boolean ok = cg.drawImage(image, x, y, width, height, this);
        lienzo.repaint(x, y, width, height);
        this.setIconImage(null);
        return ok;
    }

    /**
     * Dibuja una l�nea, comenzando desde el punto (x1, y1) y terminando en el
     * punto (x2, y2). El color usado ser� el que en ese momento est� fijado
     * como color de dibujo para la ventana.
     *
     * @param x1 coordenada de columna del punto inicial de la l�nea.
     * @param y1 coordenada de fila del punto inicial.
     * @param x2 coordenada de columna del punto final de la l�nea.
     * @param y2 coordenada de fila del punto final.
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        cg.drawLine(x1, y1, x2, y2);
        lienzo.repaint(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Dibuja el contorno de un c�rculo o de una elipse, inscripta en el
     * rect�ngulo dado. Los l�mites izquierdo y derecho del rect�ngulo son (x) y
     * (x + width) respectivamente. Los l�mites superior e inferior del
     * rect�ngulo son (y) y (y + height) respectivamente. El color usado ser� el
     * que en ese momento est� fijado como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     */
    public void drawOval(int x, int y, int width, int height) {
        cg.drawOval(x, y, width, height);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Dibuja un pol�gono cerrado con (n) puntos a modo de v�rtices. Las
     * coordenadas (x, y) de cada uno de los (n) puntos est�n dadas por los
     * arreglos (xPoints) e (yPoints). Autom�ticamente ser� trazada una l�nea
     * para unir el �ltimo punto con el primero, salvo que se trate del mismo
     * punto. El color usado ser� el que en ese momento est� fijado como color
     * de dibujo para la ventana.
     *
     * @param xPoints un arreglo con los valores de las coordenadas de columna
     * de los puntos v�rtice del pol�gono.
     * @param yPoints un arreglo con los valores de las coordenadas de fila de
     * los puntos v�rtice del pol�gono.
     * @param n la cantidad de puntos a usar para dibujar el poligono.
     */
    public void drawPolygon(int[] xPoints, int[] yPoints, int n) {
        cg.drawPolygon(xPoints, yPoints, n);
        lienzo.repaint();
    }

    /**
     * Dibuja el contorno de un rect�ngulo. Los l�mites izquierdo y derecho del
     * rect�ngulo son (x) y (x + width) respectivamente. Los l�mites superior e
     * inferior del rect�ngulo son (y) y (y + height) respectivamente. El color
     * usado ser� el que en ese momento est� fijado como color de dibujo para la
     * ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     */
    public void drawRect(int x, int y, int width, int height) {
        cg.drawRect(x, y, width, height);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Dibuja el contorno de un rect�ngulo con bordes redondeados. Los l�mites
     * izquierdo y derecho del rect�ngulo son (x) y (x + width) respectivamente.
     * Los l�mites superior e inferior del rect�ngulo son (y) y (y + height)
     * respectivamente. El color usado ser� el que en ese momento est� fijado
     * como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param arcWidth di�metro horizontal del arco usado en cada esquina.
     * @param arcHeight di�metro vertical del arco usado en cada esquina.
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        cg.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Dibuja (muestra...) el contenido de la cadena (s) usando la fuente y el
     * color actualmente activados en la ventana. La l�nea base del caracter m�s
     * a la izquierda de la cadena se ubicar� en la posici�n (x, y).
     *
     * @param s la cadena a dibujar.
     * @param x la coordenada x (columna).
     * @param y la coordenada y (fila).
     */
    public void drawString(String s, int x, int y) {
        FontMetrics fm = cg.getFontMetrics();
        Rectangle2D sb = fm.getStringBounds(s, cg);

        cg.drawString(s, x, y);

        int cx = (int) sb.getX();
        int cy = (int) sb.getY();
        int ancho = cx + (int) sb.getWidth();
        int alto = cy + (int) sb.getHeight();
        lienzo.repaint(cx, cy, ancho, alto);
    }

    /**
     * Dibuja y rellena (pinta por dentro) un rect�ngulo con bordes trabajados
     * para simular un efecto de sobre relieve (raised = true) o bajo relieve
     * (raised = false). Los l�mites izquierdo y derecho del rect�ngulo son (x)
     * y (x + width) respectivamente, aunque gr�ficamente el rect�ngulo acupar�
     * un �rea que llegar� hasta (width + 1) y (height + 1). Los l�mites
     * superior e inferior del rect�ngulo son (y) y (y + height)
     * respectivamente. El color usado ser� el que en ese momento est� fijado
     * como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param raised un boolean para indicar si el rect�ngulo debe aparecer
     * elevado o hundido en la ventana.
     */
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
        cg.fill3DRect(x, y, width, height, raised);
        lienzo.repaint(x - 1, y - 1, x + width + 1, y + height + 1);
    }

    /**
     * Dibuja y rellena un arco circular o el�ptico, dentro de los l�mites del
     * rect�ngulo indicado. Los l�mites izquierdo y derecho del rect�ngulo son
     * (x) y (x + width) respectivamente. Los l�mites superior e inferior del
     * rect�ngulo son (y) y (y + height) respectivamente. El arco comienza a
     * dibujarse desde el �ngulo indicado por (startAngle) y proseguir� hasta
     * barrer un �ngulo equivalente a (arcAngle). El color usado ser� el que en
     * ese momento est� fijado como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param startAngle el �ngulo de inicio del arco a dibujar.
     * @param arcAngle el �ngulo que ser� barrido por el arco.
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        cg.fillArc(x, y, width, height, startAngle, arcAngle);
        lienzo.repaint(x, y, x + width + 1, y + height);
    }

    /**
     * Dibuja un c�rculo o una elipse, pintada por dentro e inscripta en el
     * rect�ngulo dado. Los l�mites izquierdo y derecho del rect�ngulo son (x) y
     * (x + width) respectivamente. Los l�mites superior e inferior del
     * rect�ngulo son (y) y (y + height) respectivamente. El color usado ser� el
     * que en ese momento est� fijado como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     */
    public void fillOval(int x, int y, int width, int height) {
        cg.fillOval(x, y, width, height);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Dibuja un pol�gono cerrado y pintado por dentro con (n) puntos a modo de
     * v�rtices. Las coordenadas (x, y) de cada uno de los (n) puntos est�n
     * dadas por los arreglos (xPoints) e (yPoints). Autom�ticamente ser�
     * trazada una l�nea para unir el �ltimo punto con el primero, salvo que se
     * trate del mismo punto. El color usado ser� el que en ese momento est�
     * fijado como color de dibujo para la ventana.
     *
     * @param xPoints un arreglo con los valores de las coordenadas de columna
     * de los puntos v�rtice del pol�gono.
     * @param yPoints un arreglo con los valores de las coordenadas de fila de
     * los puntos v�rtice del pol�gono.
     * @param n la cantidad de puntos a usar para dibujar el poligono.
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int n) {
        cg.fillPolygon(xPoints, yPoints, n);
        lienzo.repaint();
    }

    /**
     * Dibuja un rect�ngulo pintado por dentro. Los l�mites izquierdo y derecho
     * del rect�ngulo son (x) y (x + width) respectivamente. Los l�mites
     * superior e inferior del rect�ngulo son (y) y (y + height)
     * respectivamente. El color usado ser� el que en ese momento est� fijado
     * como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     */
    public void fillRect(int x, int y, int width, int height) {
        cg.fillRect(x, y, width, height);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Dibuja un rect�ngulo pintado por dentro, con los bordes redondeados. Los
     * l�mites izquierdo y derecho del rect�ngulo son (x) y (x + width)
     * respectivamente. Los l�mites superior e inferior del rect�ngulo son (y) y
     * (y + height) respectivamente. El color usado ser� el que en ese momento
     * est� fijado como color de dibujo para la ventana.
     *
     * @param x coordenada de columna del punto superior izquierdo del
     * rect�ngulo.
     * @param y coordenada de fila del punto superior izquierdo del rect�ngulo.
     * @param width ancho (en pixels) del rect�ngulo.
     * @param height alto (en pixels) del rect�ngulo.
     * @param arcWidth di�metro horizontal del arco usado en cada esquina.
     * @param arcHeight di�metro vertical del arco usado en cada esquina.
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        cg.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        lienzo.repaint(x, y, x + width, y + height);
    }

    /**
     * Retorna el color actualmente usado en esta ventana.
     *
     * @return un objeto Color que representa al color actual.
     */
    public Color getColor() {
        return cg.getColor();
    }

    /**
     * Retorna la fuente de caracteres actualmente usada en esta ventana.
     *
     * @return un objeto Font que representa a la fuente actual.
     */
    public Font getFont() {
        return cg.getFont();
    }

    /**
     * Retorna las m�tricas de la fuente de caracteres actualmente usada en esta
     * ventana.
     *
     * @return un objeto FontMetrics que permite acceder a las propiedades de la
     * fuente actual.
     */
    public FontMetrics getFontMetrics() {
        return cg.getFontMetrics();
    }

    /**
     * Cambia el color de dibujo. El color usado desde la invocaci�n a este
     * m�todo en adelante, ser� el color (c) tomado como par�metro.
     *
     * @param c el nuevo color a usar.
     */
    public void setColor(Color c) {
        cg.setColor(c);
    }

    /**
     * Cambia el tipo de fuente usado para mostrar texto en esta ventana.
     *
     * @param f un objeto representando a la nueva fuente.
     */
    public void setFont(Font f) {
        cg.setFont(f);
    }

    /**
     * Cambia origen de coordenadas del contexto gr�fico usado, de forma que el
     * nuevo origen coincidir� con (x, y). Todas las coordenadas usadas de aqu�
     * en adelante ser�n relativas al nuevo origen.
     *
     * @param x la coordenada x del nuevo origen.
     * @param y la coordenada x del nuevo origen.
     */
    public void translate(int x, int y) {
        cg.translate(x, y);
    }

    //********************************************************
    //************************************* Clases privadas...
    //********************************************************
    // clase usada para representar al lienzo de dibujo usado 
    // para el doble buffering, y permitir un manejo adecuado 
    // del m�todo paintComponent()
    private class Lienzo extends JPanel {

        public Lienzo() {
            setBackground(Color.white);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(db, 0, 0, this);
        }
    }
}
