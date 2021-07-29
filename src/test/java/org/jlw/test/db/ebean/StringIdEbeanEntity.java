package org.jlw.test.db.ebean;

import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STRING_TABLE")
@Converter(autoApply = false)
public class StringIdEbeanEntity extends EBeanEntity
{
}
