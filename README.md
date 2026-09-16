# Ime Warmup

A client-side macOS fix for [MC-310487](https://bugs.mojang.com/browse/MC-310487): the first characters typed
into a text field are committed as raw characters instead of being composed. With a Korean input source,
typing `안녕하세요` in chat comes out as `ㅇㅏㄴ녕하세요`. Japanese and Chinese input sources are affected too.

## Why it happens

`TextInputManager` stops text input whenever no text field is focused, so that gameplay keys are not swallowed
by composition, and starts it again when one gains focus. On macOS the input method is a separate process, so
starting text input does not establish the composition session synchronously — it takes on the order of a
hundred milliseconds. Keystrokes arriving in the meantime are translated by the keyboard layout alone and
committed as raw characters.

## What it does

`TextInputManager.stopTextInput` is the only place vanilla tears the session down, and this mod skips the
`SDL_StopTextInput` call, so it is never stopped. Vanilla's own bookkeeping is left untouched, so it still
knows whether a text field owns the input, and the Ime events that arrive while none does are dropped in
`KeyboardHandler`. That restores vanilla's invariant of producing no text events outside a text field, and
keeps a leftover pre-edit from being replayed into the next field that gains focus.

Launch with `-Dimewarmup.debug=true` to log Ime activations and discarded compositions.
