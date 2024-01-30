/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import static fttg.commons.jsf.utils.JsfUtil.getRemoteAddress;
import static fttg.commons.jsf.utils.JsfUtil.getRemoteHost;
import static fttg.commons.jsf.utils.JsfUtil.redirect;
import fttg.commons.model.entities.fidelizacion.Usuarios;
import fttg.commons.spring.web.util.scope.annotation.SpringSessionScoped;
import fttg.fidelizacion.services.LdapUsuariosService;
import fttg.fidelizacion.services.UsuariosService;
import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringSessionScoped
public class LoginBean extends GenericBean {

    private final static Logger LOGGER = getLogger(LoginBean.class);
    
    private String nickname;
    private String contrasenia;
    private boolean login;
    private Usuarios usuario;
    private String ipOrigen;
    private String hostOrigen;
    
    @Autowired
    private LdapUsuariosService ldapUsuariosService;
    @Autowired
    private UsuariosService usuariosService;

    @Override
    public void init() {}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }

    public String getHostOrigen() {
        return hostOrigen;
    }

    public void setHostOrigen(String hostOrigen) {
        this.hostOrigen = hostOrigen;
    }
    
    public void login() {
        LOGGER.info("evento login");
        if (this.ldapUsuariosService.login(this.nickname, this.contrasenia)) {
            this.usuario = this.usuariosService.findByNickname(this.nickname);
            if (this.usuario != null) {
                if (this.usuario.getStatus().equalsIgnoreCase("D")) {
                    this.addMessage(SEVERITY_ERROR,"Aviso del Sistema", format("El usuario %s se encuentra inactivo en el sistema", this.nickname));
                } else {
                    this.login = true;
                    this.ipOrigen = getRemoteAddress();
                    this.hostOrigen = getRemoteHost();
                    redirect("/dashboard");
                }
            } else {
                this.addMessage(SEVERITY_ERROR,"Aviso del Sistema", format("El usuario %s no se encuentra registrado en el sistema", this.nickname));
            }
        } else {
            this.addMessage(SEVERITY_ERROR,"Aviso del Sistema", format("El usuario %s no pudo logarse en el Directorio Activo de FTTG", this.nickname));
        }
    }
    
    public void logout() {
        LOGGER.info("evento logout");
        this.login = false;
        this.usuario = null;
        this.nickname = null;
        this.contrasenia = null;
        redirect("/static/login");
    }
    
    public void verifyLogin() {
        LOGGER.info("evento verifyLogin");
        if (!this.login) {
            redirect("/static/login");
        }
    }
    
}
