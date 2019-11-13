package com.example.bcutzzbarbeshop;

public class Cita {

    String horaEntrada, horaSalida, orden, idDia, idHoras;
    boolean disponible;

    public Cita() {
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.orden = orden;
        this.disponible = disponible;
        this.idDia = idDia;
        this.idHoras = idHoras;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getIdDia() {
        return idDia;
    }

    public void setIdDia(String idDia) {
        this.idDia = idDia;
    }

    public String getIdHoras() {
        return idHoras;
    }

    public void setIdHoras(String idHoras) {
        this.idHoras = idHoras;
    }
}
