### Added:
- Support for Folia.

### Fixed:
- Issue with placeholders like {prefix} not being replaced on auto broadcast.
- Added null safety to /reply, fixing an NPE if no recipient found.
- Issue where all players could see /staffchat, added a permission check: `chatmanager.staffchat`
- Issue where {player} wasn't replaced for /staffchat in console.
- Issue where if you sent a message in staffchat, it wouldn't show your name but the person receiving it.

### Changes:
- PlaceholderAPI was not parsing placeholders in the broadcast/autobroadcast features.
- Bumped version, because I messed up semver.