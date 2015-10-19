package paq_tesoreria;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import framework.aplicacion.Fila;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.BotonesCombo;
import framework.componentes.Calendario;
import framework.componentes.Check;
import framework.componentes.Confirmar;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.ItemMenu;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Texto;
import framework.componentes.VisualizarPDF;
import paq_facturacion.ejb.ServicioFacturacion;
import paq_sistema.aplicacion.Pantalla;
import paq_tesoreria.ejb.ServicioTesoreria;

public class pre_consulta_facturas extends Pantalla{
	
	private Tabla tab_recaudacion = new Tabla();
	private AutoCompletar aut_cliente = new AutoCompletar();
	private Texto	txt_total = new Texto();
	private Texto	txt_cliente = new Texto();
	private Texto 	txt_documento= new Texto();
	private Texto	txt_valor_entregado = new Texto();
	private Etiqueta eti_devolucion= new Etiqueta();
	private AutoCompletar aut_caja= new AutoCompletar();
	private AutoCompletar aut_recaudador = new AutoCompletar();
	private AutoCompletar aut_sucursal= new AutoCompletar();
	private AutoCompletar aut_lugar_cobro=new AutoCompletar();
	private Confirmar con_guardar=new Confirmar();
    private VisualizarPDF vpdf_pago = new VisualizarPDF();
	private Check che_todos_emp=new Check();
	private Check che_aplica_fecha=new Check();

	private Calendario cal_fecha_inicial = new Calendario();
	private Calendario cal_fecha_final = new Calendario();


	String ide_caja="";
	String ide_empleado="";
	String ide_sucursal="";
	String ide_lugar="";
	@EJB
	private ServicioFacturacion ser_facturacion = (ServicioFacturacion ) utilitario.instanciarEJB(ServicioFacturacion.class);
	@EJB
	private ServicioTesoreria ser_tesoreria = (ServicioTesoreria ) utilitario.instanciarEJB(ServicioTesoreria.class);

	public pre_consulta_facturas(){

		
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		
		aut_cliente.setId("aut_cliente");
		aut_cliente.setAutoCompletar(ser_facturacion.getClientesDatosBasicos("0,1"));
		Etiqueta eti_colaborador=new Etiqueta("CLIENTE:");
		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_cliente);
		bar_botones.agregarBoton(bot_limpiar);
		
		che_todos_emp.setId("che_todos_emp");
		Etiqueta eti_todos_emp=new Etiqueta("Todos los Clientes");
		bar_botones.agregarComponente(eti_todos_emp);
		bar_botones.agregarComponente(che_todos_emp);
		
				
		che_aplica_fecha.setId("che_aplica_fecha");
		Etiqueta eti_aplica_fecha=new Etiqueta("Aplica Fecha de Pago");
		bar_botones.agregarComponente(eti_aplica_fecha);
		bar_botones.agregarComponente(che_aplica_fecha);
		
		
		bar_botones.agregarComponente(new Etiqueta("Fecha Inicial :"));
		cal_fecha_inicial.setValue(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		bar_botones.agregarComponente(cal_fecha_inicial);

		bar_botones.agregarComponente(new Etiqueta("Fecha Final :"));
		cal_fecha_final.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_final);
		
		
		Boton bot_actualiza = new Boton();
		bot_actualiza.setId("bot_actualiza");
		bot_actualiza.setTitle("ACTUALIZAR");
		bot_actualiza.setValue("ACTUALIZAR");
		bot_actualiza.setMetodo("cargaCliente");
		bar_botones.agregarBoton(bot_actualiza);
		
		
    			
		tab_recaudacion.setId("tab_recaudacion");
		tab_recaudacion.setSql(ser_tesoreria.getFacturaClientes("-1", "0","1900-01-01","1900-01-01","false"));
		tab_recaudacion.getColumna("ide_fafac").setVisible(false);
		tab_recaudacion.getColumna("ide_recli").setVisible(false);
		tab_recaudacion.getColumna("grupo").setVisible(false);
		tab_recaudacion.getColumna("detalle_bogrm").setNombreVisual("DETALLE");
		tab_recaudacion.getColumna("secuencial_fafac").setNombreVisual("NRO. DOCUMENTO");
		tab_recaudacion.getColumna("detalle_bogrm").setLongitud(100);
		tab_recaudacion.getColumna("secuencial_fafac").setLongitud(50);
		/*
		tab_recaudacion.getColumna("detalle_bogrm").setFiltro(true);
		tab_recaudacion.getColumna("secuencial_fafac").setFiltro(true);
		tab_recaudacion.getColumna("detalle_coest").setFiltro(true);;
		tab_recaudacion.getColumna("fecha_pago_fafac").setFiltro(true);
		tab_recaudacion.getColumna("conciliado_fafac").setFiltro(true);
		tab_recaudacion.getColumna("fecha_transaccion_fafac").setFiltro(true);
		 */
		
