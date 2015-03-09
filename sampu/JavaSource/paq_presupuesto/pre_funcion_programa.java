package paq_presupuesto;

import org.primefaces.event.NodeSelectEvent;



import framework.aplicacion.TablaGenerica;
import framework.componentes.Arbol;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;


public class pre_funcion_programa extends Pantalla {
	private Tabla tab_funcion_programa=new Tabla();
	private Tabla tab_vigente= new Tabla();
	private Arbol arb_funcion_programa=new Arbol();
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	public pre_funcion_programa (){
		tab_funcion_programa.setId("tab_funcion_programa");
		tab_funcion_programa.setTipoFormulario(true);
		tab_funcion_programa.getGrid().setColumns(4);
		tab_funcion_programa.setHeader("FUNCION PROGRAMA");
		//tab_funcion_programa.setNumeroTabla(1);
		tab_funcion_programa.setTabla("pre_funcion_programa", "ide_prfup", 1);
		tab_funcion_programa.getColumna("ide_prnfp").setCombo("pre_nivel_funcion_programa", "ide_prnfp", "detalle_prnfp","");
		tab_funcion_programa.agregarRelacion(tab_vigente);
		tab_funcion_programa.setCampoPadre( "pre_ide_prfup");
		tab_funcion_programa.setCampoNombre("(select codigo_prfup||' '||detalle_prfup from pre_funcion_programa b where b. ide_prfup=pre_funcion_programa.ide_prfup)");
		tab_funcion_programa.agregarArbol(arb_funcion_programa);
		tab_funcion_programa.dibujar();
		PanelTabla pat_funcion_programa=new PanelTabla();
		pat_funcion_programa.setPanelTabla(tab_funcion_programa);
		agregarComponente(pat_funcion_programa);
		
		arb_funcion_programa.setId("arb_funcion_programa");
		arb_funcion_programa.onSelect("seleccionoClasificador");
		arb_funcion_programa.dibujar();

		// tabla dea√±os vigente
		tab_vigente.setId("tab_vigente");
		tab_vigente.setHeader("A—O VIGENTE");
		tab_vigente.setTabla("cont_vigente", "ide_covig", 2);
		tab_vigente.getColumna("ide_geani").setCombo("gen_anio","ide_geani","detalle_geani","");
		tab_vigente.getColumna("ide_geani").setUnico(true);
		tab_vigente.getColumna("ide_prfup").setUnico(true);
		
		tab_vigente.dibujar();
		PanelTabla pat_panel2=new PanelTabla();
		pat_panel2.setPanelTabla(tab_vigente);
		
		//division2
      	Division div_vigente = new Division();
 		div_vigente.setId("div_vigente");
 		div_vigente.dividir2( pat_funcion_programa, pat_panel2,"50%","h");
 		agregarComponente(div_vigente);

				
		//arbol
		
		Division div_division=new Division();
		div_division.dividir2(arb_funcion_programa, div_vigente, "25%", "v");
      	agregarComponente(div_division);
      	
      		
	}
	
	/**DJ
	 * Se ejecuta cuando se selecciona algun nodo del arbol
	 */
		public void seleccionoClasificador(NodeSelectEvent evt){
		//Asigna evento al arbol
		arb_funcion_programa.seleccionarNodo(evt);
		//Filtra la tabla Padre
		tab_funcion_programa.ejecutarValorPadre(arb_funcion_programa.getValorSeleccionado());
		//Filtra la tabla tab_vigente
		tab_vigente.ejecutarValorForanea(tab_funcion_programa.getValorSeleccionado());
	  }
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_funcion_programa.guardar()) {
			if (tab_vigente.guardar()) {
				guardarPantalla();
				//Actualizar el arbol
				arb_funcion_programa.ejecutarSql();
				utilitario.addUpdate("arb_funcion_programa");
				
				
			}
			
		}
	}


	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}
	
	public Tabla getTab_funcion_programa() {
		return tab_funcion_programa;
	}


	public void setTab_funcion_programa(Tabla tab_funcion_programa) {
		this.tab_funcion_programa = tab_funcion_programa;
	}


	public Tabla getTab_vigente() {
		return tab_vigente;
	}


	public void setTab_vigente(Tabla tab_vigente) {
		this.tab_vigente = tab_vigente;
	}


	public Arbol getArb_funcion_programa() {
		return arb_funcion_programa;
	}


	public void setArb_funcion_programa(Arbol arb_funcion_programa) {
		this.arb_funcion_programa = arb_funcion_programa;
	}



}
