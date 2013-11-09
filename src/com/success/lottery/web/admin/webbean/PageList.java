package com.success.lottery.web.admin.webbean;

import java.util.List;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
public class PageList implements PaginatedList{

	private int		fullListSize;
	private int		perPage;
	private int		pageNumber;
	private String	searchId;
	private List	list;

	@Override
	public int getFullListSize(){
		return this.fullListSize;
	}

	@Override
	public List getList(){
		return list;
	}

	@Override
	public int getObjectsPerPage(){
		return this.perPage;
	}

	@Override
	public int getPageNumber(){
		return this.pageNumber;
	}

	@Override
	public String getSearchId(){
		return this.searchId;
	}

	@Override
	public String getSortCriterion(){
		return null;
	}

	@Override
	public SortOrderEnum getSortDirection(){
		return null;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public void setFullListSize(int fullListSize){
		this.fullListSize = fullListSize;
	}

	public void setPageNumber(int pageNumber){
		this.pageNumber = pageNumber;
	}

	public void setSearchId(String searchId){
		this.searchId = searchId;
	}

	public void setList(List list){
		this.list = list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
