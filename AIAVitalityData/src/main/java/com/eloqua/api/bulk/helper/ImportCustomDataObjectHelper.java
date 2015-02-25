package com.eloqua.api.bulk.helper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eloqua.api.Client;
import com.eloqua.api.Method;
import com.eloqua.api.Response;
import com.eloqua.api.bulk.BulkClient;
import com.eloqua.api.models.CustomDataObject;
import com.eloqua.api.models.CustomObject;
import com.eloqua.api.models.CustomObjectData;
import com.eloqua.api.models.CustomObjectField;
import com.eloqua.api.models.CustomObjectSearchResponse;
import com.eloqua.api.models.Field;
import com.eloqua.api.models.Import;
import com.eloqua.api.models.RestObjectList;
import com.eloqua.api.models.SearchResponse;
import com.eloqua.api.models.Sync;
import com.eloqua.api.models.SyncResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class ImportCustomDataObjectHelper {

	/** The Logger instance */
	private static final Logger LOG = LoggerFactory.getLogger(ImportCustomDataObjectHelper.class);
	private Client client = null;
	private BulkClient bulkClient = null;
	
	private int responseCode=0;
	
	public ImportCustomDataObjectHelper() {
		
	}
	
	public ImportCustomDataObjectHelper(String site, String user, String password, String baseUrl) {
		client=new Client(site , user, password, baseUrl);
		bulkClient=new BulkClient(site , user, password, baseUrl);
	}

	public RestObjectList<CustomObjectSearchResponse> searchCustomDataObjects(
			String searchTerm, int page, int pageSize) {

		Response response = bulkClient.get("/Bulk/1.0/customObjects?search="+ searchTerm + "&page=" + page + "&pageSize=" + pageSize);
		Gson gson = new Gson();

		RestObjectList<CustomObjectSearchResponse> resultList = gson.fromJson(response.getBody(), RestObjectList.class);
		return resultList;
	}

	

	public RestObjectList<Field> getCustomObjectFields(int id) {
		Response response = bulkClient.get("/Bulk/1.0/customObject/" + id + "/fields?");
		JsonElement jsonElement = new JsonParser().parse(response.getBody());
		Gson gson = new Gson();
		Type typeOfT = new TypeToken<RestObjectList<Field>>() {}.getType();
		RestObjectList<Field> resultList = gson.fromJson(jsonElement, typeOfT);
		return resultList;
	}

	public CustomObject getCustomObject(int id) {
		CustomObject customObject = new CustomObject();
		try {
			Response response = client.get("/Rest/1.0/assets/customObject/"+ String.valueOf(id));
			Gson gson = new Gson();
			customObject = gson.fromJson(response.getBody(), CustomObject.class);

		} catch (Exception e) {			
			LOG.error("Exception in getCustomObject() of ImportCustomDataObjectHelper :::  {0}", e.getMessage());			
		}
		return customObject;
	}

	public Sync importData(String importUri, List<Map<String, Object>> data) {
		Sync sync = null;
		try {
			Gson gson = new Gson();	
			Response response = bulkClient.execute("/Bulk/1.0/"+importUri + "/data",
					Method.POST, gson.toJson(data));
			sync = gson.fromJson(response.getBody(), Sync.class);
		}
		catch (Exception e){
			LOG.error("Exception in importData() of ImportCustomDataObjectHelper :::  "+e.getMessage(),e);	
		}
		return sync;
	}	
	
	public RestObjectList<SyncResult> checkSyncResult(String syncUri) {
		try {
	        Response response = bulkClient.get("/Bulk/1.0/"+syncUri + "/results");
	        
	        LOG.info("In checkSyncResult() response body::: "+ response.getBody() + " for the sync URI === " +syncUri );
	        JsonElement jsonElement = new JsonParser().parse(response.getBody());
	        Gson gson = new Gson();
	        Type typeOfT = new TypeToken<RestObjectList<SyncResult>>(){}.getType();
	        
	        RestObjectList<SyncResult> resultList = gson.fromJson(jsonElement,  typeOfT);
	        
	        return resultList;
		}
		catch(Exception e){
			LOG.error("Exception in checkSyncResult() of ImportCustomDataObjectHelper ::: "+ e.getMessage(), e);	
			return null;
		}
    }

	public Import createImportStructure(String id, Map<String, String> fields) {

		try {
			Import importStruc = new Import();
			importStruc.setFields(fields);					
			importStruc.setIdentifierFieldName("EMAIL_ADDRESS1");	
			importStruc.setName("CDO Import");
			importStruc.setSyncTriggeredOnImport(true);
			importStruc.setSecondsToRetainData(4500);
			importStruc.setUpdateRule("ifNewIsNotNull");

			Gson gson = new Gson();
			Response response = bulkClient.execute("/Bulk/1.0/customObject/" + id + "/import", Method.POST,gson.toJson(importStruc));

			importStruc = gson.fromJson(response.getBody(), Import.class);
			return importStruc;
		} catch (JsonSyntaxException e) {
			LOG.error("Exception in createImportStructure() of ImportCustomDataObjectHelper  :::  {0}", e.getMessage());	
			return null;
		}
		catch (Exception e) {
			LOG.error("Exception in createImportStructure() of ImportCustomDataObjectHelper  :::  {0}", e.getMessage());	
			return null;
		}
	}

	public CustomDataObject SearchCustomObjects(int customObjectId,
			String searchTerm, int page, int pageSize) {

		CustomDataObject custObjectData = new CustomDataObject();
		try {

			Response response = client.get("/Rest/1.0/assets/customObject/"
					+ String.valueOf(customObjectId) + "?search=" + searchTerm
					+ "&page=" + page + "&count=" + pageSize);
			// Response response = _client.get("/assets/customObjects");
			Gson gson = new Gson();
			custObjectData = gson.fromJson(response.getBody(),
					CustomDataObject.class);

		} catch (Exception e) {
			//e.printStackTrace();
			LOG.error("Error in SearchCustomObjects() of ImportCustomDataObjectHelper ::: {0} ", e.getMessage());
		}
		return custObjectData;
	}

	public CustomObjectField createCustomObjectField(int customObjectId,
			CustomObjectField customObjectField) {

		try {
			Gson gson = new Gson();
			Response response = client.execute(
					"/Rest/1.0/data/customObject/" + String.valueOf(customObjectId),
					Method.PUT, gson.toJson(customObjectField));
			customObjectField = gson.fromJson(response.getBody(),
					CustomObjectField.class);
		} catch (Exception e) {
			//e.printStackTrace();
			LOG.error("Error in createCustomObjectField() of ImportCustomDataObjectHelper :::  {0}", e.getMessage());
		}

		return customObjectField;
	}

	/*public void setClient(Client client) {
		this.client = client;
	}

	public void setBulkClient(BulkClient bulkClient) {
		this.bulkClient = bulkClient;
	}*/

	
	//,String importPriorityId
	public Import createContactImportStructure(Map<String, String> fields) {

		Import importStruc = new Import();
		importStruc.setFields(fields);
		importStruc.setIdentifierFieldName("C_EmailAddress");
		importStruc.setName("Import contact ");
		importStruc.setSyncTriggeredOnImport(true);
		importStruc.setUpdatingMultipleMatchedRecords(true);
		//importStruc.setSecondsToRetainData(3600);
		importStruc.setUpdateRule("always");	
		//importStruc.setImportPriorityUri("/import/priority/"+importPriorityId);

		Gson gson = new Gson();
		Response response = bulkClient.execute("/Bulk/1.0/contact/import", Method.POST,gson.toJson(importStruc));
		LOG.info("Response :::" + response.getBody());

		importStruc = gson.fromJson(response.getBody(), Import.class);
		return importStruc;

	}
	
	@SuppressWarnings("unchecked")
	public SearchResponse<CustomObjectData> getLeadHistories(int leadCDOId, String search, int page, int count) {
		SearchResponse<CustomObjectData> leads = null;
		try {
			 Response response = client.get("/Rest/1.0/data/customObject/"+leadCDOId+"?search=" + search + "&page=" + page + "&count=" + count);

			Gson gson = new Gson();
			leads = gson.fromJson(response.getBody(), SearchResponse.class);

			JsonElement jsonElement = new JsonParser().parse(response.getBody());
			Type typeOfT = new TypeToken<SearchResponse<CustomObjectData>>() {}.getType();
			leads = gson.fromJson(jsonElement, typeOfT);

		} catch (Exception e) {
			
			LOG.error("Unable to get leads from Eloqua.  {0}", e.getMessage());
			
		}
		return leads;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	

}
