package main;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Ramos Hernández Juan José
 */
public class analizadorLexico {

    DataInputStream in;
    String bufferIn, cad;
    int i, errores = 0;

    public analizadorLexico(String f) throws FileNotFoundException {
        programa(f);
    }

    public void programa(String f) throws FileNotFoundException {
        in = new DataInputStream(new FileInputStream(f));//leemos nuestro archivo de entrada
        try {
            if ((bufferIn = in.readLine()) != null) {//Leemos la primera linea de entrada que tiene que ser 'inicio'
                cad = bufferIn.trim();//Esto seria el equivalente a transformarlo a String
                if (cad.equals("inicio")) {
                    bufferIn = in.readLine();
                    cad = bufferIn.trim();
                    if (cad.equals("fin"))//Verificamos que no haya terminado
                    {
                        error("Sentencia:", "No se declaro");
                    } else {
                        declaracion_sentencia();
                    }
                } else {
                    //No comenzo con 'inicio'
                }
            }
        } catch (IOException io) {

        }
    }

    public void declaracion_sentencia() throws IOException {
        i = 0;
        String aux = "";
        try {
            while (Character.isLetter(cad.charAt(i))) {
                aux += cad.charAt(i);
                i++;
            }//La i termina en la posicion del caracter que no es una letra
        } catch (Exception e) {
            error("do-while: buscaba un { encontro", "' '");
            return;
        }
        cad = cad.replaceAll(" ", "");
        switch (aux) {
            case "if":
                IF();
                break;
            case "do":
                DO_WHILE();
                break;
            case "while":
                WHILE();
                break;
            case "for":
                FOR();
                break;
            default:
                error("declaracion_sentencia:", "no se encontro la sentencia: " + aux);
        }
        if (errores == 0) {
            System.out.println("Sin errores");
        }
    }

    public boolean IF() {
        return false;
    }

    public boolean DO_WHILE() throws IOException {
        String aux = "";
        boolean esNum = false;
        boolean bandera = false;
        try {
            if (cad.charAt(i) == '{') {
                bufferIn = in.readLine();
                cad = bufferIn.trim();
                cad = cad.replaceAll(" ", "");
                i = 0;
                if (sentencia()) {
                    bufferIn = in.readLine();
                    cad = bufferIn.trim();
                    cad = cad.replaceAll(" ", "");
                    i = 0;
                    if (cad.charAt(i) == '}') {
                        i++;
                        while (Character.isLetter(cad.charAt(i))) {
                            aux += cad.charAt(i);
                            i++;
                        }
                        if (aux.equalsIgnoreCase("while")) {
                            if (cad.charAt(i) == '(') {
                                i++;
                                if (expresion_relacional()) {
                                    if (cad.charAt(i) == ')') {
                                        i++;
                                        try {
                                            if (cad.charAt(i) == ';') {
                                                bandera = true;
                                            } else {
                                                error("Do-While: buscaba un ; encontro", String.valueOf(cad.charAt(i)));
                                            }
                                        } catch (Exception e) {
                                            error("buscaba un ; encontro", "__");
                                        }
                                    } else {
                                        error("Do-While: buscaba un ) encontro", String.valueOf(cad.charAt(i)));
                                    }
                                } else {
                                    error("expresion_ralacional:", String.valueOf(cad.charAt(i)));
                                }
                            } else {
                                error("buscaba un ( encontro", String.valueOf(cad.charAt(i)));
                            }
                        } else {
                            error("buscaba la sentencia while y encontro", aux);
                        }
                    } else {
                        error("Do-While: buscaba un } encontro", String.valueOf(cad.charAt(i)));
                    }
                } else {
                    error("Sentencia:", String.valueOf(cad.charAt(i)) + " se desconoce");
                }
            } else {
                error("Do-While, buscaba un { encontro", String.valueOf(cad.charAt(i)));
            }
        } catch (Exception e) {
        }
        return bandera;
    }

