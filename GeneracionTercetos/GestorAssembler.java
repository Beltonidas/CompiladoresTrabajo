package GeneracionTercetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Compilador.TablaSimbolos;
import Compilador.TokenLexema;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

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
    final static String dobleMin = "_dobleMin";
    final static String dobleMax = "_dobleMax";
    final static String zero = "0.";
    
    private static List<String> lineaCODE=new ArrayList<String>();
    private static List<String> lineaDATA=new ArrayList<String>();
    private static HashMap<Integer,String> variablesAux = new HashMap<Integer,String>();
    private static HashMap<String,String> ctesAux = new HashMap<String,String>();
    private static Integer x = 0;
    private static HashMap<String,String> cadenasAux = new HashMap<String,String>();
    //-------------------ATRIBUTOS--------------------------

    //------------------CONSTRUCTOR-------------------------
    
    //Nunca en el segundo argumento de un terceto vamos a tener una referencia a otro terceto
    public GestorAssembler (){
        //Constructor
    }
    
    public static int referenciaTerceto(String str) {
        return Integer.parseInt(str.substring(1,str.length()-1));
    }
    

    public static void operacion(int nroTerceto, Terceto terceto) {
        // + , - , * , /
        String segundoArgumento,tercerArgumento,tipoOp;
        String operador = "";
        boolean sOpINM= false;
        boolean tOpINM = false;
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.sarg.startsWith("[")) {
            String aux = variablesAux.get(referenciaTerceto(terceto.sarg));
            segundoArgumento = TablaSimbolos.getSimbolo(aux).getLexema().toString();
            tipoOp = TablaSimbolos.getSimbolo(aux).getTipo();
        } else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.sarg);
            if (aux.getUso()!= null){
                segundoArgumento = "_"+aux.getLexema().toString();
                segundoArgumento = segundoArgumento.replace(":","");
            }
            else{
                segundoArgumento = aux.getLexema().toString();
                sOpINM = true;
            }
            tipoOp = aux.getTipo().toString();
        }
        if (terceto.targ.startsWith("[")) {
            tercerArgumento = TablaSimbolos.getSimbolo(variablesAux.get(referenciaTerceto(terceto.targ))).getLexema().toString();
        }else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.targ);
            if (aux.getUso()!= null){
                tercerArgumento = "_"+aux.getLexema().toString();
                tercerArgumento = tercerArgumento.replace(":","");
            }
            else{
                tercerArgumento = aux.getLexema().toString();
                tOpINM = true;
            }
        }
        switch (terceto.parg){
            case "+": if (tipoOp.equals("ui8")) {operador=add;} else {operador=fadd;}break;
            case "-": if (tipoOp.equals("ui8")) {operador=sub;} else {operador=fsub;}break;
            case "*": if (tipoOp.equals("ui8")) {operador=mul;} else {operador=fmul;}break;
            case "/": if (tipoOp.equals("ui8")) {operador=div;} else {operador=fdiv;}break;
        }
        if (tipoOp.equals("ui8")) {
            procesarTerceto(segundoArgumento, tercerArgumento, operador, sOpINM, tOpINM);
        } else {
            procesarTercetoDouble(segundoArgumento, tercerArgumento, operador, sOpINM, tOpINM);    
        }
        TablaSimbolos.addSimbolo(new TokenLexema(257,(variableAux+x),tipoOp));
        variablesAux.put(nroTerceto, (variableAux+x));
        x++;
    }
    
    public static void asignacion(int nroTerceto, Terceto terceto) {
        TokenLexema segundoArgumento = TablaSimbolos.getSimbolo(terceto.sarg);
        String tipoOp = segundoArgumento.getTipo();
        TokenLexema tercerArgumento;
        Boolean terEsImm = false;
        String newNameTarg= null;
        String nombreAux = "";
        // Nuevo codigo mejora
        //Si empieza con corchetes es una referencia a otro terceto
        if (terceto.targ.startsWith("[")) {
            tercerArgumento = new TokenLexema(257,variablesAux.get(referenciaTerceto(terceto.targ)));
        }else {
            TokenLexema aux = TablaSimbolos.getSimbolo(terceto.targ);
            tercerArgumento = aux;
            if (aux.getUso()==null) {
                terEsImm=true;
            }else{
                //Cambio de nombre
                newNameTarg = "_"+tercerArgumento.getLexema();
                newNameTarg= newNameTarg.replace(":","");
            }
        }

        
        if (tipoOp.equals("ui8")) {
            //Asignacion de 8 bits
            if (newNameTarg!= null)
                lineaCODE.add(mov +" AL, " +newNameTarg);
            else{
                lineaCODE.add(mov +" AL, " +tercerArgumento.getLexema());
            }
            nombreAux = segundoArgumento.getLexema().toString();
            nombreAux = nombreAux.replace(":", "");
            lineaCODE.add(mov +" _"+nombreAux + ", AL");
        } else {
            //La diferencia esta en que un una vamos a cargar de memoria y en otra seria un inmediato
            if (terEsImm){
                String variableAuxiliar="";
                if (ctesAux.containsKey(tercerArgumento.getLexema().toString())){
                    variableAuxiliar=ctesAux.get(tercerArgumento.getLexema().toString());
                }else{
                    variableAuxiliar=(variableAux+x);
                    ctesAux.put(tercerArgumento.getLexema().toString(), (variableAux+x));
                    TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "f64"));
                    x++;
                }
                lineaCODE.add(fild +" "+variableAuxiliar);
                nombreAux = segundoArgumento.getLexema().toString();
                nombreAux = nombreAux.replace(":", "");
                lineaCODE.add(fstp +" _"+nombreAux);
            }else{
                lineaCODE.add(fild +" "+tercerArgumento.getLexema());
                nombreAux = segundoArgumento.getLexema().toString();
                nombreAux = nombreAux.replace(":", "");
                lineaCODE.add(fstp +" _"+nombreAux);
            }
        }
    }
    
    public static void comparacion(int nroTerceto, Terceto terceto) {
     // + , - , * , /
        String segundoArgumento,tercerArgumento,tipoOp;
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
                segundoArgumento = "_"+segundoArgumento;
                segundoArgumento = segundoArgumento.replace(":","");
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
                tercerArgumento = "_"+tercerArgumento;
                tercerArgumento = tercerArgumento.replace(":","");
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
            TablaSimbolos.addSimbolo(new TokenLexema(257,(variableAux+x),"f16")); // documentalo
            variablesAux.put(nroTerceto, (variableAux+x));
            x++;
        }
    }

    //---------------CREAR ARCHIVO-------------------------
    
    //Antes de ejecutar el metodo correspondiente se deben procesar los argumentos y deben estar en la tabla de simbolos
    public static void procesarArchivo(){
        //----------------ITERAR TERCETOS GENERAR CODIGO--------------------
        String operadorAnterior="";
        String nombreAUX= "";
        for (int i = 0; i < ListaTercetos.getIndice() ; i++) {
            Terceto tercetoProcesar = ListaTercetos.getTerceto(i);
            String operador = tercetoProcesar.getParg();

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
                    lineaCODE.add(jmp+" Label_"+referenciaTerceto(tercetoProcesar.sarg));
                    break;
                case "BF":
                    lineaCODE.add(nemesisOp(operadorAnterior)+" Label_"+referenciaTerceto(tercetoProcesar.targ));
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
                    if (tercetoProcesar.sarg.equals("EBX")) {
                        lineaCODE.add(push+" "+tercetoProcesar.sarg);
                    }else if (TablaSimbolos.getSimbolo(tercetoProcesar.sarg).getTipo().equals("ui8")) {
                        nombreAUX = tercetoProcesar.sarg;
                        nombreAUX = nombreAUX.replace(":", "");
                        lineaCODE.add(mov+" AL, _"+nombreAUX);
                        lineaCODE.add(push+" AX");
                    }else {
                        lineaCODE.add(fild+" _"+tercetoProcesar.sarg.replace(":", ""));
                    }
                    break;
                case "Pop":
                    if (tercetoProcesar.sarg.equals("EBX")) {
                        lineaCODE.add(pop+" "+tercetoProcesar.sarg);
                    }else if (TablaSimbolos.getSimbolo(tercetoProcesar.sarg).getTipo().equals("ui8")) {
                        lineaCODE.add(pop+" AX");
                        lineaCODE.add(mov+" _"+tercetoProcesar.sarg.replace(":","")+", AL");
                        
                    }else {
                        lineaCODE.add(fstp+" _"+tercetoProcesar.sarg.replace(":", ""));
                    }
                    break;
                case "JE":
                    lineaCODE.add(tercetoProcesar.parg+" "+tercetoProcesar.sarg);
                    break;
                case "CMP":
                    lineaCODE.add(tercetoProcesar.parg+" "+ tercetoProcesar.sarg +", "+ tercetoProcesar.targ);
                    break;
                case "MOV":
                    lineaCODE.add(tercetoProcesar.parg+" "+ tercetoProcesar.sarg +", "+ tercetoProcesar.targ);
                    break;
                case "out":
                    //Fijarse bien como se hace
                    String variableAuxiliar="";
                    if (cadenasAux.containsKey(tercetoProcesar.sarg)){
                        variableAuxiliar=cadenasAux.get(tercetoProcesar.sarg);
                    }else{
                        variableAuxiliar=variableAux+x;
                        x++;
                        TablaSimbolos.addSimbolo(new TokenLexema(259,variableAuxiliar));
                        cadenasAux.put(tercetoProcesar.sarg, variableAuxiliar);
                    }
                    lineaCODE.add("invoke MessageBox, NULL, addr _out, addr "+variableAuxiliar+", MB_OK");
                    lineaCODE.add("invoke ExitProcess, 0");
                    break;
                case "CALL":
                    String segundoArg = tercetoProcesar.sarg;
                    String primerArg = tercetoProcesar.parg;
                    String aux ="";
                    segundoArg = segundoArg.replace("[", "");
                    segundoArg = segundoArg.replace("]", "");
                    segundoArg = "Label_"+segundoArg;
                    aux = primerArg + " " +segundoArg;
                    lineaCODE.add(aux);
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
        lineaDATA.add("include \\masm32\\include\\windows.inc");
        lineaDATA.add("include \\masm32\\include\\kernel32.inc");
        lineaDATA.add("include \\masm32\\include\\user32.inc");
        lineaDATA.add("includelib \\masm32\\lib\\kernel32.lib");
        lineaDATA.add("includelib \\masm32\\lib\\user32.lib");
        lineaDATA.add(".data");
        lineaDATA.add("_ErrRec db \"Error recursion\", 0");
        lineaDATA.add("_ErrUiNeg db \"Error resta resultado negativo para ui8\", 0");
        lineaDATA.add("_ErrOvMul db \"Error overflow en mul de f64\", 0");
        lineaDATA.add(dobleMax+" DQ 1.7976931348623157E308");
        lineaDATA.add(dobleMin+" DQ 2.2250738585072014E-308");
        lineaDATA.add("_out db \"Mensaje de Programa\", 0");
        addDataTablaSimbolo();
        lineaDATA.add(".CODE");
        lineaCODE.add(0,"START:");
        addCodigosError();
        lineaCODE.add("END START");
    }
    
    public static String getKeyDeHash(String valor){
        for (HashMap.Entry<String,String> entry : ctesAux.entrySet()){
            if (entry.getValue().equals(valor)){
                return entry.getKey();
            }
        }
        return "";
    }

    public static String getKeyDeHashCadenas(String valor){
        for (HashMap.Entry<String,String> entry : cadenasAux.entrySet()){
            if (entry.getValue().equals(valor)){
                return entry.getKey();
            }
        }
        return "";
    }

    public static void addDataTablaSimbolo(){
        String nombreAux= "";
        for (HashMap.Entry<String, TokenLexema> entry : TablaSimbolos.getTablaSimbolo().entrySet()) {
            if ((entry.getValue().getId()== 257) && (entry.getValue().getTipo()!= null)){
                if(entry.getValue().getTipo().equals("ui8")){
                    // Declaramos las variables que son "ui8"
                    nombreAux = entry.getKey();
                    nombreAux = nombreAux.replace(":", "");

                    if(nombreAux.contains("@")){
                        lineaDATA.add(nombreAux+ " DB ?");
                    }
                    else{
                        lineaDATA.add("_"+nombreAux+ " DB ?");
                    }
                }
                else if(entry.getValue().getTipo().equals("f64")){
                    // Declaramos las variables que son "f64"
                    nombreAux = entry.getKey();
                    nombreAux = nombreAux.replace(":", "");
        
                    if(nombreAux.contains("@")){
                        if (ctesAux.containsValue(nombreAux)){
                            lineaDATA.add(nombreAux+" DQ "+getKeyDeHash(nombreAux));
                        }else{
                            lineaDATA.add(nombreAux+ " DQ ?");
                        }
                    }
                    else{
                        lineaDATA.add("_"+nombreAux+ " DQ ?");
                    }
                }else{
                    // valor de las comparaciones
                    nombreAux = entry.getKey();
                    nombreAux = nombreAux.replace(":", "");
                    lineaDATA.add(nombreAux+ " DW ?");

                }
                
            }
            if (entry.getValue().getId()== 259){
                String aux= entry.getKey();
                if(aux.startsWith("@")){
                    lineaDATA.add(aux+ " DB \""+getKeyDeHashCadenas(aux).replaceAll("'", "")+"\"" );
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
    
    public static void procesarTerceto(String sOp, String tOp, String funcion, boolean sOpINM, boolean tOpIMN) {
        if(tOpIMN){
            TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "ui8"));
            lineaCODE.add(mov +" AL, " +tOp);
            lineaCODE.add(mov +" "+(variableAux+x)+ ", AL");
            tOp= (variableAux+x);
            x++;
        }
        if(sOpINM){
            TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "ui8"));
            lineaCODE.add(mov +" AL, " +sOp);
            lineaCODE.add(mov +" "+(variableAux+x)+ ", AL");
            sOp= (variableAux+x);
            x++;
        }
        lineaCODE.add(mov + " AL, " +sOp);
        if (funcion.equals(mul) ||funcion.equals(div)){
            lineaCODE.add(funcion +" "+tOp);
        }
        else
            lineaCODE.add(funcion + " AL, " +tOp);
        if(funcion.equals(sub)) {
            lineaCODE.add(cmp +" AL, 0");
            lineaCODE.add(jl+" ErrUiNeg");
        }
        lineaCODE.add(mov +" " + variableAux+x + ", AL");
    }
    
    public static void procesarTercetoDouble(String sOp, String tOp, String funcion, boolean sOpINM, boolean tOpINM) {
        if(tOpINM){
            if (ctesAux.containsKey(tOp)){
                tOp=ctesAux.get(tOp);
            }else{
                ctesAux.put(tOp, (variableAux+x));
                tOp=(variableAux+x);
                TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "f64"));
                x++;
            }
        }
        if(sOpINM){
            if (ctesAux.containsKey(sOp)){
                sOp=ctesAux.get(sOp);
            }else{
                ctesAux.put(sOp, (variableAux+x));
                sOp=(variableAux+x);
                TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "f64"));
                x++;
            }
        }
        lineaCODE.add(fild+" "+sOp);
        lineaCODE.add(fild+" "+tOp);
        lineaCODE.add(funcion);
        if (funcion.equals(fmul)) {
            lineaCODE.add(fcom+" "+dobleMax); // mal, primero hay que meterlo en una variable xq sino no anda
            lineaCODE.add(fstsw+" "+(variableAux+x));
            lineaCODE.add(mov+" AX, "+(variableAux+x));
            //Insetar en tabla de simbolos
            TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "f16"));
            x++;
            lineaCODE.add(sahf);
            lineaCODE.add(jg+" ErrOvMul");
            
            lineaCODE.add(fcom+" "+dobleMin);
            lineaCODE.add(fstsw+" "+(variableAux+x));
            lineaCODE.add(mov+" AX, "+(variableAux+x));
            TablaSimbolos.addSimbolo(new TokenLexema(257, (variableAux+x), "f16"));
            x++;
            lineaCODE.add(sahf);
            lineaCODE.add(jl+" ErrOvMul");
        }
        lineaCODE.add(fstp+" "+variableAux+x);
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

    public static void generarASM(String rutaArchivo){
        //se crea el archivo en una ruta especifica
        try {
            String ruta = "./testFiles/codigo.asm";
            String lineaCodigo= "Prueba de archivo";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }else{
                //De lo
                PrintWriter pw = new PrintWriter(file);
                pw.close();
            }
            FileWriter fw = new FileWriter(file);
            PrintWriter lineaArchivo = new PrintWriter(fw);
            for (int i = 0; i < lineaDATA.size(); i++) {
                lineaCodigo = lineaDATA.get(i);
                lineaArchivo.println(lineaCodigo);
            }
            for (int j = 0; j < lineaCODE.size(); j++) {
                lineaCodigo = lineaCODE.get(j);
                lineaArchivo.println(lineaCodigo);
            }

            lineaArchivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

