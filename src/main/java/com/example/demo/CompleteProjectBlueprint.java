package com.example.demo;

public class CompleteProjectBlueprint {
	 private String systemArchitecture;
	 private String businessCase;

	 public CompleteProjectBlueprint() {}
	 public CompleteProjectBlueprint(String systemArchitecture, String businessCase) {
	     this.systemArchitecture = systemArchitecture;
	     this.businessCase = businessCase;
	 }
	 public String getSystemArchitecture() { return systemArchitecture; }
	 public void setSystemArchitecture(String systemArchitecture) { this.systemArchitecture = systemArchitecture; }
	 public String getBusinessCase() { return businessCase; }
	 public void setBusinessCase(String businessCase) { this.businessCase = businessCase; }
	}