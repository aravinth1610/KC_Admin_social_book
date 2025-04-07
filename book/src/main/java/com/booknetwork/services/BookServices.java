package com.booknetwork.services;
 
import java.util.Map;
 
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
 
import com.booknetwork.config.WebClientCall;
 
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Service
@Slf4j
@AllArgsConstructor
public class BookServices {
 
	private final WebClientCall webClientcalls;
	public void bookData(Map<String,String> bookData) {
		log.info("bookData Args Data {}", bookData);
		System.out.println(bookData);
	}
	public Map<String, Object> bookInv() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		Map<String, Object> bookInventoy = webClientcalls.makeAPIClientCalls("lb://bookInventory/bookInventory/", "", Map.class, httpHeaders, "GET");
		System.out.println(bookInventoy);

		return bookInventoy;
	}
}