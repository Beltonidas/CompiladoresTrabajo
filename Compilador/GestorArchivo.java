package Compilador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivo {

    public GestorArchivo(){
    }

    // Abre el archivo y te devuelve una lista de lista<Caracteres>

    public static List<List<Character>> readCode(String rutaArchivo){
        List<List<Character>> listaCaracteres = new ArrayList<>();
        char[] cadenaCaracteres;
        String lineaCadenaTexto;
        // Leo caracter por caracter, hasta que finalice el archivo
        try {
            int iteradorListaCaracteres = 0;
            FileReader fileReader = new FileReader(rutaArchivo);
            BufferedReader LineaArchivo = new BufferedReader(fileReader); // Leo por lineas el codigo fuente

            while((lineaCadenaTexto=LineaArchivo.readLine()) != null){
                List<Character> listaCaractereslinea = new ArrayList<Character>(); //Creo una nueva estructura
                cadenaCaracteres = lineaCadenaTexto.toCharArray(); // creo un arreglo de toda la linea --> "ejemplo" = [e,j,e,m,p,l,o]
                for (int i = 0; i < cadenaCaracteres.length; i++) {
                    listaCaractereslinea.add(cadenaCaracteres[i]);
                }
                listaCaracteres.add(listaCaractereslinea);
            }
            fileReader.close();
            return listaCaracteres;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }




    //--------CODIGO BASURA------------77
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
     */
}
