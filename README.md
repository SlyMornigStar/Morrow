# Morrow

An Android messaging client concept built with Kotlin, Jetpack Compose, and Telegram's official TDLib. Morrow is a working project name; it does not use Telegram's name or logo.

## Project status

This repository currently contains the architecture proposal, TDLib research, staged roadmap, and a Compose UI shell for the first prototype. Telegram authentication and live messaging are not connected yet. The current environment has Java 8 but no Android SDK, Android NDK, Gradle installation, or Telegram `api_id`/`api_hash`, so a live sign-in and APK cannot be produced here yet.

## Requirements for the live prototype

- Android Studio or Android SDK command-line tools with JDK 17, Android SDK Platform 35+, NDK, CMake 3.22.1, and Gradle.
- An application `api_id` and `api_hash` registered at [my.telegram.org](https://my.telegram.org). Keep these in local Gradle properties or CI secrets; never commit them.
- TDLib Android libraries and Java bindings built from the official [`tdlib/td`](https://github.com/tdlib/td) source for the ABIs you intend to support. Follow the upstream [`example/android` build guide](https://github.com/tdlib/td/tree/master/example/android). This project will pin an upstream TDLib commit before adding binaries.

## Build and run

The Android app module is being established. Once the Android toolchain and TDLib Android outputs are available, place the generated Java bindings and ABI libraries in the locations documented in `docs/TDLIB_RESEARCH.md`, configure the local API credentials, then build with Android Studio or `./gradlew :app:assembleDebug`. Do not use a third-party binary mirror as the production TDLib source.

## Documents

- [TDLib research and constraints](docs/TDLIB_RESEARCH.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Development roadmap](docs/ROADMAP.md)

## Telegram transparency and terms

This app uses the Telegram API and is part of the Telegram ecosystem. The in-app onboarding and any store listing must say so prominently. The product name must not include “Telegram” and must not use Telegram's official logo. The app must preserve expected Telegram behavior for messages, read states, typing, and disappearing content. See [Telegram API Terms of Service](https://core.telegram.org/api/terms).

## GitHub

The local repository is initialized for this project. To publish it, create an empty GitHub repository and add its URL as the `origin` remote.
