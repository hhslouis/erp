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

public String getSqlDeudaClientes (String codigo){
	
	String tab_cliente="select row_number() over(order by ide_recli,grupo,ide_fafac) as codigo,ide_fafac,ide_recli,detalle_bogrm,secuencial_fafac,total_fafac,grupo,interes"
			+" from ("
			+" select ide_fafac,ide_recli,detalle_bogrm,secuencial_fafac,total_fafac,1 as grupo, 0 as interes"
			+" from ("
			+" select ide_fafac,ide_fadaf,secuencial_fafac,total_fafac,ide_recli,ide_coest from fac_factura"
			+" ) a , ("
			+" select ide_fadaf,autorizacion_sri_bogrm,serie_factura_fadaf, detalle_bogrm" 
			+" from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm" 
			+" order by autorizacion_sri_bogrm"
			+" ) b"
			+" where a.ide_fadaf = b.ide_fadaf"
			+" and ide_coest = (select cast(valor_para as integer) from sis_parametros where nom_para ='p_factura_emitido')"
			+" union"
			+" select ide_fanod,ide_recli,'NOTA DE DEBITO',detalle_fenod,total_fanod,2 as grupo,interes_generado_fanod"
			+" from fac_nota_debito where ide_coest = (select cast(valor_para as integer) from sis_parametros where nom_para ='p_factura_emitido')"
			+" ) a"
			+" where ide_recli ="+codigo
			+" order by ide_recli,grupo,ide_fafac";

	return tab_cliente;
	
}
public String getFacturaClientes(String cliente,String tipo,String fecha_inicial,String fecha_final,String aplica_fecha){
	//tipo 1 todos, 0 cliente
	//System.out.println("chequea gfechas "+aplica_fecha);
	String factura_clientes="select row_number() over(order by a.ide_recli,grupo,ide_fafac) as codigo,ide_fafac,a.ide_recli,ruc_comercial_recli,razon_social_recli"
			+" detalle_bogrm,secuencial_fafac,round(total_fafac,2) as total,grupo,"
			+" fecha_transaccion_fafac,fecha_vencimiento_fafac,a.ide_coest,detalle_coest,fecha_pago_fafac,conciliado_fafac"
			+" from ("
				+" select ide_fafac,ide_recli,detalle_bogrm,secuencial_fafac,total_fafac,1 as grupo, fecha_transaccion_fafac,fecha_vencimiento_fafac,fecha_pago_fafac,a.ide_coest,conciliado_fafac"
				+" from ("
					+" 		select ide_fafac,ide_fadaf,secuencial_fafac,total_fafac,ide_recli,ide_coest,fecha_transaccion_fafac,fecha_vencimiento_fafac,fecha_pago_fafac,conciliado_fafac"
						+" 	from fac_factura"
							+" ) a , ("
								+" 	select ide_fadaf,autorizacion_sri_bogrm,serie_factura_fadaf, detalle_bogrm"
								+" 	from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm" 
								+" 	order by autorizacion_sri_bogrm"
								+" 	) b"
								+" 	where a.ide_fadaf = b.ide_fadaf"
								+" 	union"
								+" 	select ide_fanod,ide_recli,'NOTA DE DEBITO',detalle_fenod,interes_generado_fanod,2 as grupo,fecha_ingre,fecha_emision_fanod,null as fecha_pago,ide_coest,null as conciliado"
								+" 	from fac_nota_debito "
					+" ) a, rec_clientes b,cont_estado c"
					+" where a.ide_recli =b.ide_recli"
					+" and a.ide_coest = c.ide_coest";
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
}

