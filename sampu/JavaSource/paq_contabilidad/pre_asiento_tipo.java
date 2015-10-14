package paq_contabilidad;

import javax.ejb.EJB;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_asiento_tipo extends Pantalla{

	private Tabla tab_estado=new Tabla();
	
	
	   @EJB
	    private ServicioContabilidad ser_contabilidad = (ServicioContabilidad) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	public pre_asiento_tipo() {
		tab_estado.setId("tab_estado");  
		tab_estado.setTabla("cont_asiento_tipo", "ide_coast", 1);	
		tab_estado.getColumna("ide_gemod").setCombo("select ide_gemod,detalle_gemod from gen_modulo order by detalle_gemod");
		tab_estado.getColumna("ide_bogrm").setCombo("select ide_bogrm,detalle_bogrm from bodt_grupo_material order by detalle_bogrm");
		tab_estado.getColumna("ide_bogrm").setAutoCompletar();
		tab_estado.getColumna("ide_cocac").setCombo(ser_contabilidad.servicioCatalogoCuentasTransaccion("1"));
		tab_estado.getColumna("ide_cocac").setAutoCompletar();
		tab_estado.getColumna("ide_gelua").setCombo("select ide_gelua,detalle_gelua from gen_lugar_aplica");
		tab_estado.getColumna("ide_coest").setCombo("select ide_coest,detalle_coest from cont_estado order by detalle_coest");
		tab_estado.dibujar();
		PanelTabla pat_estado=new PanelTabla();
		pat_estado.setPanelTabla(tab_estado);
		
		Division div_division = new Division();
		div_division.dividir1(pat_estado);
		agregarComponente(div_division);
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_estado.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_estado.guardar();
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_estado.eliminar();
	}

	public Tabla gettab_estado() {
		return tab_estado;
	}

	public void settab_estado(Tabla tab_estado) {
		this.tab_estado = tab_estado;
	}


}
