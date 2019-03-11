package com.jrwp.payMent.help;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.CallConfigParam;

public class CallConfigParamHelper {

	public static final int length = 5;
	private List<CallConfigParam> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<CallConfigParam> page;
	@JsonIgnore
	private static List<CallConfigParam> temp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CallConfigParam callConfigParam;

	public CallConfigParamHelper(PageInfo<CallConfigParam> page) {
		// this.page = page;
		// items = page.getList();
		this(page.getList());
		this.page = page;
	}

	public CallConfigParamHelper() {

	}
	
	public CallConfigParamHelper(List<CallConfigParam> list) {
		// TODO Auto-generated constructor stub
//		List<DicCity> group = DicCityHelper.group(list);
//		items = group;
		List<CallConfigParam> group = CallConfigParamHelper.group(list);
		temp = list;
		items = group;
//		CallConfigParam param = new CallConfigParam();
//		param.setId(0l);
//		param.setTitle("默认菜单");
//		param.setChildren(group);
//		items = new ArrayList<CallConfigParam>();
//		items.add(param);
	}

	public CallConfigParamHelper(CallConfigParam callConfigParam) {
		this.callConfigParam = callConfigParam;
	}
	
	private static List<CallConfigParam> group(List<CallConfigParam> list) {
		// TODO Auto-generated method stub
		List<CallConfigParam> cities = getChildren(list, "");
		return cities;
	}
	
	private static List<CallConfigParam> getChildren(List<CallConfigParam> list, String min) {
		// TODO Auto-generated method stub
		List<CallConfigParam> dList = new ArrayList<CallConfigParam>();
		for(int i = 0;i < list.size(); i++){
			CallConfigParam param = list.get(i);
			if(param.getOrderCode().startsWith(min)&&
					param.getOrderCode().length() == min.length() + length){
				dList.add(param);
				param.setChildren(getChildren(list, param.getOrderCode()));
			}
		}
		if(dList.size() == 0){
			return null;
		}
		return dList;
	}

	// 分页
	public int getCurrentPage() {
		return page.getPageNum();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return page.getPages();
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalItems() {
		return new Long(page.getTotal()).intValue();
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getItemsPerPage() {
		return page.getPageSize();
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	// 其他
	public List<CallConfigParam> getItems() {
		return items;
	}

	public void setItems(List<CallConfigParam> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<CallConfigParam> getPage() {
		return page;
	}

	public void setPage(PageInfo<CallConfigParam> page) {
		this.page = page;
	}

	public CallConfigParam getCallConfigParam() {
		return callConfigParam;
	}

	public void setCallConfigParam(CallConfigParam callConfigParam) {
		this.callConfigParam = callConfigParam;
	}

	public static void main(String[] args) throws ParseException {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// System.out.println(cal.get(Calendar.MONTH+1));
		// Format fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date=format.parse("2018-11-11 12:00:00");
		// Timestamp d = new Timestamp(System.currentTimeMillis());
		// Timestamp nousedate = new Timestamp(date.getTime());
		// System.out.println(nousedate);
	}
}