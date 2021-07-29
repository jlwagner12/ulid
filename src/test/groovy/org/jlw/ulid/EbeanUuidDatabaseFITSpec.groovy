package org.jlw.ulid

import io.ebean.Database
import org.jlw.test.db.DatabaseDialect
import org.jlw.test.db.DatabaseInitializer
import org.jlw.test.db.ebean.EBeanEntity
import org.jlw.test.db.ebean.UuidIdEbeanEntity
import spock.lang.Specification

class EbeanUuidDatabaseFITSpec extends Specification {
    DatabaseDialect dbDialect

    Database db

    def cleanup() {
        Optional.ofNullable(db).ifPresent(v -> v.shutdown())
        dbDialect.getImplementation().close()
    }

    def "database string #dialect"() {
        this.dbDialect = dialect as DatabaseDialect

        if (!dbDialect.getInitializer().hasUuid()) {
            return
        }

        given:
        var dbInit = new DatabaseInitializer(dbDialect, EbeanMappingType.UUID)

        and:
        db = dbInit.getEbeanDatabase()

        and:
        var bean = new UuidIdEbeanEntity()

        bean.setValue(12345)
        db.save(bean)

        and:
        EBeanEntity dbBean = db.find(UuidIdEbeanEntity.class)
                .where()
                .eq("value", 12345)
                .findOne()

        expect:
        dbBean.getId() == bean.getId()

        where:
        dialect << DatabaseDialect.values()
    }
}
