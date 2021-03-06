<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify: Leading Temporal AI Engine" width="250">
</p>


# Breinify's API Library



<sup>Features: **Temporal Data**, **(Reverse) Geocoding**, **Events**, **Weather**, **Holidays**, **Analytics**</sup>


This library utilizes [Breinify's API](https://www.breinify.com) to provide tasks like `PushNotifications`, `geocoding`, `reverse geocoding`, `weather and events look up`, `holidays determination` through the API's endpoints, i.e., `/activity` and `/temporaldata`. Each endpoint provides different features, which are explained in the following paragraphs. In addition, this documentation gives detailed examples for each of the features available for the different endpoints.

**PushNotifications**: 
The goal of utilizing Breinify’s Time-Driven push notifications is to send highly dynamic & individualized engagements to single app-users (customer) rather than the everyone in a traditional segments. These push notifications are triggered due to user behavior and a combination of hyper-relevant weather, events, and holidays. 

**Activity Endpoint**: The endpoint is used to understand the usage-patterns and the behavior of a user using, e.g., an application, a mobile app, or a web-browser. The endpoint offers analytics and insights through Breinify's dashboard.

**TemporalData Endpoint**: The endpoint offers features to resolve temporal information like a timestamp, a location (latitude and longitude or free-text), or an IP-address, to temporal information (e.g., timezone, epoch, formatted dates, day-name),  holidays at the specified time and location, city, zip-code, neighborhood, country, or county of the location, events at the specified time and location (e.g., description, size, type), weather at the specified time and location (e.g., description, temperature).


## Getting Started

### Retrieving an API-Key

First of all, you need a valid API-key, which you can get for free at [https://www.breinify.com](https://www.breinify.com). In the examples, we assume you have the following api-key:


**938D-3120-64DD-413F-BB55-6573-90CE-473A**


It is recommended to use signed messages when utilizing the Android library. A signed messages ensures, that the request is authorized. To activate signed message ensure that Verification Signature is enabled for your key (see Breinify's API Docs for further information). In this documentation we assume that the following secret is attached to the API key and used to sign a message.

**utakxp7sm6weo5gvk7cytw==**

### Targets

- min Android Version: 15
- default target: 26

### Installation

### Including the Library

The library is available through [JCenter](https://bintray.com/breinify/brein-api-library/brein-api-library-android/) and can be easily added within the
gradle configuration like this:

```gradle
dependencies {
    ...
    compile 'com.brein.brein-api-library-android:1.0.2'
    ...
}
```

### Configuring the Library

Whenever the library is used, it needs to be configured, i.e., the configuration defines which API key and which secret 
(if signed messages are enabled, i.e., `Verification Signature` is checked) to use.

```Java
// create the configuration object
final BreinConfig breinConfig = new BreinConfig("938D-3120-64DD-413F-BB55-6573-90CE-473A", 
	"utakxp7sm6weo5gvk7cytw==");

// set the configuration for later usage
Breinify.setConfig(breinConfig);

```

The Breinify class is now configured with a valid configuration object.

### Clean-Up after Usage

Whenever the library is not used anymore, it is recommended to clean-up and release the resources held. To do so, the `Breinify.shutdown()`
method is used. A typical framework may look like that:

```java
// whenever the application utilizing the library is initialized
Breinify.setConfig("938D-3120-64DD-413F-BB55-6573-90CE-473A",
          "utakxp7sm6weo5gvk7cytw==");

// whenever the application utilizing the library is destroyed/released
Breinify.shutdown();
```

## Activity: Selected Usage Examples

The `/activity` endpoint is used to track the usage of, e.g., an application, an app, or a web-site. There are several libraries available to be used for different system (e.g.,  [Node.js](https://github.com/Breinify/brein-api-library-node), [iOS](https://github.com/Breinify/brein-api-library-ios), [Java](https://github.com/Breinify/brein-api-library-java), [JavaScript](https://github.com/Breinify/brein-api-library-javascript-browser), [Ruby](https://github.com/Breinify/brein-api-library-ruby), [PHP](https://github.com/Breinify/brein-api-library-php), [Python](https://github.com/Breinify/brein-api-library-python)).

### Sending Login 

The example shows, how to send a login activity, reading the data from an request. In general, activities are added to the interesting measure points within your applications process (e.g., `login`, `addToCart`, `readArticle`). The endpoint offers analytics and insights through Breinify's dashboard.

```Java
// create a user you are interested in 
final BreinUser breinUser = new BreinUser("user.anywhere@email.com")
         .setFirstName("User")
         .setLastName("Anyhere");
               
// invoke an activity that the user has logged in
Breinify.activity(breinUser, 
         BreinActivityType.LOGIN,
         BreinCategoryType.HOME, 
         "Login-Description");

```

### Sending readArticle

Instead of sending an activity utilizing the `Breinify.activity(...)` method, it is also possible to create an instance of a `BreinActivity` add the appropriate properties and execute the request later on by using the `Breinify.activity(...)` method.

```Java
// create a user you're interested in
final BreinUser breinUser = new BreinUser("user.anywhere@email.com")
      .setFirstName("User")
      .setLastName("Anyhere");

// create activity object and collect data        
final BreinActivity breinActivity = new BreinActivity()
      .setUser(breinUser)
      .setActivityType("readArticle")
      .setDescription("A Homebody Persident Sits Out His Honeymoon Period");
        
// invoke activity call when appropriate
Breinify.activity(breinActivity);
```


## TemporalData: Selected Usage Examples

The `/temporalData` endpoint is used to transform your temporal data into temporal information, i.e., enrich your temporal data with information like 
*current weather*, *upcoming holidays*, *regional and global events*, and *time-zones*, as well as geocoding and reverse geocoding.

### Getting User Information

Sometimes it is necessary to get some more information about the user of an application, e.g., to increase usability and enhance the user experience, 
to handle time-dependent data correctly, to add geo-based services, or increase quality of service. The client's information can be retrieved easily 
by calling the `/temporaldata` endpoint utilizing the `Breinify.temporalData(...)` method or by executing a `BreinTemporalData` instance, i.e.,:

```java
breinTemporalData.execute(new ICallback() {
   @Override
   public void callback(final BreinResult data) {
      final BreinTemporalDataResult temporalDataResult = new BreinTemporalDataResult(data);
      if (temporalDataResult.hasWeather()) {
         final BreinWeatherResult weatherResult = temporalDataResult.getWeather();
      }
      if (temporalDataResult.hasEvents()) {
         final List<BreinEventResult> eventResults = temporalDataResult.getEvents();
      } 
      if (temporalDataResult.hasHolidays()) {
         final List<BreinHolidayResult> holidayResults = temporalDataResult.getHolidays();
      }
   }
});

```

The returned result contains detailed information about the time, the location, the weather, holidays, and events at the time and the location. A detailed
example of the returned values can be found <a target="_blank" href="https://www.breinify.com/documentation">here</a>.

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-java/master/documentation/img/sample-user-information.png" alt="Sample output of the user information." width="500"><br/>
  <sup>Possilbe sample output utilizing some commanly used features.</sup>
</p>


### Geocoding (resolve Free-Text to Locations)

Sometimes it is necessary to resolve a textual representation to a specific geo-location. The textual representation can be
structured and even partly unstructured, e.g., the textual representation `the Big Apple` is considered to be unstructured,
whereby a structured location would be, e.g., `{ city: 'Seattle', state: 'Washington', country: 'USA' }`. It is also possible
to pass in partial information and let the system try to resolve/complete the location, e.g., `{ city: 'New York', country: 'USA' }`.

```java
final BreinTemporalData breinTemporalData = new BreinTemporalData()
                .setLocation("The Big Apple");

breinTemporalData.execute(new ICallback() {
   @Override
   public void callback(final BreinResult data) {

   final BreinLocationResult locationResult = new BreinLocationResult(data.getMap());
   final double lat = locationResult.getLat();
   final double lon = locationResult.getLon();
   final String country = locationResult.getCountry();
   final String state = locationResult.getState();
   final String city = locationResult.getCity();
   }
});
```

This will lead to the following result:

```
Latitude is: 40.7614927583
Longitude is: -73.9814311179
Country is: US
State is: NY
City is: New York
```

Or shown as an Apple Map result:

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-android/master/documentation/img/geomap.png" alt="Sample Map of the results from the geocoding requests." width="400"><br/>
  <sup>Map output by utilizing the result of reverse geocoding requests.</sup>
</p>

### Reverse Geocoding (retrieve GeoJsons for, e.g., Cities, Neighborhoods, or Zip-Codes)

The library also offers the feature of reverse geocoding. Having a specific geo-location and resolving the coordinates
to a specific city or neighborhood (i.e., names of neighborhood, city, state, country, and optionally GeoJson shapes). 

A possible request if you're interesed in events might look like this:

```java
final BreinTemporalData breinTemporalData = new BreinTemporalData()
    .setLatitude(37.7609295)
    .setLongitude(-122.4194155)
    .addShapeTypes("CITY", "NEIGHBORHOOD");

breinTemporalData.execute(new ICallback() {
    @Override
    public void callback(final BreinResult data) {
        final BreinTemporalDataResult temporalDataResult = new BreinTemporalDataResult(data);
                
        // access the geoJson instances for the CITY and the NEIGHBORHOOD
        temporalDataResult.getLocation().getGeoJson("CITY");
        temporalDataResult.getLocation().getGeoJson("NEIGHBORHOOD");
    }
});
```

## PushNotifications: Selected Usage Example


Let's integrate Breinify's PushNotifications within an Android App using [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/). 

### Integration


Using Breinify Push Notifications in Android apps is straightforward. 

The Breinify SDK integrates smoothly within the Android Application Lifecycle. Add in your MainActivity the initialization of the Breinify SDK:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.activity_main);

	// we come to this later
   checkAppPermission();
   
   final String kValidApiKey = "5ACB-F8B8-B6BD-46EF-B959-1536-64D2-3F38";
   final String kValidSecret = "/ss906aixyii8f6mi8xb3g==";

   Breinify.initialize(this.getApplication(), this, kValidApiKey, kValidSecret);
}
   
```

The Breinify SDK needs some permission in order to retrieve the appropriate information. In your `AndroidManifest.xml` file you have to add the following permissions:

```xml
<!-- for Inet access -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- For GPS based location -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- For using only network based location -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- For Wifi information -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- permission if the application needs to keep the processor from sleeping when a message is received -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

Android user's must be prompted and grant those permissions. This can be done like this:

```java
private void checkAppPermission() {
   final int accessFineLocationPermission = ActivityCompat.checkSelfPermission(this,
      android.Manifest.permission.ACCESS_FINE_LOCATION);
   if (accessFineLocationPermission == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
   }

   final int accessCoarseLocationPermission = ActivityCompat.checkSelfPermission(this,
      android.Manifest.permission.ACCESS_COARSE_LOCATION);
   if (accessCoarseLocationPermission == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, 
         new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
   }
}
```

Furthermore `AndroidManifest.xml` needs to contain the following additional services to handle the PushNotification:

```xml
   ...
   <service android:name="com.brein.api.BreinNotificationIdService">
         <intent-filter>
             <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
         </intent-filter>
   </service>

   <service android:name="com.brein.api.BreinNotficationService">
         <intent-filter>
             <action android:name="com.google.firebase.MESSAGING_EVENT" />
         </intent-filter>
   </service>
   ...
```

When sending a Push Notification it will appear like this:

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-android/master/documentation/img/android-push-sample.png" alt="Sample Map of the results from the geocoding requests." width="400"><br/>
  <sup>Map output by utilizing the result of reverse geocoding requests.</sup>
</p>


### Further links
To understand all the capabilities of Breinify's APIs, have a look at:

* [Breinify's Website](https://www.breinify.com).


