package com.example.openwrapdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdInspectorError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnAdInspectorClosedListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.pubmatic.sdk.common.OpenWrapSDK;
import com.pubmatic.sdk.common.models.POBApplicationInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        POBApplicationInfo appInfo = new POBApplicationInfo();
        try {
            appInfo.setStoreURL(new URL("https://play.google.com/store/apps/details?id=jp.ecnavi.ecnavienqueteapp.android&hl=ja&gl=US"));
            OpenWrapSDK.setApplicationInfo(appInfo);
        } catch (MalformedURLException e) {
            // Handle exception in your own way
        }

        List<String> testDeviceIds = Arrays.asList("9E22CD4F6E37023D95782B4017804815");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(com.google.ads.mediation.openwrap.AdMobOpenWrapAdapterConstants.ENABLE_RESPONSE_DEBUGGING_KEY, true);

                mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().addCustomEventExtrasBundle(com.google.ads.mediation.openwrap.AdMobOpenWrapBannerCustomEventAdapter.class,bundle).build();
                mAdView.loadAd(adRequest);
            }
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileAds.openAdInspector(MainActivity.this, new OnAdInspectorClosedListener() {
                    public void onAdInspectorClosed(@Nullable AdInspectorError error) {
                        // Error will be non-null if ad inspector closed due to an error.
                    }
                });
            }
        });
    }
}