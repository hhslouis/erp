/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_salud;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;

import paq_gestion.ejb.ServicioGestion;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;

/**
 *
 * @author Diego
 */
public class pre_certificado_externo extends Pantalla {

	private Tabla tab_tabla1 = new Tabla();
	private Tabla tab_tabla2 = new Tabla();
	private AutoCompletar aut_empleado =new AutoCompletar();
	@EJB
	private ServicioGestion ser_gestion = (ServicioGestion) utilitario.instanciarEJB(ServicioGestion.class);

	public pre_certificado_externo() {
		// boton limpiar
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");

		// autocompletar empleado
		aut_empleado.setId("aut_empleado");

		String str_sql_emp=ser_gestion.getSqlEmpleadosAutocompletar();
		aut_empleado.setAutoCompletar(str_sql_emp);
		aut_empleado.setMetodoChange("filtrarEmpleado");
		Etiqueta eti_colaborador=new Etiqueta("Empleado:");
		bar_botones.agregarComponente(eti_colaborador);
		bar_botones.agregarComponente(aut_empleado);
		bar_botones.agregarBoton(bot_limpiar);
		tab_tabla1.setId("tab_tabla1");
		tab_tabla1.setTabla("SAO_CERTIFICADO_EXTERNO","IDE_SACEE", 1);
		tab_tabla1.setCondicion("IDE_SACEE=-1");
		tab_tabla1.getColumna("IDE_GEEDP").setVisible(false);
		tab_tabla1.getColumna("IDE_GTEMP").setVisible(false);
		tab_tabla1.getColumna("ACTIVO_SACEE").setCheck();
		tab_tabla1.getColumna("ACTIVO_SACEE").setValorDefecto("true");
		tab_tabla1.agregarRelacion(tab_tabla2);
		tab_tabla1.dibujar();
		PanelTabla pat_panel1 = new PanelTabla();
		pat_panel1.setPanelTabla(tab_tabla1);
		tab_tabla2.setId("tab_tabla2");
		tab_tabla2.setTabla("SAO_DETALLE_CERT_EXTERNO","IDE_SADCE", 2);
		tab_tabla1.setCondicion("IDE_SADCE=-1");
		tab_tabla2.getColumna("IDE_SACET").setCombo("SAO_CERTIFCADO_TIPO", "IDE_SACET","DETALLE_SACET", "");
		tab_tabla2.getColumna("ARCHIVO_SADCE").setUpload("logos");
       	tab_tabla2.getColumna("ARCHIVO_SADCE").setImagen("128", "128");
		tab_tabla2.getColumna("ACTIVO_SADCE").setCheck();
		tab_tabla2.getColumna("ACTIVO_SADCE").setValorDefecto("true");
		tab_tabla2.dibujar();
		PanelTabla pat_panel2 = new PanelTabla();
		pat_panel2.setPanelTabla(tab_tabla2);
		Division div_division = new Division();
		div_division.setId("div_division");
		div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");
		agregarComponente(div_division);
	}
	String ide_geedp_activo="";
	public void filtrarEmpleado(SelectEvent evt){
		aut_empleado.onSelect(evt);
		ide_geedp_activo=ser_gestion.getIdeContratoActivo(aut_empleado.getValor());
		tab_tabla1.setCondicion("IDE_GEEDP="+ide_geedp_activo);
		tab_tabla1.ejecutarSql();
		System.out.println("empleado"+aut_empleado.getValor());
		System.out.println("empleado"+ide_geedp_activo);

	}

	@Override
	public void insertar() {
		if (aut_empleado.getValor()!=null) {
			if(tab_tabla1.isFocus()){
				tab_tabla1.insertar();
				tab_tabla1.setValor("IDE_GEEDP",ide_geedp_activo);
				tab_tabla1.setValor("IDE_GTEMP",aut_empleado.getValor());
			}
			if(tab_tabla2.isFocus()){
				tab_tabla2.insertar();
			}
		}
		else {
			utilitario.agregarMensajeInfo("No se puede insertar", "Debe seleccionar el Empleado que solicita Certificado Externo");
		}

	}

	@Override
	public void guardar() {
		if (tab_tabla1.guardar()) {
			if (tab_tabla2.guardar()) {
				guardarPantalla();
			}
		}
	}
	public void limpiar() {
		aut_empleado.limpiar();
		tab_tabla1.limpiar();
		tab_tabla2.limpiar();
		utilitario.addUpdate("aut_empleado,tab_tabla1,tab_tabla2");
	}
	@Override
	public void eliminar() {
		utilitario.getTablaisFocus().eliminar();

	}

	public Tabla getTab_tabla1() {
		return tab_tabla1;
	}

	public void setTab_tabla1(Tabla tab_tabla1) {
		this.tab_tabla1 = tab_tabla1;
	}

	public Tabla getTab_tabla2() {
		return tab_tabla2;
	}

	public void setTab_tabla2(Tabla tab_tabla2) {
		this.tab_tabla2 = tab_tabla2;
	}

	public AutoCompletar getAut_empleado() {
		return aut_empleado;
	}

	public void setAut_empleado(AutoCompletar aut_empleado) {
		this.aut_empleado = aut_empleado;
	}


}
