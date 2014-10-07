/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_nomina;

import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import paq_sistema.aplicacion.Pantalla;
import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

/**
 *
 * @author Diego
 */
public class pre_acciones_personal_manual extends Pantalla {

	private Tabla tab_tabla1 = new Tabla();
	private Tabla tab_tabla2 = new Tabla();
	private AutoCompletar aut_empleado = new AutoCompletar();	


	public pre_acciones_personal_manual() {

		aut_empleado.setId("aut_empleado");		
		aut_empleado.setAutoCompletar("select IDE_GTEMP,DOCUMENTO_IDENTIDAD_GTEMP,APELLIDO_PATERNO_GTEMP,APELLIDO_MATERNO_GTEMP,PRIMER_NOMBRE_GTEMP,SEGUNDO_NOMBRE_GTEMP from GTH_EMPLEADO");
		aut_empleado.setMetodoChange("filtrarEmpleado");
		bar_botones.agregarComponente(new Etiqueta("Empleado"));
		bar_botones.agregarComponente(aut_empleado);

		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		bar_botones.agregarBoton(bot_limpiar);


		tab_tabla1.setId("tab_tabla1");
		tab_tabla1.setTabla("GEN_DETALLE_EMPLEADO_DEPARTAME","IDE_GEDED", 1);
		tab_tabla1.getColumna("FECHA_SALIDA_GEDED").setVisible(false);
		tab_tabla1.getColumna("GEN_IDE_GEDED").setVisible(false);
		tab_tabla1.getColumna("IDE_GEINS").setCombo("GEN_INSTITUCION", "IDE_GEINS", "DETALLE_GEINS", "");
		tab_tabla1.getColumna("IDE_GEINS").setAutoCompletar();
		tab_tabla1.getColumna("IDE_GEAME").setCombo("SELECT a.IDE_GEAME,b.DETALLE_GEAED,c.DETALLE_GEMED FROM GEN_ACCION_MOTIVO_EMPLEADO a "
				+ "LEFT JOIN ( "
				+ "SELECT IDE_GEAED,DETALLE_GEAED from GEN_ACCION_EMPLEADO_DEPA "
				+ ")b ON b.IDE_GEAED=a.IDE_GEAED "
				+ "LEFT JOIN ( "
				+ "SELECT IDE_GEMED,DETALLE_GEMED FROM GEN_MOTIVO_EMPLEADO_DEPA "
				+ ")c ON c.IDE_GEMED=a.IDE_GEMED "
				+ "ORDER BY  b.DETALLE_GEAED,c.DETALLE_GEMED");
		tab_tabla1.getColumna("IDE_GEAME").setAutoCompletar();
		tab_tabla1.getColumna("IDE_GEAME").setMetodoChange("cambioAccion");
		tab_tabla1.getColumna("FECHA_INGRESO_GEDED").setValorDefecto(utilitario.getFechaActual());
		tab_tabla1.getColumna("ACTIVO_GEDED").setCheck();
		tab_tabla1.getColumna("ACTIVO_GEDED").setValorDefecto("true");		
		tab_tabla1.getColumna("IDE_GTEMP").setVisible(false);
		tab_tabla1.setCampoOrden("IDE_GEDED DESC");		
		tab_tabla1.setCondicion("IDE_GTEMP=-1");		
		tab_tabla1.getColumna("GEN_IDE_GEDED").setLectura(true);
		tab_tabla1.agregarRelacion(tab_tabla2);
		tab_tabla1.dibujar();
		PanelTabla pat_panel1 = new PanelTabla();
		pat_panel1.setPanelTabla(tab_tabla1);


		tab_tabla2.setId("tab_tabla2");
		tab_tabla2.setTabla("GEN_EMPLEADOS_DEPARTAMENTO_PAR","IDE_GEEDP", 3);
		tab_tabla2.onSelect("seleccionaTablaEmpleadosDepartamento");
		tab_tabla2.getColumna("IDE_GEGRO").setCombo("GEN_GRUPO_OCUPACIONAL", "IDE_GEGRO", "DETALLE_GEGRO", "");
		tab_tabla2.getColumna("FECHA_GEEDP").setValorDefecto(utilitario.getFechaActual());
		tab_tabla2.getColumna("IDE_GEGRO").setMetodoChange("cargarCargoFuncional");
		tab_tabla2.getColumna("IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL","IDE_GECAF","DETALLE_GECAF","PRINCIPAL_SECUNDARIO_GECAF=TRUE AND IDE_GECAF IN (SELECT IDE_GECAF FROM GEN_GRUPO_CARGO where IDE_GEGRO=-1)");
		tab_tabla2.getColumna("IDE_GECAF").setMetodoChange("cargarPartidaGrupoCargo");
		tab_tabla2.getColumna("IDE_GECAF").setBuscarenCombo(false);
		tab_tabla2.getColumna("GEN_IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL", "IDE_GECAF", "DETALLE_GECAF", "");
		tab_tabla2.getColumna("IDE_SUCU").setCombo("SIS_SUCURSAL", "IDE_SUCU", "NOM_SUCU", "");
		tab_tabla2.getColumna("IDE_SUCU").setVisible(true);
		tab_tabla2.getColumna("IDE_SUCU").setMetodoChange("cargarCargoAreas");
		tab_tabla2.getColumna("IDE_GEARE").setCombo("GEN_AREA","IDE_GEARE","DETALLE_GEARE","IDE_GEARE IN (SELECT IDE_GEARE from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU=-1)");
		tab_tabla2.getColumna("IDE_GEARE").setMetodoChange("cargarCargoDepartamentos");
		tab_tabla2.getColumna("IDE_GEARE").setBuscarenCombo(false);
		tab_tabla2.getColumna("IDE_GEDEP").setCombo("GEN_DEPARTAMENTO","IDE_GEDEP","DETALLE_GEDEP","IDE_GEDEP IN (SELECT IDE_GEDEP from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU=-1 AND IDE_GEARE=-1)");
		tab_tabla2.getColumna("IDE_GEDEP").setMetodoChange("cargarPartidaGrupoCargo");
		tab_tabla2.getColumna("IDE_GEDEP").setBuscarenCombo(false);
		tab_tabla2.getColumna("IDE_GECAE").setCombo("GEN_CATEGORIA_ESTATUS", "IDE_GECAE", "DETALLE_GECAE", "");
		tab_tabla2.getColumna("IDE_GETIV").setCombo("GEN_TIPO_VINCULACION", "IDE_GETIV", "DETALLE_GETIV", "");
		tab_tabla2.getColumna("IDE_GETIV").setBuscarenCombo(false);
		tab_tabla2.getColumna("AJUSTE_SUELDO_GEEDP").setMetodoChange("cambioAjuste");
		tab_tabla2.getColumna("IDE_GEPGC").setCombo("SELECT IDE_GEPGC,PAP.CODIGO_PARTIDA_GEPAP,PAP.DETALLE_GEPAP,pgc.TITULO_CARGO_GEPGC "
				+ "from GEN_PARTIDA_GRUPO_CARGO pgc "
				+ "left join GEN_PARTIDA_PRESUPUESTARIA pap on PAP.IDE_GEPAP=PGC.IDE_GEPAP");
		tab_tabla2.getColumna("IDE_GTTEM").setCombo("GTH_TIPO_EMPLEADO", "IDE_GTTEM", "DETALLE_GTTEM", "");
		tab_tabla2.getColumna("IDE_GTTCO").setCombo("GTH_TIPO_CONTRATO", "IDE_GTTCO", "DETALLE_GTTCO", "");
		tab_tabla2.getColumna("IDE_GTTSI").setCombo("GTH_TIPO_SINDICATO", "IDE_GTTSI", "DETALLE_GTTSI", "");
		tab_tabla2.getColumna("IDE_GTGRE").setCombo("GTH_GRUPO_EMPLEADO", "IDE_GTGRE", "DETALLE_GTGRE", "");
		tab_tabla2.getColumna("IDE_GTGRE").setCombo("GTH_GRUPO_EMPLEADO", "IDE_GTGRE", "DETALLE_GTGRE", "");
		tab_tabla2.getColumna("ACTIVO_GEEDP").setCheck();
		tab_tabla2.getColumna("ACTIVO_GEEDP").setLectura(true);
		tab_tabla2.getColumna("ACTIVO_GEEDP").setValorDefecto("true");
		tab_tabla2.getColumna("ACUMULA_FONDOS_GEEDP").setCheck();
		tab_tabla2.getColumna("LINEA_SUPERVICION_GEEDP").setCheck();
		tab_tabla2.getColumna("IDE_GEPGC").setAutoCompletar();
		tab_tabla2.getColumna("IDE_GEPGC").setMetodoChange("cambioPartida");
		tab_tabla2.getColumna("IDE_GTEMP").setVisible(false);
		tab_tabla2.getColumna("LIQUIDACION_GEEDP").setLectura(true);
		tab_tabla2.getColumna("LIQUIDACION_GEEDP").setCheck();
		tab_tabla2.getColumna("CONTROL_ASISTENCIA_GEEDP").setCheck();
		tab_tabla2.getColumna("CONTROL_ASISTENCIA_GEEDP").setValorDefecto("false");
		tab_tabla2.getColumna("LIQUIDACION_GEEDP").setCheck();
		tab_tabla2.getColumna("LIQUIDACION_GEEDP").setValorDefecto("false");
		tab_tabla2.getColumna("EJECUTO_LIQUIDACION_GEEDP").setCheck();
		tab_tabla2.getColumna("EJECUTO_LIQUIDACION_GEEDP").setValorDefecto("false");
		tab_tabla2.getColumna("GEN_IDE_GEGRO").setVisible(false);
		tab_tabla2.setMostrarcampoSucursal(true);
		tab_tabla2.setTipoFormulario(true);
		tab_tabla2.getGrid().setColumns(4);
		tab_tabla2.getColumna("IDE_GTTCO").setMetodoChange("cambioTipoContrato");		
		tab_tabla2.dibujar();

		actualizarCombosDepartamentoEmpleado();
		PanelTabla pat_panel2 = new PanelTabla();
		pat_panel2.setPanelTabla(tab_tabla2);

		Division div_division = new Division();
		div_division.setId("div_division");
		div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");
		agregarComponente(div_division);		
	}

