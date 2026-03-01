package br.com.ajasoftware.relatorio;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import br.com.ajasoftware.cdi.CDIServiceLocator;
import br.com.ajasoftware.service.msg.NegocioException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class ControleRelatorioPDF {

	private String nomeRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	private List<?> lista;
	
	private FacesContext facesContext;

	private boolean relatorioGerado;

	public  ControleRelatorioPDF(String nomeRelatorio,
			HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida, List<?> lista) {
		this.nomeRelatorio = nomeRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		this.lista = lista;

		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		facesContext = CDIServiceLocator.getBean(FacesContext.class);
	}

	public void gerar() throws NegocioException{
		// TODO Auto-generated method stub
		try{
			InputStream relatorioStream = this.getClass().getResourceAsStream("/relatorios/"+this.nomeRelatorio+".jasper");
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
			
			ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
			String pathRel = servletContext.getRealPath("/WEB-INF/classes/relatorios/");
			this.parametros.put("SUBREPORT_DIR", pathRel);

			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, ds);
			this.relatorioGerado = print.getPages().size() > 0;

			if(relatorioGerado){
				Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration, 
				OutputStreamExporterOutput> exportador = new JRPdfExporter();
				exportador.setExporterInput(new SimpleExporterInput(print));
				exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\"" 
						+ this.nomeArquivoSaida  + "\"");

				exportador.exportReport();
			}			
		}
		catch (Exception e) {
			System.out.println("************************");
			e.printStackTrace();
			System.out.println("************************");
			throw new NegocioException("Erro ao gerar relatório.");
		}
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}	
}