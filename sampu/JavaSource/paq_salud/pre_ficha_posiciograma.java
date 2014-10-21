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
import javax.el.ValueExpression;
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


public class pre_ficha_posiciograma extends Pantalla {

	private Tabla tab_evaluacion_posiciograma=new Tabla();
	private Tabla tab_detalle_evaluacion=new Tabla();	

	private AutoCompletar aut_empleado = new AutoCompletar();
	private Confirmar con_guardar=new Confirmar();
	@EJB
	private ServicioEmpleado ser_empleado=(ServicioEmpleado) utilitario.instanciarEJB(ServicioEmpleado.class);
	@EJB
	private ServicioNomina ser_nomina=(ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	private Reporte rep_reporte=new Reporte();
	private SeleccionFormatoReporte sef_reporte=new SeleccionFormatoReporte();
	private Map p_parametros=new HashMap();	
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
	private Grid grid_grafico = new Grid();

	private BarChart barchart = new BarChart();
	private CartesianChartModel categoryModel;
	cls_graficas grafico=new cls_graficas();


	private Division div_division=new Division();

	private SeleccionCalendario sel_cal_fechas=new SeleccionCalendario();
	private SeleccionTabla sel_tab_grupo_ocupacional=new SeleccionTabla();
	private SeleccionTabla sel_tab_cargo_funcional=new SeleccionTabla();


	public pre_ficha_posiciograma() {

		sel_tab_grupo_ocupacional.setId("sel_tab_grupo_ocupacional");
		sel_tab_grupo_ocupacional.setSeleccionTabla("SELECT IDE_GEGRO,DETALLE_GEGRO FROM  GEN_GRUPO_OCUPACIONAL WHERE IDE_GEGRO=-1", "IDE_GEGRO");
		sel_tab_grupo_ocupacional.getTab_seleccion().getColumna("DETALLE_GEGRO").setFiltro(true);
		sel_tab_grupo_ocupacional.setTitle("SELECCION DE GRUPO OCUPACIONAL");
		sel_tab_grupo_ocupacional.getBot_aceptar().setMetodo("aceptarReporte");		
		agregarComponente(sel_tab_grupo_ocupacional);

		sel_tab_cargo_funcional.setId("sel_tab_cargo_funcional");
		sel_tab_cargo_funcional.setSeleccionTabla("SELECT IDE_GECAF,DETALLE_GECAF FROM GEN_CARGO_FUNCIONAL WHERE IDE_GECAF=-1", "IDE_GECAF");
		sel_tab_cargo_funcional.getTab_seleccion().getColumna("DETALLE_GECAF").setFiltro(true);
		sel_tab_cargo_funcional.setTitle("SELECCION DE CARGO FUNCIONAL");
		sel_tab_cargo_funcional.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_tab_cargo_funcional);

		// boton limpiar
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		bar_botones.agregarReporte();
		rep_reporte.setId("rep_reporte");
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(rep_reporte);

		sef_reporte.setId("sef_reporte");
		agregarComponente(sef_reporte);
		bar_botones.agregarReporte();

		// autocompletar empleado
		aut_empleado.setId("aut_empleado");
		String str_sql_emp=ser_gestion.getSqlEmpleadosAutocompletar();
		aut_empleado.setAutoCompletar(str_sql_emp);
		aut_empleado.setMetodoChange("filtrarAnticiposEmpleado");

		Etiqueta eti_colaborador=new Etiqueta("Empleado:");
		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_empleado);
		bar_botones.agregarBoton(bot_limpiar);


		//  FICHA MEDICA (division 1)