	public void filtrarEmpleado(SelectEvent evt){
		aut_empleado.onSelect(evt);
		if(aut_empleado.getValor()!=null){
			tab_tabla1.setCondicion("IDE_GTEMP=" + aut_empleado.getValor());
			tab_tabla1.ejecutarSql();
			tab_tabla2.setValorForanea(tab_tabla1.getValorSeleccionado());
			actualizarCombosDepartamentoEmpleado();
		}else{
			utilitario.agregarMensajeInfo("No se puede mostrar registros", "Debe seleccionar un empleado");
		}
		utilitario.addUpdate("tab_tabla1,tab_tabla2");
	}

	public void limpiar(){		
		tab_tabla1.limpiar();
		tab_tabla2.limpiar();
		aut_empleado.limpiar();		
		utilitario.addUpdate("aut_empleado");
	}
	
	public void cambioPartida(SelectEvent evt){
		tab_tabla2.modificar(evt);
		if(tab_tabla2.getValor("IDE_GEPGC")!=null){
			TablaGenerica tab_datos= utilitario.consultar("select ide_gepgc,ide_gegro,ide_gecaf,ide_sucu,ide_gedep,ide_geare,ide_gttem from gen_partida_grupo_cargo where IDE_GEPGC ="+tab_tabla2.getValor("IDE_GEPGC"));
			if(tab_datos.isEmpty()==false){
				tab_tabla2.getColumna("IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL", "IDE_GECAF", "DETALLE_GECAF", "PRINCIPAL_SECUNDARIO_GECAF=TRUE AND IDE_GECAF IN (SELECT IDE_GECAF FROM GEN_GRUPO_CARGO where IDE_GEGRO="+tab_datos.getValor("IDE_GEGRO")+")");
				tab_tabla2.getColumna("IDE_GEARE").setCombo("GEN_AREA","IDE_GEARE", "DETALLE_GEARE", "IDE_GEARE IN (SELECT IDE_GEARE from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+tab_datos.getValor("ide_sucu")+")");
				tab_tabla2.getColumna("IDE_GEDEP").setCombo("GEN_DEPARTAMENTO", "IDE_GEDEP", "DETALLE_GEDEP", "IDE_GEDEP IN (SELECT IDE_GEDEP from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+tab_datos.getValor("ide_sucu")+" AND IDE_GEARE="+tab_datos.getValor("IDE_GEARE")+")");
				tab_tabla2.setValor("IDE_GEGRO",tab_datos.getValor("IDE_GEGRO"));		
				tab_tabla2.setValor("IDE_GECAF",tab_datos.getValor("IDE_GECAF"));		
				tab_tabla2.setValor("IDE_SUCU",tab_datos.getValor("ide_sucu"));		
				tab_tabla2.setValor("IDE_GTTEM",tab_datos.getValor("IDE_GTTEM"));		
				tab_tabla2.setValor("IDE_GEDEP",tab_datos.getValor("IDE_GEDEP"));		
				tab_tabla2.setValor("IDE_GEARE",tab_datos.getValor("IDE_GEARE"));
				utilitario.addUpdate("tab_tabla2");
			}
		}
	}
	
