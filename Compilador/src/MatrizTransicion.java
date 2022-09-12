import java.util.HashMap;
import java.util.Map;

public class MatrizTransicion {
    private int matrizEstado [][];
    private int row;
    private int col;
    //Se puede implementar los simbolos con un hash, la idea es buscar la columna dependeindo el simbolo.
    private Map simbolos;

    public MatrizTransicion(int row, int col) {
        this.matrizEstado = new int[row][col];
        this.simbolos = new HashMap<Character, Integer>();
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
    }

    public int identificarCaracter(){
        //este metodo dependiendo del carcater que toque lo identifica y retorna el valor de la columna
        return -1;
    }

    public void ejecutarMovimiento(){
        // en este metodo se simulan las tranciciones de estado
    }
}
