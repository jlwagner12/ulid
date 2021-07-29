package org.jlw.ulid

import io.avaje.config.Config
import io.ebean.config.DatabaseConfig
import spock.lang.Specification


class EbeanTypeDeterminationSpec extends Specification {
    def "default"() {
        given:
        var config = new UlidEbeanConfiguration()

        and:
        var dbconfig = new DatabaseConfig()

        expect:
        config.getDatabaseType(dbconfig) == EbeanMappingType.STRING
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
        config.getDatabaseType(dbconfig) == EbeanMappingType.BINARY
    }

    def "binary setting"() {
        given:
        var config = new UlidEbeanConfiguration(EbeanMappingType.BINARY)

        and:
        var dbconfig = new DatabaseConfig()

        expect:
        config.getDatabaseType(dbconfig) == EbeanMappingType.BINARY
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
        config.getDatabaseType(dbconfig) == EbeanMappingType.UUID
    }

    def "uuid setting"() {
        given:
        var config = new UlidEbeanConfiguration(EbeanMappingType.UUID)

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanMappingType.UUID
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
        config.getDatabaseType(dbconfig) == EbeanMappingType.EMBEDDED
    }

    def "embedded setting"() {
        given:
        var config = new UlidEbeanConfiguration(EbeanMappingType.EMBEDDED)

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanMappingType.EMBEDDED
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
        config.getDatabaseType(dbconfig) == EbeanMappingType.STRING
    }

    def "empty setting"() {
        given:
        var config = new UlidEbeanConfiguration("")

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanMappingType.STRING
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
        config.getDatabaseType(dbconfig) == EbeanMappingType.STRING
    }

    def "blank setting"() {
        given:
        var config = new UlidEbeanConfiguration("   \t\n\n")

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        expect:
        config.getDatabaseType(dbconfig) == EbeanMappingType.STRING
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
        var config = new UlidEbeanConfiguration("NONE")

        and:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.loadFromProperties()

        and:
        config.getDatabaseType(dbconfig)

        then:
        thrown(IllegalArgumentException.class)
    }
}
