**Fixed:**
- Issue with placeholders like {prefix} not being replaced on auto broadcast.
- Added null safety to /reply, fixing an NPE if no recipient found.
- Issue where all players could see /staffchat, added a permission check: `chatmanager.staffchat`
- Issue where {player} wasn't replaced for /staffchat in console.

**Changes:**
- PlaceholderAPI was not parsing placeholders in the broadcast/autobroadcast features.