package paq_contabilidad;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_clientes extends Pantalla {
	private Tabla tab_clientes=new Tabla();
	
	public pre_clientes (){
		tab_clientes.setId("tab_clientes");
		tab_clientes.setNumeroTabla(1);
		tab_clientes.setTabla("rec_clientes", "ide_recli", 1);
		tab_clientes.dibujar();
		PanelTabla pat_clientes=new PanelTabla ();
		pat_clientes.setPanelTabla(tab_clientes);
		
		agregarComponente(tab_clientes);
		
		
		
	}
	
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_clientes.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_clientes.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_clientes.eliminar();
	}



	public Tabla getTab_clientes() {
		return tab_clientes;
	}



	public void setTab_clientes(Tabla tab_clientes) {
		this.tab_clientes = tab_clientes;
	}

}
