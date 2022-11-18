SRC=`find . -name "*.java"`
CLASSES=`find . -name "*.class"`
OUT=ProjetoUnidade3.jar

ProjetoUnidade3:
	javac $(SRC)
	jar cfm $(OUT) MANIFEST.MF $(CLASSES)

clean:
	rm $(CLASSES) $(OUT)
