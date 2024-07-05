### Added:
- Config check for `/chatradius` to prevent switching channels if the config option `Chat_Radius.Enable` is false.

### Changes:
- The plugin when initially installed has everything disabled out of the box.
- Only sending a message for a repeated command/message if not currently in cooldown.
- Allow 1.20.6-1.21 servers to use the plugin. (this may change in the future if the plugin does not function on 1.20.6)
- Reduced method calls if checks are not passed.
- Replaces override symbols before re-setting the message in chat.
- Cleared recipients of the message instead of removing every player through a loop. we only add the players if they are in range.

### Fixed:
- Always being spammed with `Please wait x time before sending another command/message` due to the timer not being subtracted.
- `chatmanager.chatmanager.staff` should have been `chatmanager.staff`.
- Using the wrong placeholder for `/staff`, internally we used `{players}` while the default config used `{staff}`.
- Issue with staff chat using the wrong configuration path internally.
- Player Name wasn't being replaced for `/staffchat` in the console.
- Issue with chat radius no matter the channel always saying it's enabled.