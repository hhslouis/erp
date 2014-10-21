package paq_salud;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import paq_anticipos.ejb.ServicioAnticipo;
import paq_gestion.ejb.ServicioEmpleado;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.cls_graficas;
import paq_nomina.pre_rep_gerencial;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;
import framework.aplicacion.Fila;
import framework.aplicacion.TablaGenerica;
import framework.componentes.Ajax;
import framework.componentes.AreaTexto;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Confirmar;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.ItemMenu;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import framework.componentes.Texto;


public class pre_custodio_dotacion extends Pantalla {

	private Tabla tab_custodio=new Tabla();
	private Tabla tab_dotacion_uniforme=new Tabla();	

	private AutoCompletar aut_empleado = new AutoCompletar();	
	@EJB
	private ServicioEmpleado ser_empleado=(ServicioEmpleado) utilitario.instanciarEJB(ServicioEmpleado.class);
	@EJB
	private ServicioNomina ser_nomina=(ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);

	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);

	private Division div_division=new Division();
	private Reporte rep_reporte=new Reporte();
	private SeleccionFormatoReporte sef_reporte=new SeleccionFormatoReporte();
	private Map p_parametros=new HashMap();
	private SeleccionTabla sel_tab_sucursal=new SeleccionTabla();
	private SeleccionTabla sel_tab_area=new SeleccionTabla();
	private SeleccionTabla sel_tab_departamento =new SeleccionTabla();
	private SeleccionTabla sel_tab_empleado =new SeleccionTabla();


	public pre_custodio_dotacion() {

		// boton limpiar
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");


		// autocompletar empleado
		aut_empleado.setId("aut_empleado");
		String str_sql_emp=ser_gestion.getSqlEmpleadosAutocompletar();
		aut_empleado.setAutoCompletar(str_sql_emp);
		aut_empleado.setMetodoChange("filtrarAnticiposEmpleado");

		Etiqueta eti_colaborador=new Etiqueta("Empleado:");
		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_empleado);
		bar_botones.agregarBoton(bot_limpiar);
		bar_botones.agregarReporte();


		//  FICHA CUSTODIO (division 1)

		tab_custodio.setId("tab_custodio");
		tab_custodio.setTabla("SAO_CUSTODIO", "IDE_SACUS", 1);
		tab_custodio.getColumna("ACTIVO_SACUS").setCheck();
		tab_custodio.getColumna("ACTIVO_SACUS").setValorDefecto("true");
		tab_custodio.agregarRelacion(tab_dotacion_uniforme);	
		tab_custodio.getColumna("TIPO_SACUS").setValorDefecto("0");
		tab_custodio.getColumna("TIPO_SACUS").setVisible(false);
		tab_custodio.getColumna("IDE_GTEMP").setVisible(false);
		tab_custodio.getColumna("IDE_GEEDP").setVisible(false);		
		tab_custodio.getColumna("IDE_SAUBS").setAutoCompletar();
		tab_custodio.getColumna("IDE_SABIE").setVisible(false);		
		tab_custodio.getColumna("FOTO_SACUS").setVisible(false);
		tab_custodio.getColumna("COD_BARRAS_SACUS").setVisible(false);
		tab_custodio.getColumna("CANTIDAD_SACUS").setVisible(false);
		tab_custodio.getColumna("SAO_IDE_SACUS").setVisible(false);
		tab_custodio.getColumna("CUSTODIO_SACUS").setVisible(false);
		tab_custodio.getColumna("IDE_SAUBS").setCombo("SELECT a.ide_saubs,c.nom_sucu,b.DETALLE_SAUBI FROM SAO_UBICACION_SUCURSAL a " +
				"LEFT JOIN( " +
				"SELECT IDE_SAUBI,DETALLE_SAUBI FROM sao_ubicacion " +
				")b ON b.IDE_SAUBI=a.IDE_SAUBI " +
				"left join ( " +
				"SELECT ide_sucu,nom_sucu FROM sis_sucursal " +
				")c on c.ide_sucu=a.ide_sucu");

		tab_custodio.getGrid().setColumns(6);
		tab_custodio.setTipoFormulario(true);

		tab_custodio.setCondicion("IDE_SACUS=-1");		
		tab_custodio.dibujar();

		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_custodio);
		tab_custodio.setHeader("CUSTODIOS");

		//  DETALLE DOTACIONES DE INIFORME
		tab_dotacion_uniforme.setId("tab_dotacion_uniforme");		
		tab_dotacion_uniforme.setTabla("SAO_DOTACION_UNIFORME", "IDE_SADOU", 2);
		tab_dotacion_uniforme.getColumna("ACTIVO_SADOU").setCheck();
		tab_dotacion_uniforme.getColumna("ACTIVO_SADOU").setValorDefecto("true");
		tab_dotacion_uniforme.getColumna("FOTO_SADOU").setUpload("nueva_carpeta");
		tab_dotacion_uniforme.getColumna("FOTO_SADOU").setImagen("", "");
		tab_dotacion_uniforme.getColumna("IDE_SAGRB").setCombo("SAO_GRUPO_BIEN", "IDE_SAGRB", "DETALLE_SAGRB", "");
		tab_dotacion_uniforme.getColumna("IDE_SACOL").setCombo("SAO_COLOR", "IDE_SACOL", "DETALLE_SACOL", "");		
		tab_dotacion_uniforme.setHeader("DOTACION DE UNIFORMES");
		tab_dotacion_uniforme.dibujar();

		PanelTabla pat_panel2=new PanelTabla();

		pat_panel2.setPanelTabla(tab_dotacion_uniforme);


		//  DIVISION DE LA PANTALLA


		div_division.setId("div_division");
		div_division.dividir2(pat_panel1,pat_panel2,"50%","H");
		agregarComponente(div_division);


		sel_tab_sucursal.setId("sel_tab_sucursal");
		sel_tab_sucursal.setTitle("SELECCION DE SUCURSAL");
		sel_tab_sucursal.setSeleccionTabla("SELECT ide_sucu,nom_sucu  FROM sis_sucursal where ide_sucu=-1 order by  nom_sucu", "ide_sucu");
		sel_tab_sucursal.getTab_seleccion().getColumna("nom_sucu").setFiltro(true);
		sel_tab_sucursal.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_tab_sucursal);

		sel_tab_area.setId("sel_tab_area");
		sel_tab_area.setTitle("SELECCION DE AREAS");
		sel_tab_area.setSeleccionTabla("SELECT ide_geare,detalle_geare FROM gen_area where ide_geare=-1", "ide_geare");
		sel_tab_area.getTab_seleccion().getColumna("detalle_geare").setFiltro(true);
		sel_tab_area.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_tab_area);

		sel_tab_departamento.setId("sel_tab_departamento");
		sel_tab_departamento.setTitle("SELECCION DE DEPARTAMENTOS");
		sel_tab_departamento.setSeleccionTabla("SELECT ide_gedep,detalle_gedep FROM gen_departamento where ide_geare in(-1) order by detalle_gedep", "ide_gedep");
		sel_tab_departamento.getTab_seleccion().getColumna("detalle_gedep").setFiltro(true);
		sel_tab_departamento.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_tab_departamento);
		
		sel_tab_empleado.setId("sel_tab_empleado");
		sel_tab_empleado.setTitle("EMPLEADOS");
		sel_tab_empleado.setSeleccionTabla("SELECT ide_gtemp,apellido_paterno_gtemp || ' '||apellido_materno_gtemp||' '||primer_nombre_gtemp||' '|| " +
				"segundo_nombre_gtemp as nombre_empleado,documento_identidad_gtemp as documento FROM gth_empleado where ide_gtemp=-1", "IDE_GTEMP");
		sel_tab_empleado.getTab_seleccion().getColumna("nombre_empleado").setFiltro(true);
		sel_tab_empleado.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_tab_departamento);

		
		agregarComponente(rep_reporte);
		agregarComponente(sef_reporte);

		

	}

	String ide_geedp_activo="";
	public void filtrarAnticiposEmpleado(SelectEvent evt){
		aut_empleado.onSelect(evt);
		ide_geedp_activo=ser_gestion.getIdeContratoActivo(aut_empleado.getValor());
		tab_custodio.setCondicion("IDE_GTEMP="+aut_empleado.getValor());
		tab_custodio.ejecutarSql();
		tab_dotacion_uniforme.ejecutarValorForanea(tab_custodio.getValorSeleccionado());

	}

	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	public void limpiar() {
		tab_custodio.limpiar();
		tab_dotacion_uniforme.limpiar();
		ide_geedp_activo="";
		aut_empleado.limpiar();
		utilitario.addUpdate("aut_empleado");// limpia y refresca el autocompletar
	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (tab_custodio.isFocus()){
			if (aut_empleado.getValor()!=null){
				if (ide_geedp_activo!=null && !ide_geedp_activo.isEmpty()){		
					tab_custodio.insertar();
					tab_custodio.setValor("IDE_GEEDP",ide_geedp_activo);
					tab_custodio.setValor("IDE_GTEMP", aut_empleado.getValor());				
				}else{
					utilitario.agregarMensajeInfo("No se puede insertar", "El contrato del empleado no esta activo");
				}
			}else{
				utilitario.agregarMensajeInfo("No se puede insertar", "Debe seleccionar el Empleado");
			}
		}
		else if (tab_dotacion_uniforme.isFocus()){
			if (aut_empleado.getValor()!=null){
				if (tab_custodio.getTotalFilas()>0){
					tab_dotacion_uniforme.insertar();					
				}else{
					utilitario.agregarMensajeInfo("No se puede insertar", "Primero debe insertar una evaluacion de posiciograma");				
				}
			}else{
				utilitario.agregarMensajeInfo("No se puede insertar", "Debe seleccionar el Empleado");
			}			
		}	

	}

	@Override
	public void guardar() {		
		if (aut_empleado.getValor()!=null){
			if (tab_custodio.guardar()){
				if(tab_dotacion_uniforme.guardar()){									
					guardarPantalla();																		
				}
			}
		}else{
			utilitario.agregarMensajeInfo("No se puede guardar el Anticipo", "Debe seleccionar un Empleado");
		}
	}

	@Override
	public void eliminar() {
		if (aut_empleado.getValor()!=null){			
			if (tab_custodio.isFocus()){	
				tab_custodio.eliminar();
			}else if(tab_dotacion_uniforme.isFocus()){
				tab_dotacion_uniforme.eliminar();
			}				
		}else{
			utilitario.agregarMensajeInfo("No se puede guardar el Anticipo", "Debe seleccionar un Empleado");
		}
	}

	public AutoCompletar getAut_empleado() {
		return aut_empleado;
	}

	public void setAut_empleado(AutoCompletar aut_empleado) {
		this.aut_empleado = aut_empleado;
	}

	public Tabla gettab_custodio() {
		return tab_custodio;
	}

	public void settab_custodio(Tabla tab_custodio) {
		this.tab_custodio = tab_custodio;
	}

	public Tabla gettab_dotacion_uniforme() {
		return tab_dotacion_uniforme;
	}

	public void settab_dotacion_uniforme(Tabla tab_dotacion_uniforme) {
		this.tab_dotacion_uniforme = tab_dotacion_uniforme;
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

	public SeleccionTabla getSel_tab_sucursal() {
		return sel_tab_sucursal;
	}

	public void setSel_tab_sucursal(SeleccionTabla sel_tab_sucursal) {
		this.sel_tab_sucursal = sel_tab_sucursal;
	}

	public SeleccionTabla getSel_tab_area() {
		return sel_tab_area;
	}

	public void setSel_tab_area(SeleccionTabla sel_tab_area) {
		this.sel_tab_area = sel_tab_area;
	}

	public SeleccionTabla getSel_tab_departamento() {
		return sel_tab_departamento;
	}

	public void setSel_tab_departamento(SeleccionTabla sel_tab_departamento) {
		this.sel_tab_departamento = sel_tab_departamento;
	}

	public SeleccionTabla getSel_tab_empleado() {
		return sel_tab_empleado;
	}

	public void setSel_tab_empleado(SeleccionTabla sel_tab_empleado) {
		this.sel_tab_empleado = sel_tab_empleado;
	}

	@Override
	public void abrirListaReportes() {
		rep_reporte.dibujar();
	}

	@Override
	public void aceptarReporte() {
		if(rep_reporte.getReporteSelecionado().equals("DOTACION DE UNIFORMES")){
			if(rep_reporte.isVisible()){
				p_parametros =new HashMap();
				rep_reporte.cerrar();
				sel_tab_sucursal.getTab_seleccion().setSql("SELECT ide_sucu,nom_sucu  FROM sis_sucursal order by  nom_sucu");
				sel_tab_sucursal.getTab_seleccion().ejecutarSql();
				sel_tab_sucursal.dibujar();		
			}else if(sel_tab_sucursal.isVisible()){
				if(sel_tab_sucursal.getSeleccionados()!=null && !sel_tab_sucursal.getSeleccionados().isEmpty()){					
					sel_tab_area.getTab_seleccion().setSql("SELECT ide_geare,detalle_geare FROM gen_area where ide_geare in (SELECT ide_geare FROM gen_departamento_sucursal where ide_sucu in("+sel_tab_sucursal.getSeleccionados()+"))");
					sel_tab_area.getTab_seleccion().ejecutarSql();
					sel_tab_sucursal.cerrar();
					sel_tab_area.dibujar();
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "Debe seleccionar al menos una sucursal");
				}
			}else if(sel_tab_area.isVisible()){
				if(sel_tab_area.getSeleccionados()!=null && !sel_tab_area.getSeleccionados().isEmpty()){
					sel_tab_departamento.getTab_seleccion().setSql("SELECT ide_gedep,detalle_gedep FROM gen_departamento where ide_geare in("+sel_tab_area.getSeleccionados()+") order by detalle_gedep");
					sel_tab_departamento.getTab_seleccion().ejecutarSql();
					sel_tab_area.cerrar();
					sel_tab_departamento.dibujar();
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "Debe seleccionar al menos una Área");
				}
			}else if(sel_tab_departamento.isVisible()){
				if(sel_tab_departamento.getSeleccionados()!=null && ! sel_tab_departamento.getSeleccionados().isEmpty()){											
					sel_tab_departamento.cerrar();
					sel_tab_empleado.getTab_seleccion().setSql("SELECT b.ide_gtemp,b.nombre_empleado FROM gen_empleados_departamento_par  a " +
							"left join( " +
							"SELECT ide_gtemp,apellido_paterno_gtemp || ' '||apellido_materno_gtemp||' '||primer_nombre_gtemp||' '|| " +
							"segundo_nombre_gtemp as nombre_empleado,documento_identidad_gtemp as documento FROM gth_empleado " +
							")b on b.ide_gtemp=a.ide_gtemp " +
							"where a.ide_gedep in ("+sel_tab_departamento.getSeleccionados()+") and a.activo_geedp=1");
					sel_tab_empleado.getTab_seleccion().ejecutarSql();
					sel_tab_empleado.dibujar();
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "Debe seleccionar al menos un Departamento");
				}
			}else if(sel_tab_empleado.isVisible()){
				if(sel_tab_empleado.getSeleccionados()!=null && !sel_tab_empleado.getSeleccionados().isEmpty()){
					p_parametros.put("IDE_GTEMP",sel_tab_empleado.getSeleccionados());
					p_parametros.put("titulo","DOTACION DE UNIFORMES");
					sel_tab_empleado.cerrar();
					sef_reporte.setSeleccionFormatoReporte(p_parametros, rep_reporte.getPath());
					sef_reporte.dibujar();			
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "Debe seleccionar al menos un empleado");
				}
			}
		}
	}

}
