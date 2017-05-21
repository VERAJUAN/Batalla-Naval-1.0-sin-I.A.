
public class posiciones {

    private int x, y, algo = 0, algo2 = 0;
    private GraphicsConsole v;

    public posiciones(GraphicsConsole ve) {
        v = ve;
        x = 43;
        y = 33;

    }

    public void coordenadax(int col, int c) {
        if (c == 2) {
            x = 40;
            if (col != 0) {
                for (int i = 0; i < col; i++) {
                    x = x + 27;
                }
            }
        } else {

            if (c == 3) {
                x = 39;
                if (col != 0) {
                    for (int i = 0; i < col; i++) {
                        x = x + 27;
                    }
                }
            } else {
                for (int i = 0; i < col; i++) {

                    x = x + 27;

                }
            }
        }
    }

    public void coordenaday(int fila, int c) {
        for (int j = 0; j < fila; j++) {
            if (fila != j - 1) {
                if (c == 2) {
                    if (j == 0) {
                        y = 37;
                    }
                } else if (c == 3) {
                    if (j == 0) {
                        y = 40;
                    }
                }

            }
            y = y + 27;
        }
    }

    public void impresion(int c, char ubicacion) {
        if (ubicacion == 'v' || ubicacion == 'V') {
            if (c == 0) {
                v.drawImage("destructorvertical.png", x, y);
            } else if (c == 2) {
                v.drawImage("corbetavertical.png", x, y);
            } else if (c == 3) {
                v.drawImage("lancha.png", x, y);

            }
        }
        if (ubicacion == 'h' || ubicacion == 'H') {
            if (c == 0) {
                v.drawImage("destructorhorizontal.png", x, y);
            } else if (c == 2) {
                v.drawImage("corbetahorizontal.png", x, y);

            }

        }
    }

    public void coordenadaTiro(int fil, int col) {

        x = 42;
        y = 40;
        for (int i = 0; i < col; i++) {
            x = x + 27;
        }
        for (int g = 0; g < fil; g++) {
            y = y + 27;

        }

    }

    public void coordenadaTiromio(int fil, int col) {
        x = 533;
        y = 40;
        for (int i = 0; i < col; i++) {
            x = x + 27;
        }
        for (int g = 0; g < fil; g++) {
            y = y + 27;

        }
    }

    public void tiros() {

        v.drawImage("fuego.png", x, y);

    }
    public void agua(){
        v.drawImage("agua.png",x,y);
    }
}
