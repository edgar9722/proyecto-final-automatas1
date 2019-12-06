package main;

import java.io.FileNotFoundException;


public class main {
    public static void main(String[] args) throws FileNotFoundException {
        //String f="C:\\Users\\hanse\\Documents\\Escuela\\Materias\\Lenguajes y Automatas 1\\Repositorio\\proyecto-final-automatas1\\Proyecto\\Proyecto\\src\\Archivo\\archivo do-while.txt";
        //String f="C:\\\\Users\\\\hanse\\\\Documents\\\\Escuela\\\\Materias\\\\Lenguajes y Automatas 1\\\\Repositorio\\\\proyecto-final-automatas1\\\\Proyecto\\\\Proyecto\\\\src\\\\Archivo\\\\archivo for.txt";
        //String f="C:\\Users\\hanse\\Documents\\Escuela\\Materias\\Lenguajes y Automatas 1\\Repositorio\\proyecto-final-automatas1\\Proyecto\\Proyecto\\src\\Archivo\\archivo if-else.txt";
        String f="C:\\Users\\hanse\\Documents\\Escuela\\Materias\\Lenguajes y Automatas 1\\Repositorio\\proyecto-final-automatas1\\Proyecto\\Proyecto\\src\\Archivo\\archivo if.txt";
        //String f="C:\\Users\\hanse\\Documents\\Escuela\\Materias\\Lenguajes y Automatas 1\\Repositorio\\proyecto-final-automatas1\\Proyecto\\Proyecto\\src\\Archivo\\archivo while.txt";
        new analizadorLexico(f);
     
 
    }
}
