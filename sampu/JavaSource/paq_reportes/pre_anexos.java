package paq_reportes;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_anexos extends Pantalla {
	private Tabla tab_anexo=new Tabla();
	
	public pre_anexos (){
		tab_anexo.setId("tab_anexo");
		tab_anexo.setTabla("rep_anexos", "ide_rpane", 1);
		tab_anexo.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_anexo);
		agregarComponente(pat_panel1);
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_anexo.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_anexo.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_anexo.eliminar();
	}

	public Tabla getTab_anexo() {
		return tab_anexo;
	}

	public void setTab_anexo(Tabla tab_anexo) {
		this.tab_anexo = tab_anexo;
	}

}
