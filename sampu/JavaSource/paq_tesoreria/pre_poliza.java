package paq_tesoreria;


import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;



public class pre_poliza extends Pantalla {
	
	private Tabla tab_poliza = new Tabla();
    private Tabla tab_renovacion_poliza = new Tabla();
    
	public pre_poliza() {
		// TODO Auto-generated constructor stub
		
		//AGREGAR LOS DOS COMPONENTES
		tab_poliza.setId("tab_poliza");
		tab_poliza.setTabla("tes_poliza", "ide_tepol", 1);// 1 YA Q ES LA PRIMERA TABLA
		tab_poliza.agregarRelacion(tab_renovacion_poliza); //CON ESTO LE DECIMOS Q TIENE RELACION
		//COMBOS
		tab_poliza.setTipoFormulario(true);
		tab_poliza.getGrid().setColumns(4);
		tab_poliza.getColumna("ide_tetip").setCombo("tes_tipo_poliza", "ide_tetip", "detalle_tetip", "");
		tab_poliza.getColumna("ide_geins").setCombo("gen_institucion", "ide_geins", "detalle_geins", "");
		tab_poliza.getColumna("ide_coest").setCombo("cont_estado", "ide_coest", "detalle_coest", "");
		tab_poliza.dibujar();
		PanelTabla pat_poliza= new PanelTabla();
		pat_poliza.setPanelTabla(tab_poliza);
		
		tab_renovacion_poliza.setId("tab_renovacion_poliza");
		tab_renovacion_poliza.setTabla("tes_renovacion_poliza", "ide_terep", 2);//2 YA Q ES LA SEGUNDA TABLA
		//FORMULARIO
		tab_renovacion_poliza.setTipoFormulario(true);
		tab_renovacion_poliza.getGrid().setColumns(2);
		tab_renovacion_poliza.dibujar();
		PanelTabla pat_renovacion_poliza=new PanelTabla();
		pat_renovacion_poliza.setPanelTabla(tab_renovacion_poliza);
		
		Division div_division = new Division();
		div_division.dividir2(pat_poliza, pat_renovacion_poliza, "50%", "H");
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
		
		if(tab_poliza.guardar()){
			if(tab_renovacion_poliza.guardar()){
				guardarPantalla();
			}
		}
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
	}


	public Tabla getTab_poliza() {
		return tab_poliza;
	}


	public void setTab_poliza(Tabla tab_poliza) {
		this.tab_poliza = tab_poliza;
	}


	public Tabla getTab_renovacion_poliza() {
		return tab_renovacion_poliza;
	}


	public void setTab_renovacion_poliza(Tabla tab_renovacion_poliza) {
		this.tab_renovacion_poliza = tab_renovacion_poliza;
	}
	

}
