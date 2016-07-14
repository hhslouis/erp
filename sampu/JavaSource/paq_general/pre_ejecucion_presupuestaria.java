package paq_general;

import javax.ejb.EJB;

import paq_general.ejb.ServicioGeneral;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

public class pre_ejecucion_presupuestaria extends Pantalla{
	
	private Tabla tab_ejecucionP = new Tabla();
	private Calendario cal_fecha_final = new Calendario();
	
	@EJB
	private ServicioGeneral ser_general = (ServicioGeneral ) utilitario.instanciarEJB(ServicioGeneral.class);

	public pre_ejecucion_presupuestaria(){

		bar_botones.limpiar();

		bar_botones.agregarComponente(new Etiqueta("Fecha Corte :"));
		cal_fecha_final.setValue(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		bar_botones.agregarComponente(cal_fecha_final);
		
		Boton bot_actualiza = new Boton();
		bot_actualiza.setId("bot_actualiza");
		bot_actualiza.setTitle("ACTUALIZAR");
		bot_actualiza.setValue("ACTUALIZAR");
		bot_actualiza.setMetodo("cargaEjecucion");
		bar_botones.agregarBoton(bot_actualiza);
    			
		tab_ejecucionP.setId("tab_ejecucionP");
		tab_ejecucionP.setSql(ser_general.getEjecucionPresupuestaria("1900-01-01","1900-01-01","0","",""));
		tab_ejecucionP.getColumna("ide_prpoa").setVisible(false);
		tab_ejecucionP.getColumna("periodo").setNombreVisual("PERIODO");
		tab_ejecucionP.getColumna("detalle_geani").setNombreVisual("AÑO");
		tab_ejecucionP.getColumna("detalle_geani").setLongitud(15);
		tab_ejecucionP.getColumna("detalle_programa").setNombreVisual("PROGRAMA");
		tab_ejecucionP.getColumna("cod_pry").setNombreVisual("COD. PROYECTO");
		tab_ejecucionP.getColumna("detalle_proyecto").setNombreVisual("PROYECTO");
		tab_ejecucionP.getColumna("cod_prod").setNombreVisual("COD. PRODUCTO");
		tab_ejecucionP.getColumna("detalle_producto").setNombreVisual("PRODUCTO");
		tab_ejecucionP.getColumna("codigo_fuente_prfuf").setNombreVisual("CODIGO FUENTE");
		tab_ejecucionP.getColumna("codigo_clasificador_prcla").setNombreVisual("PARTIDA");
		tab_ejecucionP.getColumna("presupuesto_inicial_prpoa").setNombreVisual("ASIGNACION INICIAL");
		tab_ejecucionP.getColumna("reforma_prpoa").setNombreVisual("REFORMAS");
		tab_ejecucionP.getColumna("presupuesto_codificado_prpoa").setNombreVisual("CODIFICADO");
		tab_ejecucionP.setColumnaSuma("presupuesto_inicial_prpoa,reforma_prpoa,presupuesto_codificado_prpoa,certificado,comprometido,devengado");

		tab_ejecucionP.setRows(30);
		tab_ejecucionP.setLectura(true);
		tab_ejecucionP.dibujar();
		
		PanelTabla pat_panel = new PanelTabla();
		pat_panel.setPanelTabla(tab_ejecucionP);
		
		Division div_tabla = new Division();
		div_tabla.dividir1(pat_panel);
		agregarComponente(div_tabla);

		
	}
	
	public void cargaEjecucion(){
		
		String fecha_inicial="2016-01-01";
		String fecha_final=cal_fecha_final.getFecha();
		
		tab_ejecucionP.setSql(ser_general.getEjecucionPresupuestaria(fecha_inicial,fecha_final,"0","",""));
		tab_ejecucionP.ejecutarSql();
		utilitario.addUpdate("tab_ejecucionP");
	}
	
	public void limpiar(){
		
		tab_ejecucionP.limpiar();
		
		tab_ejecucionP.setSql(ser_general.getEjecucionPresupuestaria("1900-01-01","1900-01-01","0","",""));
		tab_ejecucionP.ejecutarSql();
		utilitario.addUpdate("tab_ejecucionP");
	}

	

	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	public Tabla getTab_ejecucionP() {
		return tab_ejecucionP;
	}

	public void setTab_ejecucionP(Tabla tab_ejecucionP) {
		this.tab_ejecucionP = tab_ejecucionP;
	}


}
