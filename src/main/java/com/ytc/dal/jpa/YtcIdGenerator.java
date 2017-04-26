package com.ytc.dal.jpa;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;

public class YtcIdGenerator extends org.hibernate.id.IncrementGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object) {
        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return (id != null && !"".equals(id.toString().trim())) ? id : ((Integer) super.generate(session, object));
    }
}
