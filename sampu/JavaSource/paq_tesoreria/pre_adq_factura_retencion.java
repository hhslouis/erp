package paq_tesoreria;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import framework.aplicacion.TablaGenerica;
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

public class pre_adq_factura_retencion extends Pantalla {

    private Tabla tab_adq_factura = new Tabla();
    
    private Tabla tab_comprobante = new Tabla();
    private Tabla tab_detalle_movimiento =new Tabla();
    private Tabla tab_retencion =new Tabla();
    private Tabla tab_detalle_retencion=new Tabla();
    private SeleccionTabla set_solicitud=new SeleccionTabla();
    private SeleccionTabla set_tramite=new SeleccionTabla();
    private SeleccionTabla set_impuesto=new SeleccionTabla();
    private SeleccionTabla set_retencion=new SeleccionTabla();
	public static String par_impuesto_renta;
	public static String par_impuesto_iva;
	public static double par_iva;

 
    
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


    
    public pre_adq_factura_retencion (){
    	
    	//parametros
    	par_impuesto_iva=utilitario.getVariable("p_tes_impuesto_iva");
		par_impuesto_renta=utilitario.getVariable("p_tes_impuesto_renta");
		par_iva=Double.parseDouble(utilitario.getVariable("p_valor_iva"));

    	///tabuladores
    	 Tabulador tab_tabulador = new Tabulador();
         tab_tabulador.setId("tab_tabulador");
            
         
         
         // tabla factura        
         tab_adq_factura.setId("tab_adq_factura");
         tab_adq_factura.setHeader("ADQUISICIÓN FACTURA");
         tab_adq_factura.setTabla("adq_factura", "ide_adfac",  1);
         tab_adq_factura.setCampoOrden("ide_adfac desc");
         tab_adq_factura.getColumna("IDE_ADSOC").setCombo(ser_Adquisicion.getSolicitudCompra("true"));
         tab_adq_factura.getColumna("IDE_ADSOC").setAutoCompletar();
         tab_adq_factura.getColumna("IDE_ADSOC").setLectura(true);
         tab_adq_factura.getColumna("subtotal_adfac").setLectura(true);
         tab_adq_factura.getColumna("valor_iva_adfac").setLectura(true);
         tab_adq_factura.getColumna("num_factura_adfac").setLectura(true);
         tab_adq_factura.getColumna("fecha_factura_adfac").setLectura(true);
         tab_adq_factura.getColumna("detalle_adfac").setLectura(true);
         tab_adq_factura.getColumna("total_adfac").setLectura(true);
         tab_adq_factura.getColumna("base_iva_adfac").setLectura(true);
         tab_adq_factura.getColumna("activo_adfac").setLectura(true);
         tab_adq_factura.getColumna("valor_descuento_adfac").setVisible(false);
         tab_adq_factura.getColumna("porcent_desc_adfac").setVisible(false);
         tab_adq_factura.getColumna("aplica_descuento_adfac").setVisible(false);
 	     tab_adq_factura.getColumna("ide_tepro").setCombo(ser_Bodega.getProveedor("true,false"));
 		 tab_adq_factura.getColumna("ide_tepro").setLectura(true);
 		 tab_adq_factura.getColumna("ide_tepro").setAutoCompletar();
 		 tab_adq_factura.getColumna("IDE_PRTRA").setCombo(ser_Presupuesto.getTramite("true"));
 		 tab_adq_factura.getColumna("IDE_PRTRA").setAutoCompletar();
 		 tab_adq_factura.getColumna("IDE_PRTRA").setLectura(true);
         tab_adq_factura.onSelect("actualizaPantallas2");
         tab_adq_factura.setTipoFormulario(true);
         tab_adq_factura.getGrid().setColumns(4);
         tab_adq_factura.agregarRelacion(tab_retencion);
         tab_adq_factura.dibujar();
         PanelTabla pat_factura=new PanelTabla();
         pat_factura.setPanelTabla(tab_adq_factura);
         
         ///RETENCION
         tab_retencion.setId("tab_retencion");
         tab_retencion.setIdCompleto("tab_tabulador:tab_retencion");
         //tab_retencion.setHeader("RETENCION");
         tab_retencion.setTabla("tes_retencion", "ide_teret", 2);
         tab_retencion.getColumna("total_ret_teret").setEtiqueta();
         tab_retencion.getColumna("total_ret_teret").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");
         tab_retencion.getColumna("total_ret_teret").setValorDefecto("0.00");
         tab_retencion.getColumna("activo_teret").setValorDefecto("true");
         tab_retencion.getColumna("activo_teret").setLectura(true);
         tab_retencion.getColumna("ide_tecpo").setVisible(false);
         tab_retencion.getColumna("fecha_teret").setValorDefecto(utilitario.getFechaActual());
         tab_retencion.setTipoFormulario(true);
         tab_retencion.getGrid().setColumns(4);
         tab_retencion.agregarRelacion(tab_detalle_retencion);
         tab_retencion.dibujar();
         PanelTabla pat_retencion =new PanelTabla();
         pat_retencion.setPanelTabla(tab_retencion);
         
         /////etiqueta
         Etiqueta eti_retencion=new Etiqueta(); 
         eti_retencion.setValue("RETENCION");
         eti_retencion.setStyle("font-size: 13px;color: red;font-weight: bold");
         pat_retencion.setHeader(eti_retencion);
      
         ///DETALLE RETENCION
         tab_detalle_retencion.setId("tab_detalle_retencion");
         tab_detalle_retencion.setIdCompleto("tab_tabulador:tab_detalle_retencion");
         //tab_detalle_retencion.setHeader("DETALLE RETENCION");
         tab_detalle_retencion.setTabla("tes_detalle_retencion", "ide_teder", 3);
         tab_detalle_retencion.getColumna("ide_teimp").setCombo("tes_impuesto", "ide_teimp", "codigo_teimp,porcentaje_teimp,detalle_teimp", "");
         tab_detalle_retencion.getColumna("ide_teimp").setLectura(true);
         tab_detalle_retencion.getColumna("ide_teimp").setAutoCompletar();
         tab_detalle_retencion.getColumna("base_imponible_teder").setMetodoChange("recalcular");
         tab_detalle_retencion.getColumna("valor_retenido_teder").setEtiqueta();
         tab_detalle_retencion.getColumna("valor_retenido_teder").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
         tab_detalle_retencion.getColumna("valor_retenido_teder").setValorDefecto("0.00");
         tab_detalle_retencion.getColumna("activo_teder").setValorDefecto("true");
         tab_detalle_retencion.getColumna("activo_teder").setLectura(true);
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



   //      tab_tabulador.agregarTab("DETALLE MOVIMIENTO", pat_detalle_movimiento);//intancia los tabuladores 
         tab_tabulador.agregarTab("RETENCION", gri);

         Division div_division =new Division();
         div_division.dividir2(pat_factura, tab_tabulador, "50%", "H");
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
         
         //agregar compromiso presupuesto
         Boton bot_busca=new Boton();
         bot_busca.setIcon("ui-icon-person");
         bot_busca.setValue("Agregar Compromiso Presupuestario");
         bot_busca.setMetodo("importarCertificacionPresupuestaria");
         bar_botones.agregarBoton(bot_busca);
         
         set_tramite.setId("set_tramite");
         set_tramite.setSeleccionTabla(ser_Presupuesto.getTramite("true"),"ide_prtra");
         set_tramite.setTitle("SELECCION UN COMPROMISO PRESUPUESTARIO");  
         set_tramite.getTab_seleccion().getColumna("nro_compromiso").setFiltro(true);
         set_tramite.getTab_seleccion().getColumna("numero_oficio_prtra").setFiltro(true);
         set_tramite.getTab_seleccion().getColumna("observaciones_prtra").setFiltro(true);
         
         set_tramite.getBot_aceptar().setMetodo("aceptarCertificacionPresupuestaria");
         set_tramite.setRadio();
         agregarComponente(set_tramite);
    
     }
   ///recalcular valores
 	
