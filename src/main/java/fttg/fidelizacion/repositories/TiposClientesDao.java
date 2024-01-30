/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.TiposClientes;
import fttg.commons.persistence.hibernate.model.dao.generic.impl.GenericDaoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author storres
 */
@Repository
public class TiposClientesDao extends GenericDaoImpl<TiposClientes, Integer> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public TiposClientesDao() {
        super(TiposClientes.class);
    }
    
    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
