package br.com.reciclaitape.classes;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Tutoriais {
    private int id_tutorial;
    private String autor;
    private String titulo;
    private String subtitulo;
    private String imagem;
    private String texto;
    private String video;
    private String dataHora;


    public int getId_tutorial() {
        return id_tutorial;
    }

    public void setId_tutorial(int id_tutorial) {
        this.id_tutorial = id_tutorial;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return String.format("{'id_tutorial':'%s','autor':'%s','titulo':'%s','subtitulo':'%s','imagem':'%s','texto':'%s','video':'%s','dataHora':'%s'}",
                id_tutorial,autor,titulo,subtitulo,imagem,texto,video,dataHora);
        //return super.toString();
    }
}
