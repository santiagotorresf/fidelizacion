/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.TiposClientes;
import fttg.commons.model.entities.fidelizacion.TiposClientesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.stereotype.Service;
import fttg.fidelizacion.services.TiposClientesService;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import java.util.List;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author storres
 */
@Service
@SpringViewScoped
public class TiposClientesBean extends GenericBean {

    private final static Logger LOGGER = getLogger(TiposClientesBean.class);

    private List<TiposClientes> tiposClientes, tiposClientesFiltered;
    private TiposClientes tipoCliente;
    private Boolean editar;
    
    @Autowired
    private TiposClientesService tiposClientesService;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.tiposClientes = this.tiposClientesService.findAll();
        this.tipoCliente = new TiposClientesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public List<TiposClientes> getTiposClientes() {
        return tiposClientes;
    }

    public void setTiposClientes(List<TiposClientes> tiposClientes) {
        this.tiposClientes = tiposClientes;
    }

    public List<TiposClientes> getTiposClientesFiltered() {
        return tiposClientesFiltered;
    }

    public void setTiposClientesFiltered(List<TiposClientes> tiposClientesFiltered) {
        this.tiposClientesFiltered = tiposClientesFiltered;
    }

    public TiposClientes getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TiposClientes tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

    public void save() {
        LOGGER.info("evento save");
        this.tipoCliente.setDescripcion(this.tipoCliente.getDescripcion().trim().toUpperCase());
        if (this.tiposClientes.contains(this.tipoCliente)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El tipo de cliente %s ya fue ingresado previamente", this.tipoCliente.getDescripcion()));
        } else {
            if (this.tiposClientesService.save(this.tipoCliente)) {
                this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo de cliente %s ha sido grabado correctamente", this.tipoCliente.getDescripcion()));
                this.tiposClientes.add(this.tipoCliente);
                LOGGER.info(format("El tipo de cliente %s ha sido grabado correctamente", this.tipoCliente));
                this.tipoCliente = new TiposClientesBuilder()
                        .status("A")
                        .build();
            } else {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar el local %s", this.tipoCliente.getDescripcion()));
            }
        }
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }

    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.tipoCliente = new TiposClientesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public void edit() {
        LOGGER.info("evento edit");
        this.tipoCliente.setDescripcion(this.tipoCliente.getDescripcion().trim().toUpperCase());
        if (this.tiposClientesService.edit(this.tipoCliente)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El tipo cliente %s ha sido editado correctamente", this.tipoCliente.getDescripcion()));
            LOGGER.info(format("El tipo cliente %s ha sido editado correctamente", this.tipoCliente));
            this.tipoCliente = null;
            this.tipoCliente = new TiposClientesBuilder()
                    .status("A")
                    .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar el local %s", this.tipoCliente.getDescripcion()));
        }
    }

}