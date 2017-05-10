package com.acemen.android.wasterz;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.acemen.android.wasterz.domain.WasteDataSource;
import com.acemen.android.wasterz.domain.model.Waste;
import com.acemen.android.wasterz.repository.local.LocalWastesRepository;

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
    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testAddWaste() {
        final Waste waste = new Waste();
        waste.setType("butt");
        waste.setAddress("12 rue de Verdun, 92150 Suresnes");
        waste.setCaptureFilename("my_capture.jpg");
        waste.setLatitude(48.871510);
        waste.setLongitude(2.228110);

        LocalWastesRepository.getInstance(mContext)
                .addWaste(waste, new WasteDataSource.AddWasteCallback() {
                    @Override
                    public void onWasteAdded(String msg) {
                        Assert.assertTrue(true);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Assert.assertTrue("Waste should not fail to be nserted", false);
                    }
                });

    }

    @Test
    public void testLoadWastes() {
        LocalWastesRepository.getInstance(mContext)
                .loadWastes(new WasteDataSource.LoadWastesCallback() {
                    @Override
                    public void onWastesLoaded(List<Waste> wastes) {
                        Assert.assertTrue(true);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Assert.assertTrue("It should have data on database", false);
                    }
                });
    }
}
