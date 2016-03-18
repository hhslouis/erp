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
}

