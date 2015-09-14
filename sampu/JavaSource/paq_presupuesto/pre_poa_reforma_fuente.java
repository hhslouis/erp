package paq_presupuesto;

import javax.ejb.EJB;

import paq_contabilidad.ejb.ServicioContabilidad;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;

public class pre_poa_reforma_fuente extends Pantalla {
	private Tabla tab_poa_reforma_fuenta =new Tabla();
	private SeleccionTabla set_reforma_fuente = new SeleccionTabla();
	private Combo com_anio=new Combo();


	@EJB
	 private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto)utilitario.instanciarEJB(ServicioPresupuesto.class);
	  @EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);

	public pre_poa_reforma_fuente() {
		
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		tab_poa_reforma_fuenta.setId("tab_poa_reforma_fuenta");
		tab_poa_reforma_fuenta.setTabla("pre_poa_reforma_fuente", "ide_prprf", 1);
		tab_poa_reforma_fuenta.getColumna("ide_prpoa").setCombo(ser_presupuesto.getPoaNombre("select ide_geani from gen_anio"));
		tab_poa_reforma_fuenta.getColumna("ide_prpoa").setAutoCompletar();
		tab_poa_reforma_fuenta.getColumna("ide_prpoa").setLectura(true);
		tab_poa_reforma_fuenta.getColumna("ide_prfuf").setCombo("pre_fuente_financiamiento","ide_prfuf","detalle_prfuf","");
		tab_poa_reforma_fuenta.getColumna("ide_prfuf").setAutoCompletar();
		tab_poa_reforma_fuenta.getColumna("ide_prfuf").setLectura(true);
		tab_poa_reforma_fuenta.dibujar();
		PanelTabla pat_reforma = new PanelTabla();
		pat_reforma.setPanelTabla(tab_poa_reforma_fuenta);
		
		Division div_poa_reforma = new Division();
		div_poa_reforma.setId("div_poa_reforma");
		div_poa_reforma.dividir1(pat_reforma);
		
		agregarComponente(div_poa_reforma);
		
		Boton bot_buscar = new Boton();
		bot_buscar.setIcon("ui-icon-person");
		bot_buscar.setValue("Buscar POA");
		bot_buscar.setMetodo("mostrarSetReformaFuente");
		bar_botones.agregarBoton(bot_buscar);		

		
		inicializaSetPoaReformar();
		
	}
	public void inicializaSetPoaReformar(){
		set_reforma_fuente.setId("set_reforma_fuente");
		set_reforma_fuente.setTitle("Seleccione una Fuente de Financiamiento para la Reforma");
		set_reforma_fuente.setSeleccionTabla(ser_presupuesto.getPoaSaldosFuenteFinanciamiento("-1"),"codigo");
		set_reforma_fuente.getTab_seleccion().getColumna("detalle_prfuf").setNombreVisual("Fuente Financiamiento");
		set_reforma_fuente.getTab_seleccion().getColumna("valor_asignado").setNombreVisual("Asignación Inicial F.F.");
		set_reforma_fuente.getTab_seleccion().getColumna("codigo_clasificador_prcla").setNombreVisual("Partida Presupuestaria");
		set_reforma_fuente.getTab_seleccion().getColumna("codigo_subactividad").setNombreVisual("Codigo Sub-Actividad");
		set_reforma_fuente.getTab_seleccion().getColumna("detalle_subactividad").setNombreVisual("Sub-Actividad");
		set_reforma_fuente.getTab_seleccion().getColumna("detalle_prfuf").setFiltro(true);
		set_reforma_fuente.getTab_seleccion().getColumna("valor_asignado").setFiltro(true);
		set_reforma_fuente.getTab_seleccion().getColumna("codigo_clasificador_prcla").setFiltro(true);
		set_reforma_fuente.getTab_seleccion().getColumna("codigo_subactividad").setFiltro(true);
		set_reforma_fuente.getTab_seleccion().getColumna("detalle_subactividad").setFiltro(true);
		set_reforma_fuente.setRadio();
		set_reforma_fuente.getTab_seleccion().ejecutarSql();
		set_reforma_fuente.getBot_aceptar().setMetodo("aceptarPoa");
		agregarComponente(set_reforma_fuente);

	}
	
	public void aceptarPoa(){
		String str_seleccionado=set_reforma_fuente.getValorSeleccionado();
		TablaGenerica tab_busca_poa=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString())); 
		if(str_seleccionado!=null){
		
			for(int i=0;i<tab_busca_poa.getTotalFilas();i++){
				if(tab_busca_poa.getValor(i,"codigo").equals(str_seleccionado)){
				tab_poa_reforma_fuenta.insertar();
				tab_poa_reforma_fuenta.setValor("ide_prpoa", tab_busca_poa.getValor(i, "ide_prpoa"));;
				tab_poa_reforma_fuenta.setValor("ide_prfuf", tab_busca_poa.getValor(i, "ide_prfuf"));;

				}
			}
			set_reforma_fuente.cerrar();
			utilitario.addUpdate("tab_poa_reforma_fuenta");	
		}

	}
	public void mostrarSetReformaFuente(){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un año", "");
			return;
		}
		else {
		set_reforma_fuente.getTab_seleccion().setSql(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString()));
		set_reforma_fuente.getTab_seleccion().ejecutarSql();
		set_reforma_fuente.dibujar();
		}
	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_poa_reforma_fuenta.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	public Tabla getTab_poa_reforma_fuenta() {
		return tab_poa_reforma_fuenta;
	}

	public void setTab_poa_reforma_fuenta(Tabla tab_poa_reforma_fuenta) {
		this.tab_poa_reforma_fuenta = tab_poa_reforma_fuenta;
	}

	public SeleccionTabla getSet_reforma_fuente() {
		return set_reforma_fuente;
	}

	public void setSet_reforma_fuente(SeleccionTabla set_reforma_fuente) {
		this.set_reforma_fuente = set_reforma_fuente;
	}



}
