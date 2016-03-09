package paq_presupuesto;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import paq_sistema.ejb.ServicioSeguridad;
import paq_sistema.aplicacion.Utilitario;


public class pre_reforma_grupo extends Pantalla {
	private SeleccionTabla set_poa=new SeleccionTabla();
	private Combo com_anio=new Combo();
	private String empleado;
	private Tabla tab_reforma_grupo=new Tabla();
	public static String par_modulo_municipio;
	public static String par_modulo_hospitalarios;
	public static String par_modulo_tasas_recoleccion;
	public static String par_modulo_ruminihaui;
	public static String par_modulo_escombreras;
	public static String par_modulo_metro_quito;
	public static String par_modulo_convenios;
	public static String par_modulo_otros_gestores;
	public static String par_modulo_tasas_anticpipos_prov_anteriores;
	public static String par_modulo_saldos_bancos;
	public static String par_modulo_comercializacion_rsu;
	public static String par_modulo_tasas_ruminahui_diciembre;
	public static String par_modulo_tasas_recoleccion_diciembre;
	public static String par_modulo_cuentas_por_cobrar_hosp;


	  @EJB
	  private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	   @EJB
	  private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto)utilitario.instanciarEJB(ServicioPresupuesto.class);
	   @EJB
	 private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	   @EJB
	 private ServicioSeguridad ser_seguridad = (ServicioSeguridad) utilitario.instanciarEJB(ServicioSeguridad.class);
	 			
	public pre_reforma_grupo(){
		

		///BOTON COMBO
		empleado=ser_seguridad.getUsuario(utilitario.getVariable("ide_usua")).getValor("ide_gtemp");
		System.out.println("empleado"+empleado);
		if(empleado==null ||empleado.isEmpty()){
			utilitario.agregarNotificacionInfo("Mensaje", "No exixte usuario registrado para el registro de compras");
			return;
		}

		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		
	        
	     
	       

		tab_reforma_grupo.setId("tab_reforma_grupo");
		tab_reforma_grupo.setTabla("pre_reforma_grupo", "ide_prepoa",1);
		tab_reforma_grupo.getColumna("ide_prepoa").setLectura(true);
		tab_reforma_grupo.getColumna("ide_prepoa").setVisible(false);
		
		tab_reforma_grupo.getColumna("ide_municipio").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_municipio").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_municipio").setLectura(true);
		
		tab_reforma_grupo.getColumna("ide_hospitalarios").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_hospitalarios").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_hospitalarios").setLectura(true);
		
		tab_reforma_grupo.getColumna("ide_tasa_recoleccion").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_tasa_recoleccion").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_tasa_recoleccion").setLectura(true);

		
		tab_reforma_grupo.getColumna("ide_ruminahi").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_ruminahi").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_ruminahi").setLectura(true);

		
		
		tab_reforma_grupo.getColumna("ide_escombreras").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_escombreras").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_escombreras").setLectura(true);

		
		tab_reforma_grupo.getColumna("ide_metro_quito").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_metro_quito").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_metro_quito").setLectura(true);

		
		tab_reforma_grupo.getColumna("ide_convenios").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_convenios").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_convenios").setLectura(true);

		
		
		tab_reforma_grupo.getColumna("ide_otros_gestores").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_otros_gestores").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_otros_gestores").setLectura(true);

		
		
		tab_reforma_grupo.getColumna("ide_saldo_bancos").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_saldo_bancos").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_saldo_bancos").setLectura(true);

		
		
		
		tab_reforma_grupo.getColumna("ide_com_rsu_aprov").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_com_rsu_aprov").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_com_rsu_aprov").setLectura(true);

		
		tab_reforma_grupo.getColumna("ide_tas_mu_ru_dic").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_tas_mu_ru_dic").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_tas_mu_ru_dic").setLectura(true);

		
		
		tab_reforma_grupo.getColumna("ide_tas_rec_dic").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_tas_rec_dic").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_tas_rec_dic").setLectura(true);

		
		
		
		tab_reforma_grupo.getColumna("ide_cue_x_cob_hosp").setCombo("pre_fuente_financiamiento", "ide_prfuf", "detalle_prfuf", "");
		tab_reforma_grupo.getColumna("ide_cue_x_cob_hosp").setAutoCompletar();
		tab_reforma_grupo.getColumna("ide_cue_x_cob_hosp").setLectura(true);

		

		
		tab_reforma_grupo.setGenerarPrimaria(false);
		

		tab_reforma_grupo.dibujar();
		PanelTabla pat_panel=new PanelTabla();
		pat_panel.setPanelTabla(tab_reforma_grupo);
		
		Division div_division=new Division();
		div_division.setId("div_division");
		div_division.dividir1(pat_panel);
		agregarComponente(div_division);
		
		
	/////boton buscar poa
			Boton bot_buscar=new Boton();
			bot_buscar.setIcon("ui-icon-person");
			bot_buscar.setValue("Buscar POA");
			bot_buscar.setMetodo("importarPoa");
			bar_botones.agregarBoton(bot_buscar);
			
			set_poa.setId("set_poa");
			set_poa.setSeleccionTabla(ser_presupuesto.getPoaNombre("-1"),"ide_prpoa");
			set_poa.setTitle("Seleccione Poa");

			set_poa.getTab_seleccion().getColumna("codigo_clasificador_prcla").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("descripcion_clasificador_prcla").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("DETALLE_PROGRAMA").setFiltro(true);//pone filtro
			//set_poa.getTab_seleccion().getColumna("PROGRAMA").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("DETALLE_PROYECTO").setFiltro(true);
		//	set_poa.getTab_seleccion().getColumna("PROYECTO").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("DETALLE_PRODUCTO").setFiltro(true);
			//set_poa.getTab_seleccion().getColumna("PRODUCTO").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("DETALLE_ACTIVIDAD").setFiltro(true);
		//	set_poa.getTab_seleccion().getColumna("ACTIVIDAD").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("DETALLE_SUBACTIVIDAD").setFiltro(true);
		//	set_poa.getTab_seleccion().getColumna("SUBACTIVIDAD").setFiltro(true);
			set_poa.getTab_seleccion().getColumna("CODIGO_SUBACTIVIDAD").setFiltro(true);
		//	set_poa.getTab_seleccion().getColumna("NUM_RESOLUCION_PRPOA").setFiltro(true);
			
			

			set_poa.getBot_aceptar().setMetodo("aceptarPoa");
			//set_poa.setRadio();
			agregarComponente(set_poa);
		
	}
	
