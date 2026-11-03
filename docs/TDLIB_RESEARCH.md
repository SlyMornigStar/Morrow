# TDLib research

Research checked against Telegram's official documentation on 2026-11-04.

## Findings

- TDLib is Telegram's cross-platform client library. It owns Telegram networking, local storage, data consistency, and cryptographic protocol handling. Its API is asynchronous; applications must process responses and updates in receive order.
- The authorization lifecycle is update driven. The app initializes TDLib with `setTdlibParameters`, then handles states such as waiting for a phone number, verification code, email verification, registration details, and two-step verification password. A successful session ends at `authorizationStateReady`.
- Messaging uses `sendMessage`; history uses `getChatHistory`; chat lists are loaded with `loadChats` and maintained from ordered updates. Message send success/failure, unread/read state, and chat actions arrive through TDLib updates.
- Contacts can be read using TDLib's contacts APIs. Importing the device address book requires the user's explicit consent and a clear explanation before sending phone numbers to Telegram.
- TDLib supports file-backed message content, including photos, videos, and documents. It handles file transfers and reports their progress through updates. Voice-message recording and playback still require Android media capture/playback UX and permissions.
- TDLib's API surface includes one-to-one call operations. A useful calling feature still requires the call state machine, microphone/camera permissions, audio focus/routing, foreground call service/notification behavior, and video rendering. Call support must be verified on real devices and compatible peers.
- TDLib's Android support is supplied by native libraries. Upstream documents building with Android SDK Platform 34 and CMake 3.22.1 at the time of the checked guide; the required tool versions should be rechecked when pinning a TDLib commit.

## Integration approach

Use the official TDLib Java interface and generated API from the same pinned upstream source revision as the native Android libraries. Keep the generated binding and native binaries as one versioned integration unit. Do not depend on an unverified community AAR for production. For the initial build, support `arm64-v8a` and `x86_64`; add other ABIs after size and device testing.

TDLib owns its encrypted database, but the app must generate a random database encryption key and protect it with Android Keystore-backed wrapping. Keep the user's verification code and two-step password only in transient UI state, clear them after submission, and never log them. Keep the app's API hash out of source control and release artifacts that are publicly inspectable where feasible; it is an application credential, not a user password.

## Product and policy constraints

- Obtain a distinct `api_id`/`api_hash` for this application.
- Disclose prominently in onboarding and store descriptions that the application uses Telegram API and belongs to the Telegram ecosystem.
- Do not include “Telegram” in the product title and do not use Telegram's official logo.
- Preserve Telegram's expected message behavior. Do not suppress typing states, falsify read states, or prevent expiring content from disappearing.
- Keep channels, bots, public groups, and stories out of the four primary destinations as requested. However, Telegram's third-party client terms require basic Telegram features to work as expected. Before release, review whether a primary-interface filter is compatible with that obligation and ensure the user can still view content received in personal chats, including forwarded content.

## Key limitations

1. A live authentication flow cannot be exercised without the app's registered API credentials and official native TDLib binaries.
2. “Contacts” has two distinct data sources: Telegram contacts and the Android address book. Device contacts must be permissioned and disclosed; Telegram may hide users based on their privacy settings.
3. Ordinary Telegram cloud chats and Secret Chats differ. Secret Chats are end-to-end encrypted and device-specific; they do not synchronize across devices like cloud chats.
4. Calls are more than a TDLib request: Android media capture, audio routing, call lifecycle, notifications, and device/network interoperability must be implemented and validated.
5. Android background delivery and notifications require platform integration and push configuration; reliable push setup may require Firebase credentials and must follow Telegram's current push-notification guidance.

## Official references

- [TDLib Getting Started](https://core.telegram.org/tdlib/getting-started)
- [TDLib Java Interface](https://core.telegram.org/tdlib/Java_Interface)
- [TDLib Java API](https://core.telegram.org/tdlib/Java_API)
- [TDLib Android build guide](https://github.com/tdlib/td/tree/master/example/android)
- [Telegram API authorization](https://core.telegram.org/api/auth)
- [Telegram API Terms of Service](https://core.telegram.org/api/terms)
