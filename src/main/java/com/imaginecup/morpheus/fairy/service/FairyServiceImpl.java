package com.imaginecup.morpheus.fairy.service;

import com.imaginecup.morpheus.utils.constant.RandomTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FairyServiceImpl implements FairyService {

    @Override
    public List<String> getTopics() {
        return RandomTopic.getTopicsAsList();
    }

}
