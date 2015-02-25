package paq_presupuesto;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

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
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_pac extends Pantalla{
	
	private Tabla tab_pac=new Tabla();
	private Tabla tab_partida=new Tabla();
	private Tabla tab_archivo=new Tabla();
	private Tabla tab_anio=new Tabla();
	
	private Combo com_anio=new Combo();
	
	private SeleccionTabla set_clasificador=new SeleccionTabla();
	@EJB
	private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto) utilitario.instanciarEJB(ServicioPresupuesto.class);


public pre_pac(){
		
	com_anio.setCombo("select ide_geani,detalle_geani from gen_anio order by detalle_geani");
	com_anio.setMetodo("seleccionaElAnio");
	bar_botones.agregarComponente(new Etiqueta("Seleccione el Año:"));
	bar_botones.agregarComponente(com_anio);		
	
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
        
        tab_pac.setId("tab_pac");
        tab_pac.setHeader("PLAN ANUAL DE CONTRATACIÒN (PAC)");
        tab_pac.setTabla("pre_pac","ide_prpac",1);
        //tab_pac.getColumna("ide_copec").setCombo("cont_periodo_cuatrimestre","ide_copec","detalle_copec","");
        tab_pac.getColumna("ide_cotio").setCombo("cont_tipo_compra","ide_cotio","detalle_cotio","");
        tab_pac.getColumna("ide_coest").setCombo("cont_estado", "ide_coest","detalle_coest","");
        tab_pac.getColumna("ide_bounm").setCombo("bodt_unidad_medida","ide_bounm","detalle_bounm","");
        tab_pac.getColumna("ide_prcla").setCombo("select ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla from pre_clasificador order by codigo_clasificador_prcla");
        tab_pac.setTipoFormulario(true);  //formulario 
	    tab_pac.getGrid().setColumns(4); //hacer  columnas 
        tab_pac.getColumna("ide_geani").setVisible(false);
        tab_pac.setCondicion("ide_geani=-1");
        tab_pac.agregarRelacion(tab_partida);//agraga relacion para los tabuladores
        tab_pac.agregarRelacion(tab_archivo);
        tab_pac.dibujar();
		PanelTabla pat_pac=new PanelTabla();
		pat_pac.setPanelTabla(tab_pac);
			
		//tabla 2
		tab_partida.setId("tab_partida");
		tab_partida.setHeader("PARTIDA DE CONTRATACION (PAC)");
		tab_partida.setIdCompleto("tab_tabulador:tab_partida");
		tab_partida.setTabla("pre_partida_pac","ide_prpap",2);
		tab_partida.getColumna("ide_prcla").setCombo("select ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla from pre_clasificador order by codigo_clasificador_prcla");
		tab_partida.getColumna("ide_prcla").setAutoCompletar();
		tab_partida.getColumna("ide_prcla").setLectura(true);
		tab_partida.getColumna("valor_prpap").setMetodoChange("cambiaValor");
		tab_partida.getColumna("ide_prcla").setUnico(true);
		tab_partida.getColumna("ide_prpac").setUnico(true);
		tab_partida.setColumnaSuma("valor_prpap");

		tab_partida.dibujar();
		PanelTabla pat_partida=new PanelTabla();
		pat_partida.setPanelTabla(tab_partida);
		Imagen fondo= new Imagen(); 
        
        fondo.setStyle("text-aling:center;position:absolute;top:100px;left:590px;");
        fondo.setValue("imagenes/logo.png");
        pat_partida.setWidth("100%");
        pat_partida.getChildren().add(fondo);
		
		 //tabla 3
		    tab_archivo.setId("tab_archivo");
		    tab_archivo.setHeader("ARCHIVO (PAC)");
	        tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
	        tab_archivo.setTipoFormulario(true);
	        tab_archivo.getGrid().setColumns(4);
	        tab_archivo.setTabla("pre_archivo","ide_prarc",3);
	        tab_archivo.getColumna("fecha_prarc").setUpload("fotos");
	        tab_archivo.dibujar();
	        PanelTabla pat_archivo= new PanelTabla();
	        pat_archivo.setPanelTabla(tab_archivo);
	        Imagen fondo1= new Imagen(); // imagen de fondo
	        
	        fondo1.setStyle("text-aling:center;position:absolute;top:100px;left:490px;");
	        fondo1.setValue("imagenes/logo.png");
	        pat_archivo.setWidth("100%");
	        pat_archivo.getChildren().add(fondo1);
		
	        tab_tabulador.agregarTab("PARTIDA",pat_partida);//tabuladores 
	        tab_tabulador.agregarTab("ARCHIVO",pat_archivo);
	        
	        

		Division div_division=new Division();
		div_division.dividir2(pat_pac,tab_tabulador, "50%", "h");
		agregarComponente(div_division);
		//Boton bot_agregar=new Boton();
		Boton bot_importar=new Boton();
		bot_importar.setIcon("ui-icon-person");
		bot_importar.setValue("Agregar Clasificador");
		bot_importar.setMetodo("importarClasificador");
		bar_botones.agregarBoton(bot_importar);

		set_clasificador.setId("set_clasificador");
		set_clasificador.setSeleccionTabla(ser_presupuesto.getCatalogoPresupuestarioAnio("true", com_anio.getValue().toString()),"ide_prcla");
		set_clasificador.getTab_seleccion().getColumna("DESCRIPCION_CLASIFICADOR_PRCLA").setFiltro(true);		
		set_clasificador.setTitle("SELECCION UNA PARTIDA");		
		set_clasificador.getBot_aceptar().setMetodo("aceptarClasificador");
		agregarComponente(set_clasificador);
		
		
		//ojo con el actualizar los totales // 
}

