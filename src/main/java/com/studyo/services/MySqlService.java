package com.studyo.services;

import com.studyo.config.DbConfiguration;
import com.studyo.econom.EconomProtocol;

import java.util.Date;
import java.util.List;


public class MySqlService extends DbServiceManager {

    public MySqlService(DbConfiguration dbConfiguration) {
        setDriverClassName(dbConfiguration.getDatabaseDriverClassName());
        setUrl(dbConfiguration.getDatasourceUrl());
        setUsername(dbConfiguration.getDatabaseUsername());
        setPassword(dbConfiguration.getDatabasePassword());
    }

    @Override
    public void insertOrUpdateBuying(EconomProtocol.Buying buying) {

    }

    @Override
    public void insertOrUpdateShopping(EconomProtocol.Shopping shopping) {

    }

    @Override
    public void insertOrUpdateGroups(EconomProtocol.Groups groups) {

    }

    @Override
    public void insertOrUpdateDivision(EconomProtocol.Division division) {

    }

    @Override
    public List<EconomProtocol.Buying> getShopping(String user, Date from, Date to) {
        return null;
    }

    @Override
    public List<EconomProtocol.Groups> getGruoups(String user) {
        return null;
    }

}
