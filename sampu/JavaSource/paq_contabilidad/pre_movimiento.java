package paq_contabilidad;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import paq_tesoreria.ejb.ServicioTesoreria;
import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Check;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.Tabla;
import framework.componentes.Texto;

public class pre_movimiento extends Pantalla{
	
	private Tabla tab_movimiento=new Tabla();
	private Tabla tab_detalle_movimiento=new Tabla();
	private Combo com_anio=new Combo();
	private Combo com_lugar_aplica=new Combo();
	private Combo com_tipo_concepto= new Combo();
	private AutoCompletar aut_catalogo=new AutoCompletar();
	private AutoCompletar aut_proveedor = new AutoCompletar();
	private Texto txt_valor = new Texto();
	private Check chk_transferencia=new Check();
	private String p_modulo_factruracion="";
	private String p_modulo_contabilidad="";

	private String p_debe="";
	private String p_haber="";	
	private Dialogo dia_movimientos=new Dialogo();
	private Reporte rep_reporte=new Reporte();
	private SeleccionFormatoReporte sef_reporte=new SeleccionFormatoReporte();
	private Map p_parametros=new HashMap();
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioPresupuesto ser_Presupuesto = (ServicioPresupuesto ) utilitario.instanciarEJB(ServicioPresupuesto.class);
	@EJB
	private ServicioBodega ser_Bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	@EJB
	private ServicioTesoreria ser_Tesoreria = (ServicioTesoreria) utilitario.instanciarEJB(ServicioTesoreria.class);
	     
