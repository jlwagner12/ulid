package org.jlw.ulid

import io.ebean.config.DatabaseConfig
import spock.lang.Specification


class UlidEbeanConfigurationSpec extends Specification {
    def "binary config"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.BINARY)

        and:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration().postConfigure(config)

        expect:
        config.getPackages().isEmpty()
        config.getClasses().contains(ULID.class)
        config.getClasses().contains(UlidBinaryAttributeConverter.class)
        config.getClasses()
                .stream()
                .noneMatch(v -> Arrays.asList(
                        UlidStringAttributeConverter.class,
                        UlidUuidAttributeConverter.class)
                        .contains(v))
        config.getIdGenerators()
                .stream()
                .anyMatch(v -> v instanceof UlidGenerator)
    }

    def "string config"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.STRING)

        and:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration().postConfigure(config)

        expect:
        config.getPackages().isEmpty()
        config.getClasses().contains(ULID.class)
        config.getClasses().contains(UlidStringAttributeConverter.class)
        config.getClasses()
                .stream()
                .noneMatch(v -> Arrays.asList(
                        UlidBinaryAttributeConverter.class,
                        UlidUuidAttributeConverter.class)
                        .contains(v))
        config.getIdGenerators()
                .stream()
                .anyMatch(v -> v instanceof UlidGenerator)
    }

    def "UUID config"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.UUID)

        and:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration().postConfigure(config)

        expect:
        config.getPackages().isEmpty()
        config.getClasses().contains(ULID.class)
        config.getClasses().contains(UlidUuidAttributeConverter.class)
        config.getClasses()
                .stream()
                .noneMatch(v -> Arrays.asList(
                        UlidBinaryAttributeConverter.class,
                        UlidStringAttributeConverter.class)
                        .contains(v))
        config.getIdGenerators()
                .stream()
                .anyMatch(v -> v instanceof UlidGenerator)
    }

    def "embedded config"() {
        given:
        UlidEbeanConfiguration.setDatabaseType(EbeanUlidType.EMBEDDED)

        and:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration().postConfigure(config)

        expect:
        config.getPackages().isEmpty()
        config.getClasses().contains(ULID.class)
        config.getClasses()
                .stream()
                .noneMatch(v -> Arrays.asList(UlidBinaryAttributeConverter.class,
                        UlidStringAttributeConverter.class,
                        UlidUuidAttributeConverter.class)
                        .contains(v))
        config.getIdGenerators()
                .stream()
                .anyMatch(v -> v instanceof UlidGenerator)
    }
}
