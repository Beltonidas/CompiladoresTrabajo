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
        System.out.println("el index es"+ indexCol);
        indexCol = identificarCaracter(caracterArchivo);
        System.out.println("el index es"+ indexCol);
        listCaracteresAcumulados.add(caracterArchivo);
        if(matrizEstado[indexFila][indexCol] == -1){
            // disparar accion semantica
            System.out.println("entre en eñ estado final");
            indexFila = 0;
            listCaracteresAcumulados.clear();
        }
        indexFila = matrizEstado[indexFila][indexCol];
    }

    public int identificarCaracter(Character character){
        //Si no es ninguno de los anteriores entonces, buscar en la tabla
        String aux = String.valueOf(character);
        //Mayuscula
        if (aux.equals(aux.toLowerCase()))
            return 4;
        if (aux.equals(aux.toUpperCase()))
            return 5;
        
        
        return simbolos.get(aux);
    }

    public void ejecutarMovimiento(){
        // en este metodo se simulan las tranciciones de estado
    }
}
