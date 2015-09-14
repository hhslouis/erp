package paq_activos.ejb;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import framework.aplicacion.TablaGenerica;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Utilitario;

@Stateless
public class ServicioActivos{
	private Utilitario utilitario = new Utilitario();
	
	
	public TablaGenerica getTablaGenericaConsultaCustodio(String ide_custodio){
		TablaGenerica tab_custodio =utilitario.consultar("SELECT ide_afcus, ide_afact, ide_geedp, detalle_afcus, fecha_entrega_afcus," + 
       " fecha_descargo_afcus, numero_acta_afcus, razon_descargo_afcus,"+ 
       " cod_barra_afcus, nro_secuencial_afcus, activo_afcus,gen_ide_geedp" +
       " FROM afi_custodio where ide_afcus in ("+ide_custodio+") order by ide_afcus;");

		return tab_custodio;
		
	}
	public String getActivosCodigo(String ide_activo){
		String tab_custodio ="select * from afi_activo where ide_afact in ("+ide_activo+")";
		//System.out.println("imprimor getActivosCodigo "+tab_custodio);
		return tab_custodio;		
	}
	public String getCustodioCodigo(String ide_activo){
		String tab_custodio ="select * from afi_custodio where ide_afact in ("+ide_activo+")";
		return tab_custodio;		
	}
	public String getCustodioSecuencial(String ide_activo){
		String tab_custodio ="select ide_afact,max(nro_secuencial_afcus) as nro_secuencial_afcus, activo_afcus"
+" from ("
+" select a.ide_afact,(case when nro_secuencial_afcus is null then 0 else nro_secuencial_afcus end) as nro_secuencial_afcus,"
+" (case when activo_afcus is null then true else activo_afcus end) as activo_afcus"
+" from afi_activo a"
+" left join afi_custodio b on a.ide_afact = b.ide_afact where a.ide_afact ="+ide_activo
+" ) a group by ide_afact, activo_afcus having activo_afcus = true";
		return tab_custodio;		
	}
}
