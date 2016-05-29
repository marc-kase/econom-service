package com.studyo.services;


import com.studyo.econom.EconomProtocol;

import java.util.Date;
import java.util.List;

public interface DbService {
    void insertOrUpdateBuying(EconomProtocol.Buying buying);
    void insertOrUpdateShopping(EconomProtocol.Shopping shopping);
    void insertOrUpdateGroups(EconomProtocol.Groups groups);
    void insertOrUpdateDivision(EconomProtocol.Division division);

    List<EconomProtocol.Buying> getShopping(String user, Date from, Date to);
    List<EconomProtocol.Groups> getGruoups(String user);
}
