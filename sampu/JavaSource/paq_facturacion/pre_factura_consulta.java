
package paq_facturacion;

import javax.ejb.EJB;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_facturacion.ejb.ServicioFacturacion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_factura_consulta extends Pantalla{
	private Tabla tab_factura_consulta = new Tabla();
	@EJB
	private ServicioFacturacion ser_facturacion = (ServicioFacturacion ) utilitario.instanciarEJB(ServicioFacturacion.class);
	@EJB
	private ServicioNomina ser_empleado = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	public pre_factura_consulta(){
		tab_factura_consulta.setId("tab_factura_consulta");
		tab_factura_consulta.setTabla("fac_factura", "ide_fafac", 1);
		tab_factura_consulta.getColumna("ide_fadaf").setCombo(ser_facturacion.getDatosFactura("1",""));
		tab_factura_consulta.getColumna("ide_comov").setCombo("cont_movimiento", "ide_comov", "detalle_comov", "");
		tab_factura_consulta.getColumna("ide_gtemp").setCombo(ser_empleado.servicioEmpleadosActivos("true,false"));
		tab_factura_consulta.getColumna("ide_tedar").setCombo(ser_facturacion.getDatosFactura("1",""));
		tab_factura_consulta.getColumna("ide_retip").setCombo("rec_tipo", "ide_retip", "detalle_retip", "");
		//tab_factura_consulta.getColumna("ide_recli").setCombo("SELECT ruc_comercial_recli FROM rec_clientes ");
		//tab_factura_consulta.getColumna("ide_recli").setCombo("SELECT nombre_comercial_recli FROM rec_clientes ");
		//tab_factura_consulta.getColumna("ide_recli").setCombo(ser_facturacion.getClientes("0,1"));
		tab_factura_consulta.getColumna("ide_tetid").setCombo("tes_tipo_documento", "ide_tetid", "detalle_tetid","");
		tab_factura_consulta.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest","");
		tab_factura_consulta.getColumna("ide_geins").setCombo("gen_institucion", "ide_geins", "detalle_geins","");
		
		tab_factura_consulta.getColumna("ide_fadaf").setLectura(true);
		tab_factura_consulta.getColumna("ide_fadaf").setLectura(true);
		tab_factura_consulta.getColumna("ide_tedar").setLectura(true);
		tab_factura_consulta.getColumna("ide_retip").setLectura(true);
		tab_factura_consulta.getColumna("ide_recli").setLectura(true);
		tab_factura_consulta.getColumna("ide_recli").setLectura(true);
		tab_factura_consulta.getColumna("ide_tetid").setLectura(true);
		tab_factura_consulta.getColumna("ide_tetid").setLectura(true);
		tab_factura_consulta.getColumna("ide_coest").setLectura(true);
		//tab_factura_consulta.getColumna("secuencia_fafac").setLectura(true);
		tab_factura_consulta.getColumna("total_fafac").setLectura(true);
		tab_factura_consulta.getColumna("valor_iva_fafac").setLectura(true);
		tab_factura_consulta.getColumna("base_no_iva_fafac").setLectura(true);
		tab_factura_consulta.getColumna("base_cero_fafac").setLectura(true);
		tab_factura_consulta.getColumna("base_aprobada_fafac").setLectura(true);
		tab_factura_consulta.getColumna("razon_anulado_fafac").setLectura(true);
		tab_factura_consulta.getColumna("ide_falug").setLectura(true);
		tab_factura_consulta.getColumna("codigo_faclinea_fafac").setLectura(true);
		tab_factura_consulta.getColumna("secuencial_fafac").setLectura(true);
		tab_factura_consulta.getColumna("observacion_fafac").setLectura(true);
		tab_factura_consulta.getColumna("responsable_faclinea_fafac").setLectura(true);
		tab_factura_consulta.getColumna("num_comprobante_fafac").setLectura(true);
		tab_factura_consulta.getColumna("factura_fisica_fafac").setLectura(true);
		tab_factura_consulta.getColumna("fecha_transaccion_fafac").setLectura(true);
		tab_factura_consulta.getColumna("valor_conciliado_fafac").setLectura(true);
		tab_factura_consulta.getColumna("fecha_pago_fafac").setLectura(true);
		tab_factura_consulta.getColumna("fecha_conciliado_fafac").setLectura(true);
		tab_factura_consulta.getColumna("documento_conciliado_fafac").setLectura(true);
		tab_factura_consulta.getColumna("documento_bancario_fafac").setLectura(true);
		tab_factura_consulta.getColumna("con_ide_coest").setLectura(true);

		

		tab_factura_consulta.getColumna("ide_fadaf").setFiltro(true);
		tab_factura_consulta.getColumna("ide_tedar").setFiltro(true);
		tab_factura_consulta.getColumna("ide_retip").setFiltro(true);
		tab_factura_consulta.getColumna("ide_tetid").setFiltro(true);
		tab_factura_consulta.getColumna("ide_coest").setFiltro(true);
		tab_factura_consulta.getColumna("ide_geins").setFiltro(true);

		
		tab_factura_consulta.getColumna("ide_comov").setVisible(false);
		tab_factura_consulta.getColumna("ide_gtemp").setVisible(false);
		tab_factura_consulta.getColumna("ide_geins").setVisible(false);
		tab_factura_consulta.getColumna("fecha_vencimiento_fafac").setVisible(false);
		tab_factura_consulta.getColumna("fecha_emision_fafac").setVisible(false);
		tab_factura_consulta.getColumna("direccion_fafac").setVisible(false);
		tab_factura_consulta.getColumna("fecha_anulado_fafac").setVisible(false);


		tab_factura_consulta.dibujar();
		tab_factura_consulta.setRows(10);

		PanelTabla pat_panel = new PanelTabla();
		pat_panel.setPanelTabla(tab_factura_consulta);
		
		Division div_tabla = new Division();
		div_tabla.dividir1(pat_panel);
		agregarComponente(div_tabla);
		
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

	public Tabla getTab_factura_consulta() {
		return tab_factura_consulta;
	}

	public void setTab_factura_consulta(Tabla tab_factura_consulta) {
		this.tab_factura_consulta = tab_factura_consulta;
	}
	
}