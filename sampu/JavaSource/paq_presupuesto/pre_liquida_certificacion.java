package paq_presupuesto;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
public class pre_liquida_certificacion extends Pantalla {
	
	private Tabla tab_liquida_certificacion=new Tabla();
	private  Tabla tab_detalle=new Tabla();
	private SeleccionTabla set_poa=new SeleccionTabla();
	private SeleccionTabla set_certificacion=new SeleccionTabla();
	private Combo com_anio=new Combo();

	
	 @EJB
	private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto)utilitario.instanciarEJB(ServicioPresupuesto.class);
	 @EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
			

	
	public pre_liquida_certificacion (){
		
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		tab_liquida_certificacion.setId("tab_liquida_certificacion");
		tab_liquida_certificacion.setHeader("LIQUIDACION CERTIFICACION");
		tab_liquida_certificacion.setTabla("pre_liquida_certificacion", "ide_prlce", 1);
		tab_liquida_certificacion.getColumna("activo_prlce").setValorDefecto("true");
		tab_liquida_certificacion.getColumna("ide_prcer").setCombo(ser_presupuesto.getCertificacion("true,false"));
		tab_liquida_certificacion.getColumna("ide_prcer").setAutoCompletar();
		tab_liquida_certificacion.getColumna("ide_prcer").setLectura(true);
		tab_liquida_certificacion.agregarRelacion(tab_detalle);
		tab_liquida_certificacion.dibujar();
		PanelTabla pat_liquida =new PanelTabla();
		pat_liquida.setPanelTabla(tab_liquida_certificacion);
		
		///// detalle liquida certificacion
		
		tab_detalle.setId("tab_detalle");
		tab_detalle.setHeader("DETALLE LIQUIDACION CERTIFICACION");
		tab_detalle.setTabla("pre_detalle_liquida_certif", "ide_prdcl", 2);
		tab_detalle.getColumna("activo_prdcl").setValorDefecto("true");
		tab_detalle.getColumna("ide_prpoa").setCombo(ser_presupuesto.getPoaTodos());
		tab_detalle.getColumna("ide_prpoa").setAutoCompletar();
		tab_detalle.dibujar();
		PanelTabla pat_detalle=new PanelTabla();
		pat_detalle.setPanelTabla(tab_detalle);
		
		
		Division div_divi=new Division();
		div_divi.dividir2(pat_liquida, pat_detalle, "50%", "H");
		
		agregarComponente(div_divi);

	
	/////boton buscar poa
			Boton bot_buscar=new Boton();
			bot_buscar.setIcon("ui-icon-person");
			bot_buscar.setValue("Buscar POA");
			bot_buscar.setMetodo("importarPoa");
			bar_botones.agregarBoton(bot_buscar);

			set_poa.setId("set_poa");
			set_poa.setSeleccionTabla(ser_presupuesto.getPoa("-1"),"ie_prpoa");
			set_poa.setTitle("Seleccione Poa");
			set_poa.getBot_aceptar().setMetodo("aceptarPoa");
			agregarComponente(set_poa);


		/////boton buscar poa
			Boton bot_certificacion=new Boton();
			bot_certificacion.setIcon("ui-icon-person");
			bot_certificacion.setValue("Buscar Certificación");
			bot_certificacion.setMetodo("importarCertificacion");
			bar_botones.agregarBoton(bot_certificacion);

			set_certificacion.setId("set_certificacion");
			set_certificacion.setSeleccionTabla(ser_presupuesto.getCertificacion("true,false"),"ide_prcer");
			set_certificacion.setTitle("Seleccione Certificación");
			set_certificacion.setRadio();
			set_certificacion.getBot_aceptar().setMetodo("aceptarCertificacion");
			agregarComponente(set_certificacion);

		
		
	}
	
////importar poa
	public void importarPoa(){
		//si no selecciono ningun valor en el combo
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		set_poa.getTab_seleccion().setSql(ser_presupuesto.getPoa(com_anio.getValue().toString()));
		set_poa.getTab_seleccion().ejecutarSql();
		set_poa.dibujar();

	}

	public  void aceptarPoa(){
		String str_seleccionados = set_poa.getSeleccionados();
		

		if (str_seleccionados!=null){
			TablaGenerica tab_poa = ser_presupuesto.getTablaGenericaPoa(str_seleccionados);		
			for(int i=0;i<tab_poa.getTotalFilas();i++){
				tab_detalle.insertar();
				tab_detalle.setValor("ide_prpoa", tab_poa.getValor(i, "ide_prpoa"));
				tab_detalle.setValor("valor_certificado_prdcl", tab_poa.getValor("presupuesto_codificado_prpoa"));
			}
			set_poa.cerrar();
			utilitario.addUpdate("tab_detalle");
		}
	}

	/////certificacion
	public void importarCertificacion(){
		//si no selecciono ningun valor en el combo
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		set_certificacion.getTab_seleccion().setSql(ser_presupuesto.getCertificacion("true,false"));
		set_certificacion.getTab_seleccion().ejecutarSql();
		set_certificacion.dibujar();

	}
	public  void aceptarCertificacion(){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		String str_seleccionado = set_certificacion.getValorSeleccionado();
		
		if (str_seleccionado!=null){
			tab_liquida_certificacion.insertar();
			tab_liquida_certificacion.setValor("ide_prcer", str_seleccionado);
			}
			set_certificacion.cerrar();
			utilitario.addUpdate("tab_liquida_certificacion");
		}
	


	
	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(tab_liquida_certificacion.isFocus()){
		utilitario.agregarMensajeInfo("No puede insertar", "Debe buscar una Certificaciòn");
		}
		else if(tab_detalle.isFocus()){
			utilitario.agregarMensajeInfo("No puede insertar", "Debe ingresar un poa");
		}
	}
		
	

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_liquida_certificacion.guardar()){
			if(tab_detalle.guardar()){
				guardarPantalla();
				
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}


	public Tabla getTab_liquida_certificacion() {
		return tab_liquida_certificacion;
	}


	public void setTab_liquida_certificacion(Tabla tab_liquida_certificacion) {
		this.tab_liquida_certificacion = tab_liquida_certificacion;
	}


	public Tabla getTab_detalle() {
		return tab_detalle;
	}


	public void setTab_detalle(Tabla tab_detalle) {
		this.tab_detalle = tab_detalle;
	}

	public SeleccionTabla getSet_poa() {
		return set_poa;
	}

	public void setSet_poa(SeleccionTabla set_poa) {
		this.set_poa = set_poa;
	}

	public SeleccionTabla getSet_certificacion() {
		return set_certificacion;
	}

	public void setSet_certificacion(SeleccionTabla set_certificacion) {
		this.set_certificacion = set_certificacion;
	}

}
