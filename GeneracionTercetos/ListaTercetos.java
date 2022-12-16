package GeneracionTercetos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class ListaTercetos {
    private static List<Terceto> tercetos = new Vector<Terceto>();
    private static Stack<Integer> pila_indices = new Stack<Integer>();
    private static Stack<Terceto> pila_tercetos_for = new Stack<Terceto>();

    private static Boolean defer=false;
    private static Stack<Hashtable<Integer,List<Terceto>>> referencias_diferidas = new Stack<Hashtable<Integer,List<Terceto>>>();
    private static List<String> tiene_referencia_diferida = new ArrayList<String>();
    
    public static void setDefer(Boolean b) {
        defer=b;
    }
    
    public static void addAmbito(){
        referencias_diferidas.push(new Hashtable<Integer,List<Terceto>>());
    }

    public static void removeAmbito(){
        referencias_diferidas.pop();
    }

    public static List<Terceto> getReferenciasDiferidas(int ind){
        return referencias_diferidas.peek().get(ind);
    }

    public static void addTerceto(Terceto t){
        if (tiene_referencia_diferida.size() != 0){
            for (String item : tiene_referencia_diferida) {
                int aux = Integer.parseInt(item)-tercetos.size();
                System.out.println("en addTerceto, aux_diferido: "+ aux);
                if (("["+item+"]").equals(t.sarg)) {
                    t.setSargR(aux);
                } else {
                    t.setTargR(aux);
                }
                System.out.println("en addTerceto, terceto t: "+t);
                if (referencias_diferidas.peek().containsKey(aux)){
                    referencias_diferidas.peek().get(aux).add(t);
                } else {
                    List<Terceto> a = new ArrayList<Terceto>();
                    a.add(t);
                    referencias_diferidas.peek().put(aux, a);
                }
            }
            tiene_referencia_diferida.clear();
        }
        if (defer) {
            Ambito.addTercetoDiferido(t);
        }else {
            tercetos.add(t);
        }
    }
    
    public static void removeTerceto(int indice) {
        tercetos.remove(indice);
    }
    
    public static int getIndice() {
        if (defer) {
            return tercetos.size()+Ambito.getIndiceDiferido();
        }
        return tercetos.size();
    }

    public void addIndice(int indice){
        pila_indices.push(indice);
    }

    public static Terceto getTerceto(int indice){
        return tercetos.get(indice);
    }
    
    public static Terceto getTerceto(String indice){
        StringBuilder aux = new StringBuilder(indice);
        //System.out.println(aux);
        aux.deleteCharAt(0);
        aux.deleteCharAt(aux.length()-1);
        System.out.println("aux: "+aux);
        int aux_indice = Integer.parseInt(aux.toString());
        System.out.println("aux indice: "+aux_indice);
        System.out.println("aux diferido: "+(aux_indice-tercetos.size()+1));
        if (tercetos.size() <= aux_indice){
            tiene_referencia_diferida.add(aux.toString());
            return Ambito.getTerceto(aux_indice-tercetos.size());
        }

        return tercetos.get(aux_indice);
    }

    public static void add_seleccion_cond(){
        pila_indices.push(tercetos.size()); //agregamos el indice del terceto incompleto que mas adelante se completara.
        int indice_terceto_ant = tercetos.size()-1;
        Terceto terceto_bifurcacion_falso = new Terceto("BF", "["+indice_terceto_ant+"]", "_");
        addTerceto(terceto_bifurcacion_falso);
    }

    public static void add_seleccion_then(){
        int indice_cond = pila_indices.pop(); //obtengo el indice del terceto incompleto de la condicion
        getTerceto(indice_cond).setTarg('['+Integer.toString(tercetos.size()+1)+']'); //completamos el terceto incompleto que se agrego por el void de arriba
    
        pila_indices.push(tercetos.size()); // apilamos terceto para BI incompleto
        Terceto bifurcacion_incondicional = new Terceto("BI","_","_"); //generamos el terceto para la BI incompleto
        addTerceto(bifurcacion_incondicional);  //y lo agregamos
    }

    public static void add_seleccion_final(){
        int indice_then = pila_indices.pop(); // obtengo el indice del terceto BI
        getTerceto(indice_then).setSarg('['+Integer.toString(tercetos.size())+']'); // le seteamos al terceto de la BI, el terceto siguiente el cual es al que tiene que saltar una vez ejecutada la rama del then
    }


    public static void add_for_act(String id, String simb, String constante){
        // guardamos el terceto de actualizacion para usarlo mas adelante
        Terceto t_operacion = new Terceto(simb,id,constante);
        pila_tercetos_for.push(t_operacion);
    }

    public static void add_for_cpo(){
        Terceto op = pila_tercetos_for.pop();
        addTerceto(op);

        int indice_for_cond = pila_indices.pop();
        Terceto t_asig = new Terceto("=:",op.getSarg(),"["+(tercetos.size()-1)+']');

        addTerceto(t_asig);
        addTerceto(new Terceto("BI", '['+Integer.toString(indice_for_cond-1)+']', "_"));
        getTerceto(indice_for_cond).setTarg('['+Integer.toString(tercetos.size())+']');
    }

    public static void imprimir(){
        considerarEtiquetas();
        System.out.println("Tercetos:");
        int x=0;
        System.out.println("start:");
        for (int i=0; i<tercetos.size(); i++){
            Terceto aux = tercetos.get(i);
            if (aux.getParg().startsWith("Label_")) {
                System.out.println(aux.getParg()+":");
            }else {
                System.out.println(x+". "+tercetos.get(i).toString());
                x++;
            }
        }
        System.out.println("end start");
    }
    
    
    public static void considerarEtiquetas() {
        List<Integer> labels = new ArrayList<Integer>();
        for (int i=0;i<tercetos.size();i++) {
            Terceto x = tercetos.get(i);
            System.out.println("terceto: "+x);
            if (x.getParg().equals("BI")) {
                String aux=x.getSarg().substring(1,x.getSarg().length()-1);
                if (!labels.contains(Integer.parseInt(aux))) { 
                    labels.add(Integer.parseInt(aux));
                }
            }
            if (x.getParg().equals("BF")) {
                String aux=x.getTarg().substring(1,x.getTarg().length()-1);
                if (!labels.contains(Integer.parseInt(aux))) { 
                    labels.add(Integer.parseInt(aux));
                }
            }
        }
        for (int i=0;i<labels.size();i++) {
            int aux = labels.get(i);
            if (aux>tercetos.size()-1) {
                Terceto x = new Terceto("NOP","_","_");
                x.carg=true;
                tercetos.add(x);
            }else {
                tercetos.get(aux).setCarg(true);
            }
            
        }
    }
}
