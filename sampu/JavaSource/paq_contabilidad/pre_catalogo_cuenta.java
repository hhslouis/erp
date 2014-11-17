package paq_contabilidad;

import java.util.ArrayList;
import java.util.List;

import framework.componentes.Arbol;
import framework.componentes.AutoCompletar;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_catalogo_cuenta extends Pantalla{
	
	
	private Tabla tab_tipo_catalogo_cuenta=new Tabla();
	
	 	
	private Arbol arb_catalogo_cuenta=new Arbol();
		
	public pre_catalogo_cuenta(){
		
					        
		tab_tipo_catalogo_cuenta.setId("tab_tipo_catalogo_cuenta");
		tab_tipo_catalogo_cuenta.setTipoFormulario(true);  //formulario 
		tab_tipo_catalogo_cuenta.getGrid().setColumns(4); //hacer  columnas 
		tab_tipo_catalogo_cuenta.setTabla("cont_catalogo_cuenta", "ide_cocac", 1);
		
		
	   tab_tipo_catalogo_cuenta.getColumna("con_ide_cocac2").setCombo("select ide_cocac,cue_codigo_cocac,cue_descripcion_cocac from cont_catalogo_cuenta order by cue_codigo_cocac ");
	   tab_tipo_catalogo_cuenta.getColumna("con_ide_cocac2").setAutoCompletar();
	   tab_tipo_catalogo_cuenta.getColumna("ide_cogrc").setCombo("cont_grupo_cuenta","ide_cogrc","detalle_cogrc", "");
	   tab_tipo_catalogo_cuenta.getColumna("nivel_cocac").setCombo(utilitario.getListaGruposNivelCuenta());
	  // para contruir los radios
	   List lista = new ArrayList();
       Object fila1[] = {
           "1", "APERTURA"
       };
       Object fila2[] = {
           "0", "CIERRE"
       };
       
       lista.add(fila1);
       lista.add(fila2);
       tab_tipo_catalogo_cuenta.getColumna("apertura_cierre_cocac").setRadio(lista, "1");
       tab_tipo_catalogo_cuenta.getColumna("apertura_cierre_cocac").setRadioVertical(true);
		
	   
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
		if(tab_tipo_catalogo_cuenta.guardar()){
            guardarPantalla();
            //actualiza el arbol
            arb_catalogo_cuenta.ejecutarSql();
            utilitario.addUpdate("arb_arbol");
        }
		
				
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
