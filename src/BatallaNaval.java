
import java.awt.Font;
import java.awt.Color;

public class BatallaNaval {

    public static void main(String[] args) {



        //Consola gráfica
        GraphicsConsole v = GraphicsConsole.getInstance();
        posiciones pos = new posiciones(v);
        chewbacca chew = new chewbacca();
        Tablero tableroh = new Tablero(pos);
        v.setVisible(true);
        v.setSize(1000, 450);
        v.setLocation(0, 30);
        v.drawImage("tablero.png", 10, 10);
        v.drawImage("tablero.png", 500, 10);
        v.drawImage("jugador.png", 330, 36);
        v.drawImage("chewbacca.png", 820, 36);

        char fil, ubicacion;
        int col = 0, fila = 0, var = 0, var2 = 0, var3 = 0, i = 0, c = 0, flag = 0, var1 = 0;


        Font f = new Font("TimesRoman", Font.BOLD, 20);
        v.setFont(f);
        v.setColor(Color.red);
        v.drawString("VERAJUAN", 340, 255);
        v.drawString("Chewbacca", 830, 255);

        Font g = new Font("TimesRoman", Font.BOLD, 40);
        v.setFont(g);
        v.setColor(Color.blue);


        System.out.println("BIENVENIDO A LA BATALLA NAVAL\n"
                + "Consideraciones previas a tener en cuenta:\n"
                + "*Se recomienda agregar de la siguiente forma: ingresar la letra de fila"
                + "\nseguido del ENTER, luego ingresar el número de columna, ENTER de nuevo y"
                + "\npor último, 'V' (si lo quiere vertical) o 'H' (si lo quiere horizontal)."
                + "\n*En caso de que el barco esté ubicado verticalmente, este se ordenará hacia abajo."
                + "\n*En caso de que el barco esté ubicado horizontalmente, este se ordenará hacia la derecha."
                + "\nEmpezaremos ubicando el Destructor, ingrese la coordenada:");

        chew.Ingresochew(tableroh);
        tableroh.copiaTablero();
        var1 = 17;

        do {
            fil = In.readChar();
            col = In.readInt();
            ubicacion = In.readChar();
            col = col - 1;
            if (tableroh.ctlIngreso(fil, col, ubicacion)) {

                if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {

                    tableroh.Ingreso(fila, col, ubicacion, i, c);
                    var = 0;
                } else {
                    System.out.println("El destructor colapsa con el tablero.\n"
                            + "Reingresar coordenadas y posición del destructor:");
                    var = 1;
                }
            } else {
                System.out.println("Los datos ingresados son incorrectos.\n"
                        + "Reingresar coordenadas y posición del Destructor:\n");
                var = 1;
            }
        } while (var == 1);

        System.out.println("\nAhora ingrese las coordenadas de la primer corbeta:");


        do {
            fil = In.readChar();
            col = In.readInt();
            ubicacion = In.readChar();
            col = col - 1;
            if (tableroh.ctlIngreso(fil, col, ubicacion)) {
                c = 2;

                if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {
                    tableroh.Ingreso(fila, col, ubicacion, i, c);
                    var = 0;
                } else {
                    System.out.println("La corbeta colapsa con otro barco o con el tablero.\n"
                            + "Reingresar coordenadas y posición de la primer corbeta:");
                    var = 1;
                }

            } else {
                System.out.println("\nLos datos ingresados son incorrectos.\n"
                        + "Reingresar coordenadas y posición de la primer corbeta:");
                var = 1;
            }
        } while (var == 1);

        System.out.println("\nAhora ingrese las coordenadas de la segunda corbeta:");

        do {
            fil = In.readChar();
            col = In.readInt();
            ubicacion = In.readChar();
            col = col - 1;
            if (tableroh.ctlIngreso(fil, col, ubicacion)) {
                c = 2;

                if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {
                    i = 2;
                    tableroh.Ingreso(fila, col, ubicacion, i, c);
                    var = 0;
                } else {
                    System.out.println("\nLa corbeta colapsa con otro barco o con el tablero.\n"
                            + "Reingresar coordenadas y posición de la segunda corbeta:");
                    var = 1;
                }
            } else {
                System.out.println("Los datos ingresados son incorrectos.\n"
                        + "Reingresar coordenadas y posición de la primer corbeta:");
                var = 1;
            }
        } while (var == 1);

        for (int m = 0; m < 3; m++) {

            System.out.println("\nAhora ingrese las coordenadas de la lancha " + (m + 1) + ":");

            do {
                fil = In.readChar();
                col = In.readInt();
                ubicacion = 'v';
                col = col - 1;

                if (tableroh.ctlIngreso(fil, col, ubicacion)) {
                    i = 1;
                    if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {
                        c = 3;
                        tableroh.Ingreso(fil, col, ubicacion, i, c);
                        var = 0;
                    } else {
                        System.out.println("\nLa lancha colapsa con otro barco o con el tablero.\n"
                                + "Reingresar coordenadas y posición de la primer corbeta:");
                        var = 1;
                    }
                } else {
                    System.out.println("\nLos datos ingresados son incorrectos.\n"
                            + "Reingresar coordenadas de la lancha " + (m + 1) + ":");
                    var = 1;
                }
            } while (var == 1);

        }


        //Los tiros

        //Turno tuyo
        do {
            System.out.println("\nTu turno de atacar:\n");
            do {
                fil = In.readChar();
                col = In.readInt();
                ubicacion = 'v';
                col = col - 1;
                if (tableroh.ctlIngreso(fil, col, ubicacion)) {
                    var2 = 0;
                    if (tableroh.tiromio(fil, col)) {
                        if (tableroh.Hundido(fil, col)) {
                            if (tableroh.verifHundido()) {
                                System.out.println("\nLo has tocado!.\n");

                            } else {
                                System.out.println("Lo Hundiste!");
                                v.beep();
                            }
                        } else {
                            System.out.println("\nYa has ingresado estas coordenadas, reingresar.\n");
                            var2 = 1;
                        }
                    } else {
                        System.out.println("\nLe has pegado al Agua.\n");

                    }
                } else {
                    System.out.println("\nCoordenadas incorrectas, reingresar.\n");
                    var2 = 1;
                }

            } while (var2 == 1);

            if (tableroh.verifGanador() != 100) {
                //Turno de Chewbacca!
                chew.Tirochew(tableroh, pos, g, v);
                if (tableroh.verifGanadorPc() == 100) {
                    System.out.println("HA GANADO Chewbacca!");
                    v.beep();
                    var3 = 1;
                }

            } else {
                System.out.println("HAS GANADO!!!");
                v.beep();
                var3 = 1;

            }


        } while (var3 != 1);
    }
}
