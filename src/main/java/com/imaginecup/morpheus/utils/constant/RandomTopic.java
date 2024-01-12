package com.imaginecup.morpheus.utils.constant;

import java.util.ArrayList;
import java.util.List;

public enum RandomTopic {

    SHARING("The Importance of Sharing: A Story of Siblings Learning to Share Their Toys"),
    BRAVERY("Bravery in Everyday Life: A Tale of a Child Overcoming Fear of the Dark"),
    JOY_OF_LEARNING("The Joy of Learning: Adventures of a Curious Squirrel in School"),
    UNDERSTANDING_DIFFERENCES("Understanding Differences: A Journey of Animals with Unique Abilities"),
    FRIENDSHIP_ACROSS_CULTURES("Friendship Across Cultures: Children from Different Countries Forming Bonds"),
    KINDNESS_TO_ANIMALS("Kindness to Animals: A Young Girl's Quest to Help Stray Dogs"),
    VALUE_OF_PATIENCE("The Value of Patience: A Rabbit's Adventure in Learning to Wait"),
    RESPECT_FOR_ELDERS("Respect for Elders: A Boy's Discovery of Wisdom from Grandparents"),
    MAGIC_OF_TEAMWORK("The Magic of Teamwork: Insects Working Together to Build a Home"),
    OVERCOMING_JEALOUSY("Overcoming Jealousy: The Story of Two Friends and a Coveted Toy"),
    PERSEVERANCE_PAYS_OFF("Perseverance Pays Off: A Duckling's Journey to Learn to Fly"),
    HONESTY_IS_THE_BEST_POLICY("Honesty is the Best Policy: A Tale of a Child's Truthful Adventure"),
    BEAUTY_OF_NATURE("The Beauty of Nature: A Day in the Life of Forest Creatures"),
    LEARNING_RESPONSIBILITY("Learning Responsibility: A Young Farmer Taking Care of Farm Animals"),
    POWER_OF_IMAGINATION("The Power of Imagination: A Child's Adventure in a Fantasy World"),
    COPING_WITH_LOSS("Coping with Loss: A Gentle Story about a Child and a Lost Toy"),
    STRENGTH_IN_FORGIVENESS("The Strength in Forgiveness: Siblings Learning to Forgive Each Other"),
    GRATITUDE_FOR_EVERYDAY_THINGS("Gratitude for Everyday Things: A Child's Journey of Thankfulness"),
    HELPING_OTHERS_IN_NEED("Helping Others in Need: A Story of a Child Volunteering in the Community"),
    EMBRACING_CHANGE("Embracing Change: A Butterfly's Transformation and Its Lessons");

    private final String description;

    RandomTopic(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public static List<String> getTopicsAsList() {
        List<String> topics = new ArrayList<>();
        for (RandomTopic topic : RandomTopic.values()) {
            topics.add(topic.getDescription());
        }
        return topics;
    }
}
