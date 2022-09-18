package Compilador;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivo {

    public GestorArchivo(){
    }

    // Abre el earchivo y te devuelve la lista de carateres con el codigo
    public static List<Character> readCode(String rutaArchivo){
        List<Character> listaCaracteres = new ArrayList<Character>();
        // Leo caracter por caracter, hasta que finalice el archivo
        try {
            Character value;
            FileReader fileReader = new FileReader(rutaArchivo);
            int caracterArchivo = fileReader.read();
            while (caracterArchivo!= -1){
                value = (char)caracterArchivo;
                listaCaracteres.add(value);
                System.out.print(value);
                caracterArchivo = fileReader.read();
            }
            fileReader.close();
            return listaCaracteres;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
