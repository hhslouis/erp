package paq_presupuesto;

import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Imagen;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_pac extends Pantalla{
	
	private Tabla tab_pac=new Tabla();
	private Tabla tab_partida=new Tabla();
	private Tabla tab_archivo=new Tabla();
	private Tabla tab_anio=new Tabla();
	
	private Combo com_anio=new Combo();

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
        tab_pac.getColumna("ide_copec").setCombo("cont_periodo_cuatrimestre","ide_copec","detalle_copec","");
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
		tab_partida.setCampoForanea("ide_prpac");
		tab_partida.dibujar();
		PanelTabla pat_partida=new PanelTabla();
		pat_partida.setPanelTabla(tab_partida);
		Imagen fondo= new Imagen(); 
        
        fondo.setStyle("text-aling:center;position:absolute;top:100px;left:490px;");
        fondo.setValue("imagenes/logo.png");
        pat_partida.setWidth("100%");
        pat_partida.getChildren().add(fondo);
		
		 //tabla 3
		    tab_archivo.setId("tab_archivo");
		    tab_archivo.setHeader("ARCHIVO (PAC)");
	        tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
	        tab_archivo.setTipoFormulario(true);
	        tab_archivo.getGrid().setColumns(4);
	        tab_archivo.setTabla("pre_archivo_pac","ide_prarp",3);
	        tab_archivo.getColumna("foto_prarp").setUpload("fotos");
	        //tab_archivo.getColumna("foto_prarp").setImagen("128", "128");
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
			if( tab_anio.guardar()){
			tab_partida.guardar();
			
         }
		}
     
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_pac.isFocus()){
			tab_pac.eliminar();
			}
		else if(tab_anio.isFocus()){
			tab_anio.eliminar();
		}
		
	}
	
		public void seleccionaElAnio(){
			if(com_anio.getValue()!=null){
				tab_pac.setCondicion("ide_geani="+com_anio.getValue());
				tab_pac.ejecutarSql();
				tab_anio.ejecutarValorForanea(tab_pac.getValorSeleccionado());
			}
			else {
				tab_pac.setCondicion("ide_geani=-1");
				tab_pac.ejecutarSql();
				tab_anio.ejecutarValorForanea(tab_pac.getValorSeleccionado());
		
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
	

}
