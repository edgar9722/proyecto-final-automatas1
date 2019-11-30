package main;

import java.io.FileNotFoundException;

/**
 *
 * @author Ramos Hernández Juan José
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException {
        String f="C:\\Users\\hanse\\Documents\\Hansel\\Escuela\\ITC\\Semestre 5\\Lenguajes y automatas 1\\PROYECTO-LA1-master\\Proyecto Final\\archivo\\archivo_a";
        System.out.println("\tANALIZADOR LÉXICO");
        new analizadorLexico(f);
    }
    
}
