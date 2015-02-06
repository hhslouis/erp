package paq_facturacion;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Confirmar;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_facturacion.ejb.ServicioFacturacion;
import paq_sistema.aplicacion.Pantalla;

public class pre_nota_credito extends Pantalla {
	private  Tabla tab_nota_credito=new Tabla();
	private SeleccionTabla set_factura = new SeleccionTabla();
	private SeleccionTabla set_actualizarfactura=new SeleccionTabla();
	private Confirmar con_guardar = new Confirmar();
	//REPORTE
	private Map p_parametros = new HashMap();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte self_reporte = new SeleccionFormatoReporte();
	private Map map_parametros = new HashMap();
	double dou_valor_referencial_fanoc=0;
	double duo_valor_iva=0.12;
	double dou_iva_fanoc=0;
	double dou_total_fanoc=0;
	
	@EJB
	private ServicioFacturacion ser_Facturacion = (ServicioFacturacion) utilitario.instanciarEJB(ServicioFacturacion.class);

	public pre_nota_credito(){
		rep_reporte.setId("rep_reporte"); //id
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");//ejecuta el metodo al aceptar reporte
		agregarComponente(rep_reporte);//agrega el componente a la pantalla
		bar_botones.agregarReporte();//aparece el boton de reportes en la barra de botones
		self_reporte.setId("self_reporte"); //id
		agregarComponente(self_reporte);
		// TODO Auto-generated constructor stub
		tab_nota_credito.setId("tab_nota_credito");
		tab_nota_credito.setHeader("NOTA DE CRÈDITO");
		tab_nota_credito.setTipoFormulario(true);
		tab_nota_credito.getGrid().setColumns(4);
		tab_nota_credito.setTabla("fac_nota_credito","ide_fanoc", 1);
		tab_nota_credito.getColumna("ide_fafac").setCombo(ser_Facturacion.getCabeceraFactura("1",""));
		tab_nota_credito.getColumna("ide_fafac").setAutoCompletar();
		tab_nota_credito.getColumna("ide_fafac").setLectura(true);
		tab_nota_credito.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
		tab_nota_credito.getColumna("valor_referencial_fanoc").setMetodoChange("calcular");		
		tab_nota_credito.getColumna("iva_fanoc").setEtiqueta();
		tab_nota_credito.getColumna("iva_fanoc").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_nota_credito.getColumna("total_fanoc").setEtiqueta();
		tab_nota_credito.getColumna("activo_fanoc").setValorDefecto("true");
		tab_nota_credito.getColumna("total_fanoc").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
		tab_nota_credito.getColumna("fecha_fanoc").setValorDefecto(utilitario.getFechaActual());
		tab_nota_credito.dibujar();
		PanelTabla pat_nota_credito=new PanelTabla();
		pat_nota_credito.setPanelTabla(tab_nota_credito);

		Division div_division=new Division();
		div_division.dividir1(pat_nota_credito);
		agregarComponente(div_division);


		Boton bot_factura = new Boton();
		bot_factura.setValue("Buscar Factura");
		bot_factura.setTitle("Factura");
		bot_factura.setIcon("ui-icon-person");
		bot_factura.setMetodo("importarFactura");
		bar_botones.agregarBoton(bot_factura);

		set_factura.setId("set_factura");
		set_factura.setSeleccionTabla(ser_Facturacion.getCabeceraFactura("1",""),"ide_fafac");
		set_factura.getTab_seleccion().getColumna("factura_fisica_fafac").setFiltro(true);
		set_factura.getTab_seleccion().getColumna("secuencial_fafac").setFiltro(true);
		set_factura.getTab_seleccion().getColumna("detalle_bogrm").setFiltro(true);
		set_factura.getBot_aceptar().setMetodo("aceptarFactura");
		set_factura.getTab_seleccion().ejecutarSql();
		set_factura.setRadio();
		agregarComponente(set_factura);

		bar_botones.agregarBoton(bot_factura);
		con_guardar.setId("con_guardar");
		agregarComponente(con_guardar);

		Boton bot_actualizarfactura=new Boton();
		bot_actualizarfactura.setIcon("ui-icon-person");
		bot_actualizarfactura.setValue("Actualizar Factura");
		bot_actualizarfactura.setMetodo("actualizarFactura");
		bar_botones.agregarBoton(bot_actualizarfactura);	

		set_actualizarfactura.setId("set_actualizarfactura");
		set_actualizarfactura.setSeleccionTabla(ser_Facturacion.getCabeceraFactura("1",""),"ide_fafac");
		set_actualizarfactura.getTab_seleccion().getColumna("factura_fisica_fafac").setFiltro(true);
		set_actualizarfactura.getTab_seleccion().getColumna("secuencial_fafac").setFiltro(true);
		set_actualizarfactura.getTab_seleccion().getColumna("detalle_bogrm").setFiltro(true);
		set_actualizarfactura.getBot_aceptar().setMetodo("modificarFactura");
		set_actualizarfactura.setRadio();
		agregarComponente(set_actualizarfactura);


	}
	public void importarFactura(){
		System.out.println(" ingresar al importar");
		set_factura.getTab_seleccion().setSql(ser_Facturacion.getCabeceraFactura("1",""));
		set_factura.getTab_seleccion().ejecutarSql();
		set_factura.dibujar();

	}

