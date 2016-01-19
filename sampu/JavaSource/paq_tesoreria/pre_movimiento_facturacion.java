package paq_tesoreria;

import java.awt.TextArea;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import paq_contabilidad.ejb.ServicioContabilidad;
import paq_facturacion.ejb.ServicioFacturacion;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import portal.entidades.TipoNominas;
import framework.aplicacion.TablaGenerica;
import framework.componentes.AreaTexto;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionCalendario;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.TablaGrid;
import framework.componentes.Texto;

public class pre_movimiento_facturacion extends Pantalla{
	
	private Tabla tab_movimiento=new Tabla();
	private Tabla tab_detalle_movimiento=new Tabla();
	private Combo com_anio=new Combo();
	private Combo com_anio_debito=new Combo();
	private Combo com_anio_credito=new Combo();

	private Combo com_estado_factura=new Combo();
	private SeleccionTabla sel_factura_contabilidad= new SeleccionTabla();
	private SeleccionTabla sel_factura_consulta = new SeleccionTabla();
	private SeleccionTabla sel_nombre_asiento = new SeleccionTabla();
	private SeleccionTabla sel_nota_debito = new SeleccionTabla();
	private SeleccionTabla sel_nota_credito = new SeleccionTabla();
	private SeleccionTabla sel_nombre_asiento_debito = new SeleccionTabla();
	private SeleccionTabla sel_nombre_asiento_credito = new SeleccionTabla();

	private Calendario cal_fecha_inicial = new Calendario();
	private Calendario cal_fecha_inicial_debito = new Calendario();
	private Calendario cal_fecha_inicial_credito = new Calendario();

	private Calendario cal_fecha_final = new Calendario();
	private Dialogo dia_cabecera_asiento= new Dialogo();
	private Dialogo dia_cabecera_asiento_debito= new Dialogo();
	private Dialogo dia_cabecera_asiento_credito= new Dialogo();

	private SeleccionCalendario sec_importar=new SeleccionCalendario();
	private SeleccionCalendario sel_cal_debito=new SeleccionCalendario();
	private SeleccionCalendario sel_cal_credito=new SeleccionCalendario();
	
	private Combo com_tipo_movimiento = new Combo();
	private Combo com_mes = new Combo();
	private Texto txt_comprobante = new Texto();
	private AreaTexto txt_area_detalle_asiento = new AreaTexto();
	private Combo com_tipo_movimiento_debito = new Combo();
	private Combo com_mes_debito = new Combo();
	private Texto txt_comprobante_debito = new Texto();
	private AreaTexto txt_area_detalle_asiento_debito = new AreaTexto();
	private Combo com_tipo_movimiento_credito = new Combo();
	private Combo com_mes_credito = new Combo();
	private Texto txt_comprobante_credito = new Texto();
	private AreaTexto txt_area_detalle_asiento_credito = new AreaTexto();
	private String str_tipo_asiento="";
	private String str_asiento_seleccionado="";
	private String str_individual="";
	private String str_tipo_emision=utilitario.getVariable("p_factura_emitido");
	private String str_tipo_pagado=utilitario.getVariable("p_factura_pagado");
	private String str_parametro_tipo_asiento="";
	private String str_seleccionados_contabilidad="";
	private String str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
	private String str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";
	private String str_lugar_aplica_debe="p_gen_lugar_aplica_debe";
	private String str_lugar_aplica_haber="p_gen_lugar_aplica_haber";
	private String str_ide_conac="";

	
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioPresupuesto ser_Presupuesto = (ServicioPresupuesto ) utilitario.instanciarEJB(ServicioPresupuesto.class);
	@EJB
	private ServicioFacturacion ser_Facturacion = (ServicioFacturacion ) utilitario.instanciarEJB(ServicioFacturacion.class);

	
	private String p_modulo_factruracion="";
	private String p_modulo_nota_debito="";
	private String p_modulo_nota_credito="";
	
	public pre_movimiento_facturacion (){
		p_modulo_factruracion=utilitario.getVariable("p_modulo_facturacion");
		p_modulo_nota_debito=utilitario.getVariable("p_modulo_nota_debito");
		p_modulo_nota_credito=utilitario.getVariable("p_modulo_nota_credito");		
		
		/*
		// agrego etiqueta para seleccionare le stado d el afactura
		Etiqueta eti_estado_factura = new Etiqueta();
		eti_estado_factura.setValue("Seleccione el Estado de la Factura: ");
		com_estado_factura.setId("com_estado_factura");
		com_estado_factura.setCombo(ser_contabilidad.getModuloEstados("true,false", utilitario.getVariable("p_modulo_facturacion")));
		bar_botones.agregarComponente(eti_estado_factura);
		bar_botones.agregarComponente(com_estado_factura);
		*/
				
		// agrgo calendarios
		Etiqueta eti_finicio = new Etiqueta();
		eti_finicio.setValue("Fecha Inicial: ");
		bar_botones.agregarComponente(eti_finicio);
		cal_fecha_inicial.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_inicial);
		
