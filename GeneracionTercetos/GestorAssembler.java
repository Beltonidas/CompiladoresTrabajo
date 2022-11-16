package GeneracionTercetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Compilador.TablaSimbolos;
import Compilador.TokenLexema;


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
    final static String fild = "FILD";
    final static String fstp = "FSTP";
    final static String variableAux= "@aux";
    
    //private StringBuilder lineaAsembler;
    private static List<String> lineaCODE=new ArrayList<String>();
    private static List<String> lineaDATA=new ArrayList<String>();
    private static Integer etiquetas=0;
    private static HashMap<Integer,String> variablesAux = new HashMap<Integer,String>();
    private static Integer x = 0;
    //-------------------ATRIBUTOS--------------------------

    //------------------CONSTRUCTOR-------------------------
    
    //Nunca en el segundo argumento de un terceto vamos a tener una referencia a otro terceto
    public GestorAssembler (){
        //lista de variables
    }
    
    public static int referenciaTerceto(String str) {
        return Integer.parseInt(str.substring(1,str.length()-1));
    }

    public static void operacion(int nroTerceto, Terceto terceto) {
        // + , - , * , /
        //Para que esto funcione el segundo argumento debe si o si estar en la tabla se simbolos
        String segundoArgumento,tercerArgumento,tipoOp;
        String operador = "";
        Boolean segEsReferencia = false;
        Boolean terEsReferencia = false;
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.sarg.startsWith("[")) {
            segEsReferencia=true;
            String aux = variablesAux.get(referenciaTerceto(terceto.sarg));
            segundoArgumento = TablaSimbolos.getSimbolo(aux).getLexema().toString();
            tipoOp = TablaSimbolos.getSimbolo(aux).getTipo();
        } else {
            segundoArgumento = TablaSimbolos.getSimbolo(terceto.sarg).getLexema().toString();
            tipoOp = TablaSimbolos.getSimbolo(terceto.sarg).getTipo().toString();
        }
        System.out.println("Tipo segundo operador: "+tipoOp);
        if (terceto.targ.startsWith("[")) {
            terEsReferencia=true;
            tercerArgumento = TablaSimbolos.getSimbolo(variablesAux.get(referenciaTerceto(terceto.targ))).getLexema().toString();
        }else {
            tercerArgumento = TablaSimbolos.getSimbolo(terceto.targ).getLexema().toString();
        }
        switch (terceto.parg){
            case "+": if (tipoOp.equals("ui8")) {operador=add;} else {operador=fadd;}break;
            case "-": if (tipoOp.equals("ui8")) {operador=sub;} else {operador=fsub;}break;
            case "*": if (tipoOp.equals("ui8")) {operador=mul;} else {operador=fmul;}break;
            case "/": if (tipoOp.equals("ui8")) {operador=div;} else {operador=fdiv;}break;
        }
        if (tipoOp.equals("ui8")) {
            procesarTerceto(segundoArgumento, tercerArgumento, operador, (variableAux+x));
        } else {
            if (segEsReferencia&&terEsReferencia) {
                procesarTercetoDoubleRR(segundoArgumento, tercerArgumento, operador, (variableAux+x));    
            }else if(!segEsReferencia&&!terEsReferencia) {
                procesarTercetoDoubleII(segundoArgumento, tercerArgumento, operador, (variableAux+x));
            }else {
                if(segEsReferencia) {
                    procesarTercetoDoubleIR(tercerArgumento, segundoArgumento, operador, (variableAux+x));
                }else {
                    procesarTercetoDoubleIR(segundoArgumento, tercerArgumento, operador, (variableAux+x));
                }
            }
        }
        TablaSimbolos.addSimbolo(new TokenLexema(0,(variableAux+x),tipoOp));
        variablesAux.put(nroTerceto, (variableAux+x));
        x++;
    }
    
    public static void asignacion(int nroTerceto, Terceto terceto) {
        TokenLexema segundoArgumento = TablaSimbolos.getSimbolo(terceto.sarg);
        String tipoOp = segundoArgumento.getTipo();
        TokenLexema tercerArgumento;
        Boolean esReferencia=false;
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.targ.startsWith("[")) {
            esReferencia=true;
            tercerArgumento = new TokenLexema(0,variablesAux.get(referenciaTerceto(terceto.targ)));
        }else {
            tercerArgumento = TablaSimbolos.getSimbolo(terceto.targ);
        }
        if (tipoOp.equals("ui8")) {
            //Asignacion de 8 bits
            lineaCODE.add(mov +" AL, " + tercerArgumento.getLexema());
            lineaCODE.add(mov +" " + segundoArgumento.getLexema() + ", AL");
        } else {
            //La diferencia esta en que un una vamos a cargar de memoria y en otra seria un inmediato
            if (esReferencia) {
                lineaCODE.add(fild +" "+tercerArgumento.getLexema());
            }else {
                lineaCODE.add(fld +" "+tercerArgumento.getLexema());
            }
            lineaCODE.add(fstp +" "+segundoArgumento.getLexema());
        }
    }
    
    public static StringBuilder comparacion(Terceto terceto) {
        return null;
    }
    
    public static StringBuilder salto(Terceto terceto) {
        return null;
    }
    
    public static StringBuilder stackOp(Terceto terceto) {
        return null;
    }
    
    public static StringBuilder outOp(Terceto terceto) {
        return null;
    }
    //---------------CREAR ARCHIVO-------------------------
    /* 
    
    (*, a , b) --> MOV AL, _b
                   MUL AL, a
                   MOV @aux1, AL    
                      


    */
    
    //Antes de ejecutar el metodo correspondiente se deben procesar los argumentos y deben estar en la tabla de simbolos
    public static void procesarArchivo(){
        //----------------ITERAR TERCETOS GENERAR CODIGO--------------------
        int x = 0;
        Boolean addLibsOut = false;
        String operadorAnterior="";
        
        for (int i = 0; i < ListaTercetos.getIndice() ; i++) {
            System.out.println("Terceto: "+i);
            Terceto tercetoProcesar = ListaTercetos.getTerceto(i);
            String operador = tercetoProcesar.getParg();
            
            if (tercetoProcesar.getCarg()!=false) {
                lineaCODE.add("Label_"+ i +":");
                etiquetas++;
            }
            
            switch (operador) {
                case "=:":
                    asignacion(i, tercetoProcesar);
                    break;
                case "+":
                    operacion(i, tercetoProcesar);
                    break;
                case "-":
                    operacion(i, tercetoProcesar);
                    break;
                case "*":
                    operacion(i, tercetoProcesar);
                    break;
                case "/":
                    operacion(i, tercetoProcesar);
                    break;
                /*case "BI":
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
                    StringBuilder contenido = new StringBuilder(mov + " EAX, " +"SP");
                    lineaCODE.add(contenido);
                    contenido = new StringBuilder(add + " EAX, " +"8");
                    lineaCODE.add(contenido);
                    contenido = new StringBuilder(mov +" " + (variableAux+x) + ", EAX");
                    lineaCODE.add(contenido);
                    lineaCODE.add(new StringBuilder(mov +" AL, " + (variableAux+x)));
                    lineaCODE.add(new StringBuilder(mov +" " + sArg + ", AL"));
                    x++;
                    break;*/
            }
        }
        lineaCODE.add(0,"F[N]INIT");
        lineaCODE.add(0,"START:");
        lineaCODE.add("END START");
        if (addLibsOut) {
            addLibreriasParaElOut();
        }
        
        
    }
    public static void addLibreriasParaElOut() {
        lineaDATA.add("include \\masm32\\include\\windows.inc\\");
        lineaDATA.add("include \\masm32\\include\\kernel32.inc");
        lineaDATA.add("include \\masm32\\include\\user32.inc");
        lineaDATA.add("includelib \\masm32\\lib\\kernel32.lib");
        lineaDATA.add("includelib \\masm32\\lib\\user32.lib");
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
        lineaCODE.add(mov + " AL, " +sOp);
        lineaCODE.add(funcion + " AL, " +tOp);
        lineaCODE.add(mov +" " + variable + ", AL");
    }
    
    public static void procesarTercetoDoubleIR(String sOp, String tOp, String funcion, String variable) {
        lineaCODE.add(fld+" "+sOp);
        lineaCODE.add(fild+" "+tOp);
        procesarDouble(funcion,variable);
    }
    
    public static void procesarTercetoDoubleRR(String sOp, String tOp, String funcion, String variable) {
        lineaCODE.add(fild+" "+sOp);
        lineaCODE.add(fild+" "+tOp);
        procesarDouble(funcion,variable);
    }
    
    public static void procesarTercetoDoubleII(String sOp, String tOp, String funcion, String variable) {
        lineaCODE.add(fld+" "+sOp);
        lineaCODE.add(fld+" "+tOp);
        procesarDouble(funcion,variable);
    }
    
    public static void procesarDouble(String funcion, String variable) {
        lineaCODE.add(funcion);
        lineaCODE.add(fstp+" "+variable);
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

