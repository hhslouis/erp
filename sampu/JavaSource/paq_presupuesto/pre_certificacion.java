package paq_presupuesto;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import paq_sistema.ejb.ServicioSeguridad;

public class pre_certificacion extends Pantalla{
	private Tabla tab_certificacion=new Tabla();
	private Tabla tab_poa_certificacion=new Tabla();
	private SeleccionTabla set_poa=new SeleccionTabla();
	private Combo com_anio=new Combo();
	private String empleado;

  
  @EJB
 private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
  @EJB
 private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto)utilitario.instanciarEJB(ServicioPresupuesto.class);
  @EJB
private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
  @EJB
private ServicioSeguridad ser_seguridad = (ServicioSeguridad) utilitario.instanciarEJB(ServicioSeguridad.class);
			
	
	public pre_certificacion(){
		///BOTON COMBO
		empleado=ser_seguridad.getUsuario(utilitario.getVariable("ide_usua")).getValor("ide_gtemp");
		System.out.println("empleado"+empleado);
		if(empleado==null ||empleado.isEmpty()){
			utilitario.agregarNotificacionInfo("Mensaje", "No exixte usuario registrado para el registro de compras");
			return;
		}

		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El A�o:"));
		bar_botones.agregarComponente(com_anio);

		
		tab_certificacion.setId("tab_certificacion");
		tab_certificacion.setHeader("CERTIFICACION PRESUPUESTARIA");
		tab_certificacion.setTabla("pre_certificacion", "ide_prcer", 1);
		tab_certificacion.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_certificacion.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_certificacion.getColumna("gen_ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_certificacion.getColumna("gen_ide_geedp").setAutoCompletar();
		tab_certificacion.getColumna("ide_gtemp").setCombo(ser_nomina.servicioEmpleadosActivos("true,false"));
		tab_certificacion.getColumna("ide_gtemp").setLectura(true);
		tab_certificacion.getColumna("ide_gtemp").setAutoCompletar();
		tab_certificacion.getColumna("activo_prcer").setValorDefecto("true");
		tab_certificacion.getColumna("ide_geani").setCombo(ser_contabilidad.getAnio("true,false","true,false"));
		tab_certificacion.setCondicion("ide_geani=-1"); 
		tab_certificacion.getColumna("ide_geani").setVisible(false);
		tab_certificacion.getColumna("VALOR_LIBERADO_PRCER").setMetodoChange("calcularCertificacion");
		tab_certificacion.getColumna("VALOR_LIBERADO_PRCER").setValorDefecto("0.00");
		tab_certificacion.getColumna("VALOR_LIBERADO_PRCER").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:black");
		tab_certificacion.getColumna("VALOR_DISPONIBLE_PRCER").setEtiqueta();
		tab_certificacion.getColumna("VALOR_DISPONIBLE_PRCER").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:black");
		tab_certificacion.getColumna("VALOR_DISPONIBLE_PRCER").setValorDefecto("0.00");
		tab_certificacion.getColumna("VALOR_CERTIFICACION_PRCER").setEtiqueta();
		tab_certificacion.getColumna("VALOR_CERTIFICACION_PRCER").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:black");
		tab_certificacion.getColumna("VALOR_CERTIFICACION_PRCER").setValorDefecto("0.00");

		tab_certificacion.setTipoFormulario(true);
		tab_certificacion.getGrid().setColumns(4);
		tab_certificacion.agregarRelacion(tab_poa_certificacion);
		tab_certificacion.dibujar();
		PanelTabla pat_certificacion=new PanelTabla();
		pat_certificacion.setPanelTabla(tab_certificacion);
		
		////// poa certificacion
		tab_poa_certificacion.setId("tab_poa_certificacion");
		tab_poa_certificacion.setHeader("POA CERTIFICACION");
		tab_poa_certificacion.setTabla("pre_poa_certificacion", "ide_prpoc", 2);
		tab_poa_certificacion.getColumna("ide_prpoa").setCombo(ser_presupuesto.getPoa("true,false"));
		tab_poa_certificacion.getColumna("activo_prpoc").setValorDefecto("true");
		tab_poa_certificacion.getColumna("valor_certificado_prpoc").setMetodoChange("calcular");
		tab_poa_certificacion.dibujar();
		PanelTabla pat_poa_certi=new PanelTabla();
		pat_poa_certi.setPanelTabla(tab_poa_certificacion);
		
		
		Division div_divi=new Division();
		div_divi.dividir2(pat_certificacion, pat_poa_certi, "50%", "H");
		agregarComponente(div_divi);
		
		
		/////boton buscar poa
		Boton bot_buscar=new Boton();
		bot_buscar.setIcon("ui-icon-person");
		bot_buscar.setValue("Buscar POA");
		bot_buscar.setMetodo("importarPoa");
		bar_botones.agregarBoton(bot_buscar);

		set_poa.setId("set_poa");
		set_poa.setSeleccionTabla(ser_presupuesto.getPoa("true,false"),"ide_prpoa");
		set_poa.setTitle("Seleccione Poa");
		set_poa.getTab_seleccion().getColumna("codigo_clasificador_prcla").setFiltro(true);
		set_poa.getTab_seleccion().getColumna("descripcion_clasificador_prcla").setFiltro(true);
		set_poa.setRadio();
		set_poa.getBot_aceptar().setMetodo("aceptarPoa");
		agregarComponente(set_poa);

	}
	
	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_certificacion.setCondicion("ide_geani="+com_anio.getValue());
			tab_certificacion.ejecutarSql();

		}
	}
	//////Calcular certificacion
	public void calcularCertificacion (){
		double dou_valor_certif=0;
		double dou_valor_liberado=0;
		double dou_valor_disponible=0;
		
		try {
			//Obtenemos el valor de la cantidad
			dou_valor_liberado=Double.parseDouble(tab_certificacion.getValor("VALOR_LIBERADO_PRCER"));
		} catch (Exception e) {
		}
		
		////suma la los mismos valores
		String valorcert=tab_poa_certificacion.getSumaColumna("valor_certificado_prpoc")+"";
		dou_valor_certif=Double.parseDouble(valorcert);
		
		///valor disponible
		dou_valor_disponible=dou_valor_certif-dou_valor_liberado;
				
		
		//Asignamos el total a la tabla detalle, con 2 decimales
		tab_certificacion.setValor("VALOR_CERTIFICACION_PRCER",utilitario.getFormatoNumero(valorcert,2));
		tab_certificacion.setValor("VALOR_DISPONIBLE_PRCER", utilitario.getFormatoNumero(dou_valor_disponible));
		tab_certificacion.modificar(tab_certificacion.getFilaActual());//para que haga el update

		utilitario.addUpdateTabla(tab_certificacion, "VALOR_CERTIFICACION_PRCER,VALOR_DISPONIBLE_PRCER", "");	
			
	}
	
	////para llamar al metodo
	public void calcular(AjaxBehaviorEvent evt) {
		tab_poa_certificacion.modificar(evt); //Siempre es la primera linea
		calcularCertificacion();

	}

	
