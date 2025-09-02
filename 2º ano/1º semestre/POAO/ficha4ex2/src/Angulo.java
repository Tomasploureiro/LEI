public class Angulo {

    private double graus;

    public Angulo() {
    }

    public Angulo(double graus) {
        this.graus = graus;
    }

    public Angulo adicao(Angulo graus2) {
        return new Angulo(this.graus + graus2.graus);
    }

    public Angulo subtracao(Angulo graus2) {
        return new Angulo(this.graus - graus2.graus);

    }

    public void setGraus(int graus) {
        this.graus = graus;
    }

    public double getGraus() {
        return this.graus;
    }

    public String toString() {
        return "ângulo de " + graus + " graus";
    }
}
