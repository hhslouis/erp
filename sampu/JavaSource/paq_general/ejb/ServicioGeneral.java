package paq_general.ejb;

import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;
import paq_general.ejb.ServicioGeneral;
import paq_sistema.aplicacion.Utilitario;

@Stateless
public  class ServicioGeneral  {
	
	private Utilitario utilitario= new Utilitario();
	/**
	 * Metodo que devuelve los Tipo Persona por Modulo.
	 * @param estado recibe el estado Activo=true, Inactivo=false. puede ingresar los dos estados.
	 * @param modulo recibe el modulo para filtrar los Tipos de Persona, recibe un solo valor para modulo, ejemplo 1= ACtivos Fijos, 2=Convenis, etc.
	 * @return string SQL Tipo Persona
	 */
	public String getTipoPersona(String estado,String  modulo){
		
		String tab_tipo_persona= "select a.ide_getip,detalle_getip from gen_tipo_persona a, gen_tipo_persona_modulo b" +
				" where a.ide_getip=b.ide_getip and ide_gemod in ( "+modulo+") and activo_getpm in ("+estado+") order by detalle_getip";
		
		 return tab_tipo_persona;
		
	}
	/**
	 * Metodo que devuelve los Tipo Persona por Modulo.
	 * @param estado recibe el estado Activo=true, Inactivo=false. puede ingresar los dos estados.
	 * @param modulo recibe el modulo para filtrar los Tipos de Persona, recibe un solo valor para modulo, ejemplo 1= ACtivos Fijos, 2=Convenis, etc.
	 * @return Tabla Tipo Persona.
	 */
	public TablaGenerica getTablaTipoPersona(String estado,String modulo){
		
		TablaGenerica tab_tipo_persona=utilitario.consultar("select a.ide_getip,detalle_getip from gen_tipo_persona a, gen_tipo_persona_modulo b" +
				" where a.ide_getip=b.ide_getip and ide_gemod in ("+modulo+") and activo_getpm in ("+estado+") order by detalle_getip");
		 return tab_tipo_persona;
		 
	}
	
	
	

}
