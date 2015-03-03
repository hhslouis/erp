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
import framework.componentes.Tabulador;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;

public class pre_tramite extends Pantalla   {

	public static String par_tramite;
	public static String par_tramite_alterno;

	private Tabla tab_tramite=new Tabla();
	private Tabla tab_poa_tramite=new Tabla();
	private Tabla tab_documento=new Tabla();
	private Tabla tab_archivo=new Tabla();
	private Combo com_anio=new Combo();
	private SeleccionTabla set_tramite=new SeleccionTabla();
	private SeleccionTabla set_poa=new SeleccionTabla();
	private SeleccionTabla set_tramite_alterno=new SeleccionTabla();



	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	@EJB
	private ServicioBodega ser_Bodega=(ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	@EJB
	private ServicioPresupuesto ser_presupuesto=(ServicioPresupuesto)utilitario.instanciarEJB(ServicioPresupuesto.class);
	public pre_tramite(){

		par_tramite=utilitario.getVariable("p_modulo_tramite");
		par_tramite_alterno=utilitario.getVariable("p_modulo_tramite_alterno");


		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		tab_tramite.setId("tab_tramite");
		tab_tramite.setHeader("TRAMITE");
		tab_tramite.setTabla("pre_tramite","ide_prtra", 1);
		tab_tramite.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_tramite.getColumna("ide_tepro").setCombo(ser_Bodega.getProveedor("true,false"));
		tab_tramite.getColumna("ide_copag").setCombo("cont_parametros_general", "ide_copag", "detalle_copag", "");
		tab_tramite.getColumna("ide_geani").setCombo(ser_contabilidad.getAnio("true,false","true,false"));
		tab_tramite.getColumna("total_compromiso_prtra").setEtiqueta();
		tab_tramite.getColumna("total_compromiso_prtra").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo


		tab_tramite.setTipoFormulario(true);
		tab_tramite.getGrid().setColumns(4);

		tab_tramite.agregarRelacion(tab_poa_tramite);
		tab_tramite.agregarRelacion(tab_archivo);
		tab_tramite.agregarRelacion(tab_documento);
		tab_tramite.dibujar();
		PanelTabla pat_tramite=new PanelTabla();
		pat_tramite.setPanelTabla(tab_tramite);

		///poa tramite
		tab_poa_tramite.setId("tab_poa_tramite");
		tab_poa_tramite.setHeader("POA TRAMITE");
		tab_poa_tramite.setIdCompleto("tab_tabulador:tab_poa_tramite");
		tab_poa_tramite.setTabla("pre_poa_tramite", "ide_prpot", 2);
		tab_poa_tramite.getColumna("comprometido_prpot").setMetodoChange("CalcularSuma");
		tab_poa_tramite.dibujar();
		PanelTabla pat_panel2=new PanelTabla();
		pat_panel2.setPanelTabla(tab_poa_tramite);

		//// documento habilitante
		tab_documento.setId("tab_documento");
		tab_documento.setHeader("DOCUMENTO HABILITANTE");
		tab_documento.setIdCompleto("tab_tabulador:tab_documento");
		tab_documento.setTabla("pre_documento_habilitante", "ide_prdoh", 3);
		tab_documento.dibujar();
		PanelTabla pat_panel3=new PanelTabla();
		pat_panel3.setPanelTabla(tab_documento);

		/// ARCHIVO
		tab_archivo.setId("tab_archivo");
		tab_archivo.setHeader("ARCHIVOS ANEXOS");
		tab_archivo.setIdCompleto("tab_tabulador:tab_archivo");
		tab_archivo.setTipoFormulario(true);
		tab_archivo.setTabla("pre_archivo","ide_prarc",4);
		tab_archivo.getColumna("foto_prarc").setUpload("presupuesto");
		tab_archivo.setCondicion("ide_prtra!=null");
		tab_archivo.dibujar();
		PanelTabla pat_panel4= new PanelTabla();
		pat_panel4.setPanelTabla(tab_archivo);



		///// tabuladores
		Tabulador tab_tabulador=new Tabulador();
		tab_tabulador.setId("tab_tabulador");

		tab_tabulador.agregarTab("POA TRAMITE", pat_panel2);
		tab_tabulador.agregarTab("DOCUMENTO HABILITANTE", pat_panel3);
		tab_tabulador.agregarTab("ARCHIVOS ANEXOS", pat_panel4);

		Division div_tramite =new Division();
		div_tramite.dividir2(pat_tramite, tab_tabulador, "50%", "h");
		agregarComponente(div_tramite);

		set_tramite.setId("set_tramite");
		set_tramite.setSeleccionTabla(ser_contabilidad.getModuloParametros("true,false", par_tramite),"ide_copag");
		set_tramite.setTitle("Seleccione el Tramite");
		set_tramite.getBot_aceptar().setMetodo("aceptarTramite");
		set_tramite.setRadio();
		agregarComponente(set_tramite);

		set_tramite_alterno.setId("set_tramite_alterno");
		set_tramite_alterno.setSeleccionTabla(ser_contabilidad.getModuloParametros("true,false", par_tramite_alterno),"ide_copag");
		set_tramite_alterno.setTitle("Seleccione el Tramite Alterno");
		set_tramite_alterno.getBot_aceptar().setMetodo("aceptarTramite");
		set_tramite_alterno.setRadio();
		agregarComponente(set_tramite_alterno);

		Boton bot_buscar=new Boton();
		bot_buscar.setIcon("ui-icon-person");
		bot_buscar.setValue("Buscar POA");
		bot_buscar.setMetodo("importarPoa");
		bar_botones.agregarBoton(bot_buscar);

		set_poa.setId("set_poa");
		set_poa.setSeleccionTabla(ser_presupuesto.getPoa("true,false"),"ide_prpoa");
		set_poa.setTitle("Seleccione Poa");
		set_poa.getBot_aceptar().setMetodo("aceptarPoa");
		agregarComponente(set_poa);


	}
	public void importarPoa(){
		System.out.println(" ingresar al importar");
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}

		set_poa.getTab_seleccion().setSql(ser_presupuesto.getPoa("true,false"));
		set_poa.getTab_seleccion().ejecutarSql();
		set_poa.dibujar();

	}

