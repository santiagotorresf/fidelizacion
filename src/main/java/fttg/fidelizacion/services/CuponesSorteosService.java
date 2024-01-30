/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.CuponesSorteos;
import fttg.fidelizacion.repositories.CuponesSorteosDao;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jvillanueva
 */
@Service
@Transactional(readOnly = false)
public class CuponesSorteosService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(CuponesSorteosService.class);
    
    @Autowired
    private CuponesSorteosDao cuponesSorteosDao;
    
    @Transactional(readOnly = true)
    public CuponesSorteos get(Long id) {
        LOGGER.info("evento get");
        return cuponesSorteosDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<CuponesSorteos> findAll() {
        LOGGER.info("evento findAll");
        return cuponesSorteosDao.findAll();
    }
    
    public List<CuponesSorteos> findAllByBeneficio(Long beneficio) {
        LOGGER.info("evento findAllByBeneficio");
        return cuponesSorteosDao.findAllByBeneficio(beneficio);
    }
    
    public Boolean save(CuponesSorteos cupon) {
        LOGGER.info("evento save");
        return cuponesSorteosDao.save(cupon);
    }
    
    public Boolean edit(CuponesSorteos cupon) {
        LOGGER.info("evento edit");
        return cuponesSorteosDao.edit(cupon);
    }
    
}
