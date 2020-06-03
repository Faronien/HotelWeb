package com.ipn.mx.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "Cuarto", schema = "public")
public class Cuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCuarto;
    private double precioDiario;
    private boolean esReservable;
    private boolean estaEnUso;

    public Cuarto() {
    }

    public int getIdCuarto() {
        return idCuarto;
    }

    public void setIdCuarto(int idCuarto) {
        this.idCuarto = idCuarto;
    }

    public double getPrecioDiario() {
        return precioDiario;
    }

    public void setPrecioDiario(double precioDiario) {
        this.precioDiario = precioDiario;
    }

    public boolean getEsReservable() {
        return esReservable;
    }

    public void setEsReservable(boolean esReservable) {
        this.esReservable = esReservable;
    }

    public boolean getEstaEnUso() {
        return estaEnUso;
    }

    public void setEstaEnUso(boolean estaEnUso) {
        this.estaEnUso = estaEnUso;
    }
    
}
