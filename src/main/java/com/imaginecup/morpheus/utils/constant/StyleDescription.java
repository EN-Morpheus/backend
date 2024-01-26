package com.imaginecup.morpheus.utils.constant;

public enum StyleDescription {

    DISNEY("Aim for a timeless, whimsical look with expressive eyes and fluid, graceful lines, characteristic of Disney's traditional hand-drawn animation."),
    PIXAR("Capture the hallmark Pixar look with its three-dimensional, computer-generated imagery, soft yet detailed textures, and lifelike yet stylized facial features."),
    GHIBLI("Ghibli's style is known for its detailed, hand-drawn art with a touch of realism and fantasy."),
    AMERICAN_CARTOON("The American cartoon style is characterized by bold lines, exaggerated features, and a more humorous, less realistic approach."),
    SIMPSOM("Emulate the show's iconic style with its bold and bright color palette, over-exaggerated features, and satirical expression."),
    KOREAN_WEBTOON("The webtoon style should reflect the unique blend of realism and stylization found in Korean digital comics, with crisp lines, detailed expressions, and vibrant colors.");

    private String description;

    StyleDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByName(String name) {
        for (StyleDescription styleDescription : StyleDescription.values()) {
            if (styleDescription.name().equalsIgnoreCase(name)) {
                return styleDescription.getDescription();
            }
        }

        return null;
    }

}
