package br.com.reciclaitape.classes;

public class Cooperativas {
    private int id_cooperativa;
    private String razao_social;
    private String email;
    private String telefone;
    private String tipo_documento;
    private String cnpj;
    private String cpf;
    private String endereco;
    private Double lat;
    private Double lng;
    private String descricao;
    private Integer status;
    private String material_aceito;


    public int getId_cooperativa() {
        return id_cooperativa;
    }

    public void setId_cooperativa(int id_cooperativa) {
        this.id_cooperativa = id_cooperativa;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMaterial_aceito() { return material_aceito; }

    public void setMaterial_aceito(String material_aceito) { this.material_aceito = material_aceito; }
}
