package Compilador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivo {

    public GestorArchivo(){
    }

    // Abre el earchivo y te devuelve la lista de carateres con el codigo
    public static List<Character> readCode(String rutaArchivo){
        List<Character> listaCaracteres = new ArrayList<Character>();
        char[] cadenaCaracteres;
        String lineaCadenaTexto;
        Character value;
        // Leo caracter por caracter, hasta que finalice el archivo
        try {
            int iteradorListaCaracteres = 0;
            FileReader fileReader = new FileReader(rutaArchivo);
            BufferedReader LineaArchivo = new BufferedReader(fileReader);
            while((lineaCadenaTexto=LineaArchivo.readLine()) != null){
                cadenaCaracteres = lineaCadenaTexto.toCharArray();
                for (int i = 0; i < cadenaCaracteres.length; i++) {
                    listaCaracteres.add(cadenaCaracteres[i]);
                }
            }
            System.out.println(listaCaracteres);
            fileReader.close();
            return listaCaracteres;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    /*
     * int caracterArchivo = fileReader.read();
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
     */
}
