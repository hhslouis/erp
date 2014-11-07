package paq_contabilidad;




import java.util.HashMap;
import java.util.Map;

import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionArbol;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;



public class pre_viaje extends Pantalla {

	private Tabla tab_tiket_viaje = new Tabla();
	private Tabla tab_cont_viajeros = new Tabla();
	private Combo com_tipo_transporte=new Combo();

	//Reportes
	private Reporte rep_reporte=new Reporte();
	private SeleccionFormatoReporte sef_formato=new SeleccionFormatoReporte();
	private Map  map_parametros=new HashMap();

	private SeleccionTabla set_tipo_transporte=new SeleccionTabla();
	private SeleccionTabla set_asunto_viaje=new SeleccionTabla();
	private SeleccionTabla set_estado=new SeleccionTabla();
	private SeleccionArbol sea_distribucion=new SeleccionArbol();

	public pre_viaje() {

		com_tipo_transporte.setCombo("select ide_cotit,detalle_cotit from cont_tipo_transporte where activo_cotit = true" +
				" order by detalle_cotit");
		com_tipo_transporte.setMetodo("seleccionaTipo_Transporte");
		bar_botones.agregarComponente(new Etiqueta("Tipo de Transporte:"));
		bar_botones.agregarComponente(com_tipo_transporte);		


		bar_botones.agregarReporte();//agrega boton de reporte

		rep_reporte.setId("rep_reporte");
		agregarComponente(rep_reporte);

		sef_formato.setId("sef_formato");
		agregarComponente(sef_formato);

		tab_tiket_viaje.setId("tab_tiket_viaje");
		tab_tiket_viaje.setTabla("cont_tiket_viaje", "ide_cotiv",1);
		tab_tiket_viaje.getColumna("ide_cotit").setVisible(false);
		tab_tiket_viaje.setCondicion("ide_cotit=-1");


		tab_tiket_viaje.getColumna("IDE_COTIV").setCombo("CONT_TIKET_VIAJE","IDE_COTIV", "DETALLE_VIAJE_COTIV", "");

		tab_tiket_viaje.getColumna("IDE_COASV").setCombo("CONT_ASUNTO_VIAJE","IDE_COASV", "DETALLE_COASV", "");
		tab_tiket_viaje.getColumna("IDE_COEST").setCombo("CONT_ESTADO","IDE_COEST", "DETALLE_COEST", "");
		tab_tiket_viaje.getColumna("IDE_INDIP").setCombo("INST_DISTRIBUCION_POLITICA","IDE_INDIP", "DETALLE_INDIP", "");

		tab_tiket_viaje.getColumna("INS_IDE_INDIP").setCombo("INST_DISTRIBUCION_POLITICA","INS_IDE_INDIP", "DETALLE_INDIP", "");

		//tab_tiket_viaje.getColumna("ACTIVO_COTIV").setValorDefecto("false");	

		tab_tiket_viaje.setTipoFormulario(true);
		tab_tiket_viaje.getGrid().setColumns(4);



		tab_tiket_viaje.dibujar();

		tab_tiket_viaje.agregarRelacion(tab_cont_viajeros);
		PanelTabla pat_tiket_viaje =new PanelTabla();
		pat_tiket_viaje.setPanelTabla(tab_tiket_viaje);

		//divicion 2 

		tab_cont_viajeros.setId("tab_cont_viajeros");
		tab_cont_viajeros.setTabla("cont_viajeros", "ide_covia",2);
		tab_cont_viajeros.getColumna("IDE_COVIA").setCombo("CONT_VIAJEROS","IDE_COVIA", "BOLETO_COVIA", "");
		tab_cont_viajeros.getColumna("IDE_COCLV").setCombo("CONT_CLASE_VIAJE","IDE_COCLV", "DETALLE_COCLV", "");
		tab_cont_viajeros.getColumna("IDE_COTIV").setCombo("CONT_TIKET_VIAJE","IDE_COTIV", "DETALLE_VIAJE_COTIV", "");
		tab_cont_viajeros.getColumna("IDE_GTEMP").setCombo("SELECT IDE_GTEMP," +
				"(PRIMER_NOMBRE_GTEMP||' '||SEGUNDO_NOMBRE_GTEMP||' '||APELLIDO_PATERNO_GTEMP||' '||APELLIDO_MATERNO_GTEMP)AS EMPLEADO " +
				" FROM gth_empleado ORDER BY EMPLEADO");


		tab_cont_viajeros.setCampoForanea("ide_cotiv");


		tab_cont_viajeros.setTipoFormulario(true);
		tab_cont_viajeros.getGrid().setColumns(4);

		tab_cont_viajeros.dibujar();
		PanelTabla pat_cont_viajeros = new PanelTabla ();
		pat_cont_viajeros.setPanelTabla(tab_cont_viajeros);

		Division div_cont_viajeros=new Division();
		div_cont_viajeros.dividir2(pat_tiket_viaje, pat_cont_viajeros, "50%", "H");
		agregarComponente(div_cont_viajeros);


		//Configurar selecion para reporte
		set_tipo_transporte.setId("set_tipo_transporte");
		set_tipo_transporte.setRadio();
		set_tipo_transporte.setSeleccionTabla("select ide_cotit,detalle_cotit from cont_tipo_transporte where activo_cotit=true order by detalle_cotit", "ide_cotit");
		set_tipo_transporte.getBot_aceptar().setMetodo("abrirTiketsdeViaje");
		agregarComponente(set_tipo_transporte);

		set_asunto_viaje.setId("set_asunto_viaje");
		set_asunto_viaje.setRadio();
		set_asunto_viaje.setSeleccionTabla("select ide_coasv,detalle_coasv from cont_asunto_viaje where activo_coasv=true order by detalle_coasv", "ide_coasv");
		set_asunto_viaje.getBot_aceptar().setMetodo("abrirTiketsdeViaje");
		agregarComponente(set_asunto_viaje);

		set_estado.setId("set_estado");
		set_estado.setRadio();
		set_estado.setSeleccionTabla("select ide_coest,detalle_coest from cont_estado where activo_coest=true order by detalle_coest", "ide_coest");
		set_estado.getBot_aceptar().setMetodo("abrirTiketsdeViaje");
		agregarComponente(set_estado);

		sea_distribucion.setId("sea_distribucion");
		sea_distribucion.setSeleccionArbol("inst_distribucion_politica", "ide_indip", "detalle_indip", "ins_ide_indip");
		sea_distribucion.getBot_aceptar().setMetodo("abrirTiketsdeViaje");
		agregarComponente(sea_distribucion);


	}


