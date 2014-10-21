/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_salud;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Arbol;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

/**
 *
 * @author Diego
 */
public class pre_grupo_bien extends Pantalla {

    
    private Tabla tab_preubicacion = new Tabla();
    private Arbol arb_arbol = new Arbol();
    public pre_grupo_bien() {        
        tab_preubicacion.setId("tab_preubicacion");
        tab_preubicacion.setTabla("SAO_GRUPO_BIEN", "IDE_SAGRB",1);
        tab_preubicacion.setCampoNombre("DETALLE_SAGRB");
        tab_preubicacion.setCampoPadre("SAO_IDE_SAGRB");
    	tab_preubicacion.getColumna("ACTIVO_SAGRB").setCheck();
    	tab_preubicacion.getColumna("ACTIVO_SAGRB").setValorDefecto("true");
        tab_preubicacion.agregarArbol(arb_arbol);
        tab_preubicacion.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_preubicacion);
        arb_arbol.setId("arb_arbol");
        arb_arbol.dibujar();
        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir2(arb_arbol, pat_panel, "21%", "V");
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_preubicacion.insertar();
    }

    @Override
    public void guardar() {
        tab_preubicacion.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_preubicacion.eliminar();
    }

	public Tabla getTab_preubicacion() {
		return tab_preubicacion;
	}

	public void setTab_preubicacion(Tabla tab_preubicacion) {
		this.tab_preubicacion = tab_preubicacion;
	}

	public Arbol getArb_arbol() {
		return arb_arbol;
	}

	public void setArb_arbol(Arbol arb_arbol) {
		this.arb_arbol = arb_arbol;
	}


    
    
}
