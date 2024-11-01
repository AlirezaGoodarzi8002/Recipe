# Recipe App

The Recipe App is an Android application built with MVVM architecture, Kotlin, and Jetpack components. It offers a smooth and user-friendly experience for browsing and viewing recipes, with support for both light and dark themes.

## Features

- **Recipe Listing and Details**: View a categorized list of recipes, including name, image, and short description.
- **Dark Mode Support**: The app switches between light and dark themes based on user interaction with toggle in first fragment, providing a visually pleasing experience in any environment.
- **Data Loading with Shimmer Effect**: While content is being fetched, a 1-second delay has been added to enhance the loading visual experience, allowing a shimmer effect to display.
- **Error Handling**: Comprehensive error handling for network issues, API errors, and unexpected failures, with retry options and clear, user-friendly messages.

## Project Structure

This project uses the **MVVM (Model-View-ViewModel)** architecture, allowing a separation of concerns and improving testability, maintainability, and scalability.

### Packages

- **data**: Contains models, repositories, and networking code.
- **ui**: Includes fragments, view models, and adapters for each part of the user interface.
- **utils**: Utility classes, extensions, and helper functions for various features like error handling and UI state management.

## Key Libraries Used

- **Jetpack Components**: Navigation, ViewModel, LiveData, and DataBinding
- **Retrofit**: For networking and API requests
- **Hilt**: For dependency injection
- **Shimmer**: For shimmer loading animations
- **Glide**: For image loading and caching

## Error Handling

Error handling is implemented across the app to improve user experience by handling common scenarios such as:

1. **Network Error**: Displays a message when there is a connectivity issue, with an option to retry.
2. **API Error**: Handles specific server-side error responses, displaying appropriate messages to the user.
3. **Unknown Error**: For unexpected issues, a generic error message is shown, allowing for graceful recovery and retry.

## Getting Started

To run this project, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/AlirezaGoodarzi8002/Recipe.git

2. **Open the project in Android Studio**.
3. **Build and run**.