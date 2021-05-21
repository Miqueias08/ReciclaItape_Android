package br.com.reciclaitape.classes;

public class Ranking {
    private int posicao;
    private String usuario;
    private Double quantidade_entregue;

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Double getQuantidade_entregue() {
        return quantidade_entregue;
    }

    public void setQuantidade_entregue(Double quantidade_entregue) {
        this.quantidade_entregue = quantidade_entregue;
    }
}
