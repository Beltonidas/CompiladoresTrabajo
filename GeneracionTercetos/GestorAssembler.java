package GeneracionTercetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Compilador.TablaSimbolos;


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
    final static String push = "PUSH";
    final static String pop = "POP";
    final static String cmp = "CMP";
    final static String zf = "ZF";
    final static String sf = "SF";
    final static String of = "OF";
    final static String fadd = "FADD";
    final static String fsub = "FSUB";
    final static String fmul = "FMUL";
    final static String fdiv = "FDIV";
    final static String fld = "FLD";
    final static String fstp = "FSTP";
    
    //private StringBuilder lineaAsembler;
    private static List<StringBuilder> lineaCODE=new ArrayList<StringBuilder>();
    private static List<StringBuilder> lineaDATA=new ArrayList<StringBuilder>();
    private static Integer etiquetas=0;
    private static Integer extras=0;
    
    //-------------------ATRIBUTOS--------------------------

    //------------------CONSTRUCTOR-------------------------
    public GestorAssembler (){
        //lista de variables
    }


    //---------------CREAR ARCHIVO-------------------------
    /* 
    
    (*, a , b) --> MOV AL, _b
                   MUL AL, a
                   MOV @aux1, AL    
                      


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
            System.out.println(tercetoProcesar);
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
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (TablaSimbolos.getSimbolo(sArg).getTipo().equals("ui8")) {
                        operador=add;
                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    } else {
                        operador=fadd;
                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "*": 
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (TablaSimbolos.getSimbolo(sArg).getTipo().equals("ui8")) {
                        operador=mul;
                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    } else {
                        operador=fmul;
                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "/": 
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (TablaSimbolos.getSimbolo(sArg).getTipo().equals("ui8")) {
                        operador=div;
                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    } else {
                        operador=fdiv;
                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "-": 
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (TablaSimbolos.getSimbolo(sArg).getTipo().equals("ui8")) {
                        operador=sub;
                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    } else {
                        operador=fsub;
                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "=:":
                    operador=mov;
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                        lineaCODE.add(new StringBuilder(mov +" AL, " + tArg));
                        lineaCODE.add(new StringBuilder(mov +" " + sArg + ", AL"));
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
                case "Push":
                    lineaCODE.add(new StringBuilder(push+" "+sArg));
                    break;
                case "Pop":
                    lineaCODE.add(new StringBuilder(pop+" "+sArg));
                    break;
                case "Stack":
                    //Aca habria que ver bien como se puede acceder al stack pointer
                    StringBuilder contenido = new StringBuilder(mov + " EAX, " +"CS");
                    lineaCODE.add(contenido);
                    contenido = new StringBuilder(add + " EAX, " +"8");
                    lineaCODE.add(contenido);
                    contenido = new StringBuilder(mov +" " + (variableAux+x) + ", EAX");
                    lineaCODE.add(contenido);
                    lineaCODE.add(new StringBuilder(mov +" AL, " + (variableAux+x)));
                    lineaCODE.add(new StringBuilder(mov +" " + sArg + ", AL"));
                    extras+=4;
                    x++;
                    break;
                default:
                    continue;
            }
            operadorAnterior=operador;
            xAnterior=x;
        }
        lineaCODE.add(0,new StringBuilder("F[N]INIT"));
        lineaCODE.add(0,new StringBuilder("START:"));
        lineaCODE.add(new StringBuilder("F[N]CLEX"));
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
        StringBuilder contenido = new StringBuilder(mov + " AL, " +sOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(funcion + " AL, " +tOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(mov +" " + variable + ", AL");
        lineaCODE.add(contenido);
        extras+=2;
    }
    
    public static void procesarTercetoDouble(String sOp, String tOp, String funcion, String variable) {
        StringBuilder contenido = new StringBuilder(fld+" "+sOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(fld+" "+tOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(funcion);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(fstp+" "+variable);
        lineaCODE.add(contenido);
        extras+=3;
    }
    
    public static void imprimir() {
        System.out.println("\n\n\n/////////Comienzo Assembler");
        Integer x = 0;
        for (int i = 0; i<lineaDATA.size();i++) {
            System.out.println(lineaDATA.get(i));
        }
        for (int i = 0; i<lineaCODE.size();i++) {
            System.out.println(x+": "+lineaCODE.get(i));
            x++;
        }
    }
}

