/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.ldap.entities.LdapUsuarios;
import fttg.fidelizacion.repositories.ldap.LdapUsuariosDao;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jvillanueva
 */
@Service
public class LdapUsuariosService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(LdapUsuariosService.class);
    
    @Autowired
    private LdapUsuariosDao ldapUsuariosDao;

    public Boolean login(String username, String password) {
        LOGGER.info("evento login");
        return ldapUsuariosDao.login(username, password);
    }

    public LdapUsuarios findByUsername(String username) {
        LOGGER.info("evento findByUsername");
        return ldapUsuariosDao.findByUsername(username);
    }

    public List<LdapUsuarios> findAll() {
        LOGGER.info("evento findAll");
        return ldapUsuariosDao.findAll();
    }
    
}
