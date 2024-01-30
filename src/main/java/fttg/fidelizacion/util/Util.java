/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.util;

import fttg.commons.model.entities.fidelizacion.CuponesSorteos;
import fttg.commons.model.entities.fidelizacion.PromocionesDetalles;
import java.io.ByteArrayInputStream;
import static java.io.File.separator;
import java.io.InputStream;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.lang.System.out;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofPattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import static net.sf.jasperreports.engine.JasperCompileManager.compileReport;
import static net.sf.jasperreports.engine.JasperRunManager.runReportToPdf;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.time.DateFormatUtils.format;

/**
 *
 * @author jvillanueva
 */
public class Util {

    public static String formatCelularPhoneFromSmart(String phone) {
        String ret, codigoPais;
        codigoPais = "593";
        switch (phone.length()) {
            case 10:
                ret = codigoPais.concat(" ").concat(substring(phone, 1, 3)).concat(" ").concat(substring(phone, 3, 6)).concat(" ").concat(substring(phone, 6, 10));
                return ret;
            case 13:
                ret = substring(phone, 0, 4).concat(" ").concat(substring(phone, 4, 6)).concat(" ").concat(substring(phone, 6, 9)).concat(" ").concat(substring(phone, 9, 13));
                return ret;
            default:
                return null;
        }
    }

    public static String removeBlankSpacesFromPhone(String phone) {
        String ret;
        ret = phone.replaceAll("\\s", "");
        return ret;
    }

    public static Boolean validateFechaBetweenRanges(LocalDate fechaInicial, LocalDate fechaFinal, LocalDate fecha) {
        Boolean ret;
        if (fecha.isAfter(fechaInicial) && fecha.isBefore(fechaFinal)) {
            ret = TRUE;
        } else if (fecha.isEqual(fechaInicial) || fecha.isEqual(fechaFinal)) {
            ret = TRUE;
        } else {
            ret = FALSE;
        }
        return ret;
    }

    public static InputStream obtainCuponStream(ServletContext servletContext, String report, List data) {
        InputStream ret;
        try {
            var fileReportPath = getFullReportsPath(servletContext) + separator + report;
            var jasperReport = compileReport(fileReportPath);
            byte[] bs;
            bs = runReportToPdf(jasperReport, null, new JRBeanCollectionDataSource(data));
            ret = new ByteArrayInputStream(bs);
            return ret;
        } catch (JRException ex) {
            out.println(ex);
            ret = null;
        }
        return ret;
    }

    public static InputStream obtainCuponesSorteoVehiculoStream(ServletContext servletContext, String report, List data) {
        InputStream ret;
        var rutaLogotipoCentroComercial = servletContext.getRealPath("/resources/commons/images/logo-cc-bn.png");
        var parameters = new HashMap();
        parameters.put("logo", rutaLogotipoCentroComercial);
        try {
            var fileReportPath = getFullReportsPath(servletContext) + separator + report;
            var jasperReport = compileReport(fileReportPath);
            byte[] bs;
            bs = runReportToPdf(jasperReport, parameters, new JRBeanCollectionDataSource(data));
            ret = new ByteArrayInputStream(bs);
            return ret;
        } catch (JRException ex) {
            out.println(ex);
            ret = null;
        }
        return ret;
    }

    private static String getFullReportsPath(ServletContext servletContext) {
        String ret;
        ret = servletContext.getRealPath("/resources/commons/reports/");
        return ret;
    }

    private static Date convertToDateViaInstant(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Cupones preProcessDetallePromocion(PromocionesDetalles promocionDetalle) {
        var ret = new CuponesBuilder()
                .codigo(promocionDetalle.getCodigo())
                .fechaHoraCanje(promocionDetalle.getFechaHoraCanje().format(ofPattern("dd/MM/yyyy HH:mm:ss")))
                .promocion(promocionDetalle.getPromocion().getDescripcion())
                .campania(promocionDetalle.getPromocion().getCampania().getDescripcion())
                .validez(format(convertToDateViaInstant(promocionDetalle.getPromocion().getCampania().getFechaFin()), "EEEE d 'de' MMMM 'del' YYYY"))
                .sponsor(promocionDetalle.getPromocion().getLocal().getNombreComercial())
                .cliente(promocionDetalle.getCliente().getRazonSocial())
                .identificacion(promocionDetalle.getCliente().getIdentificacion())
                .build();
        return ret;
    }

    public static List<CuponesSorteoVehiculo> preProcessCuponesSorteoVehiculo(List<CuponesSorteos> cupones) {
        List<CuponesSorteoVehiculo> ret = new ArrayList<>();
        cupones.forEach(cupon -> {
            CuponesSorteoVehiculo row = new CuponesSorteoVehiculoBuilder()
                    .fechaHoraCanje(cupon.getFechaHoraRegistro().format(ofPattern("dd/MM/yyyy")))
                    .numeroCupon(leftPad(valueOf(cupon.getId().intValue()), 6, "0"))
                    .identificacion(replace(cupon.getBeneficio().getCliente().getIdentificacion(), substring(cupon.getBeneficio().getCliente().getIdentificacion(), 2, 8), "******"))
                    .razonSocial(cupon.getBeneficio().getCliente().getRazonSocial())
                    .fechaValidaSorteo(LocalDate.of(2024, 01, 05).format(ofPattern("dd/MM/yyyy")))
                    .build();
            ret.add(row);
        });
        return ret;
    }

}
