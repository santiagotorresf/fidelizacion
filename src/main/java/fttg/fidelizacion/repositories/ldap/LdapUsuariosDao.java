/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories.ldap;

import fttg.commons.model.ldap.entities.LdapUsuarios;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jvillanueva
 */
@Repository
public class LdapUsuariosDao implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(LdapUsuariosDao.class);

    @Autowired
    private LdapTemplate ldapTemplate;
    
    public Boolean login(String username, String password) {
        LOGGER.info("evento login");
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("sAMAccountName", username));
        return ldapTemplate.authenticate("", filter.toString(), password);
    }
    
    public LdapUsuarios findByUsername(String username) {
        LOGGER.info("evento findByUsername");
        try {
            return ldapTemplate.findOne(LdapQueryBuilder.query().where("sAMAccountName").is(username), LdapUsuarios.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    public List<LdapUsuarios> findAll() {
        LOGGER.info("evento findAll");
        return ldapTemplate.findAll(LdapUsuarios.class);
    }

}
