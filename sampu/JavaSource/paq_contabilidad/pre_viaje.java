package paq_contabilidad;
import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.Imagen;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Texto;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;


public class pre_viaje extends Pantalla{

	private Dialogo empleado_dialogo=new Dialogo();
	
	private Tabla tab_tiket_viaje = new Tabla();
	private Tabla tab_cont_viajeros = new Tabla();	
	private Combo com_tipo_transporte=new Combo();
	private SeleccionTabla set_empleado =new SeleccionTabla();


	
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	
	public pre_viaje() {
		
		
		
		com_tipo_transporte.setCombo("select ide_cotit,detalle_cotit from cont_tipo_transporte where activo_cotit = true" +
				" order by detalle_cotit");
		com_tipo_transporte.setMetodo("seleccionaTipoTransporte");
		bar_botones.agregarComponente(new Etiqueta("Tipo de Transporte:"));
		bar_botones.agregarComponente(com_tipo_transporte);			
		//div1
		tab_tiket_viaje.setId("tab_tiket_viaje");
		tab_tiket_viaje.setHeader("TIKET DE VIAJE");

		tab_tiket_viaje.setTabla("cont_tiket_viaje", "ide_cotiv",1);
		tab_tiket_viaje.getColumna("ide_cotit").setVisible(false);
		tab_tiket_viaje.setCondicion("ide_cotit = -1");
		tab_tiket_viaje.getColumna("ide_coasv").setCombo("cont_asunto_viaje","ide_coasv", "detalle_coasv", "");
		tab_tiket_viaje.getColumna("IDE_COEST").setCombo("cont_estado","IDE_COEST", "DETALLE_COEST", "");
		
		tab_tiket_viaje.getColumna("IDE_GEDIP").setCombo(ser_gestion.getSqlDivisionPoliticaCiudad());
		
		tab_tiket_viaje.getColumna("GEN_IDE_GEDIP").setCombo(ser_gestion.getSqlDivisionPoliticaCiudad());
		tab_tiket_viaje.setTipoFormulario(true);
		tab_tiket_viaje.getGrid().setColumns(4);		
		tab_tiket_viaje.dibujar();
		
		tab_tiket_viaje.agregarRelacion(tab_cont_viajeros);
		//Imagen fondo= new Imagen(); 
	    PanelTabla pat_tiket_viaje =new PanelTabla();
	    pat_tiket_viaje.setPanelTabla(tab_tiket_viaje);
		
	   // fondo.setStyle("text-aling:center;position:absolute;top:100px;left:490px;");
		//fondo.setValue("imagenes/logo.png");
		//pat_tiket_viaje.setWidth("100%");
		//pat_tiket_viaje.getChildren().add(fondo);
	    
	    //div 2	    
	    
	    tab_cont_viajeros.setId("tab_cont_viajeros");
	    tab_cont_viajeros.setHeader("VIAJERO");
	    
	    tab_cont_viajeros.setTabla("cont_viajeros", "ide_covia",2);
	    tab_cont_viajeros.setCampoForanea("ide_cotiv");
	    
	    tab_cont_viajeros.getColumna("IDE_COCLV").setCombo("SELECT IDE_COCLV, DETALLE_COCLV " +
	    		" FROM cont_clase_viaje WHERE ACTIVO_COCLV = TRUE ORDER BY DETALLE_COCLV");

		tab_cont_viajeros.getColumna("IDE_GTEMP").setVisible(false);
	    tab_cont_viajeros.setCampoForanea("ide_cotiv");

	    tab_cont_viajeros.getColumna("BOLETO_COVIA").setUpload("viajes");

	    tab_cont_viajeros.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
	    tab_cont_viajeros.getColumna("IDE_GEEDP").setAutoCompletar();
	    tab_cont_viajeros.getColumna("IDE_GEEDP").setLectura(true);
	    tab_cont_viajeros.getColumna("IDE_GEEDP").setUnico(true);
	    tab_cont_viajeros.getColumna("ide_cotiv").setUnico(true);
	    
	    
		tab_cont_viajeros.getColumna("ACTIVO_COVIA").setValorDefecto("TRUE");
	    tab_cont_viajeros.dibujar();
		PanelTabla pat_cont_viajeros = new PanelTabla ();
		pat_cont_viajeros.setPanelTabla(tab_cont_viajeros);
		
		Division div_division=new Division();
		div_division.dividir2(pat_tiket_viaje, pat_cont_viajeros, "50%", "H");
		agregarComponente(div_division);
		
		Boton bot_importar=new Boton();
		bot_importar.setIcon("ui-icon-person");
		bot_importar.setValue("Agregar Empleado");
		bot_importar.setMetodo("importarEmpleado");
		bar_botones.agregarBoton(bot_importar);
		
		set_empleado.setId("set_empleado");
		set_empleado.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		//set_empleado.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		
		System.out.println("parametro..set_empleado .."+set_empleado);
		
		set_empleado.getTab_seleccion().getColumna(" documento_identidad_gtemp").setFiltro(true);
		set_empleado.getTab_seleccion().getColumna(" nombres_apellidos").setFiltro(true);

		//set_empleado.getTab_seleccion().getColumna("nombre_apellido").setFiltro(true);
		set_empleado.setTitle("Seleccione un Empleado");
		set_empleado.getBot_aceptar().setMetodo("aceptarEmpleado");
		agregarComponente(set_empleado);		

//fin dialogo empleado		
	}

