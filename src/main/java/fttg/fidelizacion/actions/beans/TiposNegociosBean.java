/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.TiposNegocios;
import fttg.commons.model.entities.fidelizacion.TiposNegociosBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import fttg.fidelizacion.services.TiposNegociosService;
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
public class TiposNegociosBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(TiposNegociosBean.class);
    
    private List<TiposNegocios> tiposNegocios, tiposNegociosFiltered;
    private TiposNegocios tipoNegocio;
    private Boolean editar;
    
    @Autowired
    private TiposNegociosService tiposNegociosService;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.tiposNegocios = this.tiposNegociosService.findAll();
        this.tipoNegocio = new TiposNegociosBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public List<TiposNegocios> getTiposNegocios() {
        return tiposNegocios;
    }

    public void setTiposNegocios(List<TiposNegocios> tiposNegocios) {
        this.tiposNegocios = tiposNegocios;
    }

    public List<TiposNegocios> getTiposNegociosFiltered() {
        return tiposNegociosFiltered;
    }

    public void setTiposNegociosFiltered(List<TiposNegocios> tiposNegociosFiltered) {
        this.tiposNegociosFiltered = tiposNegociosFiltered;
    }

    public TiposNegocios getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(TiposNegocios tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }
    
    public void save() {
        LOGGER.info("evento save");
        this.tipoNegocio.setDescripcion(this.tipoNegocio.getDescripcion().trim().toUpperCase());
        if (this.tiposNegocios.contains(this.tipoNegocio)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El tipo de negocio %s ha sido ingresado previamente",  this.tipoNegocio.getDescripcion()));
        } else {
            
            if (this.tiposNegociosService.save(this.tipoNegocio)) {
                this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo de negocio %s ha sido grabado correctamente",  this.tipoNegocio.getDescripcion()));
                this.tiposNegocios.add(this.tipoNegocio);
                LOGGER.info(format("El tipo de negocio %s ha sido grabado correctamente", this.tipoNegocio));
                this.tipoNegocio = new TiposNegociosBuilder()
                        .status("A")
                        .build();
            } else {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar el tipo de negocio %s",  this.tipoNegocio.getDescripcion()));
            }
        }
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }
    
    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.tipoNegocio = new TiposNegociosBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }
    
    public void edit() {
        LOGGER.info("evento edit");
        this.tipoNegocio.setDescripcion(this.tipoNegocio.getDescripcion().trim().toUpperCase());
        if (this.tiposNegociosService.edit(this.tipoNegocio)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo de negocio %s ha sido editado correctamente", this.tipoNegocio.getDescripcion()));
            LOGGER.info(format("El tipo de negocio %s ha sido editado correctamente", this.tipoNegocio));
            this.tipoNegocio = null;
            this.tipoNegocio = new TiposNegociosBuilder()
                    .status("A")
                    .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar el negocio de local %s",  this.tipoNegocio.getDescripcion()));
        }
    }
    
}
