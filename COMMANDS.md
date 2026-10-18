# to switch to java 21
sdk use java 21.0.7-amzn

# to change default java
sdk default java 21.0.7-amzn

#
Open ~/.zshrc in your editor, for example code ~/.zshrc if you use VS Code. Put a # at the start of lines 123 and 124 so they become comments:

mkdir -p src/main/java src/test/java src/test/resources
mkdir makes folders.
-p also creates parent folders (src first, then what's inside it), and doesn't complain if a folder already exists.
The three paths after it are the three folders to create.

# to compile
mvn compile
mvn test-compile

# add test folder
mkdir -p src/test/java/com/sel_int_prep

# to run your java-practice files
javac *.java
java Shapes

# to run main in maven
mvn compile exec:java -Dexec.mainClass="Main"
mvn compile exec:java -Dexec.mainClass="com.example.Main"

or 

mvn compile 
java -cp target/classes com.sel_int_prep.CollectionBasics

# to run first test in maven
mvn test -Dtest=FirstTest
mvn test -Dtest=LocatorsTest
mvn test -Dtest=InteractionsTest#uploadsWork