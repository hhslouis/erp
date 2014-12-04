package paq_contabilidad;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Arbol;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_asociacion_presupuestaria extends Pantalla {
	
	private Tabla tab_asociacion_presupuestaria =new Tabla();
	private Tabla tab_vigente =new Tabla();
	private Tabla tab_tipo_catalogo_cuenta = new Tabla();
	private Division div_vigente = new Division();
	private Arbol arb_catalogo_cuenta = new Arbol();
	private Combo com_anio =new Combo();
	
	 @EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
		

	public pre_asociacion_presupuestaria (){
		com_anio.setCombo("select ide_geani,detalle_geani from gen_anio where activo_geani = true" + 
				" order by detalle_geani");
		com_anio.setMetodo("seleccioneElAnio");
		bar_botones.agregarComponente(new Etiqueta("Año:"));
		bar_botones.agregarComponente(com_anio);
		
		tab_asociacion_presupuestaria.setId("tab_asociacion_presupuestaria");
		tab_asociacion_presupuestaria.setHeader("ASOCIACION PRESUPUESTARIA");
		tab_asociacion_presupuestaria.setTabla("pre_asociacion_presupuestaria", "ide_prasp", 1);
		tab_asociacion_presupuestaria.getColumna("ide_prcla").setCombo("pre_clasificador","ide_prcla","descripcion_clasificador_prcla","");
		tab_asociacion_presupuestaria.getColumna("ide_cocac").setCombo("cont_catalogo_cuenta","ide_cocac","cue_descripcion_cocac","");
		tab_asociacion_presupuestaria.getColumna("ide_gelua").setCombo("gen_lugar_aplica","ide_gelua","detalle_gelua","");
		tab_asociacion_presupuestaria.getColumna("ide_prmop").setCombo("pre_movimiento_presupuestario","ide_prmop","detalle_prmop","");
		tab_asociacion_presupuestaria.agregarRelacion(tab_vigente);
		tab_asociacion_presupuestaria.setTipoFormulario(true);
		tab_asociacion_presupuestaria.getGrid().setColumns(4);
		
		tab_asociacion_presupuestaria.dibujar();
		PanelTabla pat_asociacion_presupuestaria=new PanelTabla();
		pat_asociacion_presupuestaria.setPanelTabla(tab_asociacion_presupuestaria);
				
		//arbol catalogo cuenta
		arb_catalogo_cuenta.setId("arb_catalogo_cuenta");
		arb_catalogo_cuenta.setTitulo("CATALOGO CUENTA");
		arb_catalogo_cuenta.setArbol("cont_catalogo_cuenta", "ide_cocac", "cue_descripcion_cocac", "con_ide_cocac");
		//arb_catalogo_cuenta.onSelect("seleccionar_arbol");	
		arb_catalogo_cuenta.dibujar();
		
	
		
		tab_vigente.setId("tab_vigente");
		tab_vigente.setHeader("AÑO VIGENTE");
		tab_vigente.setTabla("cont_vigente", "ide_covig", 2);
		tab_vigente.getColumna("ide_geani").setCombo("gen_anio","ide_geani","detalle_geani","");
		tab_vigente.getColumna("ide_geani").setUnico(true);
		tab_vigente.getColumna("ide_prasp").setUnico(true);
		// ocultar campos de las claves  foraneas
		TablaGenerica  tab_generica=ser_contabilidad.getTablaVigente("cont_vigente");
		for(int i=0;i<tab_generica.getTotalFilas();i++){
		//muestra los ides q quiere mostras.
		if(!tab_generica.getValor(i, "column_name").equals("ide_geani")){	
		tab_vigente.getColumna(tab_generica.getValor(i, "column_name")).setVisible(false);	
		}				
		
   	}  
	    						
		tab_vigente.dibujar();
		PanelTabla pat_vigente = new PanelTabla();
	    pat_vigente.setPanelTabla(tab_vigente);
		
	  //division de la pantalla
	  	div_vigente.dividir2(pat_asociacion_presupuestaria, pat_vigente, "50%", "h");
	  	agregarComponente(div_vigente);
	  	// division arbol
	  	arb_catalogo_cuenta.setId("arb_catalogo_cuenta");
	  	arb_catalogo_cuenta.dibujar();
	    Division div_arbol=new Division();
		div_arbol.setId("div_arbol");
		div_arbol.dividir2(arb_catalogo_cuenta, div_vigente, "30%", "v");
		agregarComponente(div_arbol);
		
		
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_asociacion_presupuestaria.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_asociacion_presupuestaria.eliminar();
		
		
	}

	public Tabla getTab_asociacion_presupuestaria() {
		return tab_asociacion_presupuestaria;
	}

	public void setTab_asociacion_presupuestaria(Tabla tab_asociacion_presupuestaria) {
		this.tab_asociacion_presupuestaria = tab_asociacion_presupuestaria;
	}

	public Arbol getArb_catalogo_cuenta() {
		return arb_catalogo_cuenta;
	}

	public void setArb_catalogo_cuenta(Arbol arb_catalogo_cuenta) {
		this.arb_catalogo_cuenta = arb_catalogo_cuenta;
	}

	public Tabla getTab_tipo_catalogo_cuenta() {
		return tab_tipo_catalogo_cuenta;
	}

	public void setTab_tipo_catalogo_cuenta(Tabla tab_tipo_catalogo_cuenta) {
		this.tab_tipo_catalogo_cuenta = tab_tipo_catalogo_cuenta;
	}
	
	
	public Tabla getTab_vigente() {
		return tab_vigente;
	}

	public void setTab_vigente(Tabla tab_vigente) {
		this.tab_vigente = tab_vigente;
	}

}
