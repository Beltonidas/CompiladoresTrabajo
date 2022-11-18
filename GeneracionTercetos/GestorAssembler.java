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
    final static String fcom = "FCOM";
    final static String fcompp = "FCOMPP";
    final static String fstsw = "FSTSW";
    final static String sahf= "SAHF";
    final static String variableAux= "@aux";
    final static String dobleMin = "2.2250738585072014E-308";
    final static String dobleMax = "1.7976931348623157E308";
    final static String zero = "0.";
    
    
    //private StringBuilder lineaAsembler;
    private static List<String> lineaCODE=new ArrayList<String>();
    private static List<String> lineaDATA=new ArrayList<String>();
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
        String segundoArgumento,tercerArgumento,tipoOp;
        String operador = "";
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.sarg.startsWith("[")) {
            String aux = variablesAux.get(referenciaTerceto(terceto.sarg));
            segundoArgumento = TablaSimbolos.getSimbolo(aux).getLexema().toString();
            tipoOp = TablaSimbolos.getSimbolo(aux).getTipo();
        } else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.sarg);
            if (aux.getUso()!= null)
                segundoArgumento = "_"+aux.getLexema().toString();
            else
                segundoArgumento = aux.getLexema().toString();
            tipoOp = aux.getTipo().toString();
        }
        if (terceto.targ.startsWith("[")) {
            tercerArgumento = TablaSimbolos.getSimbolo(variablesAux.get(referenciaTerceto(terceto.targ))).getLexema().toString();
        }else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.targ);
            if (aux.getUso()!= null)
                tercerArgumento = "_"+aux.getLexema().toString();
            else
                tercerArgumento = aux.getLexema().toString();
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
            procesarTercetoDouble(segundoArgumento, tercerArgumento, operador, (variableAux+x));    
        }
        TablaSimbolos.addSimbolo(new TokenLexema(0,(variableAux+x),tipoOp));
        variablesAux.put(nroTerceto, (variableAux+x));
        x++;
    }
    
    public static void asignacion(int nroTerceto, Terceto terceto) {
        TokenLexema segundoArgumento = TablaSimbolos.getSimbolo(terceto.sarg);
        String tipoOp = segundoArgumento.getTipo();
        TokenLexema tercerArgumento;
        Boolean terEsImm = false;
        String newNameTarg= null;

        // Nuevo codigo mejora
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.targ.startsWith("[")) {
            tercerArgumento = new TokenLexema(0,variablesAux.get(referenciaTerceto(terceto.targ)));
        }else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.targ);
            tercerArgumento = aux;
            if (aux.getUso()==null) {
                terEsImm=true;
            }else{
                //Cambio de nombre
                newNameTarg = "_"+tercerArgumento.getLexema();
            }
        }

        
        if (tipoOp.equals("ui8")) {
            //Asignacion de 8 bits
            if (newNameTarg!= null)
                lineaCODE.add(mov +" AL, " +newNameTarg);
            else
                lineaCODE.add(mov +" AL, " +tercerArgumento.getLexema());
                
            lineaCODE.add(mov +" _"+segundoArgumento.getLexema() + ", AL");
        } else {
            //La diferencia esta en que un una vamos a cargar de memoria y en otra seria un inmediato
            lineaCODE.add(fild +" "+tercerArgumento.getLexema());
            lineaCODE.add(fstp +" _"+segundoArgumento.getLexema());
        } 



    }
    
    public static void comparacion(int nroTerceto, Terceto terceto) {
     // + , - , * , /
        String segundoArgumento,tercerArgumento,tipoOp;
        String operador = "";
        Boolean segEsImm = false;
        Boolean terEsImm = false;
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.sarg.startsWith("[")) {
            String aux = variablesAux.get(referenciaTerceto(terceto.sarg));
            segundoArgumento = TablaSimbolos.getSimbolo(aux).getLexema().toString();
            tipoOp = TablaSimbolos.getSimbolo(aux).getTipo();
        } else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.sarg);
            segundoArgumento = aux.getLexema().toString();
            tipoOp = aux.getTipo();
            if (aux.getUso()==null) {
                segEsImm=true;
            }else{
                //Cambio de nombre
                if(!segundoArgumento.startsWith("_"))
                    segundoArgumento = "_"+segundoArgumento;
            }
        }
        if (terceto.targ.startsWith("[")) {
            tercerArgumento = TablaSimbolos.getSimbolo(variablesAux.get(referenciaTerceto(terceto.targ))).getLexema().toString();
        }else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.targ);
            tercerArgumento = aux.getLexema().toString();
            if (aux.getUso()==null) {
                terEsImm=true;
            }else{
                //Cambio de nombre
                if(!tercerArgumento.startsWith("_"))
                    tercerArgumento = "_"+tercerArgumento;
            }
        }
        if (tipoOp.equals("ui8")) {
            if (segEsImm&&terEsImm) {
                lineaCODE.add(mov+" AL, "+segundoArgumento);
                lineaCODE.add(cmp+" AL, "+tercerArgumento);
            }else 
                lineaCODE.add(cmp+" "+segundoArgumento+", "+tercerArgumento);
        }else {
            lineaCODE.add(fild+" "+segundoArgumento);
            lineaCODE.add(fild+" "+tercerArgumento);
            lineaCODE.add(fcompp);
            lineaCODE.add(fstsw+" "+(variableAux+x));
            lineaCODE.add(mov+" AX, "+(variableAux+x));
            lineaCODE.add(sahf);
            TablaSimbolos.addSimbolo(new TokenLexema(0,(variableAux+x),"f16"));
            variablesAux.put(nroTerceto, (variableAux+x));
            x++;
        }
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
    
    //Antes de ejecutar el metodo correspondiente se deben procesar los argumentos y deben estar en la tabla de simbolos
    public static void procesarArchivo(){
        //----------------ITERAR TERCETOS GENERAR CODIGO--------------------
        String operadorAnterior="";
        for (int i = 0; i < ListaTercetos.getIndice() ; i++) {
            Terceto tercetoProcesar = ListaTercetos.getTerceto(i);
            String operador = tercetoProcesar.getParg();

            /* System.out.println("Terceto sarg: "+tercetoProcesar.getSarg() + " " + "Terceto sarg en TS: "+ TablaSimbolos.getSimbolo(tercetoProcesar.getSarg()));
            System.out.println("Terceto targ: "+tercetoProcesar.getTarg() + " " +"Terceto targ en ts: "+ TablaSimbolos.getSimbolo(tercetoProcesar.getTarg()));

            String usoSarg=null;
            String usoTarg=null;

            if(TablaSimbolos.getSimbolo(tercetoProcesar.getSarg()) != null)
                usoSarg = TablaSimbolos.getSimbolo(tercetoProcesar.getSarg()).getUso();
            
            if (TablaSimbolos.getSimbolo(tercetoProcesar.getTarg()) != null)
                usoTarg=TablaSimbolos.getSimbolo(tercetoProcesar.getTarg()).getUso();

            //Esto es en el caso de que no tenga uso no tiere errores nulos
            if (usoSarg!= null && !tercetoProcesar.getSarg().startsWith("[")){
                if (usoSarg.equals("Variable")){
                    tercetoProcesar.setSarg("_"+tercetoProcesar.getSarg());
                }
            }
            if (usoTarg!= null && !tercetoProcesar.getTarg().startsWith("[")){
                if (usoTarg.equals("Variable")){
                    tercetoProcesar.setTarg("_"+tercetoProcesar.getTarg());
                }
            }
             */
            if (tercetoProcesar.getCarg()!=false) {
                lineaCODE.add("Label_"+i+":");
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
                case "BI":
                    lineaCODE.add(jmp+" Label_"+referenciaTerceto(tercetoProcesar.sarg)+":");
                    break;
                case "BF":
                    lineaCODE.add(nemesisOp(operadorAnterior)+" Label_"+referenciaTerceto(tercetoProcesar.targ)+":");
                    break;
                case "<":
                    comparacion(i,tercetoProcesar);
                    break;
                case ">":
                    comparacion(i,tercetoProcesar);
                    break;
                case "=":
                    comparacion(i,tercetoProcesar);
                    break;
                case ">=":
                    comparacion(i,tercetoProcesar);
                    break;
                case "<=":
                    comparacion(i,tercetoProcesar);
                    break;
                case "=!":
                    comparacion(i,tercetoProcesar);
                    break;
                case "Push":
                    if (TablaSimbolos.getSimbolo(tercetoProcesar.sarg).getTipo().equals("ui8")) {
                        lineaCODE.add(push+" _"+tercetoProcesar.sarg);
                    }else {
                        lineaCODE.add(fild+" _"+tercetoProcesar.sarg);
                    }
                    break;
                case "Pop":
                    if (TablaSimbolos.getSimbolo(tercetoProcesar.sarg).getTipo().equals("ui8")) {
                        lineaCODE.add(pop+" _"+tercetoProcesar.sarg);
                    }else {
                        lineaCODE.add(fstp+" _"+tercetoProcesar.sarg);
                    }
                    break;
                default:
                    String aux = tercetoProcesar.parg;
                    if (tercetoProcesar.sarg!="_") {
                        aux +=" "+tercetoProcesar.sarg;
                    }
                    if (tercetoProcesar.targ!="_") {
                        aux +=" "+tercetoProcesar.targ;
                    }
                    lineaCODE.add(aux);
                    break;
                case "out":
                    //Fijarse bien como se hace
                    //lineaCODE.add("invoke MessageBox, NULL, addr "+terceto.sarg+", addr "+terceto.sarg+", MB_OK");
                    //lineaCODE.add("invoke ExitProcess, 0");
                    break;
            }
            operadorAnterior=operador;
        }
        addLibrerias();
    }
    
    public static void addLibrerias() {
        lineaDATA.add(".386");
        lineaDATA.add(".model flat, stdcall");
        lineaDATA.add("option casemap :none");
        lineaDATA.add("include \\masm32\\include\\windows.inc\\");
        lineaDATA.add("include \\masm32\\include\\kernel32.inc");
        lineaDATA.add("include \\masm32\\include\\user32.inc");
        lineaDATA.add("includelib \\masm32\\lib\\kernel32.lib");
        lineaDATA.add("includelib \\masm32\\lib\\user32.lib");
        lineaDATA.add(".data");
        // metodo
        addDataTablaSimbolo();
        lineaCODE.add(0,"F[N]INIT");
        lineaCODE.add(0,"START:");
        addCodigosError();
        lineaCODE.add("END START");
    }
    
    public static void addDataTablaSimbolo(){
        for (HashMap.Entry<String, TokenLexema> entry : TablaSimbolos.getTablaSimbolo().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if ((entry.getValue().getId()== 257) && (entry.getValue().getTipo()!= null)){
                if(entry.getValue().getTipo().equals("ui8")){
                    // Declaramos las variables que son "ui8"
                    lineaDATA.add("_"+entry.getKey()+ " DB ?");
                }else{
                    // Declaramos las variables que son "f64"
                    lineaDATA.add("_"+entry.getKey()+ " DQ ?");
                }
            }
        }
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
        if(funcion.equals(sub)) {
            lineaCODE.add(cmp +" AL, 0");
            lineaCODE.add(jl+" ErrUiNeg");
        }
        lineaCODE.add(mov +" " + variable + ", AL");
    }
    
    public static void procesarTercetoDouble(String sOp, String tOp, String funcion, String variable) {
        lineaCODE.add(fild+" "+sOp);
        lineaCODE.add(fild+" "+tOp);
        lineaCODE.add(funcion);
        if (funcion.equals(fmul)) {
            lineaCODE.add(fcom+" "+dobleMax);
            lineaCODE.add(fstsw+" "+(variableAux+x));
            lineaCODE.add(mov+" AX, "+(variableAux+x));
            x++;
            lineaCODE.add(sahf);
            lineaCODE.add(jg+" ErrOvMul");
            
            lineaCODE.add(fcom+" "+dobleMin);
            lineaCODE.add(fstsw+" "+(variableAux+x));
            lineaCODE.add(mov+" AX, "+(variableAux+x));
            x++;
            lineaCODE.add(sahf);
            lineaCODE.add(jl+" ErrOvMul");
        }
        lineaCODE.add(fstp+" "+variable);
    }
    
    public static void addCodigosError() {
        lineaCODE.add(jmp+" final");
        
        lineaCODE.add("ErrRec:"); 
        lineaCODE.add("invoke MessageBox, NULL, addr _ErrRec, addr _ErrRec, MB_OK");
        lineaCODE.add("invoke ExitProcess, 0");
        lineaCODE.add(jmp+" final");
        
        lineaCODE.add("ErrUiNeg:"); 
        lineaCODE.add("invoke MessageBox, NULL, addr _ErrUiNeg, addr _ErrUiNeg, MB_OK");
        lineaCODE.add("invoke ExitProcess, 0");
        lineaCODE.add(jmp+" final");
        
        lineaCODE.add("ErrOvMul:"); 
        lineaCODE.add("invoke MessageBox, NULL, addr _ErrOvMul, addr _ErrOvMul, MB_OK");
        lineaCODE.add("invoke ExitProcess, 0");
        lineaCODE.add(jmp+" final");
        
        lineaCODE.add("final:");
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

