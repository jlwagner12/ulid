package org.jlw.test.db.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jlw.test.db.ebean.EBeanEntity;

@Entity
@Table(name = "UUID_TABLE")
public class UuidIdHibernateEntity extends EBeanEntity
{
}
