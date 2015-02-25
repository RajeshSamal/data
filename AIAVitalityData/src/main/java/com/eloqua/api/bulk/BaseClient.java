package com.eloqua.api.bulk;

public class BaseClient extends com.eloqua.api.Client {

	public BaseClient() {

	}

	public BaseClient(String site, String user, String password, String url) {
		super(site, user, password, url);
	}

	public com.google.gson.Gson Gson() {
		if (gson == null) {
			gson = new com.google.gson.Gson();
		}
		return gson;
	}

	private com.google.gson.Gson gson;
}
