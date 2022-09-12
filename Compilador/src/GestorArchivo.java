import java.io.FileReader;

public class GestorArchivo {
    private FileReader fileReader;
    private int value;

    public GestorArchivo(FileReader fileReader){
        this.fileReader = fileReader;
    }

    public void readCode(String urlArchivo){

        try {
            int value = fileReader.read();
            while (value!= -1){
                System.out.println((char)value);
                value = fileReader.read();
            }
            fileReader.close();
            
        } catch (Exception e) {
            System.out.println("No se pudo leer el archivo");
        }
    }

}
