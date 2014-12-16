package paq_presupuesto;



import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Imagen;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_contratacion extends Pantalla{


	private Tabla tab_poa=new Tabla();
	private Tabla tab_mes=new Tabla();
	private Tabla tab_reforma=new Tabla();
	private Tabla tab_archivo=new Tabla();
	private Tabla tab_financiamiento=new Tabla();

	private Combo com_anio=new Combo();

	private SeleccionTabla set_clasificador=new SeleccionTabla();
	
	 @EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
			




	public pre_contratacion(){
		com_anio.setCombo("select ide_geani,detalle_geani from gen_anio order by detalle_geani");
		com_anio.setMetodo("seleccionaElAño");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);


		Tabulador tab_tabulador = new Tabulador();
		tab_tabulador.setId("tab_tabulador");

		tab_poa.setId("tab_poa");
		tab_poa.setHeader("PLAN OPERATIVO ANUAL (POA)");
		tab_poa.setTabla("pre_poa","ide_prpoa",1);
		tab_poa.getColumna("ide_geani").setVisible(false);
		tab_poa.setCondicion("ide_geani=-1");  

		tab_poa.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
		tab_poa.getColumna("ide_prcla").setCombo("SELECT ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla FROM pre_clasificador ORDER BY codigo_clasificador_prcla");
		tab_poa.getColumna("ide_prcla").setLectura(true);
		tab_poa.getColumna("ide_prsua").setCombo("pre_sub_actividad","ide_prsua","detalle_prsua","");
		tab_poa.getColumna("ide_prfup").setCombo("pre_funcion_programa","ide_prfup","detalle_prfup","");
		tab_poa.getColumna("ide_geuna").setCombo("gen_unidad_administrativa","ide_geuna","detalle_geuna","");

		tab_poa.setTipoFormulario(true);
		tab_poa.getGrid().setColumns(4);

	
		tab_poa.agregarRelacion(tab_mes);//agraga relacion para los tabuladores
		tab_poa.agregarRelacion(tab_financiamiento);
		tab_poa.agregarRelacion(tab_reforma);
		
				
		tab_poa.dibujar();
		PanelTabla pat_poa=new PanelTabla();
		pat_poa.setPanelTabla(tab_poa);
		
		//EJECUCION MENSUAL
		tab_mes.setId("tab_mes");
		tab_mes.setHeader("EJECUCION MENSUAL (POA)");
		tab_mes.setIdCompleto("tab_tabulador:tab_mes");
		tab_mes.setTabla("pre_poa_mes","ide_prpom",2);
		tab_mes.setCampoForanea("ide_prpoa");
		tab_mes.getColumna("ide_gemes").setCombo("select ide_gemes,detalle_gemes from gen_mes order by ide_gemes");
		tab_mes.dibujar();
		PanelTabla pat_panel2 = new PanelTabla();
		pat_panel2.setPanelTabla(tab_mes);
	   	
		// REFORMA MENSUAL
		tab_reforma.setId("tab_reforma");
		tab_reforma.setHeader("REFORMA MENSUAL (POA)");
		tab_reforma.setIdCompleto("tab_tabulador:tab_reforma");
		tab_reforma.setTabla("pre_poa_reforma", "ide_prpor",3);
		tab_reforma.setCampoForanea("ide_prpoa");
		tab_reforma.getColumna("pre_ide_prpoa").setCombo("select ide_prpoa,num_resolucion_prpoa,codigo_clasificador_prcla,descripcion_clasificador_prcla " +	
		"from pre_poa  a, (select ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla  from pre_clasificador ) b where a.ide_prcla=b.ide_prcla");
		tab_reforma.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
		tab_reforma.getColumna("ide_gemes").setCombo("select ide_gemes,detalle_gemes from gen_mes order by ide_gemes");
		
		tab_reforma.setTipoFormulario(true);
		tab_reforma.getGrid().setColumns(4);
		tab_reforma.dibujar();
		PanelTabla pat_panel3=new PanelTabla();
		pat_panel3.setPanelTabla(tab_reforma);

		//FINANCIAMIENTO
		tab_financiamiento.setId("tab_financiamiento");
		tab_financiamiento.setHeader("FUENTES DE FINANCIAMIENTO (POA)");
		tab_financiamiento.setIdCompleto("tab_tabulador:tab_financiamiento");
		tab_financiamiento.setTabla("pre_poa_financiamiento","ide_prpof",4);
		tab_financiamiento.setCampoForanea("ide_prpoa");
		tab_financiamiento.getColumna("ide_prfuf").setCombo("pre_fuente_financiamiento","ide_prfuf","detalle_prfuf","");
		tab_financiamiento.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
		tab_financiamiento.dibujar();
		PanelTabla pat_panel4= new PanelTabla();
		pat_panel4.setPanelTabla(tab_financiamiento);

		//ARCHIVO
		tab_archivo.setId("tab_archivo");
		tab_archivo.setHeader("ARCHIVOS ANEXOS (POA)");
		tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
		tab_archivo.setTipoFormulario(true);
		tab_archivo.setTabla("pre_archivo","ide_prarc",5);
		tab_archivo.getColumna("foto_prarc").setUpload("presupuesto");
		tab_archivo.setCampoForanea("ide_prpoa");
		//ocultar campos de las claves  foraneas
				TablaGenerica  tab_generica=ser_contabilidad.getTablaVigente("pre_archivo");
				for(int i=0;i<tab_generica.getTotalFilas();i++){
				//muestra los ides q quiere mostras.
				//if(!tab_generica.getValor(i, "column_name").equals("ide_prpoa")){	
				tab_archivo.getColumna(tab_generica.getValor(i, "column_name")).setVisible(false);	
				//}				
				
		   		}
		tab_archivo.dibujar();
		PanelTabla pat_panel5= new PanelTabla();
		pat_panel5.setPanelTabla(tab_archivo);
		Imagen fondo= new Imagen();   

		fondo.setStyle("text-aling:center;position:absolute;top:100px;left:490px;");
		fondo.setValue("imagenes/logo.png");
		pat_panel5.setWidth("100%");
		pat_panel5.getChildren().add(fondo);

		tab_tabulador.agregarTab("EJECUCION MENSUAL", pat_panel2);//intancia los tabuladores 
		tab_tabulador.agregarTab("REFORMA MENSUAL",pat_panel3);
		tab_tabulador.agregarTab("FINANCIAMIENTO", pat_panel4);
		tab_tabulador.agregarTab("ARCHIVOS",pat_panel5);
		

		//division2
		
		Division div_division=new Division();
		div_division.dividir2(pat_poa,tab_tabulador,"50%","H");
		agregarComponente(div_division);

		Boton bot_agregar=new Boton();
		bot_agregar.setValue("Agregar Clasificador");
		bot_agregar.setMetodo("agregarClasificador");
		bar_botones.agregarBoton(bot_agregar);

		set_clasificador.setId("set_clasificador");
		set_clasificador.setTitle("SELECCIONE UNA PARTIDA PRESUPUESTARIA");
		set_clasificador.setRadio(); //solo selecciona una opcion
		/*set_clasificador.setSeleccionTabla("SELECT ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla FROM pre_clasificador where ide_geani=-1" +
				"ORDER BY codigo_clasificador_prcla", "ide_prcla"); */
		//set_clasificador.getTab_seleccion().getColumna("codigo_clasificador_prcla").setFiltroContenido(); //pone filtro
		//set_clasificador.getTab_seleccion().getColumna("descripcion_clasificador_prcla").setFiltroContenido();//pone filtro
		//set_clasificador.getBot_aceptar().setMetodo("aceptarClasificador");
		agregarComponente(set_clasificador);
	}

	public void agregarClasificador(){
		//si no selecciono ningun valor en el combo
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		//Si la tabla esta vacia
		if(tab_poa.isEmpty()){
			utilitario.agregarMensajeInfo("No se puede agregar Clasificador, por que no existen registros", "");
			return;
		}
		//Filtrar los clasificadores del año seleccionado
		/*set_clasificador.getTab_seleccion().setSql("SELECT ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla FROM pre_clasificador where ide_geani=" +com_anio.getValue()+
				" ORDER BY codigo_clasificador_prcla");
		set_clasificador.getTab_seleccion().ejecutarSql();
		set_clasificador.dibujar();*/
	}

	public void aceptarClasificador(){
		if(set_clasificador.getValorSeleccionado()!=null){
			//Agrega la fila seleccionada al combo 			
			//tab_poa.getColumna("ide_prcla").getListaCombo().add(set_clasificador.getTab_seleccion().getFilaSeleccionada().getCampos());			
			//Selecciona en el combo el q se selecciono en el dialogo
			tab_poa.setValor("ide_prcla", set_clasificador.getValorSeleccionado());
			//Actualiza 
			utilitario.addUpdate("tab_poa");//actualiza mediante ajax el objeto tab_poa
			set_clasificador.cerrar();
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar un Clasificador", "");
		}
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensaje("No se puede insertar", "Debe Seleccionar un Año");
			return;

		}
		if (tab_poa.isFocus()) {
			tab_poa.insertar();
			tab_poa.setValor("ide_geani", com_anio.getValue()+"");

		}
		else if (tab_mes.isFocus()) {
			tab_mes.insertar();

		}
		else if (tab_reforma.isFocus()) {
			tab_reforma.insertar();
			
		}

		else if (tab_financiamiento.isFocus()){
			tab_financiamiento.insertar();

		}
		else if (tab_archivo.isFocus()) {
			tab_archivo.insertar();

		}
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_poa.guardar()) {
			
			if (tab_mes.guardar()) {
				if( tab_financiamiento.guardar()){
					if(tab_reforma.guardar()){
					tab_archivo.guardar();	
					}
					
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

	public void seleccionaElAño (){
		if(com_anio.getValue()!=null){
			tab_poa.setCondicion("ide_geani="+com_anio.getValue());
			tab_poa.ejecutarSql();
			tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_reforma.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_archivo.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_financiamiento.ejecutarValorForanea(tab_poa.getValorSeleccionado());
		}
		else { 
			tab_poa.setCondicion("ge_geani=-1");
			tab_poa.ejecutarSql();
			tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_reforma.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_archivo.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_financiamiento.ejecutarValorForanea(tab_poa.getValorSeleccionado());


		}
	}

	public Tabla getTab_poa() {
		return tab_poa;
	}

	public void setTab_poa(Tabla tab_poa) {
		this.tab_poa = tab_poa;
	}

	public Tabla getTab_mes() {
		return tab_mes;
	}

	public void setTab_mes(Tabla tab_mes) {
		this.tab_mes = tab_mes;
	}

	public Tabla getTab_financiamiento() {
		return tab_financiamiento;
	}

	public void setTab_financiamiento(Tabla tab_financiamiento) {
		this.tab_financiamiento = tab_financiamiento;
	}

	public Tabla getTab_archivo() {
		return tab_archivo;
	}

	public SeleccionTabla getSet_clasificador() {
		return set_clasificador;
	}

	public void setSet_clasificador(SeleccionTabla set_clasificador) {
		this.set_clasificador = set_clasificador;
	}

	public void setTab_archivo(Tabla tab_archivo) {
		this.tab_archivo = tab_archivo;
	}

	public Tabla getTab_reforma() {
		return tab_reforma;
	}

	public void setTab_reforma(Tabla tab_reforma) {
		this.tab_reforma = tab_reforma;
	}
	

}
