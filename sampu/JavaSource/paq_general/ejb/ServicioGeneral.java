package paq_general.ejb;

import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;
import paq_general.ejb.ServicioGeneral;
import paq_sistema.aplicacion.Utilitario;

@Stateless
public  class ServicioGeneral  {
	
	private Utilitario utilitario= new Utilitario();
	/**
	 * Metodo que devuelve los Tipo Persona por Modulo.
	 * @param estado recibe el estado Activo=true, Inactivo=false. puede ingresar los dos estados.
	 * @param modulo recibe el modulo para filtrar los Tipos de Persona, recibe un solo valor para modulo, ejemplo 1= ACtivos Fijos, 2=Convenis, etc.
	 * @return string SQL Tipo Persona
	 */
	public String getTipoPersona(String estado,String  modulo){
		
		String tab_tipo_persona= "select a.ide_getip,detalle_getip from gen_tipo_persona a, gen_tipo_persona_modulo b" +
				" where a.ide_getip=b.ide_getip and ide_gemod in ( "+modulo+") and activo_getpm in ("+estado+") order by detalle_getip";
		
		 return tab_tipo_persona;
		
	}
	/**
	 * Metodo que devuelve los Tipo Persona por Modulo.
	 * @param estado recibe el estado Activo=true, Inactivo=false. puede ingresar los dos estados.
	 * @param modulo recibe el modulo para filtrar los Tipos de Persona, recibe un solo valor para modulo, ejemplo 1= ACtivos Fijos, 2=Convenis, etc.
	 * @return Tabla Tipo Persona.
	 */
	public TablaGenerica getTablaTipoPersona(String estado,String modulo){
		
		TablaGenerica tab_tipo_persona=utilitario.consultar("select a.ide_getip,detalle_getip from gen_tipo_persona a, gen_tipo_persona_modulo b" +
				" where a.ide_getip=b.ide_getip and ide_gemod in ("+modulo+") and activo_getpm in ("+estado+") order by detalle_getip");
		 return tab_tipo_persona;
		 
	}

