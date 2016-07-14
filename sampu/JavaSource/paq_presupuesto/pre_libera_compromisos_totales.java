package paq_presupuesto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.poi.hssf.util.HSSFColor.TAN;

import oracle.net.aso.d;

import com.sun.org.apache.bcel.internal.generic.NEW;

import framework.aplicacion.Fila;
import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.BotonesCombo;
import framework.componentes.Calendario;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.ItemMenu;
import framework.componentes.PanelTabla;
import framework.componentes.Radio;
import framework.componentes.SeleccionCalendario;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Texto;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_libera_compromisos_totales extends Pantalla{

	Tabla tab_libera_compromiso = new Tabla();
	Dialogo dis_liberar = new Dialogo();
	Calendario cal_fecha = new Calendario();
	Texto txt_num_resolucion = new Texto();

	
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioPresupuesto ser_presupuesto = (ServicioPresupuesto ) utilitario.instanciarEJB(ServicioPresupuesto.class);

	public pre_libera_compromisos_totales() {
		
		Boton bot_liberar = new Boton();
		bot_liberar.setIcon("ui-icon-person");
		bot_liberar.setValue("LIBERAR COMPROMISOS");
		bot_liberar.setMetodo("dibujaDialogo");
		bar_botones.agregarBoton(bot_liberar);
		
		dis_liberar.setId("dis_liberar");
		dis_liberar.setTitle("Liberar Compromisos");
		dis_liberar.setWidth("25%");
		dis_liberar.setHeight("20%");
		
		Grid gri_cuerpo=new Grid();
		gri_cuerpo.setColumns(2);
		gri_cuerpo.getChildren().add(new Etiqueta("Fecha Liberación Compromiso"));
		gri_cuerpo.getChildren().add(cal_fecha);
		gri_cuerpo.getChildren().add(new Etiqueta("Nro. Resolución"));
		gri_cuerpo.getChildren().add(txt_num_resolucion);
		
		dis_liberar.getBot_aceptar().setMetodo("libera");
		dis_liberar.setDialogo(gri_cuerpo);
		agregarComponente(dis_liberar);
		
		 	BotonesCombo boc_seleccion_inversa = new BotonesCombo();
	        ItemMenu itm_todas = new ItemMenu();
	        ItemMenu itm_niguna = new ItemMenu();


	        boc_seleccion_inversa.setValue("Selección Inversa");
	        boc_seleccion_inversa.setIcon("ui-icon-circle-check");
	        boc_seleccion_inversa.setMetodo("seleccinarInversa");
	        boc_seleccion_inversa.setUpdate("tab_libera_compromiso");
	        itm_todas.setValue("Seleccionar Todo");
	        itm_todas.setIcon("ui-icon-check");
	        itm_todas.setMetodo("seleccionarTodas");
	        itm_todas.setUpdate("tab_libera_compromiso");
	        boc_seleccion_inversa.agregarBoton(itm_todas);
	        itm_niguna.setValue("Seleccionar Ninguna");
	        itm_niguna.setIcon("ui-icon-minus");
	        itm_niguna.setMetodo( "seleccionarNinguna");
	        itm_niguna.setUpdate("tab_libera_compromiso");
	        boc_seleccion_inversa.agregarBoton(itm_niguna);
		
		tab_libera_compromiso.setId("tab_libera_compromiso");
		tab_libera_compromiso.setSql(sqlSaldosCompromisos("1"," and (comprometido_prpot  - (case when devengado is null then 0.0 else devengado end)) !=0 "));
//		tab_libera_compromiso.setCampoPrimaria("codigo");
		tab_libera_compromiso.getColumna("ide_prpoa").setCombo(ser_presupuesto.getPoaTodos());
		tab_libera_compromiso.getColumna("ide_prpoa").setAutoCompletar();
		tab_libera_compromiso.getColumna("ide_prpoa").setLectura(true);
		tab_libera_compromiso.getColumna("ide_prfuf").setCombo("pre_fuente_financiamiento","ide_prfuf","detalle_prfuf","");
		tab_libera_compromiso.getColumna("ide_prfuf").setAutoCompletar();
		tab_libera_compromiso.getColumna("ide_prfuf").setLectura(true);	
		tab_libera_compromiso.getColumna("valor_liberado").setVisible(false);
		tab_libera_compromiso.getColumna("ide_prtra").setNombreVisual("NRO. COMRPOMISO");;
		tab_libera_compromiso.getColumna("nro_certificacion_prcer").setNombreVisual("NRO. CERTIFICACION");;
		tab_libera_compromiso.getColumna("ide_prfuf").setNombreVisual("FUENTE DE FINANCIAMIENTO");;
		tab_libera_compromiso.getColumna("ide_prtra").setFiltro(true);
		tab_libera_compromiso.getColumna("nro_certificacion_prcer").setFiltro(true);;

		tab_libera_compromiso.setLectura(true);
		tab_libera_compromiso.setTipoSeleccion(true);
//		tab_libera_compromiso.setValueExpression("rowStyleClass", "fila.campos[7] eq '1' ? 'text-red' : fila.campos[6] eq '0'  ? 'text-green' : null");
		tab_libera_compromiso.getSumaColumna("comprometido_prpot,devengado");

		tab_libera_compromiso.dibujar();

        PanelTabla pat_panel = new PanelTabla();
        //pat_panel.setHeader(gri_formulario);
        pat_panel.getChildren().add(boc_seleccion_inversa);
        pat_panel.setPanelTabla(tab_libera_compromiso);
		
		Division div_recaudacion = new Division();
		div_recaudacion.setId("div_recaudacion");
		div_recaudacion.dividir1(pat_panel);
		
		agregarComponente(div_recaudacion);		
		
	}
	
	public void libera(){
		TablaGenerica tab_usuario=utilitario.consultar("select now() as fecha, ide_usua,nick_usua from sis_usuario where ide_usua="+utilitario.getVariable("ide_usua"));
		String sqlgrupo="select ide_prtra, sum(saldo_devengar) as saldo_devengar  from ( select * from ("+sqlSaldosCompromisos("1", "and (comprometido_prpot  - (case when devengado is null then 0.0 else devengado end)) !=0")+") a where codigo in ("+tab_libera_compromiso.getFilasSeleccionadas()+")) a group by ide_prtra order by ide_prtra";
		//System.out.println("sql "+sqlgrupo);
		TablaGenerica tab_cabecera_compromiso = utilitario.consultar(sqlgrupo);
		
		String sqldetalle="select * from ("+sqlSaldosCompromisos("1", "and (comprometido_prpot - (case when devengado is null then 0.0 else devengado end)) !=0")+") a where codigo in ("+tab_libera_compromiso.getFilasSeleccionadas()+")";
		//System.out.println("sqldetalle "+sqldetalle);

		TablaGenerica tab_detalle=utilitario.consultar(sqldetalle);
		
		TablaGenerica tab_codigo_secuencial = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("pre_libera_compromiso", "sec_liquidacion_prlce"));
		String maximo_codigo_secuencial=tab_codigo_secuencial.getValor("codigo");
		
		for(int i=0;i<tab_cabecera_compromiso.getTotalFilas();i++){
			TablaGenerica tab_codigo_cabecera = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("pre_libera_compromiso", "ide_prlic"));
			String maximo_codigo_cabecera=tab_codigo_cabecera.getValor("codigo");
			
			String inserta_cabecera="INSERT INTO pre_libera_compromiso(ide_prlic, ide_prtra,fecha_prlic, num_doc_prlic,valor_total_prlic, total_parcial_prlic, activo_prlic, usuario_ingre," 
            +" fecha_ingre, hora_ingre,sec_liquidacion_prlce) VALUES ("+maximo_codigo_cabecera+","+tab_cabecera_compromiso.getValor(i, "ide_prtra")+",'"+cal_fecha.getValue()+"','"
            +txt_num_resolucion.getValue()+"',"+tab_cabecera_compromiso.getValor(i, "saldo_devengar")+",1,true,'"+tab_usuario.getValor("nick_usua")+"','"+tab_usuario.getValor("fecha")+"','"+tab_usuario.getValor("fecha")+"','"+maximo_codigo_secuencial+"');";
			utilitario.getConexion().ejecutarSql(inserta_cabecera);
			
			String update_tramite="update pre_tramite set valor_liberado_prtra="+tab_cabecera_compromiso.getValor(i, "saldo_devengar")+",total_compromiso_prtra = total_compromiso_prtra -"+tab_cabecera_compromiso.getValor(i, "saldo_devengar")+" where ide_prtra="+tab_cabecera_compromiso.getValor(i, "ide_prtra");
			utilitario.getConexion().ejecutarSql(update_tramite);
			
			for(int j=0;j<tab_detalle.getTotalFilas();j++){
				//System.out.println("sqldetalleddd "+tab_detalle.getValor(j, "ide_prtra"));
				//System.out.println("sqldetalleccc "+tab_cabecera_compromiso.getValor(i, "ide_prtra"));

				TablaGenerica tab_codigo_detalle = utilitario.consultar(ser_contabilidad.servicioCodigoMaximo("pre_detalle_libera_compro", "ide_prdlc"));
				String maximo_codigo_detalle=tab_codigo_detalle.getValor("codigo");
				if(tab_detalle.getValor(j, "ide_prtra").equals(tab_cabecera_compromiso.getValor(i, "ide_prtra"))){
				String inserta_detalle="INSERT INTO pre_detalle_libera_compro(ide_prdlc,ide_prlic,ide_prpoa,valor_comprometido_prdlc,valor_liberado_prdlc,activo_prdlc,usuario_ingre,fecha_ingre,hora_ingre,ide_prfuf,saldo_liquidado_prdlc)"
						+" VALUES ("+maximo_codigo_detalle+","+maximo_codigo_cabecera+","+tab_detalle.getValor(j, "ide_prpoa")+","+tab_detalle.getValor(j, "comprometido_prpot")+",-"+tab_detalle.getValor(j, "saldo_devengar")+",true,'"+tab_usuario.getValor("nick_usua")+"','"+tab_usuario.getValor("fecha")+"','"+tab_usuario.getValor("fecha")+"',"+tab_detalle.getValor(j, "ide_prfuf")+",0);";
				utilitario.getConexion().ejecutarSql(inserta_detalle);

				}
			}
		}
		dis_liberar.cerrar();
		tab_libera_compromiso.ejecutarSql();
		utilitario.agregarMensaje("Se guardo Correctamente", "");
	}
	public void dibujaDialogo(){
		if(tab_libera_compromiso.getSeleccionados().length>0){
		dis_liberar.dibujar();
		}
		else {
		utilitario.agregarMensajeError("No existen registros seleccionados", "Seleccione un registro para proceder a la liberación del Compromiso");	
		return;
		}
	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	}
	public void seleccionarTodas() {
		tab_libera_compromiso.setSeleccionados(null);
		Fila seleccionados[] = new Fila[tab_libera_compromiso.getTotalFilas()];
		for (int i = 0; i < tab_libera_compromiso.getFilas().size(); i++) {
			seleccionados[i] = tab_libera_compromiso.getFilas().get(i);
		}
		tab_libera_compromiso.setSeleccionados(seleccionados);
		
	}

	/**DFJ**/
	public void seleccinarInversa() {
		if (tab_libera_compromiso.getSeleccionados() == null) {
			seleccionarTodas();
		} else if (tab_libera_compromiso.getSeleccionados().length == tab_libera_compromiso.getTotalFilas()) {
			seleccionarNinguna();
		} else {
			Fila seleccionados[] = new Fila[tab_libera_compromiso.getTotalFilas() - tab_libera_compromiso.getSeleccionados().length];
			int cont = 0;
			for (int i = 0; i < tab_libera_compromiso.getFilas().size(); i++) {
				boolean boo_selecionado = false;
				for (int j = 0; j < tab_libera_compromiso.getSeleccionados().length; j++) {
					if (tab_libera_compromiso.getSeleccionados()[j].equals(tab_libera_compromiso.getFilas().get(i))) {
						boo_selecionado = true;
						break;
					}
				}
				if (boo_selecionado == false) {
					seleccionados[cont] = tab_libera_compromiso.getFilas().get(i);
					cont++;
				}
			}
			tab_libera_compromiso.setSeleccionados(seleccionados);
		}
	}

	/**DFJ**/
	public void seleccionarNinguna() {
		tab_libera_compromiso.setSeleccionados(null);

	}
