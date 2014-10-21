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
import framework.componentes.SeleccionCalendario;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import framework.componentes.Texto;


public class pre_ficha_control_equipos extends Pantalla {

	private Tabla tab_bienes=new Tabla();
	private Tabla tab_custodio=new Tabla();	
	@EJB
	private ServicioEmpleado ser_empleado=(ServicioEmpleado) utilitario.instanciarEJB(ServicioEmpleado.class);
	@EJB
	private ServicioNomina ser_nomina=(ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);	
	private Division div_division=new Division();
	private SeleccionTabla set_empleado=new SeleccionTabla();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte sef_reporte = new SeleccionFormatoReporte();
	private Map p_parametros = new HashMap();
	private SeleccionTabla sel_tab_area=new SeleccionTabla();
	private SeleccionTabla sel_tab_departamento =new SeleccionTabla();
	private SeleccionTabla sel_tab_sucursal=new SeleccionTabla();
	private SeleccionTabla sel_empleado_bien=new SeleccionTabla();
	private SeleccionTabla sel_tipo_bien=new SeleccionTabla();
	private SeleccionCalendario sel_calendario=new SeleccionCalendario();
	public pre_ficha_control_equipos() {

		Boton bot_importar=new Boton();
		bot_importar.setIcon("ui-icon-person");
		bot_importar.setValue("Agregar Custodios");
		bot_importar.setMetodo("importarEmpleados");
		bar_botones.agregarBoton(bot_importar);
		//
		rep_reporte.setId("rep_reporte");
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(rep_reporte);


		sef_reporte.setId("sef_reporte");
		agregarComponente(sef_reporte);
		bar_botones.agregarReporte();
		//  FICHA BIENES (division 1)

		tab_bienes.setId("tab_bienes");
		tab_bienes.setTabla("SAO_BIENES", "IDE_SABIE", 1);
		tab_bienes.getColumna("ACTIVO_SABIE").setCheck();
		tab_bienes.getColumna("IDE_SAPRO").setCombo("SAO_PROPIEDAD", "IDE_SAPRO", "DETALLE_SAPRO", "");
		tab_bienes.getColumna("IDE_SAGRB").setCombo("SAO_GRUPO_BIEN", "IDE_SAGRB", "DETALLE_SAGRB", "");
		tab_bienes.getColumna("IDE_SASEG").setCombo("SAO_SEGURO", "IDE_SASEG", "DETALLE_SASEG", "");
		tab_bienes.getColumna("IDE_SAEST").setCombo("SAO_ESTADO", "IDE_SAEST", "DETALLE_SAEST", "");
		tab_bienes.getColumna("IDE_GEBEN").setCombo("GEN_BENEFICIARIO", "IDE_GEBEN", "TITULAR_GEBEN", "");
		tab_bienes.getColumna("ACTIVO_SABIE").setValorDefecto("true");
		tab_bienes.getColumna("FOTO_SABIE").setUpload("nueva_carpeta");
		tab_bienes.getColumna("FOTO_SABIE").setImagen("", "");
		tab_bienes.agregarRelacion(tab_custodio);	
		tab_bienes.setTipoFormulario(true);
		tab_bienes.getGrid().setColumns(6);	
		tab_bienes.dibujar();

		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_bienes);
		tab_bienes.setHeader("BIENES");

		//  DETALLE EVALUACION 
		tab_custodio.setId("tab_custodio");		
		tab_custodio.setTabla("SAO_CUSTODIO", "IDE_SACUS", 2);
		tab_custodio.getColumna("ACTIVO_SACUS").setCheck();
		tab_custodio.getColumna("ACTIVO_SACUS").setValorDefecto("true");
		tab_custodio.getColumna("TIPO_SACUS").setValorDefecto("1");
		tab_custodio.getColumna("TIPO_SACUS").setVisible(false);
		tab_custodio.getColumna("IDE_GTEMP").setVisible(false);
		tab_custodio.getColumna("IDE_GEEDP").setVisible(false);		
		tab_custodio.getColumna("SAO_IDE_SACUS").setVisible(false);
		tab_custodio.getColumna("IDE_SAUBS").setCombo("SELECT a.ide_saubs,c.nom_sucu,b.DETALLE_SAUBI FROM SAO_UBICACION_SUCURSAL a " +
				"LEFT JOIN( " +
				"SELECT IDE_SAUBI,DETALLE_SAUBI FROM sao_ubicacion " +
				")b ON b.IDE_SAUBI=a.IDE_SAUBI " +
				"left join ( " +
				"SELECT ide_sucu,nom_sucu FROM sis_sucursal " +
				")c on c.ide_sucu=a.ide_sucu");
		tab_custodio.getColumna("IDE_SAUBS").setAutoCompletar();
		tab_custodio.getColumna("FOTO_SACUS").setUpload("nueva_carpeta");
		tab_custodio.getColumna("FOTO_SACUS").setImagen("", "");
		tab_custodio.setHeader("DETALLE DE CUSTODIOS");
		tab_custodio.dibujar();

