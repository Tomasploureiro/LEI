public class Estatisticas {
    private int statsEquipa1;
    private int statsEquipa2;

    public Estatisticas(){
    }

    public Estatisticas(int statsEquipa1, int statsEquipa2){
        this.statsEquipa1 = statsEquipa1;
        this.statsEquipa2 = statsEquipa2;
    }

    public void setStatsEquipa1(int statsEquipa1) {
        this.statsEquipa1 = statsEquipa1;
    }

    public int getStatsEquipa1() {
        return statsEquipa1;
    }

    public void setStatsEquipa2(int statsEquipa2) {
        this.statsEquipa2 = statsEquipa2;
    }

    public int getStatsEquipa2() {
        return statsEquipa2;
    }

    public String toString() {
        return "{" +
            " golosEquipa1='" + getStatsEquipa1() + "'" +
            ", golosEquipa2='" + getStatsEquipa2() + "'" +
            "}";
    }

}
