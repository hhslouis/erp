package paq_bodega.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;

import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Utilitario;

/**
 * Session Bean implementation class ServicioBodega
 */

@Stateless
@LocalBean

public class ServicioBodega {

	/**
	 * Default constructor. 
	 */
	private Utilitario utilitario=new Utilitario();
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);

	public TablaGenerica getTablaInventario (String codigo){

		TablaGenerica tab_inventario = utilitario.consultar("select ide_bomat, codigo_bomat,detalle_bomat " +
				" from bodt_material " +
				" WHERE ide_bomat in ("+codigo+") " +
				" ORDER BY detalle_bomat");
		return tab_inventario;

	}
	/**
	 * Este servicio retorna los materiales existentes en el cálogo de materiales 
	 * @param activo
	 * @return
	 */
	public String getInventario (String grupo,String activo,String ide_botip){

		String tab_inventario="select ide_bomat, codigo_bomat,detalle_bomat,detalle_bogrm " +
				"from bodt_material a,bodt_grupo_material b" +
				" WHERE activo_bomat in ("+activo+") AND a.ide_bogrm = b.ide_bogrm ";
		if(grupo.equals("0")){
		       tab_inventario +=" AND a.ide_bogrm = b.ide_bogrm AND a.ide_botip in("+ide_botip+") "	;
		}
		
		        tab_inventario +="ORDER BY detalle_bogrm,detalle_bomat";
			return tab_inventario;


	}
	/**
	 * Este servicio retorna los datos del inventario por material y año 
	 * @param material = Recibe el ide del material a consultar
	 * @param anio = Recibe año del inventario del material a conultar.
	 * @return String 
	 */
	public String getDatosInventario (String material,String anio){

		String tab_inventario="select ide_boinv,ide_geani,ingreso_material_boinv,egreso_material_boinv,existencia_inicial_boinv,costo_anterior_boinv,costo_actual_boinv,"
+" fecha_ingr_articulo_boinv,costo_inicial_boinv,ide_bomat from bodt_inventario where ide_geani ="+anio+" and ide_bomat ="+material;
	    return tab_inventario;

	}
	public TablaGenerica getTablaProveedor (String ide_tepro){
		TablaGenerica tab_proveedor=utilitario.consultar("select ide_tepro,nombre_tepro,ruc_tepro " +
				" from tes_proveedor where ide_tepro in ("+ide_tepro+")" +
				" order by nombre_tepro");
		return tab_proveedor;
	}
	
	public String getProveedor (String activo){
		String tab_proveedor="select ide_tepro,nombre_tepro,ruc_tepro " +
				" from tes_proveedor where activo_tepro in ("+activo+")" +
				" order by nombre_tepro";
		return tab_proveedor;
		
	}

public double getResultadoStock(String ide_bomat,String ide_geani){
	double  stock;
	TablaGenerica sql=utilitario.consultar("select 1 as codigo, inicial-egreso as stock from (select ingreso_material_boinv,egreso_material_boinv," +
			" (CASE WHEN ingreso_material_boinv is null THEN 0 ELSE ingreso_material_boinv end) as inicial," +
			" (CASE WHEN egreso_material_boinv is null THEN 0 ELSE egreso_material_boinv end)as egreso" +
			" from bodt_inventario" +
			" where ide_bomat in ("+ide_bomat+") and ide_geani in ("+ide_geani+")) a");
	
	stock=Double.parseDouble(sql.getValor("stock").toString());
	return stock;
   
}
}