		PanelTabla pat_panel2=new PanelTabla();
		pat_panel2.setPanelTabla(tab_custodio);



		//  DIVISION DE LA PANTALLA

		div_division.setId("div_division");
		div_division.dividir2(pat_panel1,pat_panel2,"65%","H");
		agregarComponente(div_division);

		//seleccionar empleados

		set_empleado.setId("set_empleado");
		set_empleado.setSeleccionTabla(ser_empleado.getSQLEmpleadosActivos(), "IDE_GTEMP");
		set_empleado.getTab_seleccion().getColumna("NOMBRES ").setFiltro(true);		
		set_empleado.setTitle("SELECCION DE EMPLEADOS");		
		set_empleado.getBot_aceptar().setMetodo("aceptarImportarEmpleados");
		agregarComponente(set_empleado);


		sel_tab_area.setId("sel_tab_area");
		sel_tab_area.setTitle("SELECCION DE AREAS");
		sel_tab_area.setSeleccionTabla("SELECT ide_geare,detalle_geare FROM gen_area where ide_geare=-1", "ide_geare");
		sel_tab_area.getTab_seleccion().getColumna("detalle_geare").setFiltro(true);
		sel_tab_area.getBot_aceptar().setMetodo("aceptarReporte");
		sel_tab_area.setDynamic(false);
		agregarComponente(sel_tab_area);

		sel_tab_departamento.setId("sel_tab_departamento");
		sel_tab_departamento.setTitle("SELECCION DE DEPARTAMENTOS");
		sel_tab_departamento.setSeleccionTabla("SELECT ide_gedep,detalle_gedep FROM gen_departamento where ide_geare in(-1) order by detalle_gedep", "ide_gedep");
		sel_tab_departamento.getTab_seleccion().getColumna("detalle_gedep").setFiltro(true);
		sel_tab_departamento.getBot_aceptar().setMetodo("aceptarReporte");
		sel_tab_departamento.setDynamic(false);
		agregarComponente(sel_tab_departamento);

		//localidades de trabajo		

		sel_tab_sucursal.setId("sel_tab_sucursal");
		sel_tab_sucursal.setTitle("SELECCION DE SUCURSAL");
		sel_tab_sucursal.setSeleccionTabla("SELECT ide_sucu,nom_sucu  FROM sis_sucursal where ide_sucu=-1 order by  nom_sucu", "ide_sucu");
		sel_tab_sucursal.getTab_seleccion().getColumna("nom_sucu").setFiltro(true);
		sel_tab_sucursal.getBot_aceptar().setMetodo("aceptarReporte");
		sel_tab_sucursal.setDynamic(false);
		agregarComponente(sel_tab_sucursal);


		sel_empleado_bien.setId("sel_empleado_bien");
		sel_empleado_bien.setSeleccionTabla("select EMP.IDE_GTEMP,EMP.DOCUMENTO_IDENTIDAD_GTEMP, " +
				"EMP.APELLIDO_PATERNO_GTEMP || ' ' ||  " +
				"EMP.APELLIDO_MATERNO_GTEMP || ' ' ||  " +
				"EMP.PRIMER_NOMBRE_GTEMP || ' ' ||  " +
				"EMP.SEGUNDO_NOMBRE_GTEMP AS NOMBRES  " +
				"from GTH_EMPLEADO EMP  " +
				"left join GEN_EMPLEADOS_DEPARTAMENTO_PAR EDP ON EDP.IDE_GTEMP=EMP.IDE_GTEMP " +
				"ORDER BY 	NOMBRES ASC", "IDE_GTEMP");
		sel_empleado_bien.getTab_seleccion().getColumna("NOMBRES").setFiltro(true);
		sel_empleado_bien.getBot_aceptar().setMetodo("aceptarReporte");
		sel_empleado_bien.setTitle("SELECCIONE EMPLEADO");
		sel_empleado_bien.setDynamic(false);
		agregarComponente(sel_empleado_bien);

