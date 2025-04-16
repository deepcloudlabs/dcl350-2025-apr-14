package com.example.hr.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;

public class HrAsyncRestClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		var httpClient = HttpClient.newBuilder().build();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4001/hr/api/v1/employees/89284227136")).build();
		httpClient.sendAsync(request, BodyHandlers.ofString())
		          .thenAcceptAsync(response -> System.out.println(response.body()));
		TimeUnit.SECONDS.sleep(1);
	}

}
