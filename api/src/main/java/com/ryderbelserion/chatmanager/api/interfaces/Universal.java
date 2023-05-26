package com.ryderbelserion.chatmanager.api.interfaces;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.ApiManager;
import com.ryderbelserion.chatmanager.api.storage.types.cache.CacheManager;
import com.ryderbelserion.stick.paper.Stick;

public interface Universal {

  ApiManager api = ApiManager.getApiManager();

  Stick stick = api.getStick();

  SettingsManager pluginSettings = api.getPluginSettings();

  SettingsManager configSettings = api.getConfigSettings();

  SettingsManager localeSettings = api.getLocaleSettings();

  SettingsManager wordFilterSettings = api.getWordFilterSettings();

  CacheManager cacheManager = api.getCacheManager();

}