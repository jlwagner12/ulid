package org.jlw.ulid

import io.avaje.config.Config
import io.ebean.config.DatabaseConfig
import spock.lang.Specification


class EbeanTypeDeterminationSpec extends Specification {
    def setup() {
        UlidEbeanConfiguration.setDatabaseType(null)
    }

    def "default"() {
        given:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.STRING
    }

    def "binary property"() {
        given:
        Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, "BINARY")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.BINARY
    }

    def "binary setting"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.BINARY)

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.BINARY
    }

    def "uuid property"() {
        given:
        Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, "UUID")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.UUID
    }

    def "uuid setting"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.UUID)

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.UUID
    }

    def "embedded property"() {
        given:
        Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, "EMBEDDED")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.EMBEDDED
    }

    def "embedded setting"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.EMBEDDED)

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.EMBEDDED
    }

    def "empty property"() {
        given:
        Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, "")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.STRING
    }

    def "empty setting"() {
        given:
        UlidEbeanConfiguration.setDatabaseType("")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.STRING
    }

    def "blank property"() {
        given:
        Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, "   \t\n\n")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.STRING
    }

    def "blank setting"() {
        given:
        UlidEbeanConfiguration.setDatabaseType("   \t\n\n")

        and:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanUlidType.STRING
    }

    def "no type property"() {
        when:
        Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, "NONE")

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        then:
        thrown(IllegalArgumentException.class)
    }

    def "no type setting"() {
        when:
        UlidEbeanConfiguration.setDatabaseType("NONE")

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        then:
        thrown(IllegalArgumentException.class)
    }
}