   	public void recalcular(AjaxBehaviorEvent evt){
   		tab_detalle_retencion.modificar(evt);
        TablaGenerica tab_rentas= utilitario.consultar(ser_Tesoreria.getImpuestoCalculo(tab_detalle_retencion.getValor("ide_teimp")));


			double dou_valor_impuesto=0;
            double dou_porcentaje_calculo=0;
            double dou_valor_resultado=0;

            dou_porcentaje_calculo=Double.parseDouble(tab_rentas.getValor("porcentaje_teimp"));
            dou_valor_impuesto=Double.parseDouble(tab_detalle_retencion.getValor("base_imponible_teder"));
            dou_valor_resultado=(dou_porcentaje_calculo*dou_valor_impuesto)/100;
  
            tab_detalle_retencion.setValor("valor_retenido_teder",utilitario.getFormatoNumero( dou_valor_resultado,2)+"");   
            String valorx=tab_detalle_retencion.getSumaColumna("valor_retenido_teder")+"";
            tab_retencion.setValor("total_ret_teret", utilitario.getFormatoNumero(valorx,2));   
            tab_retencion.modificar(tab_retencion.getFilaActual());
            utilitario.addUpdateTabla(tab_detalle_retencion, "valor_retenido_teder,base_imponible_teder,ide_teimp","");
            utilitario.addUpdateTabla(tab_retencion, "total_ret_teret",""); 
        }  
   
