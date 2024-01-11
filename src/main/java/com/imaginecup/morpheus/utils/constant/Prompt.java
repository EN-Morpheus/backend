package com.imaginecup.morpheus.utils.constant;

public enum Prompt {

    SAVE_CHARACTER_PROMPT("A character in the style of %s animation.\n" +
            "The protagonist's introduction is %s.\n" +
            "The protagonist's appearance is a %s.\n" +
            "Do not alter the character's prompt.\n" +
            "Don't include letters in the image.\n" +
            "There should be one person in the image"),
    USER_TOPIC("지금부터 사용자가 입력한 동화 주제를 밑의 가이드라인을 참고하여 구체화 시켜 줘.\n" +
            "그리고 다음 조건에 맞춰서 응답값을 제시해 줘.\n" +
            "조건1: 다른 텍스트 없이 구체화된 주제만 json파일 형식으로 제공해 줘.\n" +
            "조건2: json 형식의 구조는 다음과 같아. { topic: topic }\n" +
            "조건3: 결과값인 구체화된 주제만 응답해 줘.\n" +
            "구체화 시키는 예시는 다음과 같아. 예시: before: 우정 , after: 다양성과 포용성: 서로 다른 배경을 가진 친구들의 우정.\n" +
            "사용자 입력 주제: %s.\n" +
            "가이드 라인: - 주제 탐색: 무엇에 대해 이야기하고 싶은지 생각해 보세요. 주제는 다양할 수 있으며, 보통 어린이들이 흥미를 느낄 만한 것이 좋습니다. 예를 들어, 우정, 가족, 모험, 자연, 동물 등이 주제가 될 수 있습니다.\n" +
            "메시지 정의: 동화책에서 전달하고자 하는 중심 메시지나 교훈을 명확히 합니다. 예를 들어, '우정의 중요성', '용기와 자신감', '다양성의 수용' 등이 될 수 있습니다. 이 메시지는 동화의 교훈적인 측면을 강조하고 어린이들에게 긍정적인 영향을 줄 수 있어야 합니다.\n" +
            "시의성 고려: 현재 사회적, 문화적 맥락이나 트렌드를 반영하는 주제를 선택할 수도 있습니다. 예를 들어, 환경 보호나 다문화 사회의 이해 등이 현대적인 주제일 수 있습니다.\n" +
            "대상 독자와의 연관성: 선택한 주제가 대상 독자, 즉 어린이들에게 적합한지 고려합니다. 이는 어린이들의 연령대, 관심사, 인지 능력 등을 고려하여 결정해야 합니다.\n" +
            "창의적 접근: 전통적인 이야기나 교훈을 새롭고 창의적인 방식으로 접근할 수 있습니다. 예를 들어, 고전 동화를 현대적인 맥락으로 재해석하거나, 전혀 새로운 캐릭터와 세계를 창조하는 것이 포함될 수 있습니다."),
    USER_PLOT("Please specify the plot, material, composition, characters, and linguistic expressions of the overall fairy tale book in accordance with the following topics and conditions.\n" +
            "Themes: %s.\n" +
            "Heroine: name: %s, Introduction: %s, Personality: %s.\n" +
            "Condition 1: Please refer to the guidelines and write the plot.\n" +
            "Condition 2: Please write a rough plot and a storybook description between 10 and 15 sentences for the story section.\n" +
            "Condition 3: Please write the subjectMatter part specifically about the material of the fairy tale book.\n" +
            "Condition 4: Please write the plot specifically about the plot of the fairy tale book.\n" +
            "Condition 5: Characters please write specifically about the characters in the fairy tale book.\n" +
            "Condition 6: Please write the linguistic expression of the fairy tale book in detail.\n" +
            "Condition 7: Please only answer in json format without any other text.\n" +
            "Condition 8: Please also decide the title of this children's book.\n" +
            "Condition 9: The json format is as follows. { title: title, story: story, subjectMatter: subjectMatter, plot: plot, characters: characters, linguisticExpression: linguistic expression }\n" +
            "Guidelines: Materials: Materials are materials used to convey a topic such as witches, kings, goblins, and siblings are the main materials in old story picture books. Materials related to children's daily lives and experiences are recommended.\n" +
            "Plot: Configuration refers to the way a story unfolds. There are many different compositional schemes, such as Listing (pre-conversion), Accumulation (addition of repetition and elements over time), Serial (event development of cause and effect), Circular (in which the story returns to square one), and Illustration (introduction of multiple anecdotes and events without inter-event relevance).\n" +
            "Character: The protagonist is a personified personification of animals, toys, objects, etc. that are mostly children or have childlike personalities. The more attractive and practical the character's personality is, the longer it lasts in the memory and the easier it is for a child to sympathize with and be touched.\n" +
            "Linguistic Expression: Sentences are short and concise, and should be fully conveyed in meaning. Rhythmic language expressions, repetitions, use of chorus, and onomatopoeia and mimicry can enhance the appeal of fairy tales."),
    USER_SCENARIO("Please take a look at the overall overview of the following fairy tale books and write a completed fairy tale scenario that satisfies the following conditions.\n" +
            "story: %s.\n" +
            "subjectMatter: %s.\n" +
            "plot: %s.\n" +
            "character: %s.\n" +
            "Linguistic Expression: %s.\n" +
            "Condition 1: Please write a children's book with %d chapters.\n" +
            "Condition 2: Write the plot, image background, plot, fairy tale book prints and lines specifically for each chapter.\n" +
            "Condition 3: Please only respond in json file format with no other text.\n" +
            "Condition 4: json file format is as follows. {chapter1: {story: plot, plot: configuration, background: background, narrativeText: fairy tale book fingerprint and dialogue}, chatter2: {story: plot, plot: configuration, background: background, narrativeText: fairy tale book fingerprint and dialogue}}\n" +
            "Condition 5: Please respond to each chapter to exist in the form of an array.");

    private final String prompt;

    Prompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
