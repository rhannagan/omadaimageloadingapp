# Overview
Welcome to my submission for the SE 2 role at Omada Health!

## Recordings

## Screenshots

## Tech Used
* Jetpack Compose for UI building
* Retrofit & Gson for networking
* Coil for image loading
* Hilt for Dependency Injection
* Mockk, Junit, and kotlin coroutine libraries for testing

## AI Usage
* Grok
* Google Gemini

I will have to supply some screenshots because I didn't read the instructions clearly on the 
code session recording section, but I will share a few areas in the app where AI was used to help
me either solve a tricky bug or to give me tips on best practices. 

To start, the following code found in ViewModelExtensions.kt: 
```kotlin
@Composable
inline fun <reified VM : ViewModel> hiltActivityViewModel(): VM {
    val activity = LocalActivity.current as? ComponentActivity
        ?: throw IllegalStateException(
            "hiltActivityViewModel must be called from a ComponentActivity!!!"
        )

    return viewModel<VM>(viewModelStoreOwner = activity)
}
```
This particular code solved a very persistent bug where when navigating between screens, a crash
would occur. And the reason for this was because I was attempting to navigate to another screen
before the navgraph was fully completed. In full transparency, this particular segment came from 
a different project, but it's something that I bring along whenever I'm dealing with Compose 
navigation + SharedViewModels. 

I will share some prompts that I used with Grok below for a better visual, but I wanted to mention
the other AI tool I used while working on this project: Gemini. 

Gemini is particularly useful in three cases, one of them is whenever you're google searching a bug or 
some type of solution similar to what you're trying to develop, for example, Pagination, Gemini
will auto-pop up with solutions. Sometimes they're helpful (as I'm sure you know) and other times 
they're not. Something that I found super helpful with Gemini is during environmental "disruptions"
for example, when setting up the project I had initial issues with integrating Hilt and had 
compilation issues. When the error popped up in logcat, I found it extremely helpful to ask Gemini
to analyze the issue and give me a summary of what the problem is and naturally it would provide
some hints/solutions to resolve them. It can be a massive time saver. The third case is having 
Gemini on within Android Studio (I'm not sure if that's frowned upon at Omada and if so please forgive me).
It's nice to get suggestions you're already planning on writing to speed up the dev process. 

You can find some screenshots of some of the prompts I used with Grok below





## Confessions
Admittedly, there's a few things that I would have liked to have fixed/changed in a Professional application. 
* The first thing is I would not use packages alone for the separation of concerns. 
Ideally, I'd use modules for separating concerns. Modules create hard boundaries and we can better
ensure that other modules are only getting access to the dependencies they need and makes app's
structure a lot more secure and structured. Like for example, if I needed a module that would share
all the common dependencies that every other module would need I could make that have complete 
control over what each module shares. Using packages alone there is no boundary there you can have 
pretty much any dependency that you want. Another example, I would give the `usecase` module access
to only the repository module ensuring that UseCases could not interact with the services in the 
services module at all. And the same applies to our `feature` module, it would only have access to 
the `usecase` module preventing `ViewModels` from accessing services and repositories. Modules 
make separation of concerns much more secure. 
* Error handling could have been cleaner. I think I would have had something like a type of `Result`
class in the data/network layers. I sort of whipped up the solution fairly quick and had the error
handling be captured and handled in the ViewModel layer but I don't believe it's the best nor the 
most robust solution. And my error handling in general, is pretty trivial just accounting for empty
lists and failures. A more professional solution would be to have more helpful error handling for 
things such as Network specific errors. I had plans to improve it, but didn't get to it in time. 
* The names for the models I used to represent the data objects coming from Flickr. I have models with
the names `FlickrResponse`, `Photo`, `PhotoDetails`, and `Photos`. Some of those make sense, but some
of them do not specifically the confusion between `Photo` and `PhotoDetails`. `PhotoDetails` is used
to represent the photo object from the Flickr api itself but `Photo` is used in the UI layer to populate
UI components. I can't think of any name changes at the moment because at the time of writing this
it's late and my brain is mush but when we discuss more I will provide alternatives when I'm more 
fresh. 
* `HomeViewModelTest` is lacking robust testing. And the honest reason for that is because I was 
experiencing issues where some of the tests I was trying to write was having issues where the
coroutine in my ViewModel was using a different dispatcher than what the tests were running on. I 
did use Grok to try to help me debug that and the solution it mentioned was to inject dispatchers
directly into the constructor of the ViewModel itself. That way I could put the TestDispatcher directly
into the ViewModel so that the results behaved as expected. Because I was running on fumes and didn't 
want to potentially introduce breaking changes (as the app was working solidly and it was late my time)
I decided to omit them and explain why there's was missing coverage there in this README. 

