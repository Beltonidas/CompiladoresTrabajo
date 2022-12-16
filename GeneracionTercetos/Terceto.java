package GeneracionTercetos;

public class Terceto {
    String parg,sarg,targ;
    Boolean carg;
    int sarg_r = -1;
    int targ_r = -1;
    static int id_estatico = 0;
    int id;
    
    public Terceto(String a, String b, String c) {
        parg=a;
        sarg=b;
        targ=c;
        carg=false;
        setId();
    }
    
    public Terceto(String a, String b, String c, Boolean d) {
        parg=a;
        sarg=b;
        targ=c;
        carg=d;
        setId();
    }

    public int getSargR() {
        return sarg_r;
    }

    public int getTargR() {
        return targ_r;
    }

    public void setSargR(int arg) {
        sarg_r=arg;
    }

    public void setTargR(int arg) {
        targ_r=arg;
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
            return (id+": "+carg+":( "+parg+" , "+sarg+" | "+sarg_r+", "+targ+" | "+targ_r+" )");
        }
        return ("( "+id+": "+parg+" , "+sarg+" | "+sarg_r+", "+targ+" | "+targ_r+" )");
    }
    
}
