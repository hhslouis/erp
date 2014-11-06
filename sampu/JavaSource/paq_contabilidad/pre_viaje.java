package paq_contabilidad;




import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

 

public class pre_viaje extends Pantalla {

	private Tabla tab_tiket_viaje = new Tabla();
	private Tabla tab_cont_viajeros = new Tabla();
	private Combo com_tipo_transporte=new Combo();
	
	public pre_viaje() {
		
		com_tipo_transporte.setCombo("select ide_cotit,detalle_cotit from cont_tipo_transporte where activo_cotit = true" +
				" order by detalle_cotit");
		com_tipo_transporte.setMetodo("seleccionaTipo_Transporte");
		bar_botones.agregarComponente(new Etiqueta("Tipo de Transporte:"));
		bar_botones.agregarComponente(com_tipo_transporte);		
		

		
		tab_tiket_viaje.setId("tab_tiket_viaje");
		tab_tiket_viaje.setTabla("cont_tiket_viaje", "ide_cotiv",1);
		tab_tiket_viaje.getColumna("ide_cotit").setVisible(false);
		tab_tiket_viaje.setCondicion("ide_cotit=-1");
	
		
		tab_tiket_viaje.getColumna("IDE_COTIV").setCombo("CONT_TIKET_VIAJE","IDE_COTIV", "DETALLE_VIAJE_COTIV", "");
		
		tab_tiket_viaje.getColumna("IDE_COASV").setCombo("CONT_ASUNTO_VIAJE","IDE_COASV", "DETALLE_COASV", "");
		tab_tiket_viaje.getColumna("IDE_COEST").setCombo("CONT_ESTADO","IDE_COEST", "DETALLE_COEST", "");
		tab_tiket_viaje.getColumna("IDE_INDIP").setCombo("INST_DISTRIBUCION_POLITICA","IDE_INDIP", "DETALLE_INDIP", "");
		
		tab_tiket_viaje.getColumna("INS_IDE_INDIP").setCombo("INST_DISTRIBUCION_POLITICA","INS_IDE_INDIP", "DETALLE_INDIP", "");
		
		//tab_tiket_viaje.getColumna("ACTIVO_COTIV").setValorDefecto("false");	
		
		tab_tiket_viaje.setTipoFormulario(true);
		tab_tiket_viaje.getGrid().setColumns(4);
		
		

		tab_tiket_viaje.dibujar();
		
		tab_tiket_viaje.agregarRelacion(tab_cont_viajeros);
	    PanelTabla pat_tiket_viaje =new PanelTabla();
	    pat_tiket_viaje.setPanelTabla(tab_tiket_viaje);
	     
//divicion 2 
	    
	    tab_cont_viajeros.setId("tab_cont_viajeros");
		tab_cont_viajeros.setTabla("cont_viajeros", "ide_covia",2);
		tab_cont_viajeros.getColumna("IDE_COVIA").setCombo("CONT_VIAJEROS","IDE_COVIA", "BOLETO_COVIA", "");
		tab_cont_viajeros.getColumna("IDE_COCLV").setCombo("CONT_CLASE_VIAJE","IDE_COCLV", "DETALLE_COCLV", "");
		tab_cont_viajeros.getColumna("IDE_COTIV").setCombo("CONT_TIKET_VIAJE","IDE_COTIV", "DETALLE_VIAJE_COTIV", "");
		
		
		tab_cont_viajeros.setCampoForanea("ide_cotiv");

		
		tab_cont_viajeros.setTipoFormulario(true);
		tab_cont_viajeros.getGrid().setColumns(4);
		
		tab_cont_viajeros.dibujar();
		PanelTabla pat_cont_viajeros = new PanelTabla ();
		pat_cont_viajeros.setPanelTabla(tab_cont_viajeros);
		
		Division div_cont_viajeros=new Division();
		div_cont_viajeros.dividir2(pat_tiket_viaje, pat_cont_viajeros, "50%", "H");
		agregarComponente(div_cont_viajeros);
	}
	
	
	
	
	
	public Tabla gettab_tiket_viaje() {
		return tab_tiket_viaje;
	}





	public void settab_tiket_viaje(Tabla tab_tiket_viaje) {
		this.tab_tiket_viaje = tab_tiket_viaje;
	}





	public Tabla gettab_cont_viajeros() {
		return tab_cont_viajeros;
	}





	public void settab_cont_viajeros(Tabla tab_cont_viajeros) {
		this.tab_cont_viajeros = tab_cont_viajeros;
	}






	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (com_tipo_transporte.getValue()==null){
			utilitario.agregarMensajeInfo("No se puede insertar", "Debe Seleccionar un Transporte");
			return;
		}		
		
		
		if(tab_tiket_viaje.isFocus()){
		tab_tiket_viaje.insertar();
		tab_tiket_viaje.setValor("ide_cotit", com_tipo_transporte.getValue()+"");

	}
		
		else if(tab_cont_viajeros.isFocus()){
			    tab_cont_viajeros.insertar();
			    
		}
			
		}
	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_tiket_viaje.guardar()){
			tab_cont_viajeros.guardar();		
	}
        guardarPantalla();		
	}



	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_tiket_viaje.isFocus()){
			tab_tiket_viaje.eliminar();
		}
		else if(tab_cont_viajeros.isFocus()){
			tab_cont_viajeros.eliminar();
	}		
	}
	public void seleccionaTipo_Transporte(){
		if(com_tipo_transporte.getValue()!=null){
			tab_tiket_viaje.setCondicion("ide_cotit="+com_tipo_transporte.getValue());
			tab_tiket_viaje.ejecutarSql();
			tab_cont_viajeros.ejecutarValorForanea(tab_tiket_viaje.getValorSeleccionado());
		}
		else {
			tab_tiket_viaje.setCondicion("ide_cotit=-1");
			tab_tiket_viaje.ejecutarSql();
			tab_cont_viajeros.ejecutarValorForanea(tab_tiket_viaje.getValorSeleccionado());
	
		}
	}
		

}
