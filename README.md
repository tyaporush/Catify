# Catify
Cat Heaven: Your Ultimate Feline Photo Collection - Discover the cutest, funniest, and most heartwarming photos of cats and kittens. From playful kittens and lazy loungers to curious explorers and adorable pairs, Cat Haven brings a smile to your face with every swipe. Perfect for cat lovers of all ages!


## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [License](#license)

## Features

- View a collection of cat images
- Paging support for smooth scrolling through images
- Offline support with cached data
- Clean and modern UI

## Architecture

Catify uses the Model-View-ViewModel (MVVM) architecture pattern. The project is divided into multiple layers to ensure separation of concerns and modularity.

- **Model**: Contains the data and business logic. This includes repositories, data sources, and models.
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
- **View**: The UI layer that displays data and forwards user interactions to the ViewModel.

## Tech Stack

- **Language**: Kotlin
- **Libraries**:
    - Dagger Hilt for dependency injection
    - Retrofit for network requests
    - Paging 3 for efficient data loading
    - Coroutines for asynchronous programming
    - Jetpack Compose for building UI

## Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Kotlin 1.5 or above
