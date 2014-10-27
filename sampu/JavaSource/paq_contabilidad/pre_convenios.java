package paq_contabilidad;

import framework.componentes.Arbol;
import framework.componentes.AutoCompletar;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_convenios extends Pantalla{
	 
	 private Tabla tab_responsable_conv =new Tabla();
	 private Tabla tab_archivo_conv=new Tabla();	
	private Tabla tab_convenio=new Tabla();
		
	private Arbol arb_convenio=new Arbol();
		
	public pre_convenios(){
	
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
                
						        
		tab_convenio.setId("tab_convenio");
		tab_convenio.setTipoFormulario(true);  //formulario 
		tab_convenio.getGrid().setColumns(4); //hacer  columnas 
		tab_convenio.setTabla("cont_convenio", "ide_cocon", 1);
		
		
	    tab_convenio.getColumna("ide_cotic").setCombo("cont_tipo_consumo","ide_cotic","detalle_cotic","");
		tab_convenio.getColumna("ide_geins").setCombo("gen_institucion","ide_geins","detalle_geins","");
		tab_convenio.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
	 
	   	   
		// contruccion del arbol
		tab_convenio.setCampoPadre("con_ide_cocon"); //necesarios para el arbol ide recursivo
		tab_convenio.agregarArbol(arb_convenio);//necesarios para el arbol
		tab_convenio.setCampoNombre("(select ide_cocon||' '|| detalle_contrato_cocon as detalle_contrato_cocon from cont_convenio b where b.ide_cocon=cont_convenio.ide_cocon)"); //necesarios para el arbol campo a mostrarse
		
		tab_convenio.dibujar();
				
		PanelTabla pat_convenio = new PanelTabla();
		pat_convenio.setPanelTabla(tab_convenio);

		arb_convenio.setId("arb_convenio");
		arb_convenio.dibujar();
		Division division=new Division();
		division.dividir1(arb_convenio);
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
		div_division.dividir2(arb_convenio,tab_tabulador,"50%","H");
		//division.dividir3(arb_convenio, pat_panel2, pat_panel3,"25%", "50%", "h");
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
		if(tab_convenio.guardar()){
            guardarPantalla();
            //actualiza el arbol
            arb_convenio.ejecutarSql();
            utilitario.addUpdate("arb_arbol");
        }
		
				
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_convenio.eliminar();
		
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

	public Tabla getTab_convenio() {
		return tab_convenio;
	}

	public void setTab_convenio(Tabla tab_convenio) {
		this.tab_convenio = tab_convenio;
	}

	public Arbol getArb_convenio() {
		return arb_convenio;
	}

	public void setArb_convenio(Arbol arb_convenio) {
		this.arb_convenio = arb_convenio;
	}
	
	
	

}
