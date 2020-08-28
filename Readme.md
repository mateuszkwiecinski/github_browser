# Github Repo Browser 
![Build project](https://github.com/mateuszkwiecinski/github_browser/workflows/Build%20project/badge.svg)&nbsp;[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

## General
App shows list of [Toptal's](https://github.com/toptal) repositories on Github and shows selected repository details, such as number of opened issues, number of closed, number of the opened pull request and number of closed* pull requests.  
_\* Merged PullRequests are treated as closed ones._


## Authentication
To build the app with actual Github API access there is a requirement to pass a [Personal access token](https://github.com/settings/tokens).  
Access token can be provided one of following methods:
 - Passing project property `./gradlew build -Pgithub.token=$TOKEN`
 - Setting an environment variable `github_token=$TOKEN`
 - Adding a `github.token=$TOKEN` to `local.properties` file.

## Architecture
Application follows Clean Architecture approach. 
To provide additional support for developers to maintain correct dependencies between layers, app has been divided into mutliple Gradle modules. 
Each module has single responsibility:

- Domain layer
    - `:domain` - Module contains code that describes all business states and represents business logic
- Data layer
    - `:data:graphql` - Holds GrapQLl implementation of domain gateways. Uses Apollo GraphQL library to consume Github's _GraphQL API v4_.
    - `:data:mocked` -  Main purpose of this module is to speed up development process by excluding all external services. Can be also used for automated UI tests.
- Presentation layer
    - `presentation` - Accommodates UI related classes. Relies on Android Framework.
- Configuration
    - `app` - Provides Dependency Injection configuration logic, enables project to follow [dependency inversion](https://en.wikipedia.org/wiki/Dependency_inversion_principle) rule
    - `buildSrc` - Abstracts imperative build configuration logic. Having shared common code allows having minimal configuration of specific modules.

Following diagram presents dependencies between modules:  

![Project's architecture][logo]


## Code style
Project follows official Kotlin code style guidelines, which is greatly supported by automatic tools such as 
[Detekt](https://github.com/arturbosch/detekt) and 
[ktlint](https://github.com/jeremymailen/kotlinter-gradle).

To run all code style tasks for the whole project you may run `./gradlew projectCodestyle` task.

[logo]: images/architecture.png
