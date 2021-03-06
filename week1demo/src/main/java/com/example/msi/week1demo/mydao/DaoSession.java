package com.example.msi.week1demo.mydao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.msi.week1demo.bean.shop;

import com.example.msi.week1demo.mydao.shopDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig shopDaoConfig;

    private final shopDao shopDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        shopDaoConfig = daoConfigMap.get(shopDao.class).clone();
        shopDaoConfig.initIdentityScope(type);

        shopDao = new shopDao(shopDaoConfig, this);

        registerDao(shop.class, shopDao);
    }
    
    public void clear() {
        shopDaoConfig.clearIdentityScope();
    }

    public shopDao getShopDao() {
        return shopDao;
    }

}
