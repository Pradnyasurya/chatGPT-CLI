package com.surya;

public record ChatGPTRequest(String model, String prompt, int temperature, int max_tokens) {

}
