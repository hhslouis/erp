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
public class pre_estado extends Pantalla {

    
    private Tabla tab_estado = new Tabla();

    public pre_estado() {        
        tab_estado.setId("tab_estado");
        tab_estado.setTabla("SAO_ESTADO","IDE_SAEST",1);
     	tab_estado.getColumna("ACTIVO_SAEST").setCheck();
    	tab_estado.getColumna("ACTIVO_SAEST").setValorDefecto("true");
        tab_estado.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_estado);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_estado.insertar();
    }

    @Override
    public void guardar() {
        tab_estado.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_estado.eliminar();
    }

	public Tabla getTab_estado() {
		return tab_estado;
	}

	public void setTab_estado(Tabla tab_estado) {
		this.tab_estado = tab_estado;
	}


}
