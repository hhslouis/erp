package paq_facturacion;

import org.primefaces.event.SelectEvent;

import framework.componentes.AutoCompletar;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_datos_factura extends Pantalla{
	
private Tabla tab_datos_factura=new Tabla();
private AutoCompletar aut_opciones=new AutoCompletar();
	
	
	public AutoCompletar getAut_opciones() {
	return aut_opciones;
}

public void setAut_opciones(AutoCompletar aut_opciones) {
	this.aut_opciones = aut_opciones;
}

	public pre_datos_factura(){
		tab_datos_factura.setHeader("DATOS DE LA FACTURACIÓN");
		tab_datos_factura.setId("tab_datos_factura");
		tab_datos_factura.setTabla("fac_datos_factura","ide_fadaf", 1);
		tab_datos_factura.setTipoFormulario(true);
		
		//autocompletar
		tab_datos_factura.getColumna("ide_gedep").setCombo("select ide_gedep,gen_ide_gedep,detalle_gedep from gen_departamento order by detalle_gedep");
		tab_datos_factura.getColumna("ide_gedep").setAutoCompletar();
				
		tab_datos_factura.dibujar();
		PanelTabla pat_datos_factura=new PanelTabla();
		pat_datos_factura.setPanelTabla(tab_datos_factura);
		
		agregarComponente(pat_datos_factura);
	}


	public void seleccionoAutocompletar(SelectEvent evt){
		//Cuando selecciona una opcion del autocompletar
		aut_opciones.onSelect(evt);//siempre debe hacerse el onSelect(evt)
		utilitario.agregarMensaje("VALOR", aut_opciones.getValor()); //muestra el valor que selecciono
		utilitario.agregarMensaje("NOMBRE", aut_opciones.getValorArreglo(1)); //muestra el nombre que selecciono
		}
	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_datos_factura.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_datos_factura.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_datos_factura.eliminar();
		
	}

	public Tabla getTab_datos_factura() {
		return tab_datos_factura;
	}

	public void setTab_datos_factura(Tabla tab_datos_factura) {
		this.tab_datos_factura = tab_datos_factura;
	}

}
