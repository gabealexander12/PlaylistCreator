run: compile
	java FrontEnd

compile: CS400Graph.java Genre.java Song.java BackEndFinalProject.java Kruskal.java FrontEnd.java PlaylistWrangle.java
	javac CS400Graph.java
	javac FrontEnd.java
	javac BackEndFinalProject.java
	javac Kruskal.java
	javac Genre.java
	javac Song.java
	javac PlaylistWrangle.java
	javac -cp .:junit5.jar TestPlaylistSuite.java

test: compile
	java -jar junit5.jar --class-path . --scan-classpath --details tree

clean:
	$(RM) *.class
