/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_facturacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import paq_facturacion.ejb.ServicioFacturacion;
import paq_sistema.aplicacion.Pantalla;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Encriptar;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Radio;
import framework.componentes.Tabla;

/**
 *
 * @author HHSLOUIS
 */
public class pre_archivo_banco extends Pantalla {

    
    private Tabla tab_tabla = new Tabla();
	private Combo com_anio=new Combo();
	private Calendario cal_fecha_inicial = new Calendario();
	private Calendario cal_fecha_final = new Calendario();
	private Radio rad_imprimir= new Radio();

	@EJB
	private ServicioFacturacion ser_facturacion = (ServicioFacturacion ) utilitario.instanciarEJB(ServicioFacturacion.class);

    public pre_archivo_banco() { 
    	
    	bar_botones.limpiar();
		com_anio.setCombo(ser_facturacion.getDatosFactura("1",""));		
		com_anio.setMetodo("seleccionaOpcion");
		bar_botones.agregarComponente(new Etiqueta("Seleccione el Servicio:"));
		bar_botones.agregarComponente(com_anio);
		
		
		bar_botones.agregarComponente(new Etiqueta("Fecha Inicial :"));
		cal_fecha_inicial.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_inicial);

		bar_botones.agregarComponente(new Etiqueta("Fecha Final :"));
		cal_fecha_final.setFechaActual();
		bar_botones.agregarComponente(cal_fecha_final);
		
		List lista = new ArrayList();
	       Object fila1[] = {
	           "0", "TODOS"
	       };
	       Object fila2[] = {
	           "1", "INDIVIDUAL"
	       };
	       
	       lista.add(fila1);
	       lista.add(fila2);
	       rad_imprimir.setId("rad_imprimir");
	       rad_imprimir.setRadio(lista);
	       rad_imprimir.setValue(fila2);
	       rad_imprimir.setMetodoChange("seleccionaOpcion");
	       bar_botones.agregarComponente(rad_imprimir);
		
		Boton bot_filtrar = new Boton();
		bot_filtrar.setValue("Actualizar Consulta");
		bot_filtrar.setMetodo("seleccionaOpcion");
		bot_filtrar.setIcon("ui-icon-refresh");
		bar_botones.agregarBoton(bot_filtrar);
		
		
		
		Boton bot_limpiar = new Boton();
		bot_limpiar.setIcon("ui-icon-cancel");
		bot_limpiar.setMetodo("limpiar");
		bar_botones.agregarBoton(bot_limpiar);
		
		Boton bot_generar= new Boton();
		bot_generar.setValue("Generar Archivo Transferencia");
		bot_generar.setMetodo("generarArchivo");
		bot_generar.setAjax(false);
		bar_botones.agregarBoton(bot_generar);
		
        tab_tabla.setId("tab_tabla");
        tab_tabla.setSql("select orden,secuencial,fecha_transaccion_fafac, ruc_comercial_recli,rpad,base_aprobada_fafac, valor_iva_fafac, total_fafac,detalle_bogrm,doc_identidad,"
+" repeat ('0',(15 - length (tot_sin_punto)))||tot_sin_punto as valor_sin_punto,repeat ('0',(15 - length (secuencial)))||secuencial as factura_sin_punto,repeat ('0',(13 - length (ruc_comercial_recli)))||ruc_comercial_recli as ruc_sin_punto"
+" from ("
+" SELECT row_number() over( order by secuencial_fafac) as orden,substring(secuencial_fafac from 9 for 7) as secuencial,fecha_transaccion_fafac, ruc_comercial_recli,rpad(razon_social_recli,30,' ') as rpad,base_aprobada_fafac, valor_iva_fafac, total_fafac,"
+" detalle_bogrm,replace(total_fafac||'','.','') as tot_sin_punto, ( case when ide_gttdi = 1  then 'P' when ide_gttdi= 2 then 'R' when ide_gttdi= 3 then 'C' end) as doc_identidad"
+" FROM fac_factura fac"
+" join rec_clientes cli on cli.ide_recli=fac.ide_recli"
+" join (select ide_fadaf, detalle_bogrm from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm) b on fac.ide_fadaf = b.ide_fadaf"
+" where ide_coest=2 "
+" and activo_fafac=true" 
+" and ide_tetid=4 "
+" and cli.ide_recli=-1" 
+" order by secuencial_fafac"
+" ) a"
+" order by secuencial");
        tab_tabla.getColumna("rpad").setNombreVisual("RAZON SOCIAL");
        tab_tabla.getColumna("detalle_bogrm").setNombreVisual("SERVICIO");
        
