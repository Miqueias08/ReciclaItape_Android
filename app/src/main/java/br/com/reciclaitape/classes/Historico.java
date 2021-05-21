package br.com.reciclaitape.classes;

public class Historico {
    private int id;
    private Double peso;
    private String tipo_material;
    private String cooperativa;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getTipo_material() {
        return tipo_material;
    }

    public void setTipo_material(String tipo_material) {
        this.tipo_material = tipo_material;
    }

    public String getCooperativa() {
        return cooperativa;
    }

    public void setCooperativa(String cooperativa) {
        this.cooperativa = cooperativa;
    }
}
