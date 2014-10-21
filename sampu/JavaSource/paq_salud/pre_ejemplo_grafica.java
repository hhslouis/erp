package paq_salud;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Ajax;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import paq_nomina.cls_graficas;
import paq_sistema.aplicacion.Pantalla;


/**
 *
 * @author Diego
 */
public class pre_ejemplo_grafica extends Pantalla {


	private Division div_division = new Division();

	private TablaGenerica tab_rep_grafico=new TablaGenerica();
	private BarChart barchart = new BarChart();
	private CartesianChartModel categoryModel;

	cls_graficas grafico=new cls_graficas();
	
	public pre_ejemplo_grafica() {        

		
		tab_rep_grafico=utilitario.consultar("SELECT " +
			"a.ide_gtemp as  ide_label, " +
			"'PUNTAJE'as detalle_label, " +
			"d.IDE_SAPOS as ide_col_ejeX, " +
			"(case when d.DETALLE_SAPOS is null then  'DETALLE' ELSE d.DETALLE_SAPOS END) as nom_col_ejeX, " +
			"e.CALIFICACION_SACAL as MONTO " +
			"FROM SAO_EVALUACION_POSICIOGRAMA a " +
			"left join ( " +
			"SELECT ide_gtemp,apellido_paterno_gtemp ||' '|| apellido_materno_gtemp ||' '|| primer_nombre_gtemp ||' '|| segundo_nombre_gtemp as empleado FROM gth_empleado " +
			")b on b.ide_gtemp=a.ide_gtemp " +
			"left join ( " +
			"SELECT ide_sadee,ide_saevp,ide_sapos,ide_sacal FROM SAO_DETALLE_EVALUACION " +
			")c on c.ide_saevp=a.ide_saevp " +
			"left join( " +
			"SELECT IDE_SAPOS,DETALLE_SAPOS FROM SAO_POSICIOGRAMA " +
			")d on d.ide_sapos=c.ide_sapos " +
			"left join( " +
			"SELECT IDE_SACAL,CALIFICACION_SACAL FROM SAO_CALIFICACION " +
			")e on e.ide_sacal=c.ide_sacal " +
			"where a.ide_gtemp=968 " +
			"order by nom_col_ejeX");
		
		categoryModel=new CartesianChartModel();		

	    barchart=new BarChart();
	    barchart=grafico.getBarchar(tab_rep_grafico,"TOTAL SALARIOS POR MESES",500,570);
	    
	    Ajax evt=new Ajax();
		evt.setMetodo("itemSelect");
		barchart.addClientBehavior("itemSelect", evt);

		
		div_division.dividir1(barchart);
		div_division.getDivision1().setHeader("EJEMPLO GRAFICO EN BARRAS");
		
		agregarComponente(div_division);

	}



	public void itemSelect(ItemSelectEvent event) {  
		categoryModel=grafico.getCategoryModel(); 

		Map map= categoryModel.getSeries().get(0).getData();
		Iterator it = map.entrySet().iterator();
		List lis_nom_col_eje_x=new ArrayList();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			lis_nom_col_eje_x.add(e.getKey()+"");
		}	     


		String nomina=categoryModel.getSeries().get(event.getSeriesIndex()).getLabel();	     
		map= categoryModel.getSeries().get(event.getSeriesIndex()).getData();

		System.out.println("label "+nomina+" map "+map);
		int int_num_dep=event.getItemIndex();
		String str_nom_col_eje_x="";
		for (int i = 0; i < lis_nom_col_eje_x.size(); i++) {
			if (i==int_num_dep){
				str_nom_col_eje_x=lis_nom_col_eje_x.get(i)+"";
				break;
			}
		}

		it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			if (e.getKey().toString().equalsIgnoreCase(str_nom_col_eje_x)){
				utilitario.agregarMensaje("Item seleccionado: "+nomina, e.getKey() + " " + e.getValue());
			}
		}	     

	}  



	@Override
	public void insertar() {
		//		tab_tabla.insertar();
	}

	@Override
	public void guardar() {
		guardarPantalla();
	}

	@Override
	public void eliminar() {
	}


	private ValueExpression crearValueExpression(String valueExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createValueExpression(
				facesContext.getELContext(), "#{" + valueExpression + "}", Object.class);
	}


	private ValueExpression crearValueExpression(String valueExpression,Class c) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createValueExpression(
				facesContext.getELContext(), "#{" + valueExpression + "}", c);
	}


	public BarChart getBarchart() {
		return barchart;
	}


	public void setBarchart(BarChart barchart) {
		this.barchart = barchart;
	}



	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}


	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}





}


