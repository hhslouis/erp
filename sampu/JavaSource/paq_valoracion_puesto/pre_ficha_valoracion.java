package paq_valoracion_puesto;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_ficha_valoracion extends Pantalla{
	
	private Tabla tab_ficha=new Tabla();
	private Tabla tab_detalle=new Tabla();
	private Tabla tab_descripcion=new Tabla();
	
	
	public pre_ficha_valoracion (){
		tab_ficha.setId("tab_ficha");
		tab_ficha.setHeader("VALORACIÓN PUESTOS");
		tab_ficha.setTabla("gth_ficha_valoracion", "ide_gtfiv", 1);
		tab_ficha.agregarRelacion(tab_detalle);
		tab_ficha.dibujar();
		PanelTabla pat_fecha=new PanelTabla();
		pat_fecha.setPanelTabla(tab_ficha);
		
		////detalle valoración
		
		tab_detalle.setId("tab_detalle");
		tab_detalle.setHeader("DETALLE VALORACIÓN");
		tab_detalle.setTabla("gth_detalle_valoracion", "ide_gtdev", 2);
		tab_detalle.agregarRelacion(tab_descripcion);
		tab_detalle.dibujar();
		PanelTabla pat_detalle=new PanelTabla();
		pat_detalle.setPanelTabla(tab_detalle);
		
		///DESCRIPCIÓN VALORACION
		

		tab_descripcion.setId("tab_descripcion");
		tab_descripcion.setHeader("DESCRIPCIÓN VALORACIÓN");
		tab_descripcion.setTabla("gth_descripcion_valoracion", "ide_gtdva",3);
		tab_descripcion.dibujar();
		PanelTabla pat_des=new PanelTabla();
		pat_des.setPanelTabla(tab_descripcion);
		

		Division div_divi=new Division();
		div_divi.dividir3(pat_fecha, pat_detalle, pat_des, "40%", "30%", "h");
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
		if(tab_ficha.guardar()){
			tab_ficha.guardar();
			if(tab_detalle.guardar()){
				tab_detalle.guardar();
				if(tab_descripcion.guardar()){
					tab_descripcion.guardar();
					guardarPantalla();
					
				}
			}
		}
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}


	public Tabla getTab_ficha() {
		return tab_ficha;
	}


	public void setTab_ficha(Tabla tab_ficha) {
		this.tab_ficha = tab_ficha;
	}


	public Tabla getTab_detalle() {
		return tab_detalle;
	}


	public void setTab_detalle(Tabla tab_detalle) {
		this.tab_detalle = tab_detalle;
	}


	public Tabla getTab_descripcion() {
		return tab_descripcion;
	}


	public void setTab_descripcion(Tabla tab_descripcion) {
		this.tab_descripcion = tab_descripcion;
	}
	

}
