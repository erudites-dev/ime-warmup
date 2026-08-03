# Ime Warmup

A client-side macOS fix for [MC-310487](https://bugs.mojang.com/browse/MC-310487): the first characters typed
into a text field are committed as raw characters instead of being composed. With a Korean input source,
typing `안녕하세요` in chat comes out as `ㅇㅏㄴ녕하세요`. Japanese and Chinese input sources are affected too.

## Why it happens

Since 26.1 `TextInputManager` turns the Ime off whenever no text field is focused, and back on when one gains
focus. On macOS the input method is a separate process, so activating the text input context does not
establish the composition session synchronously — it takes on the order of a hundred milliseconds. Keystrokes
arriving in the meantime are translated by the keyboard layout alone and committed as raw characters.

## What it does

`TextInputManager.tickOutsideTextInput` is the only place vanilla deactivates the Ime, and this mod skips it,
so the session is never torn down. Composition that then happens while no screen is open is discarded in
`KeyboardHandler.preeditCallback`, which also keeps a leftover pre-edit from being replayed into the next text
field that gains focus.

Launch with `-Dimewarmup.debug=true` to log Ime activations and discarded compositions.
