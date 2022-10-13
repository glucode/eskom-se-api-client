# eskom-se-api-client
KMM API Client for EskomSePush API. 

This can be used in Android and iOS projects and is pre-configured for request/response logging as well as authentication. 

## Java 11
This project requires Java 11+ to be installed on your dev machine. 

You can check your Java version by running the following command in terminal
```
$ java -version
```

### Installing Java 11+
This can be done by using Homebrew, or by manually downloading and installing from the [Oracle webpage](https://www.oracle.com/java/technologies/downloads)

## General Setup

### Add this repo as a git submodule
```
$ cd [PATH_TO_YOUR_PROJECT_ROOT]
$ git clone https://github.com/glucode/eskom-se-api-client.git
$ git submodule update --init
```

## iOS Setup

### Step 1 - pod init
```
$ cd [PATH_TO_YOUR_PROJECT_ROOT]
$ pod init
```


### Step 2 - update podfile
Change your podfile to look like this
```
target 'YOUR_PROJECT_NAME' do
    pod 'shared', :path => 'YOUR_SUBMODULE_NAME/shared'
end

post_install do |installer|
  installer.pods_project.build_configurations.each do |config|
    config.build_settings["EXCLUDED_ARCHS[sdk=iphonesimulator*]"] = "arm64"
  end
end

```

The post-install script is only necesarry for M1/M2 Macs and will be removed in future. 

### Step 3 - pod install
```
$ cd [PATH_TO_YOUR_PROJECT_ROOT]
$ pod install
```

### Step 4 - Create an AccessTokenProvider

```Swift
import shared

class AccessTokenProviderImpl: AccessTokenProvider {
    func provideAccessToken() async throws -> String {
        return "YOUR_ACCESS_TOKEN"
    }
}
```

### Step 5 - Instantiate Dependency injection
Open YOUR_PROJECT.xcworkspace

Call DI.init() anywhere in your app, before attempting to access the Dependencies class. 
I recommend doing this in the init() function of your App class
```Swift
@main
struct EskomSeiOSClientApp: App {
    
    init() {
        _ = DI(
            host: "developer.sepush.co.za",
            accessTokenProvider: AccessTokenProviderImpl(),
            enableHttpLogging: true
        )
    }
}
```

### Step 6 - Get a reference to EskomSeAPIClient and gooi requests
```Swift
import shared

protocol MainViewModeling: ObservableObject {
    func fetchStatus() async -> StatusResponse?
    func fetchAreaInformation() async -> AreaInformationResponse?
    func fetchAreasNearby() async -> AreasNearbyResponse?
    func searchAreas() async -> AreasSearchResponse?
    func fetchTopicsNearby() async -> TopicsNearbyResponse?
    func fetchAllowance() async -> AllowanceResponse?
}

class MainViewModel: MainViewModeling {
    
    private let apiClient: EskomSeAPIClient = Dependencies.shared.eskomSeAPIClient
    
    func fetchStatus() async -> StatusResponse? {
        let result = try! await apiClient.getStatus()
        
        if (result is NetworkResultSuccess) {
            return result.data!
        } else {
            //TODO - Handle error
            return nil
        }
    }
    
    func fetchAreaInformation() async -> AreaInformationResponse? {
        let result = try! await apiClient.getAreaInformation(areaId: "eskde-10-fourwaysext10cityofjohannesburggauteng", testEvent: .current)
        return result.data
    }

    func fetchAreasNearby() async -> AreasNearbyResponse? {
        let result = try! await apiClient.getAreasNearby(lat: -26.0269658, lon: 28.0137339)
        return result.data
    }
    
    func searchAreas() async -> AreasSearchResponse? {
        let result = try! await apiClient.searchAreas(text: "fourways")
        return result.data
    }
    
    func fetchTopicsNearby() async -> TopicsNearbyResponse? {
        let result = try! await apiClient.getTopicsNearby(lat: -26.0269658, lon: 28.0137339)
        return result.data
    }
    
    func fetchAllowance() async -> AllowanceResponse? {
        let result = try! await apiClient.getAllowance()
        return result.data
    }
}
```

## Android Setup

### Step 1 - Include this project in your app
Add this to the bottom of your Settings.gradle file
```Kotlin
include ':EskomSeAPIClient'
project(':EskomSeAPIClient').projectDir = new File(settingsDir, 'YOUR_SUBMODULE_NAME/shared')
```

### Step 2 - Create an AccessTokenProvider
```Kotlin
class AccessTokenProviderImpl: AccessTokenProvider {
    override suspend fun provideAccessToken(): String = "YOUR_ACCESS_TOKEN"
}
```

### Step 3 - Instantiate Dependency injection
```Kotlin
DI(
    host = "developer.sepush.co.za",
    accessTokenProvider = AccessTokenProviderImpl(),
    enableHttpLogging = true
)
```

### Step 4 - Get a reference to EskomSeAPIClient and gooi requests
TODO
