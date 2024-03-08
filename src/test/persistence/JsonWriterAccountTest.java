package persistence;

import model.Account;
import model.AccountWorkRoom;
import model.GarageWorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterAccountTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountWorkRoom wr = new AccountWorkRoom();
            JsonWriterAccount writer = new JsonWriterAccount("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

//    @Test
//    void testWriterEmptyWorkroom() {
//        try {
//            AccountWorkRoom wr = new AccountWorkRoom();
//            JsonWriterAccount writer = new JsonWriterAccount("./data/testWriterEmptyWorkroom.json");
//            writer.open();
//            writer.write(wr);
//            writer.close();
//
//            JsonReaderAccount reader = new JsonReaderAccount("./data/testWriterEmptyWorkroom.json");
//            wr = reader.read();
//            assertEquals(0, wr.getAccount().size());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            AccountWorkRoom wr = new AccountWorkRoom();
            wr.addAccount(new Account(100));
            JsonWriterAccount writer = new JsonWriterAccount("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReaderAccount reader = new JsonReaderAccount("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            List<Account> accounts = wr.getAccount();
            assertEquals(1, accounts.size());
            checkAccount(100, accounts.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
