/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.util;


public class CuponesBuilder {

    private String codigo;
    private String fechaHoraCanje;
    private String promocion;
    private String campania;
    private String validez;
    private String sponsor;
    private String cliente;
    private String identificacion;

    public CuponesBuilder() {
    }

    public CuponesBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public CuponesBuilder fechaHoraCanje(String fechaHoraCanje) {
        this.fechaHoraCanje = fechaHoraCanje;
        return this;
    }

    public CuponesBuilder promocion(String promocion) {
        this.promocion = promocion;
        return this;
    }

    public CuponesBuilder campania(String campania) {
        this.campania = campania;
        return this;
    }

    public CuponesBuilder validez(String validez) {
        this.validez = validez;
        return this;
    }

    public CuponesBuilder sponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public CuponesBuilder cliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public CuponesBuilder identificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public Cupones build() {
        return new Cupones(codigo, fechaHoraCanje, promocion, campania, validez, sponsor, cliente, identificacion);
    }
    
}
