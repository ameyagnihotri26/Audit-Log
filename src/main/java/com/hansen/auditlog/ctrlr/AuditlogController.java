package com.hansen.auditlog.ctrlr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hansen.auditlog.dao.AuditlogDao;
import com.hansen.auditlog.model.Auditlog;
import com.hansen.auditlog.srvc.AuditlogService;

@RestController
@RequestMapping("/audit")
public class AuditlogController {

	@Autowired
	AuditlogService auditSrvc;

	@Autowired
	AuditlogDao auditlogDao;
	
//	create - This function is used to create an audit log. 
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Auditlog inputentity) {
		ResponseEntity<Object> AuditResponse;
		
		Object auditLog = auditSrvc.create(inputentity);
		if (auditLog != null) {
			AuditResponse = new ResponseEntity<Object>(auditLog, null, HttpStatus.CREATED);
		} else {
			String message = "Already ID : " + inputentity.getId() + " Exist";
			
			AuditResponse = new ResponseEntity<Object>(message, null, HttpStatus.NOT_ACCEPTABLE);
		}
		
		return AuditResponse;
	}
//	read - This function is used to get an audit log by its ID.
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
		ResponseEntity<Object> AuditResponse;

		Object auditPlanByID = auditSrvc.read(id);
		if (auditPlanByID != null) {
			AuditResponse = new ResponseEntity<Object>(auditPlanByID, null, HttpStatus.OK);
		} else {
			String message = "ID : " + id + " Not Found";
			
			AuditResponse = new ResponseEntity<Object>(message, null, HttpStatus.NOT_FOUND);
		}
		
		return AuditResponse;
	}
//	readAll - This function is used to get list of all audit logs.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Auditlog>> readAll() {
		ResponseEntity<Iterable<Auditlog>> AuditResponse;

		Iterable<Auditlog> AuditlogList = auditSrvc.readAll();

		AuditResponse = new ResponseEntity<Iterable<Auditlog>>(AuditlogList, null, HttpStatus.OK);
		
		return AuditResponse;
	}

//	update - This function is used to update an audit log.
	@RequestMapping(method = RequestMethod.PATCH) 
	public ResponseEntity<Object> update(@RequestBody Auditlog tobemerged) {
		ResponseEntity<Object> AuditResponse;
		
		Object updatedAuditPlan = auditSrvc.update(tobemerged);
		if (updatedAuditPlan != null) {
			AuditResponse = new ResponseEntity<Object>(updatedAuditPlan, null, HttpStatus.CREATED);
		} else {
			String message = "ID : " + tobemerged.getId() + " Not Found";
			
			AuditResponse = new ResponseEntity<Object>(message, null, HttpStatus.NOT_FOUND);	
		}
		
		return AuditResponse;
	}

//	delete - This function is used to delete an audit log.
	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		ResponseEntity<Object> AuditResponse;
		
		Boolean deletedAuditPlan = auditSrvc.delete(planid);
		if (deletedAuditPlan) {
			String successMessage = "ID: " + planid + " deleted successfully";
			
			AuditResponse = new ResponseEntity<Object>(successMessage, null, HttpStatus.OK);
		} else {
			String unSuccessMessage = "ID: " + planid + " Not Found";
			
			AuditResponse = new ResponseEntity<Object>(unSuccessMessage, null, HttpStatus.NOT_FOUND);
		}
		
		return AuditResponse;
	}
}