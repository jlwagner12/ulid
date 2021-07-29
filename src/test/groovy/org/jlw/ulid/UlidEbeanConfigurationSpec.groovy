package org.jlw.ulid

import io.ebean.config.DatabaseConfig
import spock.lang.Specification


class UlidEbeanConfigurationSpec extends Specification {
    def "binary config"() {
        given:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration(EbeanMappingType.BINARY).postConfigure(config)

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
                .anyMatch(v -> v instanceof EbeanUlidGenerator)
    }

    def "string config"() {
        given:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration(EbeanMappingType.STRING).postConfigure(config)

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
                .anyMatch(v -> v instanceof EbeanUlidGenerator)
    }

    def "UUID config"() {
        given:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration(EbeanMappingType.UUID).postConfigure(config)

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
                .anyMatch(v -> v instanceof EbeanUlidGenerator)
    }

    def "embedded config"() {
        given:
        var config = new DatabaseConfig()

        and:
        new UlidEbeanConfiguration(EbeanMappingType.EMBEDDED).postConfigure(config)

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
                .anyMatch(v -> v instanceof EbeanUlidGenerator)
    }
}
