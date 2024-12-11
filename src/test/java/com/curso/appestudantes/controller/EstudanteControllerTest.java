package com.curso.appestudantes.controller;

import com.curso.appestudantes.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstudanteControllerTest {

    private final EstudanteController controller = new EstudanteController();

    @Test
    void testCpfValido() {
        String validCpf = "12345678909";

        try {
            controller.validarCpf(validCpf);
        } catch (InvalidInputException e) {
            fail("Exceção incorreta de cpf inválido: " + e.getMessage());
        }
    }

    @Test
    void testCpfQntDigitos() {
        String invalidCpf1 = "12345678"; // 8 Dígitos
        String invalidCpf2 = "123456789031"; // 12 Dígitos

        InvalidInputException exception1 = assertThrows(InvalidInputException.class, () -> {
            controller.validarCpf(invalidCpf1);
        });
        assertEquals("Número de caracteres inválidos.", exception1.getMessage());


        InvalidInputException exception2 = assertThrows(InvalidInputException.class, () -> {
            controller.validarCpf(invalidCpf2);
        });
        assertEquals("Número de caracteres inválidos.", exception2.getMessage());
    }

    @Test
    void testCpfDigitoVerificador() {
        String invalidCpf = "12345678900";  // Dígitos verificadores invalidos

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            controller.validarCpf(invalidCpf);
        });

        assertEquals("Dígitos verificadores invalidos.", exception.getMessage());
    }

    @Test
    void testCpfCaractereInvalido() {
        String invalidCpf = "123a5678909";

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            controller.validarCpf(invalidCpf);
        });

        assertEquals("Caractere inválido no CPF: a", exception.getMessage());
    }
}