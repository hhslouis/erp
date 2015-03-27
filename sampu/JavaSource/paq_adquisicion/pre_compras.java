package paq_adquisicion;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_adquisicion.ejb.ServicioAdquisicion;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;
import paq_sistema.aplicacion.Utilitario;
import paq_sistema.ejb.ServicioSeguridad;

public class pre_compras extends Pantalla{

	public static String par_estado_modulo_compra;
	public static String par_modulo_adquisicion;
	public static String par_adquisicion;
	public static String par_certificacion;
	public static String par_solicitud;

	private SeleccionTabla set_tipo_compra=new SeleccionTabla();
	private SeleccionTabla set_certificacion=new SeleccionTabla();
	private SeleccionTabla set_adjudicado=new SeleccionTabla();
	private SeleccionTabla set_proveedor=new SeleccionTabla();


	private Tabla tab_compras=new Tabla();
	@EJB
	private ServicioAdquisicion ser_Adquisicion =(ServicioAdquisicion)utilitario.instanciarEJB(ServicioAdquisicion.class);
	
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina ) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioBodega ser_Bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	@EJB
	private ServicioContabilidad ser_estados = (ServicioContabilidad) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioSeguridad ser_seguridad = (ServicioSeguridad) utilitario.instanciarEJB(ServicioSeguridad.class);

	private Map p_parametros = new HashMap();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte self_reporte = new SeleccionFormatoReporte();
	private Map map_parametros = new HashMap();
	private String empleado;
	

	public pre_compras(){
		
		empleado=ser_seguridad.getUsuario(utilitario.getVariable("ide_usua")).getValor("ide_gtemp");
		System.out.println("empleado"+empleado);
		if(empleado==null ||empleado.isEmpty()){
			utilitario.agregarNotificacionInfo("Mensaje", "No exixte usuario registrado para el registro de compras");
			return;
		}
		par_modulo_adquisicion =utilitario.getVariable("p_modulo_adquisicion");
		par_estado_modulo_compra =utilitario.getVariable("p_estado_modulo_compra");
		par_adquisicion=utilitario.getVariable("p_cotizacion_adquisicion");
		par_certificacion=utilitario.getVariable("p_certificacion_adquisicion");
		par_solicitud=utilitario.getVariable("p_solicitud_pago_adquisicion");

		rep_reporte.setId("rep_reporte"); //id
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");//ejecuta el metodo al aceptar reporte
		agregarComponente(rep_reporte);//agrega el componente a la pantalla
		bar_botones.agregarReporte();//aparece el boton de reportes en la barra de botones
		self_reporte.setId("self_reporte"); //id
		agregarComponente(self_reporte);
		tab_compras.setId("tab_compras");
		tab_compras.setHeader("HOJA DE REQUERIMIENTO");
		tab_compras.setTabla("adq_solicitud_compra", "ide_adsoc", 1);
		tab_compras.setCampoOrden("ide_adsoc desc");
		tab_compras.getColumna("ide_coest").setCombo("cont_estado","ide_coest", "detalle_coest","");
		tab_compras.getColumna("ide_coest").setLectura(true);
		tab_compras.getColumna("ide_coest").setAutoCompletar();
		tab_compras.getColumna("ide_copag").setCombo("cont_parametros_general","ide_copag", "detalle_copag", "");
		tab_compras.getColumna("ide_copag").setLectura(true);
		tab_compras.getColumna("ide_copag").setAutoCompletar();
		tab_compras.getColumna("ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_compras.getColumna("ide_geedp").setLectura(true);
		tab_compras.getColumna("ide_geedp").setAutoCompletar();
		tab_compras.getColumna("ide_tepro").setCombo(ser_Bodega.getProveedor("true,false"));
		tab_compras.getColumna("ide_tepro").setLectura(true);
		tab_compras.getColumna("ide_tepro").setAutoCompletar();
		tab_compras.getColumna("ide_prtra").setCombo(ser_Adquisicion.getTramite("true,false"));
		tab_compras.getColumna("ide_prtra").setLectura(true);
		tab_compras.getColumna("ide_prtra").setAutoCompletar();
		tab_compras.getColumna("fecha_proforma2_adsoc").setVisible(false);
		tab_compras.getColumna("valor_proforma2_adsoc").setVisible(false);
		tab_compras.getColumna("factura_proforma2_adsoc").setVisible(false);
		tab_compras.getColumna("oferente2_adsoc").setVisible(false);
		tab_compras.getColumna("fecha_proforma1_adsoc").setVisible(false);
		tab_compras.getColumna("factura_proforma1_adsoc").setVisible(false);
		tab_compras.getColumna("valor_proforma1_adsoc").setVisible(false);
		tab_compras.getColumna("proforma_proveedor_adsoc").setVisible(false);
		tab_compras.getColumna("fecha_proforma_proveedor_adsoc").setVisible(false);
		tab_compras.getColumna("oferente1_adsoc").setVisible(false);
		tab_compras.getColumna("fecha_registro").setValorDefecto(utilitario.getFechaActual());
		tab_compras.getColumna("activo_adsoc").setValorDefecto("true");
		tab_compras.getColumna("activo_adsoc").setLectura(true);
		tab_compras.getColumna("aprobado_adsoc").setLectura(true);
		tab_compras.getColumna("ide_gtemp").setCombo(ser_nomina.servicioEmpleadosActivos("true,false"));
		tab_compras.getColumna("ide_gtemp").setLectura(true);
		tab_compras.getColumna("ide_gtemp").setAutoCompletar();
		tab_compras.setTipoFormulario(true);
		tab_compras.getGrid().setColumns(4);
		tab_compras.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_compras);
		agregarComponente(pat_panel1);

		set_tipo_compra.setId("set_tipo_compra");
		//set_empleado.setHeader("IMPORTAR EMPLEADO"+ser_nomina.ideEmpleadoContrato(tab_tiket_viaje.getValor("ide_geedp")).getStringColumna("nombres_apellidos"));

		set_tipo_compra.setSeleccionTabla(ser_contabilidad.getModuloParametros("true", par_modulo_adquisicion),"ide_copag");


		//set_empleado.getTab_seleccion().getColumna("nombre_apellido").setFiltro(true);
		set_tipo_compra.setTitle("Seleccione el tipo de compra");
		set_tipo_compra.getBot_aceptar().setMetodo("aceptarCompra");
		set_tipo_compra.setRadio();
		agregarComponente(set_tipo_compra);	

		Boton bot_cotizacion = new Boton();
		bot_cotizacion.setValue("Cotizacion");
		bot_cotizacion.setTitle("COTIZACION");
		bot_cotizacion.setIcon("ui-icon-person");
		bot_cotizacion.setMetodo("aceptarCotizacion");
		bar_botones.agregarBoton(bot_cotizacion);

		Boton bot_certificacion = new Boton();
		bot_certificacion.setValue("Agregar Certificaciòn Presupuestaria");
		bot_certificacion.setTitle("CERTIFICACION PRESUPUESTARIA");
		bot_certificacion.setIcon("ui-icon-person");
		bot_certificacion.setMetodo("importarCertificacion");
		bar_botones.agregarBoton(bot_certificacion);
		
		set_certificacion.setId("set_certificacion");
		set_certificacion.setSeleccionTabla(ser_Adquisicion.getTramite("true,false"),"ide_prtra");
		set_certificacion.getTab_seleccion().getColumna("fecha_tramite_prtra").setFiltro(true);
		set_certificacion.getTab_seleccion().getColumna("numero_oficio_prtra").setFiltro(true);
		set_certificacion.setTitle("Seleccione Certificaciòn");
		set_certificacion.getBot_aceptar().setMetodo("aceptarCertificacion");
		set_certificacion.setRadio();
		agregarComponente(set_certificacion);	
		
		

		Boton bot_adjudicado = new Boton();
		bot_adjudicado.setValue("Adjudicado");
		bot_adjudicado.setTitle("ADJUDICADO");
		bot_adjudicado.setIcon("ui-icon-person");
		bot_adjudicado.setMetodo("importarAdjudicado");
		bar_botones.agregarBoton(bot_adjudicado);
		
		set_adjudicado.setId("set_adjudicado");
		set_adjudicado.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		set_adjudicado.getTab_seleccion().getColumna("NOMBRES_APELLIDOS").setFiltro(true);
		set_adjudicado.getTab_seleccion().getColumna("DOCUMENTO_IDENTIDAD_GTEMP").setFiltro(true);
		set_adjudicado.setTitle("Seleccione Adjudicado");
		set_adjudicado.getBot_aceptar().setMetodo("aceptarAdjudicado");
		set_adjudicado.setRadio();
		agregarComponente(set_adjudicado);
		
		
		Boton bot_proveedor = new Boton();
		bot_proveedor.setValue("Proveedor");
		bot_proveedor.setTitle("PROVEEDOR");
		bot_proveedor.setIcon("ui-icon-person");
		bot_proveedor.setMetodo("importarProveedor");
		bar_botones.agregarBoton(bot_proveedor);
		
		set_proveedor.setId("set_proveedor");
		set_proveedor.setSeleccionTabla(ser_Bodega.getProveedor("true"),"ide_tepro");
		set_proveedor.getTab_seleccion().getColumna("nombre_tepro").setFiltro(true);
		set_proveedor.getTab_seleccion().getColumna("ruc_tepro").setFiltro(true);
		set_proveedor.setTitle("Seleccione Proveedor");
		set_proveedor.getBot_aceptar().setMetodo("aceptarProveedor");
		set_proveedor.setRadio();
		agregarComponente(set_proveedor);
		
		
	}
	public void importarProveedor(){

		set_proveedor.setSeleccionTabla(ser_Bodega.getProveedor("true"),"ide_tepro");
		set_proveedor.getTab_seleccion().ejecutarSql();
		set_proveedor.dibujar();

	}

	public  void aceptarProveedor(){
		String str_seleccionado = set_proveedor.getValorSeleccionado();
		TablaGenerica tab_proveedor=ser_Bodega.getTablaProveedor(str_seleccionado);
		if (str_seleccionado!=null){
			tab_compras.setValor("ide_tepro",str_seleccionado);
			tab_compras.modificar(tab_compras.getFilaActual());
			tab_compras.guardar(); 
			guardarPantalla();
		}
		set_proveedor.cerrar();
		utilitario.addUpdate("tab_compras");
	}
		
	
	public void aceptarCotizacion(){
		if(tab_compras.getValor("ide_coest").equals(par_estado_modulo_compra)){
		tab_compras.setValor("ide_coest",par_adquisicion); 
		tab_compras.modificar(tab_compras.getFilaActual());
		tab_compras.guardar(); 
		guardarPantalla();
		utilitario.addUpdate("tab_compras");
		}else{
			utilitario.agregarMensajeInfo("Cotizacion","Para cotizar el estado de compra debe encontrarce en estado de compra ");
		}

	}
	public void importarCertificacion(){
	
		set_certificacion.getTab_seleccion().setSql(ser_Adquisicion.getTramite("true"));
		set_certificacion.getTab_seleccion().ejecutarSql();
		set_certificacion.dibujar();
		tab_compras.setValor("ide_coest",par_certificacion); 
		tab_compras.setValor("ide_prtra",set_certificacion.getValorSeleccionado()); 
		//tab_compras.modificar();
		tab_compras.guardar(); 
		guardarPantalla();
		utilitario.addUpdate("tab_compras");

	}
	public void aceptarCertificacion(){

		String str_seleccionado = set_certificacion.getValorSeleccionado();
		TablaGenerica tab_certificacion=ser_Adquisicion.getTablaGenericaTramite(str_seleccionado);
		if (str_seleccionado!=null){
			tab_compras.setValor("ide_prtra",str_seleccionado);
			tab_compras.setValor("valor_adsoc",tab_certificacion.getValor("total_compromiso_prtra"));
			tab_compras.modificar(tab_compras.getFilaActual());
			tab_compras.guardar(); 
			guardarPantalla();
					
		}
		set_certificacion.cerrar();
		utilitario.addUpdate("tab_compras");
	}
	public void importarAdjudicado(){
		
		set_adjudicado.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
		set_adjudicado.getTab_seleccion().ejecutarSql();
		set_adjudicado.dibujar();
		tab_compras.setValor("ide_coest",par_solicitud); 
		tab_compras.setValor("ide_geedp",set_adjudicado.getValorSeleccionado()); 
		utilitario.addUpdate("tab_compras");

	}
	public void aceptarAdjudicado(){

		String str_seleccionado = set_adjudicado.getValorSeleccionado();
		TablaGenerica tab_empleado=ser_nomina.ideEmpleadoContrato(str_seleccionado);
		if (str_seleccionado!=null){
			tab_compras.setValor("ide_geedp",str_seleccionado);
			tab_compras.setValor("aprobado_adsoc","true"); 
			tab_compras.modificar(tab_compras.getFilaActual());
			tab_compras.guardar(); 
			guardarPantalla();
			//tab_compras.setValor("valor_adsoc",tab_empleado.getValor("total_compromiso_prtra"));
					
		}
		set_adjudicado.cerrar();
		utilitario.addUpdate("tab_compras");
	}
	//reporte
	public void abrirListaReportes() {
		// TODO Auto-generated method stub
		rep_reporte.dibujar();
	}
	public void aceptarCompra(){
		tab_compras.setValor("ide_copag", set_tipo_compra.getValorSeleccionado());
		utilitario.addUpdate("tab_compras");
		set_tipo_compra.cerrar();

	}
	public void aceptarReporte(){
		if(rep_reporte.getReporteSelecionado().equals("Solucitud Compra"));{
			if (rep_reporte.isVisible()){
				p_parametros=new HashMap();		
				rep_reporte.cerrar();	
				p_parametros.put("Titulo","Solucitud Compra");
				p_parametros.put("ide_usua",Integer.parseInt("7"));
				p_parametros.put("ide_empr",Integer.parseInt("0"));
				p_parametros.put("ide_sucu",Integer.parseInt("1"));
				//p_parametros.put("pide_fafac",Integer.parseInt(tab_cont_viajeros.getValor("ide_fanoc")));
				self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
				self_reporte.dibujar();

			}
		}
	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_compras.insertar();

		String ide_gtempxx=ser_seguridad.getUsuario(utilitario.getVariable("ide_usua")).getValor("ide_gtemp");
		tab_compras.setValor("ide_gtemp",ide_gtempxx );
		tab_compras.setValor("ide_coest",par_estado_modulo_compra);
		set_tipo_compra.getTab_seleccion().setSql(ser_contabilidad.getModuloParametros("true", par_modulo_adquisicion));
		set_tipo_compra.getTab_seleccion().ejecutarSql();
		set_tipo_compra.dibujar();
		utilitario.addUpdate("tab_compras");

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_compras.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_compras.eliminar();
	}
	public Tabla getTab_compras() {
		return tab_compras;
	}
	public void setTab_compras(Tabla tab_compras) {
		this.tab_compras = tab_compras;
	}
	public Map getP_parametros() {
		return p_parametros;
	}
	public void setP_parametros(Map p_parametros) {
		this.p_parametros = p_parametros;
	}
	public Reporte getRep_reporte() {
		return rep_reporte;
	}
	public void setRep_reporte(Reporte rep_reporte) {
		this.rep_reporte = rep_reporte;
	}
	public SeleccionFormatoReporte getSelf_reporte() {
		return self_reporte;
	}
	public void setSelf_reporte(SeleccionFormatoReporte self_reporte) {
		this.self_reporte = self_reporte;
	}
	public Map getMap_parametros() {
		return map_parametros;
	}
	public void setMap_parametros(Map map_parametros) {
		this.map_parametros = map_parametros;
	}
	
	public SeleccionTabla getSet_certificacion() {
		return set_certificacion;
	}

	public void setSet_certificacion(SeleccionTabla set_certificacion) {
		this.set_certificacion = set_certificacion;
	}

	public SeleccionTabla getSet_tipo_compra() {
		return set_tipo_compra;
	}
	public void setSet_tipo_compra(SeleccionTabla set_tipo_compra) {
		this.set_tipo_compra = set_tipo_compra;
	}

	public SeleccionTabla getSet_adjudicado() {
		return set_adjudicado;
	}

	public void setSet_adjudicado(SeleccionTabla set_adjudicado) {
		this.set_adjudicado = set_adjudicado;
	}
	public SeleccionTabla getSet_proveedor() {
		return set_proveedor;
	}
	public void setSet_proveedor(SeleccionTabla set_proveedor) {
		this.set_proveedor = set_proveedor;
	}
	
}
