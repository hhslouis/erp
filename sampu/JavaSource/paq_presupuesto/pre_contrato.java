package paq_presupuesto;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Confirmar;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Imagen;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_contrato extends Pantalla {
	private Tabla tab_contrato =new Tabla();
	private Tabla tab_administrador=new Tabla();
	private Tabla tab_compareciente =new Tabla();
	private Tabla tab_archivo =new Tabla();
	private SeleccionTabla set_empleado=new SeleccionTabla();
	private SeleccionTabla set_actualizar = new SeleccionTabla();
	private SeleccionTabla set_compareciente=new SeleccionTabla();
	private SeleccionTabla set_actualizar_compareciente=new SeleccionTabla();
	private Dialogo dia_empleado=new Dialogo();
	private Dialogo dia_compareciente=new Dialogo();
	private Confirmar con_guardar_compareciente=new Confirmar();
	private Confirmar con_guardar =new Confirmar();
	
	
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	
	public pre_contrato (){
	
		Tabulador tab_tabulador = new Tabulador();
		tab_tabulador.setId("tab_tabulador");
		tab_contrato.setId("tab_contrato");
		tab_contrato.setHeader("SEGUIMIENTO DE CONTRATOS");
		tab_contrato.setTabla("pre_contrato", "ide_prcon", 1);
		tab_contrato.getColumna("ide_coest").setCombo("cont_estado", " ide_coest", "detalle_coest", "");
		tab_contrato.setTipoFormulario(true);
		tab_contrato.getGrid().setColumns(4);
		tab_contrato.agregarRelacion(tab_administrador);//agraga relacion para los tabuladores
		tab_contrato.agregarRelacion(tab_compareciente);
		tab_contrato.agregarRelacion(tab_archivo);
		tab_contrato.dibujar();
		PanelTabla pat_contratacion=new PanelTabla();
		pat_contratacion.setPanelTabla(tab_contrato);
		
		//tabla administrador_contrato
		tab_administrador.setId("tab_administrador");
		tab_administrador.setHeader("ADMINISTRADOR CONTRATO");
		tab_administrador.setIdCompleto("tab_tabulador:tab_administrador");
		tab_administrador.setTabla("pre_administrador_contrato", "ide_pradc", 2);
		tab_administrador.getColumna("IDE_GTEMP").setVisible(false);
		//tab_administrador.setCampoForanea("ide_prcon");
		tab_administrador.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_administrador.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_administrador.getColumna("IDE_GEEDP").setLectura(true);
		tab_administrador.getColumna("IDE_GEEDP").setUnico(true);
		tab_administrador.getColumna("ide_pradc").setUnico(true);
		tab_administrador.setCampoForanea("ide_prcon");
		tab_administrador.dibujar();
		PanelTabla pat_administrador =new PanelTabla();
		pat_administrador.setPanelTabla(tab_administrador);
		
		// tabla compareciente contrato
		tab_compareciente.setId("tab_compareciente");
		tab_compareciente.setHeader("COMPARECIENTE CONTRATO");
		tab_compareciente.setIdCompleto("tab_tabulador:tab_compareciente");
		tab_compareciente.setTabla("pre_compareciente_contrato", "ide_prcoc", 3);
		tab_compareciente.getColumna("ide_prtio").setCombo("pre_tipo_compareciente", "ide_prtio", "detalle_prtio", "");
		tab_compareciente.getColumna("ide_getip").setCombo("gen_tipo_persona", "ide_getip", "detalle_getip", "");
		tab_compareciente.getColumna("IDE_GTEMP").setVisible(false);
		tab_compareciente.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_compareciente.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_compareciente.getColumna("IDE_GEEDP").setLectura(true);
		tab_compareciente.getColumna("IDE_GEEDP").setUnico(true);
		tab_compareciente.getColumna("ide_prcoc").setUnico(true);
		tab_compareciente.setCampoForanea("ide_prcon");
		tab_compareciente.dibujar();
		PanelTabla pat_compareciente =new PanelTabla();
		pat_compareciente.setPanelTabla(tab_compareciente);
				
	
		
		//ARCHIVO
		tab_archivo.setId("tab_archivo");
		tab_archivo.setHeader("ARCHIVOS ANEXOS");
		tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
		tab_archivo.setTipoFormulario(true);
		tab_archivo.setTabla("pre_archivo","ide_prarc",5);
		tab_archivo.getColumna("foto_prarc").setUpload("presupuesto");
		tab_archivo.setCampoForanea("ide_prcop");
		//ocultar campos de las claves  foraneas
		TablaGenerica  tab_generica=ser_contabilidad.getTablaVigente("pre_archivo");
		for(int i=0;i<tab_generica.getTotalFilas();i++){
		//muestra los ides q quiere mostras.
		if(!tab_generica.getValor(i, "column_name").equals("ide_prcon")){	
		tab_archivo.getColumna(tab_generica.getValor(i, "column_name")).setVisible(false);	
		}				
		}
		tab_archivo.setCondicion("ide_prcon!=null");
		tab_archivo.dibujar();
		PanelTabla pat_archivo= new PanelTabla();
		pat_archivo.setPanelTabla(tab_archivo);
		// FONDO
		Imagen fondo= new Imagen();  
		fondo.setStyle("text-aling:center;position:absolute;top:100px;left:490px;");
		fondo.setValue("imagenes/logo.png");
		pat_archivo.setWidth("100%");
		pat_archivo.getChildren().add(fondo);

		tab_tabulador.agregarTab("ADMINISTRADOR CONTRATO", pat_administrador);//intancia los tabuladores 
		tab_tabulador.agregarTab("COMPARECIENTE CONTRATO",pat_compareciente);
		tab_tabulador.agregarTab("ANEXO ARCHIVOS",pat_archivo);
		

		//division2
		
		Division div_division=new Division();
		div_division.dividir2(pat_contratacion,tab_tabulador,"50%","H");
		agregarComponente(div_division);
		
		//Pantalla Dialogo 
		//bara el boton administrador 
		Boton bot_empleado=new Boton();
		bot_empleado.setIcon("ui-icon-person");
		bot_empleado.setValue("Agregar Administrador");
		bot_empleado.setMetodo("importarEmpleado");
		
		bar_botones.agregarBoton(bot_empleado);
		con_guardar.setId("con_guardar");
		agregarComponente(con_guardar);
				
			
		set_empleado.setId("set_empleado");
		set_empleado.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		set_empleado.getTab_seleccion().getColumna("documento_identidad_gtemp").setFiltro(true);
		set_empleado.getTab_seleccion().getColumna("nombres_apellidos").setFiltro(true);
		set_empleado.setTitle("Seleccione un Empleado");
		set_empleado.getBot_aceptar().setMetodo("aceptarEmpleado");
		agregarComponente(set_empleado);
		
		// Boton actualizar administrador 
		Boton bot_actualizar=new Boton();
		bot_actualizar.setIcon("ui-icon-person");
		bot_actualizar.setValue("Actualizar Administrador");
		bot_actualizar.setMetodo("actualizarAdministrador");
		bar_botones.agregarBoton(bot_actualizar);
		
		set_actualizar.setId("set_actualizar");
		set_actualizar.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		set_actualizar.getTab_seleccion().getColumna("documento_identidad_gtemp").setFiltro(true);
		set_actualizar.getTab_seleccion().getColumna("nombres_apellidos").setFiltro(true);
		set_actualizar.setRadio();
		set_actualizar.getBot_aceptar().setMetodo("modificarAdministrador");
		agregarComponente(set_actualizar);	
		
		
		//boton compareciente agregar compareciente
		Boton bot_compareciente=new Boton();
		bot_compareciente.setIcon("ui-icon-person");
		bot_compareciente.setValue("Agregar Compareciente");
		bot_compareciente.setMetodo("importarCompareciente");
		bar_botones.agregarBoton(bot_compareciente);
		
		bar_botones.agregarBoton(bot_compareciente);
		con_guardar_compareciente.setId("con_guardar_compareciente");
		agregarComponente(con_guardar_compareciente);
				
		
		set_compareciente.setId("set_compareciente");
		set_compareciente.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"), "ide_geedp");
		set_compareciente.getTab_seleccion().getColumna("documento_identidad_gtemp").setFiltro(true);
		set_compareciente.getTab_seleccion().getColumna("nombres_apellidos").setFiltro(true);
		set_compareciente.setTitle("Seleccione un Empleado");
		set_compareciente.getBot_aceptar().setMetodo("aceptarCompareciente");
		agregarComponente(set_compareciente);
		
		//Boton Actualizar Compareciente
		Boton bot_actualizar_compareciente=new Boton();
		bot_actualizar_compareciente.setIcon("ui-icon-person");
		bot_actualizar_compareciente.setValue("Actualizar Compareciente");
		bot_actualizar_compareciente.setMetodo("actualizarCompareciente");
		bar_botones.agregarBoton(bot_actualizar_compareciente);
		
		
		
		set_actualizar_compareciente.setId("set_actualizar_compareciente");
		set_actualizar_compareciente.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"), "ide_geedp");
		set_actualizar_compareciente.getTab_seleccion().getColumna("documento_identidad_gtemp").setFiltro(true);
		set_actualizar_compareciente.getTab_seleccion().getColumna("nombres_apellidos").setFiltro(true);
		set_actualizar_compareciente.setRadio();
		set_actualizar_compareciente.getBot_aceptar().setMetodo("modificarCompareciente");
		agregarComponente(set_actualizar_compareciente);	
	}

	
	//ADMINISTRADOR	
	public void actualizarAdministrador(){
		if (tab_administrador.getValor("ide_pradc")==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Administrador para actualizar","");
			return;
		}
		set_actualizar.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
		set_actualizar.getTab_seleccion().ejecutarSql();
		set_actualizar.dibujar();
			
	}
	public void importarEmpleado(){
		if (tab_contrato.isEmpty()) {
			utilitario.agregarMensajeInfo("Debe ingresar un registro en el contrato", "");
			return;
			
		}
							
		set_empleado.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
		set_empleado.getTab_seleccion().ejecutarSql();
		set_empleado.dibujar();
		}
			
	public void modificarAdministrador(){
		String str_empleadoActualizado=set_actualizar.getValorSeleccionado();
	   	TablaGenerica tab_empleadoModificadoAdministrador = ser_nomina.ideEmpleadoContrato(str_empleadoActualizado,"true");		
	    tab_administrador.setValor("IDE_GEEDP", tab_empleadoModificadoAdministrador.getValor("IDE_GEEDP"));			
	    tab_administrador.setValor("IDE_GTEMP", tab_empleadoModificadoAdministrador.getValor("IDE_GTEMP"));	
	    tab_administrador.modificar(tab_administrador.getFilaActual());
		utilitario.addUpdate("tab_administrador");	

		con_guardar.setMessage("Esta Seguro de Actualizar el Administrador");
		con_guardar.setTitle("CONFIRMCION DE ACTUALIZAR");
		con_guardar.getBot_aceptar().setMetodo("guardarActualilzarAdministrador");
		con_guardar.dibujar();
		utilitario.addUpdate("con_guardar");


	}
	public void guardarActualilzarAdministrador(){
		System.out.println("Entra a guardar...");
		tab_administrador.guardar();
		con_guardar.cerrar();
		set_actualizar.cerrar();


		guardarPantalla();

	}

	public void aceptarEmpleado(){
		String str_seleccionados=set_empleado.getSeleccionados();
		if(str_seleccionados!=null){
			//Inserto los empleados seleccionados en la tabla de resposable d econtratacion 
			TablaGenerica tab_empleado_responsable = ser_nomina.ideEmpleadoContrato(str_seleccionados,"true");		
						
			System.out.println(" tabla generica"+tab_empleado_responsable.getSql());
			for(int i=0;i<tab_empleado_responsable.getTotalFilas();i++){
				tab_administrador.insertar();
				tab_administrador.setValor("IDE_GEEDP", tab_empleado_responsable.getValor(i, "IDE_GEEDP"));			
				tab_administrador.setValor("IDE_GTEMP", tab_empleado_responsable.getValor(i, "IDE_GTEMP"));			
				
			}
			set_empleado.cerrar();
			utilitario.addUpdate("tab_responsable");			
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		}
	}
		

		//Dialogo Empleaado
		public void aceptarDialogo(){
				//Muestra un mensaje al dar click sobre el boton aceptar del Dialogo
				utilitario.agregarMensaje("SU NOMBRE ES","");
				dia_empleado.cerrar();//cierra el dialogo
				}
			public void abrirDialogo(){
				//Dibuja el dialogo al dar click sobre el boton abrir
				dia_empleado.dibujar();
				}	
		// Fin Dialogo	Empleado
	
			
			
///COMPARECIENTE
	public void importarCompareciente(){
		if (tab_contrato.isEmpty()) {
			utilitario.agregarMensajeInfo("Debe ingresar un registro en el contrato", "");
			return;
		}
							
		set_compareciente.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
		set_compareciente.getTab_seleccion().ejecutarSql();
		set_compareciente.dibujar();
	}
	
	public void aceptarCompareciente(){
		String str_seleccionados=set_compareciente.getSeleccionados();
		if(str_seleccionados!=null){
			//Inserto los empleados seleccionados en la tabla de resposable d econtratacion 
			TablaGenerica tab_empleado_responsable = ser_nomina.ideEmpleadoContrato(str_seleccionados,"true");		
						
			System.out.println(" tabla generica"+tab_empleado_responsable.getSql());
			for(int i=0;i<tab_empleado_responsable.getTotalFilas();i++){
				tab_compareciente.insertar();
				tab_compareciente.setValor("IDE_GEEDP", tab_empleado_responsable.getValor(i, "IDE_GEEDP"));			
				tab_compareciente.setValor("IDE_GTEMP", tab_empleado_responsable.getValor(i, "IDE_GTEMP"));			
				
			}
			set_compareciente.cerrar();
			utilitario.addUpdate("tab_compareciente");			
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		}
	}
	
	//Dialogo Empleaado
	public void aceptarDialogoCompareciente(){
			//Muestra un mensaje al dar click sobre el boton aceptar del Dialogo
			utilitario.agregarMensaje("SU NOMBRE ES","");
			dia_compareciente.cerrar();//cierra el dialogo
			}
		public void abrirDialogoCompareciente(){
			//Dibuja el dialogo al dar click sobre el boton abrir
			dia_compareciente.dibujar();
			}	
	// Fin Dialogo	Empleado
		
	public void actualizarCompareciente(){
			if (tab_compareciente.getValor("ide_prcoc")==null){
				utilitario.agregarMensajeInfo("Debe seleccionar un Compareciente para actualizar","");
				return;
			}
			set_actualizar_compareciente.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
			set_actualizar_compareciente.getTab_seleccion().ejecutarSql();
			set_actualizar_compareciente.dibujar();
				
		}
	
	public void modificarCompareciente(){
		String str_empleadoActualizado=set_actualizar_compareciente.getValorSeleccionado();
	   	TablaGenerica tab_empleadoModificadoCompareciente = ser_nomina.ideEmpleadoContrato(str_empleadoActualizado,"true");		
	    tab_compareciente.setValor("IDE_GEEDP", tab_empleadoModificadoCompareciente.getValor("IDE_GEEDP"));			
	    tab_compareciente.setValor("IDE_GTEMP", tab_empleadoModificadoCompareciente.getValor("IDE_GTEMP"));	
	    tab_compareciente.modificar(tab_compareciente.getFilaActual());
		utilitario.addUpdate("tab_administrador");	

		con_guardar_compareciente.setMessage("Esta Seguro de Actualizar el Compareciente");
		con_guardar_compareciente.setTitle("CONFIRMCION DE ACTUALIZAR");
		con_guardar_compareciente.getBot_aceptar().setMetodo("guardarActualilzarCompareciente");
		con_guardar_compareciente.dibujar();
		utilitario.addUpdate("con_guardar_compareciente");
	}
	
	public void guardarActualilzarCompareciente(){
		tab_compareciente.guardar();
		con_guardar_compareciente.cerrar();
		set_actualizar_compareciente.cerrar();


		guardarPantalla();

	}

	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		
		if (tab_contrato.isFocus()) {
			tab_contrato.insertar();
			//tab_contratacion.setValor("ide_geani", com_anio.getValue()+"");

		}
		else if (tab_administrador.isFocus()) {
			tab_administrador.insertar();

		}
		else if (tab_compareciente.isFocus()) {
			tab_compareciente.insertar();
			
		}

		
		else if (tab_archivo.isFocus()) {
			tab_archivo.insertar();

		}
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_contrato.guardar()) {
			
			if (tab_administrador.guardar()) {
				if( tab_compareciente.guardar()){
					tab_archivo.guardar();	
					}
					
				}
			}
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

	}

	public Tabla getTab_contrato() {
		return tab_contrato;
	}

	public void setTab_contrato(Tabla tab_contrato) {
		this.tab_contrato = tab_contrato;
	}

	public Tabla getTab_administrador() {
		return tab_administrador;
	}

	public void setTab_administrador(Tabla tab_administrador) {
		this.tab_administrador = tab_administrador;
	}

	public Tabla getTab_compareciente() {
		return tab_compareciente;
	}

	public void setTab_compareciente(Tabla tab_compareciente) {
		this.tab_compareciente = tab_compareciente;
	}

	public Tabla getTab_archivo() {
		return tab_archivo;
	}

	public void setTab_archivo(Tabla tab_archivo) {
		this.tab_archivo = tab_archivo;
	}

	public SeleccionTabla getSet_empleado() {
		return set_empleado;
	}

	public void setSet_empleado(SeleccionTabla set_empleado) {
	}

	public Dialogo getDia_empleado() {
		return dia_empleado;
	}

	public void setDia_empleado(Dialogo dia_empleado) {
		this.dia_empleado = dia_empleado;
	}
	public SeleccionTabla getSet_actualizar() {
		return set_actualizar;
	}
	public void setSet_actualizar(SeleccionTabla set_actualizar) {
		this.set_actualizar = set_actualizar;
	}
	public Confirmar getCon_guardar() {
		return con_guardar;
	}
	public void setCon_guardar(Confirmar con_guardar) {
		this.con_guardar = con_guardar;
	}
	public SeleccionTabla getSet_compareciente() {
		return set_compareciente;
	}
	public void setSet_compareciente(SeleccionTabla set_compareciente) {
		this.set_compareciente = set_compareciente;
	}
	public Dialogo getDia_compareciente() {
		return dia_compareciente;
	}
	public void setDia_compareciente(Dialogo dia_compareciente) {
		this.dia_compareciente = dia_compareciente;
	}


	public SeleccionTabla getSet_actualizar_compareciente() {
		return set_actualizar_compareciente;
	}


	public void setSet_actualizar_compareciente(
			SeleccionTabla set_actualizar_compareciente) {
		this.set_actualizar_compareciente = set_actualizar_compareciente;
	}


	public Confirmar getCon_guardar_compareciente() {
		return con_guardar_compareciente;
	}


	public void setCon_guardar_compareciente(Confirmar con_guardar_compareciente) {
		this.con_guardar_compareciente = con_guardar_compareciente;
	}

}

