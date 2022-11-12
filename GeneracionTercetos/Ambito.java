package GeneracionTercetos;

import Compilador.TablaSimbolos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Ambito {
    private static List<String> ambitos = new ArrayList<String>();
    private static Stack<List<Terceto>> tercetosDiferidos = new Stack<List<Terceto>>();
    
    public static int getIndiceDiferido() {
        return tercetosDiferidos.get(tercetosDiferidos.size()-1).size();
    }
    
    public static void addAmbito(String amb){
        ambitos.add(amb);
        tercetosDiferidos.push(new ArrayList<Terceto>());
    }

    public static String removeAmbito(){
        List<Terceto> aux=tercetosDiferidos.pop();
        while (!aux.isEmpty()) {
            Terceto x = aux.remove(0);
            if (x.parg.equals("=:")&&x.sarg.equals("TerRetFuncion:_")) {
                x.targ="["+(ListaTercetos.getIndice()+2)+"]";
            }
            ListaTercetos.addTerceto(x);
        }
        return ambitos.remove(ambitos.size()-1);
    }
    
    public static void addTercetoDiferido(Terceto t) {
        tercetosDiferidos.peek().add(t);
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
    
    public static String getAmbitoDeVariable(String lexema) {
        StringBuilder aux=new StringBuilder(lexema);
        aux.delete(0, aux.indexOf(":"));
        return aux.toString();
    }
    
    public static String getNombreAmbito(){
        StringBuilder aux = new StringBuilder(getNaming().replace(":for", ""));
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
