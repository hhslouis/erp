package paq_contabilidad.ejb;

import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;
import paq_sistema.aplicacion.Utilitario;
@Stateless

public class ServicioContabilidad {
	
	private Utilitario utilitario=new Utilitario();
	
	public String getVigente (String codigo){
		
		String tab_vigente="SELECT tc.constraint_name, " +
				"tc.table_name, kcu.column_name," +
				" ccu.table_name AS foreign_table_name," +
				"ccu.column_name AS foreign_column_name " +
				"FROM information_schema.table_constraints AS tc " +
				"JOIN information_schema.key_column_usage AS kcu " +
				"ON tc.constraint_name = kcu.constraint_name " +
				"JOIN information_schema.constraint_column_usage AS ccu " +
				"ON ccu.constraint_name = tc.constraint_name " +
				"WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name='cont_vigente'" +"codigo";
				
		return tab_vigente;
		
	}
public TablaGenerica getTablaVigente (String tabla){
	
	TablaGenerica tab_vigente=utilitario.consultar("SELECT tc.constraint_name, " +
			"tc.table_name, kcu.column_name, " +
			" ccu.table_name AS foreign_table_name, " +
			"ccu.column_name AS foreign_column_name " +
			"FROM information_schema.table_constraints AS tc " +
			"JOIN information_schema.key_column_usage AS kcu " +
			"ON tc.constraint_name = kcu.constraint_name " +
			"JOIN information_schema.constraint_column_usage AS ccu " +
			"ON ccu.constraint_name = tc.constraint_name " +
			"WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name='"+tabla+"'");
	return tab_vigente;
	
	}

public String getAnio (String activo, String bloqueado ){
	String tab_anio= "select ide_geani,detalle_geani from gen_anio " +
			" where activo_geani in ("+activo+") " +
			" and bloqueado_geani in ("+bloqueado+") " +
			" order by detalle_geani desc";
	return tab_anio;

	}
public TablaGenerica getTablaAnio (String activo, String bloqueado ){
	TablaGenerica tab_anio= utilitario.consultar("select ide_geani,detalle_geani from gen_anio " +
			" where activo_geani in ("+activo+") " +
			" and bloqueado_geani in ("+bloqueado+") " +
			" order by detalle_geani desc");
	return tab_anio;
	}
public String getAnioDetalle (String activo, String bloqueado ){
	String tab_anio= "select ide_geani,detalle_geani, " +
			" CASE WHEN activo_geani = true THEN 'Activo' ELSE 'Inactivo' END AS activo_geani," +
			" CASE WHEN bloqueado_geani = true THEN 'Bloqueado' ELSE 'Habilitado' END AS bloqueado_geani" +
			" from gen_anio where activo_geani in("+activo+")" +
			" and bloqueado_geani in ("+bloqueado+")" +
			" order by detalle_geani desc" ;
			
	return tab_anio;

	}
public TablaGenerica getTablaAnioDetalle (String activo, String bloqueado ){
	TablaGenerica tab_anio= utilitario.consultar("select ide_geani,detalle_geani," +
			" CASE WHEN activo_geani = true THEN 'Activo' ELSE 'Inactivo' END AS activo_geani," +
			" CASE WHEN bloqueado_geani = true THEN 'Bloqueado' ELSE 'Habilitado' END AS bloqueado_geani" +
			" from gen_anio where activo_geani in("+activo+")  " +
			" and bloqueado_geani in ("+bloqueado+")" +
			" order by detalle_geani desc");
	return tab_anio;
	}
/**
 * Metodo que devuelve los Estados clasificados por modulo 
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param modulo recibe el codigo del Modulo por el cual desea clasificar el estado 
 * @return string SQL modulo estado
 */
public TablaGenerica getTablaModuloEstados (String estado,String modulo){
	
	TablaGenerica tab_estados=utilitario.consultar("SELECT a.ide_coest,detalle_coest,detalle_gemod "
			+"FROM gen_modulo_estado a,cont_estado b, gen_modulo c "
			+"WHERE a.ide_coest= b.ide_coest AND a.ide_gemod = c.ide_gemod "
			+"AND activo_gemoe in ("+estado+") AND a.ide_gemod ="+modulo+" order by detalle_coest");
		return tab_estados;
}

public String getModuloEstados (String estado,String modulo){
	String consultaEstados="SELECT a.ide_coest,detalle_coest,detalle_gemod "
+"FROM gen_modulo_estado a,cont_estado b, gen_modulo c "
+"WHERE a.ide_coest= b.ide_coest AND a.ide_gemod = c.ide_gemod "
+"AND activo_gemoe in ("+estado+") AND a.ide_gemod ="+modulo+" order by detalle_coest";
	return consultaEstados;
}
/**
 * Metodo que devuelve los Parametros generales requeridos por modulo
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param modulo recibe el codigo del modulo 
 * @return string SQL gen_modulo
 */
public String getModuloParametros (String estado,String modulo){
	String consultaEstados="select ide_copag,detalle_copag from cont_parametros_general" 
+" where ide_copag in (select ide_copag from cont_parametro_modulo where ide_gemod in ("+modulo+") and activo_copam in ("+estado+") )";
	System.out.println("modulo parametro"+consultaEstados);
	return consultaEstados;
}
/**
 * Metodo que devuelve los tipos de convenios 
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param modulo recibe el codigo del de los tipos de convenios 
 * @return string SQL tipo de convenio
 */
public TablaGenerica getTablaTipoConvenio (String activo,String detalle){
	TablaGenerica tab_tipo_convenio= utilitario.consultar("Select ide_cotie ,detalle_cotie " +
			" from cont_tipo_convenio" +
			"  where activo_cotie in ( true, false) order by detalle_cotie");
	
		return tab_tipo_convenio;
}

public String getTipoConvenio (String activo, String detalle){
	String tipo_convenio ="Select ide_cotie ,detalle_cotie " +
			" from cont_tipo_convenio" +
			"  where activo_cotie in ( true, false) order by detalle_cotie";
		
	return tipo_convenio ;

}
public TablaGenerica getTablaInstitucion ( String activo, String detalle){
	TablaGenerica tab_institucion= utilitario.consultar("Select ide_geins,detalle_geins" +
			" from gen_institucion" +
			" where activo_geins in (true, false) order by detalle_geins");
	
	return tab_institucion;	
	
	}
public String getInstitucion (String activo, String detalle){
	String institucion ="Select ide_geins,detalle_geins "+
			" from gen_institucion" +
			" where activo_geins in (true, false)  order by detalle_geins";
	
	return institucion;
}
/**
 * Metodo que devuelve la Cuenta Cantable
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param modulo recibe las cuentas contables activas o pasivas 
 * @return string SQL Cuenta Contable 
 */

public TablaGenerica getTablaCuentaContable ( String activo){
	TablaGenerica tab_cuenta_contable= utilitario.consultar("select ide_cocac,cue_codigo_cocac,cue_descripcion_cocac" +
			"  from cont_catalogo_cuenta " +
			"  WHERE activo_cocac IN ("+activo+")order by cue_codigo_cocac ");
	
	return tab_cuenta_contable;	
}
public String getCuentaContable (String activo){
	String cuentacontable ="select ide_cocac,cue_codigo_cocac,cue_descripcion_cocac" +
			"  from cont_catalogo_cuenta " +
			"  WHERE activo_cocac IN ("+activo+")order by cue_codigo_cocac ";
	return cuentacontable;
}

/**
 * Metodo que devuelve la Cuenta Cantable por los años vigentes
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param ide_geani recibe el año de las cuentas contables 
 * @return TablaGenerica Cuenta Contable 
 */
public TablaGenerica getTablaCatalogoCuentaAnio (String estado,String ide_geani){
	
	TablaGenerica tab_catalogo_cuenta_anio=utilitario.consultar("select a.ide_cocac,cue_codigo_cocac,cue_descripcion_cocac," +
			"detalle_geani" +
			"  from cont_catalogo_cuenta a,cont_vigente b,gen_anio c "+
			" where a.ide_cocac = b.ide_cocac" +
			" and b.ide_geani= c.ide_geani and b.ide_geani =("+ide_geani+")" +
			" and activo_coca in ("+estado+")order by cue_codigo_cocac");
	return tab_catalogo_cuenta_anio;
	
	
}
/**
 * Metodo que devuelve la Cuenta Cantable por los años vigentes
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param ide_geani recibe el año de las cuentas contables 
 * @return String SQL Cuenta Contable 
 */
public String servicioCatalogoCuentaAnio (String estado, String ide_geani){
	
	String catalogo_cuenta_anio="select a.ide_cocac,cue_codigo_cocac,cue_descripcion_cocac,detalle_geani " +
			" from cont_catalogo_cuenta a,cont_vigente b,gen_anio c where a.ide_cocac = b.ide_cocac" +
			" and b.ide_geani= c.ide_geani and b.ide_geani ="+ide_geani +
			" and activo_cocac in ("+estado+")order by cue_codigo_cocac";
	return catalogo_cuenta_anio;
}
public String getInventario(String ide_geani,String carga,String material){
	
	String tab_inventario="SELECT ide_boinv,codigo_bomat,detalle_bomat, detalle_geani,ingreso_material_boinv,egreso_material_boinv,existencia_inicial_boinv,costo_anterior_boinv," +
			"costo_actual_boinv,fecha_ingr_articulo_boinv,costo_inicial_boinv,activo_boinv " +
			" FROM bodt_inventario a, gen_anio b ,bodt_material c WHERE a.ide_geani=b.ide_geani " +
			" AND a.ide_bomat = c.ide_bomat AND a.ide_geani IN ("+ide_geani+") ";
			if (carga.equals("0")){
				tab_inventario +=" and a.ide_bomat= "+material;
			}
			    tab_inventario += " ORDER by ide_boinv";	

	return tab_inventario;
}

/**
 * Metodo que devuelve la Cuenta Cantable por realizaqr transacciones filtra cuentas contables por año fiscal vigente
 * @return String SQL Cuenta Contable para realizar transacciones
 */
public String servicioCatalogoCuentasTransaccion(){
	
	String catalogo_cuenta_anio="select ide_cocac,cue_codigo_cocac,substring (cue_descripcion_cocac from 1 for 100) as cuenta from cont_catalogo_cuenta  where not ide_cocac in ("
+" select con_ide_cocac from cont_catalogo_cuenta where not con_ide_cocac is null group by con_ide_cocac )" 
+" and ide_cocac in ( select ide_cocac from cont_vigente where not ide_cocac is null and ide_geani in (select ide_geani from gen_anio where activo_geani = true limit 1) )"
+" order by cue_codigo_cocac";
	return catalogo_cuenta_anio;
}


}

