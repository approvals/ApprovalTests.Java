package com.spun.util;

public class MarkdownTableContents implements MarkdownTableElement {
    private String contents;
    private int padUntil;

    public MarkdownTableContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return " " + StringUtils.pad(contents, padUntil) + " ";
    }

    public int getLength() {
        return contents.length();
    }

    public void setPadding(int length) {
        padUntil = length;
    }
}
