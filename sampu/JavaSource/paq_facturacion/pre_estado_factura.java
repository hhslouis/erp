package paq_facturacion;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_estado_factura extends Pantalla{

	private Tabla tab_estado_factura=new Tabla();


	public pre_estado_factura(){
		tab_estado_factura.setId("tab_estado_factura");
		tab_estado_factura.setTabla("fac_estado_factura","ide_faesf", 1);
		tab_estado_factura.dibujar();
		PanelTabla pat_estado_factura=new PanelTabla();
		pat_estado_factura.setPanelTabla(tab_estado_factura);

		agregarComponente(pat_estado_factura);
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_estado_factura.insertar();

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_estado_factura.guardar();
		guardarPantalla();
		

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_estado_factura.eliminar();

	}


	public Tabla getTab_estado_factura() {
		return tab_estado_factura;
	}


	public void setTab_estado_factura(Tabla tab_estado_factura) {
		this.tab_estado_factura = tab_estado_factura;
	}

}
