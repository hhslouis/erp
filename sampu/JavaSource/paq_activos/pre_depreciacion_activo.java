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
				//utilitario.addUpdateTabla(tab_activos_fijos, "vida_util_afact,fecha_calculo_afact,valor_depre_mes_afact,val_depreciacion_periodo_afact,valor_depreciacion_afact,valor_residual_afact", "");
				
			}

	

	
	
	/*long ide_inicial=0;
	public void  aceptarDialogoCustodio(){
		String str_seleccionados=tab_depreciacion.getFilasSeleccionadas();
		TablaGenerica tab_consulta_custodio= ser_activos.getTablaGenericaConsultaCustodio(str_seleccionados);
		utilitario.getConexion().ejecutarSql("DELETE from SIS_BLOQUEO where upper(TABLA_BLOQ) like 'afi_custodio'");
		ide_inicial=utilitario.getConexion().getMaximo("afi_custodio", "ide_afcus", 1);
		for(int i=0;i<tab_consulta_custodio.getTotalFilas();i++){
			
					utilitario.getConexion().ejecutarSql("update afi_custodio set fecha_descargo_afcus= '"+tab_tarspaso_Custodio.getValor("fecha_descargo_afcus")+"' ,"
					+" razon_descargo_afcus= '"+tab_tarspaso_Custodio.getValor("razon_descargo_afcus")+"' ,"
					+" activo_afcus=false where ide_afcus="+tab_consulta_custodio.getValor(i, "ide_afcus"));

					utilitario.getConexion().ejecutarSql("insert into afi_custodio (ide_afcus,ide_afact,ide_geedp,detalle_afcus,fecha_entrega_afcus,numero_acta_afcus,cod_barra_afcus,nro_secuencial_afcus,activo_afcus,gen_ide_geedp)"
					+" values ( "+ide_inicial+","+tab_consulta_custodio.getValor(i, "ide_afact")+", "+tab_tarspaso_Custodio.getValor("ide_geedp")+",'"+tab_consulta_custodio.getValor(i, "detalle_afcus")+"','"
					+tab_tarspaso_Custodio.getValor("fecha_entrega_afcus")+"','"+tab_tarspaso_Custodio.getValor("numero_acta_afcus")+"','"+tab_consulta_custodio.getValor(i, "cod_barra_afcus")+"',"+tab_consulta_custodio.getValor(i, "nro_secuencial_afcus")
					+",true,"+tab_consulta_custodio.getValor(i,"ide_geedp")+" )");
			
			ide_inicial++;
		}
		dia_fecha.cerrar();
		tab_depreciacion.setSql("update afi_activo" +
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
						" set valor_residual_afact = valor_compra_afact - valor_depreciacion_afact;");
				
		tab_depreciacion.ejecutarSql();
		utilitario.addUpdate("tab_traspaso");
		utilitario.agregarMensaje("Guardado", "Cambio de custodio realizado con exito");
		}
/*	public void abrirDialogoCustodio(){
		//Hace aparecer el componente
		if(aut_empleado.getValor()!=null){
			tab_tarspaso_Custodio.limpiar();
			tab_tarspaso_Custodio.insertar();
			//tab_direccion.limpiar();
		//	tab_direccion.insertar();
			dia_traspaso_custodio.dibujar();
		}
		else{
			utilitario.agregarMensaje("Inserte un Custodio", "");
		}

	}

/**DFJ**/
/*public void seleccionarTodas() {
        tab_depreciacion.setSeleccionados(null);
        Fila seleccionados[] = new Fila[tab_depreciacion.getTotalFilas()];
        for (int i = 0; i < tab_depreciacion.getFilas().size(); i++) {
            seleccionados[i] = tab_depreciacion.getFilas().get(i);
        }
        tab_depreciacion.setSeleccionados(seleccionados);
}

/**DFJ**/
/*public void seleccinarInversa() {
        if (tab_depreciacion.getSeleccionados() == null) {
            seleccionarTodas();
        } else if (tab_depreciacion.getSeleccionados().length == tab_depreciacion.getTotalFilas()) {
            seleccionarNinguna();
        } else {
            Fila seleccionados[] = new Fila[tab_depreciacion.getTotalFilas() - tab_depreciacion.getSeleccionados().length];
            int cont = 0;
            for (int i = 0; i < tab_depreciacion.getFilas().size(); i++) {
                boolean boo_selecionado = false;
                for (int j = 0; j < tab_depreciacion.getSeleccionados().length; j++) {
                    if (tab_depreciacion.getSeleccionados()[j].equals(tab_depreciacion.getFilas().get(i))) {
                        boo_selecionado = true;
                        break;
                    }
                }
                if (boo_selecionado == false) {
                    seleccionados[cont] = tab_depreciacion.getFilas().get(i);
                    cont++;
                }
            }
            tab_depreciacion.setSeleccionados(seleccionados);
        }
    }

/**DFJ**/
/*public void seleccionarNinguna() {
	tab_depreciacion.setSeleccionados(null);
    }




	public void filtrarCustodio(SelectEvent evt){
		tab_depreciacion.setSql("select b.ide_afcus,detalle_afact,serie_afact,modelo_afact,marca_afact,cod_barra_afcus,numero_acta_afcus," +
				 "fecha_entrega_afcus,apellido_paterno_gtemp,apellido_materno_gtemp,primer_nombre_gtemp,segundo_nombre_gtemp " +
				 "from afi_activo a,afi_custodio b, gen_empleados_departamento_par c, gth_empleado d where a.ide_afact=b.ide_afact " + 
				 " and b.ide_geedp=c.ide_geedp and c.ide_geedp="+aut_empleado.getValor()+" and c.ide_gtemp=d.ide_gtemp and activo_afcus=true order by fecha_entrega_afcus desc");

		tab_depreciacion.ejecutarSql();
		utilitario.addUpdate("tab_traspaso");

		

	}
	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	/*public void limpiar() {
		aut_empleado.limpiar();
		utilitario.addUpdate("aut_empleado");// limpia y refresca el autocompletar


	}*/
	
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
