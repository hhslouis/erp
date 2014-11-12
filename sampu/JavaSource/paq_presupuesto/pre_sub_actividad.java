package paq_presupuesto;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_sub_actividad extends Pantalla {
	private Tabla tab_sub_actividad=new Tabla();
	
	public pre_sub_actividad(){
		tab_sub_actividad.setId("tab_sub_actividad");
		tab_sub_actividad.setNumeroTabla(1);
		tab_sub_actividad.setTabla("pre_sub_actividad", "ide_prsua", 1);
		tab_sub_actividad.dibujar();
		PanelTabla pat_sub_actividad=new PanelTabla();
		pat_sub_actividad.setPanelTabla(tab_sub_actividad);
		
		agregarComponente(pat_sub_actividad);
		
	
	}
		
	
	
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_sub_actividad.insertar();
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_sub_actividad.guardar();
		guardarPantalla();
		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_sub_actividad.eliminar();
		
	}




	public Tabla getTab_sub_actividad() {
		return tab_sub_actividad;
	}




	public void setTab_sub_actividad(Tabla tab_sub_actividad) {
		this.tab_sub_actividad = tab_sub_actividad;
	}

}
