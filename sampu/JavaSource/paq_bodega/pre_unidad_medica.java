package paq_bodega;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_unidad_medica extends Pantalla{
	
private Tabla tab_unidad_medica=new Tabla();
	
	
	public pre_unidad_medica(){
		
		tab_unidad_medica.setId("tab_unidad_medica");
		tab_unidad_medica.setTabla("bodt_unidad_medica","ide_bounm", 1);
		tab_unidad_medica.dibujar();
		PanelTabla pat_unidad_medica=new PanelTabla();
		pat_unidad_medica.setPanelTabla(tab_unidad_medica);
		
		agregarComponente(tab_unidad_medica);
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_unidad_medica.inicializar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_unidad_medica.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_unidad_medica.eliminar();
		
	}


	public Tabla getTab_unidad_medica() {
		return tab_unidad_medica;
	}


	public void setTab_unidad_medica(Tabla tab_unidad_medica) {
		this.tab_unidad_medica = tab_unidad_medica;
	}

}
