/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.Ventas;
import fttg.commons.persistence.hibernate.model.dao.generic.impl.GenericDaoImpl;
import java.util.List;
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
public class VentasDao extends GenericDaoImpl<Ventas, Long> {

    private final static Logger LOGGER = getLogger(VentasDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public VentasDao() {
        super(Ventas.class);
    }
    
    public List<Ventas> findAllByPromocionDetalle(Integer promocionDetalle) {
        LOGGER.info("evento findAllByPromocionDetalle");
        var ret = getCurrentSession().createNamedQuery("Ventas.findAllByPromocionDetalle").setParameter("promocionDetalle", promocionDetalle).getResultList();
        return ret;
    }

    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
