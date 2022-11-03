package Compilador;

import java.util.Stack;

public class Ambito {
    private static Stack<String> ambitos = new Stack<String>();

    public static String addAmbito(String amb){
        return ambitos.push(amb);
    }

    public static String removeAmbito(){
        return ambitos.pop();
    }

    public static String tope(){
        return ambitos.peek();
    }

    public static String getAmbito(String lexema){
        Stack<String> copia_ambitos = (Stack<String>) ambitos.clone();
        while (!ambitos.empty()){ // se va vaciando la copia de la pila de ambitos hasta que se encuentre con el identificador
            String aux = lexema + getNaming();
            if (TablaSimbolos.contieneSimbolo(aux)){
                TablaSimbolos.removeSimbolo(lexema);
                ambitos = copia_ambitos;
                return aux;
            }

            ambitos.pop();
        }
        ambitos = copia_ambitos;
        // si llegamos aca quiere decir que la variable o funcion no esta declarada.
        return null;
    }

    public static boolean estaVacia(){
        return ambitos.empty();
    }

    public static String getNaming(){
        StringBuilder naming = new StringBuilder();
        for (String ambito_actual: ambitos){
            naming.append(":").append(ambito_actual);
        }
        return naming.toString();
    }
}
