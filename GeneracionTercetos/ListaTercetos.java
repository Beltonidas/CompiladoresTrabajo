package GeneracionTercetos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class ListaTercetos {
    private static List<Terceto> tercetos = new Vector<Terceto>();
    private static Stack<Integer> pila_indices = new Stack<Integer>();
    private static Stack<Terceto> pila_tercetos_for = new Stack<Terceto>();

    private static Boolean defer=false;
    
    public static void setDefer(Boolean b) {
        defer=b;
    }

    public static void addTerceto(Terceto t){
        if (defer) {
            Ambito.addTercetoDiferido(t);
        }else {
            tercetos.add(t);
        }
    }
    
    public static int getIndice() {
        return tercetos.size();
    }

    public void addIndice(int indice){
        pila_indices.push(indice);
    }

    public static void add_seleccion_cond(){
        pila_indices.push(Terceto.GetIndice()); //agregamos el indice del terceto incompleto que mas adelante se completara.
        addTerceto(new Terceto("BF", "["+Integer.toString(Terceto.GetIndice()-1)+"]", "_"));
    }

    public static void add_seleccion_then(){
        Terceto.GetTerceto(pila_indices.pop()).setTarg("["+Integer.toString(Terceto.GetIndice()+1)+"]"); //completamos el terceto incompleto que se agrego por el void de arriba
        pila_indices.push(Terceto.GetIndice()); // apilamos terceto para BI incompleto
        addTerceto(new Terceto("BI","_","_"));  //y lo agregamos
    }

    public static void add_seleccion_final(){
        Terceto.GetTerceto(pila_indices.pop()).setSarg('['+Integer.toString(Terceto.GetIndice())+']'); // le seteamos al terceto de la BI, el terceto siguiente el cual es al que tiene que saltar una vez ejecutada la rama del then
    }

    public static void add_for_act(String id, String simb, String constante){
        // guardamos el terceto de actualizacion para usarlo mas adelante
        pila_tercetos_for.push(new Terceto(simb,id,constante));
    }

    public static void add_for_cpo(){
        Terceto op = pila_tercetos_for.pop();
        addTerceto(op);

        Terceto t_asig = new Terceto("=:",op.getSarg(),"["+(op.id)+']');
        addTerceto(t_asig);
        
        int indice_for_cond = pila_indices.pop();
        addTerceto(new Terceto("BI", '['+Integer.toString(indice_for_cond-1)+']', "_"));
        Terceto.GetTerceto(indice_for_cond).setTarg('['+Integer.toString(Terceto.GetIndice())+']');
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
                System.out.println(tercetos.get(i).toString());
                x++;
            }
        }
        System.out.println("end start");
    }
    
    public static Terceto getTerceto(int id) {
        return tercetos.get(id);
    }
    
    public static void considerarEtiquetas() {
        List<Integer> labels = new ArrayList<Integer>();
        for (int i=0;i<tercetos.size();i++) {
            Terceto x = tercetos.get(i);
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
                Terceto x = new Terceto("NOP","_","_",true);
                tercetos.add(x);
            }else {
                Terceto.GetTerceto(aux).setCarg(true);
            }
            
        }
    }
}
