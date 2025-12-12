package com.ryderbelserion.chatmanager.common.registry.databases.constants;

public class UserSchema {

    public static String create_users_table = "create table if not exists users(uuid varchar(36) primary key, creation_date timestamp)";

}