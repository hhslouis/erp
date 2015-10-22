package paq_facturacion.ejb;

import javax.ejb.Stateless;

import paq_sistema.aplicacion.Utilitario;
import framework.aplicacion.TablaGenerica;


/**
 * Session Bean implementation class ServicioFacturacion
 */
@Stateless

public class ServicioFacturacion {
	private Utilitario utilitario=new Utilitario();


	public String getClientes(String matrizSucursal ){
	    String tab_cliente="select a.ide_recli, ruc_comercial_recli,razon_social_recli,establecimiento_operativo_recli," +
	            "  nro_establecimiento_recli,nombre_comercial_recli, " +
	            "  direccion_recld from rec_clientes a " +
	            " LEFT JOIN rec_cliente_direccion b on a.ide_recli=b.ide_recli where MATRIZ_SUCURSAL_RECLI in ("+matrizSucursal+")" +
	                " ORDER BY  nombre_comercial_recli";
	    return tab_cliente;
	}
	
	public TablaGenerica getTablaBodega (String tabla){
		
		TablaGenerica tab_bodega=utilitario.consultar("select a.ide_recli, a.aplica_mtarifa_recli " +
				"FROM rec_clientes a, tes_tarifas b " +
				"WHERE a.ide_tetar=b.ide_tetar and a.aplica_mtarifa_recli=true and ide_recli=1 and b.ide_tetar=1"+tabla+"'");
		return tab_bodega;
		
		}
	
	public String getFacturas(String numeroFactura ){
	    String tab_cliente="SELECT ide_bomat, codigo_bomat, detalle_bomat FROM bodt_material b WHERE codigo_bomat is not null " +
				"and ide_bogrm in(select c.ide_bogrm from fac_usuario_lugar  a " +
				"inner join fac_lugar b on a.ide_falug=b.ide_falug and a.ide_usua=" +utilitario.getVariable("ide_usua")+
				" inner join fac_venta_lugar c on c.ide_falug=b.ide_falug " +
				"inner join  bodt_grupo_material d on c.ide_bogrm=d.ide_bogrm ) order by detalle_bomat";
	    return tab_cliente;
	}


