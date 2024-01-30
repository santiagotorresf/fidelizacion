/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.Beneficios;
import fttg.commons.model.entities.fidelizacion.Clientes;
import fttg.commons.model.entities.fidelizacion.ClientesBuilder;
import fttg.commons.model.entities.fidelizacion.TiposClientesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import static fttg.commons.utils.CommonsUtils.validaIdentificacion;
import fttg.fidelizacion.services.ClientesService;
import static fttg.fidelizacion.util.Util.removeBlankSpacesFromPhone;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import static org.primefaces.PrimeFaces.current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class TestBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(TestBean.class);
    
    private Beneficios beneficio;
    private Clientes cliente, clienteEditar;
    
    @Autowired
    private ClientesService clientesService;

    public Beneficios getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(Beneficios beneficio) {
        this.beneficio = beneficio;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Clientes getClienteEditar() {
        return clienteEditar;
    }

    public void setClienteEditar(Clientes clienteEditar) {
        this.clienteEditar = clienteEditar;
    }


    @Override
    public void init() {
        LOGGER.info("evento init");
        this.cliente = new ClientesBuilder()
                .tipoCliente(new TiposClientesBuilder()
                        .id(1)
                        .descripcion("PASAJEROS")
                        .status("A")
                        .build())
                .status("A")
                .build();
    }
    
    public void searchClienteByCedula() {
        LOGGER.info("evento searchClienteByCedula");
        var identificacion = this.cliente.getIdentificacion();
        this.cliente = this.clientesService.findByIdentificacion(identificacion);
        if (isEmpty(this.cliente)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La identificación %s no corresponde a ningún cliente", identificacion));
            this.cliente = new ClientesBuilder()
                    .tipoCliente(new TiposClientesBuilder()
                            .id(1)
                            .descripcion("PASAJEROS")
                            .status("A")
                            .build())
                    .status("A")
                    .build();
        } else {
            if (this.cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("LOCALES") || this.cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("BOLETERIAS") || this.cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("FUNCIONARIOS")) {
                this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El cliente no puede ser de tipo %s", this.cliente.getTipoCliente().getDescripcion()));
                this.cliente = new ClientesBuilder()
                        .tipoCliente(new TiposClientesBuilder()
                                .id(1)
                                .descripcion("PASAJEROS")
                                .status("A")
                                .build())
                        .status("A")
                        .build();
            } else {
                
            }
        }
    }
    
    public void validateIdentificacion() {
        LOGGER.info("evento validateIdentificacion");
        if (validaIdentificacion(this.cliente.getIdentificacion()) == FALSE) {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("La identificación digitada %s es incorrecta", this.cliente.getIdentificacion()));
        }
    }
    
    private void validateCliente(Clientes cliente) {
        LOGGER.info("evento validateCliente");
        cliente.setRazonSocial(cliente.getRazonSocial().trim().toUpperCase());
        if (isNotBlank(cliente.getEmail())) {
            cliente.setEmail(cliente.getEmail().trim().toLowerCase());
            cliente.setTelefono(removeBlankSpacesFromPhone(cliente.getTelefono()));
        }
        if (isNotBlank(cliente.getTelefono()))
            cliente.setTelefono(cliente.getTelefono().trim().toLowerCase());
    }
    
    public void agregarNuevoCliente() {
        LOGGER.info("evento agregarNuevoCliente");
        this.validateCliente(this.cliente);
        if (this.clientesService.save(this.cliente)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El cliente %s ha sido creado correctamente", this.cliente.getRazonSocial()));
            LOGGER.info(format("El cliente %s ha sido grabado correctamente", this.cliente));
            current().executeScript("PF('creacionClienteDialog').hide();");
        } else {
           this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al crear el cliente %s", this.cliente.getRazonSocial())); 
        }
    }
    public void buscarClienteExistente() {
        LOGGER.info("evento buscarClienteExistente");
        var identificacion = this.cliente.getIdentificacion();
        this.cliente = this.clientesService.findByIdentificacion(identificacion);
        if (isEmpty(this.cliente)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La identificación %s no corresponde a ningún cliente", identificacion));
            this.cliente = new ClientesBuilder()
                    .tipoCliente(new TiposClientesBuilder()
                            .id(1)
                            .descripcion("PASAJEROS")
                            .status("A")
                            .build())
                    .status("A")
                    .build();
        } else {
            if (this.cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("LOCALES") || this.cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("BOLETERIAS") || this.cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("FUNCIONARIOS")) {
                this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El cliente no puede ser de tipo %s", this.cliente.getTipoCliente().getDescripcion()));
                this.cliente = new ClientesBuilder()
                    .tipoCliente(new TiposClientesBuilder()
                            .id(1)
                            .descripcion("PASAJEROS")
                            .status("A")
                            .build())
                    .status("A")
                    .build();
            } else {
                
            }
        }
    }
    
    public void editarClienteExistente() {
        LOGGER.info("evento editarClienteExistente");
        this.cliente.setRazonSocial(this.cliente.getRazonSocial().toUpperCase());
        this.cliente.setTelefono(removeBlankSpacesFromPhone(this.cliente.getTelefono()));
        if (this.clientesService.edit(this.cliente)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El cliente %s ha sido editado correctamente, favor realizar la consulta para continuar con el proceso", this.cliente.getRazonSocial()));
            current().executeScript("PF('edicionClienteDialog').hide();");
        }
    }
    
}
