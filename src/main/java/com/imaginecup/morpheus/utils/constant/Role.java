package com.imaginecup.morpheus.utils.constant;

public enum Role {
    SYSTEM_AUTHOR("지금부터 너는 세계적인 어린이들을 위한 동화책을 쓰는 작가야"),
    USER_TOPIC("다음 주제와 조건에 맞춰서 전체적인 동화책의 줄거리, 소재, 구성, 등장인물, 언어적 표현에 대해서도 구체적으로 작성해 줘.\n" +
            "주제: %s.\n" +
            "주인공: 이름: %s, 소개: %s, 환경: %s, 자기소개: %s.\n" +
            "조건1: 가이드라인을 참고하여 줄거리를 작성해 줘.\n" +
            "조건2: story 부분은 10문장에서 15문장 사이로 대략적인 줄거리 및 동화책의 설명을 작성해 줘.\n" +
            "조건3: subjectMatter 부분은 동화책의 소재에 대해 구체적으로 작성해 줘.\n" +
            "조건4: plot 부분은 동화책의 구성(plot)에 대해 구체적으로 작성해 줘.\n" +
            "조건5: characters 부분은 동화책의 등장인물에 대해 구체적으로 작성해 줘.\n" +
            "조건6: linguisticExpression 부분은 동화책의 언어적 표현에 대해 구체적으로 작성해 줘.\n" +
            "조건7: 다른 텍스트 없이 json형식으로만 답변해 줘.\n" +
            "조건8: 이 동화책의 제목도 정해 줘.\n" +
            "조건9: json형식은 다음과 같아. { title: title, story: story, subjectMatter: subjectMatter, plot: plot, characters: characters, linguisticExpression: linguistic expression }\n" +
            "가이드라인 : 소재: 소재는 주제를 전달하기 위해 사용되는 재료로, 예를 들어 마녀, 왕, 도깨비, 형제자매 등이 옛이야기 그림책의 주된 소재입니다. 아동의 일상생활이나 경험과 관련된 소재 사용이 좋습니다.\n" +
            "구성 (Plot): 구성은 이야기가 전개되는 방식을 의미합니다. 나열식(기승전결), 누적식(반복과 시간의 흐름에 따른 요소의 추가), 연쇄식(원인과 결과의 사건 전개), 순환식(이야기가 원점으로 돌아오는 형식), 삽화식(사건 간 관련성 없이 여러 일화와 사건 소개) 등 다양한 구성 방식이 있습니다.\n" +
            "등장인물: 그림책의 주인공은 주로 아동 혹은 아동다운 성격을 가진 동물, 장난감, 사물 등이 의인화된 인물입니다. 등장인물의 성격은 매력적이고 실제적일수록 기억에 오래 남으며, 아동이 공감하고 감동을 받기 쉽습니다.\n" +
            "언어적 표현: 문장은 짧고 간결하며, 의미가 충분히 전달되어야 합니다. 리듬감 있는 언어 표현, 반복, 후렴구 사용, 의성어와 의태어 사용 등이 동화책의 매력을 높일 수 있습니다."),


    private final String prompt;

    Role(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
