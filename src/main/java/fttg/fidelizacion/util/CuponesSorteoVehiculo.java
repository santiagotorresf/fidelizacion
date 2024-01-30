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
public class CuponesSorteoVehiculo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String fechaHoraCanje;
    private String numeroCupon;
    private String identificacion;
    private String razonSocial;
    private String fechaValidaSorteo;

    public CuponesSorteoVehiculo() {
    }

    public CuponesSorteoVehiculo(String fechaHoraCanje, String numeroCupon, String identificacion, String razonSocial, String fechaValidaSorteo) {
        this.fechaHoraCanje = fechaHoraCanje;
        this.numeroCupon = numeroCupon;
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
        this.fechaValidaSorteo = fechaValidaSorteo;
    }

    public String getFechaHoraCanje() {
        return fechaHoraCanje;
    }

    public void setFechaHoraCanje(String fechaHoraCanje) {
        this.fechaHoraCanje = fechaHoraCanje;
    }

    public String getNumeroCupon() {
        return numeroCupon;
    }

    public void setNumeroCupon(String numeroCupon) {
        this.numeroCupon = numeroCupon;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getFechaValidaSorteo() {
        return fechaValidaSorteo;
    }

    public void setFechaValidaSorteo(String fechaValidaSorteo) {
        this.fechaValidaSorteo = fechaValidaSorteo;
    }

    @Override
    public int hashCode() {
        var hash = 3;
        hash = 41 * hash + Objects.hashCode(this.numeroCupon);
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
        final var other = (CuponesSorteoVehiculo) obj;
        return Objects.equals(this.numeroCupon, other.numeroCupon);
    }

    @Override
    public String toString() {
        return "CuponesSorteosVehiculo{" + "fechaHoraCanje=" + fechaHoraCanje + ", numeroCupon=" + numeroCupon + ", identificacion=" + identificacion + ", razonSocial=" + razonSocial + ", fechaValidaSorteo=" + fechaValidaSorteo + '}';
    }
    
}
