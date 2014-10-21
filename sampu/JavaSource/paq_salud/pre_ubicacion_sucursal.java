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
public class pre_ubicacion_sucursal extends Pantalla {

    
    private Tabla tab_tabla = new Tabla();

    public pre_ubicacion_sucursal() {        
        tab_tabla.setId("tab_tabla");
      tab_tabla.setTabla("SAO_UBICACION_SUCURSAL","IDE_SAUBS", 1);
    // tab_tabla.getColumna("IDE_SUCU").setCombo("SIS_SUCURSAL","IDE_SUCU","NOM_SUCU", "");
        tab_tabla.getColumna("ACTIVO_SAUBS").setCheck();
    	tab_tabla.getColumna("ACTIVO_SAUBS").setValorDefecto("true");
       
        tab_tabla.dibujar();
       
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
        tab_tabla.guardar();
        guardarPantalla();
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
