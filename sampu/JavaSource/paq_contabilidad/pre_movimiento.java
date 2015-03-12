package paq_contabilidad;

import javax.ejb.EJB;

import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

public class pre_movimiento extends Pantalla{
	
	private Tabla tab_movimiento=new Tabla();
	private Tabla tab_detalle_movimiento=new Tabla();
	private Combo com_anio=new Combo();
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	
	public pre_movimiento (){
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		tab_movimiento.setId("tab_movimiento");
		tab_movimiento.setHeader("MOVIMIENTOS");
		tab_movimiento.setTabla("cont_movimiento", "ide_comov", 1);
		tab_movimiento.getColumna("ide_cotim").setCombo("cont_tipo_movimiento", "ide_cotim", "detalle_cotim", "");
		tab_movimiento.getColumna("ide_cotia").setCombo("cont_tipo_asiento", "ide_cotia", "detalle_cotia", "");
		tab_movimiento.getColumna("ide_geare").setCombo("gen_area", "ide_geare", "detalle_geare", "");
		tab_movimiento.getColumna("ide_gemes").setCombo("gen_mes", "ide_gemes", "detalle_gemes", "");
		tab_movimiento.getColumna("ide_geani").setVisible(false);
		tab_movimiento.getColumna("ide_tecpo").setLectura(true);
		tab_movimiento.getColumna("activo_comov").setValorDefecto("true");
		tab_movimiento.getColumna("activo_comov").setLectura(true);
		
		tab_movimiento.setTipoFormulario(true);
		tab_movimiento.getGrid().setColumns(4);
		tab_movimiento.agregarRelacion(tab_detalle_movimiento);
		tab_movimiento.dibujar();
		PanelTabla pat_movimiento=new PanelTabla();
		pat_movimiento.setPanelTabla(tab_movimiento);
		
		/////detalle movinto
		tab_detalle_movimiento.setId("tab_detalle_movimiento");
		tab_detalle_movimiento.setHeader("DETALLE DE MOVIMIENTO");
		tab_detalle_movimiento.setTabla("cont_detalle_movimiento", "ide_codem", 2);
		tab_detalle_movimiento.getColumna("ide_prcla").setCombo("pre_clasificador", "ide_prcla", "codigo_clasificador_prcla", "");
		tab_detalle_movimiento.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
		tab_detalle_movimiento.getColumna("ide_cocac").setCombo("cont_catalogo_cuenta", "ide_cocac", "cue_codigo_cocac", "");
		tab_detalle_movimiento.getColumna("activo_codem").setLectura(true);
		tab_detalle_movimiento.getColumna("activo_codem").setValorDefecto("true");
		tab_detalle_movimiento.setColumnaSuma("haber_codem");			
		tab_detalle_movimiento.setColumnaSuma("debe_codem");			

		tab_detalle_movimiento.getGrid().setColumns(4);
		tab_detalle_movimiento.dibujar();
		PanelTabla pat_detalle_movimiento=new PanelTabla();
		pat_detalle_movimiento.setPanelTabla(tab_detalle_movimiento);
		
		Division div_division =new Division();
		div_division.dividir2(pat_movimiento, pat_detalle_movimiento, "50%", "H");
		agregarComponente(div_division);
		
		
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensaje("No se puede insertar", "Debe Seleccionar un Año");
			return;
		}
		else if (tab_movimiento.isFocus()) {
			tab_movimiento.insertar();
			tab_movimiento.setValor("ide_geani", com_anio.getValue()+"");
            utilitario.addUpdateTabla(tab_movimiento, "ide_geani", "");


		}
		else if (tab_detalle_movimiento.isFocus()) {
			tab_detalle_movimiento.insertar();
			
		}
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_movimiento.guardar()){
			tab_detalle_movimiento.guardar();

		}
		utilitario.addUpdate("tab_detalle_movimiento");

		guardarPantalla();
		utilitario.addUpdate("tab_detalle_movimiento");

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_movimiento() {
		return tab_movimiento;
	}

	public void setTab_movimiento(Tabla tab_movimiento) {
		this.tab_movimiento = tab_movimiento;
	}

	public Tabla getTab_detalle_movimiento() {
		return tab_detalle_movimiento;
	}

	public void setTab_detalle_movimiento(Tabla tab_detalle_movimiento) {
		this.tab_detalle_movimiento = tab_detalle_movimiento;
	}
	

}
