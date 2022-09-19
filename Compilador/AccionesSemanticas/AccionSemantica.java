package Compilador.AccionesSemanticas;

import java.util.List;

public interface AccionSemantica {
    public void ejecutar(List<Character> listaCarateres, Character character);
}
