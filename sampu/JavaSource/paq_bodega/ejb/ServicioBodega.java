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
		        System.out.println("material servicio "+tab_inventario);
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
	/**
	 * Metodo que permite obtener el inventario con su descripcion de material  
	 * @return String SQL, codigo del inventario, nombre del material, codigo del material.
	 */	
	public String getInventarioMaterial (){
		String tab_proveedor="select ide_boinv,codigo_bomat,detalle_bomat from bodt_inventario a, bodt_material b"
+" where a.ide_bomat = b.ide_bomat order by codigo_bomat";
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
/**
 * Metodo que permite registrar de forma automatica la cantidad de material ingresados al stock de inventarios  
 * @param material recibe el codigo del material de bodega
 * @param anio recibe el anio fiscal en el que esea ingresra al inventario el material. 
 * @param cantidad_ingreso recibe  la cantidad de material  a ser ingresado a los inventarios. 
 * @param valor_total recibe el valor total del material  a ser ingresado a los inventarios. 
 * @return boolean True = si se registro con exito en el inventario False= si existio algun error al actualizar inventarios.
 */
public boolean registraInventarioIngresos(String material,String anio,String cantidad_ingreso,String valor_total){
	boolean confirma=false;
	double stock=0;
	TablaGenerica datos_inventario=utilitario.consultar(getDatosInventario(material, anio));
	double costo_actual=Double.parseDouble(datos_inventario.getValor("costo_actual_boinv"));
    stock=getResultadoStock(material, anio);
	// Permite conocer el valor actual de mi stock
    double valor_stock_inventario =costo_actual*stock;
    //Cantidad actual ingresada a inventarios desde el formulario
    double cantidad_actual=Double.parseDouble(cantidad_ingreso);
    //Valor total actual ingresado para inventarios desde el formulario
    double valor_actual=Double.parseDouble(valor_total);
    // Permite suma stock existen mas el numero actual de ingreso de materiales;
    double total_nueva_existencia=stock+cantidad_actual;
    // Permite Sumar Valores para depues determinar mi costo vigente por producto
    double valor_nueva_existencia=valor_stock_inventario+valor_actual;
    // Determino el valor indivual nuevo del producto
    double valor_nuevo_individual=valor_nueva_existencia/total_nueva_existencia;

    String sql_actualiza_inventarios="update bodt_inventario set ingreso_material_boinv=ingreso_material_boinv+"+cantidad_actual
    		+",costo_anterior_boinv=costo_actual_boinv, costo_actual_boinv="+valor_nuevo_individual+" where ide_bomat="+material+" and ide_geani="+anio+";";

    String resultado_upadte="";
    resultado_upadte=utilitario.getConexion().ejecutarSql(sql_actualiza_inventarios);
    if (resultado_upadte.isEmpty()) {
    	confirma=true;
     }
	return confirma;
}
/**
 * Metodo que permite registrar de forma automatica la cantidad de material egresado  
 * @param material recibe el codigo del material de bodega
 * @param cantidad_egreso recibe  la cantidad de material  a ser egresado a los inventarios. 
 * @return boolean True = si se registro con exito en el inventario False= si existio algun error al actualizar inventarios.
 */
public boolean registraInventarioEgresos(String material,String cantidad_egreso){
	boolean confirma=false;	
	//Sql actualiza los egresos en inventarios
    String sql_actualiza_inventarios="update bodt_inventario set egreso_material_boinv = egreso_material_boinv + "+cantidad_egreso+" where ide_boinv ="+material+";";

    String resultado_upadte="";
    resultado_upadte=utilitario.getConexion().ejecutarSql(sql_actualiza_inventarios);
    if (resultado_upadte.isEmpty()) {
    	confirma=true;
     }
	return confirma;
}
public String getSolicitud(String activo){
	String tab_solicitud="select ide_adsoc,detalle_adsoc,nro_solicitud_adsoc,valor_adsoc,nombre_tepro,ruc_tepro " +
			" from adq_solicitud_compra a , tes_proveedor b" +
			" where a.ide_tepro=b.ide_tepro and activo_adsoc in("+activo+") order by nro_solicitud_adsoc";
	return tab_solicitud;
}
public TablaGenerica getTablaGenericaSolicitudCompra(String ide_addef){
	TablaGenerica tab_solicitud_comp=utilitario.consultar("select b.ide_addef, a.ide_adsoc,detalle_adsoc,num_factura_adfac,valor_adsoc,nro_solicitud_adsoc,valor_total_addef,valor_unitario_addef,cantidad_addef," +
				" codigo_bomat,detalle_bomat from adq_solicitud_compra a,adq_detalle_factura b,adq_factura c , bodt_material d" +
				" where a.ide_adsoc=c.ide_adsoc and b.ide_adfac=c.ide_adfac  and d.ide_bomat=b.ide_bomat  b.ide_addef in ("+ide_addef+") order by codigo_bomat");
	return tab_solicitud_comp;
}
}
