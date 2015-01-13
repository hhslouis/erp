package paq_facturacion.ejb;

import javax.ejb.Stateless;

import paq_sistema.aplicacion.Utilitario;

import framework.aplicacion.TablaGenerica;


/**
 * Session Bean implementation class ServicioFacturacion
 */
@Stateless

public class ServicioFacturacion {
	private Utilitario utilitario=new Utilitario();


	public String getClientes( ){
	    String tab_cliente="select a.ide_recli, ruc_comercial_recli,nombre_comercial_recli," +
	            "  nro_establecimiento_recli, codigo_zona_recli, " +
	            " telefono_factura_recli, direccion_recld from rec_clientes a " +
	            " LEFT JOIN rec_cliente_direccion b on a.ide_recli=b.ide_recli " +
	                " ORDER BY  nombre_comercial_recli";
	    return tab_cliente;
	}


	public ServicioFacturacion() {
		// TODO Auto-generated constructor stub
	}

}
