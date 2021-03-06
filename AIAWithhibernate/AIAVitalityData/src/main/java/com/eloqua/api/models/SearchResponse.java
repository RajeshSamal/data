package com.eloqua.api.models;

import java.util.List;

public class SearchResponse<T> 
{
	private List<T> elements;
	private int total;
	private int page;
	private int pageSize;
	
	
	public List<T> getElements() {
		return elements;
	}
	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
