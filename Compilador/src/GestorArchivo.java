import java.io.FileReader;

public class GestorArchivo {
    private FileReader fileReader;
    private char value;
    private MatrizTransicion matrizTransicion;

    public GestorArchivo(FileReader fileReader, MatrizTransicion matrizTransicion){
        this.fileReader = fileReader;
        this.matrizTransicion = matrizTransicion;
        this.value = (char) -1;
    }

    public void readCode(String urlArchivo){
        // Leo caracter por caracter, hasta que finalice el archivo

        try {
            int caracterArchivo = fileReader.read();
            while (caracterArchivo!= -1){
                value = (char)caracterArchivo;
                System.out.println(value);
                matrizTransicion.leerCaracterArchivo(value);
                caracterArchivo = fileReader.read();
            }
            fileReader.close();
            
        } catch (Exception e) {
            System.out.println("No se pudo leer el archivo");
        }
    }

}
