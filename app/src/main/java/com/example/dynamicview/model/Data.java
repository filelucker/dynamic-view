package com.example.dynamicview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("hint")
    @Expose
    private String hint;
    @SerializedName("operation")
    @Expose
    private String operation;
    @SerializedName("minHeight")
    @Expose
    private int minHeight;
    @SerializedName("getField")
    @Expose
    private String getField;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getGetField() {
        return getField;
    }

    public void setGetField(String getField) {
        this.getField = getField;
    }
}