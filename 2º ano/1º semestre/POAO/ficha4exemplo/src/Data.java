public class Data {
    private int dia, mes, ano;

    public Data() {
    }

    public Data(int d, int m, int a) {
        dia = d;
        mes = m;
        ano = a;
    }

    public void setDia(int d) {
        dia = d;
    }

    public int getDia() {
        return dia;
    }

    public void setMes(int m) {
        mes = m;
    }

    public int getMes() {
        return mes;
    }

    public void setAno(int a) {
        ano = a;
    }

    public int getAno() {
        return ano;
    }

    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }
}