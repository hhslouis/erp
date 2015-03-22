package paq_presupuesto;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;
public class pre_liquida_certificacion extends Pantalla {
	
	private Tabla tab_liquida_certificacion=new Tabla();
	private  Tabla tab_detalle=new Tabla();
	
	public pre_liquida_certificacion (){
		tab_liquida_certificacion.setId("tab_liquida_certificacion");
		tab_liquida_certificacion.setHeader("LIQUIDACION CERTIFICACION");
		tab_liquida_certificacion.setTabla("pre_liquida_certificacion", "ide_prlce", 1);
		tab_liquida_certificacion.getColumna("activo_prlce").setValorDefecto("true");
		tab_liquida_certificacion.agregarRelacion(tab_detalle);
		tab_liquida_certificacion.dibujar();
		PanelTabla pat_liquida =new PanelTabla();
		pat_liquida.setPanelTabla(tab_liquida_certificacion);
		
		///// detalle liquida certificacion
		
		tab_detalle.setId("tab_detalle");
		tab_detalle.setHeader("DETALLE LIQUIDACION CERTIFICACION");
		tab_detalle.setTabla("pre_detalle_liquida_certif", "ide_prdcl", 2);
		tab_detalle.getColumna("activo_prdcl").setValorDefecto("true");
		tab_detalle.dibujar();
		PanelTabla pat_detalle=new PanelTabla();
		pat_detalle.setPanelTabla(tab_detalle);
		
		
		Division div_divi=new Division();
		div_divi.dividir2(pat_liquida, pat_detalle, "50%", "H");
		
		agregarComponente(div_divi);

	
		
		
		
	}
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_liquida_certificacion.guardar()){
			if(tab_detalle.guardar()){
				guardarPantalla();
				
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}


	public Tabla getTab_liquida_certificacion() {
		return tab_liquida_certificacion;
	}


	public void setTab_liquida_certificacion(Tabla tab_liquida_certificacion) {
		this.tab_liquida_certificacion = tab_liquida_certificacion;
	}


	public Tabla getTab_detalle() {
		return tab_detalle;
	}


	public void setTab_detalle(Tabla tab_detalle) {
		this.tab_detalle = tab_detalle;
	}

}
