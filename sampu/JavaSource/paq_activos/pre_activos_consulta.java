package paq_activos;

import javax.ejb.EJB;

import org.apache.poi.hssf.record.formula.functions.T;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;


public class pre_activos_consulta extends Pantalla{
	private Tabla tab_consulta_activo = new Tabla();
	@EJB
	private ServicioContabilidad ser_Contabilidad= (ServicioContabilidad) utilitario.instanciarEJB(ServicioContabilidad.class); 
	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega ) utilitario.instanciarEJB(ServicioBodega.class);
	public pre_activos_consulta(){
		tab_consulta_activo.setId("tab_consulta_activo");
		tab_consulta_activo.setTabla("afi_activo", "ide_afact", 1);
		tab_consulta_activo.getColumna("ide_afubi").setCombo("afi_ubicacion","ide_afubi","detalle_afubi","");
		tab_consulta_activo.getColumna("ide_aftia").setCombo("afi_tipo_activo","ide_aftia","detalle_aftia","");
		tab_consulta_activo.getColumna("ide_aftip").setCombo("afi_tipo_propiedad","ide_aftip","detalle_aftip","");
		tab_consulta_activo.getColumna("ide_afseg").setCombo("afi_seguro","ide_afseg","detalle_afseg","");
		tab_consulta_activo.getColumna("ide_afnoa").setCombo("afi_nombre_activo", "ide_afnoa", "detalle_afnoa", "");
		tab_consulta_activo.getColumna("ide_geare").setCombo("gen_area", "ide_geare", "detalle_geare", "");
		tab_consulta_activo.getColumna("ide_afacd").setCombo("afi_actividad", "ide_afacd", "detalle_afacd", "");
		tab_consulta_activo.getColumna("ide_cocac").setCombo(ser_Contabilidad.getCuentaContable("true,false"));
		tab_consulta_activo.getColumna("ide_afest").setCombo("afi_estado", "ide_afest", "detalle_afest,porcentaje_afest", "");
		tab_consulta_activo.getColumna("ide_tepro").setCombo(ser_bodega.getProveedor("true,false"));
		tab_consulta_activo.getColumna("ide_afubi").setFiltro(true);
		tab_consulta_activo.getColumna("ide_aftia").setFiltro(true);
		tab_consulta_activo.getColumna("ide_geare").setFiltro(true);
		tab_consulta_activo.getColumna("ide_aftip").setFiltro(true);
		tab_consulta_activo.getColumna("ide_afnoa").setFiltro(true);
		tab_consulta_activo.getColumna("ide_afacd").setFiltro(true);
		tab_consulta_activo.getColumna("ide_afseg").setFiltro(true);
		tab_consulta_activo.getColumna("ide_tepro").setFiltro(true);
		tab_consulta_activo.getColumna("fecha_baja_afact").setFiltro(true);
		tab_consulta_activo.getColumna("marca_afact").setFiltro(true);
		tab_consulta_activo.getColumna("serie_afact").setFiltro(true);
		tab_consulta_activo.getColumna("modelo_afact").setFiltro(true);
		tab_consulta_activo.getColumna("detalle_afact").setFiltro(true);
		tab_consulta_activo.getColumna("ide_cocac").setFiltro(true);
		tab_consulta_activo.getColumna("nro_factura_afact").setFiltro(true);

		tab_consulta_activo.getColumna("foto_bien_afact").setVisible(false);;

		//tab_consulta_activo.setLectura(true);
		tab_consulta_activo.dibujar();
		PanelTabla pat_panel = new PanelTabla();
		pat_panel.setPanelTabla(tab_consulta_activo);
		
		Division div_tabla = new Division();
		div_tabla.dividir1(pat_panel);
		agregarComponente(div_tabla);
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	public Tabla getTab_consulta_activo() {
		return tab_consulta_activo;
	}

	public void setTab_consulta_activo(Tabla tab_consulta_activo) {
		this.tab_consulta_activo = tab_consulta_activo;
	}
	
}
