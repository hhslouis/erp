package paq_presupuesto;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_contrato extends Pantalla {
	private Tabla tab_contrato =new Tabla();
	private Tabla tab_administrador=new Tabla();
	private Tabla tab_compareciente =new Tabla();
	
	
	public pre_contrato (){
		tab_contrato.setId("tab_contrato");
		tab_contrato.setHeader("CONTRATO");
		tab_contrato.setTabla("pre_contrato", "ide_prcon", 1);
		tab_contrato.getColumna("ide_coest").setCombo("cont_estado", " ide_coest", "detalle_coest", "");
		tab_contrato.setTipoFormulario(true);
		tab_contrato.getGrid().setColumns(4);
		tab_contrato.dibujar();
		PanelTabla pat_contrato =new PanelTabla();
		pat_contrato.setPanelTabla(tab_contrato);
		tab_contrato.agregarRelacion(tab_administrador);
		//tab_contrato.agregarRelacion(tab_compareciente);
		//agregarComponente(pat_contrato);
		
		//tabla administrador_contrato
		tab_administrador.setId("tab_administrador");
		tab_administrador.setHeader("COMPARECIENTE CONTRATO");
		tab_administrador.setTabla("pre_administrador_contrato", "ide_pradc", 2);
		tab_administrador.setTipoFormulario(true);
		tab_administrador.getGrid().setColumns(4);
		tab_administrador.dibujar();
		PanelTabla pat_administrador =new PanelTabla();
		pat_administrador.setPanelTabla(tab_administrador);
		//agregarComponente(pat_administrador);		
		
		// tabla compareciente contrato
		tab_compareciente.setId("tab_compareciente");
		tab_compareciente.setHeader("COMPARECIENTE CONTRATO");
		tab_compareciente.setTabla("pre_compareciente_contrato", "ide_prcoc", 3);
		tab_compareciente.getColumna("ide_prtio").setCombo("pre_tipo_compareciente", "ide_prtio", "detalle_prtio", "");
		tab_compareciente.getColumna("ide_getip").setCombo("gen_tipo_persona", "ide_getip", "detalle_getip", "");
		tab_compareciente.setTipoFormulario(true);
		tab_compareciente.getGrid().setColumns(4);
		tab_compareciente.dibujar();
		PanelTabla pat_compareciente =new PanelTabla();
		pat_compareciente.setPanelTabla(tab_compareciente);
		//agregarComponente(pat_compareciente);
		
		Division div_administrador =new Division();
		div_administrador.dividir2(pat_administrador, pat_compareciente, "50%", "v");
		agregarComponente(div_administrador);
		
		Division div_contrato=new Division();
		div_contrato.dividir2(pat_contrato,div_administrador,"50%","h");
		agregarComponente(div_contrato);		
		
		
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_contrato.guardar()){
			if (tab_administrador.guardar()) {
				tab_compareciente.guardar();
				guardarPantalla();
				
			}
		}
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus();
		
	}

	public Tabla getTab_contrato() {
		return tab_contrato;
	}

	public void setTab_contrato(Tabla tab_contrato) {
		this.tab_contrato = tab_contrato;
	}

	public Tabla getTab_administrador() {
		return tab_administrador;
	}

	public void setTab_administrador(Tabla tab_administrador) {
		this.tab_administrador = tab_administrador;
	}

	public Tabla getTab_compareciente() {
		return tab_compareciente;
	}

	public void setTab_compareciente(Tabla tab_compareciente) {
		this.tab_compareciente = tab_compareciente;
	}

}
