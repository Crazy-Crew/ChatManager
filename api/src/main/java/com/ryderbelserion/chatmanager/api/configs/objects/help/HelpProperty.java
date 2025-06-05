package com.ryderbelserion.chatmanager.api.configs.objects.help;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;

public class HelpProperty {

    @ExportName("results-per-page")
    @Comment("The amount of commands to show per page.")
    public int resultsPerPage = 10;

    @ExportName("colors")
    @Comment("The colors used to stylize /chatmanager help.")
    private ColorProperty color = new ColorProperty();

    public void setResultsPerPage(final int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public void setColor(ColorProperty color) {
        this.color = color;
    }

    public int getResultsPerPage() {
        return this.resultsPerPage;
    }

    public ColorProperty getColor() {
        return this.color;
    }
}