   ///sacar valores
 	
   	public void calcularTotal(AjaxBehaviorEvent evt){
   		tab_detalle_movimiento.modificar(evt);
   		tab_detalle_movimiento.sumarColumnas();
   		utilitario.addUpdate("tab_detalle_movimiento");
   	}

   

     public void aceptarSolicitudCompra(){
     	System.out.println("entra a metodo aceptar solicitud");

         String str_seleccionado = set_solicitud.getValorSeleccionado();
         TablaGenerica tab_solicitud=ser_Adquisicion.getTablaGenericaSolicitud(str_seleccionado);
         if (str_seleccionado!=null){
             tab_comprobante.insertar();
             tab_comprobante.setValor("ide_adsoc",str_seleccionado);
             tab_comprobante.setValor("valor_compra_tecpo",tab_solicitud.getValor("total_adfac"));
             tab_comprobante.setValor("valor_iva_tecpo", tab_solicitud.getValor("valor_iva_adfac"));
             tab_comprobante.setValor("ide_prtra", tab_solicitud.getValor("ide_prtra"));
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
             tab_adq_factura.setValor("ide_prtra",tab_tramite.getValor("ide_prtra"));
             tab_adq_factura.modificar(tab_adq_factura.getFilaActual());
             tab_adq_factura.guardar();
             guardarPantalla();
         }
         set_tramite.cerrar();
         utilitario.addUpdate("tab_adq_factura");
     }
     
     ////boton impuesto
     public void importarImpuesto(){
     	System.out.println("entra a metodo import impues");
     	if(tab_adq_factura.getValor("subtotal_adfac")==null||tab_adq_factura.getValor("subtotal_adfac").equals("")){
     		utilitario.agregarMensaje("No puede generar una retencion", "Ingrese un valor de compra");
     		return;
     	}
     	if(tab_adq_factura.getValor("VALOR_IVA_ADFAC")==null||tab_adq_factura.getValor("VALOR_IVA_ADFAC").equals("")){
     		utilitario.agregarMensaje("No puede generar una retencion", "Ingrese valor Iva");
     		return;
     	}
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
             		tab_detalle_retencion.setValor("base_imponible_teder", tab_adq_factura.getValor("valor_iva_adfac"));
             	}
             	else if(set_impuesto.getValorSeleccionado().equals(par_impuesto_renta)){
             		tab_detalle_retencion.setValor("base_imponible_teder", tab_adq_factura.getValor("subtotal_adfac"));
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
                 tab_detalle_retencion.setValor("valor_retenido_teder",utilitario.getFormatoNumero( dou_valor_resultado,2)+"");   
                 String valorx=tab_detalle_retencion.getSumaColumna("valor_retenido_teder")+"";
                tab_retencion.setValor("total_ret_teret", utilitario.getFormatoNumero(valorx,2));   
                tab_retencion.modificar(tab_retencion.getFilaActual());
            }

