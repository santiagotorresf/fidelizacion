
package fttg.fidelizacion.repositories;

import fttg.commons.model.entities.fidelizacion.LocalesComerciales;
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
public class LocalesComercialesDao extends GenericDaoImpl<LocalesComerciales, Integer> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public LocalesComercialesDao() {
        super(LocalesComerciales.class);
    }
    
    @Override
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
