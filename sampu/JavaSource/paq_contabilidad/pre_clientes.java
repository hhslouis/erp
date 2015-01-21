package paq_contabilidad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_facturacion.ejb.ServicioFacturacion;
import paq_gestion.ejb.ServicioGestion;
import paq_sistema.aplicacion.Pantalla;


public class pre_clientes extends Pantalla {


	private Tabla tab_direccion=new Tabla();
	private Tabla tab_telefono=new Tabla();
	private Tabla tab_email=new Tabla();
	private Tabla tab_documento=new Tabla();
	private Tabla tab_tarifa=new Tabla();
	private Tabla tab_clientes=new Tabla();
	private SeleccionTabla set_pantalla_sucursal= new SeleccionTabla();
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
	@EJB
	private ServicioFacturacion ser_facturacion = (ServicioFacturacion ) utilitario.instanciarEJB(ServicioFacturacion.class);


	public pre_clientes (){

		Tabulador tab_tabulador = new Tabulador();
		tab_tabulador.setId("tab_tabulador");


		tab_clientes.setId("tab_clientes");
		tab_clientes.setTipoFormulario(true);  //formulario 
		tab_clientes.getGrid().setColumns(4); //hacer  columnas 
		tab_clientes.setCampoNombre("(select ruc_comercial_recli||' '|| nombre_comercial_recli as nombre_comercial_recli from rec_clientes b where b.ide_recli= rec_clientes.ide_recli)");
		tab_clientes.setTabla("rec_clientes", "ide_recli",1);
		tab_clientes.getColumna("ide_retic").setCombo("rec_tipo_contribuyente", "ide_retic", "detalle_retic", "");
		tab_clientes.getColumna("ide_retia").setCombo("rec_tipo_asistencia", "ide_retia", "detalle_retia", "");
		tab_clientes.getColumna("ide_gtgen").setCombo("gth_genero", "ide_gtgen", "detalle_gtgen", "");
		tab_clientes.getColumna("ide_gedip").setCombo(ser_gestion.getSqlDivisionPoliticaCiudad());
		tab_clientes.getColumna("ide_gedip").setAutoCompletar();
		tab_clientes.getColumna("ide_gttdi").setCombo("gth_tipo_documento_identidad", "ide_gttdi", " detalle_gttdi", "");
		tab_clientes.getColumna("gth_ide_gttdi").setCombo("gth_tipo_documento_identidad", "ide_gttdi", "detalle_gttdi", "");
		tab_clientes.getColumna(" ide_gtesc ").setCombo("gth_estado_civil", " ide_gtesc ", "detalle_gtesc", "");
		tab_clientes.getColumna("ruc_comercial_recli").setMetodoChange("validaDocumento");
		tab_clientes.getColumna("representante_legal_recli").setMetodoChange("validaDocumentoRepre");
		tab_clientes.getColumna("ide_retil").setCombo("rec_tipo_cliente", "ide_retil", "detalle_retil", "");
		tab_clientes.getColumna("ide_reclr").setCombo("rec_cliente_ruta", "ide_reclr", "detalle_reclr", "");
		tab_clientes.getColumna("ide_tetar").setCombo("tes_tarifas", "ide_tetar", "detalle_tetar", "");
		
		tab_clientes.getColumna("rec_ide_recli").setCombo(ser_facturacion.getClientes());
		tab_clientes.getColumna("rec_ide_recli").setAutoCompletar();
		tab_clientes.getColumna("rec_ide_recli").setLectura(true);
		// para contruir los radios
		List lista = new ArrayList();
		Object fila1[] = {
				"1", "MATRIZ"
		};
		Object fila2[] = {
				"0", "SUCURSAL"
		};

		lista.add(fila1);
		lista.add(fila2);
		tab_clientes.getColumna("matriz_sucursal_recli").setRadio(lista, "1");
		tab_clientes.getColumna("matriz_sucursal_recli").setRadioVertical(true);

		//RADIOS 
		// para contruir los radios
		List lista1 = new ArrayList();
		Object fila3[] = {
				"1", "MATRIZ"
		};
		Object fila4[] = {
				"0", "SUCURSAL"
		};

		lista1.add(fila3);
		lista1.add(fila4);
		tab_clientes.getColumna("factura_datos_recli").setRadio(lista1, "2");
		tab_clientes.getColumna("factura_datos_recli").setRadioVertical(true);

		tab_clientes.agregarRelacion(tab_direccion);//agraga relacion para los tabuladores
		tab_clientes.agregarRelacion(tab_telefono);
		tab_clientes.agregarRelacion(tab_email);
		tab_clientes.agregarRelacion(tab_tarifa);
		//tab_clientes.agregarRelacion(tab_documento);
		// System.out.println("sql pc"+tab_clientes.getSql());

		
		tab_clientes.dibujar();
		PanelTabla pat_clientes=new PanelTabla ();
		pat_clientes.setPanelTabla(tab_clientes);


		tab_direccion.setId("tab_direccion");
		tab_direccion.setIdCompleto("tab_tabulador:tab_direccion");
		tab_direccion.setTabla("rec_cliente_direccion","ide_recld",2);
		tab_direccion.dibujar();
		PanelTabla pat_panel2 = new PanelTabla();
		pat_panel2.setPanelTabla(tab_direccion);


		tab_telefono.setId("tab_telefono");
		tab_telefono.setIdCompleto("tab_tabulador:tab_telefono");
		tab_telefono.setTabla("rec_cliente_telefono","ide_reclt",3);
		tab_telefono.dibujar();
		PanelTabla pat_panel3 = new PanelTabla();
		pat_panel3.setPanelTabla(tab_telefono);

		tab_email.setId("tab_email");
		tab_email.setIdCompleto("tab_tabulador:tab_email");
		tab_email.setTabla("rec_cliente_email","ide_recle",4);
		tab_email.dibujar();
		PanelTabla pat_panel4 = new PanelTabla();
		pat_panel4.setPanelTabla(tab_email);

		tab_documento.setId("tab_documento");
		tab_documento.setIdCompleto("tab_tabulador:tab_documento");
		tab_documento.setTipoFormulario(true);
		tab_documento.setTabla("rec_cliente_archivo","ide_recla",5);
		tab_documento.getColumna("foto_recla").setUpload("fotos");
		tab_documento.getColumna("foto_recla").setImagen("128", "128");
		tab_documento.dibujar();
		PanelTabla pat_panel5 = new PanelTabla();
		pat_panel5.setPanelTabla(tab_documento);


		tab_tarifa.setId("tab_tarifa");
		tab_tarifa.setIdCompleto("tab_tabulador:tab_tarifa");
		tab_tarifa.setTipoFormulario(true);
		tab_tarifa.setTabla("tes_cliente_tarifa","ide_teclt",6);
		tab_tarifa.getColumna("ide_temat").setCombo("tes_material_tarifa", "ide_temat", "valor_temat", "");
		tab_tarifa.dibujar();
		PanelTabla pat_panel6 = new PanelTabla();
		pat_panel6.setPanelTabla(tab_tarifa);


		tab_tabulador.agregarTab("DIRECCION", pat_panel2);//intancia los tabuladores
		tab_tabulador.agregarTab("TELEFONO", pat_panel3);
		tab_tabulador.agregarTab("EMAIL", pat_panel4);
		tab_tabulador.agregarTab("DOCUMENTO", pat_panel5);
		tab_tabulador.agregarTab("TARIFA", pat_panel6);


		Division div_division=new Division();
		div_division.dividir2(pat_clientes,tab_tabulador,"70%","H");
		agregarComponente(div_division);

		//BOTON AGREGAR CLIENTE
		Boton bot_agregarSucursal=new Boton();
		bot_agregarSucursal.setValue("AGREGAR MATRIZ");
		bot_agregarSucursal.setIcon("ui-icon-person");
		bot_agregarSucursal.setMetodo("agregarMatriz");
		bar_botones.agregarBoton(bot_agregarSucursal);

		//PANTALLA SELECIONAR CLIENTE
		set_pantalla_sucursal.setId("set_pantalla_sucursal");
		set_pantalla_sucursal.setTitle("SELECCIONE MATRIZ");
		set_pantalla_sucursal.getBot_aceptar().setMetodo("aceptarMatriz");
		set_pantalla_sucursal.setSeleccionTabla(ser_facturacion.getClientes(),"ide_recli");
		//set_pantalla_sucursal.setSeleccionTabla("rec_clientes", "ide_recli", "nombre_comercial_recli,ruc_comercial_recli");
		set_pantalla_sucursal.getTab_seleccion().getColumna("nombre_comercial_recli").setFiltro(true);
		set_pantalla_sucursal.getTab_seleccion().getColumna("ruc_comercial_recli").setFiltro(true);
		set_pantalla_sucursal.setRadio();
		set_pantalla_sucursal.getTab_seleccion().ejecutarSql();
		agregarComponente(set_pantalla_sucursal);

	}
	
