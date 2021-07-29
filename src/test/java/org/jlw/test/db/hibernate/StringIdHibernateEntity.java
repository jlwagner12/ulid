package org.jlw.test.db.hibernate;

import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jlw.test.db.ebean.EBeanEntity;

@Entity
@Table(name = "STRING_TABLE")
@Converter(autoApply = false)
public class StringIdHibernateEntity extends EBeanEntity
{
}
