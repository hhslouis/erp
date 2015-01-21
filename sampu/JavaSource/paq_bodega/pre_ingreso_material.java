package paq_bodega;

import framework.componentes.Combo;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_ingreso_material extends Pantalla{
	private Tabla tab_bodega=new Tabla();
	private Tabla tab_anio=new Tabla();
	private Combo com_anio=new Combo();
	
	public pre_ingreso_material (){
		com_anio.setCombo("select ide_geani,detalle_geani from gen_anio order by detalle_geani");
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		
		tab_bodega.setId("tab_bodega");  
		tab_bodega.setTabla("bodt_bodega","ide_bobod", 1);	
		tab_bodega.getColumna("ide_geani").setVisible(false);
		tab_bodega.setCondicion("ide_geani=-1"); 
		tab_bodega.getColumna("ide_bomat").setCombo("select ide_bomat,codigo_bomat,detalle_bomat,iva_bomat from bodt_material order by detalle_bomat");
		tab_bodega.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest"," detalle_coest","");
		tab_bodega.setTipoFormulario(true);
		tab_bodega.getGrid().setColumns(4);	
		tab_bodega.dibujar();
		PanelTabla pat_bodega=new PanelTabla();
		pat_bodega.setPanelTabla(tab_bodega);
		
		agregarComponente(pat_bodega);
		
		
		
	}
	

	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_bodega.setCondicion("ide_geani="+com_anio.getValue());
			tab_bodega.ejecutarSql();
			//tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());
			
		}
		else{
			utilitario.agregarMensajeInfo("Selecione un año", "");

		}
	}
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}else{
			tab_bodega.isFocus(); 
			tab_bodega.insertar();
			tab_bodega.setValor("ide_geani", com_anio.getValue()+"");
			
		}
				
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_bodega.guardar();
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_bodega.eliminar();
	}




	public Tabla getTab_bodega() {
		return tab_bodega;
	}




	public void setTab_bodega(Tabla tab_bodega) {
		this.tab_bodega = tab_bodega;
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
