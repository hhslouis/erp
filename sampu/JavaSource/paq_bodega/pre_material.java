package paq_bodega;



import java.util.ArrayList;
import java.util.List;


import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;

import paq_sistema.aplicacion.Pantalla;

public class pre_material extends Pantalla{
	private Tabla tab_material=new Tabla();
	private Tabla tab_material_tarifa = new Tabla();
	private Tabla tab_punto_venta=new Tabla();
	
	public pre_material() {
		tab_material.setHeader("MATERIALES DE BODEGA");
		tab_material.setId("tab_material");  
		tab_material.setTabla("bodt_material","ide_bomat", 1);	
		tab_material.getColumna("ide_bounm").setCombo("bodt_unidad_medida", "ide_bounm", "detalle_bounm,abreviatura_bounm", "");
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
   
        lista.add(fila1);
        lista.add(fila2);
       
        tab_material.getColumna("iva_bomat").setRadio(lista, "1");
        tab_material.getColumna("iva_bomat").setRadioVertical(true);
    	tab_material.agregarRelacion(tab_material_tarifa); //CON ESTO LE DECIMOS Q TIENE RELACION
		tab_material.agregarRelacion(tab_punto_venta);
		tab_material.setTipoFormulario(true);
		tab_material.getGrid().setColumns(4);
		tab_material.dibujar();
		PanelTabla pat_material=new PanelTabla();
		pat_material.setPanelTabla(tab_material);
		
		tab_material_tarifa.setHeader("TARIFAS");
		tab_material_tarifa.setId("tab_material_tarifa");
		tab_material_tarifa.setIdCompleto("tab_tabulador:tab_material_tarifa");
		tab_material_tarifa.setTabla("tes_material_tarifa", "ide_temat", 2);
		tab_material_tarifa.getGrid().setColumns(2);
		tab_material_tarifa.getColumna("ide_tetar").setCombo("tes_tarifas", "ide_tetar", "detalle_tetar", "");
		tab_material_tarifa.getColumna("ide_tetar").setUnico(true);
		tab_material_tarifa.getColumna("ide_bomat").setUnico(true);
		tab_material_tarifa.setCampoForanea("ide_bomat");
		tab_material_tarifa.dibujar();
		PanelTabla pat_material_tarifa= new PanelTabla();
		pat_material_tarifa.setPanelTabla(tab_material_tarifa);
		
		tab_punto_venta.setId("tab_punto_venta");
		tab_punto_venta.setIdCompleto("tab_tabulador:tab_punto_venta");
		tab_punto_venta.setHeader("punto ventas");
		tab_punto_venta.setTabla("fac_punto_venta", "ide_fapuv", 3);
		tab_punto_venta.setCampoForanea("ide_bomat");
		tab_punto_venta.dibujar();
		PanelTabla pat_punto_venta =new PanelTabla();
		pat_punto_venta.setPanelTabla(tab_punto_venta);
		
		
		//////TABULADORES
		Tabulador tab_tabulador = new Tabulador();
		tab_tabulador.setId("tab_tabulador");
		
		tab_tabulador.agregarTab("TARIFA DE MATERIALES",pat_material_tarifa);
		tab_tabulador.agregarTab("PUNTO DE VENTA", pat_punto_venta);
		
		Division div_division = new Division();
		div_division.dividir2(pat_material, tab_tabulador, "50%", "H");
		agregarComponente(div_division);
		
	}
	
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
				
		if (tab_material.isFocus()) {
			tab_material.insertar();
			//tab_poa.setValor("ide_geani", com_anio.getValue()+"");

		}
		else if (tab_material_tarifa.isFocus()) {
			tab_material_tarifa.insertar();

		}
		else if (tab_punto_venta.isFocus()) {
			tab_punto_venta.insertar();

		}
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if (tab_material.guardar()) {
			
			if (tab_material_tarifa.guardar()) {
				tab_punto_venta.guardar();	
					}
					
				}
			
		
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();

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
	public Tabla getTab_punto_venta() {
		return tab_punto_venta;
	}
	public void setTab_punto_venta(Tabla tab_punto_venta) {
		this.tab_punto_venta = tab_punto_venta;
	}
	
	}
