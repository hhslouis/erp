package paq_facturacion;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

public class pre_punto_venta extends Pantalla {
	private Tabla tab_punto_venta =new Tabla();
	
public pre_punto_venta (){
	tab_punto_venta.setId("tab_punto_venta");
	tab_punto_venta.setTabla("fac_punto_venta", "ide_fapuv", 1);
	tab_punto_venta.dibujar();
	PanelTabla pat_punto=new PanelTabla();
	pat_punto.setPanelTabla(tab_punto_venta);
	agregarComponente(pat_punto);
	
}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_punto_venta.insertar();
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_punto_venta.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_punto_venta.eliminar();
		
	}

	public Tabla getTab_punto_venta() {
		return tab_punto_venta;
	}

	public void setTab_punto_venta(Tabla tab_punto_venta) {
		this.tab_punto_venta = tab_punto_venta;
	}

}
