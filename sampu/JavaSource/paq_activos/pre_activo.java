
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
		tab_activo.getColumna("ide_afest").setCombo("afi_estado", "ide_afest", "detalle_afest", "");
		//tab_activo.getColumna("ide_crf").setLongitud(150);
		//tab_activo.getColumna("ide_crf").setAutoCompletar();
		tab_activo.getColumna("ide_aftip").setCombo("afi_tipo_propiedad", "ide_aftip", "detalle_aftip", "");
		tab_activo.getColumna("ide_afacd").setCombo("afi_actividad", "ide_afacd", "detalle_afacd", "");
		tab_activo.getColumna("ide_afubi").setCombo("afi_ubicacion", "ide_afubi", "detalle_afubi", "");
		tab_activo.getColumna("ide_afnoa").setCombo("afi_nombre_activo", "ide_afnoa", "detalle_afnoa", "");
		tab_activo.getColumna("ide_aftia").setCombo("afi_tipo_activo", "ide_aftia", "detalle_aftia", "");
		tab_activo.getColumna("ide_afseg").setCombo("afi_seguro", "ide_afseg", "detalle_afseg", "");
		
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
