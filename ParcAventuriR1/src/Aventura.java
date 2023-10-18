public class Aventura {

    private int cod;
    private String denumire;
    private float tarif;
    private int nrLocuri;

    public Aventura(int cod, String denumire, float tarif, int nrLocuri) {
        this.cod = cod;
        this.denumire = denumire;
        this.tarif = tarif;
        this.nrLocuri = nrLocuri;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "Aventura{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", tarif=" + tarif +
                ", nrLocuri=" + nrLocuri +
                '}';
    }
}
