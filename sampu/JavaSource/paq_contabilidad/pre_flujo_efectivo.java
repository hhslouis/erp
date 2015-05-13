 package paq_contabilidad;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Arbol;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_flujo_efectivo extends Pantalla{
	private Combo com_anio=new Combo();
	private Tabla tab_flujo_efectivo = new Tabla();
	private Arbol arb_arbol = new Arbol();
	private SeleccionTabla sel_catalogo = new SeleccionTabla();

	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	public pre_flujo_efectivo (){
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));		
		//com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		Boton bot_agregar_cuentacontable = new Boton();
		
		bot_agregar_cuentacontable.setValue("Agregar Cuenta Contable");
		bot_agregar_cuentacontable.setTitle("Agregar Cuenta Contable");
		bot_agregar_cuentacontable.setMetodo("agregarCuentaContable");
		bar_botones.agregarBoton(bot_agregar_cuentacontable);
		
		tab_flujo_efectivo.setId("tab_flujo_efectivo");
		tab_flujo_efectivo.setHeader("FLUJO EFECTIVO");
		tab_flujo_efectivo.setTabla("cont_flujo_efectivo", "ide_cofle", 1);
		tab_flujo_efectivo.agregarArbol(arb_arbol);
		tab_flujo_efectivo.setCampoPadre("con_ide_cofle"); //necesario para el arbol
		tab_flujo_efectivo.setCampoNombre("descripcion_cuenta_cofle");//necesario pa el arbol
		tab_flujo_efectivo.dibujar();
		PanelTabla pat_resultado = new PanelTabla();
		pat_resultado.setPanelTabla(tab_flujo_efectivo);
		
		
		
		/////// arbol
		
		arb_arbol.setId("arb_arbol");
		arb_arbol.dibujar();

		
		
		
		Division div_division = new Division(); //UNE OPCION 
		div_division.setId("div_division");
		div_division.dividir2(arb_arbol, pat_resultado, "40%", "V");  //arbol y diV_division
		agregarComponente(div_division);
		
		sel_catalogo.setId("sel_catalogo");
		sel_catalogo.setTitle("SELECCIONE CATALOGO");
		sel_catalogo.setSeleccionTabla(ser_contabilidad.getCatalogoCuentaAnio("true", "-1"), "ide_cocac"); 
		sel_catalogo.getTab_seleccion().getColumna("cue_codigo_cocac").setNombreVisual("CODIGO CUENTA CONTABLE"); //pone filtro
		sel_catalogo.getTab_seleccion().getColumna("cue_descripcion_cocac").setNombreVisual("NOMBRE CUENTA CONTABLE"); //pone filtro
		sel_catalogo.getTab_seleccion().getColumna("detalle_geani").setNombreVisual("AÑO"); //pone filtro
		sel_catalogo.setRadio();
		sel_catalogo.getTab_seleccion().getColumna("cue_codigo_cocac").setFiltroContenido(); //pone filtro
		sel_catalogo.getTab_seleccion().getColumna("cue_descripcion_cocac").setFiltroContenido();//pone filtro
		sel_catalogo.getBot_aceptar().setMetodo("aceptarCatalogo");
		agregarComponente(sel_catalogo);
		
	}
public void agregarCuentaContable(){
	if(com_anio.getValue()==null){
       	utilitario.agregarMensajeInfo("No Existe Año", "Debe seleccionar un año");
        return;
    	
    }
		sel_catalogo.getTab_seleccion().setSql(ser_contabilidad.getCatalogoCuentaAnio("true", com_anio.getValue().toString()));
		sel_catalogo.setRadio();
		sel_catalogo.getTab_seleccion().ejecutarSql();
		sel_catalogo.dibujar();	
		
	}
public void aceptarCatalogo(){
	
	String str_seleccionados=sel_catalogo.getValorSeleccionado();
	TablaGenerica catalogo_cuentas = utilitario.consultar(ser_contabilidad.getCuentaContableCodigo("true,false", str_seleccionados));
	if(str_seleccionados!=null){
		//Inserto los empleados seleccionados en la tabla de resposable d econtratacion 
		//TablaGenerica tab_empleado_responsable = ser_empleado.servicioEmpleadosActivos());		
		tab_flujo_efectivo.setValor("ide_cocac",str_seleccionados);			
		tab_flujo_efectivo.setValor("descripcion_cuenta_cofle",catalogo_cuentas.getValor("cue_descripcion_cocac"));			
		tab_flujo_efectivo.setValor("codigo_cuenta_cofle",catalogo_cuentas.getValor("cue_codigo_cocac"));			
		sel_catalogo.cerrar();		
		tab_flujo_efectivo.modificar(tab_flujo_efectivo.getFilaActual());
		utilitario.addUpdate("tab_flujo_efectivo");
		tab_flujo_efectivo.guardar();
		guardarPantalla();
			
	}
	else{
		utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
	}
}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();

		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_flujo_efectivo.guardar()){
	           
	        	   guardarPantalla();
	            //actualiza el arbol
	            arb_arbol.ejecutarSql();
	            utilitario.addUpdate("arb_arbol");
				   }
	           }
	    				
		
		
	

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_flujo_efectivo() {
		return tab_flujo_efectivo;
	}

	public void setTab_flujo_efectivo(Tabla tab_flujo_efectivo) {
		this.tab_flujo_efectivo = tab_flujo_efectivo;
	}

	public Arbol getArb_arbol() {
		return arb_arbol;
	}

	public void setArb_arbol(Arbol arb_arbol) {
		this.arb_arbol = arb_arbol;
	}

	public Combo getCom_anio() {
		return com_anio;
	}

	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}

	public SeleccionTabla getSel_catalogo() {
		return sel_catalogo;
	}

	public void setSel_catalogo(SeleccionTabla sel_catalogo) {
		this.sel_catalogo = sel_catalogo;
	}

	
	

}
