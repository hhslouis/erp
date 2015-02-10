package paq_activos;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_sistema.aplicacion.Pantalla;

public class pre_activo extends Pantalla {
private Tabla tab_activos_fijos=new Tabla();
private Tabla tab_custodio=new Tabla();


public pre_activo(){

tab_activos_fijos.setId("tab_activos_fijos");
tab_activos_fijos.setTabla("afi_activo","ide_afact", 1);
tab_activos_fijos.getColumna("ide_afubi").setCombo("afi_ubicacion","ide_afubi","detalle_afubi","");
tab_activos_fijos.getColumna("ide_aftia").setCombo("afi_tipo_activo","ide_aftia","detalle_aftia","");
tab_activos_fijos.getColumna("ide_aftip").setCombo("afi_tipo_propiedad","ide_aftip","detalle_aftip","");
tab_activos_fijos.getColumna("ide_afseg").setCombo("afi_seguro","ide_afseg","detalle_afseg","");


tab_activos_fijos.setTipoFormulario(true);
tab_activos_fijos.getGrid().setColumns(4);
tab_activos_fijos.agregarRelacion(tab_custodio);
tab_activos_fijos.dibujar();
PanelTabla pat_activo_fijos=new PanelTabla();
pat_activo_fijos.setPanelTabla(tab_activos_fijos);


tab_custodio.setId("tab_custodio");
tab_custodio.setTabla("afi_custodio","ide_afcus", 2);
tab_custodio.setTipoFormulario(true);
tab_custodio.getGrid().setColumns(4);
tab_custodio.dibujar();
PanelTabla pat_custodio=new PanelTabla();
pat_custodio.setPanelTabla(tab_custodio);

Division div_division=new Division();
div_division.dividir2(pat_activo_fijos,pat_custodio, "50%", "h");
agregarComponente(div_division);



}





@Override
public void insertar() {
// TODO Auto-generated method stub
utilitario.getTablaisFocus().insertar();

}

@Override
public void guardar() {
// TODO Auto-generated method stub
if (tab_activos_fijos.guardar()){
tab_custodio.guardar();
}
guardarPantalla();

}

@Override
public void eliminar() {
// TODO Auto-generated method stub
utilitario.getTablaisFocus().eliminar();

}





public Tabla getTab_activos_fijos() {
	return tab_activos_fijos;
}





public void setTab_activos_fijos(Tabla tab_activos_fijos) {
	this.tab_activos_fijos = tab_activos_fijos;
}





public Tabla getTab_custodio() {
	return tab_custodio;
}





public void setTab_custodio(Tabla tab_custodio) {
	this.tab_custodio = tab_custodio;
}

}

