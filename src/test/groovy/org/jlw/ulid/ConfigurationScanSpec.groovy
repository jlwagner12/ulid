package org.jlw.ulid

import io.ebean.config.DatabaseConfig
import org.jlw.test.db.ebean.StringIdEbeanEntity
import spock.lang.Specification

class ConfigurationScanSpec extends Specification {
    def "full scan"() {
        setup:
        UlidEbeanConfiguration scanner = Spy(UlidEbeanConfiguration)

        when:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.setDisableClasspathSearch(false)

        and:
        scanner.postConfigure(dbconfig)

        then:
        dbconfig.getPackages().isEmpty()
        !dbconfig.getClasses().isEmpty()
        1 * scanner.searchClasses(_)
        0 * scanner.staticClasses(_)
    }

    def "static scan"() {
        setup:
        UlidEbeanConfiguration scanner = Spy(UlidEbeanConfiguration)

        when:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.setDisableClasspathSearch(true)

        and:
        scanner.postConfigure(dbconfig)

        then:
        dbconfig.getPackages().isEmpty()
        !dbconfig.getClasses().isEmpty()
        0 * scanner.searchClasses(_)
        1 * scanner.staticClasses(_)
    }

    def "no entities defined"() {
        setup:
        UlidEbeanConfiguration scanner = Spy(UlidEbeanConfiguration)

        when:
        var dbconfig = new DatabaseConfig()

        and:
        dbconfig.setDisableClasspathSearch(false)

        and:
        scanner.postConfigure(dbconfig)

        then:
        dbconfig.getPackages().isEmpty()
        !dbconfig.getClasses().isEmpty()
        1 * scanner.searchClasses(_)
        0 * scanner.staticClasses(_)
    }

    def "entities defined"() {
        setup:
        UlidEbeanConfiguration scanner = Spy(UlidEbeanConfiguration)

        when:
        var dbconfig = new DatabaseConfig()

        dbconfig.addClass(StringIdEbeanEntity.class)

        and:
        dbconfig.setDisableClasspathSearch(false)

        and:
        scanner.postConfigure(dbconfig)

        then:
        dbconfig.getPackages().isEmpty()
        !dbconfig.getClasses().isEmpty()
        0 * scanner.searchClasses(_)
        1 * scanner.staticClasses(_)
    }

    def "package defined"() {
        setup:
        UlidEbeanConfiguration scanner = Spy(UlidEbeanConfiguration)

        when:
        var dbconfig = new DatabaseConfig()

        dbconfig.addPackage(StringIdEbeanEntity.class.getPackageName())

        and:
        dbconfig.setDisableClasspathSearch(false)

        and:
        scanner.postConfigure(dbconfig)

        then:
        dbconfig.getPackages().isEmpty()
        !dbconfig.getClasses().isEmpty()
        1 * scanner.searchClasses(_)
        0 * scanner.staticClasses(_)
    }

    def "no preConfigure"() {
        setup:
        UlidEbeanConfiguration scanner = Spy(UlidEbeanConfiguration)

        when:
        var dbconfig = new DatabaseConfig()

        and:
        scanner.preConfigure(dbconfig)

        then:
        0 * scanner.searchClasses(_)
        0 * scanner.staticClasses(_)
    }
}
