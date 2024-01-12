package com.imaginecup.morpheus.utils.constant;

public enum Prompt {

    SAVE_CHARACTER_PROMPT("Please draw this character. Don't change this prompt\n" +
            "A character in the style of %s animation.\n" +
            "The protagonist's introduction is %s.\n" +
            "The protagonist's appearance is a %s.\n" +
            "Do not alter the character's prompt.\n" +
            "Don't include letters in the image.\n" +
            "There should be one person in the image"),
    USER_TOPIC("From now on, please specify the fairy tale topic entered by the user by referring to the guidelines below.\n" +
            "And please provide the response values according to the following conditions.\n" +
            "Condition 1: Please provide only materialized topics in json file format without any other text.\n" +
            "Condition 2: The structure of the json format is as follows. {topic: topic}\n" +
            "Condition 3: Please only respond to the specific topics that are the result values.\n" +
            "Examples to specify include: before: friendship, after: diversity and inclusion: friendship of friends from different backgrounds.\n" +
            "User Input Topic: %s.\n" +
            "Guidelines: - Topic Exploration: Think about what you want to talk about. The topics can vary, and usually something interesting to children is good. For example, friendships, family, adventures, nature, animals, etc. can be the topics.\n" +
            "Message definition: clarify the central message or lesson that a fairy tale book intends to convey. For example, it could be the importance of friendship, courage and confidence, and acceptance of diversity. This message should emphasize the didactic aspects of a fairy tale and be able to positively influence children.\n" +
            "Considering Poeticity: You can also choose topics that reflect current social and cultural contexts or trends. For example, conservation or understanding of a multicultural society may be modern topics.\n" +
            "Relationship to target audience: Consider whether the chosen topic is appropriate for the target audience, i.e., children. This should be determined by considering their age group, interests, cognitive abilities, etc.\n" +
            "Creative approach: to approach traditional stories or lessons in a new and creative way. For example, reinterpreting classical fairy tales into modern contexts, or creating whole new characters and worlds."),
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
            "Condition 10: The inside of the Json value value written above should exist only as a String. The Json type should not exist inside Json.\n" +
            "Guidelines: Materials: Materials are materials used to convey a topic such as witches, kings, goblins, and siblings are the main materials in old story picture books. Materials related to children's daily lives and experiences are recommended.\n" +
            "Plot: Configuration refers to the way a story unfolds. There are many different compositional schemes, such as Listing (pre-conversion), Accumulation (addition of repetition and elements over time), Serial (event development of cause and effect), Circular (in which the story returns to square one), and Illustration (introduction of multiple anecdotes and events without inter-event relevance).\n" +
            "Character: The protagonist is a personified personification of animals, toys, objects, etc. that are mostly children or have childlike personalities. The more attractive and practical the character's personality is, the longer it lasts in the memory and the easier it is for a child to sympathize with and be touched.\n" +
            "Linguistic Expression: Sentences are short and concise, and should be fully conveyed in meaning. Rhythmic language expressions, repetitions, use of chorus, and onomatopoeia and mimicry can enhance the appeal of fairy tales."),
    USER_SCENARIO("Please take a look at the overall overview of the following fairy tale books and write a completed fairy tale scenario that satisfies the following conditions.\n" +
            "title: %s\n" +
            "story: %s.\n" +
            "subjectMatter: %s.\n" +
            "plot: %s.\n" +
            "character: %s.\n" +
            "Linguistic Expression: %s.\n" +
            "Condition 1: Please write a children's book with %d chapters.\n" +
            "Condition 2: Write the plot, image background, plot, fairy tale book prints and lines specifically for each chapter.\n" +
            "Condition 3: Please only respond in json file format with no other text.\n" +
            "Condition 4: json file format is as follows. [chapter1: {\"story\": \"plot\", \"plot\": \"configuration\", \"background\": \"background\", \"narrativeText\": \"fairy tale book fingerprint and dialogue\"}]\n" +
            "Condition 5: When answering in Jonson format, make sure the chapters are organized in an array. Examples are [chapter1: {}, chapter2: {}].\n" +
            "Condition 6: Order them in ascending order from Chapter 1 and put them in Jonson"),
    CHAPTER_IMAGE_GENERATOR("Please draw this character in a %s background setting.\n" +
            "Don't change this prompt\n" +
            "character's prompt: %s\n" +
            "Condition 1: The image should not contain any letters.\n" +
            "Condition 2: Draw a level painting that fits a children's fairy tale painting.\n" +
            "Condition 3: Draw a picture that fits the %s story.\n" +
            "Reference: %s is the name of the character.");

    private final String prompt;

    Prompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
