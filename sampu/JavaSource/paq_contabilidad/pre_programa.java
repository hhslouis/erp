package paq_contabilidad;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_programa extends Pantalla {
	
	private Tabla tab_programa=new Tabla();
	private Tabla tab_vigente=new Tabla();
	
	public pre_programa(){
		tab_programa.setId("tab_programa");
		tab_programa.setHeader("PROGRAMA");
		tab_programa.setTabla("pre_programa", "ide_prpro", 1);
		tab_programa.getColumna("activo_prpro").setValorDefecto("true");
		tab_programa.agregarRelacion(tab_vigente);
		tab_programa.dibujar();
		PanelTabla pat_progama=new PanelTabla();
		pat_progama.setPanelTabla(tab_programa);
		
		///vigente
		tab_vigente.setId("tab_vigente");
		tab_vigente.setHeader("VIGENTE");
		tab_vigente.setTabla("cont_vigente", "ide_covig", 2);
		tab_vigente.getColumna("activo_covig").setValorDefecto("true");
		tab_vigente.setTipoFormulario(true);
		tab_vigente.getGrid().setColumns(4);
		tab_vigente.dibujar();
		PanelTabla pat_vigente=new PanelTabla();
		pat_vigente.setPanelTabla(tab_vigente);
		
		Division div_division =new Division();
		div_division.dividir2(pat_progama, pat_vigente, "50%", "H");
		agregarComponente(div_division);
		
		
	}
	
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_programa.guardar()){
			if(tab_vigente.guardar()){
				guardarPantalla();
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
	}



	public Tabla getTab_programa() {
		return tab_programa;
	}



	public void setTab_programa(Tabla tab_programa) {
		this.tab_programa = tab_programa;
	}



	public Tabla getTab_vigente() {
		return tab_vigente;
	}



	public void setTab_vigente(Tabla tab_vigente) {
		this.tab_vigente = tab_vigente;
	}
	

}
