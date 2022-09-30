package Compilador;
import java.util.List;




public class AnalizadorLexico {
	
	private MatrizTransicion matrizTransicion = new MatrizTransicion(this);
	private List<List<Character>> archivoCodigoFuente;
	private int iteradorLineaCaracteres = 0;
	//private BufferTokens bufferTokens = new BufferTokens();
	
	public AnalizadorLexico(){
	}
        
	public void avanzarLectura() {
		iteradorLineaCaracteres++;
	}
	
	public void entregarToken(String token) {
		//this.bufferTokens.add(token);
		System.out.println("El token entregao es: "+token);
	}
	
    public void ejecutar(String ruta) {
        //Cargamos el archivo
        archivoCodigoFuente = GestorArchivo.readCode(ruta);
        
        //Procesamos el archivo
        int iteradorListaCaracteres = 0;
        iteradorLineaCaracteres = 0;
        Character simboloProcesar;
        List<Character> lineaProcesar = null;
        while (iteradorListaCaracteres < archivoCodigoFuente.size()){
            lineaProcesar = archivoCodigoFuente.get(iteradorListaCaracteres);
            while (iteradorLineaCaracteres < lineaProcesar.size()) {
                simboloProcesar = lineaProcesar.get(iteradorLineaCaracteres);
                System.out.println(simboloProcesar);
                matrizTransicion.transicionCaracter(simboloProcesar, false);
            }
            iteradorLineaCaracteres = 0;   
            iteradorListaCaracteres++;
        }
    }
}