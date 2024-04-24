package persistence;

import model.GarageWorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterGarageTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            GarageWorkRoom wr = new GarageWorkRoom();
            JsonWriterGarage writer = new JsonWriterGarage("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            GarageWorkRoom wr = new GarageWorkRoom();
            JsonWriterGarage writer = new JsonWriterGarage("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReaderGarage reader = new JsonReaderGarage("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals(0, wr.numCars());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            GarageWorkRoom wr = new GarageWorkRoom();
            wr.addCar(new Car("Audi", "R8", 2016, 8.2, 7.6, 9.0,
                    9.2, DriveType.RWD, 100000, ""));
            wr.addCar(new Car("Nissan", "GT-R", 2017, 7.9, 7.2, 9.6,
                    7.6, DriveType.AWD, 50000, ""));
            JsonWriterGarage writer = new JsonWriterGarage("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReaderGarage reader = new JsonReaderGarage("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            List<Car> carList = wr.getCars();
            assertEquals(2, carList.size());
            checkCar("Audi", "R8", 2016, 8.2, 7.6, 9.0,
                    9.2, DriveType.RWD, 100000, "", carList.get(0));
            checkCar("Nissan", "GT-R", 2017, 7.9, 7.2, 9.6,
                    7.6, DriveType.AWD, 50000, "", carList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
