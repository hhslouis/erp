
package paq_activos;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_activo extends Pantalla{

	private Tabla tab_activo=new Tabla();
	
	
	
	public pre_activo() {
		tab_activo.setId("tab_activo");  
		tab_activo.setTabla("afi_activo", "ide_afaco", 1);
		tab_activo.setTipoFormulario(true);
		tab_activo.getColumna("ide_crf").setCombo("crt_funcionario", "ide_crf", "nombre_crf", "");
		tab_activo.getColumna("ide_crf").setLongitud(150);
		tab_activo.getColumna("ide_crf").setAutoCompletar();
		tab_activo.getColumna("ide_opci").setCombo("sis_opcion", "ide_opci", "nom_opci", "");
		tab_activo.getColumna("ide_opci").setLongitud(150);
		tab_activo.getColumna("ide_opci").setAutoCompletar();
		tab_activo.getColumna("ide_repo").setCombo("sis_reporte", "ide_repo", "nom_repo", "");
		tab_activo.getColumna("ide_repo").setLongitud(150);
		tab_activo.getColumna("ide_repo").setAutoCompletar();

		tab_activo.dibujar();
		PanelTabla pat_activo=new PanelTabla();
		pat_activo.setPanelTabla(tab_activo);
		
		agregarComponente(pat_activo);
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_activo.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_activo.guardar();
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_activo.eliminar();
	}

	public Tabla gettab_activo() {
		return tab_activo;
	}

	public void settab_activo(Tabla tab_activo) {
		this.tab_activo = tab_activo;
	}


}
