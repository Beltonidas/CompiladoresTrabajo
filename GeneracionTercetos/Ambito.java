package GeneracionTercetos;

import Compilador.TablaSimbolos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Ambito {
    private static List<String> ambitos = new ArrayList<String>();
    public static Stack<List<Terceto>> tercetosDiferidos = new Stack<List<Terceto>>();
    
    public static int getIndiceDiferido() {
        return tercetosDiferidos.get(tercetosDiferidos.size()-1).size();
    }
    
    public static void addAmbito(String amb){
        ambitos.add(amb);
        tercetosDiferidos.push(new ArrayList<Terceto>());
        ListaTercetos.addAmbito();
    }

    public static Terceto getTerceto(int indice){
        return tercetosDiferidos.peek().get(indice);
    }

    public static String removeAmbito(){
        List<Terceto> aux=tercetosDiferidos.pop();
        int i = 0;
        while (!aux.isEmpty()) {
            Terceto x = aux.remove(0);

            List<Terceto> aux2 = ListaTercetos.getReferenciasDiferidas(i);
            if (aux2 != null) {
                String a = new StringBuilder(i).toString();
                for (Terceto item : aux2) {
                    if(item.sarg_r == i){
                        item.sarg = a;
                        item.sarg_r = -1;
                    }
                    if(item.targ_r == i){
                        item.targ = a;
                        item.targ_r = -1;
                    }
                }
            }
            ListaTercetos.addTerceto(x);
            i++;
        }
        ListaTercetos.removeAmbito();
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
