package paq_contabilidad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Arbol;
import framework.componentes.AutoCompletar;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabiliadad;
import paq_general.ejb.ServicioGeneral;
import paq_sistema.aplicacion.Pantalla;

public class pre_catalogo_cuenta extends Pantalla{
	
	
	private Tabla tab_tipo_catalogo_cuenta=new Tabla();
	private Tabla tab_vigente=new Tabla();
	private Tabla tab_asociacion_presupuestaria=new Tabla();
	private Division div_division = new Division();
	private Division div_division2 = new Division();
	private Division div_vigente= new Division();

	 	
	private Arbol arb_catalogo_cuenta=new Arbol();
	 @EJB
		private ServicioContabiliadad ser_contabilidad = (ServicioContabiliadad ) utilitario.instanciarEJB(ServicioContabiliadad.class);
		
	public pre_catalogo_cuenta(){
		
					        
		tab_tipo_catalogo_cuenta.setId("tab_tipo_catalogo_cuenta");
		tab_tipo_catalogo_cuenta.setHeader("CATÀLOGO CUENTAS");
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
       tab_tipo_catalogo_cuenta.agregarRelacion(tab_vigente);
       tab_tipo_catalogo_cuenta.setCampoPadre("con_ide_cocac"); //necesarios para el arbol ide recursivo
		tab_tipo_catalogo_cuenta.setCampoNombre("(select cue_codigo_cocac||' '|| cue_descripcion_cocac as cue_descripcion_cocac from cont_catalogo_cuenta b where b.ide_cocac=cont_catalogo_cuenta.ide_cocac)"); //necesarios para el arbol campo a mostrarse
		tab_tipo_catalogo_cuenta.agregarArbol(arb_catalogo_cuenta);//necesarios para el arbol
		tab_tipo_catalogo_cuenta.dibujar();
		PanelTabla pat_tipo_catalogo_cuenta = new PanelTabla();
		pat_tipo_catalogo_cuenta.setPanelTabla(tab_tipo_catalogo_cuenta);
		
		//tabla de años vigentes
				tab_vigente.setId("tab_vigente");
				tab_vigente.setHeader("AÑO VIGENTE");
				tab_vigente.setTabla("cont_vigente", "ide_covig", 2);
				tab_vigente.getColumna("ide_geani").setCombo("gen_anio","ide_geani","detalle_geani","");
				tab_vigente.getColumna("ide_geani").setUnico(true);
				// ocultar campos de las claves  foraneas
				TablaGenerica  tab_generica=ser_contabilidad.getTablaVigente("cont_vigente");
				for(int i=0;i<tab_generica.getTotalFilas();i++){
	    		//muestra los ides q quiere mostras.
				if(!tab_generica.getValor(i, "column_name").equals("ide_geani")){	
				tab_vigente.getColumna(tab_generica.getValor(i, "column_name")).setVisible(false);	
				}				
	    		
	       		}
	    	    						
				tab_vigente.dibujar();
				PanelTabla pat_vigente = new PanelTabla();
			    pat_vigente.setPanelTabla(tab_vigente); 
			 	  	
		//arbol				
		div_vigente =new Division();
		div_vigente.setId("div_vigente");
		div_vigente.dividir2(pat_tipo_catalogo_cuenta,pat_vigente,"50%", "h");
		agregarComponente(div_vigente);
		
		arb_catalogo_cuenta.setId("arb_catalogo_cuenta");
		arb_catalogo_cuenta.dibujar();
		div_division = new Division();
		div_division.setId("div_division");
		div_division.dividir2(arb_catalogo_cuenta,div_vigente,"25%","v");
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
		if(tab_tipo_catalogo_cuenta.guardar()){
           if(tab_vigente.guardar()){
        	   guardarPantalla();
            //actualiza el arbol
            arb_catalogo_cuenta.ejecutarSql();
            utilitario.addUpdate("arb_arbol");
			   }
           }
    				
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
		
	}



	public Tabla getTab_vigente() {
		return tab_vigente;
	}
	public void setTab_vigente(Tabla tab_vigente) {
		this.tab_vigente = tab_vigente;
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
