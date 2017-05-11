package com.acemen.android.wasterz.repository.local;

import android.content.Context;

import com.acemen.android.wasterz.BuildConfig;
import com.acemen.android.wasterz.domain.model.Waste;
import com.acemen.android.wasterz.domain.model.WasteType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

/**
 * Created by Audrik ! on 10/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TestLocalRepoApi {
    private Context mContext;

    @Before
    public void setUp() {
        mContext = RuntimeEnvironment.application;
    }

    @Test
    public void testAddWaste() {
        // Given
        final Waste waste = new Waste();
        waste.setType("butt");
        waste.setAddress("12 rue de Verdun, 92150 Suresnes");
        waste.setCaptureFilename("my_capture.jpg");
        waste.setLatitude(48.871510);
        waste.setLongitude(2.228110);

        // When
        try {
            LocalWastesRepository.getInstance(mContext).insertWaste(waste);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testLoadWastes_NoData() {
        final List<Waste> wastes = LocalWastesRepository.getInstance(mContext).getAllWastes();
        Assert.assertNotEquals("It should have data on database", 0, wastes.size());
    }

    @Test
    public void testLoadWastes_WithData() throws Exception {
        // Given
        final Waste waste1 = new Waste();
        waste1.setType(WasteType.BUTT.getType());
        waste1.setAddress("12 rue de Verdun, 92150 Suresnes");
        waste1.setCaptureFilename("my_capture.jpg");
        waste1.setLatitude(48.871510);
        waste1.setLongitude(2.228110);

        final Waste waste2 = new Waste();
        waste2.setType(WasteType.FULL_TRASH.getType());
        waste2.setAddress("9 Rue du Débarcadère, 93500 Pantin");
        waste2.setCaptureFilename("my_capture2.jpg");
        waste2.setLatitude(48.897727);
        waste2.setLongitude(2.396944);

        final LocalWastesRepository repo = LocalWastesRepository.getInstance(mContext);

        // When
        repo.insertWaste(waste1);
        repo.insertWaste(waste2);
        final List<Waste> wastes = LocalWastesRepository.getInstance(mContext).getAllWastes();

        // Then
        Assert.assertEquals("It should have data on database", 2, wastes.size());
        compareWastes(wastes.get(0), waste2);
        compareWastes(wastes.get(1), waste1);
    }

    private void compareWastes(Waste originalWaste, Waste actualWaste) {
        Assert.assertEquals("RemoteIds don't match", originalWaste.getRemoteId(), actualWaste.getRemoteId());
        Assert.assertEquals("Addresses don't match", originalWaste.getAddress(), actualWaste.getAddress());
        Assert.assertEquals("Capture filenames don't match", originalWaste.getCaptureFilename(), actualWaste.getCaptureFilename());
        Assert.assertEquals("Waste types don't match", originalWaste.getType(), actualWaste.getType());
        Assert.assertEquals("Latitudes don't match", originalWaste.getLatitude(), actualWaste.getLatitude(), 0.0);
        Assert.assertEquals("Longitudes don't match", originalWaste.getLongitude(), actualWaste.getLongitude(), 0.0);
    }
}
