package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarageTest {
    private Garage garage;

    @BeforeEach
    public void runBefore() {
        garage = new Garage();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, garage.getGarageSize());
    }
}