public void cambiaValor(AjaxBehaviorEvent evt){
	tab_partida.modificar(evt);
	tab_partida.setColumnaSuma("valor_prpap");
	utilitario.addUpdate("tab_tabulador:tab_partida");
}

public void importarClasificador(){
	
	if(com_anio.getValue()==null){
		utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
		return;
	}
	//Si la tabla esta vacia
	if(tab_pac.isEmpty()){
				utilitario.agregarMensajeInfo("No se puede agregar Clasificador, Por que no existen registros", "");
				return;
	}

	//Filtrar los clasificadores del año seleccionado
	set_clasificador.getTab_seleccion().setSql(ser_presupuesto.getCatalogoPresupuestarioAnio("true", com_anio.getValue().toString()));
	set_clasificador.getTab_seleccion().ejecutarSql();
	set_clasificador.dibujar();

}

public void aceptarClasificador(){
	String str_seleccionados=set_clasificador.getSeleccionados();
	if(str_seleccionados!=null){
		//Inserto los empleados seleccionados en la tabla de participantes 
		TablaGenerica tab_clasificador=ser_presupuesto.getTablaCatalogoPresupuestario(str_seleccionados);
		System.out.println(" tabla generica"+tab_clasificador.getSql());
		for(int i=0;i<tab_clasificador.getTotalFilas();i++){
			tab_partida.insertar();
			tab_partida.setValor("ide_prcla", tab_clasificador.getValor(i, "ide_prcla"));
			
		}
		set_clasificador.cerrar();
		utilitario.addUpdate("tab_partida");			
	}
	else{
		utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
	}
}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("No se puede insertar", "Debe Seleccionar un Anio");
			return;
		}
		if(tab_pac.isFocus()){
			tab_pac.insertar();
			tab_pac.setValor("ide_geani",com_anio.getValue()+"");
			}	
		else if(tab_partida.isFocus()){
			tab_partida.insertar();
		}
			else if(tab_archivo.isFocus()){
				tab_archivo.insertar();
				
			}
			
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if( tab_pac.guardar()){
			tab_partida.guardar();
			
       		}
     
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_pac.isFocus()){
			tab_pac.eliminar();
			}
		else if(tab_partida.isFocus()){
			tab_partida.eliminar();
		}
		
	}
	
		public void seleccionaElAnio(){
			if(com_anio.getValue()!=null){
				tab_pac.setCondicion("ide_geani="+com_anio.getValue());
				tab_pac.ejecutarSql();
				tab_partida.ejecutarValorForanea(tab_pac.getValorSeleccionado());
				tab_archivo.ejecutarValorForanea(tab_pac.getValorSeleccionado());
			}
			else {
				tab_pac.setCondicion("ide_geani=-1");
				tab_pac.ejecutarSql();
				tab_anio.ejecutarValorForanea(tab_pac.getValorSeleccionado());
				tab_archivo.ejecutarValorForanea(tab_pac.getValorSeleccionado());

			}
	}

	public Tabla getTab_pac() {
		return tab_pac;
	}

	public void setTab_pac(Tabla tab_pac) {
		this.tab_pac = tab_pac;
	}

	public Tabla getTab_partida() {
		return tab_partida;
	}

	public void setTab_partida(Tabla tab_partida) {
		this.tab_partida = tab_partida;
	}

	public Tabla getTab_archivo() {
		return tab_archivo;
	}

	public void setTab_archivo(Tabla tab_archivo) {
		this.tab_archivo = tab_archivo;
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
	public SeleccionTabla getSet_clasificador() {
		return set_clasificador;
	}
	public void setSet_clasificador(SeleccionTabla set_clasificador) {
		this.set_clasificador = set_clasificador;
	}
	

}
