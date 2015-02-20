package paq_tesoreria;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_adquisicion.ejb.ServicioAdquisicion;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_comprobante_pago extends Pantalla{
	private Tabla tab_comprobante = new Tabla();
	private Tabla tab_detalle_movimiento =new Tabla();
	private Tabla tab_retencion =new Tabla();
	private Tabla tab_detalle_retencion=new Tabla();
	private AutoCompletar aut_movimiento=new AutoCompletar();
	private SeleccionTabla set_solicitud=new SeleccionTabla();
	private SeleccionTabla set_tramite=new SeleccionTabla();

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
	
	public pre_comprobante_pago (){

		Tabulador tab_tabulador = new Tabulador();
		tab_tabulador.setId("tab_tabulador");

		tab_comprobante.setId("tab_comprobante");
		tab_comprobante.setHeader("COMPROBANTE DE PAGO");
		tab_comprobante.setTabla("tes_comprobante_pago", "ide_tecpo", 1);
		tab_comprobante.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest","detalle_coest","");
		tab_comprobante.getColumna("IDE_PRTRA").setCombo(ser_Presupuesto.getTramite("true"));
		tab_comprobante.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest","detalle_coest","");
		tab_comprobante.getColumna("IDE_TEPRO").setCombo(ser_Bodega.getProveedor("true,false"));
		tab_comprobante.getColumna("IDE_GEDIP").setCombo(ser_gestion.getSqlDivisionPoliticaCiudad());
		tab_comprobante.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_comprobante.getColumna("IDE_ADSOC").setCombo(ser_Adquisicion.getSolicitudCompra("true"));
		tab_comprobante.setTipoFormulario(true);
		tab_comprobante.getGrid().setColumns(4);
		tab_comprobante.agregarRelacion(tab_retencion);
		tab_comprobante.dibujar();
		PanelTabla pat_comprobante=new PanelTabla();
		pat_comprobante.setPanelTabla(tab_comprobante);

		tab_detalle_movimiento.setId("tab_detalle_movimiento");
		tab_detalle_movimiento.setIdCompleto("tab_tabulador:tab_detalle_movimiento");
		tab_detalle_movimiento.setHeader("DETALLE MOVIMIENTO");
		tab_detalle_movimiento.setTabla("cont_detalle_movimiento", "ide_codem", 2);
		//filtra por asiento contable cuando no tiene relacion a tes_comprovante_pago
		tab_detalle_movimiento.setCondicion("ide_comov=-1");
		tab_detalle_movimiento.dibujar();
		PanelTabla pat_movimiento=new PanelTabla();
		pat_movimiento.setPanelTabla(tab_detalle_movimiento);

		tab_retencion.setId("tab_retencion");
		tab_retencion.setIdCompleto("tab_tabulador:tab_retencion");
		tab_retencion.setHeader("RETENCION");
		tab_retencion.setTabla("tes_retencion", "ide_teret", 2);
		tab_retencion.dibujar();
		PanelTabla pat_retencion =new PanelTabla();
		pat_retencion.setPanelTabla(tab_retencion);

		tab_detalle_retencion.setId("tab_detalle_retencion");
		tab_detalle_retencion.setIdCompleto("tab_tabulador:tab_detalle_retencion");
		tab_detalle_retencion.setHeader("DETALLE RETENCION");
		tab_detalle_retencion.setTabla("tes_detalle_retencion", "ide_teder", 3);
		tab_detalle_retencion.dibujar();
		PanelTabla pat_detalle_retencion=new PanelTabla();
		pat_detalle_retencion.setPanelTabla(tab_detalle_retencion);


		tab_tabulador.agregarTab("DETALLE MOVIMIENTO", pat_movimiento);//intancia los tabuladores 
		tab_tabulador.agregarTab("RETENCION", pat_retencion);
		tab_tabulador.agregarTab("DETALLE RETENCION", pat_detalle_retencion);

		Division div_division =new Division();
		div_division.dividir2(pat_comprobante, tab_tabulador, "50%", "H");
		agregarComponente(div_division);

		Boton bot_buscar=new Boton();
		bot_buscar.setIcon("ui-icon-person");
		bot_buscar.setValue("Buscar Solicitud Compra ");
		bot_buscar.setMetodo("importarSolicitudCompra");
		bar_botones.agregarBoton(bot_buscar);

		set_solicitud.setId("set_solicitud");
		set_solicitud.setSeleccionTabla(ser_Adquisicion.getSolicitudCompra("true"),"ide_adsoc");
		set_solicitud.setTitle("SELECCION UNA SOLICITUD DE COMPRA");		
		set_solicitud.getBot_aceptar().setMetodo("aceptarSolicitudCompra");
		set_solicitud.setRadio();
		agregarComponente(set_solicitud);
		
		Boton bot_busca=new Boton();
		bot_busca.setIcon("ui-icon-person");
		bot_busca.setValue("Buscar Certificaciòn Presupuestaria ");
		bot_busca.setMetodo("importarCertificacionPresupuestaria");
		bar_botones.agregarBoton(bot_busca);

		set_tramite.setId("set_tramite");
		set_tramite.setSeleccionTabla(ser_Presupuesto.getTramite("true"),"ide_prtra");
		set_tramite.setTitle("SELECCION UNA CERTIFICACION PRESUPUESTARIA");		
		set_tramite.getBot_aceptar().setMetodo("aceptarCertificacionPresupuestaria");
		set_tramite.setRadio();
		agregarComponente(set_tramite);


	}
	public void importarSolicitudCompra(){

		set_solicitud.getTab_seleccion().setSql(ser_Adquisicion.getSolicitudCompra("true"));
		set_solicitud.getTab_seleccion().ejecutarSql();
		set_solicitud.dibujar();

	}

	public void aceptarSolicitudCompra(){
		
		String str_seleccionado = set_solicitud.getValorSeleccionado();
		TablaGenerica tab_solicitud=ser_Adquisicion.getTablaGenericaSolicitud(str_seleccionado);
		if (str_seleccionado!=null){
			tab_comprobante.insertar();
			tab_comprobante.setValor("ide_adsoc",str_seleccionado);
			tab_comprobante.setValor("valor_pago_tecpo",tab_solicitud.getValor("total_adfac"));
			tab_comprobante.setValor("ide_tepro",tab_solicitud.getValor("ide_tepro"));
		}
		set_solicitud.cerrar();
		utilitario.addUpdate("tab_comprobante");
	}

	public void importarCertificacionPresupuestaria(){

		set_tramite.getTab_seleccion().setSql(ser_Presupuesto.getTramite("true"));
		set_tramite.getTab_seleccion().ejecutarSql();
		set_tramite.dibujar();

	}

	public void aceptarCertificacionPresupuestaria(){
		
		String str_seleccionado = set_tramite.getValorSeleccionado();
		TablaGenerica tab_tramite=ser_Presupuesto.getTablaGenericaTramite(str_seleccionado);
		if (str_seleccionado!=null){
			tab_comprobante.insertar();
			tab_comprobante.setValor("ide_prtra",str_seleccionado);
		}
		set_tramite.cerrar();
		utilitario.addUpdate("tab_comprobante");
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (tab_comprobante.isFocus()) {
			tab_comprobante.insertar();
		}

		else if (tab_detalle_movimiento.isFocus()){
			tab_detalle_movimiento.insertar();

		}
		else if (tab_retencion.isFocus()) {
			tab_retencion.insertar();

		}
		else if (tab_detalle_retencion.isFocus()) {
			tab_detalle_retencion.insertar();

		}	
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_comprobante.guardar()) {

			if (tab_detalle_movimiento.guardar()) {
				if( tab_retencion.guardar()){
					if(tab_detalle_retencion.guardar()){

					}
				}
			}
			guardarPantalla();

		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

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


}
