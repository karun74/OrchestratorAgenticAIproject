package com.example.demo;




import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.common.OperationContext;

@Agent(description = "Enterprise Software Architect focused on dynamic infra layouts.")
public class SoftwareArchitectAgent {

    /**
     * 🚀 FIXED: By marking this as an AchievesGoal, Embabel explicitly unlocks 
     * the ability to resolve and pass OperationContext into the method parameters.
     */
    @AchievesGoal(description = "Generate a completely dynamic technical layout based on business needs")
    @Action
    public TechnicalAndBusinessSpec designSystem(BusinessSpecification businessSpec, OperationContext context) {
        
        // ❌ NO HARDCODING: We call the LLM dynamically to determine the tech stack
        return context.ai().withDefaultLlm().createObject(
            """
            You are an Enterprise Software Architect. Based on these user stories:
            %s
            
            1. Recommend an optimal, modern tech stack (Do not hardcode, choose what fits best).
            2. Design a clean PostgreSQL database schema matching the needs.
            3. Generate a raw, valid Mermaid.js diagram starting with 'sequenceDiagram' or 'erDiagram' (no backticks).
            """.formatted(businessSpec.getUserStories()),
            TechnicalAndBusinessSpec.class
        );
    }
}
