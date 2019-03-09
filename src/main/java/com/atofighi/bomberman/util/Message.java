package com.atofighi.bomberman.util;

public class Message {
    public enum Type {
        NULL, MONSTER, ERROR, JOINED, CHAT, MAP, ACTION, JOIN, NEW, LIST, TEST, NEW_BOMBERMAN, NEW_GAME
    }

    private Type type;
    private String content;
    private String id;
    private static RandomString random = new RandomString(30);

    public Message(String id, Type type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public Message(Type type, String content) {
        this(random.nextString(), type, content);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
