package paq_contabilidad;

import framework.componentes.Arbol;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_catalogo_cuenta extends Pantalla{
	private Tabla tab_tipo_catalogo_cuenta=new Tabla();
	
	 	
	private Arbol arb_catalogo_cuenta=new Arbol();
		
	public pre_catalogo_cuenta(){
					        
		tab_tipo_catalogo_cuenta.setId("tab_tipo_catalogo_cuenta");
		tab_tipo_catalogo_cuenta.setTipoFormulario(true);   
		tab_tipo_catalogo_cuenta.getGrid().setColumns(4); 
		tab_tipo_catalogo_cuenta.setTabla("cont_catalogo_cuenta", "ide_cocac", 1);
		// contruccion del arbol
		tab_tipo_catalogo_cuenta.setCampoPadre("con_ide_cocac"); //necesarios para el arbol ide recursivo
		tab_tipo_catalogo_cuenta.agregarArbol(arb_catalogo_cuenta);//necesarios para el arbol
		tab_tipo_catalogo_cuenta.setCampoNombre("(select cue_codigo_cocac||' '|| cue_descripcion_cocac as cue_descripcion_cocac from cont_catalogo_cuenta b where b.ide_cocac=cont_catalogo_cuenta.ide_cocac)"); //necesarios para el arbol campo a mostrarse
		tab_tipo_catalogo_cuenta.dibujar();
		
		
		PanelTabla pat_tipo_catalogo_cuenta = new PanelTabla();
		pat_tipo_catalogo_cuenta.setPanelTabla(tab_tipo_catalogo_cuenta);

		arb_catalogo_cuenta.setId("arb_catalogo_cuenta");
		arb_catalogo_cuenta.dibujar();
		
		Division div_division=new Division();
		div_division.dividir2(arb_catalogo_cuenta, pat_tipo_catalogo_cuenta, "25%", "v");

		agregarComponente(div_division);
		
		

		
		
	}
	
			
	
	
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_tipo_catalogo_cuenta.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_tipo_catalogo_cuenta.guardar();
		guardarPantalla();
		
		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_tipo_catalogo_cuenta.eliminar();
		
	}



	public Tabla getTab_tipo_catalogo_cuenta() {
		return tab_tipo_catalogo_cuenta;
	}







	public void setTab_tipo_catalogo_cuenta(Tabla tab_tipo_catalogo_cuenta) {
		this.tab_tipo_catalogo_cuenta = tab_tipo_catalogo_cuenta;
	}






	public Arbol getArb_catalogo_cuenta() {
		return arb_catalogo_cuenta;
	}






	public void setArb_catalogo_cuenta(Arbol arb_catalogo_cuenta) {
		this.arb_catalogo_cuenta = arb_catalogo_cuenta;
	}
	
	
	

}