	@Override
	public void insertar() {
		if(aut_empleado.getValor()!=null){
		if (tab_tabla1.isFocus()){
			tab_tabla1.insertar();
		tab_tabla1.setValor("IDE_GTEMP", aut_empleado.getValor());
		}
		if (tab_tabla2.isFocus()){
			tab_tabla2.insertar();
			tab_tabla2.setValor("IDE_GTEMP", aut_empleado.getValor());	
		}
		}else{
			utilitario.agregarMensajeInfo("No se puede Insertar", "Debe seleccionar un Empleado");
		}
	}

	@Override
	public void guardar() {

		if (tab_tabla1.guardar()) {
			if (tab_tabla2.guardar()) {
				if(tab_tabla2.getValor("fecha_finctr_geedp")!=null && !tab_tabla2.getValor("fecha_finctr_geedp").isEmpty()){
					if (utilitario.isFechaMenor(utilitario.getFecha(tab_tabla2.getValor("fecha_finctr_geedp")), utilitario.getFecha(tab_tabla2.getValor("fecha_geedp")))){
						utilitario.agregarMensajeInfo("No se puede guardar", "La fecha fin de contrato no puede ser menor que la fecha contrato");
						return;
					}	
				}					

				if(tab_tabla2.getValor("fecha_encargo_fin_geedp")!=null && !tab_tabla2.getValor("fecha_encargo_fin_geedp").isEmpty()){
					if (utilitario.isFechaMenor(utilitario.getFecha(tab_tabla2.getValor("fecha_encargo_fin_geedp")), utilitario.getFecha(tab_tabla2.getValor("fecha_geedp")))){
						utilitario.agregarMensajeInfo("No se puede guardar", "La fecha encargo fin de contrato no puede ser menor que la fecha contrato");
						return;
					}
				}

				if(tab_tabla2.getValor("fecha_encargo_geedp")!=null && !tab_tabla2.getValor("fecha_encargo_geedp").isEmpty()){
					if (utilitario.isFechaMenor(utilitario.getFecha(tab_tabla2.getValor("fecha_encargo_geedp")), utilitario.getFecha(tab_tabla2.getValor("fecha_geedp")))){
						utilitario.agregarMensajeInfo("No se puede guardar", "La fecha encargo de contrato no puede ser menor que la fecha contrato");
						return;
					}
				}					


				if(tab_tabla2.getValor("fecha_ajuste_geedp")!=null && !tab_tabla2.getValor("fecha_ajuste_geedp").isEmpty()){
					if (utilitario.isFechaMenor(utilitario.getFecha(tab_tabla2.getValor("fecha_ajuste_geedp")), utilitario.getFecha(tab_tabla2.getValor("fecha_geedp")))){
						utilitario.agregarMensajeInfo("No se puede guardar", "La fecha ajuste de contrato no puede ser menor que la fecha contrato");
						return;
					}
				}

				if(tab_tabla2.getValor("fecha_encargo_geedp")!=null && !tab_tabla2.getValor("fecha_encargo_geedp").isEmpty()){
					if(tab_tabla2.getValor("fecha_encargo_fin_geedp")!=null && !tab_tabla2.getValor("fecha_encargo_fin_geedp").isEmpty()){
						if (utilitario.isFechaMenor(utilitario.getFecha(tab_tabla2.getValor("fecha_encargo_fin_geedp")), utilitario.getFecha(tab_tabla2.getValor("fecha_encargo_geedp")))){
							utilitario.agregarMensajeInfo("No se puede guardar", "La fecha de encargo fin  de contrato no puede ser menor que la fecha encargo contrato");
							return;
						}	
					}						
				}

				guardarPantalla();
			}
		}
	}
	public void cargarCargoFuncional(AjaxBehaviorEvent evt){		
		tab_tabla2.modificar(evt);
		tab_tabla2.getColumna("IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL", "IDE_GECAF", "DETALLE_GECAF", "PRINCIPAL_SECUNDARIO_GECAF=TRUE AND IDE_GECAF IN (SELECT IDE_GECAF FROM GEN_GRUPO_CARGO where IDE_GEGRO="+tab_tabla2.getValor("IDE_GEGRO")+")");
		tab_tabla2.getColumna("GEN_IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL", "IDE_GECAF", "DETALLE_GECAF", "PRINCIPAL_SECUNDARIO_GECAF=FALSE AND IDE_GECAF IN (SELECT IDE_GECAF FROM GEN_GRUPO_CARGO where IDE_GEGRO="+tab_tabla2.getValor("IDE_GEGRO")+")");		
		tab_tabla2.getColumna("IDE_GEPGC").setCombo("SELECT IDE_GEPGC,PAP.CODIGO_PARTIDA_GEPAP,PAP.DETALLE_GEPAP " +
				"from GEN_PARTIDA_GRUPO_CARGO pgc " +
				"left join GEN_PARTIDA_PRESUPUESTARIA pap on PAP.IDE_GEPAP=PGC.IDE_GEPAP " +
				"where ide_gegro="+tab_tabla2.getValor("IDE_GEGRO")+" " +
				"and IDE_GECAF="+tab_tabla2.getValor("IDE_GECAF")+" " +
				"and IDE_SUCU="+tab_tabla2.getValor("IDE_SUCU")+" " +
				"and ide_gedep="+tab_tabla2.getValor("IDE_GEDEP")+" " +
				"and IDE_GEARE="+tab_tabla2.getValor("IDE_GEARE")+" ");		
		utilitario.addUpdate("tab_tabla2");

	}
	
