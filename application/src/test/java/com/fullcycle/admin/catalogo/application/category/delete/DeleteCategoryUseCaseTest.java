package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenAValidId_whenCallsDeleteCategory_thenShouldBeOk() {
        // Given
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.id();

        doNothing()
                .when(categoryGateway).deleteById(expectedId);

        // When
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // Then
        verify(categoryGateway, times(1)).deleteById(expectedId);
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteCategory_thenShouldBeOk() {
        // Given
        final var expectedId = CategoryID.from("123");

        doNothing()
                .when(categoryGateway).deleteById(expectedId);

        // When
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // Then
        verify(categoryGateway, times(1)).deleteById(expectedId);
    }

    @Test
    void givenAValidId_whenGatewayThrowsError_thenShouldReturnException() {
        // Given
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Gateway error.";

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).deleteById(expectedId);

        // When
        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // Then
        verify(categoryGateway, times(1)).deleteById(expectedId);
    }
}