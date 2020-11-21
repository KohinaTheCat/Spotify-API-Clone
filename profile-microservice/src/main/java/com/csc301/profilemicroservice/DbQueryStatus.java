package com.csc301.profilemicroservice;

public class DbQueryStatus {
	
	private String message;
	private DbQueryExecResult dbQueryExecResult;
	private Object data = null;  // Data can be anything returned to the Db
	
	public DbQueryStatus(String message, DbQueryExecResult dbQueryExecResult) {
		this.message = message;
		this.dbQueryExecResult = dbQueryExecResult;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DbQueryExecResult getdbQueryExecResult() {
		return dbQueryExecResult;
	}

	public void setdbQueryExecResult(DbQueryExecResult dbQueryExecResult) {
		this.dbQueryExecResult = dbQueryExecResult;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object obj) {
		this.data = obj;
	}

}
