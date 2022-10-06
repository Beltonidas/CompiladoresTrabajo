package Compilador;
import java.util.HashMap;
import java.util.List;

import Compilador.AccionesSemanticas.AccionSemantica;




public class AnalizadorLexico {
	
    private TablaSimbolos teibol = new TablaSimbolos();
	private MatrizTransicion matrizTransicion = new MatrizTransicion(this);
	private List<List<Character>> archivoCodigoFuente;
	private int iteradorLineaCaracteres = 0;
	private int iteradorListaCaracteres = 0;
    private Character simboloProcesar;
    private List<Character> lineaProcesar = null;
    private int tokenEntregar = -1;
    public static TokenLexema anteriorToken;
	
	public AnalizadorLexico(String ruta) {
	    archivoCodigoFuente = GestorArchivo.readCode(ruta);
	    anteriorToken=AccionSemantica.getNewToken();
	    lineaProcesar = archivoCodigoFuente.get(iteradorListaCaracteres);
	}
	
	public HashMap<String, Integer> getTablasSimbolos(){
        return matrizTransicion.getTablasSimbolos();
	}
        
	public int getLinea() {
	    return iteradorListaCaracteres+1;
	}
	
	public int getCaracter() {
        return iteradorLineaCaracteres+1;
    }
	
	public void entregarToken(int idToken) {
	    tokenEntregar=idToken;
	    anteriorToken=AccionSemantica.getToken();
	}
	
	public void avanzarLectura() {
		iteradorLineaCaracteres++;
	}
	
	public int siguienteToken() {
	    tokenEntregar=-1;
	    while (iteradorListaCaracteres < archivoCodigoFuente.size()) {
	        while (iteradorLineaCaracteres < lineaProcesar.size()) {
                simboloProcesar = lineaProcesar.get(iteradorLineaCaracteres);
                //System.out.print(simboloProcesar);
                matrizTransicion.transicionCaracter(simboloProcesar);
                if (tokenEntregar!=-1){
                    return tokenEntregar;
                }
            }
	        iteradorListaCaracteres++;
	        if (iteradorListaCaracteres < archivoCodigoFuente.size()) {
	            lineaProcesar = archivoCodigoFuente.get(iteradorListaCaracteres);
	        }
	        iteradorLineaCaracteres = 0;
	    }
	    return tokenEntregar;
	}
}