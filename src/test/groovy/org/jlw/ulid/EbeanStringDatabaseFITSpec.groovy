package org.jlw.ulid

import io.ebean.Database
import org.jlw.test.db.DatabaseDialect
import org.jlw.test.db.DatabaseInitializer
import org.jlw.test.db.ebean.EBeanEntity
import org.jlw.test.db.ebean.StringIdEbeanEntity
import spock.lang.Specification

class EbeanStringDatabaseFITSpec extends Specification {
    DatabaseDialect dbDialect

    Database db

    def cleanup() {
        Optional.ofNullable(db).ifPresent(v -> v.shutdown())
        dbDialect.getImplementation().close()
    }

    def "database string #dialect"() {
        this.dbDialect = dialect as DatabaseDialect

        given:
        var dbInit = new DatabaseInitializer(dbDialect)

        and:
        db = dbInit.getEbeanDatabase()

        and:
        var bean = new StringIdEbeanEntity()

        bean.setValue(12345)
        db.save(bean)

        and:
        EBeanEntity dbBean = db.find(StringIdEbeanEntity.class)
                .where()
                .eq("value", 12345)
                .findOne()

        expect:
        dbBean.getId() == bean.getId()

        where:
        dialect << DatabaseDialect.values()
    }
}
