package com.surya;

public record ChatGPTResponseUsage(
        int prompt_tokens,
        int completion_tokens,
        int total_tokens) {
}