	@Override
	public void abrirListaReportes() {
		rep_reporte.dibujar();
	}

	@Override
	public void aceptarReporte() {
		if(rep_reporte.getReporteSelecionado().equals("Tikets de Viaje")){
			abrirTiketsdeViaje();
		}

	}

	public void  abrirTiketsdeViaje(){
		if(rep_reporte.isVisible()){			
			rep_reporte.cerrar();
			set_tipo_transporte.dibujar();
			map_parametros.clear(); ///limpia parametros
		}

		else if(set_tipo_transporte.isVisible()){
			if(set_tipo_transporte.getValorSeleccionado()!=null){
				map_parametros.put("ide_cotit", Integer.parseInt( set_tipo_transporte.getValorSeleccionado()));
				set_tipo_transporte.cerrar();
				set_asunto_viaje.dibujar();
			}
			else{
				utilitario.agregarMensajeInfo("Seleccione un tipo de transporte", "");
			}

		}
		else if(set_asunto_viaje.isVisible()){
			if(set_asunto_viaje.getValorSeleccionado()!=null){
				map_parametros.put("ide_coasv", Integer.parseInt( set_asunto_viaje.getValorSeleccionado()));
				set_asunto_viaje.cerrar();
				set_estado.dibujar();
			}
			else{
				utilitario.agregarMensajeInfo("Seleccione un asunto de viaje", "");
			}
		}
		else if(set_estado.isVisible()){
			if(set_estado.getValorSeleccionado()!=null){
				map_parametros.put("ide_coest", Integer.parseInt( set_estado.getValorSeleccionado()));
				set_estado.cerrar();
				sea_distribucion.dibujar();
			}
			else{
				utilitario.agregarMensajeInfo("Seleccione un estado", "");
			}
		}
		else if(sea_distribucion.isVisible()){
			if(sea_distribucion.getSeleccionados()!=null){
				//map_parametros.put("ide_indip", Integer.parseInt( sea_distribucion.getSeleccionados()));
				sea_distribucion.cerrar();
				sef_formato.setSeleccionFormatoReporte(map_parametros, rep_reporte.getPath());
				sef_formato.dibujar();		
			}
			else{
				utilitario.agregarMensajeInfo("Seleccione un estado", "");
			}

		}


	}




	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (com_tipo_transporte.getValue()==null){
			utilitario.agregarMensajeInfo("No se puede insertar", "Debe Seleccionar un Transporte");
			return;
		}		


