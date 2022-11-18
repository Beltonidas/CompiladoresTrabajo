import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import GeneracionTercetos.ListaTercetos;
import GeneracionTercetos.Terceto;

public class GestorAsembler {
    final static String mov = "MOV";
    final static String mul = "MUL";
    final static String add = "ADD";
    final static String sub = "SUB";
    final static String div = "DIV";

    private StringBuilder lineaAsembler;
    private List<StringBuilder> lineaStart;
    private List<StringBuilder> lineaDATA;
    private List<StringBuilder> lineaCODE;
    
    //-------------------ATRIBUTOS--------------------------

    //------------------CONSTRUCTOR-------------------------
    public GestorAsembler (){
        //lista de variables
    }

    public static void cargarArchivo(){
        //----------------ITERAR TERCETOS GENERAR CODIGO--------------------
        for (int i = 0; i < listaTercetos.getTercetosize() ; i++) {
            Terceto tercetoProcesar = listaTercetos.getTerceto(i);
            String operador = tercetoProcesar.getParg();
            variableAux = variableAux + i+"";
            nuevaVariableUno = "_"+tercetoProcesar.getSarg();
            nuevaVariableDos = "_"+tercetoProcesar.getTarg();

            //PREGUNTARIAMOS SI ()

            switch(operador) {
            case "+" :
                System.out.println("es una suma");
                
                //verificar entero y double
                contenido = mov + ", RI, " +nuevaVariableUno;
                bw.write(contenido);
                contenido = add + ", RI, " +nuevaVariableDos;
                bw.write(contenido);
                contenido = mov +", " + variableAux + ", RI";
                bw.write(contenido);
                break;
            case "-":
                System.out.println("es una resta");
                contenido = mov + ", RI, " +nuevaVariableUno;
                bw.write(contenido);
                contenido = sub + ", RI, " +nuevaVariableDos;
                bw.write(contenido);
                contenido = mov +", " + variableAux + ", RI";
                bw.write(contenido);
                break;
            case "*":
                System.out.println("es una multiplicacion");
                contenido = mov + ", RI, " +nuevaVariableUno;
                bw.write(contenido);
                contenido = mul + ", RI, " +nuevaVariableDos;
                bw.write(contenido);
                contenido = mov +", " + variableAux + ", RI";
                bw.write(contenido);
                break;
            case "/":
                System.out.println("Es una divición");
                contenido = mov + ", RI, " +nuevaVariableUno;
                bw.write(contenido);
                contenido = div + ", RI, " +nuevaVariableDos;
                bw.write(contenido);
                contenido = mov +", " + variableAux + ", RI";
                bw.write(contenido);
                break;
            default:
                System.out.println("Operador sin sentido");
}
        }
    }
    public static void procesarTercetos(ListaTercetos listaTercetos){

        try {
            String ruta = "/ruta/filename.txt";
            String contenido = "Contenido de ejemplo";
            String nuevaVariableUno = "";
            String nuevaVariableDos= "";
            String variableAux= "@aux";

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

