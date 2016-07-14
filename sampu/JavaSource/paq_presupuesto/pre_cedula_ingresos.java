package paq_presupuesto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import com.sun.org.apache.bcel.internal.generic.NEW;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.Radio;
import framework.componentes.SeleccionCalendario;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_cedula_ingresos extends Pantalla{

	private Tabla tab_balance_inicial=new Tabla();
	private Combo com_anio=new Combo();
	private SeleccionTabla set_asiento_contable = new SeleccionTabla();
	private Confirmar con_guardar=new Confirmar();
	private Combo com_nivel_cuenta_inicial = new Combo();
	private Combo com_nivel_cuenta_final = new Combo();
	private Radio rad_imprimir= new Radio();
	
	public static String par_tipo_asiento_inicial;
	private SeleccionCalendario sel_calendario=new SeleccionCalendario();

	
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioPresupuesto ser_presupuesto = (ServicioPresupuesto ) utilitario.instanciarEJB(ServicioPresupuesto.class);

	public pre_cedula_ingresos() {
		par_tipo_asiento_inicial =utilitario.getVariable("p_tipo_asiento_inicial");

		bar_botones.limpiar();
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));		
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		com_nivel_cuenta_inicial.setCombo(utilitario.getListaGruposNivelPresupuesto());		
		com_nivel_cuenta_inicial.setValue("1");
		com_nivel_cuenta_inicial.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione el Nivel de Cuenta Inicial"));
		bar_botones.agregarComponente(com_nivel_cuenta_inicial);
		
		com_nivel_cuenta_final.setCombo(utilitario.getListaGruposNivelPresupuesto());		
		com_nivel_cuenta_final.setValue("1");
		com_nivel_cuenta_final.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione el Nivel de Cuenta Final"));
		bar_botones.agregarComponente(com_nivel_cuenta_final);

		// boton limpiar
				Boton bot_limpiar = new Boton();
				bot_limpiar.setIcon("ui-icon-cancel");
				bot_limpiar.setMetodo("limpiar");
				bar_botones.agregarBoton(bot_limpiar);
		
		// grid para consolidado o por fuentes de financiamiento
				Grid gri_formulario = new Grid();
		    	gri_formulario.setColumns(2);
		    	
				List listax = new ArrayList();
			       Object fila1x[] = {
			           "0", "COSOLIDADO"
			       };
			       Object fila2x[] = {
			           "1", "POR FUENTE FINANCIAMIENTO"
			       };
			       
			       listax.add(fila1x);
			       listax.add(fila2x);
			       rad_imprimir.setId("rad_imprimir");
			       rad_imprimir.setRadio(listax);
			       rad_imprimir.setValue(fila2x);
			    gri_formulario.getChildren().add(rad_imprimir);		
				
		tab_balance_inicial.setId("tab_balance_inicial");  
		tab_balance_inicial.setTabla("pre_cedula_ingreso", "ide_prcing", 1);	
		tab_balance_inicial.setHeader("CEDULA PRESUPUESTARIA DE INGRESOS");
		tab_balance_inicial.setCondicion("ide_geani=-1");
		tab_balance_inicial.getColumna("ide_geani").setCombo(ser_contabilidad.getAnio("true,false", "true,false"));
		tab_balance_inicial.getColumna("ide_geani").setAutoCompletar();
		tab_balance_inicial.getColumna("ide_prcla").setCombo(ser_presupuesto.getCatalogoPresupuestario("true,false"));
		tab_balance_inicial.getColumna("ide_prcla").setAutoCompletar();
		tab_balance_inicial.getColumna("ide_prcla").setFiltroContenido();
		tab_balance_inicial.getColumna("ide_prcla").setLongitud(200);
		tab_balance_inicial.getColumna("ide_prfuf").setCombo("select ide_prfuf,detalle_prfuf from pre_fuente_financiamiento");
		tab_balance_inicial.getColumna("ide_prfuf").setAutoCompletar();
		tab_balance_inicial.setRows(20);
		tab_balance_inicial.setLectura(true);
		tab_balance_inicial.dibujar();
		PanelTabla pat_balance_inicial=new PanelTabla();
		pat_balance_inicial.setHeader(gri_formulario);
		pat_balance_inicial.setPanelTabla(tab_balance_inicial);
		Division div1 = new Division();
		div1.dividir1(pat_balance_inicial);
		agregarComponente(div1);
		
		Boton bot_actualizar=new Boton();
		bot_actualizar.setIcon("ui-icon-person");
		bot_actualizar.setValue("Recalcular Cedula Presupuestaria");
		bot_actualizar.setMetodo("generarCedula");
		bar_botones.agregarBoton(bot_actualizar);	
		
		inicializaCalendario();
		
	}
	public void inicializaCalendario(){
		sel_calendario.setTitle("SELECCION DE FECHAS");
		sel_calendario.setFooter("Seleccione un Rango de fechas");
		sel_calendario.setFecha1(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_calendario.setFecha2(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_calendario.getBot_aceptar().setMetodo("generarCedula");
		agregarComponente(sel_calendario);
	}
	public void generarCedula(){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Selecione un Año", "");
			return;			

		}
		if(sel_calendario.isVisible()){
		sel_calendario.cerrar();
		
		String sql_borra_tabla="delete from pre_cedula_ingreso;";
		utilitario.getConexion().ejecutarSql(sql_borra_tabla);
		
		int int_repetir=1;
		TablaGenerica tab_fuente_finaciamiento = utilitario.consultar("select ide_prfuf,ide_geani from pre_fuente_financiamiento_ini where ide_geani="+com_anio.getValue());
//		tab_fuente_finaciamiento.imprimirSql();

		if(rad_imprimir.getValue().toString().equals("1")){
			int_repetir=tab_fuente_finaciamiento.getTotalFilas();
		}

		for (int i=0;i<int_repetir;i++){
		
		String sql="insert into pre_cedula_ingreso (ide_prcing,ide_geani,ide_prcla,inicial_prcing,reformado_prcing,codificado_prcing,devengado_prcing,cobrado_prcing,saldo_devengar_prcing,"
				+" saldo_cobrar_prcing,fecha_inicial_prcing,fecha_final_prcing,ide_prfuf,cobrado_efectivo_prcing)"
				+" select row_number() over(order by ide_prcla) + (select (case when max(ide_prcing) is null then 0 else max(ide_prcing) end) as codigo from pre_cedula_ingreso) as codigo,"+com_anio.getValue()+","
				+" ide_prcla,0,0,0,0,0,0,0,'"+sel_calendario.getFecha1String()+"','"+sel_calendario.getFecha2String()+"',"+tab_fuente_finaciamiento.getValor("ide_prfuf")+",0"
				+" from pre_clasificador"
				+" where tipo_prcla =1;";
		//System.out.println("imprimir sql insert "+sql);
		utilitario.getConexion().ejecutarSql(sql);
		
		}
		
		
		if(rad_imprimir.getValue().toString().equals("0")){ // conciliado
			ejecutaConsolidado();
		}
		
		if(rad_imprimir.getValue().toString().equals("1")){ // fuente financiamiento
			ejecutaFuente();
		}
		
		tab_balance_inicial.ejecutarSql();
		utilitario.addUpdate("tab_balance_inicial");
		
		}
		else {
			sel_calendario.dibujar();
		}
	}

	public void ejecutaConsolidado(){
		String sql_inicial="update pre_cedula_ingreso"  
				+" set inicial_prcing = inicial from ("
				+" 		select ide_prcla,sum(valor_inicial_pranu) as inicial from ("
				+" 				select a.ide_prcla,valor_inicial_pranu from pre_anual a where not a.ide_prcla is null and ide_geani = "+com_anio.getValue()
				+" 				) a group by ide_prcla ) a where a.ide_prcla=pre_cedula_ingreso.ide_prcla ";
		
		String sql_reformado="update pre_cedula_ingreso"
				+" set reformado_prcing = reforma from ("
				+" 	select ide_prcla,sum(reforma) as reforma from  ("
				+" 		select a.ide_prcla,(val_reforma_d_prrem - val_reforma_h_prrem) as reforma from pre_anual a, pre_reforma_mes b"
				+" 		where not a.ide_prcla is null and a.ide_pranu = b.ide_pranu and ide_geani = "+com_anio.getValue()+" and fecha_reforma_prrem between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" 		) a group by ide_prcla"
				+" 		) a  where a.ide_prcla=pre_cedula_ingreso.ide_prcla ";
		
		String sql_devengado="update pre_cedula_ingreso"
				+" set devengado_prcing = devengado from ("
				+" 		select ide_prcla,sum(devengado) as devengado from  ("
				+" 				select a.ide_prcla,devengado_prmen as devengado from pre_anual a, pre_mensual b"
				+" 				where not a.ide_prcla is null and a.ide_pranu = b.ide_pranu and ide_geani = "+com_anio.getValue()+" and fecha_ejecucion_prmen between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" 				) a group by ide_prcla"
				+" 		) a  where a.ide_prcla=pre_cedula_ingreso.ide_prcla ";
		
		String sql_cobrado="update pre_cedula_ingreso"
				+" set cobrado_prcing = cobrado from ("
				+" 		select ide_prcla,sum(cobrado) as cobrado from  ("
				+" 				select a.ide_prcla,cobrado_prmen as cobrado from pre_anual a, pre_mensual b"
				+" 				where not a.ide_prcla is null and a.ide_pranu = b.ide_pranu and ide_geani = "+com_anio.getValue()+" and fecha_ejecucion_prmen between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" 				) a group by ide_prcla"
				+" 		) a  where a.ide_prcla=pre_cedula_ingreso.ide_prcla";
		
		utilitario.getConexion().ejecutarSql(sql_inicial);
		utilitario.getConexion().ejecutarSql(sql_reformado);
		utilitario.getConexion().ejecutarSql(sql_devengado);
		utilitario.getConexion().ejecutarSql(sql_cobrado);
		
		TablaGenerica tab_nivel=utilitario.consultar("select 1 as codigo, max(nivel_prcla) as maximo from pre_clasificador");
		int int_nivel=Integer.parseInt(tab_nivel.getValor("maximo"));
		
		for(int i=0;i <int_nivel;i++){
		
		int valor=int_nivel-i;
		String sql_actualiza_niveles="update pre_cedula_ingreso"
				+" set inicial_prcing = inicial,"
				+" reformado_prcing= reforma,"
				+" devengado_prcing= devengado,"
				+" cobrado_prcing= cobrado,"
				+" cobrado_efectivo_prcing=  cobrado_efectivo"
				+" from ("
					+" 	select pre_ide_prcla,sum(inicial_prcing) as inicial,sum(reformado_prcing) as reforma,sum(devengado_prcing) as devengado,"
					+"  sum(cobrado_prcing) as cobrado,sum(cobrado_efectivo_prcing) as cobrado_efectivo"
					+" 	from pre_cedula_ingreso a,pre_clasificador b where a.ide_prcla = b.ide_prcla and nivel_prcla="+valor+" group by pre_ide_prcla"
					+" 	) a where pre_cedula_ingreso.ide_prcla = a.pre_ide_prcla; ";
		utilitario.getConexion().ejecutarSql(sql_actualiza_niveles);
		}
		String sql_saldo="update pre_cedula_ingreso"
				+" set codificado_prcing=inicial_prcing + reformado_prcing,"
				+" saldo_devengar_prcing = (inicial_prcing + reformado_prcing) - devengado_prcing,"
				+" saldo_cobrar_prcing = devengado_prcing - cobrado_prcing";
		
		utilitario.getConexion().ejecutarSql(sql_saldo);		
	}
	public void ejecutaFuente(){  
		String sql_inicial="update pre_cedula_ingreso"  
				+" set inicial_prcing = inicial from ("
				+" 		select ide_prcla,ide_prfuf,sum(valor_inicial_pranu) as inicial from ("
				+" 				select a.ide_prcla,a.ide_prfuf,valor_inicial_pranu from pre_anual a where not a.ide_prcla is null and ide_geani = "+com_anio.getValue()
				+" 				) a group by ide_prcla,ide_prfuf ) a where a.ide_prcla=pre_cedula_ingreso.ide_prcla and  a.ide_prfuf = pre_cedula_ingreso.ide_prfuf";
		
		String sql_reformado="update pre_cedula_ingreso"
				+" set reformado_prcing = reforma from ("
				+" 	select ide_prcla,ide_prfuf,sum(reforma) as reforma from  ("
				+" 		select a.ide_prcla,a.ide_prfuf,(val_reforma_d_prrem - val_reforma_h_prrem) as reforma from pre_anual a, pre_reforma_mes b"
				+" 		where not a.ide_prcla is null and a.ide_pranu = b.ide_pranu and ide_geani = "+com_anio.getValue()+" and fecha_reforma_prrem between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" 		) a group by ide_prcla,ide_prfuf"
				+" 		) a  where a.ide_prcla=pre_cedula_ingreso.ide_prcla and  a.ide_prfuf = pre_cedula_ingreso.ide_prfuf";
		
		String sql_devengado="update pre_cedula_ingreso"
				+" set devengado_prcing = devengado from ("
				+" 		select ide_prcla,ide_prfuf,sum(devengado) as devengado from  ("
				+" 				select a.ide_prcla,a.ide_prfuf,devengado_prmen as devengado from pre_anual a, pre_mensual b"
				+" 				where not a.ide_prcla is null and a.ide_pranu = b.ide_pranu and ide_geani = "+com_anio.getValue()+" and fecha_ejecucion_prmen between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" 				) a group by ide_prcla,ide_prfuf"
				+" 		) a  where a.ide_prcla=pre_cedula_ingreso.ide_prcla and  a.ide_prfuf = pre_cedula_ingreso.ide_prfuf";
		
		String sql_cobrado="update pre_cedula_ingreso"
				+" set cobrado_prcing = cobrado from ("
				+" 		select ide_prcla,ide_prfuf,sum(cobrado) as cobrado from  ("
				+" 				select a.ide_prcla,a.ide_prfuf,cobrado_prmen as cobrado from pre_anual a, pre_mensual b"
				+" 				where not a.ide_prcla is null and a.ide_pranu = b.ide_pranu and ide_geani = "+com_anio.getValue()+" and fecha_ejecucion_prmen between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" 				) a group by ide_prcla,ide_prfuf"
				+" 		) a  where a.ide_prcla=pre_cedula_ingreso.ide_prcla and  a.ide_prfuf = pre_cedula_ingreso.ide_prfuf";
		
		utilitario.getConexion().ejecutarSql(sql_inicial);
		utilitario.getConexion().ejecutarSql(sql_reformado);
		utilitario.getConexion().ejecutarSql(sql_devengado);
		utilitario.getConexion().ejecutarSql(sql_cobrado);
		
		TablaGenerica tab_nivel=utilitario.consultar("select 1 as codigo, max(nivel_prcla) as maximo from pre_clasificador");
		int int_nivel=Integer.parseInt(tab_nivel.getValor("maximo"));
		
		for(int i=0;i <int_nivel;i++){
		
		int valor=int_nivel-i;
		String sql_actualiza_niveles="update pre_cedula_ingreso"
				+" set inicial_prcing = inicial,"
				+" reformado_prcing= reforma,"
				+" devengado_prcing= devengado,"
				+" cobrado_prcing= cobrado,"
				+" cobrado_efectivo_prcing=  cobrado_efectivo"
				+" from ("
					+" 	select pre_ide_prcla,ide_prfuf,sum(inicial_prcing) as inicial,sum(reformado_prcing) as reforma,sum(devengado_prcing) as devengado,"
					+"  sum(cobrado_prcing) as cobrado,sum(cobrado_efectivo_prcing) as cobrado_efectivo"
					+" 	from pre_cedula_ingreso a,pre_clasificador b where a.ide_prcla = b.ide_prcla and nivel_prcla="+valor+" group by pre_ide_prcla,ide_prfuf"
					+" 	) a where pre_cedula_ingreso.ide_prcla = a.pre_ide_prcla and pre_cedula_ingreso.ide_prfuf = a.ide_prfuf; ";
		utilitario.getConexion().ejecutarSql(sql_actualiza_niveles);
		}
		String sql_saldo="update pre_cedula_ingreso"
				+" set codificado_prcing=inicial_prcing + reformado_prcing,"
				+" saldo_devengar_prcing = (inicial_prcing + reformado_prcing) - devengado_prcing,"
				+" saldo_cobrar_prcing = devengado_prcing - cobrado_prcing";
		
		utilitario.getConexion().ejecutarSql(sql_saldo);
	}
	public void seleccionaElAnio (){
	if(com_anio.getValue()==null){
		utilitario.agregarMensajeInfo("Selecione un Año", "");
		return;			

	}
	if(com_nivel_cuenta_inicial.getValue()==null){
		utilitario.agregarMensajeInfo("Selecione el Nivel de Cuenta Inicial", "");
		return;			

	}
	if(com_nivel_cuenta_final.getValue()==null){
		utilitario.agregarMensajeInfo("Selecione el Nivel de Cuenta Final", "");
		return;			

	}
	
		
		tab_balance_inicial.setCondicion("ide_geani="+com_anio.getValue()+"  and ide_prcla in (select ide_prcla from pre_clasificador where nivel_prcla between "+com_nivel_cuenta_inicial.getValue()+" and "+com_nivel_cuenta_final.getValue()+" and tipo_prcla = 1 )");
		tab_balance_inicial.ejecutarSql();
		utilitario.addUpdate("tab_balance_inicial");
	
}
	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	public void limpiar() {
		tab_balance_inicial.limpiar();	
		utilitario.addUpdate("tab_balance_inicial");// limpia y refresca el autocompletar
	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_balance_inicial.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_balance_inicial.guardar();
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_balance_inicial.eliminar();
	}

	public Tabla getTab_balance_inicial() {
		return tab_balance_inicial;
	}

	public void setTab_balance_inicial(Tabla tab_balance_inicial) {
		this.tab_balance_inicial = tab_balance_inicial;
	}

	public Combo getCom_anio() {
		return com_anio;
	}

	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}
	
	public Combo getCom_nivel_cuenta_inicial() {
		return com_nivel_cuenta_inicial;
	}
	public void setCom_nivel_cuenta_inicial(Combo com_nivel_cuenta_inicial) {
		this.com_nivel_cuenta_inicial = com_nivel_cuenta_inicial;
	}
	public Combo getCom_nivel_cuenta_final() {
		return com_nivel_cuenta_final;
	}
	public void setCom_nivel_cuenta_final(Combo com_nivel_cuenta_final) {
		this.com_nivel_cuenta_final = com_nivel_cuenta_final;
	}
	public SeleccionTabla getSet_asiento_contable() {
		return set_asiento_contable;
	}
	public void setSet_asiento_contable(SeleccionTabla set_asiento_contable) {
		this.set_asiento_contable = set_asiento_contable;
	}
	public Confirmar getCon_guardar() {
		return con_guardar;
	}
	public void setCon_guardar(Confirmar con_guardar) {
		this.con_guardar = con_guardar;
	}
	public SeleccionCalendario getSel_calendario() {
		return sel_calendario;
	}
	public void setSel_calendario(SeleccionCalendario sel_calendario) {
		this.sel_calendario = sel_calendario;
	}


}
