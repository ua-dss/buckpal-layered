package io.reflectoring.buckpal;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class DependencyRuleTests {

	@Test
	void validateLayeredArchitecture() {
		JavaClasses classes = new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages("io.reflectoring.buckpal..");

		// Presentation layer should NOT depend on Infrastructure layer
		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.presentation..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("io.reflectoring.buckpal.infrastructure..")
				.check(classes);

		// Domain layer should NOT depend on Presentation layer
		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.domain..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("io.reflectoring.buckpal.presentation..")
				.check(classes);

		// Domain layer should NOT depend on Infrastructure layer
		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.domain..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("io.reflectoring.buckpal.infrastructure..")
				.check(classes);

		// Infrastructure layer should NOT depend on Presentation layer
		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.infrastructure..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("io.reflectoring.buckpal.presentation..")
				.check(classes);
	}

	@Test
	void domainModelDoesNotDependOnOutside() {
		noClasses()
				.that()
				.resideInAPackage("io.reflectoring.buckpal.domain.model..")
				.should()
				.dependOnClassesThat()
				.resideOutsideOfPackages(
						"io.reflectoring.buckpal.domain.model..",
						"lombok..",
						"java..",
						"jakarta.."
				)
				.check(new ClassFileImporter()
						.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
						.importPackages("io.reflectoring.buckpal.."));
	}

}
