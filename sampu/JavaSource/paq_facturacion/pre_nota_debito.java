package paq_facturacion;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_facturacion.ejb.ServicioFacturacion;
import paq_sistema.aplicacion.Pantalla;

public class pre_nota_debito extends Pantalla{
	private Tabla tab_debito=new Tabla ();
	private Tabla tab_detalle_debito=new Tabla ();
	private AutoCompletar aut_cliente=new AutoCompletar();
	private SeleccionTabla set_factura = new SeleccionTabla();

	@EJB
	private ServicioFacturacion ser_facturacion = (ServicioFacturacion ) utilitario.instanciarEJB(ServicioFacturacion.class);
	
	
	public pre_nota_debito(){
		tab_debito.setId("tab_debito");
		tab_debito.setHeader("NOTA DE DEBITO");
		tab_debito.setTabla("fac_nota_debito", "ide_fanod", 1);
		tab_debito.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_debito.getColumna("ide_recli").setVisible(false);
		tab_debito.setTipoFormulario(true);
		tab_debito.getGrid().setColumns(4);
		tab_debito.agregarRelacion(tab_detalle_debito);
		tab_debito.dibujar();
		PanelTabla pat_debito=new PanelTabla();
		pat_debito.setPanelTabla(tab_debito);
		
		///// DETALLE DEBITO
		tab_detalle_debito.setId("tab_detalle_debito");
		tab_detalle_debito.setHeader("NOTA DETALLE DEBITO");
		tab_detalle_debito.setTabla("fac_detalle_debito", "ide_faded", 2);
		//tab_detalle_debito.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_detalle_debito.getColumna("ide_fafac").setCombo(ser_facturacion.getCabeceraFactura());
		tab_detalle_debito.getColumna("ide_fafac").setAutoCompletar();
		tab_detalle_debito.getColumna("ide_fafac").setLectura(true);
		tab_detalle_debito.getColumna("base_imponible_faded").setEtiqueta();
		tab_detalle_debito.getColumna("base_imponible_faded").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_detalle_debito.getColumna("interes_generado_faded").setEtiqueta();
		tab_detalle_debito.getColumna("interes_generado_faded").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_detalle_debito.getColumna("valor_iva_faded").setEtiqueta();
		tab_detalle_debito.getColumna("valor_iva_faded").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_detalle_debito.getColumna("total_faded").setEtiqueta();
		tab_detalle_debito.getColumna("total_faded").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_detalle_debito.getColumna("interes_aplicado_faded").setEtiqueta();
		tab_detalle_debito.getColumna("interes_aplicado_faded").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		
		tab_detalle_debito.dibujar();
		PanelTabla pat_detalle=new PanelTabla();
		pat_detalle.setPanelTabla(tab_detalle_debito);
		
		Boton bot_limpiar=new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		aut_cliente.setId("aut_cliente");
		aut_cliente.setAutoCompletar(ser_facturacion.getClientesDatosBasicos("0,1"));
		aut_cliente.setMetodoChange("seleccionoAutocompletar"); //ejecuta el metodo seleccionoAutocompletar

		Etiqueta eti_colaborador=new Etiqueta("CLIENTE:");
		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_cliente);
		bar_botones.agregarBoton(bot_limpiar);

		
		Division div_division=new Division();
		div_division.dividir2(pat_debito, pat_detalle, "50%", "H");
		agregarComponente(div_division);
		
		Boton bot_factura = new Boton();
		bot_factura.setValue("Buscar Factura");
		bot_factura.setTitle("Factura");
		bot_factura.setIcon("ui-icon-person");
		bot_factura.setMetodo("importarFactura");
		bar_botones.agregarBoton(bot_factura);

