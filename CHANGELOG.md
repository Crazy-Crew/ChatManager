**Fixed:**
- Issue with PlaceholderAPI in messages.
- Issue with title messages toggle, we used the wrong config option for the toggle.

**Changes:**
- Updated internally how messages are handled which includes a safety net if the config gets messed up.
- Added a safety net to config options to ensure the plugin functions even if the config is messed up.
- Prefix is no longer sent in console as you can add it manually to every message.
- {Prefix} is now {prefix}, {Prefix} will work for the time being, but it's recommended you use `Find and Replace` to change it to {prefix}