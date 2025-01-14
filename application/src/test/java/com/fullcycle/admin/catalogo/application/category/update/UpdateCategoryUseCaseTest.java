package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenAValidCommand_whenCallsUpdateCategory_thenShouldReturnCategoryId() {
        // Given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("film", null, true);
        final var expectedId = aCategory.id();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand).get();

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(expectedId);

        verify(categoryGateway, times(1)).update(argThat(anUpdatedCategory ->
                Objects.equals(expectedName, anUpdatedCategory.name()) &&
                        Objects.equals(expectedDescription, anUpdatedCategory.description()) &&
                        Objects.equals(expectedIsActive, anUpdatedCategory.active()) &&
                        Objects.equals(expectedId, anUpdatedCategory.id()) &&
                        Objects.equals(aCategory.createdAt(), anUpdatedCategory.createdAt()) &&
                        aCategory.updatedAt().isBefore(anUpdatedCategory.updatedAt()) &&
                        Objects.isNull(anUpdatedCategory.deletedAt())
        ));
    }

    @Test
    void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        // Given
        final var aCategory = Category.newCategory("film", null, true);
        final var expectedId = aCategory.id();

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null.";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        // When
        final var notification = useCase.execute(aCommand).getLeft();

        // Then
        assertNotNull(notification);
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());

        verify(categoryGateway, never()).update(any());
    }

    @Test
    void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_thenShouldReturnInactiveCategoryId() {
        // Given
        final var aCategory = Category.newCategory("film", null, true);
        final var expectedId = aCategory.id();

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand).get();

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).update(argThat(anUpdatedCategory ->
                Objects.equals(expectedName, anUpdatedCategory.name()) &&
                        Objects.equals(expectedDescription, anUpdatedCategory.description()) &&
                        Objects.equals(expectedIsActive, anUpdatedCategory.active()) &&
                        Objects.nonNull(anUpdatedCategory.id()) &&
                        Objects.nonNull(anUpdatedCategory.createdAt()) &&
                        Objects.nonNull(anUpdatedCategory.updatedAt()) &&
                        Objects.nonNull(anUpdatedCategory.deletedAt())
        ));
    }

    @Test
    void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAnException() {
        // Given
        final var aCategory = Category.newCategory("film", null, true);
        final var expectedId = aCategory.id();

        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error.";

        final var aCommand =
                UpdateCategoryCommand.with(
                        expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        // When
        final var notification = useCase.execute(aCommand).getLeft();

        // Then
        assertNotNull(notification);
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());

        verify(categoryGateway, times(1)).update(argThat(anUpdatedCategory ->
                Objects.equals(expectedName, anUpdatedCategory.name()) &&
                        Objects.equals(expectedDescription, anUpdatedCategory.description()) &&
                        Objects.equals(expectedIsActive, anUpdatedCategory.active()) &&
                        Objects.nonNull(anUpdatedCategory.id()) &&
                        Objects.nonNull(anUpdatedCategory.createdAt()) &&
                        Objects.nonNull(anUpdatedCategory.updatedAt()) &&
                        Objects.isNull(anUpdatedCategory.deletedAt())
        ));
    }

    @Test
    void givenACommandWithInvalidID_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {
        // Given
        final var expectedId = "123";

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "Category with ID %s was not found.".formatted(expectedId);

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(CategoryID.from(expectedId)))
                .thenReturn(Optional.empty());

        // When
        final var actualException = assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, never()).update(any());
    }
}