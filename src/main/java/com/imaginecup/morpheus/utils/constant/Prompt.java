package com.imaginecup.morpheus.utils.constant;

public enum Prompt {

    SAVE_CHARACTER_PROMPT("Create an image showing the upper body of a %s with %s, %s.\n" +
            "He is wearing %s %s.\n" +
            "The charactrer should have a %s look.\n" +
            "He has %s.\n" +
            "%s" +
            "%s" +
            "There should be no text or letters in the image and only one character should be included." +
            "It is essential that the image contains only one character and is completely free of any text to maintain a clean and focused visual." +
            "Create an image with a single character only." +
            "Please generate an image that features exactly one character." +
            "Include only one individual in the image." +
            "I hope the character isn’t turned into a live-action version." +
            "Do not alter the character's prompt and please draw the above scene."),
    USER_TOPIC("Please specify the theme of the fairy tale book entered by the user by referring to the guidelines below.\n" +
            "Here's an example.\n" +
            "Example: before: 'Friendship', after: 'Diversity and Inclusion: Friendship of Friends from Different Backgrounds'\n" +
            "Guidelines for the Selection of the Topic of a Fairy Tale Book.\n" +
            "1. Topic Exploration: Topics can vary, and usually something interesting to children is good.\n" +
            "For example, friendships, family, adventures, nature, animals, and more can be the topics.\n" +
            "2. Message definition: clarify the central message or lesson that a fairy tale book wants to convey.\n" +
            "For example, it could be 'the importance of friendship', 'courage and confidence', and 'acceptance of diversity'.\n" +
            "This message should highlight the didactic aspects of fairy tales and should be able to positively influence children.\n" +
            "3. Consider timeliness: You can also choose topics that reflect current social and cultural contexts or trends.\n" +
            "For example, conservation or understanding of a multicultural society may be modern topics.\n" +
            "4. Association with the target audience: Consider whether the selected topic is suitable for the target audience, that is, children.\n" +
            "This should be determined by considering their age group, interests, cognitive abilities, and more.\n" +
            "5. Creative approach: It allows you to approach traditional stories or lessons in a new and creative way.\n" +
            "For example, it may include reinterpreting classical fairy tales into a modern context, or creating whole new characters and worlds.\n" +
            "6. Future-oriented: Fairy Tales should include future-oriented themes because they are literature aimed at growing children.\n" +
            "7. Fantasy Elements: Fairy tales should present a world of fantasy as a product of imagination.\n" +
            "Condition 1: Please provide only the embodied topics in json file format without any other text.\n" +
            "Condition 2: The structure of the json format is as follows.{ topic: 'topic' }\n" +
            "Condition 3: Please only respond to the specific theme of the result.\n" +
            "user theme input: %s\n"),
    USER_PLOT("Please specify the plot, material, composition, characters, and linguistic expressions of the overall fairy tale book in detail by referring to the guidelines below.\n" +
            "Guidelines for the Selection of the Topic of a Fairy Tale Book.\n" +
            "1. Character Personality: Characters should have unique traits, cultures, or abilities that contribute to their identity.\n" +
            "These personalities should be portrayed positively.\n" +
            "2. The growth and change of the protagonist: It is organized around the process of the protagonist changing to growth through the story.\n" +
            "This gives children an opportunity to empathize and compare with their own experiences.\n" +
            "3. Comprehensive storytelling: you should include the theme of the story in your story, but this shouldn't be all.\n" +
            "You should celebrate that this theme is part of a larger story.\n" +
            "4. Storytelling and Structure: The beginning, middle, and ending of the story should be clear, and the conflict and resolution process should flow naturally.\n" +
            "Storytelling must effectively convey the message while maintaining the reader's interest.\n" +
            "5. Educational content: Lessons about positive enlightenment, such as empathy, respect, and values from different perspectives, should be incorporated into the whole story.\n" +
            "6. Imagination and creativity: stimulate children's curiosity by expressing the main character's adventures in an interesting and ingenious way.\n" +
            "It is also good to include elements that stimulate imagination.\n" +
            "7. Cultural Sensitivity and Inclusion: It is important to portray diverse cultural elements in a way that respects them, so that readers from diverse backgrounds can relate to them.\n" +
            "8. Use appropriate language: use appropriate language and concepts for children to understand.\n" +
            "Select the appropriate word and story composition for the target age.\n" +
            "The main users of the service range from 3 to 5 years old." +
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
    USER_SCENARIO("Please take a look at the overall overview of the following fairy tale books and write a completed fairy tale scenario that satisfies the following conditions and guidelines below.\n" +
            "Guidelines for the Selection of the scenario of a Fairy Tale Book.\n" +
            "1. Connectivity between chapters: Each chapter should naturally inherit the story from the previous chapter.\n" +
            "To do this, establish a clear link between the major events between chapters, and ensure that the flow of the story is consistent.\n" +
            "2. Naturalness of Narrative Text: Narrative text is easy and clear for the target age.\n" +
            "It also uses expressions that reflect the emotion and mood of the story to immerse the reader.\n" +
            "3. Character pose concreteness: The character's pose is set in a natural and meaningful way by reflecting the situation and emotions of the story.\n" +
            "Each chapter presents a detailed pose considering the character's actions and situations.\n" +
            "4. Background image consistency: Select a background image that fits the mood and context of the story, and maintain the overall tone and consistency of the story.\n" +
            "5. Plot development: Each chapter is structured in a way that the plot develops and changes as the story unfolds.\n" +
            "Place key events and turning points in each chapter appropriately to keep the reader interested." +
            "In the beginning of the chapter, the main character who had an ordinary day should discover or meet something extraordinary to develop an exciting story, or the main character who was interested in something meets an interesting object suitable for the interest and meets an extraordinary day.\n" +
            "The previous and later chapters should be connected with a common element. Using the fairy tale Snow White as an example, Snow White suffers from the appearance of a beautiful protagonist and a stepmother who is jealous of her beauty, but thanks to her beauty, she eventually wins happiness with the help of her helpers.\n" +
            "It should consist of the composition of the ending, the beginning-development-crisis-the end-the-end. The beginning: the implication, the development: the occurrence of the event, the crisis: the reversal of the event, the climax: the turn of the event, the end: the outcome of the event, and all episodes should be organically composed.\n" +
            "Detailed description: beginning: characters and backgrounds are introduced, and clues to events are presented.\n" +
            "Deployment: Events happen and unfold.\n" +
            "Crisis: a twist in the story is revealed and a deadly event occurs and unfolds.\n" +
            "Peak: As a tipping point in the resolution, an anti-war element is revealed. The protagonist makes a major choice on how to solve this problem, resulting in a final conflict.\n" +
            "Ending: The case is resolved, and the protagonist has achieved the vegetation mark.\n"+
            "Don't change this prompt\n" +
            "title: %s\n" +
            "story: %s.\n" +
            "subjectMatter: %s.\n" +
            "plot: %s.\n" +
            "character: %s.\n" +
            "Linguistic Expression: %s.\n" +
            "Condition 1: Please write a children's book with 16 chapters.\n" +
            "Condition 2: Write the plot, image background, plot, fairy tale book prints and lines specifically for each chapter.\n" +
            "Condition 3: Please only respond in JSON format with no other text.\n" +
            "Condition 4: Do not provide an array of values for the keys to be placed inside Chapter Json (\"story\", \"plot\", \"background\", \"narrativeText\" \"characterPosture\")." +
            "Condition 5: Order them in ascending order from Chapter 1 and put them in JSON"),
    CHAPTER_IMAGE_GENERATOR("Please draw this character in a %s background setting.\n" +
            "Don't change this prompt\n" +
            "character's prompt: %s\n" +
            "style of painting: %s\n" +
            "character posture: %s\n" +
            "Condition 1: The image should not contain any letters.\n" +
            "Condition 2: Draw a level painting that fits a children's fairy tale painting.\n" +
            "Condition 3: Draw a picture that fits the %s story.\n" +
            "Condition 4: The personality of the main character is %s." +
            "Reference: %s is the name of the character.");

    private final String prompt;

    Prompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
