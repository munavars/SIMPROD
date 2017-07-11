/**
 * This Class is created to register Nvarchar types in hibernate dialect
 */
package com.ytc.connection;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author cognizant
 *
 */
public class SQLServer2012DialectWithNvarchar extends SQLServer2012Dialect{
	
	public SQLServer2012DialectWithNvarchar() {
		super();
        registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());
    }

}
