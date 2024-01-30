/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.Clientes;
import fttg.commons.model.entities.fidelizacion.TiposClientes;
import fttg.commons.model.entities.fidelizacion.ClientesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import static fttg.commons.utils.CommonsUtils.validaIdentificacion;
import fttg.fidelizacion.services.ClientesService;
import fttg.fidelizacion.services.TiposClientesService;
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
 * @author storres
 */
@Service
@SpringViewScoped
public class ClientesBean extends GenericBean {

    private final static Logger LOGGER = getLogger(ClientesBean.class);

    @Autowired
    private ClientesService clientesService;

    @Autowired
    private TiposClientesService tiposClientesService;

    private Clientes cliente;
    private List<Clientes> clientes, clientesFiltered;
    private List<TiposClientes> tiposClientes;
    private Boolean editar;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.clientes = this.clientesService.findAll();
        this.tiposClientes = this.tiposClientesService.findAll();
        this.editar = FALSE;
        this.cliente = new ClientesBuilder()
                .status("A")
                .build();
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public List<Clientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<Clientes> clientes) {
        this.clientes = clientes;
    }

    public List<Clientes> getClientesFiltered() {
        return clientesFiltered;
    }

    public void setClientesFiltered(List<Clientes> clientesFiltered) {
        this.clientesFiltered = clientesFiltered;
    }

    public List<TiposClientes> getTiposClientes() {
        return tiposClientes;
    }

    public void setTiposClientes(List<TiposClientes> tiposClientes) {
        this.tiposClientes = tiposClientes;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

    public void save() {
        LOGGER.info("evento save");
        if (this.clientes.contains(this.cliente)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El cliente %s ya fue ingresado previamente", this.cliente.getRazonSocial().toUpperCase()));
        } else {
            this.cliente.setDireccion(this.cliente.getDireccion().toUpperCase().trim());
            this.cliente.setEmail(this.cliente.getEmail().toLowerCase().trim());
            this.cliente.setRazonSocial(this.cliente.getRazonSocial().toUpperCase().trim());
            this.cliente.setStatus("A");
            if (this.clientesService.save(this.cliente)) {
                this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El cliente %s ha sido grabado correctamente", this.cliente.getRazonSocial().toUpperCase()));
                this.clientes.add(this.cliente);
                LOGGER.info(format("El cliente %s ha sido grabado correctamente", this.cliente));
                this.cliente = new ClientesBuilder()
                        .status("A")
                        .build();
            } else {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar el cliente %s", this.cliente.getRazonSocial().toUpperCase()));
            }
        }
    }

    public void edit() {
        LOGGER.info("evento edit");
        this.cliente.setDireccion(this.cliente.getDireccion().toUpperCase().trim());
        this.cliente.setRazonSocial(this.cliente.getRazonSocial().toUpperCase().trim());
        this.cliente.setEmail(this.cliente.getEmail().toLowerCase().trim());
        if (this.clientesService.edit(this.cliente)) {
            LOGGER.info(format("El cliente %s ha sido editado correctamente", this.cliente));
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El cliente %s ha sido editado correctamente", this.cliente.getRazonSocial().toUpperCase()));
            this.cliente = null;
            this.cliente = new ClientesBuilder()
                    .status("A")
                    .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar el cliente %s", this.cliente.getRazonSocial().toUpperCase()));
        }
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }

    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.editar = FALSE;
        this.cliente = new ClientesBuilder()
                .status("A")
                .build();
    }

}
