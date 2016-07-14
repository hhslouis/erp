package paq_general;

import javax.ejb.EJB;

import paq_general.ejb.ServicioGeneral;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Check;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

public class pre_ejecucion_presupuestaria_detalle extends Pantalla{
	
	private Tabla tab_ejecucionP = new Tabla();
	private Calendario cal_fecha_final = new Calendario();
	private Check che_grupo=new Check();
	private boolean grupos=false;
	
	@EJB
	private ServicioGeneral ser_general = (ServicioGeneral ) utilitario.instanciarEJB(ServicioGeneral.class);

	public pre_ejecucion_presupuestaria_detalle(){

		bar_botones.limpiar();

		bar_botones.agregarComponente(new Etiqueta("Fecha Corte :"));
		//cal_fecha_final.setFechaActual();
		cal_fecha_final.setValue(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		bar_botones.agregarComponente(cal_fecha_final);
		
		che_grupo.setId("che_grupo");
		che_grupo.setMetodoChange("grupos");
		Etiqueta eti_grupo=new Etiqueta("GRUPOS");
		bar_botones.agregarComponente(eti_grupo);
		bar_botones.agregarComponente(che_grupo);
		
		Boton bot_actualiza = new Boton();
		bot_actualiza.setId("bot_actualiza");
		bot_actualiza.setTitle("ACTUALIZAR");
		bot_actualiza.setValue("ACTUALIZAR");
		bot_actualiza.setMetodo("cargaEjecucion");
		bar_botones.agregarBoton(bot_actualiza);
    			
		tab_ejecucionP.setId("tab_ejecucionP");
		tab_ejecucionP.setSql(ser_general.getEjecucionPresupuestaria_detallado("1900-01-01","1900-01-01",grupos));
		tab_ejecucionP.getColumna("ide_prcla").setVisible(false);
		tab_ejecucionP.getColumna("fecha_inicial").setLongitud(15);
		tab_ejecucionP.getColumna("fecha_corte").setLongitud(15);
		tab_ejecucionP.getColumna("codigo_subactividad").setFiltro(true);
		tab_ejecucionP.getColumna("codigo_clasificador_prcla").setFiltro(true);
		tab_ejecucionP.getColumna("nro_certificacion_prcer").setFiltro(true);
		tab_ejecucionP.getColumna("nro_compromiso").setFiltro(true);
		tab_ejecucionP.getColumna("nro_comprobante").setFiltro(true);
		tab_ejecucionP.getColumna("fecha_corte").setLongitud(15);
		tab_ejecucionP.setColumnaSuma("inicial,reforma,codificado,certificado,compromiso,devengado");
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
		
		tab_ejecucionP.setSql(ser_general.getEjecucionPresupuestaria_detallado(fecha_inicial,fecha_final,grupos));
		tab_ejecucionP.ejecutarSql();
		utilitario.addUpdate("tab_ejecucionP");
	}
	
	public void limpiar(){
		
		tab_ejecucionP.limpiar();
		
		tab_ejecucionP.setSql(ser_general.getEjecucionPresupuestaria_detallado("1900-01-01","1900-01-01",grupos));
		tab_ejecucionP.ejecutarSql();
		utilitario.addUpdate("tab_ejecucionP");
	}

	
	public void grupos(){
		if(che_grupo.getValue().toString().equalsIgnoreCase("true")){
			grupos=true;
		}
		else{
			grupos=false;
		}

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
