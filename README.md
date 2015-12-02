# React Native Digits

[![npm version](http://img.shields.io/npm/v/react-native-digits.svg?style=flat-square)](https://npmjs.org/package/react-native-digits "View this project on npm")
[![npm version](http://img.shields.io/npm/dm/react-native-digits.svg?style=flat-square)](https://npmjs.org/package/react-native-digits "View this project on npm")

## Getting Started
- [Installation](#installation)
- [Setup iOS](#setup-ios)
- [Setup Android](#setup-android)
- [Usage](#usage)
- [Properties](#properties)

## Installation

`npm install react-native-digits --save`

## Setup iOS
See React Native documentation on [Linking Libraries](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#content)

1. Open your project in XCode
2. Right click on `Libraries` and click `Add Files to "YOUR_PROJECT _NAME"`
3. Add `libRNDigits.a` to `Build Phases -> Link Binary With Libraries`

## Setup Android

#### In `settings.gradle`

Add to bottom:

```java
include ':react-native-digits'
project(':react-native-digits').projectDir = new File(settingsDir, '../node_modules/react-native-digits')
```

#### In `android/build.gradle`

```java
allprojects {
  repositories {
    mavenLocal()
    jcenter()
    maven { url 'https://maven.fabric.io/public' }   <--- ADD THIS
  }
}
```

#### In `android/app/build.gradle`

```java
dependencies {
  compile fileTree(dir: 'libs', include: ['*.jar'])
  compile 'com.android.support:appcompat-v7:23.0.0'
  compile 'com.facebook.react:react-native:0.14.+'
  compile project(':react-native-digits')           <--- ADD THIS
}
```

#### In `MainActivity.java`

```java
import co.fixt.react.digits.*;                      <--- ADD THIS

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
                 .addPackage(new RNDigitsModule())     <--- ADD THIS
                 .setUseDeveloperSupport(BuildConfig.DEBUG)
                 .setInitialLifecycleState(LifecycleState.RESUMED)
                 .build();

    mReactRootView.startReactApplication(mReactInstanceManager, "DropBot", null);

    setContentView(mReactRootView);
  }
 ```
 
#### In `AndroidManifest.xml`

Add this inside the `application` tag.

```xml
<meta-data
  android:name="io.fabric.ApiKey"
  android:value="YOUR_API_KEY" />
<meta-data
  android:name="io.fabric.ApiSecret"
  android:value="YOUR_API_SECRET" />
```


## Usage

```javascript
import React, { Component } from 'react-native'

import Button from ‘./button’
import Digits from 'react-native-digits'

export default class Login extends Component {
  handleDigitsError(err) {
    this.setState({showDigits: false})
    console.warn(‘Login failed’, err)
  }
  
  handleDigitsLogin(credentials) {
    this.setState({showDigits: false})
    console.log(‘Login successful’, credentials)
  }
  
  render() {
    return (
      <View>
        <Button
        	onPress={ () => this.setState({showDigits: true}) }
        >
          Login
        </Button>
        
        <Digits
          accentColor=“#16a085”
          backgroundColor=“#ffffff”
          onError={this.handleDigitsError.bind(this)}
          onLogin={this.handleDigitsLogin.bind(this)}
        />
      </View>
    )
  }
}
```

## Properties

| Prop             | Default                      | Type       | Description                                                             |
| :--------------: | :--------------------------: | :--------: | :---------------------------------------------------------------------: |
| accentColor      |                              | `string`   | The main color of elements associated with user actions (e.g. buttons). |
| backgroundColor  |                              | `string`   | The background color for all views in the Digits flow.                  |
| onError          | `(err) => console.warn(err)` | `function` | Callback on error.                                                      |
| onLogin          |                              | `function` | Callback on success. `credentials` are passed back.                     |
| visible          | `false`                      | `bool`     | Show the Digits viewController                                          |