		if(tab_tiket_viaje.isFocus()){
			tab_tiket_viaje.insertar();
			tab_tiket_viaje.setValor("ide_cotit", com_tipo_transporte.getValue()+"");

		}

		else if(tab_cont_viajeros.isFocus()){
			tab_cont_viajeros.insertar();

		}

	}
	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_tiket_viaje.guardar()){
			tab_cont_viajeros.guardar();		
		}
		guardarPantalla();		
	}



	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_tiket_viaje.isFocus()){
			tab_tiket_viaje.eliminar();
		}
		else if(tab_cont_viajeros.isFocus()){
			tab_cont_viajeros.eliminar();
		}		
	}
	public void seleccionaTipo_Transporte(){
		if(com_tipo_transporte.getValue()!=null){
			tab_tiket_viaje.setCondicion("ide_cotit="+com_tipo_transporte.getValue());
			tab_tiket_viaje.ejecutarSql();
			tab_cont_viajeros.ejecutarValorForanea(tab_tiket_viaje.getValorSeleccionado());
		}
		else {
			tab_tiket_viaje.setCondicion("ide_cotit=-1");
			tab_tiket_viaje.ejecutarSql();
			tab_cont_viajeros.ejecutarValorForanea(tab_tiket_viaje.getValorSeleccionado());

		}
	}





	public Reporte getRep_reporte() {
		return rep_reporte;
	}

	public void setRep_reporte(Reporte rep_reporte) {
		this.rep_reporte = rep_reporte;
	}

	public SeleccionFormatoReporte getSef_formato() {
		return sef_formato;
	}

	public Tabla getTab_tiket_viaje() {
		return tab_tiket_viaje;
	}

	public void setTab_tiket_viaje(Tabla tab_tiket_viaje) {
		this.tab_tiket_viaje = tab_tiket_viaje;
	}

	public Tabla getTab_cont_viajeros() {
		return tab_cont_viajeros;
	}

	public void setTab_cont_viajeros(Tabla tab_cont_viajeros) {
		this.tab_cont_viajeros = tab_cont_viajeros;
	}

	public void setSef_formato(SeleccionFormatoReporte sef_formato) {
		this.sef_formato = sef_formato;
	}


	public SeleccionTabla getSet_tipo_transporte() {
		return set_tipo_transporte;
	}


	public void setSet_tipo_transporte(SeleccionTabla set_tipo_transporte) {
		this.set_tipo_transporte = set_tipo_transporte;
	}


	public SeleccionTabla getSet_asunto_viaje() {
		return set_asunto_viaje;
	}


	public void setSet_asunto_viaje(SeleccionTabla set_asunto_viaje) {
		this.set_asunto_viaje = set_asunto_viaje;
	}


	public SeleccionTabla getSet_estado() {
		return set_estado;
	}


	public void setSet_estado(SeleccionTabla set_estado) {
		this.set_estado = set_estado;
	}


	public SeleccionArbol getSea_distribucion() {
		return sea_distribucion;
	}


	public void setSea_distribucion(SeleccionArbol sea_distribucion) {
		this.sea_distribucion = sea_distribucion;
	}


}
