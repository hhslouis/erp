package paq_contabilidad.ejb;

import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;
import paq_sistema.aplicacion.Utilitario;
@Stateless

public class ServicioContabiliadad {
	
	private Utilitario utilitario=new Utilitario();
	
	public String getVigente (String codigo){
		
		String tab_vigente="SELECT tc.constraint_name, " +
				"tc.table_name, kcu.column_name," +
				" ccu.table_name AS foreign_table_name," +
				"ccu.column_name AS foreign_column_name " +
				"FROM information_schema.table_constraints AS tc " +
				"JOIN information_schema.key_column_usage AS kcu " +
				"ON tc.constraint_name = kcu.constraint_name " +
				"JOIN information_schema.constraint_column_usage AS ccu " +
				"ON ccu.constraint_name = tc.constraint_name " +
				"WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name='cont_vigente'" +"codigo";
				
		return tab_vigente;
		
	}
public TablaGenerica getTablaVigente (String tabla){
	
	TablaGenerica tab_vigente=utilitario.consultar("SELECT tc.constraint_name, " +
			"tc.table_name, kcu.column_name, " +
			" ccu.table_name AS foreign_table_name, " +
			"ccu.column_name AS foreign_column_name " +
			"FROM information_schema.table_constraints AS tc " +
			"JOIN information_schema.key_column_usage AS kcu " +
			"ON tc.constraint_name = kcu.constraint_name " +
			"JOIN information_schema.constraint_column_usage AS ccu " +
			"ON ccu.constraint_name = tc.constraint_name " +
			"WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name='"+tabla+"'");
	return tab_vigente;
	
	
}
}
