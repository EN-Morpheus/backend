package com.imaginecup.morpheus.openai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class OpenaiServiceImpl implements OpenaiService {

    private final RestTemplate restTemplate;
    private final String DALLE_API_URL = "https://api.openai.com/v1/images/generations";
    private final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.key}")
    private String OPEN_AI_KEY;

    public String generatePicture(String prompt) {
        HttpHeaders headers = setHeader();

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("prompt", prompt);
        requestBody.put("model", "dall-e-3");
        requestBody.put("n", 1);
        requestBody.put("size", "1024x1024");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = getResponse(DALLE_API_URL, request);
        return response.getBody();
    }

    @Override
    public String connectGpt(String systemPrompt, String userPrompt) {
        HttpHeaders headers = setHeader();
        Map<String, Object> responseFormat = getResponseFormat();
        List<Map<String, String>> messages = getMessages(systemPrompt, userPrompt);

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", "gpt-4-1106-preview");
        requestBody.put("response_format", responseFormat);
        requestBody.put("message", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = getResponse(GPT_API_URL, request);
        return response.getBody();
    }

    private ResponseEntity<String> getResponse(String url, HttpEntity<Map<String, Object>> request) {
        try {
            return restTemplate.postForEntity(url, request, String.class);
        } catch (RestClientException e) {
            throw new RestClientException("OpenAI API 호출 중 오류가 발생하였습니다.", e);
        }
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        return headers;
    }

    private Map<String, Object> getResponseFormat() {
        Map<String, Object> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");

        return responseFormat;
    }

    private List<Map<String, String>> getMessages(String systemPrompt, String userPrompt) {
        List<Map<String, String>> messages = new ArrayList<>();

        // 첫 번째 메시지 추가
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemPrompt);
        messages.add(systemMessage);

        // 두 번째 메시지 추가
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userPrompt);
        messages.add(userMessage);

        return messages;
    }

}
