/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.Ubicaciones;
import fttg.commons.model.entities.fidelizacion.UbicacionesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import fttg.fidelizacion.services.UbicacionesService;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import java.util.List;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class UbicacionesBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(UbicacionesBean.class);

    private List<Ubicaciones> ubicaciones, ubicacionesFiltered;
    private Ubicaciones ubicacion;
    private Boolean editar;
    
    @Autowired
    private UbicacionesService ubicacionesService;
    
    @Override
    public void init() {
        LOGGER.info("evento init");
        this.ubicaciones = this.ubicacionesService.findAll();
        this.ubicacion = new UbicacionesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public List<Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<Ubicaciones> getUbicacionesFiltered() {
        return ubicacionesFiltered;
    }

    public void setUbicacionesFiltered(List<Ubicaciones> ubicacionesFiltered) {
        this.ubicacionesFiltered = ubicacionesFiltered;
    }

    public Ubicaciones getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicaciones ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }
    
    public void save() {
        LOGGER.info("evento save");
        this.ubicacion.setDescripcion(this.ubicacion.getDescripcion().trim().toUpperCase());
        if (this.ubicaciones.contains(this.ubicacion)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La ubicación %s ha sido ingresada previamente", this.ubicacion.getDescripcion()));
        } else {
            if (this.ubicacionesService.save(this.ubicacion)) {
                this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("La ubicación %s ha sido grabada correctamente", this.ubicacion.getDescripcion()));
                this.ubicaciones.add(this.ubicacion);
                LOGGER.info(format("La ubicación %s ha sido grabada correctamente", this.ubicacion));
                this.ubicacion = new UbicacionesBuilder()
                        .status("A")
                        .build();
            } else {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar la ubicación %s", this.ubicacion.getDescripcion()));
            }
        }
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }
    
    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.ubicacion = new UbicacionesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }
    
    public void edit() {
        LOGGER.info("evento edit");
        this.ubicacion.setDescripcion(this.ubicacion.getDescripcion().trim().toUpperCase());
        if (this.ubicacionesService.edit(this.ubicacion)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo de negocio %s ha sido editada correctamente", this.ubicacion.getDescripcion()));
            LOGGER.info(format("La ubicación %s ha sido editada correctamente", this.ubicacion));
            this.ubicacion = null;
            this.ubicacion = new UbicacionesBuilder()
                    .status("A")
                    .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar la ubicación %s", this.ubicacion.getDescripcion()));
        }
    }
    
}