	public void agregarMatriz(){
		//Hace aparecer el componente
		if(tab_clientes.getValor("matriz_sucursal_recli")!=null && tab_clientes.getValor("matriz_sucursal_recli").equals("0")){
		set_pantalla_sucursal.getTab_seleccion().setSql(ser_facturacion.getClientes());
		set_pantalla_sucursal.getTab_seleccion().getColumna("ruc_comercial_recli").setFiltro(true);
		set_pantalla_sucursal.getTab_seleccion().getColumna("nombre_comercial_recli").setFiltro(true);
		set_pantalla_sucursal.setRadio();
		set_pantalla_sucursal.getTab_seleccion().ejecutarSql();
		set_pantalla_sucursal.dibujar();	
		}
	}

	public void aceptarMatriz(){
		String str_seleccionado=set_pantalla_sucursal.getValorSeleccionado();
		if(str_seleccionado!=null){
			//Inserto la sucursal en la tabla  
			if(tab_clientes.isFilaInsertada()==false){
				//Controla que si ya esta insertada no vuelva a insertar
				tab_clientes.insertar();	
			}
			
			tab_clientes.setValor("rec_ide_recli", str_seleccionado);				

			set_pantalla_sucursal.cerrar();
			utilitario.addUpdate("tab_clientes");			
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		}
	}


