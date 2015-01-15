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
public TablaGenerica getTablaCatalogoCuentaAnio (String ide_geani){
	
	TablaGenerica tab_catalogo_cuenta_anio=utilitario.consultar("select a.ide_cocac,cue_codigo_cocac,cue_descripcion_cocac," +
			"detalle_geani" +
			"  from cont_catalogo_cuenta a,cont_vigente b,gen_anio c "+
			" where a.ide_cocac = b.ide_cocac" +
			" and b.ide_geani= c.ide_geani and b.ide_geani =("+ide_geani+")" +
			" order by cue_codigo_cocac");
	return tab_catalogo_cuenta_anio;
	
	
}
public String servicioCatalogoCuentaAnio (String ide_geani){
	
	String catalogo_cuenta_anio="select a.ide_cocac,cue_codigo_cocac,cue_descripcion_cocac,detalle_geani " +
			" from cont_catalogo_cuenta a,cont_vigente b,gen_anio c where a.ide_cocac = b.ide_cocac" +
			" and b.ide_geani= c.ide_geani and b.ide_geani ="+ide_geani +
			" order by cue_codigo_cocac";	
	System.out.println("consluat "+catalogo_cuenta_anio);
	return catalogo_cuenta_anio;
}




}

