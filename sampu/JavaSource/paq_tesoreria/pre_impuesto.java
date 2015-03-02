package paq_tesoreria;

import javax.sound.midi.Patch;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_impuesto extends Pantalla {
	
	private Tabla tab_impuesto =new Tabla();
	
	
	public pre_impuesto(){
		tab_impuesto.setId("tab_impuesto");
		tab_impuesto.setHeader("IMPUESTO");
		tab_impuesto.setTabla("tes_impuesto", "ide_teimp", 1);
		tab_impuesto.getColumna("ide_tetii").setCombo("tes_tipo_impuesto", "ide_tetii", "detalle_tetii", "");
		tab_impuesto.dibujar();
		PanelTabla pat_panel =new PanelTabla();
		pat_panel.setPanelTabla(tab_impuesto);
		
		agregarComponente(pat_panel);
	}

	@Override
	public void insertar() {
		tab_impuesto.insertar();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_impuesto.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_impuesto.eliminar();
	}

	public Tabla getTab_impuesto() {
		return tab_impuesto;
	}

	public void setTab_impuesto(Tabla tab_impuesto) {
		this.tab_impuesto = tab_impuesto;
	}

}
