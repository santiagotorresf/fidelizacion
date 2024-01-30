/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.primefaces.event.CaptureEvent;
import org.springframework.stereotype.Service;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class CaptureTestBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(CaptureTestBean.class);

    @Override
    public void init() {}
    
    public void oncapture(CaptureEvent captureEvent) {
        var data = captureEvent.getData();
        
    }
    
}
