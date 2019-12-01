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
    int i;
    
    public analizadorLexico(String f) throws FileNotFoundException{
        programa(f);
    }
    
    public void programa(String f) throws FileNotFoundException{
        in = new DataInputStream(new FileInputStream(f));//leemos nuestro archivo de entrada
        try {
            if ((bufferIn = in.readLine()) != null) {//Leemos la primera linea de entrada que tiene que ser 'inicio'
                cad = bufferIn.trim();//Esto seria el equivalente a transformarlo a String
                if (cad.equals("inicio")){
                    bufferIn = in.readLine();
                    cad = bufferIn.trim();
                    if (cad.equals("fin"))//Verificamos que no haya terminado
                        System.out.println("Termino el programa");
                    else
                        declaracion_sentencia();
                }else{
                    //No comenzo con 'inicio'
                }
            }
        }catch(IOException io){
            
        }
    }
    
    public void declaracion_sentencia(){
        i=0; String aux="";
        while(Character.isLetter(cad.charAt(i))){
            aux+=cad.charAt(i);
            i++;
        }//La i termina en la posicion del caracter que no es una letra
        switch(aux){
            case "if":
                break;
            case "do":
                break;
            case "while":
                break;
            case "for":
                break;
            default:
        }
    }
    public  operador(){

        String aux="";
        while(cad.chatAt(i) == '/' || '*' || '+' || '-'){
            aux+=cad.charAt(i);
            i++;
        }
        return aux;
    }
    public  operador(){

        String aux="";
        while(cad.chatAt(i) == '/' || '*' || '+' || '-'){
            aux+=cad.charAt(i);
            i++;
        }
        return aux;
    }
    public  identificador(){

    }

    public String letra(){
        String aux="";
        while(Character.isLetter(cad.charAt(i))){
            aux+=cad.charAt(i);
            i++;
        }
        return aux;
    }
    
    public int num_entero(){
        String aux="";
        while(Character.isDigit(cad.charAt(i))){
            aux+=cad.charAt(i);
            i++;
        }
        return Integer.parseInt(aux);
    }
}
