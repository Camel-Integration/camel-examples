# Modular Example
Camel can become very powerful when you start to modularize your routes. This
example shows how to do that.

- [number-conversion-module](number-conversion-module/README.md)<br> 
    This is meant to be shipped as a java library and used by other applications.<br>
- [orchestrator-app](orchestrator-app/README.md)<br>
    This is the main application that will use the library above.<br>

## Motivation
Imagine having individual teams working on different parts of your application (Camel routes in this case).<br>
You can build each part separately and then combine them into a single application.<br>
This will allow specialized teams to work on the parts they know best and not worry about the rest of the application.




