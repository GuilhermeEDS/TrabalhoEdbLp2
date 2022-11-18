SRC=`find . -name "*.java"`
OUT=ProjetoUnidade3.jar

ProjetoUnidade3:
	javac $(SRC)
	jar cfm $(OUT) MANIFEST.MF *.class

clean:
	rm `find . -name "*.class"` $(OUT)
