/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.BeneficiosDetalles;
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
public class BeneficiosDetallesDao extends GenericDaoImpl<BeneficiosDetalles, Long> {
    
    private final static Logger LOGGER = getLogger(ClientesDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public BeneficiosDetallesDao() {
        super(BeneficiosDetalles.class);
    }
    
    public List<BeneficiosDetalles> findAllByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial(Integer local, String establecimiento, String puntoEmision, String secuencial) {
        LOGGER.info("evento findAllByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial");
        List<BeneficiosDetalles> ret = getCurrentSession().createNamedQuery("BeneficiosDetalles.findAllByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial")
                .setParameter("local", local)
                .setParameter("establecimiento", establecimiento)
                .setParameter("puntoEmision", puntoEmision)
                .setParameter("secuencial", secuencial)
                .getResultList();
        return ret;
    }

    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