	public void cargarPartidaGrupoCargo(AjaxBehaviorEvent evt){
		tab_tabla2.modificar(evt);
		tab_tabla2.getColumna("IDE_GEPGC").setCombo("SELECT IDE_GEPGC,PAP.CODIGO_PARTIDA_GEPAP,PAP.DETALLE_GEPAP " +
				"from GEN_PARTIDA_GRUPO_CARGO pgc " +
				"left join GEN_PARTIDA_PRESUPUESTARIA pap on PAP.IDE_GEPAP=PGC.IDE_GEPAP " +
				"where ide_gegro="+tab_tabla2.getValor("IDE_GEGRO")+" " +
				"and IDE_GECAF="+tab_tabla2.getValor("IDE_GECAF")+" " +
				"and IDE_SUCU="+tab_tabla2.getValor("IDE_SUCU")+" " +
				"and ide_gedep="+tab_tabla2.getValor("IDE_GEDEP")+" " +
				"and IDE_GEARE="+tab_tabla2.getValor("IDE_GEARE")+" ");
		utilitario.addUpdate("tab_tabla2");
	}
	
	
	public void cargarCargoAreas(AjaxBehaviorEvent evt){
		tab_tabla2.modificar(evt);
		tab_tabla2.getColumna("IDE_GEARE").setCombo("GEN_AREA", "IDE_GEARE", "DETALLE_GEARE", "IDE_GEARE IN (SELECT IDE_GEARE from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+tab_tabla2.getValor("ide_sucu")+")");		
		tab_tabla2.getColumna("IDE_GEPGC").setCombo("SELECT IDE_GEPGC,PAP.CODIGO_PARTIDA_GEPAP,PAP.DETALLE_GEPAP " +
				"from GEN_PARTIDA_GRUPO_CARGO pgc " +
				"left join GEN_PARTIDA_PRESUPUESTARIA pap on PAP.IDE_GEPAP=PGC.IDE_GEPAP " +
				"where ide_gegro="+tab_tabla2.getValor("IDE_GEGRO")+" " +
				"and IDE_GECAF="+tab_tabla2.getValor("IDE_GECAF")+" " +
				"and IDE_SUCU="+tab_tabla2.getValor("IDE_SUCU")+" " +
				"and ide_gedep="+tab_tabla2.getValor("IDE_GEDEP")+" " +
				"and IDE_GEARE="+tab_tabla2.getValor("IDE_GEARE")+" ");
		utilitario.addUpdate("tab_tabla2");
	}
	
	
	public void cargarCargoDepartamentos(AjaxBehaviorEvent evt){
		tab_tabla2.modificar(evt);
		tab_tabla2.getColumna("IDE_GEDEP").setCombo("GEN_DEPARTAMENTO", "IDE_GEDEP", "DETALLE_GEDEP", "IDE_GEDEP IN (SELECT IDE_GEDEP from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+tab_tabla2.getValor("IDE_SUCU")+" AND IDE_GEARE="+tab_tabla2.getValor("IDE_GEARE")+")");		
		tab_tabla2.getColumna("IDE_GEPGC").setCombo("SELECT IDE_GEPGC,PAP.CODIGO_PARTIDA_GEPAP,PAP.DETALLE_GEPAP " +
				"from GEN_PARTIDA_GRUPO_CARGO pgc " +
				"left join GEN_PARTIDA_PRESUPUESTARIA pap on PAP.IDE_GEPAP=PGC.IDE_GEPAP " +
				"where ide_gegro="+tab_tabla2.getValor("IDE_GEGRO")+" " +
				"and IDE_GECAF="+tab_tabla2.getValor("IDE_GECAF")+" " +
				"and IDE_SUCU="+tab_tabla2.getValor("IDE_SUCU")+" " +
				"and ide_gedep="+tab_tabla2.getValor("IDE_GEDEP")+" " +
				"and IDE_GEARE="+tab_tabla2.getValor("IDE_GEARE")+" ");
		utilitario.addUpdate("tab_tabla2");		
	}
	
	
	private void actualizarCombosDepartamentoEmpleado(String ide_sucu,String IDE_GEGRO,String IDE_GEARE){
		tab_tabla2.getColumna("IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL", "IDE_GECAF", "DETALLE_GECAF", "PRINCIPAL_SECUNDARIO_GECAF=TRUE AND IDE_GECAF IN (SELECT IDE_GECAF FROM GEN_GRUPO_CARGO where IDE_GEGRO="+IDE_GEGRO+")");
		tab_tabla2.getColumna("IDE_GEARE").setCombo("GEN_AREA","IDE_GEARE", "DETALLE_GEARE", "IDE_GEARE IN (SELECT IDE_GEARE from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+ide_sucu+")");
		tab_tabla2.getColumna("IDE_GEDEP").setCombo("GEN_DEPARTAMENTO", "IDE_GEDEP", "DETALLE_GEDEP", "IDE_GEDEP IN (SELECT IDE_GEDEP from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+ide_sucu+" AND IDE_GEARE="+IDE_GEARE+")");
		tab_tabla2.setValor("IDE_GEGRO",IDE_GEGRO);		
		tab_tabla2.setValor("IDE_SUCU",ide_sucu);
//		tab_tabla2.setValor("IDE_GECAF",set_encargo.getTab_seleccion().getValor("IDE_GECAF"));				
//		tab_tabla2.setValor("IDE_GTTEM",set_encargo.getTab_seleccion().getValor("IDE_GTTEM"));		
//		tab_tabla2.setValor("IDE_GEDEP",set_encargo.getTab_seleccion().getValor("IDE_GEDEP"));		
		tab_tabla2.setValor("IDE_GEARE",IDE_GEARE);		
	}

