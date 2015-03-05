package paq_activos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;
import portal.entidades.SisTabla;

import com.lowagie.text.pdf.Barcode128;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Confirmar;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;

public class pre_activo extends Pantalla {
	private Tabla tab_activos_fijos=new Tabla();
	private Tabla tab_custodio=new Tabla();
	private Tabla tab_fecha=new Tabla();
	private Map p_parametros = new HashMap();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte self_reporte = new SeleccionFormatoReporte();
	private Map map_parametros = new HashMap();
	private SeleccionTabla set_empleado=new SeleccionTabla();
	private Etiqueta eti_titulo=new Etiqueta();
	private Etiqueta eti_pie=new Etiqueta();
	private Confirmar con_guardar= new Confirmar();
	private Dialogo dia_fecha=new Dialogo();

	@EJB
	private ServicioNomina ser_nomina = (ServicioNomina) utilitario.instanciarEJB(ServicioNomina.class);
	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega ) utilitario.instanciarEJB(ServicioBodega.class);
	@EJB
	private ServicioContabilidad ser_Contabilidad= (ServicioContabilidad) utilitario.instanciarEJB(ServicioContabilidad.class); 

	private StreamedContent codBarras;
	private GraphicImage giBarra=new GraphicImage();



	public pre_activo(){
		rep_reporte.setId("rep_reporte"); //id
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");//ejecuta el metodo al aceptar reporte
		agregarComponente(rep_reporte);//agrega el componente a la pantalla
		bar_botones.agregarReporte();//aparece el boton de reportes en la barra de botones
		self_reporte.setId("self_reporte"); //id
		agregarComponente(self_reporte);
		tab_activos_fijos.setId("tab_activos_fijos");
		tab_activos_fijos.setTabla("afi_activo","ide_afact", 1);
		tab_activos_fijos.getColumna("ide_afubi").setCombo("afi_ubicacion","ide_afubi","detalle_afubi","");
		tab_activos_fijos.getColumna("ide_aftia").setCombo("afi_tipo_activo","ide_aftia","detalle_aftia","");
		tab_activos_fijos.getColumna("ide_aftip").setCombo("afi_tipo_propiedad","ide_aftip","detalle_aftip","");
		tab_activos_fijos.getColumna("ide_afseg").setCombo("afi_seguro","ide_afseg","detalle_afseg","");
		tab_activos_fijos.getColumna("ide_afnoa").setCombo("afi_nombre_activo", "ide_afnoa", "detalle_afnoa", "");
		tab_activos_fijos.getColumna("ide_geare").setCombo("gen_area", "ide_geare", "detalle_geare", "");
		tab_activos_fijos.getColumna("ide_afacd").setCombo("afi_actividad", "ide_afacd", "detalle_afacd", "");
		tab_activos_fijos.getColumna("ide_cocac").setCombo(ser_Contabilidad.getCuentaContable("true,false"));
		tab_activos_fijos.getColumna("ide_afest").setCombo("afi_estado", "ide_afest", "detalle_afest,porcentaje_afest", "");
		tab_activos_fijos.getColumna("ide_tepro").setCombo(ser_bodega.getProveedor("true,false"));
		tab_activos_fijos.getColumna("ide_tepro").setAutoCompletar();
		tab_activos_fijos.getColumna("foto_bien_afact").setUpload("ACTIVOS");
		tab_activos_fijos.getColumna("foto_bien_afact").setValorDefecto("imagenes/activo_jpg");
		tab_activos_fijos.getColumna("foto_bien_afact").setImagen("128", "128");
		tab_activos_fijos.getColumna("valor_unitario_afact").setMetodoChange("calcular");
		tab_activos_fijos.getColumna("cantidad_afact").setMetodoChange("calcular");
		tab_activos_fijos.getColumna("valor_neto_afact").setEtiqueta();
		tab_activos_fijos.getColumna("valor_neto_afact").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");
		tab_activos_fijos.getColumna("valor_neto_afact").setMetodoChange("calcular");
		tab_activos_fijos.getColumna("valor_compra_afact").setEtiqueta();
		tab_activos_fijos.getColumna("valor_compra_afact").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");
		tab_activos_fijos.getColumna("secuencial_afact").setEtiqueta();
		tab_activos_fijos.getColumna("secuencial_afact").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");
		tab_activos_fijos.setTipoFormulario(true);
		tab_activos_fijos.getGrid().setColumns(4);
		tab_activos_fijos.agregarRelacion(tab_custodio);
		tab_activos_fijos.onSelect("seleccionarActivo");
		tab_activos_fijos.dibujar();
		PanelTabla pat_activo_fijos=new PanelTabla();
		pat_activo_fijos.setPanelTabla(tab_activos_fijos);


		tab_custodio.setId("tab_custodio");
		tab_custodio.setTabla("afi_custodio","ide_afcus", 2);
		tab_custodio.getColumna("ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_custodio.getColumna("ide_geedp").setLectura(true);
		tab_custodio.getColumna("ide_geedp").setAutoCompletar();
		tab_custodio.getColumna("ide_geedp").setUnico(true);
		tab_custodio.getColumna("ide_afact").setUnico(true);
		tab_custodio.getColumna("numero_acta_afcus").setUnico(true);
		tab_custodio.getColumna("gen_ide_geedp").setCombo(ser_nomina.servicioEmpleadoContrato("true,false"));
		tab_custodio.getColumna("gen_ide_geedp").setLectura(true);
		tab_custodio.getColumna("gen_ide_geedp").setAutoCompletar();
		tab_custodio.getColumna("activo_afcus").setValorDefecto("true");
		tab_custodio.getColumna("activo_afcus").setLectura(true);
		tab_custodio.setCampoOrden("activo_afcus desc");
		tab_custodio.setTipoFormulario(true);
		tab_custodio.getGrid().setColumns(4);
		tab_custodio.dibujar();
		PanelTabla pat_custodio=new PanelTabla();
		pat_custodio.setPanelTabla(tab_custodio);

		//////////imagen codigo de barra
		generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));		
		giBarra.setId("giBarra");
		giBarra.setWidth("300");
		giBarra.setHeight("120");
		giBarra.setTitle("EMGIRS-");
		giBarra.setValueExpression("value", crearValueExpression("pre_index.clase.codBarras"));


		/////	titulo emgirs
		Grid grid_titulo = new Grid();
		grid_titulo.setColumns(2);
		grid_titulo.setStyle("text-align:center;position:absolute;top:5px;left:55px;");
		eti_titulo.setStyle("font-size:22px;color:black;font-weight: bold;text-align:center;");
		eti_titulo.setValue("EMGIRS");
		grid_titulo.getChildren().add(eti_titulo);

		Grid grid_pie = new Grid();
		grid_pie.setColumns(2);
		grid_pie.setStyle("text-align:center;position:absolute;top:5px;left:55px;");
		eti_pie.setId("eti_pie");
		eti_pie.setStyle("font-size:22px;color:black;font-weight: bold;text-align:center;");
		eti_pie.setValue(tab_custodio.getValor("cod_barra_afcus"));
		grid_pie.getChildren().add(eti_pie);

		Division divx=new Division();
		divx.dividir3(grid_titulo,giBarra ,grid_pie, "20%","30%", "H");

		Division div=new Division();
		div.dividir2(pat_custodio,divx , "70%", "V");

		Division div_division=new Division();
		div_division.dividir2(pat_activo_fijos,div, "50%", "h");

		agregarComponente(div_division);

		////boton empleado
		Boton bot_empleado=new Boton();
		bot_empleado.setIcon("ui-icon-person");
		bot_empleado.setValue("Agregar Custodio");
		bot_empleado.setMetodo("importarEmpleado");
		bar_botones.agregarBoton(bot_empleado);

		///empelado
		set_empleado.setId("set_empleado");
		set_empleado.setSeleccionTabla(ser_nomina.servicioEmpleadoContrato("true"),"ide_geedp");
		set_empleado.getTab_seleccion().getColumna("documento_identidad_gtemp").setFiltro(true);
		set_empleado.getTab_seleccion().getColumna("nombres_apellidos").setFiltro(true);
		
		set_empleado.setTitle("Seleccione un Empleado");
		set_empleado.setRadio();
		set_empleado.getBot_aceptar().setMetodo("aceptarEmpleado");
		agregarComponente(set_empleado);
		
		///Boton depreciación activo
		Boton bot_depre=new Boton();
		bot_depre.setIcon("ui-icon-person");
		bot_depre.setValue("DEPRECIACION ACTIVO");
		bot_depre.setMetodo("abrirDialogo");
		bar_botones.agregarBoton(bot_depre);
		dia_fecha.setId("dia_fecha");
		dia_fecha.setTitle("FECHA DEPRECIACION ACTIVO");
		dia_fecha.setWidth("45%");
		dia_fecha.setHeight("45%");
		Grid gri_cuerpo=new Grid();
		
		tab_fecha.setId("tab_fecha");
		tab_fecha.setTabla("afi_custodio", "ide_afcus",10);
		tab_fecha.setCondicion("ide_afcus=-1");//para que aparesca vacia
		tab_fecha.setTipoFormulario(true);
		tab_fecha.getGrid().setColumns(2);

		//oculto todos los campos
		tab_fecha.getColumna("fecha_entrega_afcus").setNombreVisual("FECHA CALCULO");
		tab_fecha.getColumna("fecha_entrega_afcus").setValorDefecto(utilitario.getFechaActual());
		tab_fecha.getColumna("ide_afcus").setVisible(false);
		tab_fecha.getColumna("ide_afact").setVisible(false);
		tab_fecha.getColumna("gen_ide_geedp").setVisible(false);
		tab_fecha.getColumna("detalle_afcus").setVisible(false);
		tab_fecha.getColumna("cod_barra_afcus").setVisible(false);
		tab_fecha.getColumna("nro_secuencial_afcus").setVisible(false);
		tab_fecha.getColumna("activo_afcus").setVisible(false);
		tab_fecha.getColumna("ide_geedp").setVisible(false);
		tab_fecha.getColumna("fecha_entrega_afcus").setVisible(true);
		tab_fecha.getColumna("fecha_descargo_afcus").setVisible(false);
		tab_fecha.getColumna("numero_acta_afcus").setVisible(false);
		tab_fecha.getColumna("razon_descargo_afcus").setVisible(false);
		tab_fecha.dibujar();
		gri_cuerpo.getChildren().add(tab_fecha);
		
		dia_fecha.getBot_aceptar().setMetodo("aceptarDialogo");

		dia_fecha.setDialogo(gri_cuerpo);
		agregarComponente(dia_fecha);

		

	}
	
	public void abrirDialogo(){
		//Hace aparecer el componente
	
			tab_fecha.limpiar();
			tab_fecha.insertar();
			dia_fecha.dibujar();
		}
	public void  aceptarDialogo(){
		String fecha=tab_fecha.getValor("fecha_entrega_afcus");
		//TablaGenerica tab_consulta_fecha= utilitario.consultar("select avalactivos('"+fecha+"')");
		utilitario.getConexion().ejecutarSql("update afi_activo" +
				" set  vida_util_afact = 5" +
				" where vida_util_afact <=0;" +
				" update afi_activo" +
				" set fecha_calculo_afact ='"+fecha+"'" +
				" where fecha_calculo_afact is null;" +
				" update afi_activo" +
				" set fecha_calculo_afact = '"+fecha+"';" +
				" update afi_activo" +
				" set valor_depre_mes_afact = valor_compra_afact/(vida_util_afact*12);" +
				" update afi_activo" +
				" set val_depreciacion_periodo_afact = (valor_compra_afact/vida_util_afact) * EXTRACT( MONTH FROM fecha_calculo_afact)" +
				" where EXTRACT( year FROM fecha_calculo_afact) > EXTRACT( year FROM fecha_alta_afact);" +
				" update afi_activo" +
				" set val_depreciacion_periodo_afact = (valor_compra_afact/vida_util_afact) *  EXTRACT( MONTH FROM age(fecha_calculo_afact,fecha_alta_afact))" +
				" where EXTRACT( year FROM fecha_calculo_afact) = EXTRACT( year FROM fecha_alta_afact);" +
				" update afi_activo" +
				" set valor_depreciacion_afact = (valor_compra_afact/vida_util_afact)* (EXTRACT( year FROM age(fecha_calculo_afact,fecha_alta_afact))*12 + EXTRACT( MONTH FROM age(fecha_calculo_afact,fecha_alta_afact)));" +
				" update afi_activo" +
				" set valor_depreciacion_afact  = valor_compra_afact *0.9" +
				" where valor_depreciacion_afact >= valor_compra_afact;" +
				" update afi_activo" +
				" set valor_residual_afact = valor_compra_afact - valor_depreciacion_afact;");
		
		utilitario.agregarMensaje("Valoración", "Se ejecuto la valoracion con éxito");
		//utilitario.getConexion().consultar("select avalactivos('"+fecha+"')");
		dia_fecha.cerrar();
		//utilitario.addUpdateTabla(tab_activos_fijos, "vida_util_afact,fecha_calculo_afact,valor_depre_mes_afact,val_depreciacion_periodo_afact,valor_depreciacion_afact,valor_residual_afact", "");
		tab_activos_fijos.ejecutarSql();
		
	}

		
	

	public void importarEmpleado(){
		/*if (tab_custodio.isEmpty()) {
			utilitario.agregarMensajeInfo("Debe ingresar un registro en el contrato", "");
			return;

		}*/

		set_empleado.getTab_seleccion().setSql(ser_nomina.servicioEmpleadoContrato("true"));
		set_empleado.getTab_seleccion().ejecutarSql();
		set_empleado.dibujar();
	}
	public void aceptarEmpleado(){
		String str_seleccionado=set_empleado.getValorSeleccionado();
		if(str_seleccionado!=null){
			//Inserto los empleados seleccionados en la tabla de resposable d econtratacion 
			TablaGenerica tab_empleado_responsable = ser_nomina.ideEmpleadoContrato(str_seleccionado);		

			System.out.println(" tabla generica"+tab_empleado_responsable.getSql());
			for(int i=0;i<tab_empleado_responsable.getTotalFilas();i++){
				tab_custodio.insertar();
				tab_custodio.setValor("IDE_GEEDP", tab_empleado_responsable.getValor(i, "IDE_GEEDP"));	
				

			}
			set_empleado.cerrar();
			utilitario.addUpdate("tab_custodio");			
		}
		else{
			utilitario.agregarMensajeInfo("Debe agregar un Custodio", "");
		}




	}

	public void seleccionarActivo(SelectEvent evt){
		tab_activos_fijos.seleccionarFila(evt);
		tab_custodio.ejecutarValorForanea(tab_activos_fijos.getValorSeleccionado());
		generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));

	}

	@Override
	public void inicio() {
		// TODO Auto-generated method stub
		super.inicio();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
			eti_pie.setValue(tab_custodio.getValor("cod_barra_afcus"));
			utilitario.addUpdate("eti_pie");
		}
	}

	@Override
	public void siguiente() {
		// TODO Auto-generated method stub
		super.siguiente();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
			eti_pie.setValue(tab_custodio.getValor("cod_barra_afcus"));
			utilitario.addUpdate("eti_pie");
		}
	}


	@Override
	public void atras() {
		// TODO Auto-generated method stub
		super.atras();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));
			eti_pie.setValue(tab_custodio.getValor("cod_barra_afcus"));
			utilitario.addUpdate("eti_pie");
		}
	}

	@Override
	public void fin() {
		// TODO Auto-generated method stub
		super.fin();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
			eti_pie.setValue(tab_custodio.getValor("cod_barra_afcus"));
			utilitario.addUpdate("eti_pie");
		}
	}

	//reporte
	public void abrirListaReportes() {
		// TODO Auto-generated method stub
		rep_reporte.dibujar();
	}
	public void aceptarReporte(){
		if(rep_reporte.getReporteSelecionado().equals("Activo"));{
			if (rep_reporte.isVisible()){
				p_parametros=new HashMap();		
				rep_reporte.cerrar();	
				p_parametros.put("titulo","Activo");
				p_parametros.put("ide_usua",Integer.parseInt("7"));
				p_parametros.put("ide_empr",Integer.parseInt("0"));
				p_parametros.put("ide_sucu",Integer.parseInt("1"));
				//p_parametros.put("pide_fafac",Integer.parseInt(tab_cont_viajeros.getValor("ide_fanoc")));
				self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
				self_reporte.dibujar();
				}else if (rep_reporte.getReporteSelecionado().equals("Actividad")) {
					if (rep_reporte.isVisible()) {
						p_parametros=new HashMap();
						rep_reporte.cerrar();
						p_parametros.put("titulo","Actividad");
						p_parametros.put("pide_tipo",Integer.parseInt(tab_activos_fijos.getValor("afi_activo")));
						self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
						self_reporte.dibujar();
					}else if (rep_reporte.getReporteSelecionado().equals("Activo Actividad")) {
						if (rep_reporte.isVisible()) {
							p_parametros=new HashMap();
							rep_reporte.cerrar();
							p_parametros.put("titulo","Activo Actividad");
							p_parametros.put("pide_ubicacion",Integer.parseInt(tab_activos_fijos.getValor("ide_afubi")));
							self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
							self_reporte.dibujar();

						}else if (rep_reporte.getReporteSelecionado().equals("Codigo de Barra")) {
							if (rep_reporte.isVisible()) {
								p_parametros=new HashMap();
								rep_reporte.cerrar();
								p_parametros.put("titulo","Codigo de Barras");
								p_parametros.put("pide_barra",Integer.parseInt(tab_custodio.getValor("ide_afcus")));
								self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
								self_reporte.dibujar();
							}
						}
					}
				}

			}
		}
	



	private void generarCodigoBarras(String cod)  {
		if(cod==null || cod.isEmpty()){
			codBarras=null;
			utilitario.addUpdate("giBarra");
			return;
		}
		try{
			Barcode128 code128 = new Barcode128();
			code128.setCode(cod);
			java.awt.Image img = code128.createAwtImage(Color.BLACK, Color.WHITE);			
			BufferedImage outImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			outImage.getGraphics().drawImage(img, 0, 0, null);
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			ImageIO.write(outImage, "jpeg", bytesOut);
			bytesOut.flush();
			byte[] jpgImageData = bytesOut.toByteArray();
			codBarras= new DefaultStreamedContent(new ByteArrayInputStream(jpgImageData), "image/png");
			giBarra.setValue(codBarras);
			utilitario.addUpdate("giBarra");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void calcular(){
		//Variables para almacenar y calcular el total del detalle
		double duo_cantidad_afact=0;
		double duo_valor_unitario_afact=0;
		double duo_valor_neto_afact=0;
		double duo_valor_compra_afact=0;
		double duo_iva=0.12;
		double duo_total=0;

        tab_activos_fijos.setValor("secuencial_afact", tab_activos_fijos.getValor("cantidad_afact")); 
		try {
			//Obtenemos el valor de la cantidad
			duo_cantidad_afact=Double.parseDouble(tab_activos_fijos.getValor("cantidad_afact"));
		} catch (Exception e){

		}

		try {
			//Obtenemos el valor unitari
			duo_valor_unitario_afact=Double.parseDouble(tab_activos_fijos.getValor("valor_unitario_afact"));
		} catch (Exception e){

		}

		//Calculamos el total
		duo_valor_neto_afact=duo_cantidad_afact*duo_valor_unitario_afact;
		duo_valor_compra_afact=duo_valor_neto_afact*duo_iva;
		duo_total=duo_valor_compra_afact+duo_valor_neto_afact;
		//Asignamos el total a la tabla detalle, con 2 decimales
		tab_activos_fijos.setValor("valor_neto_afact",utilitario.getFormatoNumero(duo_valor_neto_afact,3));
		tab_activos_fijos.setValor("valor_compra_afact",utilitario.getFormatoNumero(duo_total,3));

		//Actualizamos el campo de la tabla AJAX
		utilitario.addUpdateTabla(tab_activos_fijos, "valor_neto_afact,valor_compra_afact,secuencial_afact","");


	}



	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_activos_fijos.guardar()){

			if(tab_custodio.isFocus()){
				generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	

				tab_custodio.guardar();
				eti_pie.setValue(tab_custodio.getValor("cod_barra_afcus"));
				utilitario.addUpdate("eti_pie");
			}

		}

		guardarPantalla();		   


	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

	}





	public Tabla getTab_activos_fijos() {
		return tab_activos_fijos;
	}





	public void setTab_activos_fijos(Tabla tab_activos_fijos) {
		this.tab_activos_fijos = tab_activos_fijos;
	}





	public Tabla getTab_custodio() {
		return tab_custodio;
	}





	public void setTab_custodio(Tabla tab_custodio) {
		this.tab_custodio = tab_custodio;
	}

	public Map getP_parametros() {
		return p_parametros;
	}

	public void setP_parametros(Map p_parametros) {
		this.p_parametros = p_parametros;
	}

	public Reporte getRep_reporte() {
		return rep_reporte;
	}

	public void setRep_reporte(Reporte rep_reporte) {
		this.rep_reporte = rep_reporte;
	}

	public SeleccionFormatoReporte getSelf_reporte() {
		return self_reporte;
	}

	public void setSelf_reporte(SeleccionFormatoReporte self_reporte) {
		this.self_reporte = self_reporte;
	}

	public Map getMap_parametros() {
		return map_parametros;
	}

	public void setMap_parametros(Map map_parametros) {
		this.map_parametros = map_parametros;
	}

	public StreamedContent getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(StreamedContent codBarras) {
		this.codBarras = codBarras;
	}

	private ValueExpression crearValueExpression(String expresion) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createValueExpression(
				facesContext.getELContext(), "#{" + expresion + "}", Object.class);
	}

	public SeleccionTabla getSet_empleado() {
		return set_empleado;
	}

	public void setSet_empleado(SeleccionTabla set_empleado) {
		this.set_empleado = set_empleado;
	}

	public Tabla getTab_fecha() {
		return tab_fecha;
	}

	public void setTab_fecha(Tabla tab_fecha) {
		this.tab_fecha = tab_fecha;
	}

	public Dialogo getDia_fecha() {
		return dia_fecha;
	}

	public void setDia_fecha(Dialogo dia_fecha) {
		this.dia_fecha = dia_fecha;
	}


}

