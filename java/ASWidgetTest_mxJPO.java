
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.EnoviaResourceBundle;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.domain.util.PersonUtil;

import matrix.db.Context;
import matrix.util.StringList;

public class ASWidgetTest_mxJPO {
	public ASWidgetTest_mxJPO(Context context, String[] args) {
		
	}
	
	public String getData (Context context, String[] args ) throws Exception {
		
		JsonObjectBuilder output = Json.createObjectBuilder();
		
		try {
			StringList objectSelects = new StringList();
			objectSelects.add(DomainConstants.SELECT_TYPE);
			objectSelects.add(DomainConstants.SELECT_NAME);
			objectSelects.add(DomainConstants.SELECT_REVISION);
			objectSelects.add(DomainConstants.SELECT_OWNER);
			objectSelects.add(DomainConstants.SELECT_ID);
			objectSelects.add(DomainConstants.SELECT_CURRENT);
			objectSelects.add(DomainConstants.SELECT_POLICY);
			
			
			MapList mlObjectInfoList = DomainObject.findObjects(context, 
					args[0], 
					DomainConstants.QUERY_WILDCARD, 
					DomainConstants.QUERY_WILDCARD,
					DomainConstants.QUERY_WILDCARD,
					DomainConstants.QUERY_WILDCARD,
					"",
					null,
					true,
					objectSelects,
					Short.parseShort(args[1])
					,new StringList());
			
			JsonArrayBuilder jsonArr = Json.createArrayBuilder();
			for (int iObj=0; iObj<mlObjectInfoList.size(); iObj++) {
				Map<String, String> mpObjInfo = (Map)mlObjectInfoList.get(iObj);
				JsonObjectBuilder jsonbObjInfo = Json.createObjectBuilder();
				
				for (Map.Entry<String, String> ent : mpObjInfo.entrySet()) {
					if (DomainConstants.SELECT_OWNER.equalsIgnoreCase(ent.getKey())) {
						jsonbObjInfo.add(ent.getKey(), PersonUtil.getFullName(context, ent.getValue()));
					} else if (DomainConstants.SELECT_TYPE.equalsIgnoreCase(ent.getKey())) {
						String sTypeUI = EnoviaResourceBundle.getProperty(context, "Framework",
								"emxFramework.Type."
										+ ((String) ent.getValue()).replace(' ', '_'),
										"en");
						jsonbObjInfo.add(ent.getKey(), sTypeUI);
					} else if (DomainConstants.SELECT_CURRENT.equalsIgnoreCase(ent.getKey())) {
						String state = "emxFramework.State." + mpObjInfo.get(DomainConstants.SELECT_POLICY).toString().replace(' ','_') + "." + ent.getValue().replace(' ','_');
		                String exactStateName = EnoviaResourceBundle.getProperty(context, "Framework", state, "en");
		                jsonbObjInfo.add(ent.getKey(), exactStateName);
					}else if (DomainConstants.SELECT_POLICY.equalsIgnoreCase(ent.getKey())) {
						
					}else {
						jsonbObjInfo.add(ent.getKey(), ent.getValue());
					}
					
				}
				jsonArr.add(jsonbObjInfo.build());
			}
			output.add("data", jsonArr.build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.build().toString();
	}
}
