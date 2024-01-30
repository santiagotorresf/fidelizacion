/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.util;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author jvillanueva
 */
public class Cupones implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String codigo;
    private String fechaHoraCanje;
    private String promocion;
    private String campania;
    private String validez;
    private String sponsor;
    private String cliente;
    private String identificacion;

    public Cupones() {
    }

    public Cupones(String codigo, String fechaHoraCanje, String promocion, String campania, String validez, String sponsor, String cliente, String identificacion) {
        this.codigo = codigo;
        this.fechaHoraCanje = fechaHoraCanje;
        this.promocion = promocion;
        this.campania = campania;
        this.validez = validez;
        this.sponsor = sponsor;
        this.cliente = cliente;
        this.identificacion = identificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechaHoraCanje() {
        return fechaHoraCanje;
    }

    public void setFechaHoraCanje(String fechaHoraCanje) {
        this.fechaHoraCanje = fechaHoraCanje;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public String getCampania() {
        return campania;
    }

    public void setCampania(String campania) {
        this.campania = campania;
    }

    public String getValidez() {
        return validez;
    }

    public void setValidez(String validez) {
        this.validez = validez;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public int hashCode() {
        var hash = 3;
        hash = 79 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cupones other = (Cupones) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Cupones{" + "codigo=" + codigo + ", fechaHoraCanje=" + fechaHoraCanje + ", promocion=" + promocion + ", campania=" + campania + ", validez=" + validez + ", sponsor=" + sponsor + ", cliente=" + cliente + ", identificacion=" + identificacion + '}';
    }
    
}
