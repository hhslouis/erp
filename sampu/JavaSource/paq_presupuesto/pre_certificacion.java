package paq_presupuesto;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_certificacion extends Pantalla{
	private Tabla tab_certificacion=new Tabla();
	private Tabla tab_poa_certificacion=new Tabla();
	private SeleccionTabla set_poa=new SeleccionTabla();
  
  @EJB
 private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
  @EJB
 private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto)utilitario.instanciarEJB(ServicioPresupuesto.class);
 		
	
	public pre_certificacion(){
		tab_certificacion.setId("tab_certificacion");
		tab_certificacion.setHeader("CERTIFICACION PRESUPUESTARIA");
		tab_certificacion.setTabla("pre_certificacion", "ide_prcer", 1);
		tab_certificacion.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_certificacion.getColumna("IDE_GEEDP").setAutoCompletar();
		tab_certificacion.getColumna("gen_ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_certificacion.getColumna("gen_ide_geedp").setAutoCompletar();
		tab_certificacion.getColumna("gen_ide_geedp2").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
		tab_certificacion.getColumna("gen_ide_geedp2").setAutoCompletar();
		tab_certificacion.getColumna("activo_prcer").setValorDefecto("true");
		tab_certificacion.setTipoFormulario(true);
		tab_certificacion.getGrid().setColumns(4);
		tab_certificacion.agregarRelacion(tab_poa_certificacion);
		tab_certificacion.dibujar();
		PanelTabla pat_certificacion=new PanelTabla();
		pat_certificacion.setPanelTabla(tab_certificacion);
		
		////// poa certificacion
		tab_poa_certificacion.setId("tab_poa_certificacion");
		tab_poa_certificacion.setHeader("POA CERTIFICACION");
		tab_poa_certificacion.setTabla("pre_poa_certificacion", "ide_prpoc", 2);
		tab_poa_certificacion.getColumna("ide_prpoa").setCombo(ser_presupuesto.getPoa("true,false"));
		tab_poa_certificacion.getColumna("activo_prpoc").setValorDefecto("true");
		tab_poa_certificacion.dibujar();
		PanelTabla pat_poa_certi=new PanelTabla();
		pat_poa_certi.setPanelTabla(tab_poa_certificacion);
		
		
		Division div_divi=new Division();
		div_divi.dividir2(pat_certificacion, pat_poa_certi, "50%", "H");
		agregarComponente(div_divi);
		
		
		/////boton buscar poa
		Boton bot_buscar=new Boton();
		bot_buscar.setIcon("ui-icon-person");
		bot_buscar.setValue("Buscar POA");
		bot_buscar.setMetodo("importarPoa");
		bar_botones.agregarBoton(bot_buscar);

		set_poa.setId("set_poa");
		set_poa.setSeleccionTabla(ser_presupuesto.getPoa("true,false"),"ide_prpoa");
		set_poa.setTitle("Seleccione Poa");
		set_poa.getBot_aceptar().setMetodo("aceptarPoa");
		agregarComponente(set_poa);

	}
////importar poa
	public void importarPoa(){
		System.out.println(" ingresar al importar");

		set_poa.getTab_seleccion().setSql(ser_presupuesto.getPoa("true,false"));
		set_poa.getTab_seleccion().ejecutarSql();
		set_poa.dibujar();

	}

	public  void aceptarPoa(){
		String str_seleccionados = set_poa.getSeleccionados();

		if (str_seleccionados!=null){
			TablaGenerica tab_poa = ser_presupuesto.getTablaGenericaPoa(str_seleccionados);		
			for(int i=0;i<tab_poa.getTotalFilas();i++){
				tab_poa_certificacion.insertar();
				tab_poa_certificacion.setValor("ide_prpoa", tab_poa.getValor(i, "ide_prpoa"));
			}
			set_poa.cerrar();
			utilitario.addUpdate("tab_poa_certificacion");
		}
	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(tab_certificacion.isFocus()){
			tab_certificacion.insertar();
		}
		else if(tab_poa_certificacion.isFocus()){
			utilitario.agregarMensajeInfo("No puede insertar", "Debe agrear un poa");
		}
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_certificacion.guardar()){
			if(tab_poa_certificacion.guardar()){
				guardarPantalla();
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
	}

	public Tabla getTab_certificacion() {
		return tab_certificacion;
	}

	public void setTab_certificacion(Tabla tab_certificacion) {
		this.tab_certificacion = tab_certificacion;
	}

	public Tabla getTab_poa_certificacion() {
		return tab_poa_certificacion;
	}

	public void setTab_poa_certificacion(Tabla tab_poa_certificacion) {
		this.tab_poa_certificacion = tab_poa_certificacion;
	}
	public SeleccionTabla getSet_poa() {
		return set_poa;
	}
	public void setSet_poa(SeleccionTabla set_poa) {
		this.set_poa = set_poa;
	}

}
