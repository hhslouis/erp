package paq_contabilidad;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;

public class pre_convenios extends Pantalla{
	 private Tabla tab_convenio =new Tabla();
	 private Tabla tab_responsable_conv =new Tabla();
	 private Tabla tab_archivo_conv=new Tabla();
	 

	public pre_convenios() {
		// TODO Auto-generated constructor stub
		
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
                
		tab_convenio.setId("tab_convenio");
		tab_convenio.setTabla("cont_convenio", "ide_cocon", 1);
		tab_convenio.agregarRelacion(tab_responsable_conv);
		tab_convenio.agregarRelacion(tab_archivo_conv);
				
		tab_convenio.dibujar();
		PanelTabla  pat_convenio =new PanelTabla();
		pat_convenio.setPanelTabla(tab_convenio);
	
		tab_responsable_conv.setId("tab_responsable_conv");
		tab_responsable_conv.setIdCompleto("tab_tabulador:tab_responsable_conv");
		tab_responsable_conv.setTabla("cont_responsable_convenio","ide_corec",2);
		tab_responsable_conv.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_responsable_conv);
        
        tab_archivo_conv.setId("tab_archivo_conv");
		tab_archivo_conv.setIdCompleto("tab_tabulador:tab_archivo_conv");
		tab_archivo_conv.setTabla("cont_archivo_convenio","ide_coarc",2);
		tab_archivo_conv.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_archivo_conv);
	
        tab_tabulador.agregarTab("RESPONSABLE", pat_panel2);//intancia los tabuladores
        tab_tabulador.agregarTab("ARCHIVO", pat_panel3);
        Division div_division=new Division();
		div_division.dividir2(pat_convenio,tab_tabulador,"50%","H");
		agregarComponente(div_division);
	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_convenio.insertar();
				
	}



	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_convenio.guardar();
	}



	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_convenio.eliminar();
	}



	public Tabla getTab_convenio() {
		return tab_convenio;
	}



	public void setTab_convenio(Tabla tab_convenio) {
		this.tab_convenio = tab_convenio;
	}



	public Tabla getTab_responsable_conv() {
		return tab_responsable_conv;
	}



	public void setTab_responsable_conv(Tabla tab_responsable_conv) {
		this.tab_responsable_conv = tab_responsable_conv;
	}



	public Tabla getTab_archivo_conv() {
		return tab_archivo_conv;
	}



	public void setTab_archivo_conv(Tabla tab_archivo_conv) {
		this.tab_archivo_conv = tab_archivo_conv;
	}

}
