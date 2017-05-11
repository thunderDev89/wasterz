package com.acemen.android.wasterz;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.acemen.android.wasterz.domain.WasteDataSource;
import com.acemen.android.wasterz.domain.model.Waste;
import com.acemen.android.wasterz.repository.local.LocalWastesRepository;
import com.acemen.android.wasterz.repository.local.WasterzContract;
import com.acemen.android.wasterz.repository.local.WasterzDbHelper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by Audrik ! on 10/05/2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestLocalRepositoryApi {
//    private static Context mContext;
    private static WasterzDbHelper mDbHelper;

    @Before
    public void setUp() {
//        mContext = InstrumentationRegistry.getTargetContext();
        mDbHelper = new WasterzDbHelper(InstrumentationRegistry.getTargetContext());
        clearDbAndRecreate();
    }

    @After
    public void tearDown() {
        clearDb();
    }

    private void clearDbAndRecreate() {
        clearDb();
        mDbHelper.onCreate(mDbHelper.getWritableDatabase());
    }

    private void clearDb() {
        mDbHelper.getWritableDatabase().execSQL(WasterzContract.WasteEntry.SQL_DELETE_TABLE);
    }

    @Test
    public void testAddWaste() {
        final Waste waste = new Waste();
        waste.setType("butt");
        waste.setAddress("12 rue de Verdun, 92150 Suresnes");
        waste.setCaptureFilename("my_capture.jpg");
        waste.setLatitude(48.871510);
        waste.setLongitude(2.228110);

        LocalWastesRepository.getInstance(InstrumentationRegistry.getTargetContext())
                .addWaste(waste, new WasteDataSource.AddWasteCallback() {
                    @Override
                    public void onWasteAdded(String msg) {
                        Assert.assertTrue(true);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Assert.assertTrue("Waste should not fail to be inserted", false);
                    }
                });

    }

    @Test
    public void testLoadWastes_WithData() throws Exception {
        final LocalWastesRepository repo = LocalWastesRepository.getInstance(InstrumentationRegistry.getTargetContext());
        final Waste waste = new Waste();
        waste.setType("butt");
        waste.setAddress("12 rue de Verdun, 92150 Suresnes");
        waste.setCaptureFilename("my_capture.jpg");
        waste.setLatitude(48.871510);
        waste.setLongitude(2.228110);

        repo.insertWaste(waste);

        repo.loadWastes(new WasteDataSource.LoadWastesCallback() {
            @Override
            public void onWastesLoaded(List<Waste> wastes) {
                Assert.assertEquals("Incorrect number of wastes", 1, wastes.size());
                Assert.assertTrue(true);
            }

            @Override
            public void onDataNotAvailable() {
                Assert.assertTrue("It should have data on database", false);
            }
        });
    }

    @Test
    public void testLoadWastes_NoData() {
        LocalWastesRepository.getInstance(InstrumentationRegistry.getTargetContext())
                .loadWastes(new WasteDataSource.LoadWastesCallback() {
                    @Override
                    public void onWastesLoaded(List<Waste> wastes) {
                        Assert.assertTrue("It should not have data into database", false);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Assert.assertTrue(true);
                    }
                });
    }
}