	public  void aceptarFactura(){
		String str_seleccionado = set_factura.getValorSeleccionado();
		System.out.println("entra al str  "+str_seleccionado);
		if (str_seleccionado!=null){
			TablaGenerica tab_aceptarfactura = ser_Facturacion.getTablaGenericaFacturaCabecera(str_seleccionado);		
			System.out.println(" tabla generica"+tab_aceptarfactura.getSql());
			tab_nota_credito.insertar();
			tab_nota_credito.setValor("ide_fafac", tab_aceptarfactura.getValor("ide_fafac"));			
			tab_nota_credito.setValor("valor_referencial_fanoc", tab_aceptarfactura.getValor("base_aprobada_fafac"));			
			tab_nota_credito.setValor("iva_fanoc", tab_aceptarfactura.getValor("valor_iva_fafac"));
			tab_nota_credito.setValor("total_fanoc",tab_aceptarfactura.getValor("total_fafac"));
			//tab_nota_credito.setValor("ide_fafac",str_seleccionado);

		}
		set_factura.cerrar();
		utilitario.addUpdate("tab_nota_credito");
	}


	///ACTUALIZAR FACTURA
	public void actualizarFactura(){
		set_actualizarfactura.getTab_seleccion().setSql(ser_Facturacion.getCabeceraFactura("1",""));
		set_actualizarfactura.getTab_seleccion().ejecutarSql();
		set_actualizarfactura.dibujar();

	}

	public void modificarFactura(){
		String str_facturaActualizado=set_actualizarfactura.getValorSeleccionado();
		System.out.println("Entra a guardar..."+str_facturaActualizado);
			TablaGenerica tab_actualizarfactura = ser_Facturacion.getTablaGenericaFacturaCabecera(str_facturaActualizado);		
			System.out.println(" tabla generica"+tab_actualizarfactura.getSql());
			tab_nota_credito.setValor("ide_fafac", tab_actualizarfactura.getValor("ide_fafac"));			
			tab_nota_credito.setValor("valor_referencial_fanoc", tab_actualizarfactura.getValor("base_aprobada_fafac"));			
			tab_nota_credito.setValor("iva_fanoc", tab_actualizarfactura.getValor("valor_iva_fafac"));
			tab_nota_credito.setValor("total_fanoc",tab_actualizarfactura.getValor("total_fafac"));
			tab_nota_credito.modificar(tab_nota_credito.getFilaActual());
			utilitario.addUpdate("tab_nota_credito");	

			con_guardar.setMessage("Esta Seguro de Actualizar La Factura");
			con_guardar.setTitle("CONFIRMACION ");
			con_guardar.getBot_aceptar().setMetodo("guardarActualilzarFactura");
			con_guardar.dibujar();
			utilitario.addUpdate("con_guardar");
		}

		
		public void guardarActualilzarFactura(){
			System.out.println("Entra a guardar...");
			tab_nota_credito.guardar();
			con_guardar.cerrar();
			set_actualizarfactura.cerrar();


			guardarPantalla();

		}
		///CALCULAR 
		
