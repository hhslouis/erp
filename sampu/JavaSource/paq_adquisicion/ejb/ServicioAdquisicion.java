package paq_adquisicion.ejb;

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
public class ServicioAdquisicion{
	private Utilitario utilitario = new Utilitario();
	
	public String getSolicitudCompra (String activo){
		
		String tab_solicitud="select a.ide_adsoc,a.ide_tepro,nombre_tepro,detalle_adsoc,nro_solicitud_adsoc,total_adfac,valor_iva_adfac,num_factura_adfac" +
				" from adq_solicitud_compra a,adq_factura b ,tes_proveedor c" +
				" where  a.ide_adsoc=b.ide_adsoc and a.ide_tepro=c.ide_tepro and activo_adsoc in ("+activo+") order by detalle_adsoc";
				return tab_solicitud;
		
	}
public String getSolicitud (String ide_adsoc){
		
		String tab_solicitud="select a.ide_adsoc,a.ide_tepro,nombre_tepro,detalle_adsoc,nro_solicitud_adsoc,total_adfac,num_factura_adfac" +
				" from adq_solicitud_compra a,adq_factura b ,tes_proveedor c" +
				" where  a.ide_adsoc=b.ide_adsoc and a.ide_tepro=c.ide_tepro and a.ide_adsoc in ("+ide_adsoc+") order by detalle_adsoc";

		return tab_solicitud;
		
	}
	public TablaGenerica getTablaGenericaSolicitud(String ide_adsoc){
		TablaGenerica tab_solicitud =utilitario.consultar("select a.ide_adsoc,a.ide_tepro,a.ide_prtra,nombre_tepro,detalle_adsoc,nro_solicitud_adsoc,total_adfac,valor_iva_adfac,num_factura_adfac" +
				" from adq_solicitud_compra a,adq_factura b ,tes_proveedor c " +
				" where  a.ide_adsoc=b.ide_adsoc and a.ide_tepro=c.ide_tepro and a.ide_adsoc in ("+ide_adsoc+") order by detalle_adsoc");

		return tab_solicitud;
		
	}
	public  String getCompras(String activo){
		String tab_compra ="select a.ide_adsoc,a.ide_tepro,nro_solicitud_adsoc,detalle_adsoc,nombre_tepro from adq_solicitud_compra a,tes_proveedor b" +
				" where a.ide_tepro=b.ide_tepro and activo_adsoc in ("+activo+") order by detalle_adsoc";
		return tab_compra;	
	}
	public  String getComprasCodigo(String ide_adsoc){
		String tab_compra ="select a.ide_adsoc,a.ide_tepro,nro_solicitud_adsoc,detalle_adsoc,nombre_tepro from adq_solicitud_compra a,tes_proveedor b" +
				" where a.ide_tepro=b.ide_tepro and  a.ide_adsoc in ("+ide_adsoc+") order by detalle_adsoc";
		return tab_compra;	
	}
	public  String getComprasCombo(String activo){
		String tab_compra ="select a.ide_adsoc,(case when nro_solicitud_adsoc is null then 'S/N' else nro_solicitud_adsoc end) as nro_solicitud_adsoc ,nombre_tepro,fecha_solicitud_adsoc from adq_solicitud_compra a,tes_proveedor b" +
				" where a.ide_tepro=b.ide_tepro and activo_adsoc in ("+activo+") order by detalle_adsoc";
		return tab_compra;	
	}	
	public String getTramite (String activo){
		String tab_estado="SELECT a.ide_prtra,fecha_tramite_prtra,numero_oficio_prtra,total_compromiso_prtra " +
				" FROM pre_tramite a, cont_estado b" +
				" WHERE a.ide_coest=b.ide_coest and activo_prtra in ("+activo+") " +
				" order by numero_oficio_prtra";
		return tab_estado;
		
	}
	public TablaGenerica getTablaGenericaTramite (String ide_prtra){
		TablaGenerica tab_estado=utilitario.consultar("SELECT a.ide_prtra,fecha_tramite_prtra,numero_oficio_prtra,total_compromiso_prtra " +
				" FROM pre_tramite a, cont_estado b" +
				" WHERE a.ide_coest=b.ide_coest and a.ide_prtra in ("+ide_prtra+") " +
				" order by numero_oficio_prtra");
		return tab_estado;
	
}
	public TablaGenerica getTablaGenericaTramitePOA (String ide_prtra){
		TablaGenerica tab_estado=utilitario.consultar("select ide_prpot,ide_prpoa,ide_prtra,comprometido_prpot,observaciones_prpot from pre_poa_tramite where ide_prtra in ("+ide_prtra+")");
		return tab_estado;
	
}
}
