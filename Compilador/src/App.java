import java.io.FileReader;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello there");
        MatrizTransicion matrizTransicion = new MatrizTransicion(14, 21);

        //-- Generamos la instancia de gestor de archivo y luego procesamos los caracatres
        try {
            FileReader fileReader = new FileReader("C://CompiladoresTrabajo//Compilador//src//prueba.txt");
            GestorArchivo gestorArchivo = new GestorArchivo(fileReader, matrizTransicion);
            gestorArchivo.readCode("urlArchivo");
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("No se puede leer el archivo");
        }
    }
}
