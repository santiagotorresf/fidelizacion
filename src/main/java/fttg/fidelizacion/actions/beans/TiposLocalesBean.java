/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.TiposLocales;
import fttg.commons.model.entities.fidelizacion.TiposLocalesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import fttg.fidelizacion.services.TiposLocalesService;
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
public class TiposLocalesBean extends GenericBean {

    private final static Logger LOGGER = getLogger(TiposLocalesBean.class);

    private List<TiposLocales> tiposLocales, tiposLocalesFiltered;
    private TiposLocales tipoLocal;
    private Boolean editar;
    
    @Autowired
    private TiposLocalesService tiposLocalesService;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.tiposLocales = this.tiposLocalesService.findAll();
        this.tipoLocal = new TiposLocalesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public List<TiposLocales> getTiposLocales() {
        return tiposLocales;
    }

    public void setTiposLocales(List<TiposLocales> tiposLocales) {
        this.tiposLocales = tiposLocales;
    }

    public List<TiposLocales> getTiposLocalesFiltered() {
        return tiposLocalesFiltered;
    }

    public void setTiposLocalesFiltered(List<TiposLocales> tiposLocalesFiltered) {
        this.tiposLocalesFiltered = tiposLocalesFiltered;
    }

    public TiposLocales getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(TiposLocales tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

    public void save() {
        LOGGER.info("evento save");
        this.tipoLocal.setDescripcion(this.tipoLocal.getDescripcion().trim().toUpperCase());
        if (this.tiposLocales.contains(this.tipoLocal)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El tipo de local %s ha sido ingresado previamente", this.tipoLocal.getDescripcion()));
        } else {
            if (this.tiposLocalesService.save(this.tipoLocal)) {
                this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo de local %s ha sido grabado correctamente", this.tipoLocal.getDescripcion()));
                this.tiposLocales.add(this.tipoLocal);
                LOGGER.info(format("El tipo de local %s ha sido grabado correctamente", this.tipoLocal));
                this.tipoLocal = new TiposLocalesBuilder()
                        .status("A")
                        .build();
            } else {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar el tipo de local %s", this.tipoLocal.getDescripcion()));
            }
        }
    }

    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }

    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.tipoLocal = new TiposLocalesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public void edit() {
        LOGGER.info("evento edit");
        this.tipoLocal.setDescripcion(this.tipoLocal.getDescripcion().trim().toUpperCase());
        if (this.tiposLocalesService.edit(this.tipoLocal)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo de local %s ha sido editado correctamente", this.tipoLocal.getDescripcion()));
            LOGGER.info(format("El tipo de local %s ha sido editado correctamente", this.tipoLocal));
            this.tipoLocal = null;
            this.tipoLocal = new TiposLocalesBuilder()
                    .status("A")
                    .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar el tipo de local %s", this.tipoLocal.getDescripcion()));
        }
    }

}
