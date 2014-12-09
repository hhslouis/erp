package paq_presupuesto;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_contratacion_publica extends Pantalla {
	private Tabla tab_contratacion =new Tabla();
	private Tabla tab_responsable=new Tabla();
	private Tabla tab_partida=new Tabla();
	private Tabla tab_archivo =new Tabla();
	
	
	public pre_contratacion_publica(){
		tab_contratacion.setId("tab_contratacion");
		tab_contratacion.setHeader("PROCESOS DE CONTRATACION PUBLICA");
		tab_contratacion.setTabla("pre_contratacion_publica", "ide_prcop", 1);
		tab_contratacion.getColumna("ide_prfop").setCombo("pre_forma_pago", "ide_prfop", "detalle_prfop", "");
		tab_contratacion.getColumna("ide_coest").setCombo("cont_estado", " ide_coest", "detalle_coest", "");
		tab_contratacion.getColumna("ide_gemos").setCombo("gen_modulo_secuencial", "ide_gemos", "numero_secuencial_gemos", "");
		tab_contratacion.getColumna("ide_prtpc").setCombo("pre_tipo_contratacion", "ide_prtpc", "detalle_prtpc", "");
		tab_contratacion.getColumna("ide_prpac").setCombo("pre_pac", "ide_prpac", "descripcion_prpac", "");
		tab_contratacion.getColumna("ide_cotio").setCombo("cont_tipo_compra", "ide_cotio", "detalle_cotio", "");
		tab_contratacion.setTipoFormulario(true);
		tab_contratacion.getGrid().setColumns(4);
		tab_contratacion.dibujar();
	  	PanelTabla pat_contratacion=new PanelTabla();
		pat_contratacion.setPanelTabla(tab_contratacion);
		
		//tabla responsable
		tab_responsable.setId("tab_responsable");
		tab_responsable.setHeader("RESPONSABLE DE CONTATACION");
		tab_responsable.setTabla("pre_responsable_contratacion", "ide_prrec", 2);
		tab_responsable.setTipoFormulario(true);
		tab_responsable.getGrid().setColumns(2);
		tab_responsable.dibujar();
		PanelTabla pat_responsable =new PanelTabla();
		pat_responsable.setPanelTabla(tab_responsable);
		
		
		//tabla responsable contrato
		tab_partida.setId("tab_partida");
		tab_partida.setHeader("CONTRATACION PARTIDA");
		tab_partida.setTabla("pre_contratacion_partida", "ide_prcoa", 3);
		tab_partida.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
		tab_partida.setTipoFormulario(true);
		tab_partida.getGrid().setColumns(2);
		tab_partida.dibujar();
		PanelTabla pat_partida=new PanelTabla();
		pat_partida.setPanelTabla(tab_partida);
		
		//tabla archivo
		
		tab_archivo.setId("tab_archivo");
		tab_archivo.setHeader("ARCHIVO");
		tab_archivo.setTabla("pre_archivo", "ide_prarc", 4);
		tab_archivo.setTipoFormulario(true);
		tab_archivo.getGrid().setColumns(4);
		tab_archivo.dibujar();
		PanelTabla pat_archivo=new PanelTabla();
		pat_archivo.setPanelTabla(tab_archivo);
		
		Division div_responsable =new Division();
		div_responsable.dividir3(pat_responsable, pat_partida, pat_archivo, "25%", "45%", "v");
		agregarComponente(div_responsable);
		
		Division div_contratacion =new Division();
		div_contratacion.dividir2(pat_contratacion, div_responsable, "50%", "h");
		agregarComponente(div_contratacion);
		
		
		
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_contratacion.guardar()) {
			if (tab_responsable.guardar()) {
				if (tab_partida.guardar()) {
					tab_archivo.guardar();
						guardarPantalla();
					
				}
				
			}
			
		}
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_contratacion() {
		return tab_contratacion;
	}

	public void setTab_contratacion(Tabla tab_contratacion) {
		this.tab_contratacion = tab_contratacion;
	}

	public Tabla getTab_responsable() {
		return tab_responsable;
	}

	public void setTab_responsable(Tabla tab_responsable) {
		this.tab_responsable = tab_responsable;
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
