package MRReportPack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import matrix.db.Context;
import com.matrixone.apps.domain.util.MqlUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Servlet implementation class MRReports
 */
@WebServlet("/MRReports")
public class MRReports extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MRReports() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] strDesignResponsibility = request.getParameterValues("DesignResponsibility");
		String strDesignResponsibilitySelected = "";
		for(String strBU : strDesignResponsibility)
		{
			if("All".equalsIgnoreCase(strBU))
			{
				strDesignResponsibilitySelected = strBU;
				break;
			} else {
				strDesignResponsibilitySelected = (strDesignResponsibilitySelected == "") ? strBU : strDesignResponsibilitySelected+"_"+strBU;
			}
		}
		String strExportCriteria = request.getParameter("exportCriteria");
		String strStartDate = request.getParameter("startdate");
		String strEndDate = request.getParameter("enddate");
		String DATE_FORMAT = "yyyyMMddHHmm";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat nFormat = new SimpleDateFormat(DATE_FORMAT);
		String dateTime = nFormat.format(cal.getTime());
		String reportFile = "MRs" + strDesignResponsibilitySelected + strExportCriteria + "Report_" + dateTime;
		reportFile = reportFile.replace(" ", "");
		reportFile = reportFile+".txt";
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("MRPropertyPage.properties");
		prop.load(inputStream);
		String reportFilePath = prop.getProperty("FileFolder");
		String reportFilePathFile = reportFilePath + reportFile;
		File file = null;
		try{
			String path="cmd /c E:\\3DXEdgeApps\\BatchJobs\\MRReports\\MRReportBatch.bat "+reportFilePath+" "+reportFile+" "+strDesignResponsibilitySelected+" "+strExportCriteria+" "+strStartDate+" "+strEndDate+"";
			Runtime rn=Runtime.getRuntime();
			Process pr=rn.exec(path);
			int exitVal=pr.waitFor();
			
			if(exitVal==0)
			{	
			file = new File(reportFilePathFile);

			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length());
			response.setHeader( "Content-Disposition",
			         String.format("attachment; filename=\"%s\"", file.getName()));

			OutputStream out = response.getOutputStream();
			try (FileInputStream in = new FileInputStream(file)) {
			    byte[] buffer = new byte[4096];
			    int length;
			    while ((length = in.read(buffer)) > 0) {
			        out.write(buffer, 0, length);
			    }
			}
			out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			file.delete();
		}
	}

}
