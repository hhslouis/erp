package paq_adquisicion;

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
import paq_adquisicion.ejb.ServicioAdquisicion;
import paq_bodega.ejb.ServicioBodega;
import paq_sistema.aplicacion.Pantalla;

public class pre_factura_compras extends Pantalla{
	private Tabla tab_adq_factura= new Tabla();
	private Tabla tab_adq_detalle= new Tabla();
	private AutoCompletar aut_adq_compra= new AutoCompletar();
	private SeleccionTabla set_solicitud=new SeleccionTabla();
	public static double par_iva;

	@EJB
	private ServicioAdquisicion ser_Adquisicion=(ServicioAdquisicion) utilitario.instanciarEJB(ServicioAdquisicion.class);
	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	public pre_factura_compras() {

		par_iva=Double.parseDouble(utilitario.getVariable("p_valor_iva"));

		tab_adq_factura.setId("tab_adq_factura");
		tab_adq_factura.setTabla("adq_factura", "ide_adfac", 1);
		tab_adq_factura.agregarRelacion(tab_adq_detalle); 
		tab_adq_factura.setTipoFormulario(true);
		tab_adq_factura.getGrid().setColumns(4);
		tab_adq_factura.setCampoOrden("ide_adfac desc");
		tab_adq_factura.getColumna("ide_adsoc").setCombo(ser_Adquisicion.getCompras("true,false"));
		tab_adq_factura.getColumna("subtotal_adfac").setEtiqueta();
		tab_adq_factura.getColumna("subtotal_adfac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_adq_factura.getColumna("base_iva_adfac").setEtiqueta();
		tab_adq_factura.getColumna("base_iva_adfac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_adq_factura.getColumna("valor_iva_adfac").setMetodoChange("calcularSolicitud");
		tab_adq_factura.getColumna("valor_iva_adfac").setEtiqueta();
		tab_adq_factura.getColumna("porcent_desc_adfac").setMetodoChange("calcularDescuentoPorce");
		tab_adq_factura.getColumna("valor_iva_adfac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_adq_factura.getColumna("valor_descuento_adfac").setMetodoChange("calcularDescuento");
		tab_adq_factura.getColumna("total_adfac").setEtiqueta();
		tab_adq_factura.getColumna("total_adfac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_adq_factura.getColumna("activo_adfac").setLectura(true);
		tab_adq_factura.getColumna("activo_adfac").setValorDefecto("true");
		tab_adq_factura.dibujar();
		PanelTabla pat_adq_factura= new PanelTabla();
		pat_adq_factura.setPanelTabla(tab_adq_factura);

		tab_adq_detalle.setId("tab_adq_detalle");
		tab_adq_detalle.setTabla("adq_detalle_factura", "ide_addef", 2);
		tab_adq_detalle.getColumna("ide_bomat").setCombo(ser_bodega.getInventario("1","true,false",""));
		tab_adq_detalle.getColumna("aplica_iva_addef").setVisible(false);
		tab_adq_detalle.getColumna("cantidad_addef").setMetodoChange("calcularDetallle");
		tab_adq_detalle.getColumna("valor_unitario_addef").setMetodoChange("calcularDetallle");
		tab_adq_detalle.getColumna("valor_total_addef").setEtiqueta();
		tab_adq_detalle.getColumna("valor_total_addef").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_adq_detalle.getColumna("activo_addef").setLectura(true);
		tab_adq_detalle.getColumna("activo_addef").setValorDefecto("true");
		tab_adq_detalle.getColumna("recibido_addef").setValorDefecto("true");
		tab_adq_detalle.getColumna("recibido_addef").setLectura(true);


		tab_adq_detalle.dibujar();
		PanelTabla pat_adq_detalle=new PanelTabla();
		pat_adq_detalle.setPanelTabla(tab_adq_detalle);


		Boton bot_buscar=new Boton();
		bot_buscar.setIcon("ui-icon-person");
		bot_buscar.setValue("Buscar Hoja de Requerimiento");
		bot_buscar.setMetodo("importarSolicitudCompra");
		bar_botones.agregarBoton(bot_buscar);

		set_solicitud.setId("set_solicitud");
		set_solicitud.setSeleccionTabla(ser_Adquisicion.getCompras("true,false"),"ide_adsoc");
		set_solicitud.setTitle("SELECCION UNA SOLICITUD DE COMPRA");		
		set_solicitud.getBot_aceptar().setMetodo("aceptarSolicitudCompra");
		set_solicitud.setRadio();
		agregarComponente(set_solicitud);

		Division div_division = new Division();
		div_division.dividir2(pat_adq_factura, pat_adq_detalle, "50%", "H");
		agregarComponente(div_division);



	}
	public void importarSolicitudCompra(){

		set_solicitud.getTab_seleccion().setSql(ser_Adquisicion.getCompras("true"));
		set_solicitud.getTab_seleccion().ejecutarSql();
		set_solicitud.dibujar();

	}

	public void aceptarSolicitudCompra(){

		String str_seleccionado = set_solicitud.getValorSeleccionado();
		TablaGenerica tab_solicitud=ser_Adquisicion.getTablaGenericaSolicitud(str_seleccionado);
		if (str_seleccionado!=null){
			tab_adq_factura.insertar();
			tab_adq_factura.setValor("ide_adsoc",str_seleccionado);
		}
		set_solicitud.cerrar();
		utilitario.addUpdate("tab_comprobante");
	}
	///CALCULAR 
	public void calcular(){
		//Variables para almacenar y calcular el total del detalle
		double dou_cantidad_addef=0;
		double dou_valor_unitario_addef=0;
		double dou_valor_total_addef=0;
		try {
			//Obtenemos el valor de la cantidad
			dou_cantidad_addef=Double.parseDouble(tab_adq_detalle.getValor("cantidad_addef"));
		} catch (Exception e) {
		}

		try {
			//Obtenemos el valor
			dou_valor_unitario_addef=Double.parseDouble(tab_adq_detalle.getValor("valor_unitario_addef"));
		} catch (Exception e) {
		}

		//Calculamos el total
		dou_valor_total_addef=dou_cantidad_addef*dou_valor_unitario_addef;

		//Asignamos el total a la tabla detalle, con 2 decimales
		tab_adq_detalle.setValor("valor_total_addef",utilitario.getFormatoNumero(dou_valor_total_addef,3));

		//Actualizamos el campo de la tabla AJAX
		utilitario.addUpdateTabla(tab_adq_detalle, "valor_total_addef", "tab_adq_factura");
		calcularSolicitud();


	}



	public void calcularDetallle(AjaxBehaviorEvent evt) {
		tab_adq_detalle.modificar(evt); //Siempre es la primera linea
		calcular();

	}
	public void calcularSolicitud(){

		double dou_subtotal_adfac= 0;
		double duo_valor_iva=par_iva;
		double dou_valor_iva_adfac=0;
		double dou_total_adfac=0;
		double dou_valor_total_addef=0;

		String sub_tot=tab_adq_detalle.getSumaColumna("valor_total_addef")+"";
		tab_adq_factura.setValor("subtotal_adfac",sub_tot );

		String base_ivt=tab_adq_detalle.getSumaColumna("valor_total_addef")+"";
		tab_adq_factura.setValor("base_iva_adfac",base_ivt );
		dou_subtotal_adfac=Double.parseDouble(sub_tot);
		dou_valor_iva_adfac=dou_subtotal_adfac*duo_valor_iva;
		dou_total_adfac=dou_subtotal_adfac+dou_valor_iva_adfac;
		tab_adq_factura.setValor("valor_iva_adfac",utilitario.getFormatoNumero(dou_valor_iva_adfac,3));
		tab_adq_factura.setValor("total_adfac",utilitario.getFormatoNumero(dou_total_adfac,3));
		tab_adq_factura.modificar(tab_adq_factura.getFilaActual());//para que haga el update

		utilitario.addUpdateTabla(tab_adq_factura, "valor_iva_adfac,total_adfac,subtotal_adfac,base_iva_adfac", "tab_adq_detalle");	
	}
	
	public void calcularDescuento(){
		double duo_subtotal=0;
		double dou_subtotal_adfac=0;
		double duo_valor_descuento=0;
		double duo_total_iva=0;
		double duo_total=0;
		double  porcentaje_descuento=0;
		double duo_iva=par_iva;
		calcularSolicitud();
				
		//tab_adq_factura.getValor("valor_descuento_adfac");
		duo_subtotal=Double.parseDouble(tab_adq_factura.getValor("valor_descuento_adfac"));
		
		//tab_adq_factura.getValor("total_adfac");
		dou_subtotal_adfac=Double.parseDouble(tab_adq_factura.getValor("subtotal_adfac"));
		if(duo_subtotal>dou_subtotal_adfac){
			utilitario.agregarMensajeInfo("Excedido Valor ", "El valor del descuento no puedo superar el valor del subtotal");
			tab_adq_factura.setValor("valor_descuento_adfac","0");
			utilitario.addUpdateTabla(tab_adq_factura, "valor_descuento_adfac", "");	

			return;
			
		}
		porcentaje_descuento=(duo_subtotal*100)/dou_subtotal_adfac;
		duo_valor_descuento=dou_subtotal_adfac-duo_subtotal;
		duo_total_iva=duo_valor_descuento*duo_iva;
		duo_total=duo_valor_descuento+duo_total_iva;
		tab_adq_factura.setValor("porcent_desc_adfac",utilitario.getFormatoNumero(porcentaje_descuento,2));
		tab_adq_factura.setValor("subtotal_adfac",utilitario.getFormatoNumero(duo_valor_descuento,2));
		tab_adq_factura.setValor("base_iva_adfac",utilitario.getFormatoNumero(duo_valor_descuento,2));

		tab_adq_factura.setValor("total_adfac",utilitario.getFormatoNumero(duo_total,2));
		tab_adq_factura.setValor("VALOR_IVA_ADFAC",utilitario.getFormatoNumero(duo_total_iva,2));


		utilitario.addUpdateTabla(tab_adq_factura, "total_adfac,subtotal_adfac,VALOR_IVA_ADFAC,porcent_desc_adfac", "tab_adq_detalle");	



	}
	public void calcularDescuentoPorce(){
		double duo_subtotal=0;
		double dou_subtotal_adfac=0;
		double duo_valor_descuento=0;
		double duo_total_iva=0;
		double duo_total=0;
		double  porcentaje_descuento=0;
		double duo_iva=par_iva;
		calcularSolicitud();
				
		//tab_adq_factura.getValor("valor_descuento_adfac");
		duo_subtotal=Double.parseDouble(tab_adq_factura.getValor("porcent_desc_adfac"));
		//tab_adq_factura.getValor("total_adfac");
		if(duo_subtotal>100){
			utilitario.agregarMensajeInfo("Excedido Valor ", "El porcentaje de descuento no puede superar el 100%");
			tab_adq_factura.setValor("porcent_desc_adfac","0");
			utilitario.addUpdateTabla(tab_adq_factura, "porcent_desc_adfac", "");	

			return;
		}
		dou_subtotal_adfac=Double.parseDouble(tab_adq_factura.getValor("subtotal_adfac"));
		porcentaje_descuento=(dou_subtotal_adfac*duo_subtotal)/100;
		duo_valor_descuento=dou_subtotal_adfac-porcentaje_descuento;
		duo_total_iva=duo_valor_descuento*duo_iva;
		duo_total=duo_valor_descuento+duo_total_iva;
		tab_adq_factura.setValor("valor_descuento_adfac",utilitario.getFormatoNumero(porcentaje_descuento,2));
		tab_adq_factura.setValor("subtotal_adfac",utilitario.getFormatoNumero(duo_valor_descuento,2));
		tab_adq_factura.setValor("base_iva_adfac",utilitario.getFormatoNumero(duo_valor_descuento,2));
		tab_adq_factura.setValor("total_adfac",utilitario.getFormatoNumero(duo_total,2));
		tab_adq_factura.setValor("VALOR_DESCUENTO_ADFAC",utilitario.getFormatoNumero(porcentaje_descuento,2));
		tab_adq_factura.setValor("VALOR_IVA_ADFAC",utilitario.getFormatoNumero(duo_total_iva,2));


		utilitario.addUpdateTabla(tab_adq_factura, "total_adfac,subtotal_adfac,VALOR_IVA_ADFAC,porcent_desc_adfac,VALOR_DESCUENTO_ADFAC", "");	



	}

	@Override
	public void insertar() {
		utilitario.getTablaisFocus().insertar();

	}

	@Override
	public void guardar() {
		if(tab_adq_factura.guardar()){
			if(tab_adq_detalle.guardar()){
				guardarPantalla();
				tab_adq_factura.ejecutarSql();
				tab_adq_detalle.ejecutarSql();
						}
		}

	}

	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();

	}

	public Tabla getTab_adq_factura() {
		return tab_adq_factura;
	}

	public void setTab_adq_factura(Tabla tab_adq_factura) {
		this.tab_adq_factura = tab_adq_factura;
	}

	public Tabla getTab_adq_detalle() {
		return tab_adq_detalle;
	}

	public void setTab_adq_detalle(Tabla tab_adq_detalle) {
		this.tab_adq_detalle = tab_adq_detalle;
	}

	public SeleccionTabla getSet_solicitud() {
		return set_solicitud;
	}
	public void setSet_solicitud(SeleccionTabla set_solicitud) {
		this.set_solicitud = set_solicitud;
	}

}
