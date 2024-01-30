
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.LocalesComerciales;
import fttg.fidelizacion.repositories.LocalesComercialesDao;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author storres
 */
@Service
@Transactional(readOnly = false)
public class LocalesComercialesService implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(LocalesComercialesService.class);
    
    @Autowired
    private LocalesComercialesDao localesComercialesDao;
    
    @Transactional(readOnly = true)
    public LocalesComerciales get(Integer id) {
        LOGGER.info("evento get");
        return localesComercialesDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<LocalesComerciales> findAll() {
        LOGGER.info("evento findAll");
        return localesComercialesDao.findAll();
    }
    
    public Boolean save(LocalesComerciales localComercial) {
        LOGGER.info("evento save");
        return localesComercialesDao.save(localComercial);
    }
    
    public Boolean edit(LocalesComerciales localComercial) {
        LOGGER.info("evento edit");
        return localesComercialesDao.edit(localComercial);
    }
    
}
