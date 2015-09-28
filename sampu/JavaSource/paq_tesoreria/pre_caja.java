package paq_tesoreria;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_caja extends Pantalla{
	private Tabla tab_caja = new Tabla();

	public pre_caja(){
		
		tab_caja.setId("tab_caja");
		tab_caja.setTabla("tes_caja", "ide_tecaj", 1);
		tab_caja.dibujar();
		PanelTabla pat_panel = new PanelTabla();
		pat_panel.setPanelTabla(tab_caja);
		//Division div_tabla = new Division();
		//div_tabla.dividir1(pat_panel);
		agregarComponente(pat_panel);
		
	}

	public Tabla getTab_caja() {
		return tab_caja;
	}

	public void setTab_caja(Tabla tab_caja) {
		this.tab_caja = tab_caja;
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_caja.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_caja.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_caja.eliminar();

	}

	public Tabla getTab_tab_caja() {
		return tab_caja;
	}

	public void setTab_tab_caja(Tabla tab_caja) {
		this.tab_caja = tab_caja;
	}
	
}