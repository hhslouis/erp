package paq_bodega;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;

import framework.aplicacion.Fila;
import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.BotonesCombo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.ItemMenu;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_ingreso_material_solicitud extends Pantalla{

	private Tabla tab_ingreso_material= new Tabla();
	private Tabla tab_solicitud=new Tabla();
	private AutoCompletar aut_ing_material= new AutoCompletar();
	private SeleccionTabla set_solicitud = new SeleccionTabla();
	private Dialogo dia_recibir_solicitud=new Dialogo();


	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);
	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);
	
	public pre_ingreso_material_solicitud() {

		Boton bot_material = new Boton();
		bot_material.setValue("Buscar Solicitud Compra");
		bot_material.setTitle("Solicitud Compra");
		bot_material.setIcon("ui-icon-person");
		bot_material.setMetodo("importarSolicitud");
		bar_botones.agregarBoton(bot_material);
		
		Boton bot_solicitud=new Boton();
		bot_solicitud.setIcon("ui-icon-person");
		bot_solicitud.setValue("Recibir Solicitud");
		bot_solicitud.setMetodo("abrirRecibirSolicitud");
		bar_botones.agregarBoton(bot_solicitud);

		BotonesCombo boc_seleccion_inversa = new BotonesCombo();
		ItemMenu itm_todas = new ItemMenu();
		ItemMenu itm_niguna = new ItemMenu();

		boc_seleccion_inversa.setValue("Selección Inversa");
		boc_seleccion_inversa.setIcon("ui-icon-circle-check");
		boc_seleccion_inversa.setMetodo("seleccinarInversa");
		boc_seleccion_inversa.setUpdate("tab_solicitud");
		itm_todas.setValue("Seleccionar Todo");
		itm_todas.setIcon("ui-icon-check");
		itm_todas.setMetodo("seleccionarTodas");
		itm_todas.setUpdate("tab_solicitud");
		boc_seleccion_inversa.agregarBoton(itm_todas);
		itm_niguna.setValue("Seleccionar Ninguna");
		itm_niguna.setIcon("ui-icon-minus");
		itm_niguna.setMetodo( "seleccionarNinguna");
		itm_niguna.setUpdate("tab_solicitud");
		boc_seleccion_inversa.agregarBoton(itm_niguna);

		tab_solicitud.setId("tab_solicitud");
		tab_solicitud.setSql("select b.ide_addef,detalle_adsoc,num_factura_adfac,valor_adsoc,nro_solicitud_adsoc,valor_total_addef,valor_unitario_addef,cantidad_addef," +
				" codigo_bomat,detalle_bomat from adq_solicitud_compra a,adq_detalle_factura b,adq_factura c , bodt_material d" +
				" where a.ide_adsoc=c.ide_adsoc and b.ide_adfac=c.ide_adfac  and d.ide_bomat=b.ide_bomat and a.ide_adsoc=-1 order by codigo_bomat");
		tab_solicitud.setNumeroTabla(2);
		tab_solicitud.setCampoPrimaria("ide_addef");
		
		tab_solicitud.setLectura(true);
		tab_solicitud.setTipoSeleccion(true);
		tab_solicitud.dibujar();
		PanelTabla pat_panel=new PanelTabla();
		pat_panel.getChildren().add(boc_seleccion_inversa);
		pat_panel.setPanelTabla(tab_solicitud);

		Division div_division=new Division();
		div_division.dividir1(pat_panel);
		agregarComponente(div_division);
		
	
		set_solicitud.setId("set_solicitud");
		set_solicitud.setSeleccionTabla(ser_bodega.getSolicitud("true,false"),"ide_adsoc");
		set_solicitud.getTab_seleccion().getColumna("detalle_adsoc").setFiltro(true);
		set_solicitud.getTab_seleccion().getColumna("nro_solicitud_adsoc").setFiltro(true);
		set_solicitud.getTab_seleccion().getColumna("nombre_tepro").setFiltro(true);
		set_solicitud.getTab_seleccion().getColumna("ruc_tepro").setFiltro(true);
		set_solicitud.getTab_seleccion().getColumna("valor_adsoc").setFiltro(true);
		set_solicitud.getBot_aceptar().setMetodo("aceptarSolicitud");
		set_solicitud.getTab_seleccion().ejecutarSql();
		set_solicitud.setRadio();
		agregarComponente(set_solicitud);
		
		dia_recibir_solicitud.setId("dia_recibir_solicitud");
		dia_recibir_solicitud.setTitle("RECIBIR SOLICITUD");
		dia_recibir_solicitud.setWidth("70%");
		dia_recibir_solicitud.setHeight("50%");
		Grid gri_cuerpo=new Grid();
		
		tab_ingreso_material.setId("tab_ingreso_material");
		tab_ingreso_material.setTabla("bodt_bodega", "ide_bobod",2);
		tab_ingreso_material.setTipoFormulario(true);
		tab_ingreso_material.setCondicion("ide_bobod=-1");//para que aparesca vacia
		tab_ingreso_material.getGrid().setColumns(4);
		tab_ingreso_material.getColumna("ide_bomat").setVisible(false);
		tab_ingreso_material.getColumna("ide_tepro").setVisible(false);
		tab_ingreso_material.getColumna("ide_coest").setVisible(false);
		tab_ingreso_material.getColumna("fecha_compra_bobod").setVisible(false);
		tab_ingreso_material.getColumna("cantidad_ingreso_bobod").setVisible(false);
		tab_ingreso_material.getColumna("recibido_bobod").setVisible(false);
		tab_ingreso_material.getColumna("modelo_bobod").setVisible(false);
		tab_ingreso_material.getColumna("marca_bobod").setVisible(false);
		tab_ingreso_material.getColumna("num_factura_bobod").setVisible(false);
		tab_ingreso_material.getColumna("serie_bobod").setVisible(false);
		tab_ingreso_material.getColumna("valor_unitario_bobod").setVisible(false);
		tab_ingreso_material.getColumna("color_bobod").setVisible(false);
		tab_ingreso_material.getColumna("saldo_bobod").setVisible(false);
		tab_ingreso_material.getColumna("ide_adsoc").setVisible(false);
		tab_ingreso_material.getColumna("activo_bobod").setVisible(false);
		tab_ingreso_material.getColumna("valor_total_bobod").setVisible(false);
		tab_ingreso_material.getColumna("ide_comov").setVisible(false);
		tab_ingreso_material.getColumna("ide_boinv").setVisible(false);
		tab_ingreso_material.getColumna("ide_bobod").setVisible(false);
		tab_ingreso_material.getColumna("ide_geani").setNombreVisual("AÑO");
		tab_ingreso_material.getColumna("num_doc_bobod").setNombreVisual("NUMERO DE DOCUMENTO");
		tab_ingreso_material.getColumna("descripcion_bobod").setNombreVisual("DESCRIPCION");
		tab_ingreso_material.getColumna("tipo_ingreso_bobod").setNombreVisual("TIPO DE INGRESO");
		tab_ingreso_material.getColumna("fecha_ingreso_bobod").setNombreVisual("FECHA DE INGRESO");
		tab_ingreso_material.getColumna("numero_ingreso_bobod").setNombreVisual("NUMERO DE INGRESO");
		tab_ingreso_material.getColumna("existencia_anterior_bobod").setVisible(false);
		tab_ingreso_material.getColumna("ide_geani").setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));
		   List lista = new ArrayList();
	       Object fila1[] = {
	           "1", "CONSUMO INTERNO"
	       };
	       Object fila2[] = {
	           "0", "CONSUMO EXTERNO"
	       };
	       
	       lista.add(fila1);
	       lista.add(fila2);
	       tab_ingreso_material.getColumna("tipo_ingreso_bobod").setRadio(lista, "1");
	       tab_ingreso_material.getColumna("tipo_ingreso_bobod").setRadioVertical(true);
		tab_ingreso_material.dibujar();
		gri_cuerpo.getChildren().add(tab_ingreso_material);
		dia_recibir_solicitud.getBot_aceptar().setMetodo("aceptarDialogoSolicitud");
		dia_recibir_solicitud.setDialogo(gri_cuerpo);
		agregarComponente(dia_recibir_solicitud);
		
		
	}
	
	public void importarSolicitud(){

		set_solicitud.getTab_seleccion().setSql(ser_bodega.getSolicitud("true,false"));
		set_solicitud.getTab_seleccion().ejecutarSql();
		set_solicitud.dibujar();

	}

	public  void aceptarSolicitud(){
		
		tab_solicitud.setSql("select b.ide_addef,detalle_adsoc,num_factura_adfac,valor_adsoc,nro_solicitud_adsoc,valor_total_addef,valor_unitario_addef,cantidad_addef," +
				" codigo_bomat,detalle_bomat from adq_solicitud_compra a,adq_detalle_factura b,adq_factura c , bodt_material d" +
				" where a.ide_adsoc=c.ide_adsoc and b.ide_adfac=c.ide_adfac  and d.ide_bomat=b.ide_bomat and a.ide_adsoc="+set_solicitud.getValorSeleccionado().toString()+" order by codigo_bomat");
		tab_solicitud.ejecutarSql();
		utilitario.addUpdate("tab_solicitud");
		set_solicitud.cerrar();
	}

	public void seleccionarTodas() {
		tab_solicitud.setSeleccionados(null);
        Fila seleccionados[] = new Fila[tab_solicitud.getTotalFilas()];
        for (int i = 0; i < tab_solicitud.getFilas().size(); i++) {
            seleccionados[i] = tab_solicitud.getFilas().get(i);
        }
        tab_solicitud.setSeleccionados(seleccionados);
}

