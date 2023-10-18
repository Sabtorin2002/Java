import java.util.List;
import java.util.Map;

public class Candidat {

    private int cod_candidat;
    private String nume;
    private double medie;
    private Map<Integer, List<Integer>> optiuni;

    public Candidat(int cod_candidat, String nume, double medie, Map<Integer, List<Integer>> optiuni) {
        this.cod_candidat = cod_candidat;
        this.nume = nume;
        this.medie = medie;
        this.optiuni = optiuni;
    }

    public int getCod_candidat() {
        return cod_candidat;
    }

    public void setCod_candidat(int cod_candidat) {
        this.cod_candidat = cod_candidat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public Map<Integer, List<Integer>> getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(Map<Integer, List<Integer>> optiuni) {
        this.optiuni = optiuni;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "cod_candidat=" + cod_candidat +
                ", nume='" + nume + '\'' +
                ", medie=" + medie +
                ", optiuni=" + optiuni +
                '}';
    }

//    public int getNrOptiuni()
//    {
//        int nrOptiuni=0;
//        for(var optiune:optiuni.entrySet())
//            nrOptiuni+=optiune.getValue().size();
//
//        return nrOptiuni;
//    }

//    public int getNrOptiuni()
//    {
//        int nrOptiuni=0;
//        for(var optiune:optiuni.entrySet())
//            nrOptiuni+=optiune.getValue().size();
//
//        return nrOptiuni;
//    }

    public int getNrOptiuni()
    {
        int nrOptiuni=0;
        for(var optiune:optiuni.entrySet())
            nrOptiuni+=optiune.getValue().size();

        return nrOptiuni;
    }
}
