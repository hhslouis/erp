package paq_bodega;

import org.primefaces.event.SelectEvent;

import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_ingreso_material_solicitud extends Pantalla{

	private Tabla tab_ingreso_material= new Tabla();
	private AutoCompletar aut_ing_material= new AutoCompletar();

	public pre_ingreso_material_solicitud() {
		tab_ingreso_material.setId("tab_ingreso_material");
		//MODIFICAR TABLA
		tab_ingreso_material.setTabla("adq_solicitud_compra", "ide_adsoc", 1);
		tab_ingreso_material.setTipoFormulario(true);
		tab_ingreso_material.getGrid().setColumns(4);
		tab_ingreso_material.dibujar();

		//BOTON LIMPIAR
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		//AUTOCOMPLETAR
		aut_ing_material.setId("aut_ing_material");
		//MODIFICAR CONSULTA
		aut_ing_material.setAutoCompletar("SELECT ide_bobod,descripcion_bobod,tipo_ingreso_bobod,marca_bobod FROM bodt_bodega WHERE tipo_ingreso_bobod is not null order by descripcion_bobod");
		aut_ing_material.setMetodoChange("seleccionoAutocompletar");
		bar_botones.agregarComponente(new Etiqueta("Seleccione"));
		bar_botones.agregarComponente(aut_ing_material);
		bar_botones.agregarBoton(bot_limpiar);

		PanelTabla pat_ingreso= new PanelTabla();
		pat_ingreso.setPanelTabla(tab_ingreso_material);
		agregarComponente(tab_ingreso_material);
	}
	//METDO AUTOCOMPLETAR
	public void seleccionoAutocompletar(SelectEvent evt){
		//Cuando selecciona una opcion del autocompletar siempre debe hacerse el onSelect(evt)
		aut_ing_material.onSelect(evt);
		tab_ingreso_material.setCondicion("ide_bobod="+aut_ing_material.getValor());
		tab_ingreso_material.ejecutarSql();
		//tab_ingreso_material.ejecutarValorForanea(tab_ingreso_material.getValorSeleccionado());

	}
	public void limpiar(){
		aut_ing_material.limpiar();
		tab_ingreso_material.limpiar();
		utilitario.addUpdate("aut_ing_material");
	}

	@Override
	public void insertar() {
		if (aut_ing_material.getValor()!=null){
			if(tab_ingreso_material.isFocus()){
				tab_ingreso_material.getColumna("ide_bobod").setValorDefecto(aut_ing_material.getValor());
				tab_ingreso_material.insertar();
			}
			
		}
		else{
			utilitario.agregarMensajeError("Debe seleccionar los datos de material","");
		}

	}

	@Override
	public void guardar() {
		if(tab_ingreso_material.guardar()){
			guardarPantalla();
		}

	}

	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();

	}
	public Tabla getTab_ingreso_material() {
		return tab_ingreso_material;
	}
	public void setTab_ingreso_material(Tabla tab_ingreso_material) {
		this.tab_ingreso_material = tab_ingreso_material;
	}
	public AutoCompletar getAut_ing_material() {
		return aut_ing_material;
	}
	public void setAut_ing_material(AutoCompletar aut_ing_material) {
		this.aut_ing_material = aut_ing_material;
	}

}
