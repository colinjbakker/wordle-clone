#Set path_to_fx
export PATH_TO_FX=/home/colin/Documents/libraries/javafx-sdk-22/lib

#compile, add src/controller*.java and src/model*.java when files added
javac --module-path $PATH_TO_FX --add-modules javafx.controls -d bin src/view/*.java

#run
java --module-path $PATH_TO_FX --add-modules javafx.controls -classpath bin view.App