	/**
	 * Metodo que devuelve el distributivo de colaboradores.
	 * @param estado recibe el estado Activo=true, Inactivo=false. puede ingresar los dos estados.
	 * @param modulo recibe el modulo para filtrar los Tipos de Persona, recibe un solo valor para modulo, ejemplo 1= ACtivos Fijos, 2=Convenis, etc.
	 * @return string SQL Tipo Persona
	 */
	public String getDistributivoColaboradores(String fecha){
		
		String tab_distributivo= "select row_number() over(order by proceso,rmu_geedp) as num_registro, a.ide_gtemp,documento_identidad_gtemp,empleado,codigo_partida_gepap,"
+" fecha_ingreso_gtemp,'' as fecha_salida, titulo_cargo_gepgc,proceso,sub_proceso,grupo_ocupacional,rmu_geedp,detalle_gttem,detalle_gttco,"
+" lugar_trabajo,genero,detalle_gtted as formacion,detalle_gtttp as titulo,detalle_gttes as area_conocimiento,"
+" discapacitado,detalle_gttds,fecha_nacimiento_gtemp,observacion_geedp,fecha_entrega_declaracion_gtdce,fecha_proxima_declaracion_gtdce, 1 as orden"
+" from ("
+" select a.ide_gtemp,documento_identidad_gtemp,"
+" apellido_paterno_gtemp||' '||(case when apellido_materno_gtemp is null then '' else apellido_materno_gtemp end)||' '||primer_nombre_gtemp"
+" ||' '||(case when segundo_nombre_gtemp is null then '' else segundo_nombre_gtemp end) as empleado,codigo_partida_gepap,"
+" fecha_ingreso_gtemp, titulo_cargo_gepgc,detalle_geare as proceso,detalle_gedep as sub_proceso,"
+" detalle_gegro as grupo_ocupacional,rmu_geedp,detalle_gttem,detalle_gttco,nom_sucu as lugar_trabajo,codigo_sbs_gtgen as genero,"
+" (case when discapacitado_gtemp = true then 'SI' else 'NO' end) as discapacitado,fecha_nacimiento_gtemp,observacion_geedp"
+" from gth_empleado a,gen_empleados_departamento_par b, gen_partida_presupuestaria c,gen_partida_grupo_cargo d,gen_area e,"
+" gen_departamento f,gen_grupo_ocupacional g,gth_tipo_empleado h,gth_tipo_contrato i,sis_sucursal j,gth_genero k"
+" where a.ide_gtemp = b.ide_gtemp"
+" and activo_geedp = true"
+" and b.ide_gepgc = d.ide_gepgc"
+" and c.ide_gepap = d.ide_gepap"
+" and d.ide_geare = e.ide_geare"
+" and d.ide_gedep = f.ide_gedep"
+" and d.ide_gegro = g.ide_gegro"
+" and d.ide_gttem =h.ide_gttem"
+" and b.ide_gttco = i.ide_gttco"
+" and a.ide_gtgen = k.ide_gtgen"
+" and d.ide_sucu = j.ide_sucu"
+" and fecha_ingreso_gtemp < '"+fecha+"'"
+" ) a"
+" left join ("
+" select ide_gtemp,fecha_entrega_declaracion_gtdce,fecha_proxima_declaracion_gtdce from gth_documentacion_empleado where activo_gtdce = true limit 1"
+" ) b on a.ide_gtemp = b.ide_gtemp"
+" left join ("
+" select ide_gtemp,detalle_gtted,detalle_gtttp,detalle_gttes"
+" from gth_educacion_empleado a"
+" left join gth_tipo_educacion b on a.ide_gtted = b.ide_gtted"
+" left join gth_tipo_titulo_profesional c on a.ide_gtttp = c.ide_gtttp"
+" left join gth_tipo_especialidad d on a.ide_gttes = d.ide_gttes"
+" ) c on a.ide_gtemp = c.ide_gtemp"
+" left join ("
+" select ide_gtemp,detalle_gttds"
+" from gth_discapacidad_empleado a , gth_tipo_discapacidad b where a.ide_gttds = b.ide_gttds"
+" ) d on a.ide_gtemp = d.ide_gtemp"
+" union "
+" select null as num_registro,null as ide_gtemp,null as documento_identidad, 'VACANTE' as empleado,codigo_partida_gepap,"
+" null as fecha_ingreso,null as fecha_salida,titulo_cargo_gepgc,detalle_geare as proceso,detalle_gedep as sub_proceso,"
+" detalle_gegro as grupo_ocupacional,rmu_gegro,detalle_gttem,null as detalle_gttco,nom_sucu as lugar_trabajo,null as genero,"
+" null as formacion, null as titulo,null as area_conocimiento,null as discapacitado,null as detalle_gttds, null as fecha_nacimiento,"
+" null as observacion,null as fecha_entrega,null as fecha_proxima_entrega,2 as orden"
+" from gen_partida_grupo_cargo a,gen_partida_presupuestaria b,gen_area c,"
+" gen_departamento d,gen_grupo_ocupacional e,gth_tipo_empleado f,sis_sucursal h"
+" where a.ide_gepap = b.ide_gepap and a.ide_geare = c.ide_geare"
+" and a.ide_gedep = d.ide_gedep and a.ide_gegro = e.ide_gegro"
+" and a.ide_gttem = f.ide_gttem and a.ide_sucu = h.ide_sucu "
+" and activo_gepgc = true and vacante_gepgc = true"
+" union"
+" select null as num_registro, a.ide_gtemp,documento_identidad_gtemp,empleado,codigo_partida_gepap,"
+" fecha_ingreso_gtemp,fecha_finctr_geedp||'' as fecha_salida, titulo_cargo_gepgc,proceso,sub_proceso,grupo_ocupacional,rmu_geedp,detalle_gttem,detalle_gttco,"
+" lugar_trabajo,genero,detalle_gtted as formacion,detalle_gtttp as titulo,detalle_gttes as area_conocimiento,"
+" discapacitado,detalle_gttds,fecha_nacimiento_gtemp,observacion_geedp,fecha_entrega_declaracion_gtdce,fecha_proxima_declaracion_gtdce, 3 as orden"
+" from ("
+" select a.ide_gtemp,documento_identidad_gtemp,"
+" apellido_paterno_gtemp||' '||(case when apellido_materno_gtemp is null then '' else apellido_materno_gtemp end)||' '||primer_nombre_gtemp"
+" ||' '||(case when segundo_nombre_gtemp is null then '' else segundo_nombre_gtemp end) as empleado,codigo_partida_gepap,"
+" fecha_ingreso_gtemp, titulo_cargo_gepgc,detalle_geare as proceso,detalle_gedep as sub_proceso,"
+" detalle_gegro as grupo_ocupacional,rmu_geedp,detalle_gttem,detalle_gttco,nom_sucu as lugar_trabajo,codigo_sbs_gtgen as genero,"
+" (case when discapacitado_gtemp = true then 'SI' else 'NO' end) as discapacitado,fecha_nacimiento_gtemp,observacion_geedp,fecha_finctr_geedp"
+" from gth_empleado a,gen_empleados_departamento_par b, gen_partida_presupuestaria c,gen_partida_grupo_cargo d,gen_area e,"
+" gen_departamento f,gen_grupo_ocupacional g,gth_tipo_empleado h,gth_tipo_contrato i,sis_sucursal j,gth_genero k"
+" where a.ide_gtemp = b.ide_gtemp"
+" and activo_geedp = true"
+" and b.ide_gepgc = d.ide_gepgc"
+" and c.ide_gepap = d.ide_gepap"
+" and d.ide_geare = e.ide_geare"
+" and d.ide_gedep = f.ide_gedep"
+" and d.ide_gegro = g.ide_gegro"
+" and d.ide_gttem =h.ide_gttem"
+" and b.ide_gttco = i.ide_gttco"
+" and a.ide_gtgen = k.ide_gtgen"
+" and d.ide_sucu = j.ide_sucu"
+" and ide_geded in ("
+" select ide_geded from gen_detalle_empleado_departame where ide_geame in ("
+" select ide_geame from gen_accion_motivo_empleado where ide_geaed in ( select ide_geaed from gen_accion_empleado_depa where finiquito_contrato_geaed = true)"
+" )"
+" )"
+" and fecha_finctr_geedp between cast(extract(year from cast('"+fecha+"' as date))||'-'||extract(month from cast('"+fecha+"' as date))||'-01' as date) and '"+fecha+"'"
+" ) a"
+" left join ("
+" select ide_gtemp,fecha_entrega_declaracion_gtdce,fecha_proxima_declaracion_gtdce from gth_documentacion_empleado where activo_gtdce = true limit 1"
+" ) b on a.ide_gtemp = b.ide_gtemp"
+" left join ("
+" select ide_gtemp,detalle_gtted,detalle_gtttp,detalle_gttes"
+" from gth_educacion_empleado a"
+" left join gth_tipo_educacion b on a.ide_gtted = b.ide_gtted"
+" left join gth_tipo_titulo_profesional c on a.ide_gtttp = c.ide_gtttp"
+" left join gth_tipo_especialidad d on a.ide_gttes = d.ide_gttes"
+" ) c on a.ide_gtemp = c.ide_gtemp"
+" left join ("
+" select ide_gtemp,detalle_gttds"
+" from gth_discapacidad_empleado a , gth_tipo_discapacidad b where a.ide_gttds = b.ide_gttds"
+" ) d on a.ide_gtemp = d.ide_gtemp"
+" order by  proceso,rmu_geedp,num_registro";
		
		 return tab_distributivo;
		
	}
	
