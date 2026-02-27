package buckpal;

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
				.importPackages("buckpal..");

		// Presentation layer should NOT depend on Infrastructure layer
		noClasses()
				.that()
				.resideInAPackage("buckpal.presentation..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("buckpal.infrastructure..")
				.check(classes);

		// Domain layer should NOT depend on Presentation layer
		noClasses()
				.that()
				.resideInAPackage("buckpal.business..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("buckpal.presentation..")
				.check(classes);

		// Domain layer should NOT depend on Infrastructure layer

		// Infrastructure layer should NOT depend on Presentation layer
		noClasses()
				.that()
				.resideInAPackage("buckpal.infrastructure..")
				.should()
				.dependOnClassesThat()
				.resideInAnyPackage("buckpal.presentation..")
				.check(classes);
	}

	@Test
	void domainModelDoesNotDependOnOutside() {
		noClasses()
				.that()
				.resideInAPackage("buckpal.business.model..")
				.should()
				.dependOnClassesThat()
				.resideOutsideOfPackages(
						"buckpal.business.model..",
						"lombok..",
						"java..",
						"jakarta.."
				)
				.check(new ClassFileImporter()
						.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
						.importPackages("buckpal.."));
	}

}
