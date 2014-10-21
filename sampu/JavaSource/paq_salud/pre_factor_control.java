/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_salud;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

/**
 *
 * @author Diego
 */
public class pre_factor_control extends Pantalla {

    private Tabla tab_tabla = new Tabla();

    public pre_factor_control() {
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("SAO_FACTOR_CONTROL","IDE_SAFAC",1);
        tab_tabla.setNumeroTabla(1);
        tab_tabla.getColumna("ACTIVO_SAFAC").setCheck();
        tab_tabla.getColumna("ACTIVO_SAFAC").setValorDefecto("true");
        tab_tabla.getColumna("IDE_SACLR").setCombo("select ide_saclr,detalle_saclr as detalle from sao_clasificacion_riesgos where not sao_ide_saclr is null order by sao_ide_saclr");
        tab_tabla.getColumna("IDE_SACLR").setAutoCompletar();
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_tabla.insertar();
    }

    @Override
    public void guardar() {
        if (tab_tabla.guardar()) {
            guardarPantalla();
        }
    }

    @Override
    public void eliminar() {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }
}
