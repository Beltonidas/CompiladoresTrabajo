import java.io.FileReader;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello there");
        try {
            FileReader fileReader = new FileReader("C://CompiladoresTrabajo//Compilador//src//prueba.txt");
            int valor = fileReader.read();
            while (valor!= -1){
                System.out.println((char)valor);
                valor = fileReader.read();
            }
            fileReader.close();
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("No se puede leer el archivo");
        }
    }
}
