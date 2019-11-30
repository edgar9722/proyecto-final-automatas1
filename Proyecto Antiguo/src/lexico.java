import java.io.*;

public class lexico {
    public lexico(String f) { //entra direccion del archivo.txt
        String bufferIn;

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));//leemos nuestro archivo de entrada
            try {

                while ((bufferIn = in.readLine()) != null) {//mientras no lleguemos al fin del archivo...
                    int i = 0;
                    String cad = bufferIn.trim();
                    //eliminamos los espacios en blanco al incio o al final (pero no a la mitad)
                    while (i < cad.length()) {//recorremos la línea
                        char t = cad.charAt(i);//vamos leyendo caracter por caracter
                        if (Character.isDigit(t)) {//comprobamos si es un digito
                            String ora = "";
                            ora += t;
                            int j = i + 1;
                            while (Character.isDigit(cad.charAt(j))) {
                                //mientras el siguiente elemento sea un numero
                                ora += cad.charAt(j);//concatenamos
                                j++;
                                if (j == cad.length()) break;//rompemos si llegamos al final de la línea
                            }//fin while

                            i = j;//movemos a nuestra variable i en la cadena
                            System.out.println("Número: " + ora);
                            continue;//pasamos al siguiente elemento
                        }//end if si es Digito

                        else if (Character.isLetter(t)) {//comprobamos si es una letra
                            String ora = "";
                            ora += t;
                            int j = i + 1;
                            while (Character.isLetterOrDigit(cad.charAt(j))) {
                                //mientras el siguiente elemento sea una letra o un digito
                                //ya que las variables pueden ser con numeros
                                ora += cad.charAt(j);
                                j++;
                                if (j == cad.length()) break;
                            }
                            i = j;
                            if (palabraSentencia(ora)) {//comprobamos si es una palabra reservada
                                System.out.println("Sentencia: " + ora);
                            } else if (tipoId(ora)) {//caso contrario es un identificador o variable
                                System.out.println("Tipo_Id: " + ora);
                            }else
                                System.out.println("Variable: "+ora);
                            continue;
                        }//end if si es variable

                        else if (!Character.isLetterOrDigit(t)) {
                            //si no es letra ni digito entonces...
                            if (evaluarCaracter(t)) {//¿es separador?
                                System.out.println("Parentesis: " + evaluarSeparador(t));
                            } else if (evaluarSignoPuntuacion(t)) {//¿Es ;?
                                System.out.println("Signo de puntuacion: " + t);
                            } else if (evaluarOperador(String.valueOf(t)))
                                System.out.println("Operador logico: "+t);
                            i++;
                            continue;
                        }//end if si es diferente de letra y digito
                    }
                }//end while
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Metodo que evalua nuestro caracter si existe y nos retorna
     * verdadero para los separadores
     * y
     * falso para los operadores
     */
    public static boolean evaluarCaracter(char c) {
        if (c == '(') return true;
        else if (c == ')') return true;
        else if (c == '{') return false;
        else if (c == '}') return false;
        else return false;
    }

    public static boolean evaluarSignoPuntuacion(char c){
        if (c == ';') return true;
        else return false;
    }

    /**
     * retornamos nuestro caracter de operador
     */
    public static boolean evaluarOperador(String c) {
        if (c.equals("<")) return true;
        else if (c.equals(">")) return true;
        else if (c.equals("<=")) return true;
        else if (c.equals(">=")) return true;
        return false;
    }

    public static char evaluarOperadorNum(char c) {
        char car = ' ';
        if (c == '+') car = '<';
        else if (c == '-') car = '>';
        else if (c == '*') car = '*';
        else if (c == '^') car = '^';
        else if (c == '=') car = '=';
        else if (c == '/') car = '/';
        return car;
    }

    /**
     * retornamos nuestro caracter de separador
     */
    public static char evaluarSeparador(char c) {
        char car = ' ';
        if (c == '(') car = '(';
        else if (c == ')') car = ')';
        return car;
    }


    public static boolean palabraSentencia(String cad) {
        if (cad.equalsIgnoreCase("if")) return true;
        else if (cad.equalsIgnoreCase("else")) return true;
        else if (cad.equalsIgnoreCase("for")) return true;
        else if (cad.equalsIgnoreCase("while")) return true;
        else if (cad.equalsIgnoreCase("do")) return true;
            //con equalsIgnoreCase no nos importa si esta en mayusculas o minusculas o alternadas
        else return false;
    }

    public static boolean modificador(String cad) {
        if (cad.equalsIgnoreCase("public")) return true;
        else if (cad.equalsIgnoreCase("private")) return true;
        else return false;
    }

    public static boolean tipoId(String cad) {
        if (cad.equalsIgnoreCase("int")) return true;
        else if (cad.equalsIgnoreCase("char")) return true;
        else if (cad.equalsIgnoreCase("boolean")) return true;
        else if (cad.equalsIgnoreCase("float")) return true;
        else if (cad.equalsIgnoreCase("string")) return true;
        else return false;
    }
}
