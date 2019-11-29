import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.LinkedList;

public class sintáctico {
    int size, s;
    String c="";
    boolean error=false;
    LinkedList<Integer> errores=new LinkedList<>();
    public sintáctico(String f) {
        String bufferIn;

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));//leemos nuestro archivo de entrada
            while ((bufferIn = in.readLine()) != null) {//mientras no lleguemos al fin del archivo...
                int i = 0;
                String cad = bufferIn.trim();
                char t = cad.charAt(i);//vamos leyendo caracter por caracter
                String ora = "";
                ora += t;
                int j = i + 1;
                while (Character.isLetterOrDigit(cad.charAt(j))) {
                    ora += cad.charAt(j);
                    j++;
                    if (j == cad.length()) break;
                }
                i = j;
                if (palabraSentencia(ora)) {
                    if (!cad.equalsIgnoreCase("do")) {
                        if (cad.charAt(i) != '(') {
                            errores.add(1);
                            error=true;
                        }
                        switch (ora){
                            case "for":
                                ora = "";
                                j = i + 1;
                                while (Character.isLetterOrDigit(cad.charAt(j))) {
                                    ora += cad.charAt(j);
                                    j++;
                                    if (j == cad.length()) break;
                                }
                                i = j;
                                if (!ora.equals("int")){
                                    errores.add(9);
                                    error=true;
                                }
                                ora="";
                                j = i + 1;
                                while (Character.isLetterOrDigit(cad.charAt(j))) {
                                    ora+=String.valueOf(cad.charAt(j));
                                    if (cad.charAt(j + 1) == ';')
                                        break;
                                    j++;
                                }
                                c="";
                                if (!Character.isLetterOrDigit(cad.charAt(j-1))){
                                    errores.add(5);
                                    error=true;
                                }else
                                    c=ora;
                                i = j+1;
                                while (i < cad.length()) {
                                    t=cad.charAt(i);
                                    if (!Character.isLetterOrDigit(t))
                                        if (t!=';' && !String.valueOf(t).equals(" ")) {
                                            errores.add(6);
                                            error=true;
                                        }
                                        else if (t==';')
                                            break;
                                    i++;
                                }
                                i++;
                                ora="";
                                while (i<cad.length()) {
                                    while (Character.isLetterOrDigit(cad.charAt(i))) {
                                        ora += String.valueOf(cad.charAt(i));
                                        if (cad.charAt(i + 1) == ';')
                                            break;
                                        i++;
                                    }
                                    if (cad.charAt(i+1)==' ')
                                        break;
                                    i++;
                                }
                                if (!c.equals(ora)) {
                                    errores.add(5);
                                    error=true;
                                }
                                ora="";
                                while (i < cad.length()) {
                                    ora=String.valueOf(cad.charAt(i));
                                    if (evaluarOperador(ora))
                                        if (cad.charAt(i+1)=='=')
                                            ora+="=";
                                        else break;
                                     if (Character.isDigit(cad.charAt(i))) {
                                         ora=cad.charAt(i-2)+""+cad.charAt(i-1);
                                         break;
                                     }
                                    i++;
                                }
                                if (!evaluarOperador(ora)) {
                                    errores.add(8);
                                    error=true;
                                }
                                j=i+1;
                                ora="";
                                while (j < cad.length()) {
                                    if (Character.isDigit(cad.charAt(j))) {
                                        ora+=String.valueOf(cad.charAt(j));
                                        if (Character.isDigit(cad.charAt(j + 1))) {
                                            ora += cad.charAt(j + 1);
                                            j++;
                                        }
                                        else break;
                                    }
                                    if (cad.charAt(j + 1) == ';')
                                        break;
                                    j++;
                                }
                                if (ora.isEmpty()) {
                                    errores.add(7);
                                    error=true;
                                }
                                i = j+1;
                                while (i < cad.length()) {
                                    t=cad.charAt(i);
                                    if (!Character.isLetterOrDigit(t))
                                        if (t!=';' && !String.valueOf(t).equals(" ")) {
                                            errores.add(6);
                                            error=true;
                                        }
                                        else if (t==';')
                                            break;
                                    if (Character.isLetterOrDigit(t)) {
                                        errores.add(6);
                                        error = true;
                                    }
                                    i++;
                                }
                                i++;
                                ora="";
                                while (i<cad.length()) {
                                    while (Character.isLetterOrDigit(cad.charAt(i))) {
                                        ora += String.valueOf(cad.charAt(i));
                                        if (cad.charAt(i + 1) == '+'||cad.charAt(i + 1) == '-')
                                            break;
                                        i++;
                                    }
                                    if (cad.charAt(i + 1) == '+'||cad.charAt(i + 1) == '-')
                                        break;
                                    i++;
                                }
                                if (!c.equals(ora)) {
                                    errores.add(5);
                                    error=true;
                                }
                                i++;
                                ora="";
                                while (i < cad.length()) {
                                    ora=String.valueOf(cad.charAt(i));
                                    if (ora.equals("+")) {
                                        ora += String.valueOf(cad.charAt(i + 1));
                                        break;
                                    }
                                    if (ora.equals("-")) {
                                        ora += String.valueOf(cad.charAt(i + 1));
                                        break;
                                    }
                                    i++;
                                }
                                if (!ora.equals("++"))
                                    if (!ora.equals("--")) {
                                        errores.add(4);
                                        error=true;
                                    }
                                i+=2;
                                while (i < cad.length()) {
                                    t=cad.charAt(i);
                                    if (t!=')')
                                        errores.add(3);
                                    if (t==')')
                                        break;
                                    if (i == cad.length()) break;
                                    i++;
                                }
                                i+=1;
                                t=cad.charAt(i);
                                if (t!='{') {
                                    errores.add(1);
                                    error=true;
                                }
                                i++;
                                while (i <= cad.length()) {
                                    t=cad.charAt(i);
                                    if (t!='}') {
                                        errores.add(1);
                                        error = true;
                                    }
                                    if (t=='}')
                                        break;
                                    if (i == cad.length()) break;
                                    i++;
                                }
                                break;
                            case "while":
                                i++;
                                j = i;
                                ora="";
                                while (Character.isLetterOrDigit(cad.charAt(j))) {
                                    ora+=String.valueOf(cad.charAt(j));
                                    if (cad.charAt(j + 1) == ';' || cad.charAt(j+1) == ' ')
                                        break;
                                    j++;
                                }
                                c="";
                                if (!Character.isLetterOrDigit(cad.charAt(j-1))){
                                    errores.add(5);
                                    error=true;
                                }else
                                    c=ora;
                                i=j;
                                while (i < cad.length()) {
                                    ora=String.valueOf(cad.charAt(i));
                                    if (evaluarOperador(ora))
                                        if (cad.charAt(i+1)=='=')
                                            ora+="=";
                                        else break;
                                    if (Character.isDigit(cad.charAt(i))) {
                                        ora=String.valueOf(cad.charAt(i-1));
                                        break;
                                    }
                                    i++;
                                }
                                if (!evaluarOperador(ora)) {
                                    errores.add(8);
                                    i++;
                                    error=true;
                                }
                                j=i+1;
                                ora="";
                                while (j < cad.length()) {
                                    if (Character.isDigit(cad.charAt(j))) {
                                        ora = String.valueOf(cad.charAt(j));
                                        if (Character.isDigit(cad.charAt(j + 1))) {
                                            j++;
                                            ora += cad.charAt(j);
                                        } else break;
                                    }else if (cad.charAt(j)!=' ')
                                        break;
                                    j++;
                                }
                                if (ora.isEmpty()) {
                                    errores.add(7);
                                    error = true;
                                }
                                i = j;
                                while (i < cad.length()) {
                                    t=cad.charAt(i);
                                    if (t!=')') {
                                        errores.add(3);
                                        error = true;
                                    }
                                    if (t==')')
                                        break;
                                    if (i == cad.length()) break;
                                    i++;
                                }
                                i=j+1;
                                if (cad.charAt(i)!='{') {
                                    errores.add(1);
                                    error=true;
                                }
                                i++;
                                while (i <= cad.length()) {
                                    t=cad.charAt(i);
                                    if (t!='}') {
                                        errores.add(1);
                                        error = true;
                                    }
                                    if (t=='}')
                                        break;
                                    if (i == cad.length()) break;
                                    i++;
                                }
                                break;
                            case "if":
                                i++;
                                j = i;
                                ora="";
                                while (Character.isLetterOrDigit(cad.charAt(j))) {
                                    ora+=String.valueOf(cad.charAt(j));
                                    if (cad.charAt(j + 1) == ';' || cad.charAt(j+1) == ' ')
                                        break;
                                    j++;
                                }
                                c="";
                                if (!Character.isLetterOrDigit(cad.charAt(j-1))){
                                    errores.add(5);
                                    error=true;
                                }else
                                    c=ora;
                                i=j;
                                while (i < cad.length()) {
                                    ora=String.valueOf(cad.charAt(i));
                                    if (evaluarOperador(ora))
                                        if (cad.charAt(i+1)=='=')
                                            ora+="=";
                                        else break;
                                    if (Character.isDigit(cad.charAt(i))) {
                                        ora=String.valueOf(cad.charAt(i-1));
                                        break;
                                    }
                                    i++;
                                }
                                if (!evaluarOperador(ora)) {
                                    errores.add(8);
                                    i++;
                                    error=true;
                                }
                                j=i+1;
                                ora="";
                                while (j < cad.length()) {
                                    if (Character.isDigit(cad.charAt(j))) {
                                        ora = String.valueOf(cad.charAt(j));
                                        if (Character.isDigit(cad.charAt(j + 1))) {
                                            j++;
                                            ora += cad.charAt(j);
                                        } else break;
                                    }else if (cad.charAt(j)!=' ')
                                        break;
                                    j++;
                                }
                                if (ora.isEmpty()) {
                                    errores.add(7);
                                    error = true;
                                }
                                i = j;
                                while (i < cad.length()) {
                                    t=cad.charAt(i);
                                    if (t!=')') {
                                        errores.add(3);
                                        error = true;
                                    }
                                    if (t==')')
                                        break;
                                    if (i == cad.length()) break;
                                    i++;
                                }
                                i=j+1;
                                if (cad.charAt(i)!='{') {
                                    errores.add(1);
                                    error=true;
                                }
                                i++;
                                while (i <= cad.length()) {
                                    t=cad.charAt(i);
                                    if (t!='}') {
                                        errores.add(1);
                                        error = true;
                                    }
                                    if (t=='}')
                                        break;
                                    if (i == cad.length()) break;
                                    i++;
                                }
                                break;
                        }
                    } else if (cad.charAt(i) != '{') {
                        System.out.println("Necesita ir un signo de puntuacion: {");
                        error=true;
                    }
                }
                i++;
            }
        }catch (Exception e) {

        }
        if (error)
            System.out.println("Hubo errores");
        else
            System.out.println("Sin errores");
    }
    public static boolean palabraSentencia(String cad) {
        if (cad.equalsIgnoreCase("if")) return true;
        else if (cad.equalsIgnoreCase("else")) return true;
        else if (cad.equalsIgnoreCase("for")) return true;
        else if (cad.equalsIgnoreCase("while")) return true;
        else if (cad.equalsIgnoreCase("do")) return true;
        else return false;
    }
    public static boolean evaluarOperador(String c) {
        if (c.equals("<")) return true;
        else if (c.equals(">")) return true;
        else if (c.equals("<=")) return true;
        else if (c.equals(">=")) return true;
        return false;
    }
}
