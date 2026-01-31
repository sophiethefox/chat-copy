call gradlew build
echo f | xcopy /s /y ".\versions\1.20.0-fabric\build\libs\chat-copy-1.0.0.jar" ".\compiled\chat-copy-1.0.0_(1.20.0 - 1.20.3).jar"
echo f | xcopy /s /y ".\versions\1.20.4-fabric\build\libs\chat-copy-1.0.0.jar" ".\compiled\chat-copy-1.0.0_(1.20.4 - 1.21.8).jar"
echo f | xcopy /s /y ".\versions\1.21.09-fabric\build\libs\chat-copy-1.0.0.jar" ".\compiled\chat-copy-1.0.0_(1.21.9 - 1.21.10).jar"
echo f | xcopy /s /y ".\versions\1.21.11-fabric\build\libs\chat-copy-1.0.0.jar" ".\compiled\chat-copy-1.0.0_(1.21.11).jar"
