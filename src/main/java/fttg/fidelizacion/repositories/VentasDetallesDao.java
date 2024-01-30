/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.VentasDetalles;
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
public class VentasDetallesDao extends GenericDaoImpl<VentasDetalles, Long> {

    private final static Logger LOGGER = getLogger(VentasDetallesDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;

    public VentasDetallesDao() {
        super(VentasDetalles.class);
    }
    
    public List<VentasDetalles> findAllByVenta(Long venta) {
        LOGGER.info("evento findAllByVenta");
        var ret = getCurrentSession().createNamedQuery("VentasDetalles.findAllByVenta").setParameter("venta", venta).getResultList();
        return ret;
    }

    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
