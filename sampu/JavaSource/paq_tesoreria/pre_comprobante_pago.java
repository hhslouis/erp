package paq_tesoreria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.TablaGrid;
import framework.componentes.Tabulador;
import paq_adquisicion.ejb.ServicioAdquisicion;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import paq_tesoreria.ejb.ServicioTesoreria;

public class pre_comprobante_pago extends Pantalla {

    private Tabla tab_comprobante = new Tabla();
    private Tabla tab_detalle_movimiento =new Tabla();
    private Tabla tab_retencion =new Tabla();
    private Tabla tab_detalle_retencion=new Tabla();
    private Tabla tab_pre_mensual=new Tabla();    
    private SeleccionTabla set_solicitud=new SeleccionTabla();
    private SeleccionTabla set_tramite=new SeleccionTabla();
    private SeleccionTabla set_contabilizar=new SeleccionTabla();
    private SeleccionTabla set_anticipo_empleado=new SeleccionTabla();
    private Combo com_tipo_concepto = new Combo();
    private Calendario cal_fecha_inicial = new Calendario();
    private SeleccionTabla set_impuesto=new SeleccionTabla();
    private SeleccionTabla set_retencion=new SeleccionTabla();
    private Dialogo dia_anticipo=new Dialogo();
	private Confirmar con_guardar=new Confirmar();
	private Reporte rep_reporte=new Reporte();
	private SeleccionFormatoReporte sef_reporte=new SeleccionFormatoReporte();
	private Map p_parametros=new HashMap();

	public static String par_impuesto_renta;
	public static String par_impuesto_iva;
	public static String par_movimiento_devengado;
	public static String par_debe;
	public static String par_haber;
	public static String par_valor_devengar;
	public static String par_lugar_aplica_banco;
	public static String par_cuenta_banco;
	public static String par_lugar_aplica_iva;
	public static String par_cuenta_iva;
	public static String par_lugar_aplica_iva_cierra;
	public static String par_cuenta_iva_cierra;
	public static String par_sec_comprobante_pago;
	public static String par_anio_vigente;
	public static String par_asiento_anticipo;
	public static String par_estado_devengado;

	public static double par_iva;

 
    
