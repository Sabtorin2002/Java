public class Inscriere {
    private long cnp;
    private String nume;
    private double nota;
    private int cod;

    public Inscriere(long cnp, String nume, double nota, int cod) {
        this.cnp = cnp;
        this.nume = nume;
        this.nota = nota;
        this.cod = cod;
    }

    public Inscriere(String linie) {
        this.cnp=Long.parseLong(linie.split(",")[0]);
        this.nume=linie.split(",")[1];
        this.nota=Double.parseDouble(linie.split(",")[2]);
        this.cod= Integer.parseInt(linie.split(",")[3]);
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Inscriere{" +
                "cnp=" + cnp +
                ", nume='" + nume + '\'' +
                ", nota=" + nota +
                ", cod=" + cod +
                '}';
    }
}
