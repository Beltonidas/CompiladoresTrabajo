package GeneracionTercetos;

public class Terceto {
    String parg,sarg,targ;
    
    
    public Terceto(String a, String b, String c) {
        parg=a;
        sarg=b;
        targ=c;
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


    public String imprimir() {
        return ("( "+parg+" , "+sarg+" , "+targ+" )");
    }
    
    @Override
    public String toString() {
        return ("( "+parg+" , "+sarg+" , "+targ+" )");
    }
    
}