		tab_evaluacion_posiciograma.setId("tab_evaluacion_posiciograma");
		tab_evaluacion_posiciograma.setTabla("SAO_EVALUACION_POSICIOGRAMA", "IDE_SAEVP", 1);
		tab_evaluacion_posiciograma.getColumna("ACTIVO_SAEVP").setCheck();
		tab_evaluacion_posiciograma.getColumna("ACTIVO_SAEVP").setValorDefecto("true");
		tab_evaluacion_posiciograma.getColumna("IDE_USUA").setCombo("SIS_USUARIO", "IDE_USUA", "NOM_USUA", "");
		tab_evaluacion_posiciograma.getColumna("IDE_USUA").setLectura(true);
		tab_evaluacion_posiciograma.getColumna("IDE_USUA").setValorDefecto(utilitario.getVariable("ide_usua"));
		tab_evaluacion_posiciograma.getColumna("IDE_GTEMP").setVisible(false);
		tab_evaluacion_posiciograma.getColumna("IDE_GEEDP").setVisible(false);
		tab_evaluacion_posiciograma.agregarRelacion(tab_detalle_evaluacion);	
		tab_evaluacion_posiciograma.onSelect("seleccionaTabla1");
		tab_evaluacion_posiciograma.setCondicion("IDE_SAEVP=-1");		
		tab_evaluacion_posiciograma.dibujar();

		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_evaluacion_posiciograma);
		tab_evaluacion_posiciograma.setHeader("EVALUACIÓN POSICIOGRAMA");

		//  DETALLE EVALUACION 
		tab_detalle_evaluacion.setId("tab_detalle_evaluacion");		
		tab_detalle_evaluacion.setTabla("SAO_DETALLE_EVALUACION", "IDE_SADEE", 2);
		tab_detalle_evaluacion.getColumna("ACTIVO_SADEE").setCheck();
		tab_detalle_evaluacion.getColumna("ACTIVO_SADEE").setValorDefecto("true");
		tab_detalle_evaluacion.getColumna("IDE_SAPOS").setCombo("SAO_POSICIOGRAMA", "IDE_SAPOS", "DETALLE_SAPOS", "");
		tab_detalle_evaluacion.getColumna("IDE_SACAL").setCombo("SAO_CALIFICACION", "IDE_SACAL", "CALIFICACION_SACAL", "");
		tab_detalle_evaluacion.getColumna("IDE_SAPOS").setUnico(true);
		tab_detalle_evaluacion.getColumna("IDE_SAEVP").setUnico(true);		
		tab_detalle_evaluacion.setHeader("DETALLE DE EVALUACIÓN POSICIOGRAMA");
		tab_detalle_evaluacion.dibujar();

		PanelTabla pat_panel2=new PanelTabla();

		pat_panel2.setPanelTabla(tab_detalle_evaluacion);


		//  DIVISION DE LA PANTALLA


		Division div_aux=new Division();
		div_aux.dividir2(pat_panel2, null, "65%", "V");

		div_division.setId("div_division");
		div_division.dividir2(pat_panel1,div_aux,"60%","H");
		agregarComponente(div_division);

		// confirmacion para guardar datos
		con_guardar.setId("con_guardar");
		agregarComponente(con_guardar);

		sel_cal_fechas.setId("sel_cal_fechas");
		sel_cal_fechas.getBot_aceptar().setMetodo("aceptarReporte");
		agregarComponente(sel_cal_fechas);

	}

	public void seleccionaTabla1(SelectEvent evt){
		tab_evaluacion_posiciograma.seleccionarFila(evt);
		if (tab_detalle_evaluacion.getTotalFilas()>0){
			graficarBarras(tab_evaluacion_posiciograma.getValorSeleccionado());
		}
	}
	public void graficarBarras(String ide_saevp){


		grid_grafico.getChildren().clear();

		Etiqueta eti = new Etiqueta("GRÁFICOS ESTADÍSTICOS EVALUACIÓN PERSONAL BIESS");
		eti.setStyle("font-size:18px;");
		grid_grafico.setHeader(eti);
		grid_grafico.setId("grid_grafico");





		TablaGenerica tab_rep_pantalla =utilitario.consultar("SELECT " +
				"a.ide_gtemp as  ide_label, " +
				"'PUNTAJE'as detalle_label, " +
				"d.IDE_SAPOS as ide_col_ejeX, " +
				"(case when d.DETALLE_SAPOS is null then  'DETALLE' ELSE d.DETALLE_SAPOS END) as nom_col_ejeX, " +
				"e.CALIFICACION_SACAL as MONTO " +
				"FROM SAO_EVALUACION_POSICIOGRAMA a " +
				"left join ( " +
				"SELECT ide_gtemp,apellido_paterno_gtemp ||' '|| apellido_materno_gtemp ||' '|| primer_nombre_gtemp ||' '|| segundo_nombre_gtemp as empleado FROM gth_empleado " +
				")b on b.ide_gtemp=a.ide_gtemp " +
				"left join ( " +
				"SELECT ide_sadee,ide_saevp,ide_sapos,ide_sacal FROM SAO_DETALLE_EVALUACION " +
				")c on c.ide_saevp=a.ide_saevp " +
				"left join( " +
				"SELECT IDE_SAPOS,DETALLE_SAPOS FROM SAO_POSICIOGRAMA " +
				")d on d.ide_sapos=c.ide_sapos " +
				"left join( " +
				"SELECT IDE_SACAL,CALIFICACION_SACAL FROM SAO_CALIFICACION " +
				")e on e.ide_sacal=c.ide_sacal " +
				"where a.ide_gtemp="+aut_empleado.getValor()+" " +
				"and a.ide_saevp="+ide_saevp+" "+
				"order by nom_col_ejeX");


		categoryModel=new CartesianChartModel();		

		barchart=new BarChart();
		barchart=grafico.getBarchar(tab_rep_pantalla,"TOTAL EVALUACION DE EMPLEADO",300,300);

		Ajax evt=new Ajax();
		evt.setMetodo("itemSelect");
		barchart.addClientBehavior("itemSelect", evt);


		grid_grafico.getChildren().add(barchart);

		div_division.getChildren().clear();

		Division div_aux=new Division();
		PanelTabla pat=new PanelTabla();
		pat.setPanelTabla(tab_detalle_evaluacion);
		div_aux.dividir2(pat, grid_grafico, "65%", "V");

		PanelTabla pat1=new PanelTabla();
		pat1.setPanelTabla(tab_evaluacion_posiciograma);


		div_division.dividir2(pat1,div_aux,"40%","H");

		utilitario.addUpdate("div_division");



	}

	public void itemSelect(ItemSelectEvent event) {  
		categoryModel=grafico.getCategoryModel(); 

		Map map= categoryModel.getSeries().get(0).getData();
		Iterator it = map.entrySet().iterator();
		List lis_nom_col_eje_x=new ArrayList();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			lis_nom_col_eje_x.add(e.getKey()+"");
		}	     


		String nomina=categoryModel.getSeries().get(event.getSeriesIndex()).getLabel();	     
		map= categoryModel.getSeries().get(event.getSeriesIndex()).getData();

		System.out.println("label "+nomina+" map "+map);
		int int_num_dep=event.getItemIndex();
		String str_nom_col_eje_x="";
		for (int i = 0; i < lis_nom_col_eje_x.size(); i++) {
			if (i==int_num_dep){
				str_nom_col_eje_x=lis_nom_col_eje_x.get(i)+"";
				break;
			}
		}

		it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			if (e.getKey().toString().equalsIgnoreCase(str_nom_col_eje_x)){
				utilitario.agregarMensaje("Item seleccionado: "+nomina, e.getKey() + " " + e.getValue());
			}
		}	     

	}  

	private ValueExpression crearValueExpression(String valueExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createValueExpression(
				facesContext.getELContext(), "#{" + valueExpression + "}", Object.class);
	}


	private ValueExpression crearValueExpression(String valueExpression,Class c) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createValueExpression(
				facesContext.getELContext(), "#{" + valueExpression + "}", c);
	}



	String ide_geedp_activo="";
	public void filtrarAnticiposEmpleado(SelectEvent evt){
		aut_empleado.onSelect(evt);
		ide_geedp_activo=ser_gestion.getIdeContratoActivo(aut_empleado.getValor());
		tab_evaluacion_posiciograma.setCondicion("IDE_GTEMP="+aut_empleado.getValor());
		tab_evaluacion_posiciograma.ejecutarSql();
		tab_detalle_evaluacion.ejecutarValorForanea(tab_evaluacion_posiciograma.getValorSeleccionado());		
		if(tab_evaluacion_posiciograma.getTotalFilas()>0){
			if(tab_detalle_evaluacion.getTotalFilas()>0){
				graficarBarras(tab_evaluacion_posiciograma.getValorSeleccionado());
			}
		}
	}

	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	public void limpiar() {
		tab_evaluacion_posiciograma.limpiar();
		tab_detalle_evaluacion.limpiar();
		ide_geedp_activo="";
		aut_empleado.limpiar();
		div_division.getChildren().clear();

		Division div_aux=new Division();
		PanelTabla pat=new PanelTabla();
		pat.setPanelTabla(tab_detalle_evaluacion);
		div_aux.dividir2(pat, null, "65%", "V");

		PanelTabla pat1=new PanelTabla();
		pat1.setPanelTabla(tab_evaluacion_posiciograma);


		div_division.dividir2(pat1,div_aux,"40%","H");

		utilitario.addUpdate("div_division");




		utilitario.addUpdate("aut_empleado");// limpia y refresca el autocompletar
	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (tab_evaluacion_posiciograma.isFocus()){
			if (aut_empleado.getValor()!=null){
				if (ide_geedp_activo!=null && !ide_geedp_activo.isEmpty()){		
					tab_evaluacion_posiciograma.insertar();
					tab_evaluacion_posiciograma.setValor("IDE_GEEDP",ide_geedp_activo);
					tab_evaluacion_posiciograma.setValor("IDE_GTEMP", aut_empleado.getValor());				
				}else{
					utilitario.agregarMensajeInfo("No se puede insertar", "El contrato del empleado no esta activo");
				}
			}else{
				utilitario.agregarMensajeInfo("No se puede insertar", "Debe seleccionar el Empleado");
			}
		}
		else if (tab_detalle_evaluacion.isFocus()){
			if (aut_empleado.getValor()!=null){
				if (tab_evaluacion_posiciograma.getTotalFilas()>0){
					tab_detalle_evaluacion.insertar();					
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
			if (tab_evaluacion_posiciograma.guardar()){
				if(tab_detalle_evaluacion.guardar()){									
					guardarPantalla();									
					graficarBarras(tab_evaluacion_posiciograma.getValorSeleccionado());									
				}
			}
		}else{
			utilitario.agregarMensajeInfo("No se puede guardar el Anticipo", "Debe seleccionar un Empleado");
		}
	}

	@Override
	public void eliminar() {
		if (aut_empleado.getValor()!=null){			
			if (tab_evaluacion_posiciograma.isFocus()){	
				tab_evaluacion_posiciograma.eliminar();
			}else if(tab_detalle_evaluacion.isFocus()){
				tab_detalle_evaluacion.eliminar();
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

	public Confirmar getCon_guardar() {
		return con_guardar;
	}

	public void setCon_guardar(Confirmar con_guardar) {
		this.con_guardar = con_guardar;
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


	@Override
	public void abrirListaReportes() {		
		rep_reporte.dibujar();
	}

	@Override
	public void aceptarReporte() {
		if(rep_reporte.getReporteSelecionado().equals("EVALUACION DE EMPLEADOS")){
		
				if(rep_reporte.isVisible()){
					p_parametros =new HashMap();
					rep_reporte.cerrar();
					sel_cal_fechas.setFecha1(null);
					sel_cal_fechas.setFecha2(null);					
					sel_cal_fechas.dibujar();		
				}else if(sel_cal_fechas.isVisible()){
					if(sel_cal_fechas.isFechasValidas()){
						sel_tab_cargo_funcional.getTab_seleccion().setSql("SELECT IDE_GECAF,DETALLE_GECAF FROM GEN_CARGO_FUNCIONAL");
						sel_tab_cargo_funcional.getTab_seleccion().ejecutarSql();
						sel_tab_cargo_funcional.getBot_aceptar().setMetodo("aceptarReporte");
						System.out.println(""+sel_cal_fechas.getFecha1String());
						System.out.println(""+sel_cal_fechas.getFecha2String());

						p_parametros.put("fecha_inicio", sel_cal_fechas.getFecha1String());
						p_parametros.put("fecha_fin", sel_cal_fechas.getFecha2String());					
						sel_cal_fechas.cerrar();
						sel_tab_grupo_ocupacional.getTab_seleccion().setSql("SELECT IDE_GEGRO,DETALLE_GEGRO FROM  GEN_GRUPO_OCUPACIONAL");
						sel_tab_grupo_ocupacional.getTab_seleccion().ejecutarSql();
						sel_tab_grupo_ocupacional.getBot_aceptar().setMetodo("aceptarReporte");
						sel_cal_fechas.cerrar();
						sel_tab_grupo_ocupacional.dibujar();
					}else{
						utilitario.agregarMensajeInfo("No se puede continuar", "La fecha final es menor a la fecha inicial");
					}
				}else if(sel_tab_grupo_ocupacional.isVisible()){
					if(sel_tab_grupo_ocupacional.getSeleccionados()!=null && !sel_tab_grupo_ocupacional.getSeleccionados().isEmpty()){
						//System.out.println(""+sel_tab_cargo_funcional.getSeleccionados());
						p_parametros.put("ide_gegro",sel_tab_grupo_ocupacional.getSeleccionados());
						sel_tab_cargo_funcional.getTab_seleccion().setSql("select CAF.IDE_GECAF,CAF.DETALLE_GECAF from GEN_CARGO_FUNCIONAL CAF " +
								"LEFT JOIN GEN_GRUPO_CARGO GCAF ON GCAF.IDE_GECAF=CAF.IDE_GECAF " +
								"WHERE GCAF.IDE_GEGRO in ("+sel_tab_grupo_ocupacional.getSeleccionados()+") " +
								"GROUP BY CAF.IDE_GECAF,CAF.DETALLE_GECAF " +
								"ORDER BY CAF.DETALLE_GECAF ASC");
						sel_tab_cargo_funcional.getTab_seleccion().ejecutarSql();
						sel_tab_cargo_funcional.getBot_aceptar().setMetodo("aceptarReporte");
						sel_tab_grupo_ocupacional.cerrar();
						sel_tab_cargo_funcional.dibujar();
						System.out.println("valor de ide_gecaf"+sel_tab_cargo_funcional.getValorSeleccionado());
					}else{
						utilitario.agregarMensajeInfo("No se puede continuar", "Debe selccionar al menos un Cargo Funcional");
					}					
				}else if(sel_tab_cargo_funcional.isVisible()){
					if(sel_tab_cargo_funcional.getSeleccionados()!=null && !sel_tab_cargo_funcional.getSeleccionados().isEmpty()){
						//System.out.println(""+sel_tab_grupo_ocupacional.getSeleccionados());
						p_parametros.put("ide_gecaf",sel_tab_cargo_funcional.getSeleccionados());

						p_parametros.put("titulo","EVALUACION DE EMPLEADOS");
						sef_reporte.setSeleccionFormatoReporte(p_parametros, rep_reporte.getPath());
						sel_tab_cargo_funcional.cerrar();
						sef_reporte.dibujar();		

					}else{
						utilitario.agregarMensajeInfo("No se puede continuar", "Debe selccionar al menos un Cargo Funcional");
					}					
						

				}
		
		}
	}

	public Tabla getTab_evaluacion_posiciograma() {
		return tab_evaluacion_posiciograma;
	}

	public void setTab_evaluacion_posiciograma(Tabla tab_evaluacion_posiciograma) {
		this.tab_evaluacion_posiciograma = tab_evaluacion_posiciograma;
	}

	public Tabla getTab_detalle_evaluacion() {
		return tab_detalle_evaluacion;
	}

	public void setTab_detalle_evaluacion(Tabla tab_detalle_evaluacion) {
		this.tab_detalle_evaluacion = tab_detalle_evaluacion;
	}

	public BarChart getBarchart() {
		return barchart;
	}

	public void setBarchart(BarChart barchart) {
		this.barchart = barchart;
	}

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public SeleccionCalendario getSel_cal_fechas() {
		return sel_cal_fechas;
	}

	public void setSel_cal_fechas(SeleccionCalendario sel_cal_fechas) {
		this.sel_cal_fechas = sel_cal_fechas;
	}

	public SeleccionTabla getSel_tab_grupo_ocupacional() {
		return sel_tab_grupo_ocupacional;
	}

	public void setSel_tab_grupo_ocupacional(
			SeleccionTabla sel_tab_grupo_ocupacional) {
		this.sel_tab_grupo_ocupacional = sel_tab_grupo_ocupacional;
	}

	public SeleccionTabla getSel_tab_cargo_funcional() {
		return sel_tab_cargo_funcional;
	}

	public void setSel_tab_cargo_funcional(SeleccionTabla sel_tab_cargo_funcional) {
		this.sel_tab_cargo_funcional = sel_tab_cargo_funcional;
	}

}
