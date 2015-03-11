package paq_asistencia.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.primefaces.component.tabview.Tab;

import framework.aplicacion.TablaGenerica;
import framework.componentes.Tabla;


import paq_sistema.aplicacion.Utilitario;

@Stateless
public class ServicioAsistencia {
	private Utilitario utilitario=new Utilitario();

	
	public TablaGenerica getNovedadJustificacion(String columna_busqueda,String ide_a_buscar){
		TablaGenerica tab_nove_jus=new TablaGenerica();
		try {
			tab_nove_jus=utilitario.consultar("select * from ASI_NOVEDAD_JUSTIFICACION where "+columna_busqueda+"="+ide_a_buscar);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tab_nove_jus;
	}
	
	public boolean desactivarPeriodoVacacion(String ide_asvac,String fecha_finiquito){
		if (utilitario.getConexion().ejecutarSql("update ASI_VACACION set ACTIVO_ASVAC=false, " +
				"fecha_finiquito_asvac=to_date('"+fecha_finiquito+"','yyyy-mm-dd') where IDE_ASVAC="+ide_asvac).isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	public String getSqlActivarPeriodoVacacion(String ide_asvac){
		String str="update ASI_VACACION set ACTIVO_ASVAC=true where IDE_ASVAC="+ide_asvac;
		return str;
	}
	
	public boolean activarPeriodoVacacion(String ide_asvac){
		if (utilitario.getConexion().ejecutarSql("update ASI_VACACION set ACTIVO_ASVAC=true where IDE_ASVAC="+ide_asvac).isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public void crearPeriodoVacacion(String ide_gtemp,String fecha_ingreso){
		Tabla tab_asi_vac=new Tabla();
		tab_asi_vac.setTabla("ASI_VACACION", "IDE_ASVAC", -1);
		tab_asi_vac.setCondicion("IDE_ASVAC=-1");
		tab_asi_vac.ejecutarSql();
		
		tab_asi_vac.insertar();
		tab_asi_vac.setValor("IDE_GTEMP", ide_gtemp);
		tab_asi_vac.setValor("FECHA_INGRESO_ASVAC", fecha_ingreso);
		tab_asi_vac.setValor("ACTIVO_ASVAC", "true");
		tab_asi_vac.guardar();
		
//		if (utilitario.getConexion().guardarPantalla().isEmpty()){
//			return true;
//		}else{
//			return false;
//		}
		
	}
	
	public TablaGenerica getAsiVacacionActiva(String ide_gtemp){
		TablaGenerica tab_asi_vacacion=utilitario.consultar("select * from ASI_VACACION " +
				"where activo_asvac=true and IDE_GTEMP="+ide_gtemp);
		return tab_asi_vacacion;
	}
	public TablaGenerica getAsiVacacionMaximoPeriodo(String ide_gtemp){
		TablaGenerica tab_asi_vacacion=utilitario.consultar("select * from ( " +
				"select * from ASI_VACACION where IDE_GTEMP="+ide_gtemp+" " +
				"order by FECHA_INGRESO_ASVAC DESC " +
				")a " +
				" limit 1"); 
		return tab_asi_vacacion;
	}

}
