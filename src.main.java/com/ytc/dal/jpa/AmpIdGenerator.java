package com.ytc.dal.jpa;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;

public class AmpIdGenerator extends org.hibernate.id.UUIDGenerator {
	@Override
	public Serializable generate(SessionImplementor session, Object object) {
		Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
		return (id != null && !"".equals(id.toString().trim())) ? id : ((String) super.generate(session, object));
	}
}
