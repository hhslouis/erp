package paq_bodega;

import org.primefaces.event.SelectEvent;

import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;


public class pre_egreso_material_solicitud extends Pantalla{
private Tabla tab_concepto_egreso=new Tabla();
private Tabla tab_egresos=new Tabla();
private AutoCompletar aut_egresos= new AutoCompletar();

	public pre_egreso_material_solicitud() {
		tab_concepto_egreso.setId("tab_concepto_egreso");
		tab_concepto_egreso.setTabla("bodt_concepto_egreso", "ide_bocoe", 1);// 1 YA Q ES LA PRIMERA TABLA
		tab_concepto_egreso.agregarRelacion(tab_egresos); //CON ESTO LE DECIMOS Q TIENE RELACION
		//COMBOS
		tab_concepto_egreso.setTipoFormulario(true);
		tab_concepto_egreso.getGrid().setColumns(4);
		tab_concepto_egreso.getColumna("ide_geedp").setCombo("gen_empleados_departamento_par", "ide_geedp", "observacion_geedp", "");
		tab_concepto_egreso.getColumna("ide_getip").setCombo("gen_tipo_persona", "ide_getip", "detalle_getip", "");
		tab_concepto_egreso.getColumna("ide_bodes").setCombo("bodt_destino", "ide_bodes", "detalle_bodes", "");
		tab_concepto_egreso.getColumna("ide_geare").setCombo("gen_area", "ide_geare", "detalle_geare", "");
		
		
		tab_concepto_egreso.dibujar();
		PanelTabla pat_concepto_egreso= new PanelTabla();
		pat_concepto_egreso.setPanelTabla(tab_concepto_egreso);
		
		tab_egresos.setId("tab_egresos");
		tab_egresos.setTabla("bodt_egreso", "ide_boegr", 2);//2 YA Q ES LA SEGUNDA TABLA
		tab_egresos.setTipoFormulario(true);
		tab_egresos.getGrid().setColumns(2);
		tab_egresos.getColumna("ide_bomat").setCombo("bodt_material", "ide_bomat ", "detalle_bomat", "");
		tab_egresos.getColumna("ide_boinv").setCombo("bodt_inventario", "ide_boinv ", "ide_boinv", "");
		tab_egresos.getColumna("ide_bocoe").setCombo("bodt_concepto_egreso", "ide_bocoe ", "ubicacion_bocoe", "");
		tab_egresos.getColumna("ide_bomat").setCombo("bodt_material", "ide_bomat ", "detalle_bomat", "");
		tab_egresos.dibujar();
		PanelTabla pat_egresos=new PanelTabla();
		pat_egresos.setPanelTabla(tab_egresos);
		
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		//AUTOCOMPLETAR
		aut_egresos.setId("aut_egresos");
		//MODIFICAR CONSULTA OJO
		aut_egresos.setAutoCompletar("SELECT ide_adsoc,detalle_adsoc,nro_solicitud_adsoc,fecha_solicitud_adsoc FROM adq_solicitud_compra WHERE nro_solicitud_adsoc is not null order by detalle_adsoc");
		aut_egresos.setMetodoChange("seleccionoAutocompletar");
		bar_botones.agregarComponente(new Etiqueta("Solicitud de compra"));
		bar_botones.agregarComponente(aut_egresos);
		bar_botones.agregarBoton(bot_limpiar);
		
	
		Division div_division = new Division();
		div_division.dividir2(pat_concepto_egreso, pat_egresos, "50%", "H");
		agregarComponente(div_division);
	}
	//METDO AUTOCOMPLETAR
		public void seleccionoAutocompletar(SelectEvent evt){
			//Cuando selecciona una opcion del autocompletar siempre debe hacerse el onSelect(evt)
			aut_egresos.onSelect(evt);
			tab_concepto_egreso.setCondicion("ide_adsoc="+aut_egresos.getValor());
			tab_concepto_egreso.ejecutarSql();
			tab_egresos.ejecutarValorForanea(tab_concepto_egreso.getValorSeleccionado());

		}
		public void limpiar(){
			aut_egresos.limpiar();
			tab_concepto_egreso.limpiar();
			tab_egresos.limpiar();
			utilitario.addUpdate("aut_egresos");
		}
	
	
	@Override
	public void insertar() {
		if (aut_egresos.getValor()!=null){
			if(tab_concepto_egreso.isFocus()){
				tab_concepto_egreso.getColumna("ide_adsoc").setValorDefecto(aut_egresos.getValor());
				tab_concepto_egreso.insertar();
			}
			else if(tab_egresos.isFocus()){
				tab_egresos.insertar();
			}
		}
		else{
			utilitario.agregarMensajeError("Debe seleccionar los datos de Egreso","");
		}
	}

	@Override
	public void guardar() {
		if(tab_concepto_egreso.guardar()){
			if(tab_egresos.guardar()){
				guardarPantalla();
			}
		}
		
	}

	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();
		
	}
	public Tabla getTab_concepto_egreso() {
		return tab_concepto_egreso;
	}
	public void setTab_concepto_egreso(Tabla tab_concepto_egreso) {
		this.tab_concepto_egreso = tab_concepto_egreso;
	}
	public Tabla getTab_egresos() {
		return tab_egresos;
	}
	public void setTab_egresos(Tabla tab_egresos) {
		this.tab_egresos = tab_egresos;
	}
	public AutoCompletar getAut_egresos() {
		return aut_egresos;
	}
	public void setAut_egresos(AutoCompletar aut_egresos) {
		this.aut_egresos = aut_egresos;
	}

}
