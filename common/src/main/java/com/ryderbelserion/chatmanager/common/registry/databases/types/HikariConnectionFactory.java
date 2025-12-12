package com.ryderbelserion.chatmanager.common.registry.databases.types;

import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.zaxxer.hikari.HikariConfig;

public abstract class HikariConnectionFactory implements IConnector {

    public void init() {
        final HikariConfig hikariConfig = new HikariConfig();
    }
}