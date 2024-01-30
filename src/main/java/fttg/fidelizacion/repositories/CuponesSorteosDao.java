/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.CuponesSorteos;
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
public class CuponesSorteosDao extends GenericDaoImpl<CuponesSorteos, Long> {
    
    private final static Logger LOGGER = getLogger(CuponesSorteosDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;

    public CuponesSorteosDao() {
        super(CuponesSorteos.class);
    }
    
    public List<CuponesSorteos> findAllByBeneficio(Long beneficio) {
        LOGGER.info("evento findAllByBeneficio");
        var ret = getCurrentSession().createNamedQuery("CuponesSorteos.findAllByBeneficio")
                .setParameter("beneficio", beneficio)
                .getResultList();
        return ret;
    }

    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
