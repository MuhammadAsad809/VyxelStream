# 🎬 VyxelStream (PrimeVision)

> **Premium Streaming App UI | Kotlin + Jetpack Compose + Dynamic Extension Engine**

---

## 🌟 Overview

**VyxelStream** is an ultra-premium, cinematic Android streaming application built with **Jetpack Compose**, **Clean Architecture (MVVM)**, and an obsidian liquid-glass visual identity.

### 🎨 Design Highlights
- **Obsidian Dark Theme**: `#050B0B` background with deep emerald (`#13B8A6`) and teal gradients.
- **Liquid Glass Navigation**: Floating pill bottom navigation bar with animated glowing selectors.
- **Cinematic Movie Cards**: 24dp rounded corners, poster artwork, gradient text overlays, and IMDb yellow badges.
- **Edge-to-Edge Hero Detail View**: Fullscreen artwork, Play Now / Download controls, and horizontal scene stills.
- **Dynamic Platform Filtering**: Instant filtering for HBO Max, Disney+, Apple TV, and Netflix.
- **Cloudstream Extension Ready**: Built-in support for dynamic `.cs3` plugins.

---

## 📱 Visual Screens

| Screen | Description |
| :--- | :--- |
| **Home** | Trending carousels for Movies and Series, category filter chips, search bar. |
| **Movie Details** | Edge-to-edge artwork, IMDb badge, runtime, synopsis, and recommended collections. |
| **Favourites** | Platform filter chips and high-impact featured movie cards. |
| **Explore** | Extension manager showing installed and active `.cs3` plugins. |

---

## 🛠 Tech Stack

- **UI**: Jetpack Compose, Material 3 (customized)
- **Language**: Kotlin 2.0.0
- **Navigation**: Navigation Compose with animated transitions
- **Image Loading**: Coil 2.6.0
- **State Management**: Kotlin Coroutines & StateFlow
- **Min SDK**: Android 7.0 (API 24+)
- **Target SDK**: Android 14 (API 34)

---

## 🚀 Automated Builds

Every push to `main` triggers a GitHub Actions workflow that compiles the Android project and generates ready-to-install debug APKs under **Releases** and **Actions Artifacts**.

---

## 📄 License

Distributed under the terms of the **GNU General Public License v3 (GPLv3)**.
