package com.openai.supi.ecommerce;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class ProductCategorizes {

  public static void main(String... args) {
    var reader = new Scanner(System.in);

    System.out.println("Type valid categories:");
    var categories = reader.nextLine();
    while (true) {
      System.out.println("Type the product name:\n");
      var user = reader.nextLine();
      var system = """
      you are a product categorize and must answer only category name informed
      
      Choose a category bellow:
      
      $s
      
      ####### usage example:
      Question: Soccer ball
      Answer: sports
      """.formatted(categories);

      request(user, system);
    }
  }

  private static void request(String user, String system) {
    var key = System.getenv("OPENAI_API_KEY");
    var service = new OpenAiService(key, Duration.ofSeconds(30));

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