public String sqlSaldosCompromisos(String poa_compromisos,String condicion){
	String sql="";
	sql+="select row_number() over(order by f.ide_prtra,a.ide_prpoa,a.ide_prfuf ) as codigo"
		+"	,f.ide_prtra,nro_certificacion_prcer,f.ide_prpoa,f.ide_prfuf,comprometido_prpot"
		+"	,(case when valor_liberado is null then 0.0 else valor_liberado end) as valor_liberado"
		+"	,(case when devengado is null then 0.0 else devengado end) as devengado"
		+"	,comprometido_prpot  - (case when devengado is null then 0.0 else devengado end) as saldo_devengar"
		+"	,(case when (comprometido_prpot - (case when devengado is null then 0.0 else devengado end) ) < 0 then 1 else 0 end) as estado"
		+"	from ("
		+"			select a.ide_prpoa,ide_prfuf,valor_financiamiento_prpof"
		+"			from pre_poa a, pre_poa_financiamiento b"
		+"			where a.ide_prpoa = b.ide_prpoa "
		+"			) a"
		+"			left join (select ide_prpoa,ide_prfuf,sum(valor_reformado_prprf) as valor_reformado_prprf from  pre_poa_reforma_fuente group by ide_prpoa,ide_prfuf )b on a.ide_prpoa = b.ide_prpoa and a.ide_prfuf = b.ide_prfuf"
		+"			left join (select a.ide_prcer,ide_prpoa,ide_prfuf,nro_certificacion_prcer,valor_certificado_prpoc from pre_certificacion a, pre_poa_certificacion b where a.ide_prcer=b.ide_prcer) c on a.ide_prpoa = c.ide_prpoa  and a.ide_prfuf = c.ide_prfuf"
		+"			left join (select ide_prcer,ide_prpoa,ide_prfuf,sum(valor_certificado_prpoc) as total_certificado from pre_poa_certificacion group by ide_prpoa,ide_prfuf,ide_prcer ) d on c.ide_prpoa= d.ide_prpoa and c.ide_prfuf = d.ide_prfuf and c.ide_prcer = d.ide_prcer"
		+"			left join ( select ide_prcer,sum( valor_liquidado_prdcl) as valor_liquidado_prdcl,ide_prpoa,ide_prfuf"
		+"					from pre_liquida_certificacion a,pre_detalle_liquida_certif b where a.ide_prlce = b.ide_prlce group by ide_prcer,ide_prpoa,ide_prfuf ) e on c.ide_prcer = e.ide_prcer"
		+"					and c.ide_prpoa = e.ide_prpoa  and c.ide_prfuf = e.ide_prfuf"
		+"					left join ( select a.ide_prcer,a.ide_prtra,ide_prpoa,ide_prfuf,comprometido_prpot from pre_tramite a,pre_poa_tramite b where a.ide_prtra = b.ide_prtra  ) f on c.ide_prcer = f.ide_prcer"
		+"					and c.ide_prpoa = f.ide_prpoa  and c.ide_prfuf = f.ide_prfuf"
		+"					left join (select ide_prtra,ide_prpoa,ide_prfuf, sum(valor_liberado_prdlc) as valor_liberado from pre_libera_compromiso a, pre_detalle_libera_compro b"
		+"							where a.ide_prlic = b.ide_prlic group by ide_prtra,ide_prpoa,ide_prfuf ) g on f.ide_prtra = g.ide_prtra and f.ide_prpoa = g.ide_prpoa and f.ide_prfuf = g.ide_prfuf"
		+"							left join ( select a.ide_prpoa,ide_prtra,b.ide_prfuf, sum(devengado_prmen) as devengado from pre_anual a,pre_mensual b where a.ide_pranu=b.ide_pranu and not ide_prtra is null" 
		+"							group by a.ide_prpoa,ide_prtra,b.ide_prfuf ) h on f.ide_prtra = h.ide_prtra and f.ide_prpoa = h.ide_prpoa and f.ide_prfuf = h.ide_prfuf"
		+"	where not f.ide_prtra is null ";
		sql+=condicion;
		sql+=" order by f.ide_prtra,f.ide_prpoa,f.ide_prfuf";
	return sql;
}
public Tabla getTab_libera_compromiso() {
	return tab_libera_compromiso;
}
public void setTab_libera_compromiso(Tabla tab_libera_compromiso) {
	this.tab_libera_compromiso = tab_libera_compromiso;
}
	


}
