package paq_facturacion;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;
import org.primefaces.event.SelectEvent;
import framework.componentes.AutoCompletar;
import framework.componentes.Etiqueta;
import paq_sistema.aplicacion.Pantalla;


public class pre_factura extends 	Pantalla{

	private Tabla tab_factura = new Tabla();
	private Tabla tab_detalle_factura=new Tabla();
	private AutoCompletar aut_factura=new AutoCompletar();
	private Boolean tieneIvaProducto = new Boolean("true");
	private double dou_por_iva=0.12;
	double dou_base_no_iva=0;
	double dou_base_cero=0;
	double dou_base_aprobada=0;
	double dou_valor_iva=0;
	double dou_total=0;


	public Boolean getTieneIvaProducto() {
		return tieneIvaProducto;
	}

	public void setTieneIvaProducto(Boolean tieneIvaProducto) {
		this.tieneIvaProducto = tieneIvaProducto;
	}


	public AutoCompletar getAut_factura() {
		return aut_factura;
	}

	public void setAut_factura(AutoCompletar aut_factura) {
		this.aut_factura = aut_factura;
	}

	public pre_factura() {
		
		
		// TODO Auto-generated constructor stub
		tab_factura.setHeader("FACTURACION");
		tab_factura.setId("tab_factura");
		tab_factura.setTabla("fac_factura", "ide_fafac", 1);
		//para q no se dibuje antes q seleccione el autocompletar
		tab_factura.setCondicion("ide_fadaf=-1");
		tab_factura.setTipoFormulario(true);
		tab_factura.getGrid().setColumns(4);
		//tab_factura.getColumna("ide_comov").setCombo("cont_movimiento", "ide_comov", "detalle_asiento_comov", "");
		tab_factura.getColumna("ide_comov").setVisible(false);
		tab_factura.getColumna("ide_fadaf").setCombo("fac_datos_factura", "ide_fadaf", "serie_factura_fadaf", "");
		tab_factura.getColumna("ide_gtemp").setCombo("gth_empleado", "ide_gtemp", "documento_identidad_gtemp", "");
		tab_factura.getColumna("ide_retip").setCombo("rec_tipo", "ide_retip", "detalle_retip", "");
		//tab_factura.getColumna("ide_tedar").setCombo("tes_datos_retencion", "ide_tedar", "tipo_retencion_tedar", "");
		tab_factura.getColumna("ide_tedar").setVisible(false);
		//tab_factura.getColumna("ide_retip").setCombo("rec_tipo", "ide_retip", "detalle_retip", "");
		tab_factura.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_factura.getColumna("ide_recli").setCombo("select ide_recli,nombre_comercial_recli from rec_clientes order by nombre_comercial_recli");

		tab_factura.dibujar();
		tab_factura.agregarRelacion(tab_detalle_factura);

		PanelTabla pat_factura=new PanelTabla();
		pat_factura.setPanelTabla(tab_factura);

		// TABLA 2
		tab_detalle_factura.setId("tab_detalle_factura");
		tab_detalle_factura.setTabla("fac_detalle_factura", "ide_fadef", 2);
		tab_detalle_factura.setCampoForanea("ide_fafac");

		// ide_bomat---setcombo y set autocompletar
		tab_detalle_factura.getColumna("ide_bomat").setCombo("select ide_bomat,codigo_bomat,detalle_bomat,iva_bomat from bodt_material order by detalle_bomat");
		tab_detalle_factura.getColumna("ide_bomat").setAutoCompletar();
		//definimos el metodo que va a ejecutar cuando el usuario seleccione del Autocompletar
		tab_detalle_factura.getColumna("ide_bomat").setMetodoChange("seleccionoProducto");
		

	  

		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		aut_factura.setId("aut_factura");	
		aut_factura.setAutoCompletar("SELECT ide_fadaf,serie_factura_fadaf,autorizacion_fadaf,fecha_impresion_fadaf,fecha_vencimiento_fadaf " +
				"FROM fac_datos_factura WHERE serie_factura_fadaf is not null order by serie_factura_fadaf");
		aut_factura.setMetodoChange("seleccionoAutocompletar"); //ejecuta el metodo seleccionoAutocompletar
		
		Etiqueta eti_colaborador=new Etiqueta("Factura:");
		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_factura);
		bar_botones.agregarBoton(bot_limpiar);
		
		
		tab_detalle_factura.dibujar();


		PanelTabla pat_detalle_factura= new PanelTabla();
		pat_detalle_factura.setMensajeWarn("DETALLE FACTURACION");
		pat_detalle_factura.setPanelTabla(tab_detalle_factura);


		Division div_division=new Division();
		div_division.dividir2(pat_factura, pat_detalle_factura, "50%", "h");
		agregarComponente(div_division);

		//TOTALES DE COLOR ROJO--ESTILO DE COLOR ROJO Y NEGRILLA
				tab_factura.getColumna("base_no_iva_fafac").setEtiqueta();
				tab_factura.getColumna("base_no_iva_fafac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");

