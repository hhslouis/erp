package paq_adquisicion;

import javax.ejb.EJB;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_compras extends Pantalla{

	private Tabla tab_compras=new Tabla();
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina ) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega ) utilitario.instanciarEJB(ServicioBodega.class);

	
	public pre_compras(){
		tab_compras.setId("tab_compras");
		tab_compras.setTabla("adq_solicitud_compra", "ide_adsoc", 1);
		tab_compras.getColumna("ide_coest").setCombo("cont_estado","ide_coest", "detalle_coest","");
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
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_compras.insertar();
		
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

}
