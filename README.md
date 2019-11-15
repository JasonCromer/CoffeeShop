# CoffeeShop

This app uses the Yelp Business Search API to provide a list of 10 nearby coffee shops with the best ratings, given an input location. While the interview prompt suggested only searching near the Envoy office, I intentionally added the functionality to support a full search, as it adds a bit more flexibility to the app.

## Architecture and Components

This app uses the MVVM Architecture: an architecture officially supported by Google. 

- Network requests are made using Retrofit, supplied by a Service class. 
- I/O operations are handled by a Repository layer, which also adds a caching layer. The caching layer might be unnecessary for this simple of an implementation, but I thought it would nice to add. 
- The ViewModel handles the business logic, subscribes to the Repository's network call, and uses LiveData to set update values. Since this architecture component is lifecycle-aware, it eliminates the need to use onSaveInstanceState in the Fragment. 
- RxJava is used for communication between the Repository and ViewModel.
- This leaves the Fragment and Activity with very minimal logic, only to display the UI.
- A RecyclerView is used to display the list of Coffee Shops, and the Glide Library is used to render the image urls.
- Dagger2 is used for dependency injection, making it a bit cleaner to manage dependencies and perform unit testing. While I did not implement unit tests (due to time constraints during this busy time of the year at Postmates), my aim would have been to test the Repository and ViewModel logic. Taking it a step further, Espresso tests could have been written for the UI as well.

