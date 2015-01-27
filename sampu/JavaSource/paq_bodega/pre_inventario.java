package paq_bodega;

import javax.ejb.EJB;

import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;
import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;

public class pre_inventario extends Pantalla{

	private Tabla tab_inventario=new Tabla();
	private Combo com_anio=new Combo();
	private SeleccionTabla set_material=new SeleccionTabla();
	private SeleccionTabla set_actualizamaterial=new SeleccionTabla();
	private Confirmar con_guardar=new Confirmar();



	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	private ServicioBodega ser_Bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	public pre_inventario() {

		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		tab_inventario.setId("tab_inventario");  
		tab_inventario.setTabla("bodt_inventario","ide_boinv", 1);
		tab_inventario.getColumna("ide_bomat").setUnico(false);
		tab_inventario.getColumna("ide_geani").setUnico(true);
		tab_inventario.getColumna("ide_geani").setVisible(false);
		tab_inventario.getColumna("gen_ide_geani").setVisible(false);
		tab_inventario.getColumna("IDE_GEARE").setVisible(false);
		tab_inventario.setCondicion("ide_geani=-1"); 
		tab_inventario.getColumna("ide_bomat").setCombo(ser_Bodega.getInventario("true,false"));
		tab_inventario.getColumna("gen_ide_geani").setCombo(ser_contabilidad.getAnio("true,false","true,false"));
		tab_inventario.getColumna("IDE_GEARE").setCombo("gen_area","ide_geare","detalle_geare","");
		tab_inventario.dibujar();

		PanelTabla pat_inventario = new PanelTabla ();
		pat_inventario.setPanelTabla(tab_inventario);

		Division div_division=new Division();
		div_division.dividir1(pat_inventario);
		agregarComponente(div_division);

		Boton bot_material = new Boton();
		bot_material.setValue("Agregar Material");
		bot_material.setTitle("MATERIAL");
		bot_material.setIcon("ui-icon-person");
		bot_material.setMetodo("importarMaterial");
		bar_botones.agregarBoton(bot_material);

		set_material.setId("set_material");
		set_material.setSeleccionTabla(ser_Bodega.getInventario("true"),"ide_bomat");
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
		set_actualizamaterial.getBot_aceptar().setMetodo("modificarMaterial");
		set_actualizamaterial.setRadio();
		agregarComponente(set_actualizamaterial);

	}
	public void actualizarMaterial(){
		System.out.println("Entra a actualizar...");
		if (tab_inventario.getValor("ide_geani")==null){
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
		tab_inventario.setValor("IDE_BOMAT", tab_materialModificado.getValor("IDE_BOMAT"));			
		tab_inventario.modificar(tab_inventario.getFilaActual());
		utilitario.addUpdate("tab_inventario");	

		con_guardar.setMessage("Esta Seguro de Actualizar el Material");
		con_guardar.setTitle("CONFIRMACION ");
		con_guardar.getBot_aceptar().setMetodo("guardarActualilzarMaterial");
		con_guardar.dibujar();
		utilitario.addUpdate("con_guardar");

	}
	public void guardarActualilzarMaterial(){
		System.out.println("Entra a guardar...");
		tab_inventario.guardar();
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
			tab_inventario.insertar();
			tab_inventario.setValor("ide_bomat",str_seleccionados);
			tab_inventario.setValor("ide_geani", com_anio.getValue()+"");

			System.out.println("inserta el valor  "+str_seleccionados);

		}
		set_material.cerrar();
		utilitario.addUpdate("tab_inventario");
	}

	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_inventario.setCondicion("ide_geani="+com_anio.getValue());
			tab_inventario.ejecutarSql();
			//tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());

		}
		else{
			tab_inventario.setCondicion("ide_geani=-1");
			tab_inventario.ejecutarSql();
		}
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		if(tab_inventario.isFocus()) {
			utilitario.agregarMensaje("No se puede insertar", "Debe Agregar Material");
		}
		else{
			tab_inventario.isFocus(); 
			tab_inventario.insertar();
			tab_inventario.setValor("ide_geani", com_anio.getValue()+"");


		} 

	}





	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_inventario.guardar();
		guardarPantalla();

	}



	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_inventario.eliminar();

	}



	public Tabla getTab_inventario() {
		return tab_inventario;
	}



	public void setTab_inventario(Tabla tab_inventario) {
		this.tab_inventario = tab_inventario;
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
