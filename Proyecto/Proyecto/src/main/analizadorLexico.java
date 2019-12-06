package main;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class analizadorLexico {

    DataInputStream in;
    String bufferIn, cad;
    int i, errores = 0;
    boolean MIF=false;
  

    public analizadorLexico(String f) throws FileNotFoundException {
        programa(f);
        
    }

    public void programa(String f) throws FileNotFoundException {
        String aux = "";
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
                        if (errores == 0 && !MIF) {
                            try {
                                bufferIn = in.readLine();
                                cad = bufferIn.trim();
                                i = 0;
                                while (Character.isLetter(cad.charAt(i))) {
                                    aux += cad.charAt(i);
                                    i++;
                                }
                            } catch (Exception e) {
                                if (aux.equalsIgnoreCase("fin")) {
                                    System.out.println("Sin errores");
                                } else {
                                    error("buscaba fin y encontro", aux);
                                }
                            }
                        }else
                            if (errores == 0) {
                                i = 0;
                                try {
                                    while (Character.isLetter(cad.charAt(i))) {
                                        aux += cad.charAt(i);
                                        i++;
                                    }
                                }catch (Exception e) {
                                    if (aux.equalsIgnoreCase("fin"))
                                        System.out.println("Sin errores");
                                    else
                                        error("buscaba fin y encontro", aux);
                                }
                            }
                    }
                } else {
                    error("buscaba inicio y encontro", cad);
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
            error("do-while: buscaba un { encontro", "__");
            return;
        }
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
                while (eliminarEspacios());
                FOR();
                break;
            default:
                error("declaracion_sentencia:", "no se encontro la sentencia: " + aux);
        }
    }

    public boolean IF() throws IOException {
        boolean bandera = false;
        String aux = "";
        if (cad.charAt(i) == '(') {
            aux += cad.charAt(i);
            i++;
            try {
                if (expresion_relacional()) {
                    try {
                        if (cad.charAt(i) == ')') {
                            aux += cad.charAt(i);
                            i++;
                            if (i != cad.length()) {
                                bandera = false;
                                error("Se buscaba un )", "Y se encontraron mas caracteres");
                            }else{
                                bufferIn = in.readLine();
                                cad = bufferIn.trim();
                                i=0;
                                if (sentencia()) {
                                    bufferIn = in.readLine();
                                    cad = bufferIn.trim();
                                    i=0;
                                    try {
                                        aux="";
                                        while (Character.isLetter(cad.charAt(i))) {
                                            aux += cad.charAt(i);
                                            i++;
                                        }
                                    } catch (Exception e) {
                                    }
                                    if (!aux.equalsIgnoreCase("") && !aux.equalsIgnoreCase("fin")){
                                        if (aux.equalsIgnoreCase("else")) {
                                            bufferIn = in.readLine();
                                            cad = bufferIn.trim();
                                            i=0;
                                            if (sentencia()) {
                                                
                                                if (i != cad.length()) {
                                                        bandera = false;
                                                        error("Se buscaba un ;", "Y se encontraron mas caracteres");
                                                }else{
                                                    bandera = true;
                                                }

                                            } else {
                                                bandera = false;
                                            }
                                        }else
                                            error("se buscaba else pero se encontro", aux);
                                    }else {
                                        bandera = true;
                                        MIF = true;
                                    }

                                } else {
                                    error("sentencia:",String.valueOf(cad.charAt(i)));
                                }
                            }
                        } else {
                            error("buscaba un ) y se encontro con",String.valueOf(cad.charAt(i)));
                        }
                    } catch (Exception e) {
                        error("buscaba un ) y se encontro con","__");
                    }
                } else {
                    error("buscaba un numero o un identificador y se encontro", String.valueOf(cad.charAt(i)));
                }
            }catch (Exception e) {
                error("se buscaba un ) se encontro","__");
            }
        } else {
            if (cad.charAt(i)==' ')
                error("se buscaba un ( se encontro", "__");
            else
                error("se buscaba un ( se encontro", String.valueOf(cad.charAt(i)));
        }
        return bandera;
    }

    public boolean DO_WHILE() throws IOException {
        String aux = "";
        boolean esNum = false;
        boolean bandera = false;
        try {
            if (cad.charAt(i) == '{') {
                i++;
                if (i != cad.length()) {
                 bandera = false;
                 error("Se buscaba un {", "Y se encontraron mas caracteres");
                }else{
                bufferIn = in.readLine();
                cad = bufferIn.trim();
                i = 0;
                if (sentencia()) {
                    bufferIn = in.readLine();
                    cad = bufferIn.trim();
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
                                                i++;
                                                if (i != cad.length()) {
                                                    bandera = false;
                                                    error("Se buscaba un ;", "Y se encontraron mas caracteres");
                                                }else{
                                                bandera = true;
                                                }
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
                                    error("expresion_relacional:", String.valueOf(cad.charAt(i)));
                                }
                            } else {
                                error("buscaba un ( encontro", String.valueOf(cad.charAt(i)));
                            }
                        } else {
                            if (cad.charAt(i)==' ')
                                error("buscaba la sentencia while y encontro", "__");
                            else
                                error("buscaba la sentencia while y encontro", aux);
                        }
                    } else {
                        error("Do-While: buscaba un } encontro", String.valueOf(cad.charAt(i)));
                    }
                } else {
                    error("Sentencia:", String.valueOf(cad.charAt(i)) + " se desconoce");
                }
                }
            } else {
                error("Do-While, buscaba un { encontro", String.valueOf(cad.charAt(i)));
            }
        } catch (Exception e) {
        }
        return bandera;
    }

    public boolean WHILE() throws IOException {
        boolean bandera = false;
        String aux = "";
        if (cad.charAt(i) == '(') {
            aux += cad.charAt(i);
            i++;
            try {
                if (expresion_relacional()) {
                    if (cad.charAt(i) == ')') {
                        aux += cad.charAt(i);
                        i++;
                        if (i != cad.length()) {
                            bandera = false;
                            error("Se buscaba un )", "Y se encontraron mas caracteres");
                        }else{
                            bufferIn = in.readLine();
                            cad = bufferIn.trim();
                            if (sentencia()) {
                                bandera = true;

                            } else {
                              error("Sentencia:", String.valueOf(cad.charAt(i)) + " se desconoce");
                            }
                        }
                    } else {
                        error("Se buca un )", String.valueOf(cad.charAt(i)));
                    }
                } else {
                    error("expresion_relacional:", String.valueOf(cad.charAt(i)));
                }
            } catch (Exception e) {
                error("se buscaba un ) se encontro","__");
            }
        } else {
            error("Se buca un (", String.valueOf(cad.charAt(i)));
        }
        return bandera;
    }

    public boolean FOR() throws IOException {
        boolean aux2 = false;
        String aux = "";
        if (cad.charAt(i) == '(') {
            aux += cad.charAt(i);
            i++;
            if (inicializacion()) {
                i++;
                if (cad.charAt(i) == ';') {
                    aux += cad.charAt(i);
                    i++;
                    recorrer();
                    if (expresion_relacional()) {
                        if (cad.charAt(i) == ';') {
                            aux += cad.charAt(i);
                            i++;
                            recorrer();
                            if (incremento()) {
                                if (cad.charAt(i) == ')') {
                                    aux += cad.charAt(i);
                                    i++;
                                    try {
                                        if (cad.charAt(i) == '{') {
                                            aux += cad.charAt(i);
                                            i++;
                                            if (i != cad.length()) {
                                                aux2 = false;
                                                error("Se buscaba un {", "Y se encontraron mas caracteres");
                                            } else {
                                                bufferIn = in.readLine();
                                                cad = bufferIn.trim();
                                                i = 0;
                                                if (sentencia()) {
                                                    bufferIn = in.readLine();
                                                    cad = bufferIn.trim();
                                                    i = 0;
                                                    if (cad.charAt(i) == '}') {
                                                        aux += cad.charAt(i);
                                                        i++;
                                                        if (i != cad.length()) {
                                                            aux2 = false;
                                                            error("Se buscaba un }", "Y se encontraron mas caracteres");
                                                        }

                                                        aux2 = true;
                                                    } else {
                                                        error("Buscaba un } y se encontro", String.valueOf(cad.charAt(i)));
                                                    }
                                                } else {
                                                    error("Sentencia:", String.valueOf(cad.charAt(i)));
                                                }
                                            }

                                        } else {
                                            error("Buscaba un { y se encontro", String.valueOf(cad.charAt(i)));
                                        }
                                    } catch (Exception e) {
                                        error("se buscaba un { se encontro", "__");
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
                while (num_entero()) {
                    i++;
                    aux = true;
                }
                if (!aux) {
                    if (identificador()) {
                        aux = true;
                    }
                }
            } else {
                error("operador_logico: invalido", String.valueOf(cad.charAt(i)));
            }
        } else {
            error("identificador:", String.valueOf(cad.charAt(i)) + " se desconoce");
        }
        return aux;
    }

    public boolean identificador() {
        boolean bandera = false;
        try {
            while (letra()) {
                i++;
                while (num_entero()) {
                    i++;
                }
                bandera = true;
            }
        } catch (Exception e) {
            
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
        try {
            if (Character.isDigit(cad.charAt(i))) {
                aux = true;
            }
        } catch (Exception e) {

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
            if(!aux)
            if (expresion()) {
                aux = true;
            }
        } catch (Exception e) {
        }
        return aux;
    }

    public boolean declaracion_var() {
        boolean aux = false;
        String aux2 = "";
        if (cad.charAt(i) == 'i') {
            aux2 += cad.charAt(i);
            i++;
            if (cad.charAt(i) == 'n') {
                aux2 += cad.charAt(i);
                i++;
                if (cad.charAt(i) == 't') {
                    aux2 += cad.charAt(i);
                    i++;
                    if (cad.charAt(i) == ' ') {
                        while (cad.charAt(i) == ' ') {
                            i++;
                        }
                        if (identificador()) {
                            try {
                                if (cad.charAt(i) == ';') {
                                    i++;
                                    if (i != cad.length()) {
                                        aux = false;
                                        error("Se buscaba un ;", "Y se encontraron mas caracteres");
                                    } else {
                                        aux = true;
                                    }

                                } else {
                                    error("buscar un ; encontro", String.valueOf(cad.charAt(i)));
                                }
                            } catch (Exception e) {
                                error("buscar un ; encontro", "__");
                            }
                        } else {
                            error("identificador:", String.valueOf(cad.charAt(i)));
                        }
                    } else {
                        error("inicializacion: buscaba int se desconoce",String.valueOf(cad.charAt(i)));
                    }
                } else {
                    error("buscaba un t y se encontro", String.valueOf(cad.charAt(i)));
                }
            } else {
                error("buscaba un n y se encontro", String.valueOf(cad.charAt(i)));
            }
        } else {
                aux = false;
        }
        return aux;
    }

    public boolean expresion() {
        boolean aux = false, esNum = false;
         cad = cad.replaceAll(" ","");
        if (identificador()) {
            if (operador()) {
                i++;
                if (identificador()) {
                    try {
                        if (cad.charAt(i) == ';') {
                            i++;
                            if (i != cad.length()) {
                                aux = false;
                                error("Se buscaba un ;", "Y se encontraron mas caracteres");
                            } else {
                                aux = true;
                            }
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
                            try {
                                if (cad.charAt(i) == ';') {
                                    i++;
                                    if (i != cad.length()) {
                                        aux = false;
                                        error("Se buscaba un ;", "Y se encontraron mas caracteres");
                                    } else {
                                        aux = true;
                                    }
                                } else {
                                    error("buscar un ; encontro", String.valueOf(cad.charAt(i)));
                                }
                            } catch (Exception e) {
                                error("buscar un ; encontro", "__");
                            }
                        } else {
                            error("expresion: se esperaba un identificador o un numero", "se encontro " + cad.charAt(i));
                        }
                    } catch (Exception e) {
                        error("buscaba un ; encontro", "__");
                    }
                }
            } else { try {
                    error("operador:", String.valueOf(cad.charAt(i)));
                } catch (Exception e) {
                    error("operador: Se busco operador y se encontro","__");
                }
                
            }
        } else {
            error("expresion:", String.valueOf(cad.charAt(i)));
        }
        return aux;
    }

    public boolean operador() {
        try {
            if (cad.charAt(i) == '/' || cad.charAt(i) == '*' || cad.charAt(i) == '+' || cad.charAt(i) == '-') {
                return true;
            }
        } catch (Exception e) {
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
                    if (cad.charAt(i)==' '){
                        while (cad.charAt(i)==' ')
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
                                if (cad.charAt(i)==' ')
                                    error("buscaba un = y se encontro", "__");
                                else
                                    error("buscaba un = y se encontro", String.valueOf(cad.charAt(i)));
                            }
                        }
                    }else
                        error("inicializacion: buscaba int se desconoce",String.valueOf(cad.charAt(i)));
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

    public boolean eliminarEspacios() {
        if (cad.charAt(i) == ' ') {
            cad = cad.replaceFirst(" ", "");
            return true;
        }
        return false;
    }
    
    public void recorrer(){
        while (cad.charAt(i)==' ')
            i++;
    }
}
