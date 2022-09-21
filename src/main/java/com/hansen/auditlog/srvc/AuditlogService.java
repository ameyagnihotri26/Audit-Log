package com.hansen.auditlog.srvc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansen.auditlog.dao.AuditlogDao;
import com.hansen.auditlog.model.Auditlog;


@Service
public class AuditlogService {

	@Autowired
	AuditlogDao auditlogDao;

//	create - This function creates and save an auditLog in our database.
	public Object create(Auditlog entity) {
		Auditlog Auditlog = auditlogDao.save(entity);
		
		return Auditlog;
	}

//	read - This function return the auditLog present in our database by its ID.
	public Object read(Long id) {
		Optional<Auditlog> auditLogByID = auditlogDao.findById(id);
		if (auditLogByID.isPresent()) {
			return auditLogByID.get();
		} else {
			return null;
		}
	}

//	readAll - This function return the list of auditLogs present inside in our database.
	public Iterable<Auditlog> readAll() {
		Iterable<Auditlog> AuditlogList = auditlogDao.findAll();
		
		return AuditlogList;
	}

//	update - This function updates an auditLog present in our database.
	public Object update(Auditlog tobemerged) {
		Optional<Auditlog> updatedAuditLog = auditlogDao.findById(tobemerged.getId());
		if (updatedAuditLog.isPresent() && 
			tobemerged.getModificationDate() != null &&
			tobemerged.getOperationType() != null && 
			tobemerged.getEntityJson() != null && 
			tobemerged.getId() != 0) {
			Auditlog Auditlog = auditlogDao.save(tobemerged);
			
			return Auditlog;
		} else {
			return null;
		}	
	}

//	delete - This function deletes the auditLog present inside in our database for existing ID.
	public boolean delete(Long planid) {
		Optional<Auditlog> deletedAuditLog = auditlogDao.findById(planid);
		if (deletedAuditLog.isPresent()) {
			auditlogDao.deleteById(planid);
			
			return true;
		}
		
		return false;
	}
	
}