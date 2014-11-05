package paq_presupuesto;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_pac extends Pantalla{
	
	private Tabla tab_pac=new Tabla();
	private Tabla tab_partida=new Tabla();
	private Tabla tab_archivo=new Tabla();

public pre_pac(){
		
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
        
        tab_pac.setId("tab_pac");
        tab_pac.setTipoFormulario(true);  //formulario 
	    tab_pac.getGrid().setColumns(4); //hacer  columnas 
        tab_pac.setTabla("pre_pac","ide_prpac",1);
        tab_pac.agregarRelacion(tab_partida);//agraga relacion para los tabuladores
        tab_pac.dibujar();
		PanelTabla pat_pac=new PanelTabla();
		pat_pac.setPanelTabla(tab_pac);
		
		
		tab_partida.setId("tab_partida");
		 tab_archivo.setIdCompleto("tab_tabulador:tab_partida");
		tab_partida.setTabla("pre_partida_pac","ide_prpap",2);
		tab_partida.setCampoForanea("ide_prpac");
		tab_partida.dibujar();
		PanelTabla pat_partida=new PanelTabla();
		pat_partida.setPanelTabla(tab_partida);
		
		    tab_archivo.setId("tab_archivo");
	        tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
	        tab_archivo.setTipoFormulario(true);
	        tab_archivo.setTabla("pre_archivo_pac","ide_prarp",3);
	        tab_archivo.getColumna("foto_prarp").setUpload("fotos");
	        tab_archivo.getColumna("foto_prarp").setImagen("128", "128");
	        tab_archivo.dibujar();
	        PanelTabla pat_archivo= new PanelTabla();
	        pat_archivo.setPanelTabla(tab_archivo);
		
	        tab_tabulador.agregarTab("ARCHIVO",pat_archivo);
	        tab_tabulador.agregarTab("PARTIDA",pat_partida);
	        

		Division div_division=new Division();
		div_division.dividir2(tab_pac,tab_partida, "50%", "h");
		agregarComponente(div_division);
		
		
		
}
	@Override
	public void insertar() {
		// TODO Auto-generated method stubif(tab_suministro.isFocus()){
		if(tab_pac.isFocus()){
			tab_pac.insertar();
			}
		else if(tab_partida.isFocus()){
			tab_partida.insertar();
			
		}
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stubif(tab_suministro.guardar()){
		if( tab_partida.guardar()){
      	   tab_archivo.guardar();
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
	

}
