package com.hansen.auditlog.ctrlr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private Log logger = LogFactory.getLog(AuditlogController.class);
	@Autowired
	AuditlogService auditSrvc;

	@Autowired
	AuditlogDao auditlogDao;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Auditlog inputentity) {
		ResponseEntity<Object> AuditResponse;
		
		Object auditLog = auditSrvc.create(inputentity);
		String m = "Already ID : " + inputentity.getId() + " Exist";
		if (auditLog != null) {
			AuditResponse = new ResponseEntity<Object>(auditLog, null, HttpStatus.CREATED);
			return AuditResponse;
		} else {
			AuditResponse = new ResponseEntity<Object>(m, null, HttpStatus.NOT_ACCEPTABLE);
			return AuditResponse;
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
		logger.info("Inside getbyid method");
		ResponseEntity<Object> AuditResponse;

		Object auditPlan = auditSrvc.read(id);
		String m1 = "ID : " + id + " Not Found";
		if (auditPlan != null) {
			AuditResponse = new ResponseEntity<Object>(auditPlan, null, HttpStatus.OK);
			return AuditResponse;
		} else {
			AuditResponse = new ResponseEntity<Object>(m1, null, HttpStatus.NOT_FOUND);
			return AuditResponse;

		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Auditlog>> readAll() {
		logger.info("Inside readAll method");
		ResponseEntity<Iterable<Auditlog>> AuditResponse;

		Iterable<Auditlog> AuditlogList = auditSrvc.readAll();

		AuditResponse = new ResponseEntity<Iterable<Auditlog>>(AuditlogList, null, HttpStatus.OK);
		return AuditResponse;
	}

	// Homework: Write methods for update and delete below

	@RequestMapping(method = RequestMethod.PATCH) // OR PUT
	public ResponseEntity<Object> update(@RequestBody Auditlog tobemerged) {
		logger.info("Inside update method");
		ResponseEntity<Object> AuditResponse;
		Object auditPlanList = auditSrvc.update(tobemerged);
		String m = "ID : " + tobemerged.getId() + " Not Found";
		if (auditPlanList != null) {
			AuditResponse = new ResponseEntity<Object>(auditPlanList, null, HttpStatus.CREATED);
			return AuditResponse;
		} else {
			AuditResponse = new ResponseEntity<Object>(m, null, HttpStatus.NOT_FOUND);
			return AuditResponse;
		}
	}

	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		logger.info("Inside delete method");
		ResponseEntity<Object> AuditResponse;
		Boolean person = auditSrvc.delete(planid);
		String m = "ID: " + planid + " deleted successfully";
		String m1 = "ID: " + planid + " Not Found";
		if (person) {
			AuditResponse = new ResponseEntity<Object>(m, null, HttpStatus.OK);
		} else {
			AuditResponse = new ResponseEntity<Object>(m1, null, HttpStatus.NOT_FOUND);
		}
		return AuditResponse;
	}
}