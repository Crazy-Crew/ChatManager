package com.ryderbelserion.chatmanager.api.configs.objects.help;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;

public class ColorProperty {

    @ExportName("primary-color")
    @Comment("The primary color for the color scheme.")
    public String primaryColor = "255,170,0";

    @ExportName("highlight")
    @Comment("The highlight colors.")
    private HighlightProperty highlight = new HighlightProperty();

    @ExportName("text-color")
    @Comment("The color used for description text.")
    public String textColor = "255,255,255";

    @ExportName("accent-color")
    @Comment("The color used for accents and symbols.")
    public String accentColor = "85,255,85";

    public void setPrimaryColor(final String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setHighlight(HighlightProperty highlight) {
        this.highlight = highlight;
    }

    public void setAccentColor(final String accentColor) {
        this.accentColor = accentColor;
    }

    public void setTextColor(final String textColor) {
        this.textColor = textColor;
    }

    public HighlightProperty getHighlight() {
        return this.highlight;
    }

    public String getPrimaryColor() {
        return this.primaryColor;
    }

    public String getAccentColor() {
        return this.accentColor;
    }

    public String getTextColor() {
        return this.textColor;
    }
}