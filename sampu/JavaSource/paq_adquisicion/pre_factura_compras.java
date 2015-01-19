package paq_adquisicion;

import org.primefaces.event.SelectEvent;

import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_factura_compras extends Pantalla{
	 private Tabla tab_adq_factura= new Tabla();
	 private Tabla tab_adq_detalle= new Tabla();
	 private AutoCompletar aut_adq_compra= new AutoCompletar();
	 
	public pre_factura_compras() {
		//AGREGAR LOS DOS COMPONENTES
				tab_adq_factura.setId("tab_adq_factura");
				tab_adq_factura.setTabla("adq_factura", "ide_adfac", 1);// 1 YA Q ES LA PRIMERA TABLA
				tab_adq_factura.agregarRelacion(tab_adq_detalle); //CON ESTO LE DECIMOS Q TIENE RELACION
				//COMBOS
				tab_adq_factura.setTipoFormulario(true);
				tab_adq_factura.getGrid().setColumns(4);
				tab_adq_factura.getColumna("ide_adsoc").setCombo("adq_solicitud_compra", "ide_adsoc", "detalle_adsoc", "");
				tab_adq_factura.dibujar();
				PanelTabla pat_adq_factura= new PanelTabla();
				pat_adq_factura.setPanelTabla(tab_adq_factura);
				
				tab_adq_detalle.setId("tab_adq_detalle");
				tab_adq_detalle.setTabla("adq_detalle_factura", "ide_addef", 2);//2 YA Q ES LA SEGUNDA TABLA
				//FORMULARIO
				tab_adq_detalle.setTipoFormulario(true);
				tab_adq_detalle.getGrid().setColumns(2);
				tab_adq_detalle.dibujar();
				PanelTabla pat_adq_detalle=new PanelTabla();
				pat_adq_detalle.setPanelTabla(tab_adq_detalle);
				
				//BOTON LIMPIAR
				Boton bot_limpiar = new Boton();
				bot_limpiar.setIcon("ui-icon-cancel");
				bot_limpiar.setMetodo("limpiar");
				//AUTOCOMPLETAR
				aut_adq_compra.setId("aut_adq_compra");
				//MODIFICAR CONSULTA OJO
				aut_adq_compra.setAutoCompletar("SELECT ide_adsoc,detalle_adsoc,nro_solicitud_adsoc,fecha_solicitud_adsoc FROM adq_solicitud_compra WHERE nro_solicitud_adsoc is not null order by detalle_adsoc");
				aut_adq_compra.setMetodoChange("seleccionoAutocompletar");
				bar_botones.agregarComponente(new Etiqueta("Seleccione"));
				bar_botones.agregarComponente(aut_adq_compra);
				bar_botones.agregarBoton(bot_limpiar);
				
				
				/* AUTOCOMPLETAR
				// ide_bomat---setcombo y set autocompletar
				tab_.getColumna("ide_bomat").setCombo("select ide_bomat,codigo_bomat,detalle_bomat,iva_bomat from bodt_material order by detalle_bomat");
				tab_detalle_factura.getColumna("ide_bomat").setAutoCompletar();
				//definimos el metodo que va a ejecutar cuando el usuario seleccione del Autocompletar
				tab_detalle_factura.getColumna("ide_bomat").setMetodoChange("seleccionoProducto");
*/
				
				Division div_division = new Division();
				div_division.dividir2(pat_adq_factura, pat_adq_detalle, "50%", "H");
				agregarComponente(div_division);
				
				
		
	}
	//METDO AUTOCOMPLETAR
		public void seleccionoAutocompletar(SelectEvent evt){
			//Cuando selecciona una opcion del autocompletar siempre debe hacerse el onSelect(evt)
			aut_adq_compra.onSelect(evt);
			tab_adq_factura.setCondicion("ide_adfac="+aut_adq_compra.getValor());
			tab_adq_factura.ejecutarSql();
			//tab_factura.ejecutarValorForanea(val)
			tab_adq_detalle.ejecutarValorForanea(tab_adq_factura.getValorSeleccionado());

		}
		public void limpiar(){
			aut_adq_compra.limpiar();
			tab_adq_factura.limpiar();
			tab_adq_detalle.limpiar();
			utilitario.addUpdate("aut_factura");
		}
	@Override
	public void insertar() {
		utilitario.getTablaisFocus().insertar();
		
	}

	@Override
	public void guardar() {
	if(tab_adq_factura.guardar()){
		if(tab_adq_detalle.guardar()){
			guardarPantalla();
		}
	}
		
	}

	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_adq_factura() {
		return tab_adq_factura;
	}

	public void setTab_adq_factura(Tabla tab_adq_factura) {
		this.tab_adq_factura = tab_adq_factura;
	}

	public Tabla getTab_adq_detalle() {
		return tab_adq_detalle;
	}

	public void setTab_adq_detalle(Tabla tab_adq_detalle) {
		this.tab_adq_detalle = tab_adq_detalle;
	}

	public AutoCompletar getAut_adq_compra() {
		return aut_adq_compra;
	}

	public void setAut_adq_compra(AutoCompletar aut_adq_compra) {
		this.aut_adq_compra = aut_adq_compra;
	}

}
