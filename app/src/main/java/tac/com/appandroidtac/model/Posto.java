package tac.com.appandroidtac.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by diego on 13/03/2017.
 */

public class Posto {

    private int _id;
    private String nome;
    private double latitude;
    private double longitude;
    private double precoGasolina;
    private double precoGasolinaAditivada;
    private double precoEtanol;
    private double precoDiesel;
    private String dtAtualizacao;

    public Posto() {

    }

    public int getID() {
        return _id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public double getPrecoGasolina() {
        return precoGasolina;
    }

    public void setPrecoGasolina(double precoGasolina) {
        this.precoGasolina = precoGasolina;
    }

    public double getPrecoGasolinaAditivada() {
        return precoGasolinaAditivada;
    }

    public void setPrecoGasolinaAditivada(double precoGasolinaAditivada) {
        this.precoGasolinaAditivada = precoGasolinaAditivada;
    }

    public double getPrecoEtanol() {
        return precoEtanol;
    }

    public void setPrecoEtanol(double precoEtanol) {
        this.precoEtanol = precoEtanol;
    }

    public double getPrecoDiesel() {
        return precoDiesel;
    }

    public void setPrecoDiesel(double precoDiesel) {
        this.precoDiesel = precoDiesel;
    }

    public String getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(String dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }
}
