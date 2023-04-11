#include <stdlib.h>
#include <string.h>

int main(int argc, char** argv) {
    if (argc > 1) {
        int size = 40 + strlen(argv[1]);
        char* cmd = malloc(size);
        for (int i = 0; i < size; i++) {
            cmd[i] = '\0';
        }
        strcat(cmd, "java -jar lib\\LyreExecutor-1.0.3.jar \"");
        strcat(cmd, argv[1]);
        strcat(cmd, "\"");
        return system(cmd);
    } else {
        return system("java -jar lib\\LyreExecutor-1.0.3.jar");
    }
    return 0;
}