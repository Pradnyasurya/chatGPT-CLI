package com.surya;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ChatgptCLI {

    public static void main(String[] args) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your query: ");
        String queryString = sc.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest("text-davinci-001", queryString, 1, 100);
        String input = objectMapper.writeValueAsString(chatGPTRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer " + dotenv.get("OPEN_AI_API_KEY"))
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200){
             ChatGPTResponse chatGPTResponse = objectMapper.readValue(response.body(), ChatGPTResponse.class);
             String answer = chatGPTResponse.choices()[chatGPTResponse.choices().length-1].text();
             if (!answer.isEmpty()){
                 System.out.println(answer.replace("\n", "").trim());
             }
        } else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }

    }
}

