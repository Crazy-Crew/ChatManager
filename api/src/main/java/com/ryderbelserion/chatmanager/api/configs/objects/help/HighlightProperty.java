package com.ryderbelserion.chatmanager.api.configs.objects.help;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;

public class HighlightProperty {

    @ExportName("alternative")
    @Comment("The secondary color used to highlight commands and queries.")
    public String highlightColorAlternative = "255,85,85";

    @ExportName("color")
    @Comment("The primary color used to highlight commands and queries.")
    public String highlightColor = "255,255,85";

    public void setHighlightColorAlternative(final String highlightColorAlternative) {
        this.highlightColorAlternative = highlightColorAlternative;
    }

    public void setHighlightColor(final String highlightColor) {
        this.highlightColor = highlightColor;
    }

    public String getHighlightColorAlternative() {
        return this.highlightColorAlternative;
    }

    public String getHighlightColor() {
        return this.highlightColor;
    }
}