# react-native-digits

[![npm version](http://img.shields.io/npm/v/react-native-digits.svg?style=flat-square)](https://npmjs.org/package/react-native-digits "View this project on npm")
[![npm version](http://img.shields.io/npm/dm/react-native-digits.svg?style=flat-square)](https://npmjs.org/package/react-native-digits "View this project on npm")

##Android

####In `settings.gradle`
Add to bottom:
```
include ':react-native-digits'
project(':react-native-digits').projectDir = new File(settingsDir, '../node_modules/react-native-digits')
```

####In `android/build.gradle`
```
allprojects {
  repositories {
    mavenLocal()
    jcenter()
    maven { url 'https://maven.fabric.io/public' }   <--- ADD THIS
  }
}
```

####In `android/app/build.gradle`
````
dependencies {
  compile fileTree(dir: 'libs', include: ['*.jar'])
  compile 'com.android.support:appcompat-v7:23.0.0'
  compile 'com.facebook.react:react-native:0.14.+'
  compile project(':react-native-digits')            <--- ADD THIS
}
````

####In `MainActivity.java`
````

import co.fixt.react.digits.*;                                <--- ADD THIS

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
  private ReactInstanceManager mReactInstanceManager;
  private ReactRootView mReactRootView;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mReactRootView = new ReactRootView(this);
 
    mReactInstanceManager = ReactInstanceManager.builder()
                 .setApplication(getApplication())
                 .setBundleAssetName("index.android.bundle")
                 .setJSMainModuleName("index.android")
                 .addPackage(new MainReactPackage())
                 .addPackage(new RNDigitsModule())             <--- ADD THIS
                 .setUseDeveloperSupport(BuildConfig.DEBUG)
                 .setInitialLifecycleState(LifecycleState.RESUMED)
                 .build();

    mReactRootView.startReactApplication(mReactInstanceManager, "DropBot", null);

    setContentView(mReactRootView);
  }
 ```
 
####In `AndroidManifest.xml`
Add this inside the `application` tag.
```
<meta-data
  android:name="io.fabric.ApiKey"
  android:value="YOUR_API_KEY" />
<meta-data
  android:name="io.fabric.ApiSecret"
  android:value="YOUR_API_SECRET" />
```
