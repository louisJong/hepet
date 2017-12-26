set M2_HOME=D:\Program\apache-maven-3.0.4\
echo %M2_HOME%
call mvn eclipse:clean
call mvn eclipse:eclipse -DdownloadSources=true -DdownloadJavadocs=false
@pause


