package org.jlw.test.db.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jlw.test.db.ebean.EBeanEntity;

@Entity
@Table(name = "BINARY_TABLE")
public class BinaryIdHibernateEntity extends EBeanEntity
{
}
