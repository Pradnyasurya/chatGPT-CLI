package com.surya;

public record ChatGPTResponseChoice(
        String text,
        int index,
        Object logprobs,
        String finish_reason) {
}
