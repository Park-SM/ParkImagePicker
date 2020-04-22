package com.smparkworld.parkimagepicker.domain;

public class Image {

    private String uri;
    private int selectedIndex;
    private boolean isSelected;

    public Image(String uri) {
        this.uri = uri;
        this.isSelected = false;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        isSelected = true;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.selectedIndex = 0;
        isSelected = selected;
    }
}
