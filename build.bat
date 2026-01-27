call gradlew build
echo f | xcopy /s /y ".\versions\1.21.0-fabric\build\libs\chat-copy-1.0.0.jar" ".\compiled\chat-copy-1.0.0_1.20-1.21.8.jar"
echo f | xcopy /s /y ".\versions\1.21.11-fabric\build\libs\chat-copy-1.0.0.jar" ".\compiled\chat-copy-1.0.0_1.21.9.jar"