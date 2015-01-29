package paq_bodega.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import framework.aplicacion.TablaGenerica;

import paq_sistema.aplicacion.Utilitario;

/**
 * Session Bean implementation class ServicioBodega
 */
@Stateless
@LocalBean
public class ServicioBodega {

	/**
	 * Default constructor. 
	 */
	private Utilitario utilitario=new Utilitario();
	public TablaGenerica getTablaInventario (String codigo){

		TablaGenerica tab_inventario = utilitario.consultar("select ide_bomat, codigo_bomat,detalle_bomat " +
				" from bodt_material " +
				" WHERE ide_bomat in ("+codigo+") " +
				" ORDER BY detalle_bomat");
		return tab_inventario;

	}
	/**
	 * Este servicio retorna los materiales existentes en el cálogo de materiales 
	 * @param activo
	 * @return
	 */
	public String getInventario (String grupo,String activo,String ide_botip){

		String tab_inventario="select ide_bomat, codigo_bomat,detalle_bomat,detalle_bogrm " +
				"from bodt_material a,bodt_grupo_material b" +
				" WHERE activo_bomat in ("+activo+") AND a.ide_bogrm = b.ide_bogrm ";
		if(grupo.equals("0")){
		       tab_inventario +=" AND a.ide_bogrm = b.ide_bogrm AND a.ide_botip in("+ide_botip+") "	;
		}
		
		        tab_inventario +="ORDER BY detalle_bogrm,detalle_bomat";
		        
		System.out.println("consluat "+tab_inventario);


		return tab_inventario;


	}

}