	public String getEjecucionActividadesPOA(String fecha_inicial,String fecha_final){
        String sql="select a.ide_prpoa,a.ide_prfup,detalle_programa,detalle_proyecto,detalle_producto, " +
		"detalle_subactividad,codigo_clasificador_prcla,descripcion_clasificador_prcla,   codigo_subactividad,b.detalle_geani,presupuesto_inicial_prpoa,reforma_prpoa, " +
		"presupuesto_codificado_prpoa,certificado,comprometido, devengado  " +
		"from pre_poa a " +
		"left join  gen_anio b on a.ide_geani= b.ide_geani " +
		"left join pre_clasificador c on a.ide_prcla = c.ide_prcla " +
		"left join ( " +
		"select a.ide_prfup,codigo_subactividad,detalle_subactividad,subactividad,detalle_actividad,actividad, " +
		"detalle_producto,producto,detalle_proyecto,proyecto,detalle_programa ,programa " +
		"from ( " +
		"select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_subactividad,detalle_prfup as detalle_subactividad,detalle_prnfp as subactividad " +
		"from pre_funcion_programa a, pre_nivel_funcion_programa b " +
		"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =5 " +
		") a , ( " +
		"select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_actividad,detalle_prfup as detalle_actividad,detalle_prnfp as actividad " +
		"from pre_funcion_programa a, pre_nivel_funcion_programa b " +
		"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =4 " +
		") b, ( " +
		"select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_producto,detalle_prfup as detalle_producto,detalle_prnfp as producto " +
		"from pre_funcion_programa a, pre_nivel_funcion_programa b " +
		"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =3 " +
		") c, ( " +
		"select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_proyecto,detalle_prfup as detalle_proyecto,detalle_prnfp as proyecto " +
		"from pre_funcion_programa a, pre_nivel_funcion_programa b " +
		"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =2 " +
		") d, ( " +
		"select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_programa,detalle_prfup as detalle_programa,detalle_prnfp as programa " +
		"from pre_funcion_programa a, pre_nivel_funcion_programa b " +
		"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =1 " +
		") e " +
		"where a.pre_ide_prfup = b.ide_prfup " +
		"and b.pre_ide_prfup = c.ide_prfup " +
		"and c.pre_ide_prfup = d.ide_prfup " +
		"and d.pre_ide_prfup = e.ide_prfup " +
		") f on a.ide_prfup = f.ide_prfup " +
		"left join gen_area g on a.ide_geare=g.ide_geare " +
		"left join (select sum(valor_certificado_prpoc) as certificado,ide_prpoa from pre_poa_certificacion group by ide_prpoa) h on a.ide_prpoa = h.ide_prpoa  " +
		"left join (select sum(comprometido_prpot) as comprometido,ide_prpoa from pre_poa_tramite group by ide_prpoa ) i on a.ide_prpoa = i.ide_prpoa "+
		"left join (select sum(devengado_prmen) as devengado, ide_prpoa from pre_mensual pm join pre_anual pa on pa.ide_pranu=pm.ide_pranu group by ide_prpoa ) dv on a.ide_prpoa = dv.ide_prpoa "+
		" where fecha_inicio_prpoa>='"+fecha_inicial+"' and fecha_fin_prpoa<='"+fecha_final+"' ";
			
		//System.out.println("consulta ejecucion subactividades: "+ sql);
		return sql;
	}
/*
 * 	@param fecha_inicial = fecha inicial desde cuando se genera el POA por fuente de Financiamiento
 *  @param fecha_final = fecha final hasta cuando se genera el POA por fuente de Financiamiento
 *  @param parametro = parametro por el cual se define los parametros de entrada al sql, ejemplo 0 Alex Becerra ingresa fechas, 1 Luis Toapanta ingresa POA y Fuente de Financiamiento
 *  @param ide_prpoa = recibe el ide del poa
 *  @param ide_prfuf = recibe el ide de la fuente de financiamiento 
 */
	public String getEjecucionPresupuestaria(String fecha_inicial,String fecha_final,String parametro,String ide_prpoa,String ide_prfuf){
		// Fecha final para sacar los datos del periodo
		
		String sql="select  a.ide_prpoa,pf.ide_prfuf, ";
			
			if(parametro.equals("0")){
				sql +="extract(month from DATE '"+fecha_final+"') as periodo, extract(year from DATE '"+fecha_final+"') as detalle_geani, ";
			}
				
				sql +="  detalle_programa,cod_pry,detalle_proyecto,cod_prod,detalle_producto,codigo_subactividad,detalle_subactividad,codigo_fuente_prfuf, detalle_prfuf as fuente ," +
				"  codigo_clasificador_prcla, descripcion_clasificador_prcla,   " +
				"  codificado as presupuesto_inicial_prpoa, " +
				"  case when reforma is null then 0.0 else reforma end as reforma_prpoa, " +
				"  (codificado + (case when reforma is null then 0.0 else reforma end)) as presupuesto_codificado_prpoa," +
				"  case when certificado is null then 0.0 else certificado end as certificado, " +
				"  (codificado + (case when reforma is null then 0.0 else reforma end))-(case when certificado is null then 0.0 else certificado end) as saldo_certificar," +
				"  case when comprometido is null then 0.0 else comprometido end as comprometido, " +
				"  (case when certificado is null then 0.0 else certificado end)-(case when comprometido is null then 0.0 else comprometido end) as saldo_comprometido, " +
				"  case when devengado is null then 0.0 else devengado end as devengado, " +
				"  (case when comprometido is null then 0.0 else comprometido end)-(case when devengado is null then 0.0 else devengado end) saldo_por_devengar  " +
				"  from pre_poa a  " +
				"  left join pre_clasificador c on a.ide_prcla = c.ide_prcla  " +
				"  left join ( select a.ide_prfup,codigo_subactividad,detalle_subactividad, detalle_producto, cod_prod, producto, detalle_proyecto, cod_pry, proyecto, detalle_programa, programa   " +
				"  from ( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_subactividad,detalle_prfup as detalle_subactividad,detalle_prnfp as subactividad from pre_funcion_programa a, pre_nivel_funcion_programa b  " +
				"  where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =5 ) a ,   " +
				"  ( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_actividad,detalle_prfup as detalle_actividad,detalle_prnfp as actividad  " +
				"  from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =4 ) b,    " +
				"  ( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_producto,detalle_prfup as detalle_producto,detalle_prnfp as producto, codigo_pry_prd_prfup as cod_prod   " +
				"  from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =3 ) c,       " +
				"  ( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_proyecto,detalle_prfup as detalle_proyecto,detalle_prnfp as proyecto, codigo_pry_prd_prfup as cod_pry   " +
				"  from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =2 ) d,   " +
				"  ( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_programa,detalle_prfup as detalle_programa,detalle_prnfp as programa   " +
				"  from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =1 ) e   " +
				"  where a.pre_ide_prfup = b.ide_prfup and b.pre_ide_prfup = c.ide_prfup and c.pre_ide_prfup = d.ide_prfup and d.pre_ide_prfup = e.ide_prfup ) f  on a.ide_prfup = f.ide_prfup " +
				"  left join (select sum(valor_financiamiento_prpof) as codificado, ide_prpoa, pf.ide_prfuf,codigo_fuente_prfuf,detalle_prfuf from pre_poa_financiamiento  pf    " +
				"  join pre_fuente_financiamiento ff on ff.ide_prfuf=pf.ide_prfuf   " +
				"  group by ide_prpoa, pf.ide_prfuf,codigo_fuente_prfuf,detalle_prfuf) pf on a.ide_prpoa = pf.ide_prpoa   " +
				"  left join (select sum(valor_reformado_prprf) as reforma, ide_prpoa, ide_prfuf from pre_poa_reforma_fuente  rf  " +
				"  group by ide_prpoa,ide_prfuf) rf on a.ide_prpoa = rf.ide_prpoa and pf.ide_prfuf=rf.ide_prfuf   " +
				"  left join (select sum(valor_certificado_prpoc) as certificado,ide_prpoa,ide_prfuf from pre_poa_certificacion group by ide_prpoa,ide_prfuf) h on a.ide_prpoa = h.ide_prpoa and pf.ide_prfuf=h.ide_prfuf  " +
				"  left join (select distinct sum(devengado_prmen) as devengado, ide_prpoa, pm.ide_prfuf from pre_mensual pm join pre_anual pa on pa.ide_pranu=pm.ide_pranu  " +
				"  where ide_prpoa>0 and devengado_prmen>0 ";
				if(parametro.equals("0")){
					sql += " and fecha_ejecucion_prmen between '"+fecha_inicial+"' and '"+fecha_final+"' group by ide_prpoa,pm.ide_prfuf) dv on a.ide_prpoa = dv.ide_prpoa and pf.ide_prfuf=dv.ide_prfuf ";
				}
				 
				 	sql += "  left join (select sum(comprometido_prpot) as comprometido,ide_prpoa,ide_prfuf from pre_poa_tramite group by ide_prpoa,ide_prfuf ) i on a.ide_prpoa = i.ide_prpoa and pf.ide_prfuf=i.ide_prfuf;";
				if(parametro.equals("1")){
					sql += " where a.ide_prpoa in ("+ide_prpoa+") and pf.ide_prfuf in ("+ide_prfuf+") ";
				}
		//System.out.println("consulta ejecucion presupuestaria: "+ sql);
		return sql;
	}
	
