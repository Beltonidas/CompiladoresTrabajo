package Compilador;
import java.util.ArrayList;
import java.util.List;




public class AnalizadorLexico {
        
    public static void main(String[] args) throws Exception {
        //Cargamos el archivo
        String ruta = "./testFiles/prueba.txt";
        List<Character> listaCaracteres = GestorArchivo.readCode(ruta);
        List<Character> listaCaracteresAcumulados = new ArrayList<>();
        //System.out.println(listaCaracteres);

        //Cargamos la matriz de transicion
        MatrizTransicion matrizTransicion = new MatrizTransicion();


         /* int i = 0;
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
         } */
        //Procesamos el archivo
        int iteradorListaCaracteres = 0;
        int valorSignoColumna = -1;
        int proximoMovimiento;
        Character simboloProcesar;
        while (iteradorListaCaracteres < listaCaracteres.size()){
            simboloProcesar = listaCaracteres.get(iteradorListaCaracteres);
            valorSignoColumna = matrizTransicion.identificarCaracter(simboloProcesar);
            //System.out.println("el valor de la columna es: " + valorSignoColumna);
            proximoMovimiento = matrizTransicion.ejecutarCambioEstado(simboloProcesar, valorSignoColumna);
            if (valorSignoColumna != 0)
                listaCaracteresAcumulados.add(simboloProcesar);
            if (proximoMovimiento < 0){
                //Estamos en un estado final hay que disparar acciones semanticas
                System.out.println(listaCaracteresAcumulados);
                System.out.println("disparar accion semantica");
                matrizTransicion.setIndexFila(0);
                listaCaracteresAcumulados.clear();
            }
            iteradorListaCaracteres++;
        }
    }
}