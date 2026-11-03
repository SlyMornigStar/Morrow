# Development roadmap

## Stage 0 — Research and project setup (current)

- Review TDLib authorization, Java interface, updates, chat history, contacts, media, and Telegram API terms.
- Define modular boundaries, privacy choices, and product constraints.
- Establish the original Morrow visual direction and Compose project shell.

## Stage 1 — Minimal live prototype

- Pin an official TDLib source revision and build Android native libraries plus matching Java bindings.
- Configure a developer `api_id`/`api_hash` without committing secrets.
- Implement secure TDLib initialization and the full first-login state sequence: phone, code, optional email, registration if needed, and two-step password.
- Load personal one-to-one chats, show a conversation, page text history, send text, and reflect live updates/delivery state.
- Add unit tests for the auth state reducer and message/update mapping; add an instrumented smoke path for the login screens.
- Build and install a debug APK on an Android device/emulator after credentials are configured.

## Stage 2 — Personal messaging foundation

- Synchronize Telegram contacts; add consent-based device contact matching.
- Add message search, read receipts, typing indicators, unread counts, and notification channels.
- Add Android lifecycle/background handling and Telegram-compatible push configuration.

## Stage 3 — Media and accessibility

- Add image/video/document selection and upload/download progress.
- Add voice recording/playback with permission handling, cancellation, and accessible controls.
- Cover loading, offline, upload failure, and retry states.

## Stage 4 — Calls and privacy

- Implement one-to-one audio/video call state handling using TDLib call APIs and Android media APIs.
- Add audio routing, foreground service/notification, interruptions, permissions, and camera preview.
- Test call interoperability with official Telegram clients on supported devices/networks.
- Add session controls, data retention controls, privacy disclosures, and security review.

## Stage 5 — Release readiness

- Review API terms and all supported Telegram content flows.
- Verify supported ABIs, package size, crash reporting privacy, notification behavior, and accessibility.
- Publish build instructions, pinned TDLib revision and license notices; create signed release builds.

## Exit checks for Stage 1

- Existing account can authenticate via phone/code and, when enabled, two-step verification.
- Existing private chat list and recent text history load from TDLib.
- Text can be sent and received, with send status updated from TDLib events.
- No auth code/password is persisted or logged; TDLib database is protected by an encryption key.
- A debug APK installs and runs on at least one emulator and one physical Android device.