	public String getEjecucionPresupuestaria_detallado(String fecha_inicial,String fecha_final, boolean grupo){
		// Fecha final para sacar los datos del periodo
		
		String sql="select '"+fecha_inicial+"' as fecha_inicial, '"+fecha_final+"' as fecha_corte, pc.ide_prcla,programa,proyecto,producto,codigo_subactividad, subactividad, pc.codigo_clasificador_prcla, pc.descripcion_clasificador_prcla,pf.detalle_prfuf as fuente " +
				",coalesce(inicial,0) as inicial,coalesce(reforma,0) as reforma,(coalesce(inicial,0)+coalesce(reforma,0)) as codificado " +
				",nro_certificacion_prcer,coalesce(certificado,0) as certificado,(coalesce(inicial,0)+coalesce(reforma,0))-coalesce(certificado,0) saldo_certificado " +
				",nro_compromiso, coalesce(compromiso,0) as compromiso,coalesce(certificado,0)-coalesce(compromiso,0) as saldo_compromiso " +
				",nro_comprobante, coalesce(devengado,0) as devengado,coalesce(compromiso,0)-coalesce(devengado,0) as saldo_devengado " +
				
				"from pre_clasificador pc  " +
				"join pre_poa poa on poa.ide_prcla = pc.ide_prcla  " +
				"join pre_poa_financiamiento ppf on ppf.ide_prpoa=poa.ide_prpoa  " +
				"join pre_fuente_financiamiento pf on pf.ide_prfuf=ppf.ide_prfuf " +
				"left join (select a.ide_prfup , detalle_programa as programa   " +
				", detalle_proyecto as proyecto   " +
				", substring(detalle_producto from 1 for 60) as producto   " +
				", detalle_subactividad as subactividad,codigo_subactividad   " +
				"from ( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_subactividad,detalle_prfup as detalle_subactividad,detalle_prnfp as subactividad from pre_funcion_programa a, pre_nivel_funcion_programa b    " +
				"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =5 ) a ,     " +
				"( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_actividad,detalle_prfup as detalle_actividad,detalle_prnfp as actividad    " +
				"from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =4 ) b,      " +
				"( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_producto,detalle_prfup as detalle_producto,detalle_prnfp as producto, codigo_pry_prd_prfup as cod_prod     " +
				"from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =3 ) c,         " +
				"( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_proyecto,detalle_prfup as detalle_proyecto,detalle_prnfp as proyecto, codigo_pry_prd_prfup as cod_pry     " +
				"from pre_funcion_programa a, pre_nivel_funcion_programa b where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =2 ) d,     " +
				"( select ide_prfup ,pre_ide_prfup,codigo_prfup as codigo_programa,detalle_prfup as detalle_programa,detalle_prnfp as programa     " +
				"from pre_funcion_programa a,  " +
				"pre_nivel_funcion_programa b  " +
				"where a.ide_prnfp = b.ide_prnfp and a.ide_prnfp =1 ) e  " +
				"where a.pre_ide_prfup = b.ide_prfup and b.pre_ide_prfup = c.ide_prfup and c.pre_ide_prfup = d.ide_prfup and d.pre_ide_prfup = e.ide_prfup) desPoa on desPoa.ide_prfup=poa.ide_prfup " +
				
				"left join (select ide_prcla,ide_prfuf,ide_prfup,sum(valor_financiamiento_prpof) as inicial from ( " +
				"select a.ide_prpoa,ide_prfuf,valor_financiamiento_prpof,ide_prcla,ide_prfup from  pre_poa_financiamiento a, pre_poa b  " +
				"where a.ide_prpoa = b.ide_prpoa " +
				") a group by ide_prcla,ide_prfuf,ide_prfup) ini on ini.ide_prcla=pc.ide_prcla and ini.ide_prfuf = ppf.ide_prfuf and ini.ide_prfup=poa.ide_prfup " +
			
				"left join (select ide_prcla,ide_prfuf,ide_prfup,sum(valor_reformado_prprf) as reforma from ( " +
				"select a.ide_prpoa,ide_prfuf,valor_reformado_prprf,ide_prcla,ide_prfup from  pre_poa_reforma_fuente a, pre_poa b  " +
				"where a.ide_prpoa = b.ide_prpoa and fecha_prprf between '"+fecha_inicial+"' and '"+fecha_final+"' " +
				") a group by ide_prcla,ide_prfuf,ide_prfup) reff on reff.ide_prcla=pc.ide_prcla and reff.ide_prfuf = ppf.ide_prfuf and reff.ide_prfup=poa.ide_prfup " +
			
				"left join (select ide_prcla,ide_prfuf,ide_prfup,nro_certificacion_prcer,ide_prpoc,sum(valor_certificado_prpoc) as certificado from (   " +
				"select a.ide_prpoa,ide_prfuf,ide_prfup,valor_certificado_prpoc,ide_prcla, nro_certificacion_prcer,a.ide_prpoc from  pre_poa_certificacion a, pre_poa b, pre_certificacion c   " +
				"where a.ide_prpoa = b.ide_prpoa and a.ide_prcer=c.ide_prcer and fecha_prcer between '"+fecha_inicial+"' and '"+fecha_final+"' " +
				") a group by ide_prcla,ide_prfuf,ide_prfup,nro_certificacion_prcer,ide_prpoc) cert on cert.ide_prcla=pc.ide_prcla and cert.ide_prfuf = ppf.ide_prfuf and cert.ide_prfup=poa.ide_prfup " +
			
				"left join (select ide_prcla,ide_prfuf,ide_prfup,ide_prtra as nro_compromiso,ide_prpoc,sum(comprometido_prpot) as compromiso from ( " +
				"select a.ide_prpoa,ide_prfuf,ide_prfup,comprometido_prpot,ide_prcla,c.ide_prtra,ide_prpoc from  pre_poa_tramite a, pre_poa b, pre_tramite c " +
				"where a.ide_prpoa = b.ide_prpoa and a.ide_prtra=c.ide_prtra and fecha_tramite_prtra between '"+fecha_inicial+"' and '"+fecha_final+"' " +
				") a group by ide_prcla,ide_prfuf,ide_prfup,ide_prtra,ide_prpoc) comp on comp.ide_prcla=pc.ide_prcla and comp.ide_prfuf = ppf.ide_prfuf and comp.ide_prfup=poa.ide_prfup and comp.ide_prpoc=cert.ide_prpoc " ;
				
		if(grupo)
		  sql+=" left join (select ide_prcla,ide_prfuf,ide_prfup,0 as nro_comprobante,ide_prtra, sum(devengado_prmen) as devengado from (  " +
				"select a.ide_prpoa,b.ide_pranu,a.ide_prcla,c.ide_prfuf,ide_prfup,devengado_prmen, c.ide_prtra   " +
				"from pre_poa a, pre_anual b, pre_mensual c  " +
				"where a.ide_prpoa = b.ide_prpoa and b.ide_pranu = c.ide_pranu and devengado_prmen != 0 and fecha_ejecucion_prmen between '"+fecha_inicial+"' and '"+fecha_final+"' " +
				") a group by ide_prcla,ide_prfuf,ide_prfup,ide_prtra) dev on dev.ide_prcla=pc.ide_prcla and dev.ide_prfuf = ppf.ide_prfuf and dev.ide_prfup=poa.ide_prfup and dev.ide_prtra=comp.nro_compromiso " ;
		else
		   sql+=" left join (select ide_prcla,ide_prfuf,ide_prfup,ide_tecpo as nro_comprobante,ide_prtra, sum(devengado_prmen) as devengado from (  " +
				"select a.ide_prpoa,b.ide_pranu,a.ide_prcla,c.ide_prfuf,ide_prfup,devengado_prmen, ide_tecpo,c.ide_prtra   " +
				"from pre_poa a, pre_anual b, pre_mensual c  " +
				"where a.ide_prpoa = b.ide_prpoa and b.ide_pranu = c.ide_pranu and devengado_prmen != 0 and fecha_ejecucion_prmen between '"+fecha_inicial+"' and '"+fecha_final+"' " +
				") a group by ide_prcla,ide_prfuf,ide_prfup,ide_tecpo,ide_prtra) dev on dev.ide_prcla=pc.ide_prcla and dev.ide_prfuf = ppf.ide_prfuf and dev.ide_prfup=poa.ide_prfup and dev.ide_prtra=comp.nro_compromiso " ;

		//sql+=" where (coalesce(inicial,0)+coalesce(reforma,0))>0  " ;
		sql+=" order by pc.ide_prcla,poa.ide_prfup,ppf.ide_prfuf,nro_certificacion_prcer,nro_compromiso,nro_comprobante;";

		//System.out.println("consulta ejecucion presupuestaria: "+ sql);
		return sql;
	}

}