	private void actualizarCombosDepartamentoEmpleado(){
		tab_tabla2.getColumna("IDE_GECAF").setCombo("GEN_CARGO_FUNCIONAL", "IDE_GECAF", "DETALLE_GECAF", "PRINCIPAL_SECUNDARIO_GECAF=TRUE AND IDE_GECAF IN (SELECT IDE_GECAF FROM GEN_GRUPO_CARGO where IDE_GEGRO="+tab_tabla2.getValor("IDE_GEGRO")+")");		
		tab_tabla2.getColumna("IDE_GEARE").setCombo("GEN_AREA","IDE_GEARE", "DETALLE_GEARE", "IDE_GEARE IN (SELECT IDE_GEARE from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+tab_tabla2.getValor("ide_sucu")+")");		
		tab_tabla2.actualizarCombosFormulario();
		tab_tabla2.getColumna("IDE_GEDEP").setCombo("GEN_DEPARTAMENTO", "IDE_GEDEP", "DETALLE_GEDEP", "IDE_GEDEP IN (SELECT IDE_GEDEP from GEN_DEPARTAMENTO_SUCURSAL where IDE_SUCU="+tab_tabla2.getValor("IDE_SUCU")+" AND IDE_GEARE="+tab_tabla2.getValor("IDE_GEARE")+")");
		tab_tabla2.actualizarCombosFormulario();		
	}
	
	
	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();
	}

	public Tabla getTab_tabla1() {
		return tab_tabla1;
	}

	public void setTab_tabla1(Tabla tab_tabla1) {
		this.tab_tabla1 = tab_tabla1;
	}

	public Tabla getTab_tabla2() {
		return tab_tabla2;
	}

	public void setTab_tabla2(Tabla tab_tabla2) {
		this.tab_tabla2 = tab_tabla2;
	}

	public AutoCompletar getAut_empleado() {
		return aut_empleado;
	}

	public void setAut_empleado(AutoCompletar aut_empleado) {
		this.aut_empleado = aut_empleado;
	}

}
