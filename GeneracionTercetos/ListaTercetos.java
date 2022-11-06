package GeneracionTercetos;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class ListaTercetos {
    private static List<Terceto> tercetos = new Vector<Terceto>();
    private static Stack<Integer> pila_indices = new Stack<Integer>();

    private static Stack<Terceto> pila_tercetos_for = new Stack<Terceto>();

    public static void addTerceto(Terceto t){
        tercetos.add(t);
    }

    public void addIndice(int indice){
        pila_indices.push(indice);
    }

    public static Terceto getTerceto(int indice){
        return tercetos.get(indice);
    }

    public static void add_seleccion_cond(){
        pila_indices.push(tercetos.size()); //agregamos el indice del terceto incompleto que mas adelante se completara.
        int indice_terceto_ant = tercetos.size()-1;
        Terceto terceto_bifurcacion_falso = new Terceto("BF", "["+indice_terceto_ant+"]", "-");
        addTerceto(terceto_bifurcacion_falso);
    }

    public static void add_seleccion_then(){
        int indice_cond = pila_indices.pop(); //obtengo el indice del terceto incompleto de la condicion
        getTerceto(indice_cond).setTarg(Integer.toString(tercetos.size()+1)); //completamos el terceto incompleto que se agrego por el void de arriba
    
        pila_indices.push(tercetos.size()); // apilamos terceto para BI incompleto
        Terceto bifurcacion_incondicional = new Terceto("BI","-","-"); //generamos el terceto para la BI incompleto
        addTerceto(bifurcacion_incondicional);  //y lo agregamos
    }

    public static void add_seleccion_final(){
        int indice_then = pila_indices.pop(); // obtengo el indice del terceto BI
        getTerceto(indice_then).setSarg(Integer.toString(tercetos.size())); // le seteamos al terceto de la BI, el terceto siguiente el cual es al que tiene que saltar una vez ejecutada la rama del then
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
        Terceto t_asig = new Terceto("=:",op.getSarg(),"["+(tercetos.size()-1)+"]");

        addTerceto(t_asig);
        addTerceto(new Terceto("BI", '['+Integer.toString(indice_for_cond-1)+']', "-"));
        getTerceto(indice_for_cond).setTarg(Integer.toString(tercetos.size()));
    }


    public static void imprimir(){
        System.out.println("Tercetos:");
        for (int i=0; i<tercetos.size(); i++){
            System.out.println(i+". "+tercetos.get(i).imprimir());
        }
    }
}
