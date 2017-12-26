set M2_HOME=D:\Program\apache-maven-3.0.4
call mvn clean:clean
call mvn -Dmaven.test.skip=true install
@pause