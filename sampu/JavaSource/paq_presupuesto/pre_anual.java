package paq_presupuesto;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_anual extends Pantalla{
	
	private Tabla tab_anual= new Tabla();
	private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto) utilitario.instanciarEJB(ServicioPresupuesto.class);
	
	public pre_anual(){
		tab_anual.setId("tab_anual");
		tab_anual.setHeader("ANUAL");
		tab_anual.setTabla("pre_anual", "ide_pranu", 1);
		tab_anual.getColumna("ide_prcla").setCombo("select ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla from pre_clasificador order by codigo_clasificador_prcla");
		tab_anual.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
		tab_anual.getColumna("ide_geani").setCombo("gen_anio", "ide_geani", "detalle_geani", "");
		//tab_anual.getColumna("ide_prfup").setCombo("pre_funcion_programa", "ide_prfup", "detalle_prfup,", "");
		tab_anual.setTipoFormulario(true);
		tab_anual.getGrid().setColumns(4);
		tab_anual.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_anual);
		agregarComponente(pat_panel1);
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_anual.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_anual.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_anual.eliminar();
	}

	public Tabla getTab_anual() {
		return tab_anual;
	}

	public void setTab_anual(Tabla tab_anual) {
		this.tab_anual = tab_anual;
	}

}
