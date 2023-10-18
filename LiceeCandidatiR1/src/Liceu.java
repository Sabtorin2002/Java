public class Liceu {
    private int cod_specializare;
    private String nume;
    private int nr_specializari;
    private int nr_locuri;

    public Liceu(int cod_specializare, String nume, int nr_specializari, int nr_locuri) {
        this.cod_specializare = cod_specializare;
        this.nume = nume;
        this.nr_specializari = nr_specializari;
        this.nr_locuri = nr_locuri;
    }

    public Liceu(String linie)
    {
        this.cod_specializare=Integer.parseInt(linie.split(",")[0]);
        this.nume=linie.split(",")[1];
        this.nr_specializari=Integer.parseInt(linie.split(",")[2]);
        this.nr_locuri=Integer.parseInt(linie.split(",")[3]);
    }

    public int getCod_specializare() {
        return cod_specializare;
    }

    public void setCod_specializare(int cod_specializare) {
        this.cod_specializare = cod_specializare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNr_specializari() {
        return nr_specializari;
    }

    public void setNr_specializari(int nr_specializari) {
        this.nr_specializari = nr_specializari;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public String toString() {
        return "Liceu{" +
                "cod_specializare=" + cod_specializare +
                ", nume='" + nume + '\'' +
                ", nr_specializari=" + nr_specializari +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}
