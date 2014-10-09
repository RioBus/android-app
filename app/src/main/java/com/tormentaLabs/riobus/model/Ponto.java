package com.tormentaLabs.riobus.model;

import java.util.Date;

/**
 * Created by pedro on 06/07/2014.
 */
public class Ponto {

    public Ponto() {
    }

    public Ponto(Date dataHora, String ordem, String linha, double latitude, double longitude, double velocidade) {
        this.dataHora = dataHora;
        this.ordem = ordem;
        this.linha = linha;
        this.latitude = latitude;
        this.longitude = longitude;
        this.velocidade = velocidade;
    }

    private Date dataHora;
    private String ordem;
    private String linha;
    private double latitude;
    private double longitude;
    private double velocidade;

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    @Override
    public String toString() {
        return "Ponto{" +
                "dataHora=" + dataHora +
                ", ordem='" + ordem + "'" +
                ", linha=" + linha +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", velocidade=" + velocidade +
                "}";
    }
}
