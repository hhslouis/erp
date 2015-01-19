package paq_adquisicion;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_solicitud_compra extends Pantalla {

	private Tabla tab_solicitud_compra= new Tabla();
	
	public pre_solicitud_compra() {
		// TODO Auto-generated constructor stub
		tab_solicitud_compra.setId("tab_solicitud_compra");
		tab_solicitud_compra.setTabla("adq_solicitud_compra", "ide_adsoc", 1);
		tab_solicitud_compra.setTipoFormulario(true);
		tab_solicitud_compra.getGrid().setColumns(4);
		tab_solicitud_compra.dibujar();
		
		PanelTabla pat_compra= new PanelTabla();
		pat_compra.setPanelTabla(tab_solicitud_compra);
		agregarComponente(tab_solicitud_compra);
	}
	
	
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
	}

	@Override
	public void guardar() {
		if(tab_solicitud_compra.guardar()){
			guardarPantalla();
		}
		
	}

	@Override
	public void eliminar() {
	utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_solicitud_compra() {
		return tab_solicitud_compra;
	}

	public void setTab_solicitud_compra(Tabla tab_solicitud_compra) {
		this.tab_solicitud_compra = tab_solicitud_compra;
	}

}
