package br.com.reciclaitape.classes;

public class markers{
    private int id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;

    public int getPapel() {
        return papel;
    }

    public void setPapel(int papel) {
        this.papel = papel;
    }

    public int getPlastico() {
        return plastico;
    }

    public void setPlastico(int plastico) {
        this.plastico = plastico;
    }

    public int getVidro() {
        return vidro;
    }

    public void setVidro(int vidro) {
        this.vidro = vidro;
    }

    private String type;
    private int papel;
    private int plastico;
    private int vidro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
