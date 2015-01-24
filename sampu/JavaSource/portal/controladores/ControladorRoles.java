/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.controladores;

import framework.aplicacion.TablaGenerica;
import framework.reportes.GenerarReporte;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import paq_gestion.ejb.ServicioEmpleado;
import paq_sistema.aplicacion.Utilitario;

/**
 *
 * @author Diego
 */
@ManagedBean
@ViewScoped
public class ControladorRoles {

    private String strOpcion = "1";
    private List lisRolesPago;
    private Object rolSeleccionado;
    private Utilitario utilitario = new Utilitario();
    @EJB
    private ServicioEmpleado ser_empleado;
    private String strPathReporte;

    @PostConstruct
    public void cargarDatos() {
        TablaGenerica tab_partida = ser_empleado.getPartida(utilitario.getVariable("IDE_GTEMP"));
        if (tab_partida != null) {
            lisRolesPago = ser_empleado.getRolesEmpleadoLista(tab_partida.getValor("IDE_GEEDP"));
        }
        strPathReporte = utilitario.getURL()+ "/reportes/reporte" + utilitario.getVariable("IDE_USUA") + ".pdf";
    }

    public void visualizarRol() {
        if (rolSeleccionado != null) {
            GenerarReporte ger = new GenerarReporte();
            Map parametros = new HashMap();
            try {
                parametros.put("IDE_GEPRO", Long.parseLong(((Object[]) rolSeleccionado)[4] + ""));
            } catch (Exception e) {
            }
            parametros.put("IDE_NRDTN", Long.parseLong(((Object[]) rolSeleccionado)[3] + ""));
            TablaGenerica tab_partida = ser_empleado.getPartida(utilitario.getVariable("IDE_GTEMP"));
            parametros.put("IDE_GEEDP", tab_partida.getValor("IDE_GEEDP"));
            parametros.put("titulo", " BOLETA DE PAGO");
            parametros.put("IDE_NRTIR", utilitario.getVariable("p_nrh_trubro_egreso") + "," + utilitario.getVariable("p_nrh_trubro_ingreso"));
        	parametros.put("par_total_recibir",Integer.parseInt(utilitario.getVariable("p_nrh_rubro_valor_recibir")));
			parametros.put("par_total_ingresos",Integer.parseInt(utilitario.getVariable("p_nrh_rubro_total_ingresos")));
			parametros.put("par_total_egresos",Integer.parseInt(utilitario.getVariable("p_nrh_rubro_total_egresos")));
            ger.generar(parametros, "/reportes/rep_rol_de_pagos/rep_n_rol_pagos.jasper");
        }        
    }

    public List getLisRolesPago() {
        return lisRolesPago;
    }

    public void setLisRolesPago(List lisRolesPago) {
        this.lisRolesPago = lisRolesPago;
    }

    public String getStrOpcion() {
        return strOpcion;
    }

    public void setStrOpcion(String strOpcion) {
        this.strOpcion = strOpcion;
    }

    public Object getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Object rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public String getStrPathReporte() {
        return strPathReporte;
    }

    public void setStrPathReporte(String strPathReporte) {
        this.strPathReporte = strPathReporte;
    }
}
