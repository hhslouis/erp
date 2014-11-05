package paq_presupuesto;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_contratacion extends Pantalla{
	
	
	private Tabla tab_poa=new Tabla();
	private Tabla tab_mes=new Tabla();
	private Tabla tab_archivo=new Tabla();
	private Tabla tab_financiamiento=new Tabla();

	public pre_contratacion(){
		
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
        
        tab_poa.setId("tab_poa");
        tab_poa.setTabla("pre_poa","ide_prpoa",1);
       // tab_poa.setCampoForanea("ide_cosum");
        tab_poa.agregarRelacion(tab_mes);//agraga relacion para los tabuladores
        tab_poa.agregarRelacion(tab_financiamiento);
        tab_poa.dibujar();
		PanelTabla pat_poa=new PanelTabla();
		pat_poa.setPanelTabla(tab_poa);
		

		tab_mes.setId("tab_mes");
		tab_mes.setIdCompleto("tab_tabulador:tab_mes");
		tab_mes.setTabla("pre_poa_mes","ide_prpom",2);
		tab_mes.dibujar();
	    PanelTabla pat_panel2 = new PanelTabla();
	    pat_panel2.setPanelTabla(tab_mes);
	        
		tab_financiamiento.setId("tab_financiamiento");
		tab_financiamiento.setIdCompleto("tab_tabulador:tab_financiamiento");
		tab_financiamiento.setTabla("pre_poa_financiamiento","ide_prpof",3);
		tab_financiamiento.dibujar();
	    PanelTabla pat_panel3 = new PanelTabla();
	    pat_panel3.setPanelTabla(tab_financiamiento);
	        
	                
	        	        
	        tab_archivo.setId("tab_archivo");
	        tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
	        tab_archivo.setTipoFormulario(true);
	        tab_archivo.setTabla("pre_poa_archivo","ide_prpor",4);
	        tab_archivo.getColumna("foto_prpor").setUpload("fotos");
	        tab_archivo.getColumna("foto_prpor").setImagen("128", "128");
	        tab_archivo.dibujar();
	        PanelTabla pat_panel4 = new PanelTabla();
	        pat_panel4.setPanelTabla(tab_archivo);
	        
	        tab_tabulador.agregarTab("MES", pat_panel2);//intancia los tabuladores
	        tab_tabulador.agregarTab("FINANCIAMIENTO", pat_panel3);
	        tab_tabulador.agregarTab("ARCHIVO",pat_panel4);
	        
	        
	        Division div_division=new Division();
			div_division.dividir2(pat_poa,tab_tabulador,"50%","H");
			agregarComponente(div_division);
			
			
			
	}
	
	
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_mes.guardar()) {
            if( tab_financiamiento.guardar()){
         	   tab_archivo.guardar();
            }
         }
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
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
