package org.jlw.ulid

import io.ebean.Database
import org.jlw.test.db.DatabaseDialect
import org.jlw.test.db.DatabaseInitializer
import org.jlw.test.db.ebean.BinaryIdEbeanEntity
import org.jlw.test.db.ebean.EBeanEntity
import org.jlw.test.db.ebean.StringIdEbeanEntity
import spock.lang.Specification

class EbeanBinaryDatabaseFITSpec extends Specification {
    DatabaseDialect dbDialect

    Database db

    def cleanup() {
        Optional.ofNullable(db).ifPresent(v -> v.shutdown())
        dbDialect.getImplementation().close()
    }

    def "database string #dialect"() {
        this.dbDialect = dialect as DatabaseDialect

        given:
        var dbInit = new DatabaseInitializer(dbDialect, EbeanUlidType.BINARY)

        and:
        db = dbInit.getEbeanDatabase()

        and:
        var bean = new BinaryIdEbeanEntity()

        bean.setValue(12345)
        db.save(bean)

        and:
        EBeanEntity dbBean = db.find(BinaryIdEbeanEntity.class)
                .where()
                .eq("value", 12345)
                .findOne()

        expect:
        dbBean.getId() == bean.getId()

        where:
        dialect << DatabaseDialect.values()
    }
}
