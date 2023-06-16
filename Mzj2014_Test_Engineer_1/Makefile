run: compile
	java FrontEnd

compile: CS400Graph.java Genre.java Song.java BackEndFinalProject.java Kruskal.java FrontEnd.java PlaylistWrangle.java PlaylistTestSuite.java
	javac CS400Graph.java
	javac Genre.java
	javac Song.java
	javac BackEndFinalProject.java
	javac Kruskal.java
	javac FrontEnd.java
	javac PlaylistWrangle.java
	javac -cp .:junit5.jar PlaylistTestSuite.java

test: compile
	java -jar junit5.jar --class-path . --scan-classpath --details tree

clean:
	$(RM) *.class
