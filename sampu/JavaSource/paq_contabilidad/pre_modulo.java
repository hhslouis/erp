package paq_contabilidad;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_sistema.aplicacion.Pantalla;

public class pre_modulo extends Pantalla{

	private Tabla tab_modulo=new Tabla();
	private Tabla tab_modulo_estado=new Tabla();
	private Tabla tab_persona_modulo=new Tabla();
	
	
	
	
	public pre_modulo() {
		tab_modulo.setId("tab_modulo");  
		tab_modulo.setTabla("gen_modulo", "ide_gemod", 1);	
		Tabulador tab_Tabulador=new Tabulador();
		tab_Tabulador.setId("tab_tabulador");
		
		tab_modulo.dibujar();
		PanelTabla pat_modulo=new PanelTabla();
		pat_modulo.setPanelTabla(tab_modulo);
		tab_modulo.agregarRelacion(tab_modulo_estado);
		tab_modulo.agregarRelacion(tab_persona_modulo);
		agregarComponente(pat_modulo);
		
		//Estado por modulo//
		tab_modulo_estado.setId("tab_modulo_estado");
		tab_modulo_estado.setIdCompleto("tab_tabulador:tab_modulo_estado");
		tab_modulo_estado.setTabla("gen_modulo_estado", "ide_gemoe", 2);
		tab_modulo_estado.getColumna("ide_gemod").setCombo("gen_modulo", "ide_gemod", "detalle_gemod", "");
		tab_modulo_estado.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_modulo_estado.dibujar();
		PanelTabla pat_panel2 = new PanelTabla();
		pat_panel2.setPanelTabla(tab_modulo_estado);
		
		//persona por modulo
		
		tab_persona_modulo.setId("tab_persona_modulo");
		tab_persona_modulo.setIdCompleto("tab_tabulador:tab_persona_modulo");
		tab_persona_modulo.setTabla("gen_tipo_persona_modulo", "ide_getpm", 3);
		tab_persona_modulo.getColumna("ide_getip").setCombo("gen_tipo_persona", "ide_getip", "detalle_getip", "");
		tab_persona_modulo.dibujar();
		PanelTabla pat_panel3 = new PanelTabla();
		pat_panel3.setPanelTabla(tab_persona_modulo);
		
		tab_Tabulador.agregarTab("ESTADO POR MODULOS", pat_panel2);
		tab_Tabulador.agregarTab("PERSONA POR MODULOS", pat_panel3);
		
		
		
		//dividir2
		Division div_Division=new Division();
		div_Division.dividir2(pat_modulo,tab_Tabulador, "50%", "H");
		agregarComponente(div_Division);
				
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if (tab_modulo.isFocus()){
			tab_modulo_estado.insertar();
		}
				
				
		
			else if (tab_modulo_estado.isFocus()) {
				tab_modulo_estado.insertar();
			
			
				}
			else if (tab_persona_modulo.isFocus()) {
				tab_persona_modulo.insertar();
				
				}		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_modulo.guardar();
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_modulo.eliminar();
	}

	public Tabla gettab_modulo() {
		return tab_modulo;
	}

	public void settab_modulo(Tabla tab_modulo) {
		this.tab_modulo = tab_modulo;
	}

	public Tabla getTab_modulo_estado() {
		return tab_modulo_estado;
	}

	public void setTab_modulo_estado(Tabla tab_modulo_estado) {
		this.tab_modulo_estado = tab_modulo_estado;
	}

	public Tabla getTab_persona_modulo() {
		return tab_persona_modulo;
	}

	public void setTab_persona_modulo(Tabla tab_persona_modulo) {
		this.tab_persona_modulo = tab_persona_modulo;
	}


}
