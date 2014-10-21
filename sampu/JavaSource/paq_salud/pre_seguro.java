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
public class pre_seguro extends Pantalla {

    
    private Tabla tab_seguro = new Tabla();

    public pre_seguro() {        
        tab_seguro.setId("tab_seguro");
        tab_seguro.setTabla("SAO_SEGURO","IDE_SASEG",1);
     	tab_seguro.getColumna("ACTIVO_SASEG").setCheck();
    	tab_seguro.getColumna("ACTIVO_SASEG").setValorDefecto("true");
        tab_seguro.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_seguro);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_seguro.insertar();
    }

    @Override
    public void guardar() {
        tab_seguro.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_seguro.eliminar();
    }

	public Tabla getTab_seguro() {
		return tab_seguro;
	}

	public void setTab_seguro(Tabla tab_seguro) {
		this.tab_seguro = tab_seguro;
	}

    
}
