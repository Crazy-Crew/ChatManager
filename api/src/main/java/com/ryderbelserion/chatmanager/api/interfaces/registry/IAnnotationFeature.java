package com.ryderbelserion.chatmanager.api.interfaces.registry;

import org.jetbrains.annotations.NotNull;

public interface IAnnotationFeature<F> {

    void registerFeature(@NotNull final F parser);

}