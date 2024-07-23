**Fixed:**
- Issue with placeholders like {prefix} not being replaced on auto broadcast.
- Added null safety to /reply, fixing an NPE if no recipient found.

**Changes:**
- PlaceholderAPI was not parsing placeholders in the broadcast/autobroadcast features.