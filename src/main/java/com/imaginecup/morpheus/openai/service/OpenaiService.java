package com.imaginecup.morpheus.openai.service;



public interface OpenaiService {

    String generatePicture(String prompt);

    String connectGpt(String userPrompt);

}