	public pre_movimiento (){
		p_modulo_factruracion=utilitario.getVariable("p_modulo_facturacion");
		p_debe=utilitario.getVariable("p_gen_lugar_aplica_debe");
		p_haber=utilitario.getVariable("p_gen_lugar_aplica_haber");		
		p_modulo_contabilidad=utilitario.getVariable("p_modulo_contabilidad");
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		bar_botones.agregarReporte();
		rep_reporte.setId("rep_reporte");
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(rep_reporte);

		sef_reporte.setId("sef_reporte");
		agregarComponente(sef_reporte);

		tab_movimiento.setId("tab_movimiento");
		tab_movimiento.setHeader("MOVIMIENTOS");
		tab_movimiento.setTabla("cont_movimiento", "ide_comov", 1);
		tab_movimiento.setCondicion("ide_geani=-1");
		tab_movimiento.getColumna("ide_gemod").setCombo("gen_modulo", "ide_gemod", "detalle_gemod", "");
		tab_movimiento.getColumna("ide_cotim").setCombo("cont_tipo_movimiento", "ide_cotim", "detalle_cotim", "");
		tab_movimiento.getColumna("ide_cotia").setCombo("cont_tipo_asiento", "ide_cotia", "detalle_cotia", "");
		tab_movimiento.getColumna("ide_geare").setCombo("gen_area", "ide_geare", "detalle_geare", "");
		tab_movimiento.getColumna("ide_gemes").setCombo("gen_mes", "ide_gemes", "detalle_gemes", "");
		tab_movimiento.getColumna("ide_geani").setCombo("gen_anio","ide_geani","detalle_geani","");
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
		//tab_detalle_movimiento.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
		tab_detalle_movimiento.getColumna("ide_cocac").setCombo(ser_contabilidad.servicioCatalogoCuentaCombo());
		tab_detalle_movimiento.getColumna("ide_cocac").setAutoCompletar();
		
		tab_detalle_movimiento.getColumna("activo_codem").setLectura(true);
		tab_detalle_movimiento.getColumna("activo_codem").setValorDefecto("true");
		tab_detalle_movimiento.getColumna("haber_codem").setMetodoChange("calcularTotal");			
		tab_detalle_movimiento.setColumnaSuma("haber_codem,debe_codem");			
		tab_detalle_movimiento.getColumna("debe_codem").setMetodoChange("calcularTotal");			
		tab_detalle_movimiento.getColumna("ide_gelua").setCombo("gen_lugar_aplica","ide_gelua","detalle_gelua","");
		tab_detalle_movimiento.getColumna("ide_gelua").setMetodoChange("lugarAplica");
		tab_detalle_movimiento.getColumna("ide_prcla").setVisible(false);		
		tab_detalle_movimiento.getColumna("ide_prpro").setVisible(false);		

		tab_detalle_movimiento.getGrid().setColumns(4);
		tab_detalle_movimiento.dibujar();
		PanelTabla pat_detalle_movimiento=new PanelTabla();
		pat_detalle_movimiento.setPanelTabla(tab_detalle_movimiento);
		
		Division div_division =new Division();
		div_division.dividir2(pat_movimiento, pat_detalle_movimiento, "50%", "H");
		agregarComponente(div_division);
		
		
		//Inicio Dialogo CREDITO
		dia_movimientos.setId("dia_movimientos");
		dia_movimientos.setTitle("GENERACION DE MOVIMIENTOS FINANCIEROS");
		dia_movimientos.setHeight("45%");
		dia_movimientos.setWidth("40%");
		
		
		//inicio del grid
		Grid gri_datos_asiento = new Grid();
		gri_datos_asiento.setColumns(2);
		gri_datos_asiento.getChildren().add(new Etiqueta("Proveedor: "));
		aut_proveedor.setId("aut_proveedor");
		aut_proveedor.setAutoCompletar(ser_Bodega.getProveedor("true,false"));
		gri_datos_asiento.getChildren().add(aut_proveedor);
		gri_datos_asiento.getChildren().add(new Etiqueta("Concepto de Pago: "));
		com_tipo_concepto.setId("com_tipo_concepto");
		com_tipo_concepto.setCombo(ser_Tesoreria.getConsultaTipoConcepto("true,false"));
		gri_datos_asiento.getChildren().add(com_tipo_concepto);
		gri_datos_asiento.getChildren().add(new Etiqueta("Cuenta Contable: "));
		aut_catalogo.setId("aut_catalogo");
		aut_catalogo.setAutoCompletar(ser_contabilidad.getCuentaContable("true,false"));
		gri_datos_asiento.getChildren().add(aut_catalogo);
		gri_datos_asiento.getChildren().add(new Etiqueta("Debe / Haber: "));
		com_lugar_aplica.setId("com_lugar_aplica");
		com_lugar_aplica.setCombo("Select ide_gelua,detalle_gelua from gen_lugar_aplica");
		gri_datos_asiento.getChildren().add(com_lugar_aplica);
		gri_datos_asiento.getChildren().add(new Etiqueta("Valor: "));
		txt_valor.setId("txt_valor");
		txt_valor.setSoloNumeros();
		gri_datos_asiento.getChildren().add(txt_valor);
		gri_datos_asiento.getChildren().add(new Etiqueta("Cuenta Transferencia: "));
		chk_transferencia.setId("chk_transferencia");
		gri_datos_asiento.getChildren().add(chk_transferencia);
		dia_movimientos.getBot_aceptar().setMetodo("generarTransacciones");
		dia_movimientos.getBot_cancelar().setMetodo("guardarCerrar");
		dia_movimientos.setDialogo(gri_datos_asiento);
		agregarComponente(dia_movimientos);
		
		Boton bot_generar_transacciones = new Boton();
		bot_generar_transacciones.setValue("Generar Transacciones");
		bot_generar_transacciones.setMetodo("generarTransacciones");
		bar_botones.agregarBoton(bot_generar_transacciones);
		
	}
	public void guardarCerrar(){
		guardarPantalla();
		dia_movimientos.cerrar();
	}
	// generara transacciones contables 
	public void generarTransacciones(){
		double dou_valor_debe=0;
		double dou_valor_haber=0;
		System.out.println("entre a fromar "+tab_movimiento.getValor("ide_comov"));
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeError("No se puede insertar", "Debe Seleccionar un Año");
			return;
		}
		if(tab_movimiento.getValor("ide_comov")==null){
			utilitario.agregarMensajeError("No se puede insertar", "Debe Guardaruna Cabecera de Movimiento Contable");
			return;
		}
		
