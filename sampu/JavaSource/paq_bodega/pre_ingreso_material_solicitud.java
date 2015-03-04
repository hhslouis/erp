package paq_bodega;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;

import framework.aplicacion.Fila;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.BotonesCombo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.ItemMenu;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import paq_bodega.ejb.ServicioBodega;
import paq_sistema.aplicacion.Pantalla;

public class pre_ingreso_material_solicitud extends Pantalla{

	private Tabla tab_ingreso_material= new Tabla();
	private Tabla tab_solicitud=new Tabla();
	private AutoCompletar aut_ing_material= new AutoCompletar();
	private SeleccionTabla set_solicitud = new SeleccionTabla();

	@EJB
	private ServicioBodega ser_bodega = (ServicioBodega) utilitario.instanciarEJB(ServicioBodega.class);

	public pre_ingreso_material_solicitud() {



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
		
		Boton bot_material = new Boton();
		bot_material.setValue("Buscar Solicitud Compra");
		bot_material.setTitle("Solicitud Compra");
		bot_material.setIcon("ui-icon-person");
		bot_material.setMetodo("importarSolicitud");
		bar_botones.agregarBoton(bot_material);

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
