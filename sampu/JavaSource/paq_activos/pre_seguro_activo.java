package paq_activos;

import framework.componentes.PanelTabla;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;


public class pre_seguro_activo extends Pantalla{
	private Tabla tab_seguro= new Tabla();
	
	
	public pre_seguro_activo() {
		tab_seguro.setId("tab_seguro");
		tab_seguro.setTabla("afi_seguro", "ide_afseg", 1);
		tab_seguro.dibujar();
		PanelTabla pat_seguro = new PanelTabla();
		pat_seguro.setPanelTabla(tab_seguro);
		agregarComponente(pat_seguro);
			
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
	tab_seguro.insertar();	
	
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_seguro.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_seguro.eliminar();
	
	}

	public Tabla getTab_seguro() {
		return tab_seguro;
	}

	public void setTab_seguro(Tabla tab_seguro) {
		this.tab_seguro = tab_seguro;
	}
	
	
	

}