/**DFJ**/
public void seleccinarInversa() {
        if (tab_solicitud.getSeleccionados() == null) {
            seleccionarTodas();
        } else if (tab_solicitud.getSeleccionados().length == tab_solicitud.getTotalFilas()) {
            seleccionarNinguna();
        } else {
            Fila seleccionados[] = new Fila[tab_solicitud.getTotalFilas() - tab_solicitud.getSeleccionados().length];
            int cont = 0;
            for (int i = 0; i < tab_solicitud.getFilas().size(); i++) {
                boolean boo_selecionado = false;
                for (int j = 0; j < tab_solicitud.getSeleccionados().length; j++) {
                    if (tab_solicitud.getSeleccionados()[j].equals(tab_solicitud.getFilas().get(i))) {
                        boo_selecionado = true;
                        break;
                    }
                }
                if (boo_selecionado == false) {
                    seleccionados[cont] = tab_solicitud.getFilas().get(i);
                    cont++;
                }
            }
            tab_solicitud.setSeleccionados(seleccionados);
        }
    }

/**DFJ**/
public void seleccionarNinguna() {
	tab_solicitud.setSeleccionados(null);
    }
public void abrirRecibirSolicitud(){
	//Hace aparecer el componente
	tab_ingreso_material.limpiar();
	tab_ingreso_material.insertar();
	//tab_direccion.limpiar();
	//	tab_direccion.insertar();
	dia_recibir_solicitud.dibujar();

}
long ide_inicial=0;