	public  void aceptarPoa(){
		String str_seleccionados = set_poa.getSeleccionados();

		if (str_seleccionados!=null){
			TablaGenerica tab_poa = ser_presupuesto.getTablaGenericaPoa(str_seleccionados);		
			for(int i=0;i<tab_poa.getTotalFilas();i++){
				tab_poa_tramite.insertar();
				tab_poa_tramite.setValor("ide_prpoa", tab_poa.getValor(i, "ide_prpoa"));
			}
			set_poa.cerrar();
			utilitario.addUpdate("tab_poa_tramite");
		}
	}
	public void aceptarTramite(){
		if(set_tramite.isVisible()){
			if (set_tramite.getValorSeleccionado()!=null){

				tab_tramite.setValor("ide_copag", set_tramite.getValorSeleccionado());
				utilitario.addUpdate("tab_tramite");
				set_tramite.cerrar();
				set_tramite_alterno.dibujar();
			}
		}else if(set_tramite_alterno.isVisible()){
			if (set_tramite_alterno.getValorSeleccionado()!=null){
				tab_tramite.setValor("ide_copag", set_tramite_alterno.getValorSeleccionado());
				utilitario.addUpdate("tab_tramite");
				set_tramite_alterno.cerrar();
				utilitario.agregarMensajeInfo("Tramite Alterno ", "ingreso al tramite alterno");
			}
		}
	}
	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_tramite.setCondicion("ide_geani="+com_anio.getValue());
			tab_tramite.ejecutarSql();
			//tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());

		}
		else{
			tab_tramite.setCondicion("ide_geani=-1");
			tab_tramite.ejecutarSql();
		}
	}
	public void CalcularSuma(){

		tab_tramite.setValor("total_compromiso_prtra",tab_poa_tramite.getSumaColumna("comprometido_prpot")+"");
		utilitario.addUpdateTabla(tab_tramite, "total_compromiso_prtra","");	

	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
		set_tramite.getTab_seleccion().setSql(ser_contabilidad.getModuloParametros("true", par_tramite));
		set_tramite.getTab_seleccion().ejecutarSql();
		set_tramite.dibujar();
		utilitario.addUpdate("tab_tramite");

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_tramite.guardar()){
			if (tab_poa_tramite.guardar()){
				if (tab_documento.guardar()){
					tab_archivo.guardar();
				}
			}
		}
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

	}


	public Tabla getTab_tramite() {
		return tab_tramite;
	}


	public void setTab_tramite(Tabla tab_tramite) {
		this.tab_tramite = tab_tramite;
	}


	public Tabla getTab_poa_tramite() {
		return tab_poa_tramite;
	}


	public void setTab_poa_tramite(Tabla tab_poa_tramite) {
		this.tab_poa_tramite = tab_poa_tramite;
	}


	public Tabla getTab_documento() {
		return tab_documento;
	}


	public void setTab_documento(Tabla tab_documento) {
		this.tab_documento = tab_documento;
	}


	public Tabla getTab_archivo() {
		return tab_archivo;
	}


	public void setTab_archivo(Tabla tab_archivo) {
		this.tab_archivo = tab_archivo;
	}

	public Combo getCom_anio() {
		return com_anio;
	}

	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}

	public SeleccionTabla getSet_tramite() {
		return set_tramite;
	}

	public void setSet_tramite(SeleccionTabla set_tramite) {
		this.set_tramite = set_tramite;
	}
	public SeleccionTabla getSet_poa() {
		return set_poa;
	}
	public void setSet_poa(SeleccionTabla set_poa) {
		this.set_poa = set_poa;
	}
	public SeleccionTabla getSet_tramite_alterno() {
		return set_tramite_alterno;
	}
	public void setSet_tramite_alterno(SeleccionTabla set_tramite_alterno) {
		this.set_tramite_alterno = set_tramite_alterno;
	}


}
