package edu.ifrs;

import java.util.Random;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/fault-tolerance")
public class FallbackExample {

    @GET
    @Path("/fallback")
    @Produces(MediaType.TEXT_PLAIN)
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(fallbackMethod = "fallback")
    public String fallbackAPI() {
        System.out.println("TRY");

        throw new RuntimeException("Not this time!");
    }

    public String fallback() {
        System.out.println("FALLBACK");
        return "Fallback!";
    }

    @GET
    @Path("/circuit-breaker")
    @Produces(MediaType.TEXT_PLAIN)
    @CircuitBreaker(requestVolumeThreshold = 2)
    public String circuitBreakerAPI() {
        Random rand = new Random();
        int randomNum = rand.nextInt(2) + 1;

        if(randomNum == 2) {
            throw new RuntimeException("BAD!");
        }

        return "GOOD!";
    }
}


