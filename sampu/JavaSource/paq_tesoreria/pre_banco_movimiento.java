/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_tesoreria;

import org.primefaces.event.SelectEvent;

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
public class pre_banco_movimiento extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private AutoCompletar aut_movimniento =new AutoCompletar();

    public pre_banco_movimiento() {
    	
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
    	aut_movimniento.setId("aut_movimniento");

    	aut_movimniento.setAutoCompletar("select a.ide_tebac,a.nro_cuenta_tebac,b.detalle_teban,detalle_gttcb  " +
    			"from tes_banco_cuenta a " +
    			"left join tes_banco b on b.ide_teban=a.ide_teban " +
    			"left join gth_tipo_cuenta_bancaria c on c.ide_gttcb=a.ide_gttcb " +
    			"order by a.ide_tebac desc");
    	aut_movimniento.setMetodoChange("Carga");
    	
    	bar_botones.agregarComponente(new Etiqueta("Seleccione: "));
    	bar_botones.agregarComponente(aut_movimniento);
    	bar_botones.agregarBoton(bot_limpiar);
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("tes_banco_anio", "ide_tebaa",1);
        
        tab_tabla1.getColumna("ide_geani").setCombo("GEN_ANIO", "ide_geani", "detalle_geani", "");
        tab_tabla1.getColumna("ide_cocac").setCombo("select ide_cocac,cue_codigo_cocac,cue_descripcion_cocac  " +
        		"from cont_catalogo_cuenta " +
        		"where activo_cocac=true");
        tab_tabla1.getColumna("ide_cocac").setAutoCompletar();
        //tab_tabla1.getColumna("ide_tebac").setVisible(false);
        tab_tabla1.getColumna("ACTIVO_tebaa").setValorDefecto("true"); 
		tab_tabla1.setCondicion("ide_tebaa=-1");
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("pre_banco_movimiento", "ide_tebam",2);


        tab_tabla2.getColumna("ACTIVO_tebam").setValorDefecto("true"); 
            tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);
        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");
        agregarComponente(div_division);
    }
    
	public void limpiar(){
		aut_movimniento.limpiar();
		tab_tabla1.limpiar();
		tab_tabla2.limpiar();
		utilitario.addUpdate("aut_movimniento");
	}
    public void Carga(SelectEvent evt){
    	System.out.println("aut_movimiento "+aut_movimniento.getValor());
    	aut_movimniento.onSelect(evt);
    		tab_tabla1.setCondicion("IDE_TEBAC="+aut_movimniento.getValor());
        	tab_tabla1.ejecutarSql();
      
    	
    
    }

    @Override
    public void insertar() {
    	if(aut_movimniento.getValor()!=null){
    		tab_tabla1.setCondicion("ide_tebac="+aut_movimniento.getValor());
        utilitario.getTablaisFocus().insertar();
    	 }
    	else
       	utilitario.agregarMensajeInfo("Debe seleccionar un una cuenta banco", "");
    		
    }

    @Override
    public void guardar() {

        if (tab_tabla1.guardar()) {
            if (tab_tabla2.guardar()) {
                guardarPantalla();
            }
        }
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

	public AutoCompletar getAut_movimniento() {
		return aut_movimniento;
	}

	public void setAut_movimniento(AutoCompletar aut_movimniento) {
		this.aut_movimniento = aut_movimniento;
	}
    
    
}
