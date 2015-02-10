package paq_reportes;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_financiera extends Pantalla {
	
	private Tabla tab_financiera=new Tabla();
	
	public pre_financiera(){
		tab_financiera.setId("tab_financiera");
		tab_financiera.setTabla("rep_estado_situacion_financiera", "ide_rpesf", 1);
		tab_financiera.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_financiera);
		agregarComponente(pat_panel1);
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_financiera.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_financiera.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_financiera.eliminar();
	}

	public Tabla getTab_financiera() {
		return tab_financiera;
	}

	public void setTab_financiera(Tabla tab_financiera) {
		this.tab_financiera = tab_financiera;
	}

}
