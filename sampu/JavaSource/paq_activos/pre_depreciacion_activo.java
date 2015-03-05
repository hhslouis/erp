package paq_activos;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;

import framework.aplicacion.Fila;
import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.ItemMenu;
import framework.componentes.BotonesCombo;
import paq_activos.ejb.ServicioActivos;
import paq_gestion.ejb.ServicioGestion;
import paq_nomina.ejb.ServicioNomina;
import paq_sistema.aplicacion.Pantalla;

public class pre_depreciacion_activo extends Pantalla {
	private Tabla tab_depreciacion = new Tabla();
	private Dialogo dia_fecha=new Dialogo();
	private Tabla tab_fecha=new Tabla();

	
	public pre_depreciacion_activo(){
		bar_botones.getBot_insertar().setRendered(false);
		
		
		
		tab_depreciacion.setId("tab_depreciacion");
		tab_depreciacion.setSql("select a.ide_afact,detalle_afact,marca_afact,serie_afact,modelo_afact,fecha_alta_afact,valor_compra_afact,vida_util_afact,"
				+" fecha_calculo_afact,valor_depre_mes_afact,val_depreciacion_periodo_afact,valor_depreciacion_afact"
				+" from afi_activo a"
				+" where not fecha_alta_afact is null and a.ide_afact=-1"
				+" order by fecha_alta_afact desc");
		tab_depreciacion.setNumeroTabla(1);
		tab_depreciacion.setCampoPrimaria("ide_afact");
		tab_depreciacion.setLectura(true);
		tab_depreciacion.dibujar();
	PanelTabla pat_panel=new PanelTabla();

	pat_panel.setPanelTabla(tab_depreciacion);
	Division div_division=new Division();
	div_division.dividir1(pat_panel);
	agregarComponente(div_division);
      
      
	
	

	///Boton depreciación activo
			Boton bot_depre=new Boton();
			bot_depre.setIcon("ui-calendario");
			bot_depre.setValue("DEPRECIACION ACTIVO");
			bot_depre.setMetodo("abrirDialogo");
			bar_botones.agregarBoton(bot_depre);
			dia_fecha.setId("dia_fecha");
			dia_fecha.setTitle("FECHA DEPRECIACION ACTIVO");
			dia_fecha.setWidth("45%");
			dia_fecha.setHeight("45%");
			Grid gri_cuerpo=new Grid();
			
			tab_fecha.setId("tab_fecha");
			tab_fecha.setTabla("afi_custodio", "ide_afcus",10);
			tab_fecha.setCondicion("ide_afact=-1");//para que aparesca vacia
			tab_fecha.setTipoFormulario(true);
			tab_fecha.getGrid().setColumns(2);
	//oculto todos los campos
			tab_fecha.getColumna("fecha_entrega_afcus").setNombreVisual("FECHA CALCULO");
			tab_fecha.getColumna("fecha_entrega_afcus").setValorDefecto(utilitario.getFechaActual());
			tab_fecha.getColumna("ide_afcus").setVisible(false);
			tab_fecha.getColumna("ide_afact").setVisible(false);
			tab_fecha.getColumna("gen_ide_geedp").setVisible(false);
			tab_fecha.getColumna("detalle_afcus").setVisible(false);
			tab_fecha.getColumna("cod_barra_afcus").setVisible(false);
			tab_fecha.getColumna("nro_secuencial_afcus").setVisible(false);
			tab_fecha.getColumna("activo_afcus").setVisible(false);
			tab_fecha.getColumna("ide_geedp").setVisible(false);
			tab_fecha.getColumna("fecha_entrega_afcus").setVisible(true);
			tab_fecha.getColumna("fecha_descargo_afcus").setVisible(false);
			tab_fecha.getColumna("numero_acta_afcus").setVisible(false);
			tab_fecha.getColumna("razon_descargo_afcus").setVisible(false);
			tab_fecha.dibujar();
			gri_cuerpo.getChildren().add(tab_fecha);
			
			dia_fecha.getBot_aceptar().setMetodo("aceptarDialogo");

			dia_fecha.setDialogo(gri_cuerpo);
			agregarComponente(dia_fecha);
		
	}

