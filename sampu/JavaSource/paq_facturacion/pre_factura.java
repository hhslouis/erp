package paq_facturacion;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_factura extends 	Pantalla{
	
	private Tabla tab_factura = new Tabla();
	private Tabla tab_detalle_factura=new Tabla();

	public pre_factura() {
		// TODO Auto-generated constructor stub
		tab_factura.setId("tab_factura");
		tab_factura.setTabla("fac_factura", "ide_fafac", 1);
		tab_factura.dibujar();
		tab_factura.agregarRelacion(tab_detalle_factura);
		
		PanelTabla pat_factura=new PanelTabla();
		pat_factura.setPanelTabla(tab_factura);
		
		tab_detalle_factura.setId("tab_detalle_factura");
		tab_detalle_factura.setTabla("fac_detalle_factura", "ide_fadef", 2);
		tab_detalle_factura.setCampoForanea("ide_fafac");
		tab_detalle_factura.dibujar();
			
		PanelTabla pat_detalle_factura= new PanelTabla();
		pat_detalle_factura.setMensajeWarn("DETALLE DE FACTURA");
		pat_detalle_factura.setPanelTabla(tab_detalle_factura);
		
		
		Division div_division=new Division();
		div_division.dividir2(pat_factura, pat_detalle_factura, "50%", "h");
		agregarComponente(div_division);
		
	}

	public Tabla gettab_factura() {
		return tab_factura;
	}

	public void settab_factura(Tabla tab_factura) {
		this.tab_factura = tab_factura;
	}

	public Tabla gettab_detalle_factura() {
		return tab_detalle_factura;
	}

	public void settab_detalle_factura(Tabla tab_detalle_factura) {
		this.tab_detalle_factura = tab_detalle_factura;
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(tab_factura.isFocus()){
			tab_factura.insertar();
			}
		else if(tab_detalle_factura.isFocus()){
			tab_detalle_factura.insertar();
			
		}
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_factura.guardar()){
			tab_detalle_factura.guardar();
		
		}
		
		guardarPantalla();
	
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_factura.isFocus()){
			tab_factura.eliminar();
			
			
	}
		else if(tab_factura.isFocus()){
			tab_factura.eliminar();

			
		}
			
		}

	
	

}