////importar poa
	public void importarPoa(){
		System.out.println(" ingresar al importar");
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un a�o", "");
			return;
		}
		else if(tab_certificacion.isEmpty()){
			utilitario.agregarMensajeInfo("No puede buscar un POA", "Debe tener una Certificaci�n Presupuestaria");
		}

		set_poa.getTab_seleccion().setSql(ser_presupuesto.getPoa("true,false"));
		set_poa.getTab_seleccion().ejecutarSql();
		set_poa.dibujar();

	}

	public  void aceptarPoa(){
		String str_seleccionados= set_poa.getValorSeleccionado();

		if (str_seleccionados!=null){
			TablaGenerica tab_poa = ser_presupuesto.getTablaGenericaPoa(str_seleccionados);		
				tab_poa_certificacion.insertar();
				tab_poa_certificacion.setValor("ide_prpoa", tab_poa.getValor( "ide_prpoa"));
			
			set_poa.cerrar();
			utilitario.addUpdate("tab_poa_certificacion");
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		}
	
	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		String ide_gtempxx=ser_seguridad.getUsuario(utilitario.getVariable("ide_usua")).getValor("ide_gtemp");
		

		
		
		if(com_anio.getValue()==null){
			utilitario.agregarMensaje("No se puede insertar", "Debe Seleccionar un a�o");
			return;
		
		}
		if(tab_certificacion.isFocus()){
			tab_certificacion.insertar();
			tab_certificacion.setValor("ide_gtemp",ide_gtempxx );
			utilitario.addUpdate("tab_certificacion");
			tab_certificacion.setValor("ide_geani",com_anio.getValue()+"");

		}
		else if(tab_poa_certificacion.isFocus()){
			utilitario.agregarMensajeInfo("No puede insertar", "Debe agrear un poa");
		}
		

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_certificacion.guardar()){
			if(tab_poa_certificacion.guardar()){
				guardarPantalla();
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
	}

	public Tabla getTab_certificacion() {
		return tab_certificacion;
	}

	public void setTab_certificacion(Tabla tab_certificacion) {
		this.tab_certificacion = tab_certificacion;
	}

	public Tabla getTab_poa_certificacion() {
		return tab_poa_certificacion;
	}

	public void setTab_poa_certificacion(Tabla tab_poa_certificacion) {
		this.tab_poa_certificacion = tab_poa_certificacion;
	}
	public SeleccionTabla getSet_poa() {
		return set_poa;
	}
	public void setSet_poa(SeleccionTabla set_poa) {
		this.set_poa = set_poa;
	}

	public Combo getCom_anio() {
		return com_anio;
	}

	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}

}