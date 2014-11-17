/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_contabilidad;

import javax.ejb.EJB;

import paq_general.ejb.ServicioGeneral;
import paq_gestion.ejb.ServicioGestion;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Arbol;
import framework.componentes.Boton;
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;


public class pre_convenios extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Tabla tab_tabla3 = new Tabla();

    private Arbol arb_arbol = new Arbol();
    
  private SeleccionTabla set_tipo_persona=new SeleccionTabla();
  @EJB
	private ServicioGeneral ser_general = (ServicioGeneral ) utilitario.instanciarEJB(ServicioGeneral.class);
  

    public pre_convenios() {
    	Tabulador tab_tabulador = new Tabulador();
        tab_tabulador.setId("tab_tabulador");

        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("cont_convenio", "ide_cocon", 1);
        tab_tabla1.setTipoFormulario(true);  //formulario 
        tab_tabla1.getGrid().setColumns(4); //hacer  columnas 
        tab_tabla1.setCampoPadre("con_ide_cocon");
        tab_tabla1.setCampoNombre("detalle_contrato_cocon");
        tab_tabla1.getColumna("ide_cotie").setCombo("cont_tipo_convenio","ide_cotie","detalle_cotie","");
		tab_tabla1.getColumna("ide_geins").setCombo("gen_institucion","ide_geins","detalle_geins","");
		tab_tabla1.getColumna("ide_coest").setCombo("cont_estado","ide_coest","detalle_coest","");
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.agregarRelacion(tab_tabla3);
        tab_tabla1.agregarArbol(arb_arbol);
        tab_tabla1.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);

        arb_arbol.setId("arb_arbol");
        arb_arbol.dibujar();

        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setIdCompleto("tab_tabulador:tab_tabla2");
        tab_tabla2.setTipoFormulario(true);  //formulario
        tab_tabla2.getGrid().setColumns(4);
        tab_tabla2.setTabla("cont_responsable_convenio", "ide_corec", 2);
        tab_tabla2.getColumna("ide_cocon").setCombo("cont_convenio", "ide_cocon", "detalle_contrato_cocon", "");
        tab_tabla2.getColumna("ide_getip").setCombo("gen_tipo_persona", "ide_getip", "detalle_getip", "");
     	tab_tabla2.getColumna("ide_gecaf").setCombo("gen_cargo_funcional", "ide_gecaf", "detalle_gecaf", "");
     	//tab_tabla2.getColumna("ide_geedp").setCombo("gen_empleados_departamento_par", "ide_geedp", "", "");
        tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);

        tab_tabla3.setId("tab_tabla3");
        tab_tabla3.setIdCompleto("tab_tabulador:tab_tabla3");
        tab_tabla3.setTipoFormulario(true);;  //formulario 
        tab_tabla3.getGrid().setColumns(4);
        tab_tabla3.setTabla("cont_archivo_convenio", "ide_coarc", 3);
        tab_tabla3.getColumna("foto_coarc").setUpload("tabla3");
        //tab_tabla3.getColumna("foto_coarc").setValorDefecto("Cargar Archivo");
        tab_tabla3.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_tabla3);

        

        tab_tabulador.agregarTab("RESPONSABLE FIRMA CONVENIO",pat_panel2);
        tab_tabulador.agregarTab("ARCHIVO ANEXO CONVENIO", pat_panel3);
       

        Division div3 = new Division(); //UNE OPCION Y DIV 2
        div3.dividir2(pat_panel1, tab_tabulador, "60%", "H");
        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir2(arb_arbol, div3, "21%", "V");  //arbol y div3
        agregarComponente(div_division);
        
        Boton bot_agregar = new Boton();
        bot_agregar.setValue("Agregar Responsable Convenio");
        bot_agregar.setMetodo("agregarResponsableConvenio");
        bar_botones.agregarBoton(bot_agregar);
        
        set_tipo_persona.setId("set_tipo_persona");
        set_tipo_persona.setTitle("Tipo Persona");
        //set_tipo_persona.setTab_seleccion(ser_general.getTablaTipoPersona("true"));
        
		//agregarComponente(set_tipo_persona);
    }

    @Override
    public void insertar() {
        utilitario.getTablaisFocus().insertar();
    }

    @Override
    public void guardar() {
        if (tab_tabla1.guardar()) {
            if (tab_tabla2.guardar()) {
                if (tab_tabla3.guardar()) {
                    
                }
            }
        }
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public Arbol getArb_arbol() {
        return arb_arbol;
    }

    public void setArb_arbol(Arbol arb_arbol) {
        this.arb_arbol = arb_arbol;
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

    public Tabla getTab_tabla3() {
        return tab_tabla3;
    }

    public void setTab_tabla3(Tabla tab_tabla3) {
        this.tab_tabla3 = tab_tabla3;
    }

  
}
