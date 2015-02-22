package paq_adquisicion;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_compras extends Pantalla{
	
	public static String par_estado_modulo_compra;
	private SeleccionTabla set_tipo_compra=new SeleccionTabla();

	private Tabla tab_compras=new Tabla();
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina ) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega ) utilitario.instanciarEJB(ServicioBodega.class);
	private Map p_parametros = new HashMap();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte self_reporte = new SeleccionFormatoReporte();
	private Map map_parametros = new HashMap();
	
	public pre_compras(){
		par_estado_modulo_compra =utilitario.getVariable("p_estado_modulo_compra");
		rep_reporte.setId("rep_reporte"); //id
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");//ejecuta el metodo al aceptar reporte
		agregarComponente(rep_reporte);//agrega el componente a la pantalla
		bar_botones.agregarReporte();//aparece el boton de reportes en la barra de botones
		self_reporte.setId("self_reporte"); //id
		agregarComponente(self_reporte);
		tab_compras.setId("tab_compras");
		tab_compras.setTabla("adq_solicitud_compra", "ide_adsoc", 1);
		tab_compras.getColumna("ide_coest").setCombo("cont_estado","ide_coest", "detalle_coest","");
		tab_compras.getColumna("ide_coest").setLectura(true);
		tab_compras.getColumna("ide_coest").setAutoCompletar();
		tab_compras.getColumna("ide_copag").setCombo("cont_parametros_general","ide_copag", "detalle_copag", "");
		tab_compras.getColumna("ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_compras.getColumna("gen_ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_compras.getColumna("ide_tepro").setCombo(ser_bodega.getProveedor("true,false"));
		tab_compras.getColumna("fecha_proforma2_adsoc").setVisible(false);
		tab_compras.getColumna("valor_proforma2_adsoc").setVisible(false);
		tab_compras.getColumna("factura_proforma2_adsoc").setVisible(false);
		tab_compras.getColumna("oferente2_adsoc").setVisible(false);
		tab_compras.getColumna("fecha_proforma1_adsoc").setVisible(false);
		tab_compras.getColumna("factura_proforma1_adsoc").setVisible(false);
		tab_compras.getColumna("valor_proforma1_adsoc").setVisible(false);
		tab_compras.getColumna("proforma_proveedor_adsoc").setVisible(false);
		tab_compras.getColumna("fecha_proforma_proveedor_adsoc").setVisible(false);
		tab_compras.getColumna("oferente1_adsoc").setVisible(false);

		tab_compras.setTipoFormulario(true);
		tab_compras.getGrid().setColumns(4);
		tab_compras.dibujar();
		PanelTabla pat_panel1=new PanelTabla();
		pat_panel1.setPanelTabla(tab_compras);
		agregarComponente(pat_panel1);
		
		
	}
	//reporte
	   public void abrirListaReportes() {
	   	// TODO Auto-generated method stub
	   	rep_reporte.dibujar();
	   }
	   public void aceptarReporte(){
	   	if(rep_reporte.getReporteSelecionado().equals("Solucitud Compra"));{
	   		if (rep_reporte.isVisible()){
	   			p_parametros=new HashMap();		
	   			rep_reporte.cerrar();	
	   			p_parametros.put("Titulo","Solucitud Compra");
	   			p_parametros.put("ide_usua",Integer.parseInt("7"));
	   			p_parametros.put("ide_empr",Integer.parseInt("0"));
	   			p_parametros.put("ide_sucu",Integer.parseInt("1"));
	   		//p_parametros.put("pide_fafac",Integer.parseInt(tab_cont_viajeros.getValor("ide_fanoc")));
	   		self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
	   		self_reporte.dibujar();
	   		
	   	}
	   }
	   }
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_compras.insertar();
		tab_compras.setValor("ide_coest",par_estado_modulo_compra);
		utilitario.addUpdate("tab_compras");
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_compras.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_compras.eliminar();
	}
	public Tabla getTab_compras() {
		return tab_compras;
	}
	public void setTab_compras(Tabla tab_compras) {
		this.tab_compras = tab_compras;
	}
	public Map getP_parametros() {
		return p_parametros;
	}
	public void setP_parametros(Map p_parametros) {
		this.p_parametros = p_parametros;
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

}
