package paq_tesoreria;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_comprobante_pago extends Pantalla{
	private Tabla tab_comprobante = new Tabla();
	private Tabla tab_detalle_movimiento =new Tabla();
	private AutoCompletar aut_movimiento=new AutoCompletar();

	
	public pre_comprobante_pago (){
		tab_comprobante.setId("tab_comprobante");
		tab_comprobante.setHeader("COMPROBANTE DE PAGO");
		tab_comprobante.setTabla("tes_comprobante_pago", "ide_tecpo", 1);
		//tab_comprobante.agregarRelacion(tab_movimiento);
		tab_comprobante.setTipoFormulario(true);
		tab_comprobante.getGrid().setColumns(4);
		tab_comprobante.dibujar();
		PanelTabla pat_comprobante=new PanelTabla();
		pat_comprobante.setPanelTabla(tab_comprobante);
		
		tab_detalle_movimiento.setId("tab_detalle_movimiento");
		tab_detalle_movimiento.setHeader("DETALLE MOVIMIENTO");
		tab_detalle_movimiento.setTabla("cont_detalle_movimiento", "ide_codem", 2);
		//filtra por asiento contable cuando no tiene relacion a tes_comprovante_pago
		tab_detalle_movimiento.setCondicion("ide_comov=-1");
		tab_detalle_movimiento.dibujar();
		PanelTabla pat_movimiento=new PanelTabla();
		pat_movimiento.setPanelTabla(tab_detalle_movimiento);
		
		Division div_division =new Division();
		div_division.dividir2(pat_comprobante, pat_movimiento, "50%", "H");
		agregarComponente(div_division);
		
			
		
	}
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_comprobante.guardar()){
			tab_detalle_movimiento.guardar();
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


}