		public void calcular(AjaxBehaviorEvent evt) {
			tab_nota_credito.modificar(evt); //Siempre es la primera lineadoubler

			double dou_valor_referencial_fanoc=0;
			double duo_valor_iva=0.12;
			double dou_iva_fanoc=0;
			double dou_total_fanoc=0;
			
			dou_valor_referencial_fanoc=Double.parseDouble(tab_nota_credito.getValor("valor_referencial_fanoc"));
			dou_iva_fanoc=dou_valor_referencial_fanoc*duo_valor_iva;
			dou_total_fanoc=dou_valor_referencial_fanoc+dou_iva_fanoc;
			tab_nota_credito.setValor("iva_fanoc",utilitario.getFormatoNumero(dou_iva_fanoc,3));
			tab_nota_credito.setValor("total_fanoc",utilitario.getFormatoNumero(dou_total_fanoc,3));
			utilitario.addUpdateTabla(tab_nota_credito, "iva_fanoc,total_fanoc", "");	
					
		}
		//REPORTE
				public void abrirListaReportes() {
					// TODO Auto-generated method stub
					rep_reporte.dibujar();
				}
				public void aceptarReporte(){
					if(rep_reporte.getReporteSelecionado().equals("Factura Credito"));{
						if (tab_nota_credito.getTotalFilas()>0){
						if (rep_reporte.isVisible()){
							p_parametros=new HashMap();		
							rep_reporte.cerrar();				
						p_parametros.put("pide_fafac",Integer.parseInt(tab_nota_credito.getValor("ide_fanoc")));
						self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
						self_reporte.dibujar();
						}
					}else{
						utilitario.agregarMensajeInfo("No se puede generar el reporte", "Debe seleccionar una Factura");

					}
				}
				}


		@Override
		public void insertar() {
			// TODO Auto-generated method stub
			tab_nota_credito.isFocus();
			tab_nota_credito.insertar();

		}

		@Override
		public void guardar() {
			// TODO Auto-generated method stub
			tab_nota_credito.guardar();
			guardarPantalla();

		}

		@Override
		public void eliminar() {
			// TODO Auto-generated method stub
			tab_nota_credito.eliminar();

		}

		public Tabla getTab_nota_credito() {
			return tab_nota_credito;
		}

		public void setTab_nota_credito(Tabla tab_nota_credito) {
			this.tab_nota_credito = tab_nota_credito;
		}

		public SeleccionTabla getSet_factura() {
			return set_factura;
		}

		public void setSet_factura(SeleccionTabla set_factura) {
			this.set_factura = set_factura;
		}
		public SeleccionTabla getSet_actualizarfactura() {
			return set_actualizarfactura;
		}
		public void setSet_actualizarfactura(SeleccionTabla set_actualizarfactura) {
			this.set_actualizarfactura = set_actualizarfactura;
		}
		public Confirmar getCon_guardar() {
			return con_guardar;
		}
		public void setCon_guardar(Confirmar con_guardar) {
			this.con_guardar = con_guardar;
		}
		public Reporte getRep_reporte() {
			return rep_reporte;
		}
		public void setRep_reporte(Reporte rep_reporte) {
			this.rep_reporte = rep_reporte;
		}
		public SeleccionFormatoReporte getSelf_reporte() {
			return self_reporte;
		}
		public void setSelf_reporte(SeleccionFormatoReporte self_reporte) {
			this.self_reporte = self_reporte;
		}
		public Map getMap_parametros() {
			return map_parametros;
		}
		public void setMap_parametros(Map map_parametros) {
			this.map_parametros = map_parametros;
		}
		public Map getP_parametros() {
			return p_parametros;
		}
		public void setP_parametros(Map p_parametros) {
			this.p_parametros = p_parametros;
		}
		
		


	}
