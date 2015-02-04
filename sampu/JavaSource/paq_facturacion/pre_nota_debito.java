package paq_facturacion;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_nota_debito extends Pantalla{
	private Tabla tab_debito=new Tabla ();
	
	
	public pre_nota_debito(){
		tab_debito.setId("tab_debito");
		tab_debito.setHeader("NOTA DE DEBITO");
		tab_debito.setTabla("fac_nota_debito", "ide_fanod", 1);
		tab_debito.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_debito.dibujar();
		PanelTabla pat_debito=new PanelTabla();
		pat_debito.setPanelTabla(tab_debito);
	
		
		Division div_division=new Division();
		div_division.dividir1(pat_debito);
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
		tab_debito.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

		
	}

	public Tabla getTab_debito() {
		return tab_debito;
	}

	public void setTab_debito(Tabla tab_debito) {
		this.tab_debito = tab_debito;
	}

}
