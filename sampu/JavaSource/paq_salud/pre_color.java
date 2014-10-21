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
public class pre_color extends Pantalla {

    
    private Tabla tab_color = new Tabla();

    public pre_color() {        
        tab_color.setId("tab_color");
        tab_color.setTabla("SAO_COLOR","IDE_SACOL",1);
     	tab_color.getColumna("ACTIVO_SACOL").setCheck();
    	tab_color.getColumna("ACTIVO_SACOL").setValorDefecto("true");
        tab_color.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_color);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_color.insertar();
    }

    @Override
    public void guardar() {
        tab_color.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_color.eliminar();
    }

	public Tabla getTab_color() {
		return tab_color;
	}

	public void setTab_color(Tabla tab_color) {
		this.tab_color = tab_color;
	}

}
