# Recipedia - Android App for Food & Beverage Recipes

## 📌 Overview
Recipedia is an Android application created for people who love exploring new recipes for foods and beverages. The app provides a wide range of recipes with detailed preparation instructions, ingredients, and images to help users cook delicious meals.

## ✨ Features
- 🍲 Browse a variety of food and beverage recipes
- 🔍 Search for recipes by name, ingredients, or category
- 📖 View detailed recipe instructions and images
- ❤️ Save favorite recipes for quick access
- 🌐 Supports English & Georgian languages 🇺🇸 🇬🇪
- 🔒 User authentication (Registration / Login)
- 📡 Retrieves data from a server using Retrofit & Kotlin Serialization
- 📱 Modern UI with Fragments and View Binding
- 🚀 Splash Screen & Custom Launch Icon

## 🛠️ Tech Stack
- **Language:** Kotlin
- **UI:** Fragments, BottomSheetDialogFragment, BottomNavigationView, ViewPager2, RecyclerView
- **Navigation:** Navigation Graph using Safe Args
- **Architecture:** Clean Architecture
- **Networking:** Retrofit + Kotlin Serialization
- **Dependency Injection:** Dagger Hilt
- **Local Storage:** Preferences Datastore
- **Authentication:** Firebase Authentication

## 📸 Screenshots
## Welcome
**You can change language by clicking the flag image or the text itself**

<img src="https://github.com/user-attachments/assets/9a2e6664-0c5c-4003-bd5f-4b14e783bd1c" width="300px">
<img src="https://github.com/user-attachments/assets/9333c47c-0964-4630-8709-0008e89c4638" width="300px">

## Registration

<img src="https://github.com/user-attachments/assets/5574cc7c-0c26-4a19-abfe-2b700beffc00" width="300px">

## Login

<img src="https://github.com/user-attachments/assets/0fcfd183-3558-48c6-8252-ea9177e23b76" width="300px">

## Home
**Recipedia loads 10 random recipes on home screen upon every launch. You can search any food or drink by entering text and clicking search button in the up right corner**

<img src="https://github.com/user-attachments/assets/23c75eb2-2bca-480d-89e8-142372e0afa7" width="300px">
<img src="https://github.com/user-attachments/assets/faad50bd-69f5-4f58-ac47-884516ba7b9d" width="300px">

## Favorites
**Here are all favorite recipes added by the user. Item can be removed by long click**

<img src="https://github.com/user-attachments/assets/86c2f240-ce67-4799-b935-321a87870883" width="300px">

## Profile
**User profile page, where user can change the language or log out**

<img src="https://github.com/user-attachments/assets/154200cf-8d6f-4d06-8776-0e5284a33fd8" width="300px">
<img src="https://github.com/user-attachments/assets/6583c3e2-c6a0-42a3-8544-f5fc9342da5e" width="300px">

## Details
**Recipe details page (bottom sheet dialog) which can be launched from both home and favorites pages by clicking on item. Here user can add recipe in favorites by clicking heart button and see necessary information about recipe.
Main layout is ScrollView, which has RecyclerView for the ingredients list**

<img src="https://github.com/user-attachments/assets/9f9fae5e-1885-42ee-8ae6-0c72d5d51d18" width="300px">
<img src="https://github.com/user-attachments/assets/f289e048-9aa4-45ea-8d4e-677ca6f1a334" width="300px">
<img src="https://github.com/user-attachments/assets/68511586-7789-4478-b03b-d45990aa8a9e" width="300px">

## 🚀 Requirements
- Minimum SDK 34+
- Internet connection

## 📄 API & Data
Recipedia fetches recipe details from free Spoonacular API service, which has daily limit. Ensure the API key that you use is not expired or has reached the daily limit. You can register for free without any credit card here - https://spoonacular.com/food-api

## 👥 Contributors
- **Davit Beradze** - [GitHub](https://github.com/davidbera1)
  
