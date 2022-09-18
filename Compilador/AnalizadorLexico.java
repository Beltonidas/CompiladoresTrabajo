package Compilador;
import java.util.List;




public class AnalizadorLexico {
        
    public static void main(String[] args) throws Exception {
        //Cargamos el archivo
        String ruta = "./testFiles/prueba.txt";
        List<Character> listaCaracteres = GestorArchivo.readCode(ruta);

        //Cargamos la matriz de transicion
        MatrizTransicion matrizTransicion = new MatrizTransicion();   
     
        //Procesamos el archivo
        int i = 0;
        boolean unico = false;
        while (i<listaCaracteres.size()) {
            Integer proximo = matrizTransicion.leerCaracterArchivo(listaCaracteres.get(i),unico);
            if (proximo == 0){
                //Chequear que no sea un simbolo de longitud 1
                unico = true;
            }
            if (unico){
                proximo=1;
                unico=false;
            }
            i=i+proximo;
        }
    }
}