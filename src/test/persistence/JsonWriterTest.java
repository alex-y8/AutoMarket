package persistence;

import model.WorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            WorkRoom wr = new WorkRoom();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkRoom wr = new WorkRoom();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals(0, wr.numCars());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkRoom wr = new WorkRoom();
            wr.addCar(new Car("Audi", "R8", 2016, 8.2, 7.6, 9.0,
                    9.2, DriveType.RWD, 100000));
            wr.addCar(new Car("Nissan", "GT-R", 2017, 7.9, 7.2, 9.6,
                    7.6, DriveType.AWD, 50000));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            List<Car> carList = wr.getCars();
            assertEquals(2, carList.size());
            checkCar("Audi", "R8", 2016, 8.2, 7.6, 9.0,
                    9.2, DriveType.RWD, 100000, carList.get(0));
            checkCar("Nissan", "GT-R", 2017, 7.9, 7.2, 9.6,
                    7.6, DriveType.AWD, 50000, carList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
