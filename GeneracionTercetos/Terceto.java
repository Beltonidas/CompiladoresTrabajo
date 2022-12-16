package GeneracionTercetos;

import java.util.ArrayList;
import java.util.List;

public class Terceto {
    String parg,sarg,targ;
    Boolean carg;
    static int id_estatico = 0;
    static List<Terceto> padron_Tercetos = new ArrayList<Terceto>();
    int id;
    
    public static Terceto GetTerceto(int indice) {
        return padron_Tercetos.get(indice);
    }
    
    public static Terceto GetTerceto(String indice) {
        if(indice.startsWith("[")) {
            StringBuilder aux = new StringBuilder(indice);
            aux.deleteCharAt(0);
            aux.deleteCharAt(aux.length()-1);
            return padron_Tercetos.get(Integer.parseInt(aux.toString()));
        }
        return padron_Tercetos.get(Integer.parseInt(indice));
    }
    
    public static int GetIndice() {
        return id_estatico;
    }
    
    public Terceto(String a, String b, String c) {
        parg=a;
        sarg=b;
        targ=c;
        carg=false;
        setId();
        padron_Tercetos.add(this);
        //System.out.println(this);
    }
    
    public Terceto(String a, String b, String c, Boolean d) {
        parg=a;
        sarg=b;
        targ=c;
        carg=d;
        setId();
        padron_Tercetos.add(this);
        //System.out.println(this);
    }

    public void setId(){
        id = id_estatico;
        id_estatico++;
    }

    public String getParg() {
        return parg;
    }


    public void setParg(String parg) {
        this.parg = parg;
    }


    public String getSarg() {
        return sarg;
    }


    public void setSarg(String sarg) {
        this.sarg = sarg;
    }


    public String getTarg() {
        return targ;
    }


    public void setTarg(String targ) {
        this.targ = targ;
    }
    
    public Boolean getCarg() {
        return carg;
    }


    public void setCarg(Boolean carg) {
        this.carg = carg;
    }
    
    @Override
    public String toString() {
        if (carg!=false) {
            return (id+". "+carg+":( "+parg+" , "+sarg+", "+targ+" )");
        }
        return (id+". ( "+parg+" , "+sarg+", "+targ+" )");
    }
    
}
