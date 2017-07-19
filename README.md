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

### Requirements

- min Android Version

### Installation

### Including the Library

The library is available through [JCenter](https://bintray.com/) and can be easily added within the
gradle configuration like this:

```gradle
dependencies {
    ...
    compile 'com.brein. brein-api-library-android:1.0.1'
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
               
// invoke an activity noting that the user has logged in
Breinify.activity(breinUser, 
         BreinActivityType.LOGIN,
         BreinCategoryType.HOME, 
         "Login-Description");

```

### Sending readArticle

Instead of sending an activity utilizing the `Breinify.activity(...)` method, it is also possible to create an instance of a `BreinActivity` and pass this later on to the `Breinify.activity(...)` method.

```Swift
// create a user you're interested in
let breinUser = BreinUser(firstName: "Fred", lastName: "Firestone")

// create activity object and collect data        
let breinActivity = BreinActivity(user: breinUser)
            .setActivityType("readArticle")
            .setDescription("A Homebody President Sits Out His Honeymoon Period")
        
// invoke activity call later
do {
     try Breinify.activity(breinActivity)
   } catch {
     print("Error is: \(error)")
   } 
```


## TemporalData: Selected Usage Examples

The `/temporalData` endpoint is used to transform your temporal data into temporal information, i.e., enrich your temporal data with information like 
*current weather*, *upcoming holidays*, *regional and global events*, and *time-zones*, as well as geocoding and reverse geocoding.

### Getting User Information

Sometimes it is necessary to get some more information about the user of an application, e.g., to increase usability and enhance the user experience, 
to handle time-dependent data correctly, to add geo-based services, or increase quality of service. The client's information can be retrieved easily 
by calling the `/temporaldata` endpoint utilizing the `Breinify.temporalData(...)` method or by executing a `BreinTemporalData` instance, i.e.,:

```swift
do {   
   try Breinify.temporalData({
      // success
      (result: BreinResult) -> Void in
                
      if let holiday = result.get("holidays") {
         print("Holiday is: \(holiday)")
      }
      if let weather = result.get("weather") {
         print("Weather is: \(weather)")
      }
      if let location = result.get("location") {
         print("Location is: \(location)")
      }
      if let time = result.get("time") {
         print("Time is: \(time)")
      }
   })
   } catch {
     print("Error")
}
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

```swift
	do {
	   let breinTemporalData = BreinTemporalData()
	           .setLocation(freeText: "The Big Apple")
	            
	   try Breinify.temporalData(breinTemporalData, {           
	          (result: BreinResult) -> Void in
	        
             let breinLocationResult = BreinLocationResult(result)
             print("Latitude is: \(breinLocationResult.getLatitude())")
             print("Longitude is: \(breinLocationResult.getLongitude())")
             print("Country is: \(breinLocationResult.getCountry())")
             print("State is: \(breinLocationResult.getState())")
             print("City is: \(breinLocationResult.getCity())")
             print("Granularity is: \(breinLocationResult.getGranularity())")
	        })
	  } catch {
	       print("Error")
	  }
```

This will lead to the following result:

```
Latitude is: 40.7614927583
Longitude is: -73.9814311179
Country is: US
State is: NY
City is: New York
```

### Reverse Geocoding (retrieve GeoJsons for, e.g., Cities, Neighborhoods, or Zip-Codes)

The library also offers the feature of reverse geocoding. Having a specific geo-location and resolving the coordinates
to a specific city or neighborhood (i.e., names of neighborhood, city, state, country, and optionally GeoJson shapes). 

A possible request if you're interesed in events might look like this:

```swift
do {
   let breinTemporalData = BreinTemporalData()
         .setLatitude(37.7609295)
         .setLongitude(-122.4194155)
         .addShapeTypes(["CITY"]) 
  
   try Breinify.temporalData(breinTemporalData, {
      (result: BreinResult) -> Void in
      print("Api Success : result is:\n \(result)")

     // Events
     let breinEventResult = BreinEventResult(result)
     breinEventResult.getEventList().forEach { (entry) in

     let eventResult = BreinEventResult(entry)
     print("Starttime is: \(eventResult.getStartTime())")
     print("Endtime is: \(eventResult.getEndTime())")
     print("Name is: \(eventResult.getName())")
     print("Size is: \(eventResult.getSize())")

     let breinLocationResult = eventResult.getLocationResult()
     print("Latitude is: \(breinLocationResult.getLatitude())")
     print("Longitude is: \(breinLocationResult.getLongitude())")
     print("Country is: \(breinLocationResult.getCountry())")
     print("State is: \(breinLocationResult.getState())")
     print("City is: \(breinLocationResult.getCity())")
     print("Granularity is: \(breinLocationResult.getGranularity())")
     }
   } 
```





