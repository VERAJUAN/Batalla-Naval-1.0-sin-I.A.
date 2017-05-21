
import java.awt.Color;
import java.awt.Font;

public class chewbacca {

    private int fil = 0, col = 0, ubi = 0, var = 0, c = 0, i = 0, var1 = 0, var2 = 0;
    private char ubicacion;

    public void Ingresochew(Tablero tableroh) {
        //Ingreso destructor
        do {
            fil = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
            ubi = (int) (Math.random() * 2);
            if (ubi == 0) {
                ubicacion = 'v';
            } else {
                ubicacion = 'h';
            }
            if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {
                var = 1;
                tableroh.Ingreso(fil, col, ubicacion, i, c);
            }

        } while (var == 0);



        //Ingreso primer corbeta
        do {
            fil = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
            ubi = (int) (Math.random() * 2);

            if (ubi == 0) {
                ubicacion = 'v';
            } else {
                ubicacion = 'h';
            }
            c = 2;
            if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {

                tableroh.Ingreso(fil, col, ubicacion, i, c);
                var = 1;
            } else {
                var = 0;
            }
        } while (var == 0);



        //Ingreso segunda corbeta
        do {
            fil = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
            ubi = (int) (Math.random() * 2);
            if (ubi == 0) {
                ubicacion = 'v';
            } else {
                ubicacion = 'h';
            }
            c = 2;
            if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {
                i = 2;
                tableroh.Ingreso(fil, col, ubicacion, i, c);
                var = 1;
            } else {
                var = 0;
            }
        } while (var == 0);


        //Ingreso de las lanchitas :D
        for (int m = 0; m < 3; m++) {
            do {
                fil = (int) (Math.random() * 10);
                col = (int) (Math.random() * 10);

                ubicacion = 'v';
                c = 3;
                i = 1;
                if (tableroh.ctlBarcos(var1, fil, col, ubicacion, i, c)) {

                    tableroh.Ingreso(fil, col, ubicacion, i, c);
                    var = 1;
                } else {
                    var = 0;
                }
            } while (var == 0);

        }

    }

    public void Tirochew(Tablero tableroh, posiciones p, Font g, GraphicsConsole v) {
        do {
            fil = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
            ubicacion = 'v';
            var2 = 0;


            if (tableroh.tiropc(fil, col)) {
                if (tableroh.HundidoPc(fil, col)) {

                    if (tableroh.verifHundido()) {
                        System.out.println("\nChewbacca le ha dado a un barco!.\n");

                    } else {
                        System.out.println("\nChewbacca te ha hundido un barco!");

                    }
                } else {
                    var2 = 1;
                }
            } else {
                System.out.println("\nChewbacca le ha dado al Agua!.\n");

            }
        } while (var2 == 1);

    }
}
