package com.ai.gpt.service;


import lombok.AllArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OpenAIService {

    private final OpenAiChatModel chatModel;

    public String queryFeatures(String productName) {
        try {
            return chatModel.call("Generate 10 Words regarding features for my query in single line" + productName);
        } catch (Exception ex) {
            return "Described By ChatGPT";
        }
    }
}
