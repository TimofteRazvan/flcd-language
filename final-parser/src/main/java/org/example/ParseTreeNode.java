package org.example;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeNode {
    private static int indexCounter = 0;

    private int index;
    private String value;
    private ParseTreeNode parent;
    private List<ParseTreeNode> children;

    public ParseTreeNode(String value) {
        this.index = ++indexCounter;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public ParseTreeNode getParent() {
        return parent;
    }

    public List<ParseTreeNode> getChildren() {
        return children;
    }

    public void addChild(ParseTreeNode child) {
        if (child != null) {
            child.parent = this;
            this.children.add(child);
        }
    }

    public ParseTreeNode getSibling() {
        if (parent == null) {
            return null;
        }

        List<ParseTreeNode> siblings = parent.getChildren();
        int index = siblings.indexOf(this);

        if (index < siblings.size() - 1) {
            return siblings.get(index + 1);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Index: " + index +
                ", Value: " + value +
                ", Parent: " + (parent != null ? parent.getIndex() : "null") +
                ", Sibling: " + (getSibling() != null ? getSibling().getIndex() : "null");
    }

    public void setParent(ParseTreeNode currentNode) {
        this.parent = currentNode;
    }
}