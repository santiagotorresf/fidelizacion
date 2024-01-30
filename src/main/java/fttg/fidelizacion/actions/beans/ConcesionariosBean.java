/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.Concesionarios;
import fttg.commons.model.entities.fidelizacion.ConcesionariosBuilder;
import fttg.commons.model.entities.fidelizacion.TiposConcesionarios;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import static fttg.commons.utils.CommonsUtils.validaIdentificacion;
import fttg.fidelizacion.services.ConcesionariosService;
import fttg.fidelizacion.services.TiposConcesionariosService;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import java.util.List;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
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
public class ConcesionariosBean extends GenericBean {

    private final static Logger LOGGER = getLogger(ConcesionariosBean.class);
    
    private List<TiposConcesionarios> tiposConcesionarios;
    private List<Concesionarios> concesionarios, concesionariosFiltered;
    private Concesionarios concesionario;
    private Boolean editar;
    
    @Autowired
    private TiposConcesionariosService tiposConcesionariosService;
    @Autowired
    private ConcesionariosService concesionariosService;
    
    @Override
    public void init() {
        LOGGER.info("evento init");
        this.tiposConcesionarios = this.tiposConcesionariosService.findAll();
        this.concesionarios = this.concesionariosService.findAll();
        this.concesionario = new ConcesionariosBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public List<TiposConcesionarios> getTiposConcesionarios() {
        return tiposConcesionarios;
    }

    public void setTiposConcesionarios(List<TiposConcesionarios> tiposConcesionarios) {
        this.tiposConcesionarios = tiposConcesionarios;
    }

    public List<Concesionarios> getConcesionarios() {
        return concesionarios;
    }

    public void setConcesionarios(List<Concesionarios> concesionarios) {
        this.concesionarios = concesionarios;
    }

    public List<Concesionarios> getConcesionariosFiltered() {
        return concesionariosFiltered;
    }

    public void setConcesionariosFiltered(List<Concesionarios> concesionariosFiltered) {
        this.concesionariosFiltered = concesionariosFiltered;
    }

    public Concesionarios getConcesionario() {
        return concesionario;
    }

    public void setConcesionario(Concesionarios concesionario) {
        this.concesionario = concesionario;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }
    
    public void validateIdentificacion() {
        LOGGER.info("evento validateIdentificacion");
        if (validaIdentificacion(this.concesionario.getIdentificacion()) == FALSE) {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("La identificación digitada %s es incorrecta", this.concesionario.getIdentificacion()));
            this.concesionario.setIdentificacion(null);
        }
    }
    
    private void validateConcesionario(Concesionarios concesionario) {
        LOGGER.info("evento validateConcesionario");
        concesionario.setRazonSocial(concesionario.getRazonSocial().trim().toUpperCase());
        if (isNotBlank(concesionario.getRepresentanteLegal()))
            concesionario.setRepresentanteLegal(concesionario.getRepresentanteLegal().trim().toUpperCase());
        if (isNotBlank(concesionario.getEmail()))
            concesionario.setEmail(concesionario.getEmail().trim().toLowerCase());
        if (isNotBlank(this.concesionario.getTelefono()))
            concesionario.setTelefono(concesionario.getTelefono());
    }
    
    public void save() {
        LOGGER.info("evento save");
        if (this.concesionarios.contains(this.concesionario)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El concesionario %s ya fue ingresado previamente", this.concesionario.getRazonSocial().toUpperCase()));
        } else {
            if (validaIdentificacion(this.concesionario.getIdentificacion()) == FALSE) {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("La identificación digitada %s es incorrecta", this.concesionario.getIdentificacion()));
                this.concesionario.setIdentificacion(null);
            } else {
                this.validateConcesionario(this.concesionario);
                if (this.concesionariosService.save(this.concesionario)) {
                    this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El concesionario %s ha sido grabado correctamente", this.concesionario.getRazonSocial()));
                    this.concesionarios.add(this.concesionario);
                    LOGGER.info(format("El concesionario %s ha sido grabado correctamente", this.concesionario));
                    this.concesionario = new ConcesionariosBuilder()
                            .status("A")
                            .build();
                } else {
                    this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar el concesionario %s", this.concesionario.getRazonSocial()));
                }
            }
        }
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }

    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.concesionario = new ConcesionariosBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }
    
    public void edit() {
        LOGGER.info("evento edit");
        this.validateConcesionario(this.concesionario);
        if (this.concesionariosService.edit(this.concesionario)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El concesionario %s ha sido editado correctamente", this.concesionario.getRazonSocial()));
            LOGGER.info(format("El concesionario %s ha sido editado correctamente", this.concesionario));
            this.concesionario = null;
            this.concesionario = new ConcesionariosBuilder()
                .status("A")
                .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar el concesionario %s", this.concesionario.getRazonSocial()));
        }
    }
    
}
