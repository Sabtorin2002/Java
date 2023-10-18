public class Penalizare {
    private int nr_apartament;
    private double valoare;

    public Penalizare(int nr_apartament, double valoare) {
        this.nr_apartament = nr_apartament;
        this.valoare = valoare;
    }

    public int getNr_apartament() {
        return nr_apartament;
    }

    public void setNr_apartament(int nr_apartament) {
        this.nr_apartament = nr_apartament;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return "Penalizare{" +
                "nr_apartament=" + nr_apartament +
                ", valoare=" + valoare +
                '}';
    }
}
