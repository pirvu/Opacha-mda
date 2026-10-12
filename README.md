# Opacha-mda

This is a source code for iOS/Android game called 'Opacha-mda'.

You can use this code to make a mod or to just see how I solved certain problems, maybe it will be interesting to someone. Please keep your mod as a non commercial project. 

How to get started:
1. Create new libGDX project. 
2. Make sure that you can compile and run it. 
3. Swap core and assets with ones provided here. 

Some essential files are missing from the repository (like android manifest or gradle files). You are supposed to already have some basic experience with libGDX then it should not be a problem. 

If you have any questions about this project (how to set it up/why it doesn't compile/why it crashes on startup) then email me at yiotro93@gmail.com, I'll help you. 


## Browser build

The game can be compiled to JavaScript with [gdx-teavm](https://github.com/xpenatan/gdx-teavm) and played in a browser.

Requirements: JDK 21 and Gradle 8.x.

```
gradle :teavm:buildJavaScript   # output: teavm/build/dist/webapp
gradle :teavm:run               # build and serve at http://localhost:8080
```

The `webapp` folder is a static site and can be hosted anywhere (GitHub Pages, itch.io, etc.).
The `Web build` GitHub Actions workflow builds it on every push and uploads it as the `opacha-web` artifact.
