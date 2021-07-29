package org.jlw.ulid

import io.ebean.Database
import org.jlw.test.db.DatabaseDialect
import org.jlw.test.db.DatabaseInitializer
import org.jlw.test.db.ebean.EBeanEntity
import org.jlw.test.db.ebean.EmbeddedIdEbeanEntity
import org.jlw.test.db.ebean.UuidIdEbeanEntity
import spock.lang.Specification

class EbeanEmbeddedDatabaseFITSpec extends Specification {
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
        var dbInit = new DatabaseInitializer(dbDialect, EbeanUlidType.EMBEDDED)

        and:
        db = dbInit.getEbeanDatabase()

        and:
        var id = ULID.nextULID()

        and:
        var bean = new EmbeddedIdEbeanEntity()

        bean.id = id
        bean.value = 12345
        db.save(bean)

        and:
        EmbeddedIdEbeanEntity dbBean = db.find(EmbeddedIdEbeanEntity.class)
                .where()
                .eq("value", 12345)
                .findOne()

        expect:
        dbBean.id == id
        dbBean.id == bean.id

        where:
        dialect << DatabaseDialect.values()
    }
}
