/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.util;


public class CuponesSorteoVehiculoBuilder {

    private String fechaHoraCanje;
    private String numeroCupon;
    private String identificacion;
    private String razonSocial;
    private String fechaValidaSorteo;

    public CuponesSorteoVehiculoBuilder() {
    }

    public CuponesSorteoVehiculoBuilder fechaHoraCanje(String fechaHoraCanje) {
        this.fechaHoraCanje = fechaHoraCanje;
        return this;
    }

    public CuponesSorteoVehiculoBuilder numeroCupon(String numeroCupon) {
        this.numeroCupon = numeroCupon;
        return this;
    }

    public CuponesSorteoVehiculoBuilder identificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public CuponesSorteoVehiculoBuilder razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public CuponesSorteoVehiculoBuilder fechaValidaSorteo(String fechaValidaSorteo) {
        this.fechaValidaSorteo = fechaValidaSorteo;
        return this;
    }
    
    public CuponesSorteoVehiculo build() {
        return new CuponesSorteoVehiculo(fechaHoraCanje, numeroCupon, identificacion, razonSocial, fechaValidaSorteo);
    }
    
}
