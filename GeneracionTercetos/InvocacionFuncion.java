package GeneracionTercetos;

public class InvocacionFuncion {
    
    private String tercetoInv;
    private String par1,par2;
    
    public InvocacionFuncion() {
    }
    
    public String getTercetoInv() {
        if (tercetoInv != null)
            return tercetoInv;
        return "";
    }
    public void setTercetoInv(String tercetoInv) {
        this.tercetoInv = tercetoInv;
    }
    public String getPar1() {
        if (par1!=null)
                return par1;
        return "";
    }
    public void setPar1(String par1) {
        this.par1 = par1;
    }
    public String getPar2() {
        if (par2!=null)
            return par2;
        return "";
    }
    public void setPar2(String par2) {
        this.par2 = par2;
    }
    
    @Override
    public String toString() {
        String msg="";
        if (tercetoInv!=null) {
            msg=msg+"Terceto de invocacion: "+tercetoInv;
        }
        if (par1!=null) {
            msg=msg+" | Parametro 1: "+par1;
        }
        if (par2!=null) {
            msg=msg+" | Parametro 2: "+par2;
        }
        return msg;
    }
}