				tab_factura.getColumna("base_cero_fafac").setEtiqueta();
				tab_factura.getColumna("base_cero_fafac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo

				tab_factura.getColumna("base_aprobada_fafac").setEtiqueta();
				tab_factura.getColumna("base_aprobada_fafac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo

				tab_factura.getColumna("valor_iva_fafac").setEtiqueta();
				tab_factura.getColumna("valor_iva_fafac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo

				tab_factura.getColumna("total_fafac").setEtiqueta();
				tab_factura.getColumna("total_fafac").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");//Estilo
				
				tab_detalle_factura.getColumna("total_fadef").setEtiqueta();
				tab_detalle_factura.getColumna("total_fadef").setEstilo("font-size:13px;font-weight:bold;");
	}
	
   
     public void limpiar(){
     aut_factura.limpiar();
     tab_factura.limpiar();
     tab_detalle_factura.limpiar();
     utilitario.addUpdate("aut_factura");
     }
     
   

	//METDO AUTOCOMPLETAR
	public void seleccionoAutocompletar(SelectEvent evt){
		//Cuando selecciona una opcion del autocompletar
		//siempre debe hacerse el onSelect(evt)
		aut_factura.onSelect(evt);
		//muestra el valor que selecciono
		utilitario.agregarMensaje("VALOR", aut_factura.getValor()); 
		//muestra el nombre que selecciono
		utilitario.agregarMensaje("NOMBRE", aut_factura.getValorArreglo(1)); 

		tab_factura.setCondicion("ide_fadaf="+aut_factura.getValor());
		tab_factura.ejecutarSql();
		//tab_factura.ejecutarValorForanea(val)
		tab_detalle_factura.ejecutarValorForanea(tab_factura.getValorSeleccionado());

	}

	public Tabla gettab_factura() {
		return tab_factura;
	}

	public void settab_factura(Tabla tab_factura) {
		this.tab_factura = tab_factura;
	}

	public Tabla gettab_detalle_factura() {
		return tab_detalle_factura;
	}

	public void settab_detalle_factura(Tabla tab_detalle_factura) {
		this.tab_detalle_factura = tab_detalle_factura;
	}

	// metodo tieneIvaProducto
	private  boolean tieneIvaProducto(String ide_bodtmat){
		//Declaramos un String con la consulta que vamos a ejecutar
		String str_sql="Select * from bodt_material where ide_bomat="+ide_bodtmat;
		//Asi se hacen consultas a la BDD
		TablaGenerica tab_consulta=utilitario.consultar(str_sql);
	
		//Preguntamos si la tabla no esta vacia es decir que si retorno un resultado la consulta
		if ( tab_consulta.isEmpty()==false) {
			//Obtenemos el valor del campo y lo almacenamos en un String
			String str_aplica_valor_bomat= tab_consulta.getValor("aplica_valor_bomat");
			//Preguntamos si el valor de la variable es true
			if(str_aplica_valor_bomat!=null && str_aplica_valor_bomat.equals("TRUE")){
				return true; //Si carga iva
			}
		}
		System.out.println(tab_consulta.getValor("aplica_valor_bomat"));
		return false;  //retorna false

	}
	
	//Metodo metodo cuando se seleccione algun producto del autocompletar
	public void seleccionoProducto(SelectEvent evt){
		tab_detalle_factura.modificar(evt); //simepre que se ejecuta un metodoChange
		//Consultamos si el producto seleccionado carga iva
		boolean boo_iva=tieneIvaProducto(tab_detalle_factura.getValor("ide_bomat"));
		//Mensaje producto, carga o no garga iva
		utilitario.agregarMensaje(tab_detalle_factura.getValor("ide_bomat"),boo_iva+"");
		}
	
	//total_fadef
	public void calcular(){
		//cantidad_fadef * valor_fadef total_fadef 
		//Obtenemos el valor del campo y lo almacenamos en un String
		String str_cantidad_fadef= tab_detalle_factura.getValor("cantidad_fadef");
		String str_valor_fadef= tab_detalle_factura.getValor("valor_fadef");
		
	}
	
	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(tab_factura.isFocus()){
			tab_factura.insertar();
			aut_factura.limpiar();
			utilitario.addUpdate("aut_factura");
		}
		else if(tab_detalle_factura.isFocus()){
			tab_detalle_factura.insertar();
		  
		}

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_factura.guardar()){
			tab_detalle_factura.guardar();
			//aut_opciones.limpiar();
			//utilitario.addUpdate("idAutocompletar");
		}

		guardarPantalla();

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		if(tab_factura.isFocus()){
			tab_factura.eliminar();
		}
		else if(tab_factura.isFocus()){
			tab_factura.eliminar();
		}
		if(tab_detalle_factura.isFocus()){
			tab_detalle_factura.eliminar();
		}
		else if(tab_detalle_factura.isFocus()){
			tab_detalle_factura.eliminar();

		}

	}




}
