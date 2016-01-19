package paq_contabilidad;

import javax.ejb.EJB;

import framework.componentes.Arbol;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import paq_contabilidad.ejb.ServicioContabilidad;
import paq_sistema.aplicacion.Pantalla;

public class pre_estado_resultado extends Pantalla{
	
	private Tabla tab_estado_resultado = new Tabla();
	private Arbol arb_arbol = new Arbol();
	private Combo com_anio=new Combo();

	@EJB
	private ServicioContabilidad ser_contabilidad = (ServicioContabilidad ) utilitario.instanciarEJB(ServicioContabilidad.class);

	public pre_estado_resultado (){
		com_anio.setCombo(ser_contabilidad.getAnioDetalle("true,false","true,false"));		
		com_anio.setMetodo("seleccionaElAnio");
		bar_botones.agregarComponente(new Etiqueta("Seleccione El Año:"));
		bar_botones.agregarComponente(com_anio);

		tab_estado_resultado.setId("tab_estado_resultado");
		tab_estado_resultado.setHeader("ESTADO DE RESULTADOS");
		tab_estado_resultado.setTabla("cont_estado_resultado", "ide_coesr", 1);
		tab_estado_resultado.agregarArbol(arb_arbol);
		tab_estado_resultado.setCampoPadre("con_ide_coesr"); //necesario para el arbol
		tab_estado_resultado.setCampoNombre("detalle_cuenta_coesr");//necesario pa el arbol
		tab_estado_resultado.dibujar();
		PanelTabla pat_resultado = new PanelTabla();
		pat_resultado.setPanelTabla(tab_estado_resultado);
		
		
		
		/////// arbol
		
		arb_arbol.setId("arb_arbol");
		arb_arbol.dibujar();

		
		
		
		Division div_division = new Division(); //UNE OPCION 
		div_division.setId("div_division");
		div_division.dividir2(arb_arbol, pat_resultado, "40%", "V");  //arbol y diV_division
		agregarComponente(div_division);
		
		
		
	}
	public void seleccionaElAnio (){
		if(com_anio.getValue()==null){
			utilitario.agregarMensajeInfo("Selecione un Año", "");
			return;			

		}
		
		String sql="update rep_estado_flujo_efectivo"
+" set valor_rpefe=0,"
+" total_rpefe=0,"
+" subtotal_rpefe=0,"
+" total_general_rpefe=0;"
+" update rep_estado_flujo_efectivo"
+" set valor_rpefe = haber "
+" from ("
+" select ide_cobac, ide_geani, a.ide_cocac,cue_codigo_cocac,cue_descripcion_cocac, (debe1_cobac+debe2_cobac+debe3_cobac+debe4_cobac+debe5_cobac+debe6_cobac+debe7_cobac+"
+" debe8_cobac+debe9_cobac+debe10_cobac+debe11_cobac+debe12_cobac )+ ("
+" haber1_cobac+haber2_cobac+haber3_cobac+debe4_cobac+haber4_cobac+haber5_cobac+haber6_cobac+haber7_cobac+haber8_cobac+haber9_cobac+"
+" haber10_cobac+haber11_cobac+haber12_cobac ) as haber"
+" from cont_balance_comprobacion a, cont_catalogo_cuenta b"
+" where a.ide_cocac = b.ide_cocac and a.ide_geani="+com_anio.getValue().toString()
+" order by cue_codigo_cocac"
+" ) a where codigo_rpefe = cue_codigo_cocac;"
+" update rep_estado_flujo_efectivo"
+" set total_rpefe=total"
+" from ("
+" select sum(valor_rpefe) as total,codigo_grupo_rpefe"
+" from rep_estado_flujo_efectivo group by codigo_grupo_rpefe"
+" ) a where a.codigo_grupo_rpefe = rep_estado_flujo_efectivo.codigo_grupo_rpefe;"
+" update rep_estado_flujo_efectivo"
+" set subtotal_rpefe=total"
+" from ("
+" select sum(valor_rpefe) as total,grupo_general_rpefe"
+" from rep_estado_flujo_efectivo group by grupo_general_rpefe"
+" ) a where a.grupo_general_rpefe = rep_estado_flujo_efectivo.grupo_general_rpefe;"
+" update rep_estado_flujo_efectivo"
+" set total_general_rpefe=total"
+" from ("
+" select sum(valor_rpefe) as total"
+" from rep_estado_flujo_efectivo "
+" ) a ;";
		
		utilitario.getConexion().ejecutarSql(sql);
		tab_estado_resultado.ejecutarSql();
			utilitario.addUpdate("tab_estado_resultado");
		
	}
	@Override
	public void insertar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().insertar();

		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		if(tab_estado_resultado.guardar()){
	           
	        	   guardarPantalla();
	            //actualiza el arbol
	            arb_arbol.ejecutarSql();
	            utilitario.addUpdate("arb_arbol");
				   }
	           }
	    				
		
		
	

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		utilitario.getTablaisFocus().eliminar();
		
	}

	public Tabla getTab_estado_resultado() {
		return tab_estado_resultado;
	}

	public void setTab_estado_resultado(Tabla tab_estado_resultado) {
		this.tab_estado_resultado = tab_estado_resultado;
	}

	public Arbol getArb_arbol() {
		return arb_arbol;
	}

	public void setArb_arbol(Arbol arb_arbol) {
		this.arb_arbol = arb_arbol;
	}
	

}
