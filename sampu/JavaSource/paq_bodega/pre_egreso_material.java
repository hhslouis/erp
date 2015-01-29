package paq_bodega;

import javax.ejb.EJB;

import paq_contabilidad.ejb.ServicioContabilidad;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;
import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;

public class pre_egreso_material extends Pantalla {
	private Tabla  tab_concepto_egreso=new Tabla();
	private Tabla  tab_egreso=new Tabla();
	private Combo com_anio=new Combo();

	private SeleccionTabla set_inventario=new SeleccionTabla();

	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);


	public pre_egreso_material(){

		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));		
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		tab_concepto_egreso.setId("tab_concepto_egreso");
		tab_concepto_egreso.setHeader("EGRESO DE MATERIALES");
		tab_concepto_egreso.setTabla("bodt_concepto_egreso","ide_bocoe", 1);
		tab_concepto_egreso.getColumna("ide_geani").setVisible(true);
		tab_concepto_egreso.setCondicion("ide_geani = -1");
		//tab_concepto_egreso.getColumna("IDE_BOBOD").setCombo("bodt_bodega","ide_bobod","descripcion_bobod","");
		tab_concepto_egreso.getColumna("IDE_GETIP").setCombo("gen_tipo_persona","ide_getip","detalle_getip","");
		tab_concepto_egreso.getColumna("IDE_GEARE").setCombo("gen_area","ide_geare", "detalle_geare", "");
		tab_concepto_egreso.getColumna("IDE_BODES").setCombo("bodt_destino","ide_bodes","detalle_bodes", "");
		tab_concepto_egreso.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_concepto_egreso.agregarRelacion(tab_egreso);
		tab_concepto_egreso.setTipoFormulario(true);
		tab_concepto_egreso.getGrid().setColumns(4);	
		tab_concepto_egreso.dibujar();
		PanelTabla pat_concepto_egreso=new PanelTabla();
		pat_concepto_egreso.setPanelTabla(tab_concepto_egreso);


		tab_egreso.setId("tab_egreso");
		tab_egreso.setHeader("DETALLE EGRESO DE MATERIALES");
		tab_egreso.setTabla("bodt_egreso", "ide_boegr", 2);
		tab_egreso.setCampoForanea("ide_bocoe");
		//tab_egreso.getColumna("").setCombo("", campo_codigo, campo_nombre, condicion)
		//tab_egreso.getColumna("ide_bomat").setCombo("select ide_bomat,codigo_bomat,detalle_bomat,iva_bomat from bodt_material order by detalle_bomat");
		tab_egreso.setTipoFormulario(true);
		tab_egreso.getGrid().setColumns(4);	
		tab_egreso.dibujar();
		PanelTabla pat_egreso=new PanelTabla();
		pat_egreso.setPanelTabla(tab_egreso);

		Division div_division=new Division();
		div_division.dividir2(pat_concepto_egreso,pat_egreso, "50%", "h");
		agregarComponente(div_division);

		Boton bot_inventario = new Boton();
		bot_inventario.setValue("Agregar Inventario");
		bot_inventario.setTitle("INVENTARIO");
		bot_inventario.setIcon("ui-icon-person");
		bot_inventario.setMetodo("importarInventario");
		bar_botones.agregarBoton(bot_inventario);


		set_inventario.setId("set_inventario");
		set_inventario.setSeleccionTabla(ser_contabilidad.getInventario("1"),"ide_boinv");
		set_inventario.getTab_seleccion().getColumna("ide_boinv").setFiltro(true);
		set_inventario.getTab_seleccion().getColumna("codigo_bomat").setFiltro(true);
		set_inventario.getTab_seleccion().getColumna("detalle_bomat").setFiltro(true);
		set_inventario.getBot_aceptar().setMetodo("aceptarInventario");
		set_inventario.setRadio();
		set_inventario.getTab_seleccion().ejecutarSql();
		agregarComponente(set_inventario);


	}

	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_concepto_egreso.setCondicion("ide_geani="+com_anio.getValue());
			tab_concepto_egreso.ejecutarSql();
			tab_concepto_egreso.imprimirSql();
			tab_egreso.ejecutarValorForanea(tab_concepto_egreso.getValorSeleccionado());


		}
		else{
			tab_egreso.ejecutarSql();
			tab_egreso.ejecutarValorForanea(tab_concepto_egreso.getValorSeleccionado());
			utilitario.agregarMensajeInfo("Selecione un Año", "");

		}
	}

	public  void aceptarInventario(){
		String str_seleccionado = set_inventario.getValorSeleccionado();
		System.out.println("pruebabab f  "+str_seleccionado);

		if (str_seleccionado!=null){
			tab_egreso.insertar();
			tab_egreso.setValor("ide_boinv",str_seleccionado);
		}
		set_inventario.cerrar();
		utilitario.addUpdate("tab_egreso");
	}

	public void importarInventario(){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		else if(tab_concepto_egreso.isEmpty()){
			utilitario.agregarMensajeInfo("Debe insertar un registro", "");
			return;
		} 
		set_inventario.getTab_seleccion().setSql(ser_contabilidad.getInventario(com_anio.getValue().toString()));
		set_inventario.getTab_seleccion().ejecutarSql();
		set_inventario.dibujar();

	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensaje("No se puede insertar", "Debe Seleccionar un año");
			return;

		}
		if (tab_concepto_egreso.isFocus()) {
			tab_concepto_egreso.insertar();
			tab_concepto_egreso.setValor("ide_geani", com_anio.getValue()+"");

		}

		else if (tab_egreso.isFocus()) {
			tab_egreso.insertar();
			//tab_egreso.setValor("ide_boinv", com_anio.getValue()+"");

		}	
	}



	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_concepto_egreso.guardar()){
			tab_egreso.guardar();
		}
		guardarPantalla();

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_concepto_egreso.isFocus()){
			tab_concepto_egreso.eliminar();
		}
		else if(tab_egreso.isFocus()){
			tab_egreso.eliminar();
		}	

	}
	public Combo getCom_anio() {
		return com_anio;
	}

	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}

	public SeleccionTabla getSet_inventario() {
		return set_inventario;
	}

	public void setSet_inventario(SeleccionTabla set_inventario) {
		this.set_inventario = set_inventario;
	}

	public Tabla getTab_concepto_egreso() {
		return tab_concepto_egreso;
	}

	public void setTab_concepto_egreso(Tabla tab_concepto_egreso) {
		this.tab_concepto_egreso = tab_concepto_egreso;
	}

	public Tabla getTab_egreso() {
		return tab_egreso;
	}

	public void setTab_egreso(Tabla tab_egreso) {
		this.tab_egreso = tab_egreso;
	}


}
