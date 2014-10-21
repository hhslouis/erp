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
public class pre_propiedad extends Pantalla {

    
    private Tabla tab_propiedad = new Tabla();

    public pre_propiedad() {        
        tab_propiedad.setId("tab_propiedad");
        tab_propiedad.setTabla("SAO_PROPIEDAD","IDE_SAPRO",1);
     	tab_propiedad.getColumna("ACTIVO_SAPRO").setCheck();
    	tab_propiedad.getColumna("ACTIVO_SAPRO").setValorDefecto("true");
        tab_propiedad.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_propiedad);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_propiedad.insertar();
    }

    @Override
    public void guardar() {
        tab_propiedad.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_propiedad.eliminar();
    }

	public Tabla getTab_propiedad() {
		return tab_propiedad;
	}

	public void setTab_propiedad(Tabla tab_propiedad) {
		this.tab_propiedad = tab_propiedad;
	}

    
}
