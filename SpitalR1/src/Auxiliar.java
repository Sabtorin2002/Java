public class Auxiliar {

    private int cod;
    private String denumire;
    private int nr_locuri;
    private double varsta_medie;

    public Auxiliar(int cod, String denumire, int nr_locuri, double varsta_medie) {
        this.cod = cod;
        this.denumire = denumire;
        this.nr_locuri = nr_locuri;
        this.varsta_medie = varsta_medie;
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

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    public double getVarsta_medie() {
        return varsta_medie;
    }

    public void setVarsta_medie(double varsta_medie) {
        this.varsta_medie = varsta_medie;
    }

    @Override
    public String toString() {
        return "Auxiliar{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", nr_locuri=" + nr_locuri +
                ", varsta_medie=" + varsta_medie +
                '}';
    }
}
