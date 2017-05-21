
public class Tablero {

    private int[][] tablero;
    private int[][] tablero18;
    private int fila = 0, c = 0, i = 0, flag = 0, flag2 = 0, col = 0;
    private posiciones p;

    public Tablero(posiciones pos) {
        tablero = new int[10][10];
        tablero18 = new int[10][10];
        p = pos;


    }
    
    

    public boolean ctlIngreso(char fil, int col, char ubicacion) {
        boolean control = false;
        if (Caracter.letrasTablero(fil) && Caracter.letrasPosicion(ubicacion) && col >= 0 && col <= 9) {
            control = true;
        }

        if (Caracter.esLetMay(fil)) {
            fila = (int) (fil - 'A');
        } else if (Caracter.esLetMin(fil)) {
            fila = (int) (fil - 'a');
        }
        return control;
    }

    public boolean ctlBarcos(int var1, int fil, int col, char ubicacion, int i, int c) {
        boolean barcos = false;
        flag2 = var1;
        if (var1 != 17) {
            fila = fil;

        }

        if (tablero[fila][col] == 0) {
            if (i == 1) {
                barcos = true;
            } else {

                if (ubicacion == 'v' || ubicacion == 'V') {
                    if (fila < 7 + c) {
                        if (tablero[fila + 1][col] == 0) {
                            barcos = true;
                        }
                    }
                } else if (ubicacion == 'h' || ubicacion == 'H') {
                    if (col < 7 + c) {
                        if (tablero[fila][col + 1] == 0) {
                            barcos = true;

                        }
                    }

                }
            }
        }
        return barcos;
    }

    public void Ingreso(int fil, int col, char ubicacion, int i, int c) {

        if (ubicacion == 'v' || ubicacion == 'V') {

            for (int j = 0; j < 4 - c; j++) {
                if (i == 2) {
                    tablero[fila + j][col] = 5;
                } else if (i == 1) {
                    tablero[fila][col] = 7;
                } else {
                    tablero[fila + j][col] = 1 + c;
                }


            }
            if (flag2 == 17) {
                p.coordenadax(col, c);
                p.coordenaday(fila, c);
                p.impresion(c, ubicacion);
            }



        } else if (ubicacion == 'h' || ubicacion == 'H') {


            for (int j = 0; j < 4 - c; j++) {
                tablero[fila][col + j] = 1 + c;
                if (i == 2) {
                    tablero[fila][col + j] = 5;

                }
            }
            if (flag2 == 17) {
                p.coordenadax(col, c);
                p.coordenaday(fila, c);
                p.impresion(c, ubicacion);
            }
        }
    }

