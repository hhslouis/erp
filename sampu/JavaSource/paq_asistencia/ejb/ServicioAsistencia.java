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

	public String getSqlConsultaVacacion(String ide_aspvh){
		String str="select ide_asvac,ide_gtemp,fecha_ingreso_asvac,activo_asvac from  asi_vacacion where ide_gtemp in (select ide_gtemp from asi_permisos_vacacion_hext where ide_aspvh="+ide_aspvh+") and activo_asvac =true";
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
	public String getConsultaAsistencia(String fecha_inicial,String fecha_final){
		String tab_asi_vacacion="select a.ide_aspvh,a.ide_asmot,a.ide_gtemp,a.ide_geedp,documento_identidad_gtemp,apellido_paterno_gtemp,apellido_materno_gtemp,primer_nombre_gtemp,segundo_nombre_gtemp,"
				+" titulo_cargo_gepgc,nom_sucu,detalle_geare,detalle_gttem,fecha_solicitud_aspvh,(case when aprobado_tthh_aspvh is true then 'Aprobado' else 'No Aprobado' end) as aprobado_talento_humano,"
				+" fecha_desde_aspvh,fecha_hasta_aspvh,detalle_aspvh,nro_dias_aspvh,tipo_aspvh,(case when tipo_aspvh=1 then 'Permisos Horas' when tipo_aspvh = 2 then 'Vacaciones' when tipo_aspvh =3 then 'Horas Extras' when tipo_aspvh =4 then 'Permisos por Dias'  end) as tipo_solicitud,"
				+" nro_documento_aspvh,nro_horas_aspvh,hora_desde_aspvh,hora_hasta_aspvh,aprobado_tthh_aspvh,"
				+" razon_anula_aspvh,documento_anula_aspvh,fecha_anula_aspvh"
				+" from asi_permisos_vacacion_hext a"
				+" left join asi_motivo b on a.ide_asmot = b.ide_asmot"
				+" left join gth_empleado c on a.ide_gtemp = c.ide_gtemp"
				+" left join gen_empleados_departamento_par d on a.ide_geedp = d.ide_geedp"
				+" left join gen_partida_grupo_cargo e on d.ide_gepgc = e.ide_gepgc"
				+" left join gen_cargo_funcional f on d.ide_gecaf = f.ide_gecaf"
				+" left join gen_area g on d.ide_geare = g.ide_geare"
				+" left join gth_tipo_empleado h on d.ide_gttem = h.ide_gttem left join sis_sucursal i on e.ide_sucu = i.ide_sucu"
				+" where fecha_solicitud_aspvh between '"+fecha_inicial+"' and '"+fecha_final+"' "
				+" order by detalle_gttem,apellido_paterno_gtemp,fecha_solicitud_aspvh desc"; 
		return tab_asi_vacacion;
	}
}
