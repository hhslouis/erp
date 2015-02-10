package paq_reportes;

import org.apache.poi.hssf.record.formula.TblPtg;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_efectivo extends Pantalla{
	private Tabla tab_efectivo=new Tabla();
	
	public pre_efectivo (){
		tab_efectivo.setId("tab_efectivo");
		tab_efectivo.setTabla("rep_estado_flujo_efectivo", "ide_rpefe", 1);
		tab_efectivo.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_efectivo);
		agregarComponente(pat_panel1);
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_efectivo.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_efectivo.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_efectivo.eliminar();
	}

	public Tabla getTab_efectivo() {
		return tab_efectivo;
	}

	public void setTab_efectivo(Tabla tab_efectivo) {
		this.tab_efectivo = tab_efectivo;
	}

}
