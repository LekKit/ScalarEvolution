package lekkit.scev.gui.util;

import lekkit.rvvm.HIDKeyboard;

import org.lwjgl.input.Keyboard;

public class KeyUtil {
    public static byte hidKeyFromLWJGL(int keycode) {
        switch (keycode) {
            case Keyboard.KEY_A: return HIDKeyboard.HID_KEY_A;
            case Keyboard.KEY_B: return HIDKeyboard.HID_KEY_B;
            case Keyboard.KEY_C: return HIDKeyboard.HID_KEY_C;
            case Keyboard.KEY_D: return HIDKeyboard.HID_KEY_D;
            case Keyboard.KEY_E: return HIDKeyboard.HID_KEY_E;
            case Keyboard.KEY_F: return HIDKeyboard.HID_KEY_F;
            case Keyboard.KEY_G: return HIDKeyboard.HID_KEY_G;
            case Keyboard.KEY_H: return HIDKeyboard.HID_KEY_H;
            case Keyboard.KEY_I: return HIDKeyboard.HID_KEY_I;
            case Keyboard.KEY_J: return HIDKeyboard.HID_KEY_J;
            case Keyboard.KEY_K: return HIDKeyboard.HID_KEY_K;
            case Keyboard.KEY_L: return HIDKeyboard.HID_KEY_L;
            case Keyboard.KEY_M: return HIDKeyboard.HID_KEY_M;
            case Keyboard.KEY_N: return HIDKeyboard.HID_KEY_N;
            case Keyboard.KEY_O: return HIDKeyboard.HID_KEY_O;
            case Keyboard.KEY_P: return HIDKeyboard.HID_KEY_P;
            case Keyboard.KEY_Q: return HIDKeyboard.HID_KEY_Q;
            case Keyboard.KEY_R: return HIDKeyboard.HID_KEY_R;
            case Keyboard.KEY_S: return HIDKeyboard.HID_KEY_S;
            case Keyboard.KEY_T: return HIDKeyboard.HID_KEY_T;
            case Keyboard.KEY_U: return HIDKeyboard.HID_KEY_U;
            case Keyboard.KEY_V: return HIDKeyboard.HID_KEY_V;
            case Keyboard.KEY_W: return HIDKeyboard.HID_KEY_W;
            case Keyboard.KEY_X: return HIDKeyboard.HID_KEY_X;
            case Keyboard.KEY_Y: return HIDKeyboard.HID_KEY_Y;
            case Keyboard.KEY_Z: return HIDKeyboard.HID_KEY_Z;

            case Keyboard.KEY_0: return HIDKeyboard.HID_KEY_0;
            case Keyboard.KEY_1: return HIDKeyboard.HID_KEY_1;
            case Keyboard.KEY_2: return HIDKeyboard.HID_KEY_2;
            case Keyboard.KEY_3: return HIDKeyboard.HID_KEY_3;
            case Keyboard.KEY_4: return HIDKeyboard.HID_KEY_4;
            case Keyboard.KEY_5: return HIDKeyboard.HID_KEY_5;
            case Keyboard.KEY_6: return HIDKeyboard.HID_KEY_6;
            case Keyboard.KEY_7: return HIDKeyboard.HID_KEY_7;
            case Keyboard.KEY_8: return HIDKeyboard.HID_KEY_8;
            case Keyboard.KEY_9: return HIDKeyboard.HID_KEY_9;

            case Keyboard.KEY_RETURN:     return HIDKeyboard.HID_KEY_ENTER;
            case Keyboard.KEY_ESCAPE:     return HIDKeyboard.HID_KEY_ESC;
            case Keyboard.KEY_BACK:       return HIDKeyboard.HID_KEY_BACKSPACE;
            case Keyboard.KEY_TAB:        return HIDKeyboard.HID_KEY_TAB;
            case Keyboard.KEY_SPACE:      return HIDKeyboard.HID_KEY_SPACE;
            case Keyboard.KEY_MINUS:      return HIDKeyboard.HID_KEY_MINUS;
            case Keyboard.KEY_EQUALS:     return HIDKeyboard.HID_KEY_EQUAL;
            case Keyboard.KEY_LBRACKET:   return HIDKeyboard.HID_KEY_LEFTBRACE;
            case Keyboard.KEY_RBRACKET:   return HIDKeyboard.HID_KEY_RIGHTBRACE;
            case Keyboard.KEY_BACKSLASH:  return HIDKeyboard.HID_KEY_BACKSLASH;
            case Keyboard.KEY_SEMICOLON:  return HIDKeyboard.HID_KEY_SEMICOLON;
            case Keyboard.KEY_APOSTROPHE: return HIDKeyboard.HID_KEY_APOSTROPHE;
            case Keyboard.KEY_GRAVE:      return HIDKeyboard.HID_KEY_GRAVE;
            case Keyboard.KEY_COMMA:      return HIDKeyboard.HID_KEY_COMMA;
            case Keyboard.KEY_PERIOD:     return HIDKeyboard.HID_KEY_DOT;
            case Keyboard.KEY_SLASH:      return HIDKeyboard.HID_KEY_SLASH;
            case Keyboard.KEY_CAPITAL:    return HIDKeyboard.HID_KEY_CAPSLOCK;

            case Keyboard.KEY_LCONTROL: return HIDKeyboard.HID_KEY_LEFTCTRL;
            case Keyboard.KEY_LSHIFT:   return HIDKeyboard.HID_KEY_LEFTSHIFT;
            case Keyboard.KEY_LMENU:    return HIDKeyboard.HID_KEY_LEFTALT;
            case Keyboard.KEY_LMETA:    return HIDKeyboard.HID_KEY_LEFTMETA;
            case Keyboard.KEY_RCONTROL: return HIDKeyboard.HID_KEY_RIGHTCTRL;
            case Keyboard.KEY_RSHIFT:   return HIDKeyboard.HID_KEY_RIGHTSHIFT;
            case Keyboard.KEY_RMENU:    return HIDKeyboard.HID_KEY_RIGHTALT;
            case Keyboard.KEY_RMETA:    return HIDKeyboard.HID_KEY_RIGHTMETA;

            case Keyboard.KEY_F1:  return HIDKeyboard.HID_KEY_F1;
            case Keyboard.KEY_F2:  return HIDKeyboard.HID_KEY_F2;
            case Keyboard.KEY_F3:  return HIDKeyboard.HID_KEY_F3;
            case Keyboard.KEY_F4:  return HIDKeyboard.HID_KEY_F4;
            case Keyboard.KEY_F5:  return HIDKeyboard.HID_KEY_F5;
            case Keyboard.KEY_F6:  return HIDKeyboard.HID_KEY_F6;
            case Keyboard.KEY_F7:  return HIDKeyboard.HID_KEY_F7;
            case Keyboard.KEY_F8:  return HIDKeyboard.HID_KEY_F8;
            case Keyboard.KEY_F9:  return HIDKeyboard.HID_KEY_F9;
            case Keyboard.KEY_F10: return HIDKeyboard.HID_KEY_F10;
            case Keyboard.KEY_F11: return HIDKeyboard.HID_KEY_F11;
            case Keyboard.KEY_F12: return HIDKeyboard.HID_KEY_F12;
            case Keyboard.KEY_F13: return HIDKeyboard.HID_KEY_F13;
            case Keyboard.KEY_F14: return HIDKeyboard.HID_KEY_F14;
            case Keyboard.KEY_F15: return HIDKeyboard.HID_KEY_F15;
            case Keyboard.KEY_F16: return HIDKeyboard.HID_KEY_F16;
            case Keyboard.KEY_F17: return HIDKeyboard.HID_KEY_F17;
            case Keyboard.KEY_F18: return HIDKeyboard.HID_KEY_F18;
            case Keyboard.KEY_F19: return HIDKeyboard.HID_KEY_F19;
            // No F24? anyways...

            case Keyboard.KEY_SYSRQ:  return HIDKeyboard.HID_KEY_SYSRQ;
            case Keyboard.KEY_SCROLL: return HIDKeyboard.HID_KEY_SCROLLLOCK;
            case Keyboard.KEY_PAUSE:  return HIDKeyboard.HID_KEY_PAUSE;
            case Keyboard.KEY_INSERT: return HIDKeyboard.HID_KEY_INSERT;
            case Keyboard.KEY_HOME:   return HIDKeyboard.HID_KEY_HOME;
            case Keyboard.KEY_PRIOR:  return HIDKeyboard.HID_KEY_PAGEUP;
            case Keyboard.KEY_DELETE: return HIDKeyboard.HID_KEY_DELETE;
            case Keyboard.KEY_END:    return HIDKeyboard.HID_KEY_END;
            case Keyboard.KEY_NEXT:   return HIDKeyboard.HID_KEY_PAGEDOWN;
            case Keyboard.KEY_RIGHT:  return HIDKeyboard.HID_KEY_RIGHT;
            case Keyboard.KEY_LEFT:   return HIDKeyboard.HID_KEY_LEFT;
            case Keyboard.KEY_DOWN:   return HIDKeyboard.HID_KEY_DOWN;
            case Keyboard.KEY_UP:     return HIDKeyboard.HID_KEY_UP;

            case Keyboard.KEY_NUMLOCK:     return HIDKeyboard.HID_KEY_NUMLOCK;
            case Keyboard.KEY_DIVIDE:      return HIDKeyboard.HID_KEY_KPSLASH;
            case Keyboard.KEY_MULTIPLY:    return HIDKeyboard.HID_KEY_KPASTERISK;
            case Keyboard.KEY_SUBTRACT:    return HIDKeyboard.HID_KEY_KPMINUS;
            case Keyboard.KEY_ADD:         return HIDKeyboard.HID_KEY_KPPLUS;
            case Keyboard.KEY_NUMPADENTER: return HIDKeyboard.HID_KEY_KPENTER;
            case Keyboard.KEY_NUMPAD1:     return HIDKeyboard.HID_KEY_KP1;
            case Keyboard.KEY_NUMPAD2:     return HIDKeyboard.HID_KEY_KP2;
            case Keyboard.KEY_NUMPAD3:     return HIDKeyboard.HID_KEY_KP3;
            case Keyboard.KEY_NUMPAD4:     return HIDKeyboard.HID_KEY_KP4;
            case Keyboard.KEY_NUMPAD5:     return HIDKeyboard.HID_KEY_KP5;
            case Keyboard.KEY_NUMPAD6:     return HIDKeyboard.HID_KEY_KP6;
            case Keyboard.KEY_NUMPAD7:     return HIDKeyboard.HID_KEY_KP7;
            case Keyboard.KEY_NUMPAD8:     return HIDKeyboard.HID_KEY_KP8;
            case Keyboard.KEY_NUMPAD9:     return HIDKeyboard.HID_KEY_KP9;
            case Keyboard.KEY_NUMPAD0:     return HIDKeyboard.HID_KEY_KP0;
            case Keyboard.KEY_DECIMAL:     return HIDKeyboard.HID_KEY_KPDOT;

            // Fix for shift key retardation in LWJGL
            case Keyboard.KEY_AT:         return HIDKeyboard.HID_KEY_2;
            case Keyboard.KEY_CIRCUMFLEX: return HIDKeyboard.HID_KEY_6;
            case Keyboard.KEY_UNDERLINE:  return HIDKeyboard.HID_KEY_MINUS;
            case Keyboard.KEY_COLON:      return HIDKeyboard.HID_KEY_SEMICOLON;

            // Fix for another dumb LWJGL bug where it reports backslash as KEY_NONE
            case Keyboard.KEY_NONE:       return HIDKeyboard.HID_KEY_BACKSLASH;
        }

        System.out.println("Unhandled LWJGL key #" + keycode + " (" + Keyboard.getKeyName(keycode) + ")");
        return HIDKeyboard.HID_KEY_NONE;
    }
}
