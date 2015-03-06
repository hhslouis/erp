package paq_presupuesto;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_anual_egresos extends Pantalla {
	
	private Tabla tab_anual= new Tabla();
	private Tabla tab_mensual= new Tabla();
	private Tabla tab_reforma= new Tabla();
	private Combo com_anio =new Combo();
	private SeleccionTabla set_programa = new SeleccionTabla();

	@EJB
	private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto) utilitario.instanciarEJB(ServicioPresupuesto.class);
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	public pre_anual_egresos(){
		
		com_anio.setCombo("select ide_geani,detalle_geani from gen_anio where activo_geani = true" + 
				" order by detalle_geani");
		com_anio.setMetodo("seleccioneElAnio");
		bar_botones.agregarComponente(new Etiqueta("Año:"));
		bar_botones.agregarComponente(com_anio);

		
		Tabulador tab_tabulador = new Tabulador();
		tab_tabulador.setId("tab_tabulador");

		tab_anual.setId("tab_anual");
		tab_anual.setHeader("EJECUCION PRESUPUESTARIA ANUAL");
		tab_anual.setTabla("pre_anual", "ide_pranu", 1);
		tab_anual.getColumna("ide_prcla").setCombo("select ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla from pre_clasificador order by codigo_clasificador_prcla");
		tab_anual.getColumna("ide_prcla").setVisible(false);
		tab_anual.setCondicion("ide_prpro!=null");
		tab_anual.getColumna("ide_prpro").setCombo(ser_presupuesto.getPrograma("true,false"));
		tab_anual.getColumna("ide_prpro").setAutoCompletar();
		tab_anual.getColumna("ide_prpro").setLectura(true);
		tab_anual.getColumna("ide_geani").setCombo(ser_contabilidad.getAnio("true,false","true,false"));
		tab_anual.getColumna("ide_geani").setVisible(false);
		tab_anual.setCondicion("ide_geani=-1");
		tab_anual.getColumna("valor_reformado_h_pranu").setEtiqueta();
		tab_anual.getColumna("valor_reformado_h_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_reformado_d_pranu").setEtiqueta();
		tab_anual.getColumna("valor_reformado_d_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		//tab_anual.getColumna("ide_geani").setValorDefecto(com_anio.getValue().toString());
		//tab_anual.getColumna("ide_prfup").setCombo("pre_funcion_programa", "ide_prfup", "detalle_prfup,", "");
		tab_anual.getColumna("valor_reformado_pranu").setEtiqueta();
		tab_anual.getColumna("valor_reformado_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_inicial_pranu").setEtiqueta();
		tab_anual.getColumna("valor_inicial_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_codificado_pranu").setEtiqueta();
		tab_anual.getColumna("valor_codificado_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_devengado_pranu").setEtiqueta();
		tab_anual.getColumna("valor_devengado_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_precomprometido_pranu").setEtiqueta();
		tab_anual.getColumna("valor_precomprometido_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_eje_comprometido_pranu").setEtiqueta();
		tab_anual.getColumna("valor_eje_comprometido_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_recaudado_pranu").setEtiqueta();
		tab_anual.getColumna("valor_recaudado_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.getColumna("valor_recaudado_efectivo_pranu").setEtiqueta();
		tab_anual.getColumna("valor_recaudado_efectivo_pranu").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_anual.setTipoFormulario(true);
		tab_anual.getGrid().setColumns(4);
		tab_anual.agregarRelacion(tab_mensual);
		tab_anual.agregarRelacion(tab_reforma);
		tab_anual.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_anual);
		
		//////////////pre_mensual
		
		tab_mensual.setId("tab_mensual");
		tab_mensual.setHeader("DETALLE DE EJECUCION PRESUPUESTARIA");
		tab_mensual.setIdCompleto("tab_tabulador:tab_mensual");
		tab_mensual.setTabla("pre_mensual", "ide_prmen", 2);
		tab_mensual.getColumna("ide_prtra").setLectura(true);
		tab_mensual.getColumna("ide_comov").setLectura(true);
		//tab_mensual.setCondicion("ide_prpro!=null");
		tab_mensual.getColumna("ide_gemes").setCombo("gen_mes", "ide_gemes", "detalle_gemes", "");
		tab_mensual.getColumna("ide_codem").setLectura(true);
		//tab_anual.getColumna("ide_prfup").setCombo("pre_funcion_programa", "ide_prfup", "detalle_prfup,", "");
		tab_mensual.setTipoFormulario(true);
		tab_mensual.getGrid().setColumns(6);
		tab_mensual.dibujar();
		PanelTabla pat_panel2=new PanelTabla();
		pat_panel2.setPanelTabla(tab_mensual);
		
		////////REFORMA MES
		tab_reforma.setId("tab_reforma");
		tab_reforma.setHeader("REFORMA PRESUPUESTARIA MENSUAL");
		tab_reforma.setIdCompleto("tab_tabulador:tab_reforma");
		tab_reforma.setTabla("pre_reforma_mes", "ide_prrem", 3);
		tab_reforma.getColumna("ide_gemes").setCombo("gen_mes", "ide_gemes", "detalle_gemes", "");
		tab_reforma.getColumna("val_reforma_h_prrem").setMetodoChange("calcularDetallle");
		tab_reforma.getColumna("val_reforma_d_prrem").setMetodoChange("calcularDetallle");

		tab_reforma.dibujar();
		PanelTabla pat_panel3=new PanelTabla();
		pat_panel3.setPanelTabla(tab_reforma);
		
		
		
		tab_tabulador.agregarTab("DETALLE DE EJECUCION PRESUPUESTARIA", pat_panel2);//intancia los tabuladores 
		tab_tabulador.agregarTab("REFORMA PRESUPUESTARIA MENSUAL",pat_panel3);

		
		Division div_division =new Division ();
		div_division.dividir2(pat_panel1, tab_tabulador, "50%", "h");
		agregarComponente(div_division);
		
		Boton bot_material = new Boton();
		bot_material.setValue("Agregar Programa");
		bot_material.setTitle("Solicitud Programa");
		bot_material.setIcon("ui-icon-person");
		bot_material.setMetodo("importarPrograma");
		bar_botones.agregarBoton(bot_material);

		set_programa.setId("set_programa");
		set_programa.setSeleccionTabla(ser_presupuesto.getPrograma("true,false"),"IDE_PRPRO");
		set_programa.getBot_aceptar().setMetodo("aceptarPrograma");
		set_programa.getTab_seleccion().ejecutarSql();
		set_programa.setRadio();
		agregarComponente(set_programa);

		
	}
	public void  Calcular(){
		double dou_total=0;
		double dou_subtotal=0;
		
		String sub_tot=tab_reforma.getSumaColumna("val_reforma_h_prrem")+"";
		tab_anual.setValor("valor_reformado_h_pranu",sub_tot );
		
		String sub_total=tab_reforma.getSumaColumna("val_reforma_d_prrem")+"";
		tab_anual.setValor("valor_reformado_d_pranu",sub_total );
		dou_total=Double.parseDouble(sub_tot);
		dou_subtotal=Double.parseDouble(sub_total);
		tab_anual.setValor("valor_reformado_h_pranu",utilitario.getFormatoNumero(sub_tot,3));
		tab_anual.setValor("valor_reformado_d_pranu",utilitario.getFormatoNumero(sub_total,3));
		tab_anual.modificar(tab_anual.getFilaActual());//para que haga el update

		utilitario.addUpdateTabla(tab_anual, "valor_reformado_h_pranu,valor_reformado_d_pranu", "tab_reforma");	
	
	}
	public void calcularDetallle(AjaxBehaviorEvent evt) {
		tab_reforma.modificar(evt); //Siempre es la primera linea
		Calcular();

	}
	public void importarPrograma(){
		
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
	
		//Filtrar los clasificadores del año seleccionado
		set_programa.getTab_seleccion().setSql(ser_presupuesto.getPrograma("true,false"));
		set_programa.getTab_seleccion().ejecutarSql();
		set_programa.dibujar();

	}

	public void aceptarPrograma(){
		String str_seleccionado=set_programa.getValorSeleccionado();
		if(str_seleccionado!=null){
			//Inserto los empleados seleccionados en la tabla de participantes 
			TablaGenerica tab_programa=ser_presupuesto.getTablaGenericaPrograma(str_seleccionado);
			System.out.println(" tabla generica"+tab_programa.getSql());
			for(int i=0;i<tab_programa.getTotalFilas();i++){
				tab_anual.insertar();
				tab_anual.setValor("ide_prpro", tab_programa.getValor(i, "ide_prpro"));
				
			}
			set_programa.cerrar();
			utilitario.addUpdate("tab_anual");			
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		}
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensaje("No se puede insertar", "Debe Seleccionar un Año");
			return;
		}
			if(tab_anual.isFocus()){
				tab_anual.insertar();
				tab_anual.setValor("ide_geani",com_anio.getValue()+"");
				}
			else if(tab_mensual.isFocus()){
				tab_mensual.insertar();
			}
				else if(tab_reforma.isFocus()){
					tab_reforma.insertar();
					
				}
				
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_anual.guardar()){
			if(tab_mensual.guardar()){
				if(tab_reforma.guardar()){
					
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


	public Tabla getTab_anual() {
		return tab_anual;
	}


	public void setTab_anual(Tabla tab_anual) {
		this.tab_anual = tab_anual;
	}


	public Tabla getTab_mensual() {
		return tab_mensual;
	}


	public void setTab_mensual(Tabla tab_mensual) {
		this.tab_mensual = tab_mensual;
	}


	public Tabla getTab_reforma() {
		return tab_reforma;
	}


	public void setTab_reforma(Tabla tab_reforma) {
		this.tab_reforma = tab_reforma;
	}


	public SeleccionTabla getSet_programa() {
		return set_programa;
	}


	public void setSet_programa(SeleccionTabla set_programa) {
		this.set_programa = set_programa;
	}

}
