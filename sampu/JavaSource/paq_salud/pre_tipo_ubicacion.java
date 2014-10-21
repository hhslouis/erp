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
public class pre_tipo_ubicacion extends Pantalla {

    
    private Tabla tab_tipo_ubicacion = new Tabla();

    public pre_tipo_ubicacion() {        
        tab_tipo_ubicacion.setId("tab_tipo_ubicacion");
        tab_tipo_ubicacion.setTabla("SAO_TIPO_UBICACION","IDE_SATIU",1);
     	tab_tipo_ubicacion.getColumna("ACTIVO_SATIU").setCheck();
    	tab_tipo_ubicacion.getColumna("ACTIVO_SATIU").setValorDefecto("true");
        tab_tipo_ubicacion.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tipo_ubicacion);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_tipo_ubicacion.insertar();
    }

    @Override
    public void guardar() {
        tab_tipo_ubicacion.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_tipo_ubicacion.eliminar();
    }

	public Tabla getTab_tipo_ubicacion() {
		return tab_tipo_ubicacion;
	}

	public void setTab_tipo_ubicacion(Tabla tab_tipo_ubicacion) {
		this.tab_tipo_ubicacion = tab_tipo_ubicacion;
	}
}