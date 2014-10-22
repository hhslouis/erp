package paq_presupuesto;

import framework.componentes.Arbol;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_clasificador extends Pantalla {
	private Tabla tab_presupuesto=new Tabla();
	
	private Arbol arb_clasificador=new Arbol();

	


	public pre_clasificador(){
		
		tab_presupuesto.setId("tab_presupuesto");
		tab_presupuesto.setTipoFormulario(true);
		tab_presupuesto.getGrid().setColumns(4);
		
		tab_presupuesto.setTabla("pre_clasificador","ide_prcla", 1);
		tab_presupuesto.getColumna("grupo_prcla").setCombo(utilitario.getListaGrupoCuentaPresupuesto());
		tab_presupuesto.getColumna("clase_economica_prcla").setCombo(utilitario.getListaClasificacionEconomica());
		tab_presupuesto.getColumna("grupo_sigef_prcla").setCombo(utilitario.getListaGrupoSigef());
		
		tab_presupuesto.setCampoPadre("pre_ide_prcla"); //necesarios para el arbol
		tab_presupuesto.setCampoNombre("codigo_clasificador_prcla"); //necesarios para el arbol
		tab_presupuesto.agregarArbol(arb_clasificador);//necesarios para el arbol
			
		
		tab_presupuesto.dibujar();
		PanelTabla pat_clasificador=new PanelTabla();
		pat_clasificador.setPanelTabla(tab_presupuesto);
		
		arb_clasificador.setId("arb_clasificador");
		arb_clasificador.dibujar();
		
		Division div_division=new Division();
		div_division.dividir2(arb_clasificador, pat_clasificador, "25%", "v");

        agregarComponente(div_division);
		
		
	}
		
	
	

		@Override
		public void insertar() {
			// TODO Auto-generated method stub
			tab_presupuesto.insertar();

		}

		@Override
		public void guardar() {
			// TODO Auto-generated method stub
			tab_presupuesto.guardar();
			guardarPantalla();

		}

		@Override
		public void eliminar() {
			// TODO Auto-generated method stub
			tab_presupuesto.eliminar();

		}
		public Tabla getTab_presupuesto() {
			return tab_presupuesto;
		}
		public void setTab_presupuesto(Tabla tab_presupuesto) {
			this.tab_presupuesto = tab_presupuesto;
		}
		public Arbol getArb_clasificador() {
			return arb_clasificador;
		}
		public void setArb_clasificador(Arbol arb_clasificador) {
			this.arb_clasificador = arb_clasificador;
		}

	}
