package com.Emerson.widget.EmersonTest;

import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.dassault_systemes.platform.restServices.RestService;
import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;

import matrix.db.JPO;


@Path("/EmersonTestService")
public class EmersonTestService extends RestService
{
	private static final String PHYSICAL_ID = "PhysicalId";
	private static final String OBJ_SELECT = "ObjectSelects";
		
	
	@GET
	@Path("/getTestData")
	public Response getAllSTRDataForExport(@javax.ws.rs.core.Context HttpServletRequest request, 
			@QueryParam("type") String sType,@QueryParam("limit") String sObjLimit) throws Exception	
	{
		Response res = null;
		try
		{
			System.out.println("----In Service-----");
			matrix.db.Context context = getAuthenticatedContext(request, true);		
			
            String strOutput = JPO.invoke(context, "ASWidgetTest", new String [] {sType, sObjLimit}, "getData", new String [] {sType, sObjLimit},String.class);
			res = Response.ok(strOutput).type("application/json").build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Export services:"+e.getMessage());
			throw e;
		}
		return res;
		
	}
}
