/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_salud;

import org.primefaces.event.NodeSelectEvent;

import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Arbol;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

/**
 *
 * @author Diego
 */
public class pre_ubicacion_grupo_sucursal extends Pantalla {


	private Tabla tab_ubicacion_grupo = new Tabla();
	private Tabla tab_ubicacion_grupo_sucursal = new Tabla();

	Arbol arb_arbol = new Arbol();
	public pre_ubicacion_grupo_sucursal() {        
		tab_ubicacion_grupo.setId("tab_ubicacion_grupo");
		tab_ubicacion_grupo.setTabla("SAO_UBICACION","IDE_SAUBI", 1);
		tab_ubicacion_grupo.setCampoNombre("DETALLE_SAUBI");
		tab_ubicacion_grupo.setCampoPadre("SAO_IDE_SAUBI");
		tab_ubicacion_grupo.getColumna("IDE_SATIU").setCombo("SAO_TIPO_UBICACION","IDE_SATIU","DETALLE_SATIU", "");
		tab_ubicacion_grupo.getColumna("ACTIVO_SAUBI").setCheck();
		tab_ubicacion_grupo.getColumna("ACTIVO_SAUBI").setValorDefecto("true");
		tab_ubicacion_grupo.agregarArbol(arb_arbol);
		tab_ubicacion_grupo.agregarRelacion(tab_ubicacion_grupo_sucursal);
		tab_ubicacion_grupo.dibujar();

		tab_ubicacion_grupo_sucursal.setId("tab_ubicacion_grupo_sucursal");
		tab_ubicacion_grupo_sucursal.setTabla("SAO_UBICACION_SUCURSAL","IDE_SAUBS", 2);
		tab_ubicacion_grupo_sucursal.setMostrarcampoSucursal(true);
		tab_ubicacion_grupo_sucursal.getColumna("IDE_SUCU").setCombo("SIS_SUCURSAL","IDE_SUCU","NOM_SUCU", "");
		tab_ubicacion_grupo_sucursal.getColumna("ACTIVO_SAUBS").setCheck();
		tab_ubicacion_grupo_sucursal.getColumna("ACTIVO_SAUBS").setValorDefecto("true");
		tab_ubicacion_grupo_sucursal.dibujar();


		PanelTabla pat_panel = new PanelTabla();
		pat_panel.setPanelTabla(tab_ubicacion_grupo);
		PanelTabla pat_panel1 = new PanelTabla();
		pat_panel1.setPanelTabla(tab_ubicacion_grupo_sucursal);

		arb_arbol.setId("arb_arbol");
		arb_arbol.dibujar();

		Division div_division1 = new Division();
		div_division1.setId("div_division1");
		div_division1.dividir2(pat_panel,pat_panel1,"50%", "H");

		Division div_division = new Division();
		div_division.setId("div_division");
		div_division.dividir2(arb_arbol, div_division1, "21%", "v");
		agregarComponente(div_division);
	}
	public void seleccionar_arbol(NodeSelectEvent evt) {
		arb_arbol.seleccionarNodo(evt);
		tab_ubicacion_grupo.setValorPadre(arb_arbol.getValorSeleccionado());
		tab_ubicacion_grupo.ejecutarSql();
	}

	@Override
	public void insertar() {

		if(tab_ubicacion_grupo.isFocus()){

			tab_ubicacion_grupo.insertar();	
	///		tab_ubicacion_grupo_sucursal.limpiar();

		}
		else if(tab_ubicacion_grupo_sucursal.isFocus()){
			if(tab_ubicacion_grupo.isFilaInsertada() && tab_ubicacion_grupo.isEmpty() == false)
			{

				tab_ubicacion_grupo_sucursal.insertar();

			}
			else {
				utilitario.agregarMensajeInfo("Debe ingresar datos", "Cabecera");
			}
		}


	}

	@Override
	public void guardar() {
		if(tab_ubicacion_grupo.isFocus()){ 
		tab_ubicacion_grupo.guardar();
	
		}
		
	 if(tab_ubicacion_grupo.isFocus()){ 
			tab_ubicacion_grupo_sucursal.guardar();
		
		}
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		
		if(tab_ubicacion_grupo.isFocus()){ 
			tab_ubicacion_grupo.eliminar();
		
			}
			
		 if(tab_ubicacion_grupo.isFocus()){ 
				tab_ubicacion_grupo_sucursal.guardar();
			
			}
		
	}

	public Tabla getTab_ubicacion_grupo() {
		return tab_ubicacion_grupo;
	}

	public void setTab_ubicacion_grupo(Tabla tab_ubicacion_grupo) {
		this.tab_ubicacion_grupo = tab_ubicacion_grupo;
	}




	public Tabla getTab_ubicacion_grupo_sucursal() {
		return tab_ubicacion_grupo_sucursal;
	}

	public void setTab_ubicacion_grupo_sucursal(Tabla tab_ubicacion_grupo_sucursal) {
		this.tab_ubicacion_grupo_sucursal = tab_ubicacion_grupo_sucursal;
	}

	public Arbol getArb_arbol() {
		return arb_arbol;
	}

	public void setArb_arbol(Arbol arb_arbol) {
		this.arb_arbol = arb_arbol;
	}


}
