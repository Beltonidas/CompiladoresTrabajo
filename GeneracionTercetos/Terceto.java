package GeneracionTercetos;

public class Terceto {
    String parg,sarg,targ;
    Boolean carg;
    
    
    public Terceto(String a, String b, String c) {
        parg=a;
        sarg=b;
        targ=c;
        carg=false;
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
            return (carg+":( "+parg+" , "+sarg+" , "+targ+" )");
        }
        return ("( "+parg+" , "+sarg+" , "+targ+" )");
    }
    
}