		set_factura.setId("set_factura");
		set_factura.setSeleccionTabla(ser_facturacion.getCabeceraFactura(),"ide_fafac");
		set_factura.getTab_seleccion().getColumna("factura_fisica_fafac").setFiltro(true);
		set_factura.getTab_seleccion().getColumna("secuencial_fafac").setFiltro(true);
		set_factura.getTab_seleccion().getColumna("detalle_bogrm").setFiltro(true);
		set_factura.getBot_aceptar().setMetodo("aceptarFactura");
		set_factura.getTab_seleccion().ejecutarSql();
		agregarComponente(set_factura);


	}
	
	public void importarFactura(){
		System.out.println(" ingresar al importar");
		set_factura.getTab_seleccion().setSql(ser_facturacion.getCabeceraFactura());
		set_factura.getTab_seleccion().ejecutarSql();
		set_factura.dibujar();

	}
	public  void aceptarFactura(){
		
		double dou_interes_generadodo=0;
		double dou_base_imponible=0;
		double dou_valor_iva=0;
		double dou_total=0;
		double dou_interes_aplicado=0;
		double dias_retrasado=0;


		String str_seleccionado = set_factura.getSeleccionados();
		System.out.println("entra al str  "+str_seleccionado);
		if (str_seleccionado!=null){
			TablaGenerica tab_aceptarfactura = ser_facturacion.getTablaGenericaFacturaCabecera(str_seleccionado);		
			System.out.println(" tabla generica"+tab_aceptarfactura.getSql());
			for(int i=0;i<tab_aceptarfactura.getTotalFilas();i++){

			tab_detalle_debito.insertar();
			tab_detalle_debito.setValor("ide_fafac", tab_aceptarfactura.getValor("ide_fafac"));
			tab_detalle_debito.setValor("base_imponible_faded",tab_aceptarfactura.getValor(i,"base_aprobada_fafac"));
			tab_detalle_debito.setValor("fecha_emision_factura_faded",tab_aceptarfactura.getValor(i,"fecha_transaccion_fafac"));			
			dou_base_imponible= Double.parseDouble(tab_aceptarfactura.getValor(i,"base_aprobada_fafac"));
			//dou_dias_plazo= Double.parseDouble(tab_aceptarfactura.getValor(i,"base_aprobada_fafac"));

			Date fecha_inicio= utilitario.getFecha(tab_debito.getValor("fecha_debito"));
			Date fecha_fin= utilitario.getFecha(tab_aceptarfactura.getValor(i,"fecha_transaccion_fafac"));
			double dias_plazo=0;
			//dias_plazo= utilitario.getVariable("p_dias_calculo_interes_mora_nd");
			dias_retrasado= utilitario.getDiferenciasDeFechas(fecha_inicio, fecha_fin);
			
		}
		set_factura.cerrar();
		utilitario.addUpdate("tab_detalle_debito");
	}
	}


	public void limpiar(){
		aut_cliente.limpiar();
		tab_debito.limpiar();
		tab_detalle_debito.limpiar();
		utilitario.addUpdate("aut_cliente");
	}
	
	//METDO AUTOCOMPLETAR
		public void seleccionoAutocompletar(SelectEvent evt){
			//Cuando selecciona una opcion del autocompletar siempre debe hacerse el onSelect(evt)
			aut_cliente.onSelect(evt);
			tab_debito.setCondicion("ide_faded="+aut_cliente.getValor());
			tab_detalle_debito.ejecutarSql();
			tab_detalle_debito.ejecutarValorForanea(tab_debito.getValorSeleccionado());
		}

		
		///CALCULAR 
		
			

		
		
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(aut_cliente.getValor()!=null){
			if(tab_debito.isFocus()){
				tab_debito.getColumna("ide_recli").setValorDefecto(aut_cliente.getValor());
				tab_debito.insertar();
								
			}
			else if(tab_detalle_debito.isFocus()){
				tab_detalle_debito.insertar();
			}
		}
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_debito.guardar()){
			tab_detalle_debito.guardar();
		}
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

		
	}

	public Tabla getTab_debito() {
		return tab_debito;
	}

	public void setTab_debito(Tabla tab_debito) {
		this.tab_debito = tab_debito;
	
	}

	public Tabla getTab_detalle_debito() {
		return tab_detalle_debito;
	}

	public void setTab_detalle_debito(Tabla tab_detalle_debito) {
		this.tab_detalle_debito = tab_detalle_debito;
	}

	public AutoCompletar getAut_cliente() {
		return aut_cliente;
	}

	public void setAut_cliente(AutoCompletar aut_cliente) {
		this.aut_cliente = aut_cliente;
	}

	public SeleccionTabla getSet_factura() {
		return set_factura;
	}

	public void setSet_factura(SeleccionTabla set_factura) {
		this.set_factura = set_factura;
	}
	

}
