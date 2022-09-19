package Compilador.AccionesSemanticas;

import java.util.List;

public class VerificarRangoInteger implements AccionSemantica {
    
    private int numero;
    public VerificarRangoInteger (){

    }

    @Override
    public void ejecutar(List<Character> listaCarateres, Character character){
        //Supongo que viene un arreglo de character y el cactaer, que es lo que le tengo que entregar a la tabla de simbolos
        //Primero convertimos la lista en una cadena e caracteres
        String numString ="";
        for (int i = 0; i < listaCarateres.size(); i++) {
            numString = numString + String.valueOf(listaCarateres.get(i));
        }
        numero = Integer.parseInt(numString);
        //Verificar el numero, no debe ser superior a 255
        if (numero < 256){
            //el Caractaer es valido se arma el token
        }else numero = 255; // colocamos el valor maximo (decision)

    }
    
}
