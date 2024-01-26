package com.imaginecup.morpheus.utils.constant;

public enum AnimationStyle {

    DISNEY("in the classic Disney animation style."),
    PIXAR("in the distinctive three-dimensional CGI style of Pixar animations."),
    GHIBLI("in the distinctive Studio Ghibli animation style."),
    CARTOON("in the American cartoon style."),
    SIMPSOM("in the style of the American animated show The Simpsons."),
    WEBTOON("in the Korean webtoon style.");

    private String style;

    AnimationStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

}
