package paq_tesoreria;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;



public class pre_proveedor extends Pantalla {
	private Tabla tab_proveedor= new Tabla();

	public pre_proveedor() {
		// TODO Auto-generated constructor stub
		tab_proveedor.setId("tab_proveedor");
		tab_proveedor.setTabla("tes_proveedor", "ide_tepro", 1);
		tab_proveedor.setTipoFormulario(true);
		tab_proveedor.getGrid().setColumns(4);
		tab_proveedor.dibujar();
		
		PanelTabla pat_proveedor= new PanelTabla();
		pat_proveedor.setPanelTabla(tab_proveedor);
		agregarComponente(tab_proveedor);
	}

	
	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_proveedor.guardar()){
			guardarPantalla();
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_proveedor.eliminar();
	}



	public Tabla getTab_proveedor() {
		return tab_proveedor;
	}



	public void setTab_proveedor(Tabla tab_proveedor) {
		this.tab_proveedor = tab_proveedor;
	}
	

}