    public boolean WHILE() {
        return false;
    }

    public boolean FOR() {
        boolean aux2 = false;
        String aux = "";
        if (cad.charAt(i) == '(') {
            aux += cad.charAt(i);
            i++;
            if (inicializacion()) {
                if (cad.charAt(i) == ';') {
                    aux += cad.charAt(i);
                    i++;
                    if (expresion_relacional()) {
                        if (cad.charAt(i) == ';') {
                            aux += cad.charAt(i);
                            i++;
                            if (incremento()) {
                                if (cad.charAt(i) == ')') {
                                    aux += cad.charAt(i);
                                    i++;
                                    if (cad.charAt(i) == '{') {
                                        aux += cad.charAt(i);
                                        i++;
                                        if (sentencia()) {
                                            if (cad.charAt(i) == '}') {
                                                aux += cad.charAt(i);
                                                i++;
                                                aux2 = true;
                                            } else {
                                                error("Buscaba un } y se encontro", String.valueOf(cad.charAt(i)));
                                            }
                                        }
                                    } else {
                                        error("Buscaba un { y se encontro", String.valueOf(cad.charAt(i)));
                                    }

                                } else {
                                    error("Buscaba un ) y se encontro", String.valueOf(cad.charAt(i)));
                                }
                            }

                        } else {
                            error("Buscaba un ; y se encontro", String.valueOf(cad.charAt(i)));
                        }
                    }
                } else {
                    error("Buscaba un ; y se encontro", String.valueOf(cad.charAt(i)));
                }
            }
        } else {
            error("Buscaba un ( y se encontro", String.valueOf(cad.charAt(i)));
        }
        return aux2;
    }

    public boolean expresion_relacional() {
        String aux2 = "";
        boolean aux = false;
        if (identificador()) {
            if (operador_logico()) {
                if (num_entero()) {
                    aux = true;
                }
                if (identificador()) {
                    aux = true;
                }
            }
        }
        return aux;
    }

    public boolean identificador() {
        boolean bandera = false;
        while (letra()) {
            i++;
            while (num_entero()) {
                i++;
            }
            bandera = true;
        }
        return bandera;
    }

    public boolean letra() {
        boolean aux = false;
        if (Character.isLetter(cad.charAt(i))) {
            aux = true;
        }
        return aux;
    }

    public boolean num_entero() {
        boolean aux = false;
        if (Character.isDigit(cad.charAt(i))) {
            aux = true;
        }
        return aux;
    }

    public boolean operador_logico() { //JUAN
        String aux = "";
        boolean aux2 = false;
        if (cad.charAt(i) == '>') {
            aux += cad.charAt(i);
            i++;
            aux2 = true;
            if (cad.charAt(i) == '=') {
                aux += cad.charAt(i);
                i++;
                aux2 = true;
            }
        } else if (cad.charAt(i) == '<') {
            aux += cad.charAt(i);
            i++;
            aux2 = true;
            if (cad.charAt(i) == '=') {
                aux += cad.charAt(i);
                i++;
                aux2 = true;
            }
        } else if (cad.charAt(i) == '=') {
            aux += cad.charAt(i);
            i++;
            aux2 = true;
            if (cad.charAt(i) == '=') {
                aux += cad.charAt(i);
                i++;
                aux2 = true;
            }
        }
        return aux2;
    }

    public boolean sentencia() {
        boolean aux = false;
        try {
            if (declaracion_var()) {
                aux = true;
            }
            if (expresion()) {
                aux = true;
            }
        } catch (Exception e) {
        }
        return aux;
    }

    public boolean declaracion_var() {
        boolean aux = false;
        if (cad.charAt(i) == 'i') {
            i++;
            if (cad.charAt(i) == 'n') {
                i++;
                if (cad.charAt(i) == 't') {
                    i++;
                    if (identificador()) {
                        if (cad.charAt(i) == ';') {
                            aux = true;
                        } else {
                            error("buscar un ; encontro", String.valueOf(cad.charAt(i)));
                        }
                    } else {
                        error("identificador:", String.valueOf(cad.charAt(i)));
                    }
                }
            }
        }
        return aux;
    }