        tab_tabla.setLectura(true);
        tab_tabla.setRows(20);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        Division div_division = new Division();
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }
    public void seleccionaOpcion (){
		
		String sql ="select orden,secuencial,fecha_transaccion_fafac, ruc_comercial_recli,rpad,base_aprobada_fafac, valor_iva_fafac, total_fafac,detalle_bogrm,doc_identidad,"
				+" repeat ('0',(15 - length (tot_sin_punto)))||tot_sin_punto as valor_sin_punto,repeat ('0',(15 - length (secuencial)))||secuencial as factura_sin_punto,repeat ('0',(13 - length (ruc_comercial_recli)))||ruc_comercial_recli as ruc_sin_punto"
				+" from ("
				+" SELECT row_number() over( order by secuencial_fafac) as orden,substring(secuencial_fafac from 9 for 7) as secuencial,fecha_transaccion_fafac, ruc_comercial_recli,rpad(razon_social_recli,30,' ') as rpad,base_aprobada_fafac, valor_iva_fafac, total_fafac,"
				+" detalle_bogrm,replace(total_fafac||'','.','') as tot_sin_punto, ( case when ide_gttdi = 1  then 'P' when ide_gttdi= 2 then 'R' when ide_gttdi= 3 then 'C' end) as doc_identidad"
				+" FROM fac_factura fac"
				+" join rec_clientes cli on cli.ide_recli=fac.ide_recli"
				+" join (select ide_fadaf, detalle_bogrm from fac_datos_factura a, bodt_grupo_material b where a.ide_bogrm = b.ide_bogrm) b on fac.ide_fadaf = b.ide_fadaf"
				+" where ide_coest=2 "
				+" and activo_fafac=true "
				+" and ide_tetid=4 ";
				if (rad_imprimir.getValue().equals("1")){
				sql+=" and fac.ide_fadaf ="+com_anio.getValue();	
				}
				sql+=" and fecha_transaccion_fafac between '"+cal_fecha_inicial.getFecha()+"' and '"+cal_fecha_final.getFecha()+"'";
				sql+=" order by secuencial_fafac"
				+" ) a"
				+" order by secuencial";
		System.out.println("actualiza archvo "+sql);
		tab_tabla.setSql(sql);
		tab_tabla.ejecutarSql();
				
		utilitario.addUpdate("tab_tabla");
	}
    /**
	 * Genera el archivo zip que contiene dos archivos el .txt y el .md5
	 */
    
	public void generarArchivo(){		
		if(tab_tabla.getValor("orden")==null){
			utilitario.agregarMensajeInfo("No se puede generar el Archivo", "No existen registros para la generación del archivo");
			return;
		}

		//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
		try {
			//StringBuilder str_spi=new StringBuilder();
			String str_nombre_archivo=utilitario.getVariable("p_fac_nombre_archivo_banco");
			String str_localidad=utilitario.getVariable("p_fac_localidad_archivo_banco");
			String str_transaccion =utilitario.getVariable("p_fac_transaccion_archivo_banco");
			String str_codservicio=utilitario.getVariable("p_fac_codservicio_archivo_banco");
			String str_referencia=utilitario.getVariable("p_fac_referencia_archivo_banco");
			String str_formapago=utilitario.getVariable("p_fac_formapago_archivo_banco");
			String str_moneda=utilitario.getVariable("p_fac_moneda_archivo_banco");
			String str_tipo_cueta="  ";
			String str_numero_cuenta="        ";
			String str_referencia_blanco="   ";
			String str_localidad_cheque="  ";
			String str_agencia_cheque="  ";
			String str_telefono="";
					
			
			ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
			String fileName= str_nombre_archivo+utilitario.getFechaActual(); //utilitario.getNombreMes(utilitario.getMes(str_fecha_hora))+utilitario.getAnio(str_fecha_hora);
			String path= extContext.getRealPath("/");			

			File archivotxt=new File(path+fileName.concat(".txt"));
			BufferedWriter escribir=new BufferedWriter(new FileWriter(archivotxt)); 
	
			tab_tabla.ejecutarSql();			
			for(int i=0;i<tab_tabla.getTotalFilas();i++){
		//		str_spi.append("\r\n");
				StringBuilder str_detalle=new StringBuilder();
				str_detalle.append(str_localidad);			
				str_detalle.append(str_transaccion);
				str_detalle.append(str_codservicio);
				str_detalle.append(str_tipo_cueta);
				str_detalle.append(str_numero_cuenta);
				str_detalle.append(tab_tabla.getValor(i, "valor_sin_punto"));
				str_detalle.append(tab_tabla.getValor(i, "factura_sin_punto"));
				str_detalle.append(str_referencia).append(str_referencia_blanco);
				str_detalle.append(str_formapago);
				str_detalle.append(str_moneda);
				str_detalle.append(tab_tabla.getValor(i, "rpad"));
				str_detalle.append(str_localidad_cheque);
				str_detalle.append(str_agencia_cheque);
				str_detalle.append(tab_tabla.getValor(i, "doc_identidad"));
				str_detalle.append(tab_tabla.getValor(i, "ruc_sin_punto"));
				str_detalle.append(str_telefono);


				String str_spi_encr="";
				try {
					 str_spi_encr=str_detalle.toString();
					str_spi_encr=str_spi_encr.replaceAll("Á", "A");
					str_spi_encr=str_spi_encr.replaceAll("É", "E");
					str_spi_encr=str_spi_encr.replaceAll("Í", "I");
					str_spi_encr=str_spi_encr.replaceAll("Ó", "O");
					str_spi_encr=str_spi_encr.replaceAll("Ú", "U");
					str_spi_encr=str_spi_encr.replaceAll("á", "a");
					str_spi_encr=str_spi_encr.replaceAll("é", "e");
					str_spi_encr=str_spi_encr.replaceAll("í", "i");
					str_spi_encr=str_spi_encr.replaceAll("ó", "o");
					str_spi_encr=str_spi_encr.replaceAll("ú", "u");
					str_spi_encr=str_spi_encr.replaceAll("Ñ", "N");
					str_spi_encr=str_spi_encr.replaceAll("ñ", "n");
				} catch (Exception e) {
					// TODO: handle exception
				}				
				//str_spi.append(str_spi_encr);
				escribir.write(str_spi_encr.toString());
				escribir.newLine();

			}
			escribir.close();
			String str_spi_encr="";
			BufferedReader entrada;
			try {
			entrada = new BufferedReader( new FileReader( archivotxt ) );
			String linea;
			while(entrada.ready()){
			linea = entrada.readLine();
			if(str_spi_encr.isEmpty()==false){
				str_spi_encr+="\r\n";
			}
			str_spi_encr+=linea;
			}
			}catch (IOException e) {
			e.printStackTrace();
			}
			
			
			System.out.println("str spi "+str_spi_encr.toString());
			utilitario.crearArchivo(new File[]{archivotxt}, fileName.concat(".txt"));						
		} catch (Exception e) {
			// TODO: handle exception
			utilitario.crearError("xxx", "xxss", e);
		}
	}

	/**
	 * limpia toda la pantalla incluyendo el autocompletar
	 */
	public void limpiar() {
		tab_tabla.limpiar();	
		utilitario.addUpdate("tab_tabla");// limpia y refresca el autocompletar
	}

    @Override
    public void insertar() {
        tab_tabla.insertar();
    }

    @Override
    public void guardar() {
        tab_tabla.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }
	public Combo getCom_anio() {
		return com_anio;
	}
	public void setCom_anio(Combo com_anio) {
		this.com_anio = com_anio;
	}
    
}
