package com.example.demo;


import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Action;

import org.springframework.stereotype.Component;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.common.OperationContext;

@Agent(description = "Master Project Orchestrator generating high-level infrastructure blueprints.")
@Component
public class ProjectOrchestratorAgent {

    private final DiagramStorageService diagramStorageService;

    public ProjectOrchestratorAgent(DiagramStorageService diagramStorageService) {
        this.diagramStorageService = diagramStorageService;
    }

    @AchievesGoal(description = "Compile complete project layout blueprint packages with high-level system diagrams")
    @Action
    public CompleteProjectBlueprint orchestrateFlow(ProjectRequirement requirement, OperationContext context) {
        
        // 1. Collect business stories
        BusinessSpecification businessSpec = context.ai().withDefaultLlm().createObject(
            "Generate user stories for: " + requirement.getDescription(),
            BusinessSpecification.class
        );

        // 2. 🚀 UPDATED PROMPT: Requesting both High-Level Infrastructure and DB Layouts
     // Inside ProjectOrchestratorAgent.java -> orchestrateFlow()
     // Inside ProjectOrchestratorAgent.java -> orchestrateFlow()
        TechnicalAndBusinessSpec combinedSpec = context.ai().withDefaultLlm().createObject(
            """
            You are an Enterprise Software Architect. Based on these user stories: %s
            
            Populate all properties in the returned JSON target schema object perfectly:
            1. techStack: Recommended technologies.
            2. databaseSchema: Raw DDL commands.
            3. architectureDiagramMarkdown: High-level logical tier flow.
            4. databaseDiagramMarkdown: ER diagram layout.
            5. deploymentDiagramMarkdown: Physical cloud topography map.
            6. sequenceDiagramMarkdown: Key request sequence interactions.
            
            ⚠️ MERMAID DIAGRAM STRING CONSTRAINTS (CRITICAL FOR PARSING):
            - When writing labels that contain spaces or parentheses, wrap the label text in standard escaped double quotes inside the shape brackets.
            - Example syntax to use inside the JSON string properties:
              * Use exactly: A[\\\"Client Application\\\"]
              * Use exactly: B[\\\"Spring Boot API\\\"]
            - Start the diagram strings directly with their core definitions ('graph LR', 'graph TD', 'sequenceDiagram', 'erDiagram'). Do NOT use markdown code fences inside the properties.

            ⚠️ DEPLOYMENT DIAGRAM RULES:
            - Start directly with 'graph TD'.
            - Use escaped double quotes for node shapes. E.g., K8s[\\\"Kubernetes Pod\\\"]

            ⚠️ SEQUENCE DIAGRAM RULES:
            - Start directly with 'sequenceDiagram'.
            - Example trace: User->>Gateway: Request

            ⚠️ DATABASE ERD RULES:
            - Start directly with 'erDiagram'.
            - Attributes must use lowercase types: string, int, float, boolean, date. (No commas or semicolons).
            """.formatted(businessSpec.getUserStories()),
            TechnicalAndBusinessSpec.class
        );


     // Render and write all four image targets out to the resources directories
        long ts = System.currentTimeMillis();
        diagramStorageService.renderAndSaveDiagram(combinedSpec.getArchitectureDiagramMarkdown(), "high-level-arch-" + ts);
        diagramStorageService.renderAndSaveDiagram(combinedSpec.getDatabaseDiagramMarkdown(), "database-erd-" + ts);
        diagramStorageService.renderAndSaveDiagram(combinedSpec.getDeploymentDiagramMarkdown(), "deployment-topography-" + ts);
        diagramStorageService.renderAndSaveDiagram(combinedSpec.getSequenceDiagramMarkdown(), "transaction-sequence-" + ts);

        // Assemble the single markdown output payload block for the UI view container pane
        CompleteProjectBlueprint blueprint = new CompleteProjectBlueprint();
        blueprint.setSystemArchitecture("""
            # 🚀 Unified Solution Blueprints
            
            ## 💻 Core Technology Stack
            %s
            
            ## 🌐 High-Level Logical Layout
            ```mermaid
            %s
            ```
            
            ## 🏗️ Physical Deployment Infrastructure Topography
            ```mermaid
            %s
            ```
            
            ## 🔄 Core Request/Response Runtime Lifecycle Flow
            ```mermaid
            %s
            ```
            
            ## 🗄️ Relational Database Entity Model (ERD)
            ```mermaid
            %s
            ```
            
            ## 📝 Primary Database DDL Schema Structure
            %s
            """.formatted(
                combinedSpec.getTechStack(),
                combinedSpec.getArchitectureDiagramMarkdown(),
                combinedSpec.getDeploymentDiagramMarkdown(), // Emits deployment block
                combinedSpec.getSequenceDiagramMarkdown(),   // Emits sequence block
                combinedSpec.getDatabaseDiagramMarkdown(),
                combinedSpec.getDatabaseSchema()
            ));
            
        blueprint.setBusinessCase(businessSpec.getUserStories());
        
        return blueprint;
    }
}