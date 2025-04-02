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

	public Mono<Void> tokenValidationAPIExchange(HttpHeaders httpHeaders, ServerWebExchange exchange,
			GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		return webClientBuild().build().post()
				.uri("lb://esecurity/esecurity/validate")
				.headers(header -> header.addAll(httpHeaders)).exchangeToMono(response -> {

					HttpHeaders responseHeaders = response.headers().asHttpHeaders();
					String userId = responseHeaders.getFirst("X-user-X-Id");

					return response.bodyToMono(String.class).flatMap(body -> {

						ServerHttpRequest mutatedRequest = request.mutate()
								.header("X-User-ID", userId != null ? userId : "unknown").build();

						return chain.filter(exchange.mutate().request(mutatedRequest).build());
					});
				}).onErrorResume(error -> {
					HttpStatusCode errorCode;
					String errorMsg;
					if (error instanceof WebClientResponseException) {
						WebClientResponseException webClientException = (WebClientResponseException) error;
						errorCode = webClientException.getStatusCode();
						errorMsg = webClientException.getResponseBodyAsString();
						System.out.println(webClientException.getResponseBodyAsString());
					} else {
						errorCode = HttpStatus.BAD_GATEWAY;
						errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
					}

					return onError(exchange, String.valueOf(errorCode.value()), errorMsg, "JWT Authentication Failed",errorCode);
				});
	}

	private Mono<Void> onError(ServerWebExchange exchange, String errCode, String err, String errDetails,HttpStatusCode httpStatus) {
		
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
