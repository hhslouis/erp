package paq_tesoreria;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import framework.componentes.Boton;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionCalendario;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_clientes_pago extends Pantalla{
	
	private Tabla tab_tipo_documento=new Tabla ();
	private SeleccionCalendario sel_calendario=new SeleccionCalendario();
	private List<Object> lis_datos_rol = new ArrayList<Object>();
	private List lis_nom_columnas=new ArrayList();


	public pre_clientes_pago (){
		
		tab_tipo_documento.setId("tab_tipo_documento");
		tab_tipo_documento.setNumeroTabla(1);
		tab_tipo_documento.setSql("select ruc_tepro,row_number()over(order by ruc_tepro) as referencia,nombre_tepro,codigo_banco_geins,numero_cuenta_tepcb,"
				+" codigo_gttcb,valor_pago_tecpo,codigo_tetic,detalle_tecpo"
				+" from tes_comprobante_pago a"
				+" left join tes_proveedor b on a.ide_tepro=b.ide_tepro"
				+" left join tes_proveedor_cuenta_bancaria c on a.ide_tepro=b.ide_tepro"
				+" left join gen_institucion d on c.ide_geins = d.ide_geins"
				+" left join gth_tipo_cuenta_bancaria e on c.ide_gttcb = e.ide_gttcb"
				+" left join tes_tipo_concepto f on a.ide_tetic = f.ide_tetic where 1=-1"
				+" order by ruc_tepro");
		tab_tipo_documento.setLectura(true);
		tab_tipo_documento.dibujar();
		PanelTabla pat_tipo_docuemto=new PanelTabla(); 
		pat_tipo_docuemto.setPanelTabla(tab_tipo_documento);
		
		Boton bot_consulta_factura = new Boton();
		bot_consulta_factura.setValue("Consultar Pagos");
		bot_consulta_factura.setMetodo("dibujaBoton");
		bar_botones.agregarBoton(bot_consulta_factura);
		
		Boton bot_excel=new Boton();
		bot_excel.setValue("Exportar EXCEL");
		bot_excel.setAjax(false);
		bot_excel.setMetodo("exportarExcel");
		bar_botones.agregarBoton(bot_excel);

		
		agregarComponente(pat_tipo_docuemto);
		inicializaCalendario();
		
		
	}
	public void inicializaCalendario(){
		sel_calendario.setTitle("SELECCION DE FECHAS");
		sel_calendario.setFooter("Seleccione un Rango de fechas");
		sel_calendario.setFecha1(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_calendario.setFecha2(utilitario.sumarDiasFecha(utilitario.getDate(), -1));
		sel_calendario.getBot_aceptar().setMetodo("actualizarConsulta");
		agregarComponente(sel_calendario);
	}
	public void dibujaBoton(){
		sel_calendario.dibujar();
	}
	public void actualizarConsulta(){
		tab_tipo_documento.setSql("select ruc_tepro,row_number()over(order by ruc_tepro) as referencia,substring(nombre_tepro from 1 for 30) as nombre_tepro,codigo_banco_geins,numero_cuenta_tepcb,"
				+" codigo_gttcb,valor_pago_tecpo,codigo_tetic,detalle_tecpo"
				+" from tes_comprobante_pago a"
				+" left join tes_proveedor b on a.ide_tepro=b.ide_tepro"
				+" left join tes_proveedor_cuenta_bancaria c on a.ide_tepro=b.ide_tepro"
				+" left join gen_institucion d on c.ide_geins = d.ide_geins"
				+" left join gth_tipo_cuenta_bancaria e on c.ide_gttcb = e.ide_gttcb"
				+" left join tes_tipo_concepto f on a.ide_tetic = f.ide_tetic where fecha_pago_tecpo between '"+sel_calendario.getFecha1String()+"' and '"+sel_calendario.getFecha2String()+"'"
				+" order by ruc_tepro");
		tab_tipo_documento.ejecutarSql();
		utilitario.addUpdate("tab_tipo_documento");
		sel_calendario.cerrar();
	}
	public void exportarExcel(){
		if (tab_tipo_documento.getTotalFilas()>0){
				exportarXLS("spi.xls","1","1");
			
		}
	}
	public void exportarXLS(String nombre,String tipo_nomina,String mes) { 
		try { 
			ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext(); 
			String nom = nombre; 
			File result = new File(extContext.getRealPath("/" + nom)); 
			WritableWorkbook archivo_xls = Workbook.createWorkbook(result); 
			WritableSheet hoja_xls = archivo_xls.createSheet("Tabla", 0); 
			WritableFont fuente = new WritableFont(WritableFont.TAHOMA, 10);
			WritableCellFormat formato_celda = new WritableCellFormat(fuente); 
			formato_celda.setAlignment(jxl.format.Alignment.LEFT); 
			formato_celda.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
			formato_celda.setOrientation(Orientation.HORIZONTAL); 
			//formato_celda.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, Colour.BLACK);

			WritableFont fuente_suc = new WritableFont(WritableFont.ARIAL, 11);
			WritableCellFormat formato_celda_suc = new WritableCellFormat(fuente_suc); 
			formato_celda_suc.setAlignment(jxl.format.Alignment.LEFT); 
			formato_celda_suc.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
			formato_celda_suc.setOrientation(Orientation.HORIZONTAL); 
			formato_celda_suc.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, Colour.BLACK);

			WritableFont fuente_totales = new WritableFont(WritableFont.ARIAL, 11);
			WritableCellFormat formato_celda_totales = new WritableCellFormat(fuente_suc); 
			formato_celda_totales.setAlignment(jxl.format.Alignment.RIGHT); 
			formato_celda_totales.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
			formato_celda_totales.setOrientation(Orientation.HORIZONTAL); 
			formato_celda_totales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, Colour.RED);

			WritableCellFormat formato_celda_valor_rubro = new WritableCellFormat(fuente); 
			formato_celda_valor_rubro.setAlignment(jxl.format.Alignment.RIGHT); 
			formato_celda_valor_rubro.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
			formato_celda_valor_rubro.setOrientation(Orientation.HORIZONTAL); 
			formato_celda_valor_rubro.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, Colour.BLACK);

			int int_columna = 0; 
			int int_fila=4;

			CellView cv=new CellView();
			for (int i = 0; i < tab_tipo_documento.getTotalFilas(); i++) {
				
					// NOMBRES DE COLUMNAS
					// DEPARTAMENTO
					jxl.write.Label lab1 = new jxl.write.Label(0, 0, "CEDULA/RUC", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(0,cv);

					// CEDULA
					lab1 = new jxl.write.Label(1, 0, "REFERENCIA", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(1,cv);

					// NOMBRE 
					lab1 = new jxl.write.Label(2, 0, "NOMBRE", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(2,cv);

					// INSTITUCION FINANCIERA
					lab1 = new jxl.write.Label(3, 0, "INSTITUCION FINANCIERA", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(3,cv);

					// CUENTA BENEFICIARIO
					lab1 = new jxl.write.Label(4, 0, "CUENTA BENEFICIARIO", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(4,cv);		
					
					// TIPOCUENTA
					lab1 = new jxl.write.Label(5, 0, "TIPOCUENTA", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(5,cv);

					// VALOR
					lab1 = new jxl.write.Label(6, 0, "VALOR", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(6,cv);

					// CONCEPTO
					lab1 = new jxl.write.Label(7, 0, "CONCEPTO", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(7,cv);
					
					// DETALLE
					lab1 = new jxl.write.Label(8, 0, "DETALLE", formato_celda);
					hoja_xls.addCell(lab1);
					cv=new CellView();
					cv.setAutosize(true);
					hoja_xls.setColumnView(8,cv);	
					
					
						//Cargamos RUC
						jxl.write.Label lab2 = new jxl.write.Label(0, i+1,tab_tipo_documento.getValor(i, "ruc_tepro"), formato_celda);
						hoja_xls.addCell(lab2);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(0,cv);
						
						//Cargamos REFRECNIA
						jxl.write.Number labnum1 = new jxl.write.Number(1, i+1,Double.parseDouble(tab_tipo_documento.getValor(i, "referencia")), formato_celda);
						hoja_xls.addCell(labnum1);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(1,cv);
						//Cargamos PROVEEDRO
						lab2 = new jxl.write.Label(2, i+1,tab_tipo_documento.getValor(i, "nombre_tepro"), formato_celda);
						hoja_xls.addCell(lab2);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(2,cv);
						//Cargamos INSITUCION FINANCIERA
						jxl.write.Number labnum2 = new jxl.write.Number(3, i+1,Double.parseDouble(tab_tipo_documento.getValor(i, "codigo_banco_geins")), formato_celda);
						hoja_xls.addCell(labnum2);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(3,cv);
						
						//Cargamos CUENTA BENEFICIARIO
						jxl.write.Number labnum3 = new jxl.write.Number(4, i+1,Double.parseDouble(tab_tipo_documento.getValor(i, "numero_cuenta_tepcb")), formato_celda);
						hoja_xls.addCell(labnum3);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(4,cv);
						
						//Cargamos tipo cuenta bancaria
						jxl.write.Number labnum4 = new jxl.write.Number(5, i+1,Double.parseDouble(tab_tipo_documento.getValor(i, "codigo_gttcb")), formato_celda);
						hoja_xls.addCell(labnum4);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(5,cv);
						
						//Cargamos VALOR PAGAR
						jxl.write.Number labnum = new jxl.write.Number(6, i+1,Double.parseDouble(tab_tipo_documento.getValor(i, "valor_pago_tecpo")), formato_celda);
						hoja_xls.addCell(labnum);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(6,cv);
						
						//Cargamos CODIGO CONCEPTO
						jxl.write.Number labnum5 = new jxl.write.Number(7, i+1,Double.parseDouble(tab_tipo_documento.getValor(i, "codigo_tetic")), formato_celda);
						hoja_xls.addCell(labnum5);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(7,cv);
						
						//Cargamos RUC
						lab2 = new jxl.write.Label(8, i+1,tab_tipo_documento.getValor(i, "detalle_tecpo"), formato_celda);
						hoja_xls.addCell(lab2);
						cv=new CellView();
						cv.setAutosize(true);
						hoja_xls.setColumnView(8,cv);
					
					
				int_fila=int_fila+1;
			}



			archivo_xls.write(); 
			archivo_xls.close(); 
			FacesContext.getCurrentInstance().getExternalContext().redirect(extContext.getRequestContextPath() + "/" + nom); 
		} catch (Exception e) { 
			System.out.println("Error no se genero el XLS :" + e.getMessage()); 
		} 
	}

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		tab_tipo_documento.insertar();
		
		
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		tab_tipo_documento.guardar();
		guardarPantalla();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		tab_tipo_documento.eliminar();
		
	}



	public Tabla getTab_tipo_documento() {
		return tab_tipo_documento;
	}



	public void setTab_tipo_documento(Tabla tab_tipo_documento) {
		this.tab_tipo_documento = tab_tipo_documento;
	}



	public SeleccionCalendario getSel_calendario() {
		return sel_calendario;
	}



	public void setSel_calendario(SeleccionCalendario sel_calendario) {
		this.sel_calendario = sel_calendario;
	}
	public List<Object> getLis_datos_rol() {
		return lis_datos_rol;
	}
	public void setLis_datos_rol(List<Object> lis_datos_rol) {
		this.lis_datos_rol = lis_datos_rol;
	}

}
