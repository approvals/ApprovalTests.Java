package com.spun.util;

public class MarkdownTableContents {
    private String contents;

    public MarkdownTableContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return " " + contents + " ";
    }
}