public void  aceptarDialogoSolicitud(){
	
	String str_seleccionados=tab_solicitud.getFilasSeleccionadas();
	System.out.println(" probando el str_Selccionado "+str_seleccionados);
	if(str_seleccionados!=null || !str_seleccionados.isEmpty()){
		
	TablaGenerica tab_consulta_solicitud= ser_bodega.getTablaGenericaSolicitudCompra(str_seleccionados);
	utilitario.getConexion().ejecutarSql("DELETE from SIS_BLOQUEO where upper(TABLA_BLOQ) like 'bodt_bodega'");
	String valor=utilitario.getConexion().ejecutarSql(ser_contabilidad.servicioCodigoMaximo("bodt_bodega", "ide_bobod"));
	ide_inicial=Long.parseLong(valor);
	
	System.out.println(" probando el inicial "+ide_inicial);

	for(int i=0;i<tab_consulta_solicitud.getTotalFilas();i++){
		
		utilitario.getConexion().ejecutarSql("update adq_detalle_factura set recibido_addef= false where ide_addef="+tab_consulta_solicitud.getValor(i,"ide_addef"));
		
		utilitario.getConexion().ejecutarSql("INSERT INTO bodt_bodega(ide_bobod, ide_geani, ide_bomat, ide_tepro, fecha_ingreso_bobod, fecha_compra_bobod, recibido_bobod, " +
				" cantidad_ingreso_bobod, numero_ingreso_bobod, num_factura_bobod, num_doc_bobod, descripcion_bobod, tipo_ingreso_bobod, " +
				" valor_unitario_bobod, valor_total_bobod,activo_bobod, ide_adsoc) VALUES("+ide_inicial+", "+tab_ingreso_material.getValor("ide_geani")+","+tab_consulta_solicitud.getValor(i,"ide_bomat")+"," 
				+ tab_consulta_solicitud.getValor(i,"ide_tepro")+",'"+tab_ingreso_material.getValor("fecha_ingreso_bobod")+"','"+tab_consulta_solicitud.getValor(i,"fecha_factura_adfac")+"',true,"
				+ tab_consulta_solicitud.getValor(i,"cantidad_addef")+",'"+tab_ingreso_material.getValor("numero_ingreso_bobod")+"','"+tab_consulta_solicitud.getValor(i,"num_factura_adfac")+
				"','"+tab_ingreso_material.getValor("num_doc_bobod")+"','"+tab_ingreso_material.getValor("descripcion_bobod")+"','"+tab_ingreso_material.getValor("tipo_ingreso_bobod")+
				"',"+tab_consulta_solicitud.getValor(i,"valor_unitario_addef")+","+tab_consulta_solicitud.getValor(i,"valor_total_addef")+",true,"+tab_consulta_solicitud.getValor(i,"ide_adsoc")+")");
		ide_inicial++;	
	}
	}
	else {
		utilitario.agregarMensajeInfo("Debe seleccionar almenos un registro", "");
		return;
		
	}
		
	}

	@Override
	public void insertar() {
		if (aut_ing_material.getValor()!=null){
			if(tab_ingreso_material.isFocus()){
				tab_ingreso_material.getColumna("ide_bobod").setValorDefecto(aut_ing_material.getValor());
				tab_ingreso_material.insertar();
			}

		}
		else{
			utilitario.agregarMensajeError("Debe seleccionar los datos de material","");
		}

	}

	@Override
	public void guardar() {
		if(tab_ingreso_material.guardar()){
			guardarPantalla();
		}

	}

	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();

	}
	public Tabla getTab_ingreso_material() {
		return tab_ingreso_material;
	}
	public void setTab_ingreso_material(Tabla tab_ingreso_material) {
		this.tab_ingreso_material = tab_ingreso_material;
	}
	public AutoCompletar getAut_ing_material() {
		return aut_ing_material;
	}
	public void setAut_ing_material(AutoCompletar aut_ing_material) {
		this.aut_ing_material = aut_ing_material;
	}
	public SeleccionTabla getSet_solicitud() {
		return set_solicitud;
	}
	public void setSet_solicitud(SeleccionTabla set_solicitud) {
		this.set_solicitud = set_solicitud;
	}
	public Tabla getTab_solicitud() {
		return tab_solicitud;
	}
	public void setTab_solicitud(Tabla tab_solicitud) {
		this.tab_solicitud = tab_solicitud;
	}

}
