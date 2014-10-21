/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_salud;

import org.primefaces.event.SelectEvent;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;

/**
 *
 * @author Diego
 */
public class pre_matriz_ocupacional extends Pantalla {

	private Tabla tab_tabla1 = new Tabla();
//	private Tabla tab_tabla2 = new Tabla();
	private  Tabla tab_riesgo_mecanico = new Tabla();
	private  Tabla tab_riesgo_biologico = new Tabla();
	private  Tabla tab_riesgo_quimico = new Tabla();
	private  Tabla tab_riesgo_fisico = new Tabla();
	private  Tabla tab_riesgo_ergonomico = new Tabla();
	private  Tabla tab_factores_psicosociales = new Tabla();
	private Tabulador tab_tabulador=new Tabulador();
	public pre_matriz_ocupacional() {
		
		tab_tabulador.setId("tab_tabulador");

		tab_tabla1.setId("tab_tabla1");
		tab_tabla1.setTabla("SAO_MATRIZ_RIESGO","IDE_SAMAR",1);
		tab_tabla1.getGrid().setColumns(4);
		tab_tabla1.setMostrarcampoSucursal(true);
		tab_tabla1.getColumna("IDE_GEEDP").setCombo("SELECT EPAR.IDE_GEEDP,EMP.DOCUMENTO_IDENTIDAD_GTEMP, " +
				"EMP.APELLIDO_PATERNO_GTEMP || ' ' ||  " +
				"EMP.APELLIDO_MATERNO_GTEMP || ' ' ||  " +
				"EMP.PRIMER_NOMBRE_GTEMP || ' ' ||  " +
				"EMP.SEGUNDO_NOMBRE_GTEMP AS NOMBRES_APELLIDOS,  " +
				"SUCU.NOM_SUCU, AREA.DETALLE_GEARE,  " +
				"DEPA.DETALLE_GEDEP  " +
				"FROM GEN_EMPLEADOS_DEPARTAMENTO_PAR EPAR  " +
				"LEFT JOIN GTH_EMPLEADO EMP ON EMP.IDE_GTEMP=EPAR.IDE_GTEMP " +
				"LEFT JOIN SIS_SUCURSAL SUCU ON SUCU.IDE_SUCU=EPAR.IDE_SUCU  " +
				"LEFT JOIN GEN_DEPARTAMENTO DEPA ON DEPA.IDE_GEDEP=EPAR.IDE_GEDEP  " +
				"LEFT JOIN GEN_AREA AREA ON AREA.IDE_GEARE=EPAR.IDE_GEARE  " +
				"WHERE EPAR.ACTIVO_GEEDP=true " +
				"ORDER BY NOMBRES_APELLIDOS ASC");
		tab_tabla1.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_tabla1.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_tabla1.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_tabla1.getColumna("IDE_SUCU").setCombo("SIS_SUCURSAL", "IDE_SUCU", "NOM_SUCU", "");
		tab_tabla1.getColumna("IDE_SUCU").setBuscarenCombo(false);
		tab_tabla1.getColumna("IDE_SUCU").setMetodoChange("cargarArea");
		tab_tabla1.getColumna("IDE_GEARE").setBuscarenCombo(false);   
		tab_tabla1.getColumna("IDE_GEARE").setCombo("SELECT AREA.IDE_GEARE,AREA.DETALLE_GEARE  " +
				"FROM GEN_DEPARTAMENTO_SUCURSAL DSUC " +
				"LEFT JOIN GEN_AREA AREA ON AREA.IDE_GEARE=DSUC.IDE_GEARE " +
				"GROUP BY AREA.IDE_GEARE,AREA.DETALLE_GEARE " +
				"ORDER BY AREA.DETALLE_GEARE");
		tab_tabla1.getColumna("IDE_GEARE").setMetodoChange("cargarDepartamentos");
		tab_tabla1.getColumna("IDE_GEDEP").setCombo("SELECT DISTINCT a.IDE_GEDEP,a.DETALLE_GEDEP FROM GEN_DEPARTAMENTO a, GEN_DEPARTAMENTO_SUCURSAL b WHERE a.IDE_GEDEP=b.IDE_GEDEP");
		tab_tabla1.getColumna("IDE_GEDEP").setBuscarenCombo(false);
		tab_tabla1.getColumna("IDE_GEDEP").setCombo("SELECT a.IDE_GEDEP,a.DETALLE_GEDEP FROM GEN_DEPARTAMENTO a, GEN_DEPARTAMENTO_SUCURSAL b WHERE a.IDE_GEDEP=b.IDE_GEDEP " );

		tab_tabla1.getColumna("IDE_GEBEN").setCombo("GEN_BENEFICIARIO", "IDE_GEBEN", "TITULAR_GEBEN", "");
		tab_tabla1.getColumna("ACTIVO_SAMAR").setCheck();
		tab_tabla1.getColumna("ACTIVO_SAMAR").setValorDefecto("true");
		tab_tabla1.setTipoFormulario(true);
		tab_tabla1.agregarRelacion(tab_riesgo_biologico);
		tab_tabla1.agregarRelacion(tab_riesgo_fisico);
		tab_tabla1.agregarRelacion(tab_riesgo_ergonomico);
		tab_tabla1.agregarRelacion(tab_riesgo_quimico);
		tab_tabla1.agregarRelacion(tab_riesgo_mecanico);
		tab_tabla1.agregarRelacion(tab_factores_psicosociales);
		tab_tabla1.dibujar();
		PanelTabla pat_panel1 = new PanelTabla();
		pat_panel1.setPanelTabla(tab_tabla1);

//		tab_tabla2.setId("tab_tabla2");
//		tab_tabla2.setIdCompleto("tab_tabulador:tab_tabla2");
//		tab_tabla2.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR", 2);
//		tab_tabla2.getColumna("IDE_SACLR").setCombo("SAO_CLASIFICACION_RIESGOS","IDE_SACLR", "DETALLE_SACLR", "");
//		tab_tabla2.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
//		tab_tabla2.getColumna("IDE_SAFAC").setCombo("SAO_FACTOR_CONTROL","IDE_SAFAC", "FACTOR_CALIF_SAFAC", "");
//		tab_tabla2.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
//		tab_tabla2.getColumna("SAO_IDE_SAGRP").setCombo(tab_tabla2.getColumna("IDE_SAGRP").getListaCombo());
//		tab_tabla2.getColumna("SAO_IDE_SAGRP2").setCombo(tab_tabla2.getColumna("IDE_SAGRP").getListaCombo());
//		tab_tabla2.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
//		tab_tabla2.getColumna("IDE_GEEDP").setAutoCompletar();
//		tab_tabla2.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
//		tab_tabla2.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
//		tab_tabla2.getColumna("ACTIVO_SADMR").setCheck();
//		tab_tabla2.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
//		tab_tabla2.getColumna("ACTIVO_SADMR").setValorDefecto("true");
//
//
//		tab_tabla2.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
//		//	tab_tabla2.getGrid().setColumns(4);
//		//	tab_tabla2.setTipoFormulario(true);
//		tab_tabla2.dibujar();
//		PanelTabla pat_panel2 = new PanelTabla();
//		pat_panel2.setPanelTabla(tab_tabla2);

		tab_riesgo_biologico.setId("tab_riesgo_biologico");
		tab_riesgo_biologico.setIdCompleto("tab_tabulador:tab_riesgo_biologico");
		tab_riesgo_biologico.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR" , 3);
		tab_riesgo_biologico.getGrid().setColumns(4);
		tab_riesgo_biologico.setTipoFormulario(true);
		tab_riesgo_biologico.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
		tab_riesgo_biologico.getColumna("IDE_SAGRP").setAutoCompletar();
		tab_riesgo_biologico.getColumna("IDE_SAFAC").setCombo("SELECT IDE_SAFAC,factor_calif_safac FROM SAO_FACTOR_CONTROL WHERE ide_saclr IN( " +
				"SELECT ide_saclr FROM sao_clasificacion_riesgos WHERE sao_ide_saclr="+utilitario.getVariable("p_riesgos_biologico")+")");
		tab_riesgo_biologico.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
		tab_riesgo_biologico .getColumna("SAO_IDE_SAGRP").setCombo(tab_riesgo_biologico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_biologico.getColumna("SAO_IDE_SAGRP2").setCombo(tab_riesgo_biologico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_biologico.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_biologico.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_riesgo_biologico.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_biologico.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_riesgo_biologico.getColumna("IDE_SAGRP").setVisible(false);
		tab_riesgo_biologico.getColumna("SAO_IDE_SAGRP").setVisible(false);
		tab_riesgo_biologico.getColumna("SAO_IDE_SAGRP2").setVisible(false);
		System.out.println("biologivo"+utilitario.getVariable("p_riesgos_biologico"));
		tab_riesgo_biologico.getColumna("IDE_SACLR").setValorDefecto(utilitario.getVariable("p_riesgos_biologico"));
		tab_riesgo_biologico.getColumna("IDE_SACLR").setLectura(true);
		tab_riesgo_biologico.getColumna("IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where IDE_SACLR="+utilitario.getVariable("p_riesgos_biologico"));
		tab_riesgo_biologico.getColumna("IDE_SACLR").setAutoCompletar();
		//tab_riesgo_biologico.getColumna("SAO_IDE_SACLR").setLectura(true);
		tab_riesgo_biologico.getColumna("SAO_IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where SAO_IDE_SACLR="+utilitario.getVariable("p_riesgos_biologico"));
		tab_riesgo_biologico.getColumna("SAO_IDE_SACLR").setAutoCompletar();
		
		tab_riesgo_biologico.setCondicion("IDE_SACLR="+utilitario.getVariable("p_riesgos_biologico"));
		tab_riesgo_biologico.getColumna("IDE_SAMAR").setVisible(false);
		tab_riesgo_biologico.getColumna("ACTIVO_SADMR").setCheck();
		tab_riesgo_biologico.getColumna("ACTIVO_SADMR").setValorDefecto("true");
		tab_riesgo_biologico.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
		tab_riesgo_biologico.getColumna("ANEXO_SADMR").setUpload("archivos");
		tab_riesgo_biologico.getColumna("CUMPLE_LEGAL_SADMAR").setValorDefecto("false");
		tab_riesgo_biologico.dibujar();
		PanelTabla pat_panel3 = new PanelTabla();
		pat_panel3.setPanelTabla(tab_riesgo_biologico);


		tab_riesgo_fisico.setId("tab_riesgo_fisico");
		tab_riesgo_fisico.setIdCompleto("tab_tabulador:tab_riesgo_fisico");
	
		tab_riesgo_fisico.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR" , 4);
		tab_riesgo_fisico.getGrid().setColumns(4);
		tab_riesgo_fisico.setTipoFormulario(true);
		tab_riesgo_fisico.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
		tab_riesgo_fisico.getColumna("IDE_SAGRP").setAutoCompletar();
		tab_riesgo_fisico.getColumna("IDE_SAFAC").setCombo("SELECT IDE_SAFAC,factor_calif_safac FROM SAO_FACTOR_CONTROL WHERE ide_saclr IN( " +
				"SELECT ide_saclr FROM sao_clasificacion_riesgos WHERE sao_ide_saclr="+utilitario.getVariable("p_riesgos_fisico")+")");
		tab_riesgo_fisico.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
		tab_riesgo_fisico.getColumna("SAO_IDE_SAGRP").setCombo(tab_riesgo_fisico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_fisico.getColumna("SAO_IDE_SAGRP2").setCombo(tab_riesgo_fisico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_fisico.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_fisico.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_riesgo_fisico.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_fisico.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_riesgo_fisico.getColumna("IDE_SAGRP").setVisible(false);
		tab_riesgo_fisico.getColumna("SAO_IDE_SAGRP").setVisible(false);
		tab_riesgo_fisico.getColumna("SAO_IDE_SAGRP2").setVisible(false);
		
	
		tab_riesgo_fisico.getColumna("IDE_SACLR").setValorDefecto(utilitario.getVariable("p_riesgos_fisico"));
		tab_riesgo_fisico.getColumna("IDE_SACLR").setLectura(true);
		tab_riesgo_fisico.getColumna("IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where IDE_SACLR="+utilitario.getVariable("p_riesgos_fisico"));
		tab_riesgo_fisico.getColumna("IDE_SACLR").setAutoCompletar();
		tab_riesgo_fisico.setCondicion("IDE_SACLR="+utilitario.getVariable("p_riesgos_fisico"));
		tab_riesgo_fisico.getColumna("IDE_SAMAR").setVisible(false);
		tab_riesgo_fisico.getColumna("SAO_IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where SAO_IDE_SACLR="+utilitario.getVariable("p_riesgos_fisico"));
		tab_riesgo_fisico.getColumna("SAO_IDE_SACLR").setAutoCompletar();
		tab_riesgo_fisico.getColumna("ACTIVO_SADMR").setCheck();
		tab_riesgo_fisico.getColumna("ACTIVO_SADMR").setValorDefecto("true");
		tab_riesgo_fisico.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
		tab_riesgo_fisico.getColumna("CUMPLE_LEGAL_SADMAR").setValorDefecto("false");
		tab_riesgo_fisico.getColumna("ANEXO_SADMR").setUpload("archivos");
		tab_riesgo_fisico.dibujar();
		PanelTabla pat_panel4 = new PanelTabla();
		pat_panel4.setPanelTabla(tab_riesgo_fisico);

		tab_riesgo_ergonomico.setId("tab_riesgo_ergonomico");
		tab_riesgo_ergonomico.setIdCompleto("tab_tabulador:tab_riesgo_ergonomico");
		tab_riesgo_ergonomico.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR", 5);
		tab_riesgo_ergonomico.getGrid().setColumns(4);
		tab_riesgo_ergonomico.setTipoFormulario(true);	
		tab_riesgo_ergonomico.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
		tab_riesgo_ergonomico.getColumna("IDE_SAGRP").setAutoCompletar();
		tab_riesgo_ergonomico.getColumna("IDE_SAFAC").setCombo("SELECT IDE_SAFAC,factor_calif_safac FROM SAO_FACTOR_CONTROL WHERE ide_saclr IN( " +
				"SELECT ide_saclr FROM sao_clasificacion_riesgos WHERE sao_ide_saclr="+utilitario.getVariable("p_riesgos_ergonomico")+")");
		tab_riesgo_ergonomico.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
		tab_riesgo_ergonomico.getColumna("SAO_IDE_SAGRP").setCombo(tab_riesgo_ergonomico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_ergonomico.getColumna("SAO_IDE_SAGRP2").setCombo(tab_riesgo_ergonomico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_ergonomico.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_ergonomico.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_riesgo_ergonomico.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_ergonomico.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_riesgo_ergonomico.getColumna("IDE_SAGRP").setVisible(false);
		tab_riesgo_ergonomico.getColumna("SAO_IDE_SAGRP").setVisible(false);
		tab_riesgo_ergonomico.getColumna("SAO_IDE_SAGRP2").setVisible(false);
		tab_riesgo_ergonomico.getColumna("IDE_SACLR").setValorDefecto(utilitario.getVariable("p_riesgos_ergonomico"));
		tab_riesgo_ergonomico.getColumna("IDE_SACLR").setLectura(true);
		tab_riesgo_ergonomico.getColumna("IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where IDE_SACLR="+utilitario.getVariable("p_riesgos_ergonomico"));
		tab_riesgo_ergonomico.getColumna("IDE_SACLR").setAutoCompletar();
		tab_riesgo_ergonomico.setCondicion("IDE_SACLR="+utilitario.getVariable("p_riesgos_ergonomico"));
		tab_riesgo_ergonomico.getColumna("IDE_SAMAR").setVisible(false);
		tab_riesgo_ergonomico.getColumna("SAO_IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where SAO_IDE_SACLR="+utilitario.getVariable("p_riesgos_ergonomico"));
		tab_riesgo_ergonomico.getColumna("SAO_IDE_SACLR").setAutoCompletar();
		tab_riesgo_ergonomico.getColumna("ACTIVO_SADMR").setCheck();
		tab_riesgo_ergonomico.getColumna("ACTIVO_SADMR").setValorDefecto("true");
		tab_riesgo_ergonomico.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
		tab_riesgo_ergonomico.getColumna("CUMPLE_LEGAL_SADMAR").setValorDefecto("false");
		tab_riesgo_ergonomico.getColumna("ANEXO_SADMR").setUpload("archivos");
		tab_riesgo_ergonomico.dibujar();
		PanelTabla pat_panel5 = new PanelTabla();
		pat_panel5.setPanelTabla(tab_riesgo_ergonomico);

		tab_riesgo_mecanico.setId("tab_riesgo_mecanico");
		tab_riesgo_mecanico.setIdCompleto("tab_tabulador:tab_riesgo_mecanico");
		tab_riesgo_mecanico.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR", 6);
		tab_riesgo_mecanico.getGrid().setColumns(4);
		tab_riesgo_mecanico.setTipoFormulario(true);
		tab_riesgo_mecanico.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
		tab_riesgo_mecanico.getColumna("IDE_SAGRP").setAutoCompletar();
		tab_riesgo_mecanico.getColumna("IDE_SAFAC").setCombo("SELECT IDE_SAFAC,factor_calif_safac FROM SAO_FACTOR_CONTROL WHERE ide_saclr IN( " +
				"SELECT ide_saclr FROM sao_clasificacion_riesgos WHERE sao_ide_saclr="+utilitario.getVariable("p_riesgos_mecanico")+")");
		tab_riesgo_mecanico.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
		tab_riesgo_mecanico.getColumna("SAO_IDE_SAGRP").setCombo(tab_riesgo_mecanico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_mecanico.getColumna("SAO_IDE_SAGRP2").setCombo(tab_riesgo_mecanico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_mecanico.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_mecanico.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_riesgo_mecanico.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_mecanico.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_riesgo_mecanico.getColumna("IDE_SAGRP").setVisible(false);
		tab_riesgo_mecanico.getColumna("SAO_IDE_SAGRP").setVisible(false);
		tab_riesgo_mecanico.getColumna("SAO_IDE_SAGRP2").setVisible(false);
		tab_riesgo_mecanico.getColumna("IDE_SACLR").setValorDefecto(utilitario.getVariable("p_riesgos_mecanico"));
		tab_riesgo_mecanico.getColumna("IDE_SACLR").setLectura(true);
		tab_riesgo_mecanico.getColumna("IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where IDE_SACLR="+utilitario.getVariable("p_riesgos_mecanico"));
		tab_riesgo_mecanico.getColumna("IDE_SACLR").setAutoCompletar();
		tab_riesgo_mecanico.setCondicion("IDE_SACLR="+utilitario.getVariable("p_riesgos_mecanico"));
		tab_riesgo_mecanico.getColumna("IDE_SAMAR").setVisible(false);
		tab_riesgo_mecanico.getColumna("SAO_IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where SAO_IDE_SACLR="+utilitario.getVariable("p_riesgos_mecanico"));
		tab_riesgo_mecanico.getColumna("SAO_IDE_SACLR").setAutoCompletar();
		tab_riesgo_mecanico.getColumna("ACTIVO_SADMR").setCheck();
		tab_riesgo_mecanico.getColumna("ACTIVO_SADMR").setValorDefecto("true");
		tab_riesgo_mecanico.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
		tab_riesgo_mecanico.getColumna("ANEXO_SADMR").setUpload("archivos");
		tab_riesgo_mecanico.getColumna("CUMPLE_LEGAL_SADMAR").setValorDefecto("false");
		tab_riesgo_mecanico.dibujar();
		PanelTabla pat_panel6 = new PanelTabla();
		pat_panel6.setPanelTabla(tab_riesgo_mecanico);


		tab_riesgo_quimico.setId("tab_riesgo_quimico");
		tab_riesgo_quimico.setIdCompleto("tab_tabulador:tab_riesgo_quimico");
		tab_riesgo_quimico.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR", 6);
		tab_riesgo_quimico.getGrid().setColumns(4);
		tab_riesgo_quimico.setTipoFormulario(true);
		tab_riesgo_quimico.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
		tab_riesgo_quimico.getColumna("IDE_SAGRP").setAutoCompletar();
		tab_riesgo_quimico.getColumna("IDE_SAFAC").setCombo("SELECT IDE_SAFAC,factor_calif_safac FROM SAO_FACTOR_CONTROL WHERE ide_saclr IN( " +
				"SELECT ide_saclr FROM sao_clasificacion_riesgos WHERE sao_ide_saclr="+utilitario.getVariable("p_riesgos_quimico")+")");
		tab_riesgo_quimico.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
		tab_riesgo_quimico.getColumna("SAO_IDE_SAGRP").setCombo(tab_riesgo_quimico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_quimico.getColumna("SAO_IDE_SAGRP2").setCombo(tab_riesgo_quimico.getColumna("IDE_SAGRP").getListaCombo());
		tab_riesgo_quimico.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_riesgo_quimico.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_quimico.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_riesgo_quimico.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_riesgo_quimico.getColumna("IDE_SAGRP").setVisible(false);
		tab_riesgo_quimico.getColumna("SAO_IDE_SAGRP").setVisible(false);
		tab_riesgo_quimico.getColumna("SAO_IDE_SAGRP2").setVisible(false);
		tab_riesgo_quimico.getColumna("IDE_SACLR").setValorDefecto(utilitario.getVariable("p_riesgos_quimico"));
		tab_riesgo_quimico.getColumna("IDE_SACLR").setLectura(true);
		tab_riesgo_quimico.getColumna("IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where IDE_SACLR="+utilitario.getVariable("p_riesgos_quimico"));
		tab_riesgo_quimico.getColumna("IDE_SACLR").setAutoCompletar();
		tab_riesgo_quimico.setCondicion("IDE_SACLR="+utilitario.getVariable("p_riesgos_quimico"));
		tab_riesgo_quimico.getColumna("IDE_SAMAR").setVisible(false);
		tab_riesgo_quimico.getColumna("SAO_IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where SAO_IDE_SACLR="+utilitario.getVariable("p_riesgos_quimico"));
		tab_riesgo_quimico.getColumna("SAO_IDE_SACLR").setAutoCompletar();
		tab_riesgo_quimico.getColumna("ACTIVO_SADMR").setCheck();
		tab_riesgo_quimico.getColumna("ACTIVO_SADMR").setValorDefecto("true");
		tab_riesgo_quimico.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
		tab_riesgo_quimico.getColumna("CUMPLE_LEGAL_SADMAR").setValorDefecto("false");
		tab_riesgo_quimico.getColumna("ANEXO_SADMR").setUpload("archivos");
		tab_riesgo_quimico.dibujar();
		PanelTabla pat_panel7 = new PanelTabla();
		pat_panel7.setPanelTabla(tab_riesgo_quimico);

		tab_factores_psicosociales.setId("tab_factores_psicosociales");
		tab_factores_psicosociales.setIdCompleto("tab_tabulador:tab_factores_psicosociales");
		tab_factores_psicosociales.setTabla("SAO_DETALLE_MATRIZ_RIESGO", "IDE_SADMR", 8);
		tab_factores_psicosociales.getGrid().setColumns(4);
		tab_factores_psicosociales.getColumna("IDE_SAGRP").setCombo("SAO_GRADO_PELIGRO","IDE_SAGRP", "DETALLE_SAGRP", "");
		tab_factores_psicosociales.getColumna("IDE_SAGRP").setAutoCompletar();
		tab_factores_psicosociales.setTipoFormulario(false);
		tab_factores_psicosociales.getColumna("IDE_SAFAC").setCombo("SELECT IDE_SAFAC,factor_calif_safac FROM SAO_FACTOR_CONTROL WHERE ide_saclr IN( " +
				"SELECT ide_saclr FROM sao_clasificacion_riesgos WHERE sao_ide_saclr="+utilitario.getVariable("p_factor_psicosociales")+")");
		tab_factores_psicosociales.getColumna("IDE_SAINW").setCombo("SAO_INDICE_WFINE","IDE_SAINW", "INTERPRETACION_SAINW", "");
		tab_factores_psicosociales.getColumna("SAO_IDE_SAGRP").setCombo(tab_factores_psicosociales.getColumna("IDE_SAGRP").getListaCombo());
		tab_factores_psicosociales.getColumna("SAO_IDE_SAGRP2").setCombo(tab_factores_psicosociales.getColumna("IDE_SAGRP").getListaCombo());
		tab_factores_psicosociales.getColumna("IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_factores_psicosociales.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_factores_psicosociales.getColumna("GEN_IDE_GEEDP").setCombo(tab_tabla1.getColumna("IDE_GEEDP").getListaCombo());
		tab_factores_psicosociales.getColumna("GEN_IDE_GEEDP").setAutoCompletar();
		tab_factores_psicosociales.getColumna("IDE_SAGRP").setVisible(false);
		tab_factores_psicosociales.getColumna("SAO_IDE_SAGRP").setVisible(false);
		tab_factores_psicosociales.getColumna("SAO_IDE_SAGRP2").setVisible(false);
		tab_factores_psicosociales.getColumna("IDE_SACLR").setValorDefecto(utilitario.getVariable("p_factor_psicosociales"));
		tab_factores_psicosociales.getColumna("IDE_SACLR").setLectura(true);
		tab_factores_psicosociales.getColumna("IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where IDE_SACLR="+utilitario.getVariable("p_factor_psicosociales"));
		tab_factores_psicosociales.getColumna("IDE_SACLR").setAutoCompletar();
		tab_factores_psicosociales.setCondicion("IDE_SACLR="+utilitario.getVariable("p_factor_psicosociales"));
		tab_factores_psicosociales.getColumna("IDE_SAMAR").setVisible(false);
		tab_factores_psicosociales.getColumna("SAO_IDE_SACLR").setCombo("select IDE_SACLR,DETALLE_SACLR from SAO_CLASIFICACION_RIESGOS where SAO_IDE_SACLR="+utilitario.getVariable("p_factor_psicosociales"));
		tab_factores_psicosociales.getColumna("SAO_IDE_SACLR").setAutoCompletar();
		tab_factores_psicosociales.getColumna("ACTIVO_SADMR").setCheck();
		tab_factores_psicosociales.getColumna("ACTIVO_SADMR").setValorDefecto("true");
		tab_factores_psicosociales.getColumna("CUMPLE_LEGAL_SADMAR").setCheck();
		tab_factores_psicosociales.getColumna("ANEXO_SADMR").setUpload("archivos");
		tab_factores_psicosociales.getColumna("CUMPLE_LEGAL_SADMAR").setValorDefecto("false");
		tab_factores_psicosociales.dibujar();
		PanelTabla pat_panel8 = new PanelTabla();
		pat_panel8.setPanelTabla(tab_factores_psicosociales);

		//tab_tabulador.agregarTab("DETALLE MATRIZ RIESGO", pat_panel2);
		tab_tabulador.agregarTab("RIESGO BIOLOGICO", pat_panel3);
		tab_tabulador.agregarTab("RIESGO FISICO", pat_panel4);
		tab_tabulador.agregarTab("RIESGO ERGONOMICO", pat_panel5);
		tab_tabulador.agregarTab("RIESGO MECANICO", pat_panel6);
		tab_tabulador.agregarTab("RIESGO QUIMICO", pat_panel7);
		tab_tabulador.agregarTab("FACTORES PSICOSOCIALES", pat_panel8);


		Division div_division = new Division();
		div_division.setId("div_division");

		div_division.dividir2(pat_panel1,tab_tabulador, "50%", "H");
		agregarComponente(div_division);
	}



	private void actualizarCombos() {

		tab_tabla1.getColumna("IDE_GEARE").setCombo("SELECT AREA.IDE_GEARE,AREA.DETALLE_GEARE  " +
				"FROM GEN_DEPARTAMENTO_SUCURSAL DSUC " +
				"LEFT JOIN GEN_AREA AREA ON AREA.IDE_GEARE=DSUC.IDE_GEARE " +
				"WHERE DSUC.IDE_SUCU="+tab_tabla1.getValor("IDE_SUCU")+" " +
				"GROUP BY AREA.IDE_GEARE,AREA.DETALLE_GEARE " +
				"ORDER BY AREA.DETALLE_GEARE");

		tab_tabla1.getColumna("IDE_GEDEP").setCombo("SELECT a.IDE_GEDEP,a.DETALLE_GEDEP FROM GEN_DEPARTAMENTO a, GEN_DEPARTAMENTO_SUCURSAL b WHERE a.IDE_GEDEP=b.IDE_GEDEP AND b.IDE_GEARE="+ tab_tabla1.getValor("IDE_GEARE")+ " AND IDE_SUCU="+ tab_tabla1.getValor("IDE_SUCU"));

		tab_tabla1.actualizarCombosFormulario();
	}    


	public void cargarArea() {

		tab_tabla1.getColumna("IDE_GEARE").setCombo("SELECT b.IDE_GEARE,b.DETALLE_GEARE FROM GEN_DEPARTAMENTO_SUCURSAL a " +
				"inner join GEN_AREA b on a.IDE_GEARE=b.IDE_GEARE " +
				"where a.IDE_SUCU="+ tab_tabla1.getValor("IDE_SUCU")+" "+
				"GROUP BY b.IDE_GEARE,b.DETALLE_GEARE " +
				"ORDER BY b.DETALLE_GEARE" );
		tab_tabla1.setValor("IDE_GEDEP", "");
		utilitario.addUpdateTabla(tab_tabla1, "IDE_GEARE","");



	}

	public void cargarDepartamentos() {

		if (tab_tabla1.getTotalFilas()>0){
			tab_tabla1.getColumna("IDE_GEDEP").setCombo("SELECT a.IDE_GEDEP,a.DETALLE_GEDEP FROM GEN_DEPARTAMENTO a, GEN_DEPARTAMENTO_SUCURSAL b WHERE a.IDE_GEDEP=b.IDE_GEDEP AND b.IDE_GEARE="+ tab_tabla1.getValor("IDE_GEARE")+ " AND IDE_SUCU="+ tab_tabla1.getValor("IDE_SUCU"));
			utilitario.addUpdateTabla(tab_tabla1, "IDE_GEARE,IDE_GEDEP", "");
		}
	}





	@Override
	public void buscar() {
		// TODO Auto-generated method stub
		super.buscar();
	}



	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		super.actualizar();
		actualizarCombos();
	}



	@Override
	public void insertar() {

		if(tab_tabla1.isFocus()){
			tab_tabla1.getColumna("IDE_GEARE").setCombo("SELECT a.IDE_GEARE,a.DETALLE_GEARE FROM GEN_AREA a, GEN_DEPARTAMENTO_SUCURSAL b WHERE a.IDE_GEARE=b.IDE_GEARE AND IDE_SUCU=-1");
			tab_tabla1.getColumna("IDE_GEDEP").setCombo("SELECT a.IDE_GEDEP,a.DETALLE_GEDEP FROM GEN_DEPARTAMENTO a, GEN_DEPARTAMENTO_SUCURSAL b WHERE a.IDE_GEDEP=b.IDE_GEDEP AND b.IDE_GEARE=-1 AND IDE_SUCU=-1");
			tab_tabla1.insertar();
			tab_tabla1.setValor("IDE_SUCU", "");




		}
		if(tab_riesgo_biologico.isFocus()){
			
			tab_riesgo_biologico.insertar();
			tab_riesgo_biologico.getColumna("IDE_SACLR").setCombo("select * FROM SAO_DETALLE_MATRIZ_RIESGO WHERE IDE_SACLR="+utilitario.getVariable("p_riesgos_biologico"));
		}
		if(tab_riesgo_fisico.isFocus()){
			tab_riesgo_fisico.insertar();
			tab_riesgo_fisico.getColumna("IDE_SACLR").setCombo("select * FROM SAO_DETALLE_MATRIZ_RIESGO WHERE IDE_SACLR="+utilitario.getVariable("p_riesgos_fisico"));
		}
		if(tab_riesgo_mecanico.isFocus()){
			tab_riesgo_mecanico.insertar();
			tab_riesgo_mecanico.getColumna("IDE_SACLR").setCombo("select * FROM SAO_DETALLE_MATRIZ_RIESGO WHERE IDE_SACLR="+utilitario.getVariable("p_riesgos_mecanico"));
		}
		if(tab_riesgo_quimico.isFocus()){
			tab_riesgo_quimico.insertar();
			tab_riesgo_quimico.getColumna("IDE_SACLR").setCombo("select * FROM SAO_DETALLE_MATRIZ_RIESGO WHERE IDE_SACLR="+utilitario.getVariable("p_riesgos_quimico"));
		}
		if(tab_riesgo_ergonomico.isFocus()){
			tab_riesgo_ergonomico.insertar();
			tab_riesgo_ergonomico.getColumna("IDE_SACLR").setCombo("select * FROM SAO_DETALLE_MATRIZ_RIESGO WHERE IDE_SACLR="+utilitario.getVariable("p_riesgos_ergonomico"));
		}
		if(tab_factores_psicosociales.isFocus()){
			tab_factores_psicosociales.insertar();
			tab_factores_psicosociales.getColumna("IDE_SACLR").setCombo("select * FROM SAO_DETALLE_MATRIZ_RIESGO WHERE IDE_SACLR="+utilitario.getVariable("p_factor_psicosociales"));
		}
	}

	@Override
	public void guardar() {

		if (tab_tabla1.guardar()) {
			if (tab_riesgo_biologico.guardar()) {
				if (tab_riesgo_fisico.guardar()) {
					if (tab_riesgo_mecanico.guardar()) {
						if (tab_riesgo_ergonomico.guardar()) {
							if (tab_riesgo_quimico.guardar()) {
								if (tab_factores_psicosociales.guardar()) {
									guardarPantalla();
								}
							}
						}
					}
				}
			}
		}

	}

			@Override
			public void inicio() {
				// TODO Auto-generated method stub
				super.inicio();
				actualizarCombos();
			}



			@Override
			public void fin() {
				// TODO Auto-generated method stub
				super.fin();
				actualizarCombos();
			}



			@Override
			public void siguiente() {
				// TODO Auto-generated method stub
				super.siguiente();
				actualizarCombos();
			}



			@Override
			public void atras() {
				// TODO Auto-generated method stub
				super.atras();
				actualizarCombos();
			}



			@Override
			public void eliminar() {
				utilitario.getTablaisFocus().eliminar();
				actualizarCombos();
			}

			public Tabla getTab_tabla1() {
				return tab_tabla1;
			}

			public void setTab_tabla1(Tabla tab_tabla1) {
				this.tab_tabla1 = tab_tabla1;
			}

//			public Tabla getTab_tabla2() {
//				return tab_tabla2;
//			}
//
//			public void setTab_tabla2(Tabla tab_tabla2) {
//				this.tab_tabla2 = tab_tabla2;
//			}



			public Tabla getTab_riesgo_mecanico() {
				return tab_riesgo_mecanico;
			}



			public void setTab_riesgo_mecanico(Tabla tab_riesgo_mecanico) {
				this.tab_riesgo_mecanico = tab_riesgo_mecanico;
			}



			public Tabla getTab_riesgo_biologico() {
				return tab_riesgo_biologico;
			}



			public void setTab_riesgo_biologico(Tabla tab_riesgo_biologico) {
				this.tab_riesgo_biologico = tab_riesgo_biologico;
			}



			public Tabla getTab_riesgo_fisico() {
				return tab_riesgo_fisico;
			}



			public void setTab_riesgo_fisico(Tabla tab_riesgo_fisico) {
				this.tab_riesgo_fisico = tab_riesgo_fisico;
			}



			public Tabla getTab_riesgo_ergonomico() {
				return tab_riesgo_ergonomico;
			}



			public void setTab_riesgo_ergonomico(Tabla tab_riesgo_ergonomico) {
				this.tab_riesgo_ergonomico = tab_riesgo_ergonomico;
			}



			public Tabla getTab_factores_psicosociales() {
				return tab_factores_psicosociales;
			}



			public void setTab_factores_psicosociales(Tabla tab_factores_psicosociales) {
				this.tab_factores_psicosociales = tab_factores_psicosociales;
			}



			public Tabla getTab_riesgo_quimico() {
				return tab_riesgo_quimico;
			}



			public void setTab_riesgo_quimico(Tabla tab_riesgo_quimico) {
				this.tab_riesgo_quimico = tab_riesgo_quimico;
			}


		}
