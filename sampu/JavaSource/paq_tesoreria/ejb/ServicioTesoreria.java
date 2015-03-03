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
		
		String tab_impuesto="select a.ide_teimp,detalle_teimp,codigo_teimp,porcentaje_teimp,detalle_tetii " +
				" from tes_impuesto a,tes_tipo_impuesto b where a.ide_tetii= b.ide_tetii" +
				" and activo_teimp in ("+activo+") ";
				if(grupo.equals("0")){
					tab_impuesto+=" and a.ide_tetii in ("+tipo_impuesto+")";		
				}
				    tab_impuesto+=" order by codigo_teimp";

		return tab_impuesto;
		
	}
}

