public class Pacient {

    private long cnp;
    private String nume;
    private int varsta;
    private int cod_sectie;

    public Pacient(long cnp, String nume, int varsta, int cod_sectie) {
        this.cnp = cnp;
        this.nume = nume;
        this.varsta = varsta;
        this.cod_sectie = cod_sectie;
    }

    public Pacient(String linie)
    {
        this.cnp=Long.parseLong(linie.split(",")[0]);
        this.nume=linie.split(",")[1];
        this.varsta=Integer.parseInt(linie.split(",")[2]);
        this.cod_sectie=Integer.parseInt(linie.split(",")[3]);
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getCod_sectie() {
        return cod_sectie;
    }

    public void setCod_sectie(int cod_sectie) {
        this.cod_sectie = cod_sectie;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cnp=" + cnp +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", cod_sectie=" + cod_sectie +
                '}';
    }
}
