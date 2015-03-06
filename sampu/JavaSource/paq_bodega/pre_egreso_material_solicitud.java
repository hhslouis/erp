package paq_bodega;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;

import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_sistema.aplicacion.Pantalla;


public class pre_egreso_material_solicitud extends Pantalla{
	private Tabla tab_concepto_egreso=new Tabla();
	private Tabla tab_egresos=new Tabla();
	private AutoCompletar aut_egresos= new AutoCompletar();
	private SeleccionTabla set_solicitud = new SeleccionTabla();

	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);


	public pre_egreso_material_solicitud() {

		Boton bot_material = new Boton();
		bot_material.setValue("Buscar Solicitud Compra Bodega");
		bot_material.setTitle("Solicitud Compra");
		bot_material.setIcon("ui-icon-person");
		bot_material.setMetodo("importarSolicitud");
		bar_botones.agregarBoton(bot_material);
		
		set_solicitud.setId("set_solicitud");
		set_solicitud.setSeleccionTabla(ser_bodega.getEgresoSolicitud(),"ide_adsoc");
		set_solicitud.getBot_aceptar().setMetodo("aceptarSolicitud");
		set_solicitud.getTab_seleccion().ejecutarSql();
		set_solicitud.setRadio();
		agregarComponente(set_solicitud);


	}

	public void importarSolicitud(){

		set_solicitud.getTab_seleccion().setSql(ser_bodega.getEgresoSolicitud());
		set_solicitud.getTab_seleccion().ejecutarSql();
		set_solicitud.dibujar();

	}

	public  void aceptarSolicitud(){
		
		set_solicitud.cerrar();
	}


	@Override
	public void insertar() {
	}

	@Override
	public void guardar() {
	}
	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();

	}

	public SeleccionTabla getSet_solicitud() {
		return set_solicitud;
	}

	public void setSet_solicitud(SeleccionTabla set_solicitud) {
		this.set_solicitud = set_solicitud;
	}


}