		if(dia_movimientos.isVisible()){
			
			tab_detalle_movimiento.insertar();
			if(com_lugar_aplica.getValue().equals(p_debe)){
				dou_valor_debe=Double.parseDouble(txt_valor.getValue().toString());
			}
			if(com_lugar_aplica.getValue().equals(p_haber)){
				dou_valor_haber=Double.parseDouble(txt_valor.getValue().toString());
			}
			
			tab_detalle_movimiento.setValor("ide_gelua", com_lugar_aplica.getValue().toString());
			tab_detalle_movimiento.setValor("debe_codem", dou_valor_debe+"");
			tab_detalle_movimiento.setValor("haber_codem", dou_valor_haber+"");
			tab_detalle_movimiento.setValor("ide_cocac",aut_catalogo.getValor());
			tab_detalle_movimiento.setValor("ide_tepro", aut_proveedor.getValor());
			tab_detalle_movimiento.setValor("ide_tetic", com_tipo_concepto.getValue().toString());
			tab_detalle_movimiento.setValor("ide_comov", tab_movimiento.getValor("ide_comov"));
			tab_detalle_movimiento.setValor("transferencia_codem", chk_transferencia.getValue().toString());

			tab_detalle_movimiento.guardar();
			tab_detalle_movimiento.sumarColumnas();
			utilitario.addUpdate("tab_detalle_movimiento");

		}
		else{
			dia_movimientos.dibujar();
		}
	}
	
	//// lugar aplica
	public void lugarAplica(AjaxBehaviorEvent evt){
		tab_detalle_movimiento.modificar(evt);
	    if(tab_detalle_movimiento.getValor("ïde_gelua").equals(p_debe)){
			tab_detalle_movimiento.setValor("haber_codem", "0");
			utilitario.addUpdate("");
		}
	    if(tab_detalle_movimiento.getValor("ïde_gelua").equals(p_haber)){
			tab_detalle_movimiento.setValor("debe_codem", "0");
		}
		utilitario.addUpdate("debe_codem,haber_codem");

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
			tab_movimiento.setValor("nro_comprobante_comov", ser_contabilidad.numeroSecuencial(p_modulo_contabilidad));
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
						p_parametros=new HashMap();				
						rep_reporte.cerrar();				
						//		p_parametros.put("IDE_GTEMP",Long.parseLong(tab_anticipo.getValor("IDE_GTEMP")));
						TablaGenerica tab_valor = utilitario.consultar(ser_Tesoreria.getConsulValorPagoContabilidad(tab_movimiento.getValor("ide_comov")));
						p_parametros.put("titulo", "EMGIRS - EP");
						p_parametros.put("p_contador_general", utilitario.getVariable("p_nombre_contador"));
						p_parametros.put("p_cuota_mensual",utilitario.getLetrasDolarNumero(utilitario.getFormatoNumero(tab_valor.getValor("valor"),2)));
						p_parametros.put("p_ide_comov",Integer.parseInt(tab_movimiento.getValor("ide_comov")));
						
						p_parametros.put("REPORT_LOCALE", locale);
						sef_reporte.setSeleccionFormatoReporte(p_parametros, rep_reporte.getPath());						
						sef_reporte.dibujar();
					
			}
			else{
				utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun registro en la cabecera del anticipo");
			}
		}
		
	
		
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
	public Dialogo getDia_movimientos() {
		return dia_movimientos;
	}
	public void setDia_movimientos(Dialogo dia_movimientos) {
		this.dia_movimientos = dia_movimientos;
	}
	public AutoCompletar getAut_catalogo() {
		return aut_catalogo;
	}
	public void setAut_catalogo(AutoCompletar aut_catalogo) {
		this.aut_catalogo = aut_catalogo;
	}
	public AutoCompletar getAut_proveedor() {
		return aut_proveedor;
	}
	public void setAut_proveedor(AutoCompletar aut_proveedor) {
		this.aut_proveedor = aut_proveedor;
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
	public Map getP_parametros() {
		return p_parametros;
	}
	public void setP_parametros(Map p_parametros) {
		this.p_parametros = p_parametros;
	}

	

}