    public void copiaTablero() {

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                tablero18[y][x] = tablero[y][x];
                tablero[y][x] = 0;
            }
        }


    }

    public boolean tiromio(char fil, int col) {
        boolean a = true;
        if (tablero18[fila][col] == 9) {
        } else {
            if (tablero18[fila][col] == 0) {
                a = false;
                tablero18[fila][col] = 9;
                p.coordenadaTiromio(fila, col);
                p.agua();


            }
        }

        return a;

    }

    public boolean tiropc(int fil, int col) {
        boolean a = true;
        fila = fil;
        if (tablero[fila][col] == 9) {
        } else {
            if (tablero[fila][col] == 0) {
                a = false;
                tablero[fila][col] = 9;
                p.coordenadaTiro(fila, col);
                p.agua();
            }
        }

        return a;

    }

    public boolean Hundido(char fil, int col) {
        boolean b = false;
        int algo = 0;
        flag = 0;
        int barquito = 0;
        if (tablero18[fila][col] != 9) {
            b = true;
            barquito = tablero18[fila][col];
            tablero18[fila][col] = 9;

            if (barquito == 7) {
            } else {
                for (int l = 1; l < 4; l++) {
                    if (col > l) {
                        algo++;
                    }
                    if (tablero18[fila][col - algo] == barquito) {
                        flag = 1;
                    }
                }
                algo = 0;
                for (int l = 9; l > 6; l = l - 1) {
                    if (col < l) {
                        algo++;
                    }
                    if (tablero18[fila][col + algo] == barquito) {
                        flag = 1;
                    }
                }
                algo = 0;
                for (int l = 1; l < 4; l++) {
                    if (fila > l) {
                        algo++;
                    }
                    if (tablero18[fila - algo][col] == barquito) {
                        flag = 1;
                    }
                }
                algo = 0;
                for (int l = 9; l > 6; l = l - 1) {
                    if (fila < l) {
                        algo++;
                    }
                    if (tablero18[fila + algo][col] == barquito) {
                        flag = 1;
                    }
                }

            }
            p.coordenadaTiromio(fila, col);
            p.tiros();
        }
        return b;
    }

    public boolean HundidoPc(int fil, int col) {
        boolean b = false;
        int algo = 0;
        flag = 0;
        int barquito = 0;
        if (tablero[fila][col] != 9) {
            b = true;
            barquito = tablero[fila][col];
            tablero[fila][col] = 9;

            if (barquito == 7) {
            } else {
                for (int l = 1; l < 4; l++) {
                    if (col > l) {
                        algo++;
                    }
                    if (tablero[fila][col - algo] == barquito) {
                        flag = 1;
                    }
                }
                algo = 0;
                for (int l = 9; l > 6; l = l - 1) {
                    if (col < l) {
                        algo++;
                    }
                    if (tablero[fila][col + algo] == barquito) {
                        flag = 1;
                    }
                }
                algo = 0;
                for (int l = 1; l < 4; l++) {
                    if (fila > l) {
                        algo++;
                    }
                    if (tablero[fila - algo][col] == barquito) {
                        flag = 1;

                    }
                }
                algo = 0;
                for (int l = 9; l > 6; l = l - 1) {
                    if (fila < l) {
                        algo++;
                    }
                    if (tablero[fila + algo][col] == barquito) {
                        flag = 1;

                    }
                }

            }
            p.coordenadaTiro(fila, col);
            p.tiros();
        }

        return b;
    }

    public boolean verifHundido() {

        boolean d = false;
        if (flag == 1) {
            d = true;
        }

        return d;
    }

    public int verifGanador() {
        int f = 0;

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (tablero18[x][y] == 0 || tablero18[x][y] == 9) {
                    f++;
                }
            }
        }
        return f;
    }

    public int verifGanadorPc() {
        int f = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (tablero[x][y] == 0 || tablero[x][y] == 9) {
                    f++;
                }
            }
        }
        return f;
    }
    /* public void prueba(){
     System.out.println("A | "+tablero[0][0]+" | "+tablero[0][1]+" | "+tablero[0][2]+" | "+tablero[0][3]+" | "+tablero[0][4]+" | "+tablero[0][5]+" | "+tablero[0][6]+" | "+tablero[0][7]+" | "+tablero[0][8]+" | "+tablero[0][9]+" |\n"
     +"B | "+tablero[1][0]+" | "+tablero[1][1]+" | "+tablero[1][2]+" | "+tablero[1][3]+" | "+tablero[1][4]+" | "+tablero[1][5]+" | "+tablero[1][6]+" | "+tablero[1][7]+" | "+tablero[1][8]+" | "+tablero[1][9]+" |\n"
     +"C | "+tablero[2][0]+" | "+tablero[2][1]+" | "+tablero[2][2]+" | "+tablero[2][3]+" | "+tablero[2][4]+" | "+tablero[2][5]+" | "+tablero[2][6]+" | "+tablero[2][7]+" | "+tablero[2][8]+" | "+tablero[2][9]+" |\n"
     +"D | "+tablero[3][0]+" | "+tablero[3][1]+" | "+tablero[3][2]+" | "+tablero[3][3]+" | "+tablero[3][4]+" | "+tablero[3][5]+" | "+tablero[3][6]+" | "+tablero[3][7]+" | "+tablero[3][8]+" | "+tablero[3][9]+" |\n"
     +"E | "+tablero[4][0]+" | "+tablero[4][1]+" | "+tablero[4][2]+" | "+tablero[4][3]+" | "+tablero[4][4]+" | "+tablero[4][5]+" | "+tablero[4][6]+" | "+tablero[4][7]+" | "+tablero[4][8]+" | "+tablero[4][9]+" |\n"
     +"F | "+tablero[5][0]+" | "+tablero[5][1]+" | "+tablero[5][2]+" | "+tablero[5][3]+" | "+tablero[5][4]+" | "+tablero[5][5]+" | "+tablero[5][6]+" | "+tablero[5][7]+" | "+tablero[5][8]+" | "+tablero[5][9]+" |\n"
     +"G | "+tablero[6][0]+" | "+tablero[6][1]+" | "+tablero[6][2]+" | "+tablero[6][3]+" | "+tablero[6][4]+" | "+tablero[6][5]+" | "+tablero[6][6]+" | "+tablero[6][7]+" | "+tablero[6][8]+" | "+tablero[6][9]+" |\n"
     +"H | "+tablero[7][0]+" | "+tablero[7][1]+" | "+tablero[7][2]+" | "+tablero[7][3]+" | "+tablero[7][4]+" | "+tablero[7][5]+" | "+tablero[7][6]+" | "+tablero[7][7]+" | "+tablero[7][8]+" | "+tablero[7][9]+" |\n"
     +"I | "+tablero[8][0]+" | "+tablero[8][1]+" | "+tablero[8][2]+" | "+tablero[8][3]+" | "+tablero[8][4]+" | "+tablero[8][5]+" | "+tablero[8][6]+" | "+tablero[8][7]+" | "+tablero[8][8]+" | "+tablero[8][9]+" |\n"
     +"J | "+tablero[9][0]+" | "+tablero[9][1]+" | "+tablero[9][2]+" | "+tablero[9][3]+" | "+tablero[9][4]+" | "+tablero[9][5]+" | "+tablero[9][6]+" | "+tablero[9][7]+" | "+tablero[9][8]+" | "+tablero[9][9]+" |\n"
     + "-------------------------------------------------------------------------------------------------------------------\n"); }
     public void prueba2(){ 
     System.out.println  ("A | "+tablero18[0][0]+" | "+tablero18[0][1]+" | "+tablero18[0][2]+" | "+tablero18[0][3]+" | "+tablero18[0][4]+" | "+tablero18[0][5]+" | "+tablero18[0][6]+" | "+tablero18[0][7]+" | "+tablero18[0][8]+" | "+tablero18[0][9]+" |\n"
     +"B | "+tablero18[1][0]+" | "+tablero18[1][1]+" | "+tablero18[1][2]+" | "+tablero18[1][3]+" | "+tablero18[1][4]+" | "+tablero18[1][5]+" | "+tablero18[1][6]+" | "+tablero18[1][7]+" | "+tablero18[1][8]+" | "+tablero18[1][9]+" |\n"
     +"C | "+tablero18[2][0]+" | "+tablero18[2][1]+" | "+tablero18[2][2]+" | "+tablero18[2][3]+" | "+tablero18[2][4]+" | "+tablero18[2][5]+" | "+tablero18[2][6]+" | "+tablero18[2][7]+" | "+tablero18[2][8]+" | "+tablero18[2][9]+" |\n"
     +"D | "+tablero18[3][0]+" | "+tablero18[3][1]+" | "+tablero18[3][2]+" | "+tablero18[3][3]+" | "+tablero18[3][4]+" | "+tablero18[3][5]+" | "+tablero18[3][6]+" | "+tablero18[3][7]+" | "+tablero18[3][8]+" | "+tablero18[3][9]+" |\n"
     +"E | "+tablero18[4][0]+" | "+tablero18[4][1]+" | "+tablero18[4][2]+" | "+tablero18[4][3]+" | "+tablero18[4][4]+" | "+tablero18[4][5]+" | "+tablero18[4][6]+" | "+tablero18[4][7]+" | "+tablero18[4][8]+" | "+tablero18[4][9]+" |\n"
     +"F | "+tablero18[5][0]+" | "+tablero18[5][1]+" | "+tablero18[5][2]+" | "+tablero18[5][3]+" | "+tablero18[5][4]+" | "+tablero18[5][5]+" | "+tablero18[5][6]+" | "+tablero18[5][7]+" | "+tablero18[5][8]+" | "+tablero18[5][9]+" |\n"
     +"G | "+tablero18[6][0]+" | "+tablero18[6][1]+" | "+tablero18[6][2]+" | "+tablero18[6][3]+" | "+tablero18[6][4]+" | "+tablero18[6][5]+" | "+tablero18[6][6]+" | "+tablero18[6][7]+" | "+tablero18[6][8]+" | "+tablero18[6][9]+" |\n"
     +"H | "+tablero18[7][0]+" | "+tablero18[7][1]+" | "+tablero18[7][2]+" | "+tablero18[7][3]+" | "+tablero18[7][4]+" | "+tablero18[7][5]+" | "+tablero18[7][6]+" | "+tablero18[7][7]+" | "+tablero18[7][8]+" | "+tablero18[7][9]+" |\n"
     +"I | "+tablero18[8][0]+" | "+tablero18[8][1]+" | "+tablero18[8][2]+" | "+tablero18[8][3]+" | "+tablero18[8][4]+" | "+tablero18[8][5]+" | "+tablero18[8][6]+" | "+tablero18[8][7]+" | "+tablero18[8][8]+" | "+tablero18[8][9]+" |\n"
     +"J | "+tablero18[9][0]+" | "+tablero18[9][1]+" | "+tablero18[9][2]+" | "+tablero18[9][3]+" | "+tablero18[9][4]+" | "+tablero18[9][5]+" | "+tablero18[9][6]+" | "+tablero18[9][7]+" | "+tablero18[9][8]+" | "+tablero18[9][9]+" |\n");
             
     }    */
}