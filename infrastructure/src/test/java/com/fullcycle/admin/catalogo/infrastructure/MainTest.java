package com.fullcycle.admin.catalogo.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MainTest {

    @Test
    void testMain() {
        assertNotNull(new Main());
        Main.main(new String[]{});
    }
}