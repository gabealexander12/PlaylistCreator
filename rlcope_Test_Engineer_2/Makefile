run: compile
        java FrontEnd
compile:
        javac BackEndFinalProject.java
        javac CS400Graph.java
        javac FrontEnd.java
        javac Genre.java
        javac Kruskal.java
        javac PlaylistWrangle.java
        javac Song.java
        javac -cp .:junit5.jar TestSuitePlaylist.java
test:

        java -jar junit5.jar --class-path . --scan-classpath --details tree
clean:
        $(RM) *.class
