/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_asistencia;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

/**
 *
 * @author Diego
 */
public class pre_marcaciones extends Pantalla {


	private Tabla tab_tabla = new Tabla();
	private Calendario cal_fecha_inicial = new Calendario();
	private Calendario cal_fecha_final = new Calendario();
	public pre_marcaciones() {      
		bar_botones.limpiar();
		bar_botones.agregarComponente(new Etiqueta("Fecha Inicial :"));
		cal_fecha_inicial.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_inicial);

		bar_botones.agregarComponente(new Etiqueta("Fecha Final :"));
		cal_fecha_final.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_final);

		Boton bot_filtrar = new Boton();
		bot_filtrar.setValue("Actualizar");
		bot_filtrar.setMetodo("actualizarMarcaciones");
		bot_filtrar.setIcon("ui-icon-refresh");
		bar_botones.agregarBoton(bot_filtrar);

		tab_tabla.setId("tab_tabla");
		tab_tabla.setCampoPrimaria("IDE_COBIM");		
		tab_tabla.setSql("SELECT BIO.IDE_COBIM,BIO.EVENTO_RELOJ_COBIM,IDE_PERSONA_COBIM,(SELECT APELLIDO_PATERNO_GTEMP || ' ' || APELLIDO_MATERNO_GTEMP || ' ' || PRIMER_NOMBRE_GTEMP || ' ' || SEGUNDO_NOMBRE_GTEMP FROM GTH_EMPLEADO WHERE DOCUMENTO_IDENTIDAD_GTEMP=trim(IDE_PERSONA_COBIM)) as NOMBRES,DETALLE_COREL,TO_CHAR(FECHA_EVENTO_COBIM,'YYYY-MM-DD') as FECHAM,TO_CHAR(FECHA_EVENTO_COBIM,'hh24:mi:ss') AS HORAM FROM CON_BIOMETRICO_MARCACIONES BIO INNER JOIN CON_RELOJ RELOJ ON BIO.IDE_COREL=RELOJ.IDE_COREL  WHERE TO_CHAR(FECHA_EVENTO_COBIM,'YYYY-MM-DD') BETWEEN '"+cal_fecha_inicial.getFecha()+"' AND '"+cal_fecha_final.getFecha()+"'");
		tab_tabla.getColumna("EVENTO_RELOJ_COBIM").setFiltro(true);
		tab_tabla.getColumna("IDE_PERSONA_COBIM").setFiltro(true);
		tab_tabla.getColumna("NOMBRES").setFiltro(true);
		tab_tabla.getColumna("DETALLE_COREL").setFiltro(true);
		tab_tabla.setNumeroTabla(1);				
		tab_tabla.setLectura(true);
		tab_tabla.setRows(20);
		tab_tabla.dibujar();		
		PanelTabla pat_panel = new PanelTabla();
		pat_panel.setPanelTabla(tab_tabla);

		Division div_division = new Division();
		div_division.setId("div_division");
		div_division.dividir1(pat_panel);
		agregarComponente(div_division);
	}

	public void actualizarMarcaciones(){
		if (cal_fecha_inicial.getValue() != null && cal_fecha_final.getValue() != null
				) {
			tab_tabla.setSql("SELECT BIO.IDE_COBIM,BIO.EVENTO_RELOJ_COBIM,IDE_PERSONA_COBIM,(SELECT APELLIDO_PATERNO_GTEMP || ' ' || APELLIDO_MATERNO_GTEMP || ' ' || PRIMER_NOMBRE_GTEMP || ' ' || SEGUNDO_NOMBRE_GTEMP FROM GTH_EMPLEADO WHERE DOCUMENTO_IDENTIDAD_GTEMP=trim(IDE_PERSONA_COBIM)) as NOMBRES,DETALLE_COREL,TO_CHAR(FECHA_EVENTO_COBIM,'YYYY-MM-DD') as FECHAM,TO_CHAR(FECHA_EVENTO_COBIM,'hh24:mi:ss') AS HORAM FROM CON_BIOMETRICO_MARCACIONES BIO INNER JOIN CON_RELOJ RELOJ ON BIO.IDE_COREL=RELOJ.IDE_COREL  WHERE TO_CHAR(FECHA_EVENTO_COBIM,'YYYY-MM-DD') BETWEEN '"+cal_fecha_inicial.getFecha()+"' AND '"+cal_fecha_final.getFecha()+"'");	
			tab_tabla.ejecutarSql();
		}
		else {
			utilitario.agregarMensajeInfo("Filtros no válidos",
					"Debe ingresar los fitros de rangos de fechas");
		}		
	}

	@Override
	public void insertar() {
		tab_tabla.insertar();
	}

	@Override
	public void guardar() {
		tab_tabla.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		tab_tabla.eliminar();
	}

	public Tabla getTab_tabla() {
		return tab_tabla;
	}

	public void setTab_tabla(Tabla tab_tabla) {
		this.tab_tabla = tab_tabla;
	}
}
