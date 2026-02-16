package io.reflectoring.buckpal;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class DependencyRuleTests {

	@Test
	void validateThreeTierArchitecture() {
		JavaClasses classes = new ClassFileImporter()
				.importPackages("io.reflectoring.buckpal..");

		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.controller..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("io.reflectoring.buckpal.repository..")
				.check(classes);

		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.service..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("io.reflectoring.buckpal.controller..")
				.check(classes);

		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.repository..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage(
						"io.reflectoring.buckpal.controller..",
						"io.reflectoring.buckpal.service..",
						"io.reflectoring.buckpal.dto..")
				.check(classes);
	}

	@Test
	void domainModelDoesNotDependOnOutside() {
		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.entity..")
				.should()
				.dependOnClassesThat()
				.resideOutsideOfPackages(
						"io.reflectoring.buckpal.entity..",
						"lombok..",
						"java..",
						"jakarta.."
				)
				.check(new ClassFileImporter()
						.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
						.importPackages("io.reflectoring.buckpal.."));
	}

}
