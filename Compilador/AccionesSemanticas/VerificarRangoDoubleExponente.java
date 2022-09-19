package Compilador.AccionesSemanticas;

import java.util.List;

public class VerificarRangoDoubleExponente implements AccionSemantica {

    private Double numero;
    private int exponenteNumero;
    private int exponeneteMaximo = 308;
    private int exponeneteMinimo = -308;
    public VerificarRangoDoubleExponente (){

    }


    @Override
    public void ejecutar(List<Character> listaCarateres, Character character){
        //Supongo que viene un arreglo de character y el cactaer, que es lo que le tengo que entregar a la tabla de simbolos
        //Primero convertimos la lista en una cadena e caracteres
        String numString ="";
        String numExponente = "E";
        char punto= '.';
        char exponente = 'D';
        boolean exp = false;

        // Tenemos que identificar donde esta el exponente, si es negativo y pasar la verificación de los numeros
        /*
         * Los posibles casos son
         * (Caso 1)
         * .3 D 120
         * .3 D +120
         * .3 D -120
         * 
         * (Caso 2)
         * 3.0 D 120
         * 3.0 D +120
         * 3.0 D -120
         * 
         * (Caso 3)   ,,
         * 0.3 D 120
         * 0.3 D +120
         * 0.3 D -120
         * 
        */

        // Caso 1
        if (Character.compare(listaCarateres.get(0), punto) == 0){
            // es del estilo con punto
            numString = "0";
            int i = 0;
            while (Character.compare(listaCarateres.get(i), exponente) != 0){
                numString = numString + String.valueOf(listaCarateres.get(i));
                i++;
            }
            for (int j = i+1; j < listaCarateres.size(); j++) {
                numExponente = numExponente + String.valueOf(listaCarateres.get(j));
            }

            exponenteNumero = Integer.parseInt(numExponente);
            if (exponenteNumero > exponeneteMinimo && exponenteNumero < exponeneteMaximo){
                exp = true;
                numString = numString + "E" + numExponente;
                numero = Double.parseDouble(numString);
                if (Double.MIN_VALUE < numero && numero < Double.MAX_VALUE){
                    //retornamos
                }
            }
        }else{
            numero = Double.parseDouble(numString);
            // Tomar decisión
        }

    }
    
}
