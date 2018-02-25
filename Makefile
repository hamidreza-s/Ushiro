.PHONY: compile test package run

compile:
	mvn compile

test:
	mvn test

package:
	mvn clean compile assembly:single

run: package
	java -jar target/ushiro-1.0-jar-with-dependencies.jar
