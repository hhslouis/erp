package paq_presupuesto;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_tramite extends Pantalla   {
	
	private Tabla tab_tramite=new Tabla();
	private Tabla tab_poa_tramite=new Tabla();
	private Tabla tab_documento=new Tabla();
	private Tabla tab_archivo=new Tabla();
	
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioBodega ser_Bodega=(ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	
	public pre_tramite(){
		tab_tramite.setId("tab_tramite");
		tab_tramite.setHeader("TRAMITE");
		tab_tramite.setTabla("pre_tramite","ide_prtra", 1);
		tab_tramite.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_tramite.getColumna("ide_tepro").setCombo(ser_Bodega.getProveedor("true,false"));
		tab_tramite.getColumna("ide_copag").setCombo("cont_parametros_general", "ide_copag", "detalle_copag", "");
		tab_tramite.getColumna("ide_geani").setCombo("gen_anio", "ide_geani", "detalle_geani", "");
		tab_tramite.setTipoFormulario(true);
		tab_tramite.getGrid().setColumns(4);
		
		tab_tramite.agregarRelacion(tab_poa_tramite);
		tab_tramite.agregarRelacion(tab_archivo);
		tab_tramite.agregarRelacion(tab_documento);
		tab_tramite.dibujar();
		PanelTabla pat_tramite=new PanelTabla();
		pat_tramite.setPanelTabla(tab_tramite);
		
		///poa tramite
		tab_poa_tramite.setId("tab_poa_tramite");
		tab_poa_tramite.setHeader("POA TRAMITE");
		tab_poa_tramite.setIdCompleto("tab_tabulador:tab_poa_tramite");
		tab_poa_tramite.setTabla("pre_poa_tramite", "ide_prpot", 2);
		tab_poa_tramite.dibujar();
		PanelTabla pat_panel2=new PanelTabla();
		pat_panel2.setPanelTabla(tab_poa_tramite);
		
		//// documento habilitante
		tab_documento.setId("tab_documento");
		tab_documento.setHeader("DOCUMENTO HABILITANTE");
		tab_documento.setIdCompleto("tab_tabulador:tab_documento");
		tab_documento.setTabla("pre_documento_habilitante", "ide_prdoh", 3);
		tab_documento.dibujar();
		PanelTabla pat_panel3=new PanelTabla();
		pat_panel3.setPanelTabla(tab_documento);
		
		/// ARCHIVO
		tab_archivo.setId("tab_archivo");
		tab_archivo.setHeader("ARCHIVOS ANEXOS");
		tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
		tab_archivo.setTipoFormulario(true);
		tab_archivo.setTabla("pre_archivo","ide_prarc",4);
		tab_archivo.getColumna("foto_prarc").setUpload("presupuesto");
		TablaGenerica  tab_generica=ser_contabilidad.getTablaVigente("pre_archivo");
		for(int i=0;i<tab_generica.getTotalFilas();i++){
		//muestra los ides q quiere mostras.
		if(!tab_generica.getValor(i, "column_name").equals("ide_prtra")){	
		tab_archivo.getColumna(tab_generica.getValor(i, "column_name")).setVisible(false);	
		}				
		}
		tab_archivo.setCondicion("ide_prtra!=null");
		tab_archivo.dibujar();
		PanelTabla pat_panel4= new PanelTabla();
		pat_panel4.setPanelTabla(tab_archivo);

		
		
		///// tabuladores
		Tabulador tab_tabulador=new Tabulador();
		tab_tabulador.setId("tab_tabulador");
		
		tab_tabulador.agregarTab("POA TRAMITE", pat_panel2);
		tab_tabulador.agregarTab("DOCUMENTO HABILITANTE", pat_panel3);
		tab_tabulador.agregarTab("ARCHIVOS ANEXOS", pat_panel4);


			
		
		Division div_tramite =new Division();
		div_tramite.dividir2(pat_tramite, tab_tabulador, "50%", "h");
		agregarComponente(div_tramite);
				
		
		
				
	}
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_tramite.guardar()){
			if (tab_poa_tramite.guardar()){
				if (tab_documento.guardar()){
					tab_archivo.guardar();
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


	public Tabla getTab_tramite() {
		return tab_tramite;
	}


	public void setTab_tramite(Tabla tab_tramite) {
		this.tab_tramite = tab_tramite;
	}


	public Tabla getTab_poa_tramite() {
		return tab_poa_tramite;
	}


	public void setTab_poa_tramite(Tabla tab_poa_tramite) {
		this.tab_poa_tramite = tab_poa_tramite;
	}


	public Tabla getTab_documento() {
		return tab_documento;
	}


	public void setTab_documento(Tabla tab_documento) {
		this.tab_documento = tab_documento;
	}


	public Tabla getTab_archivo() {
		return tab_archivo;
	}


	public void setTab_archivo(Tabla tab_archivo) {
		this.tab_archivo = tab_archivo;
	}

}