	public Tabla getTab_tarifa() {
		return tab_tarifa;
	}



	public void setTab_tarifa(Tabla tab_tarifa) {
		this.tab_tarifa = tab_tarifa;
	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_clientes.guardar()) {
			if (tab_direccion.guardar()) {
				if (tab_telefono.guardar()) {
					if( tab_email.guardar()){
						if(tab_tarifa.guardar()){
							tab_documento.guardar();  
						}  
					}  

				}
			}
		}
		guardarPantalla();

	}
	public void validaDocumento(AjaxBehaviorEvent evt){
		tab_clientes.modificar(evt);
		if(!validarDocumentoIdentidad(tab_clientes.getValor("ide_gttdi"), tab_clientes.getValor("ruc_comercial_recli"))){
			System.out.println("entre a validar cedula");
			tab_clientes.setValor("ruc_comercial_recli","");
			utilitario.addUpdate("tab_clientes");
		}			

	}


	/**
	 * @param ide_gttdi
	 * @param documento_identidad_gttdi
	 * @return
	 * 
	 * metodo booleano para validar el tipo de documento de identidad cedula y ruc
	 */
	public boolean validarDocumentoIdentidad(String ide_gttdi,String documento_identidad){
		if (ide_gttdi!=null && !ide_gttdi.isEmpty()){
			if (documento_identidad!=null && !documento_identidad.isEmpty()){
				if (ide_gttdi.equals(utilitario.getVariable("p_gth_tipo_documento_cedula"))){
					if (!utilitario.validarCedula(documento_identidad)){
						utilitario.agregarMensajeInfo("Atencion", "El numero de cedula ingresado no es valido");
						return false;
					}
				}else if (ide_gttdi.equals(utilitario.getVariable("p_gth_tipo_documento_ruc"))){
					if (!utilitario.validarRUC(documento_identidad)){
						utilitario.agregarMensajeInfo("Atencion", "El numero de RUC ingresado no es valido");
						return false;
					}
				}
			}
		}
		return true;
	}




	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
	}



	public Tabla getTab_clientes() {
		return tab_clientes;
	}



	public void setTab_clientes(Tabla tab_clientes) {
		this.tab_clientes = tab_clientes;
	}



	public Tabla getTab_direccion() {
		return tab_direccion;
	}



	public void setTab_direccion(Tabla tab_direccion) {
		this.tab_direccion = tab_direccion;
	}



	public Tabla getTab_telefono() {
		return tab_telefono;
	}



	public void setTab_telefono(Tabla tab_telefono) {
		this.tab_telefono = tab_telefono;
	}



	public Tabla getTab_email() {
		return tab_email;
	}



	public void setTab_email(Tabla tab_email) {
		this.tab_email = tab_email;
	}

	public Tabla getTab_documento() {
		return tab_documento;
	}



	public void setTab_documento(Tabla tab_documento) {
		this.tab_documento = tab_documento;
	}

	public SeleccionTabla getSet_pantalla_sucursal() {
		return set_pantalla_sucursal;
	}

	public void setSet_pantalla_sucursal(SeleccionTabla set_pantalla_sucursal) {
		this.set_pantalla_sucursal = set_pantalla_sucursal;
	}

}

