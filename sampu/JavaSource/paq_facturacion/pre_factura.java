package paq_facturacion;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;
import org.primefaces.event.SelectEvent;
import framework.componentes.AutoCompletar;
import framework.componentes.Etiqueta;
import paq_sistema.aplicacion.Pantalla;


public class pre_factura extends 	Pantalla{
	
	private Tabla tab_factura = new Tabla();
	private Tabla tab_detalle_factura=new Tabla();
	private AutoCompletar aut_opciones=new AutoCompletar();
	private AutoCompletar aut_opciones2=new AutoCompletar();
	
	
	public AutoCompletar getAut_opciones2() {
		return aut_opciones2;
	}

	public void setAut_opciones2(AutoCompletar aut_opciones2) {
		this.aut_opciones2 = aut_opciones2;
	}

	public AutoCompletar getAut_opciones() {
		return aut_opciones;
	}

	public void setAut_opciones(AutoCompletar aut_opciones) {
		this.aut_opciones = aut_opciones;
	}

	public pre_factura() {
		// TODO Auto-generated constructor stub
		tab_factura.setHeader("FACTURACIÓN");
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
		tab_detalle_factura.dibujar();
		
		
		PanelTabla pat_detalle_factura= new PanelTabla();
		pat_detalle_factura.setMensajeWarn("DETALLE FACTURACIÓN");
		pat_detalle_factura.setPanelTabla(tab_detalle_factura);
		
		
		Division div_division=new Division();
		div_division.dividir2(pat_factura, pat_detalle_factura, "50%", "h");
		agregarComponente(div_division);
		
		//el auto completar
		aut_opciones.setId("aut_opciones"); //id del componente
		//Simpre el primer campo es el campo primario de la tabla, en este caso ide_opci
		//El segundo campo es lo que va a visualizarse , en este caso los campos nom_opci,tipo_opci,paquete_opci
		aut_opciones.setAutoCompletar("SELECT ide_fadaf,serie_factura_fadaf,autorizacion_fadaf,fecha_impresion_fadaf,fecha_vencimiento_fadaf " +
		"FROM fac_datos_factura WHERE serie_factura_fadaf is not null order by serie_factura_fadaf");
		aut_opciones.setMetodoChange("seleccionoAutocompletar"); //ejecuta el metodo seleccionoAutocompletar
		bar_botones.agregarComponente(new Etiqueta("Seleccione una opción :")); //Agrego etiqueta a la barra de botones
		bar_botones.agregarComponente(aut_opciones);//Agrego autocompletar a la barra de botones
		
	}
	//METDO AUTOCOMPLETAR
	public void seleccionoAutocompletar(SelectEvent evt){
		//Cuando selecciona una opcion del autocompletar
		//siempre debe hacerse el onSelect(evt)
		aut_opciones.onSelect(evt);
		//muestra el valor que selecciono
		utilitario.agregarMensaje("VALOR", aut_opciones.getValor()); 
		//muestra el nombre que selecciono
		utilitario.agregarMensaje("NOMBRE", aut_opciones.getValorArreglo(1)); 
		
		tab_factura.setCondicion("ide_fadaf="+aut_opciones.getValor());
		tab_factura.ejecutarSql();
		//tab_factura.ejecutarValorForanea(val)
		tab_detalle_factura.ejecutarValorForanea(tab_factura.getValorSeleccionado());
		
		//Tabla.setCondicion("Campo="+autocompletar.getvalorseleccionado)
		//Tabla.ejecutarsql()
		//Tabla2.ejecutarvalorforaneo(tabla.getvalotseleccionado)
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

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		if(tab_factura.isFocus()){
			tab_factura.insertar();
			aut_opciones.limpiar();
			utilitario.addUpdate("idAutocompletar");
			}
		else if(tab_detalle_factura.isFocus()){
			tab_detalle_factura.insertar();
			aut_opciones.limpiar();
			utilitario.addUpdate("idAutocompletar");
		}
		
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_factura.guardar()){
			tab_detalle_factura.guardar();
		
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
			
		}

	
	

}
