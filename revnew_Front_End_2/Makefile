run: compile
	java FrontEnd

compile:
	javac GraphADT.java
	javac CS400Graph.java
	javac Kruskal.java
	javac Song.java
	javac Genre.java
	javac PlaylistWrangle.java
	javac BackEndFinalProject.java
	javac FrontEnd.java
	javac -cp .:junit5.jar TestSuitePlaylist.java

test: compile
	java -jar junit5.jar --class-path . --scan-classpath --details tree
	

clean:
	$(RM) *.class
