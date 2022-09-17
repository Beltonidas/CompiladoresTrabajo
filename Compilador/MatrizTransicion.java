package Compilador;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrizTransicion {
    private int matrizEstado [][];
    private List listCaracteresAcumulados;
    private int indexFila;
    private int indexCol;
    //Se puede implementar los simbolos con un hash, la idea es buscar la columna dependeindo el simbolo.
    private HashMap<String, Integer> simbolos;

    public MatrizTransicion(int[][] matriz, HashMap<String, Integer> indexSimbolo) {
        this.matrizEstado = matriz;
        this.simbolos = indexSimbolo;
        this.listCaracteresAcumulados = new ArrayList<>();
        this.indexCol = 0;
        this.indexFila = 0;
    }

    public void generarMatriz(){
        // Se completan los datos de la matriz de transicion

    }
    public void cargarSimbolos(){
        // hay que completar el hashmap para identificar los simbolos especias
        // .	<	>	!	"="	"+"	"-"	"*"	"/"	{	}	[	]	(	)	,	;	:	´	"_"	$
        // la idea de usar este map, es que dado el simbolo que entro nos devuelva la columna en el estado 0;
    }

    public void leerCaracterArchivo(char caracterArchivo){
        //Este metodo lee el caracater, se tiene que determinar que caracter es para que luego vaya ejecutando los saltos para luego llegar al estado final
        //System.out.println("el index columna "+ indexCol);
        //System.out.println("el index fila "+ indexCol);
        indexCol = identificarCaracter(caracterArchivo);
        indexFila = matrizEstado[indexFila][indexCol];
        //System.out.println("el index columna "+ indexCol);
        //System.out.println("el index fila "+ indexFila);
        if (indexFila == -1){
            // disparar accion semantica
            System.out.println("entre en el estado final");
            System.out.println("el token es: ");
            for (int i = 0; i < listCaracteresAcumulados.size(); i++) {
                System.out.println(listCaracteresAcumulados.get(i));
            }
            indexFila = 0;
            listCaracteresAcumulados.clear();
        }
        //System.out.println("Valor en la matriz= "+ matrizEstado[indexFila][indexCol]);
        listCaracteresAcumulados.add(caracterArchivo);
    }

    public int identificarCaracter(Character character){
        //Si no es ninguno de los anteriores entonces, buscar en la tabla
        String aux = String.valueOf(character);
        if (simbolos.containsKey(aux))
            return simbolos.get(aux);
        //Numero
        boolean isNumeric =  aux.matches("[+-]?\\d*(\\.\\d+)?");
        if (isNumeric)
            return 6;
        
        //Cadena vacia blanco
        if (aux.isEmpty())
            return 0;

        //Mayuscula
        if (aux.equals(aux.toLowerCase()))
            return 4;
        //Minuscula
        if (aux.equals(aux.toUpperCase()))
            return 5;
        return -1;
    }

    public void ejecutarMovimiento(){
        // en este metodo se simulan las tranciciones de estado
    }
}