	public ServicioFacturacion() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Este servicio retorna los datos de la factura
	 * @param grupos recibe 1 para mostrar todos los materiales y
	 * @param grupoMaterial recibe 0 para mostrar solo por grupos de acuerdo los ide enviados en el parámetro 
	 * @return retorna un string con el siguiente contenido (codigo datos factura, autorización 
	 */
	public String getDatosFactura(String grupos,String grupoMaterial){
		String tab_datos_factura="select ide_fadaf,autorizacion_sri_bogrm,serie_factura_fadaf, detalle_bogrm " +
						" from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm ";
						if(grupos.equals("0")){
							tab_datos_factura +=" and b.ide_bogrm in ("+grupoMaterial+") ";
						}
						tab_datos_factura += " order by autorizacion_sri_bogrm";
						//System.out.println("datos factura");
						return tab_datos_factura;
	}
	 public String getCabeceraFactura(String grupos,String cliente){
		 String tab_cabecera_factura="select ide_fafac,secuencial_fafac,factura_fisica_fafac,fecha_transaccion_fafac,base_aprobada_fafac,detalle_bogrm,valor_iva_fafac,total_fafac" +
		 		" from fac_datos_factura a, bodt_grupo_material b,fac_factura c " +
		 		" where a.ide_bogrm = b.ide_bogrm and a.ide_fadaf=c.ide_fadaf" ;
		 		if(grupos.equals("0")){
		 			tab_cabecera_factura+=" and c.ide_recli in("+cliente+")";		
		 		}
		 tab_cabecera_factura+=" order by secuencial_fafac";
		 return tab_cabecera_factura;
	 }
	 public String getClientesFactura(){
		 String tab_cabecera_factura="select ide_fafac,secuencial_fafac,ruc_comercial_recli,razon_social_recli,total_fafac,detalle_bogrm"
				 +" from fac_factura a, rec_clientes b,fac_datos_factura c,bodt_grupo_material d"
				 +" where a.ide_recli = b.ide_recli and a.ide_fadaf = c.ide_fadaf and c.ide_bogrm = d.ide_bogrm";		
		 		
		 
		 return tab_cabecera_factura;
	 }
	 public TablaGenerica getTablaGenericaFacturaCabecera(String codigo){
		 
		 TablaGenerica tab_cabecera_factura=utilitario.consultar("select ide_fafac,secuencial_fafac,factura_fisica_fafac,fecha_transaccion_fafac,detalle_bogrm,base_aprobada_fafac,valor_iva_fafac,total_fafac" +
		 		" from fac_datos_factura a, bodt_grupo_material b,fac_factura c " +
		 		" where a.ide_bogrm = b.ide_bogrm and a.ide_fadaf=c.ide_fadaf and c.ide_fafac in ("+codigo+") order by secuencial_fafac");
		 return tab_cabecera_factura;
		 
	 }
	 	/**
		 * Este servicio retorna los datos de la factura para ser contabilizados, y a su ves consulta los asientos
		 * @param tipo_emision recibe 1 para mostrar las facturas de tipo emision,2 para mostrar facturas tipo recaudacion, 0 para consultar facturas en asientos contables 
		 * @param fecha_inicial si tipo_emision=1, fecha iniciail corresponde a fecha transacccion iniicial, si tipo_emision=2 corresponde a fecha de pago de la factura. 
		 * @return retorna un string con el siguiente contenido: codigo de la factura, numero del asiento contable, nombre del asiento contable, ruc del proveedor, nombre del proveedor, numero de factura y los respectivos valores de la factura
		 */
		public String getDatosFacturaContabilidad(String tipo_emision,String fecha_inicial,String fecha_final){
			String tab_datos_factura="select a.ide_fafac,b.ide_comov,b.ide_coest,b.ide_conac,detalle_coest,detalle_conac,secuencial_fafac,ruc_comercial_recli,razon_social_recli,base_aprobada_fafac,"
					+" valor_iva_fafac,total_fafac,factura_fisica_fafac,fecha_transaccion_fafac,detalle_bogrm"
					+" from ("
						+" 	select ide_fafac,secuencial_fafac,ruc_comercial_recli,razon_social_recli,base_aprobada_fafac,valor_iva_fafac,total_fafac,factura_fisica_fafac,fecha_transaccion_fafac,detalle_bogrm"
						+" 	from fac_datos_factura a, bodt_grupo_material b,fac_factura c,rec_clientes d "
						+" 	where a.ide_bogrm = b.ide_bogrm and a.ide_fadaf=c.ide_fadaf and c.ide_recli = d.ide_recli"
						+" 	) a"
						+" 	left join cont_factura_asiento b on a.ide_fafac = b.ide_fafac"
						+" 	left join cont_estado c on b.ide_coest = c.ide_coest"
						+" 	left join cont_nombre_asiento_contable d on b.ide_conac = d.ide_conac";
						if(tipo_emision.equals("1")){
							tab_datos_factura +=" where fecha_transaccion_fafac between '"+fecha_inicial+"' and '"+fecha_final+"'"	;
						}
						tab_datos_factura += " 	order by secuencial_fafac";
							System.out.println("datos factura contabilizar"+tab_datos_factura);
							return tab_datos_factura;
		}
		
	 public TablaGenerica getTablaGenericaFacturasVencidas(String codigo){
		 
		 TablaGenerica tab_cabecera_factura=utilitario.consultar("select ide_fafac,ide_recli,fecha_transaccion_fafac,to_date(to_char(now(), 'YYYY/MM/DD'), 'YYYY/MM/DD')  - fecha_transaccion_fafac  as dias_emitido" 
				 +" from fac_factura where ide_coest = 2"
				 +" and to_date(to_char(now(), 'YYYY/MM/DD'), 'YYYY/MM/DD')  - fecha_transaccion_fafac >6 and ide_recli in ("+codigo+") "
				 +" order by ide_recli");
		 return tab_cabecera_factura;
		 
	 } 
	 public String getClientesDatosBasicos(String matrizSucursal ){
		    String tab_cliente="select a.ide_recli, ruc_comercial_recli,razon_social_recli,establecimiento_operativo_recli from rec_clientes a " +
		            " LEFT JOIN rec_cliente_direccion b on a.ide_recli=b.ide_recli where MATRIZ_SUCURSAL_RECLI in ("+matrizSucursal+")" +
		                " ORDER BY  nombre_comercial_recli";
		    return tab_cliente;
		}
	 /**Busca clientes con factura para validar documento de Pagos Banco Pacifico
		 * @param campo cedula del campo al que se realizara la busqueda
		 * @param valor factuta de la busqueda
		 * @return
		 */
		public TablaGenerica getDatosClienteFactura(String ide_factura){
			return utilitario.consultar("select orden,secuencial,fecha_transaccion_fafac, ruc_comercial_recli,rpad,base_aprobada_fafac, valor_iva_fafac, total_fafac,detalle_bogrm,doc_identidad,"
+" repeat ('0',(15 - length (tot_sin_punto)))||tot_sin_punto as valor_sin_punto,repeat ('0',(15 - length (secuencial)))||secuencial as factura_sin_punto,repeat ('0',(13 - length (ruc_comercial_recli)))||ruc_comercial_recli as ruc_sin_punto,repeat ('0',(7 - length (nuevo_iva)))||nuevo_iva as iva_sin_punto"
+" from ("
+" SELECT row_number() over( order by secuencial_fafac) as orden,substring(secuencial_fafac from 9 for 7) as secuencial,fecha_transaccion_fafac, ruc_comercial_recli,rpad(razon_social_recli,30,' ') as rpad,base_aprobada_fafac, valor_iva_fafac, total_fafac,"
+" detalle_bogrm,replace(round(total_fafac,2)||'','.','') as tot_sin_punto, ( case when ide_gttdi = 1  then 'P' when ide_gttdi= 2 then 'R' when ide_gttdi= 3 then 'C' end) as doc_identidad,replace((round(valor_iva_fafac,2))||'','.','') as nuevo_iva "
+" FROM fac_factura fac"
+" join rec_clientes cli on cli.ide_recli=fac.ide_recli"
+" join (select ide_fadaf, detalle_bogrm from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm) b on fac.ide_fadaf = b.ide_fadaf"
+" where ide_fafac="+ide_factura 

+" order by secuencial_fafac"
+" ) a");
		}
}
