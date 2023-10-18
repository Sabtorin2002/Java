public class Auxliar {

    private int cod;
    private String nume;
    private int nrOptiuni;
    private double medie;

    public Auxliar(int cod, String nume, int nrOptiuni, double medie) {
        this.cod = cod;
        this.nume = nume;
        this.nrOptiuni = nrOptiuni;
        this.medie = medie;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNrOptiuni() {
        return nrOptiuni;
    }

    public void setNrOptiuni(int nrOptiuni) {
        this.nrOptiuni = nrOptiuni;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    @Override
    public String toString() {
        return "Auxliar{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                ", nrOptiuni=" + nrOptiuni +
                ", medie=" + medie +
                '}';
    }
}
