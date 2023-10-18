public class Auxiliar {

    private String denumire;
    private int nrLocuri_rezervate;
    private double valoare;

    public Auxiliar(String denumire, int nrLocuri_rezervate, double valoare) {
        this.denumire = denumire;
        this.nrLocuri_rezervate = nrLocuri_rezervate;
        this.valoare = valoare;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNrLocuri_rezervate() {
        return nrLocuri_rezervate;
    }

    public void setNrLocuri_rezervate(int nrLocuri_rezervate) {
        this.nrLocuri_rezervate = nrLocuri_rezervate;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return "Auxiliar{" +
                "denumire='" + denumire + '\'' +
                ", nrLocuri_rezervate=" + nrLocuri_rezervate +
                ", valoare=" + valoare +
                '}';
    }
}
