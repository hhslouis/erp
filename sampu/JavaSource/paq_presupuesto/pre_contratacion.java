package paq_presupuesto;


import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Imagen;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_contratacion extends Pantalla{
	
	
	private Tabla tab_poa=new Tabla();
	private Tabla tab_mes=new Tabla();
	private Tabla tab_anio=new Tabla();
	private Tabla tab_archivo=new Tabla();
	private Tabla tab_financiamiento=new Tabla();
	
	private Combo com_anio=new Combo();
	
	

	public pre_contratacion(){
		com_anio.setCombo("select ide_geani,detalle_geani from gen_anio order by detalle_geani");
		com_anio.setMetodo("seleccionaElAño");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
        
        tab_poa.setId("tab_poa");
        tab_poa.setHeader("PLAN OPERTIVO ANUAL (POA)");
        tab_poa.setTabla("pre_poa","ide_prpoa",1);
     	tab_poa.getColumna("ide_geani").setVisible(false);
    	tab_poa.setCondicion("ide_geani=-1");  
    		
        tab_poa.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
        tab_poa.getColumna("ide_prcla").setCombo("SELECT ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla FROM pre_clasificador ORDER BY codigo_clasificador_prcla");
        tab_poa.getColumna("ide_prcla").setAutoCompletar();
        tab_poa.getColumna("ide_prsua").setCombo("pre_sub_actividad","ide_prsua","detalle_prsua","");
        tab_poa.getColumna("ide_prfup").setCombo("pre_funcion_programa","ide_prfup","detalle_prfup","");
        tab_poa.getColumna("ide_geuna").setCombo("gen_unidad_administrativa","ide_geuna","detalle_geuna","");
        
        tab_poa.setTipoFormulario(true);
        tab_poa.getGrid().setColumns(4);
      
        // tab_poa.setCampoForanea("ide_cosum");
     
        tab_poa.agregarRelacion(tab_mes);//agraga relacion para los tabuladores
        tab_poa.agregarRelacion(tab_financiamiento);
        tab_poa.dibujar();
		PanelTabla pat_poa=new PanelTabla();
		pat_poa.setPanelTabla(tab_poa);
		

		tab_mes.setId("tab_mes");
		tab_mes.setHeader("EJECUCION MENSUAL (POA)");
		tab_mes.setIdCompleto("tab_tabulador:tab_mes");
		tab_mes.setTabla("pre_poa_mes","ide_prpom",2);
		tab_mes.setCampoForanea("ide_prpoa");
		tab_mes.dibujar();
	    PanelTabla pat_panel2 = new PanelTabla();
	    pat_panel2.setPanelTabla(tab_mes);
	        
		tab_financiamiento.setId("tab_financiamiento");
		tab_financiamiento.setHeader("FUENTES DE FINANCIAMIENTO (POA)");
		tab_financiamiento.setIdCompleto("tab_tabulador:tab_financiamiento");
		tab_financiamiento.setTabla("pre_poa_financiamiento","ide_prpof",3);
		tab_financiamiento.setCampoForanea("ide_prpoa");
		tab_financiamiento.dibujar();
	    PanelTabla pat_panel3 = new PanelTabla();
	    pat_panel3.setPanelTabla(tab_financiamiento);
	        
	       
	        tab_archivo.setId("tab_archivo");
	        tab_archivo.setHeader("ARCHIVOS ANEXOS (POA)");
	        tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
	        tab_archivo.setTipoFormulario(true);
	        tab_archivo.setTabla("pre_poa_archivo","ide_prpor",4);
	        tab_archivo.getColumna("foto_prpor").setUpload("presupuesto");
	        tab_archivo.setCampoForanea("ide_prpoa");
	        tab_archivo.dibujar();
	        PanelTabla pat_panel4 = new PanelTabla();
	        pat_panel4.setPanelTabla(tab_archivo);
	        Imagen fondo= new Imagen();   
	        
	        fondo.setStyle("text-aling:center;position:absolute;top:100px;left:490px;");
	        fondo.setValue("imagenes/logo.png");
	        pat_panel4.setWidth("100%");
	        pat_panel4.getChildren().add(fondo);
	        
	        tab_tabulador.agregarTab("EJECUCION MENSUAL", pat_panel2);//intancia los tabuladores
	        tab_tabulador.agregarTab("FINANCIAMIENTO", pat_panel3);
	        tab_tabulador.agregarTab("ARCHIVOS",pat_panel4);
	        
	        
	        Division div_division=new Division();
			div_division.dividir2(pat_poa,tab_tabulador,"50%","H");
			agregarComponente(div_division);
			
			
			
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
         	       tab_archivo.guardar();
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
			tab_archivo.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			tab_financiamiento.ejecutarValorForanea(tab_poa.getValorSeleccionado());
		}
		else { 
			tab_poa.setCondicion("ge_geani=-1");
			tab_poa.ejecutarSql();
			tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());
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




	public void setTab_archivo(Tabla tab_archivo) {
		this.tab_archivo = tab_archivo;
	}

}
