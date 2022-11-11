package GeneracionTercetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GestorAssembler {
    final static String mov = "MOV";
    final static String mul = "MUL";
    final static String add = "ADD";
    final static String sub = "SUB";
    final static String div = "DIV";
    final static String jmp = "JMP";
    final static String jle = "JLE";
    final static String jge = "JGE";
    final static String jg = "JG";
    final static String jl = "JL";
    final static String je = "JE";
    final static String jne = "JNE";
    final static String stack = "STACK";
    final static String pop = "POP";
    final static String cmp = "CMP";
    final static String zf = "ZF";
    final static String sf = "SF";
    final static String of = "OF";
    
    //private StringBuilder lineaAsembler;
    private static List<StringBuilder> lineaCODE=new ArrayList<StringBuilder>();
    private static List<StringBuilder> lineaDATA=new ArrayList<StringBuilder>();
    private static Integer etiquetas=0;
    private static Integer extras=0;
    private static HashMap<Integer,List<Integer>> variablesFuturas=new HashMap<Integer,List<Integer>>();
    
    //-------------------ATR1BUTOS--------------------------

    //------------------CONSTRUCTOR-------------------------
    public GestorAssembler (){
        //lista de variables
    }


    //---------------CREAR ARCHIVO-------------------------
    /* 
    
    (*, a , b) --> MOV R1, _b
                   MUL R1, a
                   MOV @aux1, R1    
                      


    */

    @SuppressWarnings("unlikely-arg-type")
    public static void procesarArchivo(){
        //----------------ITERAR TERCETOS GENERAR CODIGO--------------------
        
        int x = 0;
        int xAnterior = 0;
        Boolean addLibsOut = false;
        String operadorAnterior="";
        
        HashMap<String,String> variablesPrevias = new HashMap<String,String>();
        
        for (int i = 0; i < ListaTercetos.getIndice() ; i++) {
            
            Terceto tercetoProcesar = ListaTercetos.getTerceto(i);
            String operador = tercetoProcesar.getParg();
            String sArg = tercetoProcesar.getSarg();
            String tArg = tercetoProcesar.getTarg();
            String variableAux= "@aux";
            if (tercetoProcesar.getCarg()!=false) {
                lineaCODE.add(new StringBuilder("Label_"+ i +":"));
                etiquetas++;
            }
            switch (operador) {
                case "+": 
                    operador=add;
                    if (sArg.startsWith("[")) {
                        if (checkVariableFutura(sArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (checkVariableFutura(tArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(tArg);
                        }
                    }
                    procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "*": 
                    operador=mul;
                    if (sArg.startsWith("[")) {
                        if (checkVariableFutura(sArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (checkVariableFutura(tArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(tArg);
                        }
                    }
                    procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "/": 
                    operador=div;
                    if (sArg.startsWith("[")) {
                        if (checkVariableFutura(sArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (checkVariableFutura(tArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(tArg);
                        }
                    }
                    procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "-": 
                    operador=sub;
                    if (sArg.startsWith("[")) {
                        if (checkVariableFutura(sArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (checkVariableFutura(tArg, i)) {
                            sArg=null;
                        }else {
                            sArg=variablesPrevias.get(tArg);
                        }
                    }
                    procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "=:":
                    operador=mov;
                    if (tArg.startsWith("[")) {
                        if (checkVariableFutura(tArg, i)) {
                            tArg=null;
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                        lineaCODE.add(new StringBuilder(mov +" R1, " + tArg));
                        lineaCODE.add(new StringBuilder(mov +" " + sArg + ", R1"));
                        extras+=1;
                    }else {
                        lineaCODE.add(new StringBuilder(mov +" " + sArg +", "+ tArg));
                    }
                    
                    break;
                case "BI":
                    if (sArg.equals("TerRetFuncion:_")) {
                        lineaCODE.add(new StringBuilder(jmp +" TerRetFuncion:_"));
                    }else {
                        lineaCODE.add(new StringBuilder(jmp +" Label_"+ sArg.substring(1, sArg.length()-1) +":"));
                    }
                    break;
                case "BF":
                    operador=nemesisOp(operadorAnterior);
                    lineaCODE.add(new StringBuilder(operador +" Label_"+ tArg.substring(1, tArg.length()-1) +":"));
                    break;
                case "<":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case ">":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case "=":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case ">=":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case "<=":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case "out":
                    addLibsOut=true;
                    lineaCODE.add(new StringBuilder("OUT "+sArg));
                    break;
                case "Stack":
                    lineaCODE.add(new StringBuilder(stack+" "+sArg));
                    break;
                case "Pop":
                    lineaCODE.add(new StringBuilder(pop+" "+sArg));
                    break;
                default:
                    continue;
            }
            operadorAnterior=operador;
            xAnterior=x;
            if (variablesFuturas.containsKey(i)) {
                List<Integer> aux = variablesFuturas.get(i);
                for (int j=0;j<aux.size();j++) {
                    StringBuilder aux_b = lineaCODE.get(aux.get(j));
                    Integer z=aux_b.lastIndexOf("null");
                    System.out.println(aux_b);
                    if (xAnterior==x) {
                        aux_b.replace(z, z+4, (variableAux+(x)));
                    }else {
                        aux_b.replace(z, z+4, (variableAux+(x-1)));
                    }
                    
                }
            }
        }
        lineaCODE.add(0,new StringBuilder("START:"));
        lineaCODE.add(new StringBuilder("END START"));
        if (addLibsOut) {
            addLibreriasParaElOut();
        }
    }
    public static void addLibreriasParaElOut() {
        lineaDATA.add(new StringBuilder("include \\masm32\\include\\windows.inc\\"));
        lineaDATA.add(new StringBuilder("include \\masm32\\include\\kernel32.inc"));
        lineaDATA.add(new StringBuilder("include \\masm32\\include\\user32.inc"));
        lineaDATA.add(new StringBuilder("includelib \\masm32\\lib\\kernel32.lib"));
        lineaDATA.add(new StringBuilder("includelib \\masm32\\lib\\user32.lib"));
    }
    
    
    //Dado que esta la usamos para bifurcaciones por falso devolvemos el opuesto
    public static String nemesisOp(String op) {
        switch (op) {
            case ">": return jle;
            case "<": return jge;
            case "=": return jne;
            case "<=": return jg;
            case ">=": return jl;
            case "!=": return je;
            default: return jmp;
        }
    }
    
    public static void procesarTerceto(String sOp, String tOp, String funcion, String variable) {
        StringBuilder contenido = new StringBuilder(mov + " R1, " +sOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(funcion + " R1, " +tOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(mov +" " + variable + ", R1");
        lineaCODE.add(contenido);
        extras+=2;
    }
    
    public static void imprimir() {
        Integer x = 0;
        for (int i = 0; i<lineaDATA.size();i++) {
            System.out.println(lineaDATA.get(i));
        }
        for (int i = 0; i<lineaCODE.size();i++) {
            System.out.println(x+": "+lineaCODE.get(i));
            x++;
        }
    }
    
    
    public static Boolean checkVariableFutura(String arg, Integer iteracion) {
        System.out.println(iteracion+";"+arg+";"+etiquetas+";"+extras);
        Integer z = Integer.parseInt(arg.substring(1,arg.length()-1));
        if (iteracion<z) {
            if (variablesFuturas.containsKey(z)) {
                variablesFuturas.get(z).add(iteracion+etiquetas+extras);
            }else {
                List<Integer> l = new ArrayList<Integer>();
                l.add(iteracion+etiquetas+extras);
                variablesFuturas.put(z,l);
            }
            return true;
        }
        return false;
    }
}