		tab_recaudacion.getColumna("detalle_bogrm").setLectura(true);
		tab_recaudacion.getColumna("secuencial_fafac").setLectura(true);
		tab_recaudacion.getColumna("detalle_coest").setLectura(true);
		tab_recaudacion.getColumna("fecha_pago_fafac").setLectura(true);
		tab_recaudacion.getColumna("conciliado_fafac").setLectura(true);
		tab_recaudacion.getColumna("fecha_transaccion_fafac").setLectura(true);
		tab_recaudacion.getColumna("total").setLectura(true);		
		tab_recaudacion.getColumna("ruc_comercial_recli").setLectura(true);	
//		tab_recaudacion.getColumna("interes").setLectura(true);		
		tab_recaudacion.getColumna("fecha_vencimiento_fafac").setLectura(true);
		tab_recaudacion.getColumna("ide_coest").setVisible(false);		
		tab_recaudacion.getColumna("ide_coest").setLectura(true);		


		

		tab_recaudacion.dibujar();
		
        PanelTabla pat_panel = new PanelTabla();
         pat_panel.setPanelTabla(tab_recaudacion);
		
		Division div_recaudacion = new Division();
		div_recaudacion.setId("div_recaudacion");
		div_recaudacion.dividir1(pat_panel);
		
		agregarComponente(div_recaudacion);
		
		//REPORTE
        vpdf_pago.setId("vpdf_pago");
        vpdf_pago.setTitle("Detalle de la Recaudación");
        agregarComponente(vpdf_pago);
		
	}
	
	public void cargaCliente(){
		
		String fecha_inicial=cal_fecha_inicial.getFecha();
		String fecha_final=cal_fecha_final.getFecha();
		
		if(che_todos_emp.getValue().toString().equalsIgnoreCase("true")){
			tab_recaudacion.setSql(ser_tesoreria.getFacturaClientes("-1", "1",fecha_inicial,fecha_final,che_aplica_fecha.getValue().toString()));
			tab_recaudacion.ejecutarSql();
			utilitario.addUpdate("tab_recaudacion");
		}
		else{
			tab_recaudacion.setSql(ser_tesoreria.getFacturaClientes(aut_cliente.getValor(), "0",fecha_inicial,fecha_final,che_aplica_fecha.getValue().toString()));
			tab_recaudacion.ejecutarSql();
			utilitario.addUpdate("tab_recaudacion");
		}
	}
	
	public void limpiar(){
		aut_cliente.limpiar();
		tab_recaudacion.limpiar();
		
		tab_recaudacion.setSql(ser_tesoreria.getSqlDeudaClientes("-1"));
		tab_recaudacion.ejecutarSql();
		utilitario.addUpdate("tab_recaudacion,aut_cliente,eti_devolucion");
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

	public Tabla getTab_recaudacion() {
		return tab_recaudacion;
	}

	public void setTab_recaudacion(Tabla tab_recaudacion) {
		this.tab_recaudacion = tab_recaudacion;
	}

	public AutoCompletar getAut_cliente() {
		return aut_cliente;
	}

	public void setAut_cliente(AutoCompletar aut_cliente) {
		this.aut_cliente = aut_cliente;
	}

	public AutoCompletar getAut_caja() {
		return aut_caja;
	}

	public void setAut_caja(AutoCompletar aut_caja) {
		this.aut_caja = aut_caja;
	}

	public AutoCompletar getAut_recaudador() {
		return aut_recaudador;
	}

	public void setAut_recaudador(AutoCompletar aut_recaudador) {
		this.aut_recaudador = aut_recaudador;
	}

	public AutoCompletar getAut_sucursal() {
		return aut_sucursal;
	}

	public void setAut_sucursal(AutoCompletar aut_sucursal) {
		this.aut_sucursal = aut_sucursal;
	}

	public AutoCompletar getAut_lugar_cobro() {
		return aut_lugar_cobro;
	}

	public void setAut_lugar_cobro(AutoCompletar aut_lugar_cobro) {
		this.aut_lugar_cobro = aut_lugar_cobro;
	}

	

}
