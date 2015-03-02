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
	
}
