package paq_contabilidad;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_clientes extends Pantalla {
	
	
	private Tabla tab_direccion=new Tabla();
	private Tabla tab_telefono=new Tabla();
	private Tabla tab_email=new Tabla();
	private Tabla tab_documento=new Tabla();
	private Tabla tab_clientes=new Tabla();
	
	
	public pre_clientes (){
		
		Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");
        
       
		tab_clientes.setId("tab_clientes");
		tab_clientes.setTipoFormulario(true);  //formulario 
	    tab_clientes.getGrid().setColumns(4); //hacer  columnas 
		tab_clientes.setTabla("rec_clientes", "ide_recli",1);
		tab_clientes.getColumna("ide_retic").setCombo("rec_tipo_contribuyente", "ide_retic", "detalle_retic", "");
		tab_clientes.getColumna("ide_retia").setCombo("rec_tipo_asistencia", "ide_retia", "detalle_retia", "");
		tab_clientes.getColumna("ide_gtgen").setCombo("gth_genero", "ide_gtgen", "detalle_gtgen", "");
		tab_clientes.getColumna("ide_indip").setCombo("inst_distribucion_politica", "ide_indip", "detalle_indip", "");
		tab_clientes.getColumna("ide_gttdi").setCombo("gth_tipo_documento_identidad", "ide_gttdi", " detalle_gttdi", "");
		tab_clientes.getColumna("gth_ide_gttdi").setCombo("gth_tipo_documento_identidad", "ide_gttdi", "codigo_sri_gttdi", "");
		tab_clientes.getColumna(" ide_gtesc ").setCombo("gth_estado_civil", " ide_gtesc ", "detalle_gtesc", "");
		tab_clientes.agregarRelacion(tab_direccion);//agraga relacion para los tabuladores
		tab_clientes.agregarRelacion(tab_telefono);
        tab_clientes.agregarRelacion(tab_email);
        tab_clientes.agregarRelacion(tab_documento);
       // System.out.println("sql pc"+tab_clientes.getSql());
        tab_clientes.dibujar();
		PanelTabla pat_clientes=new PanelTabla ();
		pat_clientes.setPanelTabla(tab_clientes);
		
		tab_direccion.setId("tab_direccion");
		tab_direccion.setIdCompleto("tab_tabulador:tab_direccion");
		tab_direccion.setTabla("rec_cliente_direccion","ide_recld",2);
		tab_direccion.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_direccion);
        
        
        tab_telefono.setId("tab_telefono");
        tab_telefono.setIdCompleto("tab_tabulador:tab_telefono");
        tab_telefono.setTabla("rec_cliente_telefono","ide_reclt",3);
        tab_telefono.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_telefono);
        
        tab_email.setId("tab_email");
        tab_email.setIdCompleto("tab_tabulador:tab_email");
        tab_email.setTabla("rec_cliente_email","ide_recle",4);
        tab_email.dibujar();
        PanelTabla pat_panel4 = new PanelTabla();
        pat_panel4.setPanelTabla(tab_email);
        
        tab_documento.setId("tab_documento");
        tab_documento.setIdCompleto("tab_tabulador:tab_documento");
        tab_documento.setTabla("rec_cliente_archivo","ide_recla",5);
        tab_documento.dibujar();
        PanelTabla pat_panel5 = new PanelTabla();
        pat_panel5.setPanelTabla(tab_documento);
        
        tab_tabulador.agregarTab("DIRECCION", pat_panel2);//intancia los tabuladores
        tab_tabulador.agregarTab("TELEFONO", pat_panel3);
        tab_tabulador.agregarTab("EMAIL", pat_panel4);
        tab_tabulador.agregarTab("DOCUMENTO", pat_panel5);
		
		
		Division div_division=new Division();
		div_division.dividir2(pat_clientes,tab_tabulador,"70%","H");
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
		if (tab_clientes.guardar()) {
            if (tab_direccion.guardar()) {
                if (tab_telefono.guardar()) {
                   if( tab_email.guardar()){
                	   tab_documento.guardar();
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



	public Tabla getTab_clientes() {
		return tab_clientes;
	}



	public void setTab_clientes(Tabla tab_clientes) {
		this.tab_clientes = tab_clientes;
	}



	public Tabla getTab_direccion() {
		return tab_direccion;
	}



	public void setTab_direccion(Tabla tab_direccion) {
		this.tab_direccion = tab_direccion;
	}



	public Tabla getTab_telefono() {
		return tab_telefono;
	}



	public void setTab_telefono(Tabla tab_telefono) {
		this.tab_telefono = tab_telefono;
	}



	public Tabla getTab_email() {
		return tab_email;
	}



	public void setTab_email(Tabla tab_email) {
		this.tab_email = tab_email;
	}

	public Tabla getTab_documento() {
		return tab_documento;
	}



	public void setTab_documento(Tabla tab_documento) {
		this.tab_documento = tab_documento;
	}
}
