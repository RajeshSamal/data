package com.eloqua.api.bulk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.eloqua.api.Method;
import com.eloqua.api.Response;
/*import com.eloqua.api.bulk.clients.contacts.*;
import com.eloqua.api.bulk.clients.customobjects.*;
import com.eloqua.api.bulk.models.login.AccountInfo;
import com.eloqua.api.models.ExportFilter;
import com.eloqua.api.models.SearchResponse;*/
import com.google.gson.Gson;

public class BulkClient extends BaseClient {

	private Gson gson;
	private String authToken;
	private String baseUrl;
	private String site;
	private String user;
	private String password;
	private String url;

	public Gson Gson() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	public BulkClient() {

	}

	public void setAuthToken() {
		this.authToken = "Basic "
				+ javax.xml.bind.DatatypeConverter.printBase64Binary((site
						+ "\\" + user + ":" + password).getBytes());
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
		setBaseUrl(url);
	}

	public BulkClient(String site, String user, String password, String url) {
		super(site, user, password, url);
		this.site = site;
		this.user = user;
		this.password = password;
		this.url = url;
		setUrl(url);
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		setAuthToken();
	}

	public Response get(String uri) {
		return this.execute(uri, Method.GET, null);
	}

	public Response post(String uri, String body) {
		return this.execute(uri, Method.POST, body);
	}

	public Response put(String uri, String body) {
		return this.execute(uri, Method.PUT, body);
	}

	public void delete(String uri) {
		this.execute(uri, Method.DELETE, null);
	}

	public Response execute(String uri, Method method, String body) {
		Response response = new Response();

		try {
			URL url = new URL(baseUrl + uri);

			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					"127.0.0.1", 8888));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod(method.toString());
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", authToken);

			if (method == Method.POST || method == Method.PUT) {
				conn.setDoOutput(true);

				final OutputStream os = conn.getOutputStream();
				os.write(body.getBytes());
				os.flush();
				os.close();
			}

			InputStream is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));

			String line;
			StringBuffer respBody = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				// System.out.println("---SSS---"+line);
				respBody.append(line);

			}

			response.setBody(respBody.toString());

			rd.close();

			response.setStatusCode(conn.getResponseCode());
			conn.disconnect();
		} catch (Exception e) {
			response.setException(e.getMessage());
			// e.printStackTrace();
		}
		return response;
	}

}