	public void importarEmpleado(){
		
		if(com_tipo_transporte.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Tipo de Transporte", "");
			return;
		}
		else if(tab_tiket_viaje.isEmpty()){
			utilitario.agregarMensajeInfo("Debe insertar un registro de ticket de Viaje", "");
			return;
		} 
		
		set_empleado.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
		set_empleado.getTab_seleccion().ejecutarSql();
		set_empleado.dibujar();

	}
	

public void aceptarEmpleado(){
	String str_seleccionados=set_empleado.getSeleccionados();
	if(str_seleccionados!=null){
		//Inserto los empleados seleccionados en la tabla de participantes 
		TablaGenerica tab_empleado = ser_nomina.ideEmpleadoContrato(str_seleccionados);		
		
		//set_empleado.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		
		System.out.println(" tabla generica"+tab_empleado.getSql());
		for(int i=0;i<tab_empleado.getTotalFilas();i++){
			tab_cont_viajeros.insertar();
			tab_cont_viajeros.setValor("IDE_GEEDP", tab_empleado.getValor(i, "IDE_GEEDP"));			
			tab_cont_viajeros.setValor("IDE_GTEMP", tab_empleado.getValor(i, "IDE_GTEMP"));			
			
		}
		set_empleado.cerrar();
		utilitario.addUpdate("tab_cont_viajeros");			
	}
	else{
		utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
	}
}
	
	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_tipo_transporte.getValue()==null){
			utilitario.agregarMensaje("No se puede insertar", "Debe Seleccionar un transporte");
			return;
		
		}
		if (tab_tiket_viaje.isFocus()) {
			tab_tiket_viaje.insertar();
			tab_tiket_viaje.setValor("ide_cotit", com_tipo_transporte.getValue()+"");
			
		}
		else if (tab_cont_viajeros.isFocus()) {
			utilitario.agregarMensaje("No se puede insertar", "Debe Agregar un Empleado");
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
	
	public void seleccionaTipoTransporte(){
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
	
	
//Dialogo Empleaado
	public void aceptarDialogo(){
		//Muestra un mensaje al dar click sobre el boton aceptar del Dialogo
		utilitario.agregarMensaje("SU NOMBRE ES","");
		empleado_dialogo.cerrar();//cierra el dialogo
		}
		public void abrirDialogo(){
		//Dibuja el dialogo al dar click sobre el boton abrir
			empleado_dialogo.dibujar();
		}	
// Fin Dialogo	Empleado
	
	

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

	public Combo getCom_tipo_transporte() {
		return com_tipo_transporte;
	}

	public void setCom_tipo_transporte(Combo com_tipo_transporte) {
		this.com_tipo_transporte = com_tipo_transporte;
	}

	public Dialogo getEmpleado_dialogo() {
		return empleado_dialogo;
	}

	public void setEmpleado_dialogo(Dialogo empleado_dialogo) {
		this.empleado_dialogo = empleado_dialogo;
	}

	public SeleccionTabla getSet_empleado() {
		return set_empleado;
	}

	public void setSet_empleado(SeleccionTabla set_empleado) {
		this.set_empleado = set_empleado;
	}



	

}
