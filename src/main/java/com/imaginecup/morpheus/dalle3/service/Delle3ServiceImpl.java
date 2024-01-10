package com.imaginecup.morpheus.dalle3.service;

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

import java.util.HashMap;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class Delle3ServiceImpl implements Delle3Service {

    private final RestTemplate restTemplate;

    @Value("${openai.key}")
    private String OPEN_AI_KEY;

    public String generatePicture(String prompt) {
        String url = "https://api.openai.com/v1/images/generations";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        Map<String, Object> requestBody = new HashMap<>();

        // 요청 질문
        requestBody.put("prompt", prompt);

        // 요청에 사용될 모델 설정
        requestBody.put("model", "dall-e-3");

        // 완료시 생성할 최대 토큰수
        requestBody.put("n", 1);

        requestBody.put("size", "1024x1024");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RestClientException("OpenAI API 호출 중 오류가 발생하였습니다.", e);
        }
    }




}
