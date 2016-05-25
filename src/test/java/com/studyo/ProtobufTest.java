package com.studyo;


import com.google.protobuf.InvalidProtocolBufferException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.studyo.econom.EconomProtocol;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class ProtobufTest {
    private enum ACTION {INSERT, UPDATE, DELETE}
    private Connection connection;

    @Before
    public void getDbConnection() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/econom_db");
        ds.setUser("gereski");
        ds.setPassword("sellnb080808!");
        connection = ds.getConnection();
    }

    @After
    public void closeAll() throws SQLException {
        connection.close();
    }

    @Test
    public void testProto() throws InvalidProtocolBufferException {
        String grName = "Food";
        long actionTime = new Date().getTime();
        long date = new Date().getTime();

        String shName = "Bread";
        double price = 10.0;
        double amount = 1;
        double total = price * amount;

        EconomProtocol.Groups gr = EconomProtocol.Groups.newBuilder()
                .setAction(ACTION.INSERT.ordinal())
                .setActionTime(actionTime)
                .setKey(1)
                .setValue(grName)
                .setColor("#0000")
                .build();

        EconomProtocol.Shopping sh = EconomProtocol.Shopping.newBuilder()
                .setAction(ACTION.INSERT.ordinal())
                .setActionTime(actionTime)
                .setOwnid(actionTime)
                .setName("Bread")
                .setShop("Market")
                .setPrice(price)
                .setAmount(amount)
                .setTotal(total)
                .setDate(date)
                .setGroupName(1)
                .build();

        byte[] groups = gr.toByteArray();
        byte[] shopps = sh.toByteArray();

        EconomProtocol.Groups gr2 = EconomProtocol.Groups.parseFrom(groups);
        EconomProtocol.Shopping sh2 = EconomProtocol.Shopping.parseFrom(shopps);

        Assert.assertEquals(ACTION.INSERT.ordinal(), gr2.getAction());
        Assert.assertEquals(actionTime, gr2.getActionTime());
        Assert.assertEquals(1, gr2.getKey());
        Assert.assertEquals(grName, gr2.getValue());
        Assert.assertEquals("#0000", gr2.getColor());

        Assert.assertEquals(ACTION.INSERT.ordinal(), sh2.getAction());
        Assert.assertEquals(actionTime, sh2.getActionTime());
        Assert.assertEquals(actionTime, sh2.getOwnid());
        Assert.assertEquals(shName, sh2.getName());
        Assert.assertEquals("Market", sh2.getShop());
        Assert.assertEquals(price, sh2.getPrice());
        Assert.assertEquals(amount, sh2.getAmount());
        Assert.assertEquals(total, sh2.getAmount() * sh2.getPrice());
        Assert.assertEquals(date, sh2.getDate());

        Assert.assertEquals(gr2.getKey(), sh2.getGroupName());
    }
}
