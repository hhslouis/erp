package paq_bodega;



import java.util.ArrayList;
import java.util.List;



import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_material extends Pantalla{

	private Tabla tab_material=new Tabla();
	
		
	public pre_material() {
		tab_material.setId("tab_material");  
		tab_material.setTabla("bodt_material","ide_bomat", 1);	
		tab_material.setTipoFormulario(true);
		tab_material.getGrid().setColumns(4);
			    tab_material.getColumna("ide_bounm").setCombo("bodt_unidad_medida", "ide_bounm", "detalle_bounm", "");
		tab_material.getColumna("ide_botip").setCombo("bodt_tipo_producto", "ide_botip", "detalle_botip", "");
		tab_material.getColumna("ide_bogrm").setCombo("bodt_grupo_material", "ide_bogrm", "detalle_bogrm", "");
		tab_material.getColumna("foto_bomat").setUpload("fotos");
    	tab_material.getColumna("foto_bomat").setImagen("128", "128");
		List lista = new ArrayList();
        Object fila1[] = {
            "1", "SI"
        };
        Object fila2[] = {
            "0", "NO"
        };
        Object fila3[] = {
            "2", "NO  OBJETO"
        };
        lista.add(fila1);
        lista.add(fila2);
        lista.add(fila3);
        tab_material.getColumna("iva_bomat").setRadio(lista, "1");
        tab_material.getColumna("iva_bomat").setRadioVertical(true);
		tab_material.dibujar();
		PanelTabla pat_material=new PanelTabla();
		pat_material.setPanelTabla(tab_material);
		
		agregarComponente(pat_material);
		
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_material.insertar();
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_material.guardar();
		guardarPantalla();		
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_material.eliminar();
	}

	public Tabla gettab_material() {
		return tab_material;
	}

	public void settab_material(Tabla tab_material) {
		this.tab_material = tab_material;
	}


}
