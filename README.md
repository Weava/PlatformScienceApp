# Interview app for Platform Science

### Description
A simple application meant to show a list of drivers associated with a single shipping address
based on specific criteria.

### Videos

[Screen_recording_20230924_153026.webm](https://github.com/Weava/PlatformScienceApp/assets/2576902/4efe2198-1b3c-4ed7-b6e7-85c60496be41)

[Screen_recording_20230924_152916.webm](https://github.com/Weava/PlatformScienceApp/assets/2576902/1b2f3d16-c489-4146-a562-e7fbb905babe)


### Explanation of documentation
Very little code has documentation as source code is accessible to all using the app.
What is documented is complicated logic that other developers may not understand at a glance
(e.g. the suitability score algorithm)

### Resources used
For creating the expanding list view:
https://proandroiddev.com/expandable-lists-in-jetpack-compose-b0b78c767b4

For refreshing on how to create generic view model factory:
https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455

### Built with
* Jetpack Compose
* Dagger 2
* Moshi
* Coroutines
* Android Jetpack Ktx
* Android ViewModel

### Tested With
* JUnit
* Mockito Kotlin
* Google Truth
* Coroutines Testing Suite
