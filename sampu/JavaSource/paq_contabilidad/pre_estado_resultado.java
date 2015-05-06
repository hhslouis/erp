package paq_contabilidad;

import framework.componentes.Arbol;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_estado_resultado extends Pantalla{
	
	private Tabla tab_estado_resultado = new Tabla();
	private Arbol arb_arbol = new Arbol();

	
	public pre_estado_resultado (){
		tab_estado_resultado.setId("tab_estado_resultado");
		tab_estado_resultado.setHeader("ESTADO DE RESULTADOS");
		tab_estado_resultado.setTabla("cont_estado_resultado", "ide_coesr", 1);
		tab_estado_resultado.agregarArbol(arb_arbol);
		tab_estado_resultado.setCampoPadre("con_ide_coesr"); //necesario para el arbol
		tab_estado_resultado.setCampoNombre("detalle_cuenta_coesr");//necesario pa el arbol
		tab_estado_resultado.dibujar();
		PanelTabla pat_resultado = new PanelTabla();
		pat_resultado.setPanelTabla(tab_estado_resultado);
		
		
		
		/////// arbol
		
		arb_arbol.setId("arb_arbol");
		arb_arbol.dibujar();

		
		
		
		Division div_division = new Division(); //UNE OPCION 
		div_division.setId("div_division");
		div_division.dividir2(arb_arbol, pat_resultado, "40%", "V");  //arbol y diV_division
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
		if(tab_estado_resultado.guardar()){
	           
	        	   guardarPantalla();
	            //actualiza el arbol
	            arb_arbol.ejecutarSql();
	            utilitario.addUpdate("arb_arbol");
				   }
	           }
	    				
		
		
	

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_estado_resultado() {
		return tab_estado_resultado;
	}

	public void setTab_estado_resultado(Tabla tab_estado_resultado) {
		this.tab_estado_resultado = tab_estado_resultado;
	}

	public Arbol getArb_arbol() {
		return arb_arbol;
	}

	public void setArb_arbol(Arbol arb_arbol) {
		this.arb_arbol = arb_arbol;
	}
	

}