		Etiqueta eti_ffin = new Etiqueta();
		eti_ffin.setValue("Fecha Final: ");
		bar_botones.agregarComponente(eti_ffin);
		cal_fecha_final.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_final);
		
		// agrego boton contabilizar
		Boton bot_contabilizar = new Boton();
		bot_contabilizar.setValue("Contabilizar Facturas");
		bot_contabilizar.setMetodo("contabilizar");
		bar_botones.agregarBoton(bot_contabilizar);

		// agrego boton contabilizar notas de debito
		Boton bot_contabilizar_debito = new Boton();
		bot_contabilizar_debito.setValue("Contabilizar Notas Debito");
		bot_contabilizar_debito.setMetodo("contabilizarNotaDebito");
		bar_botones.agregarBoton(bot_contabilizar_debito);
		
		// agrego boton contabilizar notas de debito
		Boton bot_contabilizar_credito = new Boton();
		bot_contabilizar_credito.setValue("Contabilizar Notas Credito");
		bot_contabilizar_credito.setMetodo("contabilizarNotaCredito");
		bar_botones.agregarBoton(bot_contabilizar_credito);		
		
		// agrego boton cponsultar facturas contabilizadas
		Boton bot_consulta_factura = new Boton();
		bot_consulta_factura.setValue("Consultar Facturas");
		bot_consulta_factura.setMetodo("consultaFacturas");
		bar_botones.agregarBoton(bot_consulta_factura);
				
		tab_movimiento.setId("tab_movimiento");
		tab_movimiento.setHeader("MOVIMIENTOS");
		tab_movimiento.setTabla("cont_movimiento", "ide_comov", 1);
		tab_movimiento.getColumna("ide_cotim").setCombo("cont_tipo_movimiento", "ide_cotim", "detalle_cotim", "");
		tab_movimiento.getColumna("ide_cotia").setCombo("cont_tipo_asiento", "ide_cotia", "detalle_cotia", "");
		tab_movimiento.getColumna("ide_geare").setCombo("gen_area", "ide_geare", "detalle_geare", "");
		tab_movimiento.getColumna("ide_gemes").setCombo("gen_mes", "ide_gemes", "detalle_gemes", "");
		tab_movimiento.getColumna("ide_geani").setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		tab_movimiento.setCondicion("ide_geani in (select ide_geani from gen_anio where activo_geani=true and bloqueado_geani=false) and ide_gemod in ("+p_modulo_factruracion+","+p_modulo_nota_credito+","+p_modulo_nota_debito+") "); 
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
		//tab_detalle_movimiento.getColumna("ide_prcla").setCombo(ser_Presupuesto.getCatalogoPresupuestario("true,false"));
		//tab_detalle_movimiento.getColumna("ide_prcla").setAutoCompletar();
		tab_detalle_movimiento.getColumna("ide_prcla").setVisible(false);
		//tab_detalle_movimiento.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
		tab_detalle_movimiento.getColumna("ide_prpro").setVisible(false);
		tab_detalle_movimiento.getColumna("ide_cocac").setCombo(ser_contabilidad.servicioCatalogoCuentaCombo());
		tab_detalle_movimiento.getColumna("ide_cocac").setAutoCompletar();
		tab_detalle_movimiento.getColumna("activo_codem").setLectura(true);
		tab_detalle_movimiento.getColumna("activo_codem").setValorDefecto("true");
		tab_detalle_movimiento.getColumna("haber_codem").setMetodoChange("calcularTotal");			
		tab_detalle_movimiento.setColumnaSuma("haber_codem,debe_codem");			
		tab_detalle_movimiento.getColumna("debe_codem").setMetodoChange("calcularTotal");			
		tab_detalle_movimiento.getColumna("ide_gelua").setCombo("gen_lugar_aplica","ide_gelua","detalle_gelua","");

		tab_detalle_movimiento.getGrid().setColumns(4);
		tab_detalle_movimiento.dibujar();
		PanelTabla pat_detalle_movimiento=new PanelTabla();
		pat_detalle_movimiento.setPanelTabla(tab_detalle_movimiento);
		
		Division div_division =new Division();
		div_division.dividir2(pat_movimiento, pat_detalle_movimiento, "50%", "H");
		agregarComponente(div_division);
		
		//Inicio Dialogo
		dia_cabecera_asiento.setId("dia_cabecera_asiento");
		dia_cabecera_asiento.setTitle("DATOS DEL ASIENTO CONTABLE");
		dia_cabecera_asiento.setHeight("45%");
		dia_cabecera_asiento.setWidth("40%");
		
		//Inicio Dialogo DEBITO
		dia_cabecera_asiento_debito.setId("dia_cabecera_asiento_debito");
		dia_cabecera_asiento_debito.setTitle("DATOS DEL ASIENTO CONTABLE");
		dia_cabecera_asiento_debito.setHeight("45%");
		dia_cabecera_asiento_debito.setWidth("40%");		

		//Inicio Dialogo CREDITO
		dia_cabecera_asiento_credito.setId("dia_cabecera_asiento_credito");
		dia_cabecera_asiento_credito.setTitle("DATOS DEL ASIENTO CONTABLE");
		dia_cabecera_asiento_credito.setHeight("45%");
		dia_cabecera_asiento_credito.setWidth("40%");
		
		//inicio del grid
		Grid gri_datos_asiento = new Grid();
		gri_datos_asiento.setColumns(2);
		gri_datos_asiento.getChildren().add(new Etiqueta("Año Contable: "));
		com_anio.setId("com_anio");
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		gri_datos_asiento.getChildren().add(com_anio);
		gri_datos_asiento.getChildren().add(new Etiqueta("Mes: "));
		com_mes.setId("com_mes");
		com_mes.setCombo("select ide_gemes,detalle_gemes from gen_mes order by ide_gemes");
		gri_datos_asiento.getChildren().add(com_mes);
		gri_datos_asiento.getChildren().add(new Etiqueta("Tipo de Movimiento: "));
		com_tipo_movimiento.setId("com_tipo_movimiento");
		com_tipo_movimiento.setCombo("select ide_cotim, detalle_cotim from cont_tipo_movimiento");
		com_tipo_movimiento.setValue(utilitario.getVariable("p_tipo_mov_facturacion"));
		gri_datos_asiento.getChildren().add(com_tipo_movimiento);
		gri_datos_asiento.getChildren().add(new Etiqueta("Fecha Movimiento: "));
		cal_fecha_inicial.setId("cal_fecha_inicial");
		cal_fecha_inicial.setFechaActual();
		gri_datos_asiento.getChildren().add(cal_fecha_inicial);
		gri_datos_asiento.getChildren().add(new Etiqueta("Nro. Comprobante"));
		txt_comprobante.setId("txt_comprobante");
		gri_datos_asiento.getChildren().add(txt_comprobante);
		gri_datos_asiento.getChildren().add(new Etiqueta("Detalle Asiento"));
		txt_area_detalle_asiento.setId("txt_area_detalle_asiento");
		txt_area_detalle_asiento.setRendered(true);
		gri_datos_asiento.getChildren().add(txt_area_detalle_asiento);
		dia_cabecera_asiento.getBot_aceptar().setMetodo("contabilizar");
		dia_cabecera_asiento.setDialogo(gri_datos_asiento);
		agregarComponente(dia_cabecera_asiento);
		
		
		//inicio del grid debito
				Grid gri_datos_asiento_debito = new Grid();
				gri_datos_asiento_debito.setColumns(2);
				gri_datos_asiento_debito.getChildren().add(new Etiqueta("Año Contable: "));
				com_anio_debito.setId("com_anio_debito");
				com_anio_debito.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
				gri_datos_asiento_debito.getChildren().add(com_anio_debito);
				gri_datos_asiento_debito.getChildren().add(new Etiqueta("Mes: "));
				com_mes_debito.setId("com_mes_debito");
				com_mes_debito.setCombo("select ide_gemes,detalle_gemes from gen_mes order by ide_gemes");
				gri_datos_asiento_debito.getChildren().add(com_mes_debito);
				gri_datos_asiento_debito.getChildren().add(new Etiqueta("Tipo de Movimiento: "));
				com_tipo_movimiento_debito.setId("com_tipo_movimiento_debito");
				com_tipo_movimiento_debito.setCombo("select ide_cotim, detalle_cotim from cont_tipo_movimiento");
				com_tipo_movimiento_debito.setValue(utilitario.getVariable("p_tipo_mov_facturacion"));
				gri_datos_asiento_debito.getChildren().add(com_tipo_movimiento_debito);
				gri_datos_asiento_debito.getChildren().add(new Etiqueta("Fecha Movimiento: "));
				cal_fecha_inicial_debito.setId("cal_fecha_inicial_debito");
				cal_fecha_inicial_debito.setFechaActual();
				gri_datos_asiento_debito.getChildren().add(cal_fecha_inicial_debito);
				gri_datos_asiento_debito.getChildren().add(new Etiqueta("Nro. Comprobante"));
				txt_comprobante_debito.setId("txt_comprobante_debito");
				gri_datos_asiento_debito.getChildren().add(txt_comprobante_debito);
				gri_datos_asiento_debito.getChildren().add(new Etiqueta("Detalle Asiento"));
				txt_area_detalle_asiento_debito.setId("txt_area_detalle_asiento_debito");
				txt_area_detalle_asiento_debito.setRendered(true);
				gri_datos_asiento_debito.getChildren().add(txt_area_detalle_asiento_debito);
				dia_cabecera_asiento_debito.getBot_aceptar().setMetodo("contabilizarNotaDebito");
				dia_cabecera_asiento_debito.setDialogo(gri_datos_asiento_debito);
				agregarComponente(dia_cabecera_asiento_debito);
				
				//inicio del grid
				Grid gri_datos_asiento_credito = new Grid();
				gri_datos_asiento_credito.setColumns(2);
				gri_datos_asiento_credito.getChildren().add(new Etiqueta("Año Contable: "));
				com_anio_credito.setId("com_anio_credito");
				com_anio_credito.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
				gri_datos_asiento_credito.getChildren().add(com_anio_credito);
				gri_datos_asiento_credito.getChildren().add(new Etiqueta("Mes: "));
				com_mes_credito.setId("com_mes_credito");
				com_mes_credito.setCombo("select ide_gemes,detalle_gemes from gen_mes order by ide_gemes");
				gri_datos_asiento_credito.getChildren().add(com_mes_credito);
				gri_datos_asiento_credito.getChildren().add(new Etiqueta("Tipo de Movimiento: "));
				com_tipo_movimiento_credito.setId("com_tipo_movimiento_credito");
				com_tipo_movimiento_credito.setCombo("select ide_cotim, detalle_cotim from cont_tipo_movimiento");
				com_tipo_movimiento_credito.setValue(utilitario.getVariable("p_tipo_mov_facturacion"));
				gri_datos_asiento_credito.getChildren().add(com_tipo_movimiento_credito);
				gri_datos_asiento_credito.getChildren().add(new Etiqueta("Fecha Movimiento: "));
				cal_fecha_inicial_credito.setId("cal_fecha_inicial_credito");
				cal_fecha_inicial_credito.setFechaActual();
				gri_datos_asiento_credito.getChildren().add(cal_fecha_inicial_credito);
				gri_datos_asiento_credito.getChildren().add(new Etiqueta("Nro. Comprobante"));
				txt_comprobante_credito.setId("txt_comprobante_credito");
				gri_datos_asiento_credito.getChildren().add(txt_comprobante_credito);
				gri_datos_asiento_credito.getChildren().add(new Etiqueta("Detalle Asiento"));
				txt_area_detalle_asiento_credito.setId("txt_area_detalle_asiento_credito");
				txt_area_detalle_asiento_credito.setRendered(true);
				gri_datos_asiento_credito.getChildren().add(txt_area_detalle_asiento_credito);
				dia_cabecera_asiento_credito.getBot_aceptar().setMetodo("contabilizarNotaCredito");
				dia_cabecera_asiento_credito.setDialogo(gri_datos_asiento_credito);
				agregarComponente(dia_cabecera_asiento_credito);
								
		//Inicializamos los seleccion tabla
		inicializaFacturasContabilidad();
		inicializaNotaDebitoContabilidad();
		inicializaNotaCreditoContabilidad();

		inicializaNombreAsiento();
		inicializaNombreAsientoCredito();
		inicializaNombreAsientoDebito();
		inicializaCalendario();
		inicializaCalendarioCredito();
		inicializaCalendarioDebito();
		
	}
	
	public void inicializaFacturasContabilidad(){
		sel_factura_contabilidad.setId("sel_factura_contabilidad");
		sel_factura_contabilidad.setTitle("FACTURAS POR CONTABILIZAR");
		sel_factura_contabilidad.setSeleccionTabla(ser_Facturacion.getDatosFacturaContabilidad("1", "1900-01-01", "1900-01-01",str_tipo_asiento), "ide_fafac");
		sel_factura_contabilidad.getTab_seleccion().getColumna("ide_comov").setVisible(false);
		sel_factura_contabilidad.getTab_seleccion().getColumna("ide_coest").setVisible(false);
		sel_factura_contabilidad.getTab_seleccion().getColumna("ide_conac").setVisible(false);
		sel_factura_contabilidad.getTab_seleccion().getColumna("detalle_coest").setVisible(false);
		sel_factura_contabilidad.getTab_seleccion().getColumna("detalle_conac").setVisible(false);
		sel_factura_contabilidad.seleccionarTodas();
		sel_factura_contabilidad.getTab_seleccion().ejecutarSql();
		sel_factura_contabilidad.getBot_aceptar().setMetodo("contabilizar");
		agregarComponente(sel_factura_contabilidad);
	}
	public void inicializaNotaDebitoContabilidad(){
		sel_nota_debito.setId("sel_nota_debito");
		sel_nota_debito.setTitle("NOTAS DE DEBITO POR CONTABILIZAR");
		sel_nota_debito.setSeleccionTabla(ser_Facturacion.getDatosNotaDebitoContabilidad("1", "1900-01-01", "1900-01-01",str_tipo_asiento), "ide_fafac");
		sel_nota_debito.getTab_seleccion().getColumna("ide_comov").setVisible(false);
		sel_nota_debito.getTab_seleccion().getColumna("ide_coest").setVisible(false);
		sel_nota_debito.getTab_seleccion().getColumna("ide_conac").setVisible(false);
		sel_nota_debito.getTab_seleccion().getColumna("detalle_coest").setVisible(false);
		sel_nota_debito.getTab_seleccion().getColumna("detalle_conac").setVisible(false);
		sel_nota_debito.seleccionarTodas();
		sel_nota_debito.getTab_seleccion().ejecutarSql();
		sel_nota_debito.getBot_aceptar().setMetodo("contabilizarNotaDebito");
		agregarComponente(sel_nota_debito);
	}
	public void inicializaNotaCreditoContabilidad(){
		sel_nota_credito.setId("sel_nota_credito");
		sel_nota_credito.setTitle("NOTAS DE CREDITO POR CONTABILIZAR");
		sel_nota_credito.setSeleccionTabla(ser_Facturacion.getDatosNotaCreditoContabilidad("1", "1900-01-01", "1900-01-01",str_tipo_asiento), "ide_fafac");
		sel_nota_credito.getTab_seleccion().getColumna("ide_comov").setVisible(false);
		sel_nota_credito.getTab_seleccion().getColumna("ide_coest").setVisible(false);
		sel_nota_credito.getTab_seleccion().getColumna("ide_conac").setVisible(false);
		sel_nota_credito.getTab_seleccion().getColumna("detalle_coest").setVisible(false);
		sel_nota_credito.getTab_seleccion().getColumna("detalle_conac").setVisible(false);
		sel_nota_credito.seleccionarTodas();
		sel_nota_credito.getTab_seleccion().ejecutarSql();
		sel_nota_credito.getBot_aceptar().setMetodo("contabilizarNotaCredito");
		agregarComponente(sel_nota_credito);
	}
	public void inicializaNombreAsiento(){
		sel_nombre_asiento.setId("sel_nombre_asiento");
		sel_nombre_asiento.setTitle("Seleccione el tipo de asiento que desea contabilizar");
		sel_nombre_asiento.setSeleccionTabla(ser_contabilidad.getNombreAsientoContable("-1", "true,false"), "ide_conac");
		sel_nombre_asiento.setRadio();
		sel_nombre_asiento.getTab_seleccion().ejecutarSql();
		sel_nombre_asiento.getBot_aceptar().setMetodo("contabilizar");
		agregarComponente(sel_nombre_asiento);
	}
	public void inicializaNombreAsientoDebito(){
		sel_nombre_asiento_debito.setId("sel_nombre_asiento_debito");
		sel_nombre_asiento_debito.setTitle("Seleccione el tipo de asiento que desea contabilizar");
		sel_nombre_asiento_debito.setSeleccionTabla(ser_contabilidad.getNombreAsientoContable("-1", "true,false"), "ide_conac");
		sel_nombre_asiento_debito.setRadio();
		sel_nombre_asiento_debito.getTab_seleccion().ejecutarSql();
		sel_nombre_asiento_debito.getBot_aceptar().setMetodo("contabilizarNotaDebito");
		agregarComponente(sel_nombre_asiento_debito);
	}
	public void inicializaNombreAsientoCredito(){
		sel_nombre_asiento_credito.setId("sel_nombre_asiento_credito");
		sel_nombre_asiento_credito.setTitle("Seleccione el tipo de asiento que desea contabilizar");
		sel_nombre_asiento_credito.setSeleccionTabla(ser_contabilidad.getNombreAsientoContable("-1", "true,false"), "ide_conac");
		sel_nombre_asiento_credito.setRadio();
		sel_nombre_asiento_credito.getTab_seleccion().ejecutarSql();
		sel_nombre_asiento_credito.getBot_aceptar().setMetodo("contabilizarNotaCredito");
		agregarComponente(sel_nombre_asiento_credito);
	}
	public void inicializaCalendario(){
		sec_importar.setTitle("SELECCION DE FECHAS");
		sec_importar.setFooter("Seleccione un Rango de fechas para Buscar Facturas y Contabilizar");
		sec_importar.setFecha1(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sec_importar.setFecha2(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sec_importar.getBot_aceptar().setMetodo("contabilizar");
		agregarComponente(sec_importar);
	}
	public void inicializaCalendarioDebito(){
		sel_cal_debito.setTitle("SELECCION DE FECHAS");
		sel_cal_debito.setFooter("Seleccione un Rango de fechas para Buscar Notas de Debito y Contabilizar");
		sel_cal_debito.setFecha1(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_cal_debito.setFecha2(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_cal_debito.getBot_aceptar().setMetodo("contabilizarNotaDebito");
		agregarComponente(sel_cal_debito);
	}
	public void inicializaCalendarioCredito(){
		sel_cal_credito.setTitle("SELECCION DE FECHAS");
		sel_cal_credito.setFooter("Seleccione un Rango de fechas para Buscar Notas de Credito y Contabilizar");
		sel_cal_credito.setFecha1(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_cal_credito.setFecha2(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_cal_credito.getBot_aceptar().setMetodo("contabilizarNotaCredito");
		agregarComponente(sel_cal_credito);
	}
	public void contabilizar(){

		
		if(sel_nombre_asiento.isVisible()){
			if(sel_nombre_asiento.getValorSeleccionado()!=null){
			str_asiento_seleccionado=sel_nombre_asiento.getValorSeleccionado();	
			TablaGenerica tab_asiento_seleccionado= utilitario.consultar("select ide_conac,ide_gemod,detalle_conac,individual_conac,ide_coest from cont_nombre_asiento_contable where ide_conac ="+str_asiento_seleccionado);
			str_tipo_asiento = tab_asiento_seleccionado.getValor("ide_coest");
			str_individual = tab_asiento_seleccionado.getValor("individual_conac");
			str_ide_conac =tab_asiento_seleccionado.getValor("ide_conac");
			sel_nombre_asiento.cerrar();		
			sec_importar.dibujar();
			}
			else {
				utilitario.agregarMensajeError("Seleccion", "Seleccione un registro para continuar");
			}
		}
		
		else if (sec_importar.isVisible()){
			
			sec_importar.cerrar();
			// Indico que el asiento es de tipo emision
			if(str_tipo_asiento.equals(str_tipo_emision))
			{	
				str_parametro_tipo_asiento="1";
			}
			// Indico que el asiento es de tipo recaudacion
			if(str_tipo_asiento.equals(str_tipo_pagado))
			{	
				str_parametro_tipo_asiento="2";
			}			
			sel_factura_contabilidad.setTitle("FACTURAS POR CONTABILIZAR DEL "+sec_importar.getFecha1String()+" AL "+sec_importar.getFecha2String());
			sel_factura_contabilidad.setSql(ser_Facturacion.getDatosFacturaContabilidad(str_parametro_tipo_asiento, sec_importar.getFecha1String(), sec_importar.getFecha2String(),str_tipo_asiento));
			sel_factura_contabilidad.getTab_seleccion().getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
			sel_factura_contabilidad.getTab_seleccion().ejecutarSql();
			sel_factura_contabilidad.dibujar();
		}
		else if(sel_factura_contabilidad.isVisible()){
			//Pregunto si existen registros seleccionados para contabilizar.
			if(sel_factura_contabilidad.getSeleccionados() !=null){
				str_seleccionados_contabilidad=sel_factura_contabilidad.getSeleccionados();
				sel_factura_contabilidad.cerrar();
				dia_cabecera_asiento.dibujar();
				
				
			}
			else{
				utilitario.agregarMensajeError("Selección", "Seleccione un registro para continuar");
			}
			
		}
		else if (dia_cabecera_asiento.isVisible()){

			dia_cabecera_asiento.cerrar();
				// Indicamos si el asiento contable se va a generar indivual o agrupado, por ejemplo: individual=true, grupal=false
			if(str_individual.equals("true")){
				System.out.println("entre al true de individual ");
				// Tabla genreica si es asiento contable individual consulto factura por factutra
				TablaGenerica tab_factura= utilitario.consultar("select ide_fafac,ide_fadaf from fac_factura where ide_fafac in ("+str_seleccionados_contabilidad+")");
				//Recorro todas las facturas que voy a contabilizar
				for (int i=0; i < tab_factura.getTotalFilas();i++){
					
					// Consulto codigo maximo de la cabecera del asiento contle
					TablaGenerica tab_maximo =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_movimiento", "ide_comov"));
					String maximo_cont_movimiento=tab_maximo.getValor("codigo");
					// Inserto en la cabecera del asiento contable
					//insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov)
					str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
					str_insert_contabilidad +=" values ("+maximo_cont_movimiento+","+com_tipo_movimiento.getValue()+","+com_mes.getValue()+","+str_asiento_seleccionado+",5,"+com_anio.getValue()+","+p_modulo_factruracion+",'"+cal_fecha_inicial.getFecha()+"','"+txt_area_detalle_asiento.getValue()+"','"+txt_comprobante.getValue()+"',true)";
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad);
					System.out.println("inserto en contabilidad cabecera "+str_insert_contabilidad);

					// INSERTO EN LA TABLA DEL ASIENTO CONTABLE DE FACTURACION
					// Consulto codigo maximo de la cabecera del asiento factura
					TablaGenerica tab_maximo_factura =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_factura_asiento", "ide_cofaa"));
					String maximo_cont_factura=tab_maximo_factura.getValor("codigo");

					String str_inserta_asiento_factura="insert into cont_factura_asiento (ide_cofaa,ide_coest,ide_comov,ide_fafac,ide_conac,detalle_cofaa,activo_cofaa)"
							+" values ("+maximo_cont_factura+","+str_tipo_asiento+","+maximo_cont_movimiento+","+tab_factura.getValor(i,"ide_fafac")+","+str_ide_conac+",'COMPROBANTE NRO: "+txt_comprobante.getValue()+" FECHA MOVIMIENTO: "+cal_fecha_inicial.getFecha()+" "+txt_area_detalle_asiento.getValue()+"',true  )";
					utilitario.getConexion().ejecutarSql(str_inserta_asiento_factura);
					
					// Tabla generica devuelve los asientos para insertar en contabilidad.m primewro al debe
					TablaGenerica tab_inserta_detalle = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(tab_factura.getValor(i,"ide_fafac"),str_lugar_aplica_debe,str_ide_conac,"0"));
					for(int j=0;j<tab_inserta_detalle.getTotalFilas();j++){						
						TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
						String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

						// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
						str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac,ide_gelua)";						
						str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+","+tab_inserta_detalle.getValor(j, "valor_asiento")+",0,'"+tab_inserta_detalle.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle.getValor(j, "ide_cocac")+","+tab_inserta_detalle.getValor(j, "ide_gelua")+")"; 
						utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
					}
					
					// Tabla generica devuelve los asientos para insertar en contabilidad al haber
					TablaGenerica tab_inserta_detalle_haber = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(tab_factura.getValor(i,"ide_fafac"),str_lugar_aplica_haber,str_ide_conac,"0"));
					for(int j=0;j<tab_inserta_detalle_haber.getTotalFilas();j++){						
						TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
						String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

						// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
						str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac,ide_gelua)";						
						str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+",0,"+tab_inserta_detalle_haber.getValor(j, "valor_asiento")+",'"+tab_inserta_detalle_haber.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle_haber.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle_haber.getValor(j, "ide_cocac")+","+tab_inserta_detalle_haber.getValor(j, "ide_gelua")+")"; 
						utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
					}
				}
				
			}
			
			/// contabilizo si es asiento grupal
			else{
				

				
				// Consulto codigo maximo de la cabecera del asiento contle
				TablaGenerica tab_maximo =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_movimiento", "ide_comov"));
				String maximo_cont_movimiento=tab_maximo.getValor("codigo");
				// Inserto en la cabecera del asiento contable
				//insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov)
				str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
				str_insert_contabilidad +=" values ("+maximo_cont_movimiento+","+com_tipo_movimiento.getValue()+","+com_mes.getValue()+","+str_asiento_seleccionado+",5,"+com_anio.getValue()+","+p_modulo_factruracion+",'"+cal_fecha_inicial.getFecha()+"','"+txt_area_detalle_asiento.getValue()+"','"+txt_comprobante.getValue()+"',true)";
				utilitario.getConexion().ejecutarSql(str_insert_contabilidad);
				System.out.println("inserto en contabilidad cabecera "+str_insert_contabilidad);

				TablaGenerica tab_factura= utilitario.consultar("select ide_fafac,ide_fadaf from fac_factura where ide_fafac in ("+str_seleccionados_contabilidad+")");
				//Recorro todas las facturas que voy a contabilizar
				for (int i=0; i < tab_factura.getTotalFilas();i++){
					
					// INSERTO EN LA TABLA DEL ASIENTO CONTABLE DE FACTURACION
					// Consulto codigo maximo de la cabecera del asiento factura
					TablaGenerica tab_maximo_factura =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_factura_asiento", "ide_cofaa"));
					String maximo_cont_factura=tab_maximo_factura.getValor("codigo");

					String str_inserta_asiento_factura="insert into cont_factura_asiento (ide_cofaa,ide_coest,ide_comov,ide_fafac,ide_conac,detalle_cofaa,activo_cofaa)"
							+" values ("+maximo_cont_factura+","+str_tipo_asiento+","+maximo_cont_movimiento+","+tab_factura.getValor(i,"ide_fafac")+","+str_ide_conac+",'COMPROBANTE NRO: "+txt_comprobante.getValue()+" FECHA MOVIMIENTO: "+cal_fecha_inicial.getFecha()+" "+txt_area_detalle_asiento.getValue()+"',true  )";
					utilitario.getConexion().ejecutarSql(str_inserta_asiento_factura);

				}
				// Tabla generica devuelve los asientos para insertar en contabilidad.m primewro al debe
				TablaGenerica tab_inserta_detalle = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(str_seleccionados_contabilidad,str_lugar_aplica_debe,str_ide_conac,"1"));
				for(int j=0;j<tab_inserta_detalle.getTotalFilas();j++){		
					
					
					TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
					String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

					// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
					str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";						
					str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+","+tab_inserta_detalle.getValor(j, "valor_asiento")+",0,'"+tab_inserta_detalle.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle.getValor(j, "ide_cocac")+")"; 
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
				}
				
				// Tabla generica devuelve los asientos para insertar en contabilidad al haber
				TablaGenerica tab_inserta_detalle_haber = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(str_seleccionados_contabilidad,str_lugar_aplica_haber,str_ide_conac,"1"));
				for(int j=0;j<tab_inserta_detalle_haber.getTotalFilas();j++){						
					TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
					String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

					// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
					str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";						
					str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+",0,"+tab_inserta_detalle_haber.getValor(j, "valor_asiento")+",'"+tab_inserta_detalle_haber.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle_haber.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle_haber.getValor(j, "ide_cocac")+")"; 
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
				}

				
			}
			
			tab_movimiento.ejecutarSql();
			tab_detalle_movimiento.ejecutarValorForanea(tab_movimiento.getValorSeleccionado());
			utilitario.addUpdate("tab_movimiento");

		}
		else {
			sel_nombre_asiento.getTab_seleccion().setSql(ser_contabilidad.getNombreAsientoContable(p_modulo_factruracion, "true"));
			sel_nombre_asiento.getTab_seleccion().ejecutarSql();
			sel_nombre_asiento.dibujar();
		}
		
	}
	public void contabilizarNotaDebito (){
		if(sel_nombre_asiento_debito.isVisible()){
			if(sel_nombre_asiento_debito.getValorSeleccionado()!=null){
				str_asiento_seleccionado=sel_nombre_asiento_debito.getValorSeleccionado();	
				TablaGenerica tab_asiento_seleccionado= utilitario.consultar("select ide_conac,ide_gemod,detalle_conac,individual_conac,ide_coest from cont_nombre_asiento_contable where ide_conac ="+str_asiento_seleccionado);
				str_tipo_asiento = tab_asiento_seleccionado.getValor("ide_coest");
				str_individual = tab_asiento_seleccionado.getValor("individual_conac");
				str_ide_conac =tab_asiento_seleccionado.getValor("ide_conac");
				sel_nombre_asiento_debito.cerrar();		
				sel_cal_debito.dibujar();
				}
				else {
					utilitario.agregarMensajeError("Seleccion", "Seleccione un registro para continuar");
				}
			
		}
		else if (sel_cal_debito.isVisible()){
			
			sel_cal_debito.cerrar();
			// Indico que el asiento es de tipo emision
			if(str_tipo_asiento.equals(str_tipo_emision))
			{	
				str_parametro_tipo_asiento="1";
			}
			// Indico que el asiento es de tipo recaudacion
			if(str_tipo_asiento.equals(str_tipo_pagado))
			{	
				str_parametro_tipo_asiento="2";
			}			
			sel_nota_debito.setTitle("FACTURAS POR CONTABILIZAR DEL "+sel_cal_debito.getFecha1String()+" AL "+sel_cal_debito.getFecha2String());
			sel_nota_debito.setSql(ser_Facturacion.getDatosNotaDebitoContabilidad(str_parametro_tipo_asiento, sel_cal_debito.getFecha1String(), sel_cal_debito.getFecha2String(),str_tipo_asiento));
			sel_nota_debito.getTab_seleccion().getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
			sel_nota_debito.getTab_seleccion().ejecutarSql();
			sel_nota_debito.dibujar();
		}
		else if(sel_nota_debito.isVisible()){
			//Pregunto si existen registros seleccionados para contabilizar.
			if(sel_nota_debito.getSeleccionados() !=null){
				str_seleccionados_contabilidad=sel_nota_debito.getSeleccionados();
				sel_nota_debito.cerrar();
				dia_cabecera_asiento_debito.dibujar();
				
				
			}
			else{
				utilitario.agregarMensajeError("Selección", "Seleccione un registro para continuar");
			}
			
		}
		else if (dia_cabecera_asiento_debito.isVisible()){

			dia_cabecera_asiento_debito.cerrar();
				// Indicamos si el asiento contable se va a generar indivual o agrupado, por ejemplo: individual=true, grupal=false
			if(str_individual.equals("true")){
				System.out.println("entre al true de individual ");
				// Tabla genreica si es asiento contable individual consulto factura por factutra
				TablaGenerica tab_factura= utilitario.consultar("select ide_fafac,ide_fanod from fac_detalle_debito where ide_fafac in ("+str_seleccionados_contabilidad+")");
				//Recorro todas las facturas que voy a contabilizar
				for (int i=0; i < tab_factura.getTotalFilas();i++){
					
					// Consulto codigo maximo de la cabecera del asiento contle
					TablaGenerica tab_maximo =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_movimiento", "ide_comov"));
					String maximo_cont_movimiento=tab_maximo.getValor("codigo");
					// Inserto en la cabecera del asiento contable
					//insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov)
					str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
					str_insert_contabilidad +=" values ("+maximo_cont_movimiento+","+com_tipo_movimiento_debito.getValue()+","+com_mes_debito.getValue()+","+str_asiento_seleccionado+",5,"+com_anio_debito.getValue()+","+p_modulo_nota_debito+",'"+cal_fecha_inicial_debito.getFecha()+"','"+txt_area_detalle_asiento_debito.getValue()+"','"+txt_comprobante_debito.getValue()+"',true)";
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad);
					System.out.println("inserto en contabilidad cabecera "+str_insert_contabilidad);

					// INSERTO EN LA TABLA DEL ASIENTO CONTABLE DE FACTURACION
					// Consulto codigo maximo de la cabecera del asiento nota debito 
					TablaGenerica tab_maximo_nota_debito =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_nota_debito_asiento", "ide_conda"));
					String maximo_cont_nota_debito=tab_maximo_nota_debito.getValor("codigo");

					String str_inserta_nota_debito="insert into cont_nota_debito_asiento (ide_conda,ide_coest,ide_comov,ide_fanod,ide_conac,detalle_conda,activo_conda)"
							+" values ("+maximo_cont_nota_debito+","+str_tipo_asiento+","+maximo_cont_movimiento+","+tab_factura.getValor(i,"ide_fanod")+","+str_ide_conac+",'COMPROBANTE NRO: "+txt_comprobante_debito.getValue()+" FECHA MOVIMIENTO: "+cal_fecha_inicial_debito.getFecha()+" "+txt_area_detalle_asiento_debito.getValue()+"',true  )";
					utilitario.getConexion().ejecutarSql(str_inserta_nota_debito);
					
					// Tabla generica devuelve los asientos para insertar en contabilidad.m primewro al debe
					TablaGenerica tab_inserta_detalle = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidadNotaDebito(tab_factura.getValor(i,"ide_fafac"),str_lugar_aplica_debe,str_ide_conac,"0"));
					for(int j=0;j<tab_inserta_detalle.getTotalFilas();j++){						
						TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
						String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

						// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
						str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac,ide_gelua)";						
						str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+","+tab_inserta_detalle.getValor(j, "interes")+",0,'"+tab_inserta_detalle.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle.getValor(j, "ide_cocac")+","+tab_inserta_detalle.getValor(j, "ide_gelua")+")"; 
						utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
					}
					
					// Tabla generica devuelve los asientos para insertar en contabilidad al haber
					TablaGenerica tab_inserta_detalle_haber = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidadNotaDebito(tab_factura.getValor(i,"ide_fafac"),str_lugar_aplica_haber,str_ide_conac,"0"));
					for(int j=0;j<tab_inserta_detalle_haber.getTotalFilas();j++){						
						TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
						String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

						// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
						str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac,ide_gelua)";						
						str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+",0,"+tab_inserta_detalle_haber.getValor(j, "interes")+",'"+tab_inserta_detalle_haber.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle_haber.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle_haber.getValor(j, "ide_cocac")+","+tab_inserta_detalle_haber.getValor(j, "ide_gelua")+")"; 
						utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
					}
				}
				
			}
			
			/// contabilizo si es asiento grupal
			else{
				

				
				// Consulto codigo maximo de la cabecera del asiento contle
				TablaGenerica tab_maximo =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_movimiento", "ide_comov"));
				String maximo_cont_movimiento=tab_maximo.getValor("codigo");
				// Inserto en la cabecera del asiento contable
				//insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov)
				str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
				str_insert_contabilidad +=" values ("+maximo_cont_movimiento+","+com_tipo_movimiento_debito.getValue()+","+com_mes_debito.getValue()+","+str_asiento_seleccionado+",5,"+com_anio_debito.getValue()+","+p_modulo_nota_debito+",'"+cal_fecha_inicial_debito.getFecha()+"','"+txt_area_detalle_asiento_debito.getValue()+"','"+txt_comprobante_debito.getValue()+"',true)";
				utilitario.getConexion().ejecutarSql(str_insert_contabilidad);
				System.out.println("inserto en contabilidad cabecera "+str_insert_contabilidad);

				TablaGenerica tab_factura= utilitario.consultar("select ide_fafac,ide_fanod from fac_detalle_debito where ide_fafac in ("+str_seleccionados_contabilidad+")");
				//Recorro todas las facturas que voy a contabilizar
				for (int i=0; i < tab_factura.getTotalFilas();i++){
					
					
					// Consulto codigo maximo de la cabecera del asiento nota debito 
					TablaGenerica tab_maximo_nota_debito =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_nota_debito_asiento", "ide_conda"));
					String maximo_cont_nota_debito=tab_maximo_nota_debito.getValor("codigo");

					String str_inserta_nota_debito="insert into cont_nota_debito_asiento (ide_conda,ide_coest,ide_comov,ide_fanod,ide_conac,detalle_conda,activo_conda)"
							+" values ("+maximo_cont_nota_debito+","+str_tipo_asiento+","+maximo_cont_movimiento+","+tab_factura.getValor(i,"ide_fanod")+","+str_ide_conac+",'COMPROBANTE NRO: "+txt_comprobante_debito.getValue()+" FECHA MOVIMIENTO: "+cal_fecha_inicial_debito.getFecha()+" "+txt_area_detalle_asiento_debito.getValue()+"',true  )";
					utilitario.getConexion().ejecutarSql(str_inserta_nota_debito);

				}
				// Tabla generica devuelve los asientos para insertar en contabilidad.m primewro al debe +
				TablaGenerica tab_inserta_detalle = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidadNotaDebito(str_seleccionados_contabilidad,str_lugar_aplica_debe,str_ide_conac,"1"));
				for(int j=0;j<tab_inserta_detalle.getTotalFilas();j++){		
					
					
					TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
					String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

					// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
					str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";						
					str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+","+tab_inserta_detalle.getValor(j, "interes")+",0,'"+tab_inserta_detalle.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle.getValor(j, "ide_cocac")+")"; 
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
				}
				
				// Tabla generica devuelve los asientos para insertar en contabilidad al haber
				TablaGenerica tab_inserta_detalle_haber = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidadNotaDebito(str_seleccionados_contabilidad,str_lugar_aplica_haber,str_ide_conac,"1"));
				for(int j=0;j<tab_inserta_detalle_haber.getTotalFilas();j++){						
					TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
					String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

					// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
					str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";						
					str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+",0,"+tab_inserta_detalle_haber.getValor(j, "interes")+",'"+tab_inserta_detalle_haber.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle_haber.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle_haber.getValor(j, "ide_cocac")+")"; 
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
				}

				
			}
			
			tab_movimiento.ejecutarSql();
			tab_detalle_movimiento.ejecutarValorForanea(tab_movimiento.getValorSeleccionado());
			utilitario.addUpdate("tab_movimiento");

		}

		else {
			sel_nombre_asiento_debito.getTab_seleccion().setSql(ser_contabilidad.getNombreAsientoContable(p_modulo_nota_debito, "true"));
			sel_nombre_asiento_debito.getTab_seleccion().ejecutarSql();
			sel_nombre_asiento_debito.dibujar();
		}

	}
	public void contabilizarNotaCredito (){
		if(sel_nombre_asiento_credito.isVisible()){
			if(sel_nombre_asiento_credito.getValorSeleccionado()!=null){
				str_asiento_seleccionado=sel_nombre_asiento_credito.getValorSeleccionado();	
				TablaGenerica tab_asiento_seleccionado= utilitario.consultar("select ide_conac,ide_gemod,detalle_conac,individual_conac,ide_coest from cont_nombre_asiento_contable where ide_conac ="+str_asiento_seleccionado);
				str_tipo_asiento = tab_asiento_seleccionado.getValor("ide_coest");
				str_individual = tab_asiento_seleccionado.getValor("individual_conac");
				str_ide_conac =tab_asiento_seleccionado.getValor("ide_conac");
				sel_nombre_asiento_credito.cerrar();		
				sel_cal_credito.dibujar();
				}
				else {
					utilitario.agregarMensajeError("Seleccion", "Seleccione un registro para continuar");
				}
			
		}
		else if (sel_cal_credito.isVisible()){
			
			sel_cal_credito.cerrar();
			// Indico que el asiento es de tipo emision
			if(str_tipo_asiento.equals(str_tipo_emision))
			{	
				str_parametro_tipo_asiento="1";
			}
			// Indico que el asiento es de tipo recaudacion
			if(str_tipo_asiento.equals(str_tipo_pagado))
			{	
				str_parametro_tipo_asiento="2";
			}			
			sel_nota_credito.setTitle("FACTURAS POR CONTABILIZAR DEL "+sel_cal_credito.getFecha1String()+" AL "+sel_cal_credito.getFecha2String());
			sel_nota_credito.setSql(ser_Facturacion.getDatosNotaCreditoContabilidad(str_parametro_tipo_asiento, sel_cal_credito.getFecha1String(), sel_cal_credito.getFecha2String(),str_tipo_asiento));
			sel_nota_credito.getTab_seleccion().getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
			sel_nota_credito.getTab_seleccion().ejecutarSql();
			sel_nota_credito.dibujar();
		}
		else if(sel_nota_credito.isVisible()){
			//Pregunto si existen registros seleccionados para contabilizar.
			if(sel_nota_credito.getSeleccionados() !=null){
				str_seleccionados_contabilidad=sel_nota_credito.getSeleccionados();
				sel_nota_credito.cerrar();
				dia_cabecera_asiento_credito.dibujar();
				
				
			}
			else{
				utilitario.agregarMensajeError("Selección", "Seleccione un registro para continuar");
			}
			
		}
		else if (dia_cabecera_asiento_credito.isVisible()){

			dia_cabecera_asiento_credito.cerrar();
				// Indicamos si el asiento contable se va a generar indivual o agrupado, por ejemplo: individual=true, grupal=false
			if(str_individual.equals("true")){
				System.out.println("entre al true de individual ");
				// Tabla genreica si es asiento contable individual consulto factura por factutra
				TablaGenerica tab_factura= utilitario.consultar("select ide_fafac,ide_fadaf from fac_factura where ide_fafac in ("+str_seleccionados_contabilidad+")");
				//Recorro todas las facturas que voy a contabilizar
				for (int i=0; i < tab_factura.getTotalFilas();i++){
					
					// Consulto codigo maximo de la cabecera del asiento contle
					TablaGenerica tab_maximo =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_movimiento", "ide_comov"));
					String maximo_cont_movimiento=tab_maximo.getValor("codigo");
					// Inserto en la cabecera del asiento contable
					//insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov)
					str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
					str_insert_contabilidad +=" values ("+maximo_cont_movimiento+","+com_tipo_movimiento_credito.getValue()+","+com_mes_credito.getValue()+","+str_asiento_seleccionado+",5,"+com_anio_credito.getValue()+","+p_modulo_nota_credito+",'"+cal_fecha_inicial_credito.getFecha()+"','"+txt_area_detalle_asiento_credito.getValue()+"','"+txt_comprobante_credito.getValue()+"',true)";
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad);
					System.out.println("inserto en contabilidad cabecera "+str_insert_contabilidad);
					
					
					// INSERTO EN LA TABLA DEL ASIENTO CONTABLE DE FACTURACION
					// Consulto codigo maximo de la cabecera del asiento factura
					TablaGenerica tab_maximo_factura =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_factura_asiento", "ide_cofaa"));
					String maximo_cont_factura=tab_maximo_factura.getValor("codigo");

					String str_inserta_asiento_factura="insert into cont_factura_asiento (ide_cofaa,ide_coest,ide_comov,ide_fafac,ide_conac,detalle_cofaa,activo_cofaa)"
							+" values ("+maximo_cont_factura+","+str_tipo_asiento+","+maximo_cont_movimiento+","+tab_factura.getValor(i,"ide_fafac")+","+str_ide_conac+",'COMPROBANTE NRO: "+txt_comprobante_credito.getValue()+" FECHA MOVIMIENTO: "+cal_fecha_inicial_credito.getFecha()+" "+txt_area_detalle_asiento_credito.getValue()+"',true  )";
					utilitario.getConexion().ejecutarSql(str_inserta_asiento_factura);
					
					//actualizo nota de credito
					String update_nota_credito="update fac_nota_credito set ide_comov="+maximo_cont_movimiento+" where ide_fafac="+tab_factura.getValor(i,"ide_fafac");
					utilitario.getConexion().ejecutarSql(update_nota_credito);
					
					// Tabla generica devuelve los asientos para insertar en contabilidad.m primewro al debe
					TablaGenerica tab_inserta_detalle = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(tab_factura.getValor(i,"ide_fafac"),str_lugar_aplica_debe,str_ide_conac,"0"));
					for(int j=0;j<tab_inserta_detalle.getTotalFilas();j++){						
						TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
						String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

						// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
						str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac,ide_gelua)";						
						str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+","+tab_inserta_detalle.getValor(j, "valor_asiento")+",0,'"+tab_inserta_detalle.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle.getValor(j, "ide_cocac")+","+tab_inserta_detalle.getValor(j, "ide_gelua")+")"; 
						utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
					}
					
					// Tabla generica devuelve los asientos para insertar en contabilidad al haber
					TablaGenerica tab_inserta_detalle_haber = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(tab_factura.getValor(i,"ide_fafac"),str_lugar_aplica_haber,str_ide_conac,"0"));
					for(int j=0;j<tab_inserta_detalle_haber.getTotalFilas();j++){						
						TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
						String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

						// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
						str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac,ide_gelua)";						
						str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+",0,"+tab_inserta_detalle_haber.getValor(j, "valor_asiento")+",'"+tab_inserta_detalle_haber.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle_haber.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle_haber.getValor(j, "ide_cocac")+","+tab_inserta_detalle_haber.getValor(j, "ide_gelua")+")"; 
						utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
					}
				}
				
			}
			
			/// contabilizo si es asiento grupal
			else{
				

				
				// Consulto codigo maximo de la cabecera del asiento contle
				TablaGenerica tab_maximo =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_movimiento", "ide_comov"));
				String maximo_cont_movimiento=tab_maximo.getValor("codigo");
				// Inserto en la cabecera del asiento contable
				//insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov)
				str_insert_contabilidad="insert into cont_movimiento (ide_comov,ide_cotim,ide_gemes,ide_conac,ide_cotia,ide_geani,ide_gemod,mov_fecha_comov,detalle_comov,nro_comprobante_comov,activo_comov) ";
				str_insert_contabilidad +=" values ("+maximo_cont_movimiento+","+com_tipo_movimiento_credito.getValue()+","+com_mes_credito.getValue()+","+str_asiento_seleccionado+",5,"+com_anio_credito.getValue()+","+p_modulo_nota_credito+",'"+cal_fecha_inicial_credito.getFecha()+"','"+txt_area_detalle_asiento_debito.getValue()+"','"+txt_comprobante_credito.getValue()+"',true)";
				utilitario.getConexion().ejecutarSql(str_insert_contabilidad);
				System.out.println("inserto en contabilidad cabecera "+str_insert_contabilidad);

				TablaGenerica tab_factura= utilitario.consultar("select ide_fafac,ide_fadaf from fac_factura where ide_fafac in ("+str_seleccionados_contabilidad+")");
				//Recorro todas las facturas que voy a contabilizar
				for (int i=0; i < tab_factura.getTotalFilas();i++){
					
					// INSERTO EN LA TABLA DEL ASIENTO CONTABLE DE FACTURACION
					// Consulto codigo maximo de la cabecera del asiento factura
					TablaGenerica tab_maximo_factura =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_factura_asiento", "ide_cofaa"));
					String maximo_cont_factura=tab_maximo_factura.getValor("codigo");

					String str_inserta_asiento_factura="insert into cont_factura_asiento (ide_cofaa,ide_coest,ide_comov,ide_fafac,ide_conac,detalle_cofaa,activo_cofaa)"
							+" values ("+maximo_cont_factura+","+str_tipo_asiento+","+maximo_cont_movimiento+","+tab_factura.getValor(i,"ide_fafac")+","+str_ide_conac+",'COMPROBANTE NRO: "+txt_comprobante_credito.getValue()+" FECHA MOVIMIENTO: "+cal_fecha_inicial_credito.getFecha()+" "+txt_area_detalle_asiento_credito.getValue()+"',true  )";
					utilitario.getConexion().ejecutarSql(str_inserta_asiento_factura);

					String update_nota_credito="update fac_nota_credito set ide_comov="+maximo_cont_movimiento+" where ide_fafac="+tab_factura.getValor(i,"ide_fafac");
					utilitario.getConexion().ejecutarSql(update_nota_credito);

				}
				// Tabla generica devuelve los asientos para insertar en contabilidad.m primewro al debe
				TablaGenerica tab_inserta_detalle = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(str_seleccionados_contabilidad,str_lugar_aplica_debe,str_ide_conac,"1"));
				for(int j=0;j<tab_inserta_detalle.getTotalFilas();j++){		
					
					
					TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
					String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

					// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
					str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";						
					str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+","+tab_inserta_detalle.getValor(j, "valor_asiento")+",0,'"+tab_inserta_detalle.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle.getValor(j, "ide_cocac")+")"; 
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
				}
				
				// Tabla generica devuelve los asientos para insertar en contabilidad al haber
				TablaGenerica tab_inserta_detalle_haber = utilitario.consultar(ser_Facturacion.getFacturasInsertaContabilidad(str_seleccionados_contabilidad,str_lugar_aplica_haber,str_ide_conac,"1"));
				for(int j=0;j<tab_inserta_detalle_haber.getTotalFilas();j++){						
					TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
					String maximo_detalle_movimiento=tab_codigo_detalle.getValor("codigo");

					// inserto el detalle de los asientos insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)
					str_insert_contabilidad_detalle="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,detalle_codem,activo_codem,ide_cocac)";						
					str_insert_contabilidad_detalle +=" values ("+maximo_detalle_movimiento+","+maximo_cont_movimiento+",0,"+tab_inserta_detalle_haber.getValor(j, "valor_asiento")+",'"+tab_inserta_detalle_haber.getValor(j, "detalle_bogrm")+" "+tab_inserta_detalle_haber.getValor(j, "secuencial_fafac")+"',true,"+tab_inserta_detalle_haber.getValor(j, "ide_cocac")+")"; 
					utilitario.getConexion().ejecutarSql(str_insert_contabilidad_detalle);
				}

				
			}
			
			utilitario.addUpdate("tab_movimiento");
			//tab_detalle_movimiento.ejecutarValorForanea(tab_movimiento.getValorSeleccionado());

		}

		else {
			sel_nombre_asiento_credito.getTab_seleccion().setSql(ser_contabilidad.getNombreAsientoContable(p_modulo_nota_credito, "true"));
			sel_nombre_asiento_credito.getTab_seleccion().ejecutarSql();
			sel_nombre_asiento_credito.dibujar();
		}

	}
	////metodo año
	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_movimiento.setCondicion("ide_geani="+com_anio.getValue());
			tab_movimiento.ejecutarSql();
			tab_detalle_movimiento.ejecutarValorForanea(tab_movimiento.getValorSeleccionado());


		}
		else{
			tab_movimiento.setCondicion("ide_geani=-1");
			tab_movimiento.ejecutarSql();
		}
	}
	
	///sacar valores
	
	public void calcularTotal(AjaxBehaviorEvent evt){
		tab_detalle_movimiento.modificar(evt);
		tab_detalle_movimiento.sumarColumnas();
		utilitario.addUpdate("tab_detalle_movimiento");
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
		if(tab_movimiento.getValor("activo_comov").equals("true")){
			utilitario.agregarMensajeInfo("Registro no Editable", "El asiento se encuentra mayorizado no se puede modificar");
		}
		if (tab_movimiento.guardar()){
			tab_detalle_movimiento.guardar();

		}
		//tab_detalle_movimiento.sumarColumnas();
		//utilitario.addUpdate("tab_detalle_movimiento");
		guardarPantalla();

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


	public SeleccionTabla getSel_factura_contabilidad() {
		return sel_factura_contabilidad;
	}


	public void setSel_factura_contabilidad(SeleccionTabla sel_factura_contabilidad) {
		this.sel_factura_contabilidad = sel_factura_contabilidad;
	}


	public SeleccionTabla getSel_factura_consulta() {
		return sel_factura_consulta;
	}


	public void setSel_factura_consulta(SeleccionTabla sel_factura_consulta) {
		this.sel_factura_consulta = sel_factura_consulta;
	}

	public SeleccionTabla getSel_nombre_asiento() {
		return sel_nombre_asiento;
	}

	public void setSel_nombre_asiento(SeleccionTabla sel_nombre_asiento) {
		this.sel_nombre_asiento = sel_nombre_asiento;
	}

	public SeleccionCalendario getSec_importar() {
		return sec_importar;
	}

	public void setSec_importar(SeleccionCalendario sec_importar) {
		this.sec_importar = sec_importar;
	}

	public Dialogo getDia_cabecera_asiento() {
		return dia_cabecera_asiento;
	}

	public void setDia_cabecera_asiento(Dialogo dia_cabecera_asiento) {
		this.dia_cabecera_asiento = dia_cabecera_asiento;
	}

	public SeleccionTabla getSel_nota_debito() {
		return sel_nota_debito;
	}

	public void setSel_nota_debito(SeleccionTabla sel_nota_debito) {
		this.sel_nota_debito = sel_nota_debito;
	}

	public SeleccionTabla getSel_nota_credito() {
		return sel_nota_credito;
	}

	public void setSel_nota_credito(SeleccionTabla sel_nota_credito) {
		this.sel_nota_credito = sel_nota_credito;
	}

	public SeleccionTabla getSel_nombre_asiento_debito() {
		return sel_nombre_asiento_debito;
	}

	public void setSel_nombre_asiento_debito(
			SeleccionTabla sel_nombre_asiento_debito) {
		this.sel_nombre_asiento_debito = sel_nombre_asiento_debito;
	}

	public SeleccionTabla getSel_nombre_asiento_credito() {
		return sel_nombre_asiento_credito;
	}

	public void setSel_nombre_asiento_credito(
			SeleccionTabla sel_nombre_asiento_credito) {
		this.sel_nombre_asiento_credito = sel_nombre_asiento_credito;
	}

	public SeleccionCalendario getSel_cal_debito() {
		return sel_cal_debito;
	}

	public void setSel_cal_debito(SeleccionCalendario sel_cal_debito) {
		this.sel_cal_debito = sel_cal_debito;
	}

	public SeleccionCalendario getSel_cal_credito() {
		return sel_cal_credito;
	}

	public void setSel_cal_credito(SeleccionCalendario sel_cal_credito) {
		this.sel_cal_credito = sel_cal_credito;
	}

	public Dialogo getDia_cabecera_asiento_credito() {
		return dia_cabecera_asiento_credito;
	}

	public void setDia_cabecera_asiento_credito(Dialogo dia_cabecera_asiento_credito) {
		this.dia_cabecera_asiento_credito = dia_cabecera_asiento_credito;
	}

	public Dialogo getDia_cabecera_asiento_debito() {
		return dia_cabecera_asiento_debito;
	}

	public void setDia_cabecera_asiento_debito(Dialogo dia_cabecera_asiento_debito) {
		this.dia_cabecera_asiento_debito = dia_cabecera_asiento_debito;
	}
	

}
