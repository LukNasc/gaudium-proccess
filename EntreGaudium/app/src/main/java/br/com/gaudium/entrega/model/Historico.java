package br.com.gaudium.entrega.model;

public class Historico {
    private String quando;
    private long entregas;
    private double valor;

    public String getQuando() {
        return quando;
    }

    public void setQuando(String quando) {
        this.quando = quando;
    }

    public long getEntregas() {
        return entregas;
    }

    public void setEntregas(long entregas) {
        this.entregas = entregas;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
