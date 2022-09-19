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
            int iteradorListaCaracteres = 0;
            FileReader fileReader = new FileReader(rutaArchivo);
            int caracterArchivo = fileReader.read();
            while (caracterArchivo!= -1){
                //System.out.println("el caracter en el archivo es: "+caracterArchivo);
                value = (char)caracterArchivo;
                listaCaracteres.add(iteradorListaCaracteres,value);
                //System.out.println("agrege el caracter= "+value);
                iteradorListaCaracteres++;
                caracterArchivo = fileReader.read();
            }
            System.out.println("la lista de caracteres tiene: "+ listaCaracteres.size());
            fileReader.close();
            return listaCaracteres;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
