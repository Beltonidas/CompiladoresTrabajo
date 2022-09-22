package Compilador;
import java.util.ArrayList;
import java.util.List;




public class AnalizadorLexico {
        
    public static void main(String[] args) throws Exception {
        //Cargamos el archivo
        String ruta = "./testFiles/prueba.txt";
        List<List<Character>> listaCaracteres = GestorArchivo.readCode(ruta);

        //Cargamos la matriz de transicion
        MatrizTransicion matrizTransicion = new MatrizTransicion();
        
        //Procesamos el archivo

        int iteradorListaCaracteres = 0;
        int iteradorLineaCaracteres = 0;
        Character simboloProcesar;
        List<Character> lineaProcesar = null;
        int proximoMovimiento;

        while (iteradorListaCaracteres < listaCaracteres.size()){
            lineaProcesar = listaCaracteres.get(iteradorListaCaracteres);
            while (iteradorLineaCaracteres < lineaProcesar.size()) {
                simboloProcesar = lineaProcesar.get(iteradorLineaCaracteres);
                // enviar simbolo a la matriz de transicion
                proximoMovimiento = matrizTransicion.transicionCaracter(simboloProcesar, false);
                // aca habria que tener en cuenta que hay que pedir el token generado
                iteradorLineaCaracteres = iteradorLineaCaracteres + proximoMovimiento;
            }
            iteradorLineaCaracteres = 0;   
            iteradorListaCaracteres++;
        }
            
    }
}