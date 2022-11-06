package GeneracionTercetos;

import Compilador.TablaSimbolos;

import java.util.ArrayList;
import java.util.List;

public class Ambito {
    private static List<String> ambitos = new ArrayList<String>();

    public static void addAmbito(String amb){
        ambitos.add(amb);
    }

    public static String removeAmbito(){
        return ambitos.remove(ambitos.size()-1);
    }

    public static String ultimo(){
        return ambitos.get(ambitos.size()-1);
    }

    public static String getAmbito(String lexema){
        int ind = ambitos.size()-1;
        while (ind>=0){
            String aux = lexema + getNamingVar(ind);
            //System.out.println(aux);
            if (TablaSimbolos.contieneSimbolo(aux)){
                TablaSimbolos.removeSimbolo(lexema);
                return aux;
            }

            ind--;
        }
        // si llegamos aca quiere decir que la variable o funcion no esta declarada.
        return null;
    }

    public static boolean estaVacia(){
        return ambitos.isEmpty();
    }
    
    public static String getNamingVar(int x){
        StringBuilder naming = new StringBuilder();
        int i= 0;
        while (i<=x){
            naming.append(":").append(ambitos.get(i));
            i++;
        }
        return naming.toString();
    }
    
    public static String getNaming(){
        StringBuilder naming = new StringBuilder();
        for (String ambito_actual: ambitos){
            naming.append(":").append(ambito_actual);
        }
        return naming.toString();
    }
    
    public static String getNombreAmbito(){
        StringBuilder aux = new StringBuilder(getNaming());
        StringBuilder aux2 = new StringBuilder();
        for (int i = aux.lastIndexOf(":")+1; i < aux.length(); i++) {
            aux2.append(aux.charAt(i));
        }
        for (int i = 0; i < aux.lastIndexOf(":"); i++) {
            aux2.append(aux.charAt(i));
        }
        return aux2.toString();
    }
}
