import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    LinkedList<errores> errores=new LinkedList<>();
    LinkedList<Integer> e=new LinkedList<>();
    public static void main(String[] args) {
        main m=new main();
        System.out.println("\tANALIZADOR LÉXICO");
        lexico l=new lexico("C:\\Users\\hanse\\Documents\\Hansel\\Escuela\\ITC\\Semestre 5\\Lenguajes y automatas 1\\Proyecto Final\\archivo\\archivo_a");
        //este será nuestro archivo de entrada
        System.out.println("\n\tANALIZADOR SINTACTICO");
        sintáctico s=new sintáctico("C:\\Users\\hanse\\Documents\\Hansel\\Escuela\\ITC\\Semestre 5\\Lenguajes y automatas 1\\Proyecto Final\\archivo\\archivo_a");
        m.imprimirErrores(s.errores);
        m.llenarErrores();
    }

    public void llenarErrores(){
        errores.add(new errores(1, "Falta una llave"));
        errores.add(new errores(2, "El tipo de dato tiene que ser int"));
        errores.add(new errores(3, "Falta un parentesis"));
        errores.add(new errores(4, "Increment o decrement erroneo"));
        errores.add(new errores(5, "Variable incorrecta"));
        errores.add(new errores(6, "Falta un punto y coma"));
        errores.add(new errores(7, "Falta un numero"));
        errores.add(new errores(8, "Operador invalido"));
        errores.add(new errores(9, "Falta tipo de id"));
        for (int i = 0; i < errores.size(); i++)
            for (int j = 0; j < e.size(); j++)
                if (errores.get(i).getNumero_error()==e.get(j))
                    System.out.println(errores.get(i).getMensaje());
    }

    public void imprimirErrores(LinkedList<Integer> er){
        for (int i = 0; i < er.size(); i++)
            e.add(er.get(i));
    }
}
