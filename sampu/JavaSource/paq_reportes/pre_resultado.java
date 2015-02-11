package paq_reportes;

import org.apache.poi.hssf.record.formula.TblPtg;



import paq_sistema.aplicacion.Pantalla;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

public class pre_resultado extends Pantalla {
	private Tabla tab_resultado =new Tabla();
	
	public pre_resultado (){
		tab_resultado.setId("tab_resultado");
		tab_resultado.setTabla("rep_estado_resultado", "ide_rpesr", 1);
		tab_resultado.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_resultado);
		agregarComponente(pat_panel1);
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_resultado.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_resultado.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_resultado.eliminar();
	}

	public Tabla getTab_resultado() {
		return tab_resultado;
	}

	public void setTab_resultado(Tabla tab_resultado) {
		this.tab_resultado = tab_resultado;
	}

}
