package paq_activos;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;

import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_traspaso_custodio extends Pantalla {
	private Tabla tab_traspaso = new Tabla();
	private AutoCompletar aut_empleado = new AutoCompletar();
	private SeleccionTabla set_custodio=new SeleccionTabla();
	private Tabla tab_custodio= new Tabla();

	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	

	
	public pre_traspaso_custodio(){
		// boton limpiar
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");

		// autocompletar empleado
		aut_empleado.setId("aut_empleado");

		String str_sql_emp=ser_gestion.getSqlEmpleadosAutocompletar();
		aut_empleado.setAutoCompletar(str_sql_emp);
		aut_empleado.setMetodoChange("filtrarCustodio");

		Etiqueta eti_colaborador=new Etiqueta("CUSTODIO:");


		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_empleado);
		bar_botones.agregarBoton(bot_limpiar);
	/////custodio
		Boton bot_custodio=new Boton();
		bot_custodio.setIcon("ui-icon-person");
		bot_custodio.setValue("Traspaso Custodio");
		bot_custodio.setMetodo("importarCustodio");
		bar_botones.agregarBoton(bot_custodio);
		
		
		set_custodio.setId("set_custodio");
		set_custodio.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		set_custodio.setRadio();
		set_custodio.getBot_aceptar().setMetodo("modificarAdministrador");
		agregarComponente(set_custodio);	

		tab_traspaso.setId("tab_traspaso");
		tab_traspaso.setSql("select b.ide_afcus,detalle_afact,serie_afact,modelo_afact,marca_afact,cod_barra_afcus,numero_acta_afcus," +
				 "fecha_entrega_afcus,apellido_paterno_gtemp,apellido_materno_gtemp,primer_nombre_gtemp,segundo_nombre_gtemp " +
				 "from afi_activo a,afi_custodio b, gen_empleados_departamento_par c, gth_empleado d where a.ide_afact=b.ide_afact " + 
				 " and b.ide_geedp=c.ide_geedp and c.ide_gtemp=d.ide_gtemp order by fecha_entrega_afcus desc");
	tab_traspaso.setCampoPrimaria("ide_afcus");
	tab_traspaso.setLectura(true);
	tab_traspaso.setCondicion("c.ide_geedp=-1");
	tab_traspaso.setTipoSeleccion(true);	
	tab_traspaso.dibujar();
	PanelTabla pat_panel=new PanelTabla();
	pat_panel.setPanelTabla(tab_traspaso);
	agregarComponente(pat_panel);
	
	//////custodio es una tabla temporal para cuardar los datos 
	
	tab_custodio.setId("tab_custodio");
	tab_custodio.setTabla("afi_custodio","ide_afcus", 2);
	tab_custodio.dibujar();
	
	
	}
	
	public void filtrarCustodio(SelectEvent evt){
		aut_empleado.onSelect(evt);
		tab_traspaso.setCondicion("c.ide_geedp="+aut_empleado.getValor());
		tab_traspaso.ejecutarSql();
		utilitario.addUpdate("tab_traspaso");

		

	}
	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	public void limpiar() {
		aut_empleado.limpiar();
		utilitario.addUpdate("aut_empleado");// limpia y refresca el autocompletar


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

	public Tabla getTab_traspaso() {
		return tab_traspaso;
	}

	public void setTab_traspaso(Tabla tab_traspaso) {
		this.tab_traspaso = tab_traspaso;
	}

	public AutoCompletar getAut_empleado() {
		return aut_empleado;
	}

	public void setAut_empleado(AutoCompletar aut_empleado) {
		this.aut_empleado = aut_empleado;
	}

	public SeleccionTabla getSet_custodio() {
		return set_custodio;
	}

	public void setSet_custodio(SeleccionTabla set_custodio) {
		this.set_custodio = set_custodio;
	}

	public Tabla getTab_custodio() {
		return tab_custodio;
	}

	public void setTab_custodio(Tabla tab_custodio) {
		this.tab_custodio = tab_custodio;
	}

}