////importar poa
	public void importarPoa(){
		System.out.println(" ingresar al importar");
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un año", "");
			return;
		}
		

		set_poa.getTab_seleccion().setSql(ser_presupuesto.getPoaNombre(com_anio.getValue().toString()));
		set_poa.getTab_seleccion().ejecutarSql();
		set_poa.dibujar();

	}
	
	
	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			//String str_seleccionados= set_poa.getSeleccionados();

			//tab_reforma_grupo.setCondicion("ide_prepoa in ("+str_seleccionados+")");

		tab_reforma_grupo.setCondicion("ide_prepoa="+com_anio.getValue());
			tab_reforma_grupo.ejecutarSql();
			//tab_reforma_grupo.ejecutarValorForanea(tab_certificacion.getValorSeleccionado());


		}
	}
	
	
	
	public  void aceptarPoa(){
		String str_seleccionados= set_poa.getSeleccionados();
		System.out.println(" ingresar al aceptar");
		if (str_seleccionados!=null){
			TablaGenerica tab_poa = ser_presupuesto.getTablaGenericaPoa(str_seleccionados);		
			for(int i=0;i<tab_poa.getTotalFilas();i++){
				//TablaGenerica saldo_poa=utilitario.consultar(ser_presupuesto.getSaldoPoa(tab_poa.getValor( i,"ide_prepoa")));
		
				tab_reforma_grupo.insertar();
				tab_reforma_grupo.setValor("pre_proa", tab_poa.getValor( i,"ide_prpoa"));
				TablaGenerica tab_fuente_financiamiento=utilitario.consultar("select ide_prpof, " +
						"ide_prfuf,ide_prpoa,ide_coest,valor_financiamiento_prpof,activo_prpof  " +
						"from pre_poa_financiamiento " +
						"where ide_prpoa="+tab_poa.getValor(i,"ide_prpoa"));
				
				
				
				par_modulo_municipio=utilitario.getVariable("p_modulo_municipio");
				par_modulo_hospitalarios=utilitario.getVariable("p_modulo_hospitalario");
				par_modulo_tasas_recoleccion=utilitario.getVariable("p_modulo_tasas_recoleccion");
				par_modulo_ruminihaui=utilitario.getVariable("p_modulo_ruminahui");
				par_modulo_escombreras=utilitario.getVariable("p_modulo_escombreras");
				par_modulo_metro_quito=utilitario.getVariable("p_modulo_metro_quito");
				par_modulo_convenios=utilitario.getVariable("p_modulo_convenios");
				par_modulo_otros_gestores=utilitario.getVariable("p_modulo_otros_gestores");
				par_modulo_tasas_anticpipos_prov_anteriores=utilitario.getVariable("p_modulo_tasas_anticpipos_prov_anteriores");
				par_modulo_saldos_bancos=utilitario.getVariable("p_modulo_saldos_bancos");
				par_modulo_comercializacion_rsu=utilitario.getVariable("p_modulo_comercializacion_rsu");
				par_modulo_tasas_ruminahui_diciembre=utilitario.getVariable("p_modulo_tasas_ruminahui_diciembre");
				par_modulo_tasas_recoleccion_diciembre=utilitario.getVariable("p_modulo_tasas_recoleccion_diciembre");
				par_modulo_cuentas_por_cobrar_hosp=utilitario.getVariable("p_modulo_cuentas_por_cobrar_hosp");
				
				
				
//validaciones para generacion de informacion consultada		
				
				for (int j = 0; j < tab_fuente_financiamiento.getTotalFilas(); j++) {
				
					if (par_modulo_municipio.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						tab_reforma_grupo.setValor("ide_municipio", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
						TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(),tab_fuente_financiamiento.getValor( j,"ide_prfuf"),"0","-1"));

						for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
						
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma1", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }		
							
							

						}	
						
						
						
					 }
			
				 
				
					
					
				
					
				
					if (par_modulo_hospitalarios.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						tab_reforma_grupo.setValor("ide_hospitalarios", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
						TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));

						for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma2", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
     				}
				
//					
//					
//					
//					
					if (par_modulo_tasas_recoleccion.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
     					tab_reforma_grupo.setValor("ide_tasa_recoleccion", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));

     					for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
    						
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma3", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}			
					}
				
	
					if (par_modulo_ruminihaui.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						tab_reforma_grupo.setValor("ide_ruminahi", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));

						for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma4", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
						
						
					}
					
					
					if (par_modulo_escombreras.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						tab_reforma_grupo.setValor("ide_escombreras", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));

						for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma5", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
				
					}
					
					if (par_modulo_metro_quito.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						tab_reforma_grupo.setValor("ide_metro_quito", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
					    for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma6", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					
					}
					
					
					if (par_modulo_convenios.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						tab_reforma_grupo.setValor("ide_convenios", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
					    for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma7", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					
					}
					
					
					
					if (par_modulo_otros_gestores.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
						tab_reforma_grupo.setValor("ide_otros_gestores", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
				
	for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma8", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}				
					}
					
								
					if (par_modulo_tasas_anticpipos_prov_anteriores.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
						
						tab_reforma_grupo.setValor("ide_ant_en_prov_anter", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));

	                  for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma9", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					
					}

					
					if (par_modulo_saldos_bancos.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
						tab_reforma_grupo.setValor("ide_saldo_bancos", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
	for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma10", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					}
					
					
					if (par_modulo_comercializacion_rsu.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
						tab_reforma_grupo.setValor("ide_com_rsu_aprov", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
	for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma11", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					
					}
					
					
					if (par_modulo_tasas_ruminahui_diciembre.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
						tab_reforma_grupo.setValor("ide_tas_mu_ru_dic", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
	for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma12", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					}
					
					
					if (par_modulo_tasas_recoleccion_diciembre.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
						tab_reforma_grupo.setValor("ide_tas_rec_dic", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
	for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma13", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					}
					
					if (par_modulo_cuentas_por_cobrar_hosp.equals(tab_fuente_financiamiento.getValor(j,"ide_prfuf"))) {
					    TablaGenerica tab_saldos_grupo_fuente=utilitario.consultar(ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf"),"0","-1"));
						tab_reforma_grupo.setValor("ide_cue_X_cob_hosp", tab_fuente_financiamiento.getValor( j,"ide_prfuf"));
	for (int k = 0; k < tab_saldos_grupo_fuente.getTotalFilas(); k++) {
							
							if (tab_saldos_grupo_fuente.getValor(k,"ide_prpoa").equals(tab_fuente_financiamiento.getValor(j,"ide_prpoa"))) {
								tab_reforma_grupo.setValor("valor_reforma14", tab_saldos_grupo_fuente.getValor(k,"valor_saldo_fuente"));
						
						 
						 }					
						}	
					}
				}
//					
				
			
			   
//				//	ser_presupuesto.getPoaSaldosFuenteFinanciamiento(com_anio.getValue().toString(), tab_fuente_financiamiento.getValor(j,"ide_prfuf") ,"0","-1");
//			
				
//				
		}
			for(int i=0;i<tab_poa.getTotalFilas();i++){

			
			String valor_nuevo="0";
			if (tab_reforma_grupo.getValor(i,"valor_reforma1")==null) {
				System.out.println("valor nuevo =0");
						valor_nuevo="0";
			tab_reforma_grupo.setValor(i,"valor_reforma1", valor_nuevo);
			tab_reforma_grupo.getColumna("valor_reforma1").setLectura(true);
			tab_reforma_grupo.getColumna("saldo_reforma1").setLectura(true);
utilitario.addUpdate("tab_reforma_grupo");
						
			}else
				System.out.println("valor nuevo =1 ");
				valor_nuevo= tab_reforma_grupo.getValor(i,"valor_reforma1");
			set_poa.cerrar();
			utilitario.addUpdate("tab_reforma_grupo");
			}
		}
		else{
			utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
		}
	
	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_reforma_grupo.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		 tab_reforma_grupo.guardar();
			//( tab_reforma_grupo.setGenerarPrimaria(false);
	            guardarPantalla();
	       
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	      tab_reforma_grupo.eliminar();
	}


	public SeleccionTabla getSet_poa() {
		return set_poa;
	}

	public void setSet_poa(SeleccionTabla set_poa) {
		this.set_poa = set_poa;
	}

	public Combo getCom_anio() {
		return com_anio;
	}

	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}

	public Tabla getTab_reforma_grupo() {
		return tab_reforma_grupo;
	}

	public void setTab_reforma_grupo(Tabla tab_reforma_grupo) {
		this.tab_reforma_grupo = tab_reforma_grupo;
	}

	
}
