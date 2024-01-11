package com.imaginecup.morpheus.utils.constant;

public enum Prompt {

    SAVE_CHARACTER_PROMPT("A character in the style of %s animation.\n" +
            "The protagonist's introduction is %s.\n" +
            "The protagonist's appearance is a %s.\n" +
            "Do not alter the character's prompt.\n" +
            "Don't include letters in the image.\n" +
            "There should be one person in the image"),
    USER_TOPIC("Please specify the plot, material, composition, characters, and linguistic expressions of the overall fairy tale book in accordance with the following topics and conditions.\n" +
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
