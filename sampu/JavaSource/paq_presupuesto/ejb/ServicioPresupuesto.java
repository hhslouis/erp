package paq_presupuesto.ejb;


import javax.ejb.Stateless;

import paq_sistema.aplicacion.Utilitario;

import framework.aplicacion.TablaGenerica;

/**
 * Session Bean implementation class ServicioPresupuesto
 */
@Stateless

public class ServicioPresupuesto {
	private Utilitario utilitario= new Utilitario();
	/**
	 * Metodo que devuelve el Catalogo Presupuestario por los años vigentes
	 * @param estado recibe el o los estados true y false, ejemplo: true o false
	 * @param ide_geani recibe el año para filtar el ctalaogo presupuestario 
	 * @return String SQL Clasificador Presupuestario
	 */
 public String getCatalogoPresupuestarioAnio(String estado,String ide_geani){
	 
	 String tab_presupesto="SELECT a.ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla " +
	 		" FROM pre_clasificador a,cont_vigente b,gen_anio c where a.ide_prcla = b.ide_prcla" +
			" and b.ide_geani= c.ide_geani and b.ide_geani ="+ide_geani +
			" and activo_prcla in ("+estado+")order by codigo_clasificador_prcla";
	 	 return tab_presupesto;
			 
 }

	/**
	 * Metodo que devuelve el Catalogo Presupuestario para de esta manera desplegar en los autocompletar del aplicativo de esta manera no saturamos los combos de consulta
	 * @param estado recibe el o los estados true y false, ejemplo: true o false
	 * @return String SQL Clasificador Presupuestario solo para consulta de autompletar
	 */
 public String getCatalogoPresupuestario(String activo){
	 
	 String tab_presupesto="SELECT ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla " +
	 		" FROM pre_clasificador where activo_prcla in (" +activo+")"+
				" ORDER BY codigo_clasificador_prcla";
	 return tab_presupesto;
			 
 }
 public TablaGenerica getTablaCatalogoPresupuestario(String ideClasificador){
	 
	 TablaGenerica tab_presupesto=utilitario.consultar("SELECT ide_prcla,codigo_clasificador_prcla,descripcion_clasificador_prcla " +
	 		" FROM pre_clasificador where ide_prcla=" +ideClasificador+
				" ORDER BY codigo_clasificador_prcla");
	 return tab_presupesto;
			 
 } 
 public String getFuncionPrograma(){
	String tab_funcion="select ide_prfup,detalle_prfup,codigo_prfup  " +
			"from pre_funcion_programa ";
	return tab_funcion;
	
}
TablaGenerica getTablaGenericaFuncionPro(String ideanio){
	TablaGenerica tab_funcion_progra=utilitario.consultar("select ide_prfup,detalle_prfup from pre_funcion_programa " +
			" where ide_prfup in (select ide_prfup from cont_vigente where ide_geani=" +ideanio+
			
			"	order by detalle_prfup");
	return tab_funcion_progra;
	
}
public String getTramite (String activo){
	String tab_tramite="select ide_prtra,numero_oficio_prtra from pre_tramite where activo_prtra in ("+activo+") " +
			" order by numero_oficio_prtra";
	return tab_tramite;
	
}

public TablaGenerica getTablaGenericaTramite (String ide_prtra ){
	TablaGenerica tab_tramite=utilitario.consultar("select ide_prtra,numero_oficio_prtra from pre_tramite where ide_prtra in ("+ide_prtra+") " +
			" order by numero_oficio_prtra");
	return tab_tramite;
}
public String getPoa (String activo){
	String tab_poa="select ide_prpoa,b.ide_prcla,b.codigo_clasificador_prcla,b.descripcion_clasificador_prcla " +
			" from pre_poa a,pre_clasificador b where a.ide_prcla=b.ide_prcla " +
			" and  activo_prpoa in("+activo+")order by descripcion_clasificador_prcla";
	return tab_poa;
}
public TablaGenerica getTablaGenericaPoa(String ide_prpoa) {
	TablaGenerica tab_poa=utilitario.consultar("select ide_prpoa,b.ide_prcla,b.codigo_clasificador_prcla,b.descripcion_clasificador_prcla " +
			" from pre_poa a,pre_clasificador b where a.ide_prcla=b.ide_prcla " +
			" and  ide_prpoa in("+ide_prpoa+")order by descripcion_clasificador_prcla");
	return tab_poa;
	
}
public String getPrograma (String activo){
	String tab_programa=("select  ide_prpro,cod_programa_prpro from pre_programa" +
			" where activo_prpro in ("+activo+") order by cod_programa_prpro");
	return tab_programa;
	
}
public TablaGenerica getTablaGenericaPrograma(String ide_prpro){
	TablaGenerica tab_programa=utilitario.consultar("select  ide_prpro,cod_programa_prpro from pre_programa where ide_prpro in ("+ide_prpro+")" +
			" order by cod_programa_prpro");
	return tab_programa;
}
}
