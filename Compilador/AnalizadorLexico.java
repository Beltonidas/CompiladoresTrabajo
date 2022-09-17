package Compilador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;




public class AnalizadorLexico {
    public static final int CANT_SIMBOLOS = 28;
    public static final int CANT_ESTADOS = 14;
    public static final String MATRIZ_TRANS = "C://Users//Tomas//OneDrive//Escritorio//Facultad//4to//Compiladores//CompiladoresTrabajo//testFiles//TablaTransicion.txt";
    public static final String ARCHIVO_SIMBOLO = "C://Users//Tomas//OneDrive//Escritorio//Facultad//4to//Compiladores//CompiladoresTrabajo//testFiles//TablaDistintosSimbolos.txt";
    
        /*
         * En el archivo de la matriz de transicion utilizamos la siguiente convencion:
         * Estado final, F = -1
         * Estado error, e = -2
         */
        public static int[][] cargarMatrizTrans(String ruta, int filas, int columnas) {
            int[][] matriz = new int[filas][columnas];
            
            try {
                File arch = new File(ruta);
                Scanner s = new Scanner(arch);
                
                for (int i=0; i<filas; i++) {
                    for (int j=0; j<columnas; j++) {
                        matriz[i][j] = Integer.parseInt(s.nextLine());
                    }
                }
                s.close();
            } catch (FileNotFoundException e) {
                System.out.println("No se ha podido leer el archivo localizado en: " + ruta);
                e.printStackTrace();
            }
            
            return matriz;
        }
    

    public static void main(String[] args) throws Exception {
        System.out.println("Hello there");

        //-- Generamos la instancia de gestor de archivo y luego procesamos los caracatres
        String ruta = "C://Users//Tomas//OneDrive//Escritorio//Facultad//4to//Compiladores//CompiladoresTrabajo//testFiles//prueba.txt";
        GestorArchivo gestorArchivo = new GestorArchivo(ruta);
        gestorArchivo.readCode();


        //Inicializamos la matrix
        int[][] matriz = cargarMatrizTrans(MATRIZ_TRANS, CANT_ESTADOS, CANT_SIMBOLOS);
        HashMap<String, Integer> indexSimbolo = new HashMap<String, Integer>();
        try {
            String simbolo;
            int index;
            File arch = new File(ARCHIVO_SIMBOLO);
            Scanner scanner = new Scanner(arch);
            //matriz[i][j] = Integer.parseInt(scanner.nextLine());
            while (scanner.hasNext()){
                simbolo = scanner.nextLine();
                index = Integer.parseInt(scanner.nextLine());
                indexSimbolo.put(simbolo, index);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha podido leer el archivo localizado en: " + ARCHIVO_SIMBOLO);
            e.printStackTrace();
        }
        MatrizTransicion matrizTransicion = new MatrizTransicion(matriz, indexSimbolo);        


        //Procesamos los caracteres y ejecutamos las transiciones de estado
        for (int i = 0; i < gestorArchivo.getCharacater().size(); i++) {
            //te moves en ña matrix de estado hasta encontrar el esatdo final
            //si estas en un estado final colocas -1 
            System.out.println("el simbolo es: "+ gestorArchivo.getCharacater().get(i));
            matrizTransicion.leerCaracterArchivo(gestorArchivo.getCharacater().get(i));
        }
        

    }
}
