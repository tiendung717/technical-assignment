# TradeMe Test

Android app showing the latest listings from the Trade Me sandbox API.

## Architecture

Single-activity Compose app, MVVM with a one-way data flow.

```
presentation  Compose screens + ViewModels (UiState / UiEvent)
domain        Product model, use cases, mappers
data          Retrofit service, DTOs, repository
```

The ViewModel exposes a `StateFlow<DiscoverUiState>` (`Loading` / `Empty` / `Error` / `Products`)
so the screen renders one exhaustive `when`, and a `SharedFlow<DiscoverUiEvent>` for one-shot
effects such as toasts. Network results are wrapped by `safeCall` into a `ResultState`, which the
ViewModel maps to UI state; `ResultState` never reaches Compose.

## Modules

| Module | Contents |
| --- | --- |
| `app` | Screens, ViewModels, domain, data, DI |
| `designsystem` | Theme, colours, and shared components (`TopBar`, `BottomNavBar`, state views) |
| `build-logic` | Convention plugins applied by both modules |

Build config lives in `build-logic` rather than in each module's Gradle file: `plugin.application`,
`androidx`, `compose`, `hilt`, `retrofit`, and `kotlinx.serialization` convention plugins.

## Tech stack

- Compose (BOM 2026.08.00) with Navigation Compose, type-safe routes
- Hilt 2.60.1 for DI, KSP for codegen
- Retrofit 3.0.0 + OkHttp 5.5.0 + kotlinx.serialization
- Coil 3.6.2 for image loading
- Timber for logging
- JUnit 4, MockK, coroutines-test

## Build

| | |
| --- | --- |
| AGP | 9.3.2 |
| Gradle | 9.5.0 |
| Kotlin | 2.4.20 |
| JDK | 21 (required — `build-logic` compiles to Java 21 bytecode) |
| minSdk / targetSdk / compileSdk | 24 / 36 / 37 |

```bash
./gradlew :app:assembleDebug
./gradlew test
```

No extra setup needed. `debug.jks` and `keystore.properties` are committed so the project builds
straight from a clone — both build types are signed with that throwaway debug key. A real release
would need its own keystore kept out of the repo.

## API

`GET https://api.tmsandbox.co.nz/v1/listings/latest.json?rows=20&photo_size=Large&listed_as=All`

Authenticated with OAuth 1.0 using the PLAINTEXT signature method, which Trade Me accepts for
public endpoints over TLS. `OAuthInterceptor` builds the header on every request; the base URL and
consumer key/secret come from `application.properties` via the secrets Gradle plugin.

Listings map to `Product` in `ProductMapper`: `Region` for location, `PriceDisplay` for price,
`BuyNowPrice` when present, and the first of `PhotoUrls` falling back to `PictureHref`.

## Tests

43 JVM unit tests covering the mapper, use case, ViewModels, and price formatting.

```bash
./gradlew test
```
