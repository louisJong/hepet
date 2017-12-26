@for /r . %%a in (.) do @if exist "%%a\.svn" rd /s /q "%%a\.svn" 
@Rem for /r . %%a in (.) do @if exist "%%a\.svn" 
@echo "%%a\.svn" 