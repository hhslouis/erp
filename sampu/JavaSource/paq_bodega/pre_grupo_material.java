package paq_bodega;

import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_grupo_material extends Pantalla{
	
private Tabla tab_grupo_material=new Tabla();
	
	
	public pre_grupo_material(){
		tab_grupo_material.setId("tab_grupo_material");
		tab_grupo_material.setTabla("bodt_grupo_material","ide_bogrm", 1);
		tab_grupo_material.dibujar();
		PanelTabla pat_grupo_material=new PanelTabla();
		pat_grupo_material.setPanelTabla(tab_grupo_material);
		
		agregarComponente(pat_grupo_material);
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
