package com.example.demo;



//1. Initial Input
 class ProjectRequirement {
 private String description;
 public ProjectRequirement() {}
 public ProjectRequirement(String description) { this.description = description; }
 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }
}

//2. Business Analyst Output
 class BusinessSpecification {
 private String userStories;
 private String financialEstimate;
 
 public BusinessSpecification() {}
 public String getUserStories() { return userStories; }
 public void setUserStories(String userStories) { this.userStories = userStories; }
 public String getFinancialEstimate() { return financialEstimate; }
 public void setFinancialEstimate(String financialEstimate) { this.financialEstimate = financialEstimate; }
}

//3. Combined Output from Software Architect


 class TechnicalAndBusinessSpec {
	    private BusinessSpecification businessSpec;
	    private String techStack;
	    private String databaseSchema;
	    private String databaseDiagramMarkdown;     // For Database ERD
	    private String architectureDiagramMarkdown; // For High-Level Flowchart
	    private String deploymentDiagramMarkdown;   // 🚀 ADDED: For physical server topography
	    private String sequenceDiagramMarkdown;     // 🚀 ADDED: For live request lifecycle flows

	    public TechnicalAndBusinessSpec() {}

	    // 🚀 Getters and Setters for the new diagrams
	    public String getDeploymentDiagramMarkdown() { return deploymentDiagramMarkdown; }
	    public void setDeploymentDiagramMarkdown(String deploymentDiagramMarkdown) { this.deploymentDiagramMarkdown = deploymentDiagramMarkdown; }
	    public String getSequenceDiagramMarkdown() { return sequenceDiagramMarkdown; }
	    public void setSequenceDiagramMarkdown(String sequenceDiagramMarkdown) { this.sequenceDiagramMarkdown = sequenceDiagramMarkdown; }

	    // Existing Getters and Setters
	    public String getArchitectureDiagramMarkdown() { return architectureDiagramMarkdown; }
	    public void setArchitectureDiagramMarkdown(String architectureDiagramMarkdown) { this.architectureDiagramMarkdown = architectureDiagramMarkdown; }
	    public String getDatabaseDiagramMarkdown() { return databaseDiagramMarkdown; }
	    public void setDatabaseDiagramMarkdown(String databaseDiagramMarkdown) { this.databaseDiagramMarkdown = databaseDiagramMarkdown; }
	    public BusinessSpecification getBusinessSpec() { return businessSpec; }
	    public void setBusinessSpec(BusinessSpecification businessSpec) { this.businessSpec = businessSpec; }
	    public String getTechStack() { return techStack; }
	    public void setTechStack(String techStack) { this.techStack = techStack; }
	    public String getDatabaseSchema() { return databaseSchema; }
	    public void setDatabaseSchema(String databaseSchema) { this.databaseSchema = databaseSchema; }
	}


//4. Final Aggregated Blueprint
 
