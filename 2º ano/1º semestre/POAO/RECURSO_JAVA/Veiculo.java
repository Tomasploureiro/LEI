public class Veiculo {
    private String marca;
    private String modelo;
    private int matricula;
    private int ano_matricula;
    private double distancia_percorrida;

    
    public Veiculo(String marca, String modelo, int matricula, int ano_matricula, double distancia_percorrida) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.ano_matricula = ano_matricula;
        this.distancia_percorrida = distancia_percorrida;
    }

    public String getMarca(){
        return marca;
    }
    public String getModelo(){
        return modelo;
    }
    public int getMatricula(){
        return matricula;
    }
    public int getAnoMatricula(){
        return ano_matricula;
    }
    public double getDistanciapercorrida(){
        return distancia_percorrida;
    }


    public void setMarca(String marca){
        this.marca = marca;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    public void setMatricula(int matricula){
        this.matricula = matricula;
    }
    public void setAnoMatricula (int ano_matricula){
        this.ano_matricula = ano_matricula;
    }
    public void setDistanciapercorrida(double distancia_percorrida){
        this.distancia_percorrida = distancia_percorrida;
    }
}
