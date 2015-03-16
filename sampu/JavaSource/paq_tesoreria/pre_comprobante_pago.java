package paq_tesoreria;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.collections.SetUtils;
import org.apache.poi.hssf.record.formula.Ptg;

import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import paq_adquisicion.ejb.ServicioAdquisicion;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_presupuesto.ejb.ServicioPresupuesto;
import paq_sistema.aplicacion.Pantalla;
import paq_tesoreria.ejb.ServicioTesoreria;

public class pre_comprobante_pago extends Pantalla{
    private Tabla tab_comprobante = new Tabla();
    private Tabla tab_detalle_movimiento =new Tabla();
    private Tabla tab_retencion =new Tabla();
    private Tabla tab_detalle_retencion=new Tabla();
    private AutoCompletar aut_movimiento=new AutoCompletar();
    private SeleccionTabla set_solicitud=new SeleccionTabla();
    private SeleccionTabla set_tramite=new SeleccionTabla();
    private SeleccionTabla set_impuesto=new SeleccionTabla();
    private SeleccionTabla set_retencion=new SeleccionTabla();
	public static String par_impuesto_renta;
	public static String par_impuesto_iva;



    @EJB
    private ServicioAdquisicion ser_Adquisicion=(ServicioAdquisicion) utilitario.instanciarEJB(ServicioAdquisicion.class);
    @EJB
    private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);
    @EJB
    private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
    @EJB
    private ServicioPresupuesto ser_Presupuesto = (ServicioPresupuesto) utilitario.instanciarEJB(ServicioPresupuesto.class);
    @EJB
    private ServicioBodega ser_Bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
    @EJB
    private ServicioTesoreria ser_Tesoreria = (ServicioTesoreria) utilitario.instanciarEJB(ServicioTesoreria.class);
    @EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad) utilitario.instanciarEJB(ServicioContabilidad.class);

    
    public pre_comprobante_pago (){
		par_impuesto_iva=utilitario.getVariable("p_tes_impuesto_iva");
		par_impuesto_renta=utilitario.getVariable("p_tes_impuesto_renta");

        Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");

        tab_comprobante.setId("tab_comprobante");
        tab_comprobante.setHeader("COMPROBANTE DE PAGO");
        tab_comprobante.setTabla("tes_comprobante_pago", "ide_tecpo", 1);
        tab_comprobante.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest","detalle_coest","");
        tab_comprobante.getColumna("IDE_PRTRA").setCombo(ser_Presupuesto.getTramite("true"));
        tab_comprobante.getColumna("IDE_PRTRA").setAutoCompletar();
        tab_comprobante.getColumna("IDE_COEST").setCombo("cont_estado","ide_coest","detalle_coest","");
        tab_comprobante.getColumna("IDE_TEPRO").setCombo(ser_Bodega.getProveedor("true,false"));
        tab_comprobante.getColumna("IDE_TEPRO").setAutoCompletar();
        tab_comprobante.getColumna("IDE_GEDIP").setCombo(ser_gestion.getSqlDivisionPoliticaCiudad());
        tab_comprobante.getColumna("IDE_GEEDP").setCombo(ser_nomina.servicioEmpleadoContrato("true"));
        tab_comprobante.getColumna("IDE_ADSOC").setCombo(ser_Adquisicion.getSolicitudCompra("true"));
        tab_comprobante.getColumna("ide_tetic").setCombo("tes_tipo_concepto", "ide_tetic", "detalle_tetic,fecha_pago_tetic", "");
        tab_comprobante.getColumna("fecha_tecpo").setRequerida(true);
        tab_comprobante.getColumna("comprobante_egreso_tecpo").setRequerida(true);
        tab_comprobante.getColumna("detalle_tecpo").setRequerida(true);

        // tab_comprobante.getColumna("ide_comov").setCombo("cont_movimiento", "ide_comov", "nro_comprobante_comov", "");
        tab_comprobante.setTipoFormulario(true);
        tab_comprobante.getGrid().setColumns(4);
        tab_comprobante.agregarRelacion(tab_retencion);

        tab_comprobante.dibujar();
        PanelTabla pat_comprobante=new PanelTabla();
        pat_comprobante.setPanelTabla(tab_comprobante);
        ///DETALLE MOVIMIENTO
    	/////detalle movinto
		tab_detalle_movimiento.setId("tab_detalle_movimiento");
		tab_detalle_movimiento.setIdCompleto("tab_tabulador:tab_detalle_movimiento");
		tab_detalle_movimiento.setHeader("DETALLE DE MOVIMIENTO");
		tab_detalle_movimiento.setTabla("cont_detalle_movimiento", "ide_codem", 2);
		 //filtra por asiento contable cuando no tiene relacion a tes_comprovante_pago
        tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));    
        tab_detalle_movimiento.getColumna("ide_comov").setVisible(false);
        tab_detalle_movimiento.getColumna("detalle_codem").setVisible(false);
		//tab_detalle_movimiento.getColumna("ide_prcla").setCombo(ser_Presupuesto.getCatalogoPresupuestario("true,false"));
		tab_detalle_movimiento.getColumna("ide_prcla").setAutoCompletar();
		//tab_detalle_movimiento.getColumna("ide_prpro").setCombo("pre_programa", "ide_prpro", "cod_programa_prpro", "");
		//tab_detalle_movimiento.getColumna("ide_cocac").setCombo(ser_contabilidad.servicioCatalogoCuentasTransaccion());
		tab_detalle_movimiento.getColumna("activo_codem").setLectura(true);
		tab_detalle_movimiento.getColumna("activo_codem").setValorDefecto("true");
		tab_detalle_movimiento.getColumna("haber_codem").setMetodoChange("calcularTotal");			
		tab_detalle_movimiento.setColumnaSuma("haber_codem,debe_codem");			
		tab_detalle_movimiento.getColumna("debe_codem").setMetodoChange("calcularTotal");			
				

		tab_detalle_movimiento.getGrid().setColumns(4);
		tab_detalle_movimiento.dibujar();
		PanelTabla pat_detalle_movimiento=new PanelTabla();
		pat_detalle_movimiento.setPanelTabla(tab_detalle_movimiento);


        ///RETENCION
        tab_retencion.setId("tab_retencion");
        tab_retencion.setIdCompleto("tab_tabulador:tab_retencion");
        //tab_retencion.setHeader("RETENCION");
        tab_retencion.setTabla("tes_retencion", "ide_teret", 3);
       
        tab_retencion.getColumna("total_ret_teret").setEtiqueta();
        tab_retencion.getColumna("total_ret_teret").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");
        tab_retencion.getColumna("total_ret_teret").setValorDefecto("0.00");
        tab_retencion.setTipoFormulario(true);
        tab_retencion.getGrid().setColumns(4);
        tab_retencion.agregarRelacion(tab_detalle_retencion);
        tab_retencion.dibujar();
        PanelTabla pat_retencion =new PanelTabla();
        pat_retencion.setPanelTabla(tab_retencion);

        Etiqueta eti_retencion=new Etiqueta(); 
        eti_retencion.setValue("RETENCION");
        eti_retencion.setStyle("font-size: 13px;color: red;font-weight: bold");


        pat_retencion.setHeader(eti_retencion);
    
        ///DETALLE RETENCION
        tab_detalle_retencion.setId("tab_detalle_retencion");
        tab_detalle_retencion.setIdCompleto("tab_tabulador:tab_detalle_retencion");
        //tab_detalle_retencion.setHeader("DETALLE RETENCION");
        tab_detalle_retencion.setTabla("tes_detalle_retencion", "ide_teder", 4);
        tab_detalle_retencion.getColumna("ide_teimp").setCombo("tes_impuesto", "ide_teimp", "codigo_teimp,porcentaje_teimp,detalle_teimp", "");
        tab_detalle_retencion.getColumna("ide_teimp").setLectura(true);
        tab_detalle_retencion.getColumna("ide_teimp").setAutoCompletar();
        tab_detalle_retencion.getColumna("valor_retenido_teder").setEtiqueta();
        tab_detalle_retencion.getColumna("valor_retenido_teder").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
        tab_detalle_retencion.getColumna("valor_retenido_teder").setValorDefecto("0.00");

        tab_detalle_retencion.setTipoFormulario(true);
        tab_detalle_retencion.getGrid().setColumns(2);

        tab_detalle_retencion.dibujar();
        PanelTabla pat_detalle_retencion=new PanelTabla();
        pat_detalle_retencion.setPanelTabla(tab_detalle_retencion);

        ////para obteber las dos ventanas retencion y detalla retención
        Etiqueta eti_detalle_retencion=new Etiqueta(); 
        eti_detalle_retencion.setValue("DETALLE RETENCION");
        eti_detalle_retencion.setStyle("font-size: 13px;color: red;font-weight: bold");

        pat_detalle_retencion.setHeader(eti_detalle_retencion);


        Grid gri=new Grid();
        gri.setColumns(2);
        gri.getChildren().add(pat_retencion);
        gri.getChildren().add(pat_detalle_retencion);



        tab_tabulador.agregarTab("DETALLE MOVIMIENTO", pat_detalle_movimiento);//intancia los tabuladores 
        tab_tabulador.agregarTab("RETENCION", gri);
        ///tab_tabulador.agregarTab("DETALLE RETENCION", pat_detalle_retencion);

        Division div_division =new Division();
        //div_division.setId("div_division");
        div_division.dividir2(pat_comprobante, tab_tabulador, "50%", "H");
        agregarComponente(div_division);

        
        ///boton tipo impuesto
        Boton bot_impuesto=new Boton();
        bot_impuesto.setIcon("ui-icon-person");
        bot_impuesto.setValue("Generar Retencion");
        bot_impuesto.setMetodo("importarImpuesto");
        bar_botones.agregarBoton(bot_impuesto);
        
        set_impuesto.setId("set_impuesto");
        set_impuesto.setSeleccionTabla("tes_tipo_impuesto", "ide_tetii", "detalle_tetii");
        set_impuesto.setTitle("SELECCIONE UN IMPUESTO");        
        set_impuesto.getBot_aceptar().setMetodo("aceptarImpuesto");
        set_impuesto.setRadio();
        agregarComponente(set_impuesto);
        // retencion
        set_retencion.setId("set_retencion");
        set_retencion.setSeleccionTabla(ser_Tesoreria.getImpuesto("true","1","0"),"ide_teimp");
        set_retencion.setTitle("SELECCIONE UNA RETENCIÓN");        
        set_retencion.getBot_aceptar().setMetodo("aceptarImpuesto");
        set_retencion.getTab_seleccion().getColumna("CODIGO_TEIMP").setFiltro(true);
		set_retencion.getTab_seleccion().getColumna("DETALLE_TEIMP").setFiltro(true);
		set_retencion.setRadio();
        agregarComponente(set_retencion);
        
        




        ////boton solicitud compra
        Boton bot_buscar=new Boton();
        bot_buscar.setIcon("ui-icon-person");
        bot_buscar.setValue("Buscar Solicitud Compra ");
        bot_buscar.setMetodo("importarSolicitudCompra");
        bar_botones.agregarBoton(bot_buscar);

        set_solicitud.setId("set_solicitud");
        set_solicitud.setSeleccionTabla(ser_Adquisicion.getSolicitudCompra("true"),"ide_adsoc");
        set_solicitud.setTitle("SELECCION UNA SOLICITUD DE COMPRA");        
        set_solicitud.getBot_aceptar().setMetodo("aceptarSolicitudCompra");
        set_solicitud.setRadio();
        agregarComponente(set_solicitud);
        ///certificacion presupuestaria
        Boton bot_busca=new Boton();
        bot_busca.setIcon("ui-icon-person");
        bot_busca.setValue("Buscar Certificaciòn Presupuestaria ");
        bot_busca.setMetodo("importarCertificacionPresupuestaria");
        bar_botones.agregarBoton(bot_busca);

        set_tramite.setId("set_tramite");
        set_tramite.setSeleccionTabla(ser_Presupuesto.getTramite("true"),"ide_prtra");
        set_tramite.setTitle("SELECCION UNA CERTIFICACION PRESUPUESTARIA");        
        set_tramite.getBot_aceptar().setMetodo("aceptarCertificacionPresupuestaria");
        set_tramite.setRadio();
        agregarComponente(set_tramite);


    }
  ///sacar valores
	
  	public void calcularTotal(AjaxBehaviorEvent evt){
  		tab_detalle_movimiento.modificar(evt);
  		tab_detalle_movimiento.sumarColumnas();
  		utilitario.addUpdate("tab_detalle_movimiento");
  	}

    public void importarSolicitudCompra(){

        set_solicitud.getTab_seleccion().setSql(ser_Adquisicion.getSolicitudCompra("true"));
        set_solicitud.getTab_seleccion().ejecutarSql();
        set_solicitud.dibujar();

    }

    public void aceptarSolicitudCompra(){

        String str_seleccionado = set_solicitud.getValorSeleccionado();
        TablaGenerica tab_solicitud=ser_Adquisicion.getTablaGenericaSolicitud(str_seleccionado);
        if (str_seleccionado!=null){
            tab_comprobante.insertar();
            tab_comprobante.setValor("ide_adsoc",str_seleccionado);
            tab_comprobante.setValor("valor_pago_tecpo",tab_solicitud.getValor("total_adfac"));
            tab_comprobante.setValor("ide_tepro",tab_solicitud.getValor("ide_tepro"));
        }
        set_solicitud.cerrar();
        utilitario.addUpdate("tab_comprobante");
    }

    public void importarCertificacionPresupuestaria(){

        set_tramite.getTab_seleccion().setSql(ser_Presupuesto.getTramite("true"));
        set_tramite.getTab_seleccion().ejecutarSql();
        set_tramite.dibujar();

    }

    public void aceptarCertificacionPresupuestaria(){

        String str_seleccionado = set_tramite.getValorSeleccionado();
        TablaGenerica tab_tramite=ser_Presupuesto.getTablaGenericaTramite(str_seleccionado);
        if (str_seleccionado!=null){
            tab_comprobante.insertar();
            tab_comprobante.setValor("ide_prtra",str_seleccionado);
        }
        set_tramite.cerrar();
        utilitario.addUpdate("tab_comprobante");
    }
    
    ////boton impuesto
    public void importarImpuesto(){

        set_impuesto.getTab_seleccion().setSql("select ide_tetii,detalle_tetii from tes_tipo_impuesto order by ide_tetii");
        set_impuesto.getTab_seleccion().ejecutarSql();
        set_impuesto.dibujar();
        
    }
    String str_seleccionado="";
    public void aceptarImpuesto(){
        if(set_impuesto.isVisible()){
            if (set_impuesto.getValorSeleccionado()!=null){
                tab_detalle_retencion.insertar();

            	if(set_impuesto.getValorSeleccionado().equals(par_impuesto_iva)){
            		tab_detalle_retencion.setValor("base_imponible_teder", tab_comprobante.getValor("valor_iva_tecpo"));
            	}
            	else if(set_impuesto.getValorSeleccionado().equals(par_impuesto_renta)){
            		tab_detalle_retencion.setValor("base_imponible_teder", tab_comprobante.getValor("valor_compra_tecpo"));
            	}
             str_seleccionado= set_impuesto.getValorSeleccionado();
            System.out.println("probando que valor me llega"+str_seleccionado);
            set_retencion.getTab_seleccion().setSql(ser_Tesoreria.getImpuesto("true","0",str_seleccionado));
            set_retencion.getTab_seleccion().ejecutarSql();
            set_retencion.dibujar();
            set_impuesto.cerrar();
            
            
            }
            else {
                utilitario.agregarMensajeInfo("SELECCIONE OPCION", "Seleccione un registro");
            }
                
        }
    
        else if (set_retencion.isVisible()){
            str_seleccionado= set_retencion.getValorSeleccionado();
            TablaGenerica tab_rentas= utilitario.consultar(ser_Tesoreria.getImpuestoCalculo(str_seleccionado));

            double dou_valor_impuesto=0;
           double dou_porcentaje_calculo=0;
           double dou_valor_resultado=0;

           dou_porcentaje_calculo=Double.parseDouble(tab_rentas.getValor("porcentaje_teimp"));
           dou_valor_impuesto=Double.parseDouble(tab_detalle_retencion.getValor("base_imponible_teder"));
           dou_valor_resultado=(dou_porcentaje_calculo*dou_valor_impuesto)/100;
 
           if (set_retencion.getValorSeleccionado()!=null){

                tab_detalle_retencion.setValor("ide_teimp",str_seleccionado);
                tab_detalle_retencion.setValor("valor_retenido_teder", dou_valor_resultado+"");   
                String valorx=tab_detalle_retencion.getSumaColumna("valor_retenido_teder")+"";
               tab_retencion.setValor("total_ret_teret", valorx);       
           }

            set_retencion.cerrar();
             utilitario.addUpdateTabla(tab_detalle_retencion, "valor_retenido_teder,base_imponible_teder,ide_teimp","");
             utilitario.addUpdateTabla(tab_retencion, "total_ret_teret","");

        }


    }



   

    @Override
    public void insertar() {
        // TODO Auto-generated method stub
        if (tab_comprobante.isFocus()) {
            tab_comprobante.insertar();
        }

        else if (tab_detalle_movimiento.isFocus()){
            tab_detalle_movimiento.insertar();
            tab_detalle_movimiento.setValor("ide_comov", tab_comprobante.getValor("ide_comov"));
        }
        else if (tab_retencion.isFocus()) {
            tab_retencion.insertar();

        }
        else if (tab_detalle_retencion.isFocus()) {
            tab_detalle_retencion.insertar();

        }    
    }

    @Override
    public void inicio() {
        // TODO Auto-generated method stub
        if (tab_comprobante.isFocus()){
            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));

        }
        super.inicio();
    }
    @Override
    public void siguiente() {
        // TODO Auto-generated method stub
        if (tab_comprobante.isFocus()){
            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));

        }

        super.siguiente();
    }
    @Override
    public void atras() {
        // TODO Auto-generated method stub
        if (tab_comprobante.isFocus()){
            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));

        }

        super.atras();
    }

    @Override
    public void fin() {
        // TODO Auto-generated method stub
        if (tab_comprobante.isFocus()){
            tab_detalle_movimiento.setCondicion("ide_comov="+tab_comprobante.getValor("ide_comov"));

        }

        super.fin();
    }
    @Override

    public void guardar() {
        // TODO Auto-generated method stub
        if (tab_comprobante.guardar()) {

            if (tab_detalle_movimiento.guardar()) {
                if( tab_retencion.guardar()){
                    if(tab_detalle_retencion.guardar()){

                    }
                }
            }
            guardarPantalla();

        }
    }

    @Override
    public void eliminar() {
        // TODO Auto-generated method stub
        utilitario.getTablaisFocus().eliminar();

    }


    public Tabla getTab_comprobante() {
        return tab_comprobante;
    }


    public void setTab_comprobante(Tabla tab_comprobante) {
        this.tab_comprobante = tab_comprobante;
    }


    public Tabla getTab_detalle_movimiento() {
        return tab_detalle_movimiento;
    }


    public void setTab_detalle_movimiento(Tabla tab_detalle_movimiento) {
        this.tab_detalle_movimiento = tab_detalle_movimiento;
    }


    public Tabla getTab_retencion() {
        return tab_retencion;
    }


    public void setTab_retencion(Tabla tab_retencion) {
        this.tab_retencion = tab_retencion;
    }


    public Tabla getTab_detalle_retencion() {
        return tab_detalle_retencion;
    }


    public void setTab_detalle_retencion(Tabla tab_detalle_retencion) {
        this.tab_detalle_retencion = tab_detalle_retencion;
    }
    public SeleccionTabla getSet_solicitud() {
        return set_solicitud;
    }
    public void setSet_solicitud(SeleccionTabla set_solicitud) {
        this.set_solicitud = set_solicitud;
    }
    public SeleccionTabla getSet_tramite() {
        return set_tramite;
    }
    public void setSet_tramite(SeleccionTabla set_tramite) {
        this.set_tramite = set_tramite;
    }
    public SeleccionTabla getSet_impuesto() {
        return set_impuesto;
    }
    public void setSet_impuesto(SeleccionTabla set_impuesto) {
        this.set_impuesto = set_impuesto;
    }
    public SeleccionTabla getSet_retencion() {
        return set_retencion;
    }
    public void setSet_retencion(SeleccionTabla set_retencion) {
        this.set_retencion = set_retencion;
    }


}