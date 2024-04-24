package persistence;

import model.GarageWorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderGarageTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderGarage reader = new JsonReaderGarage("./data/noSuchFile.json");
        try {
            GarageWorkRoom wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReaderGarage reader = new JsonReaderGarage("./data/testReaderEmptyWorkRoom.json");
        try {
            GarageWorkRoom wr = reader.read();
            assertEquals(0, wr.numCars());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReaderGarage reader = new JsonReaderGarage("./data/testReaderGeneralWorkRoom.json");
        try {
            GarageWorkRoom wr = reader.read();
            List<Car> carList = wr.getCars();
            assertEquals(2, carList.size());
            checkCar("Audi", "R8", 2016, 8.2, 7.6, 9.0,
                    9.2, DriveType.RWD, 242000, "", carList.get(0));
            checkCar("Nissan", "GT-R", 2017, 7.9, 7.2, 9.6,
                    7.6, DriveType.AWD, 132000, "", carList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
