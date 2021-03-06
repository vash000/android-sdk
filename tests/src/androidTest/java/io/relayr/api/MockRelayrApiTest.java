package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import io.relayr.RelayrSdk;
import io.relayr.model.App;
import io.relayr.model.Bookmark;
import io.relayr.model.BookmarkDevice;
import io.relayr.model.CreateWunderBar;
import io.relayr.model.Device;
import io.relayr.model.Model;
import io.relayr.model.ReadingMeaning;
import io.relayr.model.Transmitter;
import io.relayr.model.TransmitterDevice;
import io.relayr.model.User;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class MockRelayrApiTest {

    private final String ID = "4f1ddffb-d9fa-456b-a73e-33daa6284c39";

    @Inject RelayrApi mockApi;

    @Captor private ArgumentCaptor<User> userCaptor;
    @Captor private ArgumentCaptor<App> appCaptor;
    @Captor private ArgumentCaptor<List<Device>> userDevicesCaptor;
    @Captor private ArgumentCaptor<CreateWunderBar> wunderBarCaptor;
    @Captor private ArgumentCaptor<List<Transmitter>> transmittersCaptor;
    @Captor private ArgumentCaptor<Transmitter> transmitterCaptor;
    @Captor private ArgumentCaptor<List<TransmitterDevice>> transmitterDeviceCaptor;
    @Captor private ArgumentCaptor<List<Device>> publicDevicesCaptor;
    @Captor private ArgumentCaptor<List<BookmarkDevice>> bookmarkDevicesCaptor;
    @Captor private ArgumentCaptor<Bookmark> bookmarkCaptor;
    @Captor private ArgumentCaptor<List<Model>> modelsCaptor;
    @Captor private ArgumentCaptor<List<ReadingMeaning>> meaningsCaptor;

    @Mock private Observer subscriber;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        RelayrSdk.initInMockMode(Robolectric.application);
        ObjectGraph.create(new TestModule(Robolectric.application)).inject(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserDevicesTest() {
        mockApi.getUserDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(userDevicesCaptor.capture());

        assertThat(userDevicesCaptor.getValue().size()).isEqualTo(4);
        assertThat(userDevicesCaptor.getValue().get(0).getModel().getName())
                .isEqualTo("Wunderbar Thermometer & Humidity Sensor");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAppInfoTest() {
        mockApi.getAppInfo().subscribe(subscriber);

        verify(subscriber).onNext(appCaptor.capture());

        assertThat(appCaptor.getValue().id).isEqualTo("shiny_id");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserInfoTest() {
        mockApi.getUserInfo().subscribe(subscriber);

        verify(subscriber).onNext(userCaptor.capture());

        assertThat(userCaptor.getValue().getName()).isEqualTo("Hugo Doménech Juárez");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createWunderBarTest() {
        mockApi.createWunderBar(ID).subscribe(subscriber);

        verify(subscriber).onNext(wunderBarCaptor.capture());

        assertThat(wunderBarCaptor.getValue().masterModule.getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmittersTest() {
        mockApi.getTransmitters(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmittersCaptor.capture());

        assertThat(transmittersCaptor.getValue().size()).isEqualTo(2);
        assertThat(transmittersCaptor.getValue().get(0).getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmitterTest() {
        mockApi.getTransmitter(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmitterCaptor.capture());

        assertThat(transmitterCaptor.getValue().getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmitterDevicesTest() {
        mockApi.getTransmitterDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmitterDeviceCaptor.capture());

        assertThat(transmitterDeviceCaptor.getValue().size()).isEqualTo(6);
        assertThat(transmitterDeviceCaptor.getValue().get(0).getName())
                .isEqualTo("My Wunderbar Accelerometer & Gyroscope");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getPublicDevicesTest() {
        mockApi.getPublicDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(publicDevicesCaptor.capture());

        assertThat(publicDevicesCaptor.getValue().size()).isEqualTo(2);
        assertThat(publicDevicesCaptor.getValue().get(0).getName()).isEqualTo("DanasSecondDevice");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getBookmarkedDevicesTest() {
        mockApi.getBookmarkedDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(bookmarkDevicesCaptor.capture());

        assertThat(bookmarkDevicesCaptor.getValue().size()).isEqualTo(1);
        assertThat(bookmarkDevicesCaptor.getValue().get(0).getName()).isEqualTo("DanasDevice");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void bookmarkDeviceTest() {
        mockApi.bookmarkPublicDevice(ID, ID).subscribe(subscriber);

        verify(subscriber).onNext(bookmarkCaptor.capture());

        assertThat(bookmarkCaptor.getValue().getUserId()).isEqualTo("c70faa9f-5eda-49d8-be91-a7e4b1beeca1");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getDeviceModelsTest() {
        mockApi.getDeviceModels().subscribe(subscriber);

        verify(subscriber).onNext(modelsCaptor.capture());

        assertThat(modelsCaptor.getValue().size()).isEqualTo(1);
        assertThat(modelsCaptor.getValue().get(0).getName()).isEqualTo("Wunderbar Thermometer");
        assertThat(modelsCaptor.getValue().get(0).getReadings()).isNotNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getReadingMeaningsTest() {
        mockApi.getReadingMeanings().subscribe(subscriber);

        verify(subscriber).onNext(meaningsCaptor.capture());

        assertThat(meaningsCaptor.getValue().size()).isEqualTo(8);
        assertThat(meaningsCaptor.getValue().get(0).getKey()).isEqualTo("angular_speed");
    }
}
