package br.com.ajasoftware.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.XlsExporterConfiguration;
import net.sf.jasperreports.export.XlsReportConfiguration;

public class ExecutorRelatorio implements Work {

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	private ExternalContext context;

	private boolean relatorioGerado;
	private TipoRel tipoRel;

	public ExecutorRelatorio(String caminhoRelatorio,
			HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida, ExternalContext context,
			TipoRel tipoRel) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		this.context = context;
		this.tipoRel = tipoRel;

		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}

	public ExecutorRelatorio() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		try {
			ServletContext servlet = (ServletContext)context.getContext();
			InputStream relatorioStream = new FileInputStream(servlet.getRealPath("/relatorios/")+this.caminhoRelatorio);

			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			this.relatorioGerado = print.getPages().size() > 0;

			if (this.relatorioGerado) {
				if(tipoRel.compareTo(TipoRel.PDF)==0) {
					Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration, 
					OutputStreamExporterOutput> exportador = new JRPdfExporter();
					exportador.setExporterInput(new SimpleExporterInput(print));
					exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "attachment; filename=\"" 
							+ this.nomeArquivoSaida  + "\"");
					response.addHeader("Pragma", "no-cache");
					response.addHeader("Cache-Control", "no-cache");
					response.addHeader("Cache-Control", "no-store");

					exportador.exportReport();
				}
				else if(tipoRel.compareTo(TipoRel.XLS)==0){
					Exporter<ExporterInput, XlsReportConfiguration, XlsExporterConfiguration, OutputStreamExporterOutput>
					exportador = new JRXlsExporter();
					exportador.setExporterInput(new SimpleExporterInput(print));
					SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
					configuration.setOnePagePerSheet(false);
					configuration.setDetectCellType(true);
					configuration.setCollapseRowSpan(false);
					configuration.setShowGridLines(true);	
					configuration.setWhitePageBackground(false);
					configuration.setRemoveEmptySpaceBetweenRows(true);
					configuration.setRemoveEmptySpaceBetweenColumns(true);
					exportador.setConfiguration(configuration);
					exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

					response.setContentType("application/xls");
					response.setHeader("Content-Disposition", "attachment; filename=\"" 
							+ this.nomeArquivoSaida  + "\"");
					response.addHeader("Pragma", "no-cache");
					response.addHeader("Cache-Control", "no-cache");
					response.addHeader("Cache-Control", "no-store");

					exportador.exportReport();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
		}
	}

	public synchronized void downloadFile(String filename, String fileLocation,
			FacesContext facesContext) {

		ExternalContext context = facesContext.getExternalContext(); // Context
		String path = fileLocation; // Localizacao do arquivo
		File file = new File(path); // Objeto arquivo mesmo :)

		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\""); //aki eu seto o header e o nome q vai aparecer na hr do donwload
		response.setContentLength((int) file.length()); // O tamanho do arquivo
		response.setContentType("application/csv"); // e obviamente o tipo

		try {
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();

			byte[] buf = new byte[(int)file.length()];
			int count;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			in.close();
			out.flush();
			out.close();
			facesContext.responseComplete();
		} catch (IOException ex) {
			System.out.println("Error in downloadFile: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
}