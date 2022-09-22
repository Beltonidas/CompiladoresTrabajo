package Compilador.AccionesSemanticas;

import java.util.List;

public class VerificarRangoDouble extends AccionSemantica {
    


    public VerificarRangoDouble (){

    }


    @Override
    public int ejecutar(Character caracter){
        //Supongo que viene un arreglo de character y el caracter, que es lo que le tengo que entregar a la tabla de simbolos
        //Primero convertimos la lista en una cadena e caracteres
        String numString ="";
        char punto= '.';
        Double numero;
        // En este caso tenemos las siguientes casos
        /*
         * 1.0
         * 0.1
         * .1
         * 
        */
        String lexema = super.tokenActual.getLexema();
        if (Character.compare(lexema.charAt(0), punto) == 0){
            // es del estilo con punto
            numString = "0";
            for (int i = 0; i < lexema.length(); i++) {
                numString = numString + lexema;
            }
            numero = Double.parseDouble(numString);
        }else{
            numero = Double.parseDouble(numString);
        }
        //Verificar el numero, este debe estar bajo el siguiente rango: 1.17549435F-38 < x < 3.40282347F+38 U -1.7976931348623157D+308 < x < -2.2250738585072014D-308  0.0
        if (Double.MIN_VALUE < numero && numero < Double.MAX_VALUE){
        	return 0;
        } // colocamos el valor maximo (decision)
        return -10;

    }
    
}