    public boolean expresion() {
        boolean aux = false, esNum = false;
        if (identificador()) {
            if (operador()) {
                i++;
                if (identificador()) {
                    try {
                        if (cad.charAt(i) == ';') {
                            aux = true;
                        } else {
                            error("buscar un ; encontro", String.valueOf(cad.charAt(i)));
                        }
                    } catch (Exception e) {
                        error("buscar un ; encontro", "__");
                    }
                } else {
                    try {
                        while (num_entero()) {
                            esNum = true;
                            i++;
                        }
                        if (esNum) {
                            if (cad.charAt(i) == ';') {
                                aux = true;
                            } else {
                                error("buscaba un ; encontro", String.valueOf(cad.charAt(i)));
                            }
                        } else {
                            error("expresion:", "se esperaba un identificador o un numero");
                        }
                    } catch (Exception e) {
                        error("buscaba un ; encontro", "__");
                    }
                }
            } else {
                error("operador:", String.valueOf(cad.charAt(i)));
            }
        } else {
            error("expresion:", String.valueOf(cad.charAt(i)));
        }
        return aux;
    }

    public boolean operador() {
        if (cad.charAt(i) == '/' || cad.charAt(i) == '*' || cad.charAt(i) == '+' || cad.charAt(i) == '-') {
            return true;
        }
        return false;
    }

    public boolean inicializacion() { //JUAN
        boolean aux2 = false;
        String aux = "";
        if (cad.charAt(i) == 'i') {
            aux += cad.charAt(i);
            i++;
            if (cad.charAt(i) == 'n') {
                aux += cad.charAt(i);
                i++;
                if (cad.charAt(i) == 't') {
                    aux += cad.charAt(i);
                    i++;
                    if (identificador()) {
                        if (cad.charAt(i) == '=') {
                            aux += cad.charAt(i);
                            i++;
                            if (num_entero() == false) {
                                error("buscaba un numero y se encontro", String.valueOf(cad.charAt(i)));
                            } else {
                                aux2 = true;
                            }
                        } else {
                            error("buscaba un = y se encontro", String.valueOf(cad.charAt(i)));
                        }
                    }
                } else {
                    error("buscaba un t y se encontro", String.valueOf(cad.charAt(i)));
                }
            } else {
                error("buscaba un n y se encontro", String.valueOf(cad.charAt(i)));
            }

        } else if (identificador()) {
            if (cad.charAt(i) == '=') {
                aux += cad.charAt(i);
                i++;

                if (num_entero() == false) {
                    error("buscaba un numero y se encontro", String.valueOf(cad.charAt(i)));
                } else {
                    aux2 = true;
                }
            } else {
                error("buscaba un numero y se encontro", String.valueOf(cad.charAt(i)));
            }
        }
        return aux2;
    }

    public boolean incremento() {
        boolean aux2 = false;// JUAN
        if (identificador()) {
            if (cad.charAt(i) == '+') {
                i++;
                if (cad.charAt(i) == '+') {
                    i++;
                    aux2 = true;
                } else {
                    error("buscaba un + y se encontro", String.valueOf(cad.charAt(i)));
                }
            } else if (cad.charAt(i) == '-') {
                i++;
                if (cad.charAt(i) == '-') {
                    i++;
                    aux2 = true;
                } else {
                    error("buscaba un - y se encontro", String.valueOf(cad.charAt(i)));
                }
            } else {
                error("buscaba un + o - y se encontro", String.valueOf(cad.charAt(i)));
            }
        }
        return aux2;
    }

    public void error(String metodo, String aux) {
        System.out.println("Error: " + metodo + " " + aux);
        errores = 1;
    }

}
