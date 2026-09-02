package com.example.demo;


import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.common.OperationContext;
import org.springframework.stereotype.Component;

@Component 
public class BusinessAnalystAgent {

    @Action 
    public BusinessSpecification analyzeRequirements(ProjectRequirement requirement, OperationContext context) {
        return context.ai().withDefaultLlm().createObject(
        		// Update parameter access to use standard getter
        		"Generate 3 User Stories and a cost estimate for: " + requirement.getDescription(),
            BusinessSpecification.class
        );
    }
}

