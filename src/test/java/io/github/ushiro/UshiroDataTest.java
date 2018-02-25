package io.github.ushiro;

import io.github.ushiro.data.DataDriver;
import io.github.ushiro.data.DataModel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UshiroDataTest {

    @BeforeClass
    public static void beforeSuite() throws Exception {
        DataDriver.start();
    }

    @AfterClass
    public static void afterSuite() throws Exception {
        DataDriver.stop();
    }

    @Test
    public void testDataAPI() throws Exception {
        String keyUrl = "short-url";
        String longUrl = "long-url-long-url-long-url-long-url-long-url-long-url";
        long createdAt = 1234567890;
        int viewCount = 100;

        DataModel storingDataModel = new DataModel(keyUrl, longUrl, createdAt, viewCount);
        DataDriver.store(storingDataModel);

        DataModel retrievingDataModel = DataDriver.retrieve(keyUrl);
        assertTrue(keyUrl.equals(retrievingDataModel.getKeyUrl()));
        assertTrue(longUrl.equals(retrievingDataModel.getLongUrl()));
        assertTrue(createdAt == retrievingDataModel.getCreatedAt());
        assertTrue(viewCount == retrievingDataModel.getViewCount());
    }

}