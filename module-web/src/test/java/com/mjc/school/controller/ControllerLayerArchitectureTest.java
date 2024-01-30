package com.mjc.school.controller;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.core.domain.JavaModifier.ABSTRACT;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packagesOf = ControllerLayerArchitectureTest.class, importOptions = DoNotIncludeTests.class)
class ControllerLayerArchitectureTest {

    @ArchTest
    void classes_annotated_with_Controller_should_implement_BaseController_interface(
        final JavaClasses controllerLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("org.springframework.stereotype.Controller")
            .should()
            .implement("com.mjc.school.controller.BaseController")
            .because("it's general requirement for the controllers - to be annotated with @Controller and implement BaseController interface")
            .check(controllerLayerClasses);
    }

    @ArchTest
    void classes_implementing_BaseController_interface_should_be_annotated_with_Controller(
        final JavaClasses controllerLayerClasses
    ) {
        classes()
            .that()
            .areAssignableTo("com.mjc.school.controller.BaseController")
            .should()
            .beAnnotatedWith("org.springframework.stereotype.Controller")
            .orShould()
            .haveModifier(ABSTRACT)
            .because("it's general requirement for the controllers - to be annotated with @Controller and implement BaseController interface")
            .check(controllerLayerClasses);
    }

    @ArchTest
    void controllers_should_not_access_entities_directly(
        final JavaClasses controllerLayerClasses
    ) {
        noClasses()
            .that()
            .areAssignableTo("com.mjc.school.controller.BaseController")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("com.mjc.school.repository..")
            .because("clean layered architecture means that the controllers layer does not communicate directly with the data source, delegating this task to the services layer")
            .check(controllerLayerClasses);
    }

    @ArchTest
    void controllers_should_not_depend_on_concrete_service_implementation(
        final JavaClasses controllerLayerClasses
    ) {
        noClasses()
            .should()
            .dependOnClassesThat(
                describe(
                    "are concrete service implementations",
                    javaClass -> javaClass.isAssignableTo("com.mjc.school.service.BaseService")
                        && !(javaClass.isInterface() || javaClass.getModifiers().contains(ABSTRACT))
                )
            )
            .because("interfaces or abstract classes should be used for dependency injection")
            .check(controllerLayerClasses);
    }
}
