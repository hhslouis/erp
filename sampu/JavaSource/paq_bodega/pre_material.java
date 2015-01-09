package paq_bodega;



import java.util.ArrayList;
import java.util.List;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_material extends Pantalla{

	private Tabla tab_material=new Tabla();
	private Tabla tab_material_tarifa = new Tabla();
	
		
	public pre_material() {
		tab_material.setId("tab_material");  
		tab_material.setTabla("bodt_material","ide_bomat", 1);	
		tab_material.agregarRelacion(tab_material_tarifa); //CON ESTO LE DECIMOS Q TIENE RELACION
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
		
		
		tab_material_tarifa.setId("tab_material_tarifa");
		tab_material_tarifa.setTabla("tes_material_tarifa", "ide_temat", 2);
		tab_material_tarifa.setTipoFormulario(true);
		tab_material_tarifa.getGrid().setColumns(2);
		tab_material_tarifa.getColumna("ide_tetar").setCombo("tes_tarifas", "ide_tetar", "detalle_tetar", "");
		tab_material_tarifa.dibujar();
		PanelTabla pat_material_tarifa= new PanelTabla();
		pat_material_tarifa.setPanelTabla(tab_material_tarifa);
		
		Division div_division = new Division();
		div_division.dividir2(pat_material, pat_material_tarifa, "50%", "H");
		agregarComponente(div_division);
		
	}


	

	

	public Tabla getTab_material() {
		return tab_material;
	}


	public void setTab_material(Tabla tab_material) {
		this.tab_material = tab_material;
	}


	public Tabla getTab_material_tarifa() {
		return tab_material_tarifa;
	}

	public void setTab_material_tarifa(Tabla tab_material_tarifa) {
		this.tab_material_tarifa = tab_material_tarifa;
	}






	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();
	}


	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_material.guardar()){
			if(tab_material_tarifa.guardar()){
				guardarPantalla();
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
	}


}
