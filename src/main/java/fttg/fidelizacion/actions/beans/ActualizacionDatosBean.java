/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.apis.smartt.Tokens;
import fttg.commons.model.entities.fidelizacion.Clientes;
import fttg.commons.model.entities.fidelizacion.ClientesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import static fttg.fidelizacion.util.Util.formatCelularPhoneFromSmart;
import static fttg.fidelizacion.util.Util.removeBlankSpacesFromPhone;
import fttg.integraciones.smartt.services.SmarttApi;
import static java.lang.String.format;
import java.util.HashMap;
import java.util.Map;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class ActualizacionDatosBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(ActualizacionDatosBean.class);
    
    @Autowired
    private ConfigurableEnvironment enviroment;
    @Autowired
    private SmarttApi smarttApi;
    
    private String urlApi,methodApi,usernameApi,passwordApi,postSecretApi;
    private Tokens token;
    private Map parameters;
    private Clientes cliente;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.usernameApi = this.enviroment.getProperty("authentication.smartt.usernameApi");
        this.passwordApi = this.enviroment.getProperty("authentication.smartt.passwordApi");
        this.postSecretApi = this.enviroment.getProperty("authentication.smartt.spostSecretApi");
        this.urlApi = this.enviroment.getProperty("integration.smartt.urlApi");
        this.methodApi = this.enviroment.getProperty("integration.smartt.authenticationMethodApi");
        this.parameters = new HashMap<>();
        this.parameters.put("URL", this.urlApi);
        this.parameters.put("method", this.methodApi);
        this.parameters.put("username", this.usernameApi);
        this.parameters.put("password", this.passwordApi);
        this.parameters.put("posSecret", this.postSecretApi);
        this.token = this.smarttApi.findToken(this.parameters);
        this.cliente = new ClientesBuilder().build();
    }
    
    public void searchByCedula() {
        LOGGER.info("evento searchByCedula");
        this.purgeParameters();
        this.methodApi = this.enviroment.getProperty("integration.smartt.personaMethodApi");
        this.parameters.put("method", this.methodApi);
        this.parameters.put("token", this.token.getToken());
        this.parameters.put("identificacion", this.cliente.getIdentificacion());
        var cedula = this.cliente.getIdentificacion();
        this.cliente = this.smarttApi.findClienteByIdentificacion(this.parameters);
        if (isEmpty(this.cliente)) {
            this.cliente = new ClientesBuilder().build();
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La cédula %s no existe...", cedula));
        } else {
            if (isNotBlank(this.cliente.getTelefono())) {
                this.cliente.setTelefono(formatCelularPhoneFromSmart(this.cliente.getTelefono()));
            }
        }
    }
    
    public void updateCliente() {
        LOGGER.info("evento updateCliente");
        this.cliente.setRazonSocial(this.cliente.getRazonSocial().toUpperCase());
        this.cliente.setDireccion(this.cliente.getDireccion().toUpperCase());
        this.cliente.setTelefono(removeBlankSpacesFromPhone(this.cliente.getTelefono()));
        this.purgeParameters();
        this.methodApi = this.enviroment.getProperty("integration.smartt.personaMethodApi");
        this.parameters.put("method", this.methodApi);
        this.parameters.put("token", this.token.getToken());
        this.parameters.put("cliente", this.cliente);
        this.cliente = this.smarttApi.updateCliente(this.parameters);
        if (isEmpty(this.cliente)) {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrió un error al actualizar el cliente %s", this.cliente.getRazonSocial().toUpperCase()));
        } else {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("Cliente %s actualizado correctamente", this.cliente.getRazonSocial()));
            this.cliente = new ClientesBuilder().build();
        }
    }
    
    private void purgeParameters() {
        this.parameters = new HashMap<>();
        this.parameters.put("URL", this.urlApi);
    }

    public Tokens getToken() {
        return token;
    }

    public void setToken(Tokens token) {
        this.token = token;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
    
}
