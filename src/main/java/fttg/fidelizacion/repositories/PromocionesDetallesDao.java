/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.PromocionesDetalles;
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
public class PromocionesDetallesDao extends GenericDaoImpl<PromocionesDetalles, Integer> {

    private final static Logger LOGGER = getLogger(PromocionesDetallesDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public PromocionesDetallesDao() {
        super(PromocionesDetalles.class);
    }
    
    public PromocionesDetalles findByCodigo(String codigo) {
        LOGGER.info("evento findByCodigo");
        try {
            var ret = (PromocionesDetalles) getCurrentSession().createNamedQuery("PromocionesDetalles.findByCodigo").setParameter("codigo", codigo).getSingleResult();
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
