package paq_bodega;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_grupo_material extends Pantalla{
	
private Tabla tab_grupo_material=new Tabla();
private Tabla tab_cont_asiento=new Tabla();
	
	
	public pre_grupo_material(){
		tab_grupo_material.setId("tab_grupo_material");
		tab_grupo_material.setTabla("bodt_grupo_material","ide_bogrm", 1);
		tab_grupo_material.dibujar();
		PanelTabla pat_grupo_material=new PanelTabla();
		pat_grupo_material.setPanelTabla(tab_grupo_material);
		
		agregarComponente(pat_grupo_material);
		tab_cont_asiento.setId("tab_cont_asiento");
		tab_cont_asiento.setTabla("cont_tipo_asiento","ide_cotia", 2);
		tab_cont_asiento.dibujar();
		PanelTabla pat_material_bod=new PanelTabla();
		pat_material_bod.setPanelTabla(tab_cont_asiento);
		
		Division div_division = new Division();
		div_division.dividir2(tab_grupo_material,tab_cont_asiento , "50%", "H");
		agregarComponente(div_division);
			
		
	}


	public Tabla getTab_cont_asiento() {
		return tab_cont_asiento;
	}


	public void setTab_cont_asiento(Tabla tab_cont_asiento) {
		this.tab_cont_asiento = tab_cont_asiento;
	}


	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_grupo_material.insertar();
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_grupo_material.guardar();
		guardarPantalla();
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_grupo_material.eliminar();
		
	}


	public Tabla getTab_grupo_material() {
		return tab_grupo_material;
	}


	public void setTab_grupo_material(Tabla tab_grupo_material) {
		this.tab_grupo_material = tab_grupo_material;
	}

}
