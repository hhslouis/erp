package paq_bodega;

import javax.ejb.EJB;

import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

public class pre_egreso_material extends Pantalla {
	private Tabla  tab_concepto_egreso=new Tabla();
	private Tabla  tab_egreso=new Tabla();
	private Combo com_inventerio=new Combo();
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
		

	public pre_egreso_material(){
		/*
		com_inventerio.setCombo("select ide_geani,detalle_geani from gen_anio order by detalle_geani");
		com_inventerio.setMetodo("seleccionaElInventario");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Inventario:"));
		bar_botones.agregarComponente(com_inventerio);
				*/
		tab_concepto_egreso.setId("tab_concepto_egreso");
		tab_concepto_egreso.setHeader("CONCEPTO EGRESO");
		tab_concepto_egreso.setTabla("bodt_concepto_egreso","ide_bocoe", 1);
		tab_concepto_egreso.getColumna("IDE_GETIP").setCombo("gen_tipo_persona","ide_getip","detalle_getip","");
		tab_concepto_egreso.getColumna("IDE_GEARE").setCombo("gen_area","ide_geare", "detalle_geare", "");
		tab_concepto_egreso.getColumna("IDE_BODES").setCombo("bodt_destino","ide_bodes","detalle_bodes", "");
		tab_concepto_egreso.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_concepto_egreso.agregarRelacion(tab_egreso);
		tab_concepto_egreso.setTipoFormulario(true);
		tab_concepto_egreso.getGrid().setColumns(4);	
		tab_concepto_egreso.dibujar();
		PanelTabla pat_concepto_egreso=new PanelTabla();
		pat_concepto_egreso.setPanelTabla(tab_concepto_egreso);
		
		
		tab_egreso.setId("tab_egreso");
		tab_egreso.setHeader("EGRESO");
		tab_egreso.setTabla("bodt_egreso", "ide_boegr", 2);
		tab_egreso.setCampoForanea("ide_bocoe");
		tab_egreso.getColumna("ide_bomat").setCombo("select ide_bomat,codigo_bomat,detalle_bomat,iva_bomat from bodt_material order by detalle_bomat");
		tab_egreso.setTipoFormulario(true);
		tab_egreso.getGrid().setColumns(4);	
		tab_egreso.dibujar();
		PanelTabla pat_egreso=new PanelTabla();
		pat_egreso.setPanelTabla(tab_egreso);
		
		Division div_division=new Division();
		div_division.dividir2(pat_concepto_egreso,pat_egreso, "50%", "h");
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
		if (tab_concepto_egreso.guardar()){
			tab_egreso.guardar();
		}
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}





	public Tabla getTab_concepto_egreso() {
		return tab_concepto_egreso;
	}





	public void setTab_concepto_egreso(Tabla tab_concepto_egreso) {
		this.tab_concepto_egreso = tab_concepto_egreso;
	}





	public Tabla getTab_egreso() {
		return tab_egreso;
	}





	public void setTab_egreso(Tabla tab_egreso) {
		this.tab_egreso = tab_egreso;
	}


}
