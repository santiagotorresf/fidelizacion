/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.Ubicaciones;
import fttg.commons.persistence.hibernate.model.dao.generic.impl.GenericDaoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jvillanueva
 */
@Repository
public class UbicacionesDao extends GenericDaoImpl<Ubicaciones, Integer> {
    
    @Autowired
    private SessionFactory sessionFactory;

    public UbicacionesDao() {
        super(Ubicaciones.class);
    }

    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