             set_retencion.cerrar();
              utilitario.addUpdateTabla(tab_detalle_retencion, "valor_retenido_teder,base_imponible_teder,ide_teimp","");
              utilitario.addUpdateTabla(tab_retencion, "total_ret_teret","");
             calcularValorPago();
             //comentado la linea siguiente porque se calcula sobre la factura
            // utilitario.addUpdateTabla(tab_comprobante, "valor_pago_tecpo,","");

         }


     }
     ////calcular ValorPago
     public void calcularValorPago(){
     	double dou_val_compra=0;
     	double dou_total_retencion=0;
     	double dou_valor_pago=0;
     	double dou_valor_iva=0;
		double duo_iva=par_iva;


     	try {
 			//Obtenemos el valor de la cantidad
     		dou_val_compra=Double.parseDouble(tab_adq_factura.getValor("subtotal_adfac"));
 		} catch (Exception e) {
 		}
     	try {
 			//Obtenemos el valor de la cantidad
     		dou_total_retencion=Double.parseDouble(tab_retencion.getValor("total_ret_teret"));
 		} catch (Exception e) {
 		}
     
     	dou_valor_pago=dou_val_compra-dou_total_retencion;
     //	dou_valor_iva=dou_val_compra*duo_iva;
     	
     /* COMENTADO PORQUE YA SE REALIZA LA RETENCION A LA FACTURA
     	tab_comprobante.setValor("valor_pago_tecpo", utilitario.getFormatoNumero(dou_valor_pago,2));
     	//tab_comprobante.setValor("valor_iva_tecpo", utilitario.getFormatoNumero(dou_valor_iva,2));
     	tab_comprobante.modificar(tab_comprobante.getFilaActual());//para que haga el update
     	utilitario.addUpdateTabla(tab_comprobante,"valor_pago_tecpo,","");
     */
     }

    

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		 if (tab_retencion.isFocus()) {
			 String str_observacion="";
			 if(tab_adq_factura.getValor("detalle_adfac").length()>100){
				 str_observacion=tab_adq_factura.getValor("detalle_adfac").substring(100);
			 }
			 else {
				 str_observacion=tab_adq_factura.getValor("detalle_adfac");
			 }
			 tab_retencion.insertar();
			 tab_retencion.setValor("observacion_teret",str_observacion );

	        }
	        else if (tab_detalle_retencion.isFocus()) {
	            utilitario.agregarMensajeInfo("No puede insertar", "Debe generar una Retencion");

	        }    
	
	}
public void actualizaPantallas(){
	tab_retencion.ejecutarValorForanea(tab_adq_factura.getValorSeleccionado());
	tab_detalle_retencion.ejecutarValorForanea(tab_retencion.getValorSeleccionado());
}
public void actualizaPantallas2(SelectEvent evt){
	tab_adq_factura.seleccionarFila(evt);
	tab_retencion.ejecutarValorForanea(tab_adq_factura.getValor("ide_adfac"));
	tab_detalle_retencion.ejecutarValorForanea(tab_retencion.getValor("ide_teret"));
}
	/////botones fin,siguiente,atras,ultimo.inicio
	  @Override
	    public void inicio() {
	        // TODO Auto-generated method stub
	    
	        super.inicio();
	       // actualizaPantallas();
	    }
	    @Override
	    public void siguiente() {
	        // TODO Auto-generated method stub
	      

	        super.siguiente();
	      //  actualizaPantallas();
	    }
	    @Override
	    public void atras() {
	        // TODO Auto-generated method stub
	    
	        super.atras();
	        //actualizaPantallas();
	    }

	    @Override
	    public void fin() {
	        // TODO Auto-generated method stub
	  

	        super.fin();
	        //actualizaPantallas();
	    }

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		 if (tab_adq_factura.guardar()) {

	           
	                if( tab_retencion.guardar()){
	                    if(tab_detalle_retencion.guardar()){
	                        guardarPantalla();
	                    }
	                }
	            

	        }

		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		  utilitario.getTablaisFocus().eliminar();

    
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

	public Tabla getTab_adq_factura() {
		return tab_adq_factura;
	}

	public void setTab_adq_factura(Tabla tab_adq_factura) {
		this.tab_adq_factura = tab_adq_factura;
	}

}
