package paq_presupuesto;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_funcion_programa extends Pantalla {
	private Tabla tab_funcion_programa=new Tabla();
	public pre_funcion_programa (){
		tab_funcion_programa.setId("tab_funcion_programa");
		tab_funcion_programa.setNumeroTabla(1);
		tab_funcion_programa.setTabla("pre_funcion_programa", "ide_prfup ", 1);
		tab_funcion_programa.getColumna("ide_prnfp").setCombo("pre_nivel_funcion_programa", "ide_prnfp", "detalle_prnfp", "");
		
		//tab_funcion_programa.getColumna("pre_ide_prfup").setCombo("select ide_prfup, detalle_prfup from pre_funcion_programa order by detalle_prfup");
		//tab_funcion_programa.setTipoFormulario(true);
		//tab_funcion_programa.getGrid().setColumns(4);
		
		tab_funcion_programa.dibujar();
		PanelTabla pat_funcion_programa=new PanelTabla();
		pat_funcion_programa.setPanelTabla(tab_funcion_programa);
		agregarComponente(pat_funcion_programa);
		
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_funcion_programa.insertar();
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_funcion_programa.guardar();
		guardarPantalla();
		
	}


	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_funcion_programa.eliminar();
		
	}
	
	public Tabla getTab_funcion_programa() {
		return tab_funcion_programa;
	}


	public void setTab_funcion_programa(Tabla tab_funcion_programa) {
		this.tab_funcion_programa = tab_funcion_programa;
	}



}
