jlink ^
  --add-modules=javafx.controls,javafx.fxml,java.base,java.logging,java.xml,java.desktop,javafx.graphics ^
  --module-path "%JAVA_HOME%\jmods;%PATH_TO_FX_JMOD%" ^
  --strip-debug --no-header-files --no-man-pages --compress=2 ^
  --output runtime
