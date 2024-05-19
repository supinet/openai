package com.openai.supi.ecommerce;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class IntegrationTest {
  public static void main(String... args) {

    var user = "Generate 5 products";
    var system = "You're a product generator for fake products for an ecommerce you must generate only name's products";

    var key = System.getenv("OPENAI_API_KEY");
    var service = new OpenAiService(key);

    var completionRequest = ChatCompletionRequest.builder()
        .model("gpt-4")
        .messages(List.of(
            new ChatMessage(ChatMessageRole.USER.value(), user),
            new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
        ))
        .build();
    service.createChatCompletion(completionRequest)
        .getChoices()
        .forEach(c -> System.out.println(c.getMessage().getContent()));
  }
}
