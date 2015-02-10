package paq_reportes;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_cuentas extends Pantalla {
	
	private Tabla tab_cuenta=new Tabla();
	
	public pre_cuentas (){
	tab_cuenta.setId("tab_cuenta");
	tab_cuenta.setTabla("rep_cuentas_x_pagar", "ide_rpcxp", 1);
	tab_cuenta.dibujar();
	PanelTabla pat_panel1=new PanelTabla();
	pat_panel1.setPanelTabla(tab_cuenta);
	agregarComponente(tab_cuenta);
	
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_cuenta.eliminar();
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_cuenta.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_cuenta.eliminar();
	}

	public Tabla getTab_cuenta() {
		return tab_cuenta;
	}

	public void setTab_cuenta(Tabla tab_cuenta) {
		this.tab_cuenta = tab_cuenta;
	}

}