			public void abrirDialogo(){
				//Hace aparecer el componente
				
					tab_fecha.limpiar();
					tab_fecha.insertar();
					dia_fecha.dibujar();
				}
			public void  aceptarDialogo(){
				String fecha=tab_fecha.getValor("fecha_entrega_afcus");
				//TablaGenerica tab_consulta_fecha= utilitario.consultar("select avalactivos('"+fecha+"')");
				utilitario.getConexion().ejecutarSql("update afi_activo" +
						" set  vida_util_afact = 5" +
						" where vida_util_afact <=0;" +
						" update afi_activo" +
						" set fecha_calculo_afact ='"+fecha+"'" +
						" where fecha_calculo_afact is null;" +
						" update afi_activo" +
						" set fecha_calculo_afact = '"+fecha+"';" +
						" update afi_activo" +
						" set valor_depre_mes_afact = valor_compra_afact/(vida_util_afact*12);" +
						" update afi_activo" +
						" set val_depreciacion_periodo_afact = (valor_compra_afact/vida_util_afact) * EXTRACT( MONTH FROM fecha_calculo_afact)" +
						" where EXTRACT( year FROM fecha_calculo_afact) > EXTRACT( year FROM fecha_alta_afact);" +
						" update afi_activo" +
						" set val_depreciacion_periodo_afact = (valor_compra_afact/vida_util_afact) *  EXTRACT( MONTH FROM age(fecha_calculo_afact,fecha_alta_afact))" +
						" where EXTRACT( year FROM fecha_calculo_afact) = EXTRACT( year FROM fecha_alta_afact);" +
						" update afi_activo" +
						" set valor_depreciacion_afact = (valor_compra_afact/vida_util_afact)* (EXTRACT( year FROM age(fecha_calculo_afact,fecha_alta_afact))*12 + EXTRACT( MONTH FROM age(fecha_calculo_afact,fecha_alta_afact)));" +
						" update afi_activo" +
						" set valor_depreciacion_afact  = valor_compra_afact *0.9" +
						" where valor_depreciacion_afact >= valor_compra_afact;" +
						" update afi_activo" +
						" set valor_residual_afact = valor_compra_afact - valor_depreciacion_afact");
				
				utilitario.agregarMensaje("Valoración", "Se ejecuto la valoracion con éxito");
				//utilitario.getConexion().consultar("select avalactivos('"+fecha+"')");
				dia_fecha.cerrar();
				tab_depreciacion.setSql("select a.ide_afact,detalle_afact,marca_afact,serie_afact,modelo_afact,fecha_alta_afact,valor_compra_afact,vida_util_afact,"
						+" fecha_calculo_afact,valor_depre_mes_afact,val_depreciacion_periodo_afact,valor_depreciacion_afact"
						+" from afi_activo a"
						+" where not fecha_alta_afact is null "
						+" order by fecha_alta_afact desc");

				tab_depreciacion.ejecutarSql();
				utilitario.addUpdate("tab_depreciacion");
				utilitario.agregarMensaje("Guardado", "Proceso de Valoración realizado con exito");				
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

	
	public Tabla getTab_depreciacion() {
		return tab_depreciacion;
	}

	public void setTab_depreciacion(Tabla tab_depreciacion) {
		this.tab_depreciacion = tab_depreciacion;
	}

	public Dialogo getDia_fecha() {
		return dia_fecha;
	}

	public void setDia_fecha(Dialogo dia_fecha) {
		this.dia_fecha = dia_fecha;
	}

	public Tabla getTab_fecha() {
		return tab_fecha;
	}

	public void setTab_fecha(Tabla tab_fecha) {
		this.tab_fecha = tab_fecha;
	}
	
	

}
