package com.book.gateway.authCall;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientAuthCall {

	@Bean
	@LoadBalanced
	 WebClient.Builder webClientBuild() {
		return WebClient.builder();
	}

	public Mono<Void> tokenValidationAPIExchange(HttpHeaders httpHeaders, ServerWebExchange exchange,GatewayFilterChain chain) {
		System.out.println("httpHeaders=="+httpHeaders);
	    ServerHttpRequest request = exchange.getRequest();
	    return webClientBuild().build().post()
	        .uri("lb://esecurity/esecurity/validate")
	        .headers(header -> header.addAll(httpHeaders))
	        .retrieve()
	        .bodyToMono(String.class)
	        .flatMap(response -> {  // Use flatMap instead of map for non-blocking chaining
	            System.out.println("=====================:: " + response);

	            // Mutate the request and rebuild it
	            ServerHttpRequest mutatedRequest = request.mutate()
	                .header("X-User-ID", "datadata")  // Replace with actual data if needed
	                .build();

	            // Continue the filter chain with the modified request
	            return chain.filter(exchange.mutate().request(mutatedRequest).build());
	        })
//            .map(response -> {
////            	String userId = request.getHeaders().getFirst("X-User-ID");
//            	System.out.println("=====================:: "+response);
//                exchange.getRequest().mutate().header("X-User-ID", "datadata")
//            	.build();
//                
//                return exchange;
//            })
//            .flatMap(chain::filter)
            .onErrorResume(error -> {
            	System.out.println(error);
	            HttpStatusCode errorCode;
	            String errorMsg;
	            if (error instanceof WebClientResponseException) {
	                WebClientResponseException webClientException = (WebClientResponseException) error;
	                errorCode = webClientException.getStatusCode();
	                errorMsg = webClientException.getResponseBodyAsString();
	                System.out.println(webClientException.getResponseBodyAsString());
	            } else {
	            	 System.out.println("-----------------------------");
	                errorCode = HttpStatus.BAD_GATEWAY;
	                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
	            }
	            
	            return onError(exchange, String.valueOf(errorCode.value()), errorMsg, "JWT Authentication Failed", errorCode);
	        });
	}

    private Mono<Void> onError(ServerWebExchange exchange, String errCode, String err, String errDetails, HttpStatusCode httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            response.getHeaders().add("Content-Type", "application/json");
            byte[] bytes = err.getBytes();  
            return response.writeWith(Mono.just(bytes).map(t -> dataBufferFactory.wrap(t)));

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response.setComplete();
    }

	
}
