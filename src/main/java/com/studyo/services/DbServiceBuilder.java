package com.studyo.services;


import com.studyo.config.DbConfiguration;

public class DbServiceBuilder {
    private static final String DRIVER_MYSQL  = "com.mysql.jdbc.Driver";
    private static final String DRIVER_ORACLE = "com.ojdbc.Driver";

    private  DbConfiguration configuration;

    public DbServiceBuilder newBuilder(DbConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public DbServiceManager build () {
        switch (configuration.getDatabaseDriverClassName()) {
            case DRIVER_MYSQL:
                return new MySqlService(configuration);
            case DRIVER_ORACLE:
                return null;
            default: return null;
        }
    }
}
