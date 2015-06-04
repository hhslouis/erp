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
public String getPoa (String ide_geani){
	String tab_poa=("select a.ide_prpoa,codigo_clasificador_prcla,codigo_subactividad,detalle_programa,detalle_subactividad,descripcion_clasificador_prcla,programa," +
			" detalle_proyecto,proyecto,detalle_producto,producto,detalle_actividad,actividad," +
			" subactividad,fecha_inicio_prpoa,fecha_fin_prpoa,num_resolucion_prpoa,presupuesto_inicial_prpoa," +
			" presupuesto_codificado_prpoa,reforma_prpoa,detalle_geani,detalle_geare" +
			" from pre_poa a left join  gen_anio b on a.ide_geani= b.ide_geani left join pre_clasificador c on a.ide_prcla = c.ide_prcla left join " +
			" (select a.ide_prfup,codigo_subactividad,detalle_subactividad,subactividad,detalle_actividad,actividad,detalle_producto,producto,detalle_proyecto," +
			" proyecto,detalle_programa ,programa from (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_subactividad,detalle_prfup as detalle_subactividad," +
			" detalle_prnfp as subactividad from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =5) a , " +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_actividad,detalle_prfup as detalle_actividad,detalle_prnfp as actividad" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =4) b, " +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_producto,detalle_prfup as detalle_producto,detalle_prnfp as producto" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =3 ) c, " +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_proyecto,detalle_prfup as detalle_proyecto,detalle_prnfp as proyecto" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =2) d, " +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_programa,detalle_prfup as detalle_programa,detalle_prnfp as programa" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =1) e where a.pre_ide_prfup = b.ide_prfup" +
			" and b.pre_ide_prfup = c.ide_prfup and c.pre_ide_prfup = d.ide_prfup and d.pre_ide_prfup = e.ide_prfup ) f on a.ide_prfup = f.ide_prfup" +
			" left join gen_area g on a.ide_geare=g.ide_geare where a.ide_geani= "+ide_geani+" order by codigo_subactividad,a.ide_prpoa");
		return tab_poa;
}  
public String getPoaTodos (){
	String tab_poa=("select a.ide_prpoa,codigo_clasificador_prcla,codigo_subactividad,detalle_subactividad,descripcion_clasificador_prcla from pre_poa a" +
			" left join  gen_anio b on a.ide_geani= b.ide_geani left join pre_clasificador c on a.ide_prcla = c.ide_prcla left join " +
			" (select a.ide_prfup,codigo_subactividad,detalle_subactividad,subactividad,detalle_actividad,actividad,detalle_producto,producto,detalle_proyecto," +
			" proyecto,detalle_programa ,programa from (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_subactividad,detalle_prfup as detalle_subactividad," +
			" detalle_prnfp as subactividad from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =5) a ," +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_actividad,detalle_prfup as detalle_actividad,detalle_prnfp as actividad" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =4) b," +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_producto,detalle_prfup as detalle_producto,detalle_prnfp as producto" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =3 ) c," +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_proyecto,detalle_prfup as detalle_proyecto,detalle_prnfp as proyecto" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =2) d," +
			" (select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_programa,detalle_prfup as detalle_programa,detalle_prnfp as programa" +
			" from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =1) e where a.pre_ide_prfup = b.ide_prfup" +
			" and b.pre_ide_prfup = c.ide_prfup and c.pre_ide_prfup = d.ide_prfup and d.pre_ide_prfup = e.ide_prfup ) f on a.ide_prfup = f.ide_prfup" +
			" left join gen_area g on a.ide_geare=g.ide_geare  order by codigo_subactividad,a.ide_prpoa");

		return tab_poa;
}
public TablaGenerica getTablaGenericaPoa(String ide_prpoa) {
	TablaGenerica tab_poa=utilitario.consultar("select ide_prpoa,b.ide_prcla,b.codigo_clasificador_prcla,b.descripcion_clasificador_prcla,presupuesto_codificado_prpoa " +
			" from pre_poa a,pre_clasificador b where a.ide_prcla=b.ide_prcla " +
			" and  ide_prpoa in("+ide_prpoa+")order by descripcion_clasificador_prcla");
	return tab_poa;
	
}
public String getSaldoPoa(String ide_prpoa){
	String tab_programa=("select a.ide_prpoa,presupuesto_codificado_prpoa - (case when valor_certificado_prpoc is null then 0 else valor_certificado_prpoc end) as saldo_poa"
+" from pre_poa a"
+" left join (select sum(valor_certificado_prpoc) as valor_certificado_prpoc,ide_prpoa from pre_poa_certificacion group by ide_prpoa) b on a.ide_prpoa = b.ide_prpoa"
+" where a.ide_prpoa = "+ide_prpoa);
	return tab_programa;
	
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
public String getCertificacion(String activo){
	String tab_certificacion=("select ide_prcer,num_documento_prcer,detalle_prcer,fecha_prcer " +
			"from pre_certificacion where activo_prcer  in ("+activo+") order by num_documento_prcer");
	return tab_certificacion;
	
}
/**
 * Metodo que devuelve el POA a ser aprobado para la generacion del Presupuesto Inicial de Gastos
 * @param estado recibe el o los estados true y false, ejemplo: true o false
 * @param ide_geani recibe el año para filtar el POA 
 * @return String SQL POA
 */
public String getPoaPorAprobarse(String estado,String ide_geani){
 
 String tab_presupesto="select ide_prpoa,a.ide_prcla,a.ide_prfup,presupuesto_inicial_prpoa,codigo_clasificador_prcla,codigo_subactividad,descripcion_clasificador_prcla,"
+" detalle_subactividad,detalle_actividad,d.ide_geani,detalle_geani"
+" from pre_poa a"
+" left join ( "
+" select a.ide_prfup,codigo_subactividad,detalle_subactividad,subactividad,detalle_actividad,actividad,"
+" detalle_producto,producto,detalle_proyecto,proyecto,detalle_programa ,programa"
+" from ("
+" select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_subactividad,detalle_prfup as detalle_subactividad,detalle_prnfp as subactividad"
+" from pre_funcion_programa a, pre_nivel_funcion_programa b"
+" where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =5"
+" ) a , ("
+" select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_actividad,detalle_prfup as detalle_actividad,detalle_prnfp as actividad"
+" from pre_funcion_programa a, pre_nivel_funcion_programa b"
+" where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =4"
+" ) b, ("
+" select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_producto,detalle_prfup as detalle_producto,detalle_prnfp as producto"
+" from pre_funcion_programa a, pre_nivel_funcion_programa b"
+" where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =3"
+" ) c, ("
+" select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_proyecto,detalle_prfup as detalle_proyecto,detalle_prnfp as proyecto"
+" from pre_funcion_programa a, pre_nivel_funcion_programa b"
+" where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =2"
+" ) d, ("
+" select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_programa,detalle_prfup as detalle_programa,detalle_prnfp as programa"
+" from pre_funcion_programa a, pre_nivel_funcion_programa b"
+" where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =1"
+" ) e"
+" where a.pre_ide_prfup = b.ide_prfup"
+" and b.pre_ide_prfup = c.ide_prfup"
+" and c.pre_ide_prfup = d.ide_prfup"
+" and d.pre_ide_prfup = e.ide_prfup"
 +" ) b on a.ide_prfup = b.ide_prfup"
+" left join pre_clasificador c on a.ide_prcla = c.ide_prcla" 
+" left join ( select a.ide_geani,ide_prfup,detalle_geani from cont_vigente a, gen_anio b where  a.ide_geani = b.ide_geani" 
+" and not ide_prfup is null order by detalle_geani desc"
  +" ) d on a.ide_prfup = d.ide_prfup"
+" where activo_prpoa in ("+estado+") and ide_prpro is null and d.ide_geani ="+ide_geani
+" order by codigo_clasificador_prcla,codigo_subactividad";
 	 return tab_presupesto;
		 
}

}
