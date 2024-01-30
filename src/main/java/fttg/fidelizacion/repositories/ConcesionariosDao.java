/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.Concesionarios;
import fttg.commons.persistence.hibernate.model.dao.generic.impl.GenericDaoImpl;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jvillanueva
 */
@Repository
public class ConcesionariosDao extends GenericDaoImpl<Concesionarios, Integer> {
    
    private final static Logger LOGGER = getLogger(ConcesionariosDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;

    public ConcesionariosDao() {
        super(Concesionarios.class);
    }
    
    public Concesionarios findByIdentificacion(String identificacion) {
        LOGGER.info("evento findByIdentificacion");
        try {
            var ret = (Concesionarios) getCurrentSession().createNamedQuery("Concesionarios.findByIdentificacion")
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();
            return ret;
        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
