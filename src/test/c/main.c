#include <windows.h>

int main(int argc, char** argv) {
    while (!GetAsyncKeyState(VK_HOME)) {
    }
    return 0;
}