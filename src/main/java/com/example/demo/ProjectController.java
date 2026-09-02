package com.example.demo;



import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfigurationSource;

import com.embabel.agent.api.invocation.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController
@RequestMapping({
    "/api/orchestrator", 
 })
public class ProjectController {

	
    private final AgentPlatform agentPlatform;

    public ProjectController(@Lazy AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
    }
 // 🚀 CROSS ORIGIN ACCESS ENABLED FOR YOUR MICRO FRONTEND PORT
    @CrossOrigin(origins = "https://d3042ckvga29du.cloudfront.net",allowedHeaders = {"content-type", "Authorization", "X-Requested-With"}, 
            methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}) 
    @PostMapping("/build-blueprint")
    public ResponseEntity<CompleteProjectBlueprint> generateBlueprint(@RequestBody ProjectRequirement requirement) {
        
        // Target the final aggregate object requested from the Orchestrator
        var invocation = AgentInvocation.builder(agentPlatform)
                .build(CompleteProjectBlueprint.class);

        // Kicks off the autonomous multi-agent pipeline
        CompleteProjectBlueprint blueprint =  invocation.invoke(requirement);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "https://d3042ckvga29du.cloudfront.net");
        responseHeaders.set("Access-Control-Allow-Headers", "content-type, Authorization, X-Requested-With");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        // Return the payload wrapped nicely with your explicit headers
        return new ResponseEntity<>(blueprint, responseHeaders, HttpStatus.OK);
    }
    
    
    

}

