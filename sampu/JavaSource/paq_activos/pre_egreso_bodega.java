package paq_activos;

import javax.ejb.EJB;

import framework.aplicacion.Fila;
import framework.componentes.Boton;
import framework.componentes.BotonesCombo;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.ItemMenu;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Texto;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_egreso_bodega extends Pantalla{
	private Tabla tab_egreso= new Tabla();
	private Tabla tab_activos_fijos= new Tabla();
	private SeleccionTabla set_egreso = new SeleccionTabla();
	private Dialogo dia_recibir_activo=new Dialogo();
	private Dialogo dia_ingreso=new Dialogo();
	private int int_maximo_detalle=-1;
	private Texto tex_maximo=new Texto();
	private Combo com_anio=new Combo();




	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	
	
	public pre_egreso_bodega(){
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);
		
		
		Boton bot_importar= new Boton();
		bot_importar.setValue("Buscar Solicitud Egreso");
		bot_importar.setMetodo("importar");
		bar_botones.agregarBoton(bot_importar);

		
		Boton bot_egreso=new Boton();
		bot_egreso.setIcon("ui-icon-person");
		bot_egreso.setValue("Recibir Activo");
		bot_egreso.setMetodo("importarActivo");
		bar_botones.agregarBoton(bot_egreso);

		BotonesCombo boc_seleccion_inversa = new BotonesCombo();
		ItemMenu itm_todas = new ItemMenu();
		ItemMenu itm_niguna = new ItemMenu();

		boc_seleccion_inversa.setValue("Selección Inversa");
		boc_seleccion_inversa.setIcon("ui-icon-circle-check");
		boc_seleccion_inversa.setMetodo("seleccinarInversa");
		boc_seleccion_inversa.setUpdate("tab_egreso");
		itm_todas.setValue("Seleccionar Todo");
		itm_todas.setIcon("ui-icon-check");
		itm_todas.setMetodo("seleccionarTodas");
		itm_todas.setUpdate("tab_egreso");
		boc_seleccion_inversa.agregarBoton(itm_todas);
		itm_niguna.setValue("Seleccionar Ninguna");
		itm_niguna.setIcon("ui-icon-minus");
		itm_niguna.setMetodo( "seleccionarNinguna");
		itm_niguna.setUpdate("tab_egreso");
		boc_seleccion_inversa.agregarBoton(itm_niguna);
		
		//////egreso
		tab_egreso.setId("tab_egreso");
		tab_egreso.setSql("select c.ide_boegr,detalle_bomat,fecha_egreso_boegr," +
				" cantidad_egreso_boegr,documento_egreso_boegr," +
				" fecha_compra_bobod,num_factura_bobod,b.ide_tepro,nombre_tepro" +
				" from bodt_material a,bodt_bodega b,bodt_egreso c,tes_proveedor d" +
				" where a.ide_bomat = b.ide_bomat" +
				" and b.ide_boinv = c.ide_boinv" +
				" and b.ide_tepro = d.ide_tepro" +
				" and b.ide_geani = -1" +
				" order by detalle_bomat");
		tab_egreso.setNumeroTabla(1);
		//tab_egreso.setCampoPrimaria("ide_addef");
		
		tab_egreso.setLectura(true);
		tab_egreso.setTipoSeleccion(true);
		tab_egreso.dibujar();
		PanelTabla pat_panel=new PanelTabla();
		pat_panel.getChildren().add(boc_seleccion_inversa);
		pat_panel.setPanelTabla(tab_egreso);

		Division div_division=new Division();
		div_division.dividir1(pat_panel);
		agregarComponente(div_division);
		///dialogo1
		dia_ingreso.setId("dia_ingreso");
		dia_ingreso.setTitle("EGRESO BODEGA");
		dia_ingreso.setWidth("30%");
		dia_ingreso.setHeight("23%");		
		dia_ingreso.getBot_aceptar().setMetodo("aceptarEgreso");	
		
		Grid gri_grid=new Grid();
		gri_grid.setStyle("height:" + (dia_ingreso.getAltoPanel()-10) + "px;overflow: auto;display: block;");
		gri_grid.setColumns(1);
		gri_grid.setWidth("98%");
		gri_grid.getChildren().add(new Etiqueta("Ingrese el número de egreso de bodega"));
		tex_maximo.setStyle("width:98%");
		gri_grid.getChildren().add(tex_maximo);		
		dia_ingreso.setDialogo(gri_grid);		
		agregarComponente(dia_ingreso);
		
		
		///dialogo 2 
		
		////selecion tabla
		dia_recibir_activo.setId("dia_recibir_activo");
		dia_recibir_activo.setTitle("RECIBIR ACTIVO");
		dia_recibir_activo.setWidth("45%");
		dia_recibir_activo.setHeight("45%");
		Grid gri_cuerpo=new Grid();
		
		tab_activos_fijos.setId("tab_activos_fijos");
		tab_activos_fijos.setTabla("afi_activo","ide_afact", 2);
		tab_activos_fijos.getColumna("ide_afubi").setVisible(true);
		tab_activos_fijos.getColumna("ide_aftia").setVisible(true);
		tab_activos_fijos.getColumna("ide_aftip").setVisible(true);
		tab_activos_fijos.getColumna("ide_afseg").setVisible(true);
		tab_activos_fijos.getColumna("ide_afnoa").setVisible(true);
		tab_activos_fijos.getColumna("ide_geare").setVisible(true);
		tab_activos_fijos.getColumna("ide_afacd").setVisible(true);
		tab_activos_fijos.getColumna("ide_cocac").setVisible(true);
		tab_activos_fijos.getColumna("ide_afest").setVisible(true);
		tab_activos_fijos.getColumna("ide_tepro").setVisible(true);
		tab_activos_fijos.getColumna("foto_bien_afact").setVisible(true);
		tab_activos_fijos.getColumna("valor_unitario_afact").setVisible(true);
		tab_activos_fijos.getColumna("cantidad_afact").setVisible(true);
		tab_activos_fijos.getColumna("valor_neto_afact").setVisible(true);
		tab_activos_fijos.getColumna("valor_neto_afact").setVisible(true);
		tab_activos_fijos.getColumna("valor_compra_afact").setVisible(true);
		tab_activos_fijos.getColumna("secuencial_afact").setVisible(true);
		tab_activos_fijos.setTipoFormulario(true);
		tab_activos_fijos.getGrid().setColumns(4);
		gri_cuerpo.getChildren().add(tab_activos_fijos);
		
		dia_recibir_activo.getBot_aceptar().setMetodo("aceptarDialogo");

		dia_recibir_activo.setDialogo(gri_cuerpo);
		agregarComponente(dia_recibir_activo);

			
	}
	///////EGRESO BODEGA
	public void seleccionaElAnio (){
		if(com_anio.getValue()!=null){
			tab_egreso.setCondicion("ide_geani="+com_anio.getValue());
			tab_egreso.ejecutarSql();
			//tab_mes.ejecutarValorForanea(tab_poa.getValorSeleccionado());

		}
		else{
			utilitario.agregarMensajeInfo("Selecione un año", "");

		}
	}
	
	public void importar(){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Debe seleccionar un Año", "");
			return;
		}
		if(tab_egreso.isEmpty()){
			
			//if(getMaximoSecuencialEmpleados()==0 && int_maximo_detalle==-1){
				if(dia_ingreso.isVisible()){
					if(tex_maximo.getValue()!=null && !tex_maximo.getValue().toString().isEmpty()){
						try {
							int_maximo_detalle=Integer.parseInt(tex_maximo.getValue().toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
						dia_ingreso.cerrar();
					}
					else{
						utilitario.agregarMensajeInfo("Debe ingresar un valor en el campo de texto", "");
						return;
					}
				}else{
					dia_ingreso.dibujar();	
					return;
				}			
			}
	}
	
	
////aceptar
	
	public void aceptarEgreso() {
		tab_egreso.setSql("select c.ide_boegr,detalle_bomat,fecha_egreso_boegr," +
				" cantidad_egreso_boegr,documento_egreso_boegr," +
				" fecha_compra_bobod,num_factura_bobod,b.ide_tepro,nombre_tepro" +
				" from bodt_material a,bodt_bodega b,bodt_egreso c,tes_proveedor d" +
				" where a.ide_bomat = b.ide_bomat" +
				" and b.ide_boinv = c.ide_boinv" +
				" and b.ide_tepro = d.ide_tepro" +
				" and b.ide_geani = " +com_anio.getValue()+
				" and documento_egreso_boegr='"+tex_maximo.getValue()+"'"+
				" order by detalle_bomat");
		tab_egreso.ejecutarSql();
		tab_egreso.imprimirSql();
		dia_ingreso.cerrar();
	}
	
	//////recibir avtivo
	public void importarActivo() {
		
		
	}
	
	public void aceptarActivo() {

		
		
	}
	
	
	
	
long ide_inicial=0;
public void seleccionarTodas() {
	tab_egreso.setSeleccionados(null);
    Fila seleccionados[] = new Fila[tab_egreso.getTotalFilas()];
    for (int i = 0; i < tab_egreso.getFilas().size(); i++) {
        seleccionados[i] = tab_egreso.getFilas().get(i);
    }
    tab_egreso.setSeleccionados(seleccionados);
}

/**DFJ**/
public void seleccinarInversa() {
        if (tab_egreso.getSeleccionados() == null) {
            seleccionarTodas();
        } else if (tab_egreso.getSeleccionados().length == tab_egreso.getTotalFilas()) {
            seleccionarNinguna();
        } else {
            Fila seleccionados[] = new Fila[tab_egreso.getTotalFilas() - tab_egreso.getSeleccionados().length];
            int cont = 0;
            for (int i = 0; i < tab_egreso.getFilas().size(); i++) {
                boolean boo_selecionado = false;
                for (int j = 0; j < tab_egreso.getSeleccionados().length; j++) {
                    if (tab_egreso.getSeleccionados()[j].equals(tab_egreso.getFilas().get(i))) {
                        boo_selecionado = true;
                        break;
                    }
                }
                if (boo_selecionado == false) {
                    seleccionados[cont] = tab_egreso.getFilas().get(i);
                    cont++;
                }
            }
            tab_egreso.setSeleccionados(seleccionados);
        }
    }

/**DFJ**/
public void seleccionarNinguna() {
	tab_egreso.setSeleccionados(null);
    }
	

	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}
	public Tabla getTab_egreso() {
		return tab_egreso;
	}
	public void setTab_egreso(Tabla tab_egreso) {
		this.tab_egreso = tab_egreso;
	}

	public Dialogo getDia_ingreso() {
		return dia_ingreso;
	}

	public void setDia_ingreso(Dialogo dia_ingreso) {
		this.dia_ingreso = dia_ingreso;
	}

}