		sel_calendario.setId("sel_calendario");
		sel_calendario.setMultiple(true);
		sel_calendario.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_calendario);

		sel_tipo_bien.setId("sel_tipo_bien");
		sel_tipo_bien.setTitle("SELECCIONE TIPO BIEN ");
		sel_tipo_bien.setSeleccionTabla("SELECT IDE_SABIE,NOMBRE_SABIE FROM SAO_BIENES ORDER BY NOMBRE_SABIE", "IDE_SABIE");
		sel_tipo_bien.getTab_seleccion().getColumna("NOMBRE_SABIE").setFiltro(true);
		sel_tipo_bien.getBot_aceptar().setMetodo("aceptarReporte");
		sel_tipo_bien.setDynamic(false);
		agregarComponente(sel_tipo_bien);

	}

	public void importarEmpleados(){
		if(tab_bienes.isEmpty()==false){
			set_empleado.dibujar();		
		}		
		else{
			utilitario.agregarMensajeInfo("Debe insertar datos en la cabecera", "");
		}		
	}

	public void aceptarImportarEmpleados(){
		String str_seleccionados=set_empleado.getSeleccionados();
		if(str_seleccionados!=null){
			//Inserto los empleados seleccionados en la tabla de participantes 
			TablaGenerica tab_emp=utilitario.consultar("SELECT emp.IDE_GTEMP,DOCUMENTO_IDENTIDAD_GTEMP, " +
					"APELLIDO_PATERNO_GTEMP || ' ' || APELLIDO_MATERNO_GTEMP || ' ' || PRIMER_NOMBRE_GTEMP || ' ' || SEGUNDO_NOMBRE_GTEMP AS NOMBRES " +
					",edp.IDE_GEEDP from GTH_EMPLEADO emp " +
					"LEFT JOIN gen_empleados_departamento_par edp on EMP.IDE_GTEMP=edp.IDE_GTEMP " +
					"WHERE emp.IDE_GTEMP IN ("+str_seleccionados+")  AND edp.activo_geedp=1 ");

			for(int i=0;i<tab_emp.getTotalFilas();i++){
				tab_custodio.insertar();				
				tab_custodio.setValor("IDE_GEEDP", tab_emp.getValor(i, "IDE_GEEDP"));
				tab_custodio.setValor("IDE_GTEMP", tab_emp.getValor(i, "IDE_GTEMP"));
				tab_custodio.setValor("CUSTODIO_SACUS", tab_emp.getValor(i, "NOMBRES"));				
			}
			set_empleado.cerrar();
			utilitario.addUpdate("tab_custodio");			
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		}
	}

	//	String ide_geedp_activo="";
	//	public void filtrarAnticiposEmpleado(SelectEvent evt){
	//		aut_empleado.onSelect(evt);
	//		ide_geedp_activo=ser_gestion.getIdeContratoActivo(aut_empleado.getValor());
	//		tab_evaluacion_posiciograma.setCondicion("IDE_GTEMP="+aut_empleado.getValor());
	//		tab_evaluacion_posiciograma.ejecutarSql();
	//		tab_detalle_evaluacion.ejecutarValorForanea(tab_evaluacion_posiciograma.getValorSeleccionado());
	//		
	//		if(tab_detalle_evaluacion.getTotalFilas()>0){
	//			
	//		}
	//	}

	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	@Override
	public void insertar() {
		if (tab_bienes.isFocus()){		
			tab_bienes.insertar();
		}
		else if (tab_custodio.isFocus()){
			if (tab_bienes.getTotalFilas()>0){
				tab_custodio.insertar();					
			}else{
				utilitario.agregarMensajeInfo("No se puede insertar", "Primero debe insertar un Bien");				
			}	
		}	

	}

	@Override
	public void abrirListaReportes() {
		// TODO Auto-generated method stub
		rep_reporte.dibujar();
	}

	@Override
	public void aceptarReporte() {
		// TODO Auto-generated method stub

		if (rep_reporte.getReporteSelecionado().equals("Detalle Dotacion")){
			if (rep_reporte.isVisible()){
				p_parametros=new HashMap();
				rep_reporte.cerrar();
				sel_tab_sucursal.getTab_seleccion().setSql("SELECT IDE_SUCU, " +
						"NOM_SUCU " +
						"FROM SIS_SUCURSAL " +
						"ORDER BY NOM_SUCU ASC ");
				sel_tab_sucursal.getTab_seleccion().getColumna("NOM_SUCU").setFiltro(true);
				sel_tab_sucursal.getTab_seleccion().ejecutarSql();

				sel_tab_sucursal.dibujar();				
			}else if (sel_tab_sucursal.isVisible()) {
				if(sel_tab_sucursal.getSeleccionados()!=null && !sel_tab_sucursal.getSeleccionados().isEmpty()){
					System.out.println("SUCURSAL :"+sel_tab_sucursal.getSeleccionados());
					sel_tab_area.getTab_seleccion().setSql("SELECT IDE_GEARE,DETALLE_GEARE FROM GEN_AREA WHERE IDE_GEARE IN(SELECT IDE_GEARE FROM GEN_DEPARTAMENTO_SUCURSAL WHERE IDE_SUCU IN (" +
							""+sel_tab_sucursal.getSeleccionados()+"))");
					sel_tab_area.getTab_seleccion().getColumna("DETALLE_GEARE").setFiltro(true);
					sel_tab_area.getTab_seleccion().ejecutarSql();
					sel_tab_area.getBot_aceptar().setMetodo("aceptarReporte");
					sel_tab_sucursal.cerrar();
					sel_tab_area.dibujar();			
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ninguna Sucursal");
				}
			}	else if (sel_tab_area.isVisible()){
				if (sel_tab_area.getSeleccionados()!=null && !sel_tab_area.getSeleccionados().isEmpty()){
					System.out.println("AREA :"+sel_tab_area.getSeleccionados());
					sel_tab_departamento.getTab_seleccion().setSql("SELECT ide_gedep,detalle_gedep FROM gen_departamento where ide_geare in("+sel_tab_area.getSeleccionados()+") order by detalle_gedep");
					sel_tab_departamento.getTab_seleccion().getColumna("DETALLE_GEDEP").setFiltro(true);
					sel_tab_departamento.getTab_seleccion().ejecutarSql();
					sel_tab_departamento.getBot_aceptar().setMetodo("aceptarReporte");
					sel_tab_area.cerrar();
					sel_tab_departamento.dibujar();						
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ninguna Area");
				}
			}
			else if (sel_tab_departamento.isVisible()) {
				if(sel_tab_departamento.getSeleccionados()!=null && ! sel_tab_departamento.getSeleccionados().isEmpty()){
					System.out.println("DEPARTAMENTO :"+sel_tab_departamento.getSeleccionados());
					sel_empleado_bien.getTab_seleccion().setSql("select EMP.IDE_GTEMP,EMP.DOCUMENTO_IDENTIDAD_GTEMP,  " +
							"EMP.APELLIDO_PATERNO_GTEMP || ' ' ||  " +
							"EMP.APELLIDO_MATERNO_GTEMP || ' ' ||  " +
							"EMP.PRIMER_NOMBRE_GTEMP || ' ' ||  " +
							"EMP.SEGUNDO_NOMBRE_GTEMP AS NOMBRES  " +
							"from GTH_EMPLEADO EMP  " +
							"left join GEN_EMPLEADOS_DEPARTAMENTO_PAR EDP ON EDP.IDE_GTEMP=EMP.IDE_GTEMP  " +
							"WHERE EDP.IDE_GEDEP IN("+sel_tab_departamento.getSeleccionados()+")  " +
							"AND EDP.ide_sucu in("+sel_tab_sucursal.getSeleccionados()+") " +
							"and EDP.IDE_GEARE in("+sel_tab_area.getSeleccionados()+") " +
							"ORDER BY 	NOMBRES ASC");
					sel_empleado_bien.getTab_seleccion().getColumna("DOCUMENTO_IDENTIDAD_GTEMP").setFiltro(true);
					sel_empleado_bien.getTab_seleccion().getColumna("NOMBRES").setFiltro(true);
					sel_empleado_bien.getTab_seleccion().ejecutarSql();
					sel_tab_departamento.cerrar();
					sel_empleado_bien.dibujar();

				}else {
					utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun Departamento");
				}

			}
			else if (sel_empleado_bien.isVisible()){
					if (sel_empleado_bien.getSeleccionados()!=null && !sel_empleado_bien.getSeleccionados().isEmpty() ){
					System.out.println("EMPLEADO :"+sel_empleado_bien.getSeleccionados());
					sel_empleado_bien.getTab_seleccion().imprimirSql();
					p_parametros.put("IDE_GTEMP",sel_empleado_bien.getSeleccionados());
					p_parametros.put("titulo", "DETALLE DOTACION BIENES");
					sef_reporte.setSeleccionFormatoReporte(p_parametros, rep_reporte.getPath());
					sel_empleado_bien.cerrar();
					sef_reporte.dibujar();
				}
				else{
					utilitario.agregarMensajeInfo("No se continuar", "No ha seleccionado ningun Empleado");
				}
				}
		}


		if (rep_reporte.getReporteSelecionado().equals("Detalle DotacionXPeriodo")){
			if (rep_reporte.isVisible()){
				p_parametros=new HashMap();
				rep_reporte.cerrar();
				sel_calendario.dibujar();				
			}else if (sel_calendario.isVisible()) {
				if(sel_calendario.isFechasValidas()){
					System.out.println("Fecha1 :"+sel_calendario.getFecha1String());
					System.out.println("Fecha2 :"+sel_calendario.getFecha2String());
					p_parametros.put("FECHA_INICIO",sel_calendario.getFecha1String());
					p_parametros.put("FECHA_FIN",sel_calendario.getFecha2String());
					sel_tipo_bien.getTab_seleccion().setSql("SELECT IDE_SABIE,NOMBRE_SABIE FROM SAO_BIENES ORDER BY NOMBRE_SABIE");
					sel_tipo_bien.getTab_seleccion().getColumna("NOMBRE_SABIE").setFiltro(true);
					sel_tipo_bien.getTab_seleccion().ejecutarSql();
					sel_tipo_bien.getBot_aceptar().setMetodo("aceptarReporte");
					sel_calendario.cerrar();
					sel_tipo_bien.dibujar();			
				}else{
					utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ninguna Fecha");
				}
			}	else if (sel_tipo_bien.isVisible()){
				if (sel_tipo_bien.getSeleccionados()!=null && !sel_tipo_bien.getSeleccionados().isEmpty()){
					p_parametros.put("IDE_SABIE",sel_tipo_bien.getSeleccionados());
					p_parametros.put("titulo", "DETALLE DOTACION BIENES POR PERIODO");
					sef_reporte.setSeleccionFormatoReporte(p_parametros, rep_reporte.getPath());
					sel_tipo_bien.cerrar();
					sef_reporte.dibujar();
				}
				else{
					utilitario.agregarMensajeInfo("No se continuar", "No ha seleccionado ningun Tipo de Empleado");
				}
			}
		}


	}

	@Override
	public void guardar() {
		if (tab_bienes.guardar()){
			if(tab_custodio.guardar()){									
				guardarPantalla();																		
			}
		}
	}

	@Override
	public void eliminar() {			
		if (tab_bienes.isFocus()){	
			tab_bienes.eliminar();
		}else if(tab_custodio.isFocus()){
			tab_custodio.eliminar();
		}
	}

	public Tabla getTab_bienes() {
		return tab_bienes;
	}

	public void setTab_bienes(Tabla tab_bienes) {
		this.tab_bienes = tab_bienes;
	}

	public Tabla getTab_custodio() {
		return tab_custodio;
	}

	public void setTab_custodio(Tabla tab_custodio) {
		this.tab_custodio = tab_custodio;
	}

	public SeleccionTabla getSet_empleado() {
		return set_empleado;
	}

	public void setSet_empleado(SeleccionTabla set_empleado) {
		this.set_empleado = set_empleado;
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

	public SeleccionTabla getSel_tab_sucursal() {
		return sel_tab_sucursal;
	}

	public void setSel_tab_sucursal(SeleccionTabla sel_tab_sucursal) {
		this.sel_tab_sucursal = sel_tab_sucursal;
	}

	public SeleccionTabla getSel_empleado_bien() {
		return sel_empleado_bien;
	}

	public void setSel_empleado_bien(SeleccionTabla sel_empleado_bien) {
		this.sel_empleado_bien = sel_empleado_bien;
	}

	public SeleccionCalendario getSel_calendario() {
		return sel_calendario;
	}

	public void setSel_calendario(SeleccionCalendario sel_calendario) {
		this.sel_calendario = sel_calendario;
	}

	public SeleccionTabla getSel_tipo_bien() {
		return sel_tipo_bien;
	}

	public void setSel_tipo_bien(SeleccionTabla sel_tipo_bien) {
		this.sel_tipo_bien = sel_tipo_bien;
	}



}
