package cn.tthud.taitian.db.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import cn.tthud.taitian.db.entity.Meizi;
import cn.tthud.taitian.db.entity.Message;

import cn.tthud.taitian.db.dao.MeiziDao;
import cn.tthud.taitian.db.dao.MessageDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig meiziDaoConfig;
    private final DaoConfig messageDaoConfig;

    private final MeiziDao meiziDao;
    private final MessageDao messageDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        meiziDaoConfig = daoConfigMap.get(MeiziDao.class).clone();
        meiziDaoConfig.initIdentityScope(type);

        messageDaoConfig = daoConfigMap.get(MessageDao.class).clone();
        messageDaoConfig.initIdentityScope(type);

        meiziDao = new MeiziDao(meiziDaoConfig, this);
        messageDao = new MessageDao(messageDaoConfig, this);

        registerDao(Meizi.class, meiziDao);
        registerDao(Message.class, messageDao);
    }
    
    public void clear() {
        meiziDaoConfig.clearIdentityScope();
        messageDaoConfig.clearIdentityScope();
    }

    public MeiziDao getMeiziDao() {
        return meiziDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

}
