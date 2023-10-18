public class Apartament {
    private int nr_apartament;
    private String nume;
    private int suprafata;
    private int nr_persoane;

    public Apartament(int nr_apartament, String nume, int suprafata, int nr_persoane) {
        this.nr_apartament = nr_apartament;
        this.nume = nume;
        this.suprafata = suprafata;
        this.nr_persoane = nr_persoane;
    }

    public int getNr_apartament() {
        return nr_apartament;
    }

    public void setNr_apartament(int nr_apartament) {
        this.nr_apartament = nr_apartament;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getNr_persoane() {
        return nr_persoane;
    }

    public void setNr_persoane(int nr_persoane) {
        this.nr_persoane = nr_persoane;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "nr_apartament=" + nr_apartament +
                ", nume='" + nume + '\'' +
                ", suprafata=" + suprafata +
                ", nr_persoane=" + nr_persoane +
                '}';
    }
}
