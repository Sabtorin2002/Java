public class Intretinere {

    private int nr_Apartament;
    private String nume;
    private String valoare_plata;

    public Intretinere(int nr_Apartament, String nume, String valoare_plata) {
        this.nr_Apartament = nr_Apartament;
        this.nume = nume;
        this.valoare_plata = valoare_plata;
    }
    public Intretinere(String linie)
    {
        this.nr_Apartament=Integer.parseInt(linie.split(",")[0]);
        this.nume=linie.split(",")[1];
        this.valoare_plata=linie.split(",")[2];
    }

    public int getNr_Apartament() {
        return nr_Apartament;
    }

    public void setNr_Apartament(int nr_Apartament) {
        this.nr_Apartament = nr_Apartament;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getValoare_plata() {
        return valoare_plata;
    }

    public void setValoare_plata(String valoare_plata) {
        this.valoare_plata = valoare_plata;
    }

    @Override
    public String toString() {
        return "Intretinere{" +
                "nr_Apartament=" + nr_Apartament +
                ", nume='" + nume + '\'' +
                ", valoare_plata='" + valoare_plata + '\'' +
                '}';
    }
}
