package paq_tesoreria.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;

import paq_sistema.aplicacion.Utilitario;

/**
 * Session Bean implementation class ServicioTesoreria
 */
@Stateless
@LocalBean
public class ServicioTesoreria {
	private Utilitario utilitario = new Utilitario();


	public TablaGenerica getTablaGenericaImpuesto(String activo){
		TablaGenerica tab_impuesto =utilitario.consultar("select a.ide_teimp,detalle_teimp,codigo_teimp,porcentaje_teimp,detalle_tetii " +
				" from tes_impuesto a,tes_tipo_impuesto b where a.ide_tetii= b.ide_tetii" +
				" and a.ide_tetii in ("+activo+") order by codigo_teimp");

		return tab_impuesto;
		
	}
public String getImpuesto (String activo,String grupo,String tipo_impuesto){
		
		String tab_impuesto="select a.ide_teimp,codigo_teimp,porcentaje_teimp,detalle_teimp,detalle_tetii " +
				" from tes_impuesto a,tes_tipo_impuesto b where a.ide_tetii= b.ide_tetii" +
				" and activo_teimp in ("+activo+") ";
				if(grupo.equals("0")){
					tab_impuesto+=" and a.ide_tetii in ("+tipo_impuesto+")";		
				}
				    tab_impuesto+=" order by codigo_teimp";

		return tab_impuesto;
		
	}
public String getImpuestoCalculo (String codigo){
	
	String tab_impuesto="select a.ide_teimp,codigo_teimp,porcentaje_teimp,detalle_teimp,detalle_tetii " +
			" from tes_impuesto a,tes_tipo_impuesto b where a.ide_tetii= b.ide_tetii" +
			" and  a.ide_teimp in ("+codigo+")  order by codigo_teimp";

	return tab_impuesto;
	
}
public String getConsultaRetencion(String comprobante){
	
	String tab_impuesto="select a.ide_teder,base_imponible_teder,valor_retenido_teder,ide_cocac,ide_gelua,aplica_formula_teast,formula_teast,"
			 +" (case when aplica_formula_teast =true then 0 else base_imponible_teder end) as resultado"
			 +" from tes_detalle_retencion a,tes_asiento_tipo b, tes_comprobante_pago c,tes_retencion d,adq_factura e"
			 +" where a.ide_teimp =b.ide_teimp"
			 +" and a.ide_teret = d.ide_teret"
			 +" and d.ide_adfac = e.ide_adfac"
			 +" and c.ide_adsoc = e.ide_adsoc"
			 +" and c.ide_tecpo in ("+comprobante+")";

	return tab_impuesto;
	
}
public String getConsultaTipoConcepto(String activo){
	
	String tab_impuesto="select ide_tetic,(case when codigo_tetic is null then 'S/D' else codigo_tetic end) ||' '||detalle_tetic as detalle from tes_tipo_concepto  where activo_tetic in ("+activo+") order by codigo_tetic";

	return tab_impuesto;
	
}
public String getConsultaRetencionCalcula(String comprobante,String formula){
	
	String tab_impuesto="select a.ide_teder,base_imponible_teder,valor_retenido_teder,ide_cocac,ide_gelua,aplica_formula_teast,formula_teast,"
			+" (case when aplica_formula_teast =true then "+formula+" else base_imponible_teder end) as resultado"
			+" from tes_detalle_retencion a,tes_asiento_tipo b, tes_comprobante_pago c,tes_retencion d,adq_factura e"
			+" where a.ide_teimp =b.ide_teimp"
			+" and a.ide_teret = d.ide_teret"
			+" and d.ide_adfac = e.ide_adfac"
			+" and c.ide_adsoc = e.ide_adsoc"
			+" and c.ide_tecpo in ("+comprobante+")";

	return tab_impuesto;
	
}

public String getConsulValorPagoContabilidad(String movimiento){
	
	String tab_impuesto=" select ide_comov,sum(haber_codem) as valor from cont_detalle_movimiento where transferencia_codem = true and ide_comov="+movimiento+" group by ide_comov";

	return tab_impuesto;
	
}

public String getSqlDeudaClientes (String codigo){
	
	String tab_cliente="select row_number() over(order by ide_recli,grupo,ide_fafac) as codigo,ide_fafac,ide_recli,detalle_bogrm,secuencial_fafac,fecha_transaccion_fafac,total_fafac,grupo,interes"
			+" from ("
			+" select ide_fafac,ide_recli,detalle_bogrm,secuencial_fafac,total_fafac,1 as grupo, 0 as interes,fecha_transaccion_fafac"
			+" from ("
			+" select ide_fafac,ide_fadaf,secuencial_fafac,total_fafac,ide_recli,ide_coest,fecha_transaccion_fafac from fac_factura"
			+" ) a , ("
			+" select ide_fadaf,autorizacion_sri_bogrm,serie_factura_fadaf, detalle_bogrm" 
			+" from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm" 
			+" order by autorizacion_sri_bogrm"
			+" ) b"
			+" where a.ide_fadaf = b.ide_fadaf"
			+" and ide_coest = (select cast(valor_para as integer) from sis_parametros where nom_para ='p_factura_emitido')"
			+" union"
			+" select ide_fanod,ide_recli,'NOTA DE DEBITO',detalle_fenod,total_fanod,2 as grupo,interes_generado_fanod,fecha_emision_fanod"
			+" from fac_nota_debito where ide_coest = (select cast(valor_para as integer) from sis_parametros where nom_para ='p_factura_emitido')"
			+" ) a"
			+" where ide_recli ="+codigo
			+" order by ide_recli,grupo,ide_fafac";

	return tab_cliente;
	
}
public String getFacturaClientes(String cliente,String tipo,String fecha_inicial,String fecha_final,String aplica_fecha){
	//tipo 1 todos, 0 cliente
	//System.out.println("chequea gfechas "+aplica_fecha);
	String factura_clientes="select row_number() over(order by a.ide_recli,grupo,ide_fafac) as codigo,ide_fafac,a.ide_recli,detalle_bogrm,ruc_comercial_recli,razon_social_recli"
			+" ,secuencial_fafac,round(total_fafac,2) as total,grupo,"
			+" fecha_transaccion_fafac,fecha_vencimiento_fafac,a.ide_coest,detalle_coest,fecha_pago_fafac,conciliado_fafac,detalle_retip,comprobante_pago,cheque"
			+" from ("
				+" 	select ide_fafac,ide_recli,detalle_bogrm,secuencial_fafac,total_fafac,1 as grupo, fecha_transaccion_fafac,fecha_vencimiento_fafac,fecha_pago_fafac,a.ide_coest,conciliado_fafac,"
				+" ide_retip,documento_conciliado_fafac as comprobante_pago, documento_bancario_fafac as cheque"
				+" 	from ("
					+" 		select ide_fafac,ide_fadaf,secuencial_fafac,total_fafac,ide_recli,ide_coest,fecha_transaccion_fafac,fecha_vencimiento_fafac,fecha_pago_fafac,conciliado_fafac,ide_retip,"
					+" 		documento_conciliado_fafac,documento_bancario_fafac"
					+" 		from fac_factura"
					+" 		) a , ("
						+" 			select ide_fadaf,autorizacion_sri_bogrm,serie_factura_fadaf, detalle_bogrm"
						+" 			from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm"
						+" 			order by autorizacion_sri_bogrm"
						+" 			) b"
						+" 			where a.ide_fadaf = b.ide_fadaf"
						+" 			union"
						+" 			select a.ide_fanod,a.ide_recli,'NOTA DE DEBITO',detalle_fenod,interes_generado_fanod,2 as grupo,a.fecha_ingre,fecha_emision_fanod,fecha_pago_fafac as fecha_pago,a.ide_coest,conciliado_fafac as conciliado,"
						+" 			c.ide_retip ,documento_conciliado_fafac as comprobante_pago, documento_bancario_fafac as cheque"
						+" 			from fac_nota_debito a, fac_detalle_debito b, fac_factura c where a.ide_fanod = b.ide_fanod and b.ide_fafac =c.ide_fafac"
				+" 	) a, rec_clientes b,cont_estado c,rec_tipo d"
				+" 	where a.ide_recli =b.ide_recli"
				+" 	and a.ide_coest = c.ide_coest"
				+" 	and a.ide_retip= d.ide_retip";
				 	if(tipo.equals("0")){
				 	factura_clientes +=" and a.ide_recli= "+cliente;
					}
					if(aplica_fecha.equals("true")){
					factura_clientes +=" and fecha_pago_fafac between '"+fecha_inicial+"' and '"+fecha_final+"' ";
					}
					factura_clientes +=" order by a.ide_recli,grupo,ide_fafac";
	//System.out.println("consulta clientes "+ factura_clientes);
	return factura_clientes;
}
public String getFacturaRetencion(String ide_prtra){
	
	String tab_impuesto="select a.ide_adfac,ide_adsoc,ide_prtra,valor_retenido,detalle_adfac||' FACTURA: '||num_factura_adfac as detalle,ide_tepro,subtotal_adfac,valor_iva_adfac,total_adfac " 
						+" from adq_factura a, ( select ide_adfac,sum(total_ret_teret) as valor_retenido from tes_retencion group by ide_adfac ) b where a.ide_adfac = b.ide_adfac and ide_prtra ="+ide_prtra;

	return tab_impuesto;
	
}
public String getAtsDetalleCompras(String fecha_inicial,String fecha_final){
	String tab_detalle_compras="select row_number() over(order by a.ide_adfac) as codigo_compra, '02' as sustento,'01' as identificacion_proveedor,ruc_tepro,'01' as tipo_comprobante,"
			+" codigo_ats_retic as tipo_proveedor,'NO' as parte_relacionada,fecha_factura_adfac as fecha_registro, substring(num_factura_adfac from 1 for 3) as establecimiento,"
			+" substring (num_factura_adfac from 1 for 7) as punto_emision, substring(num_factura_adfac from 9 for 20) as secuencial,fecha_factura_adfac as fecha_emision,"
			+" nro_autorizacion_sri_adq,0 as base_imp_no_objeto_iva,0 as base_imponible_tarifa_cero_iva,subtotal_adfac as base_imponible_gravada, 0 as base_excenta, 0 as monto_ice,"
			+" valor_iva_adfac as monto_iva,(case when retencion10 is null then 0 else retencion10 end) as retencion10"
			+" ,(case when retencion20 is null then 0 else retencion20 end) as retencion20"
			+" ,(case when retencion30 is null then 0 else retencion30 end) as retencion30"
			+" ,(case when retencion70 is null then 0 else retencion70 end) as retencion70"
			+" ,(case when retencion100 is null then 0 else retencion100 end) as retencion100,subtotal_adfac as total_bases_imponibles,'01' as pago_local_exterior,'NA' as pais_paga,"
			+" 'NA' as regimen_fiscal,'NA' as aplica_convenio, 'NA' as pago_exterior_noramtiva,'001' as establecimiento,'001' as punto_emision,secuencial,fecha_teret"
			+" from adq_factura a"
			+" left join tes_proveedor b on a.ide_tepro=b.ide_tepro"
			+" left join rec_tipo_contribuyente c on b.ide_retic =c.ide_retic"
			+" left join (select a.ide_adfac,ide_teimp,sum(valor_retenido_teder)as retencion10 from tes_retencion a,tes_detalle_retencion b"
			+" where a.ide_teret =b.ide_teret and ide_teimp in (select cast(valor_para as integer) as valor from sis_parametros where nom_para='p_retencion_bienes_10') group by  a.ide_adfac,ide_teimp) d on a.ide_adfac = d.ide_adfac"
			+" left join (select a.ide_adfac,ide_teimp,sum(valor_retenido_teder)as retencion20 from tes_retencion a,tes_detalle_retencion b"
			+" where a.ide_teret =b.ide_teret and ide_teimp in (select cast(valor_para as integer) as valor from sis_parametros where nom_para='p_retencion_servicios_20') group by  a.ide_adfac,ide_teimp) e on a.ide_adfac = e.ide_adfac"
			+" left join (select a.ide_adfac,ide_teimp,sum(valor_retenido_teder)as retencion30 from tes_retencion a,tes_detalle_retencion b"
			+" where a.ide_teret =b.ide_teret and ide_teimp in (select cast(valor_para as integer) as valor from sis_parametros where nom_para='p_retencion_bienes_30') group by  a.ide_adfac,ide_teimp) f on a.ide_adfac = f.ide_adfac"
			+" left join (select a.ide_adfac,ide_teimp,sum(valor_retenido_teder)as retencion70 from tes_retencion a,tes_detalle_retencion b"
			+" where a.ide_teret =b.ide_teret and ide_teimp in (select cast(valor_para as integer) as valor from sis_parametros where nom_para='p_retencion_servicios_70') group by  a.ide_adfac,ide_teimp) g on a.ide_adfac = g.ide_adfac"
			+" left join (select a.ide_adfac,ide_teimp,sum(valor_retenido_teder)as retencion100 from tes_retencion a,tes_detalle_retencion b"
			+" where a.ide_teret =b.ide_teret and ide_teimp in (select cast(valor_para as integer) as valor from sis_parametros where nom_para='p_retencion_iva_100') group by  a.ide_adfac,ide_teimp) h on a.ide_adfac = h.ide_adfac"
			+" left join (select ide_adfac,fecha_teret,num_retencion_teret as secuencial from tes_retencion ) i on a.ide_adfac = i.ide_adfac"
			+" where  fecha_factura_adfac between '"+fecha_inicial+"' and '"+fecha_final+"'";
	return tab_detalle_compras;
}
public String getCuentaPresupuestariaGastos()
	{
	String sql="select a.ide_pranu,codigo_clasificador_prcla,substring(descripcion_clasificador_prcla from 1 for 100) as codigo from pre_anual a, pre_poa b,pre_clasificador c where a.ide_prpoa = b.ide_prpoa and b.ide_prcla= c.ide_prcla order by codigo_clasificador_prcla";
	return sql;
	}


public String getSaldoDevengado(String asiento,String comprobante)
{
String sql="select row_number() over(order by b.ide_pranu) as codigo,b.ide_pranu,b.ide_prfuf,a.codigo_clasificador_prcla,b.detalle_prfuf,comprometido_prpot - valor_devengado as saldo_compromiso,comprometido_prpot,valor_devengado"
	+" from ("
	+" 		select a.ide_cocac,a.ide_prcla,b.ide_tecpo,pagado,devengado,ide_prmop,debe_codem,haber_codem,d.codigo_clasificador_prcla,e.codigo_clasificador_prcla as cuenta_devengado"
		+" 	from pre_asociacion_presupuestaria a,tes_comprobante_pago b,cont_detalle_movimiento c,"
		+" 	(select ide_prcla,codigo_clasificador_prcla from pre_clasificador) d,"
		+" 	(select ide_prcla,codigo_clasificador_prcla from pre_clasificador) e"
		+" 	where b.ide_comov = c.ide_comov"
		+" 	and a.ide_cocac = c.ide_cocac"
		+" 	and a.ide_prcla= d.ide_prcla"
		+" 	and devengado= e.ide_prcla"
		+" 	and ide_prmop = 5"
		+" 	and c.ide_codem ="+asiento
		+" 	) a"
		+" 	left join ("
		+" 			select a.ide_prpoa,ide_tecpo,a.ide_prfuf,detalle_prfuf,codigo_clasificador_prcla,comprometido_prpot,ide_pranu,b.ide_prtra" 
		+" 			from pre_poa_tramite a, tes_comprobante_pago b, pre_poa c, pre_clasificador d, pre_fuente_financiamiento e,pre_anual f"
		+" 			where a.ide_prtra = b.ide_prtra"
		+" 			and a.ide_prpoa = c.ide_prpoa"
		+" 			and c.ide_prcla = d.ide_prcla"
		+" 			and a.ide_prfuf = e.ide_prfuf"
		+" 			and c.ide_prpoa = f.ide_prpoa"
		+" 			and b.ide_tecpo ="+comprobante
		+" 			) b on a.ide_tecpo = b.ide_tecpo and b.codigo_clasificador_prcla like cuenta_devengado||'%'"
		+" 			left join ("
		+" 					select ide_prpoa,a.ide_prtra,a.ide_prfuf,sum(devengado_prmen) as valor_devengado"
		+" 					from pre_mensual a, pre_anual b"
		+" 					where a.ide_pranu = b.ide_pranu "
		+" 					group by ide_prpoa,a.ide_prtra,a.ide_prfuf"
		+" 					) c on b.ide_prtra = c.ide_prtra and b.ide_prpoa = c.ide_prpoa and b.ide_prfuf = c.ide_prfuf";
//System.out.println("cuentas presupuetara "+sql);		
return sql;
		
}
public String getFacturasComprobantes(String condicion){
	String sql="select ide_adfac,(case when num_factura_adfac is null then 'S/N' else num_factura_adfac end) as num_factura_adfac,fecha_factura_adfac,detalle_adfac from adq_factura where ide_tecpo is null "+condicion+" order by num_factura_adfac";
	
	return sql;
}
public String getSaldoFuenteDevengar(String tramites){
	String sql="select ide_prpot, a.ide_prpoa,a.ide_prfuf,a.ide_prtra,comprometido_prpot,(case when valor_devengado is null then 0 else valor_devengado end) as val_devengado,"
			+" comprometido_prpot - (case when valor_devengado is null then 0 else valor_devengado end) as saldo_devengar,detalle_prfuf,codigo_clasificador_prcla,detalle_claificador"
			+" from pre_poa_tramite a"
			+" left join ( select ide_prpoa,a.ide_prtra,a.ide_prfuf,sum(devengado_prmen) as valor_devengado"
				+" 	from pre_mensual a, pre_anual b where a.ide_pranu = b.ide_pranu and not ide_prpoa  is null"
				+" 	group by ide_prpoa,a.ide_prtra,a.ide_prfuf"
				+" 	) b on a.ide_prtra = b.ide_prtra and a.ide_prpoa = b.ide_prpoa and a.ide_prfuf = b.ide_prfuf"
				+" 	left join pre_fuente_financiamiento c on a.ide_prfuf = c.ide_prfuf"
				+" 	left join ( select ide_prpoa,a.ide_prcla,codigo_clasificador_prcla,substring(descripcion_clasificador_prcla from 1 for 100) as detalle_claificador"
				+" 			from pre_poa a , pre_clasificador b where a.ide_prcla = b.ide_prcla ) d on a.ide_prpoa = d.ide_prpoa"
				+" 			where a.ide_prtra in ( "+tramites+") order by a.ide_prtra";
	return sql;
}
public String getPoaResumen(){
	String sql="select ide_prpoa,codigo_clasificador_prcla,substring(descripcion_clasificador_prcla from 1 for 100) as detalle_claificador from pre_poa a , pre_clasificador b where a.ide_prcla = b.ide_prcla ";
	return sql;
}
public String getFacturasComprobante(String comprobante){
	String sql="select ide_adfac,num_factura_adfac,detalle_adfac from adq_factura where ide_tecpo ="+comprobante;
	return sql;	
}
public String getComprobantePoa(String comprobante){
	String sql="select a.ide_tecmp,ide_prtra,detalle_prfuf,saldo_devengar_tecmp,valor_devengar_tecmp,valor_iva_tecmp,codigo_clasificador_prcla,detalle_claificador,"
			+" (case when aplica_iva_tecmp =true then 'SI APLICA' when aplica_iva_tecmp= false then 'NO APLICA' end) as aplica_iva"
			+" from tes_comprobante_poa a, pre_poa_tramite b,("
				+" 	select ide_prpoa,codigo_clasificador_prcla,substring(descripcion_clasificador_prcla from 1 for 100) as detalle_claificador from pre_poa a , pre_clasificador b where a.ide_prcla = b.ide_prcla" 
				+" 	) c ,pre_fuente_financiamiento d where a.ide_prpot = b.ide_prpot and a.ide_prpoa = c.ide_prpoa and a.ide_prfuf = d.ide_prfuf and ide_tecpo ="+comprobante;
	return sql;		
}
public String getTotalFacturaComprobante(String comprobante){
	
	String sql="select ide_tecpo,sum(subtotal_adfac) as subtotal,sum(total_adfac) as total,sum(valor_iva_adfac) as valor_iva,sum(base_iva_adfac) as base_iva"
			+" from ("
			+" 		select ide_adfac,subtotal_adfac,total_adfac,valor_iva_adfac,base_iva_adfac,ide_tecpo"
			+" 		from adq_factura where ide_tecpo in ("+comprobante+")"
			+" 		) a group by ide_tecpo";
	return sql;
}
public String getTotalRetencionComprobante(String comprobante){
	
	String sql="select ide_tecpo,sum (valor_retenido) as total_retenido from adq_factura a, ("
			+" select ide_adfac,sum(valor_retenido) as valor_retenido from tes_retencion a,("
			+" 		select ide_teret,sum(valor_retenido_teder) as valor_retenido "
			+" 		from tes_detalle_retencion group by ide_teret"
			+" 		) b where a.ide_teret = b.ide_teret group by ide_adfac"
			+" 	) b where a.ide_adfac= b.ide_adfac group by ide_tecpo having ide_tecpo="+comprobante;
	return sql;
}
}