package paq_activos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import paq_sistema.aplicacion.Pantalla;

import com.lowagie.text.pdf.Barcode128;

import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.Tabla;

public class pre_activo extends Pantalla {
	private Tabla tab_activos_fijos=new Tabla();
	private Tabla tab_custodio=new Tabla();
	private Map p_parametros = new HashMap();
	private Reporte rep_reporte = new Reporte();
	private SeleccionFormatoReporte self_reporte = new SeleccionFormatoReporte();
	private Map map_parametros = new HashMap();



	private StreamedContent codBarras;
	private GraphicImage giBarra=new GraphicImage();


	public pre_activo(){
		rep_reporte.setId("rep_reporte"); //id
		rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");//ejecuta el metodo al aceptar reporte
		agregarComponente(rep_reporte);//agrega el componente a la pantalla
		bar_botones.agregarReporte();//aparece el boton de reportes en la barra de botones
		self_reporte.setId("self_reporte"); //id
		agregarComponente(self_reporte);
		tab_activos_fijos.setId("tab_activos_fijos");
		tab_activos_fijos.setTabla("afi_activo","ide_afact", 1);
		tab_activos_fijos.getColumna("ide_afubi").setCombo("afi_ubicacion","ide_afubi","detalle_afubi","");
		tab_activos_fijos.getColumna("ide_aftia").setCombo("afi_tipo_activo","ide_aftia","detalle_aftia","");
		tab_activos_fijos.getColumna("ide_aftip").setCombo("afi_tipo_propiedad","ide_aftip","detalle_aftip","");
		tab_activos_fijos.getColumna("ide_afseg").setCombo("afi_seguro","ide_afseg","detalle_afseg","");


		tab_activos_fijos.setTipoFormulario(true);
		tab_activos_fijos.getGrid().setColumns(4);
		tab_activos_fijos.agregarRelacion(tab_custodio);
		tab_activos_fijos.onSelect("seleccionarActivo");
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


		generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));		
		giBarra.setId("giBarra");
		giBarra.setWidth("300");
		giBarra.setHeight("120");
		giBarra.setValueExpression("value", crearValueExpression("pre_index.clase.codBarras"));

		Division div=new Division();
		div.dividir2(pat_custodio,giBarra , "70%", "V");

		Division div_division=new Division();
		div_division.dividir2(pat_activo_fijos,div, "50%", "h");

		agregarComponente(div_division);
	}
	
	public void seleccionarActivo(SelectEvent evt){
		tab_activos_fijos.seleccionarFila(evt);
		tab_custodio.ejecutarValorForanea(tab_activos_fijos.getValorSeleccionado());
		generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));
		
	}

	@Override
	public void inicio() {
		// TODO Auto-generated method stub
		super.inicio();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
		}
	}
	
	@Override
	public void siguiente() {
		// TODO Auto-generated method stub
		super.siguiente();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
		}
	}
	
	
	@Override
	public void atras() {
		// TODO Auto-generated method stub
		super.atras();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
		}
	}
	
	@Override
	public void fin() {
		// TODO Auto-generated method stub
		super.fin();
		if(tab_custodio.isFocus()){
			generarCodigoBarras(tab_custodio.getValor("cod_barra_afcus"));	
		}
	}
	
	//reporte
	public void abrirListaReportes() {
		// TODO Auto-generated method stub
		rep_reporte.dibujar();
	}
	public void aceptarReporte(){
		if(rep_reporte.getReporteSelecionado().equals("Activo"));{
			if (rep_reporte.isVisible()){
				p_parametros=new HashMap();		
				rep_reporte.cerrar();	
				p_parametros.put("Titulo","Activo");
				p_parametros.put("ide_usua",Integer.parseInt("7"));
				p_parametros.put("ide_empr",Integer.parseInt("0"));
				p_parametros.put("ide_sucu",Integer.parseInt("1"));
				//p_parametros.put("pide_fafac",Integer.parseInt(tab_cont_viajeros.getValor("ide_fanoc")));
				self_reporte.setSeleccionFormatoReporte(p_parametros,rep_reporte.getPath());
				self_reporte.dibujar();

			}
		}
	}



	private void generarCodigoBarras(String cod)  {
		if(cod==null || cod.isEmpty()){
			codBarras=null;
			utilitario.addUpdate("giBarra");
			return;
		}
		try{
			Barcode128 code128 = new Barcode128();
			code128.setCode(cod);
			java.awt.Image img = code128.createAwtImage(Color.BLACK, Color.WHITE);			
			BufferedImage outImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			outImage.getGraphics().drawImage(img, 0, 0, null);
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			ImageIO.write(outImage, "jpeg", bytesOut);
			bytesOut.flush();
			byte[] jpgImageData = bytesOut.toByteArray();
			codBarras= new DefaultStreamedContent(new ByteArrayInputStream(jpgImageData), "image/png");
			giBarra.setValue(codBarras);
			utilitario.addUpdate("giBarra");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
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

	public Map getP_parametros() {
		return p_parametros;
	}

	public void setP_parametros(Map p_parametros) {
		this.p_parametros = p_parametros;
	}

	public Reporte getRep_reporte() {
		return rep_reporte;
	}

	public void setRep_reporte(Reporte rep_reporte) {
		this.rep_reporte = rep_reporte;
	}

	public SeleccionFormatoReporte getSelf_reporte() {
		return self_reporte;
	}

	public void setSelf_reporte(SeleccionFormatoReporte self_reporte) {
		this.self_reporte = self_reporte;
	}

	public Map getMap_parametros() {
		return map_parametros;
	}

	public void setMap_parametros(Map map_parametros) {
		this.map_parametros = map_parametros;
	}

	public StreamedContent getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(StreamedContent codBarras) {
		this.codBarras = codBarras;
	}
	
	private ValueExpression crearValueExpression(String expresion) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(
                facesContext.getELContext(), "#{" + expresion + "}", Object.class);
    }


}

