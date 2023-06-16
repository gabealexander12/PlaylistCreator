run: compile
	java FrontEnd

compile: CS400Graph.java Song.java Genre.java BackEndFinalProject.java Kruskal.java FrontEnd.java PlaylistWrangle.java
         javac CS400Graph.java
	 javac Song.java
	 javac Genre.java
	 javac BackEndFinalProject.java
	 javac Kruskal.java
	 javac FrontEnd.java
	 javac PlaylistWrangle.java
	 javac -cp .:junit5.jar PlaylistTestSuite.java
	

test: compile
	java -jar junit5.jar --class-path . --scan-classpath --details tree

clean:
	$(RM) *.class
