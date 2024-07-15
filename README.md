# Sample News App

This is just a sample app. Fetches news headlines from newsapi.org service.

## Development Setup

- Create an account at newsapi.org and generate an API key.
  Visit https://newsapi.org/docs/get-started for instructions on the same.
- Put the API key in gradle.properties as `NEWS_API_KEY=<API KEY>`. A key has already been provided 
with the source code, but it is recommended to use the newly generated key to prevent hitting API rate 
limits etc.
- You can use the standard android studio for building this app or use the gradle CLI command 
`./gradlew assembleDebug`. The built APK file will be available at 
`app/build/outputs/apk/debug/app-debug.apk`.

## Usage

- The app will display latest news headlines on launch, which can be refreshed by long pressing 
D-PAD down.
- The app is primarily optimized for 1080p TV screens, but adapts well to other form factors also.