    @EJB
    private ServicioAdquisicion ser_Adquisicion=(ServicioAdquisicion) utilitario.instanciarEJB(ServicioAdquisicion.class);
    @EJB
    private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
    @EJB
    private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
    @EJB
    private ServicioPresupuesto ser_Presupuesto = (ServicioPresupuesto) utilitario.instanciarEJB(ServicioPresupuesto.class);
    @EJB
    private ServicioBodega ser_Bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
    @EJB
    private ServicioTesoreria ser_Tesoreria = (ServicioTesoreria) utilitario.instanciarEJB(ServicioTesoreria.class);
    @EJB
    private ServicioContabilidad ser_contabilidad = (ServicioContabilidad) utilitario.instanciarEJB(ServicioContabilidad.class);


    
    public pre_comprobante_pago (){
    	
    	//parametros
    	par_impuesto_iva=utilitario.getVariable("p_tes_impuesto_iva");
		par_impuesto_renta=utilitario.getVariable("p_tes_impuesto_renta");
		par_iva=Double.parseDouble(utilitario.getVariable("p_valor_iva"));
		par_movimiento_devengado=utilitario.getVariable("p_mov_devengado");
		par_debe=utilitario.getVariable("p_gen_lugar_aplica_debe");
		par_haber=utilitario.getVariable("p_gen_lugar_aplica_haber");
		par_valor_devengar=utilitario.getVariable("p_valor_devenga");
		par_lugar_aplica_banco=utilitario.getVariable("p_lugar_ejecuta_banco");
		par_cuenta_banco=utilitario.getVariable("p_cuenta_banco");
		par_lugar_aplica_iva=utilitario.getVariable("p_lugar_ejecuta_iva");
		par_cuenta_iva=utilitario.getVariable("p_cuenta_iva");
		par_lugar_aplica_iva_cierra=utilitario.getVariable("p_lugar_ejecuta_iva_cierra");
		par_cuenta_iva_cierra=utilitario.getVariable("p_cuenta_iva_cierra");
		par_sec_comprobante_pago=utilitario.getVariable("p_modulo_comprobante_pago");
		par_anio_vigente=utilitario.getVariable("p_anio_vigente");
		par_asiento_anticipo=utilitario.getVariable("p_asiento_anticipo");
		par_estado_devengado=utilitario.getVariable("p_estado_devengado");

    	///tabuladores
    	 Tabulador tab_tabulador = new Tabulador();
         tab_tabulador.setId("tab_tabulador");
         
         
        bar_botones.agregarReporte();
 		rep_reporte.setId("rep_reporte");
 		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
 		agregarComponente(rep_reporte);

 		sef_reporte.setId("sef_reporte");
 		agregarComponente(sef_reporte);

         
         tab_comprobante.setId("tab_comprobante");
         tab_comprobante.setHeader("COMPROBANTE DE PAGO");
         tab_comprobante.setTabla("tes_comprobante_pago", "ide_tecpo",  1);
         tab_comprobante.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest","detalle_coest","");
         tab_comprobante.setCampoOrden("ide_tecpo desc");
         tab_comprobante.getColumna("IDE_PRTRA").setCombo(ser_Presupuesto.getTramite("true"));
         tab_comprobante.getColumna("IDE_PRTRA").setAutoCompletar();
         tab_comprobante.getColumna("IDE_PRTRA").setLectura(true);
         tab_comprobante.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest","detalle_coest","");
         tab_comprobante.getColumna("IDE_TEPRO").setCombo(ser_Bodega.getProveedor("true,false"));
         tab_comprobante.getColumna("IDE_TEPRO").setAutoCompletar();
         tab_comprobante.getColumna("IDE_GEDIP").setCombo(ser_gestion.getSqlDivisionPoliticaCiudad());
         tab_comprobante.getColumna("IDE_GEDIP").setValorDefecto("57");
         tab_comprobante.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
         tab_comprobante.getColumna("IDE_GEEDP").setAutoCompletar();
         tab_comprobante.getColumna("IDE_ADSOC").setCombo(ser_Adquisicion.getSolicitudCompra("true"));
         tab_comprobante.getColumna("IDE_ADSOC").setAutoCompletar();
         tab_comprobante.getColumna("IDE_ADSOC").setLectura(true);
         tab_comprobante.getColumna("ide_tetic").setCombo("tes_tipo_concepto", "ide_tetic", "detalle_tetic,fecha_pago_tetic", "");
         tab_comprobante.getColumna("fecha_tecpo").setRequerida(true);
         tab_comprobante.getColumna("comprobante_egreso_tecpo").setRequerida(true);
         tab_comprobante.getColumna("detalle_tecpo").setRequerida(true);
         tab_comprobante.getColumna("activo_tecpo").setValorDefecto("true");
         tab_comprobante.getColumna("activo_tecpo").setLectura(true);
         tab_comprobante.getColumna("fecha_tecpo").setRequerida(true);
         tab_comprobante.getColumna("fecha_tecpo").setValorDefecto(utilitario.getFechaActual()); //
         tab_comprobante.getColumna("subtotal_tecpo").setMetodoChange("valorPago");
         tab_comprobante.getColumna("valor_compra_tecpo").setEtiqueta();
         tab_comprobante.getColumna("valor_compra_tecpo").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
         tab_comprobante.getColumna("valor_iva_tecpo").setMetodoChange("valorPago");
         tab_comprobante.getColumna("valor_retencion_tecpo").setMetodoChange("valorPago");
         tab_comprobante.getColumna("valor_pago_tecpo").setEtiqueta();
         tab_comprobante.getColumna("valor_pago_tecpo").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
         
         tab_comprobante.setTipoFormulario(true);
         tab_comprobante.getGrid().setColumns(4);
         tab_comprobante.dibujar();
         PanelTabla pat_comprobante=new PanelTabla();
         pat_comprobante.setPanelTabla(tab_comprobante);
         
         ////detalle movimiento
 
         tab_detalle_movimiento.setId("tab_detalle_movimiento");
         //tab_detalle_movimiento.setIdCompleto("tab_tabulador:tab_detalle_movimiento");
         tab_detalle_movimiento.setHeader("DETALLE DE MOVIMIENTO");
         tab_detalle_movimiento.setTabla("cont_detalle_movimiento", "ide_codem", 2);
          //filtra por asiento contable cuando no tiene relacion a tes_comprovante_pago
         tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));    
         tab_detalle_movimiento.getColumna("ide_comov").setVisible(false);
         tab_detalle_movimiento.getColumna("detalle_codem").setVisible(false);
         tab_detalle_movimiento.getColumna("ide_prcla").setCombo(ser_Presupuesto.getCatalogoPresupuestario("true,false"));
         tab_detalle_movimiento.getColumna("ide_prcla").setAutoCompletar();
         tab_detalle_movimiento.getColumna("ide_prcla").setVisible(false);
         tab_detalle_movimiento.getColumna("ide_prpro").setVisible(false);
         tab_detalle_movimiento.getColumna("ide_tetic").setVisible(false);
         tab_detalle_movimiento.getColumna("ide_tepro").setVisible(false);
         tab_detalle_movimiento.getColumna("ide_gelua").setCombo("gen_lugar_aplica", "ide_gelua", "detalle_gelua", "");
        
         tab_detalle_movimiento.getColumna("conciliado_codem").setVisible(false);
         tab_detalle_movimiento.getColumna("transferencia_codem").setVisible(false);
         tab_detalle_movimiento.getColumna("fecha_concilia_codem").setVisible(false);
         tab_detalle_movimiento.getColumna("documento_codem").setVisible(false);

         tab_detalle_movimiento.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
         tab_detalle_movimiento.getColumna("ide_cocac").setCombo(ser_contabilidad.servicioCatalogoCuentaCombo());
         tab_detalle_movimiento.getColumna("ide_cocac").setAutoCompletar();

         tab_detalle_movimiento.getColumna("activo_codem").setLectura(true);
         tab_detalle_movimiento.getColumna("activo_codem").setValorDefecto("true");
         tab_detalle_movimiento.getColumna("haber_codem").setMetodoChange("calcularTotal");            
         tab_detalle_movimiento.setColumnaSuma("haber_codem,debe_codem");            
         tab_detalle_movimiento.getColumna("debe_codem").setMetodoChange("calcularTotal");            
         tab_detalle_movimiento.getGrid().setColumns(4);
         tab_detalle_movimiento.dibujar();
         PanelTabla pat_detalle_movimiento=new PanelTabla();
         pat_detalle_movimiento.setPanelTabla(tab_detalle_movimiento);
         
         
         tab_pre_mensual.setId("tab_pre_mensual");
         tab_pre_mensual.setTabla("pre_mensual", "ide_prmen", 3);
         tab_pre_mensual.dibujar();
         PanelTabla pat_pre_mensual= new PanelTabla();
         pat_pre_mensual.setPanelTabla(tab_pre_mensual);
         
         
         Division div_division =new Division();
         div_division.dividir2(pat_comprobante, pat_detalle_movimiento, "50%", "H");
         agregarComponente(div_division);
       
         set_solicitud.setId("set_solicitud");
         set_solicitud.setSeleccionTabla(ser_Adquisicion.getSolicitudCompra("true"),"ide_adsoc");
         set_solicitud.setTitle("SELECCION UNA SOLICITUD DE COMPRA");        
         set_solicitud.getBot_aceptar().setMetodo("aceptarSolicitudCompra");
         set_solicitud.setRadio();
         agregarComponente(set_solicitud);
         ///certificacion presupuestaria
         Boton bot_busca=new Boton();
         bot_busca.setIcon("ui-icon-person");
         bot_busca.setValue("Buscar Compromiso Presupuestario");
         bot_busca.setMetodo("importarCertificacionPresupuestaria");
         bar_botones.agregarBoton(bot_busca);

         set_tramite.setId("set_tramite");
         set_tramite.setSeleccionTabla(ser_Presupuesto.getTramite("true"),"ide_prtra");
         set_tramite.setTitle("SELECCION UN COMPROMISO PRESUPUESTARIO");  
         set_tramite.getTab_seleccion().getColumna("nro_compromiso").setFiltro(true);
         set_tramite.getTab_seleccion().getColumna("numero_oficio_prtra").setFiltro(true);
         set_tramite.getTab_seleccion().getColumna("observaciones_prtra").setFiltro(true);
         
         set_tramite.getBot_aceptar().setMetodo("aceptarCertificacionPresupuestaria");
         set_tramite.setRadio();
         agregarComponente(set_tramite);
         
         ///generar asiento conmtable
         Boton bot_contabilizar=new Boton();
         bot_contabilizar.setIcon("ui-icon-person");
         bot_contabilizar.setValue("Generar Asiento Contable");
         bot_contabilizar.setMetodo("seleccionarAsiento");
         bar_botones.agregarBoton(bot_contabilizar);

         set_contabilizar.setId("set_contabilizar");
         set_contabilizar.setSeleccionTabla(ser_Presupuesto.getCatalogoContabilizar("-1","-1"),"ide_prasp");
         set_contabilizar.setTitle("SELECCIONE LA CUENTA CONTABLE A EJECUTAR");   
         set_contabilizar.getTab_seleccion().getColumna("ide_prcla").setVisible(false);

         set_contabilizar.getBot_aceptar().setMetodo("aceptarAsiento");
         agregarComponente(set_contabilizar);
         
         //generar anticipos empleados
         Boton bot_anticipo=new Boton();
         bot_anticipo.setIcon("ui-icon-person");
         bot_anticipo.setValue("Generar Asiento Anticipos");
         bot_anticipo.setMetodo("seleccionarAnticipo");
         bar_botones.agregarBoton(bot_anticipo);
         
         set_anticipo_empleado.setId("set_anticipo_empleado");
         set_anticipo_empleado.setSeleccionTabla(ser_contabilidad.getConsultaAnticipos(),"ide_nrant");
         set_anticipo_empleado.setTitle("SELECCIONE LA CUENTA CONTABLE A EJECUTAR");
         set_anticipo_empleado.getTab_seleccion().getColumna("apellido_paterno_gtemp").setFiltro(true);
         set_anticipo_empleado.getTab_seleccion().getColumna("apellido_materno_gtemp").setFiltro(true);
         set_anticipo_empleado.getTab_seleccion().getColumna("primer_nombre_gtemp").setFiltro(true);
         set_anticipo_empleado.getBot_aceptar().setMetodo("aceptarAnticipo");
         agregarComponente(set_anticipo_empleado);
         
         // grid datos del anticipo
         Grid gri_anticipo = new Grid();
         gri_anticipo.setColumns(2);
         gri_anticipo.getChildren().add(new Etiqueta("Concepto de Pago: "));
         com_tipo_concepto.setId("com_tipo_concepto");
         com_tipo_concepto.setCombo("select ide_tetic,detalle_tetic,codigo_tetic from tes_tipo_concepto order by detalle_tetic");
         gri_anticipo.getChildren().add(com_tipo_concepto);
         gri_anticipo.getChildren().add(new Etiqueta("Fecha: "));
         cal_fecha_inicial.setId("cal_fecha_inicial_debito");
		 cal_fecha_inicial.setFechaActual();
		 gri_anticipo.getChildren().add(cal_fecha_inicial);
		 dia_anticipo.setId("dia_anticipo");
		 dia_anticipo.getBot_aceptar().setMetodo("aceptarAnticipo");
		 dia_anticipo.setDialogo(gri_anticipo);
		 agregarComponente(dia_anticipo);
		
		    con_guardar.setId("con_guardar");
			con_guardar.setMessage("EL COMPROBANTE DE PAGO NO REGISTRA RETENCIONES DESEA CONTINUAR, PRESIONE ACEPTAR CASO CONTRARIO CANCELAR");
			con_guardar.setTitle("CONFIRMACION DE RETENCIONES");
     		con_guardar.getBot_aceptar().setMetodo("aceptarCertificacionPresupuestaria");
     		con_guardar.getBot_cancelar().setMetodo("cancelarRetencion");
			agregarComponente(con_guardar);

     }
    public void seleccionarAnticipo(){
    	set_anticipo_empleado.getTab_seleccion().setSql(ser_contabilidad.getConsultaAnticipos());
    	set_anticipo_empleado.getTab_seleccion().ejecutarSql();
    	set_anticipo_empleado.dibujar();

    }
    String str_seleccionados="";
    public void aceptarAnticipo(){
    	//String str_seleccionados="";
    	String valor_debe="0";
        String valor_haber="0";
    	if(set_anticipo_empleado.isVisible()){
    		
    		if(set_anticipo_empleado.getSeleccionados() !=null){
    			str_seleccionados=set_anticipo_empleado.getSeleccionados();
 
    			set_anticipo_empleado.cerrar();
    			dia_anticipo.dibujar();
    		}
    		else {
    			utilitario.agregarMensajeError("Seleccione un Registro", "Debe seleccionar al menos un registro para continuar");
    		}
    	}
    	else if (dia_anticipo.isVisible()){
    		//System.out.println("entro a imprimir lso anteicipos "+str_seleccionados);
   	 		TablaGenerica tab_anticipos=utilitario.consultar("select a.ide_nrant,a.ide_gtemp,a.ide_geedp,apellido_paterno_gtemp,apellido_materno_gtemp,primer_nombre_gtemp,segundo_nombre_gtemp,detalle_nrmoa,fecha_solicitud_nrant,monto_aprobado_nrant"
    				+" from nrh_anticipo a left join nrh_motivo_anticipo c on a.ide_nrmoa = c.ide_nrmoa left join gth_empleado d on a.ide_gtemp = d.ide_gtemp where ide_nrant  in ("+str_seleccionados+")");

    		tab_anticipos.imprimirSql();
       		String sql_insert="";
            ser_contabilidad.limpiarAcceso("tes_comprobante_pago");
            ser_contabilidad.limpiarAcceso("cont_detalle_movimiento");

    		for (int i=0;i<tab_anticipos.getTotalFilas();i++){
    			String str_nombre_empleado=tab_anticipos.getValor(i, "apellido_paterno_gtemp")+" "+tab_anticipos.getValor(i, "apellido_materno_gtemp")+" "+tab_anticipos.getValor(i, "primer_nombre_gtemp")+" "+tab_anticipos.getValor(i, "segundo_nombre_gtemp");
    			// Consulto codigo maximo de la cabecera del comprobante de pago
				TablaGenerica tab_maximo_comprobante =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("tes_comprobante_pago", "ide_tecpo"));
				String maximo_comprobante=tab_maximo_comprobante.getValor("codigo");
				// consulta el codigo maximo del secuencia del numero de comprobante  
				String secuencia_maximo=ser_contabilidad.numeroSecuencial(par_sec_comprobante_pago);
    			sql_insert="insert into tes_comprobante_pago (ide_tecpo,ide_tetic,ide_geedp,ide_coest,fecha_tecpo,comprobante_egreso_tecpo,detalle_tecpo,fecha_pago_tecpo,activo_tecpo,valor_pago_tecpo,ide_nrant)"
    					+ " values ("+maximo_comprobante+","+com_tipo_concepto.getValue()+","+tab_anticipos.getValor(i, "ide_geedp")+","+par_estado_devengado+",'"+cal_fecha_inicial.getFecha()+"','"+secuencia_maximo+"','ANTICIPO SUELDO DEL FUNCIONARIO(A): "+str_nombre_empleado+", MOTIVO DEL ANTICIPO: "+tab_anticipos.getValor(i, "detalle_nrmoa")+"','"+cal_fecha_inicial.getFecha()+"',true,"+tab_anticipos.getValor(i, "monto_aprobado_nrant")+","+tab_anticipos.getValor(i, "ide_nrant")+")";
    			utilitario.getConexion().ejecutarSql(sql_insert);
    			// actualizamos el secuencial
    			ser_contabilidad.guardaSecuencial(ser_contabilidad.numeroSecuencial(par_sec_comprobante_pago), par_sec_comprobante_pago);
    			// consulto el numero de asiento generado por el disparador paracargar lso detalles del asiento contable
    			TablaGenerica tab_consulta_movimiento=utilitario.consultar("select ide_tecpo, ide_comov from tes_comprobante_pago where ide_tecpo="+maximo_comprobante);
    			TablaGenerica tab_cuenta_anticipo_empleado=utilitario.consultar("select * from gth_cuenta_anticipo where ide_gtemp="+tab_anticipos.getValor(i,"ide_gtemp")+" and ide_geani="+par_anio_vigente);
    			//System.out.println("imprimo cuenta d eanticiopo");
    			//tab_cuenta_anticipo_empleado.imprimirSql();
    			// INSERTO EN LA TABLA DEL detalle del asiento contable
				// Consulto codigo maximo del detalle del movimiento
				TablaGenerica tab_maximo_detalle =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
				String maximo_detalle=tab_maximo_detalle.getValor("codigo");

				
    			String sqlinsertaprimeralinea ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
    					+" values ( "+maximo_detalle+","+tab_consulta_movimiento.getValor("ide_comov")+","+tab_anticipos.getValor(i, "monto_aprobado_nrant")+",0,true,"+tab_cuenta_anticipo_empleado.getValor("ide_cocac")+","+par_debe+" );";
    			utilitario.getConexion().ejecutarSql(sqlinsertaprimeralinea);

    			//cosnulto cuenta que se mueve al ahaber del asiento tipo de anticipos
    			TablaGenerica tab_asiento_tipo=utilitario.consultar("select ide_coast,ide_gelua,ide_cocac from cont_asiento_tipo where ide_conac ="+par_asiento_anticipo);
    			
    			for (int j=0;j<tab_asiento_tipo.getTotalFilas();j++){	
    			//isnerta debe
    					if(tab_asiento_tipo.getValor(j,"ide_gelua").equals(par_debe)){
    						valor_debe=tab_anticipos.getValor(i, "monto_aprobado_nrant");
    						valor_haber="0";
    					}
    					//isnerta haber
    					else if(tab_asiento_tipo.getValor(j,"ide_gelua").equals(par_haber)){
    						valor_haber=tab_anticipos.getValor(i, "monto_aprobado_nrant");   
    						valor_debe="0";
    					}
    					
    					// INSERTO EN LA TABLA DEL detalle del asiento contable
    					// Consulto codigo maximo del detalle del movimiento
    					TablaGenerica tab_maximo_detalle2 =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
    					String maximo_detalle2=tab_maximo_detalle2.getValor("codigo");

    					
    	    			String sqlinsertaprimeralinea2 ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
    	    					+" values ( "+maximo_detalle2+","+tab_consulta_movimiento.getValor("ide_comov")+","+valor_debe+","+valor_haber+",true,"+tab_asiento_tipo.getValor(j, "ide_cocac")+","+tab_asiento_tipo.getValor(j, "ide_gelua")+" );";
    	    			utilitario.getConexion().ejecutarSql(sqlinsertaprimeralinea2);

    			}
    			
    		}
    		utilitario.agregarMensaje("Contabilizado Correctamente", "Asientos de Anticipos generedos");
    		dia_anticipo.cerrar();
    		tab_comprobante.ejecutarSql();
    		utilitario.addUpdate("tab_comprobante");
    		//System.out.println("nuemro de comprobante "+tab_comprobante.getValor("ide_comov"));
            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));    
            tab_detalle_movimiento.ejecutarSql();
    		utilitario.addUpdate("tab_detalle_movimiento");
            tab_comprobante.imprimirSql();
            tab_detalle_movimiento.imprimirSql();
    	
    	}
    	else{
    		utilitario.agregarMensaje("Un gusto saludarle", "Tenga un buen dia");
    	
    	}
    }
    public void aceptarAsiento(){
        String str_seleccionado = set_contabilizar.getSeleccionados();
        String valor_debe="0";
        String valor_haber="0";
        TablaGenerica tab_asociacion=utilitario.consultar("SELECT ide_prasp, ide_prcla, ide_cocac, ide_gelua, ide_prmop, activo_prasp, cuenta_padre_prasp,devengado FROM pre_asociacion_presupuestaria where ide_prasp in ("+str_seleccionado+");");
		TablaGenerica tab_valor_comprobante=utilitario.consultar("select ide_tecpo,"+par_valor_devengar+" as valor,valor_iva_tecpo,valor_pago_tecpo from tes_comprobante_pago where ide_tecpo ="+tab_comprobante.getValor("ide_tecpo"));
		//System.out.println("tab_asociacion ");
		//tab_asociacion.imprimirSql();
		//System.out.println("tab_valor_comprobante ");
		//tab_valor_comprobante.imprimirSql();

        ser_contabilidad.limpiarAcceso("cont_detalle_movimiento");
        if(tab_asociacion.getTotalFilas()>0){
    		for(int i= 0;i< tab_asociacion.getTotalFilas();i++){
    			
    			
    			//isnerta debe
    			if(tab_asociacion.getValor(i,"ide_gelua").equals(par_debe)){
    				valor_debe=tab_valor_comprobante.getValor("valor");
    				valor_haber="0";
    			}
    			//isnerta haber
    			else if(tab_asociacion.getValor(i,"ide_gelua").equals(par_haber)){
    				valor_haber=tab_valor_comprobante.getValor("valor");   
    				valor_debe="0";
    			}
    			
    			// INSERTO EN LA TABLA DEL detalle del asiento contable
				// Consulto codigo maximo de la cabecera del asiento factura
				TablaGenerica tab_maximo_detalle =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
				String maximo_detalle=tab_maximo_detalle.getValor("codigo");

				
    			String sqlinsertaprimeralinea ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
    					+" values ( "+maximo_detalle+","+tab_comprobante.getValor("ide_comov")+","+valor_debe+","+valor_haber+",true,"+tab_asociacion.getValor(i,"ide_cocac")+","+tab_asociacion.getValor(i,"ide_gelua")+" );";
    			utilitario.getConexion().ejecutarSql(sqlinsertaprimeralinea);
    			//System.out.println("sqlinsertaprimeralinea "+sqlinsertaprimeralinea);
    			//inserto las cuentas por pagar tanto al debe como al haber
    	        TablaGenerica tab_cuenta_pagar=utilitario.consultar("SELECT ide_prasp, ide_prcla, ide_cocac, ide_gelua, ide_prmop, activo_prasp, cuenta_padre_prasp FROM pre_asociacion_presupuestaria where ide_prcla in ("+tab_asociacion.getValor(i,"devengado")+");");
    			//System.out.println("tab_cuenta_pagar ");
    			//tab_cuenta_pagar.imprimirSql();
    	        for(int j=0;j<tab_cuenta_pagar.getTotalFilas();j++){
    	        	//isnerta debe
        			if(tab_cuenta_pagar.getValor(j,"ide_gelua").equals(par_debe)){
        				valor_debe=tab_valor_comprobante.getValor("valor");
        				valor_haber="0";
        			}
        			//isnerta haber
        			else if(tab_cuenta_pagar.getValor(j,"ide_gelua").equals(par_haber)){
        				valor_haber=tab_valor_comprobante.getValor("valor");   
        				valor_debe="0";
        			}
        			
        			// INSERTO EN LA TABLA DEL detalle del asiento contable
    				// Consulto codigo maximo de la cabecera del asiento factura
    				TablaGenerica tab_maximo_detallej =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
    				String maximo_detallej=tab_maximo_detallej.getValor("codigo");

    				
        			String sqlinsertaprimeralineaj ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
        					+" values ( "+maximo_detallej+","+tab_comprobante.getValor("ide_comov")+","+valor_debe+","+valor_haber+",true,"+tab_cuenta_pagar.getValor(j,"ide_cocac")+","+tab_cuenta_pagar.getValor(j,"ide_gelua")+" );";
        			utilitario.getConexion().ejecutarSql(sqlinsertaprimeralineaj);
        			
    	        }
    		}
    	}
        
        
        
        if(par_lugar_aplica_banco.equals(par_debe)){
			valor_debe=tab_valor_comprobante.getValor("valor_pago_tecpo");
			valor_haber="0";
		}
		//isnerta haber
		else if(par_lugar_aplica_banco.equals(par_haber)){
			valor_haber=tab_valor_comprobante.getValor("valor_pago_tecpo");   
			valor_debe="0";
		}
        // INSERTO EN LA TABLA DEL detalle del asiento contable de bancos
    	// Consulto codigo maximo de la cabecera del asiento factura
    	TablaGenerica tab_maximo_detallebanco =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
    	String maximo_detallebanco=tab_maximo_detallebanco.getValor("codigo");

    				
        			String sqlinsertaprimeralineabanco ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
        					+" values ( "+maximo_detallebanco+","+tab_comprobante.getValor("ide_comov")+","+valor_debe+","+valor_haber+",true,"+par_cuenta_banco+","+par_lugar_aplica_banco+" );";
        			utilitario.getConexion().ejecutarSql(sqlinsertaprimeralineabanco);
        			
        			//System.out.println("sqlinsertaprimeralineabanco "+sqlinsertaprimeralineabanco);

         // INSERTO EN LA TABLA DEL detalle del asiento contable de iva
        // Consulto codigo maximo de la cabecera del asiento factura
        
        			 if(par_lugar_aplica_iva.equals(par_debe)){
        					valor_debe=tab_valor_comprobante.getValor("valor_iva_tecpo");
        					valor_haber="0";
        				}
        				//isnerta haber
        				else if(par_lugar_aplica_iva.equals(par_haber)){
        					valor_haber=tab_valor_comprobante.getValor("valor_iva_tecpo");   
        					valor_debe="0";
        				}			
        TablaGenerica tab_maximo_detalleiva =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
        String maximo_detalleiva=tab_maximo_detalleiva.getValor("codigo");
 				
		String sqlinsertaprimeralineaiva ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
		+" values ( "+maximo_detalleiva+","+tab_comprobante.getValor("ide_comov")+","+valor_debe+","+valor_haber+",true,"+par_cuenta_iva+","+par_lugar_aplica_iva+" );";
 			utilitario.getConexion().ejecutarSql(sqlinsertaprimeralineaiva);
			//System.out.println("sqlinsertaprimeralineaiva "+sqlinsertaprimeralineaiva);
			
 		TablaGenerica tab_consulta_retencion=utilitario.consultar(ser_Tesoreria.getConsultaRetencion(tab_comprobante.getValor("ide_tecpo")));	
		//System.out.println("tab_consulta_retencion ");
		//tab_consulta_retencion.imprimirSql();
		
 		for(int k=0;k<tab_consulta_retencion.getTotalFilas();k++){
 			TablaGenerica tab_ejecuta = utilitario.consultar(ser_Tesoreria.getConsultaRetencionCalcula(tab_comprobante.getValor("ide_tecpo"), tab_consulta_retencion.getValor(k,"formula_teast")));
 			
 			if(tab_consulta_retencion.getValor(k, "ide_gelua").equals(par_debe)){
					valor_debe=tab_ejecuta.getValor(k,"resultado");
					valor_haber="0";
				}
				//isnerta haber
				else if(tab_consulta_retencion.getValor(k, "ide_gelua").equals(par_haber)){
					valor_haber=tab_ejecuta.getValor(k,"resultado");   
					valor_debe="0";
				}			
 			 TablaGenerica tab_maximo_detalleretencion =utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("cont_detalle_movimiento", "ide_codem"));
 			 String maximo_detalleretencion=tab_maximo_detalleretencion.getValor("codigo");
		
 			 String sqlinsertaprimeralinearetencion ="insert into cont_detalle_movimiento (ide_codem,ide_comov,debe_codem,haber_codem,activo_codem,ide_cocac,ide_gelua)"
 					 +" values ( "+maximo_detalleretencion+","+tab_comprobante.getValor("ide_comov")+","+valor_debe+","+valor_haber+",true,"+tab_consulta_retencion.getValor(k, "ide_cocac")+","+tab_consulta_retencion.getValor(k, "ide_gelua")+" );";
 			 utilitario.getConexion().ejecutarSql(sqlinsertaprimeralinearetencion);

 		}
 		
 
        set_contabilizar.cerrar();			
        //tab_comprobante.ejecutarSql();
        tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));    
        tab_detalle_movimiento.ejecutarSql();
        utilitario.addUpdate("tab_detalle_movimiento");
    }
    public void seleccionarAsiento(){
    	set_contabilizar.getTab_seleccion().setSql(ser_Presupuesto.getCatalogoContabilizar(tab_comprobante.getValor("IDE_PRTRA"),par_movimiento_devengado));
    	set_contabilizar.getTab_seleccion().ejecutarSql();
    	set_contabilizar.dibujar();

    }
   ///sacar valores
 	
   	public void calcularTotal(AjaxBehaviorEvent evt){
   		tab_detalle_movimiento.modificar(evt);
   		tab_detalle_movimiento.sumarColumnas();
   		utilitario.addUpdate("tab_detalle_movimiento");
   	}

     public void importarSolicitudCompra(){
     	//System.out.println("entra a metodo impostar solicitud");
     	
         set_solicitud.getTab_seleccion().setSql(ser_Adquisicion.getSolicitudCompra("true"));
         set_solicitud.getTab_seleccion().ejecutarSql();
         set_solicitud.dibujar();

     }

     public void aceptarSolicitudCompra(){
     	System.out.println("entra a metodo aceptar solicitud");

         String str_seleccionado = set_solicitud.getValorSeleccionado();
         TablaGenerica tab_solicitud=ser_Adquisicion.getTablaGenericaSolicitud(str_seleccionado);
         if (str_seleccionado!=null){
             tab_comprobante.insertar();
             tab_comprobante.setValor("ide_adsoc",str_seleccionado);
             tab_comprobante.setValor("valor_compra_tecpo",tab_solicitud.getValor("total_adfac"));
             tab_comprobante.setValor("valor_iva_tecpo", tab_solicitud.getValor("valor_iva_adfac"));
             tab_comprobante.setValor("ide_prtra", tab_solicitud.getValor("ide_prtra"));
             tab_comprobante.setValor("ide_tepro",tab_solicitud.getValor("ide_tepro"));
             tab_comprobante.setValor("subtotal_tecpo",tab_solicitud.getValor("subtotal_adfac"));

         }
         set_solicitud.cerrar();
         utilitario.addUpdate("tab_comprobante");
     }

     public void importarCertificacionPresupuestaria(){
     	//System.out.println("entra a metodo impostar cer. presu");

         set_tramite.getTab_seleccion().setSql(ser_Presupuesto.getTramite("true"));
         set_tramite.getTab_seleccion().ejecutarSql();
         set_tramite.dibujar();

     }
     String val_selecciondo ="";
     public void aceptarCertificacionPresupuestaria(){
     	
     	if(set_tramite.isVisible()){
 	   	 val_selecciondo = set_tramite.getValorSeleccionado();
         TablaGenerica tab_tramite=ser_Presupuesto.getTablaGenericaTramite(val_selecciondo);
         tab_tramite.imprimirSql();
         if (val_selecciondo!=null){
        	 
        	 tab_comprobante.insertar();
             tab_comprobante.setValor("ide_prtra",tab_tramite.getValor("ide_prtra"));

        	 TablaGenerica tab_retenciones_facturas = utilitario.consultar(ser_Tesoreria.getFacturaRetencion(tab_tramite.getValor("ide_prtra")));
             //Si tiene facturas y retenciones
        	 if(tab_retenciones_facturas.getTotalFilas()>0){        	 
             tab_detalle_movimiento.setCondicion("ide_comov is null");;
             tab_detalle_movimiento.ejecutarSql();
             tab_detalle_movimiento.setColumnaSuma("haber_codem,debe_codem"); 
             tab_comprobante.setValor("valor_retencion_tecpo", tab_retenciones_facturas.getValor("valor_retenido"));
             tab_comprobante.setValor("ide_adsoc", tab_retenciones_facturas.getValor("ide_adsoc"));
             tab_comprobante.setValor("ide_tepro", tab_retenciones_facturas.getValor("ide_tepro"));
             tab_comprobante.setValor("detalle_tecpo", tab_retenciones_facturas.getValor("detalle"));
             tab_comprobante.setValor("valor_compra_tecpo", tab_retenciones_facturas.getValor("total_adfac"));
             tab_comprobante.setValor("valor_iva_tecpo", tab_retenciones_facturas.getValor("valor_iva_adfac"));
             tab_comprobante.setValor("subtotal_tecpo", tab_retenciones_facturas.getValor("subtotal_adfac"));
             TablaGenerica tab_valor_pago = utilitario.consultar("select 1 as codigo, "+tab_retenciones_facturas.getValor("subtotal_adfac")+"+"+ tab_retenciones_facturas.getValor("valor_iva_adfac")+"-"+tab_retenciones_facturas.getValor("valor_retenido")+" as valor_pago");
             tab_comprobante.setValor("valor_pago_tecpo", tab_valor_pago.getValor("valor_pago"));
            
             utilitario.addUpdate("tab_detalle_movimiento");
             set_tramite.cerrar();
             }
             else {        	
            	con_guardar.dibujar();
             }
         }
         else {
        	 utilitario.agregarMensajeError("Seleccione un Registro", "Debe seleccionar al menos un registro para continuar");
        	 return;
         }
         set_tramite.cerrar();
     	}
 
     	else if (con_guardar.isVisible()){
     		tab_detalle_movimiento.setCondicion("ide_comov is null");;
            tab_detalle_movimiento.ejecutarSql();
            tab_detalle_movimiento.setColumnaSuma("haber_codem,debe_codem");
            TablaGenerica tab_consulta_tramite=utilitario.consultar("select ide_prtra,ide_tepro,ide_geedp,observaciones_prtra,total_compromiso_prtra from pre_tramite where ide_prtra="+tab_comprobante.getValor("ide_prtra"));
     		tab_consulta_tramite.imprimirSql();
            tab_comprobante.setValor("valor_retencion_tecpo", "0.00");  
            tab_comprobante.setValor("ide_tepro", tab_consulta_tramite.getValor("ide_tepro"));
            tab_comprobante.setValor("detalle_tecpo", tab_consulta_tramite.getValor("observaciones_prtra"));
            tab_comprobante.setValor("valor_compra_tecpo", tab_consulta_tramite.getValor("total_compromiso_prtra"));
            tab_comprobante.setValor("valor_iva_tecpo","0.00");
            tab_comprobante.setValor("subtotal_tecpo", tab_consulta_tramite.getValor("total_compromiso_prtra"));
            tab_comprobante.setValor("valor_pago_tecpo", tab_consulta_tramite.getValor("total_compromiso_prtra"));
            tab_comprobante.setValor("ide_geedp", tab_consulta_tramite.getValor("ide_geedp"));
       	    
       	    utilitario.addUpdateTabla(tab_comprobante, "valor_retencion_tecpo,ide_tepro,detalle_tecpo,valor_iva_tecpo,subtotal_tecpo,valor_pago_tecpo,valor_compra_tecpo,ide_geedp", "");
           utilitario.addUpdate("tab_detalle_movimiento");
     		con_guardar.cerrar();
     	}
			
         
        
     }
     public void cancelarRetencion(){
    	 tab_comprobante.actualizar();
    	 utilitario.addUpdate("tab_comprobante");
    	 con_guardar.cerrar();
     }
     ///calculo el valor de pago
     public void valorPago()
     {
    	 double dou_subtotal=0;
    	 double dou_valor_total=0;
    	 double dou_valor_iva=0;
    	 double dou_valor_retencion=0;
    	 double dou_valor_pago=0;
    	 try{
    		 dou_subtotal = Double.parseDouble(tab_comprobante.getValor("subtotal_tecpo"));
    	 }catch (Exception e){
    		 
    	 }
    	 try{
    		 dou_valor_retencion = Double.parseDouble(tab_comprobante.getValor("valor_retencion_tecpo"));
    	 }catch (Exception e){
    		 
    	 }
    	 
    	 dou_valor_iva =dou_subtotal*0.12;    	 
    	 BigDecimal big_valor_iva=new BigDecimal(dou_valor_iva);
    	 big_valor_iva=big_valor_iva.setScale(2, RoundingMode.HALF_UP);
    	 
    	 dou_valor_total=dou_subtotal+big_valor_iva.doubleValue();
    	 dou_valor_pago=dou_valor_total-dou_valor_retencion;
    	 
    	 tab_comprobante.setValor("valor_iva_tecpo",big_valor_iva+ "");
    	 tab_comprobante.setValor("valor_compra_tecpo", dou_valor_total+"");
    	 tab_comprobante.setValor("valor_pago_tecpo", dou_valor_pago+"");
    	 utilitario.addUpdateTabla(tab_comprobante, "valor_iva_tecpo,valor_compra_tecpo,valor_pago_tecpo", "");
		 
     }
     ////boton impuesto
     public void importarImpuesto(){
     	System.out.println("entra a metodo import impues");
     	if(tab_comprobante.getValor("valor_compra_tecpo")==null||tab_comprobante.getValor("valor_compra_tecpo").equals("")){
     		utilitario.agregarMensaje("No puede generar una retencion", "Ingrese un valor de compra");
     		return;
     	}
     	if(tab_comprobante.getValor("VALOR_IVA_TECPO")==null||tab_comprobante.getValor("VALOR_IVA_TECPO").equals("")){
     		utilitario.agregarMensaje("No puede generar una retencion", "Ingrese valor Iva");
     		return;
     	}
         set_impuesto.getTab_seleccion().setSql("select ide_tetii,detalle_tetii from tes_tipo_impuesto order by ide_tetii");
         set_impuesto.getTab_seleccion().ejecutarSql();
         set_impuesto.dibujar();
         
     }
     String str_seleccionado="";
     public void aceptarImpuesto(){
     	//System.out.println("entra a metodo aceptar impues");

         if(set_impuesto.isVisible()){
             if (set_impuesto.getValorSeleccionado()!=null){
                 tab_detalle_retencion.insertar();

             	if(set_impuesto.getValorSeleccionado().equals(par_impuesto_iva)){
             		tab_detalle_retencion.setValor("base_imponible_teder", tab_comprobante.getValor("valor_iva_tecpo"));
             	}
             	else if(set_impuesto.getValorSeleccionado().equals(par_impuesto_renta)){
             		tab_detalle_retencion.setValor("base_imponible_teder", tab_comprobante.getValor("valor_compra_tecpo"));
             	}
              str_seleccionado= set_impuesto.getValorSeleccionado();
             //System.out.println("probando que valor me llega"+str_seleccionado);
             set_retencion.getTab_seleccion().setSql(ser_Tesoreria.getImpuesto("true","0",str_seleccionado));
             set_retencion.getTab_seleccion().ejecutarSql();
             set_retencion.dibujar();
             set_impuesto.cerrar();
             
             
             }
             else {
                 utilitario.agregarMensajeInfo("SELECCIONE OPCION", "Seleccione un registro");
             }
                 
         }
     
         else if (set_retencion.isVisible()){
             str_seleccionado= set_retencion.getValorSeleccionado();
             TablaGenerica tab_rentas= utilitario.consultar(ser_Tesoreria.getImpuestoCalculo(str_seleccionado));

             double dou_valor_impuesto=0;
            double dou_porcentaje_calculo=0;
            double dou_valor_resultado=0;

            dou_porcentaje_calculo=Double.parseDouble(tab_rentas.getValor("porcentaje_teimp"));
            dou_valor_impuesto=Double.parseDouble(tab_detalle_retencion.getValor("base_imponible_teder"));
            dou_valor_resultado=(dou_porcentaje_calculo*dou_valor_impuesto)/100;
  
            if (set_retencion.getValorSeleccionado()!=null){

                 tab_detalle_retencion.setValor("ide_teimp",str_seleccionado);
                 tab_detalle_retencion.setValor("valor_retenido_teder", dou_valor_resultado+"");   
                 String valorx=tab_detalle_retencion.getSumaColumna("valor_retenido_teder")+"";
                tab_retencion.setValor("total_ret_teret", valorx);       
            }

             set_retencion.cerrar();
              utilitario.addUpdateTabla(tab_detalle_retencion, "valor_retenido_teder,base_imponible_teder,ide_teimp","");
              utilitario.addUpdateTabla(tab_retencion, "total_ret_teret","");
             calcularValorPago();
             utilitario.addUpdateTabla(tab_comprobante, "valor_pago_tecpo,","");

         }


     }
     //Calcular valor impuesto
     public void impuestoCalculo(AjaxBehaviorEvent evt){
    	 tab_detalle_retencion.modificar(evt); //Siempre es la primera linea
        TablaGenerica tab_rentas= utilitario.consultar(ser_Tesoreria.getImpuestoCalculo(tab_detalle_retencion.getValor("ide_teimp")));
        double dou_valor_impuesto=0;
        double dou_porcentaje_calculo=0;
        double dou_valor_resultado=0;

        dou_porcentaje_calculo=Double.parseDouble(tab_rentas.getValor("porcentaje_teimp"));
        dou_valor_impuesto=Double.parseDouble(tab_detalle_retencion.getValor("base_imponible_teder"));
        dou_valor_resultado=(dou_porcentaje_calculo*dou_valor_impuesto)/100;
        tab_detalle_retencion.setValor("valor_retenido_teder", dou_valor_resultado+"");   
        String valorx=tab_detalle_retencion.getSumaColumna("valor_retenido_teder")+"";
       tab_retencion.setValor("total_ret_teret", valorx);  
       utilitario.addUpdateTabla(tab_detalle_retencion, "valor_retenido_teder,base_imponible_teder,ide_teimp","");
       utilitario.addUpdateTabla(tab_retencion, "total_ret_teret","");
      calcularValorPago();
      utilitario.addUpdateTabla(tab_comprobante, "valor_pago_tecpo,","");

    	 
     }
     ////calcular ValorPago
     public void calcularValorPago(){
     	double dou_val_compra=0;
     	double dou_total_retencion=0;
     	double dou_valor_pago=0;
     	double dou_valor_iva=0;
		double duo_iva=par_iva;


     	try {
 			//Obtenemos el valor de la cantidad
     		dou_val_compra=Double.parseDouble(tab_comprobante.getValor("valor_compra_tecpo"));
 		} catch (Exception e) {
 		}
     	try {
 			//Obtenemos el valor de la cantidad
     		dou_total_retencion=Double.parseDouble(tab_retencion.getValor("total_ret_teret"));
 		} catch (Exception e) {
 		}
     
     	dou_valor_pago=dou_val_compra-dou_total_retencion;
     //	dou_valor_iva=dou_val_compra*duo_iva;
     	
     	tab_comprobante.setValor("valor_pago_tecpo", utilitario.getFormatoNumero(dou_valor_pago,2));
     	//tab_comprobante.setValor("valor_iva_tecpo", utilitario.getFormatoNumero(dou_valor_iva,2));
     	tab_comprobante.modificar(tab_comprobante.getFilaActual());//para que haga el update
     	utilitario.addUpdateTabla(tab_comprobante,"valor_pago_tecpo,","");
     }

    

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		 if (tab_comprobante.isFocus()) {
			 utilitario.agregarMensajeInfo("No puede insertar", "Debe buscar Solicitud de Compra o una certificaci�n presupuestaria");
			 //tab_comprobante.insertar();
	        }

	        else if (tab_detalle_movimiento.isFocus()){
	            tab_detalle_movimiento.insertar();
	            tab_detalle_movimiento.setValor("ide_comov", tab_comprobante.getValor("ide_comov"));
	        }
	        else if (tab_retencion.isFocus()) {
	            tab_retencion.insertar();

	        }
	        else if (tab_detalle_retencion.isFocus()) {
	            utilitario.agregarMensajeInfo("No puede insertar", "Debe generar una Retencion");

	        }    
	
	}

	/////botones fin,siguiente,atras,ultimo.inicio
	  @Override
	    public void inicio() {
	        super.inicio();

	        // TODO Auto-generated method stub
	        if (tab_comprobante.isFocus()){
	            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));
	            tab_detalle_movimiento.ejecutarSql();
	            utilitario.addUpdate("tab_detalle_movimiento");

	            
	        }
	    }
	    @Override
	    public void siguiente() {
	        // TODO Auto-generated method stub
	    	super.siguiente();
	        if (tab_comprobante.isFocus()){
	            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));
	             tab_detalle_movimiento.ejecutarSql();
	            utilitario.addUpdate("tab_detalle_movimiento");

	        }

	        
	    }
	    @Override
	    public void atras() {
	        super.atras();

	        // TODO Auto-generated method stub
	        if (tab_comprobante.isFocus()){
	            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));
	            tab_detalle_movimiento.ejecutarSql();
	            utilitario.addUpdate("tab_detalle_movimiento");


	        }

	    }

	    @Override
	    public void fin() {
	        super.fin();

	        // TODO Auto-generated method stub
	        if (tab_comprobante.isFocus()){
	            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));
	            tab_detalle_movimiento.ejecutarSql();
	            utilitario.addUpdate("tab_detalle_movimiento");
	        }

	    }

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		 if (tab_comprobante.guardar()) {

	            if (tab_detalle_movimiento.guardar()) {
	                if( tab_retencion.guardar()){
	                    if(tab_detalle_retencion.guardar()){
	                        guardarPantalla();

	                        tab_comprobante.ejecutarSql();
	                        tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));    
	                        tab_detalle_movimiento.ejecutarSql();
	                    
	                    }
	                }
	            }

	        }

		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		  utilitario.getTablaisFocus().eliminar();

    
	}


	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		super.actualizar();  
		tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));
        tab_detalle_movimiento.ejecutarSql();
        utilitario.addUpdate("tab_detalle_movimiento");
		
	}
	@Override
	public void abrirListaReportes() {
		// TODO Auto-generated method stub
		rep_reporte.dibujar();
	}


	@Override
	public void aceptarReporte() {
		Locale locale=new Locale("es","ES");

		if (rep_reporte.getReporteSelecionado().equals("Comprobante Pago")){ 
								
					if (rep_reporte.isVisible()){
						TablaGenerica tab_usuario =utilitario.consultar("select a.ide_usua,ide_empr,ide_sucu from sis_usuario a, sis_usuario_sucursal b where a.ide_usua = b.ide_usua and a.ide_usua ="+utilitario.getVariable("ide_usua"));
						rep_reporte.cerrar();	
						p_parametros.clear();
						p_parametros.put("titulo", "EMGIRS - EP");
						p_parametros.put("p_contador_general", utilitario.getVariable("p_nombre_contador"));
						p_parametros.put("p_valor_pagar",utilitario.getLetrasDolarNumero(utilitario.getFormatoNumero(tab_comprobante.getValor("valor_pago_tecpo"),2)));
						p_parametros.put("p_ide_comov",Integer.parseInt(tab_comprobante.getValor("ide_comov")));
						p_parametros.put("ide_usua",Integer.parseInt(tab_usuario.getValor("ide_usua")));
						p_parametros.put("ide_empr",Integer.parseInt(tab_usuario.getValor("ide_empr")));
						p_parametros.put("ide_sucu",Integer.parseInt(tab_usuario.getValor("ide_sucu")));
						p_parametros.put("REPORT_LOCALE", locale);
						sef_reporte.setSeleccionFormatoReporte(p_parametros, rep_reporte.getPath());						
						sef_reporte.dibujar();
						
			}
			else{
				utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun registro en la cabecera del anticipo");
			}
		}
		
	
		
	}
	public Tabla getTab_comprobante() {
		return tab_comprobante;
	}


	public void setTab_comprobante(Tabla tab_comprobante) {
		this.tab_comprobante = tab_comprobante;
	}


	public Tabla getTab_detalle_movimiento() {
		return tab_detalle_movimiento;
	}


	public void setTab_detalle_movimiento(Tabla tab_detalle_movimiento) {
		this.tab_detalle_movimiento = tab_detalle_movimiento;
	}


	public Tabla getTab_retencion() {
		return tab_retencion;
	}


	public void setTab_retencion(Tabla tab_retencion) {
		this.tab_retencion = tab_retencion;
	}


	public Tabla getTab_detalle_retencion() {
		return tab_detalle_retencion;
	}


	public void setTab_detalle_retencion(Tabla tab_detalle_retencion) {
		this.tab_detalle_retencion = tab_detalle_retencion;
	}

	public SeleccionTabla getSet_solicitud() {
		return set_solicitud;
	}

	public void setSet_solicitud(SeleccionTabla set_solicitud) {
		this.set_solicitud = set_solicitud;
	}

	public SeleccionTabla getSet_tramite() {
		return set_tramite;
	}

	public void setSet_tramite(SeleccionTabla set_tramite) {
		this.set_tramite = set_tramite;
	}

	public SeleccionTabla getSet_impuesto() {
		return set_impuesto;
	}

	public void setSet_impuesto(SeleccionTabla set_impuesto) {
		this.set_impuesto = set_impuesto;
	}

	public SeleccionTabla getSet_retencion() {
		return set_retencion;
	}

	public void setSet_retencion(SeleccionTabla set_retencion) {
		this.set_retencion = set_retencion;
	}

	public SeleccionTabla getSet_contabilizar() {
		return set_contabilizar;
	}

	public void setSet_contabilizar(SeleccionTabla set_contabilizar) {
		this.set_contabilizar = set_contabilizar;
	}
	public SeleccionTabla getSet_anticipo_empleado() {
		return set_anticipo_empleado;
	}
	public void setSet_anticipo_empleado(SeleccionTabla set_anticipo_empleado) {
		this.set_anticipo_empleado = set_anticipo_empleado;
	}
	public Dialogo getDia_anticipo() {
		return dia_anticipo;
	}
	public void setDia_anticipo(Dialogo dia_anticipo) {
		this.dia_anticipo = dia_anticipo;
	}
	public Confirmar getCon_guardar() {
		return con_guardar;
	}
	public void setCon_guardar(Confirmar con_guardar) {
		this.con_guardar = con_guardar;
	}
	public Tabla getTab_pre_mensual() {
		return tab_pre_mensual;
	}
	public void setTab_pre_mensual(Tabla tab_pre_mensual) {
		this.tab_pre_mensual = tab_pre_mensual;
	}
	public Reporte getRep_reporte() {
		return rep_reporte;
	}
	public void setRep_reporte(Reporte rep_reporte) {
		this.rep_reporte = rep_reporte;
	}
	public SeleccionFormatoReporte getSef_reporte() {
		return sef_reporte;
	}
	public void setSef_reporte(SeleccionFormatoReporte sef_reporte) {
		this.sef_reporte = sef_reporte;
	}


}
