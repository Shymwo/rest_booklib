package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.BaseModel;
import put.poznan.rest.booklib.util.FacesMessageUtil;
import put.poznan.rest.booklib.util.PropertyUtil;

public abstract class CommonBean<T extends BaseModel> implements Serializable {
	
	private static final long serialVersionUID = -8471988082496802606L;
	
	private List<T> records;
	
	private T selectedRecord;

	@SuppressWarnings("unchecked")
	public void loadRecords() {
		try {
			String path = getUrlForLoadRecords();
			Map<String, String> urlVariables = getVarsForLoadRecords();
			RestTemplate restTemplate = new RestTemplate();
			records = restTemplate.getForObject(path, List.class, urlVariables);
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}
	
	public void loadRecord(Integer id) {
		try {
			selectedRecord = null;
			String path = getUrl()+"/"+id;
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Void> entity = new HttpEntity<Void>(new HttpHeaders());
			ResponseEntity<T> response = restTemplate.exchange(
				    path, HttpMethod.GET, entity, getRecordClass());
			if(HttpStatus.OK.value() != response.getStatusCode().value()) {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return;
			}
			selectedRecord = response.getBody();
			selectedRecord.setEtag(response.getHeaders().getETag());
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}
		
	public String createToken() {
		try {
			String tokenPath = PropertyUtil.getProperty("rest.uri")+"/tokens";
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Void> entity = new HttpEntity<Void>(new HttpHeaders());
			ResponseEntity<String> response = restTemplate.exchange(
				    tokenPath, HttpMethod.POST, entity, String.class);
			if (HttpStatus.CREATED.value() != response.getStatusCode().value()) {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
			return response.getBody();
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			return null;
		}
	}
	
	public String addRecord(String token) {
		try {
			String path = getUrl()+"?token="+token;
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<T> entity = new HttpEntity<T>(selectedRecord, new HttpHeaders());
			ResponseEntity<T> response = restTemplate.exchange(
				    path, HttpMethod.POST, entity, getRecordClass());
			if (HttpStatus.CREATED.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			return null;
		}
		return "return";
	}
	
	public String updateRecord() {
		try {
			String path = getUrl()+"/"+selectedRecord.getId();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("If-Match", selectedRecord.getEtag());
			HttpEntity<T> entity = new HttpEntity<T>(selectedRecord, headers);
			ResponseEntity<T> response = restTemplate.exchange(
				    path, HttpMethod.PUT, entity, getRecordClass());
			if (HttpStatus.ACCEPTED.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			return null;
		}
		return "return";
	}
	
	public String deleteRecord() {
		try {
			String path = getUrl()+"/"+selectedRecord.getId();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("If-Match", selectedRecord.getEtag());
			HttpEntity<Void> entity = new HttpEntity<Void>(headers);
			ResponseEntity<Void> response = restTemplate.exchange(
				    path, HttpMethod.DELETE, entity, Void.class);
			if (HttpStatus.NO_CONTENT.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
			
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			if (e.getMessage() != null && 
					!e.getMessage().contains(Integer.toString(HttpStatus.NOT_FOUND.value()))) {
				return null;
			}
		}
		return "return";
	}
	
	public abstract void loadNewRecord();

	public abstract String getUrl();
	
	public abstract Map<String, String> getVarsForLoadRecords();
	
	public abstract String getUrlForLoadRecords();
	
	public abstract Class<T> getRecordClass();

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public T getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(T selectedRecord) {
		this.selectedRecord = selectedRecord;
	};
}
