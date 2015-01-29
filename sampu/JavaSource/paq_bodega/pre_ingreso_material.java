package paq_bodega;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_ingreso_material extends Pantalla{
	private Tabla tab_bodega=new Tabla();
	private Tabla tab_anio=new Tabla();
	private Combo com_anio=new Combo();
	private SeleccionTabla set_material=new SeleccionTabla();
	private SeleccionTabla set_actualizamaterial=new SeleccionTabla();
	private Confirmar con_guardar=new Confirmar();
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	private ServicioBodega ser_Bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);


	public pre_ingreso_material (){
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);


		tab_bodega.setId("tab_bodega");  
		tab_bodega.setTabla("bodt_bodega","ide_bobod", 1);	
		tab_bodega.getColumna("ide_geani").setVisible(false);
		tab_bodega.setCondicion("ide_geani=-1"); 
		tab_bodega.getColumna("ide_bomat").setCombo("select ide_bomat,codigo_bomat,detalle_bomat,iva_bomat from bodt_material order by detalle_bomat");
		tab_bodega.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest"," detalle_coest","");
		tab_bodega.setTipoFormulario(true);
		tab_bodega.getGrid().setColumns(4);	
		tab_bodega.dibujar();
		PanelTabla pat_bodega=new PanelTabla();
		pat_bodega.setPanelTabla(tab_bodega);
		agregarComponente(pat_bodega);


		Boton bot_material = new Boton();
		bot_material.setValue("Agregar Material");
		bot_material.setTitle("MATERIAL");
		bot_material.setIcon("ui-icon-person");
		bot_material.setMetodo("importarMaterial");
		bar_botones.agregarBoton(bot_material);

		set_material.setId("set_material");
		set_material.setSeleccionTabla(ser_Bodega.getInventario("true"),"ide_bomat");
		set_material.setSeleccionTabla(ser_Bodega.getInventario("true"),"ide_bomat");
		set_material.getTab_seleccion().getColumna("codigo_bomat").setFiltro(true);
		set_material.getTab_seleccion().getColumna("detalle_bomat").setFiltro(true);
		set_material.getTab_seleccion().getColumna("detalle_bogrm").setFiltro(true);
		set_material.getBot_aceptar().setMetodo("aceptarMaterial");
		set_material.getTab_seleccion().ejecutarSql();
		agregarComponente(set_material);

		bar_botones.agregarBoton(bot_material);
		con_guardar.setId("con_guardar");
		agregarComponente(con_guardar);

		Boton bot_actualizar=new Boton();
		bot_actualizar.setIcon("ui-icon-person");
		bot_actualizar.setValue("Actualizar Material");
		bot_actualizar.setMetodo("actualizarMaterial");
		bar_botones.agregarBoton(bot_actualizar);	

		set_actualizamaterial.setId("set_actualizamaterial");
		set_actualizamaterial.setSeleccionTabla(ser_Bodega.getInventario("true"),"ide_bomat");
		set_actualizamaterial.getTab_seleccion().getColumna("codigo_bomat").setFiltro(true);
		set_actualizamaterial.getTab_seleccion().getColumna("detalle_bomat").setFiltro(true);
		set_actualizamaterial.getTab_seleccion().getColumna("detalle_bogrm").setFiltro(true);
		set_actualizamaterial.getBot_aceptar().setMetodo("modificarMaterial");
		set_actualizamaterial.setRadio();
		agregarComponente(set_actualizamaterial);



	}
	public void actualizarMaterial(){
		System.out.println("Entra a actualizar...");
		if (tab_bodega.getValor("ide_geani")==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un año ","");
			return;

		}
		System.out.println("Entra a actualizar1...");
		set_actualizamaterial.getTab_seleccion().setSql(ser_Bodega.getInventario("true"));
		set_actualizamaterial.getTab_seleccion().ejecutarSql();
		set_actualizamaterial.dibujar();	
	}	

	public void modificarMaterial(){
		System.out.println("Entra modificar...");

		String str_materialActualizado=set_actualizamaterial.getValorSeleccionado();
		System.out.println("Entra a guardar..."+str_materialActualizado);

		TablaGenerica tab_materialModificado = ser_Bodega.getTablaInventario(str_materialActualizado);
		tab_bodega.setValor("IDE_BOMAT", tab_materialModificado.getValor("IDE_BOMAT"));			
		tab_bodega.modificar(tab_bodega.getFilaActual());
		utilitario.addUpdate("tab_bodega");	

		con_guardar.setMessage("Esta Seguro de Actualizar el Material");
		con_guardar.setTitle("CONFIRMACION ");
		con_guardar.getBot_aceptar().setMetodo("guardarActualilzarMaterial");
		con_guardar.dibujar();
		utilitario.addUpdate("con_guardar");

	}
	public void guardarActualilzarMaterial(){
		System.out.println("Entra a guardar...");
		tab_bodega.guardar();
		con_guardar.cerrar();
		set_actualizamaterial.cerrar();


		guardarPantalla();

	}
	public void importarMaterial(){
		System.out.println(" ingresar al importar");
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}

		set_material.getTab_seleccion().setSql(ser_Bodega.getInventario("true"));
		set_material.getTab_seleccion().ejecutarSql();
		set_material.dibujar();

	}

	public  void aceptarMaterial(){
		String str_seleccionados = set_material.getSeleccionados();
		System.out.println("entra al str  "+str_seleccionados);

		if (str_seleccionados!=null){
			tab_bodega.insertar();
			tab_bodega.setValor("ide_bomat",str_seleccionados);
			tab_bodega.setValor("ide_geani", com_anio.getValue()+"");

			System.out.println("inserta el valor  "+str_seleccionados);

		}
		set_material.cerrar();
		utilitario.addUpdate("tab_bodega");
	}

	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_bodega.setCondicion("ide_geani="+com_anio.getValue());
			tab_bodega.ejecutarSql();
			//tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());

		}
		else{
			utilitario.agregarMensajeInfo("Selecione un año", "");

		}
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}else{
			tab_bodega.isFocus(); 
			tab_bodega.insertar();
			tab_bodega.setValor("ide_geani", com_anio.getValue()+"");

		}

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_bodega.guardar();
		guardarPantalla();		

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_bodega.eliminar();
	}




	public Tabla getTab_bodega() {
		return tab_bodega;
	}




	public void setTab_bodega(Tabla tab_bodega) {
		this.tab_bodega = tab_bodega;
	}




	public Tabla getTab_anio() {
		return tab_anio;
	}




	public void setTab_anio(Tabla tab_anio) {
		this.tab_anio = tab_anio;
	}


	public Combo getCom_anio() {
		return com_anio;
	}


	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}
	public SeleccionTabla getSet_material() {
		return set_material;
	}
	public void setSet_material(SeleccionTabla set_material) {
		this.set_material = set_material;
	}
	public SeleccionTabla getSet_actualizamaterial() {
		return set_actualizamaterial;
	}
	public void setSet_actualizamaterial(SeleccionTabla set_actualizamaterial) {
		this.set_actualizamaterial = set_actualizamaterial;
	}
	public Confirmar getCon_guardar() {
		return con_guardar;
	}
	public void setCon_guardar(Confirmar con_guardar) {
		this.con_guardar = con_guardar;
	}




}
