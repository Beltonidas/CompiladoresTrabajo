import java.io.FileReader;
import java.util.ArrayList;

public class GestorArchivo {
    private FileReader fileReader;
    private char value;
    private MatrizTransicion matrizTransicion;
    private ArrayList listCaracteres;
    private String rutaArchivo;

    public GestorArchivo(String rutaArchivo){
        this.fileReader = null;
        //this.matrizTransicion = matrizTransicion;
        this.value = (char) -1;
        this.listCaracteres = new ArrayList<Character>();
        this.rutaArchivo = rutaArchivo;
    }


    public void readCode(){
        // Leo caracter por caracter, hasta que finalice el archivo
        try {
            fileReader = new FileReader(rutaArchivo);
            int caracterArchivo = fileReader.read();
            while (caracterArchivo!= -1){
                value = (char)caracterArchivo;
                System.out.println(value);
                listCaracteres.add(value);
                //matrizTransicion.leerCaracterArchivo(value);
                caracterArchivo = fileReader.read();
            }
            fileReader.close();
            
        } catch (Exception e) {
            System.out.println("No se pudo leer el archivo");
        }
    }

    public ArrayList<Character> getCharacater(){
        return listCaracteres;
    }

}
