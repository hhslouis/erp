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
	
	public TablaGenerica getTablaBodega (String tabla){
		
		TablaGenerica tab_bodega=utilitario.consultar("select a.ide_recli, a.aplica_mtarifa_recli " +
				"FROM rec_clientes a, tes_tarifas b " +
				"WHERE a.ide_tetar=b.ide_tetar and a.aplica_mtarifa_recli=true and ide_recli=1 and b.ide_tetar=1"+tabla+"'");
		return tab_bodega;
		
		}


	public ServicioFacturacion() {
		// TODO Auto-generated constructor stub
	}

}
