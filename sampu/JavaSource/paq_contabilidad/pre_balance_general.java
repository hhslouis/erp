package paq_contabilidad;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import com.sun.org.apache.bcel.internal.generic.NEW;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Confirmar;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Radio;
import framework.componentes.Reporte;
import framework.componentes.SeleccionCalendario;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_balance_general extends Pantalla{

	private Tabla tab_balance_inicial=new Tabla();
	private Combo com_anio=new Combo();
	private SeleccionTabla set_asiento_contable = new SeleccionTabla();
	private Confirmar con_guardar=new Confirmar();
	private Combo com_nivel_cuenta_inicial = new Combo();
	private Combo com_nivel_cuenta_final = new Combo();
	
	public static String par_tipo_asiento_inicial;
	private SeleccionCalendario sel_calendario=new SeleccionCalendario();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
	private Map map_parametros = new HashMap();
	
	
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);

	public pre_balance_general() {
		par_tipo_asiento_inicial =utilitario.getVariable("p_tipo_asiento_inicial");

		bar_botones.limpiar();
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));		
		bar_botones.agregarComponente(new Etiqueta("Seleccione El A�o:"));
		bar_botones.agregarComponente(com_anio);
		
		// boton limpiar
				Boton bot_limpiar = new Boton();
				bot_limpiar.setIcon("ui-icon-cancel");
				bot_limpiar.setMetodo("limpiar");
				bar_botones.agregarBoton(bot_limpiar);
				
				
				rep_reporte.setId("rep_reporte");
				rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
				agregarComponente(rep_reporte);
				
				bar_botones.agregarReporte();
				
				sel_rep.setId("sel_rep");
				agregarComponente(sel_rep);	
		tab_balance_inicial.setId("tab_balance_inicial");  
		tab_balance_inicial.setTabla("cont_balance_general", "ide_balance", 1);	
		tab_balance_inicial.setHeader("BALANCE GENERAL");
		tab_balance_inicial.getColumna("ide_cuenta").setCombo(ser_contabilidad.getCuentaContable("true,false"));
		tab_balance_inicial.getColumna("ide_cuenta").setAutoCompletar();
		tab_balance_inicial.getColumna("ide_cuenta").setLongitud(200);

		tab_balance_inicial.setLectura(true);
		tab_balance_inicial.dibujar();
		PanelTabla pat_balance_inicial=new PanelTabla();
		pat_balance_inicial.setPanelTabla(tab_balance_inicial);
		Division div1 = new Division();
		div1.dividir1(pat_balance_inicial);
		agregarComponente(div1);
		
		
		Boton bot_actualizar=new Boton();
		bot_actualizar.setIcon("ui-icon-person");
		bot_actualizar.setValue("Calcular Balance General");
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
			utilitario.agregarMensajeInfo("Selecione un A�o", "");
			return;			

		}
		TablaGenerica tab_valida_anio=utilitario.consultar("select 1 as codigo, 2 as resultado from tes_caja where not extract(year from cast('"+sel_calendario.getFecha1String()+"' as date)) =extract(year from cast('"+sel_calendario.getFecha2String()+"' as date))");
		if(tab_valida_anio.getTotalFilas()>0){
			utilitario.agregarMensajeError("Fechas no Validas", "Favor ingrese fechas que se encuentren dentro de un mismo a�o");
			return;
		}
		if(sel_calendario.isVisible()){
			sel_calendario.cerrar();

			
			
			
			
			ser_contabilidad.generarBalComprobacion(com_anio.getValue().toString(), sel_calendario.getFecha1().toString(), sel_calendario.getFecha2().toString(), par_tipo_asiento_inicial);
			TablaGenerica tab_meses = utilitario.consultar("select 1 as codigo,extract(month from cast('"+sel_calendario.getFecha1String()+"' as date)) as mes_inicial, extract(month from cast('"+sel_calendario.getFecha2String()+"' as date)) as mes_final");
			
			//// RECALCULO ESTADO DE RESULTADO
				
			String actualiza_tabla_estado="update cont_estado_resultado"
					+" set valor = 0,"
					+" periodo_inicial="+tab_meses.getValor("mes_inicial") +","
					+" periodo_final="+tab_meses.getValor("mes_final")+","
					+" fecha_inicial='"+sel_calendario.getFecha1String()+"',"
					+" fecha_final='"+sel_calendario.getFecha2String()+"'";
			utilitario.getConexion().ejecutarSql(actualiza_tabla_estado);
			
			
			String traspasa_valores_estado="update cont_estado_resultado"
					+" set valor = nuevovalor* signo"
					+" from ("
					+" select ide_cocac,(case when debe_saldo_cobac = 0 then haber_saldo_cobac else debe_saldo_cobac end) as nuevovalor from cont_balance_comprobacion ) a where ide_cocac=ide_cuenta";
			utilitario.getConexion().ejecutarSql(traspasa_valores_estado);
			
			String calcula_tabla_estado="update cont_estado_resultado"
					+" set valor = val"
					+" from ("
					+" select sum(valor) as val,con_ide_estado_resultado"
					+" from cont_estado_resultado"
					+" group by con_ide_estado_resultado"
					+" ) a where a.con_ide_estado_resultado = cont_estado_resultado.ide_estado_resultado";
			int k=0;
			while (k<=3){
			utilitario.getConexion().ejecutarSql(calcula_tabla_estado);
			 k++;
            }
			
			//// HASTA AQUI RECALCULO ESTADO DE RESULTADO

			
			String actualiza_tabla="update cont_balance_general"
					+" set valor = 0,"
					+" periodo_inicial="+tab_meses.getValor("mes_inicial") +","
					+" periodo_final="+tab_meses.getValor("mes_final") +","
					+" fecha_inicial='"+sel_calendario.getFecha1String()+"',"
					+" fecha_final='"+sel_calendario.getFecha2String()+"'";
			utilitario.getConexion().ejecutarSql(actualiza_tabla);
			
			
			String traspasa_valores="update cont_balance_general"
					+" set valor = nuevovalor* signo"
					+" from ("
					+" select ide_cocac,(case when debe_saldo_cobac = 0 then haber_saldo_cobac else debe_saldo_cobac end) as nuevovalor from cont_balance_comprobacion ) a where ide_cocac=ide_cuenta";
			utilitario.getConexion().ejecutarSql(traspasa_valores);

			String resultado_ejercicio="update cont_balance_general set valor = a.valor from (select valor from cont_estado_resultado where ide_estado_resultado =54) a where ide_balance=314";
			utilitario.getConexion().ejecutarSql(resultado_ejercicio);			
			
			String calcula_tabla="update cont_balance_general"
					+" set valor = val"
					+" from ("
					+" select sum(valor) as val,nivord"
					+" from cont_balance_general"
					+" group by nivord"
					+" ) a where a.nivord = cont_balance_general.ide_balance";
			
			int i=0;
			while (i<=3){
			utilitario.getConexion().ejecutarSql(calcula_tabla);
			 i++;
            }
			
			tab_balance_inicial.ejecutarSql();
			utilitario.addUpdate("tab_balance_inicial");
		}
		else{
			sel_calendario.dibujar();
		}
	}
		public void seleccionaElAnio (){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Selecione un A�o", "");
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
		
			
			
			tab_balance_inicial.ejecutarSql();
			utilitario.addUpdate("tab_balance_inicial");
		
	}
		
		public void generarEstadoResultado(){
			ser_contabilidad.generarBalComprobacion(com_anio.getValue().toString(), sel_calendario.getFecha1().toString(), sel_calendario.getFecha2().toString(), par_tipo_asiento_inicial);
			TablaGenerica tab_meses = utilitario.consultar("select 1 as codigo,extract(month from cast('"+sel_calendario.getFecha1String()+"' as date)) as mes_inicial, extract(month from cast('"+sel_calendario.getFecha2String()+"' as date)) as mes_final");
			
			String actualiza_tabla="update cont_estado_resultado"
					+" set valor = 0,"
					+" periodo_inicial="+tab_meses.getValor("mes_inicial") +","
					+" periodo_final="+tab_meses.getValor("mes_final")+","
					+" fecha_inicial='"+sel_calendario.getFecha1String()+"',"
					+" fecha_final='"+sel_calendario.getFecha2String()+"'";
			utilitario.getConexion().ejecutarSql(actualiza_tabla);
			
			
			String traspasa_valores="update cont_estado_resultado"
					+" set valor = nuevovalor* signo"
					+" from ("
					+" select ide_cocac,(case when debe_saldo_cobac = 0 then haber_saldo_cobac else debe_saldo_cobac end) as nuevovalor from cont_balance_comprobacion ) a where ide_cocac=ide_cuenta";
			utilitario.getConexion().ejecutarSql(traspasa_valores);
			
			String calcula_tabla="update cont_estado_resultado"
					+" set valor = val"
					+" from ("
					+" select sum(valor) as val,con_ide_estado_resultado"
					+" from cont_estado_resultado"
					+" group by con_ide_estado_resultado"
					+" ) a where a.con_ide_estado_resultado = cont_estado_resultado.ide_estado_resultado";
			int i=0;
			while (i<=3){
			utilitario.getConexion().ejecutarSql(calcula_tabla);
			 i++;
            }
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
	@Override
	public void abrirListaReportes() {
		// TODO Auto-generated method stub
		rep_reporte.dibujar();
	}
	@Override
	public void aceptarReporte() {
		// TODO Auto-generated method stub
		if(rep_reporte.getReporteSelecionado().equals("Balance General")){
			
		
			
			if (rep_reporte.isVisible()){
				rep_reporte.cerrar();	
				map_parametros.put("titulo","ESTADO DE SITUACION FINANCIERA");
				
					
				map_parametros.put("contador_general",  utilitario.getVariable("p_nombre_contador"));				
				map_parametros.put("coordinador_finaciero",  utilitario.getVariable("p_nombre_coordinador_fin"));
				map_parametros.put("gerente",  utilitario.getVariable("p_nombre_gerente"));				
				map_parametros.put("cargo_gerente",  utilitario.getVariable("p_cargo_gerente"));

				sel_rep.setSeleccionFormatoReporte(map_parametros,rep_reporte.getPath());
				sel_rep.dibujar();
				}
		} 
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
	public Reporte getRep_reporte() {
		return rep_reporte;
	}
	public void setRep_reporte(Reporte rep_reporte) {
		this.rep_reporte = rep_reporte;
	}
	public SeleccionFormatoReporte getSel_rep() {
		return sel_rep;
	}
	public void setSel_rep(SeleccionFormatoReporte sel_rep) {
		this.sel_rep = sel_rep;
	}

	


}
