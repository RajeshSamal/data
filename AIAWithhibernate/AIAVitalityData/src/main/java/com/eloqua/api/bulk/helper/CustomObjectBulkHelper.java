package com.eloqua.api.bulk.helper;

import com.eloqua.api.Method;
import com.eloqua.api.Response;
import com.eloqua.api.bulk.BulkClient;
import com.eloqua.api.models.CustomObject;
import com.google.gson.Gson;
import com.eloqua.api.models.CustomDataObject;
import com.eloqua.api.models.CustomObjectData;

public class CustomObjectBulkHelper {
	
	private BulkClient bulkClient;
	/**
	 * 
	 */
	/*public CustomObjectBulkHelper(String site, String user, String password, String baseUrl) 
	{
		bulkClient = new BulkClient(site ,user, password, baseUrl);
	}*/
	
	public CustomDataObject getCustomDataObject(int id)  
    {  
		CustomDataObject customDataObject = new CustomDataObject();
		
		try 
		{
			Response response = bulkClient.get("/assets/customObject/"+String.valueOf(id));
			//Response response = _client.get("/assets/customObjects");
			System.out.println("Response :::"+response.getBody());
			
			Gson gson = new Gson();
			customDataObject = gson.fromJson(response.getBody(), CustomDataObject.class);
					
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}
		return customDataObject;
    }  
	
	
	public CustomDataObject SearchCustomObjects(int customObjectId, String searchTerm, int page, int pageSize){
		
		CustomDataObject custObjectData=new CustomDataObject();
		try{
			
			Response response = bulkClient.get("/assets/customObject/"+String.valueOf(customObjectId)+"?search="+searchTerm+"&page="+page+"&count="+pageSize);
			//Response response = _client.get("/assets/customObjects");
			System.out.println("Response :::"+response.getBody());
			
			Gson gson = new Gson();
			custObjectData = gson.fromJson(response.getBody(), CustomDataObject.class);
			
		}catch(Exception e){
			e.printStackTrace();	
		}
		
		return custObjectData;
	}
	
	public CustomObjectData createCustomObjectData(int customObjectId,CustomObjectData customObjectData){
		
		//CustomDataObject custObjectData=new CustomDataObject();
		
		try 
		{
			Gson gson = new Gson();
			Response response = bulkClient.execute("/data/customObject/"+String.valueOf(customObjectId), Method.POST, gson.toJson(customObjectData));			
			System.out.println("response :::"+response.getException());
			
			customObjectData = gson.fromJson(response.getBody(), CustomObjectData.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}
				
		return  customObjectData;
	}
	
	/*public CustomObject CreateCustomObject(CustomObject customObject)
        {
            var request = new RestRequest(Method.POST)
                              {
                                  RequestFormat = DataFormat.Json,
                                  Resource = "/assets/customObject"
                              };
            request.AddBody(customObject);

            var response = _client.Execute<CustomObject>(request);

            return response.Data;
        }
	 * 
	 */

	public CustomObject createCustomObject(CustomObject customObject){
					
		CustomObject customObject1=new CustomObject();
		
			try 
			{
				Gson gson = new Gson();
				Response response = bulkClient.execute("/data/customObject", Method.POST, gson.toJson(customObject));			
				System.out.println("response :::"+response.getException());
				
				customObject1 = gson.fromJson(response.getBody(), CustomObject.class);
			}
			catch (Exception e)
			{
				e.printStackTrace();			
			}
					
			return  customObject1;	
	
	}
	